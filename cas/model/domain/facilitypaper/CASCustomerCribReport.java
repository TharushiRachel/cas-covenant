package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_CAS_CUSTOMER_CRIB_REPORT")
public class CASCustomerCribReport extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CAS_CUSTOMER_CRIB_REPORT")
    @SequenceGenerator(name = "SEQ_T_CAS_CUSTOMER_CRIB_REPORT", sequenceName = "SEQ_T_CAS_CUSTOMER_CRIB_REPORT", allocationSize = 1)
    @Column(name = "CAS_CUSTOMER_CRIB_REPORT_ID")
    private Integer casCustomerCribReportID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAS_CUSTOMER_ID")
    private CASCustomer CASCustomer;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRIB_DATE")
    private Date cribDate;

    @Column(name = "REPORT_CONTENT")
    private String reportContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "CRIB_STATUS")
    private DomainConstants.CribStatusType cribStatus;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "IDENTIFICATION_TYPE")
    private String identificationType;

    @Column(name = "IDENTIFICATION_NO")
    private String identificationNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Column(name = "SAVED_USER_DISPLAY_NAME")
    private String savedUserDisplayName;

    @Column(name = "SAVED_USER_DIV_CODE")
    private String savedUserDivCode;

    public Integer getCasCustomerCribReportID() {
        return casCustomerCribReportID;
    }

    public void setCasCustomerCribReportID(Integer casCustomerCribReportID) {
        this.casCustomerCribReportID = casCustomerCribReportID;
    }

    public CASCustomer getCASCustomer() {
        return CASCustomer;
    }

    public void setCASCustomer(CASCustomer CASCustomer) {
        this.CASCustomer = CASCustomer;
    }

    public Date getCribDate() {
        return cribDate;
    }

    public void setCribDate(Date cribDate) {
        this.cribDate = cribDate;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public DomainConstants.CribStatusType getCribStatus() {
        return cribStatus;
    }

    public void setCribStatus(DomainConstants.CribStatusType cribStatus) {
        this.cribStatus = cribStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNo() {
        return identificationNo;
    }

    public void setIdentificationNo(String identificationNo) {
        this.identificationNo = identificationNo;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getSavedUserDisplayName() {
        return savedUserDisplayName;
    }

    public void setSavedUserDisplayName(String savedUserDisplayName) {
        this.savedUserDisplayName = savedUserDisplayName;
    }

    public String getSavedUserDivCode() {
        return savedUserDivCode;
    }

    public void setSavedUserDivCode(String savedUserDivCode) {
        this.savedUserDivCode = savedUserDivCode;
    }
}
