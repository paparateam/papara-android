package com.mobillium.paparasdk.sdk.sampleapp.webservice.models;

/**
 * Created by oguzhandongul on 15/09/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Merchant {

    @SerializedName("legalName")
    @Expose
    private String legalName;
    @SerializedName("brandName")
    @Expose
    private String brandName;
    @SerializedName("allowedPaymentTypes")
    @Expose
    private List<AllowedPaymentType> allowedPaymentTypes = null;
    @SerializedName("balances")
    @Expose
    private List<Balance> balances = null;
    @SerializedName("allowedIps")
    @Expose
    private List<String> allowedIps = null;

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<AllowedPaymentType> getAllowedPaymentTypes() {
        return allowedPaymentTypes;
    }

    public void setAllowedPaymentTypes(List<AllowedPaymentType> allowedPaymentTypes) {
        this.allowedPaymentTypes = allowedPaymentTypes;
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }

    public List<String> getAllowedIps() {
        return allowedIps;
    }

    public void setAllowedIps(List<String> allowedIps) {
        this.allowedIps = allowedIps;
    }

}
