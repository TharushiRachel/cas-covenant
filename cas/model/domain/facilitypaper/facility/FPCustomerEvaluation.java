package com.itechro.cas.model.domain.facilitypaper.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.customer.CustomerEvaluation;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

/**
 *
 *
 * @author tharushi
 */
@Entity
@Getter
@Setter
@Table(name = "T_CUSTOMER_EVALUATION")
public class FPCustomerEvaluation{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CUSTOMER_EVALUATION")
    @SequenceGenerator(name = "SEQ_T_CUSTOMER_EVALUATION", sequenceName = "SEQ_T_CUSTOMER_EVALUATION", allocationSize = 0)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "FACILITY_PAPER_ID")
    private Integer facilityPaperID;

    @ManyToOne
    @JoinColumns( {
            @JoinColumn(name = "C_E_ID", referencedColumnName = "C_E_ID"),
            @JoinColumn(name = "CUST_ID", referencedColumnName = "CUST_ID"),

    })
    private CustomerEvaluation customerEvaluation;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.EvaluationForm Status;

}
