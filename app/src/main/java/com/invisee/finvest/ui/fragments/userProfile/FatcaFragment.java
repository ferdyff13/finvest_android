package com.invisee.finvest.ui.fragments.userProfile;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.AnswerOption;
import com.invisee.finvest.data.api.beans.AnswerReason;
import com.invisee.finvest.data.api.beans.ChildAnswerLoad;
import com.invisee.finvest.data.api.beans.Country;
import com.invisee.finvest.data.api.beans.FatcaQuestion;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.KycActivityNew;
import com.invisee.finvest.ui.activities.RiskProfileActivity;
import com.invisee.finvest.ui.adapters.rv.FatcaAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by fajarfatur on 1/13/16.
 */
public class FatcaFragment extends BaseFragment{

    public static final String TAG = FatcaFragment.class.getSimpleName();

    @Bind(R.id.rv)
    RecyclerView rv;
//    @Bind(R.id.rvCrs)
//    RecyclerView rvCrs;
    @Bind(R.id.lnCrsField)
    LinearLayout lnCrsField;
    @State
    ArrayList<FatcaQuestion> questions;
    @Bind(R.id.tv_completeness)
    TextView tvCompleteness;
    @Bind(R.id.pb_completeness)
    ProgressBar pbCompleteness;
    @Bind(R.id.togglebutton)
    Switch toggleBtn;

    @Bind(R.id.tvQuestOneFieldOne)
    TextView tvQuestOneFieldOne;
    @Bind(R.id.tvQuestTwoFieldOne)
    TextView tvQuestTwoFieldOne;
    @Bind(R.id.tvQuestThreeFieldOne)
    TextView tvQuestThreeFieldOne;

    @Bind(R.id.tvQuestOneFieldTwo)
    TextView tvQuestOneFieldTwo;
    @Bind(R.id.tvQuestTwoFieldTwo)
    TextView tvQuestTwoFieldTwo;
    @Bind(R.id.tvQuestThreeFieldTwo)
    TextView tvQuestThreeFieldTwo;

    @Bind(R.id.tvQuestOneFieldThree)
    TextView tvQuestOneFieldThree;
    @Bind(R.id.tvQuestTwoFieldThree)
    TextView tvQuestTwoFieldThree;
    @Bind(R.id.tvQuestThreeFieldThree)
    TextView tvQuestThreeFieldThree;

    @Bind(R.id.sAnswerCountryOne)
    Spinner sAnswerCountryOne;
    @Bind(R.id.sAnswerCountryTwo)
    Spinner sAnswerCountryTwo;
    @Bind(R.id.sAnswerCountryThree)
    Spinner sAnswerCountryThree;

    @Bind(R.id.containerAnswerOne)
    LinearLayout containerCountryOne;
    @Bind(R.id.containerAnswerTwo)
    LinearLayout containerCountryTwo;
    @Bind(R.id.containerAnswerThree)
    LinearLayout containerCountryThree;

    @Bind(R.id.etCrsFieldOne)
    EditText etCrsFieldOne;
    @Bind(R.id.etCrsFieldTwo)
    EditText etCrsFieldTwo;
    @Bind(R.id.etCrsFieldThree)
    EditText etCrsFieldThree;

    @Bind(R.id.sAnswerTinOne)
    Spinner sAnswerTinOne;
    @Bind(R.id.sAnswerTinTwo)
    Spinner sAnswerTinTwo;
    @Bind(R.id.sAnswerTintThree)
    Spinner sAnswerTintThree;

    @Bind(R.id.etAsnwerTinOtherOne)
    EditText etAsnwerTinOtherOne;
    @Bind(R.id.etAsnwerTinOtherTwo)
    EditText etAsnwerTinOtherTwo;
    @Bind(R.id.etAsnwerTinOtherThree)
    EditText etAsnwerTinOtherThree;

    @Bind(R.id.lnAddField)
    LinearLayout lnAddField;
    @Bind(R.id.imgDelteFieldTwo)
    ImageView imgDelteFieldTwo;
    @Bind(R.id.imgDelteFieldThree)
    ImageView imgDelteFieldThree;
    @Bind(R.id.imgDeleteField)
    ImageView imgDeleteField;

