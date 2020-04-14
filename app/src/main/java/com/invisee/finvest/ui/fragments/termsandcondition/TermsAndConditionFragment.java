package com.invisee.finvest.ui.fragments.termsandcondition;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.MainActivity;
import com.invisee.finvest.ui.activities.SignUpActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;

/**
 * Created by pandu.abbiyuarsyah on 10/03/2017.
 */

public class TermsAndConditionFragment extends BaseFragment {

    public static final String TAG = TermsAndConditionFragment.class.getSimpleName();


    public static void showFragment(BaseActivity sourceActivity) {

        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.container, new TermsAndConditionFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    public static void showFragment(BaseActivity sourceActivity, int code) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.container, new TermsAndConditionFragment(), TAG);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_termsandcondition;
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Syarat dan Ketentuan");
    }

}
