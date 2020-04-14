package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarfatur on 2/3/16.
 */
public class ActivateUserRequest {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("reset_code")
    @Expose
    private String resetCode;
    @SerializedName("token")
    @Expose
    private String token;

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The resetCode
     */
    public String getResetCode() {
        return resetCode;
    }

    /**
     * @param resetCode The reset_code
     */
    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
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
