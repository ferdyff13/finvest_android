package com.invisee.finvest.ui.adapters.table;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.invisee.finvest.data.api.beans.TransactionHistory;
import com.invisee.finvest.util.DateUtil;

import java.util.Date;
import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;

/**
 * Created by fajarfatur on 3/4/16.
 */
public class PendingOrderTableAdapter extends TableDataAdapter<TransactionHistory> {

    private static final int TEXT_SIZE = 14;

    public PendingOrderTableAdapter(Context context, List<TransactionHistory> data) {
        super(context, data);
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        TransactionHistory item = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderString(item.getPackageName());
                break;
            case 1:
                renderedView = renderString(String.valueOf(item.getOrderNumber()));
                break;
            case 2:
                Date dateInviseeFormat = DateUtil.format(item.getTransactionDate(), DateUtil.INVISEE_RETURN_FORMAT);
                try{
                    renderedView = renderString(DateUtil.format(dateInviseeFormat, DateUtil.DD_MMM_YYYY));
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }

        return renderedView;
    }

    private View renderString(String value) {
        TextView textView = new TextView(getContext());
        textView.setText(value);
        textView.setPadding(30, 30, 30, 30);
        textView.setTextSize(TEXT_SIZE);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
