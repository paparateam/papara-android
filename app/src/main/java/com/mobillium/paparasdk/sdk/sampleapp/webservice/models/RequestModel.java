package com.mobillium.paparasdk.sdk.sampleapp.webservice.models;

/**
 * Created by oguzhandongul on 31/05/2017.
 */

public class RequestModel<T> {
    private int serviceType;
    private String offsetUrl;
    private String pdMessage;
    private boolean showErrorMessage;
    private T data;

    public RequestModel(int serviceType, String offsetUrl, String pdMessage, T data) {
        this.serviceType = serviceType;
        this.offsetUrl = offsetUrl;
        this.pdMessage = pdMessage;
        this.data = data;
    }

    public RequestModel(int serviceType, String offsetUrl, String pdMessage, boolean showErrorMessage, T data) {
        this.serviceType = serviceType;
        this.offsetUrl = offsetUrl;
        this.pdMessage = pdMessage;
        this.showErrorMessage = showErrorMessage;
        data = data;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public String getOffsetUrl() {
        return offsetUrl;
    }

    public void setOffsetUrl(String offsetUrl) {
        this.offsetUrl = offsetUrl;
    }


    public String getPdMessage() {
        return pdMessage;
    }

    public void setPdMessage(String pdMessage) {
        this.pdMessage = pdMessage;
    }

    public boolean isShowErrorMessage() {
        return showErrorMessage;
    }

    public void setShowErrorMessage(boolean showErrorMessage) {
        this.showErrorMessage = showErrorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        data = data;
    }
}
