package com.invisee.finvest.ui.fragments.dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.PieChartDataDashboard;
import com.invisee.finvest.data.api.responses.PortfolioInvestmentListResponse;
import com.invisee.finvest.data.api.responses.PortfolioInvestmentSummaryResponse;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.adapters.rv.PieChartDescAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.ui.fragments.catalogue.ListOfCatalogueFragment;
import com.invisee.finvest.ui.fragments.portfolio.ListPortfolioFragment;
import com.invisee.finvest.util.AmountFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


/**
 * Created by ivan.pradana on 3/8/2017.
 */

public class DetailDashboardFragment extends BaseFragment{
    public static final String TAG = DetailDashboardFragment.class.getSimpleName();
    private DetailDashboardPresenter presenter;

    @Bind(R.id.pieChart)
    PieChartView pieChartView;
    @Bind(R.id.llPortfolio)
    LinearLayout llPortfolio;
    @Bind(R.id.llNoPortfolio)
    LinearLayout llNoPortfolio;
    @Bind(R.id.tv_TotalInv)
    TextView tvTotalInv;
    @Bind(R.id.tv_TotalMarketValue)
    TextView tvTotalMarketValue;
    @Bind(R.id.tv_TotalGainLoss)
    TextView tvTotalGainLoss;
    @Bind(R.id.tv_Profile)
    TextView tvProfile;
    @Bind(R.id.rv_detail_dashboard)
    RecyclerView rv;
    @Bind(R.id.dashboard_icon_down)
    ImageView iv_ic_down;
    @Bind(R.id.dashboard_icon_up)
    ImageView iv_ic_up;
    @Bind(R.id.lnProgressBar)
    LinearLayout lnProgressBar;
    @Bind(R.id.lnDismissBar)
    RelativeLayout lnDismissBar;
    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;
    @Bind(R.id.lnConnectionError)
    LinearLayout lnConnectionError;

    @State
    PortfolioInvestmentListResponse investmentList;
    @State
    PortfolioInvestmentSummaryResponse investmentSummary;

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            fragmentTransaction.replace(R.id.container, new DetailDashboardFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_detail_dashboard;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Profile Investasi");
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new DetailDashboardPresenter(this);
        presenter.investmentList();
    }


    void noPortfolio(boolean b) {
        llNoPortfolio.setVisibility(b ? View.VISIBLE : View.GONE);
        llPortfolio.setVisibility(b ? View.GONE : View.VISIBLE);
    }

    @OnClick(R.id.bStartToInvest)
    void startToInvest() {
        ListOfCatalogueFragment.showFragment((BaseActivity) getActivity());
    }

    void loadInvestmentSummary() {
        double totalInv = investmentSummary.getTotalInvestment();
        double totalMarketValue = investmentSummary.getTotalMarketValue();
        double totalGainLoss = totalMarketValue - totalInv;

        iv_ic_up.setVisibility(totalGainLoss >= 0 ? View.VISIBLE : View.GONE);
        iv_ic_down.setVisibility(totalGainLoss < 0 ? View.VISIBLE : View.GONE);

        tvTotalInv.setText(AmountFormatter.format(totalInv));
        tvTotalMarketValue.setText(AmountFormatter.format(totalMarketValue));
        tvTotalGainLoss.setText(AmountFormatter.format(totalGainLoss));
        tvProfile.setText("Profil Risiko Anda: "+investmentSummary.getRiskProfile().toString());
    }


    void renderPieChartDashboard(final List<PieChartDataDashboard> datas){
        rv.setAdapter(new PieChartDescAdapter(getActivity(), datas));
        int totalSlices = datas.size();
        final List<SliceValue> values = new ArrayList<>();
        for (int i = 0; i < totalSlices; ++i) {
            SliceValue sliceValue = new SliceValue(Float.valueOf(datas.get(i).getMarketValue().floatValue()),
                    Color.parseColor(datas.get(i).getFundTypeColors()));
            values.add(sliceValue);
        }

        final PieChartData pieChartData = new PieChartData(values);
        pieChartData.setHasLabels(true);
        pieChartData.setHasCenterCircle(true);

        pieChartView.setPieChartData(pieChartData);
        pieChartView.setValueSelectionEnabled(false);
        pieChartView.setOnValueTouchListener(new PieChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int arcIndex, SliceValue value) {
                ListPortfolioFragment.showFragment((BaseActivity) getActivity());
            }

            @Override
            public void onValueDeselected() {

            }
        });
    }

    void initRV() {
        LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llManager);
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
        presenter.investmentList();
    }


}
