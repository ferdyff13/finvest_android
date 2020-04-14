package com.invisee.finvest.ui.adapters.rv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.TopUpTransaction;
import com.invisee.finvest.util.AmountFormatter;
import com.invisee.finvest.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pandu.abbiyuarsyah on 20/10/2017.
 */

public class TopUpHistoryAdapter extends RecyclerView.Adapter<TopUpHistoryAdapter.HistoryHolder> {

    private Context context;
    private List<TopUpTransaction> list;

    public TopUpHistoryAdapter(Context context, List<TopUpTransaction> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public TopUpHistoryAdapter.HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_top_up_list_history, parent, false);
        return new TopUpHistoryAdapter.HistoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopUpHistoryAdapter.HistoryHolder holder, int position) {
        final  TopUpTransaction item = list.get(position);
        holder.itemView.setTag(item);

        holder.tvTotalAmount.setText(AmountFormatter.format(item.getTotalAmount()));
        holder.tvBank.setText(item.getBankName());
        holder.tvTransactionNumber.setText(item.getTrxNo());

        if (item.getTrxStatus().contains("WAIT")) {
            holder.tvStatus.setText("Menunggu Pembayaran");
        } else if (item.getTrxStatus().contains("PAID")){
            holder.tvStatus.setText("Telah Dibayar");
        } else if (item.getTrxStatus().contains("DONE")) {
            holder.tvStatus.setText("Selesai");
        } else if (item.getTrxStatus().contains("CANC")) {
            holder.tvStatus.setText("Batal");
        } else {
            holder.tvStatus.setText(item.getTrxStatus());
        }

        if (item.getTransferDate() == null){
            holder.tvTransferDate.setText("-");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ",    Locale.getDefault());
            Date date3 = null;
            try {
                date3 = sdf.parse(item.getTransferDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
            String convertedDate= formatter.format(date3);
            holder.tvTransferDate.setText(convertedDate);
        }

        if (item.getTransactionDate() == null) {
            holder.tvTransactionDate.setText("-");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ",    Locale.getDefault());
            Date date3 = null;
            try {
                date3 = sdf.parse(item.getTransactionDate() );
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String convertedDate= formatter.format(date3);
            holder.tvTransactionDate.setText(convertedDate);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ",    Locale.getDefault());
        Date date3 = null;
        try {
            date3 = sdf.parse(item.getDueDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.DD__MMM__YYYY_HH_MM, Locale.getDefault());
        String convertedDate= formatter.format(date3);
        holder.tvDueDate.setText(convertedDate);

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class HistoryHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvTotalAmount)
        TextView tvTotalAmount;
        @Bind(R.id.tvBank)
        TextView tvBank;
        @Bind(R.id.tvStatus)
        TextView tvStatus;
        @Bind(R.id.tvTransactionNumber)
        TextView tvTransactionNumber;
        @Bind(R.id.tvDueDate)
        TextView tvDueDate;
        @Bind(R.id.tvTransactionDate)
        TextView tvTransactionDate;
        @Bind(R.id.tvTransferDate)
        TextView tvTransferDate;

        public HistoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
