package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandu.abbiyuarsyah on 30/03/2017.
 */

public class PendingOrderDetail {

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("total")
    @Expose
    private Double total;

    @SerializedName("settlement_date")
    @Expose
    private String settlementDate;

    @SerializedName("price_date")
    @Expose
    private String priceDate;

    @SerializedName("orderDetail")
    @Expose
    private List<OrderDetailPendingOrder> orderDetail = new ArrayList<>();

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("paymentType")
    @Expose
    private String paymentType;



    /**
     * @return The date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    public void setDate(String date) {
        this.date = date;
    }


    /**
     * @return The total
     */
    public Double getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * @return The settlementDate
     */
    public String getSettlementDate() {
        return settlementDate;
    }

    /**
     * @param settlementDate The settlementDate
     */
    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    /**
     * @return The priceDate
     */
    public String getPriceDate() {
        return priceDate;
    }

    /**
     * @param priceDate The priceDate
     */
    public void setPriceDate(String priceDate) {
        this.priceDate = priceDate;
    }

    /**
     * @return The orderDetail
     */
    public List<OrderDetailPendingOrder> getOrderDetail() {
        return orderDetail;
    }

    /**
     * @param orderDetail The orderDetail
     */
    public void setOrderDetail(List<OrderDetailPendingOrder> orderDetail) {
        this.orderDetail = orderDetail;
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
     * @return The paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * @param paymentType The paymentType
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }


}
