package com.invisee.finvest.ui.fragments.userProfile;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.requests.KycDataRequest;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.util.Constant;
import com.invisee.finvest.util.LocationHelper;
import com.invisee.finvest.util.eventBus.RxBusObject;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import lecho.lib.hellocharts.model.Line;

/**
 * Created by fajarfatur on 2/5/16.
 */
public class KycInvestmentProfileFragment extends KycBaseFragment {

    @Bind(R.id.sSourceOfIncome)
    Spinner sSourceOfIncome;
    @Bind(R.id.sTotalOfIncome)
    Spinner sTotalOfIncome;
    @Bind(R.id.sInvestmentPurpose)
    Spinner sInvestmentPurpose;
    @Bind(R.id.sTotalAsset)
    Spinner sTotalAsset;
    @Bind(R.id.sInvestmentExperience)
    Spinner sInvestmentExperience;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.edtHidden)
    EditText edtHidden;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etOtherInvestmentExp)
    EditText edtOtherInvestment;
    @Bind(R.id.l_other_investment)
    LinearLayout lOtherInvestment;

    private boolean emptysSourceOfIncome = true;
    private boolean emptysTotalOfIncome = true;
    private boolean emptysInvestmentPurpose= true;
    private boolean emptysTotalAsset = true;
    private boolean emptysInvestmentExperience = true;

    private boolean emptyString = false;

    private final String otherInvestmentCode = "IE04";
    private Object investmentCode;
    private boolean status;
    private static final String TAG = KycInvestmentProfileFragment.class.getSimpleName();

    public KycInvestmentProfileFragment() {
    }

    public static KycInvestmentProfileFragment initiateFragment(KycDataRequest kycDataRequest) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KYC_DATA_REQUEST, kycDataRequest);
        KycInvestmentProfileFragment fragment = new KycInvestmentProfileFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.f_kyc_investment_profile;
    }


    @Override
    public void onResume() {
        super.onResume();

        setupSpinnerWithSpecificLookupSelection(sSourceOfIncome, getKycLookupFromRealm(Constant.KYC_CAT_SOURCE_OF_INCOME), request.getSourceOfIncome());
        setupSpinnerWithSpecificLookupSelection(sTotalOfIncome, getKycLookupFromRealm(Constant.KYC_CAT_ANNUAL_INCOME), request.getTotalIncomePa());
        setupSpinnerWithSpecificLookupSelection(sInvestmentPurpose, getKycLookupFromRealm(Constant.KYC_CAT_INVESTMENT_PURPOSE), request.getInvestmentPurpose());

        setupSpinnerWithSpecificLookupSelection(sTotalAsset, getKycLookupFromRealm(Constant.KYC_CAT_TOTAL_ASSET), request.getTotalAsset());
        setupSpinnerWithSpecificLookupSelection(sInvestmentExperience, getKycLookupFromRealm(Constant.KYC_CAT_INVESTMENT_EXPERIENCE), request.getInvestmentExperience());

        investmentCode = request.getInvestmentExperience();

        if (String.valueOf(investmentCode).equals(otherInvestmentCode)) {
            lOtherInvestment.setVisibility(View.VISIBLE);
            request.setOtherInvesmentExperience(request.getOtherInvesmentExperience() == null ? "" : request.getOtherInvesmentExperience());
        } else {
            edtOtherInvestment.setText("");
            edtOtherInvestment.setVisibility(View.GONE);
            lOtherInvestment.setVisibility(View.GONE);
        }
    }

