package com.itechro.cas.model.domain.casv1;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;


@Getter
@Setter
@Table(name = "CASV1_COMMON_CODES_TABLE")
public class CommonCode {

    @Column(name = "CODE_ID")
    private Integer codeID;

    @Column(name = "CODE_TYPE")
    private Integer codeType;

    @Column(name = "CODE_DESC")
    private String codeDescription;

    @Column(name = "PARENT_ID")
    private Integer parentID;

    @Column(name = "ORDER_ID")
    private Integer orderID;
}
