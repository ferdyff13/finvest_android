package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 06/06/2017.
 */

public class RetryPayProRequest {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("confirm")
    @Expose
    private boolean confirm;

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
     * @return The confirm
     */
    public boolean getConfirm() {
        return confirm;
    }

    /**
     * @param confirm The confirm
     */
    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

}
