package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.Packages;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by glenrynaldi on 4/13/16.
 */
public class PackageByTokenResponse extends GenericResponse {

    @SerializedName("data")
    @Expose
    private List<Packages> data = new ArrayList<>();

    /**
     * @return The data
     */
    public List<Packages> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<Packages> data) {
        this.data = data;
    }

}
