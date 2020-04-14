package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 09/10/2017.
 */

public class StatusCustomerResponse extends GenericResponse {

    @SerializedName("data")
    @Expose
    private String data;

    public String getData() {
        return  data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
