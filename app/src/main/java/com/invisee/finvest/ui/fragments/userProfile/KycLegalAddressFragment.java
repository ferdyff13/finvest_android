package com.invisee.finvest.ui.fragments.userProfile;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
public class KycLegalAddressFragment extends KycBaseFragment implements AdapterView.OnItemSelectedListener, LocationHelper.Callback {

    @Bind(R.id.sLegalCountry)
    Spinner sLegalCountry;
    @Bind(R.id.sLegalProvince)
    Spinner sLegalProvince;
    @Bind(R.id.sLegalCity)
    Spinner sLegalCity;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etLegalAddress)
    EditText etLegalAddress;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etLegalPostalCode)
    EditText etLegalPostalCode;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etCountryCode)
    EditText etCountryCode;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etCityCode)
    EditText etCityCode;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etLegalPhoneNumber)
    EditText etLegalPhoneNumber;
    @Bind(R.id.checkboxPhone)
    CheckBox cbPhone;

    private boolean emptyCountry = true;
    private boolean emptyProvince = true;
    private boolean emptyCity = true;
    private boolean emptyValue = false;


    private LocationHelper locationHelper;
    private Country country;
    private State state;
    private City city;
    private boolean status;
    private static final String TAG = KycLegalAddressFragment.class.getSimpleName();

    public KycLegalAddressFragment() {
    }

    public static KycLegalAddressFragment initiateFragment(KycDataRequest kycDataRequest) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KYC_DATA_REQUEST, kycDataRequest);
        KycLegalAddressFragment fragment = new KycLegalAddressFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.f_kyc_legal_address;
    }


    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        status = false;
        init();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            etLegalAddress.setText(request.getHomeAddress());
            etLegalPostalCode.setText(request.getHomePostalCode());
            if (request.getHomePhoneNumber() != null) {
                try{
                    String[] splitedPhoneNumber = request.getHomePhoneNumber().split("-");
                    etCountryCode.setText(splitedPhoneNumber[0]);
                    etCityCode.setText(splitedPhoneNumber[1]);
                    etLegalPhoneNumber.setText(splitedPhoneNumber[2]);
                }catch(ArrayIndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
            locationHelper = new LocationHelper(getApi(), this);
            locationHelper.loadCountriesStatesCities();
        }
    }



    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();
        if (!status) {
            if(PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("ACT")) {
                emptyValue();
                if (emptyValue == true) {
                    setValueToRequest();
                    getBus().send(new RxBusObject(RxBusObject.RxBusKey.SUBMIT_KYC_DATA, request));
                } else {
                    if (emptyCountry == false) {
                        ((TextView)sLegalCountry.getSelectedView()).setError("Mohon isi kolom ini");
                    } else if (emptyProvince == false) {
                        ((TextView)sLegalProvince.getSelectedView()).setError("Mohon isi kolom ini");
                    } else if (emptyCity == false) {
                        ((TextView)sLegalCity.getSelectedView()).setError("Mohon isi kolom ini");
                    }
                }
            } else {
                getBus().send(new RxBusObject(RxBusObject.RxBusKey.NEXT_FORM, null));
            }
        } else {
            if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("VER") || PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("PEN")) {
                showDialogToSaveData();
            } else {
                emptyValue();
                if (emptyValue == true) {
                    setValueToRequest();
                    getBus().send(new RxBusObject(RxBusObject.RxBusKey.SUBMIT_KYC_DATA, request));
                } else {
                    if (emptyCountry == false) {
                        ((TextView)sLegalCountry.getSelectedView()).setError("Mohon isi kolom ini");
                    } else if (emptyProvince == false) {
                        ((TextView)sLegalProvince.getSelectedView()).setError("Mohon isi kolom ini");
                    } else if (emptyCity == false) {
                        ((TextView)sLegalCity.getSelectedView()).setError("Mohon isi kolom ini");
                    }
                }
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.sLegalCountry:
                country = (Country) sLegalCountry.getSelectedItem();
                state = null;
                city = null;
                //showProgressDialog(loading);

                //Membandingkan value request dan perubahan item yang dipilih
                String countryId = request.getHomeCountry();
                if (!String.valueOf(country.getId()).equalsIgnoreCase(countryId)) {
                    status = true;
                }
                Log.d(TAG, "onItemSelected: " + request.getHomeCountry() + " value " + country.getId());

                locationHelper.loadStatesCitiesBySelectedCountry(String.valueOf(country.getId()));
                break;
            case R.id.sLegalProvince:
                state = (State) sLegalProvince.getSelectedItem();
                city = null;
                //showProgressDialog(loading);

                //Membandingkan value request dan perubahan item yang dipilih
                String provinceId = request.getHomeProvince();
                if (!String.valueOf(state.getStateCode()).equalsIgnoreCase(provinceId)) {
                    status = true;
                }
                Log.d(TAG, "onItemSelected: " + request.getHomeProvince() + " value " + state.getStateCode());

                locationHelper.loadCitiesBySelectedState(state.getStateCode());
                break;
            case R.id.sLegalCity:
                city = (City) sLegalCity.getSelectedItem();

                //Membandingkan value request dan perubahan item yang dipilih
                String cityId = request.getHomeCity();
                if (!String.valueOf(city.getId()).equalsIgnoreCase(cityId)) {
                    status = true;
                }
                Log.d(TAG, "onItemSelected: " + request.getHomeCity() + " value " + city.getId());

                break;
        }
    }

    @Override
    public void onRetrieveLocationDataComplete(LocationHelper.LoadType loadType) {
        dismissProgressDialog();
        switch (loadType) {
            case COUNTRIES_STATES_CITIES:
                setupSpinnerCountry(locationHelper.getCountries(), request.getHomeCountry());
                break;
            case STATES_CITIES:
                setupSpinnerState(locationHelper.getStates(), request.getHomeProvince());
                break;
            case CITIES:
                setupSpinnerCity(locationHelper.getCities(), request.getHomeCity());
                break;
        }

    }

    public void setupSpinnerCountry(List<Country> countries, String selectionCountryId) {
        if (countries == null) countries = new ArrayList<>();

        Country defaultCountry = new Country();
        defaultCountry.setCountryName("");
        defaultCountry.setId(0);
        defaultCountry.setNumericCode("");
        defaultCountry.setAtCountryCode("");
        defaultCountry.setAlpha3Code("");
        countries.add(0, defaultCountry);

        ArrayAdapter<Country> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, countries);
        sLegalCountry.setAdapter(spinnerArrayAdapter);
        sLegalCountry.setOnItemSelectedListener(this);
        sLegalCountry.setSelection(locationHelper.getDefaultIndexCountry(), false); // set to default (Indonesia)
        if (selectionCountryId != null) { // set selection to saved data from realm / ws
            int i = 0;
            for (Country country : countries) {
                if (country.getId() == Integer.valueOf(selectionCountryId)) {
                    sLegalCountry.setSelection(i, false);
                    break;
                }
                i++;
            }
        }
    }

    public void setupSpinnerState(List<State> states, String selectionStateCode) {
        if (states == null) states = new ArrayList<>();

        State defaultState = new State();
        defaultState.setStateCode("");
        defaultState.setStateName("");
        states.add(0, defaultState);

        ArrayAdapter<State> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, states);
        sLegalProvince.setAdapter(spinnerArrayAdapter);
        sLegalProvince.setOnItemSelectedListener(this);
        sLegalProvince.setSelection(0, false);
        if (selectionStateCode != null) { // set selection to saved data from realm / ws
            int i = 0;
            for (State state : states) {
                if (state.getStateCode().equalsIgnoreCase(selectionStateCode)) {
                    sLegalProvince.setSelection(i, false);
                    break;
                }
                i++;
            }
        }
    }

    public void setupSpinnerCity(List<City> cities, String selectionCityId) {
        if (cities == null) cities = new ArrayList<>();

        City defaultCity = new City();
        defaultCity.setCityName("");
        defaultCity.setCityCode("");
        cities.add(0, defaultCity);

        ArrayAdapter<City> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, cities);
        sLegalCity.setAdapter(spinnerArrayAdapter);
        sLegalCity.setOnItemSelectedListener(this);
        sLegalCity.setSelection(0, false);
        if (selectionCityId != null) { // set selection to saved data from realm / ws
            int i = 0;
            for (City city : cities) {
                if (city.getId() == Integer.valueOf(selectionCityId)) {
                    sLegalCity.setSelection(i, false);
                    break;
                }
                i++;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @OnClick(R.id.checkboxPhone)
    public void CheckUsingPhoneNumber() {
        if (cbPhone.isChecked()) {
            /*
                Bisa pakai yang dari preference (menu utama profile) atau dari hasil API
               if (PrefHelper.getString(PrefKey.NO_HANDPHONE) != null) {
            */
            String mobileNum = request.getMobileNumber() != null ? request.getMobileNumber() : PrefHelper.getString(PrefKey.NO_HANDPHONE);
            int counter = 0;

            for (int i = 0; i < mobileNum.length(); i++) {
                if (mobileNum.charAt(i) == '-') {
                    counter++;
                }
            }
            String[] splitedPhoneNumber = mobileNum.split("-");
            etCountryCode.setText(splitedPhoneNumber[0]);

                /*Handle jika ada strip yang cuma satu..
                  Apabila data sudah benar no hp di strip dua , maka bisa langsung pakai yang counter == 2
                 */

            if (counter == 1) {
                etCityCode.setText(splitedPhoneNumber[1].substring(0, 2));
                etLegalPhoneNumber.setText(splitedPhoneNumber[1].substring(2, splitedPhoneNumber[1].length()));
            } else if (counter == 2) {
                etCityCode.setText(splitedPhoneNumber[1]);
                etLegalPhoneNumber.setText(splitedPhoneNumber[2]);
            }
        }
    }

    void emptyValue() {
        if (sLegalCountry.getSelectedItem().toString().equals("")) {
            emptyCountry = false;
            emptyProvince = true;
            emptyCity = true;
            emptyValue = false;
        } else if (sLegalProvince.getSelectedItem().toString().equals("")) {
            emptyCountry = true;
            emptyProvince = false;
            emptyCity = true;
            emptyValue = false;
        } else if (sLegalCity.getSelectedItem().toString().equals("")) {
            emptyCountry = true;
            emptyProvince = true;
            emptyCity = false;
            emptyValue = false;
        } else {
            emptyCountry = true;
            emptyProvince = true;
            emptyCity = true;
            emptyValue = true;
        }
    }

    void init() {
        etLegalAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    status = true;
            }
        });

        etLegalPostalCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    status = true;
            }
        });

        etCountryCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    status = true;
            }
        });

        etCityCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    status = true;
            }
        });

        etLegalPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

    void setValueToRequest(){
        if (country == null) country = locationHelper.getSelectedCountry();
        if (state == null) state = locationHelper.getSelectedState();
        if (city == null) city = locationHelper.getSelectedCity();
        request.setHomeCountry(country != null ? String.valueOf(country.getId()) : null);
        request.setHomeProvince(state != null ? state.getStateCode() : null);
        request.setHomeCity(city != null ? String.valueOf(city.getId()) : null);
        request.setHomeAddress(getAndTrimValueFromEditText(etLegalAddress));
        request.setHomePostalCode(getAndTrimValueFromEditText(etLegalPostalCode));
        request.setHomePhoneNumber(constructPhoneNumber(etCountryCode, etCityCode, etLegalPhoneNumber));
        request.setToken(PrefHelper.getString(PrefKey.TOKEN));
    }
}

