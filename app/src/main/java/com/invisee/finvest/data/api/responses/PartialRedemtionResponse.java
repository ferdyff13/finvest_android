package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.DataPartialRedemtion;

import java.io.Serializable;

/**
 * Created by ulfah.ulmi on 12/04/2017.
 */

public class PartialRedemtionResponse extends GenericResponse implements Serializable {
    @SerializedName("data")
    @Expose
    private DataPartialRedemtion data;

    public DataPartialRedemtion getData() {
        return data;
    }

    public void setData(DataPartialRedemtion data) {
        this.data = data;
    }
}
