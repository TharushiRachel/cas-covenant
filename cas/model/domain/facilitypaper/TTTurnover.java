package com.itechro.cas.model.domain.facilitypaper;


import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "TT_TURNOVER_ACCOUNTS")
public class TTTurnover extends UserTrackableEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_F_TTTURNOVER_ACCOUNTS")
    @SequenceGenerator(name = "SEQ_F_TTTURNOVER_ACCOUNTS", sequenceName = "SEQ_F_TTTURNOVER_ACCOUNTS", allocationSize = 1)
    @Column(name = "ID")
    private Integer Id;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "FACILITY_PAPER_ID")
    private Integer facilityPaperId;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date  endDate;

}
