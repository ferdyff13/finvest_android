package com.invisee.finvest.data.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * e
 * Created by fajarfatur on 2/12/16.
 */
public class KycModel extends RealmObject {

    public static final int DEFAULT_ID = 1;

    @PrimaryKey
    private int id;
    private String salutation;
    private String firstName;
    private String lastName;
    private String email;
    private String birthDate;
    private String birthPlace;
    private String occupation;
    private String natureOfBusiness;
    private String employerName;
    private String homeCountry;
    private String homeProvince;
    private String homeCity;
    private String homeAddress;
    private String homePostalCode;
    private String homePhoneNumber;
    private String legalCountry;
    private String legalProvince;
    private String legalCity;
    private String legalAddress;
    private String legalPostalCode;
    private String legalPhoneNumber;
    private String idType;
    private String idNumber;
    private String idExpirationDate;
    private String nationality;
    private String citizenship;
    private String gender;
    private String religion;
    private String maritalStatus;
    private String educationBackground;
    private String sourceOfIncome;
    private String totalIncomePa;
    private String investmentPurpose;
    private String totalAsset;
    private String investmentExperience;
    private String pepName;
    private String pepPosition;
    private String pepPublicFunction;
    private String pepCountry;
    private String pepYearOfService;
    private String pepRelationship;
    private String motherMaidenName;
    private String beneficiaryName;
    private String beneficiaryRelationship;
    private String preferredMailingAddress;
    private String taxId;
    private String taxIdRegisDate;
    private String settlementAccountName;
    private String settlementAccountNo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getNatureOfBusiness() {
        return natureOfBusiness;
    }

    public void setNatureOfBusiness(String natureOfBusiness) {
        this.natureOfBusiness = natureOfBusiness;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getHomeCountry() {
        return homeCountry;
    }

    public void setHomeCountry(String homeCountry) {
        this.homeCountry = homeCountry;
    }

    public String getHomeProvince() {
        return homeProvince;
    }

    public void setHomeProvince(String homeProvince) {
        this.homeProvince = homeProvince;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getHomePostalCode() {
        return homePostalCode;
    }

    public void setHomePostalCode(String homePostalCode) {
        this.homePostalCode = homePostalCode;
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public String getLegalCountry() {
        return legalCountry;
    }

    public void setLegalCountry(String legalCountry) {
        this.legalCountry = legalCountry;
    }

    public String getLegalProvince() {
        return legalProvince;
    }

    public void setLegalProvince(String legalProvince) {
        this.legalProvince = legalProvince;
    }

    public String getLegalCity() {
        return legalCity;
    }

    public void setLegalCity(String legalCity) {
        this.legalCity = legalCity;
    }

    public String getLegalAddress() {
        return legalAddress;
    }

    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }

    public String getLegalPostalCode() {
        return legalPostalCode;
    }

    public void setLegalPostalCode(String legalPostalCode) {
        this.legalPostalCode = legalPostalCode;
    }

    public String getLegalPhoneNumber() {
        return legalPhoneNumber;
    }

    public void setLegalPhoneNumber(String legalPhoneNumber) {
        this.legalPhoneNumber = legalPhoneNumber;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdExpirationDate() {
        return idExpirationDate;
    }

    public void setIdExpirationDate(String idExpirationDate) {
        this.idExpirationDate = idExpirationDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEducationBackground() {
        return educationBackground;
    }

    public void setEducationBackground(String educationBackground) {
        this.educationBackground = educationBackground;
    }

    public String getSourceOfIncome() {
        return sourceOfIncome;
    }

    public void setSourceOfIncome(String sourceOfIncome) {
        this.sourceOfIncome = sourceOfIncome;
    }

    public String getTotalIncomePa() {
        return totalIncomePa;
    }

    public void setTotalIncomePa(String totalIncomePa) {
        this.totalIncomePa = totalIncomePa;
    }

    public String getInvestmentPurpose() {
        return investmentPurpose;
    }

    public void setInvestmentPurpose(String investmentPurpose) {
        this.investmentPurpose = investmentPurpose;
    }

    public String getMotherMaidenName() {
        return motherMaidenName;
    }

    public void setMotherMaidenName(String motherMaidenName) {
        this.motherMaidenName = motherMaidenName;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryRelationship() {
        return beneficiaryRelationship;
    }

    public void setBeneficiaryRelationship(String beneficiaryRelationship) {
        this.beneficiaryRelationship = beneficiaryRelationship;
    }

    public String getPreferredMailingAddress() {
        return preferredMailingAddress;
    }

    public void setPreferredMailingAddress(String preferredMailingAddress) {
        this.preferredMailingAddress = preferredMailingAddress;
    }

    public static int getDefaultId() {
        return DEFAULT_ID;
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

    public String getPepCountry() {
        return pepCountry;
    }

    public void setPepCountry(String pepCountry) {
        this.pepCountry = pepCountry;
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

    public String getTaxIdRegisDate() {
        return taxIdRegisDate;
    }

    public void setTaxIdRegisDate(String taxIdRegisDate) {
        this.taxIdRegisDate = taxIdRegisDate;
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
