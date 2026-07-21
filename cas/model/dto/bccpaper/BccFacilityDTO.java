package com.itechro.cas.model.dto.bccpaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.bccpaper.BccFacility;
import com.itechro.cas.model.domain.facilitypaper.facility.Facility;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityFacilitySecurity;
import com.itechro.cas.model.dto.facility.FacilitySecurityDTO;
import com.itechro.cas.util.DecimalCalculator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BccFacilityDTO implements Serializable {

    private Integer bccFacilityID;

    private Integer boardCreditCommitteePaperID;

    private Integer facilityID;

    private Integer parentFacilityID;

    private String parentCreditFacilityName;

    private String parentFacilityTypeName;

    private BigDecimal parentFacilityAmount;

    private String parentFacilityCurrency;

    private AppsConstants.YesNo isOneOff;

    private AppsConstants.YesNo directFacility;

    private AppsConstants.YesNo seriesOfLoans;

    private AppsConstants.YesNo revolving;

    private AppsConstants.YesNo reduction;

    private AppsConstants.YesNo enhancement;

    private String type;

    private String dateGranted;

    private String facilityCurrency;

    private BigDecimal amount;

    private String branchCode;

    private String interestRate;

    private String purpose;

    private String outstandingAsAt;

    private String settlementDate;

    private String settlementPlan;

    private String security;

    private DomainConstants.BCCFacilityType bccFacilityType;

    private AppsConstants.Status status;

    private List<FacilitySecurityDTO> facilitySecurities;

    private Boolean isForeignCurrency;

    public BccFacilityDTO() {
    }

    public BccFacilityDTO(BccFacility bccFacility) {
        this.bccFacilityID = bccFacility.getBccFacilityID();
        this.boardCreditCommitteePaperID = bccFacility.getBoardCreditCommitteePaper().getBoardCreditCommitteePaperID();
        this.facilityID = bccFacility.getFacilityID();
        this.parentFacilityID = bccFacility.getParentFacilityID();
        this.parentCreditFacilityName = bccFacility.getParentCreditFacilityName();
        this.parentFacilityTypeName = bccFacility.getParentFacilityTypeName();
        this.parentFacilityAmount = bccFacility.getParentFacilityAmount();
        this.parentFacilityCurrency = bccFacility.getParentFacilityCurrency();
        this.isOneOff = bccFacility.getIsOneOff();
        this.directFacility = bccFacility.getDirectFacility();
        this.directFacility = bccFacility.getDirectFacility();
        this.seriesOfLoans = bccFacility.getSeriesOfLoans();
        this.revolving = bccFacility.getRevolving();
        this.reduction = bccFacility.getReduction();
        this.enhancement = bccFacility.getEnhancement();
        this.type = bccFacility.getType();
        this.dateGranted = bccFacility.getDateGranted();
        this.facilityCurrency = bccFacility.getFacilityCurrency();
        this.amount = bccFacility.getAmount();
        this.branchCode = bccFacility.getBranchCode();
        this.interestRate = bccFacility.getInterestRate();
        this.purpose = bccFacility.getPurpose();
        this.outstandingAsAt = bccFacility.getOutstandingAsAt();
        this.settlementDate = bccFacility.getSettlementDate();
        this.settlementPlan = bccFacility.getSettlementPlan();
        this.security = bccFacility.getSecurity();
        this.bccFacilityType = bccFacility.getBccFacilityType();
        this.status = bccFacility.getStatus();
        this.isForeignCurrency = !bccFacility.getFacilityCurrency().isEmpty() && bccFacility.getFacilityCurrency() != null && !bccFacility.getFacilityCurrency().equals("LKR");

        if (bccFacility.getBoardCreditCommitteePaper() != null && bccFacility.getBoardCreditCommitteePaper().getFacilityPaper() != null) {
            List<Facility> facilities = bccFacility.getBoardCreditCommitteePaper().getFacilityPaper().getOrderedActiveFacilityList();
            Facility facility = facilities.stream().filter(fac -> fac.getFacilityID().equals(bccFacility.getFacilityID())).collect(Collectors.toList()).get(0);
            this.facilitySecurities = getFacilitySecurityDTOS(facility);
        }
    }

    private static List<FacilitySecurityDTO> getFacilitySecurityDTOS(Facility facility) {
        List<FacilitySecurityDTO> facilitySecurityDTOList = new ArrayList<>();
        for (FacilityFacilitySecurity facilityFacilitySecurity : facility.getFacilityFacilitySecurities()) {
            if (facilityFacilitySecurity.getStatus() == AppsConstants.Status.ACT) {
                FacilitySecurityDTO facilitySecurityDTO = new FacilitySecurityDTO(facilityFacilitySecurity.getFacilitySecurity());
                facilitySecurityDTO.setFacilitySecurityAmount(facilityFacilitySecurity.getFacilitySecurityAmount());
                facilitySecurityDTOList.add(facilitySecurityDTO);
            }
        }
        return facilitySecurityDTOList;
    }

    public Integer getBccFacilityID() {
        return bccFacilityID;
    }

    public void setBccFacilityID(Integer bccFacilityID) {
        this.bccFacilityID = bccFacilityID;
    }

    public Integer getBoardCreditCommitteePaperID() {
        return boardCreditCommitteePaperID;
    }

    public Integer getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Integer facilityID) {
        this.facilityID = facilityID;
    }

    public void setBoardCreditCommitteePaperID(Integer boardCreditCommitteePaperID) {
        this.boardCreditCommitteePaperID = boardCreditCommitteePaperID;
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

    public String getSettlementDate() {
        return settlementDate;
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

    public BigDecimal getMillionValue(BigDecimal amount) {
        if (amount != null) {
            return DecimalCalculator.divide(amount, 1000000);
        }
        return amount;
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

    @Override
    public String toString() {
        return "BccFacilityDTO{" +
                "bccFacilityID=" + bccFacilityID +
                ", boardCreditCommitteePaperID=" + boardCreditCommitteePaperID +
                ", facilityID=" + facilityID +
                ", parentFacilityID=" + parentFacilityID +
                ", parentCreditFacilityName='" + parentCreditFacilityName + '\'' +
                ", parentFacilityTypeName='" + parentFacilityTypeName + '\'' +
                ", parentFacilityAmount=" + parentFacilityAmount +
                ", parentFacilityCurrency='" + parentFacilityCurrency + '\'' +
                ", isOneOff=" + isOneOff +
                ", directFacility=" + directFacility +
                ", seriesOfLoans=" + seriesOfLoans +
                ", revolving=" + revolving +
                ", reduction=" + reduction +
                ", enhancement=" + enhancement +
                ", type='" + type + '\'' +
                ", dateGranted='" + dateGranted + '\'' +
                ", amount=" + amount +
                ", branchCode='" + branchCode + '\'' +
                ", interestRate='" + interestRate + '\'' +
                ", purpose='" + purpose + '\'' +
                ", outstandingAsAt='" + outstandingAsAt + '\'' +
                ", settlementDate='" + settlementDate + '\'' +
                ", settlementPlan='" + settlementPlan + '\'' +
                ", security='" + security + '\'' +
                ", bccFacilityType=" + bccFacilityType +
                ", status=" + status +
                '}';
    }

    public List<FacilitySecurityDTO> getFacilitySecurities() {
        return facilitySecurities;
    }

    public void setFacilitySecurities(List<FacilitySecurityDTO> facilitySecurities) {
        this.facilitySecurities = facilitySecurities;
    }

    public String getFacilityCurrency() {
        return facilityCurrency;
    }

    public void setFacilityCurrency(String facilityCurrency) {
        this.facilityCurrency = facilityCurrency;
    }

    public Boolean getForeignCurrency() {
        return isForeignCurrency;
    }

    public void setForeignCurrency(Boolean foreignCurrency) {
        isForeignCurrency = foreignCurrency;
    }
}
