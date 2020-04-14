package com.invisee.finvest.ui.adapters.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.PortfolioInvestment;
import com.invisee.finvest.ui.fragments.portfolio.PortfolioDetailFragment;
import com.invisee.finvest.ui.fragments.portfolio.PortfolioFundAlocationFragment;
import com.invisee.finvest.ui.fragments.portfolio.PortfolioInvesmentPerformanceFragment;

/**
 * Created by fajarfatur on 3/2/16.
 */
public class DetailPortfolioAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 3;
    private static final String[] SUBMENU_TITLE = {"DETAIL", "ALOKASI REKSA DANA", "PERFORMA INVESTASI"};
    private PortfolioInvestment investment;
    private Packages packages;

    public DetailPortfolioAdapter(FragmentManager fm, PortfolioInvestment investment, Packages packages) {
        super(fm);
        this.investment = investment;
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
                fragment = PortfolioDetailFragment.initiateFragment(investment);
                break;
            case 1:
                fragment = PortfolioFundAlocationFragment.initiateFragment(investment, packages);
                break;
            case 2:
                fragment = PortfolioInvesmentPerformanceFragment.initiateFragment(investment);
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return SUBMENU_TITLE[position];
    }
}

