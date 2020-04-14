package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.TopUpViseePay;
import com.invisee.finvest.ui.fragments.wallet.TopUpSummaryFragment;

import butterknife.Bind;

/**
 * Created by pandu.abbiyuarsyah on 19/10/2017.
 */

public class TopUpSummaryActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    private static final String TopUp = "TopUp";

    public static void startActivity(BaseActivity sourceActivity, TopUpViseePay topUpViseePay) {
        Intent intent = new Intent(sourceActivity, TopUpSummaryActivity.class);
        intent.putExtra(TopUp, topUpViseePay);
        sourceActivity.startActivity(intent);
    }


    @Override
    protected int getLayout() {
        return R.layout.a_top_up_summary;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            title.setText("Ringkasan Pengisian Saldo");
        }

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(TopUp))
            TopUpSummaryFragment.showFragment(this, (TopUpViseePay) getIntent().getExtras().getSerializable(TopUp));
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
        startActivity(new Intent(TopUpSummaryActivity.this, WalletActivity.class));
        TopUpSummaryActivity.this.finish();
    }
}
