package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by glenrynaldi on 4/12/16.
 */
public class Reminder implements Serializable {

    @SerializedName("investment_account_no")
    @Expose
    private String investmentAccountNo;
    @SerializedName("fund_package_name")
    @Expose
    private String fundPackageName;
    @SerializedName("repeat")
    @Expose
    private Object repeat;
    @SerializedName("duration_start_date")
    @Expose
    private String durationStartDate;
    @SerializedName("duration_stop_date")
    @Expose
    private String durationStopDate;
    @SerializedName("reminder_start_time")
    @Expose
    private String reminderStartTime;
    @SerializedName("investment_account_id")
    @Expose
    private Integer investmentAccountId;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("reminder_amount")
    @Expose
    private Double reminderAmount;
    @SerializedName("cust_id")
    @Expose
    private Integer custId;
    @SerializedName("reminder_desc")
    @Expose
    private Object reminderDesc;
    @SerializedName("min_topup_amount")
    @Expose
    private Double minTopupAmount;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("reminder_id")
    @Expose
    private Integer reminderId;
    @SerializedName("last_trigger")
    @Expose
    private String lastTrigger;
    @SerializedName("autodebit")
    @Expose
    private Boolean autodebit;


    private Boolean isAlarm = false;

    /**
     * @return The investmentAccountNo
     */
    public String getInvestmentAccountNo() {
        return investmentAccountNo;
    }

    /**
     * @param investmentAccountNo The investment_account_no
     */
    public void setInvestmentAccountNo(String investmentAccountNo) {
        this.investmentAccountNo = investmentAccountNo;
    }

    /**
     * @return The fundPackageName
     */
    public String getFundPackageName() {
        return fundPackageName;
    }

    /**
     * @param fundPackageName The fund_package_name
     */
    public void setFundPackageName(String fundPackageName) {
        this.fundPackageName = fundPackageName;
    }

    /**
     * @return The repeat
     */
    public Object getRepeat() {
        return repeat;
    }

    /**
     * @param repeat The repeat
     */
    public void setRepeat(Object repeat) {
        this.repeat = repeat;
    }

    /**
     * @return The durationStartDate
     */
    public String getDurationStartDate() {
        return durationStartDate;
    }

    /**
     * @param durationStartDate The duration_start_date
     */
    public void setDurationStartDate(String durationStartDate) {
        this.durationStartDate = durationStartDate;
    }

    /**
     * @return The durationStopDate
     */
    public String getDurationStopDate() {
        return durationStopDate;
    }

    /**
     * @param durationStopDate The duration_stop_date
     */
    public void setDurationStopDate(String durationStopDate) {
        this.durationStopDate = durationStopDate;
    }

    /**
     * @return The reminderStartTime
     */
    public String getReminderStartTime() {
        return reminderStartTime;
    }

    /**
     * @param reminderStartTime The reminder_start_time
     */
    public void setReminderStartTime(String reminderStartTime) {
        this.reminderStartTime = reminderStartTime;
    }

    /**
     * @return The investmentAccountId
     */
    public Integer getInvestmentAccountId() {
        return investmentAccountId;
    }

    /**
     * @param investmentAccountId The investment_account_id
     */
    public void setInvestmentAccountId(Integer investmentAccountId) {
        this.investmentAccountId = investmentAccountId;
    }

    /**
     * @return The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return The reminderAmount
     */
    public Double getReminderAmount() {
        return reminderAmount;
    }

    /**
     * @param reminderAmount The reminder_amount
     */
    public void setReminderAmount(Double reminderAmount) {
        this.reminderAmount = reminderAmount;
    }

    /**
     * @return The custId
     */
    public Integer getCustId() {
        return custId;
    }

    /**
     * @param custId The cust_id
     */
    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    /**
     * @return The reminderDesc
     */
    public Object getReminderDesc() {
        return reminderDesc;
    }

    /**
     * @param reminderDesc The reminder_desc
     */
    public void setReminderDesc(Object reminderDesc) {
        this.reminderDesc = reminderDesc;
    }

    /**
     * @return The minTopupAmount
     */
    public Double getMinTopupAmount() {
        return minTopupAmount;
    }

    /**
     * @param minTopupAmount The min_topup_amount
     */
    public void setMinTopupAmount(Double minTopupAmount) {
        this.minTopupAmount = minTopupAmount;
    }

    /**
     * @return The status
     */
    public Object getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Object status) {
        this.status = status;
    }

    /**
     * @return The reminderId
     */
    public Integer getReminderId() {
        return reminderId;
    }

    /**
     * @param reminderId The reminder_id
     */
    public void setReminderId(Integer reminderId) {
        this.reminderId = reminderId;
    }

    /**
     * @return The lastTrigger
     */
    public String getLastTrigger() {
        return lastTrigger;
    }

    /**
     * @param lastTrigger The last_trigger
     */
    public void setLastTrigger(String lastTrigger) {
        this.lastTrigger = lastTrigger;
    }


    public Boolean isAlarm() {
        return isAlarm;
    }

    public void setAlarm(Boolean isAlarm) {
        this.isAlarm = isAlarm;
    }

    public Boolean getAutodebit() {
        return autodebit;
    }

    public void setAutodebit(Boolean autodebit) {
        this.autodebit = autodebit;
    }
}
