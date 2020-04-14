package com.invisee.finvest.ui.adapters.table;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.invisee.finvest.data.api.beans.PortfolioProductComposition;
import com.invisee.finvest.util.AmountFormatter;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;

/**
 * Created by fajarfatur on 3/4/16.
 */
public class FundAllocationTableAdapter extends TableDataAdapter<PortfolioProductComposition> {

    private static final int TEXT_SIZE = 16;

    public FundAllocationTableAdapter(Context context, List<PortfolioProductComposition> data) {
        super(context, data);
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        PortfolioProductComposition product = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderString(product.getIndividualFundName());
                break;
            case 1:
                renderedView = renderString(AmountFormatter.format(product.getCurrentUnit()));
                break;
            case 2:
                renderedView = renderString(AmountFormatter.format(product.getMarketValue()));
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
