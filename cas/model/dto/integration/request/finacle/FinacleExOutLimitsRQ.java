package com.itechro.cas.model.dto.integration.request.finacle;

import lombok.Data;

import java.io.Serializable;

@Data
public class FinacleExOutLimitsRQ implements Serializable  {
    private String requestUUID;

    private String cusId;

}
