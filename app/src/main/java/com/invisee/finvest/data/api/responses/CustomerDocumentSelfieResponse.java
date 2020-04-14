package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.DocumentSelfie;

/**
 * Created by asep.surahman on 15/05/2018.
 */

public class CustomerDocumentSelfieResponse extends GenericResponse {

    @SerializedName("data")
    @Expose
    private DocumentSelfie data;

    public DocumentSelfie getData() {
        return data;
    }

    public void setData(DocumentSelfie data) {
        this.data = data;
    }
}
