package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.CASCustomerDoc;
import com.itechro.cas.model.dto.storage.DocStorageDTO;

import java.io.Serializable;

public class CASCustomerDocDTO implements Serializable {

    private Integer fpCustomerDocID;

    private Integer casCustomerID;

    private Integer supportingDocID;

    private String documentName;

    private String supportDocDescription;

    private String supportDocumentType;

    private DocStorageDTO docStorageDTO;

    private String description;

    public CASCustomerDocDTO() {
    }

    public CASCustomerDocDTO(CASCustomerDoc CASCustomerDoc) {
        this(CASCustomerDoc, true);
    }

    public CASCustomerDocDTO(CASCustomerDoc CASCustomerDoc, boolean loadDoc) {
        this.fpCustomerDocID = CASCustomerDoc.getCasCustomerDocID();
        this.casCustomerID = CASCustomerDoc.getCASCustomer().getCasCustomerID();
        this.supportingDocID = CASCustomerDoc.getSupportingDoc().getSupportingDocID();
        this.documentName = CASCustomerDoc.getSupportingDoc().getDocumentName();
        this.supportDocDescription = CASCustomerDoc.getSupportingDoc().getDescription();
        this.supportDocumentType = CASCustomerDoc.getSupportingDoc().getSupportDocumentType();
        if (CASCustomerDoc.getDocStorage() != null) {
            this.docStorageDTO = new DocStorageDTO(CASCustomerDoc.getDocStorage(), loadDoc);
        }
        this.description = CASCustomerDoc.getDescription();
    }

    public Integer getFpCustomerDocID() {
        return fpCustomerDocID;
    }

    public void setFpCustomerDocID(Integer fpCustomerDocID) {
        this.fpCustomerDocID = fpCustomerDocID;
    }

    public Integer getCasCustomerID() {
        return casCustomerID;
    }

    public void setCasCustomerID(Integer casCustomerID) {
        this.casCustomerID = casCustomerID;
    }

    public Integer getSupportingDocID() {
        return supportingDocID;
    }

    public void setSupportingDocID(Integer supportingDocID) {
        this.supportingDocID = supportingDocID;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getSupportDocDescription() {
        return supportDocDescription;
    }

    public void setSupportDocDescription(String supportDocDescription) {
        this.supportDocDescription = supportDocDescription;
    }

    public String getSupportDocumentType() {
        return supportDocumentType;
    }

    public void setSupportDocumentType(String supportDocumentType) {
        this.supportDocumentType = supportDocumentType;
    }

    public DocStorageDTO getDocStorageDTO() {
        return docStorageDTO;
    }

    public void setDocStorageDTO(DocStorageDTO docStorageDTO) {
        this.docStorageDTO = docStorageDTO;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
