package com.invisee.finvest.ui.fragments.signIn;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.ui.activities.ActivationActivity;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.ForgotPasswordActivity;
import com.invisee.finvest.ui.activities.MainActivity;
import com.invisee.finvest.ui.activities.SignUpActivity;
import com.invisee.finvest.ui.activities.UserProfileActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by fajarfatur on 1/12/16.
 */
public class SignInFragment extends BaseFragment {

    public static final String TAG = SignInFragment.class.getSimpleName();

    private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_CHECKBOX_vALUE = "checkBox";

    private final String DefaultUnameValue = "";
    private final Boolean DefaultCheckboxValue = false;
    private String UnameValue;
    private Boolean CheckboxValue;
    private Boolean checkBoxValue;

    @NotEmpty(messageResId = R.string.rules_no_empty_username)
    @Bind(R.id.etUsername)
    EditText etUsername;
    @Password(min = 6, messageResId = R.string.rules_min_password)
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.cbRememberMe)
    CheckBox cbRememberMe;

    private SignInPresenter presenter;

    public static void showFragment(BaseActivity sourceActivity) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.container, new SignInFragment(), TAG);
            fragmentTransaction.commit();
    }

    @Override
    protected int getLayout() {
        return R.layout.f_signin;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SignInPresenter(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPreferences();
    }

    @Override
    public void onPause() {
        super.onPause();
        savePreferences();

    }

    private void savePreferences() {
        if (cbRememberMe.isChecked()){
            SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();

            // Edit and commit
            UnameValue = etUsername.getText().toString();
            System.out.println("onPause save name: " + UnameValue);
            checkBoxValue = true;
            editor.putString(PREF_UNAME, UnameValue);
            editor.putBoolean(PREF_CHECKBOX_vALUE, checkBoxValue);
            editor.commit();
        } else {
            checkBoxValue = false;
            SharedPreferences preferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            preferences.edit().remove(PREF_UNAME).commit();
            preferences.edit().remove(PREF_CHECKBOX_vALUE).commit();
        }

    }

    private void loadPreferences() {

        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        // Get value
        UnameValue = settings.getString(PREF_UNAME, DefaultUnameValue);
        CheckboxValue =settings.getBoolean(PREF_CHECKBOX_vALUE, DefaultCheckboxValue);
        etUsername.setText(UnameValue);
        cbRememberMe.setChecked(CheckboxValue);
        System.out.println("onResume load name: " + UnameValue);
//        if (checkBoxValue == null) {
//            cbRememberMe.setChecked(false);
//        } else {
//            cbRememberMe.setChecked(checkBoxValue);
//        }

    }


    @OnClick(R.id.bSignIn)
    void signIn() {
        getValidator().validate();
    }

    @OnClick(R.id.bForgotPassword)
    void forgotPassword() {
        ForgotPasswordActivity.startActivity((BaseActivity) getActivity());
    }

    @OnClick(R.id.bSignUp)
    void signUp() {
        SignUpActivity.startActivity((BaseActivity) getActivity());
    }

    public void onBackPressed()
    {
        if(getActivity().getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getActivity().getSupportFragmentManager().popBackStack();
        } else {
            super.getActivity().finish();
        }
    }

    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();
        presenter.login(presenter.constructLoginRequest());
    }

    public void showUserProfileSuggestionDialog() {
        new MaterialDialog.Builder(getActivity())
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(getString(R.string.welcome).toUpperCase())
                .titleColor(Color.WHITE)
                .content(R.string.user_profile_suggestion)
                .contentColor(Color.WHITE)
                .positiveText(R.string.ok)
                .positiveColor(Color.WHITE)
                .negativeText(R.string.later)
                .negativeColor(cGray)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        UserProfileActivity.startActivity((BaseActivity) getActivity());
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        gotoMainActivity();
                    }
                })
                .cancelable(false)
                .show();
    }

    public void gotoMainActivity() {
        MainActivity.startActivity((BaseActivity) getActivity());
        getActivity().finish();
    }

    public void gotoActivationCodeActivity(String email, String password) {
        ActivationActivity.startActivity((BaseActivity) getActivity(), email, password);
    }

    void init(){
        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(etUsername.length() >0){
                    String lowercase = etUsername.getText()+"";
                    etUsername.setText(lowercase.toLowerCase());
                }
                return false;
            }
        });
    }


    // TODO: 16/05/2018 test
    /*@Override
    public void onDestroy() {
        presenter.cleanResource();
        super.onDestroy();
    }*/
}
