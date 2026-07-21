package com.itechro.cas.model.domain.casv1;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Table(name = "CASV1_DIRECTORS_TABLE")
public class Director {

    @Column(name = "REF_NUM")
    private String refNo;

    @Column(name = "DIRECTOR_NAME")
    private String directorName;

    @Column(name = "AGE")
    private Number age;
}
