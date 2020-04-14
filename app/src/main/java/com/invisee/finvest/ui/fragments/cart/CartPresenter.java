package com.invisee.finvest.ui.fragments.cart;

import android.graphics.Color;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Fee;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.responses.CartListResponse;
import com.invisee.finvest.data.api.responses.FundAllocationResponse;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.MainActivity;
import com.invisee.finvest.ui.activities.PromoActivity;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class CartPresenter {

    private CartFragment fragment;

    public CartPresenter(CartFragment fragment){
        this.fragment = fragment;
    }

    public void cartList(){
        fragment.showProgressBar();
        fragment.getApi().getCartList(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<CartListResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.connectionError();
                        fragment.getActivity().finish();
                    }

                    @Override
                    public void onNext(List<CartListResponse> response) {
                        ((BaseActivity) fragment.getActivity()).setNotifCount(0);
                        if (response != null && response.size() > 0) {
                            ((BaseActivity) fragment.getActivity()).setNotifCount(response.size());
                            fragment.cartList = response;
                            fragment.loadRule();
                            fragment.dismissProgressBar();
                        } else {
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.infortmation).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content("Keranjang anda kosong")
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.back)
                                    .positiveColor(Color.WHITE)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            MainActivity.startActivity((BaseActivity) fragment.getActivity());
                                            fragment.getActivity().finish();
                                        }
                                    })
                                    .cancelable(false)
                                    .show();
                        }
                    }
                });
    }

    public void subscriptionFee(final Packages packages, final Boolean isLastItem, final int index) {

        fragment.getApi().subscriptionFee(packages.getId(), 1l, PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Fee>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(List<Fee> list) {

                        if (list.size() > 0) {
                            fragment.setFee(index, list);

                            if (isLastItem) {
                                fragment.loadList();
                                fragment.dismissProgressDialog();
                            }
                        }
                    }
                });

    }


    public void deleteCart(int id) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().deleteCart(Integer.toString(id), PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted(){

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        if (response.getCode() == 1) {
                            cartList();
                        } else {
                            Toast.makeText(fragment.getActivity(), response.getInfo(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void fundAllocationFromAdapter(Long id) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().fundAllocationInfo(id, PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<FundAllocationResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(FundAllocationResponse response) {
                        fragment.dismissProgressDialog();
                        if (response.getCode() == 1) {
                            fragment.download(response.getData().get(0));
                        }
                    }
                });

    }

    public void cartListToDownload(final int position) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().getCartList(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<CartListResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(List<CartListResponse> response) {

                        ((BaseActivity) fragment.getActivity()).setNotifCount(0);

                        if (response != null && response.size() > 0) {
                            ((BaseActivity) fragment.getActivity()).setNotifCount(response.size());
                            fragment.cartList = response;

                            fundAllocationFromAdapter(response.get(position).getPackages().getId());

//                            for (int i = 0; i < response.size(); i++) {
//                                if (i == id){
//                                    fundAllocationFromAdapter(response.get(i).getPackages().getId());
//                                }
//                            }

                            fragment.loadRule();
                        } else {
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.infortmation).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content("Keranjang anda kosong")
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.back)
                                    .positiveColor(Color.WHITE)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            fragment.getActivity().finish();
                                        }
                                    })
                                    .cancelable(false)
                                    .show();
                        }
                    }
                });
    }
}
