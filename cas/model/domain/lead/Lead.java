package com.itechro.cas.model.domain.lead;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.applicationform.AFComment;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.customer.Customer;
import com.itechro.cas.model.domain.customer.CustomerBankDetail;
import com.itechro.cas.model.domain.customer.CustomerIdentification;
import com.itechro.cas.model.domain.applicationform.applicationfromcustomer.AFCustomer;
import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "T_LEAD")
public class Lead extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_PRIVILEGE")
    @SequenceGenerator(name = "SEQ_T_PRIVILEGE", sequenceName = "SEQ_T_PRIVILEGE", allocationSize = 1)
    @Column(name = "LEAD_ID")
    private Integer leadID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", insertable=false, updatable=false)
    private Customer customer;

    @Column(name = "CUSTOMER_ID")
    private Integer customerID;

    @Column(name = "LEAD_NAME")
    private String leadName;

    @Column(name = "LEAD_REF_NUMBER")
    private String leadRefNumber;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "MOBILE_NO")
    private String mobileNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Column(name = "ADDRESS")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "CIVIL_STATUS")
    private DomainConstants.CivilStatus civilStatus;

    @Column(name = "IDENTIFICATION_TYPE")
    private String identificationType;

    @Column(name = "IDENTIFICATION_NUMBER")
    private String identificationNumber;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "BRANCH_CODE")
    private String branchCode;

    @Column(name = "BRANCH_NAME")
    private String branchName;

    @Enumerated(EnumType.STRING)
    @Column(name = "ALLOW_FINACLE_DATA")
    private AppsConstants.YesNo allowFinacleData;

    @Enumerated(EnumType.STRING)
    @Column(name = "ALLOW_CRIB")
    private AppsConstants.YesNo allowCrib;

    @Enumerated(EnumType.STRING)
    @Column(name = "ALLOW_KALYPTO")
    private AppsConstants.YesNo allowKalypto;

    @Column(name = "CUSTOMER_BANK_ACCOUNT_NUMBER")
    private String customerBankAccountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "LEAD_STATUS")
    private DomainConstants.LeadStatus leadStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "LEAD_TYPE")
    private DomainConstants.LeadType leadType;

    @Column(name = "ASSIGN_USER_ID")
    private String assignUserID; //USER'S AD_USER_ID

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_EXISTING_CUSTOMER")
    private AppsConstants.YesNo isExistingCustomer;

    @Column(name = "TYPE_OF_BUSINESS")
    private String typeOfBusiness;

    @Column(name = "DESIGNATION")
    private String designation;

    @Enumerated(EnumType.STRING)
    @Column(name = "LEAD_CREATION_TYPE")
    private DomainConstants.LeadCreationType leadCreationType;

    @Column(name = "CONTACT_PERSON")
    private String contactPerson;

    @Column(name = "EMAIL")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_SUBMITTED")
    private AppsConstants.YesNo submitted;

    @Column(name = "FS_TYPE")
    private String fsType;

    @Column(name = "CREATED_USER_DISPLAY_NAME")
    private String createdUserDisplayName;

    @Column(name = "ASSIGN_USER_DISPLAY_NAME")
    private String assignUserDisplayName;

    @Column(name = "APPLICATION_FORM_ID")
    private Integer applicationFormID;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "lead")
    private Set<LeadFacilityDetail> leadFacilityDetails;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "lead")
    private Set<LeadAction> leadActions;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "lead")
    private Set<LeadDocument> leadDocuments;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "lead")
    private Set<LeadComment> leadCommentSet;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "lead")
    private LeadAppCode leadAppCode;

    @Column(name = "IS_COMP_LEAD")
    private String isCompLead;

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
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

    public AppsConstants.YesNo getAllowFinacleData() {
        return allowFinacleData;
    }

    public void setAllowFinacleData(AppsConstants.YesNo allowFinacleData) {
        this.allowFinacleData = allowFinacleData;
    }

    public AppsConstants.YesNo getAllowCrib() {
        return allowCrib;
    }

    public void setAllowCrib(AppsConstants.YesNo allowCrib) {
        this.allowCrib = allowCrib;
    }

    public AppsConstants.YesNo getAllowKalypto() {
        return allowKalypto;
    }

    public void setAllowKalypto(AppsConstants.YesNo allowKalypto) {
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

    public AppsConstants.YesNo getSubmitted() {
        return submitted;
    }

    public void setSubmitted(AppsConstants.YesNo submitted) {
        this.submitted = submitted;
    }

    public LeadFacilityDetail getLeadFacilityDetailByID(Integer facilityDetailID) {
        return this.getLeadFacilityDetails().stream().
                filter(leadFacilityDetail -> {
                    return facilityDetailID.equals(leadFacilityDetail.getLeadFacilityDetailID());
                }).findFirst().orElse(null);
    }

    public Set<LeadFacilityDetail> getLeadFacilityDetails() {
        if (leadFacilityDetails == null) {
            leadFacilityDetails = new HashSet<>();
        }
        return leadFacilityDetails;
    }

    public List<LeadFacilityDetail> getLeadOrderedFacilityDetails() {
        return getLeadFacilityDetails().stream().sorted(new Comparator<LeadFacilityDetail>() {
            @Override
            public int compare(LeadFacilityDetail o1, LeadFacilityDetail o2) {
                return o1.getLeadFacilityDetailID().compareTo(o2.getLeadFacilityDetailID());
            }
        }).collect(Collectors.toList());
    }

    public void setLeadFacilityDetails(Set<LeadFacilityDetail> leadFacilityDetails) {
        this.leadFacilityDetails = leadFacilityDetails;
    }

    public LeadAction getLeadActionByID(Integer leadActionID) {
        return this.getLeadActions().stream().
                filter(leadAction -> {
                    return leadActionID.equals(leadAction.getLeadActionID());
                }).findFirst().orElse(null);
    }

    public Set<LeadAction> getLeadActions() {
        if (leadActions == null) {
            leadActions = new HashSet<>();
        }
        return leadActions;
    }

    public List<LeadAction> getOrderedLeadActions() {
        return getLeadActions().stream().sorted(new Comparator<LeadAction>() {
            @Override
            public int compare(LeadAction o1, LeadAction o2) {
                return o1.getLeadActionID().compareTo(o2.getLeadActionID());
            }
        }).collect(Collectors.toList());
    }

    public void setLeadActions(Set<LeadAction> leadActions) {
        this.leadActions = leadActions;
    }

    public void addLeadAction(LeadAction leadAction) {
        leadAction.setLead(this);
        this.getLeadActions().add(leadAction);
    }

    public Set<LeadDocument> getLeadDocuments() {
        if (leadDocuments == null) {
            leadDocuments = new HashSet<>();
        }
        return leadDocuments;
    }

    public void setLeadDocuments(Set<LeadDocument> leadDocuments) {
        this.leadDocuments = leadDocuments;
    }


    public void addLeadDocument(LeadDocument leadDocument) {
        leadDocument.setLead(this);
        this.getLeadDocuments().add(leadDocument);
    }

    public LeadDocument getLeadDocumentByID(Integer leadDocID) {
        return this.getLeadDocuments().stream().
                filter(leadDocument -> {
                    return leadDocID.equals(leadDocument.getLeadDocumentID());
                }).findFirst().orElse(null);
    }

    public boolean isExternalLead() {
        return this.leadType == DomainConstants.LeadType.EXTERNAL;
    }

    public AppsConstants.YesNo getIsExistingCustomer() {
        return isExistingCustomer;
    }

    public void setIsExistingCustomer(AppsConstants.YesNo isExistingCustomer) {
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

    public DomainConstants.LeadCreationType getLeadCreationType() {
        return leadCreationType;
    }

    public void setLeadCreationType(DomainConstants.LeadCreationType leadCreationType) {
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

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getFsType() {
        return fsType;
    }

    public void setFsType(String fsType) {
        this.fsType = fsType;
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

    public Set<LeadComment> getLeadCommentSet() {
        if (leadCommentSet == null) {
            leadCommentSet = new HashSet<>();
        }
        return leadCommentSet;
    }

    public List<LeadComment> getOrderedLeadComments() {
        return getLeadCommentSet().stream().sorted(new Comparator<LeadComment>() {
            @Override
            public int compare(LeadComment o1, LeadComment o2) {
                return o1.getCommentID().compareTo(o2.getCommentID());
            }
        }).collect(Collectors.toList());
    }

    public void setLeadCommentSet(Set<LeadComment> leadCommentSet) {
        this.leadCommentSet = leadCommentSet;
    }

    public void addLeadComment(LeadComment leadComment) {
        leadComment.setLead(this);
        this.getLeadCommentSet().add(leadComment);
    }

    public LeadComment getLeadCommentByID(Integer commentID) {
        return this.getLeadCommentSet().stream().filter(afComment ->
                        commentID.equals(afComment.getCommentID()))
                .findFirst().orElse(null);
    }

    public LeadAppCode getLeadAppCode() {
        return leadAppCode;
    }

    public void setLeadAppCode(LeadAppCode leadAppCode) {
        this.leadAppCode = leadAppCode;
    }
    
    public String getIsCompLead() {
        return isCompLead;
    }

    public void setIsCompLead(String isCompLead) {
        this.isCompLead = isCompLead;
    }
}
