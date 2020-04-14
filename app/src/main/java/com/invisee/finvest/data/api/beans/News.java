package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fajarfatur on 3/11/16.
 */
public class News implements Serializable{

    @SerializedName("newsContent")
    @Expose
    private String newsContent;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("newsTitle")
    @Expose
    private String newsTitle;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("imageLocation")
    @Expose
    private String imageLocation;

    /**
     * @return The newsContent
     */
    public String getNewsContent() {
        return newsContent;
    }

    /**
     * @param newsContent The newsContent
     */
    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    /**
     * @return The createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate The createdDate
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return The newsTitle
     */
    public String getNewsTitle() {
        return newsTitle;
    }

    /**
     * @param newsTitle The newsTitle
     */
    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    /**
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The imageLocation
     */
    public String getImageLocation() {
        return imageLocation;
    }

    /**
     * @param imageLocation The imageLocation
     */
    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

}
