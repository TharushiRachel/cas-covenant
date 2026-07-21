package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.ApprovableEntity;


import javax.persistence.*;

@Entity
@Table(name = "T_UPC_SECTION")
public class UpcSection extends ApprovableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="SEQ_T_UPC_SECTION")
    @SequenceGenerator(name = "SEQ_T_UPC_SECTION",sequenceName = "SEQ_T_UPC_SECTION",allocationSize = 1)
    @Column(name = "UPC_SECTION_ID")
    private Integer upcSectionID;

    @Column(name = "UPC_SECTION_NAME")
    private String upcSectionName;

    @Column(name = "UPC_SECTION_DESCRIPTION")
    private String upcSectionDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getUpcSectionID() {
        return upcSectionID;
    }

    public void setUpcSectionID(Integer upcSectionID) {
        this.upcSectionID = upcSectionID;
    }

    public String getUpcSectionName() {
        return upcSectionName;
    }

    public void setUpcSectionName(String upcSectionName) {
        this.upcSectionName = upcSectionName;
    }

    public String getUpcSectionDescription() {
        return upcSectionDescription;
    }

    public void setUpcSectionDescription(String upcSectionDescription) {
        this.upcSectionDescription = upcSectionDescription;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }






}
