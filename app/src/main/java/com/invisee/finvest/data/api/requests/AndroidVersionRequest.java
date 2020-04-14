package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 04/05/2017.
 */

public class AndroidVersionRequest {

    @SerializedName("id_device")
    @Expose
    private int idDevice;

    /**
     * @return The idDevice
     */
    public int getIdDevice() {
        return idDevice;
    }

    /**
     * @param idDevice The idDevice
     */
    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
    }
}
