package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.Reminder;
import com.invisee.finvest.data.api.beans.ReminderDetail;

/**
 * Created by pandu.abbiyuarsyah on 08/06/2017.
 */

public class ReminderDetailResponse extends GenericResponse {

    @SerializedName("reminder")
    @Expose
    private ReminderDetail reminder;

    public ReminderDetail getReminder() {
        return reminder;
    }

    public void setReminder (ReminderDetail reminder) {
        this.reminder = reminder;
    }
}
