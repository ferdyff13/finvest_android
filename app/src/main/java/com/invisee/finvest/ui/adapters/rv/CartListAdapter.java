package com.invisee.finvest.ui.adapters.rv;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Fee;
import com.invisee.finvest.data.api.beans.FundAllocation;
import com.invisee.finvest.data.api.responses.CartListResponse;
import com.invisee.finvest.data.api.responses.FundAllocationResponse;
import com.invisee.finvest.ui.fragments.cart.CartPresenter;
import com.invisee.finvest.util.AmountFormatter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartHolder> {

    private List<CartListResponse> list;
    private List<CartHolder> holderList;
    private CartListResponse item;
    private CartPresenter presenter;
    private List<FundAllocation> fundlist;
    private FundAllocation fundAllocations;
    private FundAllocationResponse packages;
    private Context context;


    public CartListAdapter(CartPresenter presenter, List<CartListResponse> list, FundAllocationResponse packages, Context context) {
        this.list = list;
        this.holderList = new ArrayList<>();
        this.context = context;
        this.packages = packages;
        this.presenter = presenter;
    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart, parent, false);
        return new CartHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CartHolder holder, final int position) {
        item = list.get(position);

        holder.itemView.setTag(item);

        holder.tvProductName.setText(item.getFundPackages().getFundPackageName());

        if (item.getTransactionType().getTrxCode().equalsIgnoreCase("SUBCR")) {
            holder.tvProductType.setText("Subscribe");
        } else if (item.getTransactionType().getTrxCode().equalsIgnoreCase("TOPUP")) {
            holder.tvProductType.setText("Top Up (" + item.getInvestmentAccount() + ")");
        }

        holder.edtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){

                if (!s.toString().equalsIgnoreCase("")){

                    CartListResponse objItem = (CartListResponse) holder.itemView.getTag();

                    for (int i = 0; i < objItem.getFeeList().size(); i++){

//                      Long amount = Integer.parseInt(s.toString());
                        Long amount = Long.parseLong(s.toString());

                        Fee fee = objItem.getFeeList().get(i);
                        Long amountMin = fee.getAmountMin().longValue();
                        Long amountMax = fee.getAmountMax().longValue();
                        if (amountMax == 0)
                            amountMax = amount + 1;

                        if (amount >= amountMin && amount <= amountMax) {
                            holder.edtFee.setText(BigDecimal.valueOf(fee.getFeeAmount() * 100).toPlainString() + "%");

                            Double total = amount + (amount * fee.getFeeAmount());
                            /*holder.edtTotal.setText(BigDecimal.valueOf(total).toPlainString());*/
                            holder.edtTotal.setText(AmountFormatter.format(total));
                            holder.total = total;
                        }

                    }

                } else {
                    /*holder.edtAmount.setText("0");*/
                    holder.edtFee.setText("0%");
                    holder.edtTotal.setText("0");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        holder.bDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                presenter.deleteCart(item.getTrx().getId());
            }
        });

        holder.bProspectus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                presenter.cartListToDownload(position);
            }
        });

        holderList.add(holder);

    }

    public List<CartListResponse> getList() {

        for (int i = 0; i < list.size(); i++){
            CartListResponse it = list.get(i);
            CartHolder ho = holderList.get(i);

            it.setTransactionAmount(ho.edtAmount.getText().toString());
            it.setFeePercentage(ho.edtFee.getText().toString());
            /*it.setFeePrice(Double.parseDouble(ho.edtTotal.getText().toString()) - Double.parseDouble(ho.edtAmount.getText().toString()));*/

            //new
            if(!ho.edtAmount.getText().toString().equals("0") && !ho.edtAmount.getText().toString().equals("") && ho.total != null){
                it.setFeePrice(ho.total - Double.parseDouble(ho.edtAmount.getText().toString()));
            }else{
                it.setFeePrice(0.0);
            }

            /*it.setTotal(ho.edtTotal.getText().toString());*/
            if(ho.total != null){
                BigDecimal totalConvert = BigDecimal.valueOf(ho.total);
                it.setTotal(totalConvert.toString());
            }else{
                it.setTotal("0");
            }

            Log.d("id", "getList: " + it.getPackage().getId());
            list.set(i, it);
        }


        return list;
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class CartHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.bProspectus)
        ImageButton bProspectus;
        @Bind(R.id.bDelete)
        ImageButton bDelete;
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

        private Double total;
/*
        @Bind(R.id.edtTotalWrapper)
        TextInputLayout edtTotalWrapper;
*/

        public CartHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
