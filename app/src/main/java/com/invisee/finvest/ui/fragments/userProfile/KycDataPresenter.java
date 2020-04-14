package com.invisee.finvest.ui.fragments.userProfile;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.requests.DocumentIdRequest;
import com.invisee.finvest.data.api.responses.CustomerDocumentResponse;
import com.invisee.finvest.data.api.responses.CustomerDocumentSelfieResponse;
import com.invisee.finvest.data.api.responses.CustomerDocumentSignatureResponse;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.util.eventBus.RxBusObject;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MultipartBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by pandu.abbiyuarsyah on 22/03/2017.
 */

public class KycDataPresenter {

    private KycDataFragment fragment;

    public KycDataPresenter(KycDataFragment fragment) {
        this.fragment = fragment;
    }

    DocumentIdRequest constructUpload() {
        DocumentIdRequest request = new DocumentIdRequest();
        request.setType("DocTyp01");
        request.setToken(PrefHelper.getString(PrefKey.TOKEN));
        return request;
    }

    DocumentIdRequest constructUploadSignature() {
        DocumentIdRequest request = new DocumentIdRequest();
        request.setType("DocTyp03");
        request.setToken(PrefHelper.getString(PrefKey.TOKEN));
        return request;
    }

    DocumentIdRequest constructUploadSelfie() {
        DocumentIdRequest request = new DocumentIdRequest();
        request.setType("DocTyp05");
        request.setToken(PrefHelper.getString(PrefKey.TOKEN));
        return request;
    }

