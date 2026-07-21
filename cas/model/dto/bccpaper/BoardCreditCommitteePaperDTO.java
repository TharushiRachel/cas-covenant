package com.itechro.cas.model.dto.bccpaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.bccpaper.*;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BoardCreditCommitteePaperDTO implements Serializable {

    private Integer boardCreditCommitteePaperID;

    private String bccRefNumber;

    private Integer facilityPaperID;

    private DomainConstants.BCCPaperType paperType;

    private String branchCode;

    private String createdUserDisplayName;

    private String createdUserName;

    private Integer upmID;

    private String customerName;

    private String startedCapital;

    private String businessProfile;

    private String netInterestMargin;

    private String interestCover;

    private String gearing;

    private String grossNPL;

    private String marketPosition;

    private String costOfFundMonth;

    private Integer costOfFundYear;

    private Double monthlyCostOfFundsLkr;

    private Double monthlyCostOfFundsFcy;

    private Double cumulativeCostOfFundsLkr;

    private Double cumulativeCostOfFundsFcy;

    private Double incrementalCostOfFundsLkr;

    private Double incrementalCostOfFundsFcy;

    private String businessManagementStrength;

    private String securityCover;

    private String financialStability;

    private String auditorName;

    private String financialYearOne;

    private String financialYearOneFinancial;

    private String financialYearTwo;

    private String financialYearTwoFinancial;

    private Double incomeAmountYearOne;

    private Double incomeGrowthYearOne;

    private Double incomeAmountYearTwo;

    private Double incomeGrowthYearTwo;

    private Double profitAfterTaxAmountYearOne;

    private Double profitAfterTaxGrowthYearOne;

    private Double profitAfterTaxAmountYearTwo;

    private Double profitAfterTaxGrowthYearTwo;

    private DomainConstants.GRORecommendation recommendation;

    private String recommendationRemark;

    private String justification;

    private String commonSecurityText;

    private String note;

    private String riskBasedPricing;

    private String existingOutstandingAtDate;

    private String proposedOutstandingAtDate;

    private BigDecimal proposedFacilityTotal;

    private BigDecimal existingFacilityTotal;

    private BigDecimal existingPlusProposedTotal;

    private BigDecimal proposedOutstandingFacilityTotal;

    private BigDecimal existingOutstandingFacilityTotal;

    private BigDecimal existingOutstandingPlusProposedOutstandingTotal;

    private String facilityDateOfApproval;

    private List<BccFacilityDTO> bccFacilityDTOS;

    private List<BccCompanyDirectorDTO> bccCompanyDirectorDTOS;

    private List<BccCompanyShareholderDTO> bccCompanyShareholderDTOS;

    private List<BccRiskRatingYearDTO> bccRiskRatingYearDTOS;

    private List<BccCustomerCribDetailDTO> bccCustomerCribDetailDTOS;

    private String exceptions;

    private String officerOne;

    private String officerTwo;

    private String officerThree;

    private String officerFour;

    private String officerOneDesignation;

    private String officerTwoDesignation;

    private String officerThreeDesignation;

    private String officerFourDesignation;

    private String eacMemberOne;

    private String eacMemberTwo;

    private String eacMemberThree;

    private String eacMemberFour;

    private String eacMemberOneDesignation;

    private String eacMemberTwoDesignation;

    private String eacMemberThreeDesignation;

    private String eacMemberFourDesignation;

    private String pdfReport;

    private BccExposureCompanyDTO bccExposureCompanyDTO;

    private BccExposureGroupDTO bccExposureGroupDTO;

    private BCCSecuritySummaryDTO bccSecuritySummaryDTO;

    private AppsConstants.Status status;

    private String branchName;

    private String currentAssignUser;

    private String assignUserDisplayName;

    private String currentAssignUserDivCode;

    private AppsConstants.YesNo isSubmitted;

    public BoardCreditCommitteePaperDTO() {
    }

    public BoardCreditCommitteePaperDTO(BoardCreditCommitteePaper boardCreditCommitteePaper) {
        if (boardCreditCommitteePaper != null) {
            this.boardCreditCommitteePaperID = boardCreditCommitteePaper.getBoardCreditCommitteePaperID();
            this.bccRefNumber = boardCreditCommitteePaper.getBccRefNumber();
            if (boardCreditCommitteePaper.getFacilityPaper().getFacilityPaperID() != null) {
                this.facilityPaperID = boardCreditCommitteePaper.getFacilityPaper().getFacilityPaperID();
                if (boardCreditCommitteePaper.getFacilityPaper().getFpSecuritySummery() != null) {
                    this.bccSecuritySummaryDTO = new BCCSecuritySummaryDTO(boardCreditCommitteePaper.getFacilityPaper().getFpSecuritySummery());
                } else {
                    this.bccSecuritySummaryDTO = new BCCSecuritySummaryDTO();
                }
            }
            this.branchCode = boardCreditCommitteePaper.getBranchCode();
            this.paperType = boardCreditCommitteePaper.getPaperType();
            this.createdUserDisplayName = boardCreditCommitteePaper.getCreatedUserDisplayName();
            this.createdUserName = boardCreditCommitteePaper.getCreatedBy();
            this.upmID = boardCreditCommitteePaper.getUpmID();
            this.customerName = boardCreditCommitteePaper.getCustomerName();
            this.startedCapital = boardCreditCommitteePaper.getStartedCapital();
            this.status = boardCreditCommitteePaper.getStatus();
            this.marketPosition = boardCreditCommitteePaper.getMarketPosition();
            this.securityCover = boardCreditCommitteePaper.getSecurityCover();
            this.costOfFundMonth = boardCreditCommitteePaper.getCostOfFundMonth();
            this.costOfFundYear = boardCreditCommitteePaper.getCostOfFundYear();
            this.monthlyCostOfFundsLkr = boardCreditCommitteePaper.getMonthlyCostOfFundsLkr();
            this.monthlyCostOfFundsFcy = boardCreditCommitteePaper.getMonthlyCostOfFundsFcy();
            this.cumulativeCostOfFundsLkr = boardCreditCommitteePaper.getCumulativeCostOfFundsLkr();
            this.cumulativeCostOfFundsFcy = boardCreditCommitteePaper.getCumulativeCostOfFundsFcy();
            this.incrementalCostOfFundsLkr = boardCreditCommitteePaper.getIncrementalCostOfFundsLkr();
            this.incrementalCostOfFundsFcy = boardCreditCommitteePaper.getIncrementalCostOfFundsFcy();
            this.recommendation = boardCreditCommitteePaper.getRecommendation();
            this.recommendationRemark = boardCreditCommitteePaper.getRecommendationRemark();
            this.justification = boardCreditCommitteePaper.getJustification();
            this.note = boardCreditCommitteePaper.getNote();
            this.commonSecurityText = boardCreditCommitteePaper.getCommonSecurityText();
            this.riskBasedPricing = boardCreditCommitteePaper.getRiskBasedPricing();
            this.businessManagementStrength = boardCreditCommitteePaper.getBusinessManagementStrength();
            this.securityCover = boardCreditCommitteePaper.getSecurityCover();
            this.financialStability = boardCreditCommitteePaper.getFinancialStability();
            this.auditorName = boardCreditCommitteePaper.getAuditorName();
            this.financialYearOne = boardCreditCommitteePaper.getFinancialYearOne();
            this.financialYearOneFinancial = boardCreditCommitteePaper.getFinancialYearOneFinancial();
            this.financialYearTwo = boardCreditCommitteePaper.getFinancialYearTwo();
            this.financialYearTwoFinancial = boardCreditCommitteePaper.getFinancialYearTwoFinancial();
            this.incomeAmountYearOne = boardCreditCommitteePaper.getIncomeAmountYearOne();
            this.incomeGrowthYearOne = boardCreditCommitteePaper.getIncomeGrowthYearOne();
            this.incomeAmountYearTwo = boardCreditCommitteePaper.getIncomeAmountYearTwo();
            this.incomeGrowthYearTwo = boardCreditCommitteePaper.getIncomeGrowthYearTwo();
            this.profitAfterTaxAmountYearOne = boardCreditCommitteePaper.getProfitAfterTaxAmountYearOne();
            this.profitAfterTaxGrowthYearOne = boardCreditCommitteePaper.getProfitAfterTaxGrowthYearOne();
            this.profitAfterTaxAmountYearTwo = boardCreditCommitteePaper.getProfitAfterTaxAmountYearTwo();
            this.profitAfterTaxGrowthYearTwo = boardCreditCommitteePaper.getProfitAfterTaxGrowthYearTwo();
            this.businessProfile = boardCreditCommitteePaper.getBusinessProfile();
            this.netInterestMargin = boardCreditCommitteePaper.getNetInterestMargin();
            this.interestCover = boardCreditCommitteePaper.getInterestCover();
            this.gearing = boardCreditCommitteePaper.getGearing();
            this.exceptions = boardCreditCommitteePaper.getExceptions();
            this.officerOne = boardCreditCommitteePaper.getOfficerOne();
            this.officerTwo = boardCreditCommitteePaper.getOfficerTwo();
            this.officerThree = boardCreditCommitteePaper.getOfficerThree();
            this.officerFour = boardCreditCommitteePaper.getOfficerFour();
            this.officerOneDesignation = boardCreditCommitteePaper.getOfficerOneDesignation();
            this.officerTwoDesignation = boardCreditCommitteePaper.getOfficerTwoDesignation();
            this.officerThreeDesignation = boardCreditCommitteePaper.getOfficerThreeDesignation();
            this.officerFourDesignation = boardCreditCommitteePaper.getOfficerFourDesignation();
            this.eacMemberOne = boardCreditCommitteePaper.getEacMemberOne();
            this.eacMemberTwo = boardCreditCommitteePaper.getEacMemberTwo();
            this.eacMemberThree = boardCreditCommitteePaper.getEacMemberThree();
            this.eacMemberFour = boardCreditCommitteePaper.getEacMemberFour();
            this.eacMemberOneDesignation = boardCreditCommitteePaper.getEacMemberOneDesignation();
            this.eacMemberTwoDesignation = boardCreditCommitteePaper.getEacMemberTwoDesignation();
            this.eacMemberThreeDesignation = boardCreditCommitteePaper.getEacMemberThreeDesignation();
            this.eacMemberFourDesignation = boardCreditCommitteePaper.getEacMemberFourDesignation();
            this.proposedFacilityTotal = boardCreditCommitteePaper.getProposedFacilityTotal();
            this.existingFacilityTotal = boardCreditCommitteePaper.getExistingFacilityTotal();
            this.existingPlusProposedTotal = boardCreditCommitteePaper.getExistingPlusProposedTotal();
            this.proposedOutstandingFacilityTotal = boardCreditCommitteePaper.getProposedOutstandingFacilityTotal();
            this.existingOutstandingFacilityTotal = boardCreditCommitteePaper.getExistingOutstandingFacilityTotal();
            this.existingOutstandingPlusProposedOutstandingTotal = boardCreditCommitteePaper.getExistingOutstandingPlusProposedOutstandingTotal();
            this.pdfReport = boardCreditCommitteePaper.getPdfReport();
            this.currentAssignUser = boardCreditCommitteePaper.getCurrentAssignUser();
            this.assignUserDisplayName = boardCreditCommitteePaper.getAssignUserDisplayName();
            this.currentAssignUserDivCode = boardCreditCommitteePaper.getCurrentAssignUserDivCode();
            this.grossNPL = boardCreditCommitteePaper.getGrossNPL();
            this.isSubmitted = boardCreditCommitteePaper.getIsSubmitted();

            for (BccFacility bccFacility : boardCreditCommitteePaper.getOrderedBccFacilitySet()) {
                if (bccFacility.getStatus() == AppsConstants.Status.ACT) {
                    getBccFacilityDTOS().add(new BccFacilityDTO(bccFacility));
                }
            }
            for (BccCompanyDirector bccCompanyDirector : boardCreditCommitteePaper.getOrderedBccCompanyDirectorSet()) {
                if (bccCompanyDirector.getStatus() == AppsConstants.Status.ACT) {
                    getBccCompanyDirectorDTOS().add(new BccCompanyDirectorDTO(bccCompanyDirector));
                }
            }

            for (BccCompanyShareholder bccCompanyShareholder : boardCreditCommitteePaper.getOrderedBccCompanyShareholderSet()) {
                if (bccCompanyShareholder.getStatus() == AppsConstants.Status.ACT) {
                    getBccCompanyShareholderDTOS().add(new BccCompanyShareholderDTO(bccCompanyShareholder));
                }
            }

            for (BccRiskRatingYear bccRiskRatingYear : boardCreditCommitteePaper.getOrderedBccRiskRatingYears()) {
                if (bccRiskRatingYear.getStatus() == AppsConstants.Status.ACT) {
                    getBccRiskRatingYearDTOS().add(new BccRiskRatingYearDTO(bccRiskRatingYear));
                }
            }

            for (BccCustomerCribDetail bccCustomerCribDetail : boardCreditCommitteePaper.getOrderedCustomerCribDetails()) {
                if (bccCustomerCribDetail.getStatus() == AppsConstants.Status.ACT) {
                    getBccCustomerCribDetailDTOS().add(new BccCustomerCribDetailDTO(bccCustomerCribDetail));
                }
            }

//            if (boardCreditCommitteePaper.getFacilityDateOfApproval() != null) {
//                this.facilityDateOfApproval = CalendarUtil.getDefaultFormattedDateOnly(boardCreditCommitteePaper.getFacilityDateOfApproval());
//            }

            if (boardCreditCommitteePaper.getFacilityDateOfApproval() != null) {
                this.facilityDateOfApproval = CalendarUtil.getBCCFormattedDate(boardCreditCommitteePaper.getFacilityDateOfApproval());
            }

            if (boardCreditCommitteePaper.getExistingOutstandingAtDate() != null) {
                this.existingOutstandingAtDate = CalendarUtil.getDefaultFormattedDateOnly(boardCreditCommitteePaper.getExistingOutstandingAtDate());
            }
            if (boardCreditCommitteePaper.getProposedOutstandingAtDate() != null) {
                this.proposedOutstandingAtDate = CalendarUtil.getDefaultFormattedDateOnly(boardCreditCommitteePaper.getProposedOutstandingAtDate());
            }
            if (boardCreditCommitteePaper.getBccExposureCompany() != null) {
                this.bccExposureCompanyDTO = new BccExposureCompanyDTO(boardCreditCommitteePaper.getBccExposureCompany());
            }

            if (boardCreditCommitteePaper.getBccExposureGroup() != null) {
                this.bccExposureGroupDTO = new BccExposureGroupDTO(boardCreditCommitteePaper.getBccExposureGroup());
            }

        }
    }

    public Integer getBoardCreditCommitteePaperID() {
        return boardCreditCommitteePaperID;
    }

    public void setBoardCreditCommitteePaperID(Integer boardCreditCommitteePaperID) {
        this.boardCreditCommitteePaperID = boardCreditCommitteePaperID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public DomainConstants.BCCPaperType getPaperType() {
        return paperType;
    }

    public void setPaperType(DomainConstants.BCCPaperType paperType) {
        this.paperType = paperType;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getCreatedUserDisplayName() {
        return createdUserDisplayName;
    }

    public void setCreatedUserDisplayName(String createdUserDisplayName) {
        this.createdUserDisplayName = createdUserDisplayName;
    }

    public String getCreatedUserName() {
        return createdUserName;
    }

    public void setCreatedUserName(String createdUserName) {
        this.createdUserName = createdUserName;
    }

    public Integer getUpmID() {
        return upmID;
    }

    public void setUpmID(Integer upmID) {
        this.upmID = upmID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStartedCapital() {
        return startedCapital;
    }

    public void setStartedCapital(String startedCapital) {
        this.startedCapital = startedCapital;
    }

    public String getBccRefNumber() {
        return bccRefNumber;
    }

    public void setBccRefNumber(String bccRefNumber) {
        this.bccRefNumber = bccRefNumber;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getMarketPosition() {
        return marketPosition;
    }

    public void setMarketPosition(String marketPosition) {
        this.marketPosition = marketPosition;
    }

    public String getSecurityCover() {
        return securityCover;
    }

    public void setSecurityCover(String securityCover) {
        this.securityCover = securityCover;
    }

    public String getCostOfFundMonth() {
        return costOfFundMonth;
    }

    public void setCostOfFundMonth(String costOfFundMonth) {
        this.costOfFundMonth = costOfFundMonth;
    }

    public Integer getCostOfFundYear() {
        return costOfFundYear;
    }

    public void setCostOfFundYear(Integer costOfFundYear) {
        this.costOfFundYear = costOfFundYear;
    }

    public Double getMonthlyCostOfFundsLkr() {
        return monthlyCostOfFundsLkr;
    }

    public void setMonthlyCostOfFundsLkr(Double monthlyCostOfFundsLkr) {
        this.monthlyCostOfFundsLkr = monthlyCostOfFundsLkr;
    }

    public Double getMonthlyCostOfFundsFcy() {
        return monthlyCostOfFundsFcy;
    }

    public void setMonthlyCostOfFundsFcy(Double monthlyCostOfFundsFcy) {
        this.monthlyCostOfFundsFcy = monthlyCostOfFundsFcy;
    }

    public Double getCumulativeCostOfFundsLkr() {
        return cumulativeCostOfFundsLkr;
    }

    public void setCumulativeCostOfFundsLkr(Double cumulativeCostOfFundsLkr) {
        this.cumulativeCostOfFundsLkr = cumulativeCostOfFundsLkr;
    }

    public Double getCumulativeCostOfFundsFcy() {
        return cumulativeCostOfFundsFcy;
    }

    public void setCumulativeCostOfFundsFcy(Double cumulativeCostOfFundsFcy) {
        this.cumulativeCostOfFundsFcy = cumulativeCostOfFundsFcy;
    }

    public Double getIncrementalCostOfFundsLkr() {
        return incrementalCostOfFundsLkr;
    }

    public void setIncrementalCostOfFundsLkr(Double incrementalCostOfFundsLkr) {
        this.incrementalCostOfFundsLkr = incrementalCostOfFundsLkr;
    }

    public Double getIncrementalCostOfFundsFcy() {
        return incrementalCostOfFundsFcy;
    }

    public void setIncrementalCostOfFundsFcy(Double incrementalCostOfFundsFcy) {
        this.incrementalCostOfFundsFcy = incrementalCostOfFundsFcy;
    }

    public DomainConstants.GRORecommendation getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(DomainConstants.GRORecommendation recommendation) {
        this.recommendation = recommendation;
    }

    public String getRecommendationRemark() {
        return recommendationRemark;
    }

    public void setRecommendationRemark(String recommendationRemark) {
        this.recommendationRemark = recommendationRemark;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getCommonSecurityText() {
        return commonSecurityText;
    }

    public void setCommonSecurityText(String commonSecurityText) {
        this.commonSecurityText = commonSecurityText;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBusinessManagementStrength() {
        return businessManagementStrength;
    }

    public void setBusinessManagementStrength(String businessManagementStrength) {
        this.businessManagementStrength = businessManagementStrength;
    }

    public String getFinancialStability() {
        return financialStability;
    }

    public void setFinancialStability(String financialStability) {
        this.financialStability = financialStability;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public String getFinancialYearOne() {
        return financialYearOne;
    }

    public void setFinancialYearOne(String financialYearOne) {
        this.financialYearOne = financialYearOne;
    }

    public String getFinancialYearOneFinancial() {
        return financialYearOneFinancial;
    }

    public void setFinancialYearOneFinancial(String financialYearOneFinancial) {
        this.financialYearOneFinancial = financialYearOneFinancial;
    }

    public String getFinancialYearTwo() {
        return financialYearTwo;
    }

    public void setFinancialYearTwo(String financialYearTwo) {
        this.financialYearTwo = financialYearTwo;
    }

    public String getFinancialYearTwoFinancial() {
        return financialYearTwoFinancial;
    }

    public void setFinancialYearTwoFinancial(String financialYearTwoFinancial) {
        this.financialYearTwoFinancial = financialYearTwoFinancial;
    }

    public Double getIncomeAmountYearOne() {
        return incomeAmountYearOne;
    }

    public void setIncomeAmountYearOne(Double incomeAmountYearOne) {
        this.incomeAmountYearOne = incomeAmountYearOne;
    }

    public Double getIncomeGrowthYearOne() {
        return incomeGrowthYearOne;
    }

    public void setIncomeGrowthYearOne(Double incomeGrowthYearOne) {
        this.incomeGrowthYearOne = incomeGrowthYearOne;
    }

    public Double getIncomeAmountYearTwo() {
        return incomeAmountYearTwo;
    }

    public void setIncomeAmountYearTwo(Double incomeAmountYearTwo) {
        this.incomeAmountYearTwo = incomeAmountYearTwo;
    }

    public Double getIncomeGrowthYearTwo() {
        return incomeGrowthYearTwo;
    }

    public void setIncomeGrowthYearTwo(Double incomeGrowthYearTwo) {
        this.incomeGrowthYearTwo = incomeGrowthYearTwo;
    }

    public Double getProfitAfterTaxAmountYearOne() {
        return profitAfterTaxAmountYearOne;
    }

    public void setProfitAfterTaxAmountYearOne(Double profitAfterTaxAmountYearOne) {
        this.profitAfterTaxAmountYearOne = profitAfterTaxAmountYearOne;
    }

    public Double getProfitAfterTaxGrowthYearOne() {
        return profitAfterTaxGrowthYearOne;
    }

    public void setProfitAfterTaxGrowthYearOne(Double profitAfterTaxGrowthYearOne) {
        this.profitAfterTaxGrowthYearOne = profitAfterTaxGrowthYearOne;
    }

    public Double getProfitAfterTaxAmountYearTwo() {
        return profitAfterTaxAmountYearTwo;
    }

    public void setProfitAfterTaxAmountYearTwo(Double profitAfterTaxAmountYearTwo) {
        this.profitAfterTaxAmountYearTwo = profitAfterTaxAmountYearTwo;
    }

    public Double getProfitAfterTaxGrowthYearTwo() {
        return profitAfterTaxGrowthYearTwo;
    }

    public void setProfitAfterTaxGrowthYearTwo(Double profitAfterTaxGrowthYearTwo) {
        this.profitAfterTaxGrowthYearTwo = profitAfterTaxGrowthYearTwo;
    }

    public String getBusinessProfile() {
        return businessProfile;
    }

    public void setBusinessProfile(String businessProfile) {
        this.businessProfile = businessProfile;
    }

    public String getNetInterestMargin() {
        return netInterestMargin;
    }

    public void setNetInterestMargin(String netInterestMargin) {
        this.netInterestMargin = netInterestMargin;
    }

    public String getInterestCover() {
        return interestCover;
    }

    public void setInterestCover(String interestCover) {
        this.interestCover = interestCover;
    }

    public String getGearing() {
        return gearing;
    }

    public void setGearing(String gearing) {
        this.gearing = gearing;
    }

    public String getGrossNPL() {
        return grossNPL;
    }

    public void setGrossNPL(String grossNPL) {
        this.grossNPL = grossNPL;
    }

    public List<BccFacilityDTO> getBccFacilityDTOS() {
        if (bccFacilityDTOS == null) {
            this.bccFacilityDTOS = new ArrayList<>();
        }
        return bccFacilityDTOS;
    }

    public void setBccFacilityDTOS(List<BccFacilityDTO> bccFacilityDTOS) {
        this.bccFacilityDTOS = bccFacilityDTOS;
    }

    public List<BccCompanyDirectorDTO> getBccCompanyDirectorDTOS() {
        if (bccCompanyDirectorDTOS == null) {
            this.bccCompanyDirectorDTOS = new ArrayList<>();
        }
        return bccCompanyDirectorDTOS;
    }

    public void setBccCompanyDirectorDTOS(List<BccCompanyDirectorDTO> bccCompanyDirectorDTOS) {
        this.bccCompanyDirectorDTOS = bccCompanyDirectorDTOS;
    }

    public List<BccCompanyShareholderDTO> getBccCompanyShareholderDTOS() {
        if (bccCompanyShareholderDTOS == null) {
            this.bccCompanyShareholderDTOS = new ArrayList<>();
        }
        return bccCompanyShareholderDTOS;
    }

    public void setBccCompanyShareholderDTOS(List<BccCompanyShareholderDTO> bccCompanyShareholderDTOS) {
        this.bccCompanyShareholderDTOS = bccCompanyShareholderDTOS;
    }

    public String getExistingOutstandingAtDate() {
        return existingOutstandingAtDate;
    }

    public void setExistingOutstandingAtDate(String existingOutstandingAtDate) {
        this.existingOutstandingAtDate = existingOutstandingAtDate;
    }

    public String getProposedOutstandingAtDate() {
        return proposedOutstandingAtDate;
    }

    public void setProposedOutstandingAtDate(String proposedOutstandingAtDate) {
        this.proposedOutstandingAtDate = proposedOutstandingAtDate;
    }

    public BccExposureCompanyDTO getBccExposureCompanyDTO() {
        return bccExposureCompanyDTO;
    }

    public void setBccExposureCompanyDTO(BccExposureCompanyDTO bccExposureCompanyDTO) {
        this.bccExposureCompanyDTO = bccExposureCompanyDTO;
    }

    public BccExposureGroupDTO getBccExposureGroupDTO() {
        return bccExposureGroupDTO;
    }

    public void setBccExposureGroupDTO(BccExposureGroupDTO bccExposureGroupDTO) {
        this.bccExposureGroupDTO = bccExposureGroupDTO;
    }

    public String getRiskBasedPricing() {
        return riskBasedPricing;
    }

    public void setRiskBasedPricing(String riskBasedPricing) {
        this.riskBasedPricing = riskBasedPricing;
    }

    public String getExceptions() {
        return exceptions;
    }

    public void setExceptions(String exceptions) {
        this.exceptions = exceptions;
    }

    public String getOfficerOne() {
        return officerOne;
    }

    public void setOfficerOne(String officerOne) {
        this.officerOne = officerOne;
    }

    public String getOfficerTwo() {
        return officerTwo;
    }

    public void setOfficerTwo(String officerTwo) {
        this.officerTwo = officerTwo;
    }

    public String getOfficerThree() {
        return officerThree;
    }

    public void setOfficerThree(String officerThree) {
        this.officerThree = officerThree;
    }

    public String getEacMemberOne() {
        return eacMemberOne;
    }

    public void setEacMemberOne(String eacMemberOne) {
        this.eacMemberOne = eacMemberOne;
    }

    public String getEacMemberTwo() {
        return eacMemberTwo;
    }

    public void setEacMemberTwo(String eacMemberTwo) {
        this.eacMemberTwo = eacMemberTwo;
    }

    public String getEacMemberThree() {
        return eacMemberThree;
    }

    public void setEacMemberThree(String eacMemberThree) {
        this.eacMemberThree = eacMemberThree;
    }

    public String getOfficerFour() {
        return officerFour;
    }

    public void setOfficerFour(String officerFour) {
        this.officerFour = officerFour;
    }

    public String getOfficerOneDesignation() {
        return officerOneDesignation;
    }

    public void setOfficerOneDesignation(String officerOneDesignation) {
        this.officerOneDesignation = officerOneDesignation;
    }

    public String getOfficerTwoDesignation() {
        return officerTwoDesignation;
    }

    public void setOfficerTwoDesignation(String officerTwoDesignation) {
        this.officerTwoDesignation = officerTwoDesignation;
    }

    public String getOfficerThreeDesignation() {
        return officerThreeDesignation;
    }

    public void setOfficerThreeDesignation(String officerThreeDesignation) {
        this.officerThreeDesignation = officerThreeDesignation;
    }

    public String getOfficerFourDesignation() {
        return officerFourDesignation;
    }

    public void setOfficerFourDesignation(String officerFourDesignation) {
        this.officerFourDesignation = officerFourDesignation;
    }

    public String getEacMemberFour() {
        return eacMemberFour;
    }

    public void setEacMemberFour(String eacMemberFour) {
        this.eacMemberFour = eacMemberFour;
    }

    public String getEacMemberOneDesignation() {
        return eacMemberOneDesignation;
    }

    public void setEacMemberOneDesignation(String eacMemberOneDesignation) {
        this.eacMemberOneDesignation = eacMemberOneDesignation;
    }

    public String getEacMemberTwoDesignation() {
        return eacMemberTwoDesignation;
    }

    public void setEacMemberTwoDesignation(String eacMemberTwoDesignation) {
        this.eacMemberTwoDesignation = eacMemberTwoDesignation;
    }

    public String getEacMemberThreeDesignation() {
        return eacMemberThreeDesignation;
    }

    public void setEacMemberThreeDesignation(String eacMemberThreeDesignation) {
        this.eacMemberThreeDesignation = eacMemberThreeDesignation;
    }

    public String getEacMemberFourDesignation() {
        return eacMemberFourDesignation;
    }

    public void setEacMemberFourDesignation(String eacMemberFourDesignation) {
        this.eacMemberFourDesignation = eacMemberFourDesignation;
    }

    public String getFacilityDateOfApproval() {
        return facilityDateOfApproval;
    }

    public void setFacilityDateOfApproval(String facilityDateOfApproval) {
        this.facilityDateOfApproval = facilityDateOfApproval;
    }

    public List<BccRiskRatingYearDTO> getBccRiskRatingYearDTOS() {
        if (bccRiskRatingYearDTOS == null) {
            this.bccRiskRatingYearDTOS = new ArrayList<>();
        }
        return bccRiskRatingYearDTOS;
    }

    public void setBccRiskRatingYearDTOS(List<BccRiskRatingYearDTO> bccRiskRatingYearDTOS) {
        this.bccRiskRatingYearDTOS = bccRiskRatingYearDTOS;
    }

    public BigDecimal getProposedFacilityTotal() {
        return proposedFacilityTotal;
    }

    public void setProposedFacilityTotal(BigDecimal proposedFacilityTotal) {
        this.proposedFacilityTotal = proposedFacilityTotal;
    }

    public BigDecimal getExistingFacilityTotal() {
        return existingFacilityTotal;
    }

    public void setExistingFacilityTotal(BigDecimal existingFacilityTotal) {
        this.existingFacilityTotal = existingFacilityTotal;
    }

    public List<BccCustomerCribDetailDTO> getBccCustomerCribDetailDTOS() {
        if (this.bccCustomerCribDetailDTOS == null) {
            this.bccCustomerCribDetailDTOS = new ArrayList<>();
        }
        return bccCustomerCribDetailDTOS;
    }

    public void setBccCustomerCribDetailDTOS(List<BccCustomerCribDetailDTO> bccCustomerCribDetailDTOS) {
        this.bccCustomerCribDetailDTOS = bccCustomerCribDetailDTOS;
    }

    public BigDecimal getExistingPlusProposedTotal() {
        return existingPlusProposedTotal;
    }

    public void setExistingPlusProposedTotal(BigDecimal existingPlusProposedTotal) {
        this.existingPlusProposedTotal = existingPlusProposedTotal;
    }

    public String getPdfReport() {
        return pdfReport;
    }

    public void setPdfReport(String pdfReport) {
        this.pdfReport = pdfReport;
    }

    public BigDecimal getProposedOutstandingFacilityTotal() {
        return proposedOutstandingFacilityTotal;
    }

    public void setProposedOutstandingFacilityTotal(BigDecimal proposedOutstandingFacilityTotal) {
        this.proposedOutstandingFacilityTotal = proposedOutstandingFacilityTotal;
    }

    public BigDecimal getExistingOutstandingFacilityTotal() {
        return existingOutstandingFacilityTotal;
    }

    public void setExistingOutstandingFacilityTotal(BigDecimal existingOutstandingFacilityTotal) {
        this.existingOutstandingFacilityTotal = existingOutstandingFacilityTotal;
    }

    public BigDecimal getExistingOutstandingPlusProposedOutstandingTotal() {
        return existingOutstandingPlusProposedOutstandingTotal;
    }

    public void setExistingOutstandingPlusProposedOutstandingTotal(BigDecimal existingOutstandingPlusProposedOutstandingTotal) {
        this.existingOutstandingPlusProposedOutstandingTotal = existingOutstandingPlusProposedOutstandingTotal;
    }

    public BCCSecuritySummaryDTO getBccSecuritySummaryDTO() {
        return bccSecuritySummaryDTO;
    }

    public void setBccSecuritySummaryDTO(BCCSecuritySummaryDTO bccSecuritySummaryDTO) {
        this.bccSecuritySummaryDTO = bccSecuritySummaryDTO;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCurrentAssignUser() {
        return currentAssignUser;
    }

    public void setCurrentAssignUser(String currentAssignUser) {
        this.currentAssignUser = currentAssignUser;
    }

    public String getAssignUserDisplayName() {
        return assignUserDisplayName;
    }

    public void setAssignUserDisplayName(String assignUserDisplayName) {
        this.assignUserDisplayName = assignUserDisplayName;
    }

    public String getCurrentAssignUserDivCode() {
        return currentAssignUserDivCode;
    }

    public void setCurrentAssignUserDivCode(String currentAssignUserDivCode) {
        this.currentAssignUserDivCode = currentAssignUserDivCode;
    }

    @Override
    public String toString() {
        return "BoardCreditCommitteePaperDTO{" +
                "boardCreditCommitteePaperID=" + boardCreditCommitteePaperID +
                ", bccRefNumber='" + bccRefNumber + '\'' +
                ", facilityPaperID=" + facilityPaperID +
                ", paperType=" + paperType +
                ", branchCode='" + branchCode + '\'' +
                ", createdUserDisplayName='" + createdUserDisplayName + '\'' +
                ", createdUserName='" + createdUserName + '\'' +
                ", upmID=" + upmID +
                ", customerName='" + customerName + '\'' +
                ", status=" + status +
                '}';
    }

    public AppsConstants.YesNo getIsSubmitted() {
        return isSubmitted;
    }

    public void setIsSubmitted(AppsConstants.YesNo isSubmitted) {
        this.isSubmitted = isSubmitted;
    }
}
