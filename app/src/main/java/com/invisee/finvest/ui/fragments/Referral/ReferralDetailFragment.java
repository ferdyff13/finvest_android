package com.invisee.finvest.ui.fragments.Referral;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.responses.ReferralResponse;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.OnClick;

/**
 * Created by pandu.abbiyuarsyah on 12/02/2018.
 */

public class ReferralDetailFragment extends BaseFragment {

    @Bind(R.id.imgReferral)
    ImageView imgReferral;
    @Bind(R.id.btnSubmit)
    LinearLayout btnSubmit;
    @Bind(R.id.tvReferralLink)
    TextView tvReferralLink;

    @Bind(R.id.lnProgressBar)
    LinearLayout lnProgressBar;
    @Bind(R.id.lnDismissBar)
    LinearLayout lnDismissBar;
    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;
    @Bind(R.id.lnConnectionError)
    LinearLayout lnConnectionError;

    @BindString(R.string.referral_link)
    String tvReferraLink;

    private static String TAG = ReferralDetailFragment.class.getName();
    private ReferralDetailPresenter presenter;
    private String img = "https://info.invisee.com/mobile/INVISEE-share-detail.png";

    public ReferralResponse referralResponse;

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, new ReferralDetailFragment(), TAG);
            fragmentTransaction.commit();
        }
    }


    @Override
    protected int getLayout() {
        return R.layout.f_referral_detail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ReferralDetailPresenter(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getCustomerReferral();
    }

    public void loadImage(){
        Picasso.with(getActivity()).load(img).into(imgReferral);
    }

    public void startShare(String referralLink, String referralCode){
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "INVISEE");
            String sAux = String.format(tvReferraLink, referralLink, referralCode);
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }

    @OnClick(R.id.btnSubmit)
    void btnSubmit(){
        startShare(referralResponse.getData().getReferralLink(), referralResponse.getData().getReferralCode());
    }

    public void showProgressBar(){
        pbLoading.setVisibility(View.VISIBLE);
        lnConnectionError.setVisibility(View.GONE);
        lnProgressBar.setVisibility(View.VISIBLE);
        lnDismissBar.setVisibility(View.GONE);
    }

    public void dismissProgressBar(){
        lnProgressBar.setVisibility(View.GONE);
        lnDismissBar.setVisibility(View.VISIBLE);
    }

    public void connectionError() {
        lnProgressBar.setVisibility(View.VISIBLE);
        lnDismissBar.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
        lnConnectionError.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tvTryAgain)
    void retryConnection() {
        presenter.getCustomerReferral();
    }
}
