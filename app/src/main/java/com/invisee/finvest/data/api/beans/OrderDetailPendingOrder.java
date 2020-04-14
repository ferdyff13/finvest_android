package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandu.abbiyuarsyah on 30/03/2017.
 */

public class OrderDetailPendingOrder {

    @SerializedName("composition")
    @Expose
    private Integer composition;

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
    private String invesmentNumber;

    @SerializedName("productDto")
    @Expose
    private List<ProductDto> productDto = new ArrayList<>();

    @SerializedName("total")
    @Expose
    private Double total;

    @SerializedName("transactionAmount")
    @Expose
    private Double transactionAmount;

    @SerializedName("transactionType")
    @Expose
    private String transactionType;

    @SerializedName("trxStatus")
    @Expose
    private String trxStatus;

    /**
     * @return The composition
     */
    public Integer getComposition() {
        return composition;
    }

    /**
     * @param composition The composition
     */
    public void setComposition(Integer composition) {
        this.composition = composition;
    }

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
    public void getFundPackageName(String fundPackageName) {
        this.fundPackageName = fundPackageName;
    }

    /**
     * @return The invesmentNumber
     */
    public String getInvesmentNumber() {
        return invesmentNumber;
    }

    /**
     * @param invesmentNumber The invesmentNumber
     */
    public void setInvesmentNumber(String invesmentNumber) {
        this.invesmentNumber = invesmentNumber;
    }

    /**
     * @return The productDto
     */
    public List<ProductDto> getProductDto() {
        return productDto;
    }

    /**
     * @param productDto The productDto
     */
    public void setProductDto(List<ProductDto> productDto) {
        this.productDto = productDto;
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
     * @return The trxStatus
     */
    public String getTrxStatus() {
        return trxStatus;
    }

    /**
     * @param trxStatus The trxStatus
     */
    public void setTrxStatus(String trxStatus) {
        this.trxStatus = trxStatus;
    }




}
