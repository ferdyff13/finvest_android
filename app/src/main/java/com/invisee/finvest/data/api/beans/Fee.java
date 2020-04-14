package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by glenrynaldi on 2/17/16.
 */
public class Fee implements Serializable {

    @SerializedName("feeAmount")
    @Expose
    private Double feeAmount;
    @SerializedName("amountMin")
    @Expose
    private Integer amountMin;
    @SerializedName("amountMax")
    @Expose
    private Integer amountMax;

    /**
     * @return The feeAmount
     */
    public Double getFeeAmount() {
        return feeAmount;
    }

    /**
     * @param feeAmount The feeAmount
     */
    public void setFeeAmount(Double feeAmount) {
        this.feeAmount = feeAmount;
    }

    /**
     * @return The amountMin
     */
    public Integer getAmountMin() {
        return amountMin;
    }

    /**
     * @param amountMin The amountMin
     */
    public void setAmountMin(Integer amountMin) {
        this.amountMin = amountMin;
    }

    /**
     * @return The amountMax
     */
    public Integer getAmountMax() {
        return amountMax;
    }

    /**
     * @param amountMax The amountMax
     */
    public void setAmountMax(Integer amountMax) {
        this.amountMax = amountMax;
    }

}
