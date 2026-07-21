package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_CFT_VITAL_INFO")
public class CftVitalInfo extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CFT_VITAL_INFO")
    @SequenceGenerator(name = "SEQ_T_CFT_VITAL_INFO", sequenceName = "SEQ_T_CFT_VITAL_INFO", allocationSize = 1)
    @Column(name = "CFT_VITAL_INFO_ID")
    private Integer cftVitalInfoID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREDIT_FACILITY_TEMPLATE_ID")
    private CreditFacilityTemplate creditFacilityTemplate;

    @Column(name = "VITAL_INFO_NAME")
    private String vitalInfoName;

    @Enumerated(EnumType.STRING)
    @Column(name = "MANDATORY")
    private AppsConstants.YesNo mandatory;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getCftVitalInfoID() {
        return cftVitalInfoID;
    }

    public void setCftVitalInfoID(Integer cftVitalInfoID) {
        this.cftVitalInfoID = cftVitalInfoID;
    }

    public CreditFacilityTemplate getCreditFacilityTemplate() {
        return creditFacilityTemplate;
    }

    public void setCreditFacilityTemplate(CreditFacilityTemplate creditFacilityTemplate) {
        this.creditFacilityTemplate = creditFacilityTemplate;
    }

    public String getVitalInfoName() {
        return vitalInfoName;
    }

    public void setVitalInfoName(String vitalInfoName) {
        this.vitalInfoName = vitalInfoName;
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
