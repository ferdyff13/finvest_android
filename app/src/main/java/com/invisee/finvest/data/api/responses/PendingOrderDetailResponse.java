package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.PackagesPerformance;
import com.invisee.finvest.data.api.beans.PendingOrderDetail;

import java.io.Serializable;

/**
 * Created by pandu.abbiyuarsyah on 30/03/2017.
 */

public class PendingOrderDetailResponse extends GenericResponse implements Serializable {

    @SerializedName("data")
    @Expose
    private PendingOrderDetail data;

    /**
     * @return The data
     */
    public PendingOrderDetail getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(PendingOrderDetail data) {
        this.data = data;
    }

}
