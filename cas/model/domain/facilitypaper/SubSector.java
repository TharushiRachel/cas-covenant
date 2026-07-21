package com.itechro.cas.model.domain.facilitypaper;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_SUB_SECTOR")
public class SubSector implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_SUB_SECTOR")
    @SequenceGenerator(name = "SEQ_T_SUB_SECTOR", sequenceName = "SEQ_T_SUB_SECTOR", allocationSize = 1)
    @Column(name = "SUB_SECTOR_ID")
    private Integer subSectorID;

    @Column(name = "SECTRO_ID")
    private Sector sector;

    @Column(name = "LONG_REF_CODE")
    private String longRefCode;

    @Column(name = "REF_DESC")
    private String referenceDescription;

    public Integer getSubSectorID() {
        return subSectorID;
    }

    public void setSubSectorID(Integer subSectorID) {
        this.subSectorID = subSectorID;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
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
