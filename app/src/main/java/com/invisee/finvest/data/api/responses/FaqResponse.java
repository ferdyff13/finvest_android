package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.Faq;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by glenrynaldi on 6/10/16.
 */
public class FaqResponse extends GenericResponse {

    @SerializedName("data")
    @Expose
    private List<Faq> data = new ArrayList<>();


    /**
     * @return The data
     */
    public List<Faq> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<Faq> data) {
        this.data = data;
    }

}
