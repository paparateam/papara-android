
package com.mobillium.paparasdk.utils;


public interface PaparaCallback {

    public void onFailure(String message, int code);

    public void onCancel(String message, int code);
}
