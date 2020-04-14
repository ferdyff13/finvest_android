package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by pandu.abbiyuarsyah on 18/05/2017.
 */

public class PromoResponse implements Serializable{

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("effective_start")
    @Expose
    private String effectiveStart;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("effective_end")
    @Expose
    private String effectiveEnd;

    @SerializedName("joined")
    @Expose
    private Integer joined;

    @SerializedName("quota")
    @Expose
    private Integer quota;

    @SerializedName("introtext")
    @Expose
    private String introtext;

    @SerializedName("title")
    @Expose
    private String title;

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
     * @return The effectiveStart
     */
    public String getEffectiveStart() {
        return effectiveStart;
    }

    /**
     * @param effectiveStart The effectiveStart
     */
    public void setEffectiveStart(String effectiveStart) {
        this.effectiveStart = effectiveStart;
    }

    /**
     * @return The code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return The effectiveEnd
     */
    public String getEffectiveEnd() {
        return effectiveEnd;
    }

    /**
     * @param effectiveEnd The effectiveEnd
     */
    public void setEffectiveEnd(String effectiveEnd) {
        this.effectiveEnd = effectiveEnd;
    }

    /**
     * @return The joined
     */
    public Integer getJoined() {
        return joined;
    }

    /**
     * @param joined The joined
     */
    public void setJoined(Integer joined) {
        this.joined = joined;
    }

    /**
     * @return The quota
     */
    public Integer getQuota() {
        return quota;
    }

    /**
     * @param quota The quota
     */
    public void setQuota(Integer quota) {
        this.joined = quota;
    }

    /**
     * @return The introtext
     */
    public String getIntrotext() {
        return introtext;
    }

    /**
     * @param introtext The introtext
     */
    public void setIntrotext(String introtext) {
        this.introtext = introtext;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }



}
