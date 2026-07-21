package com.itechro.cas.model.domain.facilitypaper;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "COM_FAC_CODES")
public class CustomerClassification {

    @Id
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_CODE") // references ID of parent row
    private CustomerClassification parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<CustomerClassification> children = new ArrayList<>();

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "I_TYPE")
    private String iType;

    @Column(name = "PATTERN_REJEX")
    private String patternRejex;
}
