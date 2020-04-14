package com.invisee.finvest.ui.activities;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.util.Constant;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by fajarfatur on 1/12/16.
 */
public class SplashScreenActivity extends BaseActivity {

//    @Bind(R.id.ivLoading)
//    ImageView ivLoading;
    @Bind(R.id.llRetry)
    LinearLayout llRetry;
    @Bind(R.id.ivLogo)
    ImageView splashLogoImg;

    private SplashScreenPresenter presenter;

    @Override
    protected int getLayout() {
        return R.layout.a_splashscreen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        startLoadingAnimation();

        /*Picasso.with(getApplicationContext()).load(R.drawable.bg_splash).fit()
                .into(splashBackground, new Callback() {
                    @Override
                    public void onSuccess() {}

                    @Override
                    public void onError() {}
                });*/

        Picasso.with(getApplicationContext()).load(R.drawable.ic_lo_finvest).fit().centerInside()
                .into(splashLogoImg, new Callback() {
                    @Override
                    public void onSuccess() {}

                    @Override
                    public void onError() {}
                });

        presenter = new SplashScreenPresenter(this);
//        presenter.checkVersion();
        presenter.checkSecurityQuestionOnRealm();
    }

//    private void startLoadingAnimation() {
//        ((AnimationDrawable) ivLoading.getBackground()).start();
//    }
//
//    public void stopLoadingAnimation() {
//        ((AnimationDrawable) ivLoading.getBackground()).stop();
//    }

    void startApplication() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PrefHelper.getBoolean(PrefKey.IS_LOGIN)) {
                    String userStatus = PrefHelper.getString(PrefKey.CUSTOMER_STATUS);
                    if (userStatus.equalsIgnoreCase(Constant.USER_STATUS_ACTIVE)) {
                        showUserProfileSuggestionDialog();
                    } else {
                        gotoMainActivity();
                    }
                } else {
                    SignInActivity.startActivity(SplashScreenActivity.this);
                    finish();
                }
            }
        }, 3000);

    }

    @OnClick(R.id.bRetry)
    void retry() {
//        presenter.checkVersion();
        presenter.checkSecurityQuestionOnRealm();
        llRetry.setVisibility(View.GONE);
//        startLoadingAnimation();
    }

    public  void setLanguage() {

        String languageToLoad  = "in"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    public void showUserProfileSuggestionDialog() {
        new MaterialDialog.Builder(this)
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
                        UserProfileActivity.startActivity(SplashScreenActivity.this);
                        finish();
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
        MainActivity.startActivity(this);
        finish();
    }
}
