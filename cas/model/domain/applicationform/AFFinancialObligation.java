package com.itechro.cas.model.domain.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "T_AF_FINANCIAL_OBLIGATION")
public class AFFinancialObligation extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_FINANCIAL_OBLIGATION")
    @SequenceGenerator(name = "SEQ_T_AF_FINANCIAL_OBLIGATION", sequenceName = "SEQ_T_AF_FINANCIAL_OBLIGATION", allocationSize = 1)
    @Column(name = "FINANCIAL_OBLIGATION_ID")
    private Integer financialObligationID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASIC_INFORMATION_ID")
    private BasicInformation basicInformation;

    @Column(name = "CUSTOMER_CRIB_RESPONSE_ID")
    private Integer customerCribResponseID;

    @Column(name = "CUSTOMER_CRIB_LIABILITY_ID")
    private Integer customerCribLiabilityID;

    @Column(name = "IDENTIFICATION_NUMBER")
    private String identificationNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "IDENTIFICATION_TYPE")
    private DomainConstants.CustomerIdentificationType identificationType;

    @Column(name = "ORIGINAL_AMOUNT")
    private BigDecimal originalAmount;

    @Column(name = "PRESENT_OUTSTANDING")
    private BigDecimal presentOutstanding;

    @Column(name = "ARREARS")
    private BigDecimal arrears;

    @Column(name = "SIGNED_AS")
    private String signedAs;

    @Column(name = "SECURITIES")
    private String securities;

    @Column(name = "FINANCIAL_INSTITUTION")
    private String financialInstitution;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_CRIB_RECORD")
    private AppsConstants.YesNo isCribRecord;

    @Column(name = "MODIFIED_USER_DISPLAY_NAME")
    private String modifiedUserDisplayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getFinancialObligationID() {
        return financialObligationID;
    }

    public void setFinancialObligationID(Integer financialObligationID) {
        this.financialObligationID = financialObligationID;
    }

    public BasicInformation getBasicInformation() {
        return basicInformation;
    }

    public void setBasicInformation(BasicInformation basicInformation) {
        this.basicInformation = basicInformation;
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

}
