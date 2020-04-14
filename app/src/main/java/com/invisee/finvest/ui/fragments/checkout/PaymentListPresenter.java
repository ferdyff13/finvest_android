package com.invisee.finvest.ui.fragments.checkout;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.DtoNetFee;
import com.invisee.finvest.data.api.beans.PayPro;
import com.invisee.finvest.data.api.requests.CheckPinRequest;
import com.invisee.finvest.data.api.requests.ConfirmCheckPinRequest;
import com.invisee.finvest.data.api.requests.PaymentPayProRequest;
import com.invisee.finvest.data.api.requests.RetryPayProRequest;
import com.invisee.finvest.data.api.requests.TransactionTransferRequest;
import com.invisee.finvest.data.api.responses.AmountSummaryResponse;
import com.invisee.finvest.data.api.responses.CartListResponse;
import com.invisee.finvest.data.api.responses.ConfirmCheckPinResponse;
import com.invisee.finvest.data.api.responses.GeneratePINResponse;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.api.responses.PaymentMethodResponse;
import com.invisee.finvest.data.api.responses.TransactionTransferResponse;
import com.invisee.finvest.data.api.responses.WalletBalanceResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.CartActivity;
import com.invisee.finvest.ui.activities.CheckoutActivity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 3/16/16.
 */
public class PaymentListPresenter {

    private PaymentListFragment fragment;
    private String index12;
    private boolean retry;

    public PaymentListPresenter(PaymentListFragment fragment) {
        this.fragment = fragment;
    }

    void generateRandomIndexSecurityPin() {
        Random rn = new Random();
        int index1 = rn.nextInt(6) + 1;
        int index2 = index1;
        while (index2 == index1) {
            index2 = rn.nextInt(6) + 1;
        }
        fragment.labelterm.setText(String.format(fragment.inputDigit, index1, index2));
/*        fragment.etSecurityPin.setHint(String.format(fragment.inputDigit, index1, index2));*/
        index12 = String.valueOf(index1 - 1).concat(String.valueOf(index2 - 1));
        PrefHelper.setString(PrefKey.INDEX, index12);
    }

