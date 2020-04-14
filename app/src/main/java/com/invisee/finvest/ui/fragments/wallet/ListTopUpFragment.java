package com.invisee.finvest.ui.fragments.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.TopUpTransaction;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.DetailWaitingTopUpActivity;
import com.invisee.finvest.ui.activities.DetailWaitingTopUpFinPayActivity;
import com.invisee.finvest.ui.adapters.rv.TopUpHistoryAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.ui.RecyclerItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pandu.abbiyuarsyah on 19/10/2017.
 */

public class ListTopUpFragment extends BaseFragment {

    private static final String TAG = ListTopUpFragment.class.getSimpleName();

    @Bind(R.id.rv)
    RecyclerView rv;

    @Bind(R.id.sBank)
    Spinner sBank;
    @Bind(R.id.sStatus)
    Spinner sStatus;

    @Bind(R.id.lnData)
    LinearLayout lnData;
    @Bind(R.id.lnNoData)
    LinearLayout lnNoData;

    public List<TopUpTransaction> topUpTransaction;
    private ListTopUpPresenter presenter;
    private String selectedItemBank;
    private String selectedItemStatus;
    private String bank;
    private String status;

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.container, new ListTopUpFragment(), TAG);
            fragmentTransaction.commit();
        }
    }


    @Override
    protected int getLayout() {
        return R.layout.f_list_top_up;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ListTopUpPresenter(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRV();
        getSelectedItemSpinner();
    }
    void noData(boolean b) {
        lnNoData.setVisibility(b ? View.VISIBLE : View.GONE);
        lnData.setVisibility(b ? View.GONE : View.VISIBLE);
    }


    private void initRV() {
        LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llManager);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View childView, int position) {
                super.onItemClick(childView, position);
                TopUpTransaction topUpTransaction = (TopUpTransaction) childView.getTag();
                if (topUpTransaction.getTrxStatus().equals("WAIT")) {
                    if (topUpTransaction.getBankName().equals("Finpay")){
                        DetailWaitingTopUpFinPayActivity.startActivity((BaseActivity) getActivity(),  topUpTransaction);

                    } else {
                        DetailWaitingTopUpActivity.startActivity((BaseActivity) getActivity(),  topUpTransaction);
                        Log.d("responseInfo", "Success " + topUpTransaction.getTrxNo());
                    }

                } else {
                    Log.d("responseInfo", "disable");
                }

            }
        }));

    }

    public void loadtopuphistory(){
        rv.setAdapter(new TopUpHistoryAdapter(getActivity(), topUpTransaction));
    }

    void getSelectedItemSpinner() {
        sBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int spinner_pos = sBank.getSelectedItemPosition();
                String[] size_values = getResources().getStringArray(R.array.topup_bank);
                bank = size_values[spinner_pos];

                if (bank.contains("BCA")) {
                    selectedItemBank = "BCA";
                } else if (bank.contains("Mandiri")) {
                    selectedItemBank = "Mandiri";
                } else if (bank.contains("Finpay")) {
                    selectedItemBank = "FinPay";
                } else if (bank.contains("Semua")){
                    selectedItemBank = null;
                } else  {
                    selectedItemBank = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView){
            }
        });

        sStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int spinner_pos = sStatus.getSelectedItemPosition();
                String[] size_values = getResources().getStringArray(R.array.topup_status);
                status = size_values[spinner_pos];

                if (status.contains("Menunggu Pembayaran")) {
                    selectedItemStatus = "WAIT";
                } else if (status.contains("Telah Dibayar")) {
                    selectedItemStatus = "PAID";
                } else if (status.contains("Selesai")) {
                    selectedItemStatus = "DONE";
                } else if (status.contains("Batal")) {
                    selectedItemStatus = "CANC";
                } else if (status.contains("Semua")){
                    selectedItemStatus = null;
                } else {
                    selectedItemStatus = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @OnClick(R.id.btnSearch)
    void search() {
        presenter.getListTopUp(selectedItemBank, selectedItemStatus);    }

}
