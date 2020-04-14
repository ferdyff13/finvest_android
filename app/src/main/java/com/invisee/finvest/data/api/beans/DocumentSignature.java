package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 27/02/2017.
 */

public class DocumentSignature {

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
