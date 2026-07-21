package com.itechro.cas.model.domain.facilitypaper.facility;


import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.bcc.FPBcc;
import com.itechro.cas.model.domain.casmaster.UpcTemplate;
import com.itechro.cas.model.domain.casmaster.WorkFlowTemplate;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.covenant.ApplicationLevelCovenant;
import com.itechro.cas.model.domain.covenant.CustomerCovenant;
import com.itechro.cas.model.domain.esg.EnvironmentalRiskData;
import com.itechro.cas.model.domain.facilitypaper.*;
import com.itechro.cas.model.domain.lead.LeadAppCode;
import com.itechro.cas.model.domain.facilitypaper.committee.CommitteeInquiryMaster;
import com.itechro.cas.util.DecimalCalculator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "T_FACILITY_PAPER")
public class FacilityPaper extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FACILITY_PAPER")
    @SequenceGenerator(name = "SEQ_T_FACILITY_PAPER", sequenceName = "SEQ_T_FACILITY_PAPER", allocationSize = 1)
    @Column(name = "FACILITY_PAPER_ID")
    private Integer facilityPaperID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPC_TEMPLATE_ID")
    @NotAudited
    private UpcTemplate upcTemplate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKFLOW_TEMPLATE_ID")
    @NotAudited
    private WorkFlowTemplate workFlowTemplate;

    @Column(name = "BRANCH_CODE")
    private String branchCode; // Div Code of initiate branch/Department

    @Column(name = "CREATED_USER_DISPLAY_NAME")
    private String createdUserDisplayName;

    @Column(name = "LEAD_ID")
    private Integer leadID;

    @Column(name = "FP_REF_NUMBER")
    private String fpRefNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "LEAD_TYPE")
    private DomainConstants.LeadType leadType;

    @Column(name = "LEAD_REF_NUMBER")
    private String leadRefNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_COOPERATE")
    private AppsConstants.YesNo isCooperate;

    @Column(name = "FACILITY_PAPER_NUMBER")
    private String facilityPaperNumber;

    @Column(name = "CURRENT_CYCLE")
    private Integer currentCycle;

    //TODO:
    @Column(name = "BANK_ACCOUNT_ID")
    private String bankAccountID;

    @Column(name = "FP_APPROVING_AUTHORITY_LEVEL")
    private String fpApprovingAuthorityLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENT_FACILITY_PAPER_STATUS")
    private DomainConstants.FacilityPaperStatus currentFacilityPaperStatus;

    @Column(name = "CURRENT_AUTHORITY_LEVEL")
    private String currentAuthorityLevel;

    @Column(name = "CURRENT_ASSIGN_USER_ID")
    private Integer currentAssignUserID;

    @Column(name = "CURRENT_ASSIGN_AD_USER_ID")
    private String currentAssignADUserID;

    @Column(name = "CURRENT_ASSIGN_USER")
    private String currentAssignUser;

    @Column(name = "CURRENT_ASSIGN_USER_DIV_CODE")
    private String currentAssignUserDivCode;

    @Column(name = "ROA_PERCENTAGE_EXISTING")
    private Double existingFacilitiesROA;

    @Column(name = "ROA_PERCENTAGE_PROPOSED")
    private Double proposedFacilitiesROA;

    @Column(name = "TOTAL_DIRECT_EXPOSURE_PRE")
    private BigDecimal totalDirectExposurePrevious;

    @Column(name = "TOTAL_DIRECT_EXPOSURE_NEW")
    private BigDecimal totalDirectExposureNew;

    @Column(name = "TOTAL_INDIRECT_EXPOSURE_PRE")
    private BigDecimal totalIndirectExposurePrevious;

    @Column(name = "TOTAL_INDIRECT_EXPOSURE_NEW")
    private BigDecimal totalIndirectExposureNew;

    @Column(name = "TOTAL_EXPOSURE_PREVIOUS")
    private BigDecimal totalExposurePrevious;

    @Column(name = "TOTAL_EXPOSURE_NEW")
    private BigDecimal totalExposureNew;

    @Column(name = "TOTAL_DIRECT_EXPOSURE_EXIS")
    private BigDecimal totalDirectExposureExisting;

    @Column(name = "TOTAL_INDIRECT_EXPOSURE_EXIS")
    private BigDecimal totalIndirectExposureExisting;

    @Column(name = "TOTAL_EXPOSURE_EXIS")
    private BigDecimal totalExposureExisting;

    @Column(name = "EXIS_CASH_MARGIN")
    private BigDecimal existingCashMargin;

    @Column(name = "OUT_CASH_MARGIN")
    private BigDecimal outstandingCashMargin;

    @Column(name = "PRO_CASH_MARGIN")
    private BigDecimal proposedCashMargin;

    @Column(name = "NET_TOTAL_EXPOSURE_NEW")
    private BigDecimal netTotalExposureNew;

    @Column(name = "NET_TOTAL_EXPOSURE_PRE")
    private BigDecimal netTotalExposurePrevious;

    @Column(name = "NET_TOTAL_EXPOSURE_EXIS")
    private BigDecimal netTotalExposureExisting;

    @Enumerated(EnumType.STRING)
    @Column(name = "ADD_TOTAL_EXP_TO_GROUP_EXP")
    private AppsConstants.YesNo addTotalExposureToGroup;

    @Column(name = "GRP_TOTAL_DIRECT_EXPOSURE_PRE")
    private BigDecimal groupTotalDirectExposurePrevious;

    @Column(name = "GRP_TOTAL_DIRECT_EXPOSURE_NEW")
    private BigDecimal groupTotalDirectExposureNew;

    @Column(name = "GRP_TOTAL_INDIR_EXPOSURE_PRE")
    private BigDecimal groupTotalIndirectExposurePrevious;

    @Column(name = "GRP_TOTAL_INDIR_EXPOSURE_NEW")
    private BigDecimal groupTotalIndirectExposureNew;

    @Column(name = "GRP_TOTAL_EXPOSURE_PRE")
    private BigDecimal groupTotalExposurePrevious;

    @Column(name = "GRP_TOTAL_EXPOSURE_NEW")
    private BigDecimal groupTotalExposureNew;

    @Column(name = "GRP_TOTAL_DIRECT_EXPOSURE_EXIS")
    private BigDecimal groupTotalDirectExposureExisting;

    @Column(name = "GRP_TOTAL_INDIR_EXPOSURE_EXIS")
    private BigDecimal groupTotalIndirectExposureExisting;

    @Column(name = "GRP_TOTAL_EXPOSURE_EXIS")
    private BigDecimal groupTotalExposureExisting;

    @Column(name = "GRP_EXIS_CASH_MARGIN")
    private BigDecimal groupExistingCashMargin;

    @Column(name = "GRP_OUT_CASH_MARGIN")
    private BigDecimal groupOutstandingCashMargin;

    @Column(name = "GRP_PRO_CASH_MARGIN")
    private BigDecimal groupProposedCashMargin;

    @Column(name = "GRP_NET_TOTAL_EXPOSURE_NEW")
    private BigDecimal groupNetTotalExposureNew;

    @Column(name = "GRP_NET_TOTAL_EXPOSURE_PRE")
    private BigDecimal groupNetTotalExposurePrevious;

    @Column(name = "GRP_NET_TOTAL_EXPOSURE_EXIS")
    private BigDecimal groupNetTotalExposureExisting;

    @Column(name = "ASSIGN_USER_DISPLAY_NAME")
    private String assignUserDisplayName;

    @Column(name = "ASSIGN_USER_UPM_ID")
    private Integer assignUserUpmID;

    @Column(name = "ASSIGN_USER_UPM_GROUP_CODE")
    private String assignUserUpmGroupCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "IN_PROGRESS_DATE", updatable = true)
    private Date inProgressDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "APPROVED_DATE", updatable = true)
    private Date approvedDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CANCELED_DATE", updatable = true)
    private Date canceledDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REJECTED_DATE", updatable = true)
    private Date rejectedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAPER_REVIEW_STATUS")
    private DomainConstants.PaperReviewStatus paperReviewStatus;

    @Column(name = "REVIEW_USER_DISPLAY_NAME")
    private String reviewUserDisplayName;

    @Column(name = "REVIEW_USER_ID")
    private Integer reviewUserID;

    @Column(name = "REVIEW_USER_NAME")
    private String reviewUserName;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_BCC_CREATED")
    private AppsConstants.YesNo isBccCreated;

    @Column(name = "BCC_CREATED_USER_DISPLAY_NAME")
    private String bccCreatedUserDisplayName;

    @Column(name = "CREATED_USER_BRANCH_CODE")
    private String createdUserBranchCode;

    @Column(name = "APPLICATION_FORM_ID")
    private Integer applicationFormID;

    @Column(name = "APP_FORM_REF_NUMBER")
    private String applicationFormRefNumber;

 /*   @Column(name = "CURRENT_USER_COMMITTEE_LEVEL")
    private Integer currentUserCommitteeLevel;*/

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OUTSTANDING_DATE")
    private Date outstandingDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "APPLICATION_FORM_TYPE")
    private DomainConstants.BasicInformationType applicationFormType;

    @Column(name = "ASSIGN_DEPARTMENT_CODE")
    private String assignDepartmentCode; // This is for cluster forwarding for one Branch or Department (SOL IDs) 900,827.... : if Multiple SOLs need to Deprecate this Field

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_COMMITTEE")
    private AppsConstants.YesNo isCommittee = AppsConstants.YesNo.N;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_FINANCIAL_YEAR")
    private AppsConstants.YesNo isFinancialYear = AppsConstants.YesNo.N;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_ESG_PAPER")
    private AppsConstants.YesNo isESGPaper;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_ESG_APPROVED")
    private AppsConstants.YesNo isESGApproved;

    @Column(name = "APPROVED_ESG_SCORE")
    private String approvedESGScore;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    private Set<FPComment> fpCommentSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    private Set<FPCompanyRoa> fpCompanyRoaSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    private Set<FPDirectorDetail> fpDirectorDetailSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    private Set<FPShareHolderDetail> fpShareHolderDetailSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
