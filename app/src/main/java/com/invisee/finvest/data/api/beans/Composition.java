package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.InviseeService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by glenrynaldi on 3/18/16.
 */
public class Composition implements Serializable {

    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("composition")
    @Expose
    private Integer composition;
    @SerializedName("settlementAccounts")
    @Expose
    private List<SettlementAccounts> settlementAccounts = new ArrayList<>();
    @SerializedName("netAmount")
    @Expose
    private Double netAmount;
    @SerializedName("fee")
    @Expose
    private Double fee;
    @SerializedName("totalAmount")
    @Expose
    private Double totalAmount;

    /**
     *
     * @return
     * The productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     *
     * @param productName
     * The productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     *
     * @return
     * The composition
     */
    public Integer getComposition() {
        return composition;
    }

    /**
     *
     * @param composition
     * The composition
     */
    public void setComposition(Integer composition) {
        this.composition = composition;
    }

    /**
     * @return The settlementAccounts
     */
    public List<SettlementAccounts> getSettlementAccounts() {
        return settlementAccounts;
    }

    /**
     * @param settlementAccounts The settlementAccounts
     */
    public void setSettlementAccounts(List<SettlementAccounts> settlementAccounts) {
        this.settlementAccounts = settlementAccounts;
    }

    /**
     * @return
     * The netAmount
     */
    public Double getNetAmount() {
        return netAmount;
    }

    /**
     * @param netAmount
     * The netAmount
     */
    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    /**
     * @return
     * The fee
     */
    public Double getFee() {
        return fee;
    }

    /**
     * @param fee
     * The fee
     */
    public void setFee(Double fee) {
        this.fee = fee;
    }

    /**
     * @return
     * The fee
     */
    public Double getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount
     * The totalAmount
     */
    public void setTotalAmount(Double totalAmount) {
        this.fee = totalAmount;
    }

}
