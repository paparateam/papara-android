package com.mobillium.paparasdk;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by oguzhandongul on 28/11/2016.
 */

public class PaparaPaymentContainer implements Parcelable {

    private PaparaPayment paparaPayment;
    private String sdkVersion;
    private String packageName;
    private String osVersion;
    private String appVersion;
    private String appBuild;
    private String brand;
    private String model;
    private String appId;
    private String displayName;

    public PaparaPaymentContainer() {
    }

    public PaparaPayment getPaparaPayment() {
        return paparaPayment;
    }

    public void setPaparaPayment(PaparaPayment paparaPayment) {
        this.paparaPayment = paparaPayment;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppBuild() {
        return appBuild;
    }

    public void setAppBuild(String appBuild) {
        this.appBuild = appBuild;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    protected PaparaPaymentContainer(Parcel in) {
        paparaPayment = (PaparaPayment) in.readValue(PaparaPayment.class.getClassLoader());
        sdkVersion = in.readString();
        packageName = in.readString();
        osVersion = in.readString();
        appVersion = in.readString();
        appBuild = in.readString();
        brand = in.readString();
        model = in.readString();
        appId = in.readString();
        displayName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(paparaPayment);
        dest.writeString(sdkVersion);
        dest.writeString(packageName);
        dest.writeString(osVersion);
        dest.writeString(appVersion);
        dest.writeString(appBuild);
        dest.writeString(brand);
        dest.writeString(model);
        dest.writeString(appId);
        dest.writeString(displayName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PaparaPaymentContainer> CREATOR = new Parcelable.Creator<PaparaPaymentContainer>() {
        @Override
        public PaparaPaymentContainer createFromParcel(Parcel in) {
            return new PaparaPaymentContainer(in);
        }

        @Override
        public PaparaPaymentContainer[] newArray(int size) {
            return new PaparaPaymentContainer[size];
        }
    };
}
