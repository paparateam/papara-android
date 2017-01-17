package com.mobillium.paparasdk;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by oguzhandongul on 28/11/2016.
 */

public class PaparaPayment implements Parcelable {

    private String amount;
    private String desc;
    private String walletId;

    public PaparaPayment() {
    }

    public PaparaPayment(String appId, String amount, String desc, String walletId) {
        this.amount = amount;
        this.desc = desc;
        this.walletId = walletId;
    }



    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    protected PaparaPayment(Parcel in) {
        amount = in.readString();
        desc = in.readString();
        walletId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(amount);
        dest.writeString(desc);
        dest.writeString(walletId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PaparaPayment> CREATOR = new Parcelable.Creator<PaparaPayment>() {
        @Override
        public PaparaPayment createFromParcel(Parcel in) {
            return new PaparaPayment(in);
        }

        @Override
        public PaparaPayment[] newArray(int size) {
            return new PaparaPayment[size];
        }
    };
}
