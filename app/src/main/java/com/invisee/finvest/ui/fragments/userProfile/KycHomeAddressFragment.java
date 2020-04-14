package com.invisee.finvest.ui.fragments.userProfile;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
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
public class KycHomeAddressFragment extends KycBaseFragment implements AdapterView.OnItemSelectedListener, LocationHelper.Callback {

    @Bind(R.id.sHomeCountry)
    Spinner sHomeCountry;
    @Bind(R.id.sHomeProvince)
    Spinner sHomeProvince;
    @Bind(R.id.sHomeCity)
    Spinner sHomeCity;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etHomeAddress)
    EditText etHomeAddress;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etHomePostalCode)
    EditText etHomePostalCode;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etCountryCode)
    EditText etCountryCode;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etCityCode)
    EditText etCityCode;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etHomePhoneNumber)
    EditText etHomePhoneNumber;
    @Bind(R.id.checkboxPhone)
    CheckBox cbPhone;
    @Bind(R.id.checkboxLegal)
    CheckBox cbLegal;

    private boolean emptyCountry = true;
    private boolean emptyProvince = true;
    private boolean emptyCity = true;
    private boolean emptyValue = false;

    private LocationHelper locationHelper;
    private Country country;
    private State state;
    private City city;
    private boolean status;
    private static final String TAG = KycHomeAddressFragment.class.getSimpleName();

    public KycHomeAddressFragment(){

    }

    public static KycHomeAddressFragment initiateFragment(KycDataRequest kycDataRequest) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KYC_DATA_REQUEST, kycDataRequest);
        KycHomeAddressFragment fragment = new KycHomeAddressFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.f_kyc_home_address;
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
        etHomeAddress.setText(request.getLegalAddress());
        etHomePostalCode.setText(request.getLegalPostalCode());
        if (request.getLegalPhoneNumber() != null) {
            try{
                String[] splitedPhoneNumber = request.getLegalPhoneNumber().split("-");
                etCountryCode.setText(splitedPhoneNumber[0]);
                etCityCode.setText(splitedPhoneNumber[1]);
                etHomePhoneNumber.setText(splitedPhoneNumber[2]);
            }catch(ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            locationHelper = new LocationHelper(getApi(), this);
            locationHelper.loadCountriesStatesCities();
        }
    }


    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();
        if (!status) {
            if (cbLegal.isChecked()) {
                request.setHomeCountry(request.getLegalCountry() != null ? request.getLegalCountry() : null);
                request.setHomeProvince(request.getLegalProvince() != null ? request.getLegalProvince() : null);
                request.setHomeCity(request.getLegalCity() != null ? request.getLegalCity() : null);
                request.setHomeAddress(request.getLegalAddress() != null ? request.getLegalAddress() : null);
                request.setHomePostalCode(request.getLegalPostalCode() != null ? request.getLegalPostalCode() : null);
                request.setHomePhoneNumber(request.getLegalPhoneNumber() != null ? request.getLegalPhoneNumber() : null);

                PrefHelper.setBoolean(PrefKey.HOME_LEGAL, true);
                getBus().send(new RxBusObject(RxBusObject.RxBusKey.SUBMIT_KYC_DATA, request));
            } else {
                if(PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("ACT")) {
                    emptyValue();
                    if (emptyValue == true){
                        setValueToRequest();
                        getBus().send(new RxBusObject(RxBusObject.RxBusKey.SUBMIT_KYC_DATA, request));
                    } else {
                        if (emptyCountry == false) {
                            ((TextView)sHomeCountry.getSelectedView()).setError("Mohon isi kolom ini");
                        } else if (emptyProvince == false) {
                            ((TextView)sHomeProvince.getSelectedView()).setError("Mohon isi kolom ini");
                        } else if (emptyCity == false) {
                            ((TextView)sHomeCity.getSelectedView()).setError("Mohon isi kolom ini");
                        }
                    }
                } else {
                    getBus().send(new RxBusObject(RxBusObject.RxBusKey.NEXT_FORM, null));
                }
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
                        ((TextView)sHomeCountry.getSelectedView()).setError("Mohon isi kolom ini");
                    } else if (emptyProvince == false) {
                        ((TextView)sHomeProvince.getSelectedView()).setError("Mohon isi kolom ini");
                    } else if (emptyCity == false) {
                        ((TextView)sHomeCity.getSelectedView()).setError("Mohon isi kolom ini");
                    }
                }
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.sHomeCountry:
                country = (Country) sHomeCountry.getSelectedItem();
                state = null;
                city = null;

                //Membandingkan value request dan perubahan item yang dipilih
                String countryId = request.getLegalCountry();
                if (!String.valueOf(country.getId()).equalsIgnoreCase(countryId)) {
                    status = true;
                }
                Log.d(TAG, "onItemSelected: " + request.getLegalCountry() + " value " + country.getId());

                locationHelper.loadStatesCitiesBySelectedCountry(String.valueOf(country.getId()));
                break;
            case R.id.sHomeProvince:
                state = (State) sHomeProvince.getSelectedItem();
                city = null;

                //Membandingkan value request dan perubahan item yang dipilih
                String provinceId = request.getLegalProvince();
                if (!String.valueOf(state.getStateCode()).equalsIgnoreCase(provinceId)) {
                    status = true;
                }
                Log.d(TAG, "onItemSelected: " + request.getLegalProvince() + " value " + state.getStateCode());

                locationHelper.loadCitiesBySelectedState(state.getStateCode());
                break;
            case R.id.sHomeCity:
                city = (City) sHomeCity.getSelectedItem();

                //Membandingkan value request dan perubahan item yang dipilih
                String cityId = request.getLegalCity();
                if (!String.valueOf(city.getId()).equalsIgnoreCase(cityId)) {
                    status = true;
                }
                Log.d(TAG, "onItemSelected: " + request.getHomeCity() + " value " + city.getId());
                break;
        }
    }

    @Override
    public void onRetrieveLocationDataComplete(LocationHelper.LoadType loadType) {
        switch (loadType) {
            case COUNTRIES_STATES_CITIES:
                setupSpinnerCountry(locationHelper.getCountries(), request.getLegalCountry());
                break;
            case STATES_CITIES:
                setupSpinnerState(locationHelper.getStates(), request.getLegalProvince());
                break;
            case CITIES:
                setupSpinnerCity(locationHelper.getCities(), request.getLegalCity());
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
        sHomeCountry.setAdapter(spinnerArrayAdapter);
        sHomeCountry.setOnItemSelectedListener(this);
        sHomeCountry.setSelection(locationHelper.getDefaultIndexCountry(), false); // set to default (Indonesia)
        if (selectionCountryId != null) { // set selection to saved data from realm / ws
            int i = 0;
            for (Country country : countries) {
                if (country.getId() == Integer.valueOf(selectionCountryId)) {
                    sHomeCountry.setSelection(i, false);
                    this.country = country;
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
        sHomeProvince.setAdapter(spinnerArrayAdapter);
        sHomeProvince.setOnItemSelectedListener(this);
        sHomeProvince.setSelection(0, false);
        if (selectionStateCode != null) { // set selection to saved data from realm / ws
            int i = 0;
            for (State state : states) {
                if (state.getStateCode().equalsIgnoreCase(selectionStateCode)) {
                    sHomeProvince.setSelection(i, false);
                    break;
                }
                i++;
            }
        }
    }


    public void setupSpinnerCity(List<City> cities, String selectionCityId){
        if (cities == null) cities = new ArrayList<>();

        City defaultCity = new City();
        defaultCity.setCityName("");
        defaultCity.setCityCode("");
        cities.add(0, defaultCity);

        ArrayAdapter<City> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, cities);
        sHomeCity.setAdapter(spinnerArrayAdapter);
        sHomeCity.setOnItemSelectedListener(this);
        sHomeCity.setSelection(0, false);
        if (selectionCityId != null){ // set selection to saved data from realm / ws
            int i = 0;
            for(City city : cities){
                if (city.getId() == Integer.valueOf(selectionCityId)) {
                    sHomeCity.setSelection(i, false);
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
                etHomePhoneNumber.setText(splitedPhoneNumber[1].substring(2, splitedPhoneNumber[1].length()));
            } else if (counter == 2) {
                etCityCode.setText(splitedPhoneNumber[1]);
                etHomePhoneNumber.setText(splitedPhoneNumber[2]);
            }
        }
    }


    void emptyValue(){
        if (sHomeCountry.getSelectedItem().toString().equals("")) {
            emptyCountry = false;
            emptyProvince = true;
            emptyCity = true;
            emptyValue = false;
        } else if (sHomeProvince.getSelectedItem().toString().equals("")) {
            emptyCountry = true;
            emptyProvince = false;
            emptyCity = true;
            emptyValue = false;
        } else if (sHomeCity.getSelectedItem().toString().equals("")) {
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
        etHomeAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    status = true;
            }
        });

        etHomePostalCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

        etHomePhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        //status true, jika ada perubahan data
        if (country == null) country = locationHelper.getSelectedCountry();
        if (state == null) state = locationHelper.getSelectedState();
        if (city == null) city = locationHelper.getSelectedCity();
        request.setLegalCountry(country != null ? String.valueOf(country.getId()) : null);
        request.setLegalProvince(state != null ? state.getStateCode() : null);
        request.setLegalCity(city != null ? String.valueOf(city.getId()) : null);
        request.setLegalAddress(getAndTrimValueFromEditText(etHomeAddress));
        request.setLegalPostalCode(getAndTrimValueFromEditText(etHomePostalCode));
        request.setLegalPhoneNumber(constructPhoneNumber(etCountryCode, etCityCode, etHomePhoneNumber));
        if (cbLegal.isChecked()) {
            request.setHomeCountry(request.getLegalCountry() != null ? request.getLegalCountry() : null);
            request.setHomeProvince(request.getLegalProvince() != null ? request.getLegalProvince() : null);
            request.setHomeCity(request.getLegalCity() != null ? request.getLegalCity() : null);
            request.setHomeAddress(request.getLegalAddress() != null ? request.getLegalAddress() : null);
            request.setHomePostalCode(request.getLegalPostalCode() != null ? request.getLegalPostalCode() : null);
            request.setHomePhoneNumber(request.getLegalPhoneNumber() != null ? request.getLegalPhoneNumber() : null);

            PrefHelper.setBoolean(PrefKey.HOME_LEGAL, true);
        }
        request.setToken(PrefHelper.getString(PrefKey.TOKEN));
    }
}
