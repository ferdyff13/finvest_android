package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by glenrynaldi on 2/17/16.
 */
public class Packages implements Serializable {

    @SerializedName("packageDesc")
    private String packageDesc;
    @SerializedName("fundPackageName")
    private String fundPackageName;
    @SerializedName("packageImage")
    private String packageImage;
    @SerializedName("performance")
    private Double performance;
    @SerializedName("id")
    private Long id;
    @SerializedName("settlementCutOff")
    private String settlementCutOff;
    @SerializedName("minTopupAmount")
    private Double minTopupAmount;
    @SerializedName("riskProfile")
    private String riskProfile;
    @SerializedName("imageBank")
    private String imageBank;
    @SerializedName("settlementPeriod")
    private String settlementPeriod;
    @SerializedName("bankName")
    private String bankName;
    @SerializedName("transactionCutOff")
    private String transactionCutOff;
    @SerializedName("packageCode")
    private String packageCode;
    @SerializedName("minSubscriptionAmount")
    private Double minSubscriptionAmount;
    @SerializedName("currency")
    private String currency;
    @SerializedName("effectiveDate")
    private String effectiveDate;
    @SerializedName("packageName")
    private String packageName;
    @SerializedName("totalInvestmentMarketValue")
    private String totalInvestmentMarketValue;
    @SerializedName("investmentAmount")
    private String investmentAmount;
    @SerializedName("activeStatus")
    private Object activeStatus;
    @SerializedName("createdBy")
    private String createdBy;
    @SerializedName("createdDate")
    private String createdDate;
    @SerializedName("goal")
    private Object goal;
    @SerializedName("marketValue")
    private Object marketValue;
    @SerializedName("publishStatus")
    private Boolean publishStatus;
    @SerializedName("redemptionRule")
    private Object redemptionRule;
    @SerializedName("subscriptionRule")
    private Object subscriptionRule;
    @SerializedName("unrealizedGainLoss")
    private Object unrealizedGainLoss;
    @SerializedName("updatedBy")
    private String updatedBy;
    @SerializedName("updatedDate")
    private String updatedDate;


    /**
     * @return The packageDesc
     */
    public String getPackageDesc() {
        return packageDesc;
    }

    /**
     * @param packageDesc The packageDesc
     */
    public void setPackageDesc(String packageDesc) {
        this.packageDesc = packageDesc;
    }

    /**
     * @return The fundPackageName
     */
    public String getFundPackageName() {
        return fundPackageName;
    }

    /**
     * @param fundPackageName The fundPackageName
     */
    public void setFundPackageName(String fundPackageName) {
        this.fundPackageName = fundPackageName;
    }

    /**
     * @return The packageImage
     */
    public String getPackageImage() {
        return packageImage;
    }

    /**
     * @param packageImage The packageImage
     */
    public void setPackageImage(String packageImage) {
        this.packageImage = packageImage;
    }

    /**
     * @return The performance
     */
    public Double getPerformance() {
        return performance;
    }

    /**
     * @param performance The performance
     */
    public void setPerformance(Double performance) {
        this.performance = performance;
    }

    /**
     * @return The id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * @return The settlementCutOff
     */
    public String getSettlementCutOff() {
        return settlementCutOff;
    }

    /**
     * @param settlementCutOff The settlementCutOff
     */
    public void setSettlementCutOff(String settlementCutOff) {
        this.settlementCutOff = settlementCutOff;
    }

    /**
     * @return The minTopupAmount
     */
    public Double getMinTopupAmount() {
        return minTopupAmount;
    }

    /**
     * @param minTopupAmount The minTopupAmount
     */
    public void setMinTopupAmount(Double minTopupAmount) {
        this.minTopupAmount = minTopupAmount;
    }

    /**
     * @return The riskProfile
     */
    public String getRiskProfile() {
        return riskProfile;
    }

    /**
     * @param riskProfile The riskProfile
     */
    public void setRiskProfile(String riskProfile) {
        this.riskProfile = riskProfile;
    }

    /**
     * @return The imageBank
     */
    public String getImageBank() {
        return imageBank;
    }

    /**
     * @param imageBank The imageBank
     */
    public void setImageBank(String imageBank) {
        this.imageBank = imageBank;
    }

