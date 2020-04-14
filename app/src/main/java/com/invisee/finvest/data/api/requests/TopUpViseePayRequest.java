package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 18/10/2017.
 */

public class TopUpViseePayRequest {

    @SerializedName("trxAmount")
    @Expose
    private String trxAmount;
    @SerializedName("bank")
    @Expose
    private String bank;

    public String getTrxAmount() {
        return trxAmount;
    }

    public void setTrxAmount(String trxAmount) {
        this.trxAmount = trxAmount;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

}
