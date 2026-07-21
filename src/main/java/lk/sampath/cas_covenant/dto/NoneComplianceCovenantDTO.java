package lk.sampath.cas_covenant.dto;

import lk.sampath.cas_covenant.entity.NoneComplianceCovenant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoneComplianceCovenantDTO {

    private Integer nonComplianceCovenantId;

    private Integer serialNumber;

    private Integer facilityPaperId;

    private String comment;

    private Date addedDate;

    private String addedBy;

    private String addedUserDisplayName;

    private Integer addedUserId;

    public NoneComplianceCovenantDTO(NoneComplianceCovenant noneComplianceCovenant){
        this.nonComplianceCovenantId = noneComplianceCovenant.getNonComplianceCovenantId();
        this.serialNumber = noneComplianceCovenant.getSerialNumber();
        this.facilityPaperId = noneComplianceCovenant.getFacilityPaperId();
        this.comment = noneComplianceCovenant.getComment();
        this.addedDate = noneComplianceCovenant.getAddedDate();
        this.addedBy = noneComplianceCovenant.getAddedBy();
        this.addedUserDisplayName = noneComplianceCovenant.getAddedUserDisplayName();
        this.addedUserId = noneComplianceCovenant.getAddedUserId();
    }
}
