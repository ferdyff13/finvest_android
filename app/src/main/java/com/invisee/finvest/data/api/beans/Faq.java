package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by glenrynaldi on 6/10/16.
 */
public class Faq {

    @SerializedName("faqStatus")
    @Expose
    private String faqStatus;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * @return The faqStatus
     */
    public String getFaqStatus() {
        return faqStatus;
    }

    /**
     * @param faqStatus The faqStatus
     */
    public void setFaqStatus(String faqStatus) {
        this.faqStatus = faqStatus;
    }

    /**
     * @return The question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question The question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return The answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer The answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

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

}
