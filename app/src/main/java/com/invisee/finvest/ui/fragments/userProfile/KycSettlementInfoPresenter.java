package com.invisee.finvest.ui.fragments.userProfile;

import android.widget.Toast;

import com.invisee.finvest.data.api.beans.Bank;
import com.invisee.finvest.data.api.beans.Branch;
import com.invisee.finvest.data.api.requests.DocumentIdRequest;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.util.eventBus.RxBusObject;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/23/16.
 */
public class KycSettlementInfoPresenter {


    public enum LoadType {
        BANKS_BRANCHES, BRANCHES
    }

    private static final String MAX = "999";
    private static final String OFFSET = "0";

    private KycSettlementFragment fragment;
    private LoadType type;
    private List<Bank> banks;
    private List<Branch> branches;
    private Bank selectedBank;
    private Branch selectedBranch;


    public KycSettlementInfoPresenter(KycSettlementFragment fragment) {
        this.fragment = fragment;
    }

    public List<Bank> getBanks() {
        return banks;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public Bank getSelectedBank() {
        return selectedBank;
    }

    public Branch getSelectedBranch() {
        return selectedBranch;
    }

    public void setSelectedBank(Bank selectedBank) {
        this.selectedBank = selectedBank;
    }

    public void setSelectedBranch(Branch selectedBranch) {
        this.selectedBranch = selectedBranch;
    }

    void loadBanksBranches() {
        type = LoadType.BANKS_BRANCHES;
        emptyBanks();
        final String token = PrefHelper.getString(PrefKey.TOKEN);
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().getAllBank(MAX, OFFSET, token)
                .flatMap(persistBanksAndGetCities(token))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(persistBranches());
    }

    void loadBranchBySelectedBank(String bankId) {
        type = LoadType.BRANCHES;
        emptyBranches();
        final String token = PrefHelper.getString(PrefKey.TOKEN);
        fragment.getApi().getListBranchByBankCode(bankId, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(persistBranches());
    }
    private Func1<List<Bank>, Observable<List<Branch>>> persistBanksAndGetCities(final String token) {
        return new Func1<List<Bank>, Observable<List<Branch>>>() {
            @Override
            public Observable<List<Branch>> call(List<Bank> banks) {
                if (banks != null && banks.size() > 0) {
                    KycSettlementInfoPresenter.this.banks = banks;
                    Bank defaultBank = new Bank();
                    defaultBank.setId("");
                    defaultBank.setName("");
                    banks.add(0, defaultBank);
                    selectedBank = banks.get(0);
                    return fragment.getApi().getListBranchByBankCode(selectedBank.getId(), token);
                } else {
                    return null;
                }
            }
        };
    }

    private Observer<List<Branch>> persistBranches() {
        return new Observer<List<Branch>>() {
            @Override
            public void onCompleted() {
                fragment.onLoadBanksBrachesDataComplete(KycSettlementInfoPresenter.this.type);
                fragment.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e.getMessage());
                fragment.onLoadBanksBrachesDataComplete(KycSettlementInfoPresenter.this.type);
            }

            @Override
            public void onNext(List<Branch> branches) {
                if (branches != null && branches.size() > 0) {
                    KycSettlementInfoPresenter.this.branches = branches;
                    selectedBranch = branches.get(0);
                }
            }
        };
    }

    private void emptyBanks() {
        banks = null;
        selectedBank = null;
        emptyBranches();
    }

    private void emptyBranches() {
        branches = null;
        selectedBranch = null;
    }

    DocumentIdRequest constructUploadSettlement() {
        DocumentIdRequest request = new DocumentIdRequest();
        request.setType("DocTyp04");
        request.setToken(PrefHelper.getString(PrefKey.TOKEN));
        return request;
    }

    public void uploadDoc4(final DocumentIdRequest request, File file) {
        fragment.showProgressDialog(fragment.loading);
        okhttp3.RequestBody photo = okhttp3.RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("DocTyp04", file.getName(), photo);
        fragment.getApi().uploadByCustomerDoc1(request.getToken(), request.getType(), body)
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
                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        fragment.dismissProgressDialog();
                        if (response.getCode() == 0) {
                            Toast.makeText(fragment.getContext(), response.getInfo(), Toast.LENGTH_SHORT).show();
                            //sebagai trigger dr kyc activity agar masuk kembali ke page 5 upload foto
                            fragment.getBus().send(new RxBusObject(RxBusObject.RxBusKey.BACKT_TO_SETTLEMENT, null));
                        }
                    }
                });
    }

    public void uploadVerDoc4(final DocumentIdRequest request, File file) {
        fragment.showProgressDialog(fragment.loading);
        okhttp3.RequestBody photo = okhttp3.RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("DocTyp04", file.getName(), photo);
        fragment.getApi().uploadByCustomerVerDoc1(request.getToken(), request.getType(), body)
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
                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        fragment.dismissProgressDialog();
                        if (response.getCode() == 0) {
                            Toast.makeText(fragment.getContext(), response.getInfo(), Toast.LENGTH_SHORT).show();
                            //sebagai trigger dr kyc activity agar masuk kembali ke page 5 upload foto
                            fragment.getBus().send(new RxBusObject(RxBusObject.RxBusKey.BACKT_TO_SETTLEMENT, null));
                        }
                    }
                });
    }



}
