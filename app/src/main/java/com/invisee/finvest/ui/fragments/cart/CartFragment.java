package com.invisee.finvest.ui.fragments.cart;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.beans.CartList;
import com.invisee.finvest.data.api.beans.Fee;
import com.invisee.finvest.data.api.beans.FundAllocation;
import com.invisee.finvest.data.api.responses.CartListResponse;
import com.invisee.finvest.data.api.responses.FundAllocationResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.CartActivity;
import com.invisee.finvest.ui.activities.CheckoutActivity;
import com.invisee.finvest.ui.adapters.rv.CartListAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by fajarfatur on 1/22/16.
 */

@RuntimePermissions
public class CartFragment extends BaseFragment {

    public static final String TAG = CartFragment.class.getSimpleName();
    private final static String SUBSCRIBE = "SUBCR";
    private final static String TOPUP = "TOPUP";
    private final static String VERIFIED = "VER";

    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.checkbox)
    CheckBox checkbox;
    @Bind(R.id.bCheckout)
    Button bCheckout;
    @Bind(R.id.lnProgressBar)
    LinearLayout lnProgressBar;
    @Bind(R.id.lnDismissBar)
    RelativeLayout lnDismissBar;
    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;
    @Bind(R.id.lnConnectionError)
    LinearLayout lnConnectionError;

    @State
    public FundAllocationResponse response;

    public List<CartListResponse> cartList;
    private CartListAdapter adapter;

    CartPresenter presenter;

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            fragmentTransaction.replace(R.id.container, new CartFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_list_cart;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CartPresenter(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.cartList();
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    public void loadList() {
        adapter = new CartListAdapter(presenter, cartList, response, getActivity());
        rv.setAdapter(adapter);
    }

    public void loadRule() {
        for (int i = 0; i < cartList.size(); i++) {
            Boolean isLastItem = false;
            if (i == cartList.size() - 1) {
                isLastItem = true;
            }
            presenter.subscriptionFee(cartList.get(i).getPackage(), isLastItem, i);
        }
    }

    public void setFee(int index, List<Fee> feeList) {

        CartListResponse r = cartList.get(index);
        r.setFeeList(new ArrayList<Fee>());
        r.setFeeList(feeList);

        cartList.set(index, r);
    }

    public void showDialog(String message) {
        new MaterialDialog.Builder(getActivity())
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(getString(R.string.failed).toUpperCase())
                .titleColor(Color.WHITE)
                .content(message)
                .contentColor(Color.WHITE)
                .positiveText(R.string.ok)
                .positiveColor(Color.WHITE)
                .cancelable(false)
                .show();
    }

    public Boolean isDataValid() {

        Boolean isValid = true;

        for (int i = 0; i < adapter.getList().size(); i++) {

            CartListResponse item = adapter.getList().get(i);
            String transactionAmount = item.getTransactionAmount();
            //Double amount = Double.parseDouble(transactionAmount); old

            //new
            Double amount = null;
            if(transactionAmount.equalsIgnoreCase("") || transactionAmount.equalsIgnoreCase("0")){
                showDialog(getString(R.string.cart_error_amount_null));
                isValid = false;
                break;
            }else{
                amount = Double.parseDouble(transactionAmount);
            }

            if(item.getTransactionType().getTrxCode().equalsIgnoreCase(SUBSCRIBE)){
                if(amount < item.getFundPackages().getMinSubscriptionAmount()){
                    isValid = false;
                    showDialog(getString(R.string.cart_error_minimum_amount));
                    break;
                }
            }else if(item.getTransactionType().getTrxCode().equalsIgnoreCase(TOPUP)) {
                if(amount < item.getFundPackages().getMinTopupAmount()){
                    isValid = false;
                    showDialog(getString(R.string.cart_error_minimum_amount));
                    break;
                }
            }
        }
        return isValid;
    }

    @OnClick(R.id.bCheckout)
    public void onClickCheckout() {
        if (!checkbox.isChecked()) {
            showDialog(getString(R.string.cart_error_terms));
        } else if (!PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equalsIgnoreCase(VERIFIED)) {
            showDialog(getString(R.string.cart_error_user_not_verified));
        } else {
            if (isDataValid())
                CheckoutActivity.startActivity((CartActivity) getActivity(), new CartList(cartList));
        }
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void downloadProspectus(FundAllocation fundAllocData){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(InviseeService.IMAGE_DOWNLOAD_URL + fundAllocData.getProspectusKey().toString() + "&token=" + PrefHelper.getString(PrefKey.TOKEN)));
        request.setDescription("A download package with some files");
        request.setTitle("Prospektus "+fundAllocData.getProductName());
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Prospektus "+fundAllocData.getProductName() +".pdf");
        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    public void download(FundAllocation fundAllocData){
        CartFragmentPermissionsDispatcher.downloadProspectusWithCheck(CartFragment.this, fundAllocData);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CartFragmentPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
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
        presenter.cartList();
    }

}