//    @NotAudited

    private Set<FPStatusHistory> fpStatusHistorySet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    private Set<FPUpcSectionData> fpUpcSectionDataSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    private Set<FPDocument> fpDocumentSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    private Set<CASCustomer> CASCustomerSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    private Set<Facility> facilitySet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    private Set<FPWorkflowRouting> fpWorkflowRoutingSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    @Audited
    private Set<FPCreditRiskComment> fpCreditRiskCommentSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    private Set<FPReviewerComment> fpReviewerComments;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    private FPSecuritySummery fpSecuritySummery;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    private Set<FPAssignDepartment> fpAssignDepartmentSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
//    @NotAudited
    private Set<CommitteePaper> committeePaperSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    @NotAudited
    private Set<FPBcc> fpBccSet;


    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    private Set<CustomerCovenant> customerCovenantSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    private Set<ApplicationLevelCovenant> applicationLevelCovenantSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    private Set<CribDetails> cribDetailSet;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEAD_ID", referencedColumnName = "LEAD_ID", insertable = false, updatable = false)
    private LeadAppCode leadAppCode;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "facilityPaper")
    private List<EnvironmentalRiskData> environmentalRisks;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    private Set<EnvironmentalRiskData> environmentalRiskDataSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    private Set<CommitteeInquiryMaster> committeeInquiryMasterSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "facilityPaper")
    private Set<WalletShare> walletShareSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "facilityPaper")
    private Set<FPMDReviewComment> mdReviewCommentSet;

    public Set<ApplicationLevelCovenant> getApplicationLevelCovenantSet() {
        return applicationLevelCovenantSet;
    }

    public void setApplicationLevelCovenantSet(Set<ApplicationLevelCovenant> applicationLevelCovenantSet) {
        this.applicationLevelCovenantSet = applicationLevelCovenantSet;
    }

    public Set<CustomerCovenant> getCustomerCovenantSet() {
        if(customerCovenantSet == null){
            customerCovenantSet = new HashSet<>();
        }
        return customerCovenantSet;
    }
        public Set<ApplicationLevelCovenant> getFacilityCovenantSet() {
        if (applicationLevelCovenantSet == null) {
            applicationLevelCovenantSet = new HashSet<>();
        }

        return applicationLevelCovenantSet;
    }



    public void setCustomerCovenantSet(Set<CustomerCovenant> customerCovenantSet) {
        this.customerCovenantSet = customerCovenantSet;
    }

    public void setFacilityCovenantSet(Set<ApplicationLevelCovenant> applicationLevelCovenantSet) {
        this.applicationLevelCovenantSet = applicationLevelCovenantSet;
    }
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "facilityPaper")
    private Set<FPCreditRiskDocument> fpCreditRiskDocumentSet;



    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getFpRefNumber() {
        return fpRefNumber;
    }

    public void setFpRefNumber(String fpRefNumber) {
        this.fpRefNumber = fpRefNumber;
    }

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
    }

    public DomainConstants.LeadType getLeadType() {
        return leadType;
    }

    public void setLeadType(DomainConstants.LeadType leadType) {
        this.leadType = leadType;
    }

    public String getLeadRefNumber() {
        return leadRefNumber;
    }

    public void setLeadRefNumber(String leadRefNumber) {
        this.leadRefNumber = leadRefNumber;
    }

    public AppsConstants.YesNo getIsCooperate() {
        return isCooperate;
    }

    public void setIsCooperate(AppsConstants.YesNo isCooperate) {
        this.isCooperate = isCooperate;
    }

    public String getFacilityPaperNumber() {
        return facilityPaperNumber;
    }

    public void setFacilityPaperNumber(String facilityPaperNumber) {
        this.facilityPaperNumber = facilityPaperNumber;
    }

    public Integer getCurrentCycle() {
        return currentCycle;
    }

    public void setCurrentCycle(Integer currentCycle) {
        this.currentCycle = currentCycle;
    }

    public String getBankAccountID() {
        return bankAccountID;
    }

    public void setBankAccountID(String bankAccountID) {
        this.bankAccountID = bankAccountID;
    }

    public String getFpApprovingAuthorityLevel() {
        return fpApprovingAuthorityLevel;
    }

    public void setFpApprovingAuthorityLevel(String fpApprovingAuthorityLevel) {
        this.fpApprovingAuthorityLevel = fpApprovingAuthorityLevel;
    }

    public DomainConstants.FacilityPaperStatus getCurrentFacilityPaperStatus() {
        return currentFacilityPaperStatus;
    }

    public void setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus currentFacilityPaperStatus) {
        this.currentFacilityPaperStatus = currentFacilityPaperStatus;
    }

    public String getCurrentAuthorityLevel() {
        return currentAuthorityLevel;
    }

    public void setCurrentAuthorityLevel(String currentAuthorityLevel) {
        this.currentAuthorityLevel = currentAuthorityLevel;
    }

    public Integer getCurrentAssignUserID() {
        return currentAssignUserID;
    }

    public void setCurrentAssignUserID(Integer currentAssignUserID) {
        this.currentAssignUserID = currentAssignUserID;
    }

    public String getCurrentAssignADUserID() {
        return currentAssignADUserID;
    }

    public void setCurrentAssignADUserID(String currentAssignADUserID) {
        this.currentAssignADUserID = currentAssignADUserID;
    }

    public String getCurrentAssignUser() {
        return currentAssignUser;
    }

    public void setCurrentAssignUser(String currentAssignUser) {
        this.currentAssignUser = currentAssignUser;
    }

    public UpcTemplate getUpcTemplate() {
        return upcTemplate;
    }

    public void setUpcTemplate(UpcTemplate upcTemplate) {
        this.upcTemplate = upcTemplate;
    }

    public WorkFlowTemplate getWorkFlowTemplate() {
        return workFlowTemplate;
    }

    public void setWorkFlowTemplate(WorkFlowTemplate workFlowTemplate) {
        this.workFlowTemplate = workFlowTemplate;
    }

    public Double getExistingFacilitiesROA() {
        return existingFacilitiesROA;
    }

    public void setExistingFacilitiesROA(Double existingFacilitiesROA) {
        this.existingFacilitiesROA = existingFacilitiesROA;
    }

    public Double getProposedFacilitiesROA() {
        return proposedFacilitiesROA;
    }

    public void setProposedFacilitiesROA(Double proposedFacilitiesROA) {
        this.proposedFacilitiesROA = proposedFacilitiesROA;
    }

    public BigDecimal getTotalDirectExposurePrevious() {
        if (totalDirectExposurePrevious == null){
            totalDirectExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return totalDirectExposurePrevious;
    }

    public void setTotalDirectExposurePrevious(BigDecimal totalDirectExposurePrevious) {
        this.totalDirectExposurePrevious = totalDirectExposurePrevious;
    }

    public BigDecimal getTotalDirectExposureNew() {
        if (totalDirectExposureNew == null){
            totalDirectExposureNew = DecimalCalculator.getDefaultZero();
        }
        return totalDirectExposureNew;
    }

    public void setTotalDirectExposureNew(BigDecimal totalDirectExposureNew) {
        this.totalDirectExposureNew = totalDirectExposureNew;
    }

    public BigDecimal getTotalIndirectExposurePrevious() {
        if (totalIndirectExposurePrevious == null){
            totalIndirectExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return totalIndirectExposurePrevious;
    }

    public void setTotalIndirectExposurePrevious(BigDecimal totalIndirectExposurePrevious) {
        this.totalIndirectExposurePrevious = totalIndirectExposurePrevious;
    }

    public BigDecimal getTotalIndirectExposureNew() {
        if (totalIndirectExposureNew == null){
            totalIndirectExposureNew = DecimalCalculator.getDefaultZero();
        }
        return totalIndirectExposureNew;
    }

    public void setTotalIndirectExposureNew(BigDecimal totalIndirectExposureNew) {
        this.totalIndirectExposureNew = totalIndirectExposureNew;
    }

    public BigDecimal getTotalExposurePrevious() {
        if (totalExposurePrevious == null){
            totalExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return totalExposurePrevious;
    }

    public void setTotalExposurePrevious(BigDecimal totalExposurePrevious) {
        this.totalExposurePrevious = totalExposurePrevious;
    }

    public BigDecimal getTotalExposureNew() {
        if (totalExposureNew == null){
            totalExposureNew = DecimalCalculator.getDefaultZero();
        }
        return totalExposureNew;
    }

    public void setTotalExposureNew(BigDecimal totalExposureNew) {
        this.totalExposureNew = totalExposureNew;
    }

    public AppsConstants.YesNo getAddTotalExposureToGroup() {
        return addTotalExposureToGroup;
    }

    public void setAddTotalExposureToGroup(AppsConstants.YesNo addTotalExposureToGroup) {
        this.addTotalExposureToGroup = addTotalExposureToGroup;
    }

    public BigDecimal getGroupTotalDirectExposurePrevious() {
        if (groupTotalDirectExposurePrevious == null){
            groupTotalDirectExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return groupTotalDirectExposurePrevious;
    }

    public void setGroupTotalDirectExposurePrevious(BigDecimal groupTotalDirectExposurePrevious) {
        this.groupTotalDirectExposurePrevious = groupTotalDirectExposurePrevious;
    }

    public BigDecimal getGroupTotalDirectExposureNew() {
        if (groupTotalDirectExposureNew == null){
            groupTotalDirectExposureNew = DecimalCalculator.getDefaultZero();
        }
        return groupTotalDirectExposureNew;
    }

    public void setGroupTotalDirectExposureNew(BigDecimal groupTotalDirectExposureNew) {
        this.groupTotalDirectExposureNew = groupTotalDirectExposureNew;
    }

    public BigDecimal getGroupTotalIndirectExposurePrevious() {
        if (groupTotalIndirectExposurePrevious == null){
            groupTotalIndirectExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return groupTotalIndirectExposurePrevious;
    }

    public void setGroupTotalIndirectExposurePrevious(BigDecimal groupTotalIndirectExposurePrevious) {
        this.groupTotalIndirectExposurePrevious = groupTotalIndirectExposurePrevious;
    }

    public BigDecimal getGroupTotalIndirectExposureNew() {
        if (groupTotalIndirectExposureNew == null){
            groupTotalIndirectExposureNew = DecimalCalculator.getDefaultZero();
        }
        return groupTotalIndirectExposureNew;
    }

    public void setGroupTotalIndirectExposureNew(BigDecimal groupTotalIndirectExposureNew) {
        this.groupTotalIndirectExposureNew = groupTotalIndirectExposureNew;
    }

    public BigDecimal getGroupTotalExposurePrevious() {
        if (groupTotalExposurePrevious == null){
            groupTotalExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return groupTotalExposurePrevious;
    }

    public void setGroupTotalExposurePrevious(BigDecimal groupTotalExposurePrevious) {
        this.groupTotalExposurePrevious = groupTotalExposurePrevious;
    }

    public BigDecimal getGroupTotalExposureNew() {
        if (groupTotalExposureNew == null){
            groupTotalExposureNew = DecimalCalculator.getDefaultZero();
        }
        return groupTotalExposureNew;
    }

    public void setGroupTotalExposureNew(BigDecimal groupTotalExposureNew) {
        this.groupTotalExposureNew = groupTotalExposureNew;
    }

    public Set<FPComment> getFpCommentSet() {
        if (fpCommentSet == null) {
            fpCommentSet = new HashSet<>();
        }
        return fpCommentSet;
    }

    public void setFpCommentSet(Set<FPComment> fpCommentSet) {
        this.fpCommentSet = fpCommentSet;
    }

    public void addFpComment(FPComment fpComment) {
        fpComment.setFacilityPaper(this);
        this.getFpCommentSet().add(fpComment);
    }

    public void addFpReviewerComment(FPReviewerComment fpReviewerComment) {
        fpReviewerComment.setFacilityPaper(this);
        this.getFpReviewerComments().add(fpReviewerComment);
    }

    public FPComment getFpCommentByID(Integer fpCommentID) {
        return this.getFpCommentSet().stream().filter(fpComment ->
                        fpCommentID.equals(fpComment.getFpCommentID()))
                .findFirst().orElse(null);
    }

    public FPReviewerComment getFPReviewerCommentByID(Integer fpReviewerID) {
        return this.getFpReviewerComments().stream().filter(fpReviewerComment ->
                        fpReviewerID.equals(fpReviewerComment.getFpReviewerCommentID()))
                .findFirst().orElse(null);
    }

    public Set<FPCompanyRoa> getFpCompanyRoaSet() {
        if (fpCompanyRoaSet == null) {
            fpCompanyRoaSet = new HashSet<>();
        }
        return fpCompanyRoaSet;
    }

    public void setFpCompanyRoaSet(Set<FPCompanyRoa> fpCompanyRoaSet) {
        this.fpCompanyRoaSet = fpCompanyRoaSet;
    }

    public void addFpCompanyRoa(FPCompanyRoa fpCompanyRoa) {
        fpCompanyRoa.setFacilityPaper(this);
        this.getFpCompanyRoaSet().add(fpCompanyRoa);
    }

    public FPCompanyRoa getFpCompanyRoaByID(Integer fpCompanyRoaID) {
        return this.getFpCompanyRoaSet().stream().filter(fpCompanyRoa ->
                        fpCompanyRoaID.equals(fpCompanyRoa.getFpCompanyRoaID()))
                .findFirst().orElse(null);
    }

    public Set<FPDirectorDetail> getFpDirectorDetailSet() {
        if (fpDirectorDetailSet == null) {
            fpDirectorDetailSet = new HashSet<>();
        }
        return fpDirectorDetailSet;
    }

    public void setFpDirectorDetailSet(Set<FPDirectorDetail> fpDirectorDetailSet) {
        this.fpDirectorDetailSet = fpDirectorDetailSet;
    }

    public void addFpDirectorDetail(FPDirectorDetail fpDirectorDetail) {
        fpDirectorDetail.setFacilityPaper(this);
        this.getFpDirectorDetailSet().add(fpDirectorDetail);
    }

    public FPDirectorDetail getFPDirectorDetailByID(Integer fpDirectorDetailID) {
        return this.getFpDirectorDetailSet().stream().filter(fpDirectorDetail ->
                        fpDirectorDetailID.equals(fpDirectorDetail.getFpDirectorDetailID()))
                .findFirst().orElse(null);
    }

    public Set<FPStatusHistory> getFpStatusHistorySet() {
        if (fpStatusHistorySet == null) {
            fpStatusHistorySet = new HashSet<>();
        }
        return fpStatusHistorySet;
    }

    public void setFpStatusHistorySet(Set<FPStatusHistory> fpStatusHistorySet) {
        this.fpStatusHistorySet = fpStatusHistorySet;
    }

    public void addFpStatusHistory(FPStatusHistory fpStatusHistory) {
        fpStatusHistory.setFacilityPaper(this);
        this.getFpStatusHistorySet().add(fpStatusHistory);
    }

    public Set<FPUpcSectionData> getFpUpcSectionDataSet() {
        if (fpUpcSectionDataSet == null) {
            fpUpcSectionDataSet = new HashSet<>();
        }
        return fpUpcSectionDataSet;
    }

    public void setFpUpcSectionDataSet(Set<FPUpcSectionData> fpUpcSectionDataSet) {
        this.fpUpcSectionDataSet = fpUpcSectionDataSet;
    }

    public void addFpUpcSectionData(FPUpcSectionData fpUpcSectionData) {
        fpUpcSectionData.setFacilityPaper(this);
        this.getFpUpcSectionDataSet().add(fpUpcSectionData);
    }

    public FPUpcSectionData getFPUpcSectionDataByID(Integer fpUpcSectionDataID) {
        return this.getFpUpcSectionDataSet().stream().filter(fpUpcSectionData ->
                        fpUpcSectionDataID.equals(fpUpcSectionData.getFpUpcSectionDataID()))
                .findFirst().orElse(null);
    }

    public Set<FPDocument> getFpDocumentSet() {
        if (fpDocumentSet == null) {
            fpDocumentSet = new HashSet<>();
        }
        return fpDocumentSet;
    }

    public List<FPDocument> getOrderedFpDocumentList() {
        return getFpDocumentSet().stream().sorted(new Comparator<FPDocument>() {
            @Override
            public int compare(FPDocument o1, FPDocument o2) {
                return o1.getFpDocumentID().compareTo(o2.getFpDocumentID());
            }
        }).collect(Collectors.toList());
    }

    public void setFpDocumentSet(Set<FPDocument> fpDocumentSet) {
        this.fpDocumentSet = fpDocumentSet;
    }

    public void addFpDocument(FPDocument fpDocument) {
        fpDocument.setFacilityPaper(this);
        this.getFpDocumentSet().add(fpDocument);
    }

    public FPDocument getFpDocumentByID(Integer fpDocumentID) {
        return this.getFpDocumentSet().stream().filter(fpDocument ->
                        fpDocumentID.equals(fpDocument.getFpDocumentID()))
                .findFirst().orElse(null);
    }

    public Set<CASCustomer> getCASCustomerSet() {
        if (CASCustomerSet == null) {
            CASCustomerSet = new HashSet<>();
        }
        return CASCustomerSet;
    }

    public List<CASCustomer> getOrderedCasCustomerList() {
        return getCASCustomerSet().stream().sorted(new Comparator<CASCustomer>() {
            @Override
            public int compare(CASCustomer o1, CASCustomer o2) {
                return o1.getCasCustomerID().compareTo(o2.getCasCustomerID());
            }
        }).collect(Collectors.toList());
    }

    public CASCustomer getPrimaryCustomer() {
        return this.getCASCustomerSet().stream().filter(customer -> {
            return customer.getIsPrimary() == AppsConstants.YesNo.Y && customer.getStatus() == AppsConstants.Status.ACT;
        }).findFirst().orElse(null);
    }

    public void setCASCustomerSet(Set<CASCustomer> CASCustomerSet) {
        this.CASCustomerSet = CASCustomerSet;
    }

    public void addCasCustomer(CASCustomer casCustomer) {
        casCustomer.setFacilityPaper(this);
        this.getCASCustomerSet().add(casCustomer);
    }

    public CASCustomer getCasCustomerByID(Integer casCustomerID) {
        return this.getCASCustomerSet().stream().filter(casCustomer ->
                        casCustomerID.equals(casCustomer.getCasCustomerID()))
                .findFirst().orElse(null);
    }

    public Set<Facility> getFacilitySet() {
        if (facilitySet == null) {
            facilitySet = new HashSet<>();
        }
        return facilitySet;
    }

    public Set<Facility> getActiveFacility() {
        return this.getFacilitySet().stream().filter(facility ->
                facility.getStatus() == AppsConstants.Status.ACT).collect(Collectors.toSet());
    }

    public List<Facility> getOrderedActiveFacilityList() {
        return new HashSet<>(getActiveFacility()).stream().sorted(new Comparator<Facility>() {
            @Override
            public int compare(Facility o1, Facility o2) {
                return o1.getDisplayOrder().compareTo(o2.getDisplayOrder());
            }
        }).collect(Collectors.toList());
    }


    public Facility getFacilityByID(Integer facilityID) {
        return this.getFacilitySet().stream().filter(facility ->
                        facilityID.equals(facility.getFacilityID()))
                .findFirst().orElse(null);
    }

    public List<Facility> getActiveFacilitiesByParentFacilityID(Integer parentFacilityID) {
        return this.getActiveFacility().stream().filter(facility -> facility.getParentFacilityID() != null &&
                facility.getParentFacilityID().equals(parentFacilityID)
        ).collect(Collectors.toList());
    }

    public void setFacilitySet(Set<Facility> facilitySet) {
        this.facilitySet = facilitySet;
    }


    public void addFacility(Facility facility) {
        facility.setFacilityPaper(this);
        this.getFacilitySet().add(facility);
    }

    public Set<FPWorkflowRouting> getFpWorkflowRoutingSet() {
        return fpWorkflowRoutingSet;
    }

    public void setFpWorkflowRoutingSet(Set<FPWorkflowRouting> fpWorkflowRoutingSet) {
        this.fpWorkflowRoutingSet = fpWorkflowRoutingSet;
    }

    public void addFpWorkflowRouting(FPWorkflowRouting fpWorkflowRouting) {
        fpWorkflowRouting.setFacilityPaper(this);
        this.getFpWorkflowRoutingSet().add(fpWorkflowRouting);
    }

    public BigDecimal getTotalFacilityPaperAmount() {
        BigDecimal totalAmount = DecimalCalculator.getDefaultZero();

        for (Facility facility : this.getActiveFacility()) {
            totalAmount = DecimalCalculator.add(totalAmount, facility.getFacilityActualAmount());

        }
        return totalAmount;
    }

    public Set<FPCreditRiskComment> getFpCreditRiskCommentSet() {
        if (fpCreditRiskCommentSet == null) {
            fpCreditRiskCommentSet = new HashSet<>();
        }
        return fpCreditRiskCommentSet;
    }

    public FPCreditRiskComment getNotLockedRiskComment() {
        return this.getFpCreditRiskCommentSet().stream().filter(fpCreditRiskComment ->
                        fpCreditRiskComment.getIsLocked() == AppsConstants.YesNo.N)
                .findFirst().orElse(null);
    }

    public FPCreditRiskComment getValidRiskComment() {
        return this.getFpCreditRiskCommentSet().stream().filter(fpCreditRiskComment ->
                        fpCreditRiskComment.getIsValidComment() == AppsConstants.YesNo.Y)
                .findFirst().orElse(null);
    }

    public void setFpCreditRiskCommentSet(Set<FPCreditRiskComment> fpCreditRiskCommentSet) {
        this.fpCreditRiskCommentSet = fpCreditRiskCommentSet;
    }

    public void addFPCreditRiskComment(FPCreditRiskComment fpCreditRiskComment) {
        fpCreditRiskComment.setFacilityPaper(this);
        this.getFpCreditRiskCommentSet().add(fpCreditRiskComment);
    }

    public FPCreditRiskComment getFPCreditRiskCommentByID(Integer fpCreditRiskCommentID) {
        return this.getFpCreditRiskCommentSet().stream().filter(fpCreditRiskComment ->
                        fpCreditRiskCommentID.equals(fpCreditRiskComment.getFpCreditRiskCommentID()))
                .findFirst().orElse(null);
    }

    public Set<FPReviewerComment> getFpReviewerComments() {
        if (this.fpReviewerComments == null) {
            this.fpReviewerComments = new HashSet<>();
        }
        return fpReviewerComments;
    }

    public void setFpReviewerComments(Set<FPReviewerComment> fpReviewerComments) {
        this.fpReviewerComments = fpReviewerComments;
    }

    public FPSecuritySummery getFpSecuritySummery() {
        return fpSecuritySummery;
    }

    public void setFpSecuritySummery(FPSecuritySummery fpSecuritySummery) {
        this.fpSecuritySummery = fpSecuritySummery;
    }

    public String getAssignUserDisplayName() {
        return assignUserDisplayName;
    }

    public void setAssignUserDisplayName(String assignUserDisplayName) {
        this.assignUserDisplayName = assignUserDisplayName;
    }

    public Integer getAssignUserUpmID() {
        return assignUserUpmID;
    }

    public void setAssignUserUpmID(Integer assignUserUpmID) {
        this.assignUserUpmID = assignUserUpmID;
    }

    public Date getInProgressDate() {
        return inProgressDate;
    }

    public void setInProgressDate(Date inProgressDate) {
        this.inProgressDate = inProgressDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getAssignUserUpmGroupCode() {
        return assignUserUpmGroupCode;
    }

    public void setAssignUserUpmGroupCode(String assignUserUpmGroupCode) {
        this.assignUserUpmGroupCode = assignUserUpmGroupCode;
    }

    public DomainConstants.PaperReviewStatus getPaperReviewStatus() {
        return paperReviewStatus;
    }

    public void setPaperReviewStatus(DomainConstants.PaperReviewStatus paperReviewStatus) {
        this.paperReviewStatus = paperReviewStatus;
    }

    public String getReviewUserDisplayName() {
        return reviewUserDisplayName;
    }

    public void setReviewUserDisplayName(String reviewUserDisplayName) {
        this.reviewUserDisplayName = reviewUserDisplayName;
    }

    public Integer getReviewUserID() {
        return reviewUserID;
    }

    public void setReviewUserID(Integer reviewUserID) {
        this.reviewUserID = reviewUserID;
    }

    public String getReviewUserName() {
        return reviewUserName;
    }

    public void setReviewUserName(String reviewUserName) {
        this.reviewUserName = reviewUserName;
    }

    public String getBccCreatedUserDisplayName() {
        return bccCreatedUserDisplayName;
    }

    public void setBccCreatedUserDisplayName(String bccCreatedUserDisplayName) {
        this.bccCreatedUserDisplayName = bccCreatedUserDisplayName;
    }

    public AppsConstants.YesNo getIsBccCreated() {
        return isBccCreated;
    }

    public void setIsBccCreated(AppsConstants.YesNo isBccCreated) {
        this.isBccCreated = isBccCreated;
    }

    public String getCreatedUserBranchCode() {
        return createdUserBranchCode;
    }

    public void setCreatedUserBranchCode(String createdUserBranchCode) {
        this.createdUserBranchCode = createdUserBranchCode;
    }

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public DomainConstants.BasicInformationType getApplicationFormType() {
        return applicationFormType;
    }

    public void setApplicationFormType(DomainConstants.BasicInformationType applicationFormType) {
        this.applicationFormType = applicationFormType;
    }

    public BigDecimal getTotalDirectExposureExisting() {
        if (totalDirectExposureExisting == null){
            totalDirectExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return totalDirectExposureExisting;
    }

    public void setTotalDirectExposureExisting(BigDecimal totalDirectExposureExisting) {
        this.totalDirectExposureExisting = totalDirectExposureExisting;
    }

    public BigDecimal getTotalIndirectExposureExisting() {
        if (totalIndirectExposureExisting == null){
            totalIndirectExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return totalIndirectExposureExisting;
    }

    public void setTotalIndirectExposureExisting(BigDecimal totalIndirectExposureExisting) {
        this.totalIndirectExposureExisting = totalIndirectExposureExisting;
    }

    public BigDecimal getTotalExposureExisting() {
        if (totalExposureExisting == null){
            totalExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return totalExposureExisting;
    }

    public void setTotalExposureExisting(BigDecimal totalExposureExisting) {
        this.totalExposureExisting = totalExposureExisting;
    }

    public BigDecimal getGroupTotalDirectExposureExisting() {
        if (groupTotalDirectExposureExisting == null){
            groupTotalDirectExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return groupTotalDirectExposureExisting;
    }

    public void setGroupTotalDirectExposureExisting(BigDecimal groupTotalDirectExposureExisting) {
        this.groupTotalDirectExposureExisting = groupTotalDirectExposureExisting;
    }

    public BigDecimal getGroupTotalIndirectExposureExisting() {
        if (groupTotalIndirectExposureExisting == null){
            groupTotalIndirectExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return groupTotalIndirectExposureExisting;
    }

    public void setGroupTotalIndirectExposureExisting(BigDecimal groupTotalIndirectExposureExisting) {
        this.groupTotalIndirectExposureExisting = groupTotalIndirectExposureExisting;
    }

    public BigDecimal getGroupTotalExposureExisting() {
        if (groupTotalExposureExisting == null){
            groupTotalExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return groupTotalExposureExisting;
    }

    public void setGroupTotalExposureExisting(BigDecimal groupTotalExposureExisting) {
        this.groupTotalExposureExisting = groupTotalExposureExisting;
    }

    public BigDecimal getExistingCashMargin() {
        if (existingCashMargin == null){
            existingCashMargin = DecimalCalculator.getDefaultZero();
        }
        return existingCashMargin;
    }

    public void setExistingCashMargin(BigDecimal existingCashMargin) {
        this.existingCashMargin = existingCashMargin;
    }

    public BigDecimal getGroupExistingCashMargin() {
        if (groupExistingCashMargin == null){
            groupExistingCashMargin = DecimalCalculator.getDefaultZero();
        }
        return groupExistingCashMargin;
    }

    public void setGroupExistingCashMargin(BigDecimal groupExistingCashMargin) {
        this.groupExistingCashMargin = groupExistingCashMargin;
    }

    public BigDecimal getNetTotalExposureNew() {
        if (netTotalExposureNew == null){
            netTotalExposureNew = DecimalCalculator.getDefaultZero();
        }
        return netTotalExposureNew;
    }

    public void setNetTotalExposureNew(BigDecimal netTotalExposureNew) {
        this.netTotalExposureNew = netTotalExposureNew;
    }

    public BigDecimal getNetTotalExposurePrevious() {
        if (netTotalExposurePrevious == null){
            netTotalExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return netTotalExposurePrevious;
    }

    public void setNetTotalExposurePrevious(BigDecimal netTotalExposurePrevious) {
        this.netTotalExposurePrevious = netTotalExposurePrevious;
    }

    public BigDecimal getNetTotalExposureExisting() {
        if (netTotalExposureExisting == null){
            netTotalExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return netTotalExposureExisting;
    }

    public void setNetTotalExposureExisting(BigDecimal netTotalExposureExisting) {
        this.netTotalExposureExisting = netTotalExposureExisting;
    }

    public BigDecimal getGroupNetTotalExposureNew() {
        if (groupNetTotalExposureNew == null){
            groupNetTotalExposureNew = DecimalCalculator.getDefaultZero();
        }
        return groupNetTotalExposureNew;
    }

    public void setGroupNetTotalExposureNew(BigDecimal groupNetTotalExposureNew) {
        this.groupNetTotalExposureNew = groupNetTotalExposureNew;
    }

    public BigDecimal getGroupNetTotalExposurePrevious() {
        if (groupNetTotalExposurePrevious == null){
            groupNetTotalExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return groupNetTotalExposurePrevious;
    }

    public void setGroupNetTotalExposurePrevious(BigDecimal groupNetTotalExposurePrevious) {
        this.groupNetTotalExposurePrevious = groupNetTotalExposurePrevious;
    }

    public BigDecimal getGroupNetTotalExposureExisting() {
        if (groupNetTotalExposureExisting == null){
            groupNetTotalExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return groupNetTotalExposureExisting;
    }

    public void setGroupNetTotalExposureExisting(BigDecimal groupNetTotalExposureExisting) {
        this.groupNetTotalExposureExisting = groupNetTotalExposureExisting;
    }

    public Date getOutstandingDate() {
        return outstandingDate;
    }

    public void setOutstandingDate(Date outstandingDate) {
        this.outstandingDate = outstandingDate;
    }

    public Date getCanceledDate() {
        return canceledDate;
    }

    public void setCanceledDate(Date canceledDate) {
        this.canceledDate = canceledDate;
    }

    public Date getRejectedDate() {
        return rejectedDate;
    }

    public void setRejectedDate(Date rejectedDate) {
        this.rejectedDate = rejectedDate;
    }

    public BigDecimal getOutstandingCashMargin() {
        if (outstandingCashMargin == null){
            outstandingCashMargin = DecimalCalculator.getDefaultZero();
        }
        return outstandingCashMargin;
    }

    public void setOutstandingCashMargin(BigDecimal outstandingCashMargin) {
        this.outstandingCashMargin = outstandingCashMargin;
    }

    public BigDecimal getProposedCashMargin() {
        if (proposedCashMargin == null){
            proposedCashMargin = DecimalCalculator.getDefaultZero();
        }
        return proposedCashMargin;
    }

    public void setProposedCashMargin(BigDecimal proposedCashMargin) {
        this.proposedCashMargin = proposedCashMargin;
    }

    public BigDecimal getGroupOutstandingCashMargin() {
        if (groupOutstandingCashMargin == null){
            groupOutstandingCashMargin = DecimalCalculator.getDefaultZero();
        }
        return groupOutstandingCashMargin;
    }

    public void setGroupOutstandingCashMargin(BigDecimal groupOutstandingCashMargin) {
        this.groupOutstandingCashMargin = groupOutstandingCashMargin;
    }

    public BigDecimal getGroupProposedCashMargin() {
        if (groupProposedCashMargin == null){
            groupProposedCashMargin = DecimalCalculator.getDefaultZero();
        }
        return groupProposedCashMargin;
    }

    public void setGroupProposedCashMargin(BigDecimal groupProposedCashMargin) {
        this.groupProposedCashMargin = groupProposedCashMargin;
    }

    public String getApplicationFormRefNumber() {
        return applicationFormRefNumber;
    }

    public void setApplicationFormRefNumber(String applicationFormRefNumber) {
        this.applicationFormRefNumber = applicationFormRefNumber;
    }

    public String getAssignDepartmentCode() {
        return assignDepartmentCode;
    }

    public void setAssignDepartmentCode(String assignDepartmentCode) {
        this.assignDepartmentCode = assignDepartmentCode;
    }

    public Set<FPAssignDepartment> getFpAssignDepartmentSet() {
        if (fpAssignDepartmentSet == null) {
            this.fpAssignDepartmentSet = new HashSet<>();
        }
        return fpAssignDepartmentSet;
    }

    public Set<FPAssignDepartment> getActiveAssignDepartmentSet() {
        return this.getFpAssignDepartmentSet().stream().filter(assignDepartment ->
                assignDepartment.getStatus() == AppsConstants.Status.ACT).collect(Collectors.toSet());
    }

    public void setFpAssignDepartmentSet(Set<FPAssignDepartment> fpAssignDepartmentSet) {
        this.fpAssignDepartmentSet = fpAssignDepartmentSet;
    }

    public Set<CommitteePaper> getCommitteePaperSet() {
        if (committeePaperSet == null) {
            this.committeePaperSet = new HashSet<>();
        }
        return committeePaperSet;
    }

    public Set<CommitteePaper> getActiveCommitteePaperSet() {
        return this.getCommitteePaperSet().stream().filter(committee ->
                committee.getStatus() == "ACT").collect(Collectors.toSet());
    }

    public void setCommitteePaperSet(Set<CommitteePaper> committeepaperSet) {
        this.committeePaperSet = committeePaperSet;
    }

    public void addCommitteePaper(CommitteePaper committeePaper) {
        committeePaper.setFacilityPaper(this);
        this.getCommitteePaperSet().add(committeePaper);
    }

    public CommitteePaper getCommitteePaperByID(Integer committeePaperID) {
        return this.getCommitteePaperSet().stream().filter(committeePaper ->
                        committeePaperID.equals(committeePaper.getCommitteePaperID()))
                .findFirst().orElse(null);
    }

   public CommitteePaper getCommitteePaperByFacilityPaperID(Integer facilityPaperID) {
        return this.getCommitteePaperSet().stream().filter(committeePaper ->
             {
                return facilityPaperID.equals(committeePaper.getFacilityPaper().getFacilityPaperID()) && committeePaper.getStatus().equals("ACT");
             }
        ).findFirst().orElse(null);
    }

   /* public CommitteePaper getActiveAssignDepartmentBySolIDAndUserGroupUPMGroup(String divCode, String userGroupUPMCode) {
        return this.getFpAssignDepartmentSet().stream().filter(fpAssignDepartment ->
                {
                    return divCode.equals(fpAssignDepartment.getDivCode()) && userGroupUPMCode.equals(fpAssignDepartment.getUserGroupUPMCode());
                }
        ).findFirst().orElse(null);
    }*/

    public void addFPAssignDepartment(FPAssignDepartment fpAssignDepartment) {
        fpAssignDepartment.setFacilityPaper(this);
        this.getFpAssignDepartmentSet().add(fpAssignDepartment);
    }

    public FPAssignDepartment getFPAssignDepartmentByID(Integer fpAssignDepartmentID) {
        return this.getFpAssignDepartmentSet().stream().filter(fpAssignDepartment ->
                        fpAssignDepartmentID.equals(fpAssignDepartment.getFpAssignDepartmentID()))
                .findFirst().orElse(null);
    }

    public FPAssignDepartment getActiveAssignDepartmentBySolIDAndUserGroupUPMGroup(String divCode, String userGroupUPMCode) {
        return this.getFpAssignDepartmentSet().stream().filter(fpAssignDepartment ->
                {
                    return divCode.equals(fpAssignDepartment.getDivCode()) && userGroupUPMCode.equals(fpAssignDepartment.getUserGroupUPMCode());
                }
        ).findFirst().orElse(null);
    }

    public String getCurrentAssignUserDivCode() {
        return currentAssignUserDivCode;
    }

    public void setCurrentAssignUserDivCode(String currentAssignUserDivCode) {
        this.currentAssignUserDivCode = currentAssignUserDivCode;
    }

    public String getCreatedUserDisplayName() {
        return createdUserDisplayName;
    }

    public void setCreatedUserDisplayName(String createdUserDisplayName) {
        this.createdUserDisplayName = createdUserDisplayName;
    }

    public Set<FPCreditRiskDocument> getFpCreditRiskDocumentSet() {
        if (fpCreditRiskDocumentSet == null) {
            fpCreditRiskDocumentSet = new HashSet<>();
        }
        return fpCreditRiskDocumentSet;
    }

    public void setFpCreditRiskDocumentSet(Set<FPCreditRiskDocument> fpCreditRiskDocumentSet) {
        this.fpCreditRiskDocumentSet = fpCreditRiskDocumentSet;
    }

//    public List<FPCreditRiskDocument> getOrderedFpCreditRiskDocumentList() {
//        return getFpCreditRiskDocumentSet().stream().sorted(new Comparator<FPCreditRiskDocument>() {
//            @Override
//            public int compare(FPCreditRiskDocument o1, FPCreditRiskDocument o2) {
//                return o1.getFpCreditRiskDocumentID().compareTo(o2.getFpCreditRiskDocumentID());
//            }
//        }).collect(Collectors.toList());
//    }

    public void addFPCreditRiskDocument(FPCreditRiskDocument fpCreditRiskDocument) {
        fpCreditRiskDocument.setFacilityPaper(this);
        this.getFpCreditRiskDocumentSet().add(fpCreditRiskDocument);
    }

    public FPCreditRiskDocument getFpCreditRiskDocumentByID(Integer fpCreditRiskDocumentID) {
        return this.getFpCreditRiskDocumentSet().stream().filter(fpCreditRiskDocument ->
                        fpCreditRiskDocumentID.equals(fpCreditRiskDocument.getFpCreditRiskDocumentID()))
                .findFirst().orElse(null);
    }


    public List<FPCreditRiskDocument> getNotLockedRiskDocuments() {
        return this.getFpCreditRiskDocumentSet().stream().filter(fpCreditRiskDocument ->
                        fpCreditRiskDocument.getIsLocked() == AppsConstants.YesNo.N)
                .collect(Collectors.toList());
    }


    public Set<FPShareHolderDetail> getFpShareHolderDetailSet() {
        if (fpShareHolderDetailSet == null) {
            fpShareHolderDetailSet = new HashSet<>();
        }
        return fpShareHolderDetailSet;
    }

    public void setFpShareHolderDetailSet(Set<FPShareHolderDetail> fpShareHolderDetailSet) {
        this.fpShareHolderDetailSet = fpShareHolderDetailSet;
    }

    public void addFpShareHolderDetail(FPShareHolderDetail fpShareHolderDetail) {
        fpShareHolderDetail.setFacilityPaper(this);
        this.getFpShareHolderDetailSet().add(fpShareHolderDetail);
    }

    public FPShareHolderDetail getFpShareHolderDetailByID(Integer fpShareHolderDetailID) {
        return this.getFpShareHolderDetailSet().stream().filter(fpShareHolderDetail ->
                        fpShareHolderDetailID.equals(fpShareHolderDetail.getFpShareHolderDetailID()))
                .findFirst().orElse(null);
    }

    public AppsConstants.YesNo getIsCommittee() {
        if (isCommittee == null){
            return AppsConstants.YesNo.N;
        }
        return isCommittee;
    }

    public void setIsCommittee(AppsConstants.YesNo isCommittee) {
        this.isCommittee = isCommittee;
    }

    public void addCustomerCovenant(CustomerCovenant customerCovenant){
        customerCovenant.setFacilityPaper(this);
        this.getCustomerCovenantSet().add(customerCovenant);
    }

    public void addFacilityCovenant(ApplicationLevelCovenant applicationLevelCovenant){
        applicationLevelCovenant.setFacilityPaper(this);
        this.getFacilityCovenantSet().add(applicationLevelCovenant);
    }

    public Set<FPBcc> getFpBccSet() {
        if (fpBccSet == null) {
            this.fpBccSet = new HashSet<>();
        }
        return fpBccSet;
    }

  /*  public Set<CommitteePaper> getActiveCommitteePaperSet() {
        return this.getCommitteePaperSet().stream().filter(committee ->
                committee.getStatus() == "ACT").collect(Collectors.toSet());
    }*/

    public void setFpBccSet(Set<FPBcc> fpBccSet) {
        this.fpBccSet = fpBccSet;
    }

    public void addFpBcc(FPBcc fpBcc) {
        fpBcc.setFacilityPaper(this);
        this.getFpBccSet().add(fpBcc);
    }

   /* public CommitteePaper getCommitteePaperByID(Integer committeePaperID) {
        return this.getCommitteePaperSet().stream().filter(committeePaper ->
                        committeePaperID.equals(committeePaper.getCommitteePaperID()))
                .findFirst().orElse(null);
    }

    public CommitteePaper getCommitteePaperByFacilityPaperID(Integer facilityPaperID) {
        return this.getCommitteePaperSet().stream().filter(committeePaper ->
                {
                    return facilityPaperID.equals(committeePaper.getFacilityPaper().getFacilityPaperID()) && committeePaper.getStatus().equals("ACT");
                }
        ).findFirst().orElse(null);
    }*/

    public AppsConstants.YesNo getIsFinancialYear() {
        return isFinancialYear;
    }

    public void setIsFinancialYear(AppsConstants.YesNo isFinancialYear) {
        this.isFinancialYear = isFinancialYear;
    }


    public Set<CribDetails> getCribDetailSet() {
        if (cribDetailSet == null){
            this.cribDetailSet = new HashSet<>();
        }
        return cribDetailSet;
    }

    public void setCribDetailSet(Set<CribDetails> cribDetailSet) {
        this.cribDetailSet = cribDetailSet;
    }

    public void addCribDetail(CribDetails cribDetails) {
        cribDetails.setFacilityPaper(this);
        this.getCribDetailSet().add(cribDetails);
    }

    public CribDetails getCribDetailByID(Integer cribDetailsID) {
        return this.getCribDetailSet().stream().filter(detail ->
                        cribDetailsID.equals(detail.getCribDetailsID()))
                .findFirst().orElse(null);
    }

    public LeadAppCode getLeadAppCode() {
        return leadAppCode;
    }

    public void setLeadAppCode(LeadAppCode leadAppCode) {
        this.leadAppCode = leadAppCode;
    }
    
    public AppsConstants.YesNo getIsESGPaper() {
        if (this.isESGPaper == null){
            this.isESGPaper = AppsConstants.YesNo.N;
        }
        return isESGPaper;
    }

    public void setIsESGPaper(AppsConstants.YesNo isESGPaper) {
        this.isESGPaper = isESGPaper;
    }

    public List<EnvironmentalRiskData> getEnvironmentalRisks() {
        return environmentalRisks;
    }

    public void setEnvironmentalRisks(List<EnvironmentalRiskData> environmentalRisks) {
        this.environmentalRisks = environmentalRisks;
    }

    public Set<EnvironmentalRiskData> getEnvironmentalRiskDataSet() {
        if (environmentalRiskDataSet == null){
            this.environmentalRiskDataSet = new HashSet<>();
        }
        return environmentalRiskDataSet;
    }

    public void setEnvironmentalRiskDataSet(Set<EnvironmentalRiskData> environmentalRiskDataSet) {
        this.environmentalRiskDataSet = environmentalRiskDataSet;
    }

    public void addEnvironmentalRiskData(EnvironmentalRiskData environmentalRiskData) {
        environmentalRiskData.setFacilityPaper(this);
        this.getEnvironmentalRiskDataSet().add(environmentalRiskData);
    }

    public AppsConstants.YesNo getIsESGApproved() {
        if(this.isESGApproved == null){
            this.isESGApproved = AppsConstants.YesNo.N;
        }
        return isESGApproved;
    }

    public void setIsESGApproved(AppsConstants.YesNo isESGApproved) {
        this.isESGApproved = isESGApproved;
    }

    public String getApprovedESGScore() {
        return approvedESGScore;
    }

    public void setApprovedESGScore(String approvedESGScore) {
        this.approvedESGScore = approvedESGScore;
    }

    public Set<CommitteeInquiryMaster> getCommitteeInquiryMasterSet() {
        return committeeInquiryMasterSet;
    }

    public void setCommitteeInquiryMasterSet(Set<CommitteeInquiryMaster> committeeInquiryMasterSet) {
        this.committeeInquiryMasterSet = committeeInquiryMasterSet;
    }

    public Set<WalletShare> getWalletShareSet() {
        if (walletShareSet == null){
            this.walletShareSet = new HashSet<>();
        }
        return walletShareSet;
    }

    public void setWalletShareSet(Set<WalletShare> walletShareSet) {
        this.walletShareSet = walletShareSet;
    }

    public Set<FPMDReviewComment> getMdReviewCommentSet() {
        if (mdReviewCommentSet == null){
            this.mdReviewCommentSet = new HashSet<>();
        }
        return mdReviewCommentSet;
    }

    public void setMdReviewCommentSet(Set<FPMDReviewComment> mdReviewCommentSet) {
        this.mdReviewCommentSet = mdReviewCommentSet;
    }
}


