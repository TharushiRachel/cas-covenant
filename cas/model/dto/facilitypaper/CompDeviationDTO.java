package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.CompDeviation;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CompDeviationDTO implements Serializable {

    private Integer fpDeviationId ;

    private Integer deviationId;

    private Integer facilityPaperId;
    private String deviationType;

    private String description;

    private boolean checked;

    private String divComment;

    private String status;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    public CompDeviationDTO(){}

    public CompDeviationDTO(CompDeviation compDeviation) {
        this.fpDeviationId = compDeviation.getFpDeviationId();
        this.deviationId = compDeviation.getDeviationId();
        this.facilityPaperId = compDeviation.getFacilityPaperId();
        this.checked = compDeviation.getIsChecked().equals(AppsConstants.YesNo.Y.toString());
        this.description = compDeviation.getDescription();
        this.deviationType = compDeviation.getDeviationType();
        this.divComment = compDeviation.getDivComment();
        this.status = compDeviation.getStatus();
        this.createdBy = compDeviation.getCreatedBy();
        this.modifiedBy = compDeviation.getModifiedBy();
    }
}
