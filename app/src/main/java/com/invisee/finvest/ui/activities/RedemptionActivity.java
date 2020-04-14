package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.PortfolioInvestment;
import com.invisee.finvest.ui.fragments.redemption.ConfirmationFragment;

import butterknife.Bind;
import icepick.State;

/**
 * Created by fajarfatur on 3/14/16.
 */
public class RedemptionActivity extends BaseActivity {

    private static final String INVESTMENT = "investment";
    private static final String PACKAGES = "packages";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.v1)
    View v1;
    @Bind(R.id.v2)
    View v2;
    @Bind(R.id.v3)
    View v3;
    @Bind(R.id.v4)
    View v4;
    @Bind(R.id.step1)
    ImageView step1;
    @Bind(R.id.step2)
    ImageView step2;
    @Bind(R.id.step3)
    ImageView step3;

    @State
    PortfolioInvestment investment;
    @State
    Packages packages;
    @State
    public int step = 1;

    public static void startActivity(BaseActivity sourceActivity, PortfolioInvestment investment, Packages packages) {
        Intent intent = new Intent(sourceActivity, RedemptionActivity.class);
        intent.putExtra(INVESTMENT, investment);
        intent.putExtra(PACKAGES, packages);
        sourceActivity.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.a_redemption;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        if (investment == null)
            investment = (PortfolioInvestment) getIntent().getSerializableExtra(INVESTMENT);

        if (packages == null)
            packages = (Packages) getIntent().getSerializableExtra(PACKAGES);

        title.setText("Penjualan Kembali");
        ConfirmationFragment.showFragment(this, investment, packages);
        setupStep();
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
        finish();
    }

    public void setStep(int step) {
        this.step = step;
        setupStep();
    }

    void setupStep() {
        switch (step) {
            case 1:
                v1.setVisibility(View.INVISIBLE);
                v2.setVisibility(View.INVISIBLE);
                v3.setVisibility(View.INVISIBLE);
                v4.setVisibility(View.INVISIBLE);

                step2.setImageResource(R.drawable.bg_primary_oval_stroke);
                step3.setImageResource(R.drawable.bg_primary_oval_stroke);
                break;

            case 2:
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.VISIBLE);
                v3.setVisibility(View.INVISIBLE);
                v4.setVisibility(View.INVISIBLE);

                step2.setImageResource(R.drawable.bg_primary_oval);
                step3.setImageResource(R.drawable.bg_primary_oval_stroke);
                break;

            case 3:
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.VISIBLE);
                v3.setVisibility(View.VISIBLE);
                v4.setVisibility(View.VISIBLE);

                step2.setImageResource(R.drawable.bg_primary_oval);
                step3.setImageResource(R.drawable.bg_primary_oval);
                break;
        }
    }
}
