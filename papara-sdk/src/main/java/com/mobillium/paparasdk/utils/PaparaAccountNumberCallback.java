
package com.mobillium.paparasdk.utils;


public interface PaparaAccountNumberCallback extends PaparaCallback {

    public void onSuccess(String message, int code, String accountNumber);

}
