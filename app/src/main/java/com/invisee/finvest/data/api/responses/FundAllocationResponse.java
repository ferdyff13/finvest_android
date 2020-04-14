package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.FundAllocation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fajarfatur on 2/23/16.
 */
public class FundAllocationResponse extends GenericResponse implements Serializable {

    @SerializedName("data")
    @Expose
    private List<FundAllocation> data = new ArrayList<>();


    /**
     *
     * @return
     * The data
     */
    public List<FundAllocation> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<FundAllocation> data) {
        this.data = data;
    }

}
