package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.ApprovableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_CFT_SUPPORTING_DOC")
public class CftSupportingDoc extends ApprovableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CFT_SUPPORTING_DOC")
    @SequenceGenerator(name = "SEQ_T_CFT_SUPPORTING_DOC", sequenceName = "SEQ_T_CFT_SUPPORTING_DOC", allocationSize = 1)
    @Column(name = "CFT_SUPPORTING_DOC_ID")
    private Integer cftSupportingDocID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREDIT_FACILITY_TEMPLATE_ID")
    private CreditFacilityTemplate creditFacilityTemplate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPORTING_DOC_ID")
    private SupportingDoc supportingDoc;

    @Enumerated(EnumType.STRING)
    @Column(name = "MANDATORY")
    private AppsConstants.YesNo mandatory;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getCftSupportingDocID() {
        return cftSupportingDocID;
    }

    public void setCftSupportingDocID(Integer cftSupportingDocID) {
        this.cftSupportingDocID = cftSupportingDocID;
    }

    public CreditFacilityTemplate getCreditFacilityTemplate() {
        return creditFacilityTemplate;
    }

    public void setCreditFacilityTemplate(CreditFacilityTemplate creditFacilityTemplate) {
        this.creditFacilityTemplate = creditFacilityTemplate;
    }

    public SupportingDoc getSupportingDoc() {
        return supportingDoc;
    }

    public void setSupportingDoc(SupportingDoc supportingDoc) {
        this.supportingDoc = supportingDoc;
    }

    public AppsConstants.YesNo getMandatory() {
        return mandatory;
    }

    public void setMandatory(AppsConstants.YesNo mandatory) {
        this.mandatory = mandatory;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
