package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.DataRedeemFee;

import java.io.Serializable;

/**
 * Created by ulfah.ulmi on 12/04/2017.
 */

public class RedeemFeeResponse extends GenericResponse implements Serializable{
    @SerializedName("data")
    @Expose
    private DataRedeemFee data;

    public DataRedeemFee getData() {
        return data;
    }

    public void setData(DataRedeemFee data) {
        this.data = data;
    }
}