/*    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            locationHelper = new LocationHelper(getApi(), this);
            showProgressDialog(loading);
            locationHelper.loadCountriesStatesCities();
        }
    }*/

    @Override
    public void onValidationSucceeded() {
        if (!status) {
            if(PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("ACT")) {
                emptyStringValidation();
                if (emptyString == true){
                    setValueToRequest();
                    getBus().send(new RxBusObject(RxBusObject.RxBusKey.SUBMIT_KYC_DATA, request));
                } else {
                    if (emptysSourceOfIncome == false) {
                        ((TextView)sSourceOfIncome.getSelectedView()).setError("Mohon isi kolom ini");
                    } else if (emptysTotalOfIncome == false) {
                        ((TextView)sTotalOfIncome.getSelectedView()).setError("Mohon isi kolom ini");
                    } else if (emptysInvestmentPurpose == false) {
                        ((TextView)sInvestmentPurpose.getSelectedView()).setError("Mohon isi kolom ini");
                    } else if (emptysTotalAsset == false) {
                        ((TextView)sTotalAsset.getSelectedView()).setError("Mohon isi kolom ini");
                    } else if (emptysInvestmentExperience == false) {
                        ((TextView)sInvestmentExperience.getSelectedView()).setError("Mohon isi kolom ini");
                    }
                }

            } else {
                getBus().send(new RxBusObject(RxBusObject.RxBusKey.NEXT_FORM, null));
            }
        } else {
            if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("VER") || PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("PEN")) {
                showDialogToSaveData();
            } else {
                emptyStringValidation();
                if (emptyString == true){
                    setValueToRequest();
                    getBus().send(new RxBusObject(RxBusObject.RxBusKey.SUBMIT_KYC_DATA, request));
                } else {
                    if (emptysSourceOfIncome == false) {
                        ((TextView)sSourceOfIncome.getSelectedView()).setError("Mohon isi kolom ini");
                    } else if (emptysTotalOfIncome == false) {
                        ((TextView)sTotalOfIncome.getSelectedView()).setError("Mohon isi kolom ini");
                    } else if (emptysInvestmentPurpose == false) {
                        ((TextView)sInvestmentPurpose.getSelectedView()).setError("Mohon isi kolom ini");
                    } else if (emptysTotalAsset == false) {
                        ((TextView)sTotalAsset.getSelectedView()).setError("Mohon isi kolom ini");
                    } else if (emptysInvestmentExperience == false) {
                        ((TextView)sInvestmentExperience.getSelectedView()).setError("Mohon isi kolom ini");
                    }
                }

            }
        }
    }


    @OnItemSelected(R.id.sInvestmentExperience)
    public void onInvestmentExperience() {

        if (getKycLookupCodeFromSelectedItemSpinner(sInvestmentExperience).equals(otherInvestmentCode)) {
            lOtherInvestment.setVisibility(View.VISIBLE);
            edtOtherInvestment.setVisibility(View.VISIBLE);
            request.setOtherInvesmentExperience(request.getOtherInvesmentExperience() == null ? "" : request.getOtherInvesmentExperience());
            edtOtherInvestment.setText(request.getOtherInvesmentExperience());
        } else {
            edtOtherInvestment.setText("");
            edtOtherInvestment.setVisibility(View.GONE);
            lOtherInvestment.setVisibility(View.GONE);
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        status = false;
        init();
    }

    void init() {
        sSourceOfIncome.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                status = true;
                return false;
            }
        });

        sTotalOfIncome.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                status = true;
                return false;
            }
        });

        sInvestmentPurpose.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                status = true;
                return false;
            }
        });

        sTotalAsset.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                status = true;
                return false;
            }
        });

        sInvestmentExperience.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                status = true;
                return false;
            }
        });

        edtOtherInvestment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    status = true;
            }
        });
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
                        setValueToRequest();
                        getBus().send(new RxBusObject(RxBusObject.RxBusKey.SUBMIT_KYC_DATA, request));
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        onResume();
                        status = false;
                        getBus().send(new RxBusObject(RxBusObject.RxBusKey.NEXT_FORM, null));
                    }
                })
                .show();
    }

    void emptyStringValidation() {
        if (sSourceOfIncome.getSelectedItem().toString().equals("")) {
            emptysSourceOfIncome = false;
            emptysTotalOfIncome = true;
            emptysInvestmentPurpose= true;
            emptysTotalAsset = true;
            emptysInvestmentExperience = true;

            emptyString = false;
        } else if (sTotalOfIncome.getSelectedItem().toString().equals("")) {
            emptysSourceOfIncome = true;
            emptysTotalOfIncome = false;
            emptysInvestmentPurpose= true;
            emptysTotalAsset = true;
            emptysInvestmentExperience = true;

            emptyString = false;
        } else if(sInvestmentPurpose.getSelectedItem().toString().equals("")) {
            emptysSourceOfIncome = true;
            emptysTotalOfIncome = true;
            emptysInvestmentPurpose= false;
            emptysTotalAsset = true;
            emptysInvestmentExperience = true;

            emptyString = false;
        } else if (sTotalAsset.getSelectedItem().toString().equals("")) {
            emptysSourceOfIncome = true;
            emptysTotalOfIncome = true;
            emptysInvestmentPurpose= true;
            emptysTotalAsset = false;
            emptysInvestmentExperience = true;

            emptyString = false;
        } else if(sInvestmentExperience.getSelectedItem().toString().equals("")) {
            emptysSourceOfIncome = true;
            emptysTotalOfIncome = true;
            emptysInvestmentPurpose= true;
            emptysTotalAsset = true;
            emptysInvestmentExperience = false;

            emptyString = false;
        } else {
            emptysSourceOfIncome = true;
            emptysTotalOfIncome = true;
            emptysInvestmentPurpose= true;
            emptysTotalAsset = true;
            emptysInvestmentExperience = true;

            emptyString = true;
        }
    }

    void setValueToRequest(){
        request.setSourceOfIncome(getKycLookupCodeFromSelectedItemSpinner(sSourceOfIncome));
        request.setTotalIncomePa(getKycLookupCodeFromSelectedItemSpinner(sTotalOfIncome));
        request.setInvestmentPurpose(getKycLookupCodeFromSelectedItemSpinner(sInvestmentPurpose));
        request.setTotalAsset(getKycLookupCodeFromSelectedItemSpinner(sTotalAsset));
        request.setInvestmentExperience(getKycLookupCodeFromSelectedItemSpinner(sInvestmentExperience));

        if (String.valueOf(investmentCode).equals(otherInvestmentCode)) {
            request.setOtherInvesmentExperience(edtOtherInvestment.getText().toString());
        }
        if (edtOtherInvestment.getVisibility() == View.VISIBLE) {
            request.setOtherInvesmentExperience(edtOtherInvestment.getText().toString());
        }
        request.setToken(PrefHelper.getString(PrefKey.TOKEN));
    }

}

