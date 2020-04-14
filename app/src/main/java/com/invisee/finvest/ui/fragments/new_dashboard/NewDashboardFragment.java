package com.invisee.finvest.ui.fragments.new_dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.beans.PromoResponse;
import com.invisee.finvest.data.api.beans.Slider;
import com.invisee.finvest.data.api.beans.WalletBalance;
import com.invisee.finvest.data.api.responses.PortfolioInvestmentListResponse;
import com.invisee.finvest.data.api.responses.PortfolioInvestmentSummaryResponse;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.DashboardActivity;
import com.invisee.finvest.ui.activities.ListOfCatalogueActivity;
import com.invisee.finvest.ui.activities.ListPortfolioActivity;
import com.invisee.finvest.ui.activities.PendingOrderActivity;
import com.invisee.finvest.ui.activities.PromoActivity;
import com.invisee.finvest.ui.activities.ReferralActivity;
import com.invisee.finvest.ui.activities.ReminderListActivity;
import com.invisee.finvest.ui.activities.TransactiontActivity;
import com.invisee.finvest.ui.activities.UserProfileActivity;
import com.invisee.finvest.ui.activities.WalletActivity;
import com.invisee.finvest.ui.activities.YouTubePlayerActivity;
import com.invisee.finvest.ui.adapters.gridview.DashboardGridViewAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.AmountFormatter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.State;

public class NewDashboardFragment extends BaseFragment {

    public static final String TAG = NewDashboardFragment.class.getSimpleName();

    @BindDrawable(R.drawable.button_oval_white_outline)
    Drawable buttonOvalWhiteOutline;
    @BindDrawable(R.drawable.rounded_button)
    Drawable roundedButton;
    @BindString(R.string.menu_dashboard)
    String dashboard;
    @BindInt(R.color.white)
    int white;
    @BindInt(R.color.colorPrimary)
    int colorPrimary;
    @Bind(R.id.textViseepay)
    TextView txtViseepay;
    @Bind(R.id.textAmmount)
    TextView txtAmount;
    @BindString(R.string.user_profile_suggestion)
    String userProfileSuggestion;
    @Bind(R.id.lnProgressBar)
    LinearLayout lnProgressBar;
    @Bind(R.id.lnDismissBar)
    RelativeLayout lnDismissBar;
    @Bind(R.id.lnDashboard)
    LinearLayout lnDashboard;
    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;
    @Bind(R.id.lnConnectionError)
    LinearLayout lnConnectionError;
//    @Bind(R.id.imgBanneerReferral)
//    ImageView imgBanneerReferral;

    @State
    PortfolioInvestmentListResponse invesmentAmmount;
    @State
    PortfolioInvestmentSummaryResponse investmentSummary;
    @State
    public WalletBalance balance;
    private boolean completeness;

    public List<PromoResponse> listPromo;
    private NewDashboardPresenter presenter;
    private SliderLayout mDemoSlider;
    private DashboardGridViewAdapter mAdapter;
    private ArrayList<String> listImage;
    private ArrayList<Integer> listtext;
    private GridView gridView;
    public List<Slider> listlider;
//    private String url_referral = "https://info.invisee.com/mobile/INVISEE-share-banner.png";


    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            fragmentTransaction.replace(R.id.container, new NewDashboardFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_new_dashboard;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Dashboard");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new NewDashboardPresenter(this);

        presenter.getSlider();
//        presenter.getInvestmentList();
        mDemoSlider = (SliderLayout) getActivity().findViewById(R.id.slider);
//        slider();


    }

    public void loadBanner(){
//        Picasso.with(getActivity()).load(url_referral).into(imgBanneerReferral);
    }

    void createGridView(final boolean fullprofile) {

        listImage = new ArrayList<String>();
        listImage.add(getString(R.string.menu_profile));
        listImage.add(getString(R.string.menu_catalogue));
        listImage.add(getString(R.string.menu_your_order));
        listImage.add(getString(R.string.menu_portfolio));
        listImage.add(getString(R.string.menu_reminder));
        listImage.add(getString(R.string.menu_transaction));
        listImage.add(getString(R.string.menu_promo));
//        listImage.add(getString(R.string.menu_referral));


        listtext = new ArrayList<Integer>();

        if (fullprofile) {
            completeness = true;
            listtext.add(R.drawable.ic_white_profile);
        } else {
            completeness = false;
            listtext.add(R.mipmap.ic_profile_warning);
        }
        listtext.add(R.drawable.ic_white_catalogue);
        listtext.add(R.drawable.ic_white_pending_order);
        listtext.add(R.drawable.ic_white_portfolio);
        listtext.add(R.drawable.ic_white_reminder);
        listtext.add(R.drawable.ic_white_transaction_history);
        listtext.add(R.drawable.ic_promo);
//        listtext.add(R.drawable.ic_referral);


        // prepared arraylist and passed it to the Adapter class
        mAdapter = new DashboardGridViewAdapter(getContext(), listImage, listtext);

        // Set custom adapter to gridview
        gridView = (GridView) getActivity().findViewById(R.id.dashboard_grid);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                    startActivity(intent);
                } else if (position == 1) {
                    ListOfCatalogueActivity.startActivity((BaseActivity) getActivity());
                } else if (position == 2) {
                    PendingOrderActivity.startActivity((BaseActivity) getActivity());
                } else if (position == 3) {
                    ListPortfolioActivity.startActivity((BaseActivity) getActivity());
                } else if (position == 4) {
                    ReminderListActivity.startActivity((BaseActivity) getActivity());
                } else if (position == 5) {
                    TransactiontActivity.startActivity((BaseActivity) getActivity());
                } else if (position == 6) {
                    PromoActivity.startActivity((BaseActivity) getActivity());
                }
//                else if (position == 7) {
//                    ReferralActivity.startActivity((BaseActivity) getActivity());
//                }
            }
        });
    }

    void loadInvestmentAmmount(double Amount) {
        txtAmount.setText(AmountFormatter.format(Amount));
    }

    public void setBalanceView(double Balance) {
        txtViseepay.setText(AmountFormatter.format(Balance));
    }

    public static Drawable GetImage(Context c, String ImageName) {
        return c.getResources().getDrawable(c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName()));
    }

