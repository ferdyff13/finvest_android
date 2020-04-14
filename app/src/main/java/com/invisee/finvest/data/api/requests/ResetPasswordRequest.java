package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class ResetPasswordRequest {

    @SerializedName("resetCode")
    @Expose
    private String resetCode;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("confPassword")
    @Expose
    private String confPassword;

    /**
     * @return The resetCode
     */
    public String getResetCode() {
        return resetCode;
    }

    /**
     * @param resetCode The resetCode
     */
    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }

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
     * @return The confPassword
     */
    public String getConfirmPassword() {
        return confPassword;
    }

    /**
     * @param confPassword The question
     */
    public void setConfirmPassword(String confPassword) {
        this.confPassword = confPassword;
    }

}
