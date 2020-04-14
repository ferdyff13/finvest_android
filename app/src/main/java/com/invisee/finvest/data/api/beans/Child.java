package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 28/05/18.
 */

public class Child {

    @SerializedName("answer_country")
    @Expose
    private Integer answerCountry;
    @SerializedName("answer_tin")
    @Expose
    private String answerTin;
    @SerializedName("answer_reason")
    @Expose
    private String answerReason;

    public Integer getAnswerCountry() {
        return answerCountry;
    }

    public void setAnswerCountry(Integer answerCountry) {
        this.answerCountry = answerCountry;
    }

    public String getAnswerTin() {
        return answerTin;
    }

    public void setAnswerTin(String answerTin) {
        this.answerTin = answerTin;
    }

    public String getAnswerReason() {
        return answerReason;
    }

    public void setAnswerReason(String answerReason) {
        this.answerReason = answerReason;
    }

}
