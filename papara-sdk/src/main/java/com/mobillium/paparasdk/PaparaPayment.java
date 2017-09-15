package com.mobillium.paparasdk;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by oguzhandongul on 28/11/2016.
 */

public class PaparaPayment implements Parcelable {

    private String payment_id;
    private String paymentUrl;
    private String paymentAmount;

    public PaparaPayment() {
    }

    public PaparaPayment(String payment_id, String paymentUrl, String paymentAmount) {
        this.payment_id = payment_id;
        this.paymentUrl = paymentUrl;
        this.paymentAmount = paymentAmount;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    protected PaparaPayment(Parcel in) {
        payment_id = in.readString();
        paymentUrl = in.readString();
        paymentAmount = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(payment_id);
        dest.writeString(paymentUrl);
        dest.writeString(paymentAmount);
    }

    @SuppressWarnings("unused")
    public static final Creator<PaparaPayment> CREATOR = new Creator<PaparaPayment>() {
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
