package com.invisee.finvest.ui.fragments.promo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.PromoDetail;
import com.invisee.finvest.data.api.beans.PromoResponse;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.PromoActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by pandu.abbiyuarsyah on 18/05/2017.
 */

public class DetailPromoFragment extends BaseFragment {

    public static final String TAG = DetailPromoFragment.class.getSimpleName();
    private static final String PROMO = "promo";

    @Bind(R.id.tvTitle)
    TextView tvTitle;
//    @Bind(R.id.tvDesc)
//    TextView tvDesc;
//    @Bind(R.id.img1)
//    ImageView img1;
//    @Bind(R.id.img2)
//    LinearLayout img2;
//    @Bind(R.id.img3)
//    LinearLayout img3;
    @Bind(R.id.wv)
    WebView wv;
    @Bind(R.id.lnProgressBar)
    LinearLayout lnProgressBar;
    @Bind(R.id.lnDismissBar)
    RelativeLayout lnDismissBar;
    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;
    @Bind(R.id.lnConnectionError)
    LinearLayout lnConnectionError;

    @State
    PromoResponse response;

    public PromoDetail promoDetail;
    public DetailPromoPresenter presenter;

    public static void showFragment(BaseActivity sourceActivity, PromoResponse response) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, getFragment(response), TAG);
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.commit();
        }
    }

    public static Fragment getFragment(PromoResponse response) {
        Fragment f = new DetailPromoFragment();

        Bundle extras = new Bundle();
        extras.putSerializable(PROMO, response);

        f.setArguments(extras);
        return f;
    }

    @Override
    protected int getLayout() {
        return R.layout.f_detail_promo;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DetailPromoPresenter(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (response == null) {
            Bundle extras = getArguments();
            if (extras != null && extras.containsKey(PROMO)) {
                response = (PromoResponse) extras.getSerializable(PROMO);
            }
        }

        if (response != null) {
            presenter.getDetailPromo();
        }
    }

    public void loadDetail()  {

        wv.setInitialScale(1);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("https://info.invisee.com/mobile/promo-INVISEE-"+response.getCode()+".jpg");

//        if (response.getCode().contains("IGI100")) {
//            img1.setVisibility(View.VISIBLE);
//            img2.setVisibility(View.GONE);
//            img3.setVisibility(View.GONE);
//            tvDesc.setVisibility(View.GONE);
//        } else if (response.getCode().contains("PNBP")) {
//            img1.setVisibility(View.GONE);
//            img2.setVisibility(View.VISIBLE);
//            img3.setVisibility(View.GONE);
//            tvDesc.setVisibility(View.GONE);
//        } else if (response.getCode().contains("PKAT")){
//            img1.setVisibility(View.GONE);
//            img2.setVisibility(View.GONE);
//            img3.setVisibility(View.VISIBLE);
//            tvDesc.setVisibility(View.GONE);
//        }  else {
//            img1.setVisibility(View.GONE);
//            img2.setVisibility(View.GONE);
//            img3.setVisibility(View.GONE);
//            tvDesc.setVisibility(View.VISIBLE);
//        }

        tvTitle.setText(promoDetail.getTitle());

        HtmlTextView htmlTextView = (HtmlTextView) getActivity().findViewById(R.id.tvDesc);
        htmlTextView.setHtml(promoDetail.getDescription(),
                new HtmlResImageGetter(htmlTextView));
    }

    @OnClick(R.id.btnSubmit)
    void submitJoinPromo(){

        System.out.println("==================> + c" + response.getCode() + "title====>" + response.getTitle() + "introtext======>" + response.getIntrotext() + "=====>" + response.getJoined());
        if (response.getCode().contains("PNBP")) {
            new MaterialDialog.Builder(getActivity())
                    .iconRes(R.mipmap.ic_launcher_finvest)
                    .backgroundColor(cDanger)
                    .title(getString(R.string.infortmation).toUpperCase())
                    .titleColor(Color.WHITE)
                    .content(R.string.desc_bangkok_pattaya)
                    .contentColor(Color.WHITE)
                    .positiveText(R.string.ok)
                    .positiveColor(Color.WHITE)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            PromoActivity.startActivity((BaseActivity) getActivity());
                        }
                    })
                    .cancelable(false)
                    .show();
            System.out.println("================> PNBP");
        }else if(response.getCode().contains("TURKI")){ //tunggu dari invisee
            new MaterialDialog.Builder(getActivity())
                    .iconRes(R.mipmap.ic_launcher_finvest)
                    .backgroundColor(cDanger)
                    .title(getString(R.string.infortmation).toUpperCase())
                    .titleColor(Color.WHITE)
                    .content(R.string.desc_bangkok_pattaya)
                    .contentColor(Color.WHITE)
                    .positiveText(R.string.ok)
                    .positiveColor(Color.WHITE)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            PromoActivity.startActivity((BaseActivity) getActivity());
                        }
                    })
                    .cancelable(false)
                    .show();
        }else if(response.getCode().contains("KLSGH")){
            new MaterialDialog.Builder(getActivity())
                    .iconRes(R.mipmap.ic_launcher_finvest)
                    .backgroundColor(cDanger)
                    .title(getString(R.string.infortmation).toUpperCase())
                    .titleColor(Color.WHITE)
                    .content(R.string.desc_bangkok_pattaya)
                    .contentColor(Color.WHITE)
                    .positiveText(R.string.ok)
                    .positiveColor(Color.WHITE)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            PromoActivity.startActivity((BaseActivity) getActivity());
                        }
                    })
                    .cancelable(false)
                    .show();
        }else if(response.getTitle().toLowerCase().contains("paket")){
            new MaterialDialog.Builder(getActivity())
                    .iconRes(R.mipmap.ic_launcher_finvest)
                    .backgroundColor(cDanger)
                    .title(getString(R.string.infortmation).toUpperCase())
                    .titleColor(Color.WHITE)
                    .content(R.string.desc_bangkok_pattaya)
                    .contentColor(Color.WHITE)
                    .positiveText(R.string.ok)
                    .positiveColor(Color.WHITE)
                    .onPositive(new MaterialDialog.SingleButtonCallback(){
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which){
                            PromoActivity.startActivity((BaseActivity) getActivity());
                        }
                    })
                    .cancelable(false)
                    .show();
        }else{
            System.out.println("================> OTHER");
            presenter.joinPromo();
        }

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
        presenter.getDetailPromo();
    }

}
