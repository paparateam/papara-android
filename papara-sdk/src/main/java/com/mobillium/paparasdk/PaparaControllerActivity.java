package com.mobillium.paparasdk;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.mobillium.paparasdk.utils.PaparaAccountNumberCallback;
import com.mobillium.paparasdk.utils.PaparaLogger;
import com.mobillium.paparasdk.utils.PaparaPaymentCallback;
import com.mobillium.paparasdk.utils.PaparaSendMoneyCallback;
import com.mobillium.paparasdk.utils.UriHelper;

import static com.mobillium.paparasdk.Papara.PAYMENT_CANCEL;
import static com.mobillium.paparasdk.Papara.PAYMENT_FAIL;
import static com.mobillium.paparasdk.Papara.PAYMENT_SUCCESS;
import static com.mobillium.paparasdk.Papara.VALID_FORMAT;
import static com.mobillium.paparasdk.Papara.VALID_MODEL;
import static com.mobillium.paparasdk.Papara.VALID_NOT_INSTALLED;
import static com.mobillium.paparasdk.utils.UriHelper.TYPE_FETC_ACCOUNT_NUM;
import static com.mobillium.paparasdk.utils.UriHelper.TYPE_PAYMENT;

public class PaparaControllerActivity extends AppCompatActivity {

    boolean waitingForResult = false;

    @Override
    protected void onResume() {
        super.onResume();
        if (!waitingForResult) {
            startHandler();
        }
    }

    private void startHandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PaparaControllerActivity.this.finish();
            }
        }, 1000);
    }

    private void setResultHandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                waitingForResult = false;
            }
        }, 2000);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        waitingForResult = true;
        PaparaSendMoney sendMoney = getIntent().getExtras().getParcelable("data");
        PaparaPayment payment = getIntent().getExtras().getParcelable("payment");
        String type = getIntent().getExtras().getString("type");
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
            Papara.getInstance().getPaparaCallback().onFailure(message, code);
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

        if (checkMultipleOccurances(paparaSendMoney.getAmount(), ",") && checkMultipleOccurances(paparaSendMoney.getAmount(), ".")) {
            try {
                Log.d("PAPARA URI", "startPaparaApp: " + UriHelper.objectToUri(paparaModelContainer, paparaSendMoney.getType()));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(UriHelper.objectToUri(paparaModelContainer, paparaSendMoney.getType()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                waitingForResult = true;
            } catch (Exception ex) {
                waitingForResult = false;
                ex.printStackTrace();
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
            Log.d("PAPARA URI", "startPaparaApp: " + UriHelper.objectToUri(paparaModelContainer, TYPE_FETC_ACCOUNT_NUM));
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(UriHelper.objectToUri(paparaModelContainer, TYPE_FETC_ACCOUNT_NUM));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            waitingForResult = true;
        } catch (Exception ex) {
            waitingForResult = false;
            ex.printStackTrace();
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
            Log.d("PAPARA URI", "startPaparaApp: " + UriHelper.objectToUri(paparaModelContainer, TYPE_PAYMENT));
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(UriHelper.objectToUri(paparaModelContainer, TYPE_PAYMENT));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            waitingForResult = true;
        } catch (Exception ex) {
            waitingForResult = false;
            ex.printStackTrace();
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
        switch (result) {
            case Papara.PAYMENT_SUCCESS:
                if (Papara.getInstance().getPaparaCallback() instanceof PaparaAccountNumberCallback) {
                    ((PaparaAccountNumberCallback) Papara.getInstance().getPaparaCallback()).onSuccess(message, code, accountNumber);
                } else if (Papara.getInstance().getPaparaCallback() instanceof PaparaSendMoneyCallback) {
                    ((PaparaSendMoneyCallback) Papara.getInstance().getPaparaCallback()).onSuccess(message, code);
                } else if (Papara.getInstance().getPaparaCallback() instanceof PaparaPaymentCallback) {
                    ((PaparaPaymentCallback) Papara.getInstance().getPaparaCallback()).onSuccess(message, code);
                }

                PaparaLogger.writeInfoLog("Result: Sucess");
                break;
            case Papara.PAYMENT_FAIL:
                Papara.getInstance().getPaparaCallback().onFailure(message, code);
                PaparaLogger.writeInfoLog("Result: Failure");
                break;
            case PAYMENT_CANCEL:
                Papara.getInstance().getPaparaCallback().onCancel(message, code);
                PaparaLogger.writeInfoLog("Result: Cancel");
                break;

        }
        finish();
    }


    private boolean checkMultipleOccurances(String str, String findStr) {
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
            ex.printStackTrace();
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
                        Papara.getInstance().getPaparaCallback().onFailure(msg, code);
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
                    String msg = getString(R.string.cancel);
                    final int code = 0;
                    Papara.getInstance().getPaparaCallback().onFailure(msg, code);
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
            if (resultCode == PAYMENT_CANCEL) {
                Papara.getInstance().getPaparaCallback().onCancel(data.getExtras().getString("message"), resultCode);
                PaparaLogger.writeInfoLog("Result: Cancel");
            } else if (resultCode == PAYMENT_FAIL) {
                Papara.getInstance().getPaparaCallback().onFailure(data.getExtras().getString("message"), resultCode);
                PaparaLogger.writeInfoLog("Result: Failure");
            } else if (resultCode == PAYMENT_SUCCESS) {
                ((PaparaPaymentCallback) Papara.getInstance().getPaparaCallback()).onSuccess(data.getExtras().getString("message"), resultCode);
            }
        }
    }

    private void goToApp() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri
                .parse("market://details?id=com.mobillium.papara"));
        if (!MyStartActivity(intent)) {
            intent.setData(Uri
                    .parse("https://play.google.com/store/apps/details?id=com.mobillium.papara"));
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
}
