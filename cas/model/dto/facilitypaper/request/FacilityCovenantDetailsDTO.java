package com.itechro.cas.model.dto.facilitypaper.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.covenant.ApplicationLevelCovenant;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityCovenantFacilities;
import com.itechro.cas.service.faclititypaper.FacilityPaperReplicationBuilder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Data
public class FacilityCovenantDetailsDTO {
    private static final Logger LOG = LoggerFactory.getLogger(FacilityPaperReplicationBuilder.class);

    private Integer applicationCovenantId;

    @JsonProperty("RequestUUID")
    private String RequestUUID;

    private String custId;

    private String casReference;

    private String createdUserDisplayName;

    private String createdBy;

    private Date createdDate;

    private AppsConstants.CovenantStatus status;

    private String workClass;

    @JsonProperty("covenant_Code")
    private String covenant_Code;

    private String covenant_Description;

    private String covenant_Frequency;

    private Date covenant_Due_Date;

    private List<ApplicationCovenantFacilityDTO> applicationCovenantFacilityDTOS;

    private String facility_name;

    private BigDecimal facility_amount;

    private AppsConstants.CovenantStatusOnDisbursement disbursementType;

    private String noFrequencyDueDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL},  mappedBy = "applicationLevelCovenant")
    private List<FacilityCovenantFacilities> facilityCovenantFacilitiesSet;

    public FacilityCovenantDetailsDTO(){

    }
    public FacilityCovenantDetailsDTO(ApplicationLevelCovenant applicationLevelCovenant){


        this.applicationCovenantId=applicationLevelCovenant.getApplicationCovenantId();
        this.covenant_Code=applicationLevelCovenant.getCovenant_Code();
        this.covenant_Description=applicationLevelCovenant.getCovenant_Description();
        this.covenant_Frequency=applicationLevelCovenant.getCovenant_Frequency();
        this.covenant_Due_Date=applicationLevelCovenant.getCovenant_Due_Date();
        this.status=applicationLevelCovenant.getStatus();
        this.RequestUUID=applicationLevelCovenant.getRequestUUID();
        this.createdBy= applicationLevelCovenant.getCreatedBy();
        this.createdDate=applicationLevelCovenant.getCreatedDate();
        this.createdUserDisplayName=applicationLevelCovenant.getCreatedUserDisplayName();
    }

}
