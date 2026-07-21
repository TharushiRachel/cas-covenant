package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

@Data
public class Account {
    private String acctNo;
    private String sancLimit;
    private String clrBal;
    private String ccyCode;
}
