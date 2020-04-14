package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandu.abbiyuarsyah on 22/03/2017.
 */

public class DtoNetFeeResponse implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("netAmount")
    @Expose
    private Double netAmount;
    @SerializedName("feeAmount")
    @Expose
    private Double feeAmount;
    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("fundPackageId")
    @Expose
    private Integer fundPackageId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("investmentAccount")
    @Expose
    private String investmentAccount;
    @SerializedName("trxType")
    @Expose
    private String transType;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("composition")
    @Expose
    private List<Composition> compositions = new ArrayList<>();

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
    public Double getNetAmount() {
        return netAmount;
    }

    /**
     * @param netAmount The netAmount
     */
    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    /**
     * @return The feeAmount
     */
    public Double getFeeAmount() {
        return feeAmount;
    }

    /**
     * @param feeAmount The feeAmount
     */
    public void setFeeAmount(Double feeAmount) {
        this.feeAmount = feeAmount;
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


    /**
     * @return The investmentAccount
     */
    public String getInvestmentAccount() {
        return investmentAccount;
    }

    /**
     * @param investmentAccount The investmentAccount
     */
    public void setInvestmentAccount(String investmentAccount) {
        this.investmentAccount = investmentAccount;
    }

    /**
     * @return The transType
     */
    public String getTransType() {
        return transType;
    }

    /**
     * @param transType The transType
     */
    public void setTransType(String transType) {
        this.transType = transType;
    }

    /**
     * @return The compositions
     */

    public List<Composition> getCompositions() {
        return compositions;
    }


    /**
     * @param compositions The compositions
     */

    public void setCompositions(List<Composition> compositions) {
        this.compositions = compositions;
    }

    /**
     * @return The composition
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName The productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

}
