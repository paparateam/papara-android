package com.mobillium.paparasdk.sdk.sampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobillium.paparasdk.Papara;
import com.mobillium.paparasdk.PaparaPayment;
import com.mobillium.paparasdk.utils.PaparaCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText etWalletId, etAmount, etDesc;
    private LinearLayout llMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createViews();

        Papara.sdkInitialize(getApplicationContext(), "87826504", true);
        Papara.getInstance().setDebugEnabled(false);


    }

    private void createViews() {
        findViewById(R.id.button).setOnClickListener(this);
        etWalletId = (EditText) findViewById(R.id.etWalletId);
        etAmount = (EditText) findViewById(R.id.etAmount);
        etDesc = (EditText) findViewById(R.id.etDesc);
        llMain = (LinearLayout) findViewById(R.id.llMain);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                if (checkFields()) {
                    PaparaPayment payment = new PaparaPayment();
                    payment.setWalletId(etWalletId.getText().toString().trim());
                    payment.setAmount(etAmount.getText().toString().trim());
                    payment.setDesc(etDesc.getText().toString().trim());

                    Papara.getInstance().sendMoney(MainActivity.this, payment, new PaparaCallback() {
                        @Override
                        public void onSuccess(String message, int code) {
                            //Odeme islemi basarili
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(String message, int code) {
                            //Odeme islemi basarisiz
                            Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancel(String message, int code) {
                            //Odeme islemi iptal edildi
                            Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
        }
    }

    private boolean checkFields() {
        for (int i = 0; i < llMain.getChildCount(); i++) {
            if (llMain.getChildAt(i) instanceof EditText) {
                EditText editText = (EditText) llMain.getChildAt(i);
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    Toast.makeText(this, "Lütfen tüm alanları doldurunuz.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }

        return true;
    }

}
