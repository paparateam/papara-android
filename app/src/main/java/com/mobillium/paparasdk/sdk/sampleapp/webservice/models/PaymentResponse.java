package com.mobillium.paparasdk.sdk.sampleapp.webservice.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oguzhandongul on 15/09/2017.
 */

public class PaymentResponse {
    @SerializedName("merchant")
    @Expose
    private Merchant merchant;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("paymentMethod")
    @Expose
    private Integer paymentMethod;
    @SerializedName("paymentMethodDescription")
    @Expose
    private String paymentMethodDescription;
    @SerializedName("referenceId")
    @Expose
    private String referenceId;
    @SerializedName("orderDescription")
    @Expose
    private String orderDescription;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("statusDescription")
    @Expose
    private String statusDescription;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("currency")
    @Expose
    private Integer currency;
    @SerializedName("notificationUrl")
    @Expose
    private String notificationUrl;
    @SerializedName("notificationDone")
    @Expose
    private Boolean notificationDone;
    @SerializedName("redirectUrl")
    @Expose
    private String redirectUrl;
    @SerializedName("paymentUrl")
    @Expose
    private String paymentUrl;
    @SerializedName("merchantSecretKey")
    @Expose
    private String merchantSecretKey;
    @SerializedName("returningRedirectUrl")
    @Expose
    private String returningRedirectUrl;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethodDescription() {
        return paymentMethodDescription;
    }

    public void setPaymentMethodDescription(String paymentMethodDescription) {
        this.paymentMethodDescription = paymentMethodDescription;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public String getNotificationUrl() {
        return notificationUrl;
    }

    public void setNotificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
    }

    public Boolean getNotificationDone() {
        return notificationDone;
    }

    public void setNotificationDone(Boolean notificationDone) {
        this.notificationDone = notificationDone;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public Object getMerchantSecretKey() {
        return merchantSecretKey;
    }

    public void setMerchantSecretKey(String merchantSecretKey) {
        this.merchantSecretKey = merchantSecretKey;
    }

    public String getReturningRedirectUrl() {
        return returningRedirectUrl;
    }

    public void setReturningRedirectUrl(String returningRedirectUrl) {
        this.returningRedirectUrl = returningRedirectUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
