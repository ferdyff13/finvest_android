package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pandu.abbiyuarsyah on 18/10/2017.
 */

public class TopUpViseePay implements Serializable {

    @SerializedName("addrRek")
    @Expose
    private String addrRek;
    @SerializedName("trxAmount")
    @Expose
    private Double trxAmount;
    @SerializedName("totalAmount")
    @Expose
    private Double totalAmount;
    @SerializedName("bank")
    @Expose
    private String bank;
    @SerializedName("noRek")
    @Expose
    private String noRek;
    @SerializedName("dueDate")
    @Expose
    private String dueDate;
    @SerializedName("uniqueId")
    @Expose
    private String uniqueId;
    @SerializedName("nameRek")
    @Expose
    private String nameRek;
    @SerializedName("paymentCode")
    @Expose
    private String paymentCode;

    public String getAddrRek() {
        return addrRek;
    }

    public void setAddrRek(String addrRek) {
        this.addrRek = addrRek;
    }

    public Double getTrxAmount() {
        return trxAmount;
    }

    public void setTrxAmount(Double trxAmount) {
        this.trxAmount = trxAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getNoRek() {
        return noRek;
    }

    public void setNoRek(String noRek) {
        this.noRek = noRek;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getNameRek() {
        return nameRek;
    }

    public void setNameRek(String nameRek) {
        this.nameRek = nameRek;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }
}
