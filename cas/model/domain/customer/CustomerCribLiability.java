package com.itechro.cas.model.domain.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_CUSTOMER_CRIB_LIABILITY")
public class CustomerCribLiability extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CUS_CRIB_LIABILITY")
    @SequenceGenerator(name = "SEQ_T_CUS_CRIB_LIABILITY", sequenceName = "SEQ_T_CUS_CRIB_LIABILITY", allocationSize = 1)
    @Column(name = "CUSTOMER_CRIB_LIABILITY_ID")
    private Integer customerCribLiabilityID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_CRIB_RESPONSE_ID")
    private CustomerCribResponse customerCribResponse;

    @Column(name = "IDENTIFICATION_NUMBER")
    private String identificationNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "IDENTIFICATION_TYPE")
    private DomainConstants.CustomerIdentificationType identificationType;

    @Column(name = "ORIGINAL_AMOUNT")
    private String originalAmount;

    @Column(name = "PRESENT_OUTSTANDING")
    private String presentOutstanding;

    @Column(name = "ARREARS")
    private String arrears;

    @Column(name = "SIGNED_AS")
    private String signedAs;

    @Column(name = "SECURITIES")
    private String securities;

    @Column(name = "FINANCIAL_INSTITUTION")
    private String financialInstitution;

    @Column(name = "MODIFIED_USER_DISPLAY_NAME")
    private String modifiedUserDisplayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getCustomerCribLiabilityID() {
        return customerCribLiabilityID;
    }

    public void setCustomerCribLiabilityID(Integer customerCribLiabilityID) {
        this.customerCribLiabilityID = customerCribLiabilityID;
    }

    public CustomerCribResponse getCustomerCribResponse() {
        return customerCribResponse;
    }

    public void setCustomerCribResponse(CustomerCribResponse customerCribResponse) {
        this.customerCribResponse = customerCribResponse;
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

    public String getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(String originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getPresentOutstanding() {
        return presentOutstanding;
    }

    public void setPresentOutstanding(String presentOutstanding) {
        this.presentOutstanding = presentOutstanding;
    }

    public String getArrears() {
        return arrears;
    }

    public void setArrears(String arrears) {
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

    public String getModifiedUserDisplayName() {
        return modifiedUserDisplayName;
    }

    public void setModifiedUserDisplayName(String modifiedUserDisplayName) {
        this.modifiedUserDisplayName = modifiedUserDisplayName;
    }
}
