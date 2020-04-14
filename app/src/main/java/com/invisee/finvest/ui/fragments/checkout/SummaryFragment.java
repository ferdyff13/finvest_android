package com.invisee.finvest.ui.fragments.checkout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.CartList;
import com.invisee.finvest.data.api.beans.Composition;
import com.invisee.finvest.data.api.beans.DtoNetFeeResponse;
import com.invisee.finvest.data.api.beans.SettlementAccounts;
import com.invisee.finvest.data.api.beans.TransactionTransfer;
import com.invisee.finvest.data.api.responses.CartListResponse;
import com.invisee.finvest.data.api.responses.TransactionTransferResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.MainActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.ui.fragments.dashboard.PendingOrderFragment;
import com.invisee.finvest.util.AmountFormatter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.Bind;
import butterknife.OnClick;
import clojure.lang.IFn;
import icepick.State;

/**
 * Created by fajarfatur on 1/22/16.
 */
public class SummaryFragment extends BaseFragment {

    public static final String TAG = SummaryFragment.class.getSimpleName();
    private final static String TRANSACTION_TRANSFER = "transactionTransfer";
    private final static String CART_LIST = "cartList";

    @Bind(R.id.lnrsummary)
    LinearLayout lnrSummary;
    @Bind(R.id.lnrData)
    LinearLayout lnrData;

    @State
    public TransactionTransferResponse response;
    @State
    public CartList cartList;


