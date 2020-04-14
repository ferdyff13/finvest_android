package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.AndroidVersion;


/**
 * Created by pandu.abbiyuarsyah on 04/05/2017.
 */

public class AndroidVersionResponse extends GenericResponse {

    @SerializedName("data")
    @Expose
    private AndroidVersion data;

    /**
     * @return The data
     */
    public AndroidVersion getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(AndroidVersion data) {
        this.data = data;
    }

}
