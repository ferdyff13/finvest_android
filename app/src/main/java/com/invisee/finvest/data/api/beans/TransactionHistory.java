package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fajarfatur on 3/6/16.
 */
public class TransactionHistory implements Serializable {

    @SerializedName("order_number")
    @Expose
    private String orderNumber;
    @SerializedName("transaction_date")
    @Expose
    private String transactionDate;
    @SerializedName("transaction_time")
    @Expose
    private String transactionTime;
    @SerializedName("investment_number")
    @Expose
    private String investmentNumber;
    @SerializedName("package_name")
    @Expose
    private String packageName;
    @SerializedName("amount")
    @Expose
    private double amount;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("transaction_type")
    @Expose
    private String transactionType;
    @SerializedName("transaction_status")
    @Expose
    private String transactionStatus;
    @SerializedName("count")
    @Expose
    private List<Integer> count = new ArrayList<Integer>();

    /**
     * @return The orderNumber
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * @param orderNumber The order_number
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * @return The transactionDate
     */
    public String getTransactionDate() {
        return transactionDate;
    }

    /**
     * @param transactionDate The transaction_date
     */
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * @return The transactionTime
     */
    public String getTransactionTime() {
        return transactionTime;
    }

    /**
     * @param transactionTime The transaction_time
     */
    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    /**
     * @return The investmentNumber
     */
    public String getInvestmentNumber() {
        return investmentNumber;
    }

    /**
     * @param investmentNumber The investment_number
     */
    public void setInvestmentNumber(String investmentNumber) {
        this.investmentNumber = investmentNumber;
    }

    /**
     * @return The packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName The package_name
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * @return The amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount The amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return The paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * @param paymentType The payment_type
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * @return The transactionType
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * @param transactionType The transaction_type
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * @return The transactionStatus
     */
    public String getTransactionStatus() {
        return transactionStatus;
    }

    /**
     * @param transactionStatus The transaction_status
     */
    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    /**
     * @return The count
     */
    public List<Integer> getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(List<Integer> count) {
        this.count = count;
    }
}
