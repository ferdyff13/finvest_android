package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.Document;
import com.invisee.finvest.data.api.beans.Kyc;

/**
 * Created by pandu.abbiyuarsyah on 27/02/2017.
 */

public class CustomerDocumentResponse extends GenericResponse {

    @SerializedName("data")
    @Expose
    private Document data;

    /**
     * @return The data
     */
    public Document getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(Document data) {
        this.data = data;
    }

}
