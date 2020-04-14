package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.PortfolioInvestment;
import com.invisee.finvest.data.api.responses.FundAllocationResponse;
import com.invisee.finvest.ui.fragments.portfolio.PortfolioConfirmationFragment;

import butterknife.Bind;
import icepick.State;

/**
 * Created by fajarfatur on 3/2/16.
 */
public class PortfolioConfirmationActivity extends BaseActivity {

    private static final String INVESTMENT = "portfolio";
    private static final String PACKAGES = "packages";
    private static final String FUND_ALLOC = "fundAlloc";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;
    @State
    PortfolioInvestment investment;
    @State
    Packages packages;
    @State
    FundAllocationResponse fundAlloc;

//    private PortfolioInvestment investment;

    public static void startActivity(BaseActivity sourceActivity, PortfolioInvestment investment, Packages packages, FundAllocationResponse fundAlloc) {
        Intent intent = new Intent(sourceActivity, PortfolioConfirmationActivity.class);
        intent.putExtra(INVESTMENT, investment);
        intent.putExtra(PACKAGES, packages);
        intent.putExtra(FUND_ALLOC, fundAlloc);
        sourceActivity.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.a_portfolio;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        investment = (PortfolioInvestment) getIntent().getSerializableExtra(INVESTMENT);
        if (packages == null)
            packages = (Packages) getIntent().getSerializableExtra(PACKAGES);
        if (fundAlloc == null)
            fundAlloc = (FundAllocationResponse) getIntent().getSerializableExtra(FUND_ALLOC);
        title.setText(investment.getPackageName());
        PortfolioConfirmationFragment.showFragment(this, investment, packages, fundAlloc);
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
        startActivity(new Intent(PortfolioConfirmationActivity.this, ListPortfolioActivity.class));
        finish();
    }
}
