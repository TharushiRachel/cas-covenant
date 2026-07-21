package lk.sampath.cas_covenant.entity.facilityPaper;

import jakarta.persistence.*;
import lk.sampath.cas_covenant.entity.common.UserTrackableEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "T_FACILITY")
public class Facility extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FACILITY")
    @SequenceGenerator(name = "SEQ_T_FACILITY", sequenceName = "SEQ_T_FACILITY", allocationSize = 1)
    @Column(name = "FACILITY_ID")
    private Integer facilityID;

    @Column(name = "FACILITY_REF_CODE")
    private String facilityRefCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREDIT_FACILITY_TEMPLATE_ID")
    private CreditFacilityTemplate creditFacilityTemplate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Column(name = "FACILITY_CURRENCY")
    private String facilityCurrency;

    @Column(name = "FACILITY_AMOUNT")
    private BigDecimal facilityAmount;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;
}
