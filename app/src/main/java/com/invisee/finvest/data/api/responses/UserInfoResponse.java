package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.Answer;
import com.invisee.finvest.data.api.beans.Question;
import com.invisee.finvest.data.api.beans.UserInfo;

/**
 * Created by glenrynaldi on 6/14/16.
 */
public class UserInfoResponse extends GenericResponse {

    @SerializedName("data")
    @Expose
    private UserInfo data;
    @SerializedName("question")
    @Expose
    private Question question;
    @SerializedName("answer")
    @Expose
    private Answer answer;
    @SerializedName("imageKey")
    @Expose
    private String imageKey;

    /**
     * @return The data
     */
    public UserInfo getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(UserInfo data) {
        this.data = data;
    }

    /**
     * @return The question
     */
    public Question getQuestion() {
        return question;
    }

    /**
     * @param question The question
     */
    public void setQuestion(Question question) {
        this.question = question;
    }

    /**
     * @return The answer
     */
    public Answer getAnswer() {
        return answer;
    }

    /**
     * @param answer The answer
     */
    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    /**
     * @return The imageKey
     */
    public String getImageKey() {
        return imageKey;
    }

    /**
     * @param imageKey The imageKey
     */
    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }


}
