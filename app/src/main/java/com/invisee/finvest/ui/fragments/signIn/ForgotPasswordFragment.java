package com.invisee.finvest.ui.fragments.signIn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.SecurityQuestion;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by fajarfatur on 1/28/16.
 */
public class ForgotPasswordFragment extends BaseFragment {

    public static final String TAG = ForgotPasswordFragment.class.getSimpleName();

    @Email
    @NotEmpty(messageResId = R.string.rules_no_empty_email)
    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.sQuestion)
    Spinner sQuestion;
    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etAnswer)
    EditText etAnswer;

    private ForgotPasswordPresenter presenter;

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.container, new ForgotPasswordFragment(), TAG);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_forgot_password;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ForgotPasswordPresenter(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.queryAndSetupSecurityQuestions();
    }

    public void setupSecurityQuestionSpinner(List<SecurityQuestion> securityQuestionList){
        ArrayAdapter<SecurityQuestion> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, securityQuestionList);
        sQuestion.setAdapter(spinnerArrayAdapter);
        sQuestion.setSelection(0, false);
    }

    @OnClick(R.id.bSubmit)
    void submitForgotPassword(){
        getValidator().validate();
    }

    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();
        presenter.requestForgotPassword(presenter.constructForgotPasswordRequest());
    }

}
