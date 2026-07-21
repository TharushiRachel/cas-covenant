package com.itechro.cas.model.domain.facilitypaper.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_FACILITY_PURPOSE_OF_ADVANCE")
public class FacilityPurposeOfAdvance extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_F_PURPOSE_OF_ADVANCE")
    @SequenceGenerator(name = "SEQ_T_F_PURPOSE_OF_ADVANCE", sequenceName = "SEQ_T_F_PURPOSE_OF_ADVANCE", allocationSize = 1)
    @Column(name = "FACILITY_PURPOSE_OF_ADVANCE_ID")
    private Integer facilityPurposeOfAdvanceID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_ID")
    private Facility facility;

    @Column(name = "PURPOSE_OF_ADVANCE")
    private String purposeOfAdvance;

    @Column(name = "REF_CODE")
    private String referenceCode;

    @Column(name = "REF_DESC")
    private String referenceDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getFacilityPurposeOfAdvanceID() {
        return facilityPurposeOfAdvanceID;
    }

    public void setFacilityPurposeOfAdvanceID(Integer facilityPurposeOfAdvanceID) {
        this.facilityPurposeOfAdvanceID = facilityPurposeOfAdvanceID;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public String getPurposeOfAdvance() {
        return purposeOfAdvance;
    }

    public void setPurposeOfAdvance(String purposeOfAdvance) {
        this.purposeOfAdvance = purposeOfAdvance;
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

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
