package com.itechro.cas.dao.master;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.master.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {

    public Role getRoleByRoleName(String roleName);

    public Role getRoleByRoleNameAndStatus(String roleName, AppsConstants.Status status);

    public List<Role> findAllByRoleNameAndApproveStatusAndStatus(String roleName, DomainConstants.MasterDataApproveStatus approveStatus, AppsConstants.Status status);

    public List<Role> findAllByUpmPrivilegeCodeAndApproveStatusAndStatus(String upmPrivilegeCode, DomainConstants.MasterDataApproveStatus approveStatus, AppsConstants.Status status);
}
