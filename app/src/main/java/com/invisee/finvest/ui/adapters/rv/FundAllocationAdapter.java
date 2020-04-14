package com.invisee.finvest.ui.adapters.rv;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.FundAllocation;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fajarfatur on 1/14/16.
 */
public class FundAllocationAdapter extends RecyclerView.Adapter<FundAllocationAdapter.FundAllocationHolder> {

    private List<FundAllocation> list;

    public FundAllocationAdapter(List<FundAllocation> list) {
        this.list = list;
    }

    @Override
    public FundAllocationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fund_allocation, parent, false);
        return new FundAllocationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FundAllocationHolder holder, int position) {
        final FundAllocation item = list.get(position);

        holder.itemView.setTag(item);

        holder.imvColor.setColorFilter(Color.parseColor(item.getColors()), PorterDuff.Mode.SRC_ATOP);
        holder.txvName.setText(item.getProductName());
        holder.txvType.setText(item.getProductType());
        try {
            holder.txvComposition.setText((item.getCompositition() * 100) + "%");
        } catch (Exception e) {
            holder.txvComposition.setText((0 * 100) + "%");
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class FundAllocationHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imvColor)
        ImageView imvColor;
        @Bind(R.id.txvName)
        TextView txvName;
        @Bind(R.id.txvType)
        TextView txvType;
        @Bind(R.id.txvComposition)
        TextView txvComposition;

        public FundAllocationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
