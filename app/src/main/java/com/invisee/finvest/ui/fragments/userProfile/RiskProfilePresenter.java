package com.invisee.finvest.ui.fragments.userProfile;

import android.util.Log;
import android.widget.Toast;

import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.beans.RiskProfileQuestion;
import com.invisee.finvest.data.api.responses.CompletenessPercentageResponse;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.api.responses.LoadKycDataResponse;
import com.invisee.finvest.data.api.responses.RiskProfileResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.util.eventBus.RxBusObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/22/16.
 */
public class RiskProfilePresenter {

    private RiskProfileFragment fragment;
    private InviseeService inviseeService;
    private double KYCpercentage, FATCApercentage, Riskpercentage = 0;
    private final static String BASE_URL_DEV = "http://52.76.229.157:8080/avantrade-portal-core/";

    public RiskProfilePresenter(RiskProfileFragment fragment) {
        this.fragment = fragment;
    }

    void loadRiskProfileQuestionAndAnswer() {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().loadRiskProfileQuestionAndAnswer(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<RiskProfileQuestion>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Timber.e(e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<RiskProfileQuestion> riskProfileQuestions) {
                        fragment.dismissProgressDialog();
                        fragment.riskProfileQuestionList = riskProfileQuestions;
                        fragment.fetchQuestionToLayout();
                    }
                });
    }

/*    void saveOrUpdateRiskProfile(String batch) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().saveOrUpdateRiskProfile(batch, PrefHelper.getString(PrefKey.TOKEN))
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
                    }

                    @Override
                    public void onNext(GenericResponse genericResponse) {
                        fragment.dismissProgressDialog();
//                        Toast.makeText(fragment.getContext(), genericResponse.getInfo(), Toast.LENGTH_SHORT).show();
                        if (genericResponse.getCode() == 1) {
                            Log.d("masuk risk profile pres", "onNext: " + "masuk");
                            getRiskProfileResult(genericResponse.getInfo());
                            //getRiskProfileResult(); old change to confirmation
//                            fragment.showConfirmation();
                        } else if(genericResponse.getCode() == 0){
                            fragment.showDialogFailedSubmit();
                        }
                    }
                });
    }*/

    void saveOrUpdateRiskProfile(String batch){
        //fragment.showProgressDialog(fragment.loading);
        fragment.getApi().saveCunstomerAnswer(batch, "6", PrefHelper.getString(PrefKey.TOKEN))
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
                    }

                    @Override
                    public void onNext(GenericResponse genericResponse) {
                        fragment.dismissProgressDialog();
                        if (genericResponse.getCode() == 1) {
                            getRiskProfileResult(genericResponse.getInfo());
                        } else if(genericResponse.getCode() == 0){
                            fragment.showDialogGeneral(genericResponse.getInfo());
                        } else {
                            getRiskProfileResult(genericResponse.getInfo());
                        }
                    }
                });
    }



    String constructBatch(ArrayList<RiskProfileQuestion> questions) {
        StringBuilder batch = new StringBuilder("");
        List<Integer> answerId = new ArrayList<Integer>();
        for (RiskProfileQuestion question : questions) {
            if (question.getAnswerId().size() <= 0) {
                if (BASE_URL_DEV.equals(InviseeService.BASE_URL)) {
                    answerId.add(84);
                    answerId.add(88);
                    answerId.add(92);
                } else {
                    answerId.add(145);
                    answerId.add(149);
                    answerId.add(153);
                }

//                for (int i = 0; i < question.getAnswerOption().size(); i++) {
//                    answerId.add(0);
//                }
                question.setAnswerId(answerId);

                Log.d("answer", "constructBatch: " + question.getAnswerOption().size());

                batch.append(question.getQuestionId())
                        .append(",")
                        .append(question.getAnswerId().get(0))
                        .append(";");
            } else {
                batch.append(question.getQuestionId())
                        .append(",")
                        .append(question.getAnswerId().get(0))
                        .append(";");
            }

        }
        return batch.toString();
    }

