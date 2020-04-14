package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by glenrynaldi on 4/11/16.
 */
public class WalletHistory implements Serializable {

    @SerializedName("sourceAccount")
    @Expose
    private String sourceAccount;
    @SerializedName("transactionExpiryDate")
    @Expose
    private String transactionExpiryDate;
    @SerializedName("transactionNumber")
    @Expose
    private String transactionNumber;
    @SerializedName("destAccount")
    @Expose
    private String destAccount;
    @SerializedName("transactionTimeStamp")
    @Expose
    private String transactionTimeStamp;
    @SerializedName("transactionTypeCode")
    @Expose
    private String transactionTypeCode;
    @SerializedName("enteredAmount")
    @Expose
    private Double enteredAmount;
    @SerializedName("transactionType")
    @Expose
    private String transactionType;
    @SerializedName("accountedAmount")
    @Expose
    private Double accountedAmount;
    @SerializedName("drCr")
    @Expose
    private String drCr;
    @SerializedName("balanceAfterTrx")
    @Expose
    private Double balanceAfterTrx;
    @SerializedName("refNumber")
    @Expose
    private String refNumber;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     *
     * @return
     * The sourceAccount
     */
    public String getSourceAccount() {
        return sourceAccount;
    }

    /**
     *
     * @param sourceAccount
     * The sourceAccount
     */
    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    /**
     *
     * @return
     * The transactionExpiryDate
     */
    public String getTransactionExpiryDate() {
        return transactionExpiryDate;
    }

    /**
     *
     * @param transactionExpiryDate
     * The transactionExpiryDate
     */
    public void setTransactionExpiryDate(String transactionExpiryDate) {
        this.transactionExpiryDate = transactionExpiryDate;
    }

    /**
     *
     * @return
     * The transactionNumber
     */
    public String getTransactionNumber() {
        return transactionNumber;
    }

    /**
     *
     * @param transactionNumber
     * The transactionNumber
     */
    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    /**
     *
     * @return
     * The destAccount
     */
    public String getDestAccount() {
        return destAccount;
    }

    /**
     *
     * @param destAccount
     * The destAccount
     */
    public void setDestAccount(String destAccount) {
        this.destAccount = destAccount;
    }

    /**
     *
     * @return
     * The transactionTimeStamp
     */
    public String getTransactionTimeStamp() {
        return transactionTimeStamp;
    }

    /**
     *
     * @param transactionTimeStamp
     * The transactionTimeStamp
     */
    public void setTransactionTimeStamp(String transactionTimeStamp) {
        this.transactionTimeStamp = transactionTimeStamp;
    }

    /**
     *
     * @return
     * The transactionTypeCode
     */
    public String getTransactionTypeCode() {
        return transactionTypeCode;
    }

    /**
     *
     * @param transactionTypeCode
     * The transactionTypeCode
     */
    public void setTransactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    /**
     *
     * @return
     * The enteredAmount
     */
    public Double getEnteredAmount() {
        return enteredAmount;
    }

    /**
     *
     * @param enteredAmount
     * The enteredAmount
     */
    public void setEnteredAmount(Double enteredAmount) {
        this.enteredAmount = enteredAmount;
    }

    /**
     *
     * @return
     * The transactionType
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     *
     * @param transactionType
     * The transactionType
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    /**
     *
     * @return
     * The accountedAmount
     */
    public Double getAccountedAmount() {
        return accountedAmount;
    }

    /**
     *
     * @param accountedAmount
     * The accountedAmount
     */
    public void setAccountedAmount(Double accountedAmount) {
        this.accountedAmount = accountedAmount;
    }

    /**
     *
     * @return
     * The drCr
     */
    public String getDrCr() {
        return drCr;
    }

    /**
     *
     * @param drCr
     * The drCr
     */
    public void setDrCr(String drCr) {
        this.drCr = drCr;
    }

    /**
     *
     * @return
     * The balanceAfterTrx
     */
    public Double getBalanceAfterTrx() {
        return balanceAfterTrx;
    }

    /**
     *
     * @param balanceAfterTrx
     * The balanceAfterTrx
     */
    public void setBalanceAfterTrx(Double balanceAfterTrx) {
        this.balanceAfterTrx = balanceAfterTrx;
    }

    /**
     *
     * @return
     * The refNumber
     */
    public String getRefNumber() {
        return refNumber;
    }

    /**
     *
     * @param refNumber
     * The refNumber
     */
    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    /**
     *
     * @return
     * The time
     */
    public String getTime() {
        return time;
    }

    /**
     *
     * @param time
     * The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
