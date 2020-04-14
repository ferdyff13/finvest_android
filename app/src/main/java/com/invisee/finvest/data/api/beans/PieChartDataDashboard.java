package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 07/04/2017.
 */

public class PieChartDataDashboard {

    @SerializedName("individualFundType")
    @Expose
    private String individualFundType;
    @SerializedName("marketValue")
    @Expose
    private Double marketValue;
    @SerializedName("fundTypeColors")
    @Expose
    private String fundTypeColors;

    /**
     * @return The individualFundType
     */
    public String getIndividualFundType() {
        return individualFundType;
    }

    /**
     * @param individualFundType The individualFundType
     */
    public void setIndividualFundType(String individualFundType) {
        this.individualFundType = individualFundType;
    }

    /**
     * @return The marketValue
     */
    public Double getMarketValue() {
        return marketValue;
    }

    /**
     * @param marketValue The marketValue
     */
    public void setMarketValue(Double marketValue) {
        this.marketValue = marketValue;
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
