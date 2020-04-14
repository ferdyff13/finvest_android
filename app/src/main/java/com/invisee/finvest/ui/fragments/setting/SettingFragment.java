package com.invisee.finvest.ui.fragments.setting;

import android.support.v4.app.FragmentTransaction;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;

/**
 * Created by fajarfatur on 3/6/16.
 */
public class SettingFragment extends BaseFragment {

    public static final String TAG = SettingFragment.class.getSimpleName();

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.container, new SettingFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_setting;
    }
}
