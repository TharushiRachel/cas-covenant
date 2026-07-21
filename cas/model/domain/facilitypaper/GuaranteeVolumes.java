package com.itechro.cas.model.domain.facilitypaper;


import com.itechro.cas.model.domain.common.UserTrackableEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@Table(name = "GUARANTEE_VOLUMES")
public class GuaranteeVolumes extends UserTrackableEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_F_GUARANTEE_VOLUMES")
    @SequenceGenerator(name = "SEQ_F_GUARANTEE_VOLUMES", sequenceName = "SEQ_F_GUARANTEE_VOLUMES", allocationSize = 1)
    @Column(name = "ID")
    private Integer Id;

    @Column(name = "FACILITY_PAPER_ID")
    private Integer facilityPaperId;
    @Column(name = "CURRENCY")
    private String curency;
    @Column(name = "VOLUME")
    private String volume;
    @Column(name = "YEAR")
    private String year;

}
