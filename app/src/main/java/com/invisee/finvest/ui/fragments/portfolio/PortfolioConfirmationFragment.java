package com.invisee.finvest.ui.fragments.portfolio;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.beans.Fee;
import com.invisee.finvest.data.api.beans.FundAllocation;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.PortfolioInvestment;
import com.invisee.finvest.data.api.responses.CartListResponse;
import com.invisee.finvest.data.api.responses.FundAllocationResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.DateUtil;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by fajarfatur on 3/2/16.
 */

@RuntimePermissions
public class PortfolioConfirmationFragment extends BaseFragment {

    public static final String TAG = PortfolioConfirmationFragment.class.getSimpleName();
    private static final String PACKAGES = "packages";
    private static final String INVESTMENT = "investment";
    private static final String FUND_ALLOC = "fundAlloc";


    @Bind(R.id.txvMinTopup)
    TextView txvMinTopup;
    @Bind(R.id.txvTransactionCutOff)
    TextView txvTransactionCutOff;
    @Bind(R.id.txvSettlementCutOff)
    TextView txvSettlementCutOff;
    @Bind(R.id.lnrTopUpData)
    LinearLayout lnrTopUpData;
    @Bind(R.id.prbTopup)
    ProgressBar prbTopup;
    @Bind(R.id.lnrBusinessRule)
    LinearLayout lnrBusinessRule;
    @Bind(R.id.cbAgree)
    CheckBox cbAgree;
    @Bind(R.id.btnTupop)
    Button btnTupop;


    private PortfolioConfirmationPresenter presenter;

    @State
    Packages packages;
    @State
    PortfolioInvestment investment;
    @State
    FundAllocationResponse fundAlloc;
    public List<Fee> topupFees;
    public List<CartListResponse> cartList;
    @State
    public FundAllocationResponse response;

    NumberFormat nf;


