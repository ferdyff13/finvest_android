package com.invisee.finvest.ui.fragments.portfolio;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.FundAllocation;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.PortfolioInvestment;
import com.invisee.finvest.data.api.beans.PortfolioProductComposition;
import com.invisee.finvest.data.api.responses.FundAllocationResponse;
import com.invisee.finvest.ui.adapters.rv.PortfolioFundAllocationAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.AmountFormatter;
import com.invisee.finvest.util.ui.RecyclerItemClickListener;


import butterknife.Bind;
import butterknife.ButterKnife;
import icepick.State;

/**
 * Created by fajarfatur on 3/2/16.
 */
public class PortfolioFundAlocationFragment extends BaseFragment {

    private static final String PORTFOLIO = "portfolio";
    private static final String PACKAGES = "packages";

    @Bind(R.id.rv)
    RecyclerView rv;

/*    @Bind(R.id.table)
    public TableView table;
    @BindColor(R.color.table_data_row_even)
    public int even;
    @BindColor(R.color.table_data_row_odd)
    public int odd;*/

    private PortfolioFundAlocationPresenter presenter;

    @State
    PortfolioInvestment investment;
    @State
    Packages packages;
    @State
    FundAllocationResponse fundAlloc;
    @State
    PortfolioProductComposition composition;

    public static PortfolioFundAlocationFragment initiateFragment(PortfolioInvestment investment, Packages packages) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PORTFOLIO, investment);
        bundle.putSerializable(PACKAGES, packages);
        PortfolioFundAlocationFragment fragment = new PortfolioFundAlocationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.f_portfolio_fund_alocation;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PortfolioFundAlocationPresenter(this);
        investment = (PortfolioInvestment) getArguments().get(PORTFOLIO);
        packages = (Packages) getArguments().get(PACKAGES);


    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.fundAllocation(packages);
        initRv();
    }

/*    public void initTableView() {
        SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(getActivity(), "Fund Name", "Current Unit", "Market Value");
        simpleTableHeaderAdapter.setTextColor(Color.WHITE);
        simpleTableHeaderAdapter.setTextSize(16);
        simpleTableHeaderAdapter.setPaddings(30, 30, 30, 30);
        table.setHeaderAdapter(simpleTableHeaderAdapter);
        table.setDataRowColoriser(TableDataRowColorizers.alternatingRows(even, odd));
        table.addDataClickListener(new TableDataClickListener<PortfolioProductComposition>() {
            @Override
            public void onDataClicked(int rowIndex, PortfolioProductComposition composition) {
                showDetailProduct(composition, fundAlloc.getData().get(rowIndex));
            }
        });

        table.setDataAdapter(new FundAllocationTableAdapter(getActivity(), investment.getInvestmentComposition()));
    }*/

    public void initRv() {
        LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llManager);

        rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View childView, int position) {
                for (int i = 0;i < investment.getInvestmentComposition().size(); ++i) {
                    PortfolioProductComposition composition = investment.getInvestmentComposition().get(i);
                    showDetailProduct(composition, fundAlloc.getData().get(position));
                }
            }
        }));
    }

    public void loadList() {
        rv.setAdapter(new PortfolioFundAllocationAdapter(investment.getInvestmentComposition(), investment));
    }

    public void showDetailProduct(PortfolioProductComposition composition, FundAllocation fundAllocation) {
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.fund_alloc)
                .customView(R.layout.dialog_fund_allocation_detail, true)
                .positiveText(R.string.close)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).build();

        View view = dialog.getCustomView();
        ((TextView) ButterKnife.findById(view, R.id.tvFundName)).setText(composition.getIndividualFundName());
        ((TextView) ButterKnife.findById(view, R.id.tvType)).setText(composition.getIndividualFundType());
        ((TextView) ButterKnife.findById(view, R.id.tvAllocation)).setText(Double.toString(fundAllocation.getCompositition() * 100) + " %");
        ((TextView) ButterKnife.findById(view, R.id.tvInvestmentManager)).setText(composition.getInvestmentManager());
        ((TextView) ButterKnife.findById(view, R.id.tvLastNav)).setText(Double.toString(composition.getLastNav()) + " (" + composition.getLastNavDate()+")");
        ((TextView) ButterKnife.findById(view, R.id.tvCurrentUnit)).setText(Double.toString(composition.getCurrentUnit()));
        ((TextView) ButterKnife.findById(view, R.id.tvMarketValue)).setText(AmountFormatter.format(composition.getMarketValue()));

        dialog.show();
    }
}
