package com.itechro.cas.model.domain.lead;


import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "t_comp_facilities")
@Data
public class ComprehensiveFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comp_facility_seq")
    @SequenceGenerator(name = "comp_facility_seq", sequenceName = "seq_comp_facility_id", allocationSize = 1)
    @Column(name = "comp_facility_id")
    private Long compFacilityId;


    @Column(name = "facility_description", length = 1000)
    private String facilityDescription;

    @Column(name = "requested_tenure")
    private Integer requestedTenure; // in months

    @Column(name = "lease_rental", precision = 18, scale = 2)
    private BigDecimal leaseRental;

    @Column(name = "processing_fee", precision = 18, scale = 2)
    private BigDecimal processingFee;

    @Column(name = "validity_of_offer")
    private String validityOfOffer;

    @Column(name = "lease_amount", precision = 18, scale = 2)
    private BigDecimal leaseAmount;

    @Column(name = "repayment_mode", length = 50)
    private String repaymentMode;

    @Column(name = "upfront", precision = 18, scale = 2)
    private BigDecimal upfront;

    @Column(name = "insurance", precision = 18, scale = 2)
    private BigDecimal insurance;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "STATUS")
//    private AppsConstants.Status status;

    @Column(name = "effective_rate", length = 50)
    private String effectiveRate;

    @Column(name = "model", length = 50)
    private String model;

    @Column(name = "make", length = 50)
    private String make;
}
