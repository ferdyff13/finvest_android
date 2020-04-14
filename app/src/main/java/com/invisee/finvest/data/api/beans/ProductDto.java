package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pandu.abbiyuarsyah on 24/03/2017.
 */

public class ProductDto implements Serializable {

    @SerializedName("composition")
    @Expose
    private Integer composition;

    @SerializedName("netAmount")
    @Expose
    private Double netAmount;

    @SerializedName("orderAmount")
    @Expose
    private Double orderAmount;


    @SerializedName("utProductName")
    @Expose
    private String utProductName;

    /**
     * @return The composition
     */
    public Integer getComposition() {
        return composition;
    }

    /**
     * @param composition The composition
     */
    public void setComposition(Integer composition) {
        this.composition = composition;
    }

    /**
     * @return The netAmount
     */
    public Double getNetAmount() {
        return netAmount;
    }

    /**
     * @param netAmount The netAmount
     */
    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    /**
     * @return The orderAmount
     */
    public Double getOrderAmount() {
        return orderAmount;
    }

    /**
     * @param orderAmount The orderAmount
     */
    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }


    /**
     * @return The composition
     */
    public String getUtProductName() {
        return utProductName;
    }

    /**
     * @param utProductName The utProductName
     */
    public void setUtProductName(String utProductName) {
        this.utProductName = utProductName;
    }

}



