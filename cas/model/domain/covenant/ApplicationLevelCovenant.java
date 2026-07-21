package com.itechro.cas.model.domain.covenant;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.customer.Customer;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityCovenantFacilities;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "APPLICATION_LEVEL_COVENANT")
//@Where(clause = "status != 1")
public class ApplicationLevelCovenant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_APPLICATION_LEVEL_COVENANT")
    @SequenceGenerator(name = "SEQ_APPLICATION_LEVEL_COVENANT", sequenceName = "SEQ_APPLICATION_LEVEL_COVENANT", allocationSize = 1)
    @Column(name = "APPLICATION_COVENANT_ID")
    private Integer applicationCovenantId;

    @Column(name = "REQUESTUUID")
    private String requestUUID;

    @Column(name = "CUSTOMER_FINANCIAL_ID")
    private String customerFinacleID;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "FP_REF_NUMBER", referencedColumnName = "FP_REF_NUMBER")
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

    @Column(name = "ACCOUNT_ID")
    private String accountId;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "APPLICABLE_TYPE")
    private AppsConstants.CovenantApplicableType applicableType;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL},  mappedBy = "applicationLevelCovenant")
    private List<FacilityCovenantFacilities> facilityCovenantFacilitiesSet;
}
