package com.invisee.finvest.ui.fragments.termsandcondition;

import android.support.v4.app.FragmentTransaction;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.activities.BaseActivity;

import com.invisee.finvest.ui.activities.SignUpActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;

/**
 * Created by pandu.abbiyuarsyah on 19/01/2018.
 */

public class TermsAndConditionInviseeFragment extends BaseFragment {

    public static final String TAG = TermsAndConditionInviseeFragment.class.getSimpleName();

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.container, new TermsAndConditionInviseeFragment(), TAG);
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
        ((SignUpActivity) getActivity()).setActionBarTitle("Syarat dan Ketentuan");
    }

}
