package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.PaymentList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandu.abbiyuarsyah on 24/05/2017.
 */

public class PaymentMethodResponse extends GenericResponse implements Serializable{


    @SerializedName("data")
    @Expose
    private List<PaymentList> data = new ArrayList<PaymentList>();

    /**
     * @return The data
     */
    public List<PaymentList> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<PaymentList> data) {
        this.data = data;
    }

}
