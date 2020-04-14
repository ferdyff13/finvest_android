package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 27/05/18.
 */

public class AnswerOption {


    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("answerName")
    @Expose
    private String answerName;

    @SerializedName("answerText")
    @Expose
    private String answerText;

    @SerializedName("atAnswerId")
    @Expose
    private String atAnswerId;

    @SerializedName("score")
    @Expose
    private Integer score;

    @SerializedName("seq")
    @Expose
    private Integer seq;


    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
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
    public Integer getScore() {
        return score;
    }

    /**
     * @param score The score
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * @return The seq
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq The seq
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }



}
