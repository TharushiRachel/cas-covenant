package com.itechro.cas.model.dto.esg;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import com.itechro.cas.model.domain.esg.EsgDocStorage;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsgDocStorageDTO {

    private Integer esgStorageID;

    private Integer facilityPaperID;

    private Integer applicationFormID;

    private String name;

    private String description;

    private String fileName;

    private byte[] document;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    private AppsConstants.Status status;

    public EsgDocStorageDTO(EsgDocStorage esgDocStorage) {
        this.esgStorageID = esgDocStorage.getEsgStorageID();
        this.facilityPaperID = (esgDocStorage.getFacilityPaper() != null) ? esgDocStorage.getFacilityPaper().getFacilityPaperID() : null;
        this.applicationFormID = (esgDocStorage.getApplicationForm() != null) ? esgDocStorage.getApplicationForm().getApplicationFormID() : null;
        this.name = esgDocStorage.getName();
        this.description = esgDocStorage.getDescription();
        this.fileName = esgDocStorage.getFileName();
        //this.document = esgDocStorage.getDocument();
        this.createdDate = esgDocStorage.getCreatedDate();
        this.createdBy = esgDocStorage.getCreatedBy();
        this.modifiedDate = esgDocStorage.getModifiedDate();
        this.modifiedBy = esgDocStorage.getModifiedBy();
        this.status = esgDocStorage.getStatus();
    }

}
