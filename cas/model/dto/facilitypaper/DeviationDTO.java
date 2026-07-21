package com.itechro.cas.model.dto.facilitypaper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itechro.cas.model.domain.casmaster.TempDeviation;
import com.itechro.cas.model.domain.facilitypaper.Deviation;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeviationDTO implements Serializable {

    private Integer deviationId;

    private Integer parentId;

    private String deviationType;

    private String description;

    private String status;

    private String approveStatus;

    private String createdBy;

    private String modifiedBy;

    private String authorizedBy;

    @JsonProperty("isTempRecord")
    private boolean isTempRecord;

    public DeviationDTO(){
    }

    public DeviationDTO(Deviation deviation) {
        this.deviationId = deviation.getDeviationId();
        this.deviationType = deviation.getDeviationType();
        this.description = deviation.getDescription();
        this.status = deviation.getStatus();
        this.createdBy = deviation.getCreatedBy();
        this.modifiedBy = deviation.getModifiedBy();
        this.authorizedBy = deviation.getAuthorizedBy();
        this.isTempRecord = false;
    }

    public DeviationDTO(TempDeviation deviation) {
        this.deviationId = deviation.getDeviationId();
        this.parentId = deviation.getParentId();
        this.deviationType = deviation.getDeviationType();
        this.description = deviation.getDescription();
        this.status = deviation.getStatus();
        this.createdBy = deviation.getCreatedBy();
        this.isTempRecord = true;
    }
}
