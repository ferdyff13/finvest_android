package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.Kyc;

import java.io.Serializable;

/**
 * Created by fajarfatur on 2/22/16.
 */
public class LoadKycDataResponse extends GenericResponse implements Serializable{

    @SerializedName("data")
    @Expose
    private Kyc data;

    /**
     * @return The data
     */
    public Kyc getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(Kyc data) {
        this.data = data;
    }

}
