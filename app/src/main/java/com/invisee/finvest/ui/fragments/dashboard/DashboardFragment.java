package com.invisee.finvest.ui.fragments.dashboard;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.invisee.finvest.R;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.adapters.pager.DashboardPagerAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.eventBus.RxBusObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.OnClick;

/**
 * Created by fajarfatur on 1/22/16.
 */
public class DashboardFragment extends BaseFragment {

    public static final String TAG = DashboardFragment.class.getSimpleName();

    @Bind(R.id.bPortfolio)
    Button bPortfolio;
    @Bind(R.id.bPending)
    Button bPending;
    @Bind(R.id.bNewsFeed)
    Button bNewsFeed;
    @Bind(R.id.pager)
    ViewPager pager;
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

    private DashboardPresenter presenter;
    private DashboardPagerAdapter dashboardPagerAdapter;

    private SliderLayout mDemoSlider;

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.container, new DashboardFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_dashboard;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewPager();
        presenter = new DashboardPresenter(this);
        presenter.investmentList();
        mDemoSlider = (SliderLayout) getActivity().findViewById(R.id.slider);
        slider();
    }

    private void setupViewPager() {
        dashboardPagerAdapter = new DashboardPagerAdapter(getChildFragmentManager());
        pager.setAdapter(dashboardPagerAdapter);
    }

    /**
     * Event Bus
     */

    @Override
    public void busHandler(RxBusObject.RxBusKey busKey, Object busObject) {
        super.busHandler(busKey, busObject);
        switch (busKey) {
            case SELECTED_DASHBOARD_MENU:
                int page = (Integer) busObject;
                setSelectedButton(page);
                break;
        }

    }

    @OnClick(R.id.bPortfolio)
    void portfolio() {
        pager.setCurrentItem(0);
    }

    @OnClick(R.id.bPending)
    void pending() {
        pager.setCurrentItem(1);
    }

    @OnClick(R.id.bNewsFeed)
    void newsfeed() {
        pager.setCurrentItem(2);
    }

    private void setSelectedButton(int index) {
        switch (index) {
            case 0:
                setButtonOnOrOff(bPortfolio, true);
                setButtonOnOrOff(bPending, false);
                setButtonOnOrOff(bNewsFeed, false);
                break;
            case 1:
                setButtonOnOrOff(bPortfolio, true);
                setButtonOnOrOff(bPending, true);
                setButtonOnOrOff(bNewsFeed, false);
                break;
            case 2:
                setButtonOnOrOff(bPortfolio, true);
                setButtonOnOrOff(bPending, true);
                setButtonOnOrOff(bNewsFeed, true);
                break;
        }
    }

    private void setButtonOnOrOff(Button b, boolean on) {
        b.setBackground(on ? roundedButton : buttonOvalWhiteOutline);
        b.setTextColor(on ? colorPrimary : white);
    }

    private void slider() {

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Slider 1", R.drawable.slider1);
        file_maps.put("Slider 2", R.drawable.slider2);
        file_maps.put("Slider 3", R.drawable.slider3);
        file_maps.put("Slider 4", R.drawable.slider4);


        for (String name : file_maps.keySet()) {
            DefaultSliderView sliderView = new DefaultSliderView(getContext());

            // initialize a SliderLayout
            sliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            sliderView.bundle(new Bundle());
            sliderView.getBundle()
                    .putString("extra",name);

            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("extra", name);
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
}


