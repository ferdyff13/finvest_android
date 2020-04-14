package com.invisee.finvest.ui.adapters.rv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.TransactionHistory;
import com.invisee.finvest.ui.fragments.transaction.TransactionFragment;
import com.invisee.finvest.util.DateUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fajarfatur on 3/1/16.
 */
public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.HistoryHolder> {

    private Context context;
    private String convertedDate;
    private List<TransactionHistory> list;
    //private TransactionFragment mContext;

    public TransactionHistoryAdapter(Context context_, List<TransactionHistory> list) {
        this.context = context_;
        //this.mContext = context;
        this.list = list;
    }

    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_trx_history, parent, false);
        return new HistoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HistoryHolder holder, int position) {
        final TransactionHistory item  = list.get(position);
        holder.itemView.setTag(item);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault());
        Date dateParse = null;
        try {
            dateParse = sdf.parse(item.getTransactionDate());
            SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.DD_MMM_YYYY, Locale.getDefault());
            convertedDate = formatter.format(dateParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvPackageName.setText(item.getPackageName());
        holder.tvInvestmentNumber.setText(item.getInvestmentNumber());
        holder.tvOrderNumber.setText(item.getOrderNumber());
        holder.tvTrxDate.setText(convertedDate);
        holder.tvTrxStatus.setText(item.getTransactionStatus());

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class HistoryHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvPackageName)
        TextView tvPackageName;
        @Bind(R.id.tvOrderNumber)
        TextView tvOrderNumber;
        @Bind(R.id.tvInvestmentNumber)
        TextView tvInvestmentNumber;
        @Bind(R.id.tvTrxDate)
        TextView tvTrxDate;
        @Bind(R.id.tvTrxStatus)
        TextView tvTrxStatus;


        public HistoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}