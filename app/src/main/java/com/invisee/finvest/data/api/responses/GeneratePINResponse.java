package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.TokenPIN;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan.pradana on 3/14/2017.
 */

public class GeneratePINResponse extends GenericResponse  {

    @SerializedName("pin")
    @Expose
    private String pin;

    /*
    @SerializedName("token")
    @Expose
    private List<TokenPIN> listToken = new ArrayList<>();

    public List<TokenPIN> getListToken() {
        return listToken;
    }

    public void setListToken(List<TokenPIN> listToken) {
        this.listToken = listToken;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
    */
}
