package com.invisee.finvest.ui.fragments.userProfile;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.TempData;
import com.invisee.finvest.data.api.requests.KycDataRequest;
import com.invisee.finvest.data.api.responses.SettlementInfoResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.MainActivity;
import com.invisee.finvest.ui.adapters.pager.KycPagerAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.eventBus.RxBusObject;
import com.invisee.finvest.util.ui.NonSwipeableViewPager;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;
import timber.log.Timber;

/**
 * Created by fajarfatur on 1/13/16.
 */

public class KycFragment extends BaseFragment {

    public static final String TAG = KycFragment.class.getSimpleName();
    public static final int TOTAL_PAGE = 5;
    public static final int CAPTURE_FILE = 4298;

    @Bind(R.id.pager)
    NonSwipeableViewPager pager;
    @State
    int currentPage;
    @State
    KycDataRequest kycDataRequest;
    @State
    SettlementInfoResponse settlementInfoResponse;
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
    private KycPresenter presenter;
    private KycPagerAdapter kycPagerAdapter;
    private static int currentFatcaPage;
    private TempData tempData = new TempData();

    public static void showFragment(BaseActivity sourceActivity, int fromFatca) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {

            currentFatcaPage = fromFatca;

            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, new KycFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_kyc;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new KycPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getKycLookupDataAndKycUserData();
        //presenter.loadSettlement();
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
        kycPagerAdapter = new KycPagerAdapter(getChildFragmentManager(), kycDataRequest, TOTAL_PAGE);
        pager.setAdapter(kycPagerAdapter);
//        pager.setOffscreenPageLimit(5);
    }

    public void showErrorOnRetrievingKycDataDialog() {
        new MaterialDialog.Builder(getActivity())
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(getString(R.string.sorry).toUpperCase())
                .titleColor(Color.WHITE)
                .content(R.string.kyc_error)
                .contentColor(Color.WHITE)
                .positiveText(R.string.try_again)
                .positiveColor(Color.WHITE)
                .negativeText(R.string.menu_goto)
                .negativeColor(cPrimary)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        presenter.getKycLookupDataAndKycUserData();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        MainActivity.startActivity((BaseActivity) getActivity());
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
/*            case SUBMIT_KYC_DATA:
                kycDataRequest = (KycDataRequest) busObject;
                System.out.println("OUT >> 2 "+ kycDataRequest.getBirthPlace());
                presenter.saveAndSubmitKycDataRequest(kycDataRequest);
                break;*/
            case KYC_PREVIOUS_FORM:
                toPreviousForm();
                break;
            case BACKT_TO_SETTLEMENT:
//                KycSettlementFragment.initiateFragment(kycDataRequest);
                KycSettlementFragment.showFragment((BaseActivity) getActivity(), kycDataRequest);
//                KycFragment.showFragment((BaseActivity) getActivity(), 4);
                Log.d(TAG, "busHandler: "+"masuk back load");
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

            if(PrefHelper.getBoolean(PrefKey.HOME_LEGAL)){currentPage++; PrefHelper.setBoolean(PrefKey.HOME_LEGAL,false);}

            currentPage++;
            Timber.i("toNextForm %d", currentPage);
            setTextNavigation(currentPage);
            pager.setCurrentItem(currentPage);
            presenter.getCompleteness();
        } else if (currentPage == TOTAL_PAGE - 1) {
      /*    FatcaFragment.showFragment((BaseActivity) getActivity());*/
       /*   getBus().send(new RxBusObject(RxBusObject.RxBusKey.OPEN_FRAGMENT, FatcaFragment.TAG));*/
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
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case CAPTURE_FILE:
//                    KycSettlementFragment fragment = (KycSettlementFragment) getFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + pager.getCurrentItem());
//                    fragment.onActivityResult(requestCode, resultCode, data);
//                    Log.d(TAG, "onActivityResult: " + "masuk");
//                break;
//        }
//        Log.d(TAG, "onActivityResult: " + "masuk");
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    //to construct for SurveyTakePicture
//    public static void startActivityForResult(KycSettlementFragment fragment) {
//        Intent homeIntent = new Intent(fragment.getActivity(), UserProfileActivity.class);
//        fragment.startActivityForResult(homeIntent, CAPTURE_FILE);
//    }

}
