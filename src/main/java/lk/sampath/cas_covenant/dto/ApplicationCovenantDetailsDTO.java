package lk.sampath.cas_covenant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lk.sampath.cas_covenant.enums.CovenantApplicableType;
import lk.sampath.cas_covenant.enums.CovenantStatus;
import lk.sampath.cas_covenant.enums.CovenantStatusOnDisbursement;
import lk.sampath.cas_covenant.enums.YesNoStatus;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ApplicationCovenantDetailsDTO {

    private Integer applicationCovenantId;

    @JsonProperty("RequestUUID")
    private String RequestUUID;

    private String custId;

    private String casReference;

    private String createdUserDisplayName;

    private String createdBy;

    private Date createdDate;

    private CovenantStatus status;

    private String workClass;

    @JsonProperty("covenant_Code")
    private String covenant_Code;

    private String covenant_Description;

    private String covenant_Frequency;

    private Date covenant_Due_Date;

    private CovenantStatusOnDisbursement disbursementType;

    private String noFrequencyDueDate;

    private YesNoStatus isExists;

    private String complianceStatus;

    private String accountId;

    private CovenantApplicableType applicableType;

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
