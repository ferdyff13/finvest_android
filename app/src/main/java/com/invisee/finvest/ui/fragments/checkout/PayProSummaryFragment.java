package com.invisee.finvest.ui.fragments.checkout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.ProductDto;
import com.invisee.finvest.data.api.beans.Summary;
import com.invisee.finvest.data.api.responses.AmountSummaryResponse;
import com.invisee.finvest.data.api.responses.ConfirmCheckPinResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.MainActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.AmountFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by pandu.abbiyuarsyah on 30/05/2017.
 */

public class PayProSummaryFragment extends BaseFragment {

    @Bind(R.id.lnrsummary)
    LinearLayout lnrsummary;


    @Bind(R.id.lnPackage)
    LinearLayout lnPackage;


    @Bind(R.id.txvTime)
    TextView txvTime;
    @Bind(R.id.txvTotal)
    TextView txvTotal;
    @Bind(R.id.txvOrderNumber)
    TextView txvOrderNumber;


    @State
    public AmountSummaryResponse response;

    public static final String TAG = PayProSummaryFragment.class.getSimpleName();
    private final static String PAYPRO_SUMMMARY = "payprosummary";

    public static void showFragment(BaseActivity sourceActivity, AmountSummaryResponse response) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);

            PayProSummaryFragment fragment = new PayProSummaryFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(PAYPRO_SUMMMARY, response);
            fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.container, fragment, TAG);
            fragmentTransaction.commit();
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        response = (AmountSummaryResponse) getArguments().getSerializable(PAYPRO_SUMMMARY);
        setDataView();
        summary();
    }

    @Override
    protected int getLayout() {
        return R.layout.f_paypro_summary;
    }

    private void setDataView() {
        for (int i = 0; i < response.getSummary().size(); i++) {

            Summary item = response.getSummary().get(i);

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View v = inflater.inflate(R.layout.row_wallet_summary, null);

            LinearLayout lnrComposition = (LinearLayout) v.findViewById(R.id.lnrComposition);

            txvTotal.setText(AmountFormatter.format(response.getOrderAmount()));
            txvTime.setText(response.getTime());
            txvOrderNumber.setText(response.getOrderNumber());

            TextView txvProduct = (TextView) v.findViewById(R.id.txvProduct);
            txvProduct.setText(item.getFundPackageName());

            TextView txvProductTitle = (TextView) v.findViewById(R.id.txvProductTitle);
            TextView txvInvestmentNumber = (TextView) v.findViewById(R.id.txvInvestmentNumber);
            TextView txvType = (TextView) v.findViewById(R.id.txvType);
            TextView txvAmountTrans = (TextView) v.findViewById(R.id.txvAmountTrans);
            TextView txvFee = (TextView) v.findViewById(R.id.txvFee);
            TextView txvTotalAmount = (TextView) v.findViewById(R.id.txvTotalAmount);

            txvProductTitle.setText("Nama Produk");
            txvInvestmentNumber.setText(item.getInvestmentNumber());
            txvType.setText(item.getTransactionType());
            txvAmountTrans.setText(AmountFormatter.format(item.getTransactionAmount().doubleValue()));
            txvFee.setText(String.valueOf(item.getFee() + " %"));
            txvTotalAmount.setText(AmountFormatter.format(item.getTotal().doubleValue()));

            lnrsummary.addView(v);

            for (int j = 0; j < item.getSummary().size(); j++) {
                ProductDto productDto =  item.getSummary().get(j);

                View viewComposition = inflater.inflate(R.layout.row_composition, null);
         /*       View vPackage = inflater.inflate(R.layout.row_package_fund_alocation, null);*/

                TextView txvCompositionTitle = (TextView) viewComposition.findViewById(R.id.txvCompositionTitle);
                TextView txvProductComposition = (TextView) viewComposition.findViewById(R.id.txvProductComposition);
                TextView txvComposition = (TextView) viewComposition.findViewById(R.id.txvComposition);

                txvCompositionTitle.setText("Komposisi");
                txvProductComposition.setText(productDto.getUtProductName());
                txvComposition.setText(productDto.getComposition() * 100 + " " + "%");

       /*         TextView txvName = (TextView) vPackage.findViewById(R.id.txvName);
                txvName.setText(productDto.getUtProductName());
*/
                /*lnPackage.addView(vPackage);*/
                lnrComposition.addView(viewComposition);

            }


        }

    }

    private void summary() {
        /*TextView txvName = (TextView) vPackage.findViewById(R.id.txvTotalAmount);*/

        List<String> list = new ArrayList<>();
        Boolean exist = false;

        for (int i = 0; i < response.getSummary().size(); i++) {
            Summary item = response.getSummary().get(i);
            for (int j = 0; j < item.getSummary().size(); j++) {
                ProductDto dto = item.getSummary().get(j);
                for (int k = 0; k < list.size(); k++){
                    if (list.get(k).equals(dto.getUtProductName())) {
                        exist = true;
                    }
                }
                if(exist != true){
                    list.add(dto.getUtProductName().toString());
                }

            }

        }
        Log.d("before duplicate sum", list.toString());

        for (int c = 0; c < list.size(); c++ ) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View vPackage = inflater.inflate(R.layout.row_package_fund_alocation, null);
            TextView txvName = (TextView) vPackage.findViewById(R.id.txvName);
            String str = list.toString();
            str = str.substring(0, str.length() - 1);
            str  = str.substring(1);
            txvName.setText(str);
            lnPackage.addView(vPackage);
        }
        Log.d("duplicate sum", list.toString());

    }


    @OnClick(R.id.bOk)
    void onClickOk() {
        PrefHelper.clearPreference(PrefKey.CART);
        MainActivity.startActivity((BaseActivity) getActivity());
    }
}
