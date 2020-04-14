package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarfatur on 2/18/16.
 */
public class State {

    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("stateCode")
    @Expose
    private String stateCode;

    /**
     * @return The stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * @param stateName The stateName
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
     * @return The stateCode
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     * @param stateCode The stateCode
     */
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    @Override
    public String toString() {
        return stateName;
    }
}
