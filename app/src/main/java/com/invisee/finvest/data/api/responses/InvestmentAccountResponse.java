package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.InvestmentAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by glenrynaldi on 4/14/16.
 */
public class InvestmentAccountResponse extends GenericResponse {

    @SerializedName("data")
    @Expose
    private List<InvestmentAccount> data = new ArrayList<>();

    /**
     * @return The data
     */
    public List<InvestmentAccount> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<InvestmentAccount> data) {
        this.data = data;
    }

}
