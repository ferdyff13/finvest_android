package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.CartList;
import com.invisee.finvest.ui.fragments.checkout.CartDetailFragment;

import butterknife.Bind;
import icepick.State;

public class CheckoutActivity extends BaseActivity {


    private final static String CART_LIST = "cartList";

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
    public CartList cartList;

    @State
    public int step = 1;

    public static void startActivity(BaseActivity sourceActivity, CartList cartList) {
        Intent intent = new Intent(sourceActivity, CheckoutActivity.class);
        intent.putExtra(CART_LIST, cartList);
        sourceActivity.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.a_checkout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cartList = (CartList) getIntent().getSerializableExtra(CART_LIST);

        setSupportActionBar(toolbar);
        setTitle("");

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title.setText("Checkout");

        CartDetailFragment.showFragment(this, cartList);
        setupStep();
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
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(CheckoutActivity.this, CartActivity.class));
        CheckoutActivity.this.finish();
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
