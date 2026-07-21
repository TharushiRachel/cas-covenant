package com.itechro.cas.model.dto.acae.response;

import lombok.Data;

import java.util.List;

@Data
public class ACAEAasRecordResultDTO {
    private String acctNumber;

    private List<ACAEStatByAcctDataDTO> statByAcctData;
}
