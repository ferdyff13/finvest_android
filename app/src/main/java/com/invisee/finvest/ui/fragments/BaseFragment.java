package com.invisee.finvest.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.util.eventBus.RxBus;
import com.invisee.finvest.util.eventBus.RxBusObject;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.ButterKnife;
import icepick.Icepick;
import io.realm.Realm;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by fajarfatur on 12/15/15.
 */
public abstract class BaseFragment extends Fragment implements Validator.ValidationListener {

    @BindString(R.string.loading)
    public String loading;
    @BindString(R.string.connection_error)
    public String connectionError;
    @BindString(R.string.connection_error_swipe)
    public String connectionErrorSwipe;
    @BindString(R.string.no_data)
    public String noData;
    @BindInt(R.integer.success_code)
    public int successCode;
    @BindInt(R.integer.success_activication_code)
    public  int activicationSuccessCode;
    @BindInt(R.integer.failure_code)
    public int failureCode;
    @BindColor(R.color.colorPrimary)
    public int cAppsPrimary;
    @BindColor(R.color.colorPrimaryDark)
    public int cAppsPrimaryDark;
    @BindColor(R.color.blue_700)
    public int cPrimary;
    @BindColor(R.color.green_500)
    public int cSuccess;
    @BindColor(R.color.light_blue_500)
    public int cInfo;
    @BindColor(R.color.orange_500)
    public int cWarning;
    @BindColor(R.color.red_500)
    public int cDanger;
    @BindColor(R.color.grayColor)
    public int cGray;

    protected Realm realm;
    protected Validator validator;
    protected RxBus bus;
    private CompositeSubscription subscriptions;

    protected abstract int getLayout();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        Icepick.restoreInstanceState(this, savedInstanceState);
        this.validator = new Validator(this);
        this.validator.setValidationListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.bus = ((BaseActivity) getActivity()).getBus();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.realm = Realm.getDefaultInstance();
        this.subscriptions = new CompositeSubscription();
        this.subscriptions
                .add(bus.toObserverable()
                        .subscribe(new Action1<Object>() {
                            @Override
                            public void call(Object event) {
                                if (event instanceof RxBusObject) {
                                    RxBusObject busObject = (RxBusObject) event;
                                    busHandler(busObject.getKey(), busObject.getObject());
                                }
                            }
                        })
                );
    }

    @Override
    public void onStop() {
        super.onStop();
        this.realm.close();
        this.subscriptions.clear();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public InviseeService.Api getApi() {
        return ((BaseActivity) getActivity()).getInviseeService().getApi();
    }

    public Realm getRealm() {
        return realm;
    }

    public Validator getValidator() {
        return validator;
    }

    public RxBus getBus() {
        return this.bus;
    }

    public void busHandler(RxBusObject.RxBusKey busKey, Object busObject) {

    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());
            if (view instanceof EditText) {
                EditText et = ((EditText) view);
                et.setError(message);
                et.requestFocus();
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void showProgressDialog(String message) {
        ((BaseActivity) getActivity()).showProgressDialog(message);
    }

    public void showProgressDialogOnClick(String message) {
        ((BaseActivity) getActivity()).showProgressDialogOnClick(message);
    }



    public void dismissProgressDialog() {
        ((BaseActivity) getActivity()).dismissProgressDialog();
    }

    public void showFailedDialog(String message) {
        new MaterialDialog.Builder(getActivity())
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(getString(R.string.failed).toUpperCase())
                .titleColor(Color.WHITE)
                .content(message)
                .contentColor(Color.WHITE)
                .positiveText(R.string.ok)
                .positiveColor(Color.WHITE)
                .cancelable(false)
                .show();
    }

    public void showUploadSuccessDialog(String message) {
        new MaterialDialog.Builder(getActivity())
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(getString(R.string.success).toUpperCase())
                .titleColor(Color.WHITE)
                .content(message)
                .contentColor(Color.WHITE)
                .positiveText(R.string.ok)
                .positiveColor(Color.WHITE)
                .cancelable(false)
                .show();
    }

    public void showSuccessDialog(String message, final Boolean isFinishActivity) {
        new MaterialDialog.Builder(getActivity())
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(getString(R.string.success).toUpperCase())
                .titleColor(Color.WHITE)
                .content(message)
                .contentColor(Color.WHITE)
                .positiveText(R.string.ok)
                .positiveColor(Color.WHITE)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        if (isFinishActivity) {
                            getActivity().finish();
                        }
                    }
                })
                .cancelable(false)
                .show();
    }
}
