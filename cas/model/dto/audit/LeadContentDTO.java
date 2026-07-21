package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;


public class LeadContentDTO extends BaseContentDTO {

    @SerializedName("LEAD ID")
    private Integer leadID;

    @SerializedName("CUSTOMER NAME")
    private String customerName;

    @SerializedName("LEAD NAME")
    private String leadName;

    @SerializedName("LEAD REF NUMBER")
    private String leadRefNumber;

    @SerializedName("NAME")
    private String name;

    @SerializedName("ACCOUNT NUMBER")
    private String accountNumber;

    @SerializedName("MOBILE NO")
    private String mobileNo;

    @SerializedName("DATE OF BIRTH")
    private String dateOfBirth;

    @SerializedName("ADDRESS")
    private String address;

    @SerializedName("CIVIL STATUS")
    private String civilStatus;

    @SerializedName("IDENTIFICATION TYPE")
    private String identificationType;

    @SerializedName("IDENTIFICATION NUMBER")
    private String identificationNumber;

    @SerializedName("NATIONALITY")
    private String nationality;

    @SerializedName("BRANCH CODE")
    private String branchCode;

    @SerializedName("BRANCH NAME")
    private String branchName;

    @SerializedName("LEAD STATUS")
    private String leadStatus;

    @SerializedName("ASSIGN USER ID")
    private String assignUserID;

    @SerializedName("IS EXISTING CUSTOMER")
    private String isExistingCustomer;

    @SerializedName("TYPE OF BUSINESS")
    private String typeOfBusiness;

    @SerializedName("DESIGNATION")
    private String designation;

    @SerializedName("LEAD CREATION TYPE")
    private String leadCreationType;

    @SerializedName("CONTACT PERSON")
    private String contactPerson;

    @SerializedName("EMAIL")
    private String email;

    @SerializedName("FS TYPE")
    private String fsType;


    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public String getLeadRefNumber() {
        return leadRefNumber;
    }

    public void setLeadRefNumber(String leadRefNumber) {
        this.leadRefNumber = leadRefNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getLeadStatus() {
        return leadStatus;
    }

    public void setLeadStatus(String leadStatus) {
        this.leadStatus = leadStatus;
    }

    public String getAssignUserID() {
        return assignUserID;
    }

    public void setAssignUserID(String assignUserID) {
        this.assignUserID = assignUserID;
    }

    public String getIsExistingCustomer() {
        return isExistingCustomer;
    }

    public void setIsExistingCustomer(String isExistingCustomer) {
        this.isExistingCustomer = isExistingCustomer;
    }

    public String getTypeOfBusiness() {
        return typeOfBusiness;
    }

    public void setTypeOfBusiness(String typeOfBusiness) {
        this.typeOfBusiness = typeOfBusiness;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLeadCreationType() {
        return leadCreationType;
    }

    public void setLeadCreationType(String leadCreationType) {
        this.leadCreationType = leadCreationType;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFsType() {
        return fsType;
    }

    public void setFsType(String fsType) {
        this.fsType = fsType;
    }
}
