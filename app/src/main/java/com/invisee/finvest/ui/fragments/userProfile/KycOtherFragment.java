package com.invisee.finvest.ui.fragments.userProfile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.beans.Country;
import com.invisee.finvest.data.api.requests.KycDataRequest;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.util.Constant;
import com.invisee.finvest.util.DateUtil;
import com.invisee.finvest.util.LocationHelper;
import com.invisee.finvest.util.eventBus.RxBusObject;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import net.gotev.uploadservice.MultipartUploadRequest;

import org.joda.time.DateTime;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

/**
 * Created by fajarfatur on 2/5/16.
 */
public class KycOtherFragment extends KycBaseFragment implements DatePickerDialog.OnDateSetListener /*, AdapterView.OnItemSelectedListener, LocationHelper.Callback*/ {

    /*@Bind(R.id.sIdType)
    Spinner sIdType;*/
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etIdNumber)
    EditText etIdNumber;
/*    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etDay)
    EditText etDay;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etMonth)
    EditText etMonth;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etYear)
    EditText etYear;*/
/*    @Bind(R.id.sNationality)
    Spinner sNationality;*/
  /*  @Bind(R.id.sCitizenship)
    Spinner sCitizenship;*/
    @Bind(R.id.sGender)
    Spinner sGender;
    @Bind(R.id.sReligion)
    Spinner sReligion;
    @Bind(R.id.sMaritalStatus)
    Spinner sMaritalStatus;
    @Bind(R.id.sEducationBackground)
    Spinner sEducationBackground;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etMotherMaidenName)
    EditText etMotherMaidenName;
    @NotEmpty(messageResId = R.string.rules_no_empty)
/*    @Bind(R.id.etBeneficiaryName)
    EditText etBeneficiaryName;
    @Bind(R.id.sBeneficiaryRelationship)
    Spinner sBeneficiaryRelationship;*/
    @Bind(R.id.sPreferedMailingAddress)
    Spinner sPreferedMailingAddress;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etTaxId)
    EditText etTaxId;
    @NotEmpty(messageResId = R.string.rules_no_empty)
 /*   @Bind(R.id.etTaxIdRegDate)
    EditText etTaxIdRegDate;
*/
    @Bind(R.id.btTaxPhoto)
    Button btTaxPhoto;


    private LocationHelper locationHelper;
    private Country country;
    private String dateTemp, taxRegDateTemp;
    private DatePickerDialog dtExpiredDate, dtTaxRegDate;
    private final int SELECT_PHOTO_TAX = 3;

    public static KycOtherFragment initiateFragment(KycDataRequest kycDataRequest) {
        Timber.i("initiateFragment");
        Bundle bundle = new Bundle();
        bundle.putSerializable(KYC_DATA_REQUEST, kycDataRequest);
        KycOtherFragment fragment = new KycOtherFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.f_kyc_other;
    }

    @Override
    public void onResume() {
        super.onResume();
        etIdNumber.setText(request.getIdNumber());
        etMotherMaidenName.setText(request.getMotherMaidenName());
/*        etBeneficiaryName.setText(request.getBeneficiaryName());*/
        etTaxId.setText(request.getTaxId());
      /*  setupSpinnerWithSpecificLookupSelection(sIdType, getKycLookupFromRealm(Constant.KYC_CAT_ID_TYPE), request.getIdType());*/
        setupSpinnerWithSpecificLookupSelection(sGender, getKycLookupFromRealm(Constant.KYC_CAT_GENDER), request.getGender());
/*        setupSpinnerWithSpecificLookupSelection(sCitizenship, getKycLookupFromRealm(Constant.KYC_CAT_CITIZENSHIP), request.getCitizenship());*/
        setupSpinnerWithSpecificLookupSelection(sReligion, getKycLookupFromRealm(Constant.KYC_CAT_RELIGION), request.getReligion());
        setupSpinnerWithSpecificLookupSelection(sMaritalStatus, getKycLookupFromRealm(Constant.KYC_CAT_MARITAL_STATUS), request.getMaritalStatus());
        setupSpinnerWithSpecificLookupSelection(sEducationBackground, getKycLookupFromRealm(Constant.KYC_CAT_EDUCATION_BACKGROUND), request.getEducationBackground());
        /*setupSpinnerWithSpecificLookupSelection(sBeneficiaryRelationship, getKycLookupFromRealm(Constant.KYC_CAT_BENEFICIARY_TYPE), request.getBeneficiaryRelationship());*/
        setupExpirationDate();
//        setupTaxRegDate();
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
               /*    selectedImage = imageReturnedIntent.getData();
                    File file = new File(selectedImage.getPath()); */

                    Uri selectedImage = imageReturnedIntent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    try {
                        new MultipartUploadRequest(getContext(), "", InviseeService.UPLOAD)
                                .addFileToUpload(picturePath, "DocTyp02")
                                .addParameter("type", "DocTyp02")
                                .addParameter("token", PrefHelper.getString(PrefKey.TOKEN))
                                .startUpload();
                        showUploadSuccessDialog("Upload Success");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        showFailedDialog("Upload Gagal");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        showFailedDialog("Upload Gagal");
                    }
                }
        }
    }


    private void setupExpirationDate() {
        if (request.getIdExpirationDate() != null) {
            Date date;
            if (request.getIdExpirationDate().length() == 8) { // if date on realm saved as DDMMYYYY format
                date = DateUtil.format(request.getIdExpirationDate(), DateUtil.DDMMYYYY);
            } else {
                date = DateUtil.format(request.getIdExpirationDate(), DateUtil.INVISEE_RETURN_FORMAT2);
            }
            DateTime dateTime = new DateTime(date);
  /*          etDay.setText(String.valueOf(dateTime.getDayOfMonth()));
            etMonth.setText(String.valueOf(dateTime.getMonthOfYear()));
            etYear.setText(String.valueOf(dateTime.getYear()));*/
        }
    }

