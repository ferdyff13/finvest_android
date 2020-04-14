package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by glenrynaldi on 4/14/16.
 */
public class InvestmentAccount implements Serializable {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("atInvestmentAccountId")
    @Expose
    private String atInvestmentAccountId;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("fundPackages")
    @Expose
    private FundPackages fundPackages;
    @SerializedName("goalPlanner")
    @Expose
    private Object goalPlanner;
    @SerializedName("investmentAccountName")
    @Expose
    private String investmentAccountName;
    @SerializedName("investmentAccountNo")
    @Expose
    private String investmentAccountNo;
    @SerializedName("taggedDate")
    @Expose
    private Object taggedDate;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private String updatedDate;


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
     * @return The atInvestmentAccountId
     */
    public String getAtInvestmentAccountId() {
        return atInvestmentAccountId;
    }

    /**
     * @param atInvestmentAccountId The atInvestmentAccountId
     */
    public void setAtInvestmentAccountId(String atInvestmentAccountId) {
        this.atInvestmentAccountId = atInvestmentAccountId;
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
     * @return The fundPackages
     */
    public FundPackages getFundPackages() {
        return fundPackages;
    }

    /**
     * @param fundPackages The fundPackages
     */
    public void setFundPackages(FundPackages fundPackages) {
        this.fundPackages = fundPackages;
    }

    /**
     * @return The goalPlanner
     */
    public Object getGoalPlanner() {
        return goalPlanner;
    }

    /**
     * @param goalPlanner The goalPlanner
     */
    public void setGoalPlanner(Object goalPlanner) {
        this.goalPlanner = goalPlanner;
    }

    /**
     * @return The investmentAccountName
     */
    public String getInvestmentAccountName() {
        return investmentAccountName;
    }

    /**
     * @param investmentAccountName The investmentAccountName
     */
    public void setInvestmentAccountName(String investmentAccountName) {
        this.investmentAccountName = investmentAccountName;
    }

    /**
     * @return The investmentAccountNo
     */
    public String getInvestmentAccountNo() {
        return investmentAccountNo;
    }

    /**
     * @param investmentAccountNo The investmentAccountNo
     */
    public void setInvestmentAccountNo(String investmentAccountNo) {
        this.investmentAccountNo = investmentAccountNo;
    }


    /**
     * @return The taggedDate
     */
    public Object getTaggedDate() {
        return taggedDate;
    }

    /**
     * @param taggedDate The taggedDate
     */
    public void setTaggedDate(Object taggedDate) {
        this.taggedDate = taggedDate;
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
        return investmentAccountNo;
    }

}
