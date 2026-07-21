package com.itechro.cas.model.dto.facility;

import com.itechro.cas.model.dto.casmaster.CreditFacilityTemplateDTO;
import lombok.Data;

@Data
public class FacilityListDTO {

    private Integer facilityID;

    private String facilityRefCode;

    private Integer creditFacilityTemplateID;

    //private CreditFacilityTemplateDTO creditFacilityTemplateDTO;
}
