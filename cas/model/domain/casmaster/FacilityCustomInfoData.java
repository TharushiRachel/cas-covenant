package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.facilitypaper.facility.Facility;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.io.Serializable;
@Entity
@Getter
@Setter
@Table(name = "T_CUSTOM_FACILITY_INFO")
public class FacilityCustomInfoData extends UserTrackableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FACILITY_CUSTOM_FAC_INFO")
    @SequenceGenerator(name = "SEQ_T_FACILITY_CUSTOM_FAC_INFO", sequenceName = "SEQ_T_FACILITY_CUSTOM_FAC_INFO", allocationSize = 1)
    @Column(name = "FACILITY_CUSTOM_INFO_ID")
    private Integer facilityCustomInfoDataID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_ID")
    private Facility facility;

    @Column(name = "CUSTOM_FACILITY_INFO_ID")
    private Integer cftCustomFacilityInfoID;

    @Column(name = "CUSTOM_FACILITY_INFO_CODE")
    private String customFacilityInfoCode;

    @Column(name = "CUSTOM_FACILITY_INFO_NAME")
    private String customFacilityInfoName;


    @Column(name = "CUSTOM_INFO_DATA")
    private String customInfoData;

    @Enumerated(EnumType.STRING)
    @Column(name = "MANDATORY")
    private AppsConstants.YesNo mandatory;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;
}
