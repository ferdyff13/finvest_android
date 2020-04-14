package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.Referral;

/**
 * Created by pandu.abbiyuarsyah on 12/02/2018.
 */

public class ReferralResponse extends GenericResponse {

    @SerializedName("data")
    @Expose
    private Referral data;



    public Referral getData() {
        return data;
    }

    public void setData(Referral data) {
        this.data = data;
    }

}
