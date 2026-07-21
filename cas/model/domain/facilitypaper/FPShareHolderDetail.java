package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_FP_SHARE_HOLDER_DETAIL")
public class FPShareHolderDetail extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_SHARE_HOLDER_DETAIL")
    @SequenceGenerator(name = "SEQ_T_FP_SHARE_HOLDER_DETAIL", sequenceName = "SEQ_T_FP_SHARE_HOLDER_DETAIL", allocationSize = 1)
    @Column(name = "FP_SHARE_HOLDER_DETAIL_ID")
    private Integer fpShareHolderDetailID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Column(name = "SHAREHOLDER_NAME")
    private String shareHolderName;

    @Column(name = "SHARE_HOLDING")
    private Double shareHolding;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getFpShareHolderDetailID() {
        return fpShareHolderDetailID;
    }

    public void setFpShareHolderDetailID(Integer fpShareHolderDetailID) {
        this.fpShareHolderDetailID = fpShareHolderDetailID;
    }

    public FacilityPaper getFacilityPaper() {
        return facilityPaper;
    }

    public void setFacilityPaper(FacilityPaper facilityPaper) {
        this.facilityPaper = facilityPaper;
    }

    public String getShareHolderName() {
        return shareHolderName;
    }

    public void setShareHolderName(String shareHolderName) {
        this.shareHolderName = shareHolderName;
    }

    public Double getShareHolding() {
        return shareHolding;
    }

    public void setShareHolding(Double shareHolding) {
        this.shareHolding = shareHolding;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FPShareHolderDetail{" +
                "fpShareHolderDetailID=" + fpShareHolderDetailID +
                ", facilityPaper=" + facilityPaper +
                ", shareHolderName='" + shareHolderName + '\'' +
                ", shareHolding=" + shareHolding +
                ", status=" + status +
                '}';
    }
}
