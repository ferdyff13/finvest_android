package com.invisee.finvest.ui.adapters.rv;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.PortfolioInvestment;
import com.invisee.finvest.data.api.beans.PortfolioProductComposition;
import com.invisee.finvest.util.AmountFormatter;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pandu.abbiyuarsyah on 27/03/2017.
 */

public class PortfolioFundAllocationAdapter extends RecyclerView.Adapter<PortfolioFundAllocationAdapter.PortfolioFundAllocationHolder> {

    private List<PortfolioProductComposition> list;
    private PortfolioInvestment portfolioInvestment;


    public PortfolioFundAllocationAdapter(List<PortfolioProductComposition> list, PortfolioInvestment portfolioInvestment) {
        this.list = list;
        this.portfolioInvestment = portfolioInvestment;
    }

    @Override
    public PortfolioFundAllocationAdapter.PortfolioFundAllocationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_portfolio_f_alocation, parent, false);
        return new PortfolioFundAllocationAdapter.PortfolioFundAllocationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PortfolioFundAllocationAdapter.PortfolioFundAllocationHolder holder, int position) {
        final PortfolioProductComposition item = list.get(position);


        holder.itemView.setTag(item);

        holder.txvFundName.setText(item.getIndividualFundName());
        holder.txvType.setText(item.getIndividualFundType());
        holder.txvInvManager.setText(item.getInvestmentManager());
        holder.txvNav.setText(AmountFormatter.format(item.getLastNav()));

        Log.d("tanggal navdate", "onBindViewHolder: " + item.getLastNavDate());
        holder.txvNavDate.setText(item.getLastNavDate());

        DecimalFormat df = new DecimalFormat("0.00");
        Double allocation = item.getMarketValue() * 100 / portfolioInvestment.getTotalInvestmentMarketValue();
        String allocationFormatter = String.valueOf(df.format(allocation));

        holder.txvFundAllocation.setText(allocationFormatter);

        holder.txvCurrentUnit.setText(String.valueOf(item.getCurrentUnit()));
        holder.txvMarketValue.setText(AmountFormatter.format(item.getMarketValue()));

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class PortfolioFundAllocationHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txvFundName)
        TextView txvFundName;
        @Bind(R.id.txvType)
        TextView txvType;
        @Bind(R.id.txvFundAllocation)
        TextView txvFundAllocation;
        @Bind(R.id.txvInvManager)
        TextView txvInvManager;
        @Bind(R.id.txvNav)
        TextView txvNav;
        @Bind(R.id.txvNavDate)
        TextView txvNavDate;
        @Bind(R.id.txvCurrentUnit)
        TextView txvCurrentUnit;
        @Bind(R.id.txvMarketValue)
        TextView txvMarketValue;

        public PortfolioFundAllocationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
