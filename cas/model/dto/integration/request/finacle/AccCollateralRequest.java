package com.itechro.cas.model.dto.integration.request.finacle;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccCollateralRequest implements Serializable {

    private String type;

    private String acctId;

    private String nodeId;
}
