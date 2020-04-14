package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 30/03/2017.
 */

public class DetailPendingOrderRequest {

    @SerializedName("investment_number")
    @Expose
    private String investmentNumber;

    @SerializedName("orderNumber")
    @Expose
    private String orderNumber;

    @SerializedName("token")
    @Expose
    private String token;

    /**
     * @return The investmentNumber
     */
    public String getInvestmentNumber() {
        return investmentNumber;
    }

    /**
     * @param investmentNumber The investment_number
     */
    public void setInvestmentNumber(String investmentNumber) {
        this.investmentNumber = investmentNumber;
    }

    /**
     * @return The orderNumber
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * @param orderNumber The orderNumber
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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
