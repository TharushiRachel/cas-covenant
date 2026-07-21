package com.itechro.cas.model.dto.integration.request.finacle;

import lombok.Data;

import java.io.Serializable;

@Data
public class CusAASAccountsRQ implements Serializable {
    private String reqId;

    private String acctNumber;
}
