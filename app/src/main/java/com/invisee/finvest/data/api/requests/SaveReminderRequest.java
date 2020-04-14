package com.invisee.finvest.data.api.requests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class SaveReminderRequest {

    private String token;
    private String endDate;
    private String id;
    private Long investNumber;
    private String reminderAmount;
    @SerializedName("reminderIType")
    private String reminderType;
    private String reminderTime;
    private String startDate;
    @SerializedName("packagename")
    private String packageName;
    @SerializedName("autodebit")
    private Boolean autodebit;

    public SaveReminderRequest(String token, String startDate, String endDate, String reminderTime, String id, String packageName, Long investNumber, String reminderAmount, String reminderType, Boolean autodebit) {
        this.token = token;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reminderTime = reminderTime;
        this.id = id;
        this.packageName = packageName;
        this.investNumber = investNumber;
        this.reminderAmount = reminderAmount;
        this.reminderType = reminderType;
        this.autodebit = autodebit;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReminderAmount() {
        return reminderAmount;
    }

    public void setReminderAmount(String reminderAmount) {
        this.reminderAmount = reminderAmount;
    }

    public String getReminderType() {
        return reminderType;
    }

    public void setReminderType(String reminderType) {
        this.reminderType = reminderType;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getInvestNumber() {
        return investNumber;
    }

    public void setInvestNumber(Long investNumber) {
        this.investNumber = investNumber;
    }

    public Boolean getAutoDebit() {
        return autodebit;
    }

    public void setAutodebit(Boolean autodebit) {
        this.autodebit = autodebit;
    }
}
