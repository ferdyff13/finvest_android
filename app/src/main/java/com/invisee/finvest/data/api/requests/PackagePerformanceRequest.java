package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarfatur on 2/3/16.
 */
public class PackagePerformanceRequest {

    @SerializedName("packageId")
    @Expose
    private Long packageId;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("range")
    @Expose
    private String range;

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}
