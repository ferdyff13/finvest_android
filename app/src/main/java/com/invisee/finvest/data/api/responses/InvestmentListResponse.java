package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.Packages;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class InvestmentListResponse extends GenericResponse {

    @SerializedName("data")
    @Expose
    private List<Packages> packages = new ArrayList<>();
    
    public List<Packages> getPackages() {
        return packages;
    }

    public void setPackages(List<Packages> packages) {
        this.packages = packages;
    }
}
