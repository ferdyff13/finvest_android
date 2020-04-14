package com.invisee.finvest.ui.fragments.userProfile;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.beans.RiskProfileQuestion;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.FatcaActivity;
import com.invisee.finvest.ui.activities.UserProfileActivity;
import com.invisee.finvest.ui.adapters.pager.RiskProfileAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.eventBus.RxBusObject;
import com.invisee.finvest.util.ui.GlobalVariable;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by fajarfatur on 1/13/16.
 */
public class RiskProfileFragment extends BaseFragment {

    public static final String TAG = RiskProfileFragment.class.getSimpleName();

    @Bind(R.id.rv)
    RecyclerView rv;
    @State
    ArrayList<RiskProfileQuestion> riskProfileQuestionList;
    @State
    int currentPage;
    @State
    int totalPage;
    @Bind(R.id.tv_completeness)
    TextView tvCompleteness;
    @Bind(R.id.pb_completeness)
    ProgressBar pbCompleteness;
    @Bind(R.id.tvRiskProfileResult)
    TextView tvRiskProfileResult;
    @Bind(R.id.tvLabelSimpan)
    TextView tvLabelSimpan;

    private long mLastClickTime = 0;
    private RiskProfilePresenter presenter;
    private LinearLayoutManager llManager;
    private boolean status;
    private static boolean status1;

    private final static String BASE_URL_DEV = "http://52.76.229.157:8080/avantrade-portal-core/";

    public String batchDev = "41,84;42,88;43,92;44,96;45,100;46,104";
    public String batchProd = "41,145;42,149;43,153;44,157;45,161;46,165";
    public String batch;


    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, new RiskProfileFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_risk_profile;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new RiskProfilePresenter(this);

        riskProfileQuestionList = new ArrayList<RiskProfileQuestion>();

        presenter.getKycDataFromServer();

        if (BASE_URL_DEV.equals(InviseeService.BASE_URL)) {
            batch = batchDev;
        } else {
            batch = batchProd;
        }

//        if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("VER") || PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("PEN")) {
//            tvLabelSimpan.setText(R.string.risk_profile_save);
//        } else tvLabelSimpan.setText(R.string.risk_profile_send);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.getCompleteness();

        if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("VER") || PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("PEN")) {
            tvLabelSimpan.setText(R.string.risk_profile_save);
        } else {
            tvLabelSimpan.setText(R.string.risk_profile_send);
        }


    }

    private void initRV() {
//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        toolbar.setTitle(R.string.user_profile_risk_profile);


    }

    public void fetchQuestionToLayout() {
        llManager = new LinearLayoutManager(getActivity());
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llManager);
        rv.setAdapter(new RiskProfileAdapter(getActivity(), riskProfileQuestionList, status));
    }


    @OnClick(R.id.iv_right)
    void next() {
        valiadationTouchListener();
    }

    void valiadationTouchListener() {
        Log.d(TAG, "valiadationTouchListener: " + GlobalVariable.getInstance().status_next);
        if (!GlobalVariable.getInstance().status_next) {
            if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equalsIgnoreCase("ACT")){
                toNextForm();
            } else {
                showDialogNothingUpdate();
            }
        } else {
            showDialogToSaveData();
        }
    }

    @OnClick(R.id.iv_left)
    void previous() {
//        FatcaFragment.showFragment((BaseActivity) getActivity());
        FatcaActivity.startActivity((BaseActivity) getActivity());
    }

    /**
     * next to another risk profile form as long as didn't reach the last risk profile form,
     * when reach the last risk profile form, submit data to server
     */
    public void toNextForm() {
        presenter.saveOrUpdateRiskProfile(
                presenter.constructBatch(riskProfileQuestionList));
    }

    public void showConfirmation() {
        new MaterialDialog.Builder(getActivity())
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(R.string.user_profile_risk_profile)
                .titleColor(Color.WHITE)
                .content(R.string.risk_profile_confirmation)
                .contentGravity(GravityEnum.CENTER)
                .contentColor(Color.WHITE)
                .positiveText(R.string.risk_profile_confirmationend_ok)
                .positiveColor(Color.WHITE)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                        getBus().send(new RxBusObject(RxBusObject.RxBusKey.SUBMIT_USER_PROFILE_DATA_CONFIRMATION, null));
                    }
                })
                .negativeText(R.string.risk_profile_confirmationend_no)
                .negativeColor(Color.WHITE)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .cancelable(false)
                .show();
    }

    public void showDialogConservative() {
        new MaterialDialog.Builder(getActivity())
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(R.string.user_profile_risk_profile)
                .titleColor(Color.WHITE)
                .content(R.string.risk_profile_conservative)
                .contentGravity(GravityEnum.CENTER)
                .contentColor(Color.WHITE)
                .positiveText(R.string.risk_profile_confirmation_ok)
                .positiveColor(Color.WHITE)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                        presenter.saveOrUpdateRiskProfileConservative();
                    }
                })
                .negativeText(R.string.risk_profile_confirmation_no)
                .negativeColor(Color.WHITE)
                .onNegative(new MaterialDialog.SingleButtonCallback() {

                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .cancelable(false)
                .show();
    }

    public void showDialogGeneral(String info) {
        new MaterialDialog.Builder(getActivity())
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(getString(R.string.infortmation).toUpperCase())
                .titleColor(Color.WHITE)
                .content(info)
                .contentColor(Color.WHITE)
                .positiveText(R.string.ok)
                .positiveColor(Color.WHITE)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        UserProfileActivity.startActivity((BaseActivity) getActivity());
                    }
                })
                .show();
    }

    public void showDialogNothingUpdate() {
        new MaterialDialog.Builder(getActivity())
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(getString(R.string.infortmation).toUpperCase())
                .titleColor(Color.WHITE)
                .content(R.string.riskprofile_simpan_data)
                .contentColor(Color.WHITE)
                .positiveText(R.string.ok)
                .positiveColor(Color.WHITE)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    void showDialogToSaveData() {
        new MaterialDialog.Builder(getActivity())
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(getString(R.string.infortmation).toUpperCase())
                .titleColor(Color.WHITE)
                .content(R.string.kyc_update_data)
                .contentColor(Color.WHITE)
                .positiveText(R.string.btn_dialog_ya)
                .positiveColor(Color.WHITE)
                .negativeText(R.string.btn_dialog_tidak)
                .negativeColor(cPrimary)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        toNextForm();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        presenter.loadRiskProfileQuestionAndAnswer();
                        status = false;
                        dialog.dismiss();
                    }
                })
                .show();
    }


}
