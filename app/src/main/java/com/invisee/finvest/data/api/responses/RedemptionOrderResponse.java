package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pandu.abbiyuarsyah on 29/03/2017.
 */

public class RedemptionOrderResponse extends GenericResponse implements Serializable {

    @SerializedName("data")
    @Expose
    private RedemptionOrderDetailResponse data;

    /**
     * @return The data
     */
    public RedemptionOrderDetailResponse getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(RedemptionOrderDetailResponse data) {
        this.data = data;
    }
}
