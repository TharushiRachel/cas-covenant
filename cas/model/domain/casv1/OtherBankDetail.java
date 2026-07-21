package com.itechro.cas.model.domain.casv1;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "CASV1_OTHER_BANKS_TABLE")
public class OtherBankDetail {

    @Column(name = "BANK_NO")
    private Integer bankNo;

    @Column(name = "BANK_NAME")
    private String bankName;
}
