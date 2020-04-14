package com.invisee.finvest.ui.activities;

import android.util.Log;
import android.widget.Toast;

import com.invisee.finvest.data.api.beans.Completeness;
import com.invisee.finvest.data.api.responses.CompletenessPercentageResponse;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/18/16.
 */
public class UserProfilePresenter {

    public enum CompleteType {
        KYC, FATCA, RISK_PROFILE
    }

    private UserProfileActivity activity;

    public UserProfilePresenter(UserProfileActivity activity) {
        this.activity = activity;
    }

    void getCompleteness(final CompleteType type) {
        activity.showProgressDialog(activity.loading);
        activity.getInviseeService().getApi().getCompletenessPercentage(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CompletenessPercentageResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        activity.dismissProgressDialog();
                        Toast.makeText(activity, "Error on retrieving completeness percentage data", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(CompletenessPercentageResponse completenessPercentageResponse) {
                        activity.dismissProgressDialog();
                        Completeness c = completenessPercentageResponse.getData();
                        switch (type) {
                            case FATCA:
                                if (c.getKyc() == 100) {
                                    //activity.onFatqaProvisionFulfilled();
                                } else {
                                    activity.showFailureDialog(activity.fatcaNoOpen);
                                }
                                break;
                            case RISK_PROFILE:
                                if (c.getKyc() == 100 && c.getFatca() == 100) {
                                    //activity.onRiskProfileProvisionFulfilled();
                                } else {
                                    activity.showFailureDialog(activity.rpNoOpen);
                                }
                                break;
                        }
                    }
                });
    }

//    void submitUserProfile() {
//        activity.showProgressDialog(activity.loading);
//        activity.getInviseeService().getApi().postPendingCustomer(PrefHelper.getString(PrefKey.TOKEN))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<GenericResponse>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Timber.e(e.getLocalizedMessage());
//                        activity.dismissProgressDialog();
//                        Toast.makeText(activity, activity.connectionError, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNext(GenericResponse genericResponse) {
//                        activity.dismissProgressDialog();
//                        Toast.makeText(activity, genericResponse.getInfo(), Toast.LENGTH_SHORT).show();
//                        if (genericResponse.getCode() == activity.successCode) {
//                            Log.d("presenter", "onNext: "+"masuk success");
//                            activity.gotoMainActivity();
//                        }
//                    }
//                });
//    }
}
