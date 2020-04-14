package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.PromoResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandu.abbiyuarsyah on 18/05/2017.
 */

public class PromoListResponse extends GenericResponse implements Serializable{


    @SerializedName("data")
    @Expose
    private List<PromoResponse> data = new ArrayList<PromoResponse>();

    /**
     * @return The data
     */
    public List<PromoResponse> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<PromoResponse> data) {
        this.data = data;
    }

}
