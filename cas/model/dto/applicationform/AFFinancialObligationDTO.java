package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.applicationform.AFFinancialObligation;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.math.BigDecimal;

public class AFFinancialObligationDTO implements Serializable {

    private Integer applicationFormID;

    private Integer financialObligationID;

    private Integer basicInformationID;

    private Integer customerCribResponseID;

    private Integer customerCribLiabilityID;

    private String identificationNumber;

    private DomainConstants.CustomerIdentificationType identificationType;

    private BigDecimal originalAmount;

    private BigDecimal presentOutstanding;

    private BigDecimal arrears;

    private String signedAs;

    private String securities;

    private String financialInstitution;

    private AppsConstants.YesNo isCribRecord;

    private String modifiedUserDisplayName;

    private String modifiedDateStr;

    private AppsConstants.Status status;

    public AFFinancialObligationDTO() {
    }

    public AFFinancialObligationDTO(AFFinancialObligation afFinancialObligation) {
        this.financialObligationID = afFinancialObligation.getFinancialObligationID();
        this.basicInformationID = afFinancialObligation.getBasicInformation().getBasicInformationID();
        this.originalAmount = afFinancialObligation.getOriginalAmount();
        this.presentOutstanding = afFinancialObligation.getPresentOutstanding();
        this.arrears = afFinancialObligation.getArrears();
        this.signedAs = afFinancialObligation.getSignedAs();
        this.securities = afFinancialObligation.getSecurities();
        this.financialInstitution = afFinancialObligation.getFinancialInstitution();
        this.status = afFinancialObligation.getStatus();
        this.customerCribResponseID = afFinancialObligation.getCustomerCribResponseID();
        this.customerCribLiabilityID = afFinancialObligation.getCustomerCribLiabilityID();
        this.identificationNumber = afFinancialObligation.getIdentificationNumber();
        this.identificationType = afFinancialObligation.getIdentificationType();
        this.isCribRecord = afFinancialObligation.getIsCribRecord();
        this.modifiedUserDisplayName = afFinancialObligation.getModifiedUserDisplayName();
        if (afFinancialObligation.getModifiedDate() != null) {
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(afFinancialObligation.getModifiedDate());
        }
    }

    public Integer getFinancialObligationID() {
        return financialObligationID;
    }

    public void setFinancialObligationID(Integer financialObligationID) {
        this.financialObligationID = financialObligationID;
    }

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public Integer getBasicInformationID() {
        return basicInformationID;
    }

    public void setBasicInformationID(Integer basicInformationID) {
        this.basicInformationID = basicInformationID;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getPresentOutstanding() {
        return presentOutstanding;
    }

    public void setPresentOutstanding(BigDecimal presentOutstanding) {
        this.presentOutstanding = presentOutstanding;
    }

    public BigDecimal getArrears() {
        return arrears;
    }

    public void setArrears(BigDecimal arrears) {
        this.arrears = arrears;
    }

    public String getSignedAs() {
        return signedAs;
    }

    public void setSignedAs(String signedAs) {
        this.signedAs = signedAs;
    }

    public String getSecurities() {
        return securities;
    }

    public void setSecurities(String securities) {
        this.securities = securities;
    }

    public String getFinancialInstitution() {
        return financialInstitution;
    }

    public void setFinancialInstitution(String financialInstitution) {
        this.financialInstitution = financialInstitution;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public Integer getCustomerCribResponseID() {
        return customerCribResponseID;
    }

    public void setCustomerCribResponseID(Integer customerCribResponseID) {
        this.customerCribResponseID = customerCribResponseID;
    }

    public Integer getCustomerCribLiabilityID() {
        return customerCribLiabilityID;
    }

    public void setCustomerCribLiabilityID(Integer customerCribLiabilityID) {
        this.customerCribLiabilityID = customerCribLiabilityID;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public DomainConstants.CustomerIdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(DomainConstants.CustomerIdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public AppsConstants.YesNo getIsCribRecord() {
        return isCribRecord;
    }

    public void setIsCribRecord(AppsConstants.YesNo isCribRecord) {
        this.isCribRecord = isCribRecord;
    }

    public String getModifiedUserDisplayName() {
        return modifiedUserDisplayName;
    }

    public void setModifiedUserDisplayName(String modifiedUserDisplayName) {
        this.modifiedUserDisplayName = modifiedUserDisplayName;
    }

    public String getModifiedDateStr() {
        return modifiedDateStr;
    }

    public void setModifiedDateStr(String modifiedDateStr) {
        this.modifiedDateStr = modifiedDateStr;
    }

    @Override
    public String toString() {
        return "AFFinancialObligationDTO{" +
                "applicationFormID=" + applicationFormID +
                ", financialObligationID=" + financialObligationID +
                ", customerCribResponseID=" + customerCribResponseID +
                ", customerCribLiabilityID=" + customerCribLiabilityID +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", identificationType=" + identificationType +
                ", basicInformationID=" + basicInformationID +
                ", originalAmount=" + originalAmount +
                ", presentOutstanding=" + presentOutstanding +
                ", arrears=" + arrears +
                ", signedAs='" + signedAs + '\'' +
                ", securities='" + securities + '\'' +
                ", financialInstitution='" + financialInstitution + '\'' +
                ", isCribRecord=" + isCribRecord +
                ", modifiedUserDisplayName='" + modifiedUserDisplayName + '\'' +
                ", status=" + status +
                '}';
    }
}
