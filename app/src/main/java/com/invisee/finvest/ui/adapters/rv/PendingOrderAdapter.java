package com.invisee.finvest.ui.adapters.rv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.PortfolioProductComposition;
import com.invisee.finvest.data.api.beans.TransactionHistory;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.DetailPendingOrderActivity;
import com.invisee.finvest.ui.activities.ReminderActivity;
import com.invisee.finvest.ui.fragments.dashboard.PendingOrderPresenter;
import com.invisee.finvest.util.AmountFormatter;
import com.invisee.finvest.util.DateUtil;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pandu.abbiyuarsyah on 30/03/2017.
 */

public class PendingOrderAdapter extends RecyclerView.Adapter<PendingOrderAdapter.PendingOrderHolder>  {

    ArrayList<TransactionHistory> list;
    private String convertedDate;

    Context context;
    PendingOrderPresenter presenter;


    public PendingOrderAdapter(ArrayList<TransactionHistory> list, Context context, PendingOrderPresenter presenter) {
        this.list = list;
        this.context = context;
        this.presenter = presenter;
    }


    @Override
    public PendingOrderAdapter.PendingOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pending_order, parent, false);
        return new PendingOrderAdapter.PendingOrderHolder(itemView);
    }

        @Override
        public void onBindViewHolder(PendingOrderAdapter.PendingOrderHolder holder, int position) {
            final TransactionHistory item = list.get(position);

        holder.txvPackageName.setText(item.getPackageName());
        holder.txvTransactionType.setText(item.getTransactionType());
        holder.txvOrderNumber.setText(item.getOrderNumber());
        holder.txvInvestmentNumber.setText(item.getInvestmentNumber());
        holder.txvTotal.setText(AmountFormatter.format(item.getAmount()));
        holder.txvTransactionStatus.setText(item.getTransactionStatus());
        holder.txvTime.setText(item.getTransactionTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault());
        Date dateParse = null;
        try {
            dateParse = sdf.parse(item.getTransactionDate());
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy" , Locale.getDefault());
            convertedDate= formatter.format(dateParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.txvDate.setText(convertedDate);
        holder.lnItemOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailPendingOrderActivity.startActivity((BaseActivity) context, item);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class PendingOrderHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txvDate)
        TextView txvDate;
        @Bind(R.id.txvTime)
        TextView txvTime;
        @Bind(R.id.txvTransactionType)
        TextView txvTransactionType;
        @Bind(R.id.txvPackageName)
        TextView txvPackageName;
        @Bind(R.id.txvOrderNumber)
        TextView txvOrderNumber;
        @Bind(R.id.txvInvestmentNumber)
        TextView txvInvestmentNumber;
        @Bind(R.id.txvTotal)
        TextView txvTotal;
        @Bind(R.id.txvTransactionStatus)
        TextView txvTransactionStatus;
        @Bind(R.id.lnItemOrder)
        LinearLayout lnItemOrder;

        public PendingOrderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}
