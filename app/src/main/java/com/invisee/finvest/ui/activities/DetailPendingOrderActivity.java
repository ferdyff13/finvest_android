package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.TransactionHistory;
import com.invisee.finvest.ui.fragments.pending_order.DetailPendingOrderFragment;

import butterknife.Bind;
import icepick.State;

/**
 * Created by pandu.abbiyuarsyah on 30/03/2017.
 */

public class DetailPendingOrderActivity extends BaseActivity {

    private static final String TRANSACTION_HISTORY = "transcationHistory";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    @State
    public TransactionHistory transactionHistory;


    public static void startActivity(BaseActivity sourceActivity, TransactionHistory transactionHistory){
        Intent intent = new Intent(sourceActivity, DetailPendingOrderActivity.class);
        intent.putExtra(TRANSACTION_HISTORY, transactionHistory);
        sourceActivity.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.a_pending_order_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        setTitle("Detail Pesanan");
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        transactionHistory = (TransactionHistory) getIntent().getSerializableExtra(TRANSACTION_HISTORY);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(TRANSACTION_HISTORY))
            DetailPendingOrderFragment.showFragment(this, (TransactionHistory) getIntent().getExtras().getSerializable(TRANSACTION_HISTORY));
        else
            DetailPendingOrderFragment.showFragment(this, null);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(DetailPendingOrderActivity.this, PendingOrderActivity.class));
        finish();
    }
}
