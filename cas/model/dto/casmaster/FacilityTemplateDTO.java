package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.model.domain.casmaster.CreditFacilityTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacilityTemplateDTO {

    private Integer facilityTemplateID;

    private String templateName;

    private String description;


    public FacilityTemplateDTO(CreditFacilityTemplate creditFacilityTemplate) {
        this.facilityTemplateID = creditFacilityTemplate.getCreditFacilityTemplateID();
        this.templateName = creditFacilityTemplate.getCreditFacilityName();
        this.description = creditFacilityTemplate.getDescription();
    }
}
