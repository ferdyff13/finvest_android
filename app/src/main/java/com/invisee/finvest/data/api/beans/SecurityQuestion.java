package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarfatur on 2/1/16.
 */
public class SecurityQuestion {


    @SerializedName("questionName")
    @Expose
    private String questionName;
    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("questionText")
    @Expose
    private String questionText;


    /**
     * @return The questionName
     */
    public String getQuestionName() {
        return questionName;
    }

    /**
     * @param questionName The questionName
     */
    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    /**
     * @return The Id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The questionText
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * @param questionText The questionText
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @Override
    public String toString() {
        return getQuestionText();
    }

}
