package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pandu.abbiyuarsyah on 22/03/2017.
 */

public class SettlementAccounts implements Serializable {

    @SerializedName("accountName")
    @Expose
    private String accountName;

    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;

    /**
     *
     * @return
     * The accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     *
     * @param accountName
     * The accountName
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     *
     * @return
     * The accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     *
     * @param accountNumber
     * The accountNumber
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


}
