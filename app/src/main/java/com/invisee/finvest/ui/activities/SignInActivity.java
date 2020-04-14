package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.ui.fragments.signIn.SignInFragment;

/**
 * Created by fajarfatur on 1/12/16.
 */
public class SignInActivity extends BaseActivity {

    private static final String AUTO_LOGOUT = "autoLogout";

    int click = 0;

    public static void startActivity(BaseActivity sourceActivity) {
        FragmentManager manager = sourceActivity.getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        Intent intent = new Intent(sourceActivity, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        sourceActivity.startActivity(intent);
    }

    public static void startActivityFromAutoLogout(BaseActivity sourceActivity) {
        Intent intent = new Intent(sourceActivity, SignInActivity.class);

        intent.putExtra(AUTO_LOGOUT, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        sourceActivity.startActivityForResult(intent, 0);
    }

    @Override
    protected int getLayout() {
        return R.layout.a_signin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SignInFragment.showFragment(this);
        if(getIntent().hasExtra(AUTO_LOGOUT) && getIntent().getBooleanExtra(AUTO_LOGOUT, false)){
            showAutoLogoutDialog();
        }
    }

    private void showAutoLogoutDialog() {
        new MaterialDialog.Builder(this)
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(getString(R.string.auto_logout).toUpperCase())
                .titleColor(Color.WHITE)
                .content(R.string.expired_session)
                .contentColor(Color.WHITE)
                .positiveText(R.string.ok)
                .positiveColor(Color.WHITE)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);

    }
}
