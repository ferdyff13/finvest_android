package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Created by pandu.abbiyuarsyah on 24/03/2017.
 */

public class PayWalletRequest {

    @SerializedName("amount")
    @Expose
    private BigDecimal amount;

    @SerializedName("index")
    @Expose
    private String index;

    @SerializedName("orderNo")
    @Expose
    private String orderNo;

    @SerializedName("pin")
    @Expose
    private String pin;

    @SerializedName("token")
    @Expose
    private String token;

    /**
     * @return The amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount The amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return The index
     */
    public String getIndex() {
        return index;
    }

    /**
     * @param index The index
     */
    public void setIndex(String index) {
        this.index = index;
    }

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
    public String getPin() {
        return pin;
    }

    /**
     * @param pin The pin
     */
    public void setPin(String pin) {
        this.pin = pin;
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
