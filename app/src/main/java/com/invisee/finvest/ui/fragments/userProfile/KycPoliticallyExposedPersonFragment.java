package com.invisee.finvest.ui.fragments.userProfile;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Country;
import com.invisee.finvest.data.api.requests.KycDataRequest;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.util.Constant;
import com.invisee.finvest.util.LocationHelper;
import com.invisee.finvest.util.eventBus.RxBusObject;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by fajarfatur on 2/5/16.
 */
public class KycPoliticallyExposedPersonFragment extends KycBaseFragment implements AdapterView.OnItemSelectedListener, LocationHelper.Callback {

    @Bind(R.id.sPepCountry)
    Spinner sPepCountry;
    @Bind(R.id.sRelationship)
    Spinner sRelationship;
    @Bind(R.id.etPepName)
    EditText etPepName;
    @Bind(R.id.etPepPosition)
    EditText etPepPosition;
    @Bind(R.id.etOtherPepPublicFunction)
    EditText etPepPublicFunction;
    @Bind(R.id.etYearOfService)
    EditText etYearOfService;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.edtHidden)
    EditText edtHidden;

    private LocationHelper locationHelper;
    private Country country;

    public static KycPoliticallyExposedPersonFragment initiateFragment(KycDataRequest kycDataRequest) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KYC_DATA_REQUEST, kycDataRequest);
        KycPoliticallyExposedPersonFragment fragment = new KycPoliticallyExposedPersonFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.f_kyc_pollitically_exposed_person;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        etPepName.setText(request.getPepName());
        etPepPosition.setText(request.getPepPosition());
        etPepPublicFunction.setText(request.getPepPublicFunction());
        etYearOfService.setText(request.getPepYearOfService());

        setupSpinnerWithSpecificLookupSelection(sRelationship, getKycLookupFromRealm(Constant.KYC_CAT_PEP_RELATIONSHIP), request.getPepRelationship());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            locationHelper = new LocationHelper(getApi(), this);
            showProgressDialog(loading);
            locationHelper.loadCountriesStatesCities();
        }
    }

    @OnClick(R.id.fabPrev)
    void previousForm() {
        getBus().send(new RxBusObject(RxBusObject.RxBusKey.KYC_PREVIOUS_FORM, null));
    }

    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();
        if (country == null) country = locationHelper.getSelectedCountry();
        /*request.setPepCountry(country != null ? new PepCountry(country.getId()) : null);*/
        request.setPepName(getAndTrimValueFromEditText(etPepName));
        request.setPepPosition(getAndTrimValueFromEditText(etPepPosition));
        request.setPepPublicFunction(getAndTrimValueFromEditText(etPepPublicFunction));
        request.setPepYearOfService(getAndTrimValueFromEditText(etYearOfService));
        request.setPepRelationship(getKycLookupCodeFromSelectedItemSpinner(sRelationship));
        request.setToken(PrefHelper.getString(PrefKey.TOKEN));
        getBus().send(new RxBusObject(RxBusObject.RxBusKey.SUBMIT_KYC_DATA, request));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.sPepCountry:
                country = (Country) sPepCountry.getSelectedItem();
                showProgressDialog(loading);
                locationHelper.loadStatesCitiesBySelectedCountry(String.valueOf(country.getId()));
                break;
        }
    }

    @Override
    public void onRetrieveLocationDataComplete(LocationHelper.LoadType loadType) {
        dismissProgressDialog();
        switch (loadType) {
            case COUNTRIES_STATES_CITIES:
                setupSpinnerCountry(locationHelper.getCountries(), request.getLegalCountry());
                break;
        }

    }

    public void setupSpinnerCountry(List<Country> countries, String selectionCountryId) {
        if (countries == null) countries = new ArrayList<>();
        ArrayAdapter<Country> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, countries);
        sPepCountry.setAdapter(spinnerArrayAdapter);
        sPepCountry.setOnItemSelectedListener(this);
        sPepCountry.setSelection(locationHelper.getDefaultIndexCountry(), false); // set to default (Indonesia)
        if (selectionCountryId != null) { // set selection to saved data from realm / ws
            int i = 0;
            for (Country country : countries) {
                if (country.getId() == Integer.valueOf(selectionCountryId)) {
                    sPepCountry.setSelection(i, false);
                    break;
                }
                i++;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}

