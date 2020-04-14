package com.invisee.finvest.ui.adapters.rv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.pojo.Menu;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fajarfatur on 1/14/16.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerHolder> {

    private Context context;
    private List<Menu> menuList;

    public DrawerAdapter(Context context, List<Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @Override
    public DrawerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_drawer, parent, false);
        DrawerHolder drawerHolder = new DrawerHolder(itemView);
        return drawerHolder;
    }

    @Override
    public void onBindViewHolder(DrawerHolder holder, int position) {
        final Menu menu = menuList.get(position);
        holder.itemView.setTag(menu);
        holder.ivIcon.setImageDrawable(menu.getIcon());
        holder.tvName.setText(menu.getName());
    }

    @Override
    public int getItemCount() {
        return menuList != null ? menuList.size() : 0;
    }

    public static class DrawerHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ivIcon)
        ImageView ivIcon;
        @Bind(R.id.tvName)
        TextView tvName;

        public DrawerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
