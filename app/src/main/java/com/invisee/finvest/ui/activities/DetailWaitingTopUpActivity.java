package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.TopUpTransaction;
import com.invisee.finvest.data.api.beans.TopUpViseePay;
import com.invisee.finvest.ui.fragments.wallet.DetailOfTopUpTransactionFragment;
import com.invisee.finvest.ui.fragments.wallet.TopUpSummaryFragment;

import butterknife.Bind;

/**
 * Created by pandu.abbiyuarsyah on 26/10/2017.
 */

public class DetailWaitingTopUpActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    private static final String TopUp = "DetailWaitingTopUp";

    public static void startActivity(BaseActivity sourceActivity, TopUpTransaction topUpTransaction) {
        Intent intent = new Intent(sourceActivity, DetailWaitingTopUpActivity.class);
        intent.putExtra(TopUp, topUpTransaction);
        sourceActivity.startActivity(intent);
    }


    @Override
    protected int getLayout() {
        return R.layout.a_about;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            title.setText("Menunggu Pembayaran");
        }

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(TopUp))
        DetailOfTopUpTransactionFragment.showFragment(this,  (TopUpTransaction) getIntent().getExtras().getSerializable(TopUp));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        startActivity(new Intent(DetailWaitingTopUpActivity.this, ListTopUpActivity.class));
        DetailWaitingTopUpActivity.this.finish();
    }


}
