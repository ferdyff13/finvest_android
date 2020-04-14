package com.invisee.finvest.ui.adapters.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.invisee.finvest.R;

import java.util.ArrayList;

/**
 * Created by pandu.abbiyuarsyah on 07/03/2017.
 */

public class DashboardGridViewAdapter extends BaseAdapter {
    private Context c;
    private ArrayList<String> listimage;
    private ArrayList<Integer> listtext;


    // Constructor
    public DashboardGridViewAdapter(Context c, ArrayList<String> listimage, ArrayList<Integer> listtext) {
        this.listimage = listimage;
        this.listtext = listtext;
        this.c = c;

    }

    public int getCount() {
        return listimage.size();
    }

    public Object getItem(int position) {
        return listimage.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder
    {
        public ImageView imgViewFlag;
        public TextView txtViewTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder view;
        LayoutInflater inflater = LayoutInflater.from(c);


        if(convertView==null)
        {
            view = new ViewHolder();
            convertView = inflater.inflate(R.layout.grid_cell, null);

            view.txtViewTitle = (TextView) convertView.findViewById(R.id.textView1);
            view.imgViewFlag = (ImageView) convertView.findViewById(R.id.imageView1);

            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }

        view.txtViewTitle.setText(listimage.get(position));
        view.imgViewFlag.setImageResource(listtext.get(position));

        return convertView;
    }


    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.ic_profile_white, R.drawable.ic_catalogue_white,
            R.drawable.ic_catalogue_white, R.drawable.ic_reminder_white,
            R.drawable.ic_transaction_white, R.drawable.ic_profile_white,

    };

}
