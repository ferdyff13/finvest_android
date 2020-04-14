package com.invisee.finvest.ui.fragments.userProfile;

import android.content.SharedPreferences;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Kyc;
import com.invisee.finvest.data.api.beans.KycLookup;
import com.invisee.finvest.data.api.requests.KycDataRequest;
import com.invisee.finvest.data.api.requests.SettlementInfoRequest;
import com.invisee.finvest.data.api.responses.CompletenessPercentageResponse;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.api.responses.LoadKycDataResponse;
import com.invisee.finvest.data.api.responses.SettlementInfoResponse;
import com.invisee.finvest.data.api.responses.UploadResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.data.realm.RealmHelper;
import com.invisee.finvest.data.realm.model.KycModel;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.KycActivityNew;

import org.modelmapper.ModelMapper;

import java.io.File;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import okhttp3.MultipartBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/10/16.
 */
public class KycPresenter {

    private KycFragment fragment;
    private KycActivityNew activity;
    private Kyc kycResponse;
    private double KYCpercentage, FATCApercentage, Riskpercentage = 0;
    private int totalCut = 100;
    private LoadKycDataResponse myLoadKycData;

    public KycPresenter(KycFragment fragment) {
        this.fragment = fragment;
    }

    void getKycLookupDataAndKycUserData() {
        //fragment.showProgressDialog(fragment.loading);
        if (!isKycLookupAlreadyOnRealm()) {
            fragment.getApi().getKycLookup(PrefHelper.getString(PrefKey.TOKEN))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<List<KycLookup>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e("getKycLookupDataAndKycUserData");
                            Timber.e(e.getLocalizedMessage());
                            //fragment.dismissProgressDialog();
                            fragment.showErrorOnRetrievingKycDataDialog();
                        }

                        @Override
                        public void onNext(List<KycLookup> kycLookupList) {
                            RealmHelper.createKycLookup(fragment.getRealm(), kycLookupList);
                            getKycDataFromServer();
                        }
                    });
        } else {
            getKycDataFromServer();
        }
    }

    private boolean isKycLookupAlreadyOnRealm() {
        return RealmHelper.getKycLookupSize(fragment.getRealm()) > 0;
    }

    void getKycDataFromServer() {
        String token = PrefHelper.getString(PrefKey.TOKEN);
        String email = PrefHelper.getString(PrefKey.EMAIL);
        fragment.getApi().loadKycData(email, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<LoadKycDataResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("getKycDataFromServer");
                        Timber.e(e.getLocalizedMessage());
                        fragment.dismissProgressDialog();
                        getKycLookupDataAndKycUserData();
                    }

                    @Override
                    public void onNext(LoadKycDataResponse loadKycDataResponse) {
                        loadSettlement(loadKycDataResponse);

                    }
                });
    }

    void loadSettlement(LoadKycDataResponse loadKycDataResponse) {
        String email = PrefHelper.getString(PrefKey.EMAIL);
        String token = PrefHelper.getString(PrefKey.TOKEN);
        myLoadKycData = loadKycDataResponse;

        fragment.getApi().loadSettlementInformation(email, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<SettlementInfoResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                    }

                    @Override
                    public void onNext(SettlementInfoResponse settlementInfoResponse) {
                        //try to use bus
                        try{
                            myLoadKycData.getData().setSettlementAccountNo(settlementInfoResponse.getSettlementAccountNo());
                        }catch(NullPointerException e){
                            myLoadKycData.getData().setSettlementAccountNo("");
                        }

                        try{
                            myLoadKycData.getData().setSettlementAccountName(settlementInfoResponse.getSettlementAccountName());
                        }catch(NullPointerException e){
                            myLoadKycData.getData().setSettlementAccountName("");
                        }

                        try{
                            myLoadKycData.getData().setBankId(settlementInfoResponse.getId().toString());
                        }catch(NullPointerException e){
                            myLoadKycData.getData().setBankId("0");
                        }

                        initiateKycDataRequest(myLoadKycData.getData());
                        fragment.onKycDataLoaded();

                      /*
                      PrefHelper.setString(PrefKey.ACCOUNT_NAME, settlementInfoResponse.getSettlementAccountName());
                      PrefHelper.setString(PrefKey.ACCOUNT_NO, settlementInfoResponse.getSettlementAccountNo());
                      PrefHelper.setInt(PrefKey.BANK_ID, settlementInfoResponse.getId());*/
                    }
                });

    }

    void saveKycDataToRealm(final Kyc kycResponse) {
        fragment.getRealm().asObservable()
                .map(new Func1<Realm, KycModel>() {
                    @Override
                    public KycModel call(Realm realm) {
                        KycModel kycModel = realm.where(KycModel.class).equalTo("id", KycModel.DEFAULT_ID).findAllAsync().first();
                        if (kycModel != null) {
                            return kycModel;
                        } else {
                            return null;
                        }

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<KycModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("saveKycDataToRealm");
                        Timber.e(e.getLocalizedMessage());
                        fragment.dismissProgressDialog();
                        fragment.showErrorOnRetrievingKycDataDialog();
                    }

                    @Override
                    public void onNext(KycModel kycModel) {
                        RealmHelper.createOrUpdateKycModel(fragment.getRealm(), kycModel, kycResponse);
                        fragment.dismissProgressDialog();
                        fragment.onKycDataLoaded();
                    }
                });
    }

    void getKycData() {
        String token = PrefHelper.getString(PrefKey.TOKEN);
        String email = PrefHelper.getString(PrefKey.EMAIL);
        fragment.getApi().loadKycData(email, token)
                .flatMap(new Func1<LoadKycDataResponse, Observable<KycModel>>() {
                    @Override
                    public Observable<KycModel> call(LoadKycDataResponse loadKycDataResponse) {
                        initiateKycDataRequest(loadKycDataResponse.getData());
                        kycResponse = loadKycDataResponse.getData();
                        return fragment.getRealm().where(KycModel.class).equalTo("id", KycModel.DEFAULT_ID).findAllAsync().first()
                                .asObservable();
                    }
                })
                .filter(new Func1<KycModel, Boolean>() {
                    @Override
                    public Boolean call(KycModel kycModel) {
                        return kycModel.isLoaded();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<KycModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.dismissProgressDialog();
                        fragment.showErrorOnRetrievingKycDataDialog();
                    }

                    @Override
                    public void onNext(KycModel kycModel) {
                        RealmHelper.createOrUpdateKycModel(fragment.getRealm(), kycModel, kycResponse);
                        fragment.dismissProgressDialog();
                        fragment.onKycDataLoaded();
                    }
                });
    }

    private void initiateKycDataRequest(Kyc kyc) {
        if (kyc != null) {
            ModelMapper modelMapper = new ModelMapper();
            fragment.kycDataRequest = modelMapper.map(kyc, KycDataRequest.class);
        } else {
            fragment.kycDataRequest = new KycDataRequest();
        }
    }

    /*
   private void initiateLoadSettlementRequest(SettlementInfoResponse settlementInfoResponse) {
        if (settlementInfoResponse != null) {
            ModelMapper modelMapper = new ModelMapper();
            fragment.kycDataRequest.setSettlementAccountName(settlementInfoResponse.getSettlementAccountName());
            fragment.kycDataRequest.setBankId(String.valueOf(settlementInfoResponse.getId()));
        } else {
            fragment.kycDataRequest = new KycDataRequest();
        }
    }*/

    void saveAndSubmitKycDataRequest(final KycDataRequest kycDataRequest) {
        System.out.println("OUT >> 3 "+kycDataRequest.getBirthPlace());

        requestSubmitKycData(kycDataRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(GenericResponse genericResponse) {
                        fragment.dismissProgressDialog();

                        System.out.println("OUT >> 4 "+genericResponse.getCode());

                            fragment.toNextForm();
                        }

                });
    }

    private Observable<GenericResponse> requestSubmitKycData(KycDataRequest request) {
        Timber.i("Submitted KYC Data To WS: %s", request);
        return fragment.getApi().saveKycData(
                request.getSalutation(),
                request.getFirstName(),
                request.getMiddleName(),
                request.getLastName(),
                request.getEmail(),
                request.getBirthDate(),
                request.getBirthPlace(),
                request.getOccupation(),
                request.getNatureOfBusiness(),
                request.getEmployerName(),
                request.getHomeCountry(),
                request.getHomeProvince(),
                request.getHomeCity(),
                request.getHomeAddress(),
                request.getHomePostalCode(),
                request.getHomePhoneNumber(),
                request.getLegalCountry(),
                request.getLegalProvince(),
                request.getLegalCity(),
                request.getLegalAddress(),
                request.getLegalPostalCode(),
                request.getLegalPhoneNumber(),
                request.getIdType(),
                request.getIdNumber(),
                request.getIdExpirationDate(),
                request.getNationality(),
                request.getCitizenship(),
                request.getGender(),
                request.getReligion(),
                request.getMaritalStatus(),
                request.getEducationBackground(),
                request.getSourceOfIncome(),
                request.getTotalIncomePa(),
                request.getTotalAsset(),
                request.getInvestmentExperience(),
                request.getInvestmentPurpose(),
                request.getOtherInvesmentExperience(),
                request.getPepName(),
                request.getPepPosition(),
                request.getPepPublicFunction(),
                request.getPepYearOfService(),
                request.getPepRelationship(),
                request.getMotherMaidenName(),
                request.getBeneficiaryName(),
                request.getBeneficiaryRelationship(),
                request.getPreferredMailingAddress(),
                request.getTaxId(),
//                request.getTaxIdRegisDate(),
                PrefHelper.getString(PrefKey.TOKEN),
                request.getReferral(),
                request.getReferralName(),
                request.getSpouseName(),
                request.getBankId(),
                request.getBranchId(),
                request.getSettlementAccountName(),
                request.getSettlementAccountNo());
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
                        Timber.e(e.getMessage());
                        fragment.showFailedDialog("Error on retrieving completeness percentage data");
                    }

                    @Override
                    public void onNext(CompletenessPercentageResponse completenessPercentageResponse) {
                        KYCpercentage   = completenessPercentageResponse.getData().getKyc()*0.8;
                        FATCApercentage = completenessPercentageResponse.getData().getFatca()*0.1;
                        Riskpercentage  = completenessPercentageResponse.getData().getRiskProfile()*0.1;
                        int total = (int) ((KYCpercentage+FATCApercentage+Riskpercentage));
                        if (total > 100) {
                            fragment.tvCompleteness.setText(totalCut +"%");
                        } else {
                            fragment.tvCompleteness.setText(total + "%");
                        }
                        fragment.pbCompleteness.setProgress(total);

                        //NumberFormat percentFormat = NumberFormat.getPercentInstance();
                        //percentFormat.setMaximumFractionDigits(2);
                    /*    fragment.tvCompleteness.setText(total+"%");*/
                    }
                });
    }
}