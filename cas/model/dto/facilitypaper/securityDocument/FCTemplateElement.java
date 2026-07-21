package com.itechro.cas.model.dto.facilitypaper.securityDocument;

import com.itechro.cas.model.dto.facility.FacilityDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FCTemplateElement {

    private Integer creditFacilityTemplateID;

    private String creditFacilityName;

    private String facilityTypeName;

    private List<SDFacilityDTO> facilities;

    private List<DocumentElementDTO> documentElements;

    private List<SecurityDocumentDTO> securityDocuments;

    public List<SDFacilityDTO> getFacilities() {
        if (facilities == null){
            return new ArrayList<>();
        }
        return facilities;
    }

    public List<SecurityDocumentDTO> getSecurityDocuments() {
        if (securityDocuments == null){
            return new ArrayList<>();
        }
        return securityDocuments;
    }

    public void addFacility(SDFacilityDTO facilityDTO){
        facilities = getFacilities();
        facilities.add(facilityDTO);
    }

    public void addSecurityDocuments(List<SecurityDocumentDTO> newSecurityDocuments) {
        securityDocuments = getSecurityDocuments();
        for (SecurityDocumentDTO document : newSecurityDocuments) {
            securityDocuments.add(document);
        }
    }
}
