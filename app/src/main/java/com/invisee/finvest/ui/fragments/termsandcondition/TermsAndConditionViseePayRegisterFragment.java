package com.invisee.finvest.ui.fragments.termsandcondition;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.webkit.WebView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.SignUpActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;

import butterknife.Bind;

/**
 * Created by pandu.abbiyuarsyah on 19/01/2018.
 */

public class TermsAndConditionViseePayRegisterFragment extends BaseFragment {


    public static final String TAG = TermsAndConditionViseePayRegisterFragment.class.getSimpleName();

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            fragmentTransaction.replace(R.id.container, new TermsAndConditionViseePayRegisterFragment(), TAG);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


    @Bind(R.id.webView)
    WebView webView;

    private String webDev = "http://52.76.229.157:8080";

    @Override
    protected int getLayout() {
        return R.layout.f_terms_and_conditions_viseepay;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((SignUpActivity) getActivity()).setActionBarTitle("Syarat dan Ketentuan");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView.getSettings().setJavaScriptEnabled(true);
        if (InviseeService.BASE_URL.contains(webDev)) {
            webView.loadUrl("http://52.76.229.157:8080/customer/views/popup/viseepay.html");
        } else {
            webView.loadUrl("https://invisee.com/views/popup/viseepay.html");
        }

    }

}