    void checkPin() {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().checkPin(PrefHelper.getString(PrefKey.TOKEN), constructCheckPinRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<AmountSummaryResponse>() {
                    @Override
                    public void onCompleted() {
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.dismissProgressDialog();

                    }

                    @Override
                    public void onNext(AmountSummaryResponse response) {
                        fragment.dismissProgressDialog();
                        if (response.getCode() == null) {
                            PayProSummaryFragment.showFragment((BaseActivity) fragment.getActivity(), response);
                        } else {
                            if (response.getCode() == 1) {
                                if (fragment.rbTransfer.isChecked()) {
                                    fragment.type = "TRAN";
                                    transactionTransfer();
                                } else if (fragment.rbWallet.isChecked()) {
                                    fragment.type = "WALL";
                                    confirmCheckPin();
                                }
//                                else if (fragment.rbPayPro.isChecked()) {
//                                    fragment.type = "DOMP";
//                                    confirmCheckPinPaypro();
//                                }
                            } else if (response.getCode() == 90) {
                                showRetryDialog(response);
                            } else if (response.getCode() == 2) {

                                Date datenow = new Date();
                                Date limitdate = new Date();

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/hh:mm:ss",    Locale.getDefault());
                                try {
                                    limitdate = sdf.parse("03/07/2017/00:00:00");
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                Log.d("dateNow", datenow.toString());
                                Log.d("dateLimit", limitdate.toString());

                                if (datenow.before(limitdate)) {
                                    new MaterialDialog.Builder(fragment.getActivity())
                                            .iconRes(R.mipmap.ic_launcher_finvest)
                                            .backgroundColor(fragment.cDanger)
                                            .title(fragment.getString(R.string.infortmation).toUpperCase())
                                            .titleColor(Color.WHITE)
                                            .content("Selamat Hari Raya Idul Fitri 1438 H. Pesanan anda akan diproses menggunakan NAB hari bursa berikutnya (3 Juli 2017). Lanjutkan?")
                                            .contentColor(Color.WHITE)
                                            .positiveText(R.string.yes)
                                            .positiveColor(Color.WHITE)
                                            .negativeText("Tidak")
                                            .negativeColor(fragment.cGray)
                                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                                    if (fragment.rbTransfer.isChecked()) {
                                                        fragment.type = "TRAN";
                                                        transactionTransfer();
                                                    } else if (fragment.rbWallet.isChecked()) {
                                                        fragment.type = "WALL";
                                                        confirmCheckPin();
                                                    }
//                                                    else if(fragment.rbPayPro.isChecked()) {
//                                                        fragment.type = "DOMP";
//                                                        confirmCheckPinPaypro();
//                                                    }
                                                }
                                            })
                                            .cancelable(false)
                                            .show();
                                } else {
                                    new MaterialDialog.Builder(fragment.getActivity())
                                            .iconRes(R.mipmap.ic_launcher_finvest)
                                            .backgroundColor(fragment.cDanger)
                                            .title(fragment.getString(R.string.infortmation).toUpperCase())
                                            .titleColor(Color.WHITE)
                                            .content(response.getInfo())
                                            .contentColor(Color.WHITE)
                                            .positiveText(R.string.yes)
                                            .positiveColor(Color.WHITE)
                                            .negativeText(R.string.no)
                                            .negativeColor(fragment.cGray)
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                       /*     if (fragment.rbTransfer.isChecked())*/
                                                    if (fragment.rbTransfer.isChecked()) {
                                                        fragment.type = "TRAN";
                                                        transactionTransfer();
                                                    } else if (fragment.rbWallet.isChecked()) {
                                                        fragment.type = "WALL";
                                                        confirmCheckPin();
                                                    }
//                                                    else if(fragment.rbPayPro.isChecked()) {
//                                                        fragment.type = "DOMP";
//                                                        confirmCheckPinPaypro();
//                                                    }
                                                }
                                            })
                                            .cancelable(false)
                                            .show();

                                }
                            } else if (response.getCode() == 0) {
                                new MaterialDialog.Builder(fragment.getActivity())
                                        .iconRes(R.mipmap.ic_launcher_finvest)
                                        .backgroundColor(fragment.cDanger)
                                        .title(fragment.getString(R.string.infortmation).toUpperCase())
                                        .titleColor(Color.WHITE)
                                        .content(response.getInfo())
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
                                        .content(response.getInfo())
                                        .contentColor(Color.WHITE)
                                        .positiveText(R.string.ok)
                                        .positiveColor(Color.WHITE)
                                        .cancelable(false)
                                        .show();
                            }
                        }
                    }
                });
    }

    void checkPinPayPro() {
        fragment.noReminder(true);
        fragment.getApi().checkPin(PrefHelper.getString(PrefKey.TOKEN), constructCheckPinRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<AmountSummaryResponse>() {
                    @Override
                    public void onCompleted() {
                        fragment.noReminder(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.noReminder(false);

                    }

                    @Override
                    public void onNext(AmountSummaryResponse response) {
                        fragment.noReminder(false);
                        if (response.getCode() == null) {
                            ((CheckoutActivity) fragment.getActivity()).setStep(3);
                            PayProSummaryFragment.showFragment((BaseActivity) fragment.getActivity(), response);
                        } else {
                            if (response.getCode() == 1) {
                                confirmCheckPinPaypro();
                            } else if (response.getCode() == 90) {
                                showRetryDialog(response);
                            } else if (response.getCode() == 2) {

                                Date datenow = new Date();
                                Date limitdate = new Date();

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/hh:mm:ss",    Locale.getDefault());
                                try {
                                    limitdate = sdf.parse("03/07/2017/00:00:00");
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                Log.d("dateNow", datenow.toString());
                                Log.d("dateLimit", limitdate.toString());

                                if (datenow.before(limitdate)) {
                                    new MaterialDialog.Builder(fragment.getActivity())
                                            .iconRes(R.mipmap.ic_launcher_finvest)
                                            .backgroundColor(fragment.cDanger)
                                            .title(fragment.getString(R.string.infortmation).toUpperCase())
                                            .titleColor(Color.WHITE)
                                            .content("Selamat Hari Raya Idul Fitri 1438 H. Pesanan anda akan diproses menggunakan NAB hari bursa berikutnya (3 Juli 2017). Lanjutkan?")
                                            .contentColor(Color.WHITE)
                                            .positiveText(R.string.yes)
                                            .positiveColor(Color.WHITE)
                                            .negativeText("Tidak")
                                            .negativeColor(fragment.cGray)
                                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                                    confirmCheckPinPaypro();
                                                }
                                            })
                                            .cancelable(false)
                                            .show();
                                } else {
                                    new MaterialDialog.Builder(fragment.getActivity())
                                            .iconRes(R.mipmap.ic_launcher_finvest)
                                            .backgroundColor(fragment.cDanger)
                                            .title(fragment.getString(R.string.infortmation).toUpperCase())
                                            .titleColor(Color.WHITE)
                                            .content(response.getInfo())
                                            .contentColor(Color.WHITE)
                                            .positiveText(R.string.yes)
                                            .positiveColor(Color.WHITE)
                                            .negativeText(R.string.no)
                                            .negativeColor(fragment.cGray)
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                                    confirmCheckPinPaypro();

                                                }
                                            })
                                            .cancelable(false)
                                            .show();

                                }
                            } else if (response.getCode() == 0) {
                                new MaterialDialog.Builder(fragment.getActivity())
                                        .iconRes(R.mipmap.ic_launcher_finvest)
                                        .backgroundColor(fragment.cDanger)
                                        .title(fragment.getString(R.string.infortmation).toUpperCase())
                                        .titleColor(Color.WHITE)
                                        .content(response.getInfo())
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
                                        .content(response.getInfo())
                                        .contentColor(Color.WHITE)
                                        .positiveText(R.string.ok)
                                        .positiveColor(Color.WHITE)
                                        .cancelable(false)
                                        .show();
                            }
                        }
                    }
                });
    }

    void transactionTransfer() {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().transactionTransfer(PrefHelper.getString(PrefKey.TOKEN), constructtransactionTransferRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<TransactionTransferResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(TransactionTransferResponse response) {
                        fragment.dismissProgressDialog();
                        if (response.getCode() == 1 && response.getData().size() > 0) {
                            fragment.transferResponse = response;
                            ((BaseActivity) fragment.getActivity()).hideKeyboard();
                            ((CheckoutActivity) fragment.getActivity()).setStep(3);
                            for (int i = 0; i < response.getData().size(); ++i) {
                                if (response.getData().get(i).getOrderNumber().contains("O")) {
                                    TransferSummaryFragment.showFragment((BaseActivity) fragment.getActivity(), response, fragment.cartList);
                                } else {
                                    VASummaryFragment.showFragment((BaseActivity) fragment.getActivity(), response, fragment.cartList);
                                }

                            }

                        } else {
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.error).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(response.getInfo())
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.ok)
                                    .positiveColor(Color.WHITE)
                                    .cancelable(false)
                                    .show();
                        }
                    }
                });

    }

    void confirmCheckPin() {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().confirmCheckPin(PrefHelper.getString(PrefKey.TOKEN), cunstructConfirmPinRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ConfirmCheckPinResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(ConfirmCheckPinResponse response) {
                        fragment.dismissProgressDialog();
                        if (response.getCode() == null) {

                        } else {
                            if (response.getCode() == 1) {
                                /*fragment.confirmCheckPinResponse = response;*/
                                ((BaseActivity) fragment.getActivity()).hideKeyboard();
                                ((CheckoutActivity) fragment.getActivity()).setStep(3);
                                String pin = fragment.etSecurityPin.getText().toString();
                                ConfirmationWalletFragment.showFragment((BaseActivity) fragment.getActivity(), response, pin);
                            } else if(response.getCode() == 90) {
                                showRetryConfirmDialog(response);
                            } else {
                                new MaterialDialog.Builder(fragment.getActivity())
                                        .iconRes(R.mipmap.ic_launcher_finvest)
                                        .backgroundColor(fragment.cDanger)
                                        .title(fragment.getString(R.string.error).toUpperCase())
                                        .titleColor(Color.WHITE)
                                        .content(response.getInfo())
                                        .contentColor(Color.WHITE)
                                        .positiveText(R.string.ok)
                                        .positiveColor(Color.WHITE)
                                        .cancelable(false)
                                        .show();
                            }
                        }

                        }
                });
    }

    void confirmCheckPinPaypro() {
        fragment.noReminder(true);
        fragment.getApi().confirmCheckPinPaypro(PrefHelper.getString(PrefKey.TOKEN), cunstructConfirmPinRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<AmountSummaryResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.noReminder(false);
                    }

                    @Override
                    public void onNext(AmountSummaryResponse response) {
                        fragment.noReminder(false);
                        if (response.getCode() == null) {
                            ((CheckoutActivity) fragment.getActivity()).setStep(3);
                            PayProSummaryFragment.showFragment((BaseActivity) fragment.getActivity(), response);
                        } else {
                            if (response.getCode() == 1) {

                            } else if(response.getCode() == 90) {
                                showRetryDialog(response);
                            } else {
                                new MaterialDialog.Builder(fragment.getActivity())
                                        .iconRes(R.mipmap.ic_launcher_finvest)
                                        .backgroundColor(fragment.cDanger)
                                        .title(fragment.getString(R.string.error).toUpperCase())
                                        .titleColor(Color.WHITE)
                                        .content(response.getInfo())
                                        .contentColor(Color.WHITE)
                                        .positiveText(R.string.ok)
                                        .positiveColor(Color.WHITE)
                                        .cancelable(false)
                                        .show();
                            }
                        }

                    }
                });
    }



    void getPaymentList() {
        fragment.showProgressBar();
        fragment.getApi().getPaymentMethod(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PaymentMethodResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.connectionError();
                    }

                    @Override
                    public void onNext(PaymentMethodResponse response) {
                        fragment.paymentMethodResponse = response;
                        fragment.payment();
                        getWalletBalance();
                    }
                });
    }



    void getWalletBalance() {
        fragment.getApi().requestWalletBalance(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<WalletBalanceResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressBar();
                        fragment.rbWallet.setEnabled(false);
                        fragment.txvWarningIcon.setVisibility(View.VISIBLE);
                        fragment.txvNoVpBalance.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(WalletBalanceResponse response) {
                        fragment.dismissProgressBar();
                        if (response.getCode() == 0) {
                            if (response.getData().getBalance().equals(0)) {
                                fragment.rbWallet.setEnabled(false);
                                fragment.txvWarningIcon.setVisibility(View.VISIBLE);
                                fragment.txvNoVpBalance.setVisibility(View.VISIBLE);
                            } else if (response.getData().getBalance() < getTotal().doubleValue()) {
                                fragment.rbWallet.setEnabled(false);
                                fragment.txvWarningIcon.setVisibility(View.VISIBLE);
                                fragment.txvNoVpBalance.setVisibility(View.VISIBLE);
                            } else if (response.getData().getBalance() > getTotal().doubleValue()) {
                                fragment.rbWallet.setEnabled(true);
                                fragment.rbWallet.setChecked(true);
                                fragment.txvWarningIcon.setVisibility(View.GONE);
                            }
                        } else {
                            fragment.payment();
                            fragment.rbWallet.setEnabled(false);
                            fragment.txvWarningIcon.setVisibility(View.VISIBLE);
                            fragment.txvNoVpAccount.setText(response.getInfo() + "");
                            fragment.txvNoVpAccount.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    void retryPayPro() {
        fragment.noReminder(true);
        fragment.getApi().retryPaypro(constructretryRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<AmountSummaryResponse>() {
                    @Override
                    public void onCompleted() {
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.noReminder(false);

                    }

                    @Override
                    public void onNext(AmountSummaryResponse response) {
                        fragment.noReminder(false);
                        if (response.getCode() == null) {
                            PayProSummaryFragment.showFragment((BaseActivity) fragment.getActivity(), response);
                        } else {
                            if (response.getCode() == 2) {

                                Date datenow = new Date();
                                Date limitdate = new Date();

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/hh:mm:ss",    Locale.getDefault());
                                try {
                                    limitdate = sdf.parse("03/07/2017/00:00:00");
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                if (datenow.before(limitdate)) {
                                    new MaterialDialog.Builder(fragment.getActivity())
                                            .iconRes(R.mipmap.ic_launcher_finvest)
                                            .backgroundColor(fragment.cDanger)
                                            .title(fragment.getString(R.string.infortmation).toUpperCase())
                                            .titleColor(Color.WHITE)
                                            .content("Selamat Hari Raya Idul Fitri 1438 H. Pesanan anda akan diproses menggunakan NAB hari bursa berikutnya (3 Juli 2017). Lanjutkan?")
                                            .contentColor(Color.WHITE)
                                            .positiveText(R.string.yes)
                                            .positiveColor(Color.WHITE)
                                            .negativeText("Tidak")
                                            .negativeColor(fragment.cGray)
                                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                                    retry =  true;
                                                    retryPayPro();
                                                }
                                            })
                                            .cancelable(false)
                                            .show();
                                } else {
                                    new MaterialDialog.Builder(fragment.getActivity())
                                            .iconRes(R.mipmap.ic_launcher_finvest)
                                            .backgroundColor(fragment.cDanger)
                                            .title(fragment.getString(R.string.infortmation).toUpperCase())
                                            .titleColor(Color.WHITE)
                                            .content(response.getInfo())
                                            .contentColor(Color.WHITE)
                                            .positiveText(R.string.yes)
                                            .positiveColor(Color.WHITE)
                                            .negativeText(R.string.no)
                                            .negativeColor(fragment.cGray)
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                                    retry =  true;
                                                    retryPayPro();

                                                }
                                            })
                                            .cancelable(false)
                                            .show();

                                }
                            } else if (response.getCode() == 90) {
                                showRetryDialog(response);
                            } else if (response.getCode() == 14) {
                                new MaterialDialog.Builder(fragment.getActivity())
                                        .iconRes(R.mipmap.ic_launcher_finvest)
                                        .backgroundColor(fragment.cDanger)
                                        .title(fragment.getString(R.string.infortmation).toUpperCase())
                                        .titleColor(Color.WHITE)
                                        .content(response.getInfo())
                                        .contentColor(Color.WHITE)
                                        .positiveText(R.string.ok)
                                        .positiveColor(Color.WHITE)
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                                CartActivity.startActivity((BaseActivity) fragment.getActivity());
                                            }
                                        })
                                        .cancelable(false)
                                        .show();
                            } else {
                                new MaterialDialog.Builder(fragment.getActivity())
                                        .iconRes(R.mipmap.ic_launcher_finvest)
                                        .backgroundColor(fragment.cDanger)
                                        .title(fragment.getString(R.string.infortmation).toUpperCase())
                                        .titleColor(Color.WHITE)
                                        .content(response.getInfo())
                                        .contentColor(Color.WHITE)
                                        .positiveText(R.string.yes)
                                        .positiveColor(Color.WHITE)
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                                CartActivity.startActivity((BaseActivity) fragment.getActivity());
                                            }
                                        })
                                        .cancelable(false)
                                        .show();
                            }
                        }
                    }
                });
    }

    ConfirmCheckPinRequest cunstructConfirmPinRequest() {
        ConfirmCheckPinRequest confirmCheckPinRequest = new ConfirmCheckPinRequest();

        confirmCheckPinRequest.setToken(PrefHelper.getString(PrefKey.TOKEN));
        confirmCheckPinRequest.setAmount(getTotal());
        confirmCheckPinRequest.setType(fragment.type);
        confirmCheckPinRequest.setCurrency("IDR");
        confirmCheckPinRequest.setUsername(PrefHelper.getString(PrefKey.EMAIL));
        confirmCheckPinRequest.setConfirm("yes");
        confirmCheckPinRequest.setPin(fragment.etSecurityPin.getText().toString());
        confirmCheckPinRequest.setIndex(index12);

        List<DtoNetFee> dtoNetFees = new ArrayList<>();
        for (int i = 0; i < fragment.cartList.getCartList().size(); i++) {

            CartListResponse cart = fragment.cartList.getCartList().get(i);
            DtoNetFee fee = new DtoNetFee();

            fee.setId(cart.getTransaction().getId());

            Double transaction = Double.parseDouble(cart.getTransactionAmount());
            BigDecimal toBigDecimal = new BigDecimal(transaction);
            fee.setNetAmount(toBigDecimal);

            Double freePrice = cart.getFeePrice();
            BigDecimal fpToBigDecimal = new BigDecimal(freePrice);
            fpToBigDecimal = fpToBigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
            fee.setFeeAmount(fpToBigDecimal);

            BigDecimal tToBigDecimal = new BigDecimal(cart.getTotal());
            fee.setTotal(tToBigDecimal);

            dtoNetFees.add(fee);

        }

        confirmCheckPinRequest.setDtoNetFees(dtoNetFees);
        confirmCheckPinRequest.setCustomerNumber(fragment.etPaypro.getText().toString());
        return confirmCheckPinRequest;

    }




    CheckPinRequest constructCheckPinRequest() {
        CheckPinRequest checkPinRequest = new CheckPinRequest();

        checkPinRequest.setToken(PrefHelper.getString(PrefKey.TOKEN));
        checkPinRequest.setPin(fragment.etSecurityPin.getText().toString());
        checkPinRequest.setType(fragment.type);
        checkPinRequest.setAmount(getTotal());
        checkPinRequest.setUsername(PrefHelper.getString(PrefKey.EMAIL));
        checkPinRequest.setCurrency("IDR");
        checkPinRequest.setIndex(index12);

        List<DtoNetFee> dtoNetFees = new ArrayList<>();
        for (int i = 0; i < fragment.cartList.getCartList().size(); i++) {

            CartListResponse cart = fragment.cartList.getCartList().get(i);
            DtoNetFee fee = new DtoNetFee();

            fee.setId(cart.getTransaction().getId());
            fee.setId(cart.getTransaction().getId());

            Double transaction = Double.parseDouble(cart.getTransactionAmount());
            BigDecimal toBigDecimal = new BigDecimal(transaction);
            fee.setNetAmount(toBigDecimal);

            Double freePrice = cart.getFeePrice();
            BigDecimal fpToBigDecimal = new BigDecimal(freePrice);
            fpToBigDecimal = fpToBigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
            fee.setFeeAmount(fpToBigDecimal);

            BigDecimal tToBigDecimal = new BigDecimal(cart.getTotal());
            fee.setTotal(tToBigDecimal);

            fee.setFundPackageId(cart.getFundPackages().getId());

            dtoNetFees.add(fee);

   /*         fee.setFeeAmount(cart.getFeePrice().doubleValue());*/

        }

        checkPinRequest.setDtoNetFees(dtoNetFees);
        checkPinRequest.setCustomerNumber(fragment.etPaypro.getText().toString());

        return checkPinRequest;
    }


    TransactionTransferRequest constructtransactionTransferRequest() {
        TransactionTransferRequest transactionTransferRequest = new TransactionTransferRequest();

        transactionTransferRequest.setToken(PrefHelper.getString(PrefKey.TOKEN));
        transactionTransferRequest.setType(fragment.type);

        Double b = getTotal();

        BigDecimal bd = new BigDecimal(b);
        bd = bd.setScale(2, BigDecimal.ROUND_DOWN);
        transactionTransferRequest.setTotal(bd);

        transactionTransferRequest.setIndex(index12);
        transactionTransferRequest.setPin(fragment.etSecurityPin.getText().toString());

        transactionTransferRequest.setUsername(PrefHelper.getString(PrefKey.EMAIL));
        transactionTransferRequest.setCurrency("IDR");

        List<DtoNetFee> dtoNetFees = new ArrayList<>();
        for (int i = 0; i < fragment.cartList.getCartList().size(); i++) {

            CartListResponse cart = fragment.cartList.getCartList().get(i);
            DtoNetFee fee = new DtoNetFee();

            fee.setId(cart.getTransaction().getId());

            Double transaction = Double.parseDouble(cart.getTransactionAmount());
            BigDecimal big = new BigDecimal(transaction);
            fee.setNetAmount(big);

            Double h = cart.getFeePrice();
            BigDecimal f = new BigDecimal(h);
            f = f.setScale(2, BigDecimal.ROUND_DOWN);
            fee.setFeeAmount(f);

            String o = cart.getTotal();
            Double p = Double.parseDouble(o);

            BigDecimal d = new BigDecimal(p);
            d = d.setScale(2, BigDecimal.ROUND_DOWN);
            fee.setTotal(d);

            fee.setFundPackageId(cart.getFundPackages().getId());
            fee.setStatus(cart.getTransactionType().getTrxCode());

            dtoNetFees.add(fee);

        }
        transactionTransferRequest.setDetail(dtoNetFees);
        return transactionTransferRequest;
    }

    RetryPayProRequest constructretryRequest() {
        RetryPayProRequest retryPayProRequest = new RetryPayProRequest();

        retryPayProRequest.setToken(PrefHelper.getString(PrefKey.TOKEN));
        retryPayProRequest.setConfirm(retry);

        return retryPayProRequest;
    }

    Double getTotal() {
        Double total = 0d;

        for (int i = 0; i < fragment.cartList.getCartList().size(); i++) {

            Double t = Double.parseDouble(fragment.cartList.getCartList().get(i).getTotal());
            total += t;

        }
        return total;
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

    PaymentPayProRequest constructpaymentpayprorequest() {
        PaymentPayProRequest request = new PaymentPayProRequest();
        request.setToken(PrefHelper.getString(PrefKey.TOKEN));

        List<PayPro> list = new ArrayList<>();
        PayPro payPro = new PayPro();
        payPro.setCode(fragment.type);
        payPro.setAccount(fragment.etPaypro.getText().toString());
        list.add(payPro);

        request.setData(list);

        return request;
    }

     void getPaymentPayPro() {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().getPaymentPaypro(constructpaymentpayprorequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
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
                    public void onNext(GenericResponse response) {
                        fragment.dismissProgressDialog();
                        if (response.getCode() == 0) {
                            checkPinPayPro();
                        }
                    }
                });
    }

    public void showRetryDialog(AmountSummaryResponse response) {
        final MaterialDialog dialog = new MaterialDialog.Builder(fragment.getActivity())
                .title(R.string.paypro_dialog_title)
                .customView(R.layout.dialog_retry_paypro, true)
                .negativeText("Batalkan")
                .positiveText("Coba lagi")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        CartActivity.startActivity((BaseActivity) fragment.getActivity());
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        retryPayPro();
                    }
                }).build();

        View view = dialog.getCustomView();

        ((TextView) ButterKnife.findById(view, R.id.tvOrderNumber)).setText(response.getData().getOrderNo());
        ((TextView) ButterKnife.findById(view, R.id.tvAccountPaypro)).setText(response.getData().getDompetku());
        ((TextView) ButterKnife.findById(view, R.id.tvTotalOder)).setText(response.getData().getTotal());
        ((TextView) ButterKnife.findById(view, R.id.tvInfo)).setText(response.getInfo());
        dialog.show();
    }



    public void showRetryConfirmDialog(ConfirmCheckPinResponse response) {
        final MaterialDialog dialog = new MaterialDialog.Builder(fragment.getActivity())
                .title(R.string.paypro_dialog_title)
                .customView(R.layout.dialog_retry_paypro, true)
                .negativeText("Batalkan")
                .positiveText("Coba lagi")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        retryPayPro();
                    }
                }).build();

        View view = dialog.getCustomView();

        ((TextView) ButterKnife.findById(view, R.id.tvOrderNumber)).setText(response.getData().getOrderNo());
        ((TextView) ButterKnife.findById(view, R.id.tvAccountPaypro)).setText(response.getData().getDompetku());
        ((TextView) ButterKnife.findById(view, R.id.tvTotalOder)).setText(response.getData().getTotal());
        ((TextView) ButterKnife.findById(view, R.id.tvInfo)).setText(response.getInfo());


        dialog.show();
    }



}
