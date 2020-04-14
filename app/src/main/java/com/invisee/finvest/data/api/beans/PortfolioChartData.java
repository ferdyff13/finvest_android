package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarfatur on 3/6/16.
 */
public class PortfolioChartData {

    @SerializedName("composition")
    @Expose
    private String composition;
    @SerializedName("fundTypeName")
    @Expose
    private String fundTypeName;
    @SerializedName("fundTypeColors")
    @Expose
    private String fundTypeColors;

    /**
     * @return The composition
     */
    public String getComposition() {
        return composition;
    }

    /**
     * @param composition The composition
     */
    public void setComposition(String composition) {
        this.composition = composition;
    }

    /**
     * @return The fundTypeName
     */
    public String getFundTypeName() {
        return fundTypeName;
    }

    /**
     * @param fundTypeName The fundTypeName
     */
    public void setFundTypeName(String fundTypeName) {
        this.fundTypeName = fundTypeName;
    }

    /**
     * @return The fundTypeColors
     */
    public String getFundTypeColors() {
        return fundTypeColors;
    }

    /**
     * @param fundTypeColors The fundTypeColors
     */
    public void setFundTypeColors(String fundTypeColors) {
        this.fundTypeColors = fundTypeColors;
    }

}
