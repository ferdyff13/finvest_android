package com.invisee.finvest.ui.adapters.rv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.beans.ProductList;
import com.invisee.finvest.data.api.beans.PromoResponse;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.DetailPromoActivity;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pandu.abbiyuarsyah on 18/05/2017.
 */

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.PromoHolder> {

    private Context context;
    private List<PromoResponse> list;

    public PromoAdapter(Context context, List<PromoResponse> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public PromoAdapter.PromoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_promo_list, parent, false);
        PromoAdapter.PromoHolder promoHolder = new PromoAdapter.PromoHolder(itemView);
        return promoHolder;
    }

    @Override
    public void onBindViewHolder(PromoHolder holder, int position) {
        final PromoResponse item = list.get(position);
        holder.itemView.setTag(item);

        holder.tvProduct.setText(item.getTitle());
/*        holder.tvTitle.setText(item.getIntrotext());*/

        holder.tvDesc.setHtml(item.getIntrotext());

        Picasso.with(context).load(InviseeService.IMAGE_DOWNLOAD_URL + item.getImage()).into(holder.ivImagePromo);

        holder.lnRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailPromoActivity.startActivity((BaseActivity) context, item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class PromoHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvProduct)
        TextView tvProduct;
/*        @Bind(R.id.tvTitle)
        TextView tvTitle;*/
        @Bind(R.id.ivImagePromo)
        ImageView ivImagePromo;
        @Bind(R.id.lnRow)
        LinearLayout lnRow;
        @Bind(R.id.tvDesc)
        HtmlTextView tvDesc;

        public PromoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
