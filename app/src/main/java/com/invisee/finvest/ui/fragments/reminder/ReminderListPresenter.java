package com.invisee.finvest.ui.fragments.reminder;

import android.graphics.Color;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Reminder;
import com.invisee.finvest.data.api.requests.DeleteReminderRequest;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fajarfatur on 3/21/16.
 */
public class ReminderListPresenter {

    private ReminderListFragment fragment;

    public ReminderListPresenter(ReminderListFragment fragment) {
        this.fragment = fragment;
    }

    void getReminderList() {
        fragment.showProgressBar();
        fragment.getApi().reminderList(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Reminder>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressBar();
                        fragment.noReminder(true);
                    }

                    @Override
                    public void onNext(List<Reminder> reminderList) {
                        if (reminderList != null && reminderList.size() > 0 ) {
                            fragment.noReminder(false);
                            fragment.reminderList = reminderList;
                            fragment.loadList();
                            fragment.dismissProgressBar();
                        } else {
                            fragment.noReminder(true);
                            fragment.dismissProgressBar();
                        }

                    }
                });
    }

    DeleteReminderRequest constructdeletereminder(Integer id){
        DeleteReminderRequest deleteReminderRequest = new DeleteReminderRequest();
        deleteReminderRequest.setToken(PrefHelper.getString(PrefKey.TOKEN));
        deleteReminderRequest.setId(id);

        return deleteReminderRequest;

    }

    public void dialog(final int id){
        new MaterialDialog.Builder(fragment.getActivity())
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(fragment.cDanger)
                .title(fragment.getString(R.string.infortmation).toUpperCase())
                .titleColor(Color.WHITE)
                .content(R.string.reminder_delete_info)
                .contentColor(Color.WHITE)
                .positiveText(R.string.yes)
                .positiveColor(Color.WHITE)
                .negativeText(R.string.no)
                .negativeColor(fragment.cDanger)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                        deleteReminder(id);
                    }
                })
                .cancelable(false)
                .show();
    }


    public void deleteReminder(Integer id) {
        fragment.getApi().deleteReminder(PrefHelper.getString(PrefKey.TOKEN), constructdeletereminder(id))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.connectionError();

                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        if(response.getCode() == 1) {
                            getReminderList();
                        } else {
                            fragment.dismissProgressBar();
                        }

                    }

                });
    }
}
