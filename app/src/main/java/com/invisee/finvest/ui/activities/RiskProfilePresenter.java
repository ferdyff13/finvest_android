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
public class RiskProfilePresenter {

    private RiskProfileActivity activity;

    public RiskProfilePresenter(RiskProfileActivity activity) {
        this.activity = activity;
    }

    void submitUserProfile() {
        activity.showProgressDialog(activity.loading);
        activity.getInviseeService().getApi().postPendingCustomer(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        activity.dismissProgressDialog();
                        Toast.makeText(activity, activity.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(GenericResponse genericResponse) {
                        activity.dismissProgressDialog();
                        Toast.makeText(activity, genericResponse.getInfo(), Toast.LENGTH_SHORT).show();
                        if (genericResponse.getCode() == 1 || genericResponse.getCode() == 0) {
                            Log.d("presenter", "onNext: "+"masuk success");
                            activity.gotoMainActivity();
                        }
                    }
                });

    }
}
