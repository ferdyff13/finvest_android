package com.invisee.finvest.ui.fragments.signIn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by fajarfatur on 1/28/16.
 */
public class ResetPasswordFragment extends BaseFragment {

    public static final String TAG = ResetPasswordFragment.class.getSimpleName();
    private static final String EMAIL = "email";
    private static final String ANSWER = "answer";
    private static final String QUESTION = "question";


    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etResetCode)
    EditText etResetCode;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Pattern(regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$", messageResId = R.string.rules_password)
    @Password
    @Bind(R.id.etPassword)
    EditText etPassword;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @ConfirmPassword
    @Bind(R.id.etConfirmPassword)
    EditText etConfirmPassword;

    private ResetPasswordPresenter presenter;
    private String email;
    private String question;
    private String answer;
    private int maxLength = 6;

    public static void showFragment(BaseActivity sourceActivity, String email, String answer, String question) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            Fragment fragment = new ResetPasswordFragment();
            Bundle bundle = new Bundle();
            bundle.putString(EMAIL, email);
            bundle.putString(ANSWER, answer);
            bundle.putString(QUESTION, question);
            fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.container, fragment, TAG);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


    @Override
    protected int getLayout() {
        return R.layout.f_reset_password;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ResetPasswordPresenter(this);
        email = getArguments().getString(EMAIL);
        answer = getArguments().getString(ANSWER);
        question = getArguments().getString(QUESTION);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etResetCode.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(6)});
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @OnClick(R.id.bSubmit)
    void submitResetPassword() {
        getValidator().validate();
    }

    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();
        presenter.requestResetPassword(presenter.constructResetPasswordRequest(email));
    }

    @OnClick(R.id.bResendCode)
    void resendCode() {
        presenter.requestResendCode(presenter.constructForgotResendCode(email, answer, question));
    }
}
