package com.itechro.cas.model.domain.kalyptosystem;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "T_KALYPTO_PERIOD")

public class KalyptoPeriod {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PERIOD_ID")
    private String periodId;

    @Column(name = "HEADER_TEXT")
    private String headerText;

    @Column(name = "FACILITY_ID")
    private String facilityId;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "IS_NEW_ADDED")
    private Integer isNewAdded;


}