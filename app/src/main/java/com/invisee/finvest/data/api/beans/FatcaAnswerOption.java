package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarfatur on 2/22/16.
 */
public class FatcaAnswerOption {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("forcingCategory")
    @Expose
    private String forcingCategory;
    @SerializedName("seq")
    @Expose
    private int seq;
    @SerializedName("answerText")
    @Expose
    private String answerText;
    @SerializedName("atAnswerId")
    @Expose
    private String atAnswerId;
    @SerializedName("score")
    @Expose
    private int score;
    @SerializedName("answerName")
    @Expose
    private String answerName;

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
     * @return The forcingCategory
     */
    public String getForcingCategory() {
        return forcingCategory;
    }

    /**
     * @param forcingCategory The forcingCategory
     */
    public void setForcingCategory(String forcingCategory) {
        this.forcingCategory = forcingCategory;
    }

    /**
     * @return The seq
     */
    public int getSeq() {
        return seq;
    }

    /**
     * @param seq The seq
     */
    public void setSeq(int seq) {
        this.seq = seq;
    }

    /**
     * @return The answerText
     */
    public String getAnswerText() {
        return answerText;
    }

    /**
     * @param answerText The answerText
     */
    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    /**
     * @return The atAnswerId
     */
    public String getAtAnswerId() {
        return atAnswerId;
    }

    /**
     * @param atAnswerId The atAnswerId
     */
    public void setAtAnswerId(String atAnswerId) {
        this.atAnswerId = atAnswerId;
    }

    /**
     * @return The score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score The score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return The answerName
     */
    public String getAnswerName() {
        return answerName;
    }

    /**
     * @param answerName The answerName
     */
    public void setAnswerName(String answerName) {
        this.answerName = answerName;
    }

    @Override
    public String toString() {
        return getAnswerText();
    }
}

