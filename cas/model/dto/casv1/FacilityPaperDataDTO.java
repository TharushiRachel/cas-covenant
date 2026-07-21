package com.itechro.cas.model.dto.casv1;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FacilityPaperDataDTO implements Serializable {

    private List<FacilityTotalDTO> facilities;

    private List<String> facilityTexts;

    private List<String> securityTexts;
}
