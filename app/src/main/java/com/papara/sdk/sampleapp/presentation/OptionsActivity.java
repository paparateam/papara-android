package com.papara.sdk.sampleapp.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.papara.sdk.sampleapp.R;
import com.papara.sdk.Papara;
import com.papara.sdk.utils.PaparaAccountNumberCallback;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Papara.sdkInitialize(getApplicationContext(), "87826504", true);
        Papara.getInstance().setDebugEnabled(true);

        findViewById(R.id.buttonSend).setOnClickListener(v ->
                startActivity(new Intent(OptionsActivity.this, MainActivity.class))
        );

        findViewById(R.id.buttonNumber).setOnClickListener(v -> Papara.getInstance().getAccountNumber(OptionsActivity.this, new PaparaAccountNumberCallback() {
            @Override
            public void onSuccess(String message, int code, String accountNumber) {
                //Fetching Successful
                showResultDialog(message, code);
                Toast.makeText(OptionsActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message, int code) {
                //Fetching Failed
                showResultDialog(message, code);
                Toast.makeText(OptionsActivity.this, "Fail", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel(String message, int code) {
                //Fetching Cancelled by user
                showResultDialog(message, code);
                Toast.makeText(OptionsActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        }));


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
                    .setTitle(getString(R.string.title))
                    .setMessage(message + " (" + code + ")")
                    .setPositiveButton(getString(R.string.done), (dialogInterface, i) -> dialogInterface.dismiss())
                    .create();

            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
