package lk.sampath.cas_covenant.entity.facilityPaper;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "T_CREDIT_FACILITY_TYPE")
public class CreditFacilityType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CREDIT_FACILITY_TYPE")
    @SequenceGenerator(name = "SEQ_T_CREDIT_FACILITY_TYPE", sequenceName = "SEQ_T_CREDIT_FACILITY_TYPE", allocationSize = 1)
    @Column(name = "CREDIT_FACILITY_TYPE_ID")
    private Integer creditFacilityTypeID;

    @Column(name = "FACILITY_TYPE_NAME")
    private String facilityTypeName;

}
