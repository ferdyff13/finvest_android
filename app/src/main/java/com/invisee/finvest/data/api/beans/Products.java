package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandu.abbiyuarsyah on 17/03/2017.
 */

public class Products {

    @SerializedName("package_type")
    @Expose
    private String packageType;

    @SerializedName("package_list")
    @Expose
    private List<ProductList> packageList = new ArrayList<>();

    @SerializedName("type_code")
    @Expose
    private String typeCode;


    /**
     * @return The packageType
     */
    public String getPackageType() {
        return packageType;
    }

    /**
     * @param packageType The packageType
     */
    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    /**
     * @return The packageList
     */
    public List<ProductList> getPackageList() {
        return packageList;
    }

    /**
     * @param packageList The packageList
     */
    public void setPackageList(List<ProductList> packageList) {
        this.packageList = packageList;
    }

    /**
     * @return The typeCode
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * @param typeCode The typeCode
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }


}
