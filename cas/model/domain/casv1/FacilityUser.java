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
@Table(name = "CASV1_FACILITY_USERS_TABLE")
public class FacilityUser {

    @Column(name = "REF_NO")
    private String refNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FACILITY_DATE")
    private Date facilityDate;

    @Column(name = "FROM_USER_ID")
    private String fromUserID;

    @Column(name = "TO_USER_ID")
    private String toUserID;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PASSED_DATE")
    private Date date;
}
