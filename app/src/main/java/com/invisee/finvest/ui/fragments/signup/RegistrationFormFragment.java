package com.invisee.finvest.ui.fragments.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.SecurityQuestion;
import com.invisee.finvest.ui.activities.ActivationActivity;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.SignUpActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.ui.fragments.termsandcondition.TermsAndConditionFragment;
import com.invisee.finvest.ui.fragments.termsandcondition.TermsAndConditionInviseeFragment;
import com.invisee.finvest.ui.fragments.termsandcondition.TermsAndConditionViseePayFragment;
import com.invisee.finvest.ui.fragments.termsandcondition.TermsAndConditionViseePayRegisterFragment;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by fajarfatur on 1/12/16.
 */
public class RegistrationFormFragment extends BaseFragment {

    public static final String TAG = RegistrationFormFragment.class.getSimpleName();

    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etFirstName)
    EditText etFirstName;

    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etLastName)
    EditText etLastName;


    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etCountryCodeMobilePhone)
    EditText etCountryCodeMobilePhone;


    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etMobilePhoneNumber)
    EditText etMobilePhoneNumber;


    @Email
    @NotEmpty(messageResId = R.string.rules_no_empty_email)
    @Bind(R.id.etEmail)
    EditText etEmail;


    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Pattern(regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$", messageResId = R.string.rules_password)
    @Password
    @Bind(R.id.etPassword)
    EditText etPassword;

    @ConfirmPassword
    @Bind(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @Bind(R.id.sQuestion)
    Spinner sQuestion;

    @NotEmpty(messageResId = R.string.rules_no_empty)
    @Bind(R.id.etAnswer)
    EditText etAnswer;

    @Bind(R.id.cbAgree)
    CheckBox cbAgree;

    @Bind(R.id.tvAgree)
    TextView tvAgree;

    private RegistrationFormPresenter presenter;

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, new RegistrationFormFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_registration_form;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RegistrationFormPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }



    @Override
    public void onResume() {
        super.onResume();
        ((SignUpActivity) getActivity()).setActionBarTitle("Register");
        presenter.queryAndSetupSecurityQuestions();
    }

    public void setupSecurityQuestionSpinner(List<SecurityQuestion> securityQuestionList) {
        ArrayAdapter<SecurityQuestion> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, securityQuestionList);
        sQuestion.setAdapter(spinnerArrayAdapter);
        sQuestion.setSelection(0, false);
    }

    @OnClick(R.id.bSignUp)
    void signUp() {
        if(etEmail.length() >0){
            String lowercase = etEmail.getText()+"";
            etEmail.setText(lowercase.toLowerCase());
        }

        if (!validate()) {
            onSignupFailed();
            return;
        } else {
            validator.validate();
//            presenter.register(presenter.constructRegistrationRequest());
           /* signup();*/
        }
     /*   getValidator().validate();*/
    }

    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();
        presenter.register(presenter.constructRegistrationRequest());
    }
    public void onSignupFailed() {
        Toast.makeText(getContext(), "Silahkan lengkapi data yang dibutuhkan", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {

        boolean valid;

        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String countryMobilePhone = etCountryCodeMobilePhone.getText().toString();
        String mobilePhoneNumber = etMobilePhoneNumber.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmpass = etConfirmPassword.getText().toString();
        String answer = etAnswer.getText().toString();

        if (firstName.isEmpty() && firstName.length() <= 0) {
            etFirstName.setError("Mohon isi kolom ini");
            valid = false;
        } else {
            etFirstName.setError(null);
        }

        if (lastName.isEmpty() && lastName.length() <= 1) {
            etLastName.setError("Mohon isi kolom ini");
            valid = false;
        } else {
            etLastName.setError(null);
        }

        if (countryMobilePhone.isEmpty()) {
            etCountryCodeMobilePhone.setText("62");
            valid = true;
        } else {
            etCountryCodeMobilePhone.setError(null);
        }

        if (mobilePhoneNumber.isEmpty()) {
            etMobilePhoneNumber.setError("Mohon isi kolom ini");
            valid = false;
        } else {
            etMobilePhoneNumber.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Masukan email anda dengan benar");
            valid = false;
        }

        String match = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";

        if (password.isEmpty() || !password.matches(match) ) {
            etPassword.setError("Masukan password dengan benar");
            valid = false;
        }

        String confirmpassmatch = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";

        if (confirmpass.isEmpty()) {
            etConfirmPassword.setError("Masukan konfirmasi password dengan benar");
            valid = false;
        }

        if (answer.isEmpty()) {
            etAnswer.setError("Mohon isi kolom ini");
            valid = false;
        } else {
            etAnswer.setError(null);
        }
        valid = cbAgree.isChecked();

        return valid;
    }

    public void gotoActivationCodeActivity(String email, String password) {
        ActivationActivity.startActivity((BaseActivity) getActivity(), email, password);
        getActivity().finish();
    }

    @OnClick(R.id.tvTermsInvisee)
    void termsConditions(){
        TermsAndConditionInviseeFragment.showFragment((BaseActivity) getActivity());
    }

//    @OnClick(R.id.tvTermsViseePay)
//    void termsConditionsViseePay() {
//        TermsAndConditionViseePayRegisterFragment.showFragment((BaseActivity) getActivity());
//    }
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        super.onValidationFailed(errors);
    }

    void init(){
        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(etEmail.length() >0){
                    String lowercase = etEmail.getText()+"";
                    etEmail.setText(lowercase.toLowerCase());
                }
                return false;
            }
        });
    }
}
