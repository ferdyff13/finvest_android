package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fajarfatur on 3/1/16.
 */
public class PortfolioInvestmentSummaryResponse extends GenericResponse implements Serializable{

    @SerializedName("totalMarketValue")
    @Expose
    private double totalMarketValue;
    @SerializedName("totalInvestment")
    @Expose
    private double totalInvestment;
    @SerializedName("riskProfile")
    @Expose
    private String riskProfile;

    /**
     * @return The totalMarketValue
     */
    public double getTotalMarketValue() {
        return totalMarketValue;
    }

    /**
     * @param totalMarketValue The totalMarketValue
     */
    public void setTotalMarketValue(double totalMarketValue) {
        this.totalMarketValue = totalMarketValue;
    }

    /**
     * @return The totalInvestment
     */
    public double getTotalInvestment() {
        return totalInvestment;
    }

    /**
     * @param totalInvestment The totalInvestment
     */
    public void setTotalInvestment(double totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public String getRiskProfile() {
        return riskProfile;
    }

    public void setRiskProfile(String riskProfile) {
        this.riskProfile = riskProfile;
    }
}
