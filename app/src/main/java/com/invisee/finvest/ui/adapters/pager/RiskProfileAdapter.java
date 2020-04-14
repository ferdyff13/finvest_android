package com.invisee.finvest.ui.adapters.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.RiskProfileAnswerOption;
import com.invisee.finvest.data.api.beans.RiskProfileQuestion;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.fragments.userProfile.RiskProfileQuestionFragment;
import com.invisee.finvest.util.ui.GlobalVariable;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fajarfatur on 2/22/16.
 */


public class RiskProfileAdapter extends RecyclerView.Adapter<RiskProfileAdapter.RiskProfileHolder> implements AdapterView.OnItemSelectedListener {

    private static Context context;
    private ArrayList<RiskProfileQuestion> list;
    private Object itemId;
    private boolean status;

    public RiskProfileAdapter(Context context, ArrayList<RiskProfileQuestion> list, Boolean status) {
        this.status = status;
        this.context = context;
        this.list = list;
    }

    @Override
    public RiskProfileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.row_risk_profile_question, parent, false);
        RiskProfileHolder holder = new RiskProfileHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RiskProfileHolder holder, int position) {
        if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("VER") || PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("PEN")) {
               /* status = false;*/
            GlobalVariable.getInstance().status_next = false;
            holder.sAnswerOption.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    GlobalVariable.getInstance().status_next = true;
                  /*  status = true;*/
                    return false;
                }
            });
        } else {
            GlobalVariable.getInstance().status_next = true;
           // status = true;
        }

        RiskProfileQuestion item = list.get(position);
        holder.tvQuestion.setText(item.getQuestionText());

        /**
         * setup spinner*/
        holder.sAnswerOption.setTag(item);

        setupSpinner(holder.sAnswerOption, item.getAnswerOption());
        /**
         * setup spinner selection & default value*/
        try{
            itemId = item.getAnswerId().get(0);
        }
        catch(IndexOutOfBoundsException e)
        {
            itemId = 0;
        }

         if( (Integer) itemId == 0 ){ // question not answered yet, default value 0, set to selected item (answer id)
            RiskProfileAnswerOption answer = (RiskProfileAnswerOption) holder.sAnswerOption.getSelectedItem();
            //Modify to single page
            List<Integer> intList = new ArrayList<Integer>();
             intList.add(answer.getId());
            item.setAnswerId(intList);
        }else{ // set spinner selection to answered question id (answer id)
            int i = 0;
            for(RiskProfileAnswerOption answer : item.getAnswerOption()){
                if(answer.getId() == item.getAnswerId().get(0)){
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

    public static class RiskProfileHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvQuestion)
        TextView tvQuestion;
        @Bind(R.id.sAnswerOption)
        Spinner sAnswerOption;
        @Bind(R.id.space)
        View space;

        public RiskProfileHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setupSpinner(Spinner s, List<RiskProfileAnswerOption> fatcaAnswerOptions) {
        if (fatcaAnswerOptions == null) fatcaAnswerOptions = new ArrayList<>();
        ArrayAdapter<RiskProfileAnswerOption> spinnerArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner, fatcaAnswerOptions);
        s.setAdapter(spinnerArrayAdapter);
        s.setOnItemSelectedListener(this);
        s.setSelection(0, false);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        RiskProfileQuestion question = (RiskProfileQuestion) spinner.getTag();
        if (question != null) {
            RiskProfileAnswerOption answer = (RiskProfileAnswerOption) spinner.getSelectedItem();
            //Modify to single page
            List<Integer> intList = new ArrayList<Integer>();
            intList.add(answer.getId());
            question.setAnswerId(intList);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


/*
public class RiskProfileAdapter extends FragmentPagerAdapter {

    private List<RiskProfileQuestion> riskProfileQuestionList;

    public RiskProfileAdapter(FragmentManager fm, List<RiskProfileQuestion> riskProfileQuestionList) {
        super(fm);
        this.riskProfileQuestionList = riskProfileQuestionList;
    }

    @Override
    public Fragment getItem(int position) {
        RiskProfileQuestion question = riskProfileQuestionList.get(position);
        return RiskProfileQuestionFragment.initiateFragment(question);
    }

    @Override
    public int getCount() {
        return riskProfileQuestionList != null ? riskProfileQuestionList.size() : 0;
    }
}*/
