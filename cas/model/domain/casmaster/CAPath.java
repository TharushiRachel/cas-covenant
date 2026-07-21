package com.itechro.cas.model.domain.casmaster;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "CA_PATH_CONFIG")
public class CAPath {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CA_PATH_CONFIG ")
    @SequenceGenerator(name = "SEQ_CA_PATH_CONFIG ", sequenceName = "SEQ_CA_PATH_CONFIG ", allocationSize = 1)
    @Column(name = "PATH_CONFIG_ID")
    private Integer pathId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMITTEE_ID", referencedColumnName = "COMMITTEE_ID")
    private CACommittee subCommittee;

    @Column(name = "PATH_TYPE")
    private String pathType;
}
