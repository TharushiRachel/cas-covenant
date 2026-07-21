package com.itechro.cas.model.dto.advancedAnalytics;

import lombok.Data;

import java.io.Serializable;

@Data
public class CRIBRequestDTO implements Serializable {

    private String identityDoc;

    private String identityRef;

    private Object reportJson;
}
