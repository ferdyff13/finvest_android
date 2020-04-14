package com.invisee.finvest.ui.fragments.userProfile;

import android.view.View;
import android.widget.Toast;

import com.invisee.finvest.data.api.beans.Child;
import com.invisee.finvest.data.api.beans.ChildAnswer;
import com.invisee.finvest.data.api.beans.Country;
import com.invisee.finvest.data.api.beans.FatcaQuestion;
import com.invisee.finvest.data.api.requests.SaveFatcaRequest;
import com.invisee.finvest.data.api.responses.CompletenessPercentageResponse;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/22/16.
 */
public class FatcaPresenter {

    private FatcaFragment fragment;
    private double KYCpercentage, FATCApercentage, Riskpercentage = 0;
    StringBuilder batchYes = new StringBuilder("");
    StringBuilder batchNo = new StringBuilder("");
    StringBuilder finalBatch;

    public FatcaPresenter(FatcaFragment fragment) {
        this.fragment = fragment;
    }

    public SaveFatcaRequest constructsavefatca() {
        SaveFatcaRequest saveFatcaRequest = new SaveFatcaRequest();

        List<ChildAnswer> listChildAnswer = new ArrayList<ChildAnswer>();
        List<Child> childList = new ArrayList<Child>();

        listChildAnswer.clear();
        childList.clear();

        for (int i =0; i< fragment.countryIdList.size(); i++) {
            Child child = new Child();
            child.setAnswerCountry(fragment.countryIdList.get(i));
            child.setAnswerTin(fragment.tinList.get(i));
            child.setAnswerReason(fragment.reasonList.get(i));
            childList.add(child);
        }

        for (int j = 0; j < 1 ; j++) {
            ChildAnswer childAnswer = new ChildAnswer();
            childAnswer.setQuestionParent(54);
            childAnswer.setChilds(childList);
            listChildAnswer.add(childAnswer);
        }

        saveFatcaRequest.setChildAnswers(listChildAnswer);

        return saveFatcaRequest;

    }

    String constructBatchFatca(boolean changed, boolean checked, ArrayList<FatcaQuestion> questions){

            if (checked) {
                finalBatch = new StringBuilder("");

                StringBuilder batch = new StringBuilder("");
                for (FatcaQuestion question : questions) {
                    batch.append(question.getQuestionId())
                            .append(",")
                            .append(question.getAnswerId())
                            .append(";");
                }

                finalBatch = batch;
            } else {
                finalBatch = batchNo;
            }
        return finalBatch.toString();
    }


    void loadFatchaQuestionAndAnswer(){
        fragment.getApi().loadFatcaQuestionAndAnswer(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<FatcaQuestion>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        fragment.dismissProgressBar();
                    }

                    @Override
                    public void onNext(ArrayList<FatcaQuestion> fatcaQuestions) {
                        fragment.questions = fatcaQuestions;
                        fragment.fetchQuestionToLayout();

                        /*
                        History on 21 March 2017
                        Mobile change to one question & one answer to all. different than web version
                         */

                        for(int i=0; i<fatcaQuestions.size(); i++){

                            for(int c=0; c<fatcaQuestions.get(i).getAnswerOption().size(); c++)
                            {
                                if(fatcaQuestions.get(i).getAnswerOption().get(c).getAnswerName().contains("A1")) //A1 = YES
                                {
                                    if(String.valueOf(fatcaQuestions.get(i).getAnswerId()).equals(String.valueOf(fatcaQuestions.get(i).getAnswerOption().get(c).getId()))){
                                        fragment.toggleBtn.setChecked(true);
                                        fragment.mainRvContainer.setVisibility(View.VISIBLE);
                                    }

                                    batchYes.append(String.valueOf(fatcaQuestions.get(i).getQuestionId()))
                                            .append(",")
                                            .append(String.valueOf(fatcaQuestions.get(i).getAnswerOption().get(c).getId()))
                                            .append(";");
                                }
                                else if (fatcaQuestions.get(i).getAnswerOption().get(c).getAnswerName().contains("A2")) //A2 = NO
                                {
                                    batchNo.append(String.valueOf(fatcaQuestions.get(i).getQuestionId()))
                                            .append(",")
                                            .append(String.valueOf(fatcaQuestions.get(i).getAnswerOption().get(c).getId()))
                                            .append(";");
                                }
                            }
                        }

                        if (fragment.questions.get(10).getChildAnswerLoad().size() > 0) {
                            fragment.loadChildAnswer();
                        }
                        fragment.validation();
                        fragment.onClickAnswer();
                        fragment.dismissProgressBar();
                    }
                });
    }

    void saveFatca(String batch){
        fragment.getApi().saveFatca(batch, "2", PrefHelper.getString(PrefKey.TOKEN), constructsavefatca())
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
                        Toast.makeText(fragment.getContext(), genericResponse.getInfo(), Toast.LENGTH_SHORT).show();
                        if (genericResponse.getCode() == 2) {
                            fragment.toNextForm();
                        } else if(genericResponse.getCode() == 1) {
                            fragment.toNextForm();
                        } else if(genericResponse.getCode() == 0) {
                            fragment.showDialogFailedSubmit(genericResponse.getInfo());
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
                        KYCpercentage   = completenessPercentageResponse.getData().getKyc()*0.8;
                        FATCApercentage = completenessPercentageResponse.getData().getFatca()*0.1;
                        Riskpercentage  = completenessPercentageResponse.getData().getRiskProfile()*0.1;
                        int total = (int) ((KYCpercentage+FATCApercentage+Riskpercentage));
                        fragment.pbCompleteness.setProgress(total);
                        fragment.tvCompleteness.setText(total+"%");
                    }
                });
    }

    public void getAllCountry() {
        fragment.showProgressBar();
        fragment.getApi().getAllCountry(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Country>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressBar();

                    }

                    @Override
                    public void onNext(List<Country> countries) {
                        fragment.countries = countries;
                        loadFatchaQuestionAndAnswer();

                    }
                });
    }

    //================================ VIEW CONDITON FOR ADAPTER ================================

    public void setShowCrs() {
        fragment.lnCrsField.setVisibility(View.VISIBLE);
        fragment.setCRSView();

    }

    public void setHideCrs() {
        fragment.lnCrsField.setVisibility(View.GONE);
    }





    /*
    String constructBatch(ArrayList<FatcaQuestion> questions){
        StringBuilder batch = new StringBuilder("");
        for(FatcaQuestion question:questions){
            batch.append(question.getQuestionId())
                    .append(",")
                    .append(question.getAnswerId())
                    .append(";");
        }
        return batch.toString();
    }*/

    String constructBatch(boolean changed, boolean checked, ArrayList<FatcaQuestion> questions){
        finalBatch = new StringBuilder("");

        if(!changed){
            if(checked){
                finalBatch = batchYes;
            } else {
                finalBatch = batchNo;
            }
/*            StringBuilder batch = new StringBuilder("");
            for(FatcaQuestion question:questions){
                batch.append(question.getQuestionId())
                        .append(",")
                        .append(question.getAnswerId())
                        .append(";");
            }
            finalBatch =  batch;*/

        } else {
            if(checked){
                finalBatch = batchYes;
            } else {
                finalBatch = batchNo;
            }
        }
        return finalBatch.toString();
    }

    /*
    String constructBatchYes(ArrayList<FatcaQuestion> questions){
        StringBuilder batch = new String("");
        for
    }*/


}
