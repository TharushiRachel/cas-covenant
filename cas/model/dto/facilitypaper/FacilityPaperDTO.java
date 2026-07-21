package com.itechro.cas.model.dto.facilitypaper;

import com.google.common.collect.Lists;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.bcc.FPBcc;
import com.itechro.cas.model.domain.casmaster.UpcTemplateData;
import com.itechro.cas.model.domain.covenant.ApplicationLevelCovenant;
import com.itechro.cas.model.domain.covenant.CustomerCovenant;
import com.itechro.cas.model.domain.facilitypaper.*;
import com.itechro.cas.model.domain.facilitypaper.facility.CommitteePaper;
import com.itechro.cas.model.domain.facilitypaper.facility.Facility;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.dto.advancedAnalytics.AnalyticsDecisionDTO;
import com.itechro.cas.model.dto.bcc.FPBccDTO;
import com.itechro.cas.model.dto.customer.CustomerCovernantDTO;
import com.itechro.cas.model.dto.esg.AnswerDataDTO;
import com.itechro.cas.model.dto.esg.EnvironmentalRiskDataDTO;
import com.itechro.cas.model.dto.facility.ApprovedFacilityDTO;
import com.itechro.cas.model.dto.facility.FacilityDTO;
import com.itechro.cas.model.dto.facilitypaper.committee.CommitteeInquiryMasterDTO;
import com.itechro.cas.model.dto.facilitypaper.creditcalculator.CalculatorResponse;
import com.itechro.cas.model.dto.facilitypaper.creditcalculator.Formula;
import com.itechro.cas.model.dto.facilitypaper.facilitypaperreview.FPReviewerCommentDTO;
import com.itechro.cas.model.dto.facilitypaper.request.FacilityCovenantDetailsDTO;
import com.itechro.cas.model.dto.facilitypaper.response.CribDetailsResponse;
import com.itechro.cas.model.dto.facilitypaper.response.WalletShareDTO;
import com.itechro.cas.model.dto.facilitypaper.securityDocument.SDCountDTO;
import com.itechro.cas.service.faclititypaper.FacilityPaperReplicationBuilder;
import com.itechro.cas.util.CalendarUtil;
import com.itechro.cas.util.DecimalCalculator;
import com.itechro.cas.util.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class  FacilityPaperDTO implements Serializable {


    private Integer facilityPaperID;

    private Integer upcTemplateID;

    private String upcTemplateName;

    private String upcLabel;

    private String upcLabelBackgroundColor;

    private String upcLabelFontColor;

    private Integer workflowTemplateID;

    private String branchCode; //

    private String branchName;

    private String createdUserDisplayName;

    private String customerName; //

    private String fpRefNumber;

    private String leadRefNumber;

    private Integer leadID;

    private DomainConstants.LeadType leadType;

    private AppsConstants.YesNo isCooperate; //

    private String facilityPaperNumber;

    private String bankAccountID; //

    private String fpApprovingAuthorityLevel;

    private DomainConstants.FacilityPaperStatus currentFacilityPaperStatus;

    private String currentAuthorityLevel;

    private Integer currentAssignUserID;

    private String currentAssignADUserID;

    private String currentAssignUser;

    private String currentAssignUserDivCode;

    private String assignUserDisplayName;

    private Double existingFacilitiesROA;

    private Double proposedFacilitiesROA;

    private BigDecimal totalDirectExposurePrevious = DecimalCalculator.getDefaultZero();

    private BigDecimal totalDirectExposureNew = DecimalCalculator.getDefaultZero();

    private BigDecimal totalIndirectExposurePrevious = DecimalCalculator.getDefaultZero();

    private BigDecimal totalIndirectExposureNew = DecimalCalculator.getDefaultZero();

    private BigDecimal totalExposurePrevious = DecimalCalculator.getDefaultZero();

    private BigDecimal totalExposureNew = DecimalCalculator.getDefaultZero();

    private BigDecimal totalDirectExposureExisting = DecimalCalculator.getDefaultZero();

    private BigDecimal totalIndirectExposureExisting = DecimalCalculator.getDefaultZero();

    private BigDecimal totalExposureExisting = DecimalCalculator.getDefaultZero();

    private BigDecimal existingCashMargin = DecimalCalculator.getDefaultZero();

    private BigDecimal outstandingCashMargin = DecimalCalculator.getDefaultZero();

    private BigDecimal proposedCashMargin = DecimalCalculator.getDefaultZero();

    private BigDecimal netTotalExposureNew = DecimalCalculator.getDefaultZero();

    private BigDecimal netTotalExposurePrevious = DecimalCalculator.getDefaultZero();

    private BigDecimal netTotalExposureExisting = DecimalCalculator.getDefaultZero();

    private AppsConstants.YesNo addTotalExposureToGroup = AppsConstants.YesNo.N;

    private BigDecimal groupTotalDirectExposurePrevious = DecimalCalculator.getDefaultZero();

    private BigDecimal groupTotalDirectExposureNew = DecimalCalculator.getDefaultZero();

    private BigDecimal groupTotalIndirectExposurePrevious = DecimalCalculator.getDefaultZero();

    private BigDecimal groupTotalIndirectExposureNew = DecimalCalculator.getDefaultZero();

    private BigDecimal groupTotalExposurePrevious = DecimalCalculator.getDefaultZero();

    private BigDecimal groupTotalExposureNew = DecimalCalculator.getDefaultZero();

    private BigDecimal groupTotalDirectExposureExisting = DecimalCalculator.getDefaultZero();

    private BigDecimal groupTotalIndirectExposureExisting = DecimalCalculator.getDefaultZero();

    private BigDecimal groupTotalExposureExisting = DecimalCalculator.getDefaultZero();

    private BigDecimal groupExistingCashMargin = DecimalCalculator.getDefaultZero();

    private BigDecimal groupOutstandingCashMargin = DecimalCalculator.getDefaultZero();

    private BigDecimal groupProposedCashMargin = DecimalCalculator.getDefaultZero();

    private BigDecimal groupNetTotalExposureNew = DecimalCalculator.getDefaultZero();

    private BigDecimal groupNetTotalExposurePrevious = DecimalCalculator.getDefaultZero();

    private BigDecimal groupNetTotalExposureExisting = DecimalCalculator.getDefaultZero();

    private String createdDateStr;

    private String outstandingDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    private Integer assignUserUpmID;

    private String assignUserUpmGroupCode;

    private String inProgressDateStr;

    private String approvedDateStr;

    private String canceledDateStr;

    private String rejectedDateStr;

    private DomainConstants.PaperReviewStatus paperReviewStatus;

    private String reviewUserDisplayName;

    private Integer reviewUserID;

    private String reviewUserName;

    private AppsConstants.YesNo isBccCreated;

    private String bccCreatedUserDisplayName;

    private String createdUserBranchCode;

    private Integer applicationFormID;

    private String applicationFormRefNumber;

    private Long daysDiff;

    private String lastActionDateStr;

    private DomainConstants.BasicInformationType applicationFormType;

    private String assignDepartmentCode; // This is for cluster forwarding for one Branch or Department (SOL IDs) 900,827.... : if Multiple SOLs need to Deprecate this Field

    private List<FPDirectorDetailDTO> fpDirectorDetailDTOList;

    private List<FPCompanyRoaDTO> fpCompanyRoaDTOList;

    private List<FPCommentDTO> fpCommentDTOList;

    private List<FPDocumentDTO> fpDocumentDTOList;

    private List<CASCustomerDTO> casCustomerDTOList; //

    private List<FPUpcSectionDataDTO> fpUpcSectionDataDTOList;

    private List<FacilityDTO> facilityDTOList;

    private List<FacilityDTO> newFacilityDTOList;

    private List<FacilityDTO> existingFacilityDTOList;

    private List<CustomerCovernantDTO> customerCovenantDTOList;

    private List<FacilityCovenantDetailsDTO> facilityCovenantDetailsDTOList;

    private List<FPCreditRiskCommentDTO> fpCreditRiskCommentList;

    private CreditRiskCommentFilterDTO fpCreditRiskCommentFilterDTO;

    private List<FPReviewerCommentDTO> fpReviewerCommentList;

    private List<FPAssignDepartmentDTO> fpAssignDepartmentList;

    private List<CommitteePaperDTO> committeePaperList;

    private List<FPBccDTO> fpBccList; ///

    private FPSecuritySummeryDTO fpSecuritySummeryDTO;

    //add credit cal popup data here
    private CalculatorResponse calculatorResponse;

    private List<FPCreditRiskDocumentDTO> fpCreditRiskDocumentDTOList;

    // private Integer currentUserCommitteeLevel;
    private List<ApprovedFacilityDTO> approvedFacilityDTOList;
    private static final Logger LOG = LoggerFactory.getLogger(FacilityPaperReplicationBuilder.class);

    private List<FPShareHolderDetailDTO> fpShareHolderDetailDTOList;

    private AppsConstants.YesNo isCommittee;

    private String bccActionComment;

    private String commentUserDisplayName;

    private String committeeType;

    private Date approvedDate;

    private Date rejectedDate;

    private AppsConstants.YesNo isFinancialYear;

    private List<CribDetailsResponse> cribDetailList;

    private List<CustomerTurnoverGurantee> turnoverGurantees;

    private AppsConstants.YesNo isESGPaper;

    private AppsConstants.YesNo isESGApproved;

    private String approvedESGScore;

    private List<EnvironmentalRiskDataDTO> riskCategories;

    private List<AnswerDataDTO> answerDataDTO;

    private List<CommitteeInquiryMasterDTO> committeeInquiryMasterDTOList;

    private List<WalletShareDTO> walletShares;

    private String externalAppDescription;

    private String externalAppRefNumber;

    private Integer securityDocumentVersion;

    private SDCountDTO sdCount;

    private Integer deviationCount;

    private AnalyticsDecisionDTO analyticsDecision;

    private AppsConstants.YesNo isCompLead;

    private List<FPMDReviewCommentDTO> mdReviewComments;

    public FacilityPaperDTO() {
    }

    public FacilityPaperDTO(FacilityPaper facilityPaper) {
        this(facilityPaper, null, null);
    }

    public FacilityPaperDTO(FacilityPaper facilityPaper, FPLoadOptionDTO fpLoadOptionDTO) {
        this(facilityPaper, fpLoadOptionDTO, null);
    }

    public FacilityPaperDTO(FacilityPaper facilityPaper, FPLoadOptionDTO fpLoadOptionDTO, List<Formula> formulaList) {

        //LOG.info("fpLoadOptionDTO.isLoadCustomerCovenant() : {}", fpLoadOptionDTO.isLoadCustomerCovenant());

        this.facilityPaperID = facilityPaper.getFacilityPaperID();
        this.branchCode = facilityPaper.getBranchCode();
        this.createdUserDisplayName = facilityPaper.getCreatedUserDisplayName();
        this.fpRefNumber = facilityPaper.getFpRefNumber();
        this.leadID = facilityPaper.getLeadID();
        this.leadRefNumber = facilityPaper.getLeadRefNumber();
        this.leadType = facilityPaper.getLeadType();
        this.facilityPaperNumber = facilityPaper.getFacilityPaperNumber();
        this.bankAccountID = facilityPaper.getBankAccountID();
        if (facilityPaper.getUpcTemplate() != null) {
            this.upcTemplateID = facilityPaper.getUpcTemplate().getUpcTemplateID();
            this.upcTemplateName = facilityPaper.getUpcTemplate().getTemplateName();
            this.upcLabel = facilityPaper.getUpcTemplate().getUpcLabel();
            this.upcLabelBackgroundColor = facilityPaper.getUpcTemplate().getUpcLabelBackgroundColor();
            this.upcLabelFontColor = facilityPaper.getUpcTemplate().getUpcLabelFontColor();
        }
        if (facilityPaper.getWorkFlowTemplate() != null) {
            this.workflowTemplateID = facilityPaper.getWorkFlowTemplate().getWorkFlowTemplateID();
        }
        this.fpApprovingAuthorityLevel = facilityPaper.getFpApprovingAuthorityLevel();
        this.currentFacilityPaperStatus = facilityPaper.getCurrentFacilityPaperStatus();
        this.currentAuthorityLevel = facilityPaper.getCurrentAuthorityLevel();
        this.currentAssignUser = facilityPaper.getCurrentAssignUser();
        this.currentAssignUserID = facilityPaper.getCurrentAssignUserID();
        this.currentAssignADUserID = facilityPaper.getCurrentAssignADUserID();
        this.currentAssignUserDivCode = facilityPaper.getCurrentAssignUserDivCode();
        this.isCooperate = facilityPaper.getIsCooperate();
        this.existingFacilitiesROA = facilityPaper.getExistingFacilitiesROA();
        this.proposedFacilitiesROA = facilityPaper.getProposedFacilitiesROA();
        this.totalDirectExposureNew = facilityPaper.getTotalDirectExposureNew();
        this.totalDirectExposurePrevious = facilityPaper.getTotalDirectExposurePrevious();
        this.totalIndirectExposurePrevious = facilityPaper.getTotalIndirectExposurePrevious();
        this.totalIndirectExposureNew = facilityPaper.getTotalIndirectExposureNew();
        this.totalExposureNew = facilityPaper.getTotalExposureNew();
        this.totalExposurePrevious = facilityPaper.getTotalExposurePrevious();
        this.totalDirectExposureExisting = facilityPaper.getTotalDirectExposureExisting();
        this.totalIndirectExposureExisting = facilityPaper.getTotalIndirectExposureExisting();
        this.totalExposureExisting = facilityPaper.getTotalExposureExisting();
        this.existingCashMargin = facilityPaper.getExistingCashMargin();
        this.outstandingCashMargin = facilityPaper.getOutstandingCashMargin();
        this.proposedCashMargin = facilityPaper.getProposedCashMargin();
        this.netTotalExposurePrevious = facilityPaper.getNetTotalExposurePrevious();
        this.netTotalExposureExisting = facilityPaper.getNetTotalExposureExisting();
        this.netTotalExposureNew = facilityPaper.getNetTotalExposureNew();
        this.addTotalExposureToGroup = facilityPaper.getAddTotalExposureToGroup();
        this.groupTotalDirectExposureNew = facilityPaper.getGroupTotalDirectExposureNew();
        this.groupTotalDirectExposurePrevious = facilityPaper.getGroupTotalDirectExposurePrevious();
        this.groupTotalIndirectExposurePrevious = facilityPaper.getGroupTotalIndirectExposurePrevious();
        this.groupTotalIndirectExposureNew = facilityPaper.getGroupTotalIndirectExposureNew();
        this.groupTotalExposureNew = facilityPaper.getGroupTotalExposureNew();
        this.groupTotalExposurePrevious = facilityPaper.getGroupTotalExposurePrevious();
        this.groupTotalDirectExposureExisting = facilityPaper.getGroupTotalDirectExposureExisting();
        this.groupTotalIndirectExposureExisting = facilityPaper.getGroupTotalIndirectExposureExisting();
        this.groupTotalExposureExisting = facilityPaper.getGroupTotalExposureExisting();
        this.groupExistingCashMargin = facilityPaper.getGroupExistingCashMargin();
        this.groupOutstandingCashMargin = facilityPaper.getGroupOutstandingCashMargin();
        this.groupProposedCashMargin = facilityPaper.getGroupProposedCashMargin();
        this.groupNetTotalExposurePrevious = facilityPaper.getGroupNetTotalExposurePrevious();
        this.groupNetTotalExposureNew = facilityPaper.getGroupNetTotalExposureNew();
        this.groupNetTotalExposureExisting = facilityPaper.getGroupNetTotalExposureExisting();
        this.createdBy = facilityPaper.getCreatedBy();
        this.reviewUserDisplayName = facilityPaper.getReviewUserDisplayName();
        this.paperReviewStatus = facilityPaper.getPaperReviewStatus();
        this.reviewUserID = facilityPaper.getReviewUserID();
        this.reviewUserName = facilityPaper.getReviewUserName();
        this.assignUserUpmID = facilityPaper.getAssignUserUpmID();
        this.assignUserUpmGroupCode = facilityPaper.getAssignUserUpmGroupCode();
        this.assignUserDisplayName = facilityPaper.getAssignUserDisplayName();
        this.modifiedBy = facilityPaper.getModifiedBy();
        this.isBccCreated = facilityPaper.getIsBccCreated();
        this.bccCreatedUserDisplayName = facilityPaper.getBccCreatedUserDisplayName();
        this.assignDepartmentCode = facilityPaper.getAssignDepartmentCode();
        this.createdUserBranchCode = facilityPaper.getCreatedUserBranchCode();
        this.isCommittee = facilityPaper.getIsCommittee();
        this.isFinancialYear = facilityPaper.getIsFinancialYear();
        this.isESGPaper = facilityPaper.getIsESGPaper();
        this.isESGApproved = facilityPaper.getIsESGApproved();
        this.approvedESGScore = facilityPaper.getApprovedESGScore();

        //       this.currentUserCommitteeLevel = facilityPaper.getCurrentUserCommitteeLevel();

        if (facilityPaper.getEnvironmentalRisks() != null && !facilityPaper.getEnvironmentalRisks().isEmpty()) {
            this.riskCategories = facilityPaper.getEnvironmentalRisks().stream().map(EnvironmentalRiskDataDTO::new).collect(Collectors.toList());
        }

        if (facilityPaper.getApplicationFormID() != null) {
            this.applicationFormID = facilityPaper.getApplicationFormID();
            this.applicationFormRefNumber = facilityPaper.getApplicationFormRefNumber();
            this.applicationFormType = facilityPaper.getApplicationFormType();
        }
        if (facilityPaper.getOutstandingDate() != null) {
            this.outstandingDateStr = CalendarUtil.getDefaultFormattedDateOnly(facilityPaper.getOutstandingDate());
        }

        if (facilityPaper.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateOnly(facilityPaper.getCreatedDate());
        }

        if (facilityPaper.getModifiedDate() != null) {
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(facilityPaper.getModifiedDate());
        }

        if (facilityPaper.getFpSecuritySummery() != null) {
            this.fpSecuritySummeryDTO = new FPSecuritySummeryDTO(facilityPaper.getFpSecuritySummery());
        }

        if (facilityPaper.getInProgressDate() != null) {
            this.inProgressDateStr = CalendarUtil.getDefaultFormattedDateTime(facilityPaper.getInProgressDate());
        }

        if (facilityPaper.getApprovedDate() != null) {
            this.approvedDateStr = CalendarUtil.getDefaultFormattedDateTime(facilityPaper.getApprovedDate());
            this.approvedDate = facilityPaper.getApprovedDate();
        }

        if (facilityPaper.getRejectedDate() != null) {
            this.rejectedDateStr = CalendarUtil.getDefaultFormattedDateTime(facilityPaper.getRejectedDate());
            this.rejectedDate = facilityPaper.getRejectedDate();
        }

        if (facilityPaper.getCanceledDate() != null) {
            this.canceledDateStr = CalendarUtil.getDefaultFormattedDateTime(facilityPaper.getCanceledDate());
        }

        switch (facilityPaper.getCurrentFacilityPaperStatus()) {
            case APPROVED:
                this.daysDiff = CalendarUtil.getDateDiff(facilityPaper.getCreatedDate(), facilityPaper.getApprovedDate(), TimeUnit.DAYS);
                lastActionDateStr = CalendarUtil.getDefaultFormattedDateTime(facilityPaper.getApprovedDate());
                break;

            case REJECTED:
                this.daysDiff = CalendarUtil.getDateDiff(facilityPaper.getCreatedDate(), facilityPaper.getRejectedDate(), TimeUnit.DAYS);
                lastActionDateStr = CalendarUtil.getDefaultFormattedDateTime(facilityPaper.getRejectedDate());
                break;

            case IN_PROGRESS:
                this.daysDiff = CalendarUtil.getDateDiff(facilityPaper.getCreatedDate(), new Date(), TimeUnit.DAYS);
                lastActionDateStr = CalendarUtil.getDefaultFormattedDateTime(facilityPaper.getInProgressDate());
                break;

            case CANCEL:
                this.daysDiff = CalendarUtil.getDateDiff(facilityPaper.getCreatedDate(), new Date(), TimeUnit.DAYS);
                lastActionDateStr = CalendarUtil.getDefaultFormattedDateTime(facilityPaper.getCanceledDate());
                break;

            case DRAFT:
            default:
                this.daysDiff = CalendarUtil.getDateDiff(facilityPaper.getCreatedDate(), new Date(), TimeUnit.DAYS);
                lastActionDateStr = CalendarUtil.getDefaultFormattedDateTime(facilityPaper.getCreatedDate());
        }

        if (fpLoadOptionDTO != null) {
            if (fpLoadOptionDTO.isLoadDirectorDetail()) {
                for (FPDirectorDetail fpDirectorDetail : facilityPaper.getFpDirectorDetailSet()) {
                    if (fpDirectorDetail.getStatus() == AppsConstants.Status.ACT) {
                        this.getFpDirectorDetailDTOList().add(new FPDirectorDetailDTO(fpDirectorDetail));
                    }
                }

                this.getFpDirectorDetailDTOList().sort(Comparator.comparing(FPDirectorDetailDTO::getFpDirectorDetailID));
            }
            if (fpLoadOptionDTO.isLoadCompanyROA()) {
                for (FPCompanyRoa fpCompanyRoa : facilityPaper.getFpCompanyRoaSet()) {
                    if (fpCompanyRoa.getStatus() == AppsConstants.Status.ACT) {
                        this.getFpCompanyRoaDTOList().add(new FPCompanyRoaDTO(fpCompanyRoa));
                    }
                }
            }
            if (fpLoadOptionDTO.isLoadComment()) {
                if (facilityPaper.getFpCommentSet() != null && !facilityPaper.getFpCommentSet().isEmpty()) {
                    List<FPComment> fpCommentList = Lists.newArrayList(facilityPaper.getFpCommentSet());

                    Collections.sort(fpCommentList, new Comparator<FPComment>() {
                        @Override
                        public int compare(FPComment o1, FPComment o2) {
                            return o2.getCreatedDate().compareTo(o1.getCreatedDate());
                        }
                    });
                    for (FPComment fpComment : fpCommentList) {
                        this.getFpCommentDTOList().add(new FPCommentDTO(fpComment));
                    }
                }
            }

            if (fpLoadOptionDTO.isLoadReviewerComment()) {
                if (facilityPaper.getFpReviewerComments() != null && !facilityPaper.getFpReviewerComments().isEmpty()) {
                    List<FPReviewerComment> fpReviewerComments = Lists.newArrayList(facilityPaper.getFpReviewerComments());

                    Collections.sort(fpReviewerComments, new Comparator<FPReviewerComment>() {
                        @Override
                        public int compare(FPReviewerComment o1, FPReviewerComment o2) {
                            return o2.getCreatedDate().compareTo(o1.getCreatedDate());
                        }
                    });
                    for (FPReviewerComment fpReviewerComment : fpReviewerComments) {
                        this.getFpReviewerCommentList().add(new FPReviewerCommentDTO(fpReviewerComment));
                    }
                }

            }
            if (fpLoadOptionDTO.isLoadDocument()) {
                for (FPDocument fpDocument : facilityPaper.getOrderedFpDocumentList()) {
                    if (fpDocument.getStatus() == AppsConstants.Status.ACT) {
                        this.getFpDocumentDTOList().add(new FPDocumentDTO(fpDocument, false));
                    }
                }
            }

//            if (fpLoadOptionDTO.isLoadDocument()) {
//                for (FPCreditRiskDocument fpCreditRiskDocument : facilityPaper.getOrderedFpCreditRiskDocumentList()) {
//                    if (fpCreditRiskDocument.getStatus() == AppsConstants.Status.ACT) {
//                        this.getFpCreditRiskDocumentDTOList().add(new FPCreditRiskDocumentDTO(fpCreditRiskDocument, false));
//                    }
//                }
//            }

            if (fpLoadOptionDTO.isLoadCustomer()) {

                for (CASCustomer CASCustomer : facilityPaper.getOrderedCasCustomerList()) {
                    if (CASCustomer.getStatus() == AppsConstants.Status.ACT) {
                        this.getCasCustomerDTOList().add(new CASCustomerDTO(CASCustomer, fpLoadOptionDTO));
                    }
                }
            }
            if (fpLoadOptionDTO.isLoadFacilities()) {
                for (Facility facility : facilityPaper.getActiveFacility()) {
                    this.getFacilityDTOList().add(new FacilityDTO(facility, fpLoadOptionDTO.getFacilityLoadOptionDTO(), formulaList));
                }
                this.getFacilityDTOList().sort(Comparator.comparingInt(FacilityDTO::getDisplayOrder));
            }
            //new : customer covenant
            if (fpLoadOptionDTO.isLoadCustomerCovenant()) {

                for (CustomerCovenant customerCovenant : facilityPaper.getCustomerCovenantSet()) {
                    if (customerCovenant.getStatus() == AppsConstants.CovenantStatus.Active) {
                        this.getCustomerCovenantDTOList().add(new CustomerCovernantDTO(customerCovenant));
                        LOG.info("getCustomerCovenantDTOList()");
                    }

                }
                this.getCustomerCovenantDTOList();
            }

            if (fpLoadOptionDTO.isLoadFacilityCovenant()) {
                LOG.info("fpLoadOptionDTO.loadFacilityCovenant");

                for (ApplicationLevelCovenant facilityCovenant : facilityPaper.getFacilityCovenantSet()) {
                    LOG.info("ApplicationLevelCovenant facilityCovenant");
                    if (facilityCovenant.getStatus() == AppsConstants.CovenantStatus.Active) {
                        LOG.info("facilityCovenant.getStatus()");
                        this.getFacilityCovenantDetailsDTOList().add(new FacilityCovenantDetailsDTO(facilityCovenant));//
                        LOG.info("getFacilityCovenantDTOList()");
                    }

                }
                this.getFacilityCovenantDetailsDTOList();
            }

//                         if (facilityPaper.getCustomerCovenantSet() != null && !facilityPaper.getCustomerCovenantSet().isEmpty()) {
//                    List<CustomerCovenant> customerCovenants = Lists.newArrayList(facilityPaper.getCustomerCovenantSet());
//
//                    Collections.sort(customerCovenants, new Comparator<CustomerCovenant>() {
//                        @Override
//                        public int compare(CustomerCovenant o1, CustomerCovenant o2) {
//                            return o2.getCreatedDate().compareTo(o1.getCreatedDate());
//                        }
//                    });
//                    for (CustomerCovenant customerCovenant : customerCovenants) {
//                        this.getCustomerCovenantDTOList().add(new CustomerCovernantDTO(customerCovenant));
//                    }


            //
            if (fpLoadOptionDTO.isLoadCreditRiskComments()) {
                if (facilityPaper.getFpCreditRiskCommentSet() != null && !facilityPaper.getFpCreditRiskCommentSet().isEmpty()) {
                    List<FPCreditRiskComment> fpCreditRiskComments = Lists.newArrayList(facilityPaper.getFpCreditRiskCommentSet());

                    Collections.sort(fpCreditRiskComments, new Comparator<FPCreditRiskComment>() {
                        @Override
                        public int compare(FPCreditRiskComment o1, FPCreditRiskComment o2) {
                            return o2.getCreatedDate().compareTo(o1.getCreatedDate());
                        }
                    });

                    for (FPCreditRiskComment fpCreditRiskComment : fpCreditRiskComments) {
                        if (fpCreditRiskComment.getStatus() == AppsConstants.Status.ACT) {
                            this.getFpCreditRiskCommentList().add(new FPCreditRiskCommentDTO(fpCreditRiskComment));
                        }
                    }
                }
            }

            if (fpLoadOptionDTO.isLoadUpcSectionData()) {
                for (FPUpcSectionData fpUpcSectionData : facilityPaper.getFpUpcSectionDataSet()) {
                    if (fpUpcSectionData.getStatus() == AppsConstants.Status.ACT) {
                        this.getFpUpcSectionDataDTOList().add(new FPUpcSectionDataDTO(fpUpcSectionData));
                    }
                }
                if (this.getFpUpcSectionDataDTOList().isEmpty() && facilityPaper.getUpcTemplate() != null) {
                    for (UpcTemplateData upcTemplateData : facilityPaper.getUpcTemplate().getUpcTemplateDataSet()) {
                        FPUpcSectionDataDTO upcSectionDataDTO = new FPUpcSectionDataDTO(upcTemplateData, facilityPaperID);
                        if (!this.getFpUpcSectionDataDTOList().contains(upcSectionDataDTO)) {
                            this.getFpUpcSectionDataDTOList().add(upcSectionDataDTO);
                        }
                    }
                }
                this.getFpUpcSectionDataDTOList().sort(Comparator.comparingInt(FPUpcSectionDataDTO::getDisplayOrder));
            }

            if (!facilityPaper.getActiveAssignDepartmentSet().isEmpty()) {
                for (FPAssignDepartment fpAssignDepartment : facilityPaper.getActiveAssignDepartmentSet()) {
                    this.getFpAssignDepartmentList().add(new FPAssignDepartmentDTO(fpAssignDepartment));
                }
            }

            if (this.getCommitteePaperList().isEmpty() && facilityPaper.getCommitteePaperSet() != null) {
                for (CommitteePaper committeePaper : facilityPaper.getCommitteePaperSet()) {
                    if (committeePaper.getStatus().equals("ACT")) {
                        this.getCommitteePaperList().add(new CommitteePaperDTO(committeePaper));
                    }
                }
            }

            if (!facilityPaper.getFpBccSet().isEmpty() || facilityPaper.getFpBccSet() != null) {
                for (FPBcc fpBcc : facilityPaper.getFpBccSet()) {
                    this.getFpBccList().add(new FPBccDTO(fpBcc));

//                    for (FPBccDocument fpBccDocument : fpBcc.getFpBccDocumentSet()) {
//                        fpBcc.getFpBccDocumentSet().add(fpBccDocument);
//                    }
                }
            }

            if (fpLoadOptionDTO.isLoadApprovedFacilityDocuments()) {
                if (facilityPaper.getCurrentFacilityPaperStatus() == DomainConstants.FacilityPaperStatus.APPROVED) {
                    for (Facility facility : facilityPaper.getActiveFacility()) {
                        this.getApprovedFacilityDTOList().add(new ApprovedFacilityDTO(facility, fpLoadOptionDTO.getFacilityLoadOptionDTO(), formulaList));
                    }
                    this.getApprovedFacilityDTOList().sort(Comparator.comparingInt(ApprovedFacilityDTO::getDisplayOrder));
                }
            }
            if (fpLoadOptionDTO.isLoadCreditRiskComments()) {
                if (facilityPaper.getFpCreditRiskCommentSet() != null && !facilityPaper.getFpCreditRiskCommentSet().isEmpty()) {
                    List<FPCreditRiskComment> fpCreditRiskComments = Lists.newArrayList(facilityPaper.getFpCreditRiskCommentSet());

                    Collections.sort(fpCreditRiskComments, new Comparator<FPCreditRiskComment>() {
                        @Override
                        public int compare(FPCreditRiskComment o1, FPCreditRiskComment o2) {
                            return o2.getCreatedDate().compareTo(o1.getCreatedDate());
                        }
                    });

                    CreditRiskCommentFilterDTO p = new CreditRiskCommentFilterDTO();

                    for (FPCreditRiskComment fpCreditRiskComment : fpCreditRiskComments) {
                        if (fpCreditRiskComment.getStatus() == AppsConstants.Status.ACT && fpCreditRiskComment.getIsValidComment() == AppsConstants.YesNo.Y) {
                            p.setActiveRiskComment(new FPCreditRiskCommentDTO(fpCreditRiskComment));
                        }

                        if (fpCreditRiskComment.getStatus() == AppsConstants.Status.ACT && fpCreditRiskComment.getIsLocked() == AppsConstants.YesNo.Y && fpCreditRiskComment.getIsValidComment() == AppsConstants.YesNo.N && fpCreditRiskComment.getVersion() > 1) {
                            p.addPreviousLockedComments(new FPCreditRiskCommentDTO(fpCreditRiskComment));
                        }
                    }

                    this.setFpCreditRiskCommentFilterDTO(p);
                }
            }


            if (fpLoadOptionDTO.isLoadShareHolderDetail()) {
                if (!facilityPaper.getFpShareHolderDetailSet().isEmpty()) {
                    for (FPShareHolderDetail fpShareHolderDetail : facilityPaper.getFpShareHolderDetailSet()) {
                        if (fpShareHolderDetail.getStatus() == AppsConstants.Status.ACT) {
                            this.getFpShareHolderDetailDTOList().add(new FPShareHolderDetailDTO(fpShareHolderDetail));
                        }
                    }

                    this.getFpShareHolderDetailDTOList().sort(Comparator.comparing(FPShareHolderDetailDTO::getFpShareHolderDetailID));
                }

            }

            if (facilityPaper.getLeadID() != null) {
                if (facilityPaper.getLeadAppCode() != null) {
                    this.externalAppRefNumber = facilityPaper.getLeadAppCode().getAppRef();
                    if (facilityPaper.getLeadAppCode().getAppDescription() != null) {
                        this.externalAppDescription = facilityPaper.getLeadAppCode().getAppDescription().getAppDescription();
                    }
                }
            }

            if (fpLoadOptionDTO.isLoadCribDetails()) {
                if (!facilityPaper.getCribDetailSet().isEmpty()) {
                    for (CribDetails cribDetail : facilityPaper.getCribDetailSet()) {
                        if (cribDetail.getStatus() == AppsConstants.Status.ACT) {
                            this.getCribDetailList().add(new CribDetailsResponse(cribDetail));
                        }
                    }
                }
            }

            if (facilityPaper.getCommitteeInquiryMasterSet() != null && !facilityPaper.getCommitteeInquiryMasterSet().isEmpty()) {
                this.committeeInquiryMasterDTOList = facilityPaper.getCommitteeInquiryMasterSet().stream()
                        .map(CommitteeInquiryMasterDTO::new)
                        .collect(Collectors.toList());
            }

            if (fpLoadOptionDTO.isLoadWalletShares()) {
                if (facilityPaper.getWalletShareSet() != null && !facilityPaper.getWalletShareSet().isEmpty()) {
                    for (WalletShare walletShare : facilityPaper.getWalletShareSet()) {
                        this.getWalletShares().add(new WalletShareDTO(walletShare));
                    }
                }
            }

            if (fpLoadOptionDTO.isLoadMDReviewComments()) {
                if (facilityPaper.getMdReviewCommentSet() != null && !facilityPaper.getMdReviewCommentSet().isEmpty()) {
                    for (FPMDReviewComment fpmdReviewComment : facilityPaper.getMdReviewCommentSet()) {
                        this.getMdReviewComments().add(new FPMDReviewCommentDTO(fpmdReviewComment));
                    }
                }
            }
        }
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public Integer getUpcTemplateID() {
        return upcTemplateID;
    }

    public void setUpcTemplateID(Integer upcTemplateID) {
        this.upcTemplateID = upcTemplateID;
    }

    public String getUpcTemplateName() {
        return upcTemplateName;
    }

    public void setUpcTemplateName(String upcTemplateName) {
        this.upcTemplateName = upcTemplateName;
    }

    public Integer getWorkflowTemplateID() {
        return workflowTemplateID;
    }

    public void setWorkflowTemplateID(Integer workflowTemplateID) {
        this.workflowTemplateID = workflowTemplateID;
    }

    public String getFpRefNumber() {
        return fpRefNumber;
    }

    public void setFpRefNumber(String fpRefNumber) {
        this.fpRefNumber = fpRefNumber;
    }

    public String getLeadRefNumber() {
        return leadRefNumber;
    }

    public void setLeadRefNumber(String leadRefNumber) {
        this.leadRefNumber = leadRefNumber;
    }

    public DomainConstants.LeadType getLeadType() {
        return leadType;
    }

    public void setLeadType(DomainConstants.LeadType leadType) {
        this.leadType = leadType;
    }

    public AppsConstants.YesNo getIsCooperate() {
        return isCooperate;
    }

    public void setIsCooperate(AppsConstants.YesNo isCooperate) {
        this.isCooperate = isCooperate;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFacilityPaperNumber() {
        return facilityPaperNumber;
    }

    public void setFacilityPaperNumber(String facilityPaperNumber) {
        this.facilityPaperNumber = facilityPaperNumber;
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

    public String getCurrentAssignUser() {
        return currentAssignUser;
    }

    public void setCurrentAssignUser(String currentAssignUser) {
        this.currentAssignUser = currentAssignUser;
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
        if (totalDirectExposurePrevious == null) {
            totalDirectExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return totalDirectExposurePrevious;
    }

    public void setTotalDirectExposurePrevious(BigDecimal totalDirectExposurePrevious) {
        this.totalDirectExposurePrevious = totalDirectExposurePrevious;
    }

    public BigDecimal getTotalDirectExposureNew() {
        if (totalDirectExposureNew == null) {
            totalDirectExposureNew = DecimalCalculator.getDefaultZero();
        }
        return totalDirectExposureNew;
    }

    public void setTotalDirectExposureNew(BigDecimal totalDirectExposureNew) {
        this.totalDirectExposureNew = totalDirectExposureNew;
    }

    public BigDecimal getTotalIndirectExposurePrevious() {
        if (totalIndirectExposurePrevious == null) {
            totalIndirectExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return totalIndirectExposurePrevious;
    }

    public void setTotalIndirectExposurePrevious(BigDecimal totalIndirectExposurePrevious) {
        this.totalIndirectExposurePrevious = totalIndirectExposurePrevious;
    }

    public BigDecimal getTotalIndirectExposureNew() {
        if (totalIndirectExposureNew == null) {
            totalIndirectExposureNew = DecimalCalculator.getDefaultZero();
        }
        return totalIndirectExposureNew;
    }

    public void setTotalIndirectExposureNew(BigDecimal totalIndirectExposureNew) {
        this.totalIndirectExposureNew = totalIndirectExposureNew;
    }

    public BigDecimal getTotalExposurePrevious() {
        if (totalExposurePrevious == null) {
            totalExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return totalExposurePrevious;
    }

    public void setTotalExposurePrevious(BigDecimal totalExposurePrevious) {
        this.totalExposurePrevious = totalExposurePrevious;
    }

    public BigDecimal getTotalExposureNew() {
        if (totalExposureNew == null) {
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
        if (groupTotalDirectExposurePrevious == null) {
            groupTotalDirectExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return groupTotalDirectExposurePrevious;
    }

    public void setGroupTotalDirectExposurePrevious(BigDecimal groupTotalDirectExposurePrevious) {
        this.groupTotalDirectExposurePrevious = groupTotalDirectExposurePrevious;
    }

    public BigDecimal getGroupTotalDirectExposureNew() {
        if (groupTotalDirectExposureNew == null) {
            groupTotalDirectExposureNew = DecimalCalculator.getDefaultZero();
        }
        return groupTotalDirectExposureNew;
    }

    public void setGroupTotalDirectExposureNew(BigDecimal groupTotalDirectExposureNew) {
        this.groupTotalDirectExposureNew = groupTotalDirectExposureNew;
    }

    public BigDecimal getGroupTotalIndirectExposurePrevious() {
        if (groupTotalIndirectExposurePrevious == null) {
            groupTotalIndirectExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return groupTotalIndirectExposurePrevious;
    }

    public void setGroupTotalIndirectExposurePrevious(BigDecimal groupTotalIndirectExposurePrevious) {
        this.groupTotalIndirectExposurePrevious = groupTotalIndirectExposurePrevious;
    }

    public BigDecimal getGroupTotalIndirectExposureNew() {
        if (groupTotalIndirectExposureNew == null) {
            groupTotalIndirectExposureNew = DecimalCalculator.getDefaultZero();
        }
        return groupTotalIndirectExposureNew;
    }

    public void setGroupTotalIndirectExposureNew(BigDecimal groupTotalIndirectExposureNew) {
        this.groupTotalIndirectExposureNew = groupTotalIndirectExposureNew;
    }

    public BigDecimal getGroupTotalExposurePrevious() {
        if (groupTotalExposurePrevious == null) {
            groupTotalExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return groupTotalExposurePrevious;
    }

    public void setGroupTotalExposurePrevious(BigDecimal groupTotalExposurePrevious) {
        this.groupTotalExposurePrevious = groupTotalExposurePrevious;
    }

    public BigDecimal getGroupTotalExposureNew() {
        if (groupTotalExposureNew == null) {
            groupTotalExposureNew = DecimalCalculator.getDefaultZero();
        }
        return groupTotalExposureNew;
    }

    public void setGroupTotalExposureNew(BigDecimal groupTotalExposureNew) {
        this.groupTotalExposureNew = groupTotalExposureNew;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedDateStr() {
        return modifiedDateStr;
    }

    public void setModifiedDateStr(String modifiedDateStr) {
        this.modifiedDateStr = modifiedDateStr;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public List<FPDirectorDetailDTO> getFpDirectorDetailDTOList() {
        if (fpDirectorDetailDTOList == null) {
            fpDirectorDetailDTOList = new ArrayList<>();
        }
        return fpDirectorDetailDTOList;
    }

    public void setFpDirectorDetailDTOList(List<FPDirectorDetailDTO> fpDirectorDetailDTOList) {
        this.fpDirectorDetailDTOList = fpDirectorDetailDTOList;
    }

    public List<FPCompanyRoaDTO> getFpCompanyRoaDTOList() {
        if (fpCompanyRoaDTOList == null) {
            fpCompanyRoaDTOList = new ArrayList<>();
        }
        return fpCompanyRoaDTOList;
    }

    public void setFpCompanyRoaDTOList(List<FPCompanyRoaDTO> fpCompanyRoaDTOList) {
        this.fpCompanyRoaDTOList = fpCompanyRoaDTOList;
    }

    public List<FPCommentDTO> getFpCommentDTOList() {
        if (fpCommentDTOList == null) {
            fpCommentDTOList = new ArrayList<>();
        }
        return fpCommentDTOList;
    }

    public void setFpCommentDTOList(List<FPCommentDTO> fpCommentDTOList) {
        this.fpCommentDTOList = fpCommentDTOList;
    }

    public List<FPDocumentDTO> getFpDocumentDTOList() {
        if (fpDocumentDTOList == null) {
            fpDocumentDTOList = new ArrayList<>();
        }
        return fpDocumentDTOList;
    }

    public void setFpDocumentDTOList(List<FPDocumentDTO> fpDocumentDTOList) {
        this.fpDocumentDTOList = fpDocumentDTOList;
    }

    public List<CASCustomerDTO> getCasCustomerDTOList() {
        if (casCustomerDTOList == null) {
            casCustomerDTOList = new ArrayList<>();
        }
        return casCustomerDTOList;
    }

    public void setCasCustomerDTOList(List<CASCustomerDTO> casCustomerDTOList) {
        this.casCustomerDTOList = casCustomerDTOList;
    }


    public List<FPUpcSectionDataDTO> getFpUpcSectionDataDTOList() {
        if (fpUpcSectionDataDTOList == null) {
            fpUpcSectionDataDTOList = new ArrayList<>();
        }
        return fpUpcSectionDataDTOList;
    }

    public void setFpUpcSectionDataDTOList(List<FPUpcSectionDataDTO> fpUpcSectionDataDTOList) {
        this.fpUpcSectionDataDTOList = fpUpcSectionDataDTOList;
    }

    public List<FacilityDTO> getFacilityDTOList() {
        if (facilityDTOList == null) {
            facilityDTOList = new ArrayList<>();
        }
        return facilityDTOList;
    }

    public void setFacilityDTOList(List<FacilityDTO> facilityDTOList) {
        this.facilityDTOList = facilityDTOList;
    }

    public List<FPCreditRiskCommentDTO> getFpCreditRiskCommentList() {
        if (fpCreditRiskCommentList == null) {
            fpCreditRiskCommentList = new ArrayList<>();
        }
        return fpCreditRiskCommentList;
    }

    public void setFpCreditRiskCommentList(List<FPCreditRiskCommentDTO> fpCreditRiskCommentList) {
        this.fpCreditRiskCommentList = fpCreditRiskCommentList;
    }

    public List<FPReviewerCommentDTO> getFpReviewerCommentList() {
        if (fpReviewerCommentList == null) {
            this.fpReviewerCommentList = new ArrayList<>();
        }
        return fpReviewerCommentList;
    }

    public void setFpReviewerCommentList(List<FPReviewerCommentDTO> fpReviewerCommentList) {
        this.fpReviewerCommentList = fpReviewerCommentList;
    }

    public FPSecuritySummeryDTO getFpSecuritySummeryDTO() {
        return fpSecuritySummeryDTO;
    }

    public void setFpSecuritySummeryDTO(FPSecuritySummeryDTO fpSecuritySummeryDTO) {
        this.fpSecuritySummeryDTO = fpSecuritySummeryDTO;
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

    public String getInProgressDateStr() {
        return inProgressDateStr;
    }

    public void setInProgressDateStr(String inProgressDateStr) {
        this.inProgressDateStr = inProgressDateStr;
    }

    public String getApprovedDateStr() {
        return approvedDateStr;
    }

    public void setApprovedDateStr(String approvedDateStr) {
        this.approvedDateStr = approvedDateStr;
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

    public AppsConstants.YesNo getIsBccCreated() {
        return isBccCreated;
    }

    public void setIsBccCreated(AppsConstants.YesNo isBccCreated) {
        this.isBccCreated = isBccCreated;
    }

    public String getBccCreatedUserDisplayName() {
        return bccCreatedUserDisplayName;
    }

    public void setBccCreatedUserDisplayName(String bccCreatedUserDisplayName) {
        this.bccCreatedUserDisplayName = bccCreatedUserDisplayName;
    }

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
    }

    public String getAssignUserUpmGroupCode() {
        return assignUserUpmGroupCode;
    }

    public void setAssignUserUpmGroupCode(String assignUserUpmGroupCode) {
        this.assignUserUpmGroupCode = assignUserUpmGroupCode;
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
        if (totalDirectExposureExisting == null) {
            totalDirectExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return totalDirectExposureExisting;
    }

    public void setTotalDirectExposureExisting(BigDecimal totalDirectExposureExisting) {
        this.totalDirectExposureExisting = totalDirectExposureExisting;
    }

    public BigDecimal getTotalIndirectExposureExisting() {
        if (totalIndirectExposureExisting == null) {
            totalIndirectExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return totalIndirectExposureExisting;
    }

    public void setTotalIndirectExposureExisting(BigDecimal totalIndirectExposureExisting) {
        this.totalIndirectExposureExisting = totalIndirectExposureExisting;
    }

    public BigDecimal getTotalExposureExisting() {
        if (totalExposureExisting == null) {
            totalExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return totalExposureExisting;
    }

    public void setTotalExposureExisting(BigDecimal totalExposureExisting) {
        this.totalExposureExisting = totalExposureExisting;
    }

    public BigDecimal getGroupTotalDirectExposureExisting() {
        if (groupTotalDirectExposureExisting == null) {
            groupTotalDirectExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return groupTotalDirectExposureExisting;
    }

    public void setGroupTotalDirectExposureExisting(BigDecimal groupTotalDirectExposureExisting) {
        this.groupTotalDirectExposureExisting = groupTotalDirectExposureExisting;
    }

    public BigDecimal getGroupTotalIndirectExposureExisting() {
        if (groupTotalIndirectExposureExisting == null) {
            groupTotalIndirectExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return groupTotalIndirectExposureExisting;
    }

    public void setGroupTotalIndirectExposureExisting(BigDecimal groupTotalIndirectExposureExisting) {
        this.groupTotalIndirectExposureExisting = groupTotalIndirectExposureExisting;
    }

    public BigDecimal getGroupTotalExposureExisting() {
        if (groupTotalExposureExisting == null) {
            groupTotalExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return groupTotalExposureExisting;
    }

    public void setGroupTotalExposureExisting(BigDecimal groupTotalExposureExisting) {
        this.groupTotalExposureExisting = groupTotalExposureExisting;
    }

    public BigDecimal getExistingCashMargin() {
        if (existingCashMargin == null) {
            existingCashMargin = DecimalCalculator.getDefaultZero();
        }
        return existingCashMargin;
    }

    public void setExistingCashMargin(BigDecimal existingCashMargin) {
        this.existingCashMargin = existingCashMargin;
    }

    public BigDecimal getGroupExistingCashMargin() {
        if (groupExistingCashMargin == null) {
            groupExistingCashMargin = DecimalCalculator.getDefaultZero();
        }
        return groupExistingCashMargin;
    }

    public void setGroupExistingCashMargin(BigDecimal groupExistingCashMargin) {
        this.groupExistingCashMargin = groupExistingCashMargin;
    }

    public BigDecimal getNetTotalExposureNew() {
        if (netTotalExposureNew == null) {
            netTotalExposureNew = DecimalCalculator.getDefaultZero();
        }
        return netTotalExposureNew;
    }

    public void setNetTotalExposureNew(BigDecimal netTotalExposureNew) {
        this.netTotalExposureNew = netTotalExposureNew;
    }

    public BigDecimal getNetTotalExposurePrevious() {
        if (netTotalExposurePrevious == null) {
            netTotalExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return netTotalExposurePrevious;
    }

    public void setNetTotalExposurePrevious(BigDecimal netTotalExposurePrevious) {
        this.netTotalExposurePrevious = netTotalExposurePrevious;
    }

    public BigDecimal getNetTotalExposureExisting() {
        if (netTotalExposureExisting == null) {
            netTotalExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return netTotalExposureExisting;
    }

    public void setNetTotalExposureExisting(BigDecimal netTotalExposureExisting) {
        this.netTotalExposureExisting = netTotalExposureExisting;
    }

    public BigDecimal getGroupNetTotalExposureNew() {
        if (groupNetTotalExposureNew == null) {
            groupNetTotalExposureNew = DecimalCalculator.getDefaultZero();
        }
        return groupNetTotalExposureNew;
    }

    public void setGroupNetTotalExposureNew(BigDecimal groupNetTotalExposureNew) {
        this.groupNetTotalExposureNew = groupNetTotalExposureNew;
    }

    public BigDecimal getGroupNetTotalExposurePrevious() {
        if (groupNetTotalExposurePrevious == null) {
            groupNetTotalExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return groupNetTotalExposurePrevious;
    }

    public void setGroupNetTotalExposurePrevious(BigDecimal groupNetTotalExposurePrevious) {
        this.groupNetTotalExposurePrevious = groupNetTotalExposurePrevious;
    }

    public BigDecimal getGroupNetTotalExposureExisting() {
        if (groupNetTotalExposureExisting == null) {
            groupNetTotalExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return groupNetTotalExposureExisting;
    }

    public void setGroupNetTotalExposureExisting(BigDecimal groupNetTotalExposureExisting) {
        this.groupNetTotalExposureExisting = groupNetTotalExposureExisting;
    }

    public String getOutstandingDateStr() {
        return outstandingDateStr;
    }

    public void setOutstandingDateStr(String outstandingDateStr) {
        this.outstandingDateStr = outstandingDateStr;
    }

    public String getCanceledDateStr() {
        return canceledDateStr;
    }

    public void setCanceledDateStr(String canceledDateStr) {
        this.canceledDateStr = canceledDateStr;
    }

    public String getRejectedDateStr() {
        return rejectedDateStr;
    }

    public void setRejectedDateStr(String rejectedDateStr) {
        this.rejectedDateStr = rejectedDateStr;
    }

    public Long getDaysDiff() {
        return daysDiff;
    }

    public void setDaysDiff(Long daysDiff) {
        this.daysDiff = daysDiff;
    }

    public String getLastActionDateStr() {
        return lastActionDateStr;
    }

    public void setLastActionDateStr(String lastActionDateStr) {
        this.lastActionDateStr = lastActionDateStr;
    }

    public BigDecimal getOutstandingCashMargin() {
        if (outstandingCashMargin == null) {
            outstandingCashMargin = DecimalCalculator.getDefaultZero();
        }
        return outstandingCashMargin;
    }

    public void setOutstandingCashMargin(BigDecimal outstandingCashMargin) {
        this.outstandingCashMargin = outstandingCashMargin;
    }

    public BigDecimal getProposedCashMargin() {
        if (proposedCashMargin == null) {
            proposedCashMargin = DecimalCalculator.getDefaultZero();
        }
        return proposedCashMargin;
    }

    public void setProposedCashMargin(BigDecimal proposedCashMargin) {
        this.proposedCashMargin = proposedCashMargin;
    }

    public BigDecimal getGroupOutstandingCashMargin() {
        if (groupOutstandingCashMargin == null) {
            groupOutstandingCashMargin = DecimalCalculator.getDefaultZero();
        }
        return groupOutstandingCashMargin;
    }

    public void setGroupOutstandingCashMargin(BigDecimal groupOutstandingCashMargin) {
        this.groupOutstandingCashMargin = groupOutstandingCashMargin;
    }

    public BigDecimal getGroupProposedCashMargin() {
        if (groupProposedCashMargin == null) {
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

    public List<FPAssignDepartmentDTO> getFpAssignDepartmentList() {
        if (fpAssignDepartmentList == null) {
            this.fpAssignDepartmentList = new ArrayList<>();
        }
        return fpAssignDepartmentList;
    }

    public void setFpAssignDepartmentList(List<FPAssignDepartmentDTO> fpAssignDepartmentList) {
        this.fpAssignDepartmentList = fpAssignDepartmentList;
    }

    public List<CommitteePaperDTO> getCommitteePaperList() {
        if (committeePaperList == null) {
            this.committeePaperList = new ArrayList<>();
        }
        return committeePaperList;
    }

    public void setCommitteePaperList(List<CommitteePaperDTO> committeePaperList) {
        this.committeePaperList = committeePaperList;
    }

    public List<FPBccDTO> getFpBccList() {
        if (fpBccList == null) {
            this.fpBccList = new ArrayList<>();
        }
        return fpBccList;
    }

    public void setFpBccList(List<FPBccDTO> fpBccList) {
        this.fpBccList = fpBccList;
    }

    public String getAssignDepartmentCode() {
        return assignDepartmentCode;
    }

    public void setAssignDepartmentCode(String assignDepartmentCode) {
        this.assignDepartmentCode = assignDepartmentCode;
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

    public List<FPCreditRiskDocumentDTO> getFpCreditRiskDocumentDTOList() {
        if (fpCreditRiskDocumentDTOList == null) {
            fpCreditRiskDocumentDTOList = new ArrayList<>();
        }
        return fpCreditRiskDocumentDTOList;
    }

    public void setFpCreditRiskDocumentDTOList(List<FPCreditRiskDocumentDTO> fpCreditRiskDocumentDTOList) {
        this.fpCreditRiskDocumentDTOList = fpCreditRiskDocumentDTOList;
    }

    public List<ApprovedFacilityDTO> getApprovedFacilityDTOList() {
        if (approvedFacilityDTOList == null) {
            approvedFacilityDTOList = new ArrayList<>();
        }
        return approvedFacilityDTOList;
    }

    public void setApprovedFacilityDTOList(List<ApprovedFacilityDTO> approvedFacilityDTOList) {
        this.approvedFacilityDTOList = approvedFacilityDTOList;
    }

    public CreditRiskCommentFilterDTO getFpCreditRiskCommentFilterDTO() {
        return fpCreditRiskCommentFilterDTO;
    }

    public void setFpCreditRiskCommentFilterDTO(CreditRiskCommentFilterDTO fpCreditRiskCommentFilterDTO) {
        this.fpCreditRiskCommentFilterDTO = fpCreditRiskCommentFilterDTO;
    }

    public CalculatorResponse getCalculatorResponse() {
        return calculatorResponse;
    }

    public void setCalculatorResponse(CalculatorResponse calculatorResponse) {
        this.calculatorResponse = calculatorResponse;
    }

    public List<FPShareHolderDetailDTO> getFpShareHolderDetailDTOList() {
        if (fpShareHolderDetailDTOList == null) {
            fpShareHolderDetailDTOList = new ArrayList<>();
        }
        return fpShareHolderDetailDTOList;
    }

    public void setFpShareHolderDetailDTOList(List<FPShareHolderDetailDTO> fpShareHolderDetailDTOList) {
        this.fpShareHolderDetailDTOList = fpShareHolderDetailDTOList;
    }

    public List<CustomerCovernantDTO> getCustomerCovenantDTOList() {
        if (customerCovenantDTOList == null) {
            customerCovenantDTOList = new ArrayList<>();
        }
        return customerCovenantDTOList;
    }

    public List<FacilityCovenantDetailsDTO> getFacilityCovenantDetailsDTOList() {
        if (facilityCovenantDetailsDTOList == null) {
            facilityCovenantDetailsDTOList = new ArrayList<>();
        }
        return facilityCovenantDetailsDTOList;
    }

    public void setCustomerCovenantDTOList(List<CustomerCovernantDTO> customerCovenantDTOList) {
        this.customerCovenantDTOList = customerCovenantDTOList;
    }

    public void setFacilityCovenantDetailsDTOList(List<FacilityCovenantDetailsDTO> facilityCovenantDetailsDTOList) {
        this.facilityCovenantDetailsDTOList = facilityCovenantDetailsDTOList;
    }

    public String getUpcLabel() {
        return upcLabel;
    }

    public void setUpcLabel(String upcLabel) {
        this.upcLabel = upcLabel;
    }

    public String getUpcLabelBackgroundColor() {
        return upcLabelBackgroundColor;
    }

    public void setUpcLabelBackgroundColor(String upcLabelBackgroundColor) {
        this.upcLabelBackgroundColor = upcLabelBackgroundColor;
    }

    public String getUpcLabelFontColor() {
        return upcLabelFontColor;
    }

    public void setUpcLabelFontColor(String upcLabelFontColor) {
        this.upcLabelFontColor = upcLabelFontColor;
    }

    @Override
    public String toString() {
        return "FacilityPaperDTO{" +
                "facilityPaperID=" + facilityPaperID +
                ", upcTemplateID=" + upcTemplateID +
                ", upcTemplateName='" + upcTemplateName + '\'' +
                ", upcLabel='" + upcLabel + '\'' +
                ", upcLabelBackgroundColor='" + upcLabelBackgroundColor + '\'' +
                ", upcLabelFontColor='" + upcLabelFontColor + '\'' +
                ", workflowTemplateID=" + workflowTemplateID +
                ", branchCode='" + branchCode + '\'' +
                ", createdUserDisplayName='" + createdUserDisplayName + '\'' +
                ", customerName='" + customerName + '\'' +
                ", fpRefNumber='" + fpRefNumber + '\'' +
                ", leadRefNumber='" + leadRefNumber + '\'' +
                ", leadID=" + leadID +
                ", leadType=" + leadType +
                ", isCooperate=" + isCooperate +
                ", facilityPaperNumber='" + facilityPaperNumber + '\'' +
                ", bankAccountID='" + bankAccountID + '\'' +
                ", fpApprovingAuthorityLevel='" + fpApprovingAuthorityLevel + '\'' +
                ", currentFacilityPaperStatus=" + currentFacilityPaperStatus +
                ", currentAuthorityLevel='" + currentAuthorityLevel + '\'' +
                ", currentAssignUserID=" + currentAssignUserID +
                ", currentAssignADUserID='" + currentAssignADUserID + '\'' +
                ", currentAssignUser='" + currentAssignUser + '\'' +
                ", currentAssignUserDivCode='" + currentAssignUserDivCode + '\'' +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", existingFacilitiesROA=" + existingFacilitiesROA +
                ", proposedFacilitiesROA=" + proposedFacilitiesROA +
                ", totalDirectExposurePrevious=" + totalDirectExposurePrevious +
                ", totalDirectExposureNew=" + totalDirectExposureNew +
                ", totalIndirectExposurePrevious=" + totalIndirectExposurePrevious +
                ", totalIndirectExposureNew=" + totalIndirectExposureNew +
                ", totalExposurePrevious=" + totalExposurePrevious +
                ", totalExposureNew=" + totalExposureNew +
                ", totalDirectExposureExisting=" + totalDirectExposureExisting +
                ", totalIndirectExposureExisting=" + totalIndirectExposureExisting +
                ", totalExposureExisting=" + totalExposureExisting +
                ", existingCashMargin=" + existingCashMargin +
                ", outstandingCashMargin=" + outstandingCashMargin +
                ", proposedCashMargin=" + proposedCashMargin +
                ", netTotalExposureNew=" + netTotalExposureNew +
                ", netTotalExposurePrevious=" + netTotalExposurePrevious +
                ", netTotalExposureExisting=" + netTotalExposureExisting +
                ", addTotalExposureToGroup=" + addTotalExposureToGroup +
                ", groupTotalDirectExposurePrevious=" + groupTotalDirectExposurePrevious +
                ", groupTotalDirectExposureNew=" + groupTotalDirectExposureNew +
                ", groupTotalIndirectExposurePrevious=" + groupTotalIndirectExposurePrevious +
                ", groupTotalIndirectExposureNew=" + groupTotalIndirectExposureNew +
                ", groupTotalExposurePrevious=" + groupTotalExposurePrevious +
                ", groupTotalExposureNew=" + groupTotalExposureNew +
                ", groupTotalDirectExposureExisting=" + groupTotalDirectExposureExisting +
                ", groupTotalIndirectExposureExisting=" + groupTotalIndirectExposureExisting +
                ", groupTotalExposureExisting=" + groupTotalExposureExisting +
                ", groupExistingCashMargin=" + groupExistingCashMargin +
                ", groupOutstandingCashMargin=" + groupOutstandingCashMargin +
                ", groupProposedCashMargin=" + groupProposedCashMargin +
                ", groupNetTotalExposureNew=" + groupNetTotalExposureNew +
                ", groupNetTotalExposurePrevious=" + groupNetTotalExposurePrevious +
                ", groupNetTotalExposureExisting=" + groupNetTotalExposureExisting +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", outstandingDateStr='" + outstandingDateStr + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedDateStr='" + modifiedDateStr + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", assignUserUpmID=" + assignUserUpmID +
                ", assignUserUpmGroupCode='" + assignUserUpmGroupCode + '\'' +
                ", inProgressDateStr='" + inProgressDateStr + '\'' +
                ", approvedDateStr='" + approvedDateStr + '\'' +
                ", canceledDateStr='" + canceledDateStr + '\'' +
                ", rejectedDateStr='" + rejectedDateStr + '\'' +
                ", paperReviewStatus=" + paperReviewStatus +
                ", reviewUserDisplayName='" + reviewUserDisplayName + '\'' +
                ", reviewUserID=" + reviewUserID +
                ", reviewUserName='" + reviewUserName + '\'' +
                ", isBccCreated=" + isBccCreated +
                ", bccCreatedUserDisplayName='" + bccCreatedUserDisplayName + '\'' +
                ", createdUserBranchCode='" + createdUserBranchCode + '\'' +
                ", applicationFormID=" + applicationFormID +
                ", applicationFormRefNumber='" + applicationFormRefNumber + '\'' +
                ", daysDiff=" + daysDiff +
                ", lastActionDateStr='" + lastActionDateStr + '\'' +
                ", applicationFormType=" + applicationFormType +
                ", assignDepartmentCode='" + assignDepartmentCode + '\'' +
                ", fpDirectorDetailDTOList=" + fpDirectorDetailDTOList +
                ", fpCompanyRoaDTOList=" + fpCompanyRoaDTOList +
                ", fpCommentDTOList=" + fpCommentDTOList +
                ", fpDocumentDTOList=" + fpDocumentDTOList +
                ", casCustomerDTOList=" + casCustomerDTOList +
                ", fpUpcSectionDataDTOList=" + fpUpcSectionDataDTOList +
                ", facilityDTOList=" + facilityDTOList +
                ", fpCreditRiskCommentList=" + fpCreditRiskCommentList +
                ", fpCreditRiskCommentFilterDTO=" + fpCreditRiskCommentFilterDTO +
                ", fpReviewerCommentList=" + fpReviewerCommentList +
                ", fpAssignDepartmentList=" + fpAssignDepartmentList +
                ", committeePaperList=" + committeePaperList +
                ", fpBccList=" + fpBccList +
                ", fpSecuritySummeryDTO=" + fpSecuritySummeryDTO +
                ", calculatorResponse=" + calculatorResponse +
                ", fpCreditRiskDocumentDTOList=" + fpCreditRiskDocumentDTOList +
                // ", currentUserCommitteeLevel=" + currentUserCommitteeLevel +
                ", fpReviewerCommentList=" + fpReviewerCommentList +
                ", fpAssignDepartmentList=" + fpAssignDepartmentList +
                ", fpSecuritySummeryDTO=" + fpSecuritySummeryDTO +
                ", calculatorResponse=" + calculatorResponse +
                ", committeeType=" + committeeType +
                ", fpCreditRiskDocumentDTOList=" + fpCreditRiskDocumentDTOList +
                ", approvedFacilityDTOList=" + approvedFacilityDTOList +
                '}';
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
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

    public List<FacilityDTO> getNewFacilityDTOList() {
        return newFacilityDTOList;
    }

    public void setNewFacilityDTOList(List<FacilityDTO> newFacilityDTOList) {
        this.newFacilityDTOList = newFacilityDTOList;
    }

    public List<FacilityDTO> getExistingFacilityDTOList() {
        return existingFacilityDTOList;
    }

    public void setExistingFacilityDTOList(List<FacilityDTO> existingFacilityDTOList) {
        this.existingFacilityDTOList = existingFacilityDTOList;
    }

    public String getBccActionComment() {
        return bccActionComment;
    }

    public void setBccActionComment(String bccActionComment) {
        this.bccActionComment = bccActionComment;
    }

    public String getCommentUserDisplayName() {
        return commentUserDisplayName;
    }

    public void setCommentUserDisplayName(String commentUserDisplayName) {
        this.commentUserDisplayName = commentUserDisplayName;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Date getRejectedDate() {
        return rejectedDate;
    }

    public void setRejectedDate(Date rejectedDate) {
        this.rejectedDate = rejectedDate;
    }

    public String getCommitteeType() {
        return committeeType;
    }

    public void setCommitteeType(String committeeType) {
        this.committeeType = committeeType;
    }

    public AppsConstants.YesNo getIsFinancialYear() {
        return isFinancialYear;
    }

    public void setIsFinancialYear(AppsConstants.YesNo isFinancialYear) {
        this.isFinancialYear = isFinancialYear;
    }

    public List<CribDetailsResponse> getCribDetailList() {
        if (cribDetailList == null) {
            cribDetailList = new ArrayList<>();
        }
        return cribDetailList;
    }

    public void setCribDetailList(List<CribDetailsResponse> cribDetailList) {
        this.cribDetailList = cribDetailList;
    }

    public BigDecimal getMillionValue(BigDecimal totalExposureNew) {

        return NumberUtil.getMillionValue(totalExposureNew);
    }

    public void setTurnoverGurantees(List<CustomerTurnoverGurantee> turnoverGurantees) {
        this.turnoverGurantees = turnoverGurantees;
    }

    public List<CustomerTurnoverGurantee> getTurnoverGurantees() {
        return this.turnoverGurantees;
    }


    public AppsConstants.YesNo getIsESGPaper() {
        return isESGPaper;
    }

    public void setIsESGPaper(AppsConstants.YesNo isESGPaper) {
        this.isESGPaper = isESGPaper;
    }

    public List<EnvironmentalRiskDataDTO> getRiskCategories() {
        return riskCategories;
    }

    public void setRiskCategories(List<EnvironmentalRiskDataDTO> riskCategories) {
        this.riskCategories = riskCategories;
    }

    public List<AnswerDataDTO> getAnswerDataDTO() {
        return answerDataDTO;
    }

    public void setAnswerDataDTO(List<AnswerDataDTO> answerDataDTO) {
        this.answerDataDTO = answerDataDTO;
    }

    public AppsConstants.YesNo getIsESGApproved() {
        if (this.isESGApproved == null) {
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

    public List<CommitteeInquiryMasterDTO> getCommitteeInquiryMasterDTOList() {
        return committeeInquiryMasterDTOList;
    }

    public void setCommitteeInquiryMasterDTOList(List<CommitteeInquiryMasterDTO> committeeInquiryMasterDTOList) {
        this.committeeInquiryMasterDTOList = committeeInquiryMasterDTOList;
    }

    public List<WalletShareDTO> getWalletShares() {
        if (walletShares == null) {
            walletShares = new ArrayList<>();
        }

        return walletShares;
    }

    public void setWalletShares(List<WalletShareDTO> walletShares) {
        this.walletShares = walletShares;
    }

    public List<WalletShareDTO> getSortedWalletShares(List<WalletShareDTO> shares) {
        if (shares == null) {
            return new ArrayList<>();
        }
        shares.sort(Comparator.comparing(WalletShareDTO::getIsSystem));
        return shares;
    }

    public String getTotalLimit(List<WalletShareDTO> walletShares) {
        BigDecimal total = DecimalCalculator.getDefaultZero();

        if (walletShares != null) {
            for (WalletShareDTO share : walletShares) {
                if (share.getLimitTotal() != null) {
                    total = total.add(share.getLimitTotal());
                }
            }
        }
        return getFormattedMnAmount(total);
    }

    public String getTotalOS(List<WalletShareDTO> walletShares) {
        BigDecimal total = DecimalCalculator.getDefaultZero();
        if (walletShares != null) {
            for (WalletShareDTO share : walletShares) {
                if (share.getOsTotal() != null) {
                    total = total.add(share.getOsTotal());
                }
            }
        }
        return getFormattedMnAmount(total);
    }

    public String getFormattedMnAmount(Object unFormattedAmount) {
        if (unFormattedAmount != null) {
            DecimalFormat df = new DecimalFormat("#,###,###,###,##0.000");
            return df.format(unFormattedAmount);
        } else {
            return null;
        }
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

    public Integer getSecurityDocumentVersion() {
        return securityDocumentVersion;
    }

    public void setSecurityDocumentVersion(Integer securityDocumentVersion) {
        this.securityDocumentVersion = securityDocumentVersion;
    }

    public SDCountDTO getSdCount() {
        return sdCount;
    }

    public void setSdCount(SDCountDTO sdCount) {
        this.sdCount = sdCount;
    }

    public Integer getDeviationCount() {
        return deviationCount;
    }

    public void setDeviationCount(Integer deviationCount) {
        this.deviationCount = deviationCount;
    }

    public AnalyticsDecisionDTO getAnalyticsDecision() {
        return analyticsDecision;
    }

    public void setAnalyticsDecision(AnalyticsDecisionDTO analyticsDecision) {
        this.analyticsDecision = analyticsDecision;
    }

    public AppsConstants.YesNo getIsCompLead() {
        if (this.isCompLead == null) {
            return AppsConstants.YesNo.N;
        }
        return isCompLead;
    }

    public void setIsCompLead(AppsConstants.YesNo isCompLead) {
        this.isCompLead = isCompLead;
    }

    public List<FPMDReviewCommentDTO> getMdReviewComments() {
        if (mdReviewComments == null) {
            this.mdReviewComments = new ArrayList<>();
        }
        return mdReviewComments;
    }

    public void setMdReviewComments(List<FPMDReviewCommentDTO> mdReviewComments) {
        this.mdReviewComments = mdReviewComments;
    }
}

