package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fajarfatur on 3/1/16.
 */
public class PortfolioProductComposition implements Serializable{

    @SerializedName("individualFundType")
    @Expose
    private String individualFundType;
    @SerializedName("investmentManager")
    @Expose
    private String investmentManager;
    @SerializedName("lastNav")
    @Expose
    private double lastNav;
    @SerializedName("marketValue")
    @Expose
    private double marketValue;
    @SerializedName("lastNavDate")
    @Expose
    private String lastNavDate;
    @SerializedName("currentUnit")
    @Expose
    private double currentUnit;
    @SerializedName("individualFundName")
    @Expose
    private String individualFundName;

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
     * @return The investmentManager
     */
    public String getInvestmentManager() {
        return investmentManager;
    }

    /**
     * @param investmentManager The investmentManager
     */
    public void setInvestmentManager(String investmentManager) {
        this.investmentManager = investmentManager;
    }

    /**
     * @return The lastNav
     */
    public double getLastNav() {
        return lastNav;
    }

    /**
     * @param lastNav The lastNav
     */
    public void setLastNav(double lastNav) {
        this.lastNav = lastNav;
    }

    /**
     * @return The marketValue
     */
    public double getMarketValue() {
        return marketValue;
    }

    /**
     * @param marketValue The marketValue
     */
    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }

    /**
     * @return The lastNavDate
     */
    public String getLastNavDate() {
        return lastNavDate;
    }

    /**
     * @param lastNavDate The lastNavDate
     */
    public void setLastNavDate(String lastNavDate) {
        this.lastNavDate = lastNavDate;
    }

    /**
     * @return The currentUnit
     */
    public double getCurrentUnit() {
        return currentUnit;
    }

    /**
     * @param currentUnit The currentUnit
     */
    public void setCurrentUnit(double currentUnit) {
        this.currentUnit = currentUnit;
    }

    /**
     * @return The individualFundName
     */
    public String getIndividualFundName() {
        return individualFundName;
    }

    /**
     * @param individualFundName The individualFundName
     */
    public void setIndividualFundName(String individualFundName) {
        this.individualFundName = individualFundName;
    }

}
