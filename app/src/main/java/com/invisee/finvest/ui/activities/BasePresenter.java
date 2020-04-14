package com.invisee.finvest.ui.activities;

import android.graphics.Color;

import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by glenrynaldi on 7/29/16.
 */
public class BasePresenter {

    private BaseActivity activity;

    public BasePresenter(BaseActivity activity) {
        this.activity = activity;
    }

    void invalid() {
        activity.dismissProgressDialog();
        new MaterialDialog.Builder(activity)
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(activity.cDanger)
                .title(activity.getString(R.string.logout).toUpperCase())
                .titleColor(Color.WHITE)
                .content(activity.expiredSession)
                .contentColor(Color.WHITE)
                .neutralText(R.string.ok)
                .show();

        PrefHelper.clearAllPreferences();
        SignInActivity.startActivityFromAutoLogout(activity);
        activity.finish();
    }

    void logout() {

        /*---TOKEN INVALID
        * Option: show login dialog
        * ---*/

        activity.showProgressDialog(activity.loading);
        activity.getInviseeService().getApi().logout(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        activity.dismissProgressDialog();

                        new MaterialDialog.Builder(activity)
                                .iconRes(R.mipmap.ic_launcher_finvest)
                                .backgroundColor(activity.cDanger)
                                .title(activity.getString(R.string.logout).toUpperCase())
                                .titleColor(Color.WHITE)
                                .content(activity.connectionError)
                                .contentColor(Color.WHITE)
                                .neutralText(R.string.ok)
                                .show();
                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        activity.dismissProgressDialog();
                        if (response.getCode() == 1) {
                            PrefHelper.clearAllPreferences();
                            SignInActivity.startActivityFromAutoLogout(activity);
                            activity.finish();
                        } else {
                            new MaterialDialog.Builder(activity)
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(activity.cDanger)
                                    .title(activity.getString(R.string.logout).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(response.getInfo())
                                    .contentColor(Color.WHITE)
                                    .neutralText(R.string.ok)
                                    .show();
                        }

                    }
                });

    }

}
