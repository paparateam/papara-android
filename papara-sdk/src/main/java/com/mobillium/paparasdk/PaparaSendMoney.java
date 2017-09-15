package com.mobillium.paparasdk;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by oguzhandongul on 28/11/2016.
 */

public class PaparaSendMoney implements Parcelable {

    private String amount;
    private String receiver;
    private String type;

    public PaparaSendMoney() {
    }

//    public PaparaSendMoney(String appId, String amount, String desc, String receiver) {
//        this.amount = amount;
//        this.desc = desc;
//        this.receiver = receiver;
//    }
//
//


    public PaparaSendMoney(String amount, String receiver, String type) {
        this.amount = amount;
        this.receiver = receiver;
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    protected PaparaSendMoney(Parcel in) {
        amount = in.readString();
        receiver = in.readString();
        type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(amount);
        dest.writeString(receiver);
        dest.writeString(type);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PaparaSendMoney> CREATOR = new Parcelable.Creator<PaparaSendMoney>() {
        @Override
        public PaparaSendMoney createFromParcel(Parcel in) {
            return new PaparaSendMoney(in);
        }

        @Override
        public PaparaSendMoney[] newArray(int size) {
            return new PaparaSendMoney[size];
        }
    };
}
