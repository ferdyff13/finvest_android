package com.invisee.finvest.util;

import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.beans.City;
import com.invisee.finvest.data.api.beans.Country;
import com.invisee.finvest.data.api.beans.State;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/19/16.
 */
public class LocationHelper {

    public enum LoadType {
        COUNTRIES_STATES_CITIES, STATES_CITIES, CITIES
    }

    public interface Callback {
        void onRetrieveLocationDataComplete(LoadType loadType);
    }

    private static final int COUNTRY_ID_INA = 52;

    private InviseeService.Api api;
    private Callback callback;
    private LoadType type;
    private List<Country> countries;
    private List<State> states;
    private List<City> cities;
    private Country selectedCountry;
    private State selectedState;
    private City selectedCity;
    private int defaultIndexCountry;

    public LocationHelper(InviseeService.Api api, Callback callback) {
        this.api = api;
        this.callback = callback;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public List<State> getStates() {
        return states;
    }

    public List<City> getCities() {
        return cities;
    }

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public State getSelectedState() {
        return selectedState;
    }

    public City getSelectedCity() {
        return selectedCity;
    }

    public int getDefaultIndexCountry() {
        return defaultIndexCountry;
    }

    public void loadCountriesStatesCities() {
        type = LoadType.COUNTRIES_STATES_CITIES;
        emptyCountries();
        final String token = PrefHelper.getString(PrefKey.TOKEN);
        api.getAllCountry(token)
                .flatMap(persistCountriesAndGetStates(token))
                .flatMap(persistStatesAndGetCities(token))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(persistCities());
    }

    public void loadStatesCitiesBySelectedCountry(String countryId) {
        type = LoadType.STATES_CITIES;
        emptyStates();
        final String token = PrefHelper.getString(PrefKey.TOKEN);
        api.getStateByCountry(countryId, token)
                .flatMap(persistStatesAndGetCities(token))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(persistCities());
    }

    public void loadCitiesBySelectedState(String state) {
        type = LoadType.CITIES;
        emptyCities();
        final String token = PrefHelper.getString(PrefKey.TOKEN);
        api.getCityByState(state, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(persistCities());
    }

    private Func1<List<Country>, Observable<List<State>>> persistCountriesAndGetStates(final String token) {
        return new Func1<List<Country>, Observable<List<State>>>() {
            @Override
            public Observable<List<State>> call(List<Country> countries) {
                if (countries != null && countries.size() > 0) {
                    LocationHelper.this.countries = countries;
                    int index = 0;
                    for (Country c : countries) {
                        if (c.getId() == COUNTRY_ID_INA) {
                            selectedCountry = c;
                            defaultIndexCountry = index;
                            break;
                        }
                        index++;
                    }
                    if (selectedCountry == null) selectedCountry = countries.get(0);
                    return api.getStateByCountry(String.valueOf(selectedCountry.getId()), token);
                } else {
                    return null;
                }
            }
        };
    }

    private Func1<List<State>, Observable<List<City>>> persistStatesAndGetCities(final String token) {
        return new Func1<List<State>, Observable<List<City>>>() {
            @Override
            public Observable<List<City>> call(List<State> states) {
                if(states!=null && states.size()>0){
                    LocationHelper.this.states = states;
                    selectedState = states.get(0);
                    return api.getCityByState(selectedState.getStateCode(), token);
                }else{
                    return null;
                }
            }
        };
    }

    private Observer<List<City>> persistCities() {
        return new Observer<List<City>>() {
            @Override
            public void onCompleted() {
                callback.onRetrieveLocationDataComplete(type);
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e.getMessage());
                callback.onRetrieveLocationDataComplete(type);
            }

            @Override
            public void onNext(List<City> cities) {
                if(cities!=null && cities.size()>0){
                    LocationHelper.this.cities = cities;
                    selectedCity = cities.get(0);
                }
            }
        };
    }

    private void emptyCountries() {
        LocationHelper.this.countries = null;
        selectedCountry = null;
        emptyStates();
    }

    private void emptyStates() {
        LocationHelper.this.states = null;
        selectedState = null;
        emptyCities();
    }

    private void emptyCities() {
        LocationHelper.this.cities = null;
        selectedCity = null;
    }


}
