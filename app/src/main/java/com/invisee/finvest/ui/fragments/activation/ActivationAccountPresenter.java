package com.invisee.finvest.ui.fragments.activation;

import android.util.Log;
import android.widget.Toast;

import com.invisee.finvest.data.api.requests.ActivateUserRequest;
import com.invisee.finvest.data.api.requests.LoginRequest;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.api.responses.LoginResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.util.Constant;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/3/16.
 */
public class ActivationAccountPresenter {

    private ActivationAccountFragment fragment;

    public ActivationAccountPresenter(ActivationAccountFragment fragment) {
        this.fragment = fragment;
    }

    ActivateUserRequest cunstructActivateUserRequest() {
        ActivateUserRequest request = new ActivateUserRequest();
        request.setEmail(fragment.email);
        request.setResetCode(fragment.etActivationCode.getText().toString());
        request.setToken("userNotLogin");
        return request;
    }

    void activate(final ActivateUserRequest request) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().activateUserWithActivationCode(
                request.getEmail(),
                request.getResetCode(),
                request.getToken())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        fragment.dismissProgressDialog();

                        if (loginResponse.getCode() == fragment.activicationSuccessCode) {
                            Toast.makeText(fragment.getContext(), loginResponse.getInfo(), Toast.LENGTH_SHORT).show();
                            if (loginResponse.getUser().getUserStatus().equalsIgnoreCase(Constant.USER_STATUS_ACTIVE)) {
                                saveCredential(loginResponse);
                                fragment.showUserProfileSuggestionDialog();
//                                fragment.gotoMainActivity();
                            } else {
                                saveCredential(loginResponse);
                                fragment.gotoMainActivity();
                            }
                        } else {
                            if (loginResponse.getInfo().contains("Ambiguous method overloading for method java.lang.String")) {
                                login(constructLoginRequest());
                            } else {
                                Toast.makeText(fragment.getContext(), loginResponse.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void saveCredential(LoginResponse loginResponse) {
        PrefHelper.setInt(PrefKey.ID, loginResponse.getUser().getId());
        PrefHelper.setString(PrefKey.EMAIL, loginResponse.getUser().getEmail());
        PrefHelper.setString(PrefKey.CUSTOMER_STATUS, loginResponse.getUser().getUserStatus());
        PrefHelper.setString(PrefKey.TOKEN, loginResponse.getUser().getToken());
        /*PrefHelper.setInt(PrefKey.KYC_ID, loginResponse.getKyc().getId());*/
       /* PrefHelper.setInt(PrefKey.KYC_ID, loginResponse.getData().getKyc());*/
        PrefHelper.setInt(PrefKey.KYC_ID, loginResponse.getKyc().getId());
        PrefHelper.setString(PrefKey.KYC_ID, loginResponse.getKyc().getFirstName());
    }

    void resend(String email) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().resendActivationCode(email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(GenericResponse genericResponse) {
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), genericResponse.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    LoginRequest constructLoginRequest() {
        LoginRequest request = new LoginRequest();
        request.setPassword(fragment.password);
        request.setEmail(fragment.email);
        return request;
    }

    void login(final LoginRequest loginRequest) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().login(
                loginRequest.getEmail(),
                loginRequest.getPassword())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), loginResponse.getInfo(), Toast.LENGTH_SHORT).show();
                        if (loginResponse.getCode() == fragment.successCode) {
                            if (loginResponse.getUser().getUserStatus().equalsIgnoreCase(Constant.USER_STATUS_REGISTER)) {
                                //fragment.gotoActivationCodeActivity(loginRequest.getEmail());
                            } else if (loginResponse.getUser().getUserStatus().equalsIgnoreCase(Constant.USER_STATUS_ACTIVE)) {
                                saveCredential(loginResponse);
                                fragment.showUserProfileSuggestionDialog();
                            } else {
                                saveCredential(loginResponse);
                                fragment.gotoMainActivity();
                            }
                        }
                    }
                });
    }

}
