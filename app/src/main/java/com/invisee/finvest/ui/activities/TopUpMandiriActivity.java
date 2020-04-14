package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.fragments.wallet.TopUpMandiriFragment;

import butterknife.Bind;

/**
 * Created by pandu.abbiyuarsyah on 27/10/2017.
 */

public class TopUpMandiriActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    public static void startActivity(BaseActivity sourceActivity) {
        Intent intent = new Intent(sourceActivity, TopUpMandiriActivity.class);
        sourceActivity.startActivity(intent);
    }


    @Override
    protected int getLayout() {
        return R.layout.a_top_up_mandiri;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TopUpMandiriFragment.showFragment(this);
        setupToolbar();
    }

    public void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        title.setText(R.string.title_mandiri);
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
        startActivity(new Intent(TopUpMandiriActivity.this, TopUpActivity.class));
        TopUpMandiriActivity.this.finish();
    }
}
