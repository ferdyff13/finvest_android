package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.DtoNetFee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fajarfatur on 3/16/16.
 */
public class CheckPinRequest {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("index")
    @Expose
    private String index;
    @SerializedName("dtoNetFees")
    @Expose
    private List<DtoNetFee> dtoNetFees = new ArrayList<>();

    @SerializedName("customer_payment_method")
    @Expose
    private String customerNumber;

    /**
     * @return The token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return The pin
     */
    public String getPin() {
        return pin;
    }

    /**
     * @param pin The pin
     */
    public void setPin(String pin) {
        this.pin = pin;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @param amount The amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return The index
     */
    public String getIndex() {
        return index;
    }

    /**
     * @param index The index
     */
    public void setIndex(String index) {
        this.index = index;
    }

    /**
     * @return The dtoNetFees
     */
    public List<DtoNetFee> getDtoNetFees() {
        return dtoNetFees;
    }

    /**
     * @param dtoNetFees The dtoNetFees
     */
    public void setDtoNetFees(List<DtoNetFee> dtoNetFees) {
        this.dtoNetFees = dtoNetFees;
    }


    /**
     * @return The customerNumber
     */
    public String getCustomerNumber() {
        return customerNumber;
    }

    /**
     * @param customerNumber The customerNumber
     */
    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }



}
