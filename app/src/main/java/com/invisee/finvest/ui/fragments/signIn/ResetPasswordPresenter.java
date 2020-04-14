package com.invisee.finvest.ui.fragments.signIn;

import android.widget.Toast;

import com.invisee.finvest.data.api.requests.ResendCodePasswordRequest;
import com.invisee.finvest.data.api.requests.ResetPasswordRequest;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.ui.activities.BaseActivity;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class ResetPasswordPresenter {

    private ResetPasswordFragment fragment;

    public ResetPasswordPresenter(ResetPasswordFragment fragment) {
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
        fragment.showProgressDialog(fragment.loading);
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
                            SignInFragment.showFragment((BaseActivity) fragment.getActivity());
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

}
