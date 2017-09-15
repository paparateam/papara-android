package com.mobillium.paparasdk.sdk.sampleapp.webservice.models;


import com.mobillium.paparasdk.sdk.sampleapp.webservice.ServiceError;

/**
 * Created by oguzhandongul on 31/05/2017.
 */

public class BaseResponse<T> {
    private boolean succeeded;
    private ServiceError error;
    private T data;

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public ServiceError getError() {
        return error;
    }

    public void setError(ServiceError error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }
}
