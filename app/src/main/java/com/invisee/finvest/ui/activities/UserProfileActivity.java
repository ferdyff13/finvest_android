package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.ui.fragments.userProfile.FatcaFragment;
import com.invisee.finvest.ui.fragments.userProfile.KycFragment;
import com.invisee.finvest.ui.fragments.userProfile.RiskProfileFragment;
import com.invisee.finvest.ui.fragments.userProfile.UserInfoFragment;
import com.invisee.finvest.util.eventBus.RxBusObject;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindInt;
import butterknife.BindString;

/**
 * Created by fajarfatur on 1/13/16.
 */
public class UserProfileActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;
    @BindString(R.string.user_profile)
    String userProfile;
    @BindString(R.string.user_profile_kyc_open)
    String kycOpen;
    @BindString(R.string.user_profile_fatca_open)
    String fatcaOpen;
    @BindString(R.string.user_profile_risk_profile_open)
    String rpOpen;
    @BindString(R.string.user_profile_fatca_no_open)
    String fatcaNoOpen;
    @BindString(R.string.user_profile_risk_profile_no_open)
    String rpNoOpen;
    @BindDrawable(R.drawable.button_oval_white_outline)
    Drawable buttonOvalWhiteOutline;
    @BindDrawable(R.drawable.rounded_button)
    Drawable roundedButton;
    @BindInt(R.color.white)
    int white;
    @BindInt(R.color.colorPrimary)
    int colorPrimary;

    private UserProfilePresenter presenter;
    private static final String TAG = "UserProfileActivity";

    public static void startActivity(BaseActivity sourceActivity) {
        Intent intent = new Intent(sourceActivity, UserProfileActivity.class);
        sourceActivity.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.a_user_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }


        toolbar.setTitle("Profile");

        presenter = new UserProfilePresenter(this);
        UserInfoFragment.showFragment(UserProfileActivity.this);
    }

    /**
     * Event Bus
     */

    @Override
    public void busHandler(RxBusObject.RxBusKey busKey, Object busObject) {
        super.busHandler(busKey, busObject);
        switch (busKey) {
            case OPEN_FRAGMENT:
                String TAG = (String) busObject;
                if (TAG.equalsIgnoreCase(KycFragment.TAG)) {
                    //kyc();
                } else if (TAG.equalsIgnoreCase(FatcaFragment.TAG)) {
                    //fatca();
                } else if (TAG.equalsIgnoreCase(RiskProfileFragment.TAG)) {
                    //riskProfile();
                }
                break;
        }
    }


    public void showFailureDialog(String message) {
        new MaterialDialog.Builder(this)
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(getString(R.string.sorry).toUpperCase())
                .titleColor(Color.WHITE)
                .content(message)
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
    public void onBackPressed() {

        Snackbar snackbar = Snackbar.make(UserProfileActivity.this.findViewById(android.R.id.content), R.string.user_profile_exit, Snackbar.LENGTH_LONG).setAction(R.string.yes, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfileActivity.this, MainActivity.class));
                finish();
            }
        });
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_action);
        tv.setTypeface(null, Typeface.BOLD);
        snackbar.show();
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

}
