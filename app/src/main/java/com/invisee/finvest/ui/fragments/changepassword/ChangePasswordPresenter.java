package com.invisee.finvest.ui.fragments.changepassword;

import android.graphics.Color;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.requests.ChangePasswordRequest;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.UserProfileActivity;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by pandu.abbiyuarsyah on 14/03/2017.
 */

public class ChangePasswordPresenter {

    private ChangePasswordFragment fragment;

    public ChangePasswordPresenter(ChangePasswordFragment fragment) {
        this.fragment = fragment;
    }

    ChangePasswordRequest constructChangePasswordRequest() {
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setEmail(PrefHelper.getString(PrefKey.EMAIL));
        request.setToken(PrefHelper.getString(PrefKey.TOKEN));
        request.setConfPassword(fragment.etConfirmPass.getText().toString());
        request.setNewPassword(fragment.etNewPass.getText().toString());
        request.setOldPassword(fragment.etOldPass.getText().toString());

        return request;
    }

    void requestChangePassword(final ChangePasswordRequest changePasswordRequest) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().changePassword(
                changePasswordRequest.getEmail(),
                changePasswordRequest.getConfPassword(),
                changePasswordRequest.getNewPassword(),
                changePasswordRequest.getOldPassword(),
                changePasswordRequest.getToken())
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
                        if (genericResponse.getCode() == 1) {
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.success).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(genericResponse.getInfo())
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.ok)
                                    .positiveColor(Color.WHITE)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            UserProfileActivity.startActivity((BaseActivity) fragment.getActivity());
                                        }
                                    })
                                    .cancelable(false)
                                    .show();

                        } else {
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.error).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(genericResponse.getInfo())
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.ok)
                                    .positiveColor(Color.WHITE)
                                    .cancelable(false)
                                    .show();
                        }

                    }
                });

    }




}
