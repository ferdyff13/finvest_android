package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarfatur on 2/23/16.
 */
public class Bank {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("imageKey")
    @Expose
    private String imageKey;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("swiftCode")
    @Expose
    private String swiftCode;

    /**
     * @return The code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The imageKey
     */
    public String getImageKey() {
        return imageKey;
    }

    /**
     * @param imageKey The imageKey
     */
    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The swiftCode
     */
    public String getSwiftCode() {
        return swiftCode;
    }

    /**
     * @param swiftCode The swiftCode
     */
    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    @Override
    public String toString() {
        return getName();
    }
}
