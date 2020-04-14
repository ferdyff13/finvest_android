package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarfatur on 2/18/16.
 */
public class City {

    @SerializedName("cityCode")
    @Expose
    private String cityCode;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("atCityId")
    @Expose
    private String atCityId;
    @SerializedName("cityName")
    @Expose
    private String cityName;

    /**
     * @return The cityCode
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * @param cityCode The cityCode
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
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
     * @return The atCityId
     */
    public String getAtCityId() {
        return atCityId;
    }

    /**
     * @param atCityId The atCityId
     */
    public void setAtCityId(String atCityId) {
        this.atCityId = atCityId;
    }

    /**
     * @return The cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName The cityName
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return cityName;
    }
}
