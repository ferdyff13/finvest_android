package com.invisee.finvest.ui.fragments.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.responses.PortfolioInvestmentListResponse;
import com.invisee.finvest.data.api.responses.PortfolioInvestmentSummaryResponse;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.ui.fragments.catalogue.ListOfCatalogueFragment;
import com.invisee.finvest.ui.fragments.portfolio.ListPortfolioFragment;
import com.invisee.finvest.util.AmountFormatter;
import com.invisee.finvest.util.eventBus.RxBusObject;




import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;

import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by fajarfatur on 1/22/16.
 */
public class MyPortfolioFragment extends BaseFragment {

    @Bind(R.id.pieChart)
    PieChartView pieChartView;
    @Bind(R.id.llPortfolio)
    LinearLayout llPortfolio;
    @Bind(R.id.llNoPortfolio)
    LinearLayout llNoPortfolio;
    @Bind(R.id.etTotalInv)
    EditText etTotalInv;
    @Bind(R.id.etTotalMarketValue)
    EditText etTotalMarketValue;
    @Bind(R.id.etTotalGainLoss)
    EditText etTotalGainLoss;
    @Bind(R.id.rv)
    RecyclerView rv;
    @State
    PortfolioInvestmentListResponse investmentList;
    @State
    PortfolioInvestmentSummaryResponse investmentSummary;

    ListPortfolioFragment fragment;

    private MyPortfolioPresenter presenter;
    private boolean activityCreated, visibleToUser;

    @Override
    protected int getLayout() {
        return R.layout.f_my_portfolio;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MyPortfolioPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initRV();
        if (investmentList != null) {
            loadInvestmentSummary();
        } else {
            presenter.getInvestmentList();

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activityCreated = true;
        if (visibleToUser) {
            getBus().send(new RxBusObject(RxBusObject.RxBusKey.SELECTED_DASHBOARD_MENU, 0));
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            visibleToUser = true;
            if (activityCreated) {
                getBus().send(new RxBusObject(RxBusObject.RxBusKey.SELECTED_DASHBOARD_MENU, 0));
            }
        }else{
            visibleToUser = false;
        }
    }

    private void initRV() {
        LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llManager);
    }

    void loadInvestmentSummary() {
        double totalInv = investmentSummary.getTotalInvestment();
        double totalMarketValue = investmentSummary.getTotalMarketValue();
        double totalGainLoss = totalMarketValue - totalInv;
        etTotalInv.setText(AmountFormatter.format(totalInv));
        etTotalMarketValue.setText(AmountFormatter.format(totalMarketValue));
        etTotalGainLoss.setText(AmountFormatter.format(totalGainLoss));
    }

/*    void renderPieChart(List<PortfolioChartData> datas){
        rv.setAdapter(new PieChartDescAdapter(getActivity(), datas));
        int totalSlices = datas.size();
        List<SliceValue> values = new ArrayList<>();
        for (int i = 0; i < totalSlices; ++i) {
            SliceValue sliceValue = new SliceValue(Float.valueOf(datas.get(i).getComposition()),
                    Color.parseColor(datas.get(i).getFundTypeColors()));
            values.add(sliceValue);
        }

        PieChartData pieChartData = new PieChartData(values);
        pieChartData.setHasLabels(true);
        pieChartData.setHasCenterCircle(true);

        pieChartView.setPieChartData(pieChartData);
        pieChartView.setValueSelectionEnabled(false);

    }*/

    void noPortfolio(boolean b) {
        llNoPortfolio.setVisibility(b ? View.VISIBLE : View.GONE);
        llPortfolio.setVisibility(b ? View.GONE : View.VISIBLE);
    }

    @OnClick(R.id.bStartToInvest)
    void startToInvest() {
        ListOfCatalogueFragment.showFragment((BaseActivity) getActivity());
    }
}
