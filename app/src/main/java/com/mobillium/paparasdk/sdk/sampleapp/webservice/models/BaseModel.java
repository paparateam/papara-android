package com.mobillium.paparasdk.sdk.sampleapp.webservice.models;

/**
 * Created by oguzhandongul on 04/06/2017.
 */

public class BaseModel<T> {
    private T data;

    public BaseModel() {
    }

    public BaseModel(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
