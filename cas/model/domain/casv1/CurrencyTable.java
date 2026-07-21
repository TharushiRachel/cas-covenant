package com.itechro.cas.model.domain.casv1;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "CASV1_CURRENCY_TABLE")
public class CurrencyTable {

    @Column(name = "CURRENCY_NO")
    private Integer currencyNo;

    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    @Column(name = "CURRENCY_NAME")
    private String currencyName;
}
