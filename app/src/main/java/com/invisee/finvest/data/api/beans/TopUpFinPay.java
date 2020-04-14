package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TopUpFinPay implements Serializable {

    @SerializedName("response")
    @Expose
    private ResponseFinPay response;
    @SerializedName("request")
    @Expose
    private RequestFinPay request;

    public ResponseFinPay getResponse() {
        return response;
    }

    public void setResponse(ResponseFinPay response) {
        this.response = response;
    }

    public RequestFinPay getRequest() {
        return request;
    }

    public void setRequest(RequestFinPay request) {
        this.request = request;
    }
}
