package com.invisee.finvest.ui.fragments.checkout;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.CartList;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.CheckoutActivity;
import com.invisee.finvest.ui.adapters.rv.CartDetailAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by fajarfatur on 1/22/16.
 */
public class CartDetailFragment extends BaseFragment {

    public static final String TAG = CartDetailFragment.class.getSimpleName();
    private final static String CART_LIST = "cartList";

    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.txvTotal)
    TextView txvTotal;

    @State
    public CartList cartList;

    public static void showFragment(BaseActivity sourceActivity, CartList cartList) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);

            CartDetailFragment fragment = new CartDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(CART_LIST, cartList);
            fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.container, fragment, TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_detail_cart;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartList = (CartList) getArguments().getSerializable(CART_LIST);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadList();

        txvTotal.setText(getTotal());

    }

    public void loadList() {
        rv.setAdapter(new CartDetailAdapter(getActivity(), cartList.getCartList(), this));
    }

    public String getTotal() {
        Double total = 0d;

        for (int i = 0; i < cartList.getCartList().size(); i++) {

            Double t = Double.parseDouble(cartList.getCartList().get(i).getTotal());
            total += t;

        }

        NumberFormat nf = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);

        return "Rp " + nf.format(total);
    }


    @OnClick(R.id.bCheckout)
    void onClickCheckout() {
        ((CheckoutActivity) getActivity()).setStep(2);
        PaymentListFragment.showFragment((CheckoutActivity) getActivity(), cartList);
    }

}
