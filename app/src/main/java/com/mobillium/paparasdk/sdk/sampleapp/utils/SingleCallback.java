package com.mobillium.paparasdk.sdk.sampleapp.utils;

import android.support.v7.app.AlertDialog;

import com.mobillium.paparasdk.sdk.sampleapp.webservice.models.BaseModel;


/**
 * Created by oguzhandongul on 01/03/2017.
 */

public interface SingleCallback<T> {

    public void onNegative(AlertDialog alertDialog, BaseModel<T> data);
    public void onPositive(AlertDialog alertDialog, BaseModel<T> data);
}
