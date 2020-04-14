package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.Completeness;

/**
 * Created by fajarfatur on 2/23/16.
 */
public class CompletenessPercentageResponse extends GenericResponse {

    @SerializedName("data")
    @Expose
    private Completeness data;

    /**
     * @return The data
     */
    public Completeness getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(Completeness data) {
        this.data = data;
    }

}