    public static void showFragment(BaseActivity sourceActivity, PortfolioInvestment investment, Packages packages, FundAllocationResponse fundAlloc) {

        if (!sourceActivity.isFragmentNotNull(TAG)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(PACKAGES, packages);
            bundle.putSerializable(FUND_ALLOC, fundAlloc);
            bundle.putSerializable(INVESTMENT, investment);
            PortfolioConfirmationFragment fragment = new PortfolioConfirmationFragment();
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment, TAG);
            fragmentTransaction.commit();
        }

    }

    @Override
    protected int getLayout() {
        return R.layout.f_portfolio_confirmation;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (packages == null) {
            Bundle extras = getArguments();
            if (extras != null && extras.containsKey(PACKAGES)) {
                packages = (Packages) extras.getSerializable(PACKAGES);
            }
        }

        if (investment == null) {
            Bundle extras = getArguments();
            if (extras != null && extras.containsKey(INVESTMENT)) {
                investment = (PortfolioInvestment) extras.getSerializable(INVESTMENT);
            }
        }

        if (fundAlloc == null) {
            Bundle extras = getArguments();
            if (extras != null && extras.containsKey(FUND_ALLOC)) {
                fundAlloc = (FundAllocationResponse) extras.getSerializable(FUND_ALLOC);
            }
        }

        if (packages != null) {
            presenter = new PortfolioConfirmationPresenter(this);
            presenter.topupFee(packages);
            setDataToView();
        }

        if (fundAlloc != null) {
            setupBusinessRuleView();
        }

        cbAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                               @Override
                                               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                   if (isChecked) {
                                                       btnTupop.setEnabled(true);
                                                   } else {
                                                       btnTupop.setEnabled(false);
                                                   }
                                               }
                                           }
        );

    }


    @Override
    public void onResume() {
        super.onResume();
        getCartList();
    }

    public void getCartList() {
        presenter.cartList();
    }


    public void setDataToView() {
        nf = NumberFormat.getCurrencyInstance(new Locale("ID", "id"));
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);


        Calendar transCutOff = Calendar.getInstance();
        transCutOff.setTime(DateUtil.format(packages.getTransactionCutOff(), "yyyy-MM-dd'T'HH:mm:ssXXX"));
        Calendar settlementCutOff = Calendar.getInstance();
        settlementCutOff.setTime(DateUtil.format(packages.getSettlementCutOff(), "yyyy-MM-dd'T'HH:mm:ssXXX"));

        txvMinTopup.setText(nf.format(packages.getMinTopupAmount()).trim());
        txvTransactionCutOff.setText(DateUtil.format(transCutOff.getTime(), DateUtil.HH_MM));
        txvSettlementCutOff.setText(DateUtil.format(settlementCutOff.getTime(), DateUtil.HH_MM));
    }


    public void showTopupLoading() {
        prbTopup.setVisibility(View.VISIBLE);
    }

    public void hideTopupLoading() {
        prbTopup.setVisibility(View.GONE);
    }

    public void setupTopupView() {
        for (int i = 0; i < topupFees.size(); i++) {

            Fee fee = topupFees.get(i);

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.row_fee, null);

            TextView txvItem = (TextView) view.findViewById(R.id.txvItem);
            TextView txvRate = (TextView) view.findViewById(R.id.txvRate);

            if (fee.getAmountMax() == 0) {
                txvItem.setText(" more than " + nf.format(fee.getAmountMin()));
            } else {
                txvItem.setText(nf.format(fee.getAmountMin()) + " - " + nf.format(fee.getAmountMax()));
            }

            Double feeAmount =  fee.getFeeAmount() * 100;
            txvRate.setText(feeAmount + " " + "%");

            lnrTopUpData.addView(view);
        }
    }


    public void setupBusinessRuleView() {
        for (int i = 0; i < fundAlloc.getData().size(); i++) {

            final FundAllocation fundAllocData = fundAlloc.getData().get(i);

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.row_product, null);

            final TextView txvName = (TextView) view.findViewById(R.id.txvName);
            LinearLayout lnrProspectus = (LinearLayout) view.findViewById(R.id.lnrProspectus);
            LinearLayout lnrFundFactSheet = (LinearLayout) view.findViewById(R.id.lnrFundFactSheet);

            txvName.setText(fundAllocData.getProductName());
            lnrProspectus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PortfolioConfirmationFragmentPermissionsDispatcher.downloadProspectusWithCheck(PortfolioConfirmationFragment.this, fundAllocData);
//                    downloadProspectus(fundAllocData);
                }
            });
            lnrFundFactSheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PortfolioConfirmationFragmentPermissionsDispatcher.downloadFundFactWithCheck(PortfolioConfirmationFragment.this, fundAllocData);
//                    downloadFundFact(fundAllocData);
                }
            });

            lnrBusinessRule.addView(view);
        }
    }


    @OnClick(R.id.btnTupop)
    void onTopupClicked() {
        if (cbAgree.isChecked()) {
            presenter.topUpToCart(investment, packages);
        }
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void downloadProspectus(FundAllocation fundAllocData){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(InviseeService.IMAGE_DOWNLOAD_URL + fundAllocData.getProspectusKey().toString() + "&token=" + PrefHelper.getString(PrefKey.TOKEN)));
        request.setDescription("A download package with some files");
        request.setTitle("Prospektus "+fundAllocData.getProductName());
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Prospektus "+fundAllocData.getProductName()+".pdf");

        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void downloadFundFact(FundAllocation fundAllocData){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(InviseeService.IMAGE_DOWNLOAD_URL + fundAllocData.getProspectusKey().toString() + "&token=" + PrefHelper.getString(PrefKey.TOKEN)));
        request.setDescription("A download package with some files");
        request.setTitle("Fund Fact Sheet "+fundAllocData.getProductName());
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Fund Fact Sheet "+fundAllocData.getProductName()+".pdf");

        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PortfolioConfirmationFragmentPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }
}
