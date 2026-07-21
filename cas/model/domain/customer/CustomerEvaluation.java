package com.itechro.cas.model.domain.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.facilitypaper.CASCustomer;
import com.itechro.cas.model.domain.facilitypaper.facility.FPCustomerEvaluation;
import com.itechro.cas.model.domain.facilitypaper.facility.Facility;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 *
 * @author tharushi
 */
@Getter
@Setter
@Entity
@Table(name = "CEF_CUSTOMER_EVALUATION")
public class CustomerEvaluation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CEF_CUSTOMER_EVALUATION")
    @SequenceGenerator(name = "SEQ_CEF_CUSTOMER_EVALUATION", sequenceName = "SEQ_CEF_CUSTOMER_EVALUATION", allocationSize = 1)
    @Column(name = "C_E_ID")
    private Integer customerEvaluationId;

    @Column(name = "CUST_ID")
    private String customerId;

    @Column(name = "E_R_ID")
    private Integer evaluationRecordId;

    @Column(name = "SCORE")
    private Integer score;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FILLED_DATE")
    private Date filledDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.CustomerEvaluation status;

    @Column(name = "USER_ID")
    private String userId;

//    @Column(name = "FACILITY_PAPER_ID")
//    private String facilityPaper;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "customerEvaluation")
    @NotAudited
    private Set<CustomerEvaluationResults> customerEvaluationResultsSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "customerEvaluation")
    @NotAudited
    private Set<FPCustomerEvaluation> fpCustomerEvaluationSet;

}
