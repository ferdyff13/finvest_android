package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pandu.abbiyuarsyah on 24/05/2017.
 */

public class PaymentList implements Serializable{

    @SerializedName("code")
    private String code;

    @SerializedName("value")
    private String value;

    /**
     * @return The code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return The value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value The value
     */
    public void setValue(String value) {
        this.value = value;
    }

}
