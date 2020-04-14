package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.WalletHistory;

import java.util.Map;

/**
 * Created by glenrynaldi on 4/11/16.
 */
public class WalletHistoryResponse extends GenericResponse {

    @SerializedName("data")
    @Expose
    private Map<String, WalletHistory> data;

    /**
     * @return The data
     */
    public Map<String, WalletHistory> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(Map<String, WalletHistory> data) {
        this.data = data;
    }

}
