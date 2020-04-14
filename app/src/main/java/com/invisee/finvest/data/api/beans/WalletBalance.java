package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by glenrynaldi on 4/11/16.
 */
public class WalletBalance implements Serializable {

    @SerializedName("balance")
    @Expose
    private Double balance;
    @SerializedName("failure")
    @Expose
    private Boolean failure;
    @SerializedName("securityKey")
    @Expose
    private String securityKey;
    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("point")
    @Expose
    private Integer point;
    @SerializedName("pocket")
    @Expose
    private String pocket;

    /**
     * @return The balance
     */
    public Double getBalance() {
        return balance;
    }

    /**
     * @param balance The balance
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    /**
     * @return The failure
     */
    public Boolean getFailure() {
        return failure;
    }

    /**
     * @param failure The failure
     */
    public void setFailure(Boolean failure) {
        this.failure = failure;
    }

    /**
     * @return The securityKey
     */
    public String getSecurityKey() {
        return securityKey;
    }

    /**
     * @param securityKey The securityKey
     */
    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    /**
     * @return The account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account The account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return The point
     */
    public Integer getPoint() {
        return point;
    }

    /**
     * @param point The point
     */
    public void setPoint(Integer point) {
        this.point = point;
    }

    /**
     * @return The pocket
     */
    public String getPocket() {
        return pocket;
    }

    /**
     * @param pocket The pocket
     */
    public void setPocket(String pocket) {
        this.pocket = pocket;
    }

}
