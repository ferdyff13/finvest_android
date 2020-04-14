package com.invisee.finvest.ui.fragments.userProfile;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.beans.Bank;
import com.invisee.finvest.data.api.requests.KycDataRequest;
import com.invisee.finvest.data.api.responses.CustomerDocumentSettlementResponse;
import com.invisee.finvest.data.api.responses.SettlementInfoResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.FatcaActivity;
import com.invisee.finvest.util.DateUtil;
import com.invisee.finvest.util.eventBus.RxBusObject;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.squareup.picasso.Picasso;


import org.joda.time.DateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by pandu.abbiyuarsyah on 17/02/2017.
 */

public class KycSettlementFragment extends KycBaseFragment implements AdapterView.OnItemSelectedListener {

    @Bind(R.id.sBankName)
    Spinner sBankName;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etAccountName)
    EditText etAccountName;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etAccountNumber)
    EditText etAccountNumber;
    @Bind(R.id.imvBankBook)
    ImageView imvBankBook;

    private boolean status;
    private boolean emptyString;

    public static final String TAG = KycSettlementFragment.class.getSimpleName();

    public static final String KYC_DATA_REQUEST = "kycDataRequest";


    public static final String MyPREFERENCES = "MyPrefs";
    public static final int SELECT_PHOTO_TAX = 4;

    private KycSettlementInfoPresenter presenter;
    @State
    KycDataRequest kycDataRequest;

    public KycSettlementFragment() {
    }

    public static KycSettlementFragment initiateFragment(KycDataRequest kycDataRequest) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KYC_DATA_REQUEST, kycDataRequest);
        KycSettlementFragment fragment = new KycSettlementFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static void showFragment(BaseActivity sourceActivity, KycDataRequest kycDataRequest) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            KycSettlementFragment fragment = new KycSettlementFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(KYC_DATA_REQUEST, kycDataRequest);
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment, TAG);
            fragmentTransaction.commit();
        }
    }


    @Override
    protected int getLayout() {
        return R.layout.f_kyc_settlement_info;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new KycSettlementInfoPresenter(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        status = false;
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadBanksBranches();
        getDefaultValue();
        getDoc4();
        setBirthdate();
        setIdExpirationDate();
}

    @Override
    public void onStart() {
        super.onStart();
        presenter.loadBanksBranches();
        getDefaultValue();
        getDoc4();
    }

    private void generateRequest() {
        request.setBankId(presenter.getSelectedBank() != null ? presenter.getSelectedBank().getId() : "");
        //request.setBranchId(presenter.getSelectedBranch() != null ? String.valueOf(presenter.getSelectedBranch().getId()) : "");
        request.setSettlementAccountName(getAndTrimValueFromEditText(etAccountName));
        Log.d(TAG, "generateRequest: " + getAndTrimValueFromEditText(etAccountName));
        request.setSettlementAccountNo(getAndTrimValueFromEditText(etAccountNumber));
        Log.d(TAG, "generateRequest: " + request.getSettlementAccountName() + request.getSettlementAccountNo());
    }

    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();
        if (!status) {
            if(PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("ACT")) {
                generateRequest();
                getBus().send(new RxBusObject(RxBusObject.RxBusKey.SUBMIT_KYC_DATA, request));
            } else {
                FatcaActivity.startActivity((BaseActivity) getActivity());
            }
        } else {
            if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("VER") || PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("PEN")) {
                showDialogToSaveData();
            } else {
                emptyStringValidation();
                if (emptyString == true) {
                    generateRequest();
                    getBus().send(new RxBusObject(RxBusObject.RxBusKey.SUBMIT_KYC_DATA, request));
                } else {
                    ((TextView)sBankName.getSelectedView()).setError("Mohon isi kolom ini");

                }

            }
        }
    }

    void onLoadBanksBrachesDataComplete(KycSettlementInfoPresenter.LoadType loadType) {
        SharedPreferences prefs = getContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        dismissProgressDialog();
        switch (loadType) {
            case BANKS_BRANCHES:
                Object toString = request.getBankId();
                setupSpinnerBank(presenter.getBanks(), String.valueOf(toString));
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.sBankName:
                showProgressDialog(loading);

                //Membandingkan value request dan perubahan item yang dipilih
                String bankId = request.getBankId();
                if (!String.valueOf(((Bank) sBankName.getSelectedItem()).getId()).equalsIgnoreCase(bankId)) {
                    status = true;
                }
                Log.d(TAG, "onItemSelected: " + request.getBankId() + " value " + String.valueOf(((Bank) sBankName.getSelectedItem()).getId()));

                presenter.setSelectedBank((Bank) spinner.getSelectedItem());
                presenter.loadBranchBySelectedBank(presenter.getSelectedBank().getId());
                break;
            /*
            case R.id.sBranchName:
                presenter.setSelectedBranch((Branch) spinner.getSelectedItem());
                break;*/
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setupSpinnerBank(List<Bank> banks, String selectionBankId) {
        if (banks == null) banks = new ArrayList<>();
        ArrayAdapter<Bank> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, banks);
        sBankName.setAdapter(spinnerArrayAdapter);
        sBankName.setOnItemSelectedListener(this);
        sBankName.setSelection(0, false);
        Log.d(TAG, "setupSpinnerBank: " + selectionBankId);
        if (!selectionBankId.equalsIgnoreCase("null")) { // set selection to saved data from realm / ws
            int i = 0;
            for (Bank bank : banks) {
                if (bank.getId().equalsIgnoreCase(selectionBankId)) {
                    sBankName.setSelection(i, false);
                    presenter.setSelectedBank((Bank) sBankName.getSelectedItem());
                    break;
                }
                i++;
            }
        }
    }

    /*
    public void setupSpinnerBranches(List<Branch> branches, String selectionBranchId) {
        if (branches == null) branches = new ArrayList<>();
        ArrayAdapter<Branch> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, branches);
        sBranchName.setAdapter(spinnerArrayAdapter);
        sBranchName.setOnItemSelectedListener(this);
        sBranchName.setSelection(0, false);
        if (!selectionBranchId.equalsIgnoreCase("null")) { // set selection to saved data from realm / ws
            int i = 0;
            for (Branch branch : branches) {
                if (branch.getId() == Integer.parseInt(selectionBranchId)) {
                    sBranchName.setSelection(i, false);
                    break;
                }
                i++;
            }
        }
    }*/


    @OnClick(R.id.btSettlementPhoto)
    void upload() {
        generateRequest();
        getBus().send(new RxBusObject(RxBusObject.RxBusKey.SAVE_KYC_DATA, request));
        startGalleryTax();
    }

    private void startGalleryTax() {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECT_PHOTO_TAX);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {

        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case SELECT_PHOTO_TAX:
                if (resultCode == RESULT_OK) {

                    Uri selectedImage = imageReturnedIntent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    File file = new File(picturePath);
                    long length = file.length() / 1024; // Size in KB

                    if (length > 2000) {
                        showFailedDialog(getResources().getString(R.string.uploadlimit));
                    } else {
                        generateRequest();
                        if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("VER")) {
                            presenter.uploadVerDoc4(presenter.constructUploadSettlement(), file);
                        } else {
                            presenter.uploadDoc4(presenter.constructUploadSettlement(), file);
                        }
                    }
                }
        }
    }

    private void getDoc4() {
        getApi().getDocumentDoc04(
                "DocTyp04",
                PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CustomerDocumentSettlementResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        // dismissProgressDialog();
                    }

                    @Override
                    public void onNext(final CustomerDocumentSettlementResponse response) {
                        Picasso.with(getContext()).load(InviseeService.IMAGE_CUSTOMER_DOWNLOAD_URL + response.getData().getFileKey() + "&token=" + PrefHelper.getString(PrefKey.TOKEN)).into(imvBankBook);

                        TextView doc4 = (TextView) getActivity().findViewById(R.id.doc4);
                        doc4.setText(response.getData().getFileName());
                        doc4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(InviseeService.Download + response.getData().getFileKey() + PrefHelper.getString(PrefKey.TOKEN)));
                                request.setDescription("A download package with some files");
                                request.setTitle("Download package");
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                request.allowScanningByMediaScanner();
                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "File Download");

                                // get download service and enqueue file
                                DownloadManager manager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                                manager.enqueue(request);
                            }
                        });
                    }
                });
    }

    private void getDefaultValue() {
        etAccountNumber.setText(request.getSettlementAccountNo());
        etAccountName.setText(request.getSettlementAccountName());
    }

    void emptyStringValidation() {
        emptyString = !sBankName.getSelectedItem().toString().equals("");
    }

    void init() {
        etAccountName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    status = true;
            }
        });

        etAccountNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                        generateRequest();
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

    void setBirthdate() {
            if (request.getBirthDate() != null) {
                Date date;
                int day;
                int month;
                int year;

                if (request.getBirthDate().length() == 8) { // if date on realm saved as DDMMYYYY format
                    date = DateUtil.format(request.getBirthDate(), DateUtil.MMDDYYYY);
                } else {
                    date = DateUtil.format(request.getBirthDate(), DateUtil.INVISEE_RETURN_FORMAT2);
                }

                DateTime dateTime = new DateTime(date);
                day = dateTime.getDayOfMonth();
                month = dateTime.getMonthOfYear();
                year = dateTime.getYear();

                request.setBirthDate(setFormatDate(year, month, day));
            }
    }

    void setIdExpirationDate() {
        if (request.getIdExpirationDate() != null) {
            Date date;
            int day;
            int month;
            int year;

            if (request.getIdExpirationDate().length() == 8) { // if date on realm saved as DDMMYYYY format
                date = DateUtil.format(request.getIdExpirationDate(), DateUtil.MMDDYYYY);
            } else {
                date = DateUtil.format(request.getIdExpirationDate(), DateUtil.INVISEE_RETURN_FORMAT2);
            }

            DateTime dateTime = new DateTime(date);
            day = dateTime.getDayOfMonth();
            month = dateTime.getMonthOfYear();
            year = dateTime.getYear();

            request.setIdExpirationDate(setFormatDate(year, month, day));
        }

    }

    private String setFormatDate(int year, int monthOfYear, int dayOfMonth) {
        return DateUtil.format(getDateTime(year, monthOfYear, dayOfMonth).toDate(), DateUtil.MMDDYYYY);
    }

    @NonNull
    private DateTime getDateTime(int year, int monthOfYear, int dayOfMonth) {
        return new DateTime(year, monthOfYear, dayOfMonth, 0, 0, 0, 0);
    }


}


