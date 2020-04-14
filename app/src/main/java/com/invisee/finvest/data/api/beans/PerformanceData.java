package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandu.abbiyuarsyah on 20/03/2017.
 */

public class PerformanceData {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("value")
    @Expose
    private List<Double> value = new ArrayList<Double>();

    /**
     * @return The label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return The value
     */
    public List<Double> getValue() {
        return value;
    }

    /**
     * @param value The value
     */
    public void setValue(List<Double> value) {
        this.value = value;
    }

}
