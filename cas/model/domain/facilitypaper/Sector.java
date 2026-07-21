package com.itechro.cas.model.domain.facilitypaper;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_SECTOR")
public class Sector implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_SECTOR")
    @SequenceGenerator(name = "SEQ_T_SECTOR", sequenceName = "SEQ_T_SECTOR", allocationSize = 1)
    @Column(name = "SECTOR_ID")
    private Integer sectorID;

    @Column(name = "REF_CODE")
    private String referenceCode;

    @Column(name = "REF_DESC")
    private String referenceDescription;

    public Integer getSectorID() {
        return sectorID;
    }

    public void setSectorID(Integer sectorID) {
        this.sectorID = sectorID;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getReferenceDescription() {
        return referenceDescription;
    }

    public void setReferenceDescription(String referenceDescription) {
        this.referenceDescription = referenceDescription;
    }
}
