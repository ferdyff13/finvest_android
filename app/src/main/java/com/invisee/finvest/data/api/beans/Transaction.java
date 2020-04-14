package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by glenrynaldi on 3/14/16.
 */
public class Transaction implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("feeAmount")
    @Expose
    private Object feeAmount;
    @SerializedName("goalPlanner")
    @Expose
    private Object goalPlanner;
    @SerializedName("investmentAccount")
    @Expose
    private Object investmentAccount;
    @SerializedName("netAmount")
    @Expose
    private Object netAmount;
    @SerializedName("orderAmount")
    @Expose
    private Double orderAmount;
    @SerializedName("orderNo")
    @Expose
    private Object orderNo;
    @SerializedName("paymentType")
    @Expose
    private Object paymentType;
    @SerializedName("settlementRefNo")
    @Expose
    private String settlementRefNo;
    @SerializedName("settlementStatus")
    @Expose
    private Object settlementStatus;
    @SerializedName("trxDate")
    @Expose
    private String trxDate;
    @SerializedName("trxStatus")
    @Expose
    private String trxStatus;
    @SerializedName("updatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private Object updatedDate;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy
     * The createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return
     * The createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     *
     * @param createdDate
     * The createdDate
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     *
     * @return
     * The feeAmount
     */
    public Object getFeeAmount() {
        return feeAmount;
    }

    /**
     *
     * @param feeAmount
     * The feeAmount
     */
    public void setFeeAmount(Object feeAmount) {
        this.feeAmount = feeAmount;
    }

    /**
     *
     * @return
     * The goalPlanner
     */
    public Object getGoalPlanner() {
        return goalPlanner;
    }

    /**
     *
     * @param goalPlanner
     * The goalPlanner
     */
    public void setGoalPlanner(Object goalPlanner) {
        this.goalPlanner = goalPlanner;
    }

    /**
     *
     * @return
     * The investmentAccount
     */
    public Object getInvestmentAccount() {
        return investmentAccount;
    }

    /**
     *
     * @param investmentAccount
     * The investmentAccount
     */
    public void setInvestmentAccount(Object investmentAccount) {
        this.investmentAccount = investmentAccount;
    }

    /**
     *
     * @return
     * The netAmount
     */
    public Object getNetAmount() {
        return netAmount;
    }

    /**
     *
     * @param netAmount
     * The netAmount
     */
    public void setNetAmount(Object netAmount) {
        this.netAmount = netAmount;
    }

    /**
     *
     * @return
     * The orderAmount
     */
    public Double getOrderAmount() {
        return orderAmount;
    }

    /**
     *
     * @param orderAmount
     * The orderAmount
     */
    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     *
     * @return
     * The orderNo
     */
    public Object getOrderNo() {
        return orderNo;
    }

    /**
     *
     * @param orderNo
     * The orderNo
     */
    public void setOrderNo(Object orderNo) {
        this.orderNo = orderNo;
    }

    /**
     *
     * @return
     * The paymentType
     */
    public Object getPaymentType() {
        return paymentType;
    }

    /**
     *
     * @param paymentType
     * The paymentType
     */
    public void setPaymentType(Object paymentType) {
        this.paymentType = paymentType;
    }

    /**
     *
     * @return
     * The settlementRefNo
     */
    public String getSettlementRefNo() {
        return settlementRefNo;
    }

    /**
     *
     * @param settlementRefNo
     * The settlementRefNo
     */
    public void setSettlementRefNo(String settlementRefNo) {
        this.settlementRefNo = settlementRefNo;
    }

    /**
     *
     * @return
     * The settlementStatus
     */
    public Object getSettlementStatus() {
        return settlementStatus;
    }

    /**
     *
     * @param settlementStatus
     * The settlementStatus
     */
    public void setSettlementStatus(Object settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    /**
     *
     * @return
     * The trxDate
     */
    public String getTrxDate() {
        return trxDate;
    }

    /**
     *
     * @param trxDate
     * The trxDate
     */
    public void setTrxDate(String trxDate) {
        this.trxDate = trxDate;
    }

    /**
     *
     * @return
     * The trxStatus
     */
    public String getTrxStatus() {
        return trxStatus;
    }

    /**
     *
     * @param trxStatus
     * The trxStatus
     */
    public void setTrxStatus(String trxStatus) {
        this.trxStatus = trxStatus;
    }

    /**
     *
     * @return
     * The updatedBy
     */
    public Object getUpdatedBy() {
        return updatedBy;
    }

    /**
     *
     * @param updatedBy
     * The updatedBy
     */
    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     *
     * @return
     * The updatedDate
     */
    public Object getUpdatedDate() {
        return updatedDate;
    }

    /**
     *
     * @param updatedDate
     * The updatedDate
     */
    public void setUpdatedDate(Object updatedDate) {
        this.updatedDate = updatedDate;
    }

}
