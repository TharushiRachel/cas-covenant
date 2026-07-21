package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.casmaster.CreditFacilityTemplate;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 *
 *
 * @author tharushi
 */
@Getter
@Setter
@Entity
@Table(name = "T_CFT_CUSTOM_FACILITY_INFO")
public class CftCustomFacilityInfo extends UserTrackableEntity {//

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CUSTOM_FACILITY_INFO")
    @SequenceGenerator(name = "SEQ_T_CUSTOM_FACILITY_INFO", sequenceName = "SEQ_T_CUSTOM_FACILITY_INFO", allocationSize = 1)
    @Column(name = "CUSTOM_FACILITY_INFO_ID")
    private Integer cftCustomFacilityInfoID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREDIT_FACILITY_TEMPLATE_ID")
    private CreditFacilityTemplate creditFacilityTemplate;

    @Column(name = "CUSTOM_FACILITY_INFO_NAME")
    private String customFacilityInfoName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CUSTOM_FACILITY_INFO_CODE")
    private String customFacilityInfoCode;

    @Column(name = "FIELD_TYPE")
    private String fieldType;

    @Enumerated(EnumType.STRING)
    @Column(name = "MANDATORY")
    private AppsConstants.YesNo mandatory;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

}
