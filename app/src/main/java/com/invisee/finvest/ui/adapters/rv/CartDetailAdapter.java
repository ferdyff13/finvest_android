package com.invisee.finvest.ui.adapters.rv;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.responses.CartListResponse;
import com.invisee.finvest.ui.fragments.BaseFragment;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fajarfatur on 1/14/16.
 */
public class CartDetailAdapter extends RecyclerView.Adapter<CartDetailAdapter.CartHolder> {

    private Context context;
    private List<CartListResponse> list;
    private BaseFragment fragment;

    public CartDetailAdapter(Context context, List<CartListResponse> list, BaseFragment fragment) {
        this.context = context;
        this.list = list;
        this.fragment = fragment;
    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart_detail, parent, false);
        CartHolder holder = new CartHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CartHolder holder, int position) {
        final CartListResponse item = list.get(position);


        holder.itemView.setTag(item);

        NumberFormat nf = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);

        holder.tvProductName.setText(item.getFundPackages().getFundPackageName());
        holder.edtAmount.setText(nf.format(Double.parseDouble(item.getTransactionAmount())));
        holder.edtFee.setText(item.getFeePercentage());
        holder.edtTotal.setText("Rp" + " " + nf.format(Double.parseDouble(item.getTotal())));

        if (item.getTransactionType().getTrxCode().equalsIgnoreCase("SUBCR")) {
            holder.tvProductType.setText("Subscribe");
        } else if (item.getTransactionType().getTrxCode().equalsIgnoreCase("TOPUP")) {
            holder.tvProductType.setText("Top Up (" + item.getInvestmentAccount() + ")");
        }

        for (int i = 0; i < item.getFundPackagesProduct().size(); i++) {

            List<String> fundAlloc = item.getFundPackagesProduct().get(i);

            LayoutInflater inflater = fragment.getActivity().getLayoutInflater();
            View v = inflater.inflate(R.layout.row_fund_allocation, null);

            TextView txvName = (TextView) v.findViewById(R.id.txvName);
            TextView txvType = (TextView) v.findViewById(R.id.txvType);
            TextView txvComposition = (TextView) v.findViewById(R.id.txvComposition);

            txvName.setText(fundAlloc.get(2));
            txvType.setVisibility(View.GONE);
            try {
                txvComposition.setText((Double.parseDouble(fundAlloc.get(1)) * 100) + "%");
            } catch (Exception e) {
                txvComposition.setText((0 * 100) + "%");
            }

            holder.lnrFundAlloc.addView(v);


        }


    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class CartHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvProductName)
        TextView tvProductName;
        @Bind(R.id.tvProductType)
        TextView tvProductType;
        @Bind(R.id.edtAmount)
        EditText edtAmount;
        @Bind(R.id.edtFee)
        EditText edtFee;
        @Bind(R.id.edtTotal)
        EditText edtTotal;
        @Bind(R.id.edtAmountWrapper)
        TextInputLayout edtAmountWrapper;
        @Bind(R.id.edtFeeWrapper)
        TextInputLayout edtFeeWrapper;
/*        @Bind(R.id.edtTotalWrapper)
        TextInputLayout edtTotalWrapper;*/
        @Bind(R.id.lnrFundAlloc)
        LinearLayout lnrFundAlloc;

        public CartHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
