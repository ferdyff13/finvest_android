package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by glenrynaldi on 3/17/16.
 */
public class DtoNetFee implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("netAmount")
    @Expose
    private BigDecimal netAmount;
    @SerializedName("feeAmount")
    @Expose
    private BigDecimal feeAmount;
    @SerializedName("total")
    @Expose
    private BigDecimal total;
    @SerializedName("fundPackageId")
    @Expose
    private Integer fundPackageId;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The netAmount
     */
    public BigDecimal getNetAmount() {
        return netAmount;
    }

    /**
     * @param netAmount The netAmount
     */
    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    /**
     * @return The feeAmount
     */
    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    /**
     * @param feeAmount The feeAmount
     */
    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    /**
     * @return The total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return The fundPackageId
     */
    public Integer getFundPackageId() {
        return fundPackageId;
    }

    /**
     * @param fundPackageId The fundPackageId
     */
    public void setFundPackageId(Integer fundPackageId) {
        this.fundPackageId = fundPackageId;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
