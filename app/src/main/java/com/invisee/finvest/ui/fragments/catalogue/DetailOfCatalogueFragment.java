package com.invisee.finvest.ui.fragments.catalogue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.ProductList;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.adapters.pager.DetailOfCataloguePagerAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.DateUtil;
import com.invisee.finvest.util.eventBus.RxBusObject;
import com.invisee.finvest.util.ui.WrapContentHeightViewPager;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by fajarfatur on 1/22/16.
 */
public class DetailOfCatalogueFragment extends BaseFragment {

    @Bind(R.id.ivProduct)
    ImageView ivProduct;
    @Bind(R.id.tvProductName)
    TextView tvProductName;
    @Bind(R.id.tvProductDesc)
    TextView tvProductDesc;
    @Bind(R.id.txvCurrency)
    TextView txvCurrency;
    @Bind(R.id.txvRiskCategory)
    TextView txvRiskCategory;
    @Bind(R.id.pager)
    WrapContentHeightViewPager pager;
    @Bind(R.id.txvTitle)
    TextView txvTitle;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.tvDisclaimerDesc)
    TextView tvDisclaimer;
    @Bind(R.id.tvProvision)
    TextView tvProvision;
    @Bind(R.id.cbAgree)
    CheckBox cbAgree;
    @Bind(R.id.bSubscribe)
    Button btnSubscribe;
    @Bind(R.id.llDesc)
    LinearLayout llDesc;
    @Bind(R.id.ivArrow)
    ImageView ivArrow;
    @Bind(R.id.lnProgressBar)
    LinearLayout lnProgressBar;
    @Bind(R.id.lnDismissBar)
    RelativeLayout lnDismissBar;
    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;
    @Bind(R.id.lnConnectionError)
    LinearLayout lnConnectionError;
    @BindString(R.string.catalogue_diclaimer)
    String catalogueDisclaimer;
    @BindString(R.string.catalogue_provision)
    String provison;
    @BindString(R.string.fund_alloc)
    String fundAlloc;
    @BindString(R.string.package_performance)
    String packagePerformance;
    @BindString(R.string.business_rule)
    String businessRule;
    @State
    Packages packages;
    @State
    ProductList product;
    @State
    int pageNumber = 0;

    private DetailOfCataloguePresenter presenter;
    private DetailOfCataloguePagerAdapter pagerAdapter;
    private static final String PACKAGES = "packages";

    public static final String TAG = DetailOfCatalogueFragment.class.getSimpleName();

    public static void showFragment(BaseActivity sourceActivity, ProductList packages) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            fragmentTransaction.replace(R.id.container, getFragment(packages), TAG);
            fragmentTransaction.commit();
        }
    }

    public static Fragment getFragment(ProductList packages) {
        Fragment f = new DetailOfCatalogueFragment();
        Bundle extras = new Bundle();
        extras.putSerializable(PACKAGES, packages);
        f.setArguments(extras);
        return f;
    }

    @Override
    protected int getLayout() {
        return R.layout.f_detail_of_catalogue;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        presenter = new DetailOfCataloguePresenter(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (product == null) {
            Bundle extras = getArguments();
            if (extras != null && extras.containsKey(PACKAGES)) {
                product = (ProductList) extras.getSerializable(PACKAGES);
            }
        }

        presenter.packageDetail(product);

        cbAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                               @Override
                                               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                   if (isChecked) {
                                                       btnSubscribe.setEnabled(true);
                                                   } else {
                                                       btnSubscribe.setEnabled(false);
                                                   }
                                               }
                                           }
        );
    }

    @Override
    public void busHandler(RxBusObject.RxBusKey busKey, Object busObject) {
        super.busHandler(busKey, busObject);
        switch (busKey) {
            case SCROLL_TO_BOTTOM:
        }
    }

    public void setupViewPager() {
        pagerAdapter = new DetailOfCataloguePagerAdapter(this, getChildFragmentManager(), packages);
        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new WrapContentHeightViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Do nothing
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        txvTitle.setText(fundAlloc);
                        break;
                    case 1:
                        txvTitle.setText(packagePerformance);
                        break;
                    case 2:
                        txvTitle.setText(businessRule);
                        break;
                    default:
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Do nothing
            }
        });
        setTabTitle();
    }

    public void setPackagesDataToView() {
        DecimalFormat df = new DecimalFormat("0.000");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault());
        Date dateParse = null;
        try {
            dateParse = sdf.parse(packages.getEffectiveDate());
            SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.INVISEE_RETURN_FORMAT2, Locale.getDefault());
            String convertedDate = formatter.format(dateParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ivProduct.setScaleType(ImageView.ScaleType.FIT_XY);
        ivProduct.setAdjustViewBounds(true);
        Picasso.with(getActivity()).load(InviseeService.IMAGE_DOWNLOAD_URL + packages.getPackageImage()).into(ivProduct);
        tvProductName.setText(packages.getFundPackageName());

        HtmlTextView htmlTextView = (HtmlTextView) getActivity().findViewById(R.id.tvProductDesc);
        htmlTextView.setHtml(packages.getPackageDesc(),
                new HtmlResImageGetter(htmlTextView));

        txvCurrency.setText(packages.getCurrency());
        txvRiskCategory.setText(packages.getRiskProfile());
        tvDisclaimer.setText(catalogueDisclaimer);
        tvProvision.setText(provison);
    }

    void setTabTitle() {
        switch (pageNumber) {
            case 0:
                txvTitle.setText(fundAlloc);
                break;
            case 1:
                txvTitle.setText(packagePerformance);
                break;
            case 2:
                txvTitle.setText(businessRule);
                break;
            default:
        }
    }

    @OnClick(R.id.bSubscribe)
    void onClickSubscribe() {
        presenter.getMaxScore(product);
    }

    @OnClick(R.id.ivLeft)
    void onClickLeft() {
        if (pageNumber > 0) {
            pageNumber--;
            pager.setCurrentItem(pageNumber);
            setTabTitle();
        }
    }

    @OnClick(R.id.ivRight)
    void onClickRight() {
        if (pageNumber < pagerAdapter.getCount()) {
            pageNumber++;
            pager.setCurrentItem(pageNumber);
        }
    }

    @OnClick(R.id.btnFull)
    void showFullDesc() {
        if (llDesc.getVisibility() == View.GONE) {
            tvProductDesc.setMaxLines(Integer.MAX_VALUE);
            tvProductDesc.setEllipsize(null);
            llDesc.setVisibility(View.VISIBLE);
            ivArrow.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp);
        } else {
            tvProductDesc.setMaxLines(5);
            tvProductDesc.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            llDesc.setVisibility(View.GONE);
            ivArrow.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
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
        presenter.packageDetail(product);
    }
}
