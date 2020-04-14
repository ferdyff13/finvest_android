package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.DtoNetFee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fajarfatur on 3/16/16.
 */
public class TransactionTransferRequest {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("total")
    @Expose
    private BigDecimal total;
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
    @SerializedName("detail")
    @Expose
    private List<DtoNetFee> detail = new ArrayList<>();

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
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total The amount
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
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
    public List<DtoNetFee> getDetail() {
        return detail;
    }

    /**
     * @param detail The dtoNetFees
     */
    public void setDetail(List<DtoNetFee> detail) {
        this.detail = detail;
    }


}
