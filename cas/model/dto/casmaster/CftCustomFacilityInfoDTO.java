package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.casmaster.CftCustomFacilityInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class CftCustomFacilityInfoDTO implements Serializable {
    private Integer cftCustomFacilityInfoID;
    private Integer creditFacilityTemplateID;
    private String customFacilityInfoName;
    private String description;
    private String customFacilityInfoCode;
    private String fieldType;
    private AppsConstants.YesNo mandatory;
    private AppsConstants.Status status;
    private Integer displayOrder;

    public CftCustomFacilityInfoDTO() {
    }

    public CftCustomFacilityInfoDTO(CftCustomFacilityInfo cftCustomFacilityInfo){
        this.cftCustomFacilityInfoID = cftCustomFacilityInfo.getCftCustomFacilityInfoID();
        this.creditFacilityTemplateID = cftCustomFacilityInfo.getCreditFacilityTemplate().getCreditFacilityTemplateID();
        this.customFacilityInfoName = cftCustomFacilityInfo.getCustomFacilityInfoName();
        this.description = cftCustomFacilityInfo.getDescription();;
        this.customFacilityInfoCode = cftCustomFacilityInfo.getCustomFacilityInfoCode();
        this.fieldType = cftCustomFacilityInfo.getFieldType();
        this.mandatory = cftCustomFacilityInfo.getMandatory();
        this.status = cftCustomFacilityInfo.getStatus();
        this.displayOrder = cftCustomFacilityInfo.getDisplayOrder();
    }
}
