package com.invisee.finvest.data.api.beans;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by glenrynaldi on 7/15/16.
 */
public class PepCountry {

    @SerializedName("id")
    @Expose
    private Integer id;

    public PepCountry(int id) {
        this.id = id;
    }


    /**
     * @return The id
     */
    @Nullable
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

}
