package com.invisee.finvest.ui.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.InviseeApplication;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.util.LocaleUtil;
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

public abstract class BaseActivity extends AppCompatActivity implements Validator.ValidationListener {

    @BindString(R.string.app_name)
    public String appName;
    @BindString(R.string.loading)
    public String loading;
    @BindString(R.string.connection_error)
    public String connectionError;
    @BindInt(R.integer.success_code)
    public int successCode;
    @BindInt(R.integer.success_activication_code)
    public  int activicationSuccessCode;
    @BindInt(R.integer.failure_code)
    public int failureCode;
    @BindString(R.string.expired_session)
    public String expiredSession;
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
    @BindColor(R.color.red_300)
    public int cDanger2;
    @BindColor(R.color.grey_600)
    public int cGrey;
    @BindColor(R.color.grayColor)
    public int cGray;

    static TextView notifCount;

    protected InviseeService inviseeService;
    protected Realm realm;
    protected RxBus bus;
    protected Validator validator;
    private ProgressDialog progressDialog;
    private CompositeSubscription subscriptions;
    private BroadcastReceiver invalidTokenBroadcastReceiver;

    protected abstract int getLayout();

    private BasePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }*/

        setContentView(getLayout());
        Icepick.restoreInstanceState(this, savedInstanceState);
        ButterKnife.bind(this);
        presenter = new BasePresenter(this);
        this.inviseeService = InviseeApplication.getInstance().getInviseeService();
        this.realm = Realm.getDefaultInstance();
        this.bus = new RxBus();
        this.validator = new Validator(this);
        this.validator.setValidationListener(this);
        this.progressDialog = new ProgressDialog(this);
        initUnauthorizedAwareness();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        this.subscriptions.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.progressDialog.dismiss();
        this.realm.close();
        unregisterReceiver(invalidTokenBroadcastReceiver);
    }

    public InviseeService getInviseeService() {
        return inviseeService;
    }

    public Realm getRealm() {
        return realm;
    }

    public RxBus getBus() {
        return bus;
    }

    public void busHandler(RxBusObject.RxBusKey busKey, Object busObject) {

    }

    public Validator getValidator() {
        return validator;
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void showProgressDialog(String message) {
        if (progressDialog != null) {
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

    }

    public void showProgressDialogOnClick(String message) {
        if (progressDialog != null) {
            progressDialog.setMessage(message);
            progressDialog.show();
        }

    }

    public void dismissProgressDialog() {
        progressDialog.hide();
    }

    public boolean isFragmentNotNull(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag) != null;
    }

    public boolean isFragmentVisible(String tag) {
        return isFragmentNotNull(tag)
                && getSupportFragmentManager().findFragmentByTag(tag).isVisible();
    }

    public Fragment getVisibleFragment(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    public void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void initUnauthorizedAwareness() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Invalid");
        invalidTokenBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                presenter.invalid();
                //presenter.logout();
            }
        };
        registerReceiver(invalidTokenBroadcastReceiver, intentFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        View count = menu.findItem(R.id.badge).getActionView();
        notifCount = (TextView) count.findViewById(R.id.notif_count);

        if  (PrefHelper.getInt(PrefKey.CART) < 0 ) {
            notifCount.setText(String.valueOf(0));
        } else {
            notifCount.setText(String.valueOf(PrefHelper.getInt(PrefKey.CART)));
        }


        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartActivity.startActivity(BaseActivity.this);
            }
        });
        return true;
    }

    public void setNotifCount(int count) {
        if (PrefHelper.getInt(PrefKey.CART) >=0){
            PrefHelper.setInt(PrefKey.CART, count);
            invalidateOptionsMenu();
        } else {
            PrefHelper.setInt(PrefKey.CART, 0);
            invalidateOptionsMenu();
        }
    }

    public void addNotifCount() {
        setNotifCount(PrefHelper.getInt(PrefKey.CART) + 1);
    }

    public BaseActivity() {
        LocaleUtil.updateConfig(this);
    }

    public void showFailedDialog(String message) {
        new MaterialDialog.Builder(this)
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


    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }


}
