package com.invisee.finvest.data.api.beans;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class Kyc{

    @SerializedName("id")
    @Expose
    private int id = 0;
    @SerializedName("salutation")
    @Expose
    private String salutation;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("middleName")
    @Expose
    private String middleName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("birthDate")
    @Expose
    private String birthDate;
    @SerializedName("birthPlace")
    @Expose
    private String birthPlace;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("natureOfBusiness")
    @Expose
    private String natureOfBusiness;
    @SerializedName("employerName")
    @Expose
    private String employerName;
    @SerializedName("homeCountry")
    @Expose
    private String homeCountry;
    @SerializedName("homeProvince")
    @Expose
    private String homeProvince;
    @SerializedName("homeCity")
    @Expose
    private String homeCity;
    @SerializedName("homeAddress")
    @Expose
    private String homeAddress;
    @SerializedName("homePostalCode")
    @Expose
    private String homePostalCode;
    @SerializedName("homePhoneNumber")
    @Expose
    private String homePhoneNumber;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("legalCountry")
    @Expose
    private String legalCountry;
    @SerializedName("legalProvince")
    @Expose
    private String legalProvince;
    @SerializedName("legalCity")
    @Expose
    private String legalCity;
    @SerializedName("legalAddress")
    @Expose
    private String legalAddress;
    @SerializedName("legalPostalCode")
    @Expose
    private String legalPostalCode;
    @SerializedName("legalPhoneNumber")
    @Expose
    private String legalPhoneNumber;
    @SerializedName("idType")
    @Expose
    private String idType;
    @SerializedName("idNumber")
    @Expose
    private String idNumber;
    @SerializedName("idExpirationDate")
    @Expose
    private String idExpirationDate;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("citizenship")
    @Expose
    private String citizenship;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("maritalStatus")
    @Expose
    private String maritalStatus;
    @SerializedName("educationBackground")
    @Expose
    private String educationBackground;
    @SerializedName("sourceOfIncome")
    @Expose
    private String sourceOfIncome;
    @SerializedName("totalIncomePa")
    @Expose
    private String totalIncomePa;
    @SerializedName("investmentPurpose")
    @Expose
    private String investmentPurpose;
    @SerializedName("totalAsset")
    @Expose
    private String totalAsset;
    @SerializedName("investmentExperience")
    @Expose
    private String investmentExperience;
    @SerializedName("otherInvestmentExperience")
    @Expose
    private String otherInvesmentExperience;
    @SerializedName("pepName")
    @Expose
    private String pepName;
    @SerializedName("pepPosition")
    @Expose
    private String pepPosition;
    @SerializedName("pepPublicFunction")
    @Expose
    private String pepPublicFunction;
    @SerializedName("pepCountry")
    @Expose
    private PepCountry pepCountry;
    @SerializedName("pepYearOfService")
    @Expose
    private String pepYearOfService;
    @SerializedName("pepRelationship")
    @Expose
    private String pepRelationship;
    @SerializedName("motherMaidenName")
    @Expose
    private String motherMaidenName;
    @SerializedName("beneficiaryName")
    @Expose
    private String beneficiaryName = "";
    @SerializedName("beneficiaryRelationship")
    @Expose
    private String beneficiaryRelationship;
    @SerializedName("preferredMailingAddress")
    @Expose
    private String preferredMailingAddress;
    @SerializedName("taxId")
    @Expose
    private String taxId;
//    @SerializedName("taxIdRegisDate")
//    @Expose
//    private String taxIdRegisDate;
    @SerializedName("referral")
    @Expose
    private String referral;
    @SerializedName("referralName")
    @Expose
    private String referralName;

    @SerializedName("spouseName")
    @Expose
    private String spouseName;

    @SerializedName("bankId")
    @Expose
    private String bankId;

    @SerializedName("branchId")
    @Expose
    private String branchId;

    @SerializedName("settlementAccountName")
    @Expose
    private String settlementAccountName;

    @SerializedName("settlementAccountNo")
    @Expose
    private String settlementAccountNo;


    /**
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The salutation
     */
    public String getSalutation() {
        return salutation;
    }

    /**
     * @param salutation The salutation
     */
    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    /**
     * @return The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return The middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName The middleName
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The birthDate
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate The birthDate
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return The birthPlace
     */
    public String getBirthPlace() {
        return birthPlace;
    }

    /**
     * @param birthPlace The birthPlace
     */
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    /**
     * @return The occupation
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * @param occupation The occupation
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * @return The natureOfBusiness
     */
    public String getNatureOfBusiness() {
        return natureOfBusiness;
    }

    /**
     * @param natureOfBusiness The natureOfBusiness
     */
    public void setNatureOfBusiness(String natureOfBusiness) {
        this.natureOfBusiness = natureOfBusiness;
    }

    /**
     * @return The employerName
     */
    public String getEmployerName() {
        return employerName;
    }

    /**s
     */
    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    /**
     * @return The homeCountry
     */
    public String getHomeCountry() {
        return homeCountry;
    }

    /**
     * @param homeCountry The homeCountry
     */
    public void setHomeCountry(String homeCountry) {
        this.homeCountry = homeCountry;
    }

    /**
     * @return The homeProvince
     */
    public String getHomeProvince() {
        return homeProvince;
    }

    /**
     * @param homeProvince The homeProvince
     */
    public void setHomeProvince(String homeProvince) {
        this.homeProvince = homeProvince;
    }

    /**
     * @return The homeCity
     */
    public String getHomeCity() {
        return homeCity;
    }

    /**
     * @param homeCity The homeCity
     */
    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    /**
     * @return The homeAddress
     */
    public String getHomeAddress() {
        return homeAddress;
    }

    /**
     * @param homeAddress The homeAddress
     */
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    /**
     * @return The homePostalCode
     */
    public String getHomePostalCode() {
        return homePostalCode;
    }

    /**
     * @param homePostalCode The homePostalCode
     */
    public void setHomePostalCode(String homePostalCode) {
        this.homePostalCode = homePostalCode;
    }

    /**
     * @return The homePhoneNumber
     */
    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    /**
     * @param homePhoneNumber The homePhoneNumber
     */
    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * @return The legalCountry
     */
    public String getLegalCountry() {
        return legalCountry;
    }

    /**
     * @param legalCountry The legalCountry
     */
    public void setLegalCountry(String legalCountry) {
        this.legalCountry = legalCountry;
    }

    public PepCountry getPepCountry() {
        return pepCountry;
    }

    public void setPepCountry(PepCountry pepCountry) {
        //Handling if pepCountry object is null
        if(pepCountry==null){
         this.pepCountry =  new PepCountry(0);
        }else {
            this.pepCountry = pepCountry;
        }
    }

    /**
     * @return The legalProvince
     */
    public String getLegalProvince() {
        return legalProvince;
    }

    /**
     * @param legalProvince The legalProvince
     */
    public void setLegalProvince(String legalProvince) {
        this.legalProvince = legalProvince;
    }

    /**
     * @return The legalCity
     */
    public String getLegalCity() {
        return legalCity;
    }

    /**
     * @param legalCity The legalCity
     */
    public void setLegalCity(String legalCity) {
        this.legalCity = legalCity;
    }

    /**
     * @return The legalAddress
     */
    public String getLegalAddress() {
        return legalAddress;
    }

    /**
     * @param legalAddress The legalAddress
     */
    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }

    /**
     * @return The legalPostalCode
     */
    public String getLegalPostalCode() {
        return legalPostalCode;
    }

    /**
     * @param legalPostalCode The legalPostalCode
     */
    public void setLegalPostalCode(String legalPostalCode) {
        this.legalPostalCode = legalPostalCode;
    }

    /**
     * @return The legalPhoneNumber
     */
    public String getLegalPhoneNumber() {
        return legalPhoneNumber;
    }

    /**
     * @param legalPhoneNumber The legalPhoneNumber
     */
    public void setLegalPhoneNumber(String legalPhoneNumber) {
        this.legalPhoneNumber = legalPhoneNumber;
    }

    /**
     * @return The idType
     */
    public String getIdType() {
        return idType;
    }

    /**
     * @param idType The idType
     */
    public void setIdType(String idType) {
        this.idType = idType;
    }

    /**
     * @return The idNumber
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * @param idNumber The idNumber
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * @return The idExpirationDate
     */
    public String getIdExpirationDate() {
        return idExpirationDate;
    }

    /**
     * @param idExpirationDate The idExpirationDate
     */
    public void setIdExpirationDate(String idExpirationDate) {
        this.idExpirationDate = idExpirationDate;
    }

    /**
     * @return The nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @param nationality The nationality
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * @return The citizenship
     */
    public String getCitizenship() {
        return citizenship;
    }

    /**
     * @param citizenship The citizenship
     */
    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    /**
     * @return The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return The religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     * @param religion The religion
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

    /**
     * @return The maritalStatus
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * @param maritalStatus The maritalStatus
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * @return The educationBackground
     */
    public String getEducationBackground() {
        return educationBackground;
    }

    /**
     * @param educationBackground The educationBackground
     */
    public void setEducationBackground(String educationBackground) {
        this.educationBackground = educationBackground;
    }

    /**
     * @return The sourceOfIncome
     */
    public String getSourceOfIncome() {
        return sourceOfIncome;
    }

    /**
     * @param sourceOfIncome The sourceOfIncome
     */
    public void setSourceOfIncome(String sourceOfIncome) {
        this.sourceOfIncome = sourceOfIncome;
    }

    /**
     * @return The totalIncomePa
     */
    public String getTotalIncomePa() {
        return totalIncomePa;
    }

    /**
     * @param totalIncomePa The totalIncomePa
     */
    public void setTotalIncomePa(String totalIncomePa) {
        this.totalIncomePa = totalIncomePa;
    }

    /**
     * @return The investmentPurpose
     */
    public String getInvestmentPurpose() {
        return investmentPurpose;
    }

    /**
     * @param investmentPurpose The investmentPurpose
     */
    public void setInvestmentPurpose(String investmentPurpose) {
        this.investmentPurpose = investmentPurpose;
    }


    public String getOtherInvesmentExperience() {
        return otherInvesmentExperience;
    }

    public void setOtherInvesmentExperience(String otherInvesmentExperience) {
        this.otherInvesmentExperience = otherInvesmentExperience;
    }


    /**
     * @return The motherMaidenName
     */
    public String getMotherMaidenName() {
        return motherMaidenName;
    }

    /**
     * @param motherMaidenName The motherMaidenName
     */
    public void setMotherMaidenName(String motherMaidenName) {
        this.motherMaidenName = motherMaidenName;
    }

    /**
     * @return The beneficiaryName
     */
    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    /**
     * @param beneficiaryName The beneficiaryName
     */
    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    /**
     * @return The beneficiaryRelationship
     */
    public String getBeneficiaryRelationship() {
        return beneficiaryRelationship;
    }

    /**
     * @param beneficiaryRelationship The beneficiaryRelationship
     */
    public void setBeneficiaryRelationship(String beneficiaryRelationship) {
        this.beneficiaryRelationship = beneficiaryRelationship;
    }

    /**
     * @return The preferredMailingAddress
     */
    public String getPreferredMailingAddress() {
        return preferredMailingAddress;
    }

    /**
     * @param preferredMailingAddress The preferredMailingAddress
     */
    public void setPreferredMailingAddress(String preferredMailingAddress) {
        this.preferredMailingAddress = preferredMailingAddress;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getTotalAsset() {
        return totalAsset;
    }

    public void setTotalAsset(String totalAsset) {
        this.totalAsset = totalAsset;
    }

    public String getInvestmentExperience() {
        return investmentExperience;
    }

    public void setInvestmentExperience(String investmentExperience) {
        this.investmentExperience = investmentExperience;
    }

    public String getPepName() {
        return pepName;
    }

    public void setPepName(String pepName) {
        this.pepName = pepName;
    }

    public String getPepPosition() {
        return pepPosition;
    }

    public void setPepPosition(String pepPosition) {
        this.pepPosition = pepPosition;
    }

    public String getPepPublicFunction() {
        return pepPublicFunction;
    }

    public void setPepPublicFunction(String pepPublicFunction) {
        this.pepPublicFunction = pepPublicFunction;
    }

    public String getPepYearOfService() {
        return pepYearOfService;
    }

    public void setPepYearOfService(String pepYearOfService) {
        this.pepYearOfService = pepYearOfService;
    }

    public String getPepRelationship() {
        return pepRelationship;
    }

    public void setPepRelationship(String pepRelationship) {
        this.pepRelationship = pepRelationship;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

//    public String getTaxIdRegisDate() {
//        return taxIdRegisDate;
//    }
//
//    public void setTaxIdRegisDate(String taxIdRegisDate) {
//        this.taxIdRegisDate = taxIdRegisDate;
//    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getReferralName() {
        return referralName;
    }

    public void setReferralName(String referralName) {
        this.referralName = referralName;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getSettlementAccountName() {
        return settlementAccountName;
    }

    public void setSettlementAccountName(String settlementAccountName) {
        this.settlementAccountName = settlementAccountName;
    }

    public String getSettlementAccountNo() {
        return settlementAccountNo;
    }

    public void setSettlementAccountNo(String settlementAccountNo) {
        this.settlementAccountNo = settlementAccountNo;
    }

}
