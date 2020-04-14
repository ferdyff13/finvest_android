package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.User;
import com.invisee.finvest.data.api.requests.KycDataRequest;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.adapters.pager.KycPagerAdapterNew;
import com.invisee.finvest.util.eventBus.RxBusObject;
import com.invisee.finvest.util.ui.NonSwipeableViewPager;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;
import timber.log.Timber;

/**
 * Created by ulfah.ulmi on 07/04/2017.
 */

public class KycActivityNew extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    private static final String TAG = "KycActivityNew";
    public static final int TOTAL_PAGE = 5;

    @Bind(R.id.pager)
    NonSwipeableViewPager pager;
    @State
    int currentPage;
    @State
    KycDataRequest kycDataRequest;
    @Bind(R.id.tv_left)
    TextView tvLeft;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.rl_leftside)
    RelativeLayout rlLeft;
    @Bind(R.id.tv_completeness)
    TextView tvCompleteness;
    @Bind(R.id.pb_completeness)
    ProgressBar pbCompleteness;

    private long mLastClickTime = 0;
    private KycActivityPresenter presenter;
    private KycPagerAdapterNew kycPagerAdapter;
    private static int currentFatcaPage;

    @Override
    protected int getLayout() {
        return R.layout.a_kyc_new;
    }

    public static void startActivity(BaseActivity sourceActivity, int fromFatca){
        currentFatcaPage = fromFatca;
        Intent intent = new Intent(sourceActivity, KycActivityNew.class);
        sourceActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + "masuk 0");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        toolbar.setTitle("Kyc Profil");
        presenter = new KycActivityPresenter(this);
    }


    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume: " + "masuk 0");
        presenter.getKycLookupDataAndKycUserData();
    }

    @Override
    public void onBackPressed(){

        Snackbar snackbar = Snackbar.make(KycActivityNew.this.findViewById(android.R.id.content), R.string.user_profile_exit, Snackbar.LENGTH_LONG).setAction(R.string.yes, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KycActivityNew.this, UserProfileActivity.class));
                KycActivityNew.this.finish();
            }
        });

        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_action);
        tv.setTypeface(null, Typeface.BOLD);
        snackbar.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return true;
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

    void onKycDataLoaded() {
        currentPage = 0;

        setupViewPager();
        if (currentFatcaPage > 0) {
            currentPage = currentFatcaPage;
            pager.setCurrentItem(currentPage);
        }
        setTextNavigation(currentPage);
        presenter.getCompleteness();
    }

    private void setupViewPager() {
        kycPagerAdapter = new KycPagerAdapterNew(getSupportFragmentManager(), kycDataRequest, TOTAL_PAGE);
        pager.setAdapter(kycPagerAdapter);
        pager.setOffscreenPageLimit(5);
    }

    public void showErrorOnRetrievingKycDataDialog(){
        new MaterialDialog.Builder(this)
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(getString(R.string.sorry).toUpperCase())
                .titleColor(Color.WHITE)
                .content(R.string.kyc_error)
                .contentColor(Color.WHITE)
                .positiveText(R.string.try_again)
                .positiveColor(Color.WHITE)
                .negativeText(R.string.menu_goto)
                .negativeColor(cGray)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        presenter.getKycLookupDataAndKycUserData();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        MainActivity.startActivity(KycActivityNew.this);
                    }
                })
                .show();
    }

    /**
     * Event Bus
     */

    @Override
    public void busHandler(RxBusObject.RxBusKey busKey, Object busObject) {
        super.busHandler(busKey, busObject);
        switch (busKey) {
            case SUBMIT_KYC_DATA:
                kycDataRequest = (KycDataRequest) busObject;
                System.out.println("OUT >> 2 " + kycDataRequest.getBirthPlace());
                presenter.saveAndSubmitKycDataRequest(kycDataRequest);
                break;
            case KYC_PREVIOUS_FORM:
                toPreviousForm();
                break;
                //to kembali ke page 5 ketika upload foto
            case BACKT_TO_SETTLEMENT:
                KycActivityNew.startActivity(this, 4);
                Log.d(TAG, "busHandler: " + "masuk back load");
                break;
            case SAVE_KYC_DATA:
                kycDataRequest = (KycDataRequest) busObject;
                presenter.saveKycDataRequest(kycDataRequest);
                break;
            case NEXT_FORM:
                toNextForm();
                break;

        }
    }


    @OnClick(R.id.iv_left)
    void previousForm() {
        getBus().send(new RxBusObject(RxBusObject.RxBusKey.KYC_PREVIOUS_FORM, null));
    }

    @OnClick(R.id.iv_right)
    void next() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return; // prevent multiple click
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        getBus().send(new RxBusObject(RxBusObject.RxBusKey.GET_NEW_INPUTTED_KYC_DATA, null));
    }

    /**
     * next to another kyc form as long as didn't reach the last kyc form,
     * when reach the last kyc form, next to fatca form
     */
    public void toNextForm() {
        if (currentPage < TOTAL_PAGE - 1) {

            if (PrefHelper.getBoolean(PrefKey.HOME_LEGAL)) {
                currentPage++;
                PrefHelper.setBoolean(PrefKey.HOME_LEGAL, false);
            }

            currentPage++;
            Timber.i("toNextForm %d", currentPage);
            setTextNavigation(currentPage);
            pager.setCurrentItem(currentPage);
            presenter.getCompleteness();
        } else if (currentPage == TOTAL_PAGE - 1) {
            FatcaActivity.startActivity(this);
        }
    }

    public void toNextFormVer() {
        if (currentPage < TOTAL_PAGE - 1) {

            if (PrefHelper.getBoolean(PrefKey.HOME_LEGAL)) {
                currentPage++;
                PrefHelper.setBoolean(PrefKey.HOME_LEGAL, false);
            }

            currentPage++;
            Timber.i("toNextForm %d", currentPage);
            setTextNavigation(currentPage);
            pager.setCurrentItem(currentPage);
            presenter.getCompleteness();
        } else if (currentPage == TOTAL_PAGE - 1) {
            FatcaActivity.startActivity(this);
        }
    }

    public void toPreviousForm() {
        if (currentPage > 0) {
            currentPage--;
            Timber.i("toPreviousForm %d", currentPage);
            setTextNavigation(currentPage);
            pager.setCurrentItem(currentPage);
        }
    }

    private void setTextNavigation(int current) {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        switch (current) {
            case 0:
                rlLeft.setVisibility(View.INVISIBLE);
                tvRight.setText(R.string.user_profile_fatca_addr_resident);
                toolbar.setTitle(R.string.user_profile_fatca_private_info);
                break;
            case 1:
                rlLeft.setVisibility(View.VISIBLE);
                tvLeft.setText(R.string.user_profile_fatca_private_info);
                tvRight.setText(R.string.user_profile_fatca_addr_postal);
                toolbar.setTitle(R.string.user_profile_fatca_addr_resident);
                break;
            case 2:
                tvLeft.setText(R.string.user_profile_fatca_addr_resident);
                tvRight.setText(R.string.user_profile_kyc_profile_investment);
                toolbar.setTitle(R.string.user_profile_fatca_addr_postal);
                break;
            case 3:
                tvLeft.setText(R.string.user_profile_fatca_addr_postal);
                tvRight.setText(R.string.user_profile_kyc_cust_account);
                toolbar.setTitle(R.string.user_profile_kyc_profile_investment);
                break;
            case 4:
                rlLeft.setVisibility(View.VISIBLE);
                tvLeft.setText(R.string.user_profile_kyc_profile_investment);
                tvRight.setText(R.string.user_profile_fatca);
                toolbar.setTitle(R.string.user_profile_kyc_cust_account);
                break;
            case 5:
                toolbar.setTitle(R.string.user_profile_fatca);
                break;
        }
    }

    void showDialogFailedSubmit(String info) {
        new MaterialDialog.Builder(this)
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(getString(R.string.infortmation).toUpperCase())
                .titleColor(Color.WHITE)
                .content(info)
                .contentColor(Color.WHITE)
                .positiveText(R.string.ok)
                .positiveColor(Color.WHITE)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        PrefHelper.setBoolean(PrefKey.HOME_LEGAL, false);
                        UserProfileActivity.startActivity(KycActivityNew.this);
                    }
                })
                .show();
    }

}