    public void uploadDoc1(final DocumentIdRequest documentIdRequest, File file) {
        fragment.showProgressDialog(fragment.loading);
        okhttp3.RequestBody photo = okhttp3.RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("DocTyp01", file.getName(), photo);
        fragment.getApi().uploadByCustomerDoc1(documentIdRequest.getToken(), documentIdRequest.getType() , body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Timber.e(e.getMessage());
                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        if (response.getCode() == 0) {
                            Toast.makeText(fragment.getContext(), response.getInfo(), Toast.LENGTH_SHORT).show();
                            loadImageDoc1();
                        }
                    }
                });
    }

    public void uploadVerDoc1(final DocumentIdRequest documentIdRequest, File file) {
        fragment.showProgressDialog(fragment.loading);
        okhttp3.RequestBody photo = okhttp3.RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("DocTyp01", file.getName(), photo);
        fragment.getApi().uploadByCustomerVerDoc1(documentIdRequest.getToken(), documentIdRequest.getType() , body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Timber.e(e.getMessage());
                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        if (response.getCode() == 0) {
                            Toast.makeText(fragment.getContext(), response.getInfo(), Toast.LENGTH_SHORT).show();
                            loadImageDoc1();
                        }
                    }
                });
    }

    public void uploadVerDoc3(final DocumentIdRequest request, File file) {
        fragment.showProgressDialog(fragment.loading);
        okhttp3.RequestBody photo = okhttp3.RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("DocTyp03", file.getName(), photo);
        fragment.getApi().uploadByCustomerVerDoc1(request.getToken(), request.getType() , body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        if (response.getCode() == 0) {
                            Toast.makeText(fragment.getContext(), response.getInfo(), Toast.LENGTH_SHORT).show();
                            loadImageDoc3();
                        }
                    }
                });
    }



    public void uploadDoc3(final DocumentIdRequest request, File file) {
        fragment.showProgressDialog(fragment.loading);
        okhttp3.RequestBody photo = okhttp3.RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("DocTyp03", file.getName(), photo);
        fragment.getApi().uploadByCustomerDoc1(request.getToken(), request.getType() , body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        if (response.getCode() == 0) {
                            Toast.makeText(fragment.getContext(), response.getInfo(), Toast.LENGTH_SHORT).show();
                            loadImageDoc3();
                        }
                    }
                });
    }


    public void loadImageDoc1() {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().getDocumentDoc01(
                "DocTyp01",
                PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CustomerDocumentResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(final CustomerDocumentResponse response) {
                        Picasso.with(fragment.getContext()).load(InviseeService.IMAGE_CUSTOMER_DOWNLOAD_URL + response.getData().getFileKey() + "&token=" + PrefHelper.getString(PrefKey.TOKEN)).into(fragment.imvIdPhoto);
                        fragment.ImageKeyDoc1 = response.getData().getFileKey();
                        TextView doc1 = (TextView) fragment.getActivity().findViewById(R.id.doc1);
                        doc1.setText(response.getData().getFileName());
                        doc1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(InviseeService.Download + response.getData().getFileKey() + PrefHelper.getString(PrefKey.TOKEN)));
                                request.setDescription("A download package with some files");
                                request.setTitle("Download package");
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                request.allowScanningByMediaScanner();
                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "File Download");

                                // get download service and enqueue file
                                DownloadManager manager = (DownloadManager) fragment.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                                manager.enqueue(request);
                            }
                        });
                        loadImageDoc3();
                    }
                });
    }


    public void loadImageDoc3(){
        fragment.getApi().getDocumentDoc03(
                "DocTyp03",
                PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CustomerDocumentSignatureResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(final CustomerDocumentSignatureResponse response){
                        Picasso.with(fragment.getContext()).load(InviseeService.IMAGE_CUSTOMER_DOWNLOAD_URL + response.getData().getFileKey() + "&token=" + PrefHelper.getString(PrefKey.TOKEN)).into(fragment.imvSignaturePhoto);
                        fragment.ImageKeyDoc2 = response.getData().getFileKey();
                        TextView doc3 = (TextView) fragment.getActivity().findViewById(R.id.doc3);
                        doc3.setText(response.getData().getFileName());
                        doc3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(InviseeService.Download + response.getData().getFileKey() + PrefHelper.getString(PrefKey.TOKEN)));
                                request.setDescription("A download package with some files");
                                request.setTitle("Download package");
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                request.allowScanningByMediaScanner();
                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "File Download");

                                // get download service and enqueue file
                                DownloadManager manager = (DownloadManager) fragment.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                                manager.enqueue(request);
                            }
                        });
                        fragment.dismissProgressDialog();
                        loadImageDoc5();
                    }
                });
    }



    public void loadImageDoc5(){
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().getDocumentDoc05("DocTyp05",
                PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CustomerDocumentSelfieResponse>() {
                    @Override
                    public void onCompleted(){

                    }

                    @Override
                    public void onError(Throwable e){
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(final CustomerDocumentSelfieResponse response){
                        Picasso.with(fragment.getContext()).load(InviseeService.IMAGE_CUSTOMER_DOWNLOAD_URL + response.getData().getFileKey() + "&token=" + PrefHelper.getString(PrefKey.TOKEN)).into(fragment.imvSelfiePhoto);
                        fragment.imvSelfiePhoto.setVisibility(View.VISIBLE);
                        fragment.ImageKeyDoc5 = response.getData().getFileKey();
                        TextView doc5 = (TextView) fragment.getActivity().findViewById(R.id.doc5);
                        doc5.setText(response.getData().getFileName());
                        doc5.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {
                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(InviseeService.Download + response.getData().getFileKey() + PrefHelper.getString(PrefKey.TOKEN)));
                                request.setDescription("A download package with some files");
                                request.setTitle("Download package");
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                request.allowScanningByMediaScanner();
                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "File Download");

                                // get download service and enqueue file
                                DownloadManager manager = (DownloadManager) fragment.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                                manager.enqueue(request);
                            }
                        });
                        fragment.dismissProgressDialog();
                    }
                });
    }


    //new
    public void uploadVerDoc5(final DocumentIdRequest request, File file) {
        fragment.showProgressDialog(fragment.loading);
        okhttp3.RequestBody photo = okhttp3.RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("DocTyp05", file.getName(), photo);
        fragment.getApi().uploadByCustomerVerDoc5(request.getToken(), request.getType() , body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        if (response.getCode() == 0) {
                            Toast.makeText(fragment.getContext(), response.getInfo(), Toast.LENGTH_SHORT).show();
                            loadImageDoc5();
                        }
                    }
                });
    }



    public void uploadDoc5(final DocumentIdRequest request, File file) {
        fragment.showProgressDialog(fragment.loading);
        okhttp3.RequestBody photo = okhttp3.RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("DocTyp05", file.getName(), photo);
        fragment.getApi().uploadByCustomerDoc5(request.getToken(), request.getType() , body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted(){

                    }

                    @Override
                    public void onError(Throwable e){
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        if (response.getCode() == 0) {
                            Toast.makeText(fragment.getContext(), response.getInfo(), Toast.LENGTH_SHORT).show();
                            loadImageDoc5();
                        }
                    }
                });
    }


}
