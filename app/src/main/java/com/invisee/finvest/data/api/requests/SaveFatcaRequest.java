package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.ChildAnswer;

import java.util.List;

/**
 * Created by pandu.abbiyuarsyah on 28/05/18.
 */

public class SaveFatcaRequest {

    @SerializedName("child_answers")
    @Expose
    private List<ChildAnswer> childAnswers;

    /**
     * @return The childAnswers
     */
    public List<ChildAnswer> getChildAnswers() {
        return childAnswers;
    }

    /**
     * @param childAnswers The childAnswers
     */
    public void setChildAnswers(List<ChildAnswer> childAnswers) {
        this.childAnswers = childAnswers;
    }




}
