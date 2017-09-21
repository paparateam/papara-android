package com.mobillium.paparasdk.sdk.sampleapp.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mobillium.paparasdk.Papara;
import com.mobillium.paparasdk.PaparaSendMoney;
import com.mobillium.paparasdk.sdk.sampleapp.R;
import com.mobillium.paparasdk.utils.PaparaSendMoneyCallback;

import static com.mobillium.paparasdk.utils.UriHelper.TYPE_SEND_ACCOUNT;
import static com.mobillium.paparasdk.utils.UriHelper.TYPE_SEND_MAIL;
import static com.mobillium.paparasdk.utils.UriHelper.TYPE_SEND_PHONE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText etPhone, etMail, etAccount, etAmount;
    private LinearLayout llMain;
    private RadioGroup radioGroup;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createViews();


    }

    private void createViews() {
        findViewById(R.id.button).setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etMail = (EditText) findViewById(R.id.etMail);
        etAccount = (EditText) findViewById(R.id.etAccount);
        etAmount = (EditText) findViewById(R.id.etAmount);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        llMain = (LinearLayout) findViewById(R.id.llMain);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                etPhone.setVisibility(checkedId == R.id.rbPhone ? View.VISIBLE : View.GONE);
                etMail.setVisibility(checkedId == R.id.rbEmail ? View.VISIBLE : View.GONE);
                etAccount.setVisibility(checkedId == R.id.rbAccount ? View.VISIBLE : View.GONE);

                tvTitle.setText(getSelectedTitle());


            }
        });
    }

    private EditText getSelectedET() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rbPhone:
                return etPhone;
            case R.id.rbEmail:
                return etMail;
            case R.id.rbAccount:
                return etAccount;
        }

        return null;
    }

    private String getSelectedTitle() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rbPhone:
                return "Telefon Numarası";
            case R.id.rbEmail:
                return "E-posta Adresi";
            case R.id.rbAccount:
                return "Papara Hesap Numarası";
        }

        return null;
    }

    private String getSelectedType() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rbPhone:
                return TYPE_SEND_PHONE;
            case R.id.rbEmail:
                return TYPE_SEND_MAIL;
            case R.id.rbAccount:
                return TYPE_SEND_ACCOUNT;
        }

        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                if (checkFields()) {
                    PaparaSendMoney sendMoney = new PaparaSendMoney();
                    sendMoney.setReceiver(getSelectedET().getText().toString().trim());
                    sendMoney.setAmount(etAmount.getText().toString().trim());
                    sendMoney.setType(getSelectedType());

                    Papara.getInstance().sendMoney(MainActivity.this, sendMoney, new PaparaSendMoneyCallback() {
                        @Override
                        public void onSuccess(String message, int code) {
                            //Sending Money Successfull
                            showResultDialog(message, code);
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(String message, int code) {
                            //Sending Money Failed
                            showResultDialog(message, code);
                            Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancel(String message, int code) {
                            //Sending Money Cancelled by user
                            showResultDialog(message, code);
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
