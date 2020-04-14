package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.PortfolioInvestment;
import com.invisee.finvest.data.api.beans.Products;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandu.abbiyuarsyah on 17/03/2017.
 */

public class ProductResponse extends GenericResponse implements Serializable {

    @SerializedName("data")
    @Expose
    private List<Products> data = new ArrayList<>();

    /**
     * @return The data
     */
    public List<Products> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<Products> data) {
        this.data = data;
    }

}
