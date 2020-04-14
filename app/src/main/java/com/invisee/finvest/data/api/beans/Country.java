package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarfatur on 2/18/16.
 */
public class Country {

    @SerializedName("numericCode")
    @Expose
    private String numericCode;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("atCountryCode")
    @Expose
    private String atCountryCode;
    @SerializedName("countryName")
    @Expose
    private String countryName;
    @SerializedName("alpha3Code")
    @Expose
    private String alpha3Code;

    /**
     * @return The numericCode
     */
    public String getNumericCode() {
        return numericCode;
    }

    /**
     * @param numericCode The numericCode
     */
    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
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
     * @return The atCountryCode
     */
    public String getAtCountryCode() {
        return atCountryCode;
    }

    /**
     * @param atCountryCode The atCountryCode
     */
    public void setAtCountryCode(String atCountryCode) {
        this.atCountryCode = atCountryCode;
    }

    /**
     * @return The countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName The countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return The alpha3Code
     */
    public String getAlpha3Code() {
        return alpha3Code;
    }

    /**
     * @param alpha3Code The alpha3Code
     */
    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    @Override
    public String toString() {
        return countryName;
    }
}
