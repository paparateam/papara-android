package com.papara.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.papara.sdk.utils.PaparaCallback;
import com.papara.sdk.utils.PaparaException;
import com.papara.sdk.utils.PaparaLogger;
import com.papara.sdk.utils.PaparaSdkNotInitializedException;
import com.papara.sdk.utils.PaparaSendMoneyCallback;

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
    public static final int PAYMENT_FAIL = -1;
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
            PaparaLogger.writeSdkErrorLog();
            throw new PaparaSdkNotInitializedException("You must initialize the Papara SDK first");
        }
    }

    public String getPackageName() {
        if (applicationContext == null) {
            PaparaLogger.writeSdkErrorLog();
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
     * @param appId              String identifier value provided by Papara
     * @param sandBox            boolean value determines SKD works in sandbox or live mode
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
            String message = "Package Name Not found";
            PaparaLogger.writeErrorLog(message);
            throw new PaparaException(message);
        }
    }


    public PaparaCallback getPaparaCallback() {
        return paparaCallback;
    }


    /**
     * This function starts Send Money Flow and launches Papara App
     *
     * @param activity        The activity context
     * @param paparaSendMoney The object model of send money
     * @param callback        The callback that will be called when the returned to app from Papara. Cannot be null.
     */
    public void sendMoney(Activity activity, PaparaSendMoney paparaSendMoney, @NonNull final PaparaSendMoneyCallback callback) {
        if (!sdkInitialized) {
            PaparaLogger.writeSdkErrorLog();
            throw new PaparaSdkNotInitializedException("You must initialize the Papara SDK first");
        }
        this.paparaCallback = callback;
        Intent intent = new Intent(activity, PaparaControllerActivity.class);
        intent.putExtra("data", paparaSendMoney);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    /**
     * This function starts Fetching Account Number Flow and launches Papara App
     *
     * @param activity The activity context
     * @param callback The callback that will be called when the returned to app from Papara. Cannot be null.
     */
    public void getAccountNumber(Activity activity, @NonNull final PaparaCallback callback) {
        if (!sdkInitialized) {
            PaparaLogger.writeSdkErrorLog();
            throw new PaparaSdkNotInitializedException("You must initialize the Papara SDK first");
        }
        this.paparaCallback = callback;
        Intent intent = new Intent(activity, PaparaControllerActivity.class);
        intent.putExtra("type", "accountNumber");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    /**
     * This function starts Fetching Account Number Flow and launches Papara App
     *
     * @param fragment The fragment context
     * @param callback The callback that will be called when the returned to app from Papara. Cannot be null.
     */
    public void makePayment(
            Fragment fragment,
            PaparaPayment paparaPayment,
            @NonNull final PaparaCallback callback
    ) {
        makePayment(fragment.getActivity(), paparaPayment, callback);
    }

    /**
     * This function starts Fetching Account Number Flow and launches Papara App
     *
     * @param activity The activity context
     * @param callback The callback that will be called when the returned to app from Papara. Cannot be null.
     */
    public void makePayment(
            Activity activity,
            PaparaPayment paparaPayment,
            @NonNull final PaparaCallback callback
    ) {
        makePayment((Context) activity, paparaPayment, callback);
    }

    /**
     * This function starts Fetching Account Number Flow and launches Papara App
     *
     * @param context  The context
     * @param callback The callback that will be called when the returned to app from Papara. Cannot be null.
     */
    public void makePayment(
            Context context,
            PaparaPayment paparaPayment,
            @NonNull final PaparaCallback callback
    ) {
        if (!sdkInitialized) {
            PaparaLogger.writeSdkErrorLog();
            throw new PaparaSdkNotInitializedException("You must initialize the Papara SDK first");
        }

        this.paparaCallback = callback;

        Intent intent = new Intent(context, PaparaControllerActivity.class);
        intent.putExtra("payment", paparaPayment);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
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
