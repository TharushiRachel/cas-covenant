package com.itechro.cas.model.dto.facility;

import java.io.Serializable;

public class SubSectorDTO implements Serializable {

    private Integer subSectorID;

    private SectorDTO sectorDTO;

    private Integer sectorID;

    private String longRefCode;

    private String referenceDescription;

    public Integer getSubSectorID() {
        return subSectorID;
    }

    public void setSubSectorID(Integer subSectorID) {
        this.subSectorID = subSectorID;
    }

    public SectorDTO getSectorDTO() {
        return sectorDTO;
    }

    public void setSectorDTO(SectorDTO sectorDTO) {
        this.sectorDTO = sectorDTO;
    }

    public Integer getSectorID() {
        return sectorID;
    }

    public void setSectorID(Integer sectorID) {
        this.sectorID = sectorID;
    }

    public String getLongRefCode() {
        return longRefCode;
    }

    public void setLongRefCode(String longRefCode) {
        this.longRefCode = longRefCode;
    }

    public String getReferenceDescription() {
        return referenceDescription;
    }

    public void setReferenceDescription(String referenceDescription) {
        this.referenceDescription = referenceDescription;
    }
}
