package com.itechro.cas.model.domain.casv1;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;


@Getter
@Setter
@Table(name = "CASV1_FACILITY_TABLE")
public class FacilityTable {

    @Column(name = "NO")
    private Integer no;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;
}
