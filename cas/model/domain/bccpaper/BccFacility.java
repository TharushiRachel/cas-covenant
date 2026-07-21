package com.itechro.cas.model.domain.bccpaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "T_BCC_FACILITY")
public class BccFacility extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_BCC_FACILITY")
    @SequenceGenerator(name = "SEQ_T_BCC_FACILITY", sequenceName = "SEQ_T_BCC_FACILITY", allocationSize = 1)
    @Column(name = "BCC_FACILITY_ID")
    private Integer bccFacilityID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BCC_PAPER_ID")
    private BoardCreditCommitteePaper boardCreditCommitteePaper;

    @Column(name = "FACILITY_ID")
    private Integer facilityID;

    @Column(name = "PARENT_FACILITY_ID")
    private Integer parentFacilityID;

    @Column(name = "PARENT_FACILITY_NAME")
    private String parentCreditFacilityName;

    @Column(name = "PARENT_FACILITY_TYPE")
    private String parentFacilityTypeName;

    @Column(name = "PARENT_FACILITY_AMOUNT")
    private BigDecimal parentFacilityAmount;

    @Column(name = "PARENT_FACILITY_CURRENCY")
    private String parentFacilityCurrency;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_ONE_OFF")
    private AppsConstants.YesNo isOneOff;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_DIRECT_FACILITY")
    private AppsConstants.YesNo directFacility;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_SERIES_OF_LOANS")
    private AppsConstants.YesNo seriesOfLoans;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_REVOLVING")
    private AppsConstants.YesNo revolving;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_REDUCTION")
    private AppsConstants.YesNo reduction;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_ENHANCEMENT")
    private AppsConstants.YesNo enhancement;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "DATE_GRANTED")
    private String dateGranted;

    @Column(name = "FACILITY_CURRENCY")
    private String facilityCurrency;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "BRANCH_CODE")
    private String branchCode;

    @Column(name = "INTEREST_RATE")
    private String interestRate;

    @Column(name = "PURPOSE")
    private String purpose;

    @Column(name = "OUTSTANDING_AS_AT")
    private String outstandingAsAt;

    @Column(name = "SETTLEMENT_DATE")
    private String settlementDate;

    @Column(name = "SETTLEMENT_PLAN")
    private String settlementPlan;

    @Column(name = "SECURITY")
    private String security;

    @Enumerated(EnumType.STRING)
    @Column(name = "BCC_FACILITY_TYPE")
    private DomainConstants.BCCFacilityType bccFacilityType;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getBccFacilityID() {
        return bccFacilityID;
    }

    public void setBccFacilityID(Integer bccFacilityID) {
        this.bccFacilityID = bccFacilityID;
    }

    public Integer getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Integer facilityID) {
        this.facilityID = facilityID;
    }

    public BoardCreditCommitteePaper getBoardCreditCommitteePaper() {
        return boardCreditCommitteePaper;
    }

    public void setBoardCreditCommitteePaper(BoardCreditCommitteePaper boardCreditCommitteePaper) {
        this.boardCreditCommitteePaper = boardCreditCommitteePaper;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateGranted() {
        return dateGranted;
    }

    public void setDateGranted(String dateGranted) {
        this.dateGranted = dateGranted;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getOutstandingAsAt() {
        return outstandingAsAt;
    }

    public void setOutstandingAsAt(String outstandingAsAt) {
        this.outstandingAsAt = outstandingAsAt;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public String getSettlementPlan() {
        return settlementPlan;
    }

    public void setSettlementPlan(String settlementPlan) {
        this.settlementPlan = settlementPlan;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public DomainConstants.BCCFacilityType getBccFacilityType() {
        return bccFacilityType;
    }

    public void setBccFacilityType(DomainConstants.BCCFacilityType bccFacilityType) {
        this.bccFacilityType = bccFacilityType;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public Integer getParentFacilityID() {
        return parentFacilityID;
    }

    public void setParentFacilityID(Integer parentFacilityID) {
        this.parentFacilityID = parentFacilityID;
    }

    public String getParentCreditFacilityName() {
        return parentCreditFacilityName;
    }

    public void setParentCreditFacilityName(String parentCreditFacilityName) {
        this.parentCreditFacilityName = parentCreditFacilityName;
    }

    public String getParentFacilityTypeName() {
        return parentFacilityTypeName;
    }

    public void setParentFacilityTypeName(String parentFacilityTypeName) {
        this.parentFacilityTypeName = parentFacilityTypeName;
    }

    public BigDecimal getParentFacilityAmount() {
        return parentFacilityAmount;
    }

    public void setParentFacilityAmount(BigDecimal parentFacilityAmount) {
        this.parentFacilityAmount = parentFacilityAmount;
    }

    public String getParentFacilityCurrency() {
        return parentFacilityCurrency;
    }

    public void setParentFacilityCurrency(String parentFacilityCurrency) {
        this.parentFacilityCurrency = parentFacilityCurrency;
    }

    public AppsConstants.YesNo getIsOneOff() {
        return isOneOff;
    }

    public void setIsOneOff(AppsConstants.YesNo isOneOff) {
        this.isOneOff = isOneOff;
    }

    public AppsConstants.YesNo getDirectFacility() {
        return directFacility;
    }

    public void setDirectFacility(AppsConstants.YesNo directFacility) {
        this.directFacility = directFacility;
    }

    public AppsConstants.YesNo getSeriesOfLoans() {
        return seriesOfLoans;
    }

    public void setSeriesOfLoans(AppsConstants.YesNo seriesOfLoans) {
        this.seriesOfLoans = seriesOfLoans;
    }

    public AppsConstants.YesNo getRevolving() {
        return revolving;
    }

    public void setRevolving(AppsConstants.YesNo revolving) {
        this.revolving = revolving;
    }

    public AppsConstants.YesNo getReduction() {
        return reduction;
    }

    public void setReduction(AppsConstants.YesNo reduction) {
        this.reduction = reduction;
    }

    public AppsConstants.YesNo getEnhancement() {
        return enhancement;
    }

    public void setEnhancement(AppsConstants.YesNo enhancement) {
        this.enhancement = enhancement;
    }

    public String getFacilityCurrency() {
        return facilityCurrency;
    }

    public void setFacilityCurrency(String facilityCurrency) {
        this.facilityCurrency = facilityCurrency;
    }
}
