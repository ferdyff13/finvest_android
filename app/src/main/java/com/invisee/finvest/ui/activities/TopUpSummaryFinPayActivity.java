package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.TopUpFinPay;
import com.invisee.finvest.ui.fragments.wallet.TopUpSummaryFinPayFragment;

import butterknife.Bind;

public class TopUpSummaryFinPayActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    private static final String TopUpFin = "TopUpFin";

    public static void startActivity(BaseActivity sourceActivity, TopUpFinPay topUpFinPay) {
        Intent intent = new Intent(sourceActivity, TopUpSummaryFinPayActivity.class);
        intent.putExtra(TopUpFin, topUpFinPay);
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

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(TopUpFin))
            TopUpSummaryFinPayFragment.showFragment(this, (TopUpFinPay) getIntent().getExtras().getSerializable(TopUpFin));
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
        startActivity(new Intent(TopUpSummaryFinPayActivity.this, WalletActivity.class));
        TopUpSummaryFinPayActivity.this.finish();
    }
}
