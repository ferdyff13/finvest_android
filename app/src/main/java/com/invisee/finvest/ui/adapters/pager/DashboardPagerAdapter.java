package com.invisee.finvest.ui.adapters.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.invisee.finvest.ui.fragments.dashboard.MyPortfolioFragment;
import com.invisee.finvest.ui.fragments.dashboard.NewsFeedFragment;
import com.invisee.finvest.ui.fragments.dashboard.PendingOrderFragment;

/**
 * Created by fajarfatur on 1/14/16.
 */
public class DashboardPagerAdapter extends FragmentPagerAdapter {

    public DashboardPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MyPortfolioFragment();
                break;
            case 1:
                fragment = new PendingOrderFragment();
                break;
            case 2:
                fragment = new NewsFeedFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
