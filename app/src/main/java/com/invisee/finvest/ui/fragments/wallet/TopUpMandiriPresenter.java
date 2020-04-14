package com.invisee.finvest.ui.fragments.wallet;

import android.widget.Toast;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.requests.TopUpViseePayRequest;
import com.invisee.finvest.data.api.responses.TopUpViseePayResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.TopUpSummaryActivity;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pandu.abbiyuarsyah on 19/10/2017.
 */

public class TopUpMandiriPresenter {

    private TopUpMandiriFragment fragment;

    public TopUpMandiriPresenter(TopUpMandiriFragment fragment) {
        this.fragment = fragment;
    }

    public TopUpViseePayRequest constructviseepayrequest(){
        TopUpViseePayRequest topUpViseePayRequest = new TopUpViseePayRequest();
        String amount = fragment.etAmount.getText().toString().replaceAll("[.]","");
        topUpViseePayRequest.setTrxAmount(amount);
        topUpViseePayRequest.setBank("Mandiri");
        return topUpViseePayRequest;
    }

    void topUpViseePay() {
        fragment.showProgressDialog(fragment.getString(R.string.please_wait));
        fragment.getApi().topUpViseePay(PrefHelper.getString(PrefKey.TOKEN), constructviseepayrequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<TopUpViseePayResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        fragment.showFailedDialog(fragment.connectionError);
                    }

                    @Override
                    public void onNext(TopUpViseePayResponse response) {
                        fragment.dismissProgressDialog();
                        if (response.getCode() == 1) {
                            Toast.makeText(fragment.getContext(), response.getInfo(), Toast.LENGTH_SHORT).show();
                            TopUpSummaryActivity.startActivity((BaseActivity) fragment.getActivity(), response.getData());
                        } else {
                            Toast.makeText(fragment.getContext(), response.getInfo(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

}
