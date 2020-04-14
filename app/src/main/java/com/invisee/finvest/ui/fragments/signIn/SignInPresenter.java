package com.invisee.finvest.ui.fragments.signIn;

import android.graphics.Color;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.requests.LoginRequest;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.api.responses.LoginResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.util.Constant;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class SignInPresenter {

    private SignInFragment fragment;

    public SignInPresenter(SignInFragment fragment) {
        this.fragment = fragment;
    }

    // TODO: 16/05/2018 test
    /*private Subscription loginReqSubscription;*/

    LoginRequest constructLoginRequest() {
        LoginRequest request = new LoginRequest();
        String username = fragment.etUsername.getText().toString();
        request.setPassword(fragment.etPassword.getText().toString());
        request.setEmail(username.toLowerCase());
        return request;
    }

    void login(final LoginRequest loginRequest) {
        fragment.showProgressDialog(fragment.loading);
        // TODO: 16/05/2018 test
       /* loginReqSubscription = fragment.getApi().login(*/
        fragment.getApi().login(
                loginRequest.getEmail(),
                loginRequest.getPassword())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<LoginResponse>(){
                    @Override
                    public void onCompleted(){

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
                        if (loginResponse.getCode() == fragment.successCode) {
                            if (loginResponse.getData().getUserStatus().equalsIgnoreCase(Constant.USER_STATUS_REGISTER)) {
                                Toast.makeText(fragment.getContext(), loginResponse.getInfo(), Toast.LENGTH_SHORT).show();
                                fragment.gotoActivationCodeActivity(loginRequest.getEmail(), loginRequest.getPassword());
                            } else if (loginResponse.getData().getUserStatus().equalsIgnoreCase(Constant.USER_STATUS_ACTIVE)) {
                                Toast.makeText(fragment.getContext(), loginResponse.getInfo(), Toast.LENGTH_SHORT).show();
                                saveCredential(loginResponse);
                                fragment.showUserProfileSuggestionDialog();
                            } else if (loginResponse.getData().getUserStatus().equalsIgnoreCase(Constant.USER_STATUS_PENDING)) {
                                Toast.makeText(fragment.getContext(), loginResponse.getInfo(), Toast.LENGTH_SHORT).show();
                                saveCredential(loginResponse);
                                fragment.gotoMainActivity();
                            } else {
                                Toast.makeText(fragment.getContext(), loginResponse.getInfo(), Toast.LENGTH_SHORT).show();
                                saveCredential(loginResponse);
                                fragment.gotoMainActivity();
                            }
                        } else if (loginResponse.getCode() == 99) {
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.infortmation).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content("Pelanggan kami yang terhormat, karena saat ini kami sedang melakukan pengkinian keamanan sistem, maka kami menyarankan anda untuk melakukan pengkinian password pada akun anda. Mohon maaf atas ketidaknyamanannya")
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.ok)
                                    .positiveColor(Color.WHITE)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            urgentForgotPass();
                                        }
                                    })
                                    .cancelable(false)
                                    .show();

                        } else if (loginResponse.getCode() == 12) {
                            Toast.makeText(fragment.getContext(), loginResponse.getInfo(), Toast.LENGTH_SHORT).show();
                        } else if (loginResponse.getCode() == 50 ) {
                            Toast.makeText(fragment.getContext(), loginResponse.getInfo(), Toast.LENGTH_SHORT).show();
                        } else  {
                            Toast.makeText(fragment.getContext(), loginResponse.getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void urgentForgotPass() {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().urgentForgotPassword(fragment.etUsername.getText().toString())
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
                    public void onNext(GenericResponse response) {
                        if (response.getCode() == 1) {
                            fragment.dismissProgressDialog();
                            RePasswordFragment.showFragment((BaseActivity) fragment.getActivity(), fragment.etUsername.getText().toString());
                        } else {
                            fragment.dismissProgressDialog();
                        }

                    }
                });
    }

    private void saveCredential(LoginResponse loginResponse) {
        PrefHelper.setBoolean(PrefKey.IS_LOGIN, true);
        PrefHelper.setInt(PrefKey.ID, loginResponse.getData().getId());
        PrefHelper.setInt(PrefKey.KYC_ID, loginResponse.getData().getKyc());
        PrefHelper.setString(PrefKey.EMAIL, loginResponse.getData().getEmail());
        PrefHelper.setString(PrefKey.CUSTOMER_STATUS, loginResponse.getData().getUserStatus());
        PrefHelper.setString(PrefKey.TOKEN, loginResponse.getData().getToken());
        PrefHelper.setString(PrefKey.FIRST_NAME, loginResponse.getData().getName());
        PrefHelper.setString(PrefKey.IMAGE, loginResponse.getData().getImage());
        PrefHelper.setString(PrefKey.NAME, loginResponse.getData().getName());
        PrefHelper.setString(PrefKey.CUSTOMER_STATUS, loginResponse.getData().getUserStatus());
    }


    // TODO: 16/05/2018 test
   /* public void cleanResource(){
        if(loginReqSubscription != null){
            loginReqSubscription.unsubscribe();
        }
    }*/
}
