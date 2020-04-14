package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by glenrynaldi on 2/22/16.
 */
public class FundAllocation implements Serializable {

    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("compositition")
    @Expose
    private Double compositition;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("prospectus_key")
    @Expose
    private Object prospectusKey;
    @SerializedName("fund_fact_sheet_key")
    @Expose
    private Object fundFactSheetKey;
    @SerializedName("colors")
    @Expose
    private String colors;

    /**
     * @return The productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName The product_name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return The productType
     */
    public String getProductType() {
        return productType;
    }

    /**
     * @param productType The product_type
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * @return The compositition
     */
    public Double getCompositition() {
        return compositition;
    }

    /**
     * @param compositition The compositition
     */
    public void setCompositition(Double compositition) {
        this.compositition = compositition;
    }

    /**
     * @return The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName The full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return The prospectusKey
     */
    public Object getProspectusKey() {
        return prospectusKey;
    }

    /**
     * @param prospectusKey The prospectus_key
     */
    public void setProspectusKey(Object prospectusKey) {
        this.prospectusKey = prospectusKey;
    }

    /**
     * @return The fundFactSheetKey
     */
    public Object getFundFactSheetKey() {
        return fundFactSheetKey;
    }

    /**
     * @param fundFactSheetKey The fund_fact_sheet_key
     */
    public void setFundFactSheetKey(Object fundFactSheetKey) {
        this.fundFactSheetKey = fundFactSheetKey;
    }

    /**
     * @return The colors
     */
    public String getColors() {
        return colors;
    }

    /**
     * @param colors The colors
     */
    public void setColors(String colors) {
        this.colors = colors;
    }

}
