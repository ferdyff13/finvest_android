package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.ProductDetail;

/**
 * Created by pandu.abbiyuarsyah on 20/03/2017.
 */

public class ProductDetailResponse {

    @SerializedName("code")
    private Integer code;

    @SerializedName("data")
    private ProductDetail data;

    /**
     * @return The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return The data
     */
    public ProductDetail getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(ProductDetail data) {
        this.data = data;
    }

}
