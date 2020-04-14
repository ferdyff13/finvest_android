package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 20/03/2017.
 */

public class ProductDetail {

    @SerializedName("settlementCutOff")
    private String settlementCutOff;

    @SerializedName("minTopUpAmmount")
    private Integer minTopUpAmmount;

    @SerializedName("return_value")
    private Double returnValue;

    @SerializedName("fundPackageName")
    private String fundPackageName;

    @SerializedName("riskProfile")
    private String riskProfile;

    @SerializedName("nav_date")
    private String navDate;

    @SerializedName("settlementPeriod")
    private String settlementPeriod;

    @SerializedName("bankName")
    private String bankName;

    @SerializedName("packageDesc")
    private String packageDesc;

    @SerializedName("packageImage")
    private String packageImage;

    @SerializedName("nav_value")
    private Double navValue;

    @SerializedName("transactionCutOff")
    private String transactionCutOff;

    @SerializedName("packageCode")
    private String packageCode;

    @SerializedName("minSubcriptionAmount")
    private Integer minSubcriptionAmount;

    @SerializedName("currency")
    private String currency;

    @SerializedName("effectiveDate")
    private String effectiveDate;

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
     * @return The minTopUpAmmount
     */
    public Integer getMinTopUpAmmount() {
        return minTopUpAmmount;
    }

    /**
     * @param minTopUpAmmount The minTopUpAmmount
     */
    public void setMinTopUpAmmount(Integer minTopUpAmmount) {
        this.minTopUpAmmount = minTopUpAmmount;
    }

    /**
     * @return The returnValue
     */
    public Double getReturnValue() {
        return returnValue;
    }

    /**
     * @param returnValue The returnValue
     */
    public void setReturnValue(Double returnValue) {
        this.returnValue = returnValue;
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
     * @return The navDate
     */
    public String getNavDate() {
        return navDate;
    }

    /**
     * @param navDate The navDate
     */
    public void setNavDate(String navDate) {
        this.navDate = navDate;
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
     * @return The bankName
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
     * @return The bankName
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
     * @return The navValue
     */
    public Double getNavValue() {
        return navValue;
    }

    /**
     * @param navValue The navValue
     */
    public void setNavValue(Double navValue) {
        this.navValue = navValue;
    }

    /**
     * @return The navValue
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
     * @return The navValue
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
     * @return The minSubcriptionAmount
     */
    public Integer getMinSubcriptionAmount() {
        return minSubcriptionAmount;
    }

    /**
     * @param minSubcriptionAmount The minSubcriptionAmount
     */
    public void setMinSubcriptionAmount(Integer minSubcriptionAmount) {
        this.minSubcriptionAmount = minSubcriptionAmount;
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


}
