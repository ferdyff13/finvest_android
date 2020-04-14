package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ulfah.ulmi on 12/04/2017.
 */

public class DataRedeemFee {
    @SerializedName("redeemFee")
    @Expose
    private double redeemFee;

    public double getRedeemFee() {
        return redeemFee;
    }

    public void setRedeemFee(double redeemFee) {
        this.redeemFee = redeemFee;
    }
}
