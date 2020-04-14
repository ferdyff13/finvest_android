package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.RedemptionProductComposition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fajarfatur on 3/15/16.
 */
public class RedemptionOrderDetailResponse extends GenericResponse implements Serializable{

    @SerializedName("investment")
    @Expose
    private String investment;
    @SerializedName("packageName")
    @Expose
    private String packageName;
    @SerializedName("marketValue")
    @Expose
    private double marketValue;
    @SerializedName("fee")
    @Expose
    private double fee;
    @SerializedName("redeemAmount")
    @Expose
    private double redeemAmount;
    @SerializedName("totalCurrentAmount")
    @Expose
    private double totalCurrentAmount;
    @SerializedName("products")
    @Expose
    private List<RedemptionProductComposition> products = new ArrayList<>();

    /**
     * @return The investment
     */
    public String getInvestment() {
        return investment;
    }

    /**
     * @param investment The investment
     */
    public void setInvestment(String investment) {
        this.investment = investment;
    }

    /**
     * @return The packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName The packageName
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * @return The marketValue
     */
    public double getMarketValue() {
        return marketValue;
    }

    /**
     * @param marketValue The marketValue
     */
    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }

    /**
     * @return The fee
     */
    public double getFee() {
        return fee;
    }

    /**
     * @param fee The fee
     */
    public void setFee(double fee) {
        this.fee = fee;
    }

    /**
     * @return The redeemAmount
     */
    public double getRedeemAmount() {
        return redeemAmount;
    }

    /**
     * @param redeemAmount The redeemAmount
     */
    public void setRedeemAmount(double redeemAmount) {
        this.redeemAmount = redeemAmount;
    }

    /**
     * @return The products
     */
    public List<RedemptionProductComposition> getProducts() {
        return products;
    }

    /**
     * @param products The products
     */
    public void setProducts(List<RedemptionProductComposition> products) {
        this.products = products;
    }

    /**
     * @return The totalCurrentAmount
     */
    public double getTotalCurrentAmount() {
        return totalCurrentAmount;
    }

    /**
     * @param totalCurrentAmount The totalCurrentAmount
     */
    public void setTotalCurrentAmount(double totalCurrentAmount) {
        this.totalCurrentAmount = totalCurrentAmount;
    }


}
