package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.DocumentSettlement;
import com.invisee.finvest.data.api.beans.DocumentSignature;

/**
 * Created by pandu.abbiyuarsyah on 27/02/2017.
 */

public class CustomerDocumentSettlementResponse {

    @SerializedName("data")
    @Expose
    private DocumentSettlement data;

    /**
     * @return The data
     */
    public DocumentSettlement getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(DocumentSettlement data) {
        this.data = data;
    }

}
