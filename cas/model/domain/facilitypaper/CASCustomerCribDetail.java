package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.SupportingDoc;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.storage.DocStorage;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_CAS_CUSTOMER_CRIB_DETAIL")
public class CASCustomerCribDetail extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CAS_CUSTOMER_CRIB_DETAIL")
    @SequenceGenerator(name = "SEQ_T_CAS_CUSTOMER_CRIB_DETAIL", sequenceName = "SEQ_T_CAS_CUSTOMER_CRIB_DETAIL", allocationSize = 1)
    @Column(name = "CAS_CUSTOMER_CRIB_DETAIL_ID")
    private Integer casCustomerCribDetailsID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAS_CUSTOMER_ID")
    private CASCustomer CASCustomer;

    @Enumerated(EnumType.STRING)
    @Column(name = "CRIB_STATUS")
    private DomainConstants.CribStatusType cribStatus;

    @Column(name = "REMARK")
    private String remark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_STORAGE_ID")
    private DocStorage docStorage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPORTING_DOC_ID")
    private SupportingDoc supportingDoc;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRIB_ISSUE_DATE")
    private Date cribIssueDate;

    @Column(name = "UPLOADED_USER_DISPLAY_NAME")
    private String uploadedUserDisplayName;

    @Column(name = "UPLOADED_SOL_ID")
    private String uploadedDivCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getCasCustomerCribDetailsID() {
        return casCustomerCribDetailsID;
    }

    public void setCasCustomerCribDetailsID(Integer casCustomerCribDetailsID) {
        this.casCustomerCribDetailsID = casCustomerCribDetailsID;
    }

    public CASCustomer getCASCustomer() {
        return CASCustomer;
    }

    public void setCASCustomer(CASCustomer CASCustomer) {
        this.CASCustomer = CASCustomer;
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

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public Date getCribIssueDate() {
        return cribIssueDate;
    }

    public void setCribIssueDate(Date cribIssueDate) {
        this.cribIssueDate = cribIssueDate;
    }

    public DocStorage getDocStorage() {
        return docStorage;
    }

    public void setDocStorage(DocStorage docStorage) {
        this.docStorage = docStorage;
    }

    public SupportingDoc getSupportingDoc() {
        return supportingDoc;
    }

    public void setSupportingDoc(SupportingDoc supportingDoc) {
        this.supportingDoc = supportingDoc;
    }

    public String getUploadedUserDisplayName() {
        return uploadedUserDisplayName;
    }

    public void setUploadedUserDisplayName(String uploadedUserDisplayName) {
        this.uploadedUserDisplayName = uploadedUserDisplayName;
    }

    public String getUploadedDivCode() {
        return uploadedDivCode;
    }

    public void setUploadedDivCode(String uploadedDivCode) {
        this.uploadedDivCode = uploadedDivCode;
    }
}
