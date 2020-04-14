package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.PayPro;
import com.invisee.finvest.data.api.beans.RedemptionProductComposition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandu.abbiyuarsyah on 30/05/2017.
 */

public class PaymentPayProRequest {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("data")
    @Expose
    private List<PayPro> data = new ArrayList<>();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<PayPro> getData() {
        return data;
    }

    public void setData(List<PayPro> data) {
        this.data = data;
    }

}
