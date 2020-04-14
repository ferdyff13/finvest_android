package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandu.abbiyuarsyah on 27/05/18.
 */

public class FatcaChilds {


    @SerializedName("questionId")
    @Expose
    private int questionId;

    @SerializedName("questionName")
    @Expose
    private String questionName;

    @SerializedName("answerOption")
    @Expose
    private List<AnswerOption> answerOption = new ArrayList<>();

    @SerializedName("questionText")
    @Expose
    private String questionText;


    /**
     * @return The questionId
     */
    public int getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId The questionId
     */
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

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
     * @return The answerOption
     */
    public List<AnswerOption> getAnswerOption() {
        return answerOption;
    }

    /**
     * @param answerOption The answerOption
     */
    public void setAnswerOption(List<AnswerOption> answerOption) {
        this.answerOption = answerOption;
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


}
