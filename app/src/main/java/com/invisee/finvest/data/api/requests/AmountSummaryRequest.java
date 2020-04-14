package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 24/03/2017.
 */

public class AmountSummaryRequest {

    @SerializedName("orderNumber")
    @Expose
    private String orderNo;

    @SerializedName("token")
    @Expose
    private String token;

    /**
     * @return The orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo The orderNo
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return The pin
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
