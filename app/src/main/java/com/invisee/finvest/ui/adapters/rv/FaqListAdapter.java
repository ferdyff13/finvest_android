package com.invisee.finvest.ui.adapters.rv;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Faq;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fajarfatur on 1/14/16.
 */
public class FaqListAdapter extends RecyclerView.Adapter<FaqListAdapter.FaqListHolder> {

    private Context context;
    private List<Faq> list;
    private Faq item;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public FaqListAdapter(Context context, List<Faq> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public FaqListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_faq, parent, false);
        return new FaqListHolder(v);
    }


    @Override
    public void onBindViewHolder(final FaqListHolder holder, int position) {
        item = list.get(position);
        holder.itemView.setTag(item);

        holder.txvQuestion.setText(item.getQuestion());
        holder.txvAnswer.setText(item.getAnswer());

        final LinearLayout llHide;
        llHide = holder.lineSetHide;

        final ImageView ivArrow;
        ivArrow = holder.ivButton;

        holder.cvFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llHide.getVisibility() == View.GONE) {
                    llHide.setVisibility(View.VISIBLE);
                    ivArrow.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp);
                } else {
                    llHide.setVisibility(View.GONE);
                    ivArrow.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
                }
            }
        });


    }

    //increasing getItemcount to 1. This will be the row of header.
    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class FaqListHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txvQuestion)
        TextView txvQuestion;
        @Bind(R.id.txvAnswer)
        TextView txvAnswer;
        @Bind(R.id.lineHide)
        LinearLayout lineSetHide;
        @Bind(R.id.cvFaq)
        CardView cvFaq;
        @Bind(R.id.ic_arrow)
        ImageView ivButton;


        public FaqListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
