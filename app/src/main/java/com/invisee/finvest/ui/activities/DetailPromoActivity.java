package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.ProductList;
import com.invisee.finvest.data.api.beans.PromoResponse;
import com.invisee.finvest.ui.fragments.promo.DetailPromoFragment;

import butterknife.Bind;
import icepick.State;

/**
 * Created by pandu.abbiyuarsyah on 18/05/2017.
 */

public class DetailPromoActivity extends BaseActivity {

    private static final String PROMO = "promo";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    @State
    PromoResponse response;

    public static void startActivity(BaseActivity sourceActivity, PromoResponse response) {
        Intent intent = new Intent(sourceActivity, DetailPromoActivity.class);
        intent.putExtra(PROMO, response);
        sourceActivity.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.a_detail_promo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Detail Promo");
        }

        response = (PromoResponse) getIntent().getSerializableExtra(PROMO);
        /*title.setText(response.getName());*/

        DetailPromoFragment.showFragment(this, response);
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
        startActivity(new Intent(DetailPromoActivity.this, PromoActivity.class));
        finish();
    }
}