    @Bind(R.id.lnProgressBar)
    LinearLayout lnProgressBar;
    @Bind(R.id.lnDismissBar)
    RelativeLayout lnDismissBar;
    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;
    @Bind(R.id.lnConnectionError)
    LinearLayout lnConnectionError;

    @Bind(R.id.mainRvContainer)
    LinearLayout mainRvContainer;

    public Integer countryIdOne, countryIdTwo, countryIdThree;
    public String tinOne, tinTwo, tinThree;
    public String reasonOne, reasonTwo, reasonThree;
    public List<Country> countries;
    public List<Integer> countryIdList = new ArrayList<Integer>();;
    public List<String> reasonList = new ArrayList<String>();;
    public List<String> tinList =  new ArrayList<String>();;
    public List<ChildAnswerLoad> childAnswerLoadList = new ArrayList<>();
    public List<AnswerOption> answerOptionList = new ArrayList<>();
    public LinearLayoutManager llManager;

    private long mLastClickTime = 0;
    private FatcaPresenter presenter;
    private FatcaFragment fragment;
    private boolean changed = false;
    private boolean oldChecked = false;
    private boolean status;
    private Integer currentField = 0;
    private int defaultIndexCountry = 0;
    private Country country;
    private AnswerReason answerReason;
    private String answerOption;

