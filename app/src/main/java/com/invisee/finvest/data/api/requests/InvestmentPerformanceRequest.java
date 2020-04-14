package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarfatur on 3/4/16.
 */
public class InvestmentPerformanceRequest {

    @SerializedName("investmentAccountNo")
    @Expose
    private String investmentAccountNo;

    /**
     * @param investmentAccountNo
     */
    public InvestmentPerformanceRequest(String investmentAccountNo) {
        this.investmentAccountNo = investmentAccountNo;
    }

    /**
     * @return The investmentAccountNo
     */
    public String getInvestmentAccountNo() {
        return investmentAccountNo;
    }

    /**
     * @param investmentAccountNo The investmentAccountNo
     */
    public void setInvestmentAccountNo(String investmentAccountNo) {
        this.investmentAccountNo = investmentAccountNo;
    }

}
