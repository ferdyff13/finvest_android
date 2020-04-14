package com.invisee.finvest.ui.fragments.activation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.MainActivity;
import com.invisee.finvest.ui.activities.UserProfileActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by fajarfatur on 2/3/16.
 */
public class ActivationAccountFragment extends BaseFragment implements Validator.ValidationListener{

    public static final String TAG = ActivationAccountFragment.class.getSimpleName();
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Length(max = 6, message = "Maximal 6 Karakter")
    @Bind(R.id.etActivationCode)
    EditText etActivationCode;
    @Bind(R.id.tvClue)
    TextView tvClue;
    @BindString(R.string.activation_clue)
    String activationClue;
    @State
    String email;
    @State
    String password;

    Validator validator;

    private ActivationAccountPresenter presenter;

    public static void showFragment(BaseActivity sourceActivity, String email, String password) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            Bundle bundle = new Bundle();
            bundle.putString(EMAIL, email);
            bundle.putString(PASSWORD, password);
            ActivationAccountFragment fragment = new ActivationAccountFragment();
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            fragmentTransaction.replace(R.id.container, fragment, TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_activation_account;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validator = new Validator(this);
        validator.setValidationListener(this);
        presenter = new ActivationAccountPresenter(this);
        email = getArguments().getString(EMAIL);
        password = getArguments().getString(PASSWORD);
        if (email == null) getActivity().onBackPressed();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String clue = String.format(activationClue, email);
        tvClue.setText(clue);
        tvClue.setTextColor(Color.WHITE);
        etActivationCode.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(6)});
    }

    @OnClick(R.id.bActivate)
    void activate() {
        validator.validate();
    }

    @OnClick(R.id.bResend)
    void resend() {
        presenter.resend(email);
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
                .negativeColor(cPrimary)
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
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this.getContext());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onValidationSucceeded() {
        presenter.activate(presenter.cunstructActivateUserRequest());
    }
}
