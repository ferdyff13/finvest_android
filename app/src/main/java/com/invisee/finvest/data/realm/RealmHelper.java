package com.invisee.finvest.data.realm;

import com.invisee.finvest.data.api.beans.Kyc;
import com.invisee.finvest.data.api.beans.KycLookup;
import com.invisee.finvest.data.api.beans.SecurityQuestion;
import com.invisee.finvest.data.api.requests.SettlementInfoRequest;
import com.invisee.finvest.data.realm.model.KycLookupModel;
import com.invisee.finvest.data.realm.model.KycModel;
import com.invisee.finvest.data.realm.model.SecurityQuestionModel;
import com.invisee.finvest.data.realm.model.SettlementInfoModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class RealmHelper {

    /*Security Question*/

    public static void createSecurityQuestionList(Realm realm, final List<SecurityQuestion> securityQuestionList) {
        realm.beginTransaction();
        for (SecurityQuestion securityQuestion : securityQuestionList) {
            SecurityQuestionModel securityQuestionModel = realm.createObject(SecurityQuestionModel.class);
            securityQuestionModel.setId(securityQuestion.getId());
            securityQuestionModel.setQuestionName(securityQuestion.getQuestionName());
            securityQuestionModel.setQuestionText(securityQuestion.getQuestionText());
        }
        Timber.i("%d Security Question Created", securityQuestionList.size());
        realm.commitTransaction();
    }

    public static Observable<RealmResults<SecurityQuestionModel>> readSecurityQuestions(Realm realm) {
        return realm.where(SecurityQuestionModel.class).findAllAsync().asObservable()
                .filter(new Func1<RealmResults<SecurityQuestionModel>, Boolean>() {
                    @Override
                    public Boolean call(RealmResults<SecurityQuestionModel> securityQuestionModels) {
                        return securityQuestionModels.isLoaded();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static RealmResults<SecurityQuestionModel> readSecurityQuestionList(Realm realm) {
        RealmQuery<SecurityQuestionModel> query = realm.where(SecurityQuestionModel.class);
        return query.findAll();
    }

    /*KYC*/

    public static void createKycLookup(Realm realm, final List<KycLookup> kycLookupList) {
        realm.beginTransaction();
        for (KycLookup kycLookup : kycLookupList) {
            KycLookupModel kycLookupModel = realm.createObject(KycLookupModel.class);
            kycLookupModel.setCategory(kycLookup.getCategory());
            kycLookupModel.setCode(kycLookup.getCode());
            kycLookupModel.setValue(kycLookup.getValue());
        }

        Timber.i("%d KYC Lookup Created", kycLookupList.size());
        realm.commitTransaction();
    }

    /**
     * @param category the category data that need to be retrieved. Set to null to retrieve all data
     */
    public static RealmResults<KycLookupModel> readKycLookupListByCategory(Realm realm, String category) {
        RealmQuery<KycLookupModel> query = realm.where(KycLookupModel.class);
        if (category != null) query.equalTo("category", category);
        return query.findAll();
    }

    public static int getKycLookupSize(Realm realm) {
        int size = readKycLookupListByCategory(realm, null).size();
        return size;
    }

    /**
     * @param kyc the KYC data object that need to be saved to Realm
     */
    public static void createOrUpdateKyc(Realm realm, Kyc kyc) {
        Timber.i("Create Or Update Kyc : %s", kyc.toString());
        realm.beginTransaction();
        KycModel kycModel = readKyc(realm);
        if (kycModel == null) {
            kycModel = realm.createObject(KycModel.class);
            kycModel.setId(KycModel.DEFAULT_ID);
        }
        kycModel.setSalutation(kyc.getSalutation());
        kycModel.setFirstName(kyc.getFirstName());
        kycModel.setLastName(kyc.getLastName());
        kycModel.setEmail(kyc.getEmail());
        kycModel.setBirthDate(kyc.getBirthDate());
        kycModel.setBirthPlace(kyc.getBirthPlace());
        kycModel.setOccupation(kyc.getOccupation());
        kycModel.setNatureOfBusiness(kyc.getNatureOfBusiness());
        kycModel.setEmployerName(kyc.getEmployerName());
        kycModel.setHomeCountry(kyc.getHomeCountry());
        kycModel.setHomeProvince(kyc.getHomeProvince());
        kycModel.setHomeCity(kyc.getHomeCity());
        kycModel.setHomeAddress(kyc.getHomeAddress());
        kycModel.setHomePostalCode(kyc.getHomePostalCode());
        kycModel.setHomePhoneNumber(kyc.getHomePhoneNumber());
        kycModel.setLegalCountry(kyc.getLegalCountry());
        kycModel.setLegalProvince(kyc.getLegalProvince());
        kycModel.setLegalCity(kyc.getLegalCity());
        kycModel.setLegalAddress(kyc.getLegalAddress());
        kycModel.setLegalPostalCode(kyc.getLegalPostalCode());
        kycModel.setLegalPhoneNumber(kyc.getLegalPhoneNumber());
//        kycModel.setOfficeCountry(kyc.getOfficeCountry());
//        kycModel.setOfficeProvince(kyc.getOfficeProvince());
//        kycModel.setOfficeCity(kyc.getOfficeCity());
//        //kycModel.setOfficeAddress(kyc.getOfficeAddress());
//        kycModel.setOfficePostalCode(kyc.getOfficePostalCode());
//        kycModel.setOfficePhoneNumber(kyc.getOfficePhoneNumber());
        kycModel.setIdType(kyc.getIdType());
        kycModel.setIdNumber(kyc.getIdNumber());
        kycModel.setIdExpirationDate(kyc.getIdExpirationDate());
        kycModel.setNationality(kyc.getNationality());
        kycModel.setCitizenship(kyc.getCitizenship());
        kycModel.setGender(kyc.getGender());
        kycModel.setReligion(kyc.getReligion());
        kycModel.setMaritalStatus(kyc.getMaritalStatus());
        kycModel.setEducationBackground(kyc.getEducationBackground());
        kycModel.setSourceOfIncome(kyc.getSourceOfIncome());
        kycModel.setTotalIncomePa(kyc.getTotalIncomePa());
        kycModel.setInvestmentPurpose(kyc.getInvestmentPurpose());
        kycModel.setMotherMaidenName(kyc.getMotherMaidenName());
        kycModel.setBeneficiaryName(kyc.getBeneficiaryName());
        kycModel.setBeneficiaryRelationship(kyc.getBeneficiaryRelationship());
        kycModel.setPreferredMailingAddress(kyc.getPreferredMailingAddress());
        kycModel.setPepCountry(kyc.getPepCountry().toString());
        kycModel.setSettlementAccountName(kyc.getSettlementAccountName());
        kycModel.setSettlementAccountNo(kyc.getSettlementAccountNo());
        realm.commitTransaction();
    }

    public static void createOrUpdateKycModel(Realm realm, KycModel kycModel, Kyc kyc) {
        Timber.i("Create Or Update Kyc Model : %s", kyc.toString());
        if (kycModel == null) {
            kycModel = realm.createObject(KycModel.class);
            kycModel.setId(KycModel.DEFAULT_ID);
        }
        realm.beginTransaction();
        kycModel.setSalutation(kyc.getSalutation());
        kycModel.setFirstName(kyc.getFirstName());
        kycModel.setLastName(kyc.getLastName());
        kycModel.setEmail(kyc.getEmail());
        kycModel.setBirthDate(kyc.getBirthDate());
        kycModel.setBirthPlace(kyc.getBirthPlace());
        kycModel.setOccupation(kyc.getOccupation());
        kycModel.setNatureOfBusiness(kyc.getNatureOfBusiness());
        kycModel.setEmployerName(kyc.getEmployerName());
        kycModel.setHomeCountry(kyc.getHomeCountry());
        kycModel.setHomeProvince(kyc.getHomeProvince());
        kycModel.setHomeCity(kyc.getHomeCity());
        kycModel.setHomeAddress(kyc.getHomeAddress());
        kycModel.setHomePostalCode(kyc.getHomePostalCode());
        kycModel.setHomePhoneNumber(kyc.getHomePhoneNumber());
        kycModel.setLegalCountry(kyc.getLegalCountry());
        kycModel.setLegalProvince(kyc.getLegalProvince());
        kycModel.setLegalCity(kyc.getLegalCity());
        kycModel.setLegalAddress(kyc.getLegalAddress());
        kycModel.setLegalPostalCode(kyc.getLegalPostalCode());
        kycModel.setLegalPhoneNumber(kyc.getLegalPhoneNumber());
//        kycModel.setOfficeCountry(kyc.getOfficeCountry());
//        kycModel.setOfficeProvince(kyc.getOfficeProvince());
//        kycModel.setOfficeCity(kyc.getOfficeCity());
//        //kycModel.setOfficeAddress(kyc.getOfficeAddress());
//        kycModel.setOfficePostalCode(kyc.getOfficePostalCode());
//        kycModel.setOfficePhoneNumber(kyc.getOfficePhoneNumber());
        kycModel.setIdType(kyc.getIdType());
        kycModel.setIdNumber(kyc.getIdNumber());
        kycModel.setIdExpirationDate(kyc.getIdExpirationDate());
        kycModel.setNationality(kyc.getNationality());
        kycModel.setCitizenship(kyc.getCitizenship());
        kycModel.setGender(kyc.getGender());
        kycModel.setReligion(kyc.getReligion());
        kycModel.setMaritalStatus(kyc.getMaritalStatus());
        kycModel.setEducationBackground(kyc.getEducationBackground());
        kycModel.setSourceOfIncome(kyc.getSourceOfIncome());
        kycModel.setTotalIncomePa(kyc.getTotalIncomePa());
        kycModel.setInvestmentPurpose(kyc.getInvestmentPurpose());
        kycModel.setMotherMaidenName(kyc.getMotherMaidenName());
        kycModel.setBeneficiaryName(kyc.getBeneficiaryName());
        kycModel.setBeneficiaryRelationship(kyc.getBeneficiaryRelationship());
        kycModel.setPreferredMailingAddress(kyc.getPreferredMailingAddress());
        kycModel.setSettlementAccountName(kyc.getSettlementAccountName());
        kycModel.setSettlementAccountNo(kyc.getSettlementAccountNo());
        realm.commitTransaction();
    }

    public static KycModel readKyc(Realm realm) {
        RealmQuery<KycModel> query = realm.where(KycModel.class);
        query.equalTo("id", KycModel.DEFAULT_ID);
        RealmResults<KycModel> results = query.findAll();
        if (results != null && results.size() > 0) {
            KycModel kycModel = results.first(); // read from realm
            return kycModel;
        } else {
            return null;
        }
    }

    /*Settlement Info*/

    public static void createOrUpdateSettlementInfo(Realm realm, SettlementInfoRequest request) {
        realm.beginTransaction();
        SettlementInfoModel settlementInfoModel = readSettlementInfo(realm);
        if (settlementInfoModel == null) {
            settlementInfoModel = realm.createObject(SettlementInfoModel.class);
            settlementInfoModel.setId(KycModel.DEFAULT_ID);
        }
        settlementInfoModel.setBankId(request.getBankId());
        settlementInfoModel.setBranchId(request.getBranchId());
        settlementInfoModel.setSettlementAccountNo(request.getSettlementAccountNo());
        settlementInfoModel.setSettlementAccountName(request.getSettlementAccountName());
        settlementInfoModel.setEmail(request.getEmail());
        realm.commitTransaction();
    }

    public static SettlementInfoModel readSettlementInfo(Realm realm) {
        RealmQuery<SettlementInfoModel> query = realm.where(SettlementInfoModel.class);
        query.equalTo("id", SettlementInfoModel.DEFAULT_ID);
        RealmResults<SettlementInfoModel> results = query.findAll();
        if (results != null && results.size() > 0) {
            SettlementInfoModel settlementInfoModel = results.first(); // read from realm
            return settlementInfoModel;
        } else {
            return null;
        }
    }

}
