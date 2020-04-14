package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.RedemptionData;

/**
 * Created by fajarfatur on 3/16/16.
 */
public class CreateRedemptionResponse extends GenericResponse {

    @SerializedName("data")
    @Expose
    private RedemptionData data;

    /**
     * @return The data
     */
    public RedemptionData getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(RedemptionData data) {
        this.data = data;
    }

}
