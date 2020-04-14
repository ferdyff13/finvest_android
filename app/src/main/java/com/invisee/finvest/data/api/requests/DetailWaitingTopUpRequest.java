package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 25/10/2017.
 */

public class DetailWaitingTopUpRequest {

    @SerializedName("trxNumber")
    @Expose
    private String trxNumber;

    public String getTrxNumber() {
        return  trxNumber;
    }

    public void setTrxNumber(String trxNumber) {
        this.trxNumber = trxNumber;
    }


}
