package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "T_FP_CUSTOMER_CLASSIFICATION")
public class FPCustomerClassification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FP_CUSTOMER_CLASSIFICATION")
    @SequenceGenerator(name = "SEQ_FP_CUSTOMER_CLASSIFICATION", sequenceName = "SEQ_FP_CUSTOMER_CLASSIFICATION", allocationSize = 1)
    @Column(name = "CUSTOMER_CLASSIFICATION_ID")
    private Integer customerClassificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAS_CUSTOMER_ID")
    private CASCustomer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FAC_CODE_ID")
    private CustomerClassification classification;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @Column(name = "CLASSIFICATION_COMMENT")
    private String comment;
}
