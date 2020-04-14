package com.invisee.finvest.ui.fragments.signIn;

import android.widget.Toast;

import com.invisee.finvest.data.api.beans.SecurityQuestion;
import com.invisee.finvest.data.api.requests.ForgotPasswordRequest;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.realm.RealmHelper;
import com.invisee.finvest.data.realm.model.SecurityQuestionModel;
import com.invisee.finvest.ui.activities.BaseActivity;

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
public class ForgotPasswordPresenter {

    private ForgotPasswordFragment fragment;

    public ForgotPasswordPresenter(ForgotPasswordFragment fragment) {
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

    ForgotPasswordRequest constructForgotPasswordRequest() {
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail(fragment.etEmail.getText().toString());
        request.setQuestion(String.valueOf(((SecurityQuestion) fragment.sQuestion.getSelectedItem()).getId()));
        request.setAnswer(fragment.etAnswer.getText().toString());
        return request;
    }

    void requestForgotPassword(final ForgotPasswordRequest forgotPasswordRequest) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().forgotPassword(
                forgotPasswordRequest.getEmail(),
                forgotPasswordRequest.getAnswer(),
                forgotPasswordRequest.getQuestion())
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
                            ResetPasswordFragment.showFragment((BaseActivity) fragment.getActivity(), forgotPasswordRequest.getEmail(), forgotPasswordRequest.getAnswer(), forgotPasswordRequest.getQuestion());
                    }
                });

    }

}
