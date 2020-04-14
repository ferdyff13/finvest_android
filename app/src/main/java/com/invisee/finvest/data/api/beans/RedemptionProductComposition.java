package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fajarfatur on 3/16/16.
 */
public class RedemptionProductComposition implements Serializable{

    @SerializedName("composition")
    @Expose
    private double composition;
    @SerializedName("utProductName")
    @Expose
    private String utProductName;

    /**
     * @return The composition
     */
    public double getComposition() {
        return composition;
    }

    /**
     * @param composition The composition
     */
    public void setComposition(double composition) {
        this.composition = composition;
    }

    /**
     * @return The utProductName
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
