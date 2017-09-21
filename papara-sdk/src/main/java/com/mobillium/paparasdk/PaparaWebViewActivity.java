package com.mobillium.paparasdk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.net.URL;

import static com.mobillium.paparasdk.Papara.PAYMENT_CANCEL;
import static com.mobillium.paparasdk.Papara.PAYMENT_FAIL;
import static com.mobillium.paparasdk.Papara.PAYMENT_SUCCESS;

public class PaparaWebViewActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;
    PaparaPayment paparaPayment;
    String errorPath = "errors";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_papara_web_view);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        paparaPayment = getIntent().getExtras().getParcelable("data");
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);

        showHTMLPage(paparaPayment.getPaymentUrl());

    }

    private void showHTMLPage(String url) {
        webView.setHapticFeedbackEnabled(true);
        webView.getSettings()
                .setSupportZoom(true);
        webView.getSettings()
                .setJavaScriptEnabled(true);
        webView.getSettings()
                .setSaveFormData(false);//Disable AutoSuggestion for webview bug
        webView.addJavascriptInterface(new JavaScriptInterface(this), "HTMLOUT");


        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("OVERRIDE", "onOverride: " + url);

                if (checkParams(url, paparaPayment.getReturningRedirectUrl())) {
                    finishSuccess();
                }else if (checkParamsForFail(url, errorPath)) {
                    finishFail();
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                show();

                Log.d("START", "onPageStarted: " + url);

            }

            @Override
            public void onPageFinished(WebView view, String url) {

                hide();
                Log.d("START", "onPagefinished: " + url);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                hide();

                super.onReceivedHttpError(view, request, errorResponse);
            }

        });


        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d("START", "progressss: " + newProgress);
            }
        });
    }


    public interface JavascriptCallback {

    }

    public class JavaScriptInterface implements JavascriptCallback {

        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        JavaScriptInterface(Context c) {
            mContext = c;
        }

        /**
         * Show a toast from the web page
         */
        @JavascriptInterface
        public void processSuccessHTML(String response) {
            Log.d("TAG_HTML", "processHTML: " + response);
        }

        @JavascriptInterface
        public void processFailHTML(String response) {
            Log.d("TAG_HTML", "processHTML: " + response);
        }
    }


    private void show() {
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hide() {
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.GONE);

    }

    private boolean checkParams(String url, String url2) {
        try {
            URL fullUrl = new URL(url2);
            String baseUrl = fullUrl.getProtocol() + "://" + fullUrl.getHost() + fullUrl.getPath();
            Log.d("BASEURL", "checkParams: " + baseUrl);

            if (!url.contains(baseUrl)) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        try {
            UrlQuerySanitizer sanitizer = new UrlQuerySanitizer(url);
            UrlQuerySanitizer sanitizer2 = new UrlQuerySanitizer(url2);
            String paymentId = sanitizer.getValue("paymentId");
            String paymentId2 = sanitizer2.getValue("paymentId");
            String referenceId = sanitizer.getValue("referenceId");
            String referenceId2 = sanitizer2.getValue("referenceId");

            if (paymentId.equalsIgnoreCase(paymentId2) && referenceId.equalsIgnoreCase(referenceId2)) {
                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    private boolean checkParamsForFail(String url, String url2) {
        try {
            URL fullUrl = new URL(url2.toLowerCase());
            String baseUrl = fullUrl.getProtocol() + "://" + fullUrl.getHost() + fullUrl.getPath();
            Log.d("BASEURL", "checkParams: " + baseUrl);

            if (!url.contains(baseUrl)) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("message", "Payment canceled by user");
        PaparaWebViewActivity.this.setResult(PAYMENT_CANCEL, intent);
        finish();
    }

    private void finishSuccess() {
        Intent intent = new Intent();
        intent.putExtra("message", "Payment successful");
        PaparaWebViewActivity.this.setResult(PAYMENT_SUCCESS, intent);
        finish();
    }
    private void finishFail() {
        Intent intent = new Intent();
        intent.putExtra("message", "Payment failed");
        PaparaWebViewActivity.this.setResult(PAYMENT_FAIL, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
