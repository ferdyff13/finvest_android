package com.invisee.finvest.ui.fragments.signIn;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pandu.abbiyuarsyah on 13/07/2017.
 */

public class ForgotPasswordTempFragment extends BaseFragment{

    public static final String TAG = ForgotPasswordTempFragment.class.getSimpleName();

    @Email
    @NotEmpty(messageResId = R.string.rules_no_empty_email)
    @Bind(R.id.etEmail)
    EditText etEmail;

    ForgotPasswordTempPresenter presenter;

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.container, new ForgotPasswordTempFragment(), TAG);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


    @Override
    protected int getLayout() {
        return R.layout.f_forgot_password_temp;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ForgotPasswordTempPresenter(this);
    }


    @OnClick(R.id.bSubmit)
    void submitForgotPassword(){
        getValidator().validate();
    }

    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();
        presenter.urgentForgotPass(etEmail.getText().toString());
    }

    public void showDialogLoad(){
        new MaterialDialog.Builder(getActivity())
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(getString(R.string.loading).toUpperCase())
                .titleColor(Color.WHITE)
                .content(R.string.loading)
                .contentColor(Color.WHITE)
                .cancelable(false)
                .show();
    }

}
