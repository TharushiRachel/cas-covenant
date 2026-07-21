package com.itechro.cas.model.domain.finacle;


import com.itechro.cas.model.domain.casmaster.CAUser;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "COLLATERAL_DETAILS")
@ToString
public class CollateralDetails implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_F_COLLATERAL_DETAILS")
    @SequenceGenerator(name = "SEQ_F_COLLATERAL_DETAILS", sequenceName = "SEQ_F_COLLATERAL_DETAILS", allocationSize = 1)
    @Column(name = "ID")
    private Integer Id;

    @Column(name = "COLLATERAL_TYPE")
    private String collateralType;
    @Column(name = "INSURANCE_VALUATION_EXP_DATE")
    private String insuranceValuationExpireDates;
    @Column(name = "COLLATERAL_ID")
    private String collateralId;
    @Column(name = "FACILITY_PAPER_ID")
    private Integer facilityPaperId;
    @Column(name = "CUSTOMER_FINACLE_ID")
    private String customerFinacleId;
    @Column(name = "CREATED_DATE")
    private String createdDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "collateralDetails")
    private List<InsuranceValuationDetails> insuranceValuationDetails;
}
