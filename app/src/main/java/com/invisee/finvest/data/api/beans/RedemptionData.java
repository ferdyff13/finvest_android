package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fajarfatur on 3/16/16.
 */
public class RedemptionData implements Serializable{

    @SerializedName("product")
    @Expose
    private List<RedemptionProductComposition> product = new ArrayList<>();
    @SerializedName("orderNumber")
    @Expose
    private String orderNumber;
    @SerializedName("paidDate")
    @Expose
    private String paidDate;
    @SerializedName("accountName")
    @Expose
    private String accountName;
    @SerializedName("redeemAmount")
    @Expose
    private double redeemAmount;
    @SerializedName("fee")
    @Expose
    private double fee;
    @SerializedName("investment")
    @Expose
    private String investment;
    @SerializedName("marketValue")
    @Expose
    private double marketValue;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("packageName")
    @Expose
    private String packageName;
    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("branch")
    @Expose
    private String branch;

    public List<RedemptionProductComposition> getProduct() {
        return product;
    }

    public void setProduct(List<RedemptionProductComposition> product) {
        this.product = product;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getRedeemAmount() {
        return redeemAmount;
    }

    public void setRedeemAmount(double redeemAmount) {
        this.redeemAmount = redeemAmount;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getInvestment() {
        return investment;
    }

    public void setInvestment(String investment) {
        this.investment = investment;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
