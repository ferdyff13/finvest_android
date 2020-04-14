package com.invisee.finvest.ui.adapters.rv;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.pojo.Menu;

import java.util.List;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.ButterKnife;

/**
 * Created by fajarfatur on 1/14/16.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {

    private Context context;
    private List<Menu> menuList;

    public MenuAdapter(Context context, List<Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu, parent, false);
        MenuHolder menuHolder = new MenuHolder(itemView);
        return menuHolder;
    }

    @Override
    public void onBindViewHolder(MenuHolder holder, int position) {
        final Menu menu = menuList.get(position);
        holder.itemView.setTag(menu);
        holder.ivIcon.setImageDrawable(menu.getIcon());
        holder.tvName.setText(menu.getName());
        holder.llWrapper.setBackground(position % 2 == 0 ? holder.bgMenuLeft : holder.bgMenuRight);
    }

    @Override
    public int getItemCount() {
        return menuList != null ? menuList.size() : 0;
    }

    public static class MenuHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.llWrapper)
        LinearLayout llWrapper;
        @Bind(R.id.ivIcon)
        ImageView ivIcon;
        @Bind(R.id.tvName)
        TextView tvName;
        @BindDrawable(R.drawable.bg_menu_left)
        Drawable bgMenuLeft;
        @BindDrawable(R.drawable.bg_menu_right)
        Drawable bgMenuRight;

        public MenuHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
