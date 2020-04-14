package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 30/05/2017.
 */

public class PayPro {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("account")
    @Expose
    private String account;

    /**
     * @return The code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(String code) {
        this.code = code;
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


}
