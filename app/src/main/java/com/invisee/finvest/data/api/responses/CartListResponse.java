package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.Fee;
import com.invisee.finvest.data.api.beans.FundPackages;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.Transaction;
import com.invisee.finvest.data.api.beans.TransactionType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class CartListResponse extends GenericResponse implements Serializable {

    @SerializedName("trx")
    @Expose
    private Transaction trx;
    @SerializedName("investmentAccount")
    @Expose
    private String investmentAccount;
    @SerializedName("feeAmount")
    @Expose
    private Double feeAmount;
    @SerializedName("package")
    @Expose
    private Packages packages;
    @SerializedName("transactionType")
    @Expose
    private TransactionType transactionType;
    @SerializedName("fundPackages")
    @Expose
    private FundPackages fundPackages;
    @SerializedName("fundPackagesProduct")
    @Expose
    private List<List<String>> fundPackagesProduct = new ArrayList<List<String>>();
    private List<Fee> feeList;
    private String transactionAmount = "0";
    private String feePercentage = "0";
    private Double feePrice = 0d;
    private String total = "0";

    /**
     * @return The trx
     */
    public Transaction getTransaction() {
        return trx;
    }

    /**
     * @param trx The trx
     */
    public void setTransaction(Transaction trx) {
        this.trx = trx;
    }

    /**
     * @return The investmentAccount
     */
    public String getInvestmentAccount() {
        return investmentAccount;
    }

    /**
     * @param investmentAccount The investmentAccount
     */
    public void setInvestmentAccount(String investmentAccount) {
        this.investmentAccount = investmentAccount;
    }

    /**
     * @return The feeAmount
     */
    public Double getFeeAmount() {
        return feeAmount;
    }

    /**
     * @param feeAmount The feeAmount
     */
    public void setFeeAmount(Double feeAmount) {
        this.feeAmount = feeAmount;
    }

    /**
     * @return The _package
     */
    public Packages getPackage() {
        return packages;
    }

    /**
     * @param packages The package
     */
    public void setPackage(Packages packages) {
        this.packages = packages;
    }

    /**
     * @return The transactionType
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * @param transactionType The transactionType
     */
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
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
     * @return The fundPackagesProduct
     */
    public List<List<String>> getFundPackagesProduct() {
        return fundPackagesProduct;
    }

    /**
     * @param fundPackagesProduct The fundPackagesProduct
     */
    public void setFundPackagesProduct(List<List<String>> fundPackagesProduct) {
        this.fundPackagesProduct = fundPackagesProduct;
    }


    public List<Fee> getFeeList() {
        return feeList;
    }

    public void setFeeList(List<Fee> feeList) {
        this.feeList = feeList;
    }

    public Transaction getTrx() {
        return trx;
    }

    public void setTrx(Transaction trx) {
        this.trx = trx;
    }

    public Packages getPackages() {
        return packages;
    }

    public void setPackages(Packages packages) {
        this.packages = packages;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFeePercentage() {
        return feePercentage;
    }

    public void setFeePercentage(String feePercentage) {
        this.feePercentage = feePercentage;
    }

    public Double getFeePrice() {
        return feePrice;
    }

    public void setFeePrice(Double feePrice) {
        this.feePrice = feePrice;
    }
}
