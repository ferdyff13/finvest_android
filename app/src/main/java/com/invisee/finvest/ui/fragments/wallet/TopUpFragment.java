package com.invisee.finvest.ui.fragments.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.TopUpBCAActivity;
import com.invisee.finvest.ui.activities.TopUpFinPayActivity;
import com.invisee.finvest.ui.activities.TopUpMandiriActivity;
import com.invisee.finvest.ui.activities.TopUpPermataActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;

import butterknife.OnClick;


/**
 * Created by ivan.pradana on 3/9/2017.
 */

public class TopUpFragment extends BaseFragment {
    public static final String TAG = TopUpFragment.class.getSimpleName();


    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.container, new TopUpFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_topup;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick(R.id.lnMandiri)
    void goToMandiri() {
        TopUpMandiriActivity.startActivity((BaseActivity) getActivity());
    }

    @OnClick(R.id.lnPermata)
    void goToPermata() {
        TopUpPermataActivity.startActivity((BaseActivity) getActivity());
    }

    @OnClick(R.id.lnBCA)
    void goToBCA() {
        TopUpBCAActivity.startActivity((BaseActivity) getActivity());
    }

    @OnClick(R.id.lnFinPay)
    void gotoFinPay(){
        TopUpFinPayActivity.startActivity((BaseActivity) getActivity());
    }

}
