package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fajarfatur on 2/22/16.
 */
public class FatcaQuestion {

    @SerializedName("questionaireId")
    @Expose
    private int questionaireId;
    @SerializedName("answerOption")
    @Expose
    private List<FatcaAnswerOption> answerOption = new ArrayList<>();
    @SerializedName("answerName")
    @Expose
    private String answerName;
    @SerializedName("questionaireCategory")
    @Expose
    private int questionaireCategory;
    @SerializedName("answerText")
    @Expose
    private String answerText;
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
    private int answerId;
    @SerializedName("childs")
    @Expose
    private List<FatcaChilds> fatcaChilds = new ArrayList<>();
    @SerializedName("childs_answer")
    @Expose
    private List<ChildAnswerLoad> childAnswerLoad = new ArrayList<>();

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
    public List<FatcaAnswerOption> getAnswerOption() {
        return answerOption;
    }

    /**
     * @param answerOption The answerOption
     */
    public void setAnswerOption(List<FatcaAnswerOption> answerOption) {
        this.answerOption = answerOption;
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
     * @return The answerId
     */
    public int getAnswerId() {
        return answerId;
    }

    /**
     * @param answerId The answerId
     */
    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }


    /**
     * @return The answerOption
     */
    public List<FatcaChilds> getFatcaChilds() {
        return fatcaChilds;
    }

    /**
     * @param answerOption The answerOption
     */
    public void setFatcaChilds(List<FatcaChilds> answerOption) {
        this.fatcaChilds = fatcaChilds;
    }

    /**
     * @return The childAnswerLoad
     */
    public List<ChildAnswerLoad> getChildAnswerLoad() {
        return childAnswerLoad;
    }

    /**
     * @param childAnswerLoad The childAnswerLoad
     */
    public void setChildAnswerLoad(List<ChildAnswerLoad> childAnswerLoad) {
        this.childAnswerLoad = childAnswerLoad;
    }
}
