package com.invisee.finvest.ui.fragments.faq;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.webkit.WebView;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;

/**
 * Created by pandu.abbiyuarsyah on 08/03/2017.
 */

public class FaqFragment extends BaseFragment {

    public static final String TAG = FaqFragment.class.getSimpleName();

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.container, new FaqFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_faq;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView wv1 = (WebView) getActivity().findViewById(R.id.webView);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.loadUrl("http://52.76.229.157:8080/customer/#/support/faq");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("FAQ");
    }

}

