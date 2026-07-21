package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.ApprovableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_CREDIT_FACILITY_TYPE")
public class CreditFacilityType extends ApprovableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CREDIT_FACILITY_TYPE")
    @SequenceGenerator(name = "SEQ_T_CREDIT_FACILITY_TYPE", sequenceName = "SEQ_T_CREDIT_FACILITY_TYPE", allocationSize = 1)
    @Column(name = "CREDIT_FACILITY_TYPE_ID")
    private Integer creditFacilityTypeID;

    @Column(name = "FACILITY_TYPE_NAME")
    private String facilityTypeName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getCreditFacilityTypeID() {
        return creditFacilityTypeID;
    }

    public void setCreditFacilityTypeID(Integer creditFacilityTypeID) {
        this.creditFacilityTypeID = creditFacilityTypeID;
    }

    public String getFacilityTypeName() {
        return facilityTypeName;
    }

    public void setFacilityTypeName(String facilityTypeName) {
        this.facilityTypeName = facilityTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
