package lk.sampath.cas_covenant.entity.facilityPaper;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "T_CREDIT_FACILITY_TEMPLATE")
public class CreditFacilityTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CREDIT_FACILITY_TEMPLATE")
    @SequenceGenerator(name = "SEQ_T_CREDIT_FACILITY_TEMPLATE", sequenceName = "SEQ_T_CREDIT_FACILITY_TEMPLATE", allocationSize = 1)
    @Column(name = "CREDIT_FACILITY_TEMPLATE_ID")
    private Integer creditFacilityTemplateID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREDIT_FACILITY_TYPE_ID")
    private CreditFacilityType creditFacilityType;

    @Column(name = "CREDIT_FACILITY_NAME")
    private String creditFacilityName;
}
