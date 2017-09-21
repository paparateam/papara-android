package com.mobillium.paparasdk.sdk.sampleapp.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.mobillium.paparasdk.Papara;
import com.mobillium.paparasdk.PaparaPayment;
import com.mobillium.paparasdk.sdk.sampleapp.R;
import com.mobillium.paparasdk.sdk.sampleapp.webservice.ServiceCallback;
import com.mobillium.paparasdk.sdk.sampleapp.webservice.ServiceException;
import com.mobillium.paparasdk.sdk.sampleapp.webservice.ServiceOperations;
import com.mobillium.paparasdk.sdk.sampleapp.webservice.models.BaseResponse;
import com.mobillium.paparasdk.sdk.sampleapp.webservice.models.PaymentRequest;
import com.mobillium.paparasdk.sdk.sampleapp.webservice.models.PaymentResponse;
import com.mobillium.paparasdk.sdk.sampleapp.webservice.models.RequestModel;
import com.mobillium.paparasdk.utils.PaparaPaymentCallback;

public class PaymentActivity extends AppCompatActivity {

    EditText etAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        etAmount = findViewById(R.id.etAmount);


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                paymentRequest();

            }
        });
    }

    private void paymentRequest() {

        PaymentRequest cardAddRequest = new PaymentRequest(etAmount.getText().toString().trim().replace("\\,","\\."),"123456789","Test payment","https://www.paparamerchant.com/ipn","https://www.paparamerchant.com/checkout");
        ServiceOperations.makeRequest(PaymentActivity.this, new RequestModel(Request.Method.POST, "payments", "Loading...", cardAddRequest), new ServiceCallback<PaymentResponse>() {
            @Override
            public void success(PaymentResponse result) {
                PaparaPayment paparaPayment = new PaparaPayment();
                paparaPayment.setPaymentId(result.getId());
                paparaPayment.setPaymentUrl(result.getPaymentUrl());
                paparaPayment.setReturningRedirectUrl(result.getReturningRedirectUrl());
                Papara.getInstance().makePayment(PaymentActivity.this, paparaPayment, new PaparaPaymentCallback() {
                    @Override
                    public void onSuccess(String message, int code) {
                        //Payment Successful
                        showResultDialog(message, code);
                        Toast.makeText(PaymentActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String message, int code) {
                        //Payment Failed
                        showResultDialog(message, code);
                        Toast.makeText(PaymentActivity.this, "Fail", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancel(String message, int code) {
                        //Payment Cancelled by user
                        showResultDialog(message, code);
                        Toast.makeText(PaymentActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void error(ServiceException e) {


            }
        }, new TypeToken<BaseResponse<PaymentResponse>>() {
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
