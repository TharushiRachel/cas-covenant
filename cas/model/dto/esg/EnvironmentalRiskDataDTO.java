package com.itechro.cas.model.dto.esg;

import com.itechro.cas.model.domain.esg.EnvironmentalRiskData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class EnvironmentalRiskDataDTO implements Serializable {

    private Integer riskDataId;

    private Integer applicationFormId;

    private Integer facilityPaperId;

    private Integer riskCategoryId;

    private Integer categoryParentId;

    private String description;

    private String score;

    private String type;

    private String createdBy;

    private Date createdDate;

    public EnvironmentalRiskDataDTO(EnvironmentalRiskData environmentalRisk) {
        this.riskDataId = environmentalRisk.getRiskDataId();
        if (environmentalRisk.getApplicationForm() != null) {
            this.applicationFormId = environmentalRisk.getApplicationForm().getApplicationFormID();
        }else {
            this.applicationFormId = 0;
        }
        if (environmentalRisk.getFacilityPaper() != null) {
            this.facilityPaperId = environmentalRisk.getFacilityPaper().getFacilityPaperID();
        }else {
            this.facilityPaperId = 0;
        }
        this.riskCategoryId = environmentalRisk.getRiskCategoryId();
        this.categoryParentId = environmentalRisk.getCategoryParentId();
        this.description = environmentalRisk.getDescription();
        this.score = environmentalRisk.getScore();
        this.type = environmentalRisk.getType();
        this.createdBy = environmentalRisk.getCreatedBy();
        this.createdDate = environmentalRisk.getCreatedDate();
    }
}
