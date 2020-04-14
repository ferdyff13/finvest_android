package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fajarfatur on 3/1/16.
 */
public class PortfolioInvestment implements Serializable {

    @SerializedName("taggedGoalName")
    @Expose
    private String taggedGoalName;
    @SerializedName("totalInvestmentMarketValue")
    @Expose
    private double totalInvestmentMarketValue;
    @SerializedName("investmentComposition")
    @Expose
    private List<PortfolioProductComposition> investmentComposition = new ArrayList<>();
    @SerializedName("investmentAccountNumber")
    @Expose
    private String investmentAccountNumber;
    @SerializedName("investmentId")
    @Expose
    private String investmentId;
    @SerializedName("packageId")
    @Expose
    private Long packageId;
    @SerializedName("packageImageKey")
    @Expose
    private String packageImageKey;
    @SerializedName("investmentAmount")
    @Expose
    private double investmentAmount;
    @SerializedName("packageName")
    @Expose
    private String packageName;

    /**
     * @return The taggedGoalName
     */
    public String getTaggedGoalName() {
        return taggedGoalName;
    }

    /**
     * @param taggedGoalName The taggedGoalName
     */
    public void setTaggedGoalName(String taggedGoalName) {
        this.taggedGoalName = taggedGoalName;
    }

    /**
     * @return The totalInvestmentMarketValue
     */
    public double getTotalInvestmentMarketValue() {
        return totalInvestmentMarketValue;
    }

    /**
     * @param totalInvestmentMarketValue The totalInvestmentMarketValue
     */
    public void setTotalInvestmentMarketValue(double totalInvestmentMarketValue) {
        this.totalInvestmentMarketValue = totalInvestmentMarketValue;
    }

    /**
     * @return The investmentComposition
     */
    public List<PortfolioProductComposition> getInvestmentComposition() {
        return investmentComposition;
    }

    /**
     * @param investmentComposition The investmentComposition
     */
    public void setInvestmentComposition(List<PortfolioProductComposition> investmentComposition) {
        this.investmentComposition = investmentComposition;
    }

    /**
     * @return The investmentAccountNumber
     */
    public String getInvestmentAccountNumber() {
        return investmentAccountNumber;
    }

    /**
     * @param investmentAccountNumber The investmentAccountNumber
     */
    public void setInvestmentAccountNumber(String investmentAccountNumber) {
        this.investmentAccountNumber = investmentAccountNumber;
    }

    /**
     * @return The investmentId
     */
    public String getInvestmentId() {
        return investmentId;
    }

    /**
     * @param investmentId The investmentId
     */
    public void setInvestmentId(String investmentId) {
        this.investmentId = investmentId;
    }

    /**
     * @return The packageId
     */
    public Long getPackageId() {
        return packageId;
    }

    /**
     * @param packageId The packageId
     */
    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    /**
     * @return The packageImageKey
     */
    public String getPackageImageKey() {
        return packageImageKey;
    }

    /**
     * @param packageImageKey The packageImageKey
     */
    public void setPackageImageKey(String packageImageKey) {
        this.packageImageKey = packageImageKey;
    }

    /**
     * @return The investmentAmount
     */
    public double getInvestmentAmount() {
        return investmentAmount;
    }

    /**
     * @param investmentAmount The investmentAmount
     */
    public void setInvestmentAmount(double investmentAmount) {
        this.investmentAmount = investmentAmount;
    }

    /**
     * @return The packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName The packageName
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

}
