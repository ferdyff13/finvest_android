package com.invisee.finvest.ui.fragments.userProfile;


import android.widget.Toast;

import com.invisee.finvest.data.api.beans.SecurityQuestion;
import com.invisee.finvest.data.api.requests.SaveUserInfoRequest;
import com.invisee.finvest.data.api.responses.CompletenessPercentageResponse;
import com.invisee.finvest.data.api.responses.GeneratePINResponse;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.api.responses.UploadResponse;
import com.invisee.finvest.data.api.responses.UserInfoResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.data.realm.RealmHelper;
import com.invisee.finvest.data.realm.model.SecurityQuestionModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import io.realm.RealmResults;
import okhttp3.MultipartBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class UserInfoPresenter {

    private UserInfoFragment fragment;

    public UserInfoPresenter(UserInfoFragment fragment){
        this.fragment = fragment;
    }

    void userInfo() {
        fragment.showProgressBar();
        fragment.getApi().userInfo(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<UserInfoResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressBar();
                    }

                    @Override
                    public void onNext(UserInfoResponse response){
                        if(response.getCode() == 1){
                            fragment.userInfo = response;
                            if(fragment.userInfo.getQuestion().getId() != null){
                            }else{
                                fragment.userInfo.getQuestion().setId(19);
                            }
                            queryAndSetupSecurityQuestions();
                            getCompleteness();
                        }else{
                            fragment.dismissProgressBar();
                        }
                    }
                });
    }


    void queryAndSetupSecurityQuestions() {
        RealmHelper.readSecurityQuestions(fragment.getRealm())
                .map(new Func1<RealmResults<SecurityQuestionModel>, List<SecurityQuestion>>() {
                    @Override
                    public List<SecurityQuestion> call(RealmResults<SecurityQuestionModel> securityQuestionModels) {
                        List<SecurityQuestion> list = new ArrayList<>();
                        for (SecurityQuestionModel sqm : securityQuestionModels) {
                            SecurityQuestion sq = new SecurityQuestion();
                            sq.setId(sqm.getId());
                            sq.setQuestionName(sqm.getQuestionName());
                            sq.setQuestionText(sqm.getQuestionText());
                            list.add(sq);
                        }
                        return list;
                    }
                })
                .subscribe(new Action1<List<SecurityQuestion>>() {
                    @Override
                    public void call(List<SecurityQuestion> securityQuestionList) {
                        fragment.securityQuestionList = securityQuestionList;
                        fragment.setUpSpinnerQuestion(securityQuestionList, fragment.userInfo.getQuestion().getId().toString());

                    }
                });
    }


    void saveUserInfo(SaveUserInfoRequest request) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().saveUserInfo(request.getAnswerNote(),
                request.getEmail(),
                request.getFirstName(),
                request.getHomePhoneNumber(),
                request.getImageKey(),
                request.getLastName(),
                request.getMiddleName(),
                request.getMobileNumber(),
                request.getQuestion(),
                PrefHelper.getString(PrefKey.TOKEN))
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
                        fragment.dismissProgressDialog();
                        if (response.getCode() == 1) {
                            fragment.showSuccessDialog("Saving success", false);
                        }

                    }
                });
    }

    public void uploadPhoto(File file) {
        fragment.showProgressDialog(fragment.loading);
        okhttp3.RequestBody photo = okhttp3.RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("lampiran", file.getName(), photo);
        fragment.getApi().uploadPhoto(PrefHelper.getString(PrefKey.TOKEN), body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<UploadResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.connectionError();
                    }

                    @Override
                    public void onNext(UploadResponse response) {
                        if (response.getCode() == 1) {
                            Toast.makeText(fragment.getContext(), response.getInfo(),Toast.LENGTH_LONG).show();
                            saveCredential(response);
                            fragment.saveUser();
                            fragment.loadPicture();
                            fragment.dismissProgressDialog();
                        } else {
                            fragment.dismissProgressDialog();
                            Toast.makeText(fragment.getContext(), response.getInfo(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void saveCredential(UploadResponse response) {
        PrefHelper.clearPref(PrefKey.IMAGE);
        PrefHelper.setString(PrefKey.IMAGE, response.getKey());
    }


    void sendPIN(){
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().generatePin(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GeneratePINResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Timber.e(e.getMessage());
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(GeneratePINResponse generatePINResponse) {
                        if (generatePINResponse.getCode() == 1) {
                            fragment.dismissProgressDialog();
                            Toast.makeText(fragment.getContext(), generatePINResponse.getInfo(),Toast.LENGTH_LONG).show();
                        } else {
                            fragment.dismissProgressDialog();
                            Toast.makeText(fragment.getContext(), generatePINResponse.getInfo(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void getCompleteness() {
        fragment.getApi().getCompletenessPercentage(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CompletenessPercentageResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.connectionError();
                    }

                    @Override
                    public void onNext(CompletenessPercentageResponse completenessPercentageResponse) {
                        if(completenessPercentageResponse.getData().getKyc()==100 && completenessPercentageResponse.getData().getFatca()==100 && completenessPercentageResponse.getData().getRiskProfile()==100
                                || completenessPercentageResponse.getData().getKyc()>100 || completenessPercentageResponse.getData().getFatca()>100 || completenessPercentageResponse.getData().getRiskProfile()>100)
                        {
                            fragment.setView(true);
                            fragment.dismissProgressBar();
                        }
                        else
                        {
                            queryAndSetupSecurityQuestions();
                            fragment.setView(false);
                            fragment.dismissProgressBar();
                        }

                    }
                });
    }
}
