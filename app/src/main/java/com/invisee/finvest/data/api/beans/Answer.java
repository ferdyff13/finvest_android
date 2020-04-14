package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by glenrynaldi on 6/14/16.
 */
public class Answer {

    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("answer")
    @Expose
    private Object answer;
    @SerializedName("answerNote")
    @Expose
    private String answerNote;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("kyc")
    @Expose
    private Kyc kyc;
    @SerializedName("question")
    @Expose
    private Question question;
    @SerializedName("updatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private Object updatedDate;

    /**
     *
     * @return
     * The _class
     */
    public String getClass_() {
        return _class;
    }

    /**
     *
     * @param _class
     * The class
     */
    public void setClass_(String _class) {
        this._class = _class;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The answer
     */
    public Object getAnswer() {
        return answer;
    }

    /**
     *
     * @param answer
     * The answer
     */
    public void setAnswer(Object answer) {
        this.answer = answer;
    }

    /**
     *
     * @return
     * The answerNote
     */
    public String getAnswerNote() {
        return answerNote;
    }

    /**
     *
     * @param answerNote
     * The answerNote
     */
    public void setAnswerNote(String answerNote) {
        this.answerNote = answerNote;
    }

    /**
     *
     * @return
     * The createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy
     * The createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return
     * The createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     *
     * @param createdDate
     * The createdDate
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     *
     * @return
     * The kyc
     */
    public Kyc getKyc() {
        return kyc;
    }

    /**
     *
     * @param kyc
     * The kyc
     */
    public void setKyc(Kyc kyc) {
        this.kyc = kyc;
    }

    /**
     *
     * @return
     * The question
     */
    public Question getQuestion() {
        return question;
    }

    /**
     *
     * @param question
     * The question
     */
    public void setQuestion(Question question) {
        this.question = question;
    }

    /**
     *
     * @return
     * The updatedBy
     */
    public Object getUpdatedBy() {
        return updatedBy;
    }

    /**
     *
     * @param updatedBy
     * The updatedBy
     */
    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     *
     * @return
     * The updatedDate
     */
    public Object getUpdatedDate() {
        return updatedDate;
    }

    /**
     *
     * @param updatedDate
     * The updatedDate
     */
    public void setUpdatedDate(Object updatedDate) {
        this.updatedDate = updatedDate;
    }

}
