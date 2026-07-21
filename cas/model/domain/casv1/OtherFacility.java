package com.itechro.cas.model.domain.casv1;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Table(name = "CASV1_OTHER_FACILITIES")
public class OtherFacility {

    @Column(name = "REF_NUM")
    private String refNo;

    @Column(name = "BANK_ID")
    private String bankID;

    @Column(name = "FACILITY")
    private String facility;

    @Column(name = "LIMIT")
    private BigDecimal limit;

    @Column(name = "LIMIT_CURRENCY")
    private Integer limitCurrency;

    @Column(name = "LIMIT_TYPE")
    private Integer limitType;

    @Column(name = "O_S")
    private BigDecimal os;

    @Column(name = "O_S_CURRENCY")
    private Integer osCurrency;

    @Column(name = "O_S_TYPE")
    private Integer osType;

    @Column(name = "SECURITY")
    private String security;

    @Column(name = "FAC_ORDER")
    private Integer facOrder;
}
