package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.ProductList;
import com.invisee.finvest.ui.fragments.catalogue.DetailOfCatalogueFragment;

import butterknife.Bind;
import icepick.State;

public class CatalogueActivity extends BaseActivity {

    private static final String PACKAGES = "packages";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    @State
    Packages packages;

    @State
    ProductList products;

    public static void startActivity(BaseActivity sourceActivity, ProductList packages) {
        Intent intent = new Intent(sourceActivity, CatalogueActivity.class);
        intent.putExtra(PACKAGES, packages);
        sourceActivity.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.a_catalogue;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        products = (ProductList) getIntent().getSerializableExtra(PACKAGES);
        title.setText(products.getName());
        DetailOfCatalogueFragment.showFragment(this, products);
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
        startActivity(new Intent(CatalogueActivity.this, ListOfCatalogueActivity.class));
        CatalogueActivity.this.finish();
    }
}
