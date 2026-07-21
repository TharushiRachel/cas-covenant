package com.itechro.cas.model.domain.facilitypaper;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_PURPOSE_OF_ADVANCE")
public class PurposeOfAdvance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_PURPOSE_OF_ADVANCE")
    @SequenceGenerator(name = "SEQ_T_PURPOSE_OF_ADVANCE", sequenceName = "SEQ_T_PURPOSE_OF_ADVANCE", allocationSize = 1)
    @Column(name = "PURPOSE_OF_ADVANCE_ID")
    private Integer purposeOfAdvanceID;

    @Column(name = "REF_CODE")
    private String referenceCode;

    @Column(name = "REF_DESC")
    private String referenceDescription;

    public Integer getPurposeOfAdvanceID() {
        return purposeOfAdvanceID;
    }

    public void setPurposeOfAdvanceID(Integer purposeOfAdvanceID) {
        this.purposeOfAdvanceID = purposeOfAdvanceID;
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
