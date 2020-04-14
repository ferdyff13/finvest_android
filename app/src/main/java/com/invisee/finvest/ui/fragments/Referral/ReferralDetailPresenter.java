package com.invisee.finvest.ui.fragments.Referral;

import android.graphics.Color;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.responses.ReferralResponse;
import com.invisee.finvest.data.api.responses.StatusCustomerResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.MainActivity;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pandu.abbiyuarsyah on 12/02/2018.
 */

public class ReferralDetailPresenter {

    private ReferralDetailFragment fragment;

    public ReferralDetailPresenter(ReferralDetailFragment fragment) {
        this.fragment = fragment;
    }

    void getCustomerReferral() {
        fragment.showProgressBar();
        fragment.getApi().getCustomerReferral(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ReferralResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.connectionError();

                    }

                    @Override
                    public void onNext(ReferralResponse response) {
                        fragment.referralResponse = response;
                        fragment.loadImage();
                        if (response.getCode() == 0) {
                            fragment.tvReferralLink.setText(response.getData().getReferralLink());
                            fragment.dismissProgressBar();
                        } else {
                            fragment.dismissProgressBar();
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.failed).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(response.getInfo())
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.ok)
                                    .positiveColor(Color.WHITE)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            MainActivity.startActivity((BaseActivity) fragment.getActivity());
                                        }
                                    })
                                    .cancelable(false)
                                    .show();

                        }

                    }
                });
    }


}
