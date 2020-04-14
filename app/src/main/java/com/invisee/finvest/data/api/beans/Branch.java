package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarfatur on 2/23/16.
 */
public class Branch {

    @SerializedName("atBranchId")
    @Expose
    private String atBranchId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("branchCode")
    @Expose
    private String branchCode;
    @SerializedName("branchName")
    @Expose
    private String branchName;

    /**
     * @return The atBranchId
     */
    public String getAtBranchId() {
        return atBranchId;
    }

    /**
     * @param atBranchId The atBranchId
     */
    public void setAtBranchId(String atBranchId) {
        this.atBranchId = atBranchId;
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
     * @return The branchCode
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * @param branchCode The branchCode
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
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

    @Override
    public String toString() {
        return getBranchName();
    }
}
