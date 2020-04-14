package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by glenrynaldi on 3/14/16.
 */
public class TransactionTransfer implements Serializable {

    @SerializedName("orderNumber")
    @Expose
    private String orderNumber;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("branchName")
    @Expose
    private String branchName;
    @SerializedName("accountName")
    @Expose
    private String accountName;
    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("effectiveDate")
    @Expose
    private String effectiveDate;
    @SerializedName("effectiveTime")
    @Expose
    private String effectiveTime;
    @SerializedName("netFeeDto")
    @Expose
    private List<DtoNetFeeResponse> netFeeDto = new ArrayList<>();

    /**
     * @return The orderNumber
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * @param orderNumber The orderNumber
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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
     * @return The accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * @param accountNumber The accountNumber
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * @return The branchName
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * @param branchName The branchName
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * @return The accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName The accountName
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * @return The total
     */
    public Double getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    public void setTotal(Double total) {
        this.total = total;
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
     * @return The effectiveTime
     */
    public String getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * @param effectiveTime The effectiveTime
     */
    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    /**
     * @return The netFeeDto
     */
    public List<DtoNetFeeResponse> getNetFeeDto() {
        return netFeeDto;
    }

    /**
     * @param netFeeDto The netFeeDto
     */
    public void setNetFeeDto(List<DtoNetFeeResponse> netFeeDto) {
        this.netFeeDto = netFeeDto;
    }

}
