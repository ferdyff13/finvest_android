package com.invisee.finvest.ui.fragments.reminder;

import android.graphics.Color;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.requests.InvesmentNumberRequest;
import com.invisee.finvest.data.api.requests.ReminderEditRequest;
import com.invisee.finvest.data.api.requests.SaveReminderRequest;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.api.responses.InvestmentAccountResponse;
import com.invisee.finvest.data.api.responses.PackageByTokenResponse;
import com.invisee.finvest.data.api.responses.ReminderDetailResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.ReminderListActivity;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ReminderAddPresenter {

    private ReminderAddFragment fragment;

    public ReminderAddPresenter(ReminderAddFragment fragment) {
        this.fragment = fragment;
    }

 /*   ReminderEditRequest constructreminderedit() {
        ReminderEditRequest reminderEditRequest = new ReminderEditRequest();

        reminderEditRequest.setId(String.valueOf(fragment.packageList..getReminderId()));
        reminderEditRequest.setToken(PrefHelper.getString(PrefKey.TOKEN));

        return reminderEditRequest;
    }

    void getReminderDetail(final ReminderEditRequest request) {
        fragment.getApi().getReimderDetail(request.getId(), request.getToken())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ReminderDetailResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(ReminderDetailResponse response) {
                        fragment.dismissProgressDialog();
                        if (response.getCode() == 1) {
                            fragment.reminderDetail = response.getReminder();
                            getInvestmentNumberByPackage(constructInvesmentNumbber());
                        }
                    }
                });
    }*/

    void getPackageNameByToken() {
        fragment.showProgressDialog(fragment.getString(R.string.please_wait));
        fragment.getApi().viewPackageName(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PackageByTokenResponse>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        fragment.dismissProgressDialog();
                        fragment.showFailedDialog(fragment.connectionError);
                    }

                    @Override
                    public void onNext(PackageByTokenResponse response) {
                        fragment.dismissProgressDialog();

                        if (response.getCode() == 1) {
                            fragment.packageList = response.getData();
                            fragment.setSpinnerPackage();
                            fragment.setDefaultValue();
                            /*getReminderDetail(constructreminderedit());*/
                        } else {
                            fragment.showFailedDialog(response.getInfo());
                        }
                    }
                });
    }

   /* InvesmentNumberRequest constructInvesmentNumbber(S) {
        InvesmentNumberRequest invesmentNumberRequest = new InvesmentNumberRequest();

        if (fragment.packageList != null && fragment.packageList.size() > 0) {
           getInvestmentNumberByPackage(Long.toString(packageList.get(position).getId()));
            invesmentNumberRequest.setFundPackage(String.valueOf());
        }

        invesmentNumberRequest.setFundPackage(String.valueOf(fragment.reminderDetail.getFundPackageRefId()));
        invesmentNumberRequest.setToken(PrefHelper.getString(PrefKey.TOKEN));

        return invesmentNumberRequest;
    }

    void getInvestmentNumberByPackage(final InvesmentNumberRequest request) {
        fragment.getApi().viewInvestementAccount(request.getFundPackage(), request.getToken())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<InvestmentAccountResponse>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        fragment.dismissProgressDialog();
                        fragment.showFailedDialog(fragment.connectionError);
                    }

                    @Override
                    public void onNext(InvestmentAccountResponse response) {
                        fragment.dismissProgressDialog();
                        if (response.getCode() == 1) {
                            fragment.investmentAccountList = response.getData();
                            fragment.setSpinnerInvestmentNo();
                        } else {
                            fragment.showFailedDialog(response.getInfo());
                        }
                    }
                });
    }*/

    void getInvestmentNumberByPackage(String fundPackageId) {
        fragment.showProgressDialog(fragment.getString(R.string.please_wait));
        fragment.getApi().viewInvestementAccountrEeminder(PrefHelper.getString(PrefKey.TOKEN), fundPackageId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<InvestmentAccountResponse>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        fragment.dismissProgressDialog();
                        fragment.showFailedDialog(fragment.connectionError);
                    }

                    @Override
                    public void onNext(InvestmentAccountResponse response) {
                        fragment.dismissProgressDialog();

                        if (response.getCode() == 1) {
                            fragment.investmentAccountList = response.getData();
                            fragment.setSpinnerInvestmentNo();
                        } else {
                            fragment.showFailedDialog(response.getInfo());
                        }
                    }
                });
    }

    void saveOrUpdate(SaveReminderRequest request) {
        fragment.showProgressDialog(fragment.getString(R.string.please_wait));
        fragment.getApi().saveOrUpdateReminder(PrefHelper.getString(PrefKey.TOKEN), request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        fragment.dismissProgressDialog();
                        fragment.showFailedDialog(fragment.connectionError);
                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        fragment.dismissProgressDialog();

                        if (response.getCode() == 1) {
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.success).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(response.getInfo())
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.ok)
                                    .positiveColor(Color.WHITE)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            ReminderListActivity.startActivity((BaseActivity) fragment.getActivity());
                                            fragment.getActivity().finish();
                                        }
                                    })
                                    .cancelable(false)
                                    .show();

                        } else {
                            fragment.showFailedDialog(response.getInfo());
                        }
                    }
                });
    }


}
