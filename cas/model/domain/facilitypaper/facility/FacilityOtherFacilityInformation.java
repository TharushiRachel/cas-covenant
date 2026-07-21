package com.itechro.cas.model.domain.facilitypaper.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_FACILITY_OTHER_FAC_INFO")
public class FacilityOtherFacilityInformation extends UserTrackableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FACILITY_OTHER_FAC_INFO")
    @SequenceGenerator(name = "SEQ_T_FACILITY_OTHER_FAC_INFO", sequenceName = "SEQ_T_FACILITY_OTHER_FAC_INFO", allocationSize = 1)
    @Column(name = "FACILITY_OTHER_FAC_INFO_ID")
    private Integer facilityOtherFacilityInformationID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_ID")
    private Facility facility;

    @Column(name = "CFT_OTHER_FACILITY_INFO_ID")
    private Integer cftOtherFacilityInfoID;

    @Column(name = "OTHER_FACILITY_INFO_NAME")
    private String otherFacilityInfoName;

    @Column(name = "OTHER_INFO_DATA")
    private String otherInfoData;

    @Column(name = "OTHER_FACILITY_INFO_CODE")
    private String otherFacilityInfoCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "FIELD_TYPE")
    private DomainConstants.InputFieldValueType otherFacilityInfoFieldType;

    @Column(name = "DEFAULT_VALUE")
    private String defaultValue;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "MANDATORY")
    private AppsConstants.YesNo mandatory;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getFacilityOtherFacilityInformationID() {
        return facilityOtherFacilityInformationID;
    }

    public void setFacilityOtherFacilityInformationID(Integer facilityOtherFacilityInformationID) {
        this.facilityOtherFacilityInformationID = facilityOtherFacilityInformationID;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Integer getCftOtherFacilityInfoID() {
        return cftOtherFacilityInfoID;
    }

    public void setCftOtherFacilityInfoID(Integer cftOtherFacilityInfoID) {
        this.cftOtherFacilityInfoID = cftOtherFacilityInfoID;
    }

    public String getOtherFacilityInfoName() {
        return otherFacilityInfoName;
    }

    public void setOtherFacilityInfoName(String otherFacilityInfoName) {
        this.otherFacilityInfoName = otherFacilityInfoName;
    }

    public String getOtherInfoData() {
        return otherInfoData;
    }

    public void setOtherInfoData(String otherInfoData) {
        this.otherInfoData = otherInfoData;
    }

    public String getOtherFacilityInfoCode() {
        return otherFacilityInfoCode;
    }

    public void setOtherFacilityInfoCode(String otherFacilityInfoCode) {
        this.otherFacilityInfoCode = otherFacilityInfoCode;
    }

    public DomainConstants.InputFieldValueType getOtherFacilityInfoFieldType() {
        return otherFacilityInfoFieldType;
    }

    public void setOtherFacilityInfoFieldType(DomainConstants.InputFieldValueType otherFacilityInfoFieldType) {
        this.otherFacilityInfoFieldType = otherFacilityInfoFieldType;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public AppsConstants.YesNo getMandatory() {
        return mandatory;
    }

    public void setMandatory(AppsConstants.YesNo mandatory) {
        this.mandatory = mandatory;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

}
