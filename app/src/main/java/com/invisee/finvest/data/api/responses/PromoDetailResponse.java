package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.PromoDetail;

import java.io.Serializable;

/**
 * Created by pandu.abbiyuarsyah on 19/05/2017.
 */

public class PromoDetailResponse implements Serializable {

    @SerializedName("data")
    @Expose
    private PromoDetail data;

    /**
     * @return The data
     */
    public PromoDetail getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(PromoDetail data) {
        this.data = data;
    }

}
