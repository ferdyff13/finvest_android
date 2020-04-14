package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.DocumentTax;
import com.invisee.finvest.data.api.beans.DocumentTransaction;
import com.invisee.finvest.data.api.beans.InvestmentAccount;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandu.abbiyuarsyah on 31/03/2017.
 */

public class DocTransactionResponse extends GenericResponse implements Serializable {

    @SerializedName("data")
    @Expose

    private List<DocumentTransaction> data = new ArrayList<>();

    /**
     * @return The data
     */
    public List<DocumentTransaction> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<DocumentTransaction> data) {
        this.data = data;
    }
}
