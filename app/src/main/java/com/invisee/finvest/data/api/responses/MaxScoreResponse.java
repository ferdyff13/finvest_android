package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.MaxScore;

/**
 * Created by pandu.abbiyuarsyah on 03/07/2017.
 */

public class MaxScoreResponse extends GenericResponse {

    @SerializedName("data")
    @Expose
    private MaxScore data;

    public MaxScore getData() {
        return data;
    }

    public void setData(MaxScore data) {
        this.data = data;
    }

}
