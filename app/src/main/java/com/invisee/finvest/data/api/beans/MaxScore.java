package com.invisee.finvest.data.api.beans;

import android.renderscript.Long2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 03/07/2017.
 */

public class MaxScore {

    @SerializedName("maxScoreFundPackage")
    @Expose
    private Integer maxScoreFundPackage;
    @SerializedName("maxScoreKyc")
    @Expose
    private String maxScoreKyc;

    public Integer getMaxScoreFundPackage() {
        return maxScoreFundPackage;
    }

    public void setMaxScoreFundPackage(Integer maxScoreFundPackage) {
        this.maxScoreFundPackage = maxScoreFundPackage;
    }

    public String getMaxScoreKyc() {
        return maxScoreKyc;
    }

    public void setMaxScoreKyc(String maxScoreKyc) {
        this.maxScoreKyc = maxScoreKyc;
    }

}
