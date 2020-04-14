package com.invisee.finvest.ui.fragments.catalogue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.ProductList;
import com.invisee.finvest.data.api.beans.Products;
import com.invisee.finvest.data.api.responses.CartListResponse;
import com.invisee.finvest.data.api.responses.ProductResponse;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.adapters.rv.CatalogueAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by fajarfatur on 1/22/16.
 */
public class ListOfCatalogueFragment extends BaseFragment {

    public static final String TAG = ListOfCatalogueFragment.class.getSimpleName();

    @Bind(R.id.rv)
    RecyclerView rv;
    @State
    ProductResponse response;
    @Bind(R.id.lnProgressBar)
    LinearLayout lnProgressBar;
    @Bind(R.id.lnDismissBar)
    RelativeLayout lnDismissBar;
    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;
    @Bind(R.id.lnConnectionError)
    LinearLayout lnConnectionError;

    public List<CartListResponse> cartList;

    public int index = 0;

    public List<Products> productsList;
    public List<ProductList> list;
    private List<Products> products = new ArrayList<>();
    private String packagetype = "";
    public Integer temporaaryIndex = 0;

    ListOfCataloguePresenter presenter;

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
        FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragmentTransaction.replace(R.id.container, new ListOfCatalogueFragment(), TAG);
        fragmentTransaction.commit();
    }
}

    @Override
    protected int getLayout() {
        return R.layout.f_list_of_catalogue;
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Produk");
        getCartList();
    }


    public void getCartList() {
        presenter.cartList();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ListOfCataloguePresenter(this);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.productList();

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 1 : 1;
            }
        });

        rv.setLayoutManager(layoutManager);

    }

    public void loadList() {
        rv.setAdapter(new CatalogueAdapter(getActivity(), list));
    }

    public void setSpinner() {
        products = response.getData();
        final List<String> loop = new ArrayList<String>();
        for (int i = 0; i < response.getData().size(); i++) {
            Products state = response.getData().get(i);
            packagetype = state.getPackageType();
            loop.add(packagetype);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item_list, loop);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinner2 = (Spinner) getActivity().findViewById(R.id.sPackageType);
        spinner2.setAdapter(dataAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index = spinner2.getSelectedItemPosition();
                if (temporaaryIndex == index) {

                } else {
                    presenter.productListSelectedItems();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void showProgressBar(){
    pbLoading.setVisibility(View.VISIBLE);
    lnConnectionError.setVisibility(View.GONE);
    lnProgressBar.setVisibility(View.VISIBLE);
    lnDismissBar.setVisibility(View.GONE);
}

    public void dismissProgressBar(){
        lnProgressBar.setVisibility(View.GONE);
        lnDismissBar.setVisibility(View.VISIBLE);
    }

    public void connectionError() {
        lnProgressBar.setVisibility(View.VISIBLE);
        lnDismissBar.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
        lnConnectionError.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tvTryAgain)
    void retryConnection() {
        presenter.productList();
    }
}
