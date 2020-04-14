package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.Slider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandu.abbiyuarsyah on 15/11/2017.
 */

public class SliderResponse extends GenericResponse {

    @SerializedName("data")
    @Expose
    private List<Slider> data = new ArrayList<>();

    public List<Slider> getData() {
        return data;
    }

    public void setData(List<Slider> data) {
        this.data = data;
    }
}
