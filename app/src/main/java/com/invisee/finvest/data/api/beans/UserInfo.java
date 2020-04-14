package com.invisee.finvest.data.api.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by glenrynaldi on 6/14/16.
 */
public class UserInfo {

    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("account")
    @Expose
    private Account account;
    @SerializedName("atCustomerId")
    @Expose
    private String atCustomerId;
    @SerializedName("beneficiaryName")
    @Expose
    private String beneficiaryName;
    @SerializedName("beneficiaryRelationship")
    @Expose
    private Object beneficiaryRelationship;
    @SerializedName("birthDate")
    @Expose
    private String birthDate;
    @SerializedName("birthPlace")
    @Expose
    private String birthPlace;
    @SerializedName("branchName")
    @Expose
    private Object branchName;
    @SerializedName("citizenship")
    @Expose
    private String citizenship;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("educationBackground")
    @Expose
    private String educationBackground;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("employementStatus")
    @Expose
    private Object employementStatus;
    @SerializedName("employerName")
    @Expose
    private Object employerName;
    @SerializedName("employmentDate")
    @Expose
    private Object employmentDate;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("homeAddress")
    @Expose
    private String homeAddress;
    @SerializedName("homeCity")
    @Expose
    private String homeCity;
    @SerializedName("homeCountry")
    @Expose
    private String homeCountry;
    @SerializedName("homeFaxNumber")
    @Expose
    private Object homeFaxNumber;
    @SerializedName("homePhoneNumber")
    @Expose
    private String homePhoneNumber;
    @SerializedName("homePostalCode")
    @Expose
    private String homePostalCode;
    @SerializedName("homeProvince")
    @Expose
    private String homeProvince;
    @SerializedName("idExpirationDate")
    @Expose
    private String idExpirationDate;
    @SerializedName("idNumber")
    @Expose
    private String idNumber;
    @SerializedName("idType")
    @Expose
    private String idType;
    @SerializedName("investmentExperience")
    @Expose
    private Object investmentExperience;
    @SerializedName("investmentPurpose")
    @Expose
    private String investmentPurpose;
    @SerializedName("jobTitle")
    @Expose
    private Object jobTitle;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("legalAddress")
    @Expose
    private String legalAddress;
    @SerializedName("legalCity")
    @Expose
    private String legalCity;
    @SerializedName("legalCountry")
    @Expose
    private String legalCountry;
    @SerializedName("legalFaxNumber")
    @Expose
    private Object legalFaxNumber;
    @SerializedName("legalPhoneNumber")
    @Expose
    private String legalPhoneNumber;
    @SerializedName("legalPostalCode")
    @Expose
    private String legalPostalCode;
    @SerializedName("legalProvince")
    @Expose
    private String legalProvince;
    @SerializedName("maritalStatus")
    @Expose
    private String maritalStatus;
    @SerializedName("middleName")
    @Expose
    private String middleName;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("motherMaidenName")
    @Expose
    private String motherMaidenName;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("natureOfBusiness")
    @Expose
    private String natureOfBusiness;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("officeAddress")
    @Expose
    private String officeAddress;
    @SerializedName("officeCity")
    @Expose
    private String officeCity;
    @SerializedName("officeCountry")
    @Expose
    private String officeCountry;
    @SerializedName("officeFaxNumber")
    @Expose
    private Object officeFaxNumber;
    @SerializedName("officePhoneNumber")
    @Expose
    private String officePhoneNumber;
    @SerializedName("officePostalCode")
    @Expose
    private String officePostalCode;
    @SerializedName("officeProvince")
    @Expose
    private String officeProvince;
    @SerializedName("oldValueFatca")
    @Expose
    private Object oldValueFatca;
    @SerializedName("oldValueKyc")
    @Expose
    private String oldValueKyc;
    @SerializedName("oldValueRiskProfile")
    @Expose
    private Object oldValueRiskProfile;
    @SerializedName("otherInvestmentExperience")
    @Expose
    private Object otherInvestmentExperience;
    @SerializedName("pepCountry")
    @Expose
    private Object pepCountry;
    @SerializedName("pepName")
    @Expose
    private Object pepName;
    @SerializedName("pepOther")
    @Expose
    private Object pepOther;
    @SerializedName("pepPosition")
    @Expose
    private Object pepPosition;
    @SerializedName("pepPublicFunction")
    @Expose
    private Object pepPublicFunction;
    @SerializedName("pepRelationship")
    @Expose
    private Object pepRelationship;
    @SerializedName("pepYearOfService")
    @Expose
    private Object pepYearOfService;
    @SerializedName("portalcif")
    @Expose
    private String portalcif;
    @SerializedName("preferredMailingAddress")
    @Expose
    private String preferredMailingAddress;
    @SerializedName("profession")
    @Expose
    private Object profession;
    @SerializedName("question")
    @Expose
    private Question question;
    @SerializedName("referral")
    @Expose
    private Object referral;
    @SerializedName("referralName")
    @Expose
    private Object referralName;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("riskProfile")
    @Expose
    private RiskProfile riskProfile;
    @SerializedName("sales")
    @Expose
    private Sales sales;
    @SerializedName("salutation")
    @Expose
    private String salutation;
    @SerializedName("sourceOfIncome")
    @Expose
    private String sourceOfIncome;
    @SerializedName("spouseDateOfBirth")
    @Expose
    private Object spouseDateOfBirth;
    @SerializedName("spouseName")
    @Expose
    private Object spouseName;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("taxId")
    @Expose
    private String taxId;
    @SerializedName("taxIdRegisDate")
    @Expose
    private Object taxIdRegisDate;
    @SerializedName("taxNo")
    @Expose
    private Object taxNo;
    @SerializedName("totalAsset")
    @Expose
    private Object totalAsset;
    @SerializedName("totalIncomePa")
    @Expose
    private String totalIncomePa;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("userAccounts")
    @Expose
    private String userAccounts;

