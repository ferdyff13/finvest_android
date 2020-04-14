package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pandu.abbiyuarsyah on 28/05/18.
 */

public class ChildAnswer {

    @SerializedName("question_parent")
    @Expose
    private Integer questionParent;

    @SerializedName("childs")
    @Expose
    private List<Child> childs;


    /**
     * @return The questionParent
     */
    public Integer getQuestionParent() {
        return questionParent;
    }

    /**
     * @param questionParent The questionParent
     */
    public void setQuestionParent(Integer questionParent) {
        this.questionParent = questionParent;
    }

    /**
     * @return The childs
     */
    public List<Child> getChilds() {
        return childs;
    }

    /**
     * @param childs The childs
     */
    public void setChilds(List<Child> childs) {
        this.childs = childs;
    }

}
