package com.mobillium.paparasdk.utils;

import android.net.Uri;
import android.text.TextUtils;

import com.mobillium.paparasdk.Papara;
import com.mobillium.paparasdk.PaparaModelContainer;
import com.mobillium.paparasdk.PaparaPayment;
import com.mobillium.paparasdk.PaparaSendMoney;

/**
 * Created by oguzhandongul on 28/11/2016.
 * <p>
 * Helps to create URI from PaparaModelContainer object
 */

public class UriHelper {

    public static final String TYPE_SEND_PHONE = "://send/phone?";
    public static final String TYPE_SEND_MAIL = "://send/mail?";
    public static final String TYPE_SEND_ACCOUNT = "://send/account?";
    public static final String TYPE_FETC_ACCOUNT_NUM = "://fetch/account?";
    public static final String TYPE_PAYMENT = "://payment?";

    public static Uri objectToUri(PaparaModelContainer paparaModelContainer, String type) {
        PaparaSendMoney paparaSendMoney = paparaModelContainer.getPaparaSendMoney();
        PaparaPayment paparaPayment = paparaModelContainer.getPaparaPayment();
//        String uriString = Papara.getDeepLinkHost() + "/personal/sendMoney?";
        String uriString = Papara.getDeepLinkHost() + type;

        //Payment Model Related
        if (paparaSendMoney != null) {
            if (!TextUtils.isEmpty(paparaSendMoney.getReceiver())) {
                uriString += "receiver=" + paparaSendMoney.getReceiver();
            }
            if (!TextUtils.isEmpty(paparaSendMoney.getAmount())) {
                uriString += "&amount=" + paparaSendMoney.getAmount();
            }
        }
        //Payment Model Related
        if (paparaPayment != null) {
            if (!TextUtils.isEmpty(paparaPayment.getPayment_id())) {
                uriString += "payment_id=" + paparaPayment.getPayment_id();
            }
            if (!TextUtils.isEmpty(paparaPayment.getPaymentUrl())) {
                uriString += "&paymentUrl=" + paparaPayment.getPaymentUrl();
            }
        }

        //Device Related
        if (!TextUtils.isEmpty(paparaModelContainer.getAppBuild())) {
            uriString += "&appBuild=" + paparaModelContainer.getAppBuild();
        }
        if (!TextUtils.isEmpty(paparaModelContainer.getSdkVersion())) {
            uriString += "&sdkVersion=" + paparaModelContainer.getSdkVersion();
        }
        if (!TextUtils.isEmpty(paparaModelContainer.getPackageName())) {
            uriString += "&packageName=" + paparaModelContainer.getPackageName();
        }
        if (!TextUtils.isEmpty(paparaModelContainer.getOsVersion())) {
            uriString += "&osVersion=" + paparaModelContainer.getOsVersion();
        }
        if (!TextUtils.isEmpty(paparaModelContainer.getAppVersion())) {
            uriString += "&appVersion=" + paparaModelContainer.getAppVersion();
        }
        if (!TextUtils.isEmpty(paparaModelContainer.getBrand())) {
            uriString += "&brand=" + paparaModelContainer.getBrand();
        }
        if (!TextUtils.isEmpty(paparaModelContainer.getModel())) {
            uriString += "&model=" + paparaModelContainer.getModel();
        }
        if (!TextUtils.isEmpty(paparaModelContainer.getAppId())) {
            uriString += "&appId=" + paparaModelContainer.getAppId();
        }
        if (!TextUtils.isEmpty(paparaModelContainer.getDisplayName())) {
            uriString += "&displayName=" + paparaModelContainer.getDisplayName();
        }


        return Uri.parse(uriString);
    }
}
