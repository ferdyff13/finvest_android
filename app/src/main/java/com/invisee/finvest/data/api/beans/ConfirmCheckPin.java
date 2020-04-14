package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by pandu.abbiyuarsyah on 24/03/2017.
 */

public class ConfirmCheckPin implements Serializable{


    @SerializedName("amount")
    @Expose
    private BigDecimal amount;

    @SerializedName("orderNo")
    @Expose
    private String orderNo;

    @SerializedName("balance")
    @Expose
    private BigDecimal balance;

    @SerializedName("account")
    @Expose
    private String account;

    @SerializedName("total")
    @Expose
    private String total;

    @SerializedName("dompetku")
    @Expose
    private String dompetku;

    /**
     * @return The amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount The amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return The orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo The orderNo
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return The balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * @param balance The balance
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * @return The account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account The account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return The total
     */
    public String getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * @return The dompetku
     */
    public String getDompetku() {
        return dompetku;
    }

    /**
     * @param dompetku The dompetku
     */
    public void setDompetku(String dompetku) {
        this.dompetku = dompetku;
    }

}


