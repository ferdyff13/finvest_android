package com.invisee.finvest.ui.adapters.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.ui.fragments.catalogue.BusinessRuleFragment;
import com.invisee.finvest.ui.fragments.catalogue.FundAllocationFragment;
import com.invisee.finvest.ui.fragments.catalogue.PackagePerformanceFragment;

import java.util.HashMap;
import java.util.Map;

public class DetailOfCataloguePagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private BaseFragment bFragment;
    private Packages packages;

    public DetailOfCataloguePagerAdapter(BaseFragment bFragment, FragmentManager fm, Packages packages) {
        super(fm);

        this.bFragment = bFragment;
        this.packages = packages;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = FundAllocationFragment.getFragment(packages);
                break;
            case 1:
                fragment = PackagePerformanceFragment.getFragment(packages);
                break;
            case 2:
                fragment = BusinessRuleFragment.getFragment(packages);
                break;
        }
        return fragment;
    }

}

