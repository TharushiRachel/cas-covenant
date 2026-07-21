package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class DocStorageContentDTO extends BaseContentDTO {

    @SerializedName("DOCUMENT STORAGE ID")
    private Integer docStorageID;

    @SerializedName("DESCRIPTION")
    private String description;

    @SerializedName("FILE NAME")
    private String fileName;

    @SerializedName("LAST UPDATED DATE")
    private String lastUpdatedDateStr;

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

    public String getLastUpdatedDateStr() {
        return lastUpdatedDateStr;
    }

    public void setLastUpdatedDateStr(String lastUpdatedDateStr) {
        this.lastUpdatedDateStr = lastUpdatedDateStr;
    }
}
