package com.itechro.cas.model.domain.covenant;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


/**
 *
 *
 * @author tharushi
 */
@Entity
@Getter
@Setter
@Table(name = "CUSTOMER_COVENANT")
public class CustomerCovenant{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CUSTOMER_COVENANT")
    @SequenceGenerator(name = "SEQ_CUSTOMER_COVENANT", sequenceName = "SEQ_CUSTOMER_COVENANT", allocationSize = 1)
    @Column(name = "CUSTOMER_COVENANT_ID")
    private Integer customerCovenantId;
    @Column(name = "REQUESTUUID")
    private String requestUUID;

    @Column(name = "CUSTOMER_FINANCIAL_ID")
    private String customerFinancialID;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumns({
            @JoinColumn(name = "FP_REF_NUMBER", referencedColumnName = "FP_REF_NUMBER"),
            @JoinColumn(name = "FACILITY_PAPER_ID", referencedColumnName = "FACILITY_PAPER_ID")
    })
    private FacilityPaper facilityPaper;

    @Column(name = "COVENANT_CODE")
    private String covenant_Code;

    @Column(name = "COVENANT_DESCRIPTION")
    private String covenant_Description;

    @Column(name = "COVENANT_FREQUENCY")
    private String covenant_Frequency;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "COVENANT_DUE_DATE")
    private Date covenant_Due_Date;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.CovenantStatus status;

    @Column(name = "CREATED_USER_DISPLAY_NAME")
    private String createdUserDisplayName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "DISBURSEMENT_TYPE")
    private AppsConstants.CovenantStatusOnDisbursement disbursementType;

    @Column(name = "NO_FREQUENCY_DUE_DATE")
    private String noFrequencyDueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_EXISTS")
    private AppsConstants.YesNo isExists;

    @Column(name = "COMPLIENCE_STATUS")
    private String complianceStatus;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "APPLICABLE_TYPE")
    private AppsConstants.CovenantApplicableType applicableType;
}
