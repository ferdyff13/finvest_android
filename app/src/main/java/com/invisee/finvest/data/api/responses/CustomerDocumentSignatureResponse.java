package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.Document;
import com.invisee.finvest.data.api.beans.DocumentSignature;

/**
 * Created by pandu.abbiyuarsyah on 27/02/2017.
 */

public class CustomerDocumentSignatureResponse extends GenericResponse{
    @SerializedName("data")
    @Expose
    private DocumentSignature data;

    /**
     * @return The data
     */
    public DocumentSignature getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(DocumentSignature data) {
        this.data = data;
    }

}
