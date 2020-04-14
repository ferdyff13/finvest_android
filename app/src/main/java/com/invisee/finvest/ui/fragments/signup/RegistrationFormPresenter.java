package com.invisee.finvest.ui.fragments.signup;

import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.Toast;

import com.invisee.finvest.data.api.beans.SecurityQuestion;
import com.invisee.finvest.data.api.requests.RegistrationRequest;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.realm.RealmHelper;
import com.invisee.finvest.data.realm.model.SecurityQuestionModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class RegistrationFormPresenter {

    private RegistrationFormFragment fragment;

    public RegistrationFormPresenter(RegistrationFormFragment fragment) {
        this.fragment = fragment;
    }

    void queryAndSetupSecurityQuestions() {
        RealmHelper.readSecurityQuestions(fragment.getRealm())
                .map(new Func1<RealmResults<SecurityQuestionModel>, List<SecurityQuestion>>() {
                    @Override
                    public List<SecurityQuestion> call(RealmResults<SecurityQuestionModel> securityQuestionModels) {
                        List<SecurityQuestion> list = new ArrayList<>();
                        for (SecurityQuestionModel sqm : securityQuestionModels) {
                            SecurityQuestion sq = new SecurityQuestion();
                            sq.setId(sqm.getId());
                            sq.setQuestionName(sqm.getQuestionName());
                            sq.setQuestionText(sqm.getQuestionText());
                            list.add(sq);
                        }
                        return list;
                    }
                })
                .subscribe(new Action1<List<SecurityQuestion>>() {
                    @Override
                    public void call(List<SecurityQuestion> securityQuestionList) {
                        fragment.setupSecurityQuestionSpinner(securityQuestionList);
                    }
                });
    }

    public RegistrationRequest constructRegistrationRequest() {
        RegistrationRequest request = new RegistrationRequest();
        String email = getAndTrimValueFromEditText(fragment.etEmail);
        request.setAnswerNote(getAndTrimValueFromEditText(fragment.etAnswer));
        request.setEmail(email.toLowerCase());
        request.setFirstName(getAndTrimValueFromEditText(fragment.etFirstName));
   /*     request.setMiddleName(getAndTrimValueFromEditText(fragment.etMiddleName));*/
        request.setLastName(getAndTrimValueFromEditText(fragment.etLastName));
/*        request.setHomePhoneNumber(constructHomePhoneNumber(fragment.etCountryCode, fragment.etCityCode, fragment.etHomePhoneNumber));*/
        request.setMobileNumber(constructMobilePhoneNumber(fragment.etCountryCodeMobilePhone, fragment.etMobilePhoneNumber));
        request.setPassword(getAndTrimValueFromEditText(fragment.etPassword));
        request.setConfirmPassword(getAndTrimValueFromEditText(fragment.etConfirmPassword));
        request.setQuestion(getSelectedQuestionId());
        request.setAgent("FINVEST");
        return request;
    }

    void register(final RegistrationRequest registrationRequest) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().regsiterCustomer(
                registrationRequest.getAnswerNote(),
                registrationRequest.getEmail(),
                registrationRequest.getFirstName(),
                registrationRequest.getHomePhoneNumber(),
                registrationRequest.getLastName(),
                registrationRequest.getMiddleName(),
                registrationRequest.getMobileNumber(),
                registrationRequest.getPassword(),
                registrationRequest.getConfirmPassword(),
                registrationRequest.getQuestion(),
                registrationRequest.getAgent())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Timber.e(e.getLocalizedMessage());
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(GenericResponse genericResponse) {
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), genericResponse.getInfo(), Toast.LENGTH_SHORT).show();
                        if (genericResponse.getCode() == fragment.successCode) {
                            fragment.gotoActivationCodeActivity(registrationRequest.getEmail(), registrationRequest.getPassword());
                        }
                    }
                });
    }

    private String constructHomePhoneNumber(EditText countryCode, EditText cityCode, EditText phoneNumber) {
        StringBuilder constructedHomePhoneNumber = new StringBuilder("");
        constructedHomePhoneNumber
                .append(getAndTrimValueFromEditText(countryCode))
                .append("-")
                .append(getAndTrimValueFromEditText(cityCode))
                .append("-")
                .append(getAndTrimValueFromEditText(phoneNumber));
        return constructedHomePhoneNumber.toString();

    }

    private String constructMobilePhoneNumber(EditText countryCode, EditText phoneNumber) {
        StringBuilder constructedHomePhoneNumber = new StringBuilder("");
        constructedHomePhoneNumber
                .append(getAndTrimValueFromEditText(countryCode))
                .append("-")
                .append(getAndTrimValueFromEditText(phoneNumber));
        return constructedHomePhoneNumber.toString();
    }

    private String getAndTrimValueFromEditText(EditText e) {
        return e.getText().toString().trim();
    }

    @NonNull
    private String getSelectedQuestionId() {
        if (fragment.sQuestion.getSelectedItem() != null) {
            return String.valueOf(((SecurityQuestion) fragment.sQuestion.getSelectedItem()).getId());
        } else {
            return "";
        }
    }

}
