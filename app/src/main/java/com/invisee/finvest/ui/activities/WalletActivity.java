package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.fragments.wallet.WalletFragment;

import butterknife.Bind;

/**
 * Created by ivan.pradana on 3/8/2017.
 */

public class WalletActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    @Override
    protected int getLayout() {
        return R.layout.a_dashboard;
    }

    public static void startActivity(BaseActivity sourceActivity) {
        Intent intent = new Intent(sourceActivity, WalletActivity.class);
        sourceActivity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolbar();
        WalletFragment.showFragment(this);
    }

    public void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
//        title.setText("");

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
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(WalletActivity.this, MainActivity.class));
        WalletActivity.this.finish();
//        int fragments = getFragmentManager().getBackStackEntryCount();
//        if (fragments == 1) {
//            // make layout invisible since last fragment will be removed
//        }
//        super.onBackPressed();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}


