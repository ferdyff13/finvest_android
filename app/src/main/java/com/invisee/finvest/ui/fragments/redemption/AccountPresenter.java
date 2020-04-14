package com.invisee.finvest.ui.fragments.redemption;

import android.graphics.Color;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.requests.ConfirmationPartialRedemptionRequest;
import com.invisee.finvest.data.api.requests.ConfirmationRedemptionRequest;
import com.invisee.finvest.data.api.requests.CreatePartialRedemptionRequest;
import com.invisee.finvest.data.api.requests.CreateRedemptionRequest;
import com.invisee.finvest.data.api.responses.ConfirmationRedemptionResponse;
import com.invisee.finvest.data.api.responses.CreateRedemptionResponse;
import com.invisee.finvest.data.api.responses.GeneratePINResponse;
import com.invisee.finvest.data.api.responses.RedemptionOrderResponse;
import com.invisee.finvest.data.api.responses.SettlementInfoResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.MainActivity;

import java.util.Random;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 3/16/16.
 */
public class AccountPresenter {

    private AccountFragment fragment;
    private CreateRedemptionRequest createRedemptionRequest;
    private CreatePartialRedemptionRequest createPartialRedemptionRequest;
    private ConfirmationRedemptionRequest confirmationRedemptionRequest;
    private ConfirmationPartialRedemptionRequest confirmationPartialRedemption;
    private String index12;

    public AccountPresenter(AccountFragment fragment) {
        this.fragment = fragment;
    }

    /**
     * LOAD SETTLEMENT INFO
     */