//    private void setupTaxRegDate() {
//        if (request.getTaxIdRegisDate() != null) {
//            Date date;
//            if (request.getTaxIdRegisDate().length() == 8) { // if date on realm saved as DDMMYYYY format
//                date = DateUtil.format(request.getTaxIdRegisDate(), DateUtil.DDMMYYYY);
//            } else {
//                date = DateUtil.format(request.getTaxIdRegisDate(), DateUtil.INVISEE_RETURN_FORMAT2);
//            }
//         /*   etTaxIdRegDate.setText(DateUtil.format(date, DateUtil.DD_MM_YYYY_));*/
//        }
//    }

/*
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            locationHelper = new LocationHelper(getApi(), this);
            showProgressDialog(loading);
            locationHelper.loadCountriesStatesCities();
        }
    }
*/

    @OnClick(R.id.fabPrev)
    void previousForm() {
        getBus().send(new RxBusObject(RxBusObject.RxBusKey.KYC_PREVIOUS_FORM, null));
    }

    @OnClick(R.id.btTaxPhoto)
    void uploadTaxPhoto() {
        startGalleryTax();
    }


    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();
        if (country == null) country = locationHelper.getSelectedCountry();
        request.setIdNumber(getAndTrimValueFromEditText(etIdNumber));
        request.setMotherMaidenName(getAndTrimValueFromEditText(etMotherMaidenName));
/*        request.setBeneficiaryName(getAndTrimValueFromEditText(etBeneficiaryName));*/
        request.setIdExpirationDate(dateTemp);
/*        request.setIdType(getKycLookupCodeFromSelectedItemSpinner(sIdType));*/
        request.setNationality(country != null ? String.valueOf(country.getId()) : null);
        request.setGender(getKycLookupCodeFromSelectedItemSpinner(sGender));
