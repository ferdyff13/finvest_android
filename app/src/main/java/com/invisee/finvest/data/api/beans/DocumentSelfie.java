package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by asep.surahman on 15/05/2018.
 */

public class DocumentSelfie {

    @SerializedName("fileName")
    @Expose
    private String fileName;

    @SerializedName("fileKey")
    @Expose
    private String fileKey;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

}
