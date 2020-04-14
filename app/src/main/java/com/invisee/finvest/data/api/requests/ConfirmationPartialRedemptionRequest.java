package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarfatur on 3/16/16.
 */
public class ConfirmationPartialRedemptionRequest {

    @SerializedName("confirm")
    @Expose
    private String confirm;
    @SerializedName("investId")
    @Expose
    private String investId;
    @SerializedName("percentage")
    @Expose
    private String percentage;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("index")
    @Expose
    private String index;

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getInvestId() {
        return investId;
    }

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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

}
