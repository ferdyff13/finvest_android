package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.PortfolioInvestment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fajarfatur on 3/1/16.
 */
public class PortfolioInvestmentListResponse extends GenericResponse implements Serializable {

    @SerializedName("data")
    @Expose
    private List<PortfolioInvestment> data = new ArrayList<>();

    /**
     * @return The data
     */
    public List<PortfolioInvestment> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<PortfolioInvestment> data) {
        this.data = data;
    }

}
