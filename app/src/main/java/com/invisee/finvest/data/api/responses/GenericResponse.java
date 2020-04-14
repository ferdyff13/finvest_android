package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarfatur on 2/1/16.
 */
public class GenericResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("info")
    @Expose
    private String info;

    /**
     * @return The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return The info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info The info
     */
    public void setInfo(String info) {
        this.info = info;
    }

}