    private String answerNo = "9,62;10,64;11,46;12,48;13,50;14,52;15,54;16,56;17,58;18,60";
    private String answerYes = "9,61;10,63;11,65;12,48;13,49;14,51;15,53;16,55;17,57;18,59";
    private String answeFatca = "";


    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, new FatcaFragment(), TAG);
            fragmentTransaction.commit();
        }
    }


    @Override
    protected int getLayout() {
        return R.layout.f_fatca;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FatcaPresenter(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRV();

        if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("VER") || PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("PEN")) {
            status = false;
            changedValidation();
        } else {
            status = true;
        }

        presenter.getAllCountry();
        presenter.getCompleteness();
        onToggleChanged();
        getSelectedItem();
    }

    //=============================== VIEW ================================

    private void initRV() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.user_profile_fatca);
        toggleBtn.setChecked(false);
        oldChecked = toggleBtn.isChecked();
    }

    public void fetchQuestionToLayout(){
        llManager = new LinearLayoutManager(getActivity());
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llManager);
        rv.setAdapter(new FatcaAdapter(getActivity(), questions, presenter));
    }

    public void loadChildAnswer() {
        lnCrsField.setVisibility(View.VISIBLE);

        //MARK - Static Load, Not recommended code. Try to change it dynamically in the future

        if (questions.get(10).getChildAnswerLoad().size() > 0) {
            containerCountryOne.setVisibility(View.VISIBLE);

            if (questions.get(10).getChildAnswerLoad().get(0).getCAnswerTin()!= null) {
                etCrsFieldOne.setText(questions.get(10).getChildAnswerLoad().get(0).getCAnswerTin());
            }

            if (questions.get(10).getChildAnswerLoad().get(0).getCAnswerReason() != null) {
                etAsnwerTinOtherOne.setText(questions.get(10).getChildAnswerLoad().get(0).getCAnswerReason());
            }
        }

        if (questions.get(10).getChildAnswerLoad().size() > 1) {
            containerCountryTwo.setVisibility(View.VISIBLE);

            if (questions.get(10).getChildAnswerLoad().get(1).getCAnswerTin() != null) {
                etCrsFieldTwo.setText(questions.get(10).getChildAnswerLoad().get(1).getCAnswerTin());
            }

            if (questions.get(10).getChildAnswerLoad().get(1).getCAnswerReason() != null) {
                etAsnwerTinOtherTwo.setText(questions.get(10).getChildAnswerLoad().get(1).getCAnswerReason());
            }
        }

        if (questions.get(10).getChildAnswerLoad().size() > 2) {
            containerCountryThree.setVisibility(View.VISIBLE);
            lnAddField.setVisibility(View.GONE);

            if (questions.get(10).getChildAnswerLoad().get(2).getCAnswerTin() != null) {
                etCrsFieldThree.setText(questions.get(10).getChildAnswerLoad().get(2).getCAnswerTin());

            }

            if (questions.get(10).getChildAnswerLoad().get(2).getCAnswerReason() != null) {
                etAsnwerTinOtherThree.setText(questions.get(10).getChildAnswerLoad().get(2).getCAnswerReason());

            }

        }

        if (!etCrsFieldOne.getText().toString().isEmpty())  {
            sAnswerTinOne.setEnabled(false);
        }

        if (!etCrsFieldTwo.getText().toString().isEmpty())  {
            sAnswerTinTwo.setEnabled(false);
        }

        if (!etCrsFieldThree.getText().toString().isEmpty())  {
            sAnswerTintThree.setEnabled(false);
        }
    }

    private void setCountryAnswer() {

        //MARK - Static Load, Not recommended code. Try to change it dynamically in the future

        String countryOne = null, countryTwo = null, countryThree = null;
        String idReasonOne = null, idReasonTwo = null, idReasonThree = null;

        List<String> answerPosition = new ArrayList<>();
        answerPosition.add("Select Reason");

        for (int i = 0; i < questions.get(10).getFatcaChilds().size(); i++) {
            for (int j = 0; j < questions.get(10).getFatcaChilds().get(i).getAnswerOption().size(); j++) {
                answerPosition.add(questions.get(10).getFatcaChilds().get(i).getAnswerOption().get(j).getAnswerText());

            }
        }

        for (int i = 0; i < questions.get(10).getFatcaChilds().get(2).getAnswerOption().size(); i++) {
            answerOptionList.add(questions.get(10).getFatcaChilds().get(2).getAnswerOption().get(i));
        }

        if (questions.get(10).getChildAnswerLoad().size() > 0) {
            if (questions.get(10).getChildAnswerLoad().get(0).getCAnswerCountry() != null) {
                countryOne = questions.get(10).getChildAnswerLoad().get(0).getCAnswerCountry();
            }

            if (questions.get(10).getChildAnswerLoad().get(0).getcAnswerReasoObject() == null) {
                if (questions.get(10).getChildAnswerLoad().get(0).getCAnswerTin().equals("")) {
                    idReasonOne = "Other reasons, please explain";
                } else {
                    idReasonOne = "Select Reason";
                }

            } else {
                idReasonOne = questions.get(10).getChildAnswerLoad().get(0).getcAnswerReasoObject().getAnswerText();

            }
        }

        if (questions.get(10).getChildAnswerLoad().size() > 1) {

            if (questions.get(10).getChildAnswerLoad().get(1).getCAnswerCountry() != null) {
                countryTwo = questions.get(10).getChildAnswerLoad().get(1).getCAnswerCountry();
            }

            if (questions.get(10).getChildAnswerLoad().get(1).getcAnswerReasoObject() == null) {
                if (questions.get(10).getChildAnswerLoad().get(1).getCAnswerTin().equals("")) {
                    idReasonTwo = "Other reasons, please explain";
                } else {
                    idReasonTwo = "Select Reason" ;
                }

            } else {
                idReasonTwo = questions.get(10).getChildAnswerLoad().get(1).getcAnswerReasoObject().getAnswerText();

            }
        }

        if (questions.get(10).getChildAnswerLoad().size() > 2) {
            if (questions.get(10).getChildAnswerLoad().get(2).getCAnswerCountry() != null) {
                countryThree = questions.get(10).getChildAnswerLoad().get(2).getCAnswerCountry();
            }

            if (questions.get(10).getChildAnswerLoad().get(2).getcAnswerReasoObject() == null) {
                if (questions.get(10).getChildAnswerLoad().get(2).getCAnswerTin().equals("")) {
                    idReasonThree = "Other reasons, please explain";
                } else {
                    idReasonThree = "Select Reason";
                }
            } else {
                idReasonThree = questions.get(10).getChildAnswerLoad().get(2).getcAnswerReasoObject().getAnswerText();
            }

        }

        if (countryOne == null) {
            countryOne = "Select Country";
        } else if (countryTwo == null) {
            countryTwo = "Select Country";
        } else if (countryThree == null) {
            countryThree = "Select Country";
        }

        if (idReasonOne == null) {
            idReasonOne = "Select Reason";
        }

        if (idReasonTwo == null) {
            idReasonTwo = "Select Reason" ;

        }

        if (idReasonThree == null) {
            idReasonThree = "Select Reason";

        }

        Country defaultCountry = new Country();
        defaultCountry.setCountryName("Select Country");
        defaultCountry.setId(0);
        defaultCountry.setNumericCode("");
        defaultCountry.setAtCountryCode("");
        defaultCountry.setAlpha3Code("");
        countries.add(0, defaultCountry);

        setupSpinnerCountry(sAnswerCountryOne, countries, countryOne);
        setupSpinnerCountry(sAnswerCountryTwo, countries, countryTwo);
        setupSpinnerCountry(sAnswerCountryThree, countries, countryThree);
        setupSpinnerAnswer(sAnswerTinOne, answerPosition, idReasonOne);
        setupSpinnerAnswer(sAnswerTinTwo, answerPosition, idReasonTwo);
        setupSpinnerAnswer(sAnswerTintThree, answerPosition, idReasonThree);
    }

    public void setCRSView() {

        tvQuestOneFieldOne.setText(questions.get(10).getFatcaChilds().get(0).getQuestionText());
        tvQuestTwoFieldOne.setText(questions.get(10).getFatcaChilds().get(1).getQuestionText());
        tvQuestThreeFieldOne.setText(questions.get(10).getFatcaChilds().get(2).getQuestionText());

        tvQuestOneFieldTwo.setText(questions.get(10).getFatcaChilds().get(0).getQuestionText());
        tvQuestTwoFieldTwo.setText(questions.get(10).getFatcaChilds().get(1).getQuestionText());
        tvQuestThreeFieldTwo.setText(questions.get(10).getFatcaChilds().get(2).getQuestionText());

        tvQuestOneFieldThree.setText(questions.get(10).getFatcaChilds().get(0).getQuestionText());
        tvQuestTwoFieldThree.setText(questions.get(10).getFatcaChilds().get(1).getQuestionText());
        tvQuestThreeFieldThree.setText(questions.get(10).getFatcaChilds().get(2).getQuestionText());

        setCountryAnswer();
        setTINAnswer();

    }

    private void setTINAnswer() {
        List<String> answerPosition = new ArrayList<>();

        for (int i = 0; i < questions.get(10).getFatcaChilds().size(); i++) {
            for (int j = 0; j < questions.get(10).getFatcaChilds().get(i).getAnswerOption().size(); j++) {
                answerPosition.add(questions.get(10).getFatcaChilds().get(i).getAnswerOption().get(j).getAnswerText());

            }
        }
    }

    //================================ GET DATA =================================

    private void getSelectedItem() {

        sAnswerTinOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 3) {
                    etAsnwerTinOtherOne.setVisibility(View.VISIBLE);
                }

                if (position == 0) {
                    etCrsFieldOne.setEnabled(true);
                } else {
                    etCrsFieldOne.setEnabled(false);
                }

                if (position != 3){
                    etAsnwerTinOtherOne.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sAnswerTinTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 3) {
                    etAsnwerTinOtherTwo.setVisibility(View.VISIBLE);
                }

                if (position == 0) {
                    etCrsFieldTwo.setEnabled(true);

                } else {
                    etCrsFieldTwo.setEnabled(false);
                }

                if (position != 3) {
                    etAsnwerTinOtherTwo.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sAnswerTintThree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 3) {
                    etAsnwerTinOtherThree.setVisibility(View.VISIBLE);
                }

                if (position == 0) {
                    etCrsFieldThree.setEnabled(true);
                } else {
                    etCrsFieldThree.setEnabled(false);
                }

                if (position != 3) {
                    etAsnwerTinOtherThree.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void getSelectedCountry() {
        countryIdList.clear();
        countryIdOne = countries.get(sAnswerCountryOne.getSelectedItemPosition()).getId();
        countryIdList.add(countryIdOne);

        if (containerCountryTwo.getVisibility() == View.VISIBLE) {
            countryIdTwo = countries.get(sAnswerCountryTwo.getSelectedItemPosition()).getId();
            countryIdList.add(countryIdTwo);
        }

        if (containerCountryThree.getVisibility() == View.VISIBLE) {
            countryIdThree = countries.get(sAnswerCountryThree.getSelectedItemPosition()).getId();
            countryIdList.add(countryIdThree);
        }

    }

    private void getTinAnswer() {
        tinList.clear();
        if (!etCrsFieldOne.getText().toString().isEmpty() || !etCrsFieldOne.getText().toString().equals("")) {
            tinOne = etCrsFieldOne.getText().toString();
            tinList.add(tinOne);
        } else {
            tinOne = "";
            tinList.add(tinOne);
        }

        if (etCrsFieldTwo.getVisibility() == View.VISIBLE) {
            if (!etCrsFieldTwo.getText().toString().isEmpty() || !etCrsFieldTwo.getText().toString().equals("")) {
                tinTwo = etCrsFieldTwo.getText().toString();
                tinList.add(tinTwo);
            } else {
                tinTwo = "";
                tinList.add(tinTwo);
            }
        }

        if (etCrsFieldThree.getVisibility() == View.VISIBLE) {
            if (!etCrsFieldThree.getText().toString().isEmpty() || !etCrsFieldThree.getText().toString().equals("")) {
                tinThree = etCrsFieldThree.getText().toString();
                tinList.add(tinThree);
            } else  {
                tinThree = "";
                tinList.add(tinThree);
            }
        }
    }

    private void getReasonAnswer() {
        reasonList.clear();
        for (int i = 0; i < questions.get(10).getFatcaChilds().size(); i++) {
            for (int j = 0; j < questions.get(10).getFatcaChilds().get(i).getAnswerOption().size(); j++) {

                if (sAnswerTinOne.getVisibility() == View.VISIBLE) {
                    if (sAnswerTinOne.getSelectedItemPosition() != 0) {
                        reasonOne = questions.get(10).getFatcaChilds().get(i).getAnswerOption().get(sAnswerTinOne.getSelectedItemPosition() -1).getAnswerName();
                    } else {
                        reasonOne = "";
                    }
                }

                if (sAnswerTinTwo.getVisibility() == View.VISIBLE) {
                    if (sAnswerTinTwo.getSelectedItemPosition() != 0) {
                        reasonTwo = questions.get(10).getFatcaChilds().get(i).getAnswerOption().get(sAnswerTinTwo.getSelectedItemPosition() -1).getAnswerName();
                    } else {
                        reasonTwo = "";
                    }
                }
                if (sAnswerTintThree.getVisibility() == View.VISIBLE) {
                    if (sAnswerTintThree.getSelectedItemPosition() != 0) {
                        reasonThree = questions.get(10).getFatcaChilds().get(i).getAnswerOption().get(sAnswerTintThree.getSelectedItemPosition() -1).getAnswerName();
                    } else  {
                        reasonThree = "";
                    }
                }
            }
        }

        if (etAsnwerTinOtherOne.getVisibility() == View.VISIBLE) {
            reasonOne = etAsnwerTinOtherOne.getText().toString();
        }

        if (etAsnwerTinOtherTwo.getVisibility() == View.VISIBLE) {
            reasonTwo = etAsnwerTinOtherTwo.getText().toString();
        }

        if (etAsnwerTinOtherThree.getVisibility() == View.VISIBLE) {
            reasonThree = etAsnwerTinOtherThree.getText().toString();
        }

        if (sAnswerTinTwo.getVisibility() == View.VISIBLE) {
            reasonList.add(reasonOne);
        }

        if (sAnswerTinTwo.getVisibility() == View.VISIBLE){

            reasonList.add(reasonTwo);
        }

        if (sAnswerTintThree.getVisibility() == View.VISIBLE){
            reasonList.add(reasonThree);

        }

    }

    //================================== SAVE DATA =================================

    private void saveFatca() {
        Boolean flag = false;
        if (toggleBtn.isChecked()) {
            if (lnCrsField.getVisibility() == View.VISIBLE){

                if (containerCountryOne.getVisibility() == View.VISIBLE){
                    if (sAnswerCountryOne.getSelectedItemPosition() == 0) {
                        ((TextView)sAnswerCountryOne.getSelectedView()).setError("");
                        flag = false;
                    } else if (sAnswerTinOne.getSelectedItemPosition() == 0 && etCrsFieldOne.getText().toString().isEmpty()) {
                       ((TextView)sAnswerTinOne.getSelectedView()).setError("");
                        flag = false;
                   } else {
                        flag = true;
                   }
                }

                if (containerCountryTwo.getVisibility() == View.VISIBLE) {
                    if (sAnswerCountryTwo.getSelectedItemPosition() == 0) {
                        ((TextView)sAnswerCountryTwo.getSelectedView()).setError("");
                        flag = false;
                    } else if (sAnswerTinTwo.getSelectedItemPosition() == 0 && etCrsFieldTwo.getText().toString().isEmpty()) {
                        ((TextView)sAnswerTinTwo.getSelectedView()).setError("");
                        flag = false;
                    } else  {
                        flag = true;
                    }
                }

                if (containerCountryThree.getVisibility() == View.VISIBLE) {
                    if (sAnswerCountryThree.getSelectedItemPosition() == 0) {
                        ((TextView)sAnswerCountryThree.getSelectedView()).setError("");
                        flag = false;
                    } else if (sAnswerTintThree.getSelectedItemPosition() == 0 && etCrsFieldThree.getText().toString().isEmpty()) {
                        ((TextView)sAnswerTintThree.getSelectedView()).setError("");
                        flag = false;
                    } else {
                        flag = true;
                    }
                }

                if (flag) {
                    saveAllValue();
                }
            } else {
                presenter.saveFatca(presenter.constructBatchFatca(changed,toggleBtn.isChecked(),questions));
            }
        } else {
            presenter.saveFatca(presenter.constructBatch(changed,toggleBtn.isChecked(),questions));
        }

    }

    private void saveAllValue() {
        getSelectedCountry();
        getTinAnswer();
        getReasonAnswer();
        presenter.saveFatca(presenter.constructBatchFatca(changed,toggleBtn.isChecked(),questions));
    }

    //================================== HELPER =================================

    public void setupSpinnerCountry(Spinner s, List<Country> countries, String selectionCountryId) {
        if (countries == null) countries = new ArrayList<>();

        ArrayAdapter<Country> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, countries);
        s.setAdapter(spinnerArrayAdapter);
        s.setSelection(defaultIndexCountry, false); // set to default (Indonesia)
        if (selectionCountryId != null) { // set selection to saved data from realm / ws
            int i = 0;
            for (Country country : countries) {
                if (country.getCountryName().equals(selectionCountryId)) {
                    s.setSelection(i, false);
                    this.country = country;
                    break;
                }
                i++;
            }
        }
    }

    public void validation() {

        final List<String> answerPosition = new ArrayList<>();
        answerPosition.add("Select Reason");

        for (int i = 0; i < questions.get(10).getFatcaChilds().size(); i++) {
            for (int j = 0; j < questions.get(10).getFatcaChilds().get(i).getAnswerOption().size(); j++) {
                answerPosition.add(questions.get(10).getFatcaChilds().get(i).getAnswerOption().get(j).getAnswerText());

            }
        }

        etCrsFieldOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!s.toString().equalsIgnoreCase("")) {
                    sAnswerTinOne.setEnabled(false);
                    etAsnwerTinOtherOne.setVisibility(View.GONE);
                    setupSpinnerAnswer(sAnswerTinOne, answerPosition, "Select Reason");
                } else {
                    sAnswerTinOne.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equalsIgnoreCase("")) {
                    sAnswerTinOne.setEnabled(false);
                    etAsnwerTinOtherOne.setVisibility(View.GONE);
                    setupSpinnerAnswer(sAnswerTinOne, answerPosition, "Select Reason");
                } else {
                    sAnswerTinOne.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCrsFieldTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equalsIgnoreCase("")) {
                    sAnswerTinTwo.setEnabled(false);
                    etAsnwerTinOtherTwo.setVisibility(View.GONE);
                    setupSpinnerAnswer(sAnswerTinTwo, answerPosition, "Select Reason");
                } else {
                    sAnswerTinTwo.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCrsFieldThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equalsIgnoreCase("")) {
                    sAnswerTintThree.setEnabled(false);
                    etAsnwerTinOtherThree.setVisibility(View.GONE);
                    setupSpinnerAnswer(sAnswerTintThree, answerPosition, "Select Reason");

                } else {
                    sAnswerTintThree.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void setupSpinnerAnswer(Spinner s, List<String> fatcaAnswerOptions, String answerText) {
        if (fatcaAnswerOptions == null) fatcaAnswerOptions = new ArrayList<>();
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner, fatcaAnswerOptions);
        s.setAdapter(spinnerArrayAdapter);
        for (int position = 0; position < fatcaAnswerOptions.size(); position++) {
            if(fatcaAnswerOptions.get(position).equals(answerText)) {
                s.setSelection(position);
                return;
            }
        }
    }

    private void changedValidation() {
        toggleBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                status = true;
                return false;
            }
        });
    }

    public void toNextForm() {
        RiskProfileActivity.startActivity((BaseActivity) getActivity());
    }

    private void valiadationTouchListener() {

//        if (!status){
//            RiskProfileActivity.startActivity((BaseActivity) getActivity());
//        } else {
//            if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("VER") || PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("PEN")) {
//                showDialogToSaveData();
//            } else {
//                saveFatca();
//            }
//        }

        saveFatca();
    }

    //================================== ON CLICK =================================

    public void onClickAnswer() {

        final List<String> answerPosition = new ArrayList<>();
        answerPosition.add("Select Reason");

        for (int i = 0; i < questions.get(10).getFatcaChilds().size(); i++) {
            for (int j = 0; j < questions.get(10).getFatcaChilds().get(i).getAnswerOption().size(); j++) {
                answerPosition.add(questions.get(10).getFatcaChilds().get(i).getAnswerOption().get(j).getAnswerText());

            }
        }


        lnAddField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentField = currentField + 1;

                if (currentField > 2) {
                    currentField = 2;
                }

                if (currentField == 1) {
                    containerCountryTwo.setVisibility(View.VISIBLE);
                }

                if (currentField == 2) {
                    containerCountryThree.setVisibility(View.VISIBLE);
                    lnAddField.setVisibility(View.GONE);
                }
            }
        });


        imgDeleteField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentField = currentField - 1;

                if (currentField < 0) {
                    currentField = 0;
                }

                setupSpinnerCountry(sAnswerCountryOne, countries, "Select Country");
                etCrsFieldOne.setText("");
                setupSpinnerAnswer(sAnswerTinOne, answerPosition, "Select Reason");

            }
        });

        imgDelteFieldTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                containerCountryTwo.setVisibility(View.GONE);
                currentField = currentField - 1;
            }
        });

        imgDelteFieldThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentField = currentField - 2;
                containerCountryThree.setVisibility(View.GONE);
                lnAddField.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick(R.id.togglebutton)
    public void onToggleChanged(){

        if (oldChecked != toggleBtn.isChecked()){
            mainRvContainer.setVisibility(View.VISIBLE);
        } else {
            mainRvContainer.setVisibility(View.GONE);
//            lnCrsField.setVisibility(View.GONE);
        }
//        if (oldChecked != toggleBtn.isChecked()){
//           changed = true;
//           answeFatca = answerYes;
//        } else{
//            changed = false;
//            answeFatca = answerNo;
//        }

        changed = oldChecked != toggleBtn.isChecked();


    }

    @OnClick(R.id.iv_left)
    void prev() {
        KycActivityNew.startActivity((BaseActivity) getActivity(), 4);
    }

    @OnClick(R.id.iv_right)
    void next() {
        valiadationTouchListener();

    }

    //================================== MATERIAL =================================

    public void showProgressBar(){
        pbLoading.setVisibility(View.VISIBLE);
        lnConnectionError.setVisibility(View.GONE);
        lnProgressBar.setVisibility(View.VISIBLE);
        lnDismissBar.setVisibility(View.GONE);
    }

    public void dismissProgressBar(){
        lnProgressBar.setVisibility(View.GONE);
        lnDismissBar.setVisibility(View.VISIBLE);
    }

    public void connectionError() {
        lnProgressBar.setVisibility(View.VISIBLE);
        lnDismissBar.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
        lnConnectionError.setVisibility(View.VISIBLE);
    }


    void showDialogFailedSubmit(String info) {
        new MaterialDialog.Builder(getActivity())
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(getString(R.string.infortmation).toUpperCase())
                .titleColor(Color.WHITE)
                .content(info)
                .contentColor(Color.WHITE)
                .positiveText(R.string.ok)
                .positiveColor(Color.WHITE)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    void showDialogToSaveData() {
        new MaterialDialog.Builder(getActivity())
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(getString(R.string.infortmation).toUpperCase())
                .titleColor(Color.WHITE)
                .content(R.string.kyc_update_data)
                .contentColor(Color.WHITE)
                .positiveText(R.string.btn_dialog_ya)
                .positiveColor(Color.WHITE)
                .negativeText(R.string.btn_dialog_tidak)
                .negativeColor(cPrimary)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        saveFatca();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        fetchQuestionToLayout();
                        status = false;
                        RiskProfileActivity.startActivity((BaseActivity) getActivity());
                    }
                })
                .show();
    }
}
