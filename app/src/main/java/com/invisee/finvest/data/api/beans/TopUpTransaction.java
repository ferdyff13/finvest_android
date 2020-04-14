package com.invisee.finvest.data.api.beans;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pandu.abbiyuarsyah on 19/10/2017.
 */

public class TopUpTransaction implements Serializable {

    @SerializedName("transactionDate")
    @Expose
    private String transactionDate;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("trxStatus")
    @Expose
    private String trxStatus;
    @SerializedName("trxNo")
    @Expose
    private String trxNo;
    @SerializedName("totalAmount")
    @Expose
    private Double totalAmount;
    @SerializedName("dueDate")
    @Expose
    private String dueDate;
    @SerializedName("transferDate")
    @Expose
    private String transferDate;

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTrxStatus() {
        return trxStatus;
    }

    public void setTrxStatus(String trxStatus) {
        this.trxStatus = trxStatus;
    }

    public String getTrxNo() {
        return trxNo;
    }

    public void setTrxNo(String trxNo) {
        this.trxNo = trxNo;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }


}
