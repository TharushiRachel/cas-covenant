package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.model.domain.casmaster.DeviationType;
import lombok.Data;

import java.util.Date;

@Data
public class DeviationTypeDTO {

    private Integer deviationTypeId;

    private String deviationType;

    private String status;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    public DeviationTypeDTO() {
    }

    public DeviationTypeDTO(DeviationType deviationType) {
        this.deviationTypeId = deviationType.getDeviationTypeId();
        this.deviationType = deviationType.getDeviationType();
        this.status = deviationType.getStatus();
        this.createdDate = deviationType.getCreatedDate();
        this.createdBy = deviationType.getCreatedBy();
        this.modifiedDate = deviationType.getModifiedDate();
        this.modifiedBy = deviationType.getModifiedBy();
    }
}
