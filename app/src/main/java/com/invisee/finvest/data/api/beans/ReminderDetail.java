package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Created by pandu.abbiyuarsyah on 08/06/2017.
 */

public class ReminderDetail {

    @SerializedName("reminderStartTime")
    @Expose
    private String reminderStartTime;

    @SerializedName("fundPackageRefId")
    @Expose
    private Long fundPackageRefId;

    @SerializedName("durationStopDate")
    @Expose
    private String durationStopDate;

    @SerializedName("reminderAmount")
    @Expose
    private Integer reminderAmount;

    @SerializedName("invesmentAccountId")
    @Expose
    private Long invesmentAccountId;

    @SerializedName("reminderType")
    @Expose
    private String reminderType;

    @SerializedName("autodebit")
    @Expose
    private Boolean autodebit;

    @SerializedName("durationStartDate")
    @Expose
    private String durationStartDate;

    @SerializedName("id")
    @Expose
    private Integer id;

    public String getReminderStartTime() {
        return reminderStartTime;
    }

    public void setReminderStartTime(String reminderStartTime) {
        this.reminderStartTime = reminderStartTime;
    }

    public Long getFundPackageRefId() {
        return fundPackageRefId;
    }

    public void setFundPackageRefId(Long fundPackageRefId) {
        this.fundPackageRefId = fundPackageRefId;
    }

    public String getDurationStopDate() {
        return durationStopDate;
    }

    public void setDurationStopDate(String durationStopDate) {
        this.durationStopDate = durationStopDate;
    }

    public Integer getReminderAmount() {
        return reminderAmount;
    }

    public void setReminderAmount(Integer reminderAmount) {
        this.reminderAmount = reminderAmount;
    }

    public String getReminderType() {
        return reminderType;
    }

    public void setReminderType(String reminderType) {
        this.reminderType = reminderType;
    }


    public Boolean getAutodebit() {
        return autodebit;
    }

    public void setAutodebit(Boolean autodebit) {
        this.autodebit = autodebit;
    }

    public String getDurationStartDate() {
        return durationStartDate;
    }

    public void setDurationStartDate(String durationStartDate) {
        this.durationStartDate = durationStartDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Long getInvesmentAccountId() {
        return invesmentAccountId;
    }

    public void setInvesmentAccountId(Long invesmentAccountId) {
        this.invesmentAccountId = invesmentAccountId;
    }








}
