package com.invisee.finvest.data.api.requests;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.Kyc;

import java.io.Serializable;

/**
 * Created by fajarfatur on 2/10/16.
 */
public class KycDataRequest extends Kyc implements Serializable{

    @SerializedName("token")
    @Expose
    private String token;

    /**
     * @return The token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
