package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandu.abbiyuarsyah on 24/03/2017.
 */

public class Summary implements Serializable {

    @SerializedName("fee")
    @Expose
    private Double fee;

    @SerializedName("fundPackageId")
    @Expose
    private Integer fundPackageId;

    @SerializedName("fundPackageName")
    @Expose
    private String fundPackageName;

    @SerializedName("investmentNumber")
    @Expose
    private String investmentNumber;

    @SerializedName("productDto")
    @Expose
    private List<ProductDto> productDto = new ArrayList<ProductDto>();

    @SerializedName("total")
    @Expose
    private BigDecimal total;

    @SerializedName("transactionAmount")
    @Expose
    private BigDecimal transactionAmount;

    @SerializedName("transactionType")
    @Expose
    private String transactionType;

    /**
     * @return The fee
     */
    public Double getFee() {
        return fee;
    }

    /**
     * @param fee The fee
     */
    public void setFee(Double fee) {
        this.fee = fee;
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
     * @return The fundPackageName
     */

    public String getFundPackageName() {
        return fundPackageName;
    }

    /**
     * @param fundPackageName The fundPackageName
     */
    public void setFundPackageName(String fundPackageName) {
        this.fundPackageName = fundPackageName;
    }

    /**
     * @return The investmentNumber
     */
    public String getInvestmentNumber() {
        return investmentNumber;
    }

    /**
     * @param investmentNumber The investmentNumber
     */
    public void setInvestmentNumber(String investmentNumber) {
        this.investmentNumber = investmentNumber;
    }

    /**
     * @return The productDto
     */
    public List<ProductDto> getSummary() {
        return productDto;
    }

    /**
     * @param productDto The productDto
     */
    public void setSummary(List<ProductDto> productDto) {
        this.productDto = productDto;
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
     * @return The transactionAmount
     */
    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * @param transactionAmount The transactionAmount
     */
    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     * @return The transactionType
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * @param transactionType The transactionType
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

}
