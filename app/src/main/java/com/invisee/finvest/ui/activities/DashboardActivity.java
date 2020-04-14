package com.invisee.finvest.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.fragments.dashboard.DetailDashboardFragment;

import butterknife.Bind;

/**
 * Created by ivan.pradana on 3/7/2017.
 */

public class DashboardActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    @Override
    protected int getLayout() {
        return R.layout.a_dashboard;
    }

    public static void startActivity(BaseActivity sourceActivity) {
        Intent intent = new Intent(sourceActivity, DashboardActivity.class);
        sourceActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        setTitle("");
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showProgressDialog(loading);
        DetailDashboardFragment.showFragment(this);
        dismissProgressDialog();
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
        startActivity(new Intent(DashboardActivity.this, MainActivity.class));
        finish();
    }
}

