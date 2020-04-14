package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 06/06/2017.
 */

public class PayProAccount {

    @SerializedName("total")
    @Expose
    private String total;

    @SerializedName("orderNo")
    @Expose
    private String orderNo;

    @SerializedName("dompetku")
    @Expose
    private String dompetku;

    /**
     * @return The total
     */
    public String getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * @return The orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo The orderNo
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return The orderNo
     */
    public String getDompetku() {
        return dompetku;
    }

    /**
     * @param dompetku The dompetku
     */
    public void setDompetku(String dompetku) {
        this.dompetku = dompetku;
    }



}
