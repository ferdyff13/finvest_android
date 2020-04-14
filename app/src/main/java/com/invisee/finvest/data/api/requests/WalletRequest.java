package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarfatur on 2/3/16.
 */
public class WalletRequest {

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("transactionDate")
    @Expose
    private String transactionDate;

    public WalletRequest(String token, String transactionDate) {
        this.token = token;
        this.transactionDate = transactionDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
