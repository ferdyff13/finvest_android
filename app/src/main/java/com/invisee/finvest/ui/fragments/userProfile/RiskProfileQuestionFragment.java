package com.invisee.finvest.ui.fragments.userProfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.RiskProfileAnswerOption;
import com.invisee.finvest.data.api.beans.RiskProfileQuestion;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.eventBus.RxBusObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by fajarfatur on 2/22/16.
 */
public class RiskProfileQuestionFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {

    public static final String TAG = RiskProfileQuestionFragment.class.getSimpleName();
    public static final String QUESTION = "question";

    @Bind(R.id.tvQuestion)
    TextView tvQuestion;
    @Bind(R.id.sAnswerOption)
    Spinner sAnswerOption;
    @State
    RiskProfileQuestion question;
    @State
    RiskProfileAnswerOption selectedAnswer;

    public static RiskProfileQuestionFragment initiateFragment(RiskProfileQuestion question) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(QUESTION, question);
        RiskProfileQuestionFragment fragment = new RiskProfileQuestionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.f_risk_profile_question;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().getSerializable(QUESTION) != null) {
            question = (RiskProfileQuestion) getArguments().getSerializable(QUESTION);
        } else {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvQuestion.setText(question.getQuestionText());
        setupSpinner(question);
        /**
         * setup spinner selection & default value*/
        if (question.getAnswerId() == null || question.getAnswerId().size() < 1 || question.getAnswerId().get(0) == 0) { // question not answered yet, default value 0, set to selected item (answer id)
            updateSelectedAnswer();
        } else { // set spinner selection to answered question id (answer id)
            int i = 0;
            for (RiskProfileAnswerOption answer : question.getAnswerOption()) {
                if (answer.getId() == question.getAnswerId().get(0)) {
                    sAnswerOption.setSelection(i, false);
                    break;
                }
                i++;
            }
        }
    }

    public void setupSpinner(RiskProfileQuestion question) {
        List<RiskProfileAnswerOption> answerOptions = question.getAnswerOption();
        if (answerOptions == null) answerOptions = new ArrayList<>();
        ArrayAdapter<RiskProfileAnswerOption> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, answerOptions);
        sAnswerOption.setAdapter(spinnerArrayAdapter);
        sAnswerOption.setOnItemSelectedListener(this);
        sAnswerOption.setSelection(0, false);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateSelectedAnswer();
    }

    private void updateSelectedAnswer() {
        selectedAnswer = (RiskProfileAnswerOption) sAnswerOption.getSelectedItem();
        question.setSelectedAnswerId(selectedAnswer.getId());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @OnClick(R.id.fabPrev)
    void prev() {
        //getBus().send(new RxBusObject(RxBusObject.RxBusKey.RISK_PROFILE_PREVIOUS_FORM, null));
    }

}
