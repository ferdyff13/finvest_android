package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fajarfatur on 2/22/16.
 */
public class RiskProfileQuestion implements Serializable{

    @SerializedName("questionaireId")
    @Expose
    private int questionaireId;
    @SerializedName("answerOption")
    @Expose
    private List<RiskProfileAnswerOption> answerOption = new ArrayList<>();
    @SerializedName("questionaireCategory")
    @Expose
    private int questionaireCategory;
    @SerializedName("questionText")
    @Expose
    private String questionText;
    @SerializedName("questionId")
    @Expose
    private int questionId;
    @SerializedName("questionName")
    @Expose
    private String questionName;
    @SerializedName("kycId")
    @Expose
    private int kycId;
    @SerializedName("answerId")
    @Expose
    private List<Integer> answerId = new ArrayList<Integer>();
    /**/
    private int selectedAnswerId;

    /**
     * @return The questionaireId
     */
    public int getQuestionaireId() {
        return questionaireId;
    }

    /**
     * @param questionaireId The questionaireId
     */
    public void setQuestionaireId(int questionaireId) {
        this.questionaireId = questionaireId;
    }

    /**
     * @return The answerOption
     */
    public List<RiskProfileAnswerOption> getAnswerOption() {
        return answerOption;
    }

    /**
     * @param answerOption The answerOption
     */
    public void setAnswerOption(List<RiskProfileAnswerOption> answerOption) {
        this.answerOption = answerOption;
    }

    /**
     * @return The questionaireCategory
     */
    public int getQuestionaireCategory() {
        return questionaireCategory;
    }

    /**
     * @param questionaireCategory The questionaireCategory
     */
    public void setQuestionaireCategory(int questionaireCategory) {
        this.questionaireCategory = questionaireCategory;
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
     * @return The kycId
     */
    public int getKycId() {
        return kycId;
    }

    /**
     * @param kycId The kycId
     */
    public void setKycId(int kycId) {
        this.kycId = kycId;
    }

    /**
     *
     * @return
     * The answerId
     */
    public List<Integer> getAnswerId() {
        return answerId;
    }

    /**
     *
     * @param answerId
     * The answerId
     */
    public void setAnswerId(List<Integer> answerId) {
        this.answerId = answerId;
    }

    public int getSelectedAnswerId() {
        return selectedAnswerId;
    }

    public void setSelectedAnswerId(int selectedAnswerId) {
        this.selectedAnswerId = selectedAnswerId;
    }
}
