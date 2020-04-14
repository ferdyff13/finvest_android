package com.invisee.finvest.ui.adapters.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.invisee.finvest.data.api.requests.KycDataRequest;
import com.invisee.finvest.ui.fragments.userProfile.KycDataFragment;
import com.invisee.finvest.ui.fragments.userProfile.KycHomeAddressFragment;
import com.invisee.finvest.ui.fragments.userProfile.KycInvestmentProfileFragment;
import com.invisee.finvest.ui.fragments.userProfile.KycLegalAddressFragment;
import com.invisee.finvest.ui.fragments.userProfile.KycSettlementFragment;

/**
 * Created by fajarfatur on 2/9/16.
 */
public class KycPagerAdapterNew extends FragmentPagerAdapter {

    private static final String TAG = "KycPagerAdapterNew";
    private KycDataRequest request;
    private int totalPage;


    public KycPagerAdapterNew(FragmentManager fm, KycDataRequest request, int totalPage) {
        super(fm);
        this.totalPage = totalPage;
        this.request = request;
    }

    @Override
    public int getCount() {
        return this.totalPage;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = KycDataFragment.initiateFragment(request);
                break;
            case 1:
                fragment = KycHomeAddressFragment.initiateFragment(request);
                break;
            case 2:
                fragment = KycLegalAddressFragment.initiateFragment(request);
                break;
            case 3:
                fragment = KycInvestmentProfileFragment.initiateFragment(request);
                break;
            case 4:
                fragment = KycSettlementFragment.initiateFragment(request);
                break;
        }
        return fragment;
    }

}
