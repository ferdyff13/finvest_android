package com.invisee.finvest.ui.fragments.signIn;

import android.widget.Toast;

import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.ui.activities.BaseActivity;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by pandu.abbiyuarsyah on 13/07/2017.
 */

public class ForgotPasswordTempPresenter {

    ForgotPasswordTempFragment fragment;

    public ForgotPasswordTempPresenter(ForgotPasswordTempFragment fragment) {
        this.fragment = fragment;
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
                            RePasswordFragment.showFragment((BaseActivity) fragment.getActivity(), fragment.etEmail.getText().toString());

                        } else {
                            fragment.dismissProgressDialog();
                            Toast.makeText(fragment.getContext(), response.getInfo(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }



}