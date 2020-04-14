package com.invisee.finvest.ui.fragments.changepassword;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pandu.abbiyuarsyah on 14/03/2017.
 */

public class ChangePasswordFragment extends BaseFragment {

    public static final String TAG = ChangePasswordFragment.class.getSimpleName();

    @Bind(R.id.etConfirmPass)
    EditText etConfirmPass;
    @Bind(R.id.etOldPass)
    EditText etOldPass;
    @Bind(R.id.etNewPass)
    EditText etNewPass;
    @Bind(R.id.btSaveChangePass)
    Button btSaveChangePass;

    ChangePasswordPresenter presenter;


    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.container, new ChangePasswordFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_change_password;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ChangePasswordPresenter(this);


    }

    @OnClick(R.id.btSaveChangePass)
    void ChangePassword() {
        String vnewpass = etNewPass.getText().toString();
        String vconfirmpass = etConfirmPass.getText().toString();

        if (!validate()) {
            onSignupFailed();
            return;
        } else {
            if (vnewpass.equals(vconfirmpass)) {
                presenter.requestChangePassword(presenter.constructChangePasswordRequest());
            } else {
                Toast.makeText(getActivity(), "Tolong periksa lagi password baru dan konfirmasi password", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void onSignupFailed() {
        Toast.makeText(getContext(), "Silahkan lengkapi data yang dibutuhkan", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {

        boolean valid = false;

        String oldpass = etOldPass.getText().toString();
        String newpass = etNewPass.getText().toString();
        String confirmpass = etConfirmPass.getText().toString();

        String oldpassmatch = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";
        String match = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";
        String confirmpassmatch = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";

        if (oldpass.isEmpty()|| !oldpass.matches(oldpassmatch)) {
            etOldPass.setError("Masukkan password lama dengan benar");
        } else if (newpass.isEmpty() || !newpass.matches(match)) {
            etNewPass.setError("Masukkan password baru dengan benar");
        } else if (confirmpass.isEmpty() || !confirmpass.matches(confirmpassmatch)) {
            etConfirmPass.setError("Masukkan konfirmasi password dengan benar");
        } else {
            valid = true;
        }

        return valid;
    }

}
