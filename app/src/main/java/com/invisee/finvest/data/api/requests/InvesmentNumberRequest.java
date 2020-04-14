package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 08/06/2017.
 */

public class InvesmentNumberRequest {

    @SerializedName("fundPackage")
    @Expose
    private String fundPackage;

    @SerializedName("token")
    @Expose
    private String token;

    /**
     * @return The fundPackage
     */
    public String getFundPackage() {
        return fundPackage;
    }

    /**
     * @param fundPackage The fundPackage
     */
    public void setFundPackage(String fundPackage) {
        this.fundPackage = fundPackage;
    }

    /**
     * @return The token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token The token
     */
    public void setToken(String token) {
        this.token = token;
    }


}
