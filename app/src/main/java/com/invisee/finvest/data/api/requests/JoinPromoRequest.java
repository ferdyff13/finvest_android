package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 19/05/2017.
 */

public class JoinPromoRequest {


    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("promo")
    @Expose
    private String promo;

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


    /**
     * @return The promo
     */
    public String getPromo() {
        return promo;
    }

    /**
     * @param promo The promo
     */
    public void setPromo(String promo) {
        this.promo = promo;
    }


}
