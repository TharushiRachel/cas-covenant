package com.itechro.cas.model.dto.storage;

import com.itechro.cas.model.domain.storage.DocStorage;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class DocStorageDTO implements Serializable {

    private Integer docStorageID;

    private String description;

    private String fileName;

    private byte[] document;

    private String lastUpdatedDateStr;

    public DocStorageDTO() {
    }

    public DocStorageDTO(DocStorage docStorage) {
        this(docStorage, true);
    }

    public DocStorageDTO(DocStorage docStorage, boolean loadDocData) {
        this.docStorageID = docStorage.getDocStorageID();
        this.description = docStorage.getDescription();
        this.fileName = docStorage.getFileName();
        if (loadDocData) {
            this.document = docStorage.getDocument();
        }
        if (docStorage.getLastUpdatedDate() != null) {
            this.lastUpdatedDateStr = CalendarUtil.getDefaultFormattedDateTime(docStorage.getLastUpdatedDate());
        }

    }

    public Integer getDocStorageID() {
        return docStorageID;
    }

    public void setDocStorageID(Integer docStorageID) {
        this.docStorageID = docStorageID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getLastUpdatedDateStr() {
        return lastUpdatedDateStr;
    }

    public void setLastUpdatedDateStr(String lastUpdatedDateStr) {
        this.lastUpdatedDateStr = lastUpdatedDateStr;
    }

    @Override
    public String toString() {
        return "DocStorageDTO{" +
                "docStorageID=" + docStorageID +
                ", description='" + description + '\'' +
                ", fileName='" + fileName + '\'' +
                ", lastUpdatedDateStr='" + lastUpdatedDateStr + '\'' +
                '}';
    }
}
