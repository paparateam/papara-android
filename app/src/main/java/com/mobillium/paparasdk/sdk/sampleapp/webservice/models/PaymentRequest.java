package com.mobillium.paparasdk.sdk.sampleapp.webservice.models;

/**
 * Created by oguzhandongul on 13/07/2017.
 */

public class PaymentRequest {
    private String amount;
    private String referenceId;
    private String orderDescription;
    private String notificationUrl;
    private String redirectUrl;

    public PaymentRequest(String amount, String referenceId, String orderDescription, String notificationUrl, String redirectUrl) {
        this.amount = amount;
        this.referenceId = referenceId;
        this.orderDescription = orderDescription;
        this.notificationUrl = notificationUrl;
        this.redirectUrl = redirectUrl;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getNotificationUrl() {
        return notificationUrl;
    }

    public void setNotificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
