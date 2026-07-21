package com.itechro.cas.model.domain.master;

import com.itechro.cas.commons.constants.AppsConstants;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "T_PRIVILEGE")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_PRIVILEGE")
    @SequenceGenerator(name = "SEQ_T_PRIVILEGE", sequenceName = "SEQ_T_PRIVILEGE", allocationSize = 1)
    @Column(name = "PRIVILEGE_ID")
    private Integer privilegeID;

    @Column(name = "PRIVILEGE_NAME")
    private String privilegeName;

    @Column(name = "PRIVILEGE_CODE")
    private String code;

    @Column(name = "BEHAVIOUR_DESCRIPTION")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRIVILEGE_CATEGORY_ID")
    private PrivilegeCategory privilegeCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getPrivilegeID() {
        return privilegeID;
    }

    public void setPrivilegeID(Integer privilegeID) {
        this.privilegeID = privilegeID;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PrivilegeCategory getPrivilegeCategory() {
        return privilegeCategory;
    }

    public void setPrivilegeCategory(PrivilegeCategory privilegeCategory) {
        this.privilegeCategory = privilegeCategory;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Privilege privilege = (Privilege) o;

        return getCode() != null ? getCode().equals(privilege.getCode()) : privilege.getCode() == null;
    }

    @Override
    public int hashCode() {
        return getCode() != null ? getCode().hashCode() : 0;
    }
}
