package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_CFT_OTHER_FACILITY_INFO")
public class CftOtherFacilityInformation extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_OTHER_FACILITY_INFO")
    @SequenceGenerator(name = "SEQ_T_OTHER_FACILITY_INFO", sequenceName = "SEQ_T_OTHER_FACILITY_INFO", allocationSize = 1)
    @Column(name = "OTHER_FACILITY_INFO_ID")
//    TODO OTHER_FACILITY_INFO_ID ==> CFT_OTHER_FACILITY_INFO_ID / cftOtherFacilityInfoID ==> cftOtherFacilityInfoID @Thilina
    private Integer cftOtherFacilityInfoID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREDIT_FACILITY_TEMPLATE_ID")
    private CreditFacilityTemplate creditFacilityTemplate;

    @Column(name = "OTHER_FACILITY_INFO_NAME")
    private String otherFacilityInfoName;

    @Column(name = "DESCRIPTION")
    private String description;

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

    public Integer getCftOtherFacilityInfoID() {
        return cftOtherFacilityInfoID;
    }

    public void setCftOtherFacilityInfoID(Integer cftOtherFacilityInfoID) {
        this.cftOtherFacilityInfoID = cftOtherFacilityInfoID;
    }

    public CreditFacilityTemplate getCreditFacilityTemplate() {
        return creditFacilityTemplate;
    }

    public void setCreditFacilityTemplate(CreditFacilityTemplate creditFacilityTemplate) {
        this.creditFacilityTemplate = creditFacilityTemplate;
    }

    public String getOtherFacilityInfoName() {
        return otherFacilityInfoName;
    }

    public void setOtherFacilityInfoName(String otherFacilityInfoName) {
        this.otherFacilityInfoName = otherFacilityInfoName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
