package com.invisee.finvest.data.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.beans.ListDetail;
import com.invisee.finvest.data.api.beans.PieChartDataDashboard;
import com.invisee.finvest.data.api.beans.UserInfo;

/**
 * Created by pandu.abbiyuarsyah on 07/04/2017.
 */

public class PieChartResponse extends GenericResponse {

    @SerializedName("data")
    @Expose
    private ListDetail data;

    /**
     * @return The data
     */
    public ListDetail getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(ListDetail data) {
        this.data = data;
    }

}
