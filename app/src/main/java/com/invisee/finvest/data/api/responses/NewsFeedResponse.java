package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.News;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fajarfatur on 3/11/16.
 */
public class NewsFeedResponse extends GenericResponse implements Serializable{

    @SerializedName("data")
    @Expose
    private List<News> data = new ArrayList<News>();

    /**
     * @return The data
     */
    public List<News> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<News> data) {
        this.data = data;
    }


}
