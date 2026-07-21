package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.model.domain.casmaster.EnvironmentalRisk;
import com.itechro.cas.model.domain.casmaster.EnvironmentalRiskTemp;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EnvironmentalRiskDTO implements Serializable {
    private Integer riskCategoryId;

    private Integer parentCategoryId;

    private Integer parentId;

    private String description;

    private String score;

    private String type;

    private String status;

    private String createdBy;

    private Date createdDate;

    private String modifiedBy;

    private Date modifiedDate;

    private String approvedStatus;

    private Date approvedDate;

    private String approvedBy;

    public EnvironmentalRiskDTO(EnvironmentalRisk environmentalRisk) {
        this.riskCategoryId = environmentalRisk.getRiskCategoryId();
        this.parentId =  environmentalRisk.getParentId();
        this.description =  environmentalRisk.getDescription();
        this.score =  environmentalRisk.getScore();
        this.type =  environmentalRisk.getType();
        this.status = environmentalRisk.getStatus();
        this.createdBy =  environmentalRisk.getCreatedBy();
        this.createdDate =  environmentalRisk.getCreatedDate();
        this.modifiedBy =  environmentalRisk.getModifiedBy();
        this.modifiedDate =  environmentalRisk.getModifiedDate();
        this.approvedStatus = environmentalRisk.getApprovedStatus();
        this.approvedBy =  environmentalRisk.getApprovedBy();
        this.approvedDate =  environmentalRisk.getApprovedDate();
    }


    public EnvironmentalRiskDTO(EnvironmentalRiskTemp environmentalRisk) {
        this.riskCategoryId = environmentalRisk.getRiskCategoryId();
        this.parentCategoryId = environmentalRisk.getParentCategoryId();
        this.parentId =  environmentalRisk.getParentId();
        this.description =  environmentalRisk.getDescription();
        this.score =  environmentalRisk.getScore();
        this.type =  environmentalRisk.getType();
        this.status = environmentalRisk.getStatus();
        this.createdBy =  environmentalRisk.getCreatedBy();
        this.createdDate =  environmentalRisk.getCreatedDate();
    }
}
