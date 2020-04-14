package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.InvestmentPerformance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fajarfatur on 3/4/16.
 */
public class InvestmentPerformanceResponse extends GenericResponse{

    @SerializedName("data")
    @Expose
    private List<InvestmentPerformance> data = new ArrayList<>();

    /**
     *
     * @return
     * The data
     */
    public List<InvestmentPerformance> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<InvestmentPerformance> data) {
        this.data = data;
    }

}
