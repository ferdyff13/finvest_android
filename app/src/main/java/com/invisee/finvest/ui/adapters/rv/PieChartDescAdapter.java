package com.invisee.finvest.ui.adapters.rv;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.PieChartDataDashboard;
import com.invisee.finvest.data.api.beans.PortfolioChartData;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.model.PieChartData;

/**
 * Created by fajarfatur on 3/6/16.
 */
public class PieChartDescAdapter extends RecyclerView.Adapter<PieChartDescAdapter.PieChartDescHolder> {

    private Context context;
    private List<PieChartDataDashboard> list;

    public PieChartDescAdapter(Context context, List<PieChartDataDashboard> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public PieChartDescHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_piechart_desc, parent, false);
        return new PieChartDescHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PieChartDescHolder holder, int position) {
        final PieChartDataDashboard item  = list.get(position);
        holder.itemView.setTag(item);
        holder.colorFundType.setBackgroundColor(Color.parseColor(item.getFundTypeColors()));
        holder.tvFundTypeName.setText(item.getIndividualFundType());
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class PieChartDescHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.colorFundType)
        View colorFundType;
        @Bind(R.id.tvFundTypeName)
        TextView tvFundTypeName;

        public PieChartDescHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
