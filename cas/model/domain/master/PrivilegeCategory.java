package com.itechro.cas.model.domain.master;

import javax.persistence.*;

@Entity
@Table(name = "T_PRIVILEGE_CATEGORY")
public class PrivilegeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_PRIVILEGE_CATEGORY")
    @SequenceGenerator(name = "SEQ_T_PRIVILEGE_CATEGORY", sequenceName = "SEQ_T_PRIVILEGE_CATEGORY", allocationSize = 1)
    @Column(name = "PRIVILEGE_CATEGORY_ID")
    private Integer privilegeID;

    @Column(name = "CATEGORY")
    private String category;

    public Integer getPrivilegeID() {
        return privilegeID;
    }

    public void setPrivilegeID(Integer privilegeID) {
        this.privilegeID = privilegeID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
