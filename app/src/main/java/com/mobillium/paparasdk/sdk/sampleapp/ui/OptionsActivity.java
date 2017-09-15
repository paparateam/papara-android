package com.mobillium.paparasdk.sdk.sampleapp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mobillium.paparasdk.Papara;
import com.mobillium.paparasdk.sdk.sampleapp.R;
import com.mobillium.paparasdk.utils.PaparaAccountNumberCallback;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Papara.sdkInitialize(getApplicationContext(), "87826504", false);
        Papara.getInstance().setDebugEnabled(false);

        findViewById(R.id.buttonSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OptionsActivity.this, MainActivity.class));
            }
        });

        findViewById(R.id.buttonNumber).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Papara.getInstance().getAccountNumber(OptionsActivity.this, new PaparaAccountNumberCallback() {
                    @Override
                    public void onSuccess(String message, int code, String accountNumber) {
                        //Payment Successful
                        showResultDialog(message, code);
                        Toast.makeText(OptionsActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String message, int code) {
                        //Payment Failed
                        showResultDialog(message, code);
                        Toast.makeText(OptionsActivity.this, "Fail", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancel(String message, int code) {
                        //Payment Cancelled by user
                        showResultDialog(message, code);
                        Toast.makeText(OptionsActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        findViewById(R.id.buttonPayment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OptionsActivity.this, PaymentActivity.class));

            }
        });
    }

    private void showResultDialog(final String message, int code) {

        try {
            final AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(getString(com.mobillium.paparasdk.R.string.title))
                    .setMessage(message + " (" + code + ")")
                    .setPositiveButton(getString(R.string.done), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create();

            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
