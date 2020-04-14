package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarfatur on 3/16/16.
 */
public class CreatePartialRedemptionRequest {

    @SerializedName("index")
    @Expose
    private String index;
    @SerializedName("investId")
    @Expose
    private String investId;
    @SerializedName("percentage")
    @Expose
    private String percentage;
    @SerializedName("pin")
    @Expose
    private String pin;

    /**
     * @return The index
     */
    public String getIndex() {
        return index;
    }

    /**
     * @param index The index
     */
    public void setIndex(String index) {
        this.index = index;
    }

    /**
     * @return The investId
     */
    public String getInvestId() {
        return investId;
    }

    /**
     * @param investId The investId
     */
    public void setInvestId(String investId) {
        this.investId = investId;
    }

    /**
     * @return The percentage
     */
    public String getPercentage() {
        return percentage;
    }

    /**
     * @param percentage The pin
     */
    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    /**
     * @return The pin
     */
    public String getPin() {
        return pin;
    }

    /**
     * @param pin The pin
     */
    public void setPin(String pin) {
        this.pin = pin;
    }


}
