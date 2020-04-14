package com.invisee.finvest.ui.fragments.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.TransactionHistory;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.adapters.rv.PendingOrderAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;

import java.io.File;
import java.util.ArrayList;


import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by fajarfatur on 3/6/16.
 */
public class PendingOrderFragment extends BaseFragment {

    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.llTransaction)
    LinearLayout llTransaction;
    @Bind(R.id.llNoTransaction)
    LinearLayout llNoTranscation;
    @Bind(R.id.lnProgressBar)
    LinearLayout lnProgressBar;
    @Bind(R.id.lnDismissBar)
    RelativeLayout lnDismissBar;
    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;
    @Bind(R.id.lnConnectionError)
    LinearLayout lnConnectionError;


    @State
    ArrayList<TransactionHistory> transactionHistories;

    private PendingOrderPresenter presenter;
    public File file;

    public static final String TAG = PendingOrderFragment.class.getSimpleName();

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.container, new PendingOrderFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_pending_order;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PendingOrderPresenter(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRv();
        if (transactionHistories != null) {
            loadList();
        } else {
            presenter.getPendingTransaction();
        }

    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        getActivity().setTitle("Pesanan Anda");
//        initRv();
//        if (transactionHistories != null) {
//            loadList();
//        } else {
//            presenter.getPendingTransaction();
//        }
//    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        activityCreated = true;
//        if (visibleToUser) {
//            getBus().send(new RxBusObject(RxBusObject.RxBusKey.SELECTED_DASHBOARD_MENU, 1));
//        }
//    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            visibleToUser = true;
//            if (activityCreated) {
//                getBus().send(new RxBusObject(RxBusObject.RxBusKey.SELECTED_DASHBOARD_MENU, 1));
//            }
//        }else{
//            visibleToUser = false;
//        }
//    }

    public void initRv() {
        LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llManager);
    }

    public void loadList() {
        rv.setAdapter(new PendingOrderAdapter(transactionHistories, getContext(), presenter));
    }

    void llNoTranscation(boolean b) {
        llTransaction.setVisibility(b ? View.VISIBLE : View.GONE);
        llNoTranscation.setVisibility(b ? View.GONE : View.VISIBLE);
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
        presenter.getPendingTransaction();
    }




//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case 0:
//                if (resultCode == RESULT_OK) {
//
//                    Uri selectedImage = data.getData();
//                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
//                            filePathColumn, null, null, null);
//                    cursor.moveToFirst();
//
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    String picturePath = cursor.getString(columnIndex);
//                    cursor.close();
//                    file = new File(picturePath);
//                }
//        }
//    }

}
