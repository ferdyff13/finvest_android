package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.Packages;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class PackageResponse extends GenericResponse {

    @SerializedName("data")
    @Expose
    private Packages data;

    /**
     * @return The data
     */
    public Packages getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(Packages data) {
        this.data = data;
    }

}
