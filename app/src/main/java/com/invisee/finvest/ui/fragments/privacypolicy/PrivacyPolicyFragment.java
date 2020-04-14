package com.invisee.finvest.ui.fragments.privacypolicy;

import android.support.v4.app.FragmentTransaction;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;

/**
 * Created by pandu.abbiyuarsyah on 13/03/2017.
 */

public class PrivacyPolicyFragment extends BaseFragment {

    public static final String TAG = PrivacyPolicyFragment.class.getSimpleName();


    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.container, new PrivacyPolicyFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_privacy_policy;
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Kebijakan Privasi");
    }

}