    /**
     *
     * @return
     * The _class
     */
    public String getClass_() {
        return _class;
    }

    /**
     *
     * @param _class
     * The class
     */
    public void setClass_(String _class) {
        this._class = _class;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The account
     */
    public Account getAccount() {
        return account;
    }

    /**
     *
     * @param account
     * The account
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     *
     * @return
     * The atCustomerId
     */
    public String getAtCustomerId() {
        return atCustomerId;
    }

    /**
     *
     * @param atCustomerId
     * The atCustomerId
     */
    public void setAtCustomerId(String atCustomerId) {
        this.atCustomerId = atCustomerId;
    }

    /**
     *
     * @return
     * The beneficiaryName
     */
    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    /**
     *
     * @param beneficiaryName
     * The beneficiaryName
     */
    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    /**
     *
     * @return
     * The beneficiaryRelationship
     */
    public Object getBeneficiaryRelationship() {
        return beneficiaryRelationship;
    }

    /**
     *
     * @param beneficiaryRelationship
     * The beneficiaryRelationship
     */
    public void setBeneficiaryRelationship(Object beneficiaryRelationship) {
        this.beneficiaryRelationship = beneficiaryRelationship;
    }

    /**
     *
     * @return
     * The birthDate
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     *
     * @param birthDate
     * The birthDate
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     *
     * @return
     * The birthPlace
     */
    public String getBirthPlace() {
        return birthPlace;
    }

    /**
     *
     * @param birthPlace
     * The birthPlace
     */
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    /**
     *
     * @return
     * The branchName
     */
    public Object getBranchName() {
        return branchName;
    }

    /**
     *
     * @param branchName
     * The branchName
     */
    public void setBranchName(Object branchName) {
        this.branchName = branchName;
    }

    /**
     *
     * @return
     * The citizenship
     */
    public String getCitizenship() {
        return citizenship;
    }

    /**
     *
     * @param citizenship
     * The citizenship
     */
    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    /**
     *
     * @return
     * The createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy
     * The createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return
     * The createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     *
     * @param createdDate
     * The createdDate
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     *
     * @return
     * The educationBackground
     */
    public String getEducationBackground() {
        return educationBackground;
    }

    /**
     *
     * @param educationBackground
     * The educationBackground
     */
    public void setEducationBackground(String educationBackground) {
        this.educationBackground = educationBackground;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The employementStatus
     */
    public Object getEmployementStatus() {
        return employementStatus;
    }

    /**
     *
     * @param employementStatus
     * The employementStatus
     */
    public void setEmployementStatus(Object employementStatus) {
        this.employementStatus = employementStatus;
    }

    /**
     *
     * @return
     * The employerName
     */
    public Object getEmployerName() {
        return employerName;
    }

    /**
     *
     * @param employerName
     * The employerName
     */
    public void setEmployerName(Object employerName) {
        this.employerName = employerName;
    }

    /**
     *
     * @return
     * The employmentDate
     */
    public Object getEmploymentDate() {
        return employmentDate;
    }

    /**
     *
     * @param employmentDate
     * The employmentDate
     */
    public void setEmploymentDate(Object employmentDate) {
        this.employmentDate = employmentDate;
    }

    /**
     *
     * @return
     * The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     * The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     * The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     * The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     * The homeAddress
     */
    public String getHomeAddress() {
        return homeAddress;
    }

    /**
     *
     * @param homeAddress
     * The homeAddress
     */
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    /**
     *
     * @return
     * The homeCity
     */
    public String getHomeCity() {
        return homeCity;
    }

    /**
     *
     * @param homeCity
     * The homeCity
     */
    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    /**
     *
     * @return
     * The homeCountry
     */
    public String getHomeCountry() {
        return homeCountry;
    }

    /**
     *
     * @param homeCountry
     * The homeCountry
     */
    public void setHomeCountry(String homeCountry) {
        this.homeCountry = homeCountry;
    }

    /**
     *
     * @return
     * The homeFaxNumber
     */
    public Object getHomeFaxNumber() {
        return homeFaxNumber;
    }

    /**
     *
     * @param homeFaxNumber
     * The homeFaxNumber
     */
    public void setHomeFaxNumber(Object homeFaxNumber) {
        this.homeFaxNumber = homeFaxNumber;
    }

    /**
     *
     * @return
     * The homePhoneNumber
     */
    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    /**
     *
     * @param homePhoneNumber
     * The homePhoneNumber
     */
    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    /**
     *
     * @return
     * The homePostalCode
     */
    public String getHomePostalCode() {
        return homePostalCode;
    }

    /**
     *
     * @param homePostalCode
     * The homePostalCode
     */
    public void setHomePostalCode(String homePostalCode) {
        this.homePostalCode = homePostalCode;
    }

    /**
     *
     * @return
     * The homeProvince
     */
    public String getHomeProvince() {
        return homeProvince;
    }

    /**
     *
     * @param homeProvince
     * The homeProvince
     */
    public void setHomeProvince(String homeProvince) {
        this.homeProvince = homeProvince;
    }

    /**
     *
     * @return
     * The idExpirationDate
     */
    public String getIdExpirationDate() {
        return idExpirationDate;
    }

    /**
     *
     * @param idExpirationDate
     * The idExpirationDate
     */
    public void setIdExpirationDate(String idExpirationDate) {
        this.idExpirationDate = idExpirationDate;
    }

    /**
     *
     * @return
     * The idNumber
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     *
     * @param idNumber
     * The idNumber
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    /**
     *
     * @return
     * The idType
     */
    public String getIdType() {
        return idType;
    }

    /**
     *
     * @param idType
     * The idType
     */
    public void setIdType(String idType) {
        this.idType = idType;
    }

    /**
     *
     * @return
     * The investmentExperience
     */
    public Object getInvestmentExperience() {
        return investmentExperience;
    }

    /**
     *
     * @param investmentExperience
     * The investmentExperience
     */
    public void setInvestmentExperience(Object investmentExperience) {
        this.investmentExperience = investmentExperience;
    }

    /**
     *
     * @return
     * The investmentPurpose
     */
    public String getInvestmentPurpose() {
        return investmentPurpose;
    }

    /**
     *
     * @param investmentPurpose
     * The investmentPurpose
     */
    public void setInvestmentPurpose(String investmentPurpose) {
        this.investmentPurpose = investmentPurpose;
    }

    /**
     *
     * @return
     * The jobTitle
     */
    public Object getJobTitle() {
        return jobTitle;
    }

    /**
     *
     * @param jobTitle
     * The jobTitle
     */
    public void setJobTitle(Object jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     *
     * @return
     * The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     * The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     * The legalAddress
     */
    public String getLegalAddress() {
        return legalAddress;
    }

    /**
     *
     * @param legalAddress
     * The legalAddress
     */
    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }

    /**
     *
     * @return
     * The legalCity
     */
    public String getLegalCity() {
        return legalCity;
    }

    /**
     *
     * @param legalCity
     * The legalCity
     */
    public void setLegalCity(String legalCity) {
        this.legalCity = legalCity;
    }

    /**
     *
     * @return
     * The legalCountry
     */
    public String getLegalCountry() {
        return legalCountry;
    }

    /**
     *
     * @param legalCountry
     * The legalCountry
     */
    public void setLegalCountry(String legalCountry) {
        this.legalCountry = legalCountry;
    }

    /**
     *
     * @return
     * The legalFaxNumber
     */
    public Object getLegalFaxNumber() {
        return legalFaxNumber;
    }

    /**
     *
     * @param legalFaxNumber
     * The legalFaxNumber
     */
    public void setLegalFaxNumber(Object legalFaxNumber) {
        this.legalFaxNumber = legalFaxNumber;
    }

    /**
     *
     * @return
     * The legalPhoneNumber
     */
    public String getLegalPhoneNumber() {
        return legalPhoneNumber;
    }

    /**
     *
     * @param legalPhoneNumber
     * The legalPhoneNumber
     */
    public void setLegalPhoneNumber(String legalPhoneNumber) {
        this.legalPhoneNumber = legalPhoneNumber;
    }

    /**
     *
     * @return
     * The legalPostalCode
     */
    public String getLegalPostalCode() {
        return legalPostalCode;
    }

    /**
     *
     * @param legalPostalCode
     * The legalPostalCode
     */
    public void setLegalPostalCode(String legalPostalCode) {
        this.legalPostalCode = legalPostalCode;
    }

    /**
     *
     * @return
     * The legalProvince
     */
    public String getLegalProvince() {
        return legalProvince;
    }

    /**
     *
     * @param legalProvince
     * The legalProvince
     */
    public void setLegalProvince(String legalProvince) {
        this.legalProvince = legalProvince;
    }

    /**
     *
     * @return
     * The maritalStatus
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     *
     * @param maritalStatus
     * The maritalStatus
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     *
     * @return
     * The middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     *
     * @param middleName
     * The middleName
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     *
     * @return
     * The mobileNumber
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     *
     * @param mobileNumber
     * The mobileNumber
     */
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     *
     * @return
     * The motherMaidenName
     */
    public String getMotherMaidenName() {
        return motherMaidenName;
    }

    /**
     *
     * @param motherMaidenName
     * The motherMaidenName
     */
    public void setMotherMaidenName(String motherMaidenName) {
        this.motherMaidenName = motherMaidenName;
    }

    /**
     *
     * @return
     * The nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     *
     * @param nationality
     * The nationality
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     *
     * @return
     * The natureOfBusiness
     */
    public String getNatureOfBusiness() {
        return natureOfBusiness;
    }

    /**
     *
     * @param natureOfBusiness
     * The natureOfBusiness
     */
    public void setNatureOfBusiness(String natureOfBusiness) {
        this.natureOfBusiness = natureOfBusiness;
    }

    /**
     *
     * @return
     * The occupation
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     *
     * @param occupation
     * The occupation
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     *
     * @return
     * The officeAddress
     */
    public String getOfficeAddress() {
        return officeAddress;
    }

    /**
     *
     * @param officeAddress
     * The officeAddress
     */
    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    /**
     *
     * @return
     * The officeCity
     */
    public String getOfficeCity() {
        return officeCity;
    }

    /**
     *
     * @param officeCity
     * The officeCity
     */
    public void setOfficeCity(String officeCity) {
        this.officeCity = officeCity;
    }

    /**
     *
     * @return
     * The officeCountry
     */
    public String getOfficeCountry() {
        return officeCountry;
    }

    /**
     *
     * @param officeCountry
     * The officeCountry
     */
    public void setOfficeCountry(String officeCountry) {
        this.officeCountry = officeCountry;
    }

    /**
     *
     * @return
     * The officeFaxNumber
     */
    public Object getOfficeFaxNumber() {
        return officeFaxNumber;
    }

    /**
     *
     * @param officeFaxNumber
     * The officeFaxNumber
     */
    public void setOfficeFaxNumber(Object officeFaxNumber) {
        this.officeFaxNumber = officeFaxNumber;
    }

    /**
     *
     * @return
     * The officePhoneNumber
     */
    public String getOfficePhoneNumber() {
        return officePhoneNumber;
    }

    /**
     *
     * @param officePhoneNumber
     * The officePhoneNumber
     */
    public void setOfficePhoneNumber(String officePhoneNumber) {
        this.officePhoneNumber = officePhoneNumber;
    }

    /**
     *
     * @return
     * The officePostalCode
     */
    public String getOfficePostalCode() {
        return officePostalCode;
    }

    /**
     *
     * @param officePostalCode
     * The officePostalCode
     */
    public void setOfficePostalCode(String officePostalCode) {
        this.officePostalCode = officePostalCode;
    }

    /**
     *
     * @return
     * The officeProvince
     */
    public String getOfficeProvince() {
        return officeProvince;
    }

    /**
     *
     * @param officeProvince
     * The officeProvince
     */
    public void setOfficeProvince(String officeProvince) {
        this.officeProvince = officeProvince;
    }

    /**
     *
     * @return
     * The oldValueFatca
     */
    public Object getOldValueFatca() {
        return oldValueFatca;
    }

    /**
     *
     * @param oldValueFatca
     * The oldValueFatca
     */
    public void setOldValueFatca(Object oldValueFatca) {
        this.oldValueFatca = oldValueFatca;
    }

    /**
     *
     * @return
     * The oldValueKyc
     */
    public String getOldValueKyc() {
        return oldValueKyc;
    }

    /**
     *
     * @param oldValueKyc
     * The oldValueKyc
     */
    public void setOldValueKyc(String oldValueKyc) {
        this.oldValueKyc = oldValueKyc;
    }

    /**
     *
     * @return
     * The oldValueRiskProfile
     */
    public Object getOldValueRiskProfile() {
        return oldValueRiskProfile;
    }

    /**
     *
     * @param oldValueRiskProfile
     * The oldValueRiskProfile
     */
    public void setOldValueRiskProfile(Object oldValueRiskProfile) {
        this.oldValueRiskProfile = oldValueRiskProfile;
    }

    /**
     *
     * @return
     * The otherInvestmentExperience
     */
    public Object getOtherInvestmentExperience() {
        return otherInvestmentExperience;
    }

    /**
     *
     * @param otherInvestmentExperience
     * The otherInvestmentExperience
     */
    public void setOtherInvestmentExperience(Object otherInvestmentExperience) {
        this.otherInvestmentExperience = otherInvestmentExperience;
    }

    /**
     *
     * @return
     * The pepCountry
     */
    public Object getPepCountry() {
        return pepCountry;
    }

    /**
     *
     * @param pepCountry
     * The pepCountry
     */
    public void setPepCountry(Object pepCountry) {
        this.pepCountry = pepCountry;
    }

    /**
     *
     * @return
     * The pepName
     */
    public Object getPepName() {
        return pepName;
    }

    /**
     *
     * @param pepName
     * The pepName
     */
    public void setPepName(Object pepName) {
        this.pepName = pepName;
    }

    /**
     *
     * @return
     * The pepOther
     */
    public Object getPepOther() {
        return pepOther;
    }

    /**
     *
     * @param pepOther
     * The pepOther
     */
    public void setPepOther(Object pepOther) {
        this.pepOther = pepOther;
    }

    /**
     *
     * @return
     * The pepPosition
     */
    public Object getPepPosition() {
        return pepPosition;
    }

    /**
     *
     * @param pepPosition
     * The pepPosition
     */
    public void setPepPosition(Object pepPosition) {
        this.pepPosition = pepPosition;
    }

    /**
     *
     * @return
     * The pepPublicFunction
     */
    public Object getPepPublicFunction() {
        return pepPublicFunction;
    }

    /**
     *
     * @param pepPublicFunction
     * The pepPublicFunction
     */
    public void setPepPublicFunction(Object pepPublicFunction) {
        this.pepPublicFunction = pepPublicFunction;
    }

    /**
     *
     * @return
     * The pepRelationship
     */
    public Object getPepRelationship() {
        return pepRelationship;
    }

    /**
     *
     * @param pepRelationship
     * The pepRelationship
     */
    public void setPepRelationship(Object pepRelationship) {
        this.pepRelationship = pepRelationship;
    }

    /**
     *
     * @return
     * The pepYearOfService
     */
    public Object getPepYearOfService() {
        return pepYearOfService;
    }

    /**
     *
     * @param pepYearOfService
     * The pepYearOfService
     */
    public void setPepYearOfService(Object pepYearOfService) {
        this.pepYearOfService = pepYearOfService;
    }

    /**
     *
     * @return
     * The portalcif
     */
    public String getPortalcif() {
        return portalcif;
    }

    /**
     *
     * @param portalcif
     * The portalcif
     */
    public void setPortalcif(String portalcif) {
        this.portalcif = portalcif;
    }

    /**
     *
     * @return
     * The preferredMailingAddress
     */
    public String getPreferredMailingAddress() {
        return preferredMailingAddress;
    }

    /**
     *
     * @param preferredMailingAddress
     * The preferredMailingAddress
     */
    public void setPreferredMailingAddress(String preferredMailingAddress) {
        this.preferredMailingAddress = preferredMailingAddress;
    }

    /**
     *
     * @return
     * The profession
     */
    public Object getProfession() {
        return profession;
    }

    /**
     *
     * @param profession
     * The profession
     */
    public void setProfession(Object profession) {
        this.profession = profession;
    }

    /**
     *
     * @return
     * The question
     */
    public Question getQuestion() {
        return question;
    }

    /**
     *
     * @param question
     * The question
     */
    public void setQuestion(Question question) {
        this.question = question;
    }

    /**
     *
     * @return
     * The referral
     */
    public Object getReferral() {
        return referral;
    }

    /**
     *
     * @param referral
     * The referral
     */
    public void setReferral(Object referral) {
        this.referral = referral;
    }

    /**
     *
     * @return
     * The referralName
     */
    public Object getReferralName() {
        return referralName;
    }

    /**
     *
     * @param referralName
     * The referralName
     */
    public void setReferralName(Object referralName) {
        this.referralName = referralName;
    }

    /**
     *
     * @return
     * The religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     *
     * @param religion
     * The religion
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

    /**
     *
     * @return
     * The riskProfile
     */
    public RiskProfile getRiskProfile() {
        return riskProfile;
    }

    /**
     *
     * @param riskProfile
     * The riskProfile
     */
    public void setRiskProfile(RiskProfile riskProfile) {
        this.riskProfile = riskProfile;
    }

    /**
     *
     * @return
     * The sales
     */
    public Sales getSales() {
        return sales;
    }

    /**
     *
     * @param sales
     * The sales
     */
    public void setSales(Sales sales) {
        this.sales = sales;
    }

    /**
     *
     * @return
     * The salutation
     */
    public String getSalutation() {
        return salutation;
    }

    /**
     *
     * @param salutation
     * The salutation
     */
    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    /**
     *
     * @return
     * The sourceOfIncome
     */
    public String getSourceOfIncome() {
        return sourceOfIncome;
    }

    /**
     *
     * @param sourceOfIncome
     * The sourceOfIncome
     */
    public void setSourceOfIncome(String sourceOfIncome) {
        this.sourceOfIncome = sourceOfIncome;
    }

    /**
     *
     * @return
     * The spouseDateOfBirth
     */
    public Object getSpouseDateOfBirth() {
        return spouseDateOfBirth;
    }

    /**
     *
     * @param spouseDateOfBirth
     * The spouseDateOfBirth
     */
    public void setSpouseDateOfBirth(Object spouseDateOfBirth) {
        this.spouseDateOfBirth = spouseDateOfBirth;
    }

    /**
     *
     * @return
     * The spouseName
     */
    public Object getSpouseName() {
        return spouseName;
    }

    /**
     *
     * @param spouseName
     * The spouseName
     */
    public void setSpouseName(Object spouseName) {
        this.spouseName = spouseName;
    }

    /**
     *
     * @return
     * The status
     */
    public Object getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Object status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The taxId
     */
    public String getTaxId() {
        return taxId;
    }

    /**
     *
     * @param taxId
     * The taxId
     */
    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    /**
     *
     * @return
     * The taxIdRegisDate
     */
    public Object getTaxIdRegisDate() {
        return taxIdRegisDate;
    }

    /**
     *
     * @param taxIdRegisDate
     * The taxIdRegisDate
     */
    public void setTaxIdRegisDate(Object taxIdRegisDate) {
        this.taxIdRegisDate = taxIdRegisDate;
    }

    /**
     *
     * @return
     * The taxNo
     */
    public Object getTaxNo() {
        return taxNo;
    }

    /**
     *
     * @param taxNo
     * The taxNo
     */
    public void setTaxNo(Object taxNo) {
        this.taxNo = taxNo;
    }

    /**
     *
     * @return
     * The totalAsset
     */
    public Object getTotalAsset() {
        return totalAsset;
    }

    /**
     *
     * @param totalAsset
     * The totalAsset
     */
    public void setTotalAsset(Object totalAsset) {
        this.totalAsset = totalAsset;
    }

    /**
     *
     * @return
     * The totalIncomePa
     */
    public String getTotalIncomePa() {
        return totalIncomePa;
    }

    /**
     *
     * @param totalIncomePa
     * The totalIncomePa
     */
    public void setTotalIncomePa(String totalIncomePa) {
        this.totalIncomePa = totalIncomePa;
    }

    /**
     *
     * @return
     * The updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     *
     * @param updatedBy
     * The updatedBy
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     *
     * @return
     * The updatedDate
     */
    public String getUpdatedDate() {
        return updatedDate;
    }

    /**
     *
     * @param updatedDate
     * The updatedDate
     */
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     *
     * @return
     * The userAccounts
     */
    public String getUserAccounts() {
        return userAccounts;
    }

    /**
     *
     * @param userAccounts
     * The userAccounts
     */
    public void setUserAccounts(String userAccounts) {
        this.userAccounts = userAccounts;
    }

}
