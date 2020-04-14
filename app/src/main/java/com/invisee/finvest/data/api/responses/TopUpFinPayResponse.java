package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.TopUpFinPay;

public class TopUpFinPayResponse extends GenericResponse {

    @SerializedName("data")
    @Expose
    private TopUpFinPay data;

    public TopUpFinPay getData() {
        return data;
    }

    public void setData(TopUpFinPay data) {
        this.data = data;
    }
}
