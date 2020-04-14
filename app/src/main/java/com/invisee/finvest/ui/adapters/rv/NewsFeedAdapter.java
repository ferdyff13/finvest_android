package com.invisee.finvest.ui.adapters.rv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.beans.News;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fajarfatur on 3/6/16.
 */
public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.NewsFeedHolder> {

    private Context context;
    private List<News> list;

    public NewsFeedAdapter(Context context, List<News> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public NewsFeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news, parent, false);
        return new NewsFeedHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsFeedHolder holder, int position) {
        final News item = list.get(position);
        holder.itemView.setTag(item);
        Glide.with(context).load(InviseeService.IMAGE_DOWNLOAD_URL + item.getImageLocation()).into(holder.ivPhoto);
        holder.tvtTime.setText(item.getCreatedDate());
        holder.tvNewsTitle.setText(item.getNewsTitle());
        holder.tvNewsContent.setText(Html.fromHtml(item.getNewsContent()).toString());

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class NewsFeedHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ivPhoto)
        ImageView ivPhoto;
        @Bind(R.id.tvtTime)
        TextView tvtTime;
        @Bind(R.id.tvNewsTitle)
        TextView tvNewsTitle;
        @Bind(R.id.tvNewsContent)
        TextView tvNewsContent;

        public NewsFeedHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
