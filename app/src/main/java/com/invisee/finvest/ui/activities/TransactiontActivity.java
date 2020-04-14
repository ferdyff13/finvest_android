package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.fragments.transaction.TransactionFragment;

import butterknife.Bind;
import butterknife.BindString;

/**
 * Created by fajarfatur on 1/12/16.
 */
public class TransactiontActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;
    @BindString(R.string.transactionTitle)
    String transactionTitle;

    public static void startActivity(BaseActivity sourceActivity) {
        Intent intent = new Intent(sourceActivity, TransactiontActivity.class);
        sourceActivity.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.a_signup;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolbar();
        TransactionFragment.showFragment(this);
    }

    public void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        title.setText(transactionTitle);
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
        startActivity(new Intent(TransactiontActivity.this, MainActivity.class));
        finish();
    }
}