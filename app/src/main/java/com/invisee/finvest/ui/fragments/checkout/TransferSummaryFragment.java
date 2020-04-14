package com.invisee.finvest.ui.fragments.checkout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.CartList;
import com.invisee.finvest.data.api.beans.Composition;
import com.invisee.finvest.data.api.beans.DtoNetFeeResponse;
import com.invisee.finvest.data.api.beans.SettlementAccounts;
import com.invisee.finvest.data.api.beans.TransactionTransfer;
import com.invisee.finvest.data.api.responses.TransactionTransferResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.MainActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.AmountFormatter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by pandu.abbiyuarsyah on 06/05/2017.
 */

public class TransferSummaryFragment extends BaseFragment {

    public static final String TAG = TransferSummaryFragment.class.getSimpleName();
    private final static String TRANSACTION_TRANSFER = "transactionTransfer";
    private final static String CART_LIST = "cartList";

    @Bind(R.id.lnrsummary)
    LinearLayout lnrSummary;
    @Bind(R.id.lnrData)
    LinearLayout lnrData;

    String norek = "";

    @State
    public TransactionTransferResponse response;
    @State
    public CartList cartList;

    public static void showFragment(BaseActivity sourceActivity, TransactionTransferResponse response, CartList cartList) {

//            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
//            sourceActivity.getSupportFragmentManager().beginTransaction().remove(fragmentTransaction).commit();
//            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
//            TransferSummaryFragment fragment = new TransferSummaryFragment();
//            Bundle bundle = new Bundle();
//            bundle.putSerializable(TRANSACTION_TRANSFER, response);
//            bundle.putSerializable(CART_LIST, cartList);
//            fragment.setArguments(bundle);
//            fragmentTransaction.replace(R.id.container, fragment, TAG);
//            fragmentTransaction.commit();

        TransferSummaryFragment fragment = new TransferSummaryFragment();
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

    @Override
    protected int getLayout() {
        return R.layout.f_summary;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        response = (TransactionTransferResponse) getArguments().getSerializable(TRANSACTION_TRANSFER);
        cartList = (CartList) getArguments().getSerializable(CART_LIST);

        /*loadList();*/
        setDataToView();

    }

    public void loadList() {

        for (int i = 0; i < response.getData().size(); i++) {
            TransactionTransfer item = response.getData().get(i);

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View v = inflater.inflate(R.layout.row_transfer_summary, null);

            TextView txvOrderNumber = (TextView) v.findViewById(R.id.txvOrderNumber);
            TextView txvExpirationTime = (TextView) v.findViewById(R.id.txvExpirationTime);

            NumberFormat nf = NumberFormat.getCurrencyInstance();
            DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
            decimalFormatSymbols.setCurrencySymbol("");
            ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);

            txvOrderNumber.setText(item.getOrderNumber());
            txvExpirationTime.setText(String.format(getString(R.string.summary_expired_time), item.getEffectiveDate() + " " + item.getEffectiveTime()));

            for (int a = 0; a < item.getNetFeeDto().size(); a++) {
                DtoNetFeeResponse itemDto = item.getNetFeeDto().get(a);

                TextView txvProductName = (TextView) v.findViewById(R.id.txvProductName);
                TextView txvInvesmentNumber = (TextView) v.findViewById(R.id.txvNoInvestasi);
                TextView txvTransactionType = (TextView) v.findViewById(R.id.txvTransactionType);

                txvProductName.setText(": " + itemDto.getProductName());
                txvInvesmentNumber.setText(": " + itemDto.getInvestmentAccount());
                txvTransactionType.setText(": " + itemDto.getTransType());

                for (int b = 0; b < itemDto.getCompositions().size(); b++) {

                    Composition itemComposition = itemDto.getCompositions().get(b);

                    TextView tvProductName = (TextView) v.findViewById(R.id.tvProductName);
                    TextView edtAmount = (TextView) v.findViewById(R.id.edtAmount);
                    TextView edtTotal = (TextView) v.findViewById(R.id.edtTotal);
                    TextView edtFee = (TextView) v.findViewById(R.id.edtFee);
                    TextView txvPackage = (TextView) v.findViewById(R.id.txvPackage);

                    tvProductName.setText(itemComposition.getProductName());
                    edtAmount.setText(AmountFormatter.format(itemComposition.getNetAmount()));
                    edtTotal.setText(AmountFormatter.format(itemComposition.getTotalAmount()));
                    edtFee.setText(String.valueOf(itemComposition.getFee()) + " %");
                    txvPackage.setText(itemComposition.getProductName());

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
                lnrSummary.addView(v);
            }


        }
    }

    void setDataToView() {

        for (int i = 0; i < response.getData().size(); i++) {
            if (response.getData().get(i).getOrderNumber().contains("O")) {
                TransactionTransfer item = response.getData().get(i);

                for (int a = 0; a < item.getNetFeeDto().size(); a++) {
                    DtoNetFeeResponse itemDto = item.getNetFeeDto().get(a);

                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View v = inflater.inflate(R.layout.row_transfer_summary, null);

                    TextView txvTotal = (TextView) v.findViewById(R.id.txvTotal);
                    TextView txvOrderNumber = (TextView) v.findViewById(R.id.txvOrderNumber);
                    TextView txvExpirationTime = (TextView) v.findViewById(R.id.txvExpirationTime);
                    LinearLayout btnCopy = (LinearLayout) v.findViewById(R.id.btn_copy);

                    NumberFormat nf = NumberFormat.getCurrencyInstance();
                    DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
                    decimalFormatSymbols.setCurrencySymbol("");
                    ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);

                    txvOrderNumber.setText(item.getOrderNumber());
                    txvExpirationTime.setText(String.format(getString(R.string.summary_expired_time), item.getEffectiveDate() + " " + item.getEffectiveTime()));

                    TextView txvProductName = (TextView) v.findViewById(R.id.txvProductName);
                    TextView txvInvesmentNumber = (TextView) v.findViewById(R.id.txvNoInvestasi);
                    TextView txvTransactionType = (TextView) v.findViewById(R.id.txvTransactionType);

                    txvProductName.setText(": " + itemDto.getProductName());
                    txvInvesmentNumber.setText(": " + itemDto.getInvestmentAccount());
                    txvTransactionType.setText(": " + itemDto.getTransType());

                    for (int b = 0; b < itemDto.getCompositions().size(); b++) {

                        Composition itemComposition = itemDto.getCompositions().get(b);

                        TextView tvProductName = (TextView) v.findViewById(R.id.tvProductName);
                        TextView edtAmount = (TextView) v.findViewById(R.id.edtAmount);
                        TextView edtTotal = (TextView) v.findViewById(R.id.edtTotal);
                        TextView edtFee = (TextView) v.findViewById(R.id.edtFee);
                        TextView txvPackage = (TextView) v.findViewById(R.id.txvPackage);

                        tvProductName.setText(itemComposition.getProductName());
                        edtAmount.setText(AmountFormatter.format(itemComposition.getNetAmount()));
                        edtTotal.setText(AmountFormatter.format(itemComposition.getTotalAmount()));
                        txvTotal.setText(AmountFormatter.format(itemComposition.getTotalAmount()));
                        edtFee.setText(String.valueOf(itemComposition.getFee()) + " %");
                        txvPackage.setText(itemComposition.getProductName());

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
                            norek = itemSettlement.getAccountNumber();

                            btnCopy.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    copyToClipboard();
                                }
                            });

                        }
                    }
                    lnrSummary.addView(v);

                }
            } else {
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

                    inflater = getActivity().getLayoutInflater();
                    View v2 = inflater.inflate(R.layout.row_package_fund_alocation, null);
                    TextView txvName = (TextView) v2.findViewById(R.id.txvName);


                    for (int b = 0; b < itemDto.getCompositions().size(); b++) {
                        Composition itemComposition = itemDto.getCompositions().get(b);

                        /*txvName.setText(itemComposition.getProductName());*/
                        tvProductName.setText(itemComposition.getProductName());
                        txvProductName.setText(itemComposition.getProductName());


                        for (int k = 0; k < list.size(); k++) {
                            if (list.get(k).equals(itemComposition.getProductName())) {
                                exist = true;
                            }
                        }

                        if (exist != true) {
                            list.add(itemComposition.getProductName());

                        Log.d("before duplicate sum", list.toString());

                        for (int c = 0; c < list.size(); c++) {
                            String str = list.toString();
                            str = str.substring(0, str.length() - 1);
                            str = str.substring(1);
                            txvName.setText(str);
                        }

                        Log.d("duplicate sum", list.toString());

                    }
                    lnrSummary.addView(v);
                }

                    lnPackage.addView(v2);

            }


        }


    }

    }

    @OnClick(R.id.bOk)
    void onClickOk() {
        PrefHelper.clearPreference(PrefKey.CART);
        getActivity().finish();
        MainActivity.startActivity((BaseActivity) getActivity());
    }

    /*
    @OnClick(R.id.btn_copy)
    void copyToClip(){
        copyToClipboard();
    }
    */


    void copyToClipboard(){
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(norek.toString());
            Toast.makeText(getContext(), R.string.info_copy_to_clipboard_norek, Toast.LENGTH_LONG).show();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Jumlah tranfer", norek.toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), R.string.info_copy_to_clipboard_norek, Toast.LENGTH_LONG).show();
        }
    }



}
