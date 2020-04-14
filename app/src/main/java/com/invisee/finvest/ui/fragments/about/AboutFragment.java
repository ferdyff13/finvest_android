package com.invisee.finvest.ui.fragments.about;

import android.support.v4.app.FragmentTransaction;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.ui.fragments.userProfile.FatcaFragment;

/**
 * Created by pandu.abbiyuarsyah on 23/06/2017.
 */

public class AboutFragment extends BaseFragment {

    public static final String TAG = AboutFragment.class.getSimpleName();


    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            fragmentTransaction.replace(R.id.container, new AboutFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_about;
    }


}
