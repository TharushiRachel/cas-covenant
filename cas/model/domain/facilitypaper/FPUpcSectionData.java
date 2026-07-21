package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.casmaster.UpcSection;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;

@Entity
@Table(name = "T_FP_UPC_SECTION_DATA")
public class FPUpcSectionData extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_T_FP_UPC_SECTION_DATA")
    @SequenceGenerator(name = "SEQ_T_FP_UPC_SECTION_DATA",sequenceName = "SEQ_T_FP_UPC_SECTION_DATA",allocationSize = 1)
    @Column(name = "FP_UPC_SECTION_DATA_ID")
    private Integer fpUpcSectionDataID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPC_SECTION_ID")
    private UpcSection upcSection;

    @Column(name = "PARENT_SECTION_ID")
    private Integer parentSectionID;

    @Column(name = "SECTION_LEVEL")
    private Integer sectionLevel;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @Column(name = "DATA")
    private String data;

    @Column(name = "MODIFIED_USER_DISPLAY_NAME")
    private String modifiedUserDisplayName;

    @Column(name = "SECTION_COMMENT")
    private String comment;
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getFpUpcSectionDataID() {
        return fpUpcSectionDataID;
    }

    public void setFpUpcSectionDataID(Integer fpUpcSectionDataID) {
        this.fpUpcSectionDataID = fpUpcSectionDataID;
    }

    public FacilityPaper getFacilityPaper() {
        return facilityPaper;
    }

    public void setFacilityPaper(FacilityPaper facilityPaper) {
        this.facilityPaper = facilityPaper;
    }

    public UpcSection getUpcSection() {
        return upcSection;
    }

    public void setUpcSection(UpcSection upcSection) {
        this.upcSection = upcSection;
    }

    public Integer getParentSectionID() {
        return parentSectionID;
    }

    public void setParentSectionID(Integer parentSectionID) {
        this.parentSectionID = parentSectionID;
    }

    public Integer getSectionLevel() {
        return sectionLevel;
    }

    public void setSectionLevel(Integer sectionLevel) {
        this.sectionLevel = sectionLevel;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getModifiedUserDisplayName() {
        return modifiedUserDisplayName;
    }

    public void setModifiedUserDisplayName(String modifiedUserDisplayName) {
        this.modifiedUserDisplayName = modifiedUserDisplayName;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
