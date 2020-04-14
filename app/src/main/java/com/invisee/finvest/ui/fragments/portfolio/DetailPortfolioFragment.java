package com.invisee.finvest.ui.fragments.portfolio;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.PortfolioInvestment;
import com.invisee.finvest.data.api.responses.FundAllocationResponse;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.PortfolioConfirmationActivity;
import com.invisee.finvest.ui.activities.RedemptionActivity;
import com.invisee.finvest.ui.adapters.pager.DetailPortfolioAdapter;
import com.invisee.finvest.ui.adapters.pager.KycPagerAdapterNew;
import com.invisee.finvest.ui.fragments.BaseFragment;

import butterknife.Bind;
import butterknife.OnClick;
import lecho.lib.hellocharts.model.Line;

/**
 * Created by fajarfatur on 3/1/16.
 */
public class DetailPortfolioFragment extends BaseFragment {

    public static final String TAG = DetailPortfolioFragment.class.getSimpleName();
    private static final String PORTFOLIO = "portfolio";

    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.lnTopUp)
    LinearLayout lnTopUp;
    @Bind(R.id.lnProgressBar)
    LinearLayout lnProgressBar;
    @Bind(R.id.lnDismissBar)
    RelativeLayout lnDismissBar;
    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;
    @Bind(R.id.lnConnectionError)
    LinearLayout lnConnectionError;


    public PortfolioInvestment investment;
    public Packages packages;
    public FundAllocationResponse fundAlloc;
    private DetailPortfolioAdapter detailPortfolioAdapter;


    public static void showFragment(BaseActivity sourceActivity, PortfolioInvestment investment) {

        if (!sourceActivity.isFragmentNotNull(TAG)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(PORTFOLIO, investment);
            DetailPortfolioFragment fragment = new DetailPortfolioFragment();
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment, TAG);
            fragmentTransaction.commit();
        }

    }

    private DetailPortfolioPresenter presenter;

    @Override
    protected int getLayout() {
        return R.layout.f_detail_portfolio;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        investment = (PortfolioInvestment) getArguments().get(PORTFOLIO);
        presenter = new DetailPortfolioPresenter(this);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.packageDetail(investment);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.cartList();

    }

    public void setupViewPager() {
        detailPortfolioAdapter = new DetailPortfolioAdapter(getFragmentManager(), investment, packages);
        pager.setAdapter(detailPortfolioAdapter);

    }

    public void setupTabLayout() {
        tabs.setupWithViewPager(pager);
    }

    @OnClick(R.id.lnRedemption)
    void redemption() {
        presenter.checkRedemptionTransaction(investment.getInvestmentAccountNumber());
    }

    @OnClick(R.id.lnTopUp)
    void topUp() {
        presenter.topUpValidation(investment.getInvestmentAccountNumber());
    }

    void redemptionGranted() {
        RedemptionActivity.startActivity((BaseActivity) getActivity(), investment, packages);
    }

    void portfolioConfirmation() {
        Log.d("invesment", "onCreate: "+ investment.getInvestmentAccountNumber());
//       PortfolioConfirmationFragment.showFragment((BaseActivity) getActivity(), investment, packages, fundAlloc);
       PortfolioConfirmationActivity.startActivity((BaseActivity) getActivity(), investment, packages, fundAlloc);
    }

    public void showConfirmation(String info) {
        new MaterialDialog.Builder(getActivity())
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(R.string.redeemtion_failed_title)
                .titleColor(Color.WHITE)
                .content(info)
                .contentGravity(GravityEnum.CENTER)
                .contentColor(Color.WHITE)
                .positiveText(R.string.redeemtion_failed_tutup)
                .positiveColor(Color.WHITE)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .cancelable(true)
                .show();
    }

    public void showProgressBar(){
        pbLoading.setVisibility(View.VISIBLE);
        lnConnectionError.setVisibility(View.GONE);
        lnProgressBar.setVisibility(View.VISIBLE);
        lnDismissBar.setVisibility(View.GONE);
    }

    public void dismissProgressBar(){
        lnProgressBar.setVisibility(View.GONE);
        lnDismissBar.setVisibility(View.VISIBLE);
    }

    public void connectionError() {
        lnProgressBar.setVisibility(View.VISIBLE);
        lnDismissBar.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
        lnConnectionError.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tvTryAgain)
    void retryConnection() {
        presenter.packageDetail(investment);
    }

}
