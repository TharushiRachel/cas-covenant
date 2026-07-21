package com.itechro.cas.model.domain.master;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.ApprovableEntity;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "T_ROLE")
public class Role extends ApprovableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_ROLE")
    @SequenceGenerator(name = "SEQ_T_ROLE", sequenceName = "SEQ_T_ROLE", allocationSize = 1)
    @Column(name = "ROLE_ID")
    private Integer roleID;

    @Column(name = "ROLE_NAME")
    private String roleName;

    @Column(name = "UPM_PRIVILAGE_CODE")
    private String upmPrivilegeCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "T_ROLE_PRIVILEGE",
            joinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "PRIVILEGE_ID", referencedColumnName = "PRIVILEGE_ID")})
    private Set<Privilege> privileges;

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUpmPrivilegeCode() {
        return upmPrivilegeCode;
    }

    public void setUpmPrivilegeCode(String upmPrivilegeCode) {
        this.upmPrivilegeCode = upmPrivilegeCode;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public Set<Privilege> getPrivileges() {
        if (privileges == null) {
            this.privileges = new HashSet<>();
        }
        return privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    public void removePrivileges(List<Privilege> removeSet) {
        if (removeSet != null && removeSet.size() > 0) {
            privileges.removeAll(removeSet);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return getRoleName() != null ? getRoleName().equals(role.getRoleName()) : role.getRoleName() == null;
    }

    @Override
    public int hashCode() {
        return getRoleName() != null ? getRoleName().hashCode() : 0;
    }
}
