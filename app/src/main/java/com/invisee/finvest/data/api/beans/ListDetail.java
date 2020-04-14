package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisee.finvest.data.api.responses.PieChartResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandu.abbiyuarsyah on 07/04/2017.
 */

public class ListDetail {

    @SerializedName("listDetail")
    @Expose
    private List<PieChartDataDashboard> listDetail = new ArrayList<>();

    /**
     * @return The listDetail
     */
    public List<PieChartDataDashboard> getListDetail() {
        return listDetail;
    }

    /**
     * @param listDetail The listDetail
     */
    public void setListDetail(List<PieChartDataDashboard> listDetail) {
        this.listDetail = listDetail;
    }

}
