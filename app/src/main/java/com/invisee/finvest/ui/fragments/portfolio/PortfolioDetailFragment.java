package com.invisee.finvest.ui.fragments.portfolio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.beans.PortfolioInvestment;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.AmountFormatter;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

/**
 * Created by fajarfatur on 3/2/16.
 */
public class PortfolioDetailFragment extends BaseFragment {

    private static final String PORTFOLIO = "portfolio";

    @Bind(R.id.ivPackage)
    ImageView ivPackage;
    @Bind(R.id.tvPackageName)
    TextView tvPackageName;
    @Bind(R.id.tvInvestmentNumber)
    TextView tvInvestmentNumber;
    @Bind(R.id.tvInvesmentAmount)
    TextView tvInvesmentAmount;
    @Bind(R.id.tvGoal)
    TextView tvGoal;
    @Bind(R.id.tvMarketValue)
    TextView tvMarketValue;
    @Bind(R.id.tvUrealizeGainLossPercent)
    TextView tvUrealizeGainLossPercent;

    private PortfolioInvestment investment;

    public static PortfolioDetailFragment initiateFragment(PortfolioInvestment investment) {
        PortfolioDetailFragment fragment = new PortfolioDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PORTFOLIO, investment);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.f_portfolio_detail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        investment = (PortfolioInvestment) getArguments().get(PORTFOLIO);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        double totalInv = investment.getInvestmentAmount();
        double totalMarketValue = investment.getTotalInvestmentMarketValue();
        double totalGainLoss = totalMarketValue - totalInv;
        float percentage = (float) (totalGainLoss / totalInv) * 100;


        ivPackage.setScaleType(ImageView.ScaleType.FIT_XY);
        ivPackage.setAdjustViewBounds(true);
        Picasso.with(getActivity()).load(InviseeService.IMAGE_DOWNLOAD_URL + investment.getPackageImageKey()).into(ivPackage);
        tvPackageName.setText(investment.getPackageName());
        tvPackageName.setText(investment.getPackageName());
        tvInvestmentNumber.setText(investment.getInvestmentAccountNumber());
        tvInvesmentAmount.setText(AmountFormatter.format(investment.getInvestmentAmount()));
        if (investment.getTaggedGoalName().equals("null")) {
            tvGoal.setText("-");
        } else {
            tvGoal.setText(investment.getTaggedGoalName());
        }

        tvMarketValue.setText(AmountFormatter.format(totalMarketValue));
        tvUrealizeGainLossPercent.setText(String.format("%.2f", percentage) + " " + " %");
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//
//    }

}
