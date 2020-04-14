package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 18/01/2017.
 */

public class Data {

    @SerializedName("customer_id")
    @Expose
    private int id;

    @SerializedName("customer_email")
    @Expose
    private String email;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("customer_status")
    @Expose
    private String userStatus;

    @SerializedName("customer_kyc")
    @Expose
    private int kyc;

    @SerializedName("customer_name")
    @Expose
    private String name;

    @SerializedName("customer_image")
    @Expose
    private String image;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKyc() {
        return kyc;
    }

    public void setKyc(int kyc) {
        this.kyc = kyc;
    }


    /**
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
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
     * @return The userStatus
     */
    public String getUserStatus() {
        return userStatus;
    }

    /**
     * @param userStatus The userStatus
     */
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * @return The image
     */

    public String getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    public void setImage(String image) {
        this.image = image;
    }
}
