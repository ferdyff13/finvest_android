package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 14/03/2017.
 */

public class ChangePasswordRequest {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("confPassword")
    @Expose
    private String confPassword;
    @SerializedName("newPassword")
    @Expose
    private String newPassword;
    @SerializedName("oldPassword")
    @Expose
    private String oldPassword;
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
     * @return The confPassword
     */
    public String getConfPassword() {
        return confPassword;
    }

    /**
     * @param confPassword The confPassword
     */
    public void setConfPassword(String confPassword) {
        this.confPassword = confPassword;
    }

    /**
     * @return The newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword The newPassword
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * @return The oldPassword
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * @param oldPassword The oldPassword
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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
