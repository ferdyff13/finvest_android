package com.invisee.finvest.ui.fragments.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.webkit.WebView;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;

/**
 * Created by pandu.abbiyuarsyah on 18/10/2017.
 */

public class TopUpPermataFragment extends BaseFragment {

    public static final String TAG = TopUpPermataFragment.class.getSimpleName();

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.container, new TopUpPermataFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_top_up_permata;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        WebView wv1 = (WebView) getActivity().findViewById(R.id.webViewTopUp);
//        wv1.getSettings().setJavaScriptEnabled(true);
//        wv1.loadUrl("https://invisee.com/views/popup/wallet.html");
    }



}
