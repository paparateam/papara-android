package com.mobillium.paparasdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.mobillium.paparasdk.utils.PaparaCallback;
import com.mobillium.paparasdk.utils.PaparaException;
import com.mobillium.paparasdk.utils.PaparaSdkNotInitializedException;

/**
 * Created by oguzhandongul on 28/11/2016.
 */

public class Papara {

    static Papara instance = null;
    static String appId;
    static Context applicationContext;
    static Boolean sdkInitialized = false; //Default false
    boolean isDebugEnabled = false; //Default false

    public static boolean SANDBOX_MODE = false; //Default false
    public static String PROD_HOST = "papara";
    public static String SANDBOX_HOST = "papara-sandbox";


    public static final int PAYMENT_SUCCESS = 1;
    public static final int PAYMENT_FAIL = 0;
    public static final int PAYMENT_CANCEL = 2;

    public static final int VALID_MODEL = -2;
    public static final int VALID_FORMAT = -3;
    public static final int VALID_NOT_INSTALLED = -4;


    PaparaCallback paparaCallback;


    /**
     * Default empty constructor
     */
    private Papara() {
    }

    /**
     * This function creates an instance of Papara
     * Must be called after "sdkInitialize" method otherwise will throw an exception
     */
    public static Papara getInstance() {
        try {
            if (instance == null) {
                instance = new Papara();
            }
            return instance;
        } catch (PaparaSdkNotInitializedException ex) {
            throw new PaparaSdkNotInitializedException("You must initialize the Papara SDK first");
        }
    }

    public String getPackageName() {
        if (applicationContext == null) {
            throw new PaparaSdkNotInitializedException("You must initialize the Papara SDK first");
        }
        return applicationContext.getPackageName();
    }

    public boolean isDebugEnabled() {
        return isDebugEnabled;
    }

    /**
     * This function sets the debug state of Papara SDK
     * If it is true, Logs will appear on Android Monitor / LogCat
     * If it is false Logs will not appear on Android Monitor / LogCat
     *
     * @param debugEnabled the boolean value of debug is enabled or not
     */
    public void setDebugEnabled(boolean debugEnabled) {
        isDebugEnabled = debugEnabled;
    }

    /**
     * This function initializes the Papara SDK, the behavior of Papara SDK functions are
     * undetermined if this function is not called. It should be called as early as possible.
     *
     * @param applicationContext The application context
     * @param appId String identifier value provided by Papara
     * @param sandBox boolean value determines SKD works in sandbox or live mode
     */
    public static synchronized void sdkInitialize(Context applicationContext, String appId, boolean sandBox) {
        if (sdkInitialized == true) {
            return;
        }

        Papara.applicationContext = applicationContext.getApplicationContext();
        Papara.appId = appId;
        setSandBoxMode(sandBox);
        sdkInitialized = true;
    }


    static PackageInfo getPackageInfo() {
        try {
            return applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException ex) {
            throw new PaparaException("Package Name Not found");
        }
    }


    public PaparaCallback getPaparaCallback() {
        return paparaCallback;
    }

    /**
     * This function starts Payment Flow and launches Papara App
     *
     * @param activity      The activity context
     * @param paparaPayment The object model of payment
     * @param callback      The callback that will be called when the returned to app from Papara. Cannot be null.
     */
    public void sendMoney(Activity activity, PaparaPayment paparaPayment, @NonNull final PaparaCallback callback) {
        if (!sdkInitialized) {
            throw new PaparaSdkNotInitializedException("You must initialize the Papara SDK first");
        }
        this.paparaCallback = callback;
        Intent intent = new Intent(activity, PaparaPaymentActivity.class);
        intent.putExtra("data", paparaPayment);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }


    private static void setSandBoxMode(boolean val) {
        SANDBOX_MODE = val;
    }

    public static String getDeepLinkHost() {
        if (SANDBOX_MODE) {
            return SANDBOX_HOST;
        }
        return PROD_HOST;
    }
}
