package com.itechro.cas.model.domain.covenant;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "T_nc_facility_covenants")
@Getter
@Setter
public class ExistingFacilityCovenants {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NC_FACILITY_COVENANTS")
    @SequenceGenerator(name = "SEQ_NC_FACILITY_COVENANTS", sequenceName = "SEQ_NC_FACILITY_COVENANTS", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "acct_id")
    private String acctId;

    @Column(name = "facility_id")
    private Integer facilityId;

    @Column(name = "facility_paper_id")
    private Integer facilityPaperId;

}