/*
    void saveOrUpdateRiskProfileConservative() {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().saveOrUpdateRiskProfile(fragment.batch, PrefHelper.getString(PrefKey.TOKEN))
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
                    }

                    @Override
                    public void onNext(GenericResponse genericResponse) {
                        fragment.dismissProgressDialog();
                        if (genericResponse.getCode() == 1) {
                            //getRiskProfileResult(); old change to confirmation
//                            fragment.showConfirmation();
//                            Toast.makeText(fragment.getContext(), "Data Berhasil diubah", Toast.LENGTH_SHORT).show();
                            Toast.makeText(fragment.getContext(), genericResponse.getInfo(), Toast.LENGTH_SHORT).show();
                        } else if(genericResponse.getCode() == 0){
                            fragment.showDialogFailedSubmit();
                        }
                    }
                });
    }
*/

    void saveOrUpdateRiskProfileConservative( ){
        //fragment.showProgressDialog(fragment.loading);
        fragment.getApi().saveCunstomerAnswer(fragment.batch, "6", PrefHelper.getString(PrefKey.TOKEN))
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
                    }

                    @Override
                    public void onNext(GenericResponse genericResponse) {
                        fragment.dismissProgressDialog();
                        if (genericResponse.getCode() == 1) {
                            Toast.makeText(fragment.getContext(), genericResponse.getInfo(), Toast.LENGTH_SHORT).show();
                        } else if(genericResponse.getCode() == 0){
                            fragment.showDialogGeneral(genericResponse.getInfo());
                        } else if (genericResponse.getCode() == 2) {
                            getRiskProfileResult(genericResponse.getInfo());
                        }
                    }
                });
    }

    String constructBatchDefault(ArrayList<RiskProfileQuestion> questions) {
        StringBuilder batch = new StringBuilder("");
        for (RiskProfileQuestion question : questions) {
            batch.append(question.getQuestionId())
                    .append(",")
                    .append(question.getAnswerId().get(0))
                    .append(";");
        }
        return batch.toString();
    }

    void getRiskProfileResult(final String info) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().getRiskProfileResult(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<RiskProfileResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Timber.e(e.getMessage());
                    }

                    @Override
                    public void onNext(RiskProfileResponse riskProfileResponse) {
                        fragment.dismissProgressDialog();
                        fragment.tvRiskProfileResult.setText(riskProfileResponse.getRiskProfile().toUpperCase());
                        if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equalsIgnoreCase("ACT")) {
                            fragment.showConfirmation();
                        } else fragment.showDialogGeneral(info);
                    }
                });

    }

    void getRiskProfileFirst() {
        ///fragment.showProgressDialog(fragment.loading);
        fragment.getApi().getRiskProfileResult(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<RiskProfileResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Timber.e(e.getMessage());
                    }

                    @Override
                    public void onNext(RiskProfileResponse riskProfileResponse) {
                        //fragment.dismissProgressDialog();
                        try {
                            fragment.tvRiskProfileResult.setText(riskProfileResponse.getRiskProfile().toUpperCase());
                        } catch (NullPointerException e) {
                            fragment.tvRiskProfileResult.setText("-");
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
                        Timber.e(e.getMessage());
                        fragment.showFailedDialog("Error on retrieving completeness percentage data");
                    }

                    @Override
                    public void onNext(CompletenessPercentageResponse completenessPercentageResponse) {
                        KYCpercentage = completenessPercentageResponse.getData().getKyc() * 0.8;
                        FATCApercentage = completenessPercentageResponse.getData().getFatca() * 0.1;
                        Riskpercentage = completenessPercentageResponse.getData().getRiskProfile() * 0.1;
                        int total = (int) ((KYCpercentage + FATCApercentage + Riskpercentage));
                        fragment.pbCompleteness.setProgress(total);

                        //NumberFormat percentFormat = NumberFormat.getPercentInstance();
                        //percentFormat.setMaximumFractionDigits(2);
                        fragment.tvCompleteness.setText(total + "%");
                    }
                });
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
                        fragment.showDialogConservative();
                    }

                    @Override
                    public void onNext(LoadKycDataResponse loadKycDataResponse) {
                        fragment.dismissProgressDialog();
                        if (loadKycDataResponse.getData().getInvestmentExperience().equals("IE05")) {
                            fragment.showDialogConservative();
                        } else {
                            getRiskProfileFirst();
                        }

                        loadRiskProfileQuestionAndAnswer();
                    }
                });
    }
}