    /**
     * @return The settlementPeriod
     */
    public String getSettlementPeriod() {
        return settlementPeriod;
    }

    /**
     * @param settlementPeriod The settlementPeriod
     */
    public void setSettlementPeriod(String settlementPeriod) {
        this.settlementPeriod = settlementPeriod;
    }

    /**
     * @return The bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankName The bankName
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * @return The transactionCutOff
     */
    public String getTransactionCutOff() {
        return transactionCutOff;
    }

    /**
     * @param transactionCutOff The transactionCutOff
     */
    public void setTransactionCutOff(String transactionCutOff) {
        this.transactionCutOff = transactionCutOff;
    }

    /**
     * @return The packageCode
     */
    public String getPackageCode() {
        return packageCode;
    }

    /**
     * @param packageCode The packageCode
     */
    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    /**
     * @return The minSubscriptionAmount
     */
    public Double getMinSubscriptionAmount() {
        return minSubscriptionAmount;
    }

    /**
     * @param minSubscriptionAmount The minSubscriptionAmount
     */
    public void setMinSubscriptionAmount(Double minSubscriptionAmount) {
        this.minSubscriptionAmount = minSubscriptionAmount;
    }

    /**
     * @return The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return The effectiveDate
     */
    public String getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * @param effectiveDate The effectiveDate
     */
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
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

    /**
     * @return The totalInvestmentMarketValue
     */
    public String getTotalInvestmentMarketValue() {
        return totalInvestmentMarketValue;
    }

    /**
     * @param totalInvestmentMarketValue The totalInvestmentMarketValue
     */
    public void setTotalInvestmentMarketValue(String totalInvestmentMarketValue) {
        this.totalInvestmentMarketValue = totalInvestmentMarketValue;
    }

    /**
     * @return The investmentAmount
     */
    public String getInvestmentAmount() {
        return investmentAmount;
    }

    /**
     * @param investmentAmount The investmentAmount
     */
    public void setInvestmentAmount(String investmentAmount) {
        this.investmentAmount = investmentAmount;
    }

    ////

    /**
     * @return The activeStatus
     */
    public Object getActiveStatus() {
        return activeStatus;
    }

    /**
     * @param activeStatus The activeStatus
     */
    public void setActiveStatus(Object activeStatus) {
        this.activeStatus = activeStatus;
    }

    /**
     * @return The createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy The createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return The createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate The createdDate
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }


    /**
     * @return The goal
     */
    public Object getGoal() {
        return goal;
    }

    /**
     * @param goal The goal
     */
    public void setGoal(Object goal) {
        this.goal = goal;
    }

    /**
     * @return The marketValue
     */
    public Object getMarketValue() {
        return marketValue;
    }

    /**
     * @param marketValue The marketValue
     */
    public void setMarketValue(Object marketValue) {
        this.marketValue = marketValue;
    }


    /**
     * @return The publishStatus
     */
    public Boolean getPublishStatus() {
        return publishStatus;
    }

    /**
     * @param publishStatus The publishStatus
     */
    public void setPublishStatus(Boolean publishStatus) {
        this.publishStatus = publishStatus;
    }

    /**
     * @return The redemptionRule
     */
    public Object getRedemptionRule() {
        return redemptionRule;
    }

    /**
     * @param redemptionRule The redemptionRule
     */
    public void setRedemptionRule(Object redemptionRule) {
        this.redemptionRule = redemptionRule;
    }


    /**
     * @return The subscriptionRule
     */
    public Object getSubscriptionRule() {
        return subscriptionRule;
    }

    /**
     * @param subscriptionRule The subscriptionRule
     */
    public void setSubscriptionRule(Object subscriptionRule) {
        this.subscriptionRule = subscriptionRule;
    }

    /**
     * @return The unrealizedGainLoss
     */
    public Object getUnrealizedGainLoss() {
        return unrealizedGainLoss;
    }

    /**
     * @param unrealizedGainLoss The unrealizedGainLoss
     */
    public void setUnrealizedGainLoss(Object unrealizedGainLoss) {
        this.unrealizedGainLoss = unrealizedGainLoss;
    }

    /**
     * @return The updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy The updatedBy
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return The updatedDate
     */
    public String getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate The updatedDate
     */
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return fundPackageName;
    }

}
