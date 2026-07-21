package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

import java.util.List;

@Data
public class cusAccountsRS {

    private String Status;
    private String RequestUUID;
    private List<CasAaasData> CasAaasData;
}
