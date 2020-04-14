package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 31/03/2017.
 */

public class DocTransactionRequest {


    @SerializedName("orderno")
    @Expose
    private String orderno;

    @SerializedName("token")
    @Expose
    private String token;

    /**
     * @return The orderno
     */
    public String getOrderno() {
        return orderno;
    }

    /**
     * @param orderno The orderno
     */
    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

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
}
