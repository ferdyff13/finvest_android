package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fajarfatur on 2/23/16.
 */
public class Completeness implements Serializable{

    @SerializedName("fatca")
    @Expose
    private int fatca;
    @SerializedName("kyc")
    @Expose
    private int kyc;
    @SerializedName("riskProfile")
    @Expose
    private int riskProfile;

    /**
     * @return The fatca
     */
    public int getFatca() {
        return fatca;
    }

    /**
     * @param fatca The fatca
     */
    public void setFatca(int fatca) {
        this.fatca = fatca;
    }

    /**
     * @return The kyc
     */
    public int getKyc() {
        return kyc;
    }

    /**
     * @param kyc The kyc
     */
    public void setKyc(int kyc) {
        this.kyc = kyc;
    }

    /**
     * @return The riskProfile
     */
    public int getRiskProfile() {
        return riskProfile;
    }

    /**
     * @param riskProfile The riskProfile
     */
    public void setRiskProfile(int riskProfile) {
        this.riskProfile = riskProfile;
    }

}
