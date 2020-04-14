package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by glenrynaldi on 3/14/16.
 */
public class FundPackages implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("activeStatus")
    @Expose
    private Object activeStatus;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("effectiveDate")
    @Expose
    private String effectiveDate;
    @SerializedName("fundPackageName")
    @Expose
    private String fundPackageName;
    @SerializedName("goal")
    @Expose
    private Object goal;
    @SerializedName("marketValue")
    @Expose
    private Object marketValue;
    @SerializedName("minSubscriptionAmount")
    @Expose
    private Double minSubscriptionAmount;
    @SerializedName("minTopupAmount")
    @Expose
    private Double minTopupAmount;
    @SerializedName("packageCode")
    @Expose
    private String packageCode;
    @SerializedName("packageDesc")
    @Expose
    private String packageDesc;
    @SerializedName("packageImage")
    @Expose
    private String packageImage;
    @SerializedName("publishStatus")
    @Expose
    private Boolean publishStatus;
    @SerializedName("redemptionRule")
    @Expose
    private Object redemptionRule;
    @SerializedName("riskProfile")
    @Expose
    private String riskProfile;
    @SerializedName("settlementCutOff")
    @Expose
    private String settlementCutOff;
    @SerializedName("settlementPeriod")
    @Expose
    private String settlementPeriod;
    @SerializedName("subscriptionRule")
    @Expose
    private Object subscriptionRule;
    @SerializedName("transactionCutOff")
    @Expose
    private String transactionCutOff;
    @SerializedName("unrealizedGainLoss")
    @Expose
    private Object unrealizedGainLoss;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private String updatedDate;


    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

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

}
