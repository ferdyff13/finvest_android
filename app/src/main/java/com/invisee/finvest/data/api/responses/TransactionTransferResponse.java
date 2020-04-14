package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.TransactionTransfer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class TransactionTransferResponse extends GenericResponse implements Serializable {

    @SerializedName("data")
    @Expose
    private List<TransactionTransfer> data = new ArrayList<TransactionTransfer>();

    /**
     * @return The data
     */
    public List<TransactionTransfer> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<TransactionTransfer> data) {
        this.data = data;
    }
}
