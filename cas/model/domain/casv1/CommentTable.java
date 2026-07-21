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
@Table(name = "CASV1_COMMENTS_TABLE")
public class CommentTable {

    @Column(name = "REF_NUM")
    private String refNo;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "REMARK_1")
    private String remark1;

    @Column(name = "REMARK_2")
    private String remark2;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REMARK_DATE")
    private Date remarkDate;

    @Column(name = "USER_ID")
    private String userID;

    @Column(name = "STATE")
    private Integer state;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FACILITY_DATE")
    private Date facilityDate;
}
