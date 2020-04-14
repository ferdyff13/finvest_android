package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ulfah.ulmi on 12/04/2017.
 */

public class DataPartialRedemtion {
    @SerializedName("minimal_investment_amount")
    @Expose
    private int minimalInvestmentAmount;
    @SerializedName("minPartialRedemption")
    @Expose
    private double minPartialRedemption;
    @SerializedName("maxPartialRedemption")
    @Expose
    private double maxPartialRedemption;
    @SerializedName("minimal_investment_redeem_date")
    @Expose
    private String minimalInvestmentRedeemDate;

    public int getMinimalInvestmentAmount() {
        return minimalInvestmentAmount;
    }

    public void setMinimalInvestmentAmount(int minimalInvestmentAmount) {
        this.minimalInvestmentAmount = minimalInvestmentAmount;
    }

    public double getMinPartialRedemption() {
        return minPartialRedemption;
    }

    public void setMinPartialRedemption(double minPartialRedemption) {
        this.minPartialRedemption = minPartialRedemption;
    }

    public double getMaxPartialRedemption() {
        return maxPartialRedemption;
    }

    public void setMaxPartialRedemption(double maxPartialRedemption) {
        this.maxPartialRedemption = maxPartialRedemption;
    }

    public String getMinimalInvestmentRedeemDate() {
        return minimalInvestmentRedeemDate;
    }

    public void setMinimalInvestmentRedeemDate(String minimalInvestmentRedeemDate) {
        this.minimalInvestmentRedeemDate = minimalInvestmentRedeemDate;
    }
}
