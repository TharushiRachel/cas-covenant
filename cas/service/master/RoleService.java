package com.itechro.cas.service.master;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.commons.constants.DomainConstants.MasterDataApproveStatus;
import com.itechro.cas.dao.master.PrivilegeDao;
import com.itechro.cas.dao.master.RoleDao;
import com.itechro.cas.dao.master.jdbc.RoleJDBCDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.domain.master.Role;
import com.itechro.cas.model.dto.audit.WebAuditDTO;
import com.itechro.cas.model.dto.casmaster.ApproveRejectRQ;
import com.itechro.cas.model.dto.casmaster.CreditFacilityTypeDTO;
import com.itechro.cas.model.dto.master.PrivilegeDTO;
import com.itechro.cas.model.dto.master.RoleDTO;
import com.itechro.cas.model.dto.master.RoleSearchRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.security.SecurityService;
import com.itechro.cas.service.audit.WebAuditService;
import com.itechro.cas.util.WebAuditUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RoleService {

    private static Logger LOG = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private PrivilegeDao privilegeDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleJDBCDao roleJDBCDao;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private WebAuditService webAuditService;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public RoleDTO saveOrUpdateRole(RoleDTO roleUpdateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Role update : {} : {}", roleUpdateDTO, credentialsDTO.getUserID());
        Date date = new Date();
        Role role = null;
        Role roleOld = null;
        RoleDTO previousDTO = null;
        boolean isNewRole = roleUpdateDTO.getRoleID() == null;

        if (!isNewRole) {
            roleOld = roleDao.getOne(roleUpdateDTO.getRoleID());
        }

        if (isNewRole || !roleUpdateDTO.getRoleName().equals(roleOld.getRoleName())) {
            List<Role> rolesInName = roleDao.findAllByRoleNameAndApproveStatusAndStatus(roleUpdateDTO.getRoleName(),
                    MasterDataApproveStatus.APPROVED, AppsConstants.Status.ACT);

            if (rolesInName != null && !rolesInName.isEmpty()) {
                LOG.error("ERROR : Role exists : {} : {}", roleUpdateDTO, credentialsDTO.getUserID());
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_ROLE_ALREADY_EXISTS);
            }
        }

        if (isNewRole || !roleUpdateDTO.getUpmPrivilegeCode().equals(roleOld.getUpmPrivilegeCode())) {
            List<Role> rolesInUpmPrivilegeCode = roleDao.findAllByUpmPrivilegeCodeAndApproveStatusAndStatus(roleUpdateDTO.getUpmPrivilegeCode(),
                    MasterDataApproveStatus.APPROVED, AppsConstants.Status.ACT);

            if (rolesInUpmPrivilegeCode != null && !rolesInUpmPrivilegeCode.isEmpty()) {
                LOG.error("ERROR : Role exists : {} : {}", roleUpdateDTO, credentialsDTO.getUserID());
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_ROLE_ALREADY_EXISTS);
            }
        }

        if (!isNewRole) {
            previousDTO = new RoleDTO(roleOld);
            //TODO: approve process need to update
            if (roleOld.getStatus() == AppsConstants.Status.INA) {
                LOG.error("ERROR : Inactive record cannot modified {}", roleUpdateDTO);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_UPC_TEMPLATE_INACTIVE_RECODE_CANNOT_MODIFIED);
            }
            if (roleOld.getApproveStatus() == MasterDataApproveStatus.APPROVED) {
                boolean hasAlreadyPendingRecords = this.roleJDBCDao.getPendingRoleCount(roleUpdateDTO.getRoleID()) > 0;
                if (hasAlreadyPendingRecords) {
                    LOG.error("ERROR : Already pending modified recode is there {}", roleUpdateDTO);
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_ROLE_PENDING_APPROVE_RECORD_EXIST);
                }
                role = new Role();
                role.setParentRecordID(roleUpdateDTO.getRoleID());
                role.setCreatedBy(credentialsDTO.getUserName());
                role.setCreatedDate(date);
                role.setApproveStatus(MasterDataApproveStatus.PENDING);
                role.getPrivileges().addAll(roleOld.getPrivileges());
            } else {
                role = roleOld;
                role.setModifiedBy(credentialsDTO.getUserName());
                role.setModifiedDate(date);
            }
        } else {
            role = new Role();
            role.setCreatedBy(credentialsDTO.getUserName());
            role.setCreatedDate(date);
            role.setApproveStatus(MasterDataApproveStatus.PENDING);
        }

        role.setRoleName(roleUpdateDTO.getRoleName());
        role.setUpmPrivilegeCode(roleUpdateDTO.getUpmPrivilegeCode());
        role.setStatus(roleUpdateDTO.getStatus());

        if (isNewRole) {
            role.getPrivileges().addAll(privilegeDao.findAllById(roleUpdateDTO.getPrivileges()));
        } else {
            if (!roleUpdateDTO.getDeletedPrivileges().isEmpty()) {
                role.removePrivileges(privilegeDao.findAllById(roleUpdateDTO.getDeletedPrivileges()));
            }
            if (!roleUpdateDTO.getAddedPrivileges().isEmpty()) {
                role.getPrivileges().addAll(privilegeDao.findAllById(roleUpdateDTO.getAddedPrivileges()));
            }
        }
        role = roleDao.saveAndFlush(role);

        //audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructRoleAudit(new RoleDTO(role), previousDTO, credentialsDTO, date, isNewRole);
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        this.securityService.destroyUserCache();

        LOG.info("END : Role update : {} : {}", roleUpdateDTO, credentialsDTO.getUserID());
        return new RoleDTO(role);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RoleDTO getRoleUpdateDTO(Integer roleID) {
        return new RoleDTO(roleDao.getOne(roleID));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<RoleDTO> getPagedRoles(RoleSearchRQ searchRQ) {
        return roleJDBCDao.getPagedRoles(searchRQ);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<RoleDTO> getRoles(AppsConstants.Status status) {
        return roleJDBCDao.getRoles(status);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, List<PrivilegeDTO>> getCategoryWisePrivileges(AppsConstants.Status status, DomainConstants.PrivilegeModule privilegeModule) {
        Map<String, List<PrivilegeDTO>> result = new HashMap<>();
        List<PrivilegeDTO> privileges = roleJDBCDao.getPrivileges(status, privilegeModule);
        for (PrivilegeDTO privilegeDTO : privileges) {
            result.putIfAbsent(privilegeDTO.getCategory(), new ArrayList<>());
            result.get(privilegeDTO.getCategory()).add(privilegeDTO);
        }
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public RoleDTO approveRoleDTO(ApproveRejectRQ updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Role update : {} : {}", updateDTO, credentialsDTO.getUserID());
        Date date = new Date();
        Role role = null;

        role = roleDao.getOne(updateDTO.getApproveRejectDataID());
        role.setModifiedBy(credentialsDTO.getUserName());
        role.setModifiedDate(date);
        role.setApproveStatus(updateDTO.getApproveStatus());
        role.setApprovedBy(credentialsDTO.getUserName());
        role.setApprovedDate(date);
        if (updateDTO.getApproveStatus() == MasterDataApproveStatus.APPROVED) {
            role.setStatus(AppsConstants.Status.ACT);
            if (role.getParentRecordID() != null) {
                Role parentRole = roleDao.getOne(role.getParentRecordID());
                parentRole.setStatus(AppsConstants.Status.INA);
                parentRole.setModifiedDate(date);
                parentRole.setModifiedBy(credentialsDTO.getUserName());
                roleDao.saveAndFlush(parentRole);
            }
        } else {
            role.setStatus(AppsConstants.Status.INA);
        }
        role = roleDao.saveAndFlush(role);

        //Audit
        //TODO

        LOG.info("END : Role update : {} : {}", updateDTO, credentialsDTO.getUserID());

        return new RoleDTO(role);
    }
}
