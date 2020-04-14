package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.BuildConfig;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.SecurityQuestion;
import com.invisee.finvest.data.api.requests.AmountSummaryRequest;
import com.invisee.finvest.data.api.requests.AndroidVersionRequest;
import com.invisee.finvest.data.api.responses.AndroidVersionResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.data.realm.RealmHelper;
import com.invisee.finvest.data.realm.model.SecurityQuestionModel;

import java.util.List;

import io.realm.RealmResults;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/1/16.
 */
public class SplashScreenPresenter {

    private SplashScreenActivity activity;

    public SplashScreenPresenter(SplashScreenActivity activity) {
        this.activity = activity;
    }

    void checkSecurityQuestionOnRealm() {
        RealmHelper.readSecurityQuestions(activity.getRealm())
                .subscribe(new Action1<RealmResults<SecurityQuestionModel>>() {
                    @Override
                    public void call(RealmResults<SecurityQuestionModel> securityQuestionModels) {
                        int size = securityQuestionModels.size();
                        Timber.i("Security Question Size %d", size);
                        if (size > 0) {
                            activity.startApplication();
                        } else {
                            getSecurityQuestion();
                        }
                    }
                });
    }

    public void getSecurityQuestion() {
        activity.getInviseeService().getApi().loadSecurityQuestion()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<SecurityQuestion>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        activity.llRetry.setVisibility(View.VISIBLE);
//                        activity.stopLoadingAnimation();
                    }

                    @Override
                    public void onNext(List<SecurityQuestion> securityQuestions) {
                        RealmHelper.createSecurityQuestionList(activity.getRealm(), securityQuestions);
                        activity.startApplication();
                    }
                });

    }

    AndroidVersionRequest constructandroidversionrequest() {
        AndroidVersionRequest androidVersionRequest = new AndroidVersionRequest();
        androidVersionRequest.setIdDevice(1);
        return androidVersionRequest;
    }

    public void checkVersion() {
        activity.getInviseeService().getApi().checkVersion(constructandroidversionrequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<AndroidVersionResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        activity.llRetry.setVisibility(View.VISIBLE);
//                        activity.stopLoadingAnimation();
                    }

                    @Override
                    public void onNext(AndroidVersionResponse response) {
                        if (response.getCode() == 0) {
                            Integer coreCode = response.getData().getCore().intValue();
                            if(BuildConfig.VERSION_CODE < coreCode) {
                                new MaterialDialog.Builder(activity)
                                        .iconRes(R.mipmap.ic_launcher_finvest)
                                        .backgroundColor(activity.cDanger)
                                        .title(activity.getString(R.string.update).toUpperCase())
                                        .titleColor(Color.WHITE)
                                        .content(activity.getString(R.string.android_version))
                                        .contentColor(Color.WHITE)
                                        .positiveText(R.string.yes)
                                        .positiveColor(Color.WHITE)
                                        .negativeText(R.string.no)
                                        .negativeColor(activity.cGray)
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.indivara.invisee"));
                                                activity.startActivity(webIntent);
                                            }
                                        })
                                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                                                homeIntent.addCategory( Intent.CATEGORY_HOME );
                                                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                activity.startActivity(homeIntent);
                                            }
                                        })
                                        .show();
                            } else {
                                checkSecurityQuestionOnRealm();
                            }
                        } else {
                                checkSecurityQuestionOnRealm();
                        }

                    }
                });

    }


}
