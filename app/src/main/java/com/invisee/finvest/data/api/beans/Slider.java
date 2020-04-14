package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 15/11/2017.
 */

public class Slider {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("urlStatus")
    @Expose
    private Boolean urlStatus;
    @SerializedName("youtube")
    @Expose
    private String youtube;
    @SerializedName("newTab")
    @Expose
    private Boolean newTab;
    @SerializedName("bgImage")
    @Expose
    private String bgImage;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("mainImage")
    @Expose
    private String mainImage;
    @SerializedName("androidImage")
    @Expose
    private String androidImage;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getUrlStatus() {
        return urlStatus;
    }

    public void setUrlStatus(Boolean urlStatus) {
        this.urlStatus = urlStatus;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public Boolean getNewTab() {
        return newTab;
    }

    public void setNewTab(Boolean newTab) {
        this.newTab = newTab;
    }

    public String getBgImage() {
        return bgImage;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getAndroidImage(){
        return androidImage;
    }

    public void  setAndroidImage(String androidImage) {
        this.androidImage = androidImage;
    }
}
