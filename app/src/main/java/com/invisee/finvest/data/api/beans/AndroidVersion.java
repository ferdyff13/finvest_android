package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 04/05/2017.
 */

public class AndroidVersion {

    @SerializedName("playstore")
    @Expose
    private Double playstore;

    @SerializedName("device_name")
    @Expose
    private String deviceName;

    @SerializedName("device")
    @Expose
    private Double device;

    @SerializedName("core")
    @Expose
    private Double core;


    /**
     * @return The playstore
     */
    public Double getPlaystore() {
        return playstore;
    }

    /**
     * @param playstore The playstore
     */
    public void setPlaystore(Double playstore) {
        this.playstore = playstore;
    }


    /**
     * @return The deviceName
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * @param deviceName The deviceName
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    /**
     * @return The device
     */
    public Double getDevice() {
        return device;
    }

    /**
     * @param device The device
     */
    public void setDevice(Double device) {
        this.device = device;
    }

    /**
     * @return The core
     */
    public Double getCore() {
        return core;
    }

    /**
     * @core device The core
     */
    public void setCore(Double core) {
        this.core = core;
    }

}
