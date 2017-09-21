package com.mobillium.paparasdk;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by oguzhandongul on 28/11/2016.
 */

public class PaparaPayment implements Parcelable {

    private String payment_id;
    private String paymentUrl;
    private String returningRedirectUrl;

    public PaparaPayment() {
    }


    public String getPaymentId() {
        return payment_id;
    }

    public void setPaymentId(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getReturningRedirectUrl() {
        return returningRedirectUrl;
    }

    public void setReturningRedirectUrl(String returningRedirectUrl) {
        this.returningRedirectUrl = returningRedirectUrl;
    }

    protected PaparaPayment(Parcel in) {
        payment_id = in.readString();
        paymentUrl = in.readString();
        returningRedirectUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(payment_id);
        dest.writeString(paymentUrl);
        dest.writeString(returningRedirectUrl);
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
