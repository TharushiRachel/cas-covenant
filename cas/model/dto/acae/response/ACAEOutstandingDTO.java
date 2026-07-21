package com.itechro.cas.model.dto.acae.response;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ACAEOutstandingDTO {

    private String acctType;

    private String acctNum;

    private String lienAmount;

    private String reservedAmount;

    private String balance;

    private String currency;

    private Integer index;

    private Boolean isSameAcct;

    private List<ACAEStatByAcctDataDTO> statByAcctData = new ArrayList<>();
}
