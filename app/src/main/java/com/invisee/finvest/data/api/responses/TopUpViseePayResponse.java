package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.TopUpViseePay;

/**
 * Created by pandu.abbiyuarsyah on 18/10/2017.
 */

public class TopUpViseePayResponse extends GenericResponse {

    @SerializedName("data")
    @Expose
    private TopUpViseePay data;

    public TopUpViseePay getData() {
        return data;
    }

    public void setData(TopUpViseePay data) {
        this.data = data;
    }


}
