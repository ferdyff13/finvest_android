package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by glenrynaldi on 6/14/16.
 */
public class Question {

    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("atQuestionId")
    @Expose
    private String atQuestionId;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("effectiveDateFrom")
    @Expose
    private Object effectiveDateFrom;
    @SerializedName("effectiveDateTo")
    @Expose
    private Object effectiveDateTo;
    @SerializedName("publishStatus")
    @Expose
    private Object publishStatus;
    @SerializedName("questionName")
    @Expose
    private String questionName;
    @SerializedName("questionText")
    @Expose
    private String questionText;
    @SerializedName("questionType")
    @Expose
    private Integer questionType;
    @SerializedName("questionaires")
    @Expose
    private Questionaires questionaires;
    @SerializedName("seq")
    @Expose
    private Integer seq;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("weighted")
    @Expose
    private Integer weighted;

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
     * The atQuestionId
     */
    public String getAtQuestionId() {
        return atQuestionId;
    }

    /**
     *
     * @param atQuestionId
     * The atQuestionId
     */
    public void setAtQuestionId(String atQuestionId) {
        this.atQuestionId = atQuestionId;
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
     * The effectiveDateFrom
     */
    public Object getEffectiveDateFrom() {
        return effectiveDateFrom;
    }

    /**
     *
     * @param effectiveDateFrom
     * The effectiveDateFrom
     */
    public void setEffectiveDateFrom(Object effectiveDateFrom) {
        this.effectiveDateFrom = effectiveDateFrom;
    }

    /**
     *
     * @return
     * The effectiveDateTo
     */
    public Object getEffectiveDateTo() {
        return effectiveDateTo;
    }

    /**
     *
     * @param effectiveDateTo
     * The effectiveDateTo
     */
    public void setEffectiveDateTo(Object effectiveDateTo) {
        this.effectiveDateTo = effectiveDateTo;
    }

    /**
     *
     * @return
     * The publishStatus
     */
    public Object getPublishStatus() {
        return publishStatus;
    }

    /**
     *
     * @param publishStatus
     * The publishStatus
     */
    public void setPublishStatus(Object publishStatus) {
        this.publishStatus = publishStatus;
    }

    /**
     *
     * @return
     * The questionName
     */
    public String getQuestionName() {
        return questionName;
    }

    /**
     *
     * @param questionName
     * The questionName
     */
    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    /**
     *
     * @return
     * The questionText
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     *
     * @param questionText
     * The questionText
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     *
     * @return
     * The questionType
     */
    public Integer getQuestionType() {
        return questionType;
    }

    /**
     *
     * @param questionType
     * The questionType
     */
    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    /**
     *
     * @return
     * The questionaires
     */
    public Questionaires getQuestionaires() {
        return questionaires;
    }

    /**
     *
     * @param questionaires
     * The questionaires
     */
    public void setQuestionaires(Questionaires questionaires) {
        this.questionaires = questionaires;
    }

    /**
     *
     * @return
     * The seq
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     *
     * @param seq
     * The seq
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     *
     * @return
     * The updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     *
     * @param updatedBy
     * The updatedBy
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     *
     * @return
     * The updatedDate
     */
    public String getUpdatedDate() {
        return updatedDate;
    }

    /**
     *
     * @param updatedDate
     * The updatedDate
     */
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     *
     * @return
     * The weighted
     */
    public Integer getWeighted() {
        return weighted;
    }

    /**
     *
     * @param weighted
     * The weighted
     */
    public void setWeighted(Integer weighted) {
        this.weighted = weighted;
    }

}
