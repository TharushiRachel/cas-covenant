package com.itechro.cas.model.domain.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_AF_FACILITY_VITAL_INFO")
public class AFFacilityVitalInfoData extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_F_VITAL_INFO")
    @SequenceGenerator(name = "SEQ_T_AF_F_VITAL_INFO", sequenceName = "SEQ_T_AF_F_VITAL_INFO", allocationSize = 1)
    @Column(name = "FACILITY_VITAL_INFO_ID")
    private Integer facilityVitalInfoDataID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AF_FACILITY_ID")
    private AFFacility afFacility;

    @Column(name = "CFT_VITAL_INFO_ID")
    private Integer cftVitalInfoID;

    @Column(name = "VITAL_INFO_NAME")
    private String vitalInfoName;

    @Column(name = "VITAL_INFO_DATA")
    private String vitalInfoData;

    @Enumerated(EnumType.STRING)
    @Column(name = "MANDATORY")
    private AppsConstants.YesNo mandatory;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getFacilityVitalInfoDataID() {
        return facilityVitalInfoDataID;
    }

    public void setFacilityVitalInfoDataID(Integer facilityVitalInfoDataID) {
        this.facilityVitalInfoDataID = facilityVitalInfoDataID;
    }

    public AFFacility getAfFacility() {
        return afFacility;
    }

    public void setAfFacility(AFFacility afFacility) {
        this.afFacility = afFacility;
    }

    public Integer getCftVitalInfoID() {
        return cftVitalInfoID;
    }

    public void setCftVitalInfoID(Integer cftVitalInfoID) {
        this.cftVitalInfoID = cftVitalInfoID;
    }

    public String getVitalInfoName() {
        return vitalInfoName;
    }

    public void setVitalInfoName(String vitalInfoName) {
        this.vitalInfoName = vitalInfoName;
    }

    public String getVitalInfoData() {
        return vitalInfoData;
    }

    public void setVitalInfoData(String vitalInfoData) {
        this.vitalInfoData = vitalInfoData;
    }

    public AppsConstants.YesNo getMandatory() {
        return mandatory;
    }

    public void setMandatory(AppsConstants.YesNo mandatory) {
        this.mandatory = mandatory;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
