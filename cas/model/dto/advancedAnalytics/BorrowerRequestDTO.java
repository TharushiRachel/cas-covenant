package com.itechro.cas.model.dto.advancedAnalytics;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BorrowerRequestDTO implements Serializable {

    private String name;

    private String type;

    private List<BorrowerPrincipalRequestDTO> principal;

    private List<CRIBRequestDTO> cribRequests;
}
