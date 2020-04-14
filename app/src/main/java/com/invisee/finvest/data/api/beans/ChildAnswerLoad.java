package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pandu.abbiyuarsyah on 29/05/18.
 */

public class ChildAnswerLoad {

    @SerializedName("c_answer_reason_text")
    @Expose
    private String cAnswerReason;
    @SerializedName("c_answer_reason_object")
    @Expose
    private AnswerReason cAnswerReasoObject;
    @SerializedName("c_answer_tin")
    @Expose
    private String cAnswerTin;
    @SerializedName("c_answer_id")
    @Expose
    private Integer cAnswerId;
    @SerializedName("c_answer_country")
    @Expose
    private String cAnswerCountry;
    @SerializedName("c_answer_code")
    @Expose
    private String cAnswerCode;

    public String getCAnswerReason() {
        return cAnswerReason;
    }

    public void setCAnswerReason(String cAnswerReason) {
        this.cAnswerReason = cAnswerReason;
    }

    public AnswerReason getcAnswerReasoObject() {
        return cAnswerReasoObject;
    }

    public void setcAnswerReasoObject(AnswerReason cAnswerReasoObject) {
        this.cAnswerReasoObject = cAnswerReasoObject;
    }

    public String getCAnswerTin() {
        return cAnswerTin;
    }

    public void setCAnswerTin(String cAnswerTin) {
        this.cAnswerTin = cAnswerTin;
    }

    public Integer getCAnswerId() {
        return cAnswerId;
    }

    public void setCAnswerId(Integer cAnswerId) {
        this.cAnswerId = cAnswerId;
    }

    public String getCAnswerCountry() {
        return cAnswerCountry;
    }

    public void setCAnswerCountry(String cAnswerCountry) {
        this.cAnswerCountry = cAnswerCountry;
    }

    public String getCAnswerCode() {
        return cAnswerCode;
    }

    public void setCAnswerCode(String cAnswerCode) {
        this.cAnswerCode = cAnswerCode;
    }


}
