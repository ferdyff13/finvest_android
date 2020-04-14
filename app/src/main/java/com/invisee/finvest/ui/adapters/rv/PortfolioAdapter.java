package com.invisee.finvest.ui.adapters.rv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.beans.PortfolioInvestment;
import com.invisee.finvest.util.AmountFormatter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fajarfatur on 3/1/16.
 */
public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.PortofolioHolder> {

    private Context context;
    private List<PortfolioInvestment> list;

    public PortfolioAdapter(Context context, List<PortfolioInvestment> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public PortofolioHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_portfolio, parent, false);
        return new PortofolioHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PortofolioHolder holder, int position) {
        final PortfolioInvestment item = list.get(position);
        double totalInv = item.getInvestmentAmount();
        double totalMarketValue = item.getTotalInvestmentMarketValue();
        double totalGainLoss = totalMarketValue - totalInv;
        float percentage = (float) (totalGainLoss / totalInv) * 100;

        holder.itemView.setTag(item);

        Picasso.with(context).load(InviseeService.IMAGE_DOWNLOAD_URL + item.getPackageImageKey()).into(holder.ivPackage);
        holder.tvPackageName.setText(item.getPackageName());
        holder.tvPackageName.setText(item.getPackageName());
        holder.tvInvestmentNumber.setText(item.getInvestmentAccountNumber());
        holder.tvMarketValue.setText(AmountFormatter.format(totalMarketValue));
        holder.tvUrealizeGainLoss.setText(AmountFormatter.format(totalGainLoss));
        holder.tvUrealizeGainLossPercent.setText(String.format("%.2f", percentage) + " " + "%");
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class PortofolioHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ivPackage)
        ImageView ivPackage;
        @Bind(R.id.tvPackageName)
        TextView tvPackageName;
        @Bind(R.id.tvInvestmentNumber)
        TextView tvInvestmentNumber;
        @Bind(R.id.tvMarketValue)
        TextView tvMarketValue;
        @Bind(R.id.tvUrealizeGainLoss)
        TextView tvUrealizeGainLoss;
        @Bind(R.id.tvUrealizeGainLossPercent)
        TextView tvUrealizeGainLossPercent;

        public PortofolioHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}