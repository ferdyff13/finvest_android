package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ivan.pradana on 3/14/2017.
 */

public class TokenPIN implements Serializable {

    @SerializedName("token")
    private String token;
    @SerializedName("controller")
    private String controller;
    @SerializedName("format")
    private String format;
    @SerializedName("action")
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
