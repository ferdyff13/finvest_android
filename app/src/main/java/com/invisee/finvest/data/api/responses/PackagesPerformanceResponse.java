package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.PackagesPerformance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fajarfatur on 2/23/16.
 */
public class PackagesPerformanceResponse extends GenericResponse implements Serializable {

    @SerializedName("data")
    @Expose
    private PackagesPerformance data;

    /**
     * @return The data
     */
    public PackagesPerformance getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(PackagesPerformance data) {
        this.data = data;
    }

}
