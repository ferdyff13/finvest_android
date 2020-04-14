package com.invisee.finvest.ui.fragments.signIn;

import android.widget.Toast;

import com.invisee.finvest.data.api.requests.ResendCodePasswordRequest;
import com.invisee.finvest.data.api.requests.ResetPasswordRequest;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.SignInActivity;
import com.invisee.finvest.ui.activities.SplashScreenActivity;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by pandu.abbiyuarsyah on 13/07/2017.
 */

public class RePasswordPresenter {

    private RePasswordFragment fragment;

    public RePasswordPresenter(RePasswordFragment fragment) {
        this.fragment = fragment;
    }

    ResetPasswordRequest constructResetPasswordRequest(String email) {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setEmail(email);
        request.setResetCode(fragment.etResetCode.getText().toString());
        request.setConfirmPassword(fragment.etConfirmPassword.getText().toString());
        return request;
    }

    void requestResetPassword(final ResetPasswordRequest request) {
        fragment.showProgressDialogOnClick(fragment.loading);
        fragment.getApi().resetPassword(
                request.getEmail(),
                request.getConfirmPassword(),
                request.getResetCode())
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
                        Timber.i("Code %s", genericResponse.getCode());
                        Timber.i("Info %s", genericResponse.getInfo());
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), genericResponse.getInfo(), Toast.LENGTH_SHORT).show();
                        if (genericResponse.getCode() == 1)
                            SignInActivity.startActivity((BaseActivity) fragment.getActivity());
                    }
                });

    }

    ResendCodePasswordRequest constructForgotResendCode(String email, String answer, String question) {
        ResendCodePasswordRequest request = new ResendCodePasswordRequest();
        request.setEmail(email);
        request.setAnswer(answer);
        request.setQuestion(question);
        return request;
    }

    void requestResendCode(final ResendCodePasswordRequest resendCodePasswordRequest) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().resendCode(
                resendCodePasswordRequest.getEmail(),
                resendCodePasswordRequest.getAnswer(),
                resendCodePasswordRequest.getQuestion())
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

    void urgentForgotPass(String email) {
        fragment.showProgressDialogOnClick(fragment.loading);
        fragment.getApi().urgentForgotPassword(email)
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
                            Toast.makeText(fragment.getContext(), response.getInfo(), Toast.LENGTH_SHORT).show();
                        } else {
                            fragment.dismissProgressDialog();
                            Toast.makeText(fragment.getContext(), response.getInfo(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }



}
