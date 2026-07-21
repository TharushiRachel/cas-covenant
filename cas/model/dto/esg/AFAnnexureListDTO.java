package com.itechro.cas.model.dto.esg;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;

@Data
public class AFAnnexureListDTO {

    private Integer annexureId;

    private String name;

    private String isMandatory;

    private String allowRiskEdit;
}
