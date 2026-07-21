package com.itechro.cas.model.dto.facilitypaper.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;

import java.util.*;

@Data
public class ApplicationCovenantDetailsDTO {

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

    private AppsConstants.CovenantStatusOnDisbursement disbursementType;

    private String noFrequencyDueDate;

    private AppsConstants.YesNo isExists;

    private String complianceStatus;

    private String accountId;
    
    private AppsConstants.CovenantApplicableType applicableType;

    private List<ApplicationCovenantFacilityDTO> applicationCovenantFacilityDTOS;

    public String getCovanentKey() {
        Map<Integer, String> facilityMap = new TreeMap<>(); // Using TreeMap to automatically sort by keys

        for (ApplicationCovenantFacilityDTO acf : applicationCovenantFacilityDTOS) {
            facilityMap.put(acf.getFacilityID(), acf.getDisplayText());
        }

        StringBuilder covKey = new StringBuilder();
        for (Map.Entry<Integer, String> entry : facilityMap.entrySet()) {
            covKey.append(" ").append(entry.getValue());
        }

        return covKey.toString().trim();
    }



}
