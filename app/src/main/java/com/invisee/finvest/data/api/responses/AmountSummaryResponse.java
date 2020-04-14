package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.PayProAccount;
import com.invisee.finvest.data.api.beans.Summary;
import com.invisee.finvest.data.api.beans.TransactionTransfer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandu.abbiyuarsyah on 24/03/2017.
 */

public class AmountSummaryResponse extends GenericResponse implements Serializable {

    @SerializedName("orderNumber")
    @Expose
    private String orderNumber;

    @SerializedName("orderAmount")
    @Expose
    private Double orderAmount;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("summary")
    @Expose
    private List<Summary> summary = new ArrayList<Summary>();

    @SerializedName("sum")
    @Expose
    private String sum;

    @SerializedName("data")
    @Expose
    private PayProAccount data;


    /**
     * @return The orderNumber
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * @param orderNumber The orderNumber
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * @return The orderAmount
     */
    public Double getOrderAmount() {
        return orderAmount;
    }

    /**
     * @param orderAmount The orderAmount
     */
    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     * @return The time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return The summary
     */
    public List<Summary> getSummary() {
        return summary;
    }

    /**
     * @param summary The summary
     */
    public void setSummary(List<Summary> summary) {
        this.summary = summary;
    }

    /**
     * @return The sum
     */
    public String getSum() {
        return sum;
    }

    /**
     * @param sum The sum
     */
    public void setSum(String sum) {
        this.sum = sum;
    }

    /**
     * @return The data
     */
    public PayProAccount getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(PayProAccount data) {
        this.data = data;
    }


}
