package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.WalletBalance;

import java.io.Serializable;

/**
 * Created by glenrynaldi on 4/11/16.
 */
public class WalletBalanceResponse extends GenericResponse implements Serializable {

    @SerializedName("data")
    @Expose
    private WalletBalance data;

    /**
     * @return The data
     */
    public WalletBalance getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(WalletBalance data) {
        this.data = data;
    }

}
