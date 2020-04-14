package com.invisee.finvest.ui.adapters.rv;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.WalletHistory;
import com.invisee.finvest.util.AmountFormatter;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fajarfatur on 1/14/16.
 */
public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.WalletHistoryHolder> {

    private Map<String, WalletHistory> list;


    public WalletHistoryAdapter(Map<String, WalletHistory> list) {
        this.list = list;
    }

    @Override
    public WalletHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wallet_history, parent, false);
        return new WalletHistoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WalletHistoryHolder holder, int position) {
        WalletHistory item = list.get(Integer.toString(position));


        holder.itemView.setTag(item);

        holder.txvTransactionName.setText(item.getTransactionType() + " - " + item.getTransactionNumber());
        holder.txvAmount.setText(AmountFormatter.format(item.getEnteredAmount()));
        holder.txvTransactionStatus.setText(item.getStatus());
        holder.txvTransactionTime.setText(item.getTransactionTimeStamp() + ", " + item.getTime());


    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class WalletHistoryHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txvTransactionName)
        TextView txvTransactionName;
        @Bind(R.id.txvAmount)
        TextView txvAmount;
        @Bind(R.id.txvTransactionStatus)
        TextView txvTransactionStatus;
        @Bind(R.id.txvTransactionTime)
        TextView txvTransactionTime;


        public WalletHistoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
