package com.invisee.finvest.ui.fragments.redemption;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.PortfolioInvestment;
import com.invisee.finvest.data.api.beans.RedemptionData;
import com.invisee.finvest.data.api.responses.RedemptionOrderDetailResponse;
import com.invisee.finvest.data.api.responses.SettlementInfoResponse;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.KycActivityNew;
import com.invisee.finvest.ui.activities.RedemptionActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.mobsandgeeks.saripaar.annotation.Length;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by fajarfatur on 3/14/16.
 */
public class AccountFragment extends BaseFragment {

    public static final String TAG = AccountFragment.class.getSimpleName();
    private static final String INVESTMENT = "investment";
    private static final String PACKAGES = "packages";
    private static final String STATUSREDEEM = "statusRedeem";
    public String percentageRedeem;
    private String statusRedeem="";

    @Bind(R.id.tvAccountName)
    TextView tvAccountName;
    @Bind(R.id.tvAccountNumber)
    TextView tvAccountNumber;
    @Bind(R.id.tvBranch)
    TextView tvBranch;
/*    @Bind(R.id.txvWarning)
    TextView txvWarning;*/
    @Length(min = 2)
    @Bind(R.id.etSecurityPin)
    EditText etSecurityPin;
    @Bind(R.id.txvSecurityPin)
    TextView txvSecurityPin;
    @BindString(R.string.redemption_please_input)
    String inputDigit;
    @State
    SettlementInfoResponse settlementInfoResponse;
    @State
    PortfolioInvestment investment;
    @State
    Packages packages;
    @State
    RedemptionOrderDetailResponse redemptionOrderDetailResponse;

    private AccountPresenter presenter;

    public static void showFragment(BaseActivity sourceActivity, PortfolioInvestment investment, Packages packages, String statusRedem) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            AccountFragment fragment = new AccountFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(INVESTMENT, investment);
            bundle.putSerializable(PACKAGES, packages);
            bundle.putString(STATUSREDEEM, statusRedem);
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.container, fragment, TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_redemption_account;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AccountPresenter(this);
        if (investment == null )
            investment = (PortfolioInvestment) getArguments().getSerializable(INVESTMENT);
        if (packages == null )
            packages = (Packages) getArguments().getSerializable(PACKAGES);
        if (statusRedeem.equals("")){
            statusRedeem = getArguments().getString(STATUSREDEEM);
            Log.d(TAG, "onCreate: "+ statusRedeem);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.generateRandomIndexSecurityPin();
    }

    @Override
    public void onResume() {
        super.onResume();
//        presenter.generateRandomIndexSecurityPin();
        if (settlementInfoResponse != null) {
            bindingSettlementInfoData();
        } else {
            presenter.loadSettlementInfo();
        }
    }

    void bindingSettlementInfoData() {
/*
        String accountName = settlementInfoResponse.getBankName().concat(" - ").concat(settlementInfoResponse.getSettlementAccountName());
*/
        tvAccountName.setText(settlementInfoResponse.getSettlementAccountName());
        tvAccountNumber.setText(settlementInfoResponse.getSettlementAccountNo());
        tvBranch.setText(settlementInfoResponse.getBankName());
    }

    @OnClick(R.id.bProceed)
    void proceed() {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();
        if(statusRedeem.equals("full")){
            presenter.getRedemptionOrderDetails(investment.getInvestmentAccountNumber());
        } else {
            percentageRedeem = statusRedeem;
            presenter.getPartialRedemptionOrderDetails(investment.getInvestmentAccountNumber(), percentageRedeem);
        }
    }

    void gotoSummary(RedemptionData redemptionData, String percentage){
        ((RedemptionActivity) getActivity()).setStep(3);
        SummaryFragment.showFragment((BaseActivity) getActivity(), redemptionData, packages, percentage);

    }

    @OnClick(R.id.bSendPin)
    public void generateNewPin() {
        presenter.sendPIN();
    }

    @OnClick(R.id.bChangeBank)
    void toChangeBankAccount() {
        KycActivityNew.startActivity((BaseActivity) getActivity(), 4);
    }

}
