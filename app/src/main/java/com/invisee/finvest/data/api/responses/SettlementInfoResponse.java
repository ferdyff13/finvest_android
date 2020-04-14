package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fajarfatur on 2/23/16.
 */
public class SettlementInfoResponse extends GenericResponse implements Serializable{

    @SerializedName("settlementAccountNo")
    @Expose
    private String settlementAccountNo;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("branchID")
    @Expose
    private Integer branchID;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("bankCode")
    @Expose
    private String bankCode;
    @SerializedName("settlementAccountName")
    @Expose
    private String settlementAccountName;
    @SerializedName("branchName")
    @Expose
    private String branchName;

    /**
     * @return The settlementAccountNo
     */
    public String getSettlementAccountNo() {
        return settlementAccountNo;
    }

    /**
     * @param settlementAccountNo The settlementAccountNo
     */
    public void setSettlementAccountNo(String settlementAccountNo) {
        this.settlementAccountNo = settlementAccountNo;
    }

    /**
     * @return The bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankName The bankName
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * @return The branchID
     */
    public Integer getBranchID() {
        return branchID;
    }

    /**
     * @param branchID The branchID
     */
    public void setBranchID(Integer branchID) {
        this.branchID = branchID;
    }

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
     * @return The bankCode
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * @param bankCode The bankCode
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * @return The settlementAccountName
     */
    public String getSettlementAccountName() {
        return settlementAccountName;
    }

    /**
     * @param settlementAccountName The settlementAccountName
     */
    public void setSettlementAccountName(String settlementAccountName) {
        this.settlementAccountName = settlementAccountName;
    }

    /**
     * @return The branchName
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * @param branchName The branchName
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

}
