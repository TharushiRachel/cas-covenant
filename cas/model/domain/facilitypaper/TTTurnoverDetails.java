package com.itechro.cas.model.domain.facilitypaper;


import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "TT_TURNOVER_DETAILS")
public class TTTurnoverDetails extends UserTrackableEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_F_TTTURNOVER_DETAILS")
    @SequenceGenerator(name = "SEQ_F_TTTURNOVER_DETAILS", sequenceName = "SEQ_F_TTTURNOVER_DETAILS", allocationSize = 1)
    @Column(name = "ID")
    private Integer Id;

    @Column(name = "TT_REF")
    private String ttRef;
    @Column(name = "TT_CURRENCY")
    private String ttCurency;
    @Column(name = "TT_AMOUNT")
    private String ttAmount;
    @Column(name = "USR_RATE")
    private String  usrRate;
    @Column(name = "CURRENCY_RATE")
    private String  currencyRate;
    @Column(name = "SOL_DESC")
    private String  solDesc;
    @Column(name = "RM")
    private String  rm;
    @Column(name = "SOL_ID")
    private String  solId;
    @Column(name = "PARTY_NAME")
    private String  partyName;
    @Column(name = "CIF_ID")
    private String  cifId;
    @Column(name = "LKR_AMOUNT")
    private String  lkrAmount;
    @Column(name = "USD_EQUIVALENT")
    private String  usdEquivalalent;
    @Column(name = "ACCOUNT_NUMBER")
    private String  accountNumber;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date  endDate;
}
