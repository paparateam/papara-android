package com.mobillium.paparasdk.sdk.sampleapp.webservice.models;

/**
 * Created by oguzhandongul on 15/09/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Balance {

    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("totalBalance")
    @Expose
    private String totalBalance;
    @SerializedName("lockedBalance")
    @Expose
    private String lockedBalance;
    @SerializedName("availableBalance")
    @Expose
    private String availableBalance;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getLockedBalance() {
        return lockedBalance;
    }

    public void setLockedBalance(String lockedBalance) {
        this.lockedBalance = lockedBalance;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
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