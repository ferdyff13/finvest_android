package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pandu.abbiyuarsyah on 31/03/2017.
 */

public class DocumentTransaction implements Serializable {

    @SerializedName("fileName")
    @Expose
    private String fileName;

    @SerializedName("fileKey")
    @Expose
    private String fileKey;


    /**
     * @return The fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName The fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return The fileKey
     */
    public String getFileKey() {
        return fileKey;
    }

    /**
     * @param fileKey The fileKey
     */
    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

}