    public static void showFragment(BaseActivity sourceActivity, TransactionTransferResponse response, CartList cartList) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);

            SummaryFragment fragment = new SummaryFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(TRANSACTION_TRANSFER, response);
            bundle.putSerializable(CART_LIST, cartList);
            fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.container, fragment, TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_summary;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        response = (TransactionTransferResponse) getArguments().getSerializable(TRANSACTION_TRANSFER);
        cartList = (CartList) getArguments().getSerializable(CART_LIST);

        setDataToView();
        loadList();

    }

    public void loadList() {

        for (int i = 0; i < cartList.getCartList().size(); i++) {

            CartListResponse item = cartList.getCartList().get(i);

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View v = inflater.inflate(R.layout.row_cart_detail, null);

            TextView tvProductName = (TextView) v.findViewById(R.id.tvProductName);
            TextView tvProductType = (TextView) v.findViewById(R.id.tvProductType);
            TextView edtAmount = (EditText) v.findViewById(R.id.edtAmount);
            TextView edtFee = (EditText) v.findViewById(R.id.edtFee);
            TextView edtTotal = (EditText) v.findViewById(R.id.edtTotal);
            LinearLayout lnrFundAlloc = (LinearLayout) v.findViewById(R.id.lnrFundAlloc);


            NumberFormat nf = NumberFormat.getCurrencyInstance();
            DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
            decimalFormatSymbols.setCurrencySymbol("");
            ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);

            tvProductName.setText(item.getFundPackages().getFundPackageName());
            edtAmount.setText(nf.format(Double.parseDouble(item.getTransactionAmount())));
            edtFee.setText(item.getFeePercentage());

            Double total = Double.parseDouble(item.getTotal());
            edtTotal.setText(AmountFormatter.format(total));

            if (item.getTransactionType().getTrxCode().equalsIgnoreCase("SUBCR")) {
                tvProductType.setText("Subscribe");
            }

            for (int j = 0; j < item.getFundPackagesProduct().size(); j++) {

                List<String> fundAlloc = item.getFundPackagesProduct().get(j);

                inflater = getActivity().getLayoutInflater();
                View v2 = inflater.inflate(R.layout.row_fund_allocation, null);

                TextView txvName = (TextView) v2.findViewById(R.id.txvName);
                TextView txvType = (TextView) v2.findViewById(R.id.txvType);
                TextView txvComposition = (TextView) v2.findViewById(R.id.txvComposition);

                txvName.setText(fundAlloc.get(2));
                txvType.setVisibility(View.GONE);
                try {
                    txvComposition.setText((Double.parseDouble(fundAlloc.get(1)) * 100) + "%");
                } catch (Exception e) {
                    txvComposition.setText((0 * 100) + "%");
                }

                lnrFundAlloc.addView(v2);
            }
            lnrData.addView(v);

        }

    }

    void setDataToView() {

        for (int i = 0; i < response.getData().size(); i++) {

            TransactionTransfer item = response.getData().get(i);

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View v = inflater.inflate(R.layout.row_summary, null);

            TextView txvOrderNumber = (TextView) v.findViewById(R.id.txvOrderNumber);
            TextView txvTotal = (TextView) v.findViewById(R.id.txvTotal);
            TextView txvExpirationTime = (TextView) v.findViewById(R.id.txvExpirationTime);

            LinearLayout lnPackage = (LinearLayout) v.findViewById(R.id.lnPackage);

            NumberFormat nf = NumberFormat.getCurrencyInstance();
            DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
            decimalFormatSymbols.setCurrencySymbol("");
            ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);

            txvOrderNumber.setText(item.getOrderNumber());
            txvTotal.setText(AmountFormatter.format(item.getTotal()));
            txvExpirationTime.setText(String.format(getString(R.string.summary_expired_time), item.getEffectiveDate() + " " + item.getEffectiveTime()));

            for (int a = 0; a < item.getNetFeeDto().size(); a++) {
                DtoNetFeeResponse itemDto = item.getNetFeeDto().get(a);
                for (int b = 0; b < itemDto.getCompositions().size(); b++) {
                    Composition itemComposition = itemDto.getCompositions().get(b);
                    inflater = getActivity().getLayoutInflater();

                    View v2 = inflater.inflate(R.layout.row_package_fund_alocation, null);
                    TextView txvName = (TextView) v2.findViewById(R.id.txvName);
                    txvName.setText(itemComposition.getProductName());
                    lnPackage.addView(v2);


                    for (int c = 0; c < itemComposition.getSettlementAccounts().size(); c++) {
                        SettlementAccounts itemSettlement = itemComposition.getSettlementAccounts().get(c);
                        TextView txvSettlementName = (TextView) v.findViewById(R.id.txvSettlementName);
                        TextView txvSettlementNumber = (TextView) v.findViewById(R.id.txvSetttlementNumber);
                        String replacestr = itemSettlement.getAccountName();

                        StringTokenizer st = new StringTokenizer(replacestr, "|");
                        String bankname = st.nextToken();
                        String itemname = st.nextToken();

                        txvSettlementName.setText(bankname + "\n" + itemname);
                        txvSettlementNumber.setText(itemSettlement.getAccountNumber());
                    }
                }
            }

            //for utk

            for (int a = 0; a < item.getNetFeeDto().size(); a++) {
                DtoNetFeeResponse itemDto = item.getNetFeeDto().get(a);
                LayoutInflater inflater3 = getActivity().getLayoutInflater();
                View v3 = inflater.inflate(R.layout.row_summary_manual, null);

                TextView txvProductName = (TextView) v3.findViewById(R.id.txvProductName);
                TextView txvNoInvestasi = (TextView) v3.findViewById(R.id.txvNoInvestasi);
                TextView txvTransactionType = (TextView) v3.findViewById(R.id.txvTransactionType);

//                LinearLayout lnPackage = (LinearLayout) v3.findViewById(R.id.lnPackage);

                txvProductName.setText(item.getNetFeeDto().get(i).getProductName());
                txvNoInvestasi.setText(item.getNetFeeDto().get(i).getInvestmentAccount());
                txvTransactionType.setText(item.getNetFeeDto().get(i).getTransType());
            }


            lnrSummary.addView(v);
        }
    }

    @OnClick(R.id.bOk)
    void onClickOk() {
        PrefHelper.clearPreference(PrefKey.CART);
       /* getActivity().setResult(Activity.RESULT_OK);*/
        MainActivity.startActivity((BaseActivity) this.getActivity());
    }
}
