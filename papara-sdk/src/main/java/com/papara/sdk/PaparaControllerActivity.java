package com.papara.sdk;

import static com.papara.sdk.Papara.PAYMENT_CANCEL;
import static com.papara.sdk.Papara.PAYMENT_FAIL;
import static com.papara.sdk.Papara.PAYMENT_SUCCESS;
import static com.papara.sdk.Papara.VALID_FORMAT;
import static com.papara.sdk.Papara.VALID_MODEL;
import static com.papara.sdk.Papara.VALID_NOT_INSTALLED;
import static com.papara.sdk.utils.UriHelper.TYPE_FETC_ACCOUNT_NUM;
import static com.papara.sdk.utils.UriHelper.TYPE_PAYMENT;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.papara.sdk.utils.PaparaAccountNumberCallback;
import com.papara.sdk.utils.PaparaLogger;
import com.papara.sdk.utils.PaparaPaymentCallback;
import com.papara.sdk.utils.PaparaSendMoneyCallback;
import com.papara.sdk.utils.UriHelper;

public class PaparaControllerActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "data";
    public static final String EXTRA_PAYMENT = "payment";
    public static final String EXTRA_TYPE = "type";

    boolean waitingForResult = false;
    PaparaSendMoney sendMoney;
    PaparaPayment payment;
    String type;

    @Override
    protected void onResume() {
        super.onResume();
        if (!waitingForResult) {
            startHandler();
        }
    }

    private void startHandler() {
        new Handler().postDelayed(() -> PaparaControllerActivity.this.finish(), 1000);
    }

    private void setResultHandler() {
        new Handler().postDelayed(() -> waitingForResult = false, 2000);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        waitingForResult = true;

        if (savedInstanceState != null) {
            sendMoney = savedInstanceState.getParcelable(EXTRA_DATA);
            payment = savedInstanceState.getParcelable(EXTRA_PAYMENT);
            type = savedInstanceState.getString(EXTRA_TYPE);
        } else {
            sendMoney = getIntent().getExtras().getParcelable(EXTRA_DATA);
            payment = getIntent().getExtras().getParcelable(EXTRA_PAYMENT);
            type = getIntent().getExtras().getString(EXTRA_TYPE);
        }

        if (sendMoney != null) {
            startPaparaApp(sendMoney);
        } else if (type != null) {
            startPaparaAppForAccountNumber();
        } else if (payment != null) {
            startPaparaAppForPayment(payment);
        } else {
            waitingForResult = false;
            String message = getString(R.string.validation_payment_model);
            int code = VALID_MODEL;
            if (Papara.getInstance().getPaparaCallback() != null) {
                Papara.getInstance().getPaparaCallback().onFailure(message, code);
            }
            PaparaLogger.writeErrorLog("You have to send a valid PaparaSendMoney model to SDK");
            finish();
        }
    }


    private void startPaparaApp(PaparaSendMoney paparaSendMoney) {
        //Set Device & App Related Info
        PaparaModelContainer paparaModelContainer = new PaparaModelContainer();
        paparaModelContainer.setPaparaSendMoney(paparaSendMoney);
        paparaModelContainer.setAppBuild("" + Papara.getInstance().getPackageInfo().versionCode);
        paparaModelContainer.setAppVersion(Papara.getInstance().getPackageInfo().versionName);
        paparaModelContainer.setBrand(Build.BRAND);
        paparaModelContainer.setModel(Build.MODEL);
        paparaModelContainer.setOsVersion(Build.VERSION.RELEASE);
        paparaModelContainer.setPackageName(Papara.getInstance().getPackageName());
        paparaModelContainer.setSdkVersion(PaparaSdkVersion.BUILD);
        paparaModelContainer.setAppId(Papara.appId);
        paparaModelContainer.setDisplayName(getApplicationName());

        if (checkMultipleOccurrences(paparaSendMoney.getAmount(), ",") && checkMultipleOccurrences(paparaSendMoney.getAmount(), ".")) {
            try {
                String uri = "URI: " + UriHelper.objectToUri(paparaModelContainer, paparaSendMoney.getType());
                PaparaLogger.writeInfoLog(uri);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(UriHelper.objectToUri(paparaModelContainer, paparaSendMoney.getType()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                waitingForResult = true;
            } catch (Exception ex) {
                waitingForResult = false;
                PaparaLogger.writeErrorLog(ex.getMessage());
                showAppDialog(null);
            }

            setResultHandler();
        } else {
            waitingForResult = false;
            String message = getString(R.string.validation_format);
            int code = VALID_FORMAT;
            Papara.getInstance().getPaparaCallback().onFailure(message, code);
            PaparaLogger.writeInfoLog("Result: Amount Format Failure");
            finish();
        }
    }

    private void startPaparaAppForAccountNumber() {
        //Set Device & App Related Info
        PaparaModelContainer paparaModelContainer = new PaparaModelContainer();
        paparaModelContainer.setAppBuild("" + Papara.getInstance().getPackageInfo().versionCode);
        paparaModelContainer.setAppVersion(Papara.getInstance().getPackageInfo().versionName);
        paparaModelContainer.setBrand(Build.BRAND);
        paparaModelContainer.setModel(Build.MODEL);
        paparaModelContainer.setOsVersion(Build.VERSION.RELEASE);
        paparaModelContainer.setPackageName(Papara.getInstance().getPackageName());
        paparaModelContainer.setSdkVersion(PaparaSdkVersion.BUILD);
        paparaModelContainer.setAppId(Papara.appId);
        paparaModelContainer.setDisplayName(getApplicationName());

        try {
            String uri = "URI: " + UriHelper.objectToUri(paparaModelContainer, TYPE_FETC_ACCOUNT_NUM);
            PaparaLogger.writeInfoLog(uri);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(UriHelper.objectToUri(paparaModelContainer, TYPE_FETC_ACCOUNT_NUM));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            waitingForResult = true;
        } catch (Exception ex) {
            waitingForResult = false;
            PaparaLogger.writeErrorLog(ex.getMessage());
            showAppDialog(null);
        }

        setResultHandler();
    }

    private void startPaparaAppForPayment(PaparaPayment paparaPayment) {
        //Set Device & App Related Info
        PaparaModelContainer paparaModelContainer = new PaparaModelContainer();
        paparaModelContainer.setPaparaPayment(paparaPayment);
        paparaModelContainer.setAppBuild("" + Papara.getInstance().getPackageInfo().versionCode);
        paparaModelContainer.setAppVersion(Papara.getInstance().getPackageInfo().versionName);
        paparaModelContainer.setBrand(Build.BRAND);
        paparaModelContainer.setModel(Build.MODEL);
        paparaModelContainer.setOsVersion(Build.VERSION.RELEASE);
        paparaModelContainer.setPackageName(Papara.getInstance().getPackageName());
        paparaModelContainer.setSdkVersion(PaparaSdkVersion.BUILD);
        paparaModelContainer.setAppId(Papara.appId);
        paparaModelContainer.setDisplayName(getApplicationName());

        try {
            String uri = "URI: " + UriHelper.objectToUri(paparaModelContainer, TYPE_PAYMENT);
            PaparaLogger.writeInfoLog(uri);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(UriHelper.objectToUri(paparaModelContainer, TYPE_PAYMENT));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            waitingForResult = true;
        } catch (Exception ex) {
            waitingForResult = false;
            PaparaLogger.writeErrorLog(ex.getMessage());
            showAppDialog(paparaPayment);
        }

        setResultHandler();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int result = intent.getExtras().getInt("result");
        String message = intent.getExtras().getString("message", "");
        int code = intent.getExtras().getInt("code", 0);
        String accountNumber = null;
        if (intent.getExtras().containsKey("accountNumber")) {
            accountNumber = intent.getExtras().getString("accountNumber", "");
        }
        PaparaLogger.writeInfoLog("Result: onNewIntent" + result);
        PaparaLogger.writeInfoLog("Result: onNewIntent" + message);
        PaparaLogger.writeInfoLog("Result: onNewIntent" + code);
        PaparaLogger.writeInfoLog("Result: onNewIntent" + accountNumber);
        switch (result) {
            case PAYMENT_SUCCESS:
                if (Papara.getInstance().getPaparaCallback() instanceof PaparaAccountNumberCallback) {
                    ((PaparaAccountNumberCallback) Papara.getInstance().getPaparaCallback()).onSuccess(message, code, accountNumber);
                } else if (Papara.getInstance().getPaparaCallback() instanceof PaparaSendMoneyCallback) {
                    ((PaparaSendMoneyCallback) Papara.getInstance().getPaparaCallback()).onSuccess(message, code);
                } else if (Papara.getInstance().getPaparaCallback() instanceof PaparaPaymentCallback) {
                    ((PaparaPaymentCallback) Papara.getInstance().getPaparaCallback()).onSuccess(message, code);
                } else {
                    PaparaLogger.writeInfoLog("Result: PAYMENT_SUCCESS - NO CONDITION ");
                }

                PaparaLogger.writeInfoLog("Result: PAYMENT_SUCCESS " + message);
                break;
            case PAYMENT_FAIL:
                Papara.getInstance().getPaparaCallback().onFailure(message, code);
                PaparaLogger.writeInfoLog("Result: PAYMENT_FAIL " + message);
                break;
            case PAYMENT_CANCEL:
                Papara.getInstance().getPaparaCallback().onCancel(message, code);
                PaparaLogger.writeInfoLog("Result: PAYMENT_CANCEL" + message);
                break;

        }
        finish();
    }


    private boolean checkMultipleOccurrences(String str, String findStr) {
        int lastIndex = 0;
        int count = 0;

        while (lastIndex != -1) {

            lastIndex = str.indexOf(findStr, lastIndex);

            if (lastIndex != -1) {
                count++;
                lastIndex += findStr.length();
            }
        }

        if (count > 1) {
            return false;
        }


        try {
            Double.parseDouble(str.replace(",", "."));
        } catch (Exception ex) {
            PaparaLogger.writeErrorLog(ex.getMessage());
            return false;
        }

        return true;
    }

    private void showAppDialog(final Parcelable data) {


        String message = getString(R.string.install_papara);

        //change message if Sandbox mode on
        if (Papara.SANDBOX_MODE) {
            message = getString(R.string.install_papara_sandbox);
        }

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.title))
                .setMessage(message)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        String msg = getString(R.string.not_installed);
                        final int code = VALID_NOT_INSTALLED;
                        Papara.getInstance().getPaparaCallback().onCancel(msg, code);
                        PaparaLogger.writeInfoLog("Result: Papara App not Installed");
                        finish();
                    }
                }).create();

        if (Papara.SANDBOX_MODE) {
            //add single button if Sandbox mode on
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String msg = getString(R.string.not_installed);
                    final int code = VALID_NOT_INSTALLED;
                    Papara.getInstance().getPaparaCallback().onFailure(msg, code);
                    PaparaLogger.writeInfoLog("Result: Papara App not Installed");
                    finish();
                }
            });

            if (data != null) {
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.continue_), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(PaparaControllerActivity.this, PaparaWebViewActivity.class).putExtra("data", data), 345);
                        waitingForResult = false;
                    }
                });
            }
        } else {
            //add two buttons if Sandbox mode off
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.install_now), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    goToApp();
                    PaparaLogger.writeInfoLog("Result: User clicked to install the App");
                    finish();
                }
            });
            if (data != null) {
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.continue_), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(PaparaControllerActivity.this, PaparaWebViewActivity.class).putExtra("data", data), 345);
                        waitingForResult = false;
                    }
                });
            } else {
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String msg = getString(R.string.not_installed);
                        final int code = VALID_NOT_INSTALLED;
                        Papara.getInstance().getPaparaCallback().onFailure(msg, code);
                        PaparaLogger.writeInfoLog("Result: Papara App not Installed");
                        finish();
                    }
                });


            }
        }

        waitingForResult = true;
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 345) {
            String message = "";
            try {
                message = data.getExtras().getString("message");
            } catch (Exception ex) {
                message = "";
                PaparaLogger.writeErrorLog(ex.getMessage());
            }

            if (resultCode == PAYMENT_CANCEL) {
                PaparaLogger.writeInfoLog("Result: PAYMENT_CANCEL " + message);
                Papara.getInstance().getPaparaCallback().onCancel(message, resultCode);
            } else if (resultCode == PAYMENT_FAIL) {
                PaparaLogger.writeInfoLog("Result: PAYMENT_FAIL " + message);
                Papara.getInstance().getPaparaCallback().onFailure(message, resultCode);
            } else if (resultCode == PAYMENT_SUCCESS) {
                PaparaLogger.writeInfoLog("Result: PAYMENT_SUCCESS " + message);
                ((PaparaPaymentCallback) Papara.getInstance().getPaparaCallback()).onSuccess(message, resultCode);
            } else {
                PaparaLogger.writeInfoLog("onActivityResult - NO CONDITION " + message);
            }
        }
        PaparaLogger.writeInfoLog("onActivityResult - requestCode different");
    }

    private void goToApp() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=com.mobillium.papara"));

        if (!MyStartActivity(intent)) {
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.mobillium.papara"));

            if (!MyStartActivity(intent)) {
                Toast.makeText(
                        this,
                        "Cihazınızda Google Play uygulaması yüklü değildir.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean MyStartActivity(Intent aIntent) {
        try {
            this.startActivity(aIntent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    public static String getApplicationName() {
        ApplicationInfo applicationInfo = Papara.applicationContext.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : Papara.applicationContext.getString(stringId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable(EXTRA_DATA, sendMoney);
        outState.putParcelable(EXTRA_PAYMENT, payment);
        outState.putString(EXTRA_TYPE, type);
    }
}
