package com.invisee.finvest.ui.fragments.wallet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.ui.fragments.support.SupportFragment;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pandu.abbiyuarsyah on 17/10/2017.
 */

public class TopUpMandiriFragment extends BaseFragment {

    public static final String TAG = TopUpMandiriFragment.class.getSimpleName();

    @Bind(R.id.etAmount)
    EditText etAmount;

    private DecimalFormat df;
    private DecimalFormat dfnd;
    private boolean hasFractionalPart;
    private String current = "";

    private TopUpMandiriPresenter presenter;


    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.container, new TopUpMandiriFragment(), TAG);
            fragmentTransaction.commit();
        }
    }


    @Override
    protected int getLayout() {
        return R.layout.f_top_up_mandiri;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TopUpMandiriPresenter(this);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        amountFormatter();
    }

    @OnClick(R.id.btnTopUp)
    void topUp(){
        presenter.topUpViseePay();
    }

    void amountFormatter(){
        df = new DecimalFormat("#,###.##", new DecimalFormatSymbols(Locale.GERMAN));
        df.setDecimalSeparatorAlwaysShown(true);
        dfnd = new DecimalFormat("#,###.##", new DecimalFormatSymbols(Locale.GERMAN));
        hasFractionalPart = false;
        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals(current)){
                    etAmount.removeTextChangedListener(this);

                    String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");

                    try {
                        Number n = df.parse(v);
                        if (hasFractionalPart) {
                            etAmount.setText(df.format(n));
                            etAmount.setSelection(etAmount.getText().length());
                        } else {
                            etAmount.setText(dfnd.format(n));
                            etAmount.setSelection(etAmount.getText().length());
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    etAmount.addTextChangedListener(this);


                }
            }
        });
    }




}