//    private void slider() {
//
//        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
//        file_maps.put("Slider 1", R.drawable.slider1);
//        file_maps.put("Slider 2", R.drawable.slider2);
//        file_maps.put("Slider 3", R.drawable.slider3);
//        file_maps.put("Slider 4", R.drawable.slider4);
//
//
//        for (String name : file_maps.keySet()) {
//            DefaultSliderView sliderView = new DefaultSliderView(getContext());
//
//            // initialize a SliderLayout
//            sliderView
//                    .description(name)
//                    .image(file_maps.get(name))
//                    .setScaleType(BaseSliderView.ScaleType.Fit);
//            sliderView.bundle(new Bundle());
//            sliderView.getBundle()
//                    .putString("extra",name);
//
//            sliderView.bundle(new Bundle());
//            sliderView.getBundle().putString("extra", name);
//            mDemoSlider.addSlider(sliderView);
//        }
//        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
//        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
//
//        //AutoCycle additional code to make ImageSlider work inside fragment
//        mDemoSlider.stopAutoCycle();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mDemoSlider.startAutoCycle();
//            }
//        }, 4000);
//
//    }

    public void slider(List<Slider> slideShows) {

        final Map<String, String> file_maps = new HashMap<>(slideShows.size());

        for (Slider slideShowItem : slideShows)
        {
            String url = "";

            if (slideShowItem.getUrl().equals("null")) {
                url = UUID.randomUUID().toString();
            } else if (slideShowItem.getUrl().contains("promo")) {
               String a = slideShowItem.getUrl();
               String random = UUID.randomUUID().toString();
               String replaced = a.replace("invisee", random);
               url = replaced;
            } else {
                url = slideShowItem.getUrl();
            }

            file_maps.put(url, InviseeService.IMAGE_DOWNLOAD_URL + slideShowItem.getAndroidImage());

        }

//        Uri otherPath = Uri.parse("android.resource://com.indivara.invisee/drawable/slider_siapai_invisee");
//        String path = otherPath.toString();

        for (String name : file_maps.keySet()) {
            DefaultSliderView sliderView = new DefaultSliderView(getContext());
            // initialize a SliderLayout
            sliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            sliderView.bundle(new Bundle());
            sliderView.getBundle()
                    .putString("extra", name);

            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("extra", name);

            final String target = name;

            sliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {

                    if (target.trim().length() == 0)
                        return;
                    else if (!target.contains("/") && !target.equals("null") && !target.contains("-")) {
                        Intent intent = new Intent(getActivity(), YouTubePlayerActivity.class);
                        startActivity(intent);
                    } else if (target.contains("promo")) {
                        PromoActivity.startActivity((BaseActivity) getActivity());
                    }

                }
            });
            mDemoSlider.addSlider(sliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());

        //AutoCycle additional code to make ImageSlider work inside fragment
        mDemoSlider.stopAutoCycle();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDemoSlider.startAutoCycle();
            }
        }, 4000);
    }

    @OnClick(R.id.btn_investation_detail)
    void showInvestationDetail() {
        Intent intent = new Intent(getActivity(), DashboardActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_viseepay)
    void showViseepayDetail() {
        Intent intent = new Intent(getActivity(), WalletActivity.class);
        startActivity(intent);
    }

    public void showProgressBar() {
        pbLoading.setVisibility(View.VISIBLE);
        lnConnectionError.setVisibility(View.GONE);
        lnProgressBar.setVisibility(View.VISIBLE);
        lnDismissBar.setVisibility(View.GONE);
    }

    public void dismissProgressBar() {
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
        presenter.getSlider();
    }

//    @OnClick(R.id.imgBanneerReferral)
//    void share(){
//        ReferralActivity.startActivity((BaseActivity) getActivity());
//    }

}


