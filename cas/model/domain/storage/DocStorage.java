package com.itechro.cas.model.domain.storage;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="T_DOCUMENT_STORAGE")
public class DocStorage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_DOCUMENT_STORAGE")
    @SequenceGenerator(name = "SEQ_T_DOCUMENT_STORAGE", sequenceName = "SEQ_T_DOCUMENT_STORAGE", allocationSize = 1)
    @Column(name = "DOCUMENT_STORAGE_ID")
    private Integer docStorageID;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "DOCUMENT_BYTE")
    private byte[] document;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATED_DATE")
    private Date lastUpdatedDate;


    public Integer getDocStorageID() {
        return docStorageID;
    }

    public void setDocStorageID(Integer docStorageID) {
        this.docStorageID = docStorageID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public String toString() {
        return "DocStorage{" +
                "docStorageID=" + docStorageID +
                ", description='" + description + '\'' +
                ", fileName='" + fileName + '\'' +
                ", lastUpdatedDate=" + lastUpdatedDate +
                '}';
    }
}
