package com.invisee.finvest.ui.adapters.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.invisee.finvest.ui.fragments.userProfile.FatcaFragment;
import com.invisee.finvest.ui.fragments.userProfile.KycFragment;
import com.invisee.finvest.ui.fragments.userProfile.RiskProfileFragment;

/**
 * Created by fajarfatur on 1/14/16.
 */
public class UserProfilePagerAdapter extends FragmentPagerAdapter {

    public UserProfilePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new RiskProfileFragment();
                break;
            case 1:
                fragment = new KycFragment();
                break;
            case 2:
                fragment = new FatcaFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
