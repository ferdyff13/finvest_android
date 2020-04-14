package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.Data;
import com.invisee.finvest.data.api.beans.Kyc;
import com.invisee.finvest.data.api.beans.User;
import com.invisee.finvest.data.api.beans.UserInfo;

import java.io.Serializable;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class LoginResponse extends GenericResponse implements Serializable {

    @SerializedName("data")
    @Expose
    private Data data;

    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("kyc")
    @Expose
    private Kyc kyc;

    /**
     * @return The user
     */
    public Data getData() {
        return data;
    }

    /**
     * @param data The user
     */

    public void setData(Data data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;

    }

    public Kyc getKyc() {
        return kyc;
    }

    public void setKyc(Kyc kyc) {
        this.kyc = kyc;

    }


}