    void loadSettlementInfo() {
        String email = PrefHelper.getString(PrefKey.EMAIL);
        String token = PrefHelper.getString(PrefKey.TOKEN);
        fragment.getApi().loadSettlementInformation(email, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<SettlementInfoResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(SettlementInfoResponse settlementInfoResponse) {
                        fragment.dismissProgressDialog();
                        fragment.settlementInfoResponse = settlementInfoResponse;
                        fragment.bindingSettlementInfoData();
                    }
                });

    }

    void generateRandomIndexSecurityPin() {
        Random rn = new Random();
        int index1 = rn.nextInt(6) + 1;
        int index2 = index1;
        while (index2 == index1) {
            index2 = rn.nextInt(6) + 1;
        }
        fragment.txvSecurityPin.setText(String.format(fragment.inputDigit, index1, index2));
        index12 = String.valueOf(index1 - 1).concat(String.valueOf(index2 - 1));
    }

    void createRedemption(CreateRedemptionRequest request) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().createRedemption(request.getIndex(), request.getInvestId(), request.getPin(),
                PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CreateRedemptionResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(CreateRedemptionResponse createRedemptionResponse) {
                        fragment.dismissProgressDialog();
                        if (createRedemptionResponse.getCode() == 1) {
                            fragment.gotoSummary(createRedemptionResponse.getData(), "0");
                        } else if (createRedemptionResponse.getCode() == 2) {
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.infortmation).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(createRedemptionResponse.getInfo())
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.yes)
                                    .positiveColor(Color.WHITE)
                                    .negativeText(R.string.no)
                                    .negativeColor(fragment.cDanger)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            confirmRedemption(constructConfirmationRedemptionRequest());
                                        }
                                    })
                                    .cancelable(false)
                                    .show();
                        } else if (createRedemptionResponse.getCode() == 0) {
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.infortmation).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(createRedemptionResponse.getInfo())
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.redeemtion_failed_tutup)
                                    .positiveColor(Color.WHITE)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            generateRandomIndexSecurityPin();
                                            dialog.dismiss();
                                        }
                                    })
                                    .cancelable(false)
                                    .show();
                        } else {
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.error).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(createRedemptionResponse.getInfo())
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.ok)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            MainActivity.startActivity((BaseActivity) fragment.getActivity());
                                        }
                                    })
                                    .positiveColor(Color.WHITE)
                                    .cancelable(false)
                                    .show();
                        }
                    }
                });

    }

    void createPartialRedemption(CreatePartialRedemptionRequest request) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().createPartialRedemption(request.getIndex(), request.getInvestId(), request.getPercentage(), request.getPin(),
                PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CreateRedemptionResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(CreateRedemptionResponse createRedemptionResponse) {
                        fragment.dismissProgressDialog();
                        if (createRedemptionResponse.getCode() == 1) {
                            fragment.gotoSummary(createRedemptionResponse.getData(), fragment.percentageRedeem);
                        } else if (createRedemptionResponse.getCode() == 2) {
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.infortmation).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(createRedemptionResponse.getInfo())
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.yes)
                                    .positiveColor(Color.WHITE)
                                    .negativeText(R.string.no)
                                    .negativeColor(fragment.cDanger)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            confirmPartialRedemption(constructConfirmationPartialRedemption());
                                        }
                                    })
                                    .cancelable(false)
                                    .show();
                        } else if (createRedemptionResponse.getCode() == 0) {
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.infortmation).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(createRedemptionResponse.getInfo())
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.redeemtion_failed_tutup)
                                    .positiveColor(Color.WHITE)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            generateRandomIndexSecurityPin();
                                            dialog.dismiss();
                                        }
                                    })
                                    .cancelable(false)
                                    .show();
                        } else {
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.error).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(createRedemptionResponse.getInfo())
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.ok)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            MainActivity.startActivity((BaseActivity) fragment.getActivity());
                                        }
                                    })
                                    .positiveColor(Color.WHITE)
                                    .cancelable(false)
                                    .show();
                        }
                    }
                });

    }


    void confirmRedemption(ConfirmationRedemptionRequest request) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().confirmTransRedemption(request.getConfirm(), request.getPin(), request.getIndex(), request.getInvestId(),
                PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ConfirmationRedemptionResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ConfirmationRedemptionResponse response) {
                        fragment.dismissProgressDialog();
                        if (response.getCode() == 1) {
                            fragment.gotoSummary(response.getData(), "0");
                        } else {
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.error).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(response.getInfo())
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.ok)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            MainActivity.startActivity((BaseActivity) fragment.getActivity());
                                        }
                                    })
                                    .positiveColor(Color.WHITE)
                                    .cancelable(false)
                                    .show();
                        }
                    }
                });
    }

    void confirmPartialRedemption(ConfirmationPartialRedemptionRequest request) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().confirmTransPartialRedemption(request.getConfirm(), request.getPin(), request.getIndex(), request.getInvestId(), request.getPercentage(),
                PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ConfirmationRedemptionResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ConfirmationRedemptionResponse response) {
                        fragment.dismissProgressDialog();
                        if (response.getCode() == 1) {
                            fragment.gotoSummary(response.getData(), fragment.percentageRedeem);
                        } else {
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.error).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(response.getInfo())
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.ok)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            MainActivity.startActivity((BaseActivity) fragment.getActivity());
                                        }
                                    })
                                    .positiveColor(Color.WHITE)
                                    .cancelable(false)
                                    .show();
                        }
                    }
                });

    }

    CreateRedemptionRequest constructCreateRedemptionRequest() {
        if (createRedemptionRequest == null)
            createRedemptionRequest = new CreateRedemptionRequest();
        createRedemptionRequest.setPin(fragment.etSecurityPin.getText().toString());
        createRedemptionRequest.setIndex(index12);
        createRedemptionRequest.setInvestId(fragment.investment.getInvestmentAccountNumber());
        return createRedemptionRequest;
    }

    CreatePartialRedemptionRequest constructCreatePartialRedemptionRequest() {
        if (createPartialRedemptionRequest == null)
            createPartialRedemptionRequest = new CreatePartialRedemptionRequest();
        createPartialRedemptionRequest.setPin(fragment.etSecurityPin.getText().toString());
        createPartialRedemptionRequest.setIndex(index12);
        createPartialRedemptionRequest.setPercentage(fragment.percentageRedeem);
        createPartialRedemptionRequest.setInvestId(fragment.investment.getInvestmentAccountNumber());
        return createPartialRedemptionRequest;
    }


    ConfirmationRedemptionRequest constructConfirmationRedemptionRequest() {
        if (confirmationRedemptionRequest == null)
            confirmationRedemptionRequest = new ConfirmationRedemptionRequest();
        confirmationRedemptionRequest.setConfirm("yes");
        confirmationRedemptionRequest.setInvestId(fragment.investment.getInvestmentAccountNumber());
        confirmationRedemptionRequest.setIndex(index12);
        confirmationRedemptionRequest.setPin(fragment.etSecurityPin.getText().toString());
        return confirmationRedemptionRequest;
    }

    ConfirmationPartialRedemptionRequest constructConfirmationPartialRedemption() {
        if (confirmationPartialRedemption == null)
            confirmationPartialRedemption = new ConfirmationPartialRedemptionRequest();
        confirmationPartialRedemption.setConfirm("yes");
        confirmationPartialRedemption.setInvestId(fragment.investment.getInvestmentAccountNumber());
        confirmationPartialRedemption.setPercentage(fragment.percentageRedeem);
        confirmationPartialRedemption.setIndex(index12);
        confirmationPartialRedemption.setPin(fragment.etSecurityPin.getText().toString());
        return confirmationPartialRedemption;
    }

    void getRedemptionOrderDetails(String investmentAccountNo) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().redemptionOrderDetails(investmentAccountNo, PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<RedemptionOrderResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(RedemptionOrderResponse redemptionOrderDetailResponse) {
                        fragment.dismissProgressDialog();
                        if (redemptionOrderDetailResponse.getCode() == 1) {
//                            fragment.redemptionOrderDetailResponse = redemptionOrderDetailResponse.getData();
                            createRedemption(constructCreateRedemptionRequest());
                        } else {
                            Toast.makeText(fragment.getContext(), redemptionOrderDetailResponse.getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void getPartialRedemptionOrderDetails(String investmentAccountNo, String presentageRedeem) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().partialRedemptionOrderDetails(investmentAccountNo, presentageRedeem, PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<RedemptionOrderResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(RedemptionOrderResponse redemptionOrderDetailResponse) {
                        fragment.dismissProgressDialog();
                        if (redemptionOrderDetailResponse.getCode() == 1) {
                            createPartialRedemption(constructCreatePartialRedemptionRequest());
//                            fragment.redemptionOrderDetailResponse = redemptionOrderDetailResponse.getData();
                        } else {
                            Toast.makeText(fragment.getContext(), redemptionOrderDetailResponse.getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void sendPIN() {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().generatePin(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GeneratePINResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Timber.e(e.getMessage());
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(GeneratePINResponse generatePINResponse) {
                        fragment.dismissProgressDialog();

                        if (generatePINResponse.getCode() == 1) {
                            Toast.makeText(fragment.getContext(), generatePINResponse.getInfo(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(fragment.getContext(), generatePINResponse.getInfo(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
