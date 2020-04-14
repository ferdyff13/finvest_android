package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.fragments.userProfile.FatcaFragment;
import com.invisee.finvest.util.eventBus.RxBusObject;

import butterknife.Bind;

/**
 * Created by pandu.abbiyuarsyah on 06/04/2017.
 */

public class FatcaActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    public static void startActivity(BaseActivity sourceActivity) {
        Intent intent = new Intent(sourceActivity, FatcaActivity.class);
        sourceActivity.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.a_fatca;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        setTitle("");

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title.setText("FATCA & CRS");

        FatcaFragment.showFragment(this);
    }

    @Override
    public void onBackPressed() {
        Snackbar snackbar = Snackbar.make(FatcaActivity.this.findViewById(android.R.id.content), R.string.user_profile_exit, Snackbar.LENGTH_LONG).setAction(R.string.yes, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FatcaActivity.this, UserProfileActivity.class));
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

    @Override
    public void busHandler(RxBusObject.RxBusKey busKey, Object busObject) {
        super.busHandler(busKey, busObject);
        switch (busKey) {
            case NEXT_TO_RISK_PROFILE:
                toNextForm();
                break;

        }
    }

    public void toNextForm() {
        RiskProfileActivity.startActivity(this);
    }


}
