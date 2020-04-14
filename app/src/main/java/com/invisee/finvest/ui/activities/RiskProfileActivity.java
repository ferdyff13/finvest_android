package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.fragments.userProfile.*;
import com.invisee.finvest.util.eventBus.RxBusObject;

import butterknife.Bind;

/**
 * Created by pandu.abbiyuarsyah on 06/04/2017.
 */

public class RiskProfileActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    private static final String TAG = "RiskProfileActivity";

    private RiskProfilePresenter presenter;

    public static void startActivity(BaseActivity sourceActivity) {
        Intent intent = new Intent(sourceActivity, RiskProfileActivity.class);
        sourceActivity.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.a_risk_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        setTitle(R.string.riskProfileTitle);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new RiskProfilePresenter(this);
        RiskProfileFragment.showFragment(this);
    }

    @Override
    public void busHandler(RxBusObject.RxBusKey busKey, Object busObject) {
        super.busHandler(busKey, busObject);
        switch (busKey) {
            case SUBMIT_USER_PROFILE_DATA_CONFIRMATION:
                presenter.submitUserProfile();
                Log.d("Activity", "busHandler: "+"masuk activi success");
                break;
        }
    }

    public void gotoMainActivity() {
        MainActivity.startActivity((BaseActivity) this);
        Log.d(TAG, "gotoMainActivity: "+"out");
    }

    @Override
    public void onBackPressed() {
        Snackbar snackbar = Snackbar.make(RiskProfileActivity.this.findViewById(android.R.id.content), R.string.user_profile_exit, Snackbar.LENGTH_LONG).setAction(R.string.yes, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RiskProfileActivity.this, UserProfileActivity.class));
                finish();
            }
        });
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_action);
        tv.setTypeface(null, Typeface.BOLD);
        snackbar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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

}
