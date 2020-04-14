package com.invisee.finvest.ui.fragments.catalogue;

import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.responses.CartListResponse;
import com.invisee.finvest.data.api.responses.ProductResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class ListOfCataloguePresenter {

    private ListOfCatalogueFragment fragment;

    public ListOfCataloguePresenter(ListOfCatalogueFragment fragment) {
        this.fragment = fragment;
    }

    void productList(){
        fragment.showProgressBar();
        fragment.getApi().getProductList(PrefHelper.getString(PrefKey.TOKEN))  //add new token for ITB
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ProductResponse>() {
                    @Override
                    public void onCompleted(){

                    }

                    @Override
                    public void onError(Throwable e){
                        fragment.connectionError();
                    }

                    @Override
                    public void onNext(ProductResponse response) {
                        if (response != null) {
                            fragment.response = response;
                            fragment.productsList = response.getData();
                            fragment.list = response.getData().get(fragment.index).getPackageList();
                            fragment.loadList();
                            fragment.setSpinner();
                            fragment.dismissProgressBar();
                        } else {
                            fragment.dismissProgressBar();
                        }
                    }
                });

    }

    void productListSelectedItems() {
        fragment.showProgressBar();
        fragment.getApi().getProductList(PrefHelper.getString(PrefKey.TOKEN)) //add new token for ITB
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ProductResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.connectionError();
                    }

                    @Override
                    public void onNext(ProductResponse response) {
                        if (response != null) {
                            fragment.response = response;
                            fragment.productsList = response.getData();
                            fragment.list = response.getData().get(fragment.index).getPackageList();
                            fragment.temporaaryIndex = fragment.index;
                            fragment.loadList();
                            fragment.dismissProgressBar();
                        } else {
                            fragment.dismissProgressBar();
                        }
                    }
                });

    }

    public void cartList() {
        fragment.getApi().getCartList(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<CartListResponse>>() {
                    @Override
                    public void onCompleted(){

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.connectionError();
                    }

                    @Override
                    public void onNext(List<CartListResponse> response) {
                        ((BaseActivity) fragment.getActivity()).setNotifCount(0);

                        if (response != null && response.size() > 0) {
                            ((BaseActivity) fragment.getActivity()).setNotifCount(response.size());
                            fragment.cartList = response;

                            for (int i = 0; i < response.size(); i++) {

                            }

                        } else {

                        }
                    }
                });

    }

}
