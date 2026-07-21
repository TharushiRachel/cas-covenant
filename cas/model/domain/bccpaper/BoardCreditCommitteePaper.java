package com.itechro.cas.model.domain.bccpaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.util.DecimalCalculator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "T_BOARD_CREDIT_COMMITTEE_PAPER")
public class BoardCreditCommitteePaper extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_BCC_PAPER")
    @SequenceGenerator(name = "SEQ_T_BCC_PAPER", sequenceName = "SEQ_T_BCC_PAPER", allocationSize = 1)
    @Column(name = "BCC_PAPER_ID")
    private Integer boardCreditCommitteePaperID;

    @Column(name = "BCC_REF_NUMBER")
    private String bccRefNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAPER_TYPE")
    private DomainConstants.BCCPaperType paperType;

    @Column(name = "BRANCH_CODE")
    private String branchCode;

    @Column(name = "CREATED_USER_DISPLAY_NAME")
    private String createdUserDisplayName;

    @Column(name = "UPM_ID")
    private Integer upmID;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "BUSINESS_PROFILE")
    private String businessProfile;

    @Column(name = "STARTED_CAPITAL")
    private String startedCapital;

    @Column(name = "NET_INTEREST_MARGIN")
    private String netInterestMargin;

    @Column(name = "GROSS_NPL")
    private String grossNPL;

    @Column(name = "INTEREST_COVER")
    private String interestCover;

    @Column(name = "GEARING")
    private String gearing;

    @Column(name = "MARKET_POSITION")
    private String marketPosition;

    @Column(name = "COST_OF_FUNDS_MONTH")
    private String costOfFundMonth;

    @Column(name = "COST_OF_FUNDS_YEAR")
    private Integer costOfFundYear;

    @Column(name = "MONTHLY_COST_OF_FUNDS_LKR")
    private Double monthlyCostOfFundsLkr;

    @Column(name = "MONTHLY_COST_OF_FUNDS_FCY")
    private Double monthlyCostOfFundsFcy;

    @Column(name = "CUMULATIVE_COST_OF_FUNDS_LKR")
    private Double cumulativeCostOfFundsLkr;

    @Column(name = "CUMULATIVE_COST_OF_FUNDS_FCY")
    private Double cumulativeCostOfFundsFcy;

    @Column(name = "INCREMENTAL_COST_OF_FUNDS_LKR")
    private Double incrementalCostOfFundsLkr;

    @Column(name = "INCREMENTAL_COST_OF_FUNDS_FCY")
    private Double incrementalCostOfFundsFcy;

    @Column(name = "BUSINESS_MANAGEMENT_STRENGTH")
    private String businessManagementStrength;

    @Column(name = "SECURITY_COVER")
    private String securityCover;

    @Column(name = "FINANCIAL_STABILITY")
    private String financialStability;

    @Column(name = "AUDITOR_NAME")
    private String auditorName;

    @Column(name = "FINANCIAL_YEAR_ONE")
    private String financialYearOne;

    @Column(name = "FINANCIAL_YEAR_ONE_FINANCIAL")
    private String financialYearOneFinancial;

    @Column(name = "FINANCIAL_YEAR_TWO")
    private String financialYearTwo;

    @Column(name = "FINANCIAL_YEAR_TWO_FINANCIAL")
    private String financialYearTwoFinancial;

    @Column(name = "INCOME_AMOUNT_YEAR_ONE")
    private Double incomeAmountYearOne;

    @Column(name = "INCOME_GROWTH_YEAR_ONE")
    private Double incomeGrowthYearOne;

    @Column(name = "INCOME_AMOUNT_YEAR_TWO")
    private Double incomeAmountYearTwo;

    @Column(name = "INCOME_GROWTH_YEAR_TWO")
    private Double incomeGrowthYearTwo;

    @Column(name = "PAT_AMOUNT_YEAR_ONE")
    private Double profitAfterTaxAmountYearOne;

    @Column(name = "PAT_GROWTH_YEAR_ONE")
    private Double profitAfterTaxGrowthYearOne;

    @Column(name = "PAT_AMOUNT_YEAR_TWO")
    private Double profitAfterTaxAmountYearTwo;

    @Column(name = "PAT_GROWTH_YEAR_TWO")
    private Double profitAfterTaxGrowthYearTwo;

    @Enumerated(EnumType.STRING)
    @Column(name = "GRO_RECOMMENDATION")
    private DomainConstants.GRORecommendation recommendation;

    @Column(name = "RECOMMENDATION_REMARK")
    private String recommendationRemark;

    @Column(name = "RISK_BASED_PRICING")
    private String riskBasedPricing;

    @Column(name = "JUSTIFICATION")
    private String justification;

    @Column(name = "COM_SECURITY_TEXT")
    private String commonSecurityText;

    @Column(name = "NOTE")
    private String note;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FACILITY_DATE_OF_APPROVAL")
    private Date facilityDateOfApproval;

    @Column(name = "PROPOSED_FACILITY_TOTAL")
    private BigDecimal proposedFacilityTotal;

    @Column(name = "EXISTING_FACILITY_TOTAL")
    private BigDecimal existingFacilityTotal;

    @Column(name = "EXISTING_PURPOSED_TOTAL")
    private BigDecimal existingPlusProposedTotal;

    @Column(name = "PROPOSED_OUTSTANDING_F_TOTAL")
    private BigDecimal proposedOutstandingFacilityTotal;

    @Column(name = "EXISTING_OUTSTANDING_F_TOTAL")
    private BigDecimal existingOutstandingFacilityTotal;

    @Column(name = "EXIS_PURPO_OUTSTAND_TOTAL")
    private BigDecimal existingOutstandingPlusProposedOutstandingTotal;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXISTING_OUTSTANDING_DATE")
    private Date existingOutstandingAtDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PROPOSED_OUTSTANDING_DATE")
    private Date proposedOutstandingAtDate;

    @Column(name = "EXCEPTIONS")
    private String exceptions;

    @Column(name = "OFFICER_ONE")
    private String officerOne;

    @Column(name = "OFFICER_TWO")
    private String officerTwo;

    @Column(name = "OFFICER_THREE")
    private String officerThree;

    @Column(name = "OFFICER_FOUR")
    private String officerFour;

    @Column(name = "OFFICER_ONE_DESIGNATION")
    private String officerOneDesignation;

    @Column(name = "OFFICER_TWO_DESIGNATION")
    private String officerTwoDesignation;

    @Column(name = "OFFICER_THREE_DESIGNATION")
    private String officerThreeDesignation;

    @Column(name = "OFFICER_FOUR_DESIGNATION")
    private String officerFourDesignation;

    @Column(name = "ECA_MEMBER_ONE")
    private String eacMemberOne;

    @Column(name = "ECA_MEMBER_TWO")
    private String eacMemberTwo;

    @Column(name = "ECA_MEMBER_THREE")
    private String eacMemberThree;

    @Column(name = "ECA_MEMBER_FOUR")
    private String eacMemberFour;

    @Column(name = "ECA_MEMBER_ONE_DESIGNATION")
    private String eacMemberOneDesignation;

    @Column(name = "ECA_MEMBER_TWO_DESIGNATION")
    private String eacMemberTwoDesignation;

    @Column(name = "ECA_MEMBER_THREE_DESIGNATION")
    private String eacMemberThreeDesignation;

    @Column(name = "ECA_MEMBER_FOUR_DESIGNATION")
    private String eacMemberFourDesignation;

    @Column(name = "PDF_REPORT")
    private String pdfReport;

    @Column(name = "CURRENT_ASSIGN_USER")
    private String currentAssignUser;

    @Column(name = "ASSIGN_USER_DISPLAY_NAME")
    private String assignUserDisplayName;

    @Column(name = "CURRENT_ASSIGN_USER_DIV_CODE")
    private String currentAssignUserDivCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_SUBMITTED")
    private AppsConstants.YesNo isSubmitted;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "boardCreditCommitteePaper")
    private Set<BccFacility> bccFacilitySet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "boardCreditCommitteePaper")
    private Set<BccCompanyDirector> bccCompanyDirectorSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "boardCreditCommitteePaper")
    private Set<BccCompanyShareholder> bccCompanyShareholderSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "boardCreditCommitteePaper")
    private Set<BccRiskRatingYear> bccRiskRatingYears;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "boardCreditCommitteePaper")
    private BccExposureGroup bccExposureGroup;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "boardCreditCommitteePaper")
    private BccExposureCompany bccExposureCompany;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "boardCreditCommitteePaper")
    private Set<BccCustomerCribDetail> bccCustomerCribDetails;

    public FacilityPaper getFacilityPaper() {
        return facilityPaper;
    }

    public void setFacilityPaper(FacilityPaper facilityPaper) {
        this.facilityPaper = facilityPaper;
    }

    public Integer getBoardCreditCommitteePaperID() {
        return boardCreditCommitteePaperID;
    }

    public void setBoardCreditCommitteePaperID(Integer boardCreditCommitteePaperID) {
        this.boardCreditCommitteePaperID = boardCreditCommitteePaperID;
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

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getBccRefNumber() {
        return bccRefNumber;
    }

    public void setBccRefNumber(String bccRefNumber) {
        this.bccRefNumber = bccRefNumber;
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

    public BigDecimal getProposedFacilityTotal() {
        if (proposedFacilityTotal == null) {
            return DecimalCalculator.getDefaultZero();
        }
        return proposedFacilityTotal;
    }

    public void setProposedFacilityTotal(BigDecimal proposedFacilityTotal) {
        this.proposedFacilityTotal = proposedFacilityTotal;
    }

    public BigDecimal getExistingFacilityTotal() {
        if (existingFacilityTotal == null) {
            return DecimalCalculator.getDefaultZero();
        }
        return existingFacilityTotal;
    }

    public BigDecimal getExistingPlusProposedTotal() {
        if (existingPlusProposedTotal == null) {
            return DecimalCalculator.getDefaultZero();
        }
        return existingPlusProposedTotal;
    }

    public void setExistingPlusProposedTotal(BigDecimal existingPlusProposedTotal) {
        this.existingPlusProposedTotal = existingPlusProposedTotal;
    }

    public void setExistingFacilityTotal(BigDecimal existingFacilityTotal) {
        this.existingFacilityTotal = existingFacilityTotal;
    }

    public Date getExistingOutstandingAtDate() {
        return existingOutstandingAtDate;
    }

    public void setExistingOutstandingAtDate(Date existingOutstandingAtDate) {
        this.existingOutstandingAtDate = existingOutstandingAtDate;
    }

    public Date getProposedOutstandingAtDate() {
        return proposedOutstandingAtDate;
    }

    public void setProposedOutstandingAtDate(Date proposedOutstandingAtDate) {
        this.proposedOutstandingAtDate = proposedOutstandingAtDate;
    }

    public Set<BccFacility> getBccFacilitySet() {
        if (bccFacilitySet == null) {
            this.bccFacilitySet = new HashSet<>();
        }
        return bccFacilitySet;
    }

    public List<BccFacility> getOrderedBccFacilitySet() {
        return getBccFacilitySet().stream().sorted(new Comparator<BccFacility>() {
            @Override
            public int compare(BccFacility o1, BccFacility o2) {
                return o1.getBccFacilityID().compareTo(o2.getBccFacilityID());
            }
        }).collect(Collectors.toList());
    }

    public Set<BccCompanyDirector> getBccCompanyDirectorSet() {
        if (bccCompanyDirectorSet == null) {
            bccCompanyDirectorSet = new HashSet<>();
        }
        return bccCompanyDirectorSet;
    }

    public void addBccCompanyDirector(BccCompanyDirector bccCompanyDirector) {
        bccCompanyDirector.setBoardCreditCommitteePaper(this);
        this.getBccCompanyDirectorSet().add(bccCompanyDirector);
    }

    public List<BccCompanyDirector> getOrderedBccCompanyDirectorSet() {
        return getBccCompanyDirectorSet().stream().sorted(new Comparator<BccCompanyDirector>() {
            @Override
            public int compare(BccCompanyDirector o1, BccCompanyDirector o2) {
                return o1.getBccCompanyDirectorID().compareTo(o2.getBccCompanyDirectorID());
            }
        }).collect(Collectors.toList());
    }

    public void setBccCompanyDirectorSet(Set<BccCompanyDirector> bccCompanyDirectorSet) {
        this.bccCompanyDirectorSet = bccCompanyDirectorSet;
    }

    public Set<BccCompanyShareholder> getBccCompanyShareholderSet() {
        if (bccCompanyShareholderSet == null) {
            bccCompanyShareholderSet = new HashSet<>();
        }
        return bccCompanyShareholderSet;
    }

    public void setBccCompanyShareholderSet(Set<BccCompanyShareholder> bccCompanyShareholderSet) {
        this.bccCompanyShareholderSet = bccCompanyShareholderSet;
    }

    public void addBccCompanyShareholder(BccCompanyShareholder bccCompanyShareholder) {
        bccCompanyShareholder.setBoardCreditCommitteePaper(this);
        this.getBccCompanyShareholderSet().add(bccCompanyShareholder);
    }

    public List<BccCompanyShareholder> getOrderedBccCompanyShareholderSet() {
        return getBccCompanyShareholderSet().stream().sorted(new Comparator<BccCompanyShareholder>() {
            @Override
            public int compare(BccCompanyShareholder o1, BccCompanyShareholder o2) {
                return o1.getBccCompanyShareHolderID().compareTo(o2.getBccCompanyShareHolderID());
            }
        }).collect(Collectors.toList());
    }

    public void setBccFacilitySet(Set<BccFacility> bccFacilitySet) {
        this.bccFacilitySet = bccFacilitySet;
    }

    public BccExposureGroup getBccExposureGroup() {
        return bccExposureGroup;
    }

    public void setBccExposureGroup(BccExposureGroup bccExposureGroup) {
        this.bccExposureGroup = bccExposureGroup;
    }

    public BccExposureCompany getBccExposureCompany() {
        return bccExposureCompany;
    }

    public void setBccExposureCompany(BccExposureCompany bccExposureCompany) {
        this.bccExposureCompany = bccExposureCompany;
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

    public Date getFacilityDateOfApproval() {
        return facilityDateOfApproval;
    }

    public void setFacilityDateOfApproval(Date facilityDateOfApproval) {
        this.facilityDateOfApproval = facilityDateOfApproval;
    }

    public Set<BccRiskRatingYear> getBccRiskRatingYears() {
        if (bccRiskRatingYears == null) {
            this.bccRiskRatingYears = new HashSet<>();
        }
        return bccRiskRatingYears;
    }

    public List<BccRiskRatingYear> getOrderedBccRiskRatingYears() {
        return getBccRiskRatingYears().stream().sorted(new Comparator<BccRiskRatingYear>() {
            @Override
            public int compare(BccRiskRatingYear o1, BccRiskRatingYear o2) {
                return o1.getBccRiskRatingYearID().compareTo(o2.getBccRiskRatingYearID());
            }
        }).collect(Collectors.toList());
    }

    public void setBccRiskRatingYears(Set<BccRiskRatingYear> bccRiskRatingYears) {
        this.bccRiskRatingYears = bccRiskRatingYears;
    }

    public void addBccRiskRatingYear(BccRiskRatingYear bccRiskRatingYear) {
        bccRiskRatingYear.setBoardCreditCommitteePaper(this);
        this.getBccRiskRatingYears().add(bccRiskRatingYear);
    }

    public void addBccFacility(BccFacility bccFacility) {
        bccFacility.setBoardCreditCommitteePaper(this);
        this.getBccFacilitySet().add(bccFacility);
    }

    public void addCustomerCribDetails(BccCustomerCribDetail bccCustomerCribDetail) {
        bccCustomerCribDetail.setBoardCreditCommitteePaper(this);
        this.getBccCustomerCribDetails().add(bccCustomerCribDetail);
    }

    public Set<BccCustomerCribDetail> getBccCustomerCribDetails() {
        if (bccCustomerCribDetails == null) {
            bccCustomerCribDetails = new HashSet<>();
        }
        return bccCustomerCribDetails;
    }

    public List<BccCustomerCribDetail> getOrderedCustomerCribDetails() {
        return getBccCustomerCribDetails().stream().sorted(new Comparator<BccCustomerCribDetail>() {
            @Override
            public int compare(BccCustomerCribDetail o1, BccCustomerCribDetail o2) {
                return o1.getBccCustomerCribDetailsID().compareTo(o2.getBccCustomerCribDetailsID());
            }
        }).collect(Collectors.toList());
    }

    public void setBccCustomerCribDetails(Set<BccCustomerCribDetail> bccCustomerCribDetails) {
        this.bccCustomerCribDetails = bccCustomerCribDetails;
    }

    public String getPdfReport() {
        return pdfReport;
    }

    public void setPdfReport(String pdfReport) {
        this.pdfReport = pdfReport;
    }

    public BigDecimal getProposedOutstandingFacilityTotal() {
        if (proposedOutstandingFacilityTotal == null) {
            this.proposedOutstandingFacilityTotal = DecimalCalculator.getDefaultZero();
        }
        return proposedOutstandingFacilityTotal;
    }

    public void setProposedOutstandingFacilityTotal(BigDecimal proposedOutstandingFacilityTotal) {
        this.proposedOutstandingFacilityTotal = proposedOutstandingFacilityTotal;
    }

    public BigDecimal getExistingOutstandingFacilityTotal() {
        if (existingOutstandingFacilityTotal == null) {
            this.existingOutstandingFacilityTotal = DecimalCalculator.getDefaultZero();
        }
        return existingOutstandingFacilityTotal;
    }

    public void setExistingOutstandingFacilityTotal(BigDecimal existingOutstandingFacilityTotal) {
        this.existingOutstandingFacilityTotal = existingOutstandingFacilityTotal;
    }

    public BigDecimal getExistingOutstandingPlusProposedOutstandingTotal() {
        if (existingOutstandingPlusProposedOutstandingTotal == null) {
            this.existingOutstandingPlusProposedOutstandingTotal = DecimalCalculator.getDefaultZero();
        }
        return existingOutstandingPlusProposedOutstandingTotal;
    }

    public void setExistingOutstandingPlusProposedOutstandingTotal(BigDecimal existingOutstandingPlusProposedOutstandingTotal) {
        this.existingOutstandingPlusProposedOutstandingTotal = existingOutstandingPlusProposedOutstandingTotal;
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

    public AppsConstants.YesNo getIsSubmitted() {
        return isSubmitted;
    }

    public void setIsSubmitted(AppsConstants.YesNo isSubmitted) {
        this.isSubmitted = isSubmitted;
    }
}
