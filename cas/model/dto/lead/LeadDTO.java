package com.itechro.cas.model.dto.lead;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.applicationform.AFComment;
import com.itechro.cas.model.domain.lead.*;
import com.itechro.cas.model.dto.applicationform.AFCommentDTO;
import com.itechro.cas.util.CalendarUtil;
import com.itechro.cas.model.domain.customer.CustomerBankDetail;
import com.itechro.cas.model.dto.customer.CASCustomerBankDetailsDTO;
import com.itechro.cas.model.dto.customer.CustomerBankDetailsDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LeadDTO implements Serializable {

    private Integer leadID;

    private Integer leadId;

    private Integer customerID;

    private String customerName;

    private String leadName;

    private String leadRefNumber;

    private String name;

    private String accountNumber;

    private String mobileNo;

    private String dateOfBirthStr;

    private String address;

    private DomainConstants.CivilStatus civilStatus;

    private String identificationType;

    private String identificationNumber;

    private String nationality;

    private String branchCode;

    private String branchName;

    private boolean allowFinacleData;

    private boolean allowCrib;

    private boolean allowKalypto;

    private String customerBankAccountNumber;

    private DomainConstants.LeadStatus leadStatus;

    private DomainConstants.LeadType leadType;

    private String assignUserID;

    private List<LeadActionDTO> leadActionDTOList;

    private List<LeadFacilityDetailDTO> leadFacilityDetailDTOList;

    private List<LeadDocumentDTO> leadDocumentDTOList;

    private String createdBy;

    private String createdDateStr;

    private String modifiedDateStr;

    private String contactPerson;

    private DomainConstants.LeadCreationType leadCreationType;

    private String designation;

    private String typeOfBusiness;

    private String email;

    private AppsConstants.YesNo isExistingCustomer;

    private AppsConstants.YesNo submitted;

    //TODO move to another DTO
    private AppsConstants.YesNo isLast3MonthsLeadFound;

    private AppsConstants.YesNo isCompLead;

    private List<CustomerBankDetailsDTO> customerBankDetailsDTOList;

    private String leadFsType;

    private String createdUserDisplayName;

    private String assignUserDisplayName;

    private Integer applicationFormID;

    private List<LeadCommentDTO> leadCommentDTOList;

    private String externalAppDescription;
    private String externalAppRefNumber;

    private ComprehensiveLeadDTO comprehensiveLeadDTO;

    public LeadDTO() {
    }

    public LeadDTO(Lead lead) {
        this.leadID = lead.getLeadID();
        this.customerID = lead.getCustomerID();
        this.leadName = lead.getLeadName();
        this.leadRefNumber = lead.getLeadRefNumber();
        this.name = lead.getName();
        this.accountNumber = lead.getAccountNumber();
        this.mobileNo = lead.getMobileNo();
        if (lead.getDateOfBirth() != null) {
            this.dateOfBirthStr = CalendarUtil.getDefaultFormattedDateOnly(lead.getDateOfBirth());
        }
        this.address = lead.getAddress();
        this.civilStatus = lead.getCivilStatus();
        this.identificationType = lead.getIdentificationType();
        this.identificationNumber = lead.getIdentificationNumber();
        this.nationality = lead.getNationality();
        this.branchCode = lead.getBranchCode();
        this.branchName = lead.getBranchName();
        this.allowFinacleData = lead.getAllowFinacleData() == AppsConstants.YesNo.Y;
        this.allowCrib = lead.getAllowCrib() == AppsConstants.YesNo.Y;
        this.allowKalypto = lead.getAllowKalypto() == AppsConstants.YesNo.Y;
        this.customerBankAccountNumber = lead.getCustomerBankAccountNumber();
        this.leadStatus = lead.getLeadStatus();
        this.leadType = lead.getLeadType();
        this.assignUserID = lead.getAssignUserID();
        this.createdBy = lead.getCreatedBy();
        this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(lead.getCreatedDate());
        if (lead.getModifiedDate() != null) {
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(lead.getModifiedDate());
        }
        this.isExistingCustomer = lead.getIsExistingCustomer();
        this.typeOfBusiness = lead.getTypeOfBusiness();
        this.designation = lead.getDesignation();
        this.leadCreationType = lead.getLeadCreationType();
        this.contactPerson = lead.getContactPerson();
        this.email = lead.getEmail();
        this.submitted = lead.getSubmitted();
        this.leadFsType = lead.getFsType();
        this.createdUserDisplayName = lead.getCreatedUserDisplayName();
        this.assignUserDisplayName = lead.getAssignUserDisplayName();
        this.applicationFormID = lead.getApplicationFormID();

        for (LeadFacilityDetail leadFacilityDetail : lead.getLeadOrderedFacilityDetails()) {
            if (leadFacilityDetail.getStatus() == AppsConstants.Status.ACT) {
                this.getLeadFacilityDetailDTOList().add(new LeadFacilityDetailDTO(leadFacilityDetail));
            }
        }

        for (LeadAction leadAction : lead.getOrderedLeadActions()) {
            this.getLeadActionDTOList().add(new LeadActionDTO(leadAction));
        }

        for (LeadDocument leadDocument : lead.getLeadDocuments()) {
            this.getLeadDocumentDTOList().add(new LeadDocumentDTO(leadDocument));
        }


        for (LeadComment leadComment : lead.getOrderedLeadComments()) {
            if (leadComment.getStatus() == AppsConstants.Status.ACT) {
                this.getLeadCommentDTOList().add(new LeadCommentDTO(leadComment));
            }
        }


        if (lead.getCustomerID() != null) {
            for (CustomerBankDetail CustomerBankDetail : lead.getCustomer().getCustomerBankDetails()) {
                if (CustomerBankDetail.getStatus() == AppsConstants.Status.ACT) {
                    this.getCustomerBankDetailsDTOList().add(new CustomerBankDetailsDTO(CustomerBankDetail));
                }
            }
        }

        if(lead.getLeadID() !=null){
            if(lead.getLeadAppCode() !=null){
                this.externalAppRefNumber = lead.getLeadAppCode().getAppRef();
                if(lead.getLeadAppCode().getAppDescription() !=null){
                    this.externalAppDescription = lead.getLeadAppCode().getAppDescription().getAppDescription();
                }
            }
        }
    }

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
    }

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

    public String getDateOfBirthStr() {
        return dateOfBirthStr;
    }

    public void setDateOfBirthStr(String dateOfBirthStr) {
        this.dateOfBirthStr = dateOfBirthStr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DomainConstants.CivilStatus getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(DomainConstants.CivilStatus civilStatus) {
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

    public boolean isAllowFinacleData() {
        return allowFinacleData;
    }

    public void setAllowFinacleData(boolean allowFinacleData) {
        this.allowFinacleData = allowFinacleData;
    }

    public boolean isAllowCrib() {
        return allowCrib;
    }

    public void setAllowCrib(boolean allowCrib) {
        this.allowCrib = allowCrib;
    }

    public boolean isAllowKalypto() {
        return allowKalypto;
    }

    public void setAllowKalypto(boolean allowKalypto) {
        this.allowKalypto = allowKalypto;
    }

    public String getCustomerBankAccountNumber() {
        return customerBankAccountNumber;
    }

    public void setCustomerBankAccountNumber(String customerBankAccountNumber) {
        this.customerBankAccountNumber = customerBankAccountNumber;
    }

    public DomainConstants.LeadStatus getLeadStatus() {
        return leadStatus;
    }

    public void setLeadStatus(DomainConstants.LeadStatus leadStatus) {
        this.leadStatus = leadStatus;
    }

    public DomainConstants.LeadType getLeadType() {
        return leadType;
    }

    public void setLeadType(DomainConstants.LeadType leadType) {
        this.leadType = leadType;
    }

    public String getAssignUserID() {
        return assignUserID;
    }

    public void setAssignUserID(String assignUserID) {
        this.assignUserID = assignUserID;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<LeadActionDTO> getLeadActionDTOList() {
        if (leadActionDTOList == null) {
            leadActionDTOList = new ArrayList<>();
        }
        return leadActionDTOList;
    }

    public void setLeadActionDTOList(List<LeadActionDTO> leadActionDTOList) {
        this.leadActionDTOList = leadActionDTOList;
    }

    public List<LeadFacilityDetailDTO> getLeadFacilityDetailDTOList() {
        if (leadFacilityDetailDTOList == null) {
            leadFacilityDetailDTOList = new ArrayList<>();
        }
        return leadFacilityDetailDTOList;
    }

    public void setLeadFacilityDetailDTOList(List<LeadFacilityDetailDTO> leadFacilityDetailDTOList) {
        this.leadFacilityDetailDTOList = leadFacilityDetailDTOList;
    }

    public List<LeadDocumentDTO> getLeadDocumentDTOList() {
        if (leadDocumentDTOList == null) {
            leadDocumentDTOList = new ArrayList<>();
        }
        return leadDocumentDTOList;
    }

    public void setLeadDocumentDTOList(List<LeadDocumentDTO> leadDocumentDTOList) {
        this.leadDocumentDTOList = leadDocumentDTOList;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public boolean isExternalLead() {
        return this.leadType == DomainConstants.LeadType.EXTERNAL;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public DomainConstants.LeadCreationType getLeadCreationType() {
        return leadCreationType;
    }

    public void setLeadCreationType(DomainConstants.LeadCreationType leadCreationType) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AppsConstants.YesNo getIsLast3MonthsLeadFound() {
        if(isLast3MonthsLeadFound == null) {
            isLast3MonthsLeadFound = AppsConstants.YesNo.N;
        }
        return isLast3MonthsLeadFound;
    }

    public void setIsLast3MonthsLeadFound(AppsConstants.YesNo isLast3MonthsLeadFound) {
        this.isLast3MonthsLeadFound = isLast3MonthsLeadFound;
    }

    public AppsConstants.YesNo getSubmitted() {
        return submitted;
    }

    public void setSubmitted(AppsConstants.YesNo submitted) {
        this.submitted = submitted;
    }

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormId) {
        this.applicationFormID = applicationFormID;
    }

    public List<CustomerBankDetailsDTO> getCustomerBankDetailsDTOList() {
        if (customerBankDetailsDTOList == null) {
            this.customerBankDetailsDTOList = new ArrayList<>();
        }
        return customerBankDetailsDTOList;
    }

    public void setCustomerBankDetailsDTOList(List<CustomerBankDetailsDTO> customerBankDetailsDTOList) {
        this.customerBankDetailsDTOList = customerBankDetailsDTOList;
    }

    public String getLeadFsType() {
        return leadFsType;
    }

    public void setLeadFsType(String leadFsType) {
        this.leadFsType = leadFsType;
    }

    public String getCreatedUserDisplayName() {
        return createdUserDisplayName;
    }

    public void setCreatedUserDisplayName(String createdUserDisplayName) {
        this.createdUserDisplayName = createdUserDisplayName;
    }

    public String getAssignUserDisplayName() {
        return assignUserDisplayName;
    }

    public void setAssignUserDisplayName(String assignUserDisplayName) {
        this.assignUserDisplayName = assignUserDisplayName;
    }

    public List<LeadCommentDTO> getLeadCommentDTOList() {
        if (leadCommentDTOList == null) {
            this.leadCommentDTOList = new ArrayList<>();
        }
        return leadCommentDTOList;
    }

    public void setLeadCommentDTOList(List<LeadCommentDTO> leadCommentDTOList) {
        this.leadCommentDTOList = leadCommentDTOList;
    }

    @Override
    public String toString() {
        return "LeadDTO{" +
                "leadID=" + leadID +
                ", customerID=" + customerID +
                ", customerName='" + customerName + '\'' +
                ", leadName='" + leadName + '\'' +
                ", leadRefNumber='" + leadRefNumber + '\'' +
                ", name='" + name + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", dateOfBirthStr='" + dateOfBirthStr + '\'' +
                ", address='" + address + '\'' +
                ", civilStatus=" + civilStatus +
                ", identificationType='" + identificationType + '\'' +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", nationality='" + nationality + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", branchName='" + branchName + '\'' +
                ", allowFinacleData=" + allowFinacleData +
                ", allowCrib=" + allowCrib +
                ", allowKalypto=" + allowKalypto +
                ", customerBankAccountNumber='" + customerBankAccountNumber + '\'' +
                ", leadStatus=" + leadStatus +
                ", leadType=" + leadType +
                ", assignUserID='" + assignUserID + '\'' +
                ", leadActionDTOList=" + leadActionDTOList +
                ", leadFacilityDetailDTOList=" + leadFacilityDetailDTOList +
                ", leadDocumentDTOList=" + leadDocumentDTOList +
                ", createdBy='" + createdBy + '\'' +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", modifiedDateStr='" + modifiedDateStr + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", leadCreationType=" + leadCreationType +
                ", designation='" + designation + '\'' +
                ", typeOfBusiness='" + typeOfBusiness + '\'' +
                ", email='" + email + '\'' +
                ", isExistingCustomer=" + isExistingCustomer +
                ", submitted=" + submitted +
                ", isLast3MonthsLeadFound=" + isLast3MonthsLeadFound +
                ", customerBankDetailsDTOList=" + customerBankDetailsDTOList +
                ", leadFsType='" + leadFsType + '\'' +
                ", leadCommentDTOList=" + leadCommentDTOList +
                ", createdUserDisplayName='" + createdUserDisplayName + '\'' +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", applicationFormID=" + applicationFormID +
                '}';
    }

    public String getExternalAppDescription() {
        return externalAppDescription;
    }

    public void setExternalAppDescription(String externalAppDescription) {
        this.externalAppDescription = externalAppDescription;
    }

    public String getExternalAppRefNumber() {
        return externalAppRefNumber;
    }

    public void setExternalAppRefNumber(String externalAppRefNumber) {
        this.externalAppRefNumber = externalAppRefNumber;
    }

    public Integer getLeadId() {
        return leadId;
    }

    public void setLeadId(Integer leadId) {
        this.leadId = leadId;
    }

    public ComprehensiveLeadDTO getComprehensiveLeadDTO() {
        return comprehensiveLeadDTO;
    }

    public void setComprehensiveLeadDTO(ComprehensiveLeadDTO comprehensiveLeadDTO) {
        this.comprehensiveLeadDTO = comprehensiveLeadDTO;
    }

    public AppsConstants.YesNo getIsCompLead() {
        return isCompLead;
    }

    public void setIsCompLead(AppsConstants.YesNo isCompLead) {
        this.isCompLead = isCompLead;
    }
}
