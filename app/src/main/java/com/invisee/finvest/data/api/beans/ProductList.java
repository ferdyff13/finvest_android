package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pandu.abbiyuarsyah on 17/03/2017.
 */

public class ProductList implements Serializable {


    @SerializedName("last_nav_date")
    @Expose
    private String lastNavDate;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("last_nav")
    @Expose
    private Double lastNav;

    @SerializedName("perf_oneyear")
    @Expose
    private Double perfOneYear;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("total_fund")
    @Expose
    private Integer totalFund;

    /**
     * @return The image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return The lastNavDate
     */
    public String getLastNavDate() {
        return lastNavDate;
    }

    /**
     * @param lastNavDate The lastNavDate
     */
    public void setLastNavDate(String lastNavDate) {
        this.lastNavDate = lastNavDate;
    }

    /**
     * @return The lastNav
     */
    public Double getLastNav() {
        return lastNav;
    }

    /**
     * @param lastNav The lastNav
     */
    public void setLastNav(Double lastNav) {
        this.lastNav = lastNav;
    }

    /**
     * @return The lastNav
     */
    public Double getPerfOneYear() {
        return perfOneYear;
    }

    /**
     * @param perfOneYear The perfOneYear
     */

    public void setPerfOneYear(Double perfOneYear) {
        this.perfOneYear = perfOneYear;
    }

    /**
     * @return The lastNav
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */

    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return The lastNav
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id The id
     */

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return The lastNav
     */
    public Integer getTotalFund() {
        return totalFund;
    }

    /**
     * @param totalFund The totalFund
     */

    public void setTotalFund(Integer totalFund) {
        this.totalFund = totalFund;
    }

}
