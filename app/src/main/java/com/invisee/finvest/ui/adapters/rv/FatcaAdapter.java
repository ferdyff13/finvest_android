package com.invisee.finvest.ui.adapters.rv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.FatcaAnswerOption;
import com.invisee.finvest.data.api.beans.FatcaQuestion;
import com.invisee.finvest.ui.fragments.userProfile.FatcaPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fajarfatur on 2/22/16.
 */
public class FatcaAdapter extends RecyclerView.Adapter<FatcaAdapter.FatcaHolder> implements AdapterView.OnItemSelectedListener {

    private static Context context;
    private ArrayList<FatcaQuestion> list;
    private FatcaPresenter presenter;

    public FatcaAdapter(Context context, ArrayList<FatcaQuestion> list, FatcaPresenter presenter) {
        this.context = context;
        this.list = list;
        this.presenter = presenter;
    }


    @Override
    public FatcaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        FatcaHolder holder;

        itemView = LayoutInflater.from(context).inflate(R.layout.row_fatca_question, parent, false);
        holder = new FatcaHolder(itemView);
        return  holder;

    }

    @Override
    public void onBindViewHolder(FatcaHolder holder, int position) {
        FatcaQuestion item = list.get(position);
        holder.tvQuestion.setText(item.getQuestionText());
        /**
         * setup spinner*/
        holder.sAnswerOption.setTag(item);
        setupSpinner(holder.sAnswerOption, item.getAnswerOption());
        /**
         * setup spinner selection & default value*/
        if(item.getAnswerId()==0){ // question not answered yet, default value 0, set to selected item (answer id)
            FatcaAnswerOption answer = (FatcaAnswerOption) holder.sAnswerOption.getSelectedItem();
            item.setAnswerId(answer.getId());
        }else{ // set spinner selection to answered question id (answer id)
            int i = 0;
            for(FatcaAnswerOption answer : item.getAnswerOption()){
                if(answer.getId() == item.getAnswerId()){
                    holder.sAnswerOption.setSelection(i, false);
                    break;
                }
                i++;
            }
        }
        /**
         * give a space on the bottom of list for the FAB area*/
        if(position == getItemCount()-1){
            holder.space.setVisibility(View.VISIBLE);
        }else{
            holder.space.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class FatcaHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvQuestion)
        TextView tvQuestion;
        @Bind(R.id.sAnswerOption)
        Spinner sAnswerOption;
        @Bind(R.id.space)
        View space;

        public FatcaHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setupSpinner(Spinner s, List<FatcaAnswerOption> fatcaAnswerOptions) {
        if (fatcaAnswerOptions == null) fatcaAnswerOptions = new ArrayList<>();
        ArrayAdapter<FatcaAnswerOption> spinnerArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner, fatcaAnswerOptions);
        s.setAdapter(spinnerArrayAdapter);
        s.setOnItemSelectedListener(this);
        s.setSelection(1, false);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        FatcaQuestion question = (FatcaQuestion) spinner.getTag();
        if (question != null) {
            FatcaAnswerOption answer = (FatcaAnswerOption) spinner.getSelectedItem();
            question.setAnswerId(answer.getId());
            if (question.getQuestionName().equals("Q11")) {
                if (answer.getAnswerText().equals("Yes")) {
                    presenter.setShowCrs();
            } else if (answer.getAnswerText().equals("No")) {
                    presenter.setHideCrs();
                }
        }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
