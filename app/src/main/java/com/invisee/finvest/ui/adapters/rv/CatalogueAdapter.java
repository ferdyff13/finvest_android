package com.invisee.finvest.ui.adapters.rv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.beans.ProductList;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.CatalogueActivity;
import com.invisee.finvest.util.AmountFormatter;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fajarfatur on 1/14/16.
 */
public class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.CatalogueHolder> {

    private Context context;
    private List<ProductList> list;

    public CatalogueAdapter(Context context, List<ProductList> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CatalogueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_catalogue, parent, false);
        CatalogueHolder catalogueHolder = new CatalogueHolder(itemView);
        return catalogueHolder;
    }

    @Override
    public void onBindViewHolder(CatalogueHolder holder, int position) {
        final ProductList item = list.get(position);
        holder.itemView.setTag(item);
        holder.tvProductName.setText(item.getName());
        double lastNav = 0;
        if (item.getLastNav() != null){
            lastNav = item.getLastNav();
        } else {
            lastNav = 0;
        }

        if (item.getName().contains("TERPROTEKSI")) {
            holder.tvPrice.setVisibility(View.GONE);
        } else {
            holder.tvPrice.setText(AmountFormatter.format(lastNav));
        }

        if (item.getName().contains("TERPROTEKSI")) {
            holder.tvFund.setText("Indikasi Imbal Hasil per Tahun");
        } else {
            holder.tvFund.setText("Tingkat Pengembalian " + item.getTotalFund() + " Tahun");
        }


        if (item.getLastNavDate() == null){
            if (item.getName().contains("TERPROTEKSI")) {
                holder.tvNab.setVisibility(View.GONE);
            } else {
                holder.tvNab.setText("NAB per tanggal " +"-");
            }
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",    Locale.getDefault());
            Date date3 = null;
            try {
                date3 = sdf.parse(item.getLastNavDate().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String convertedDate= formatter.format(date3);

            if (item.getName().contains("TERPROTEKSI")) {
                holder.tvNab.setVisibility(View.GONE);
            } else {
                holder.tvNab.setText("NAB per tanggal " +convertedDate);
            }


        }

        Picasso.with(context).load(InviseeService.IMAGE_DOWNLOAD_URL + item.getImage()).into(holder.ivProduct);

        DecimalFormat df = new DecimalFormat("0.000");

        Double percentage = null;
        if(item.getPerfOneYear() != null){
            percentage = item.getPerfOneYear() * 100;
        }else{
            percentage = 0.0;
        }

        if (item.getName().contains("TERPROTEKSI")) {
            holder.tvRate.setText("7.5 %");
        } else {
            holder.tvRate.setText(String.format("%.2f", percentage) + " " + "%");
        }

        holder.bShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CatalogueActivity.startActivity((BaseActivity) context, item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class CatalogueHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.showMore)
        LinearLayout bShowMore;
        @Bind(R.id.tvProductName)
        TextView tvProductName;
        @Bind(R.id.tvRate)
        TextView tvRate;
        @Bind(R.id.ivProduct)
        ImageView ivProduct;
        @Bind(R.id.tvPrice)
        TextView tvPrice;
        @Bind(R.id.tvNAB)
        TextView tvNab;
        @Bind(R.id.tvFund)
        TextView tvFund;

        public CatalogueHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
