package com.itechro.cas.model.dto.advancedAnalytics;

import lombok.Data;

import java.io.Serializable;

@Data
public class BorrowerPrincipalRequestDTO implements Serializable {

    private String relationship;

    private String identityDoc;

    private String identityRef;

    private String share;
}
