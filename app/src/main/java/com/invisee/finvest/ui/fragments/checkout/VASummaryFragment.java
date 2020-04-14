package com.invisee.finvest.ui.fragments.checkout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.CartList;
import com.invisee.finvest.data.api.beans.Composition;
import com.invisee.finvest.data.api.beans.DtoNetFeeResponse;
import com.invisee.finvest.data.api.beans.TransactionTransfer;
import com.invisee.finvest.data.api.responses.CartListResponse;
import com.invisee.finvest.data.api.responses.TransactionTransferResponse;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.MainActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.AmountFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by pandu.abbiyuarsyah on 17/04/2017.
 */

public class VASummaryFragment extends BaseFragment {

    public static final String TAG = VASummaryFragment.class.getSimpleName();
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
        VASummaryFragment fragment = new VASummaryFragment();
        FragmentTransaction transaction = sourceActivity.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        Bundle bundle = new Bundle();
        bundle.putSerializable(TRANSACTION_TRANSFER, response);
        bundle.putSerializable(CART_LIST, cartList);
        fragment.setArguments(bundle);
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, new VASummaryFragment(), TAG);
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

        try {
            jsonToMap();
        } catch (JSONException e) {
            e.printStackTrace();

        }
        /*loadList();*/

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    void jsonToMap() throws JSONException {
        String json = "{\"phonetype\":\"N95\",\"cat\":\"WP\"}";

        HashMap<String, String> map = new HashMap<String, String>();
        JSONObject jObject = new JSONObject(json);
        Iterator<?> keys = jObject.keys();

        while( keys.hasNext() ){
            String key = (String)keys.next();
            String value = jObject.getString(key);
            map.put(key, value);

        }
        Log.d("jsonTest", jObject.toString());
        Log.d("mapTest", map.toString());

        System.out.println("json : "+jObject);
        System.out.println("map : "+map.get("cat"));
    }

    public void loadList() {

        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i < cartList.getCartList().size(); i++) {

            CartListResponse item = cartList.getCartList().get(i);

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View v = inflater.inflate(R.layout.row_cart_detail, null);

            TextView tvProductName = (TextView) v.findViewById(R.id.tvProductName);

            TextView edtAmount = (EditText) v.findViewById(R.id.edtAmount);
            TextView edtFee = (EditText) v.findViewById(R.id.edtFee);
            TextView edtTotal = (EditText) v.findViewById(R.id.edtTotal);
            TextView tvProductType = (TextView) v.findViewById(R.id.tvProductType);
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
/*

            if (item.getTransactionType().getTrxCode().equalsIgnoreCase("SUBCR")) {
                tvProductType.setText("Subscribe");
            }
*/

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
            View v = inflater.inflate(R.layout.row_va_summary, null);

            TextView txvOrderNumber = (TextView) v.findViewById(R.id.txvOrderNumber);
            TextView txvTotal = (TextView) v.findViewById(R.id.txvTotal);
            TextView txvExpirationTime = (TextView) v.findViewById(R.id.txvExpirationTime);
            TextView txvSettlementName = (TextView) v.findViewById(R.id.txvSettlementName);
            TextView txvSettlementNumber = (TextView) v.findViewById(R.id.txvSetttlementNumber);
            TextView txvNoInvestasi = (TextView) v.findViewById(R.id.txvNoInvestasi);
            TextView txvTransactionType = (TextView) v.findViewById(R.id.txvTransactionType);
            TextView txvProductName = (TextView) v.findViewById(R.id.txvProductName);

            TextView tvProductName = (TextView) v.findViewById(R.id.tvProductName);
            TextView edtAmount = (TextView) v.findViewById(R.id.edtAmount);
            TextView edtTotal = (TextView) v.findViewById(R.id.edtTotal);
            TextView edtFee = (TextView) v.findViewById(R.id.edtFee);

            LinearLayout lnPackage = (LinearLayout) v.findViewById(R.id.lnPackage);

            NumberFormat nf = NumberFormat.getCurrencyInstance();
            DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
            decimalFormatSymbols.setCurrencySymbol("");
            ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);

            txvOrderNumber.setText(item.getOrderNumber());
            txvTotal.setText(AmountFormatter.format(item.getTotal()));

            txvExpirationTime.setText(String.format(getString(R.string.summary_expired_time), item.getEffectiveDate() + " " + item.getEffectiveTime()));

            inflater = getActivity().getLayoutInflater();
            View v2 = inflater.inflate(R.layout.row_package_fund_alocation, null);
            TextView txvName = (TextView) v2.findViewById(R.id.txvName);

            for (int a = 0; a < item.getNetFeeDto().size(); a++) {
                DtoNetFeeResponse itemDto = item.getNetFeeDto().get(a);

                edtAmount.setText(AmountFormatter.format(itemDto.getNetAmount()));
                edtTotal.setText(AmountFormatter.format(itemDto.getTotal()));
                DecimalFormat df = new DecimalFormat("0.00");
                edtFee.setText(String.valueOf(df.format(itemDto.getFeeAmount()) + " %"));

                txvNoInvestasi.setText(itemDto.getInvestmentAccount());
                txvSettlementName.setText(itemDto.getTransType());
                txvTransactionType.setText(itemDto.getStatus());
                txvSettlementNumber.setText("Nomor Rekening VA :" + " " + item.getAccountNumber() + "\n" + "Nama Pemilik : " + item.getAccountName() + "\n" + "Bank : " + " " + item.getBankName());

                List<String> list = new ArrayList<>();
                Boolean exist = false;

                for (int b = 0; b < itemDto.getCompositions().size(); b++) {
                    Composition itemComposition = itemDto.getCompositions().get(b);

                    tvProductName.setText(itemComposition.getProductName());
                    txvProductName.setText(itemComposition.getProductName());

                    for (int k = 0; k < list.size(); k++){
                        if (list.get(k).equals(itemComposition.getProductName())) {
                            exist = true;
                        }
                    }

                    if(exist != true){
                        list.add(itemComposition.getProductName());
                    }

                    for (int c = 0; c < list.size(); c++ ) {
                        String str = list.toString();
                        str = str.substring(0, str.length() - 1);
                        str  = str.substring(1);
                        txvName.setText(str);
                    }

                }

            }
            lnPackage.addView(v2);
            lnrSummary.addView(v);
        }
    }



    @OnClick(R.id.bOk)
    void onClickOk() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
        MainActivity.startActivity((BaseActivity) this.getActivity());
    }


}
