package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;

import javax.persistence.*;

@Entity
@Table(name = "T_FP_COMPANY_ROA")
public class FPCompanyRoa extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_COMPANY_ROA")
    @SequenceGenerator(name = "SEQ_T_FP_COMPANY_ROA", sequenceName = "SEQ_T_FP_COMPANY_ROA", allocationSize = 1)
    @Column(name = "FP_COMPANY_ROA_ID")
    private Integer fpCompanyRoaID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ROA_COMMENT")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getFpCompanyRoaID() {
        return fpCompanyRoaID;
    }

    public void setFpCompanyRoaID(Integer fpCompanyRoaID) {
        this.fpCompanyRoaID = fpCompanyRoaID;
    }

    public FacilityPaper getFacilityPaper() {
        return facilityPaper;
    }

    public void setFacilityPaper(FacilityPaper facilityPaper) {
        this.facilityPaper = facilityPaper;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
