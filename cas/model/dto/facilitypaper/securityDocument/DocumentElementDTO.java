package com.itechro.cas.model.dto.facilitypaper.securityDocument;

import com.itechro.cas.model.domain.facilitypaper.securityDocument.SecurityDocumentElement;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class DocumentElementDTO implements Serializable {

    private Integer elementID;

    private String elementName;

    private String creditFacilityName;

    private String documentFileName;

    private List<DocumentElementFacilityTag> facilityTags;

    public DocumentElementDTO() {
    }

    public DocumentElementDTO(SecurityDocumentElement documentElement) {
        this.elementID = documentElement.getElementID();
        this.elementName = documentElement.getElementName();
        this.creditFacilityName = documentElement.getCreditFacilityName();
        this.documentFileName = documentElement.getDocumentFileName();
    }

    public String getDocumentFileName() {
        if (documentFileName == null){
            return "";
        }
        return documentFileName;
    }

    public List<DocumentElementFacilityTag> getFacilityTags() {
        if (facilityTags == null){
            return new ArrayList<>();
        }
        return facilityTags;
    }

    public void addFacilityTag(DocumentElementFacilityTag tag){
        facilityTags = getFacilityTags();
        facilityTags.add(tag);
    }

    public void addFacilityTags(List<DocumentElementFacilityTag> tags){
        facilityTags = getFacilityTags();

        for (DocumentElementFacilityTag tag : tags) {
            facilityTags.add(tag);
        }
    }
}
