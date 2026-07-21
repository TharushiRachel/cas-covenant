package com.itechro.cas.model.domain.lead;

import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "APP_CODE_DESCRIPTION")
public class AppDescriptions implements Serializable {

    @Id
    @Column(name = "ID")
    private Integer ID;

    @Column(name = "APP_ID")
    private String appId;

    @Column(name = "APP_DESCRIPTION")
    private String appDescription;

    @OneToMany(mappedBy = "appDescription", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LeadAppCode> leadAppCodes;


}
