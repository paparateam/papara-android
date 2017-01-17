package com.mobillium.paparasdk.utils;

import android.net.Uri;
import android.text.TextUtils;

import com.mobillium.paparasdk.Papara;
import com.mobillium.paparasdk.PaparaPayment;
import com.mobillium.paparasdk.PaparaPaymentContainer;

/**
 * Created by oguzhandongul on 28/11/2016.
 *
 * Helps to create URI from PaparaPaymentContainer object
 */

public class UriHelper {


    public static Uri objectToUri(PaparaPaymentContainer paparaPaymentContainer) {
        PaparaPayment paparaPayment = paparaPaymentContainer.getPaparaPayment();
//        String uriString = Papara.getDeepLinkHost() + "/personal/sendMoney?";
        String uriString = Papara.getDeepLinkHost() + "://send/confirm?";

        //Payment Model Related
        if (!TextUtils.isEmpty(paparaPayment.getWalletId())) {
            uriString += "walletId=" + paparaPayment.getWalletId();
        }
        if (!TextUtils.isEmpty(paparaPayment.getDesc())) {
            uriString += "&desc=" + paparaPayment.getDesc();
        }
        if (!TextUtils.isEmpty(paparaPayment.getAmount())) {
            uriString += "&amount=" + paparaPayment.getAmount();
        }

        //Device Related
        if (!TextUtils.isEmpty(paparaPaymentContainer.getAppBuild())) {
            uriString += "&appBuild=" + paparaPaymentContainer.getAppBuild();
        }
        if (!TextUtils.isEmpty(paparaPaymentContainer.getSdkVersion())) {
            uriString += "&sdkVersion=" + paparaPaymentContainer.getSdkVersion();
        }
        if (!TextUtils.isEmpty(paparaPaymentContainer.getPackageName())) {
            uriString += "&packageName=" + paparaPaymentContainer.getPackageName();
        }
        if (!TextUtils.isEmpty(paparaPaymentContainer.getOsVersion())) {
            uriString += "&osVersion=" + paparaPaymentContainer.getOsVersion();
        }
        if (!TextUtils.isEmpty(paparaPaymentContainer.getAppVersion())) {
            uriString += "&appVersion=" + paparaPaymentContainer.getAppVersion();
        }
        if (!TextUtils.isEmpty(paparaPaymentContainer.getBrand())) {
            uriString += "&brand=" + paparaPaymentContainer.getBrand();
        }
        if (!TextUtils.isEmpty(paparaPaymentContainer.getModel())) {
            uriString += "&model=" + paparaPaymentContainer.getModel();
        }
        if (!TextUtils.isEmpty(paparaPaymentContainer.getAppId())) {
            uriString += "&appId=" + paparaPaymentContainer.getAppId();
        }
        if (!TextUtils.isEmpty(paparaPaymentContainer.getDisplayName())) {
            uriString += "&displayName=" + paparaPaymentContainer.getDisplayName();
        }


        return Uri.parse(uriString);
    }
}
