package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by glenrynaldi on 2/22/16.
 */
public class PackagesPerformance implements Serializable {

    @SerializedName("performance_data")
    @Expose
    private List<PerformanceData> performanceData = new ArrayList<>();

    @SerializedName("performance_date")
    @Expose
    private List<String> performanceDate= new ArrayList<>();

    /**
     * @return The performanceData
     */
    public List<PerformanceData> getPerformanceData() {
        return performanceData;
    }

    /**
     * @param performanceData The performanceData
     */
    public void setPerformanceData(List<PerformanceData> performanceData) {
        this.performanceData = performanceData;
    }

    /**
     * @return The performanceDate
     */

    public List<String> getPerformanceDate() {
        return performanceDate;
    }

    /**
     * @param performanceDate The performanceDate
     */
    public void setPerformanceDate(List<String> performanceDate) {
        this.performanceDate = performanceDate;
    }


}
