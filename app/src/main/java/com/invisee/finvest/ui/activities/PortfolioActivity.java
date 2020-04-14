package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.PortfolioInvestment;
import com.invisee.finvest.ui.fragments.portfolio.DetailPortfolioFragment;

import butterknife.Bind;

/**
 * Created by fajarfatur on 3/2/16.
 */
public class PortfolioActivity extends BaseActivity {

    private static final String PORTFOLIO = "portfolio";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    private PortfolioInvestment investment;

    public static void startActivity(BaseActivity sourceActivity, PortfolioInvestment investment) {
        Intent intent = new Intent(sourceActivity, PortfolioActivity.class);
        intent.putExtra(PORTFOLIO, investment);
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
        investment = (PortfolioInvestment) getIntent().getSerializableExtra(PORTFOLIO);
        title.setText(investment.getPackageName());
        DetailPortfolioFragment.showFragment(this, investment);
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
        ListPortfolioActivity.startActivity(this);
        this.finish();
    }
}