/*        request.setCitizenship(getKycLookupCodeFromSelectedItemSpinner(sCitizenship));*/
        request.setReligion(getKycLookupCodeFromSelectedItemSpinner(sReligion));
        request.setMaritalStatus(getKycLookupCodeFromSelectedItemSpinner(sMaritalStatus));
        request.setEducationBackground(getKycLookupCodeFromSelectedItemSpinner(sEducationBackground));
      /*  request.setBeneficiaryRelationship(getKycLookupCodeFromSelectedItemSpinner(sBeneficiaryRelationship));*/
        request.setPreferredMailingAddress("email");
        request.setTaxId(getAndTrimValueFromEditText(etTaxId));
/*        request.setTaxIdRegisDate(getAndTrimValueFromEditText(etTaxIdRegDate));*/
        request.setToken(PrefHelper.getString(PrefKey.TOKEN));
        getBus().send(new RxBusObject(RxBusObject.RxBusKey.SUBMIT_KYC_DATA, request));
    }

    /**
     * LOCATION(NATIONALITY)
     */

/*
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.sHomeCountry:
                country = (Country) sNationality.getSelectedItem();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
*/

/*
    @Override
    public void onRetrieveLocationDataComplete(LocationHelper.LoadType loadType) {
        dismissProgressDialog();
        switch (loadType) {
            case COUNTRIES_STATES_CITIES:
                setupSpinnerCountry(locationHelper.getCountries(), request.getNationality());
                break;
        }

    }
*/

/*    public void setupSpinnerCountry(List<Country> countries, String selectionCountryId) {

        if (countries == null) countries = new ArrayList<>();
        ArrayAdapter<Country> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, countries);
        sNationality.setAdapter(spinnerArrayAdapter);
        sNationality.setOnItemSelectedListener(this);
        sNationality.setSelection(locationHelper.getDefaultIndexCountry(), false); // set to default (Indonesia)
        if (selectionCountryId != null) { // set selection to saved data from realm / ws
            int i = 0;
            for (Country country : countries) {
                if (country.getId() == Integer.valueOf(selectionCountryId)) {
                    sNationality.setSelection(i, false);
                    break;
                }
                i++;
            }
        }

    }*/

    /**
     * DATE PICKER
     */

/*    @OnClick({R.id.etDay, R.id.etMonth, R.id.etYear})
    void birthDate() {
        dtExpiredDate = initDatePickerDialog();
        dtExpiredDate.show();
    }*/

/*    @OnClick(R.id.etTaxIdRegDate)
    void taxRegDate() {
        dtTaxRegDate = initDatePickerDialog();
        dtTaxRegDate.show();
    }*/

    @NonNull
    private DatePickerDialog initDatePickerDialog() {
        DateTime jodaTime = new DateTime();
        return new DatePickerDialog(getActivity(), this,
                jodaTime.getYear(),
                jodaTime.getMonthOfYear() - 1,
                jodaTime.getDayOfMonth());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        //EXPIRED_DATE
        if (view == dtExpiredDate.getDatePicker()) {
            /*set value to temp variable*/
            dateTemp = setFormatDate(year, monthOfYear, dayOfMonth);
        /**/

        }
        //TAX_REG_DATE
        else {
            /*set value to temp variable*/
            taxRegDateTemp = setFormatDate(year, monthOfYear, dayOfMonth);
        /**/
            Date date = new Date(year - 1900, monthOfYear, dayOfMonth);
     /*       etTaxIdRegDate.setText(DateUtil.format(date, DateUtil.DD_MM_YYYY_));*/
        }

    }

    @NonNull
    private String setFormatDate(int year, int monthOfYear, int dayOfMonth) {
        return DateUtil.format(getDateTime(year, monthOfYear, dayOfMonth).toDate(), DateUtil.DDMMYYYY);
    }

    @NonNull
    private DateTime getDateTime(int year, int monthOfYear, int dayOfMonth) {
        return new DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0, 0, 0);
    }


}

