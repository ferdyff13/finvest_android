package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestFinPay implements Serializable {

    @SerializedName("add_info1")
    @Expose
    private String add_info1;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("cust_email")
    @Expose
    private String cust_email;
    @SerializedName("cust_id")
    @Expose
    private String cust_id;
    @SerializedName("cust_msisdn")
    @Expose
    private String cust_msisdn;
    @SerializedName("cust_name")
    @Expose
    private String cust_name;
    @SerializedName("invoice")
    @Expose
    private String invoice;
    @SerializedName("merchant_id")
    @Expose
    private String merchant_id;
    @SerializedName("return_url")
    @Expose
    private String return_url;
    @SerializedName("sof_id")
    @Expose
    private String sof_id;
    @SerializedName("sof_type")
    @Expose
    private String sof_type;
    @SerializedName("timeout")
    @Expose
    private String timeout;
    @SerializedName("trans_date")
    @Expose
    private String trans_date;
    @SerializedName("mer_signature")
    @Expose
    private String mer_signature;

}
