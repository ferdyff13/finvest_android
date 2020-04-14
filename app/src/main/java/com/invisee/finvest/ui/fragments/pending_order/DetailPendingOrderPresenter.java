package com.invisee.finvest.ui.fragments.pending_order;

import android.widget.Toast;

import com.invisee.finvest.data.api.requests.DetailPendingOrderRequest;
import com.invisee.finvest.data.api.requests.DocTransactionRequest;
import com.invisee.finvest.data.api.responses.DocTransactionResponse;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.api.responses.PendingOrderDetailResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MultipartBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by pandu.abbiyuarsyah on 30/03/2017.
 */

public class DetailPendingOrderPresenter {

    private DetailPendingOrderFragment fragment;

    public DetailPendingOrderPresenter(DetailPendingOrderFragment fragment) {
        this.fragment = fragment;
    }

    public void uploadTransaction(String request, final File file) {
        fragment.showProgressDialog(fragment.loading);

        okhttp3.RequestBody photo = okhttp3.RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("CusTrans01", file.getName(), photo);

        fragment.getApi().uploadTransaction(request, PrefHelper.getString(PrefKey.TOKEN), "CusTrans01", body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        fragment.dismissProgressDialog();
                        fragment.showUploadSuccessDialog(response.getInfo());
                        Picasso.with(fragment.getContext()).load(file).fit().centerCrop().into(fragment.imgOrder);
                    }
                });
    }

    DetailPendingOrderRequest constructDetailPendingOrderRequest() {
        DetailPendingOrderRequest request = new DetailPendingOrderRequest();
        request.setInvestmentNumber(fragment.transactionHistory.getInvestmentNumber());
        request.setOrderNumber(fragment.transactionHistory.getOrderNumber());
        request.setToken(PrefHelper.getString(PrefKey.TOKEN));

        return request;
    }


    public void getPendingOrderDetail(final DetailPendingOrderRequest request) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().getDetailPendingOrder(request.getInvestmentNumber(), request.getOrderNumber(), request.getToken())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PendingOrderDetailResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(PendingOrderDetailResponse response) {
                        fragment.dismissProgressDialog();
                        fragment.response = response;
                        fragment.loadValue();
                    }
                });
    }

    DocTransactionRequest constructDocTransactionRequest() {
        DocTransactionRequest request = new DocTransactionRequest();
        request.setOrderno(fragment.transactionHistory.getOrderNumber());
        request.setToken(PrefHelper.getString(PrefKey.TOKEN));

        return request;
    }

    public void getDocTransaction(final DocTransactionRequest request) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().getDocTransaction(request.getOrderno(), request.getToken())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<DocTransactionResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(DocTransactionResponse response) {

                        if (response.getCode() == 0) {
                            fragment.docTransactionResponse = response;
                            fragment.txvFileName.setText(response.getData().get(0).getFileName());
                            fragment.loadImage();
                            fragment.downloadImage();

                        } else {

                        }

                        fragment.dismissProgressDialog();

                    }
                });
    }





}
