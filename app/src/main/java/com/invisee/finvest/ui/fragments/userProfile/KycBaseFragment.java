package com.invisee.finvest.ui.fragments.userProfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.KycLookup;
import com.invisee.finvest.data.api.requests.KycDataRequest;
import com.invisee.finvest.data.realm.RealmHelper;
import com.invisee.finvest.data.realm.model.KycLookupModel;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.eventBus.RxBusObject;
import org.modelmapper.ModelMapper;
import java.util.ArrayList;
import java.util.List;
import io.realm.RealmResults;

/**
 * Created by fajarfatur on 2/12/16.
 */
public abstract class KycBaseFragment extends BaseFragment {

    public static final String KYC_DATA_REQUEST = "kycDataRequest";

    protected boolean visibleToUser = false;
    protected KycDataRequest request;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().get(KYC_DATA_REQUEST) != null) {
            request = (KycDataRequest) getArguments().getSerializable(KYC_DATA_REQUEST);
        } else {
            request = new KycDataRequest();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        this.visibleToUser = isVisibleToUser;
    }


    @Override
    public void busHandler(RxBusObject.RxBusKey busKey, Object busObject) {
        super.busHandler(busKey, busObject);
        switch (busKey) {
            case GET_NEW_INPUTTED_KYC_DATA:
                if (visibleToUser) {
                    getValidator().validate();
                }
                break;
        }
    }

    protected List<KycLookup>
    getKycLookupFromRealm(String category) {
        List<KycLookup> list = new ArrayList<>();
        ModelMapper modelMapperDefaultValue = new ModelMapper();
        KycLookupModel kycLookupModelDefaultValue = new KycLookupModel();
        kycLookupModelDefaultValue.setCategory(category);
        kycLookupModelDefaultValue.setCode("");
        kycLookupModelDefaultValue.setValue("");
        KycLookup dataFromRealm2 = modelMapperDefaultValue.map(kycLookupModelDefaultValue, KycLookup.class);
        list.add(dataFromRealm2);
        RealmResults<KycLookupModel> kycLookupModels = RealmHelper.readKycLookupListByCategory(realm, category);
        if (kycLookupModels != null) {
            for (KycLookupModel model : kycLookupModels) {
                ModelMapper modelMapper = new ModelMapper();
                KycLookup dataFromRealm =
                        modelMapper.map(model, KycLookup.class); // map to non realm object
                list.add(dataFromRealm);
            }
        }
        return list;
    }

    protected String getKycLookupCodeFromSelectedItemSpinner(Spinner s) {
        return ((KycLookup) s.getSelectedItem()).getCode();
    }

    protected void setupSpinner(Spinner s, List<KycLookup> kycLookupList) {
        ArrayAdapter<KycLookup> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, kycLookupList);
        s.setAdapter(spinnerArrayAdapter);
        s.setSelection(0, false);
    }

    protected void setupSpinnerWithSpecificLookupSelection(Spinner s, List<KycLookup> kycLookupList, String lookupCode) {
        setupSpinner(s, kycLookupList);
        int i = 0;
        for (KycLookup lookup : kycLookupList) {
            if (lookup.getCode().equalsIgnoreCase(lookupCode)) {
                s.setSelection(i, false);
                break;
            }
            i++;
        }
    }

    protected String getAndTrimValueFromEditText(EditText e) {
        return e.getText().toString().trim();
    }

    protected String constructPhoneNumber(EditText countryCode, EditText cityCode, EditText phoneNumber) {
        StringBuilder constructedPhoneNumber = new StringBuilder("");
        constructedPhoneNumber
                .append(getAndTrimValueFromEditText(countryCode))
                .append("-")
                .append(getAndTrimValueFromEditText(cityCode))
                .append("-")
                .append(getAndTrimValueFromEditText(phoneNumber));
        return constructedPhoneNumber.toString();
    }


}
