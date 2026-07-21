package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.model.domain.casmaster.SupportingDoc;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.storage.DocStorage;

import javax.persistence.*;

@Entity
@Table(name = "T_CAS_CUSTOMER_DOC")
public class CASCustomerDoc extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CAS_CUSTOMER_DOC")
    @SequenceGenerator(name = "SEQ_T_CAS_CUSTOMER_DOC", sequenceName = "SEQ_T_CAS_CUSTOMER_DOC", allocationSize = 1)
    @Column(name = "CAS_CUSTOMER_DOC_ID")
    private Integer casCustomerDocID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAS_CUSTOMER_ID")
    private CASCustomer CASCustomer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPORTING_DOC_ID")
    private SupportingDoc supportingDoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_STORAGE_ID")
    private DocStorage docStorage;

    @Column(name = "DESCRIPTION")
    private String description;

    public Integer getCasCustomerDocID() {
        return casCustomerDocID;
    }

    public void setCasCustomerDocID(Integer casCustomerDocID) {
        this.casCustomerDocID = casCustomerDocID;
    }

    public CASCustomer getCASCustomer() {
        return CASCustomer;
    }

    public void setCASCustomer(CASCustomer CASCustomer) {
        this.CASCustomer = CASCustomer;
    }

    public SupportingDoc getSupportingDoc() {
        return supportingDoc;
    }

    public void setSupportingDoc(SupportingDoc supportingDoc) {
        this.supportingDoc = supportingDoc;
    }

    public DocStorage getDocStorage() {
        return docStorage;
    }

    public void setDocStorage(DocStorage docStorage) {
        this.docStorage = docStorage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
