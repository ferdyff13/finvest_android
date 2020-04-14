package com.invisee.finvest.ui.fragments.portfolio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.PortfolioInvestment;
import com.invisee.finvest.data.api.responses.CartListResponse;
import com.invisee.finvest.data.api.responses.PortfolioInvestmentListResponse;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.ListOfCatalogueActivity;
import com.invisee.finvest.ui.activities.PortfolioActivity;
import com.invisee.finvest.ui.adapters.rv.PortfolioAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.ui.RecyclerItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by fajarfatur on 3/1/16.
 */
public class ListPortfolioFragment extends BaseFragment {

    public static final String TAG = ListPortfolioFragment.class.getSimpleName();

    @Bind(R.id.llPortfolio)
    LinearLayout llPortfolio;
    @Bind(R.id.llNoPortfolio)
    LinearLayout llNoPortfolio;
    @Bind(R.id.rv)
    RecyclerView rv;
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
    public List<CartListResponse> cartList;

    private ListPortfolioPresenter presenter;

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            fragmentTransaction.replace(R.id.container, new ListPortfolioFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_list_portfolio;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ListPortfolioPresenter(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRV();

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 1 : 1;
            }
        });

        rv.setLayoutManager(layoutManager);

        if (investmentList != null) {
            loadInvestmentList();
        } else {
            presenter.getInvestmentList();
        }
        presenter.cartList();
    }

/*
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Portofolio");

    }


*/

    private void initRV() {
        LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llManager);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View childView, int position) {
                super.onItemClick(childView, position);
                PortfolioInvestment investment = (PortfolioInvestment) childView.getTag();
                PortfolioActivity.startActivity((BaseActivity) getActivity(), investmentList.getData().get(investmentList.getData().indexOf(investment)));
            }
        }));
    }

    public void loadInvestmentList() {
        rv.setAdapter(new PortfolioAdapter(getActivity(), investmentList.getData()));
    }

    void noPortfolio(boolean b) {
        llNoPortfolio.setVisibility(b ? View.VISIBLE : View.GONE);
        llPortfolio.setVisibility(b ? View.GONE : View.VISIBLE);
    }


    @OnClick(R.id.bStartToInvest)
    void startToInvest() {
        ListOfCatalogueActivity.startActivity((BaseActivity) getActivity());
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
        presenter.getInvestmentList();
    }

}
