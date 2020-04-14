package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.fragments.Referral.ReferralDetailFragment;

import butterknife.Bind;

/**
 * Created by pandu.abbiyuarsyah on 12/02/2018.
 */

public class ReferralActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    public static void startActivity(BaseActivity sourceActivity) {
        Intent intent = new Intent(sourceActivity, ReferralActivity.class);
        sourceActivity.startActivity(intent);

    }


    @Override
    protected int getLayout() {
        return R.layout.a_base;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolbar();
        ReferralDetailFragment.showFragment(this);
    }

    public void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        title.setText("Referral");
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
        startActivity(new Intent(ReferralActivity.this, MainActivity.class));
        finish();
    }


}
