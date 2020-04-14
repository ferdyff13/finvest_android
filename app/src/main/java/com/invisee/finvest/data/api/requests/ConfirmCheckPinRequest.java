package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.DtoNetFee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandu.abbiyuarsyah on 24/03/2017.
 */

public class ConfirmCheckPinRequest {

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("amount")
    @Expose
    private Double amount;

    @SerializedName("confirm")
    @Expose
    private String confirm;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("pin")
    @Expose
    private String pin;

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
     * @return The confirm
     */
    public String getConfirm() {
        return confirm;
    }

    /**
     * @param confirm The confirm
     */
    public void setConfirm(String confirm) {
        this.confirm = confirm;
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
