package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarfatur on 2/25/16.
 */
public class RiskProfileResponse extends GenericResponse {

    @SerializedName("riskProfile")
    @Expose
    private String riskProfile;

    /**
     * @return The riskProfile
     */
    public String getRiskProfile() {
        return riskProfile;
    }

    /**
     * @param riskProfile The riskProfile
     */
    public void setRiskProfile(String riskProfile) {
        this.riskProfile = riskProfile;
    }

}
