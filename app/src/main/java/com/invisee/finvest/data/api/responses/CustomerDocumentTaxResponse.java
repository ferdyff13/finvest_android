package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.DocumentSignature;
import com.invisee.finvest.data.api.beans.DocumentTax;

/**
 * Created by pandu.abbiyuarsyah on 27/02/2017.
 */

public class CustomerDocumentTaxResponse {

    @SerializedName("data")
    @Expose
    private DocumentTax data;

    /**
     * @return The data
     */
    public DocumentTax getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(DocumentTax data) {
        this.data = data;
    }

}
