package com.invisee.finvest.ui.activities;

import android.widget.Toast;

import com.invisee.finvest.data.api.beans.Kyc;
import com.invisee.finvest.data.api.beans.KycLookup;
import com.invisee.finvest.data.api.requests.KycDataRequest;
import com.invisee.finvest.data.api.responses.CompletenessPercentageResponse;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.api.responses.LoadKycDataResponse;
import com.invisee.finvest.data.api.responses.SettlementInfoResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.data.realm.RealmHelper;
import com.invisee.finvest.data.realm.model.KycModel;
import com.invisee.finvest.ui.fragments.userProfile.FatcaFragment;
import com.invisee.finvest.ui.fragments.userProfile.KycFragment;

import org.modelmapper.ModelMapper;

import java.util.List;

import io.realm.Realm;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/10/16.
 */
public class KycActivityPresenter {

    private KycActivityNew activity;
    private double KYCpercentage, FATCApercentage, Riskpercentage = 0;
    private int totalCut = 100;
    private LoadKycDataResponse myLoadKycData;


    public KycActivityPresenter(KycActivityNew activity) {
        this.activity = activity;
    }

    void getKycLookupDataAndKycUserData(){
        activity.showProgressDialog(activity.loading);
        if (!isKycLookupAlreadyOnRealm()){
            activity.getInviseeService().getApi().getKycLookup(PrefHelper.getString(PrefKey.TOKEN))
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
                            activity.dismissProgressDialog();
                            activity.showErrorOnRetrievingKycDataDialog();
                        }

                        @Override
                        public void onNext(List<KycLookup> kycLookupList) {
                            activity.dismissProgressDialog();
                            RealmHelper.createKycLookup(activity.getRealm(), kycLookupList);
                            getKycDataFromServer();
                        }
                    });
        } else {
            getKycDataFromServer();
        }
    }

    private boolean isKycLookupAlreadyOnRealm() {
        return RealmHelper.getKycLookupSize(activity.getRealm()) > 0;
    }

    void getKycDataFromServer() {
        activity.showProgressDialog(activity.loading);
        String token = PrefHelper.getString(PrefKey.TOKEN);
        String email = PrefHelper.getString(PrefKey.EMAIL);
        activity.getInviseeService().getApi().loadKycData(email, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<LoadKycDataResponse>() {
                    @Override
                    public void onCompleted(){

                    }

                    @Override
                    public void onError(Throwable e){
                        Timber.e("getKycDataFromServer");
                        Timber.e(e.getLocalizedMessage());
                        activity.dismissProgressDialog();
                        getKycLookupDataAndKycUserData();
                      /*  fragment.showErrorOnRetrievingKycDataDialog();*/
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
        activity.getInviseeService().getApi().loadSettlementInformation(email, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<SettlementInfoResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        activity.dismissProgressDialog();
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
                        activity.onKycDataLoaded();
                        activity.dismissProgressDialog();
                    }
                });

    }


    private void initiateKycDataRequest(Kyc kyc) {
        if (kyc != null) {
            ModelMapper modelMapper = new ModelMapper();
            activity.kycDataRequest = modelMapper.map(kyc, KycDataRequest.class);
        } else {
            activity.kycDataRequest = new KycDataRequest();
        }
    }

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
                        activity.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(GenericResponse genericResponse) {
                        activity.dismissProgressDialog();
                        System.out.println("OUT >> 4 "+genericResponse.getCode());
                       /* RealmHelper.createOrUpdateKycModel(fragment.getRealm(), kycModel, kycDataRequest);*/
                        if (genericResponse.getCode() == 1) {
                            Toast.makeText(activity.getBaseContext(), genericResponse.getInfo(), Toast.LENGTH_SHORT).show();
                            activity.toNextFormVer();
                        } else if (genericResponse.getCode() == 2 ) {
                            Toast.makeText(activity, genericResponse.getInfo(), Toast.LENGTH_SHORT).show();
                            FatcaActivity.startActivity(activity);
                        } else if (genericResponse.getCode() == 0) {
                            activity.showDialogFailedSubmit(genericResponse.getInfo());
                        }
                    }
                });
    }

    void saveKycDataRequest(final KycDataRequest kycDataRequest) {
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
                        activity.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(GenericResponse genericResponse) {
                        activity.dismissProgressDialog();
                        System.out.println("OUT >> 4 "+genericResponse.getCode());
                        if(genericResponse.getCode() != activity.successCode){
                        }
                        if (genericResponse.getCode() == activity.activicationSuccessCode) {

                        } else if (genericResponse.getCode() == 2 ) {

                        } else if (genericResponse.getCode() == 0) {

                        }
                    }
                });
    }

    private Observable<GenericResponse> requestSubmitKycData(KycDataRequest request) {
        Timber.i("Submitted KYC Data To WS: %s", request);
        return activity.getInviseeService().getApi().saveKycData(
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
        activity.showProgressDialog(activity.loading);
        activity.getInviseeService().getApi().getCompletenessPercentage(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CompletenessPercentageResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        activity.showFailedDialog("Error on retrieving completeness percentage data");
                    }

                    @Override
                    public void onNext(CompletenessPercentageResponse completenessPercentageResponse) {
                        activity.dismissProgressDialog();
                        KYCpercentage   = completenessPercentageResponse.getData().getKyc()*0.8;
                        FATCApercentage = completenessPercentageResponse.getData().getFatca()*0.1;
                        Riskpercentage  = completenessPercentageResponse.getData().getRiskProfile()*0.1;
                        int total = (int) ((KYCpercentage+FATCApercentage+Riskpercentage));
                        if (total > 100) {
                            activity.tvCompleteness.setText(totalCut +"%");
                        } else {
                            activity.tvCompleteness.setText(total + "%");
                        }
                        activity.pbCompleteness.setProgress(total);
                    }
                });
    }
}