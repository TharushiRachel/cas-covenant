package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "T_CAS_CUS_OTR_BANK_FACILITY")
public class CasCustomerOtherBankFacility extends UserTrackableEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CAS_CUS_OTR_FACILITY")
    @SequenceGenerator(name = "SEQ_T_CAS_CUS_OTR_FACILITY", sequenceName = "SEQ_T_CAS_CUS_OTR_FACILITY", allocationSize = 1)
    @Column(name = "CAS_OTR_CUS_BANK_FACILITY_ID")
    private Integer casCusOtherBankFacilityID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAS_CUSTOMER_ID")
    private CASCustomer CASCustomer;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "BRANCH_NAME")
    private String branchName;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "FACILITY_AMOUNT")
    private BigDecimal facilityAmount;

    @Column(name = "EXISTING_AMOUNT")
    private BigDecimal existingAmount;

    @Column(name = "ORIGINAL_AMOUNT")
    private BigDecimal originalAmount;

    @Column(name = "OUTSTANDING_AMOUNT")
    private BigDecimal outstandingAmount;

    @Column(name = "FACILITY_TYPE")
    private String facilityType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DISBURSEMENT_DATE")
    private Date disbursementDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MATURITY_DATE")
    private Date maturityDate;

    @Column(name = "SECURITIES")
    private String securities;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getCasCusOtherBankFacilityID() {
        return casCusOtherBankFacilityID;
    }

    public void setCasCusOtherBankFacilityID(Integer casCusOtherBankFacilityID) {
        this.casCusOtherBankFacilityID = casCusOtherBankFacilityID;
    }

    public CASCustomer getCASCustomer() {
        return CASCustomer;
    }

    public void setCASCustomer(CASCustomer CASCustomer) {
        this.CASCustomer = CASCustomer;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getFacilityAmount() {
        return facilityAmount;
    }

    public void setFacilityAmount(BigDecimal facilityAmount) {
        this.facilityAmount = facilityAmount;
    }

    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public BigDecimal getExistingAmount() {
        return existingAmount;
    }

    public void setExistingAmount(BigDecimal existingAmount) {
        this.existingAmount = existingAmount;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public Date getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(Date disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getSecurities() {
        return securities;
    }

    public void setSecurities(String securities) {
        this.securities = securities;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
