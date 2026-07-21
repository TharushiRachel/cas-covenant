package com.itechro.cas.model.domain.casv1;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Table(name = "CASV1_SECURITY_TEXT_TABLE")
public class SecurityText {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FORMAT_DATE")
    private Date formatDate;

    @Column(name = "FACILITY_NO")
    private Integer facilityNo;

    @Column(name = "FORMAT_NO")
    private Integer formatNo;

    @Column(name = "LINE_NO")
    private Integer lineNo;

    @Column(name = "START_WITH")
    private Integer startWith;

    @Column(name = "END_WITH")
    private Integer endWith;

    @Column(name = "TEXT_NO")
    private Integer textNo;

    @Column(name = "TEXT")
    private String text;
}
