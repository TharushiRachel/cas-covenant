package com.itechro.cas.model.dto.facilitypaper.securityDocument;
import lombok.Data;
import java.util.List;

@Data
public class DocumentElementFacilityTag {

    private Integer facilityId;

    private List<String> emptyEntries;
}
