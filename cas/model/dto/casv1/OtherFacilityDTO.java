package com.itechro.cas.model.dto.casv1;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OtherFacilityDTO implements Serializable {

    private String refNo;

    private String bankID;

    private String bankName;

    private String facility;

    private BigDecimal limit;

    private String limitCurrency;

    private Integer limitType;

    private BigDecimal os;

    private String osCurrency;

    private Integer osType;

    private String security;

    private Integer facOrder;
}
