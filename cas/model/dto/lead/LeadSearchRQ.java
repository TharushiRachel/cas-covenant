package com.itechro.cas.model.dto.lead;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.common.PagedParamDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LeadSearchRQ extends PagedParamDTO {

    private Integer customerID;

    private String leadName;

    private String leadRefNumber;

    private String name;

    private String accountNumber;

    private String mobileNo;

    private Date dateOfBirth;

    private String address;

    private String identificationType;

    private String identificationNumber;

    private String nationality;

    private String branchCode;

    private String branchName;

    private String assignedUserID;

    private String contactPerson;

    private String leadCreationType;

    private String designation;

    private String typeOfBusiness;

    private AppsConstants.YesNo isExistingCustomer;

    private List<DomainConstants.LeadStatus> leadStatusList;

    private List<DomainConstants.LeadCreationType> leadCreationTypeList;

    private String createdFromDateStr;

    private String createdToDateStr;

    private AppsConstants.YesNo isInMyBranchLeadPage;

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<DomainConstants.LeadStatus> getLeadStatusList() {
        if (leadStatusList == null) {
            leadStatusList = new ArrayList<>();
        }
        return leadStatusList;
    }

    public void setLeadStatusList(List<DomainConstants.LeadStatus> leadStatusList) {
        this.leadStatusList = leadStatusList;
    }

    public String getCreatedFromDateStr() {
        return createdFromDateStr;
    }

    public void setCreatedFromDateStr(String createdFromDateStr) {
        this.createdFromDateStr = createdFromDateStr;
    }

    public String getCreatedToDateStr() {
        return createdToDateStr;
    }

    public void setCreatedToDateStr(String createdToDateStr) {
        this.createdToDateStr = createdToDateStr;
    }

    public AppsConstants.YesNo getIsInMyBranchLeadPage() {
        return isInMyBranchLeadPage;
    }

    public void setIsInMyBranchLeadPage(AppsConstants.YesNo isInMyBranchLeadPage) {
        this.isInMyBranchLeadPage = isInMyBranchLeadPage;
    }

    public String getAssignedUserID() {
        return assignedUserID;
    }

    public void setAssignedUserID(String assignedUserID) {
        this.assignedUserID = assignedUserID;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getLeadCreationType() {
        return leadCreationType;
    }

    public void setLeadCreationType(String leadCreationType) {
        this.leadCreationType = leadCreationType;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getTypeOfBusiness() {
        return typeOfBusiness;
    }

    public void setTypeOfBusiness(String typeOfBusiness) {
        this.typeOfBusiness = typeOfBusiness;
    }

    public AppsConstants.YesNo getIsExistingCustomer() {
        return isExistingCustomer;
    }

    public void setIsExistingCustomer(AppsConstants.YesNo isExistingCustomer) {
        this.isExistingCustomer = isExistingCustomer;
    }

    public List<DomainConstants.LeadCreationType> getLeadCreationTypeList() {
        return leadCreationTypeList;
    }

    public void setLeadCreationTypeList(List<DomainConstants.LeadCreationType> leadCreationTypeList) {
        this.leadCreationTypeList = leadCreationTypeList;
    }

    @Override
    public String toString() {
        return "LeadSearchRQ{" +
                "customerID=" + customerID +
                ", leadName='" + leadName + '\'' +
                ", leadRefNumber='" + leadRefNumber + '\'' +
                ", name='" + name + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", identificationType='" + identificationType + '\'' +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", nationality='" + nationality + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", branchName='" + branchName + '\'' +
                ", assignedUserID='" + assignedUserID + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", leadCreationType='" + leadCreationType + '\'' +
                ", designation='" + designation + '\'' +
                ", typeOfBusiness='" + typeOfBusiness + '\'' +
                ", isExistingCustomer=" + isExistingCustomer +
                ", leadStatusList=" + leadStatusList +
                ", leadCreationTypeList=" + leadCreationTypeList +
                ", createdFromDateStr='" + createdFromDateStr + '\'' +
                ", createdToDateStr='" + createdToDateStr + '\'' +
                '}';
    }
}
