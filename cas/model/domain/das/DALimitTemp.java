package com.itechro.cas.model.domain.das;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.ApprovableEntity;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "DA_LIMITS_TEMP")
//@AuditOverride(forClass = ApprovableEntity.class)
//@AuditOverride(forClass = UserTrackableEntity.class)
public class DALimitTemp extends ApprovableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DA_LIMITS_TEMP")
    @SequenceGenerator(name = "SEQ_DA_LIMITS_TEMP", sequenceName = "SEQ_DA_LIMITS_TEMP", allocationSize = 1)
    @Column(name = "DA_LIMITS_ID")
    private Integer daLimitsId;

    @Column(name = "DESIGNATION_ID")
    private Integer designationId;

    @Column(name = "COLUMN_ID")
    private Integer columnId;

    @Column(name = "RISK_VALUE")
    private Double riskValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Column(name = "AUTHORIZER_DISPLAY_NAME")
    private String authorizerDisplayName;

    @Column(name = "RISK_RATING")
    private String riskRating;

    @Column(name = "IS_COMMITTEE")
    private String isCommittee;

}
