package com.invisee.finvest.ui.fragments.userProfile;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.City;
import com.invisee.finvest.data.api.beans.Country;
import com.invisee.finvest.data.api.beans.State;
import com.invisee.finvest.data.api.requests.KycDataRequest;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
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
//public class KycOfficeAddressFragment extends KycBaseFragment implements AdapterView.OnItemSelectedListener, LocationHelper.Callback {
//
//    @Bind(R.id.sOfficeCountry)
//    Spinner sOfficeCountry;
//    @Bind(R.id.sOfficeProvince)
//    Spinner sOfficeProvince;
//    @Bind(R.id.sOfficeCity)
//    Spinner sOfficeCity;
////    @NotEmpty(messageResId = R.string.rules_no_empty)
////    @Bind(R.id.etOfficeAddress)
////    EditText etOfficeAddress;
//    @NotEmpty(messageResId = R.string.rules_no_empty)
//    @Bind(R.id.etOfficePostalCode)
//    EditText etOfficePostalCode;
//    @NotEmpty(messageResId = R.string.rules_no_empty)
//    @Bind(R.id.etCountryCode)
//    EditText etCountryCode;
//    @NotEmpty(messageResId = R.string.rules_no_empty)
//    @Bind(R.id.etCityCode)
//    EditText etCityCode;
//    @NotEmpty(messageResId = R.string.rules_no_empty)
//    @Bind(R.id.etOfficePhoneNumber)
//    EditText etOfficePhoneNumber;
//
//    private LocationHelper locationHelper;
//    private Country country;
//    private State state;
//    private City city;
//
//    public static KycOfficeAddressFragment initiateFragment(KycDataRequest kycDataRequest) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(KYC_DATA_REQUEST, kycDataRequest);
//        KycOfficeAddressFragment fragment = new KycOfficeAddressFragment();
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//
//    @Override
//    protected int getLayout() {
//        return R.layout.f_kyc_office_address;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        //etOfficeAddress.setText(request.getOfficeAddress());
//        etOfficePostalCode.setText(request.getOfficePostalCode());
//        etOfficePhoneNumber.setText(request.getOfficePhoneNumber());
//        if (request.getOfficePhoneNumber() != null) {
//            String[] splitedPhoneNumber = request.getOfficePhoneNumber().split("-");
//            etCountryCode.setText(splitedPhoneNumber[0]);
//            etCityCode.setText(splitedPhoneNumber[1]);
//            etOfficePhoneNumber.setText(splitedPhoneNumber[2]);
//        }
//    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            locationHelper = new LocationHelper(getApi(), this);
//            showProgressDialog(loading);
//            locationHelper.loadCountriesStatesCities();
//        }
//    }
//
//    @OnClick(R.id.fabPrev)
//    void previousForm() {
//        getBus().send(new RxBusObject(RxBusObject.RxBusKey.KYC_PREVIOUS_FORM, null));
//    }
//
//    @Override
//    public void onValidationSucceeded() {
//        super.onValidationSucceeded();
//        if (country == null) country = locationHelper.getSelectedCountry();
//        if (state == null) state = locationHelper.getSelectedState();
//        if (city == null) city = locationHelper.getSelectedCity();
//        request.setOfficeCountry(country != null ? String.valueOf(country.getId()) : null);
//        request.setOfficeProvince(state != null ? state.getStateCode() : null);
//        request.setOfficeCity(city != null ? String.valueOf(city.getId()) : null);
//        //request.setOfficeAddress(getAndTrimValueFromEditText(etOfficeAddress));
//        request.setOfficePostalCode(getAndTrimValueFromEditText(etOfficePostalCode));
//        request.setOfficePhoneNumber(constructPhoneNumber(etCountryCode, etCityCode, etOfficePhoneNumber));
//        request.setToken(PrefHelper.getString(PrefKey.TOKEN));
//        getBus().send(new RxBusObject(RxBusObject.RxBusKey.SUBMIT_KYC_DATA, request));
//    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Spinner spinner = (Spinner) parent;
//        switch (spinner.getId()) {
//            case R.id.sOfficeCountry:
//                country = (Country) sOfficeCountry.getSelectedItem();
//                state = null;
//                city = null;
//                showProgressDialog(loading);
//                locationHelper.loadStatesCitiesBySelectedCountry(String.valueOf(country.getId()));
//                break;
//            case R.id.sOfficeProvince:
//                state = (State) sOfficeProvince.getSelectedItem();
//                city = null;
//                showProgressDialog(loading);
//                locationHelper.loadCitiesBySelectedState(state.getStateCode());
//                break;
//            case R.id.sOfficeCity:
//                city = (City) sOfficeCity.getSelectedItem();
//                break;
//        }
//    }
//
//    @Override
//    public void onRetrieveLocationDataComplete(LocationHelper.LoadType loadType) {
//        dismissProgressDialog();
//        switch (loadType) {
//            case COUNTRIES_STATES_CITIES:
//                setupSpinnerCountry(locationHelper.getCountries(), request.getOfficeCountry());
//                break;
//            case STATES_CITIES:
//                setupSpinnerState(locationHelper.getStates(), request.getOfficeProvince());
//                break;
//            case CITIES:
//                setupSpinnerCity(locationHelper.getCities(), request.getOfficeCity());
//                break;
//        }
//
//    }
//
//    public void setupSpinnerCountry(List<Country> countries, String selectionCountryId) {
//        if (countries == null) countries = new ArrayList<>();
//        ArrayAdapter<Country> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, countries);
//        sOfficeCountry.setAdapter(spinnerArrayAdapter);
//        sOfficeCountry.setOnItemSelectedListener(this);
//        sOfficeCountry.setSelection(locationHelper.getDefaultIndexCountry(), false); // set to default (Indonesia)
//        if (selectionCountryId != null) { // set selection to saved data from realm / ws
//            int i = 0;
//            for (Country country : countries) {
//                if (country.getId() == Integer.valueOf(selectionCountryId)) {
//                    sOfficeCountry.setSelection(i, false);
//                    break;
//                }
//                i++;
//            }
//        }
//    }
//
//    public void setupSpinnerState(List<State> states, String selectionStateCode) {
//        if (states == null) states = new ArrayList<>();
//        ArrayAdapter<State> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, states);
//        sOfficeProvince.setAdapter(spinnerArrayAdapter);
//        sOfficeProvince.setOnItemSelectedListener(this);
//        sOfficeProvince.setSelection(0, false);
//        if (selectionStateCode != null) { // set selection to saved data from realm / ws
//            int i = 0;
//            for (State state : states) {
//                if (state.getStateCode().equalsIgnoreCase(selectionStateCode)) {
//                    sOfficeProvince.setSelection(i, false);
//                    break;
//                }
//                i++;
//            }
//        }
//    }
//
//    public void setupSpinnerCity(List<City> cities, String selectionCityId) {
//        if (cities == null) cities = new ArrayList<>();
//        ArrayAdapter<City> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, cities);
//        sOfficeCity.setAdapter(spinnerArrayAdapter);
//        sOfficeCity.setOnItemSelectedListener(this);
//        sOfficeCity.setSelection(0, false);
//        if (selectionCityId != null) { // set selection to saved data from realm / ws
//            int i = 0;
//            for (City city : cities) {
//                if (city.getId() == Integer.valueOf(selectionCityId)) {
//                    sOfficeCity.setSelection(i, false);
//                    break;
//                }
//                i++;
//            }
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
//}

