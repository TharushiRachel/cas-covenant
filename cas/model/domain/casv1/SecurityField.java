package com.itechro.cas.model.domain.casv1;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;


@Getter
@Setter
@Table(name = "CASV1_SECURITY_FIELDS")
public class SecurityField {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FACILITY_DATE")
    private Date facilityDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SECURITY_TEXT_DATE")
    private Date securityTextDate;

    @Column(name = "SECURITY_TEXT_NO")
    private Integer securityTextNo;

    @Column(name = "FACILITY_ID")
    private Integer facilityID;

    @Column(name = "FACILITY_NO")
    private Integer facilityNo;

    @Column(name = "CUSTOMER_REF")
    private String customerRef;

    @Column(name = "FIELD_NO")
    private String fieldNo;

    @Column(name = "FIELD_VALUE")
    private String fieldValue;
}
