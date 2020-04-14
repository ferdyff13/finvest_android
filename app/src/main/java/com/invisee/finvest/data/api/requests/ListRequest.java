package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarfatur on 2/3/16.
 */
public class ListRequest {

    @SerializedName("minRow")
    @Expose
    private String minRow;
    @SerializedName("maxRow")
    @Expose
    private String maxRow;
    @SerializedName("max")
    @Expose
    private String max;


    public String getMinRow() {
        return minRow;
    }

    public void setMinRow(String minRow) {
        this.minRow = minRow;
    }

    public String getMaxRow() {
        return maxRow;
    }

    public void setMaxRow(String maxRow) {
        this.maxRow = maxRow;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }
}
