package com.itechro.cas.controller.master;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.casmaster.ApproveRejectRQ;
import com.itechro.cas.model.dto.master.PrivilegeDTO;
import com.itechro.cas.model.dto.master.RoleDTO;
import com.itechro.cas.model.dto.master.RoleSearchRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.casmaster.MasterDataService;
import com.itechro.cas.service.master.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "${api.prefix}/role")
public class RoleController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private MasterDataService masterDataService;

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedRoles", headers = "Accept=application/json", method = RequestMethod.POST)
//    @PreAuthorize("hasAuthority('" + PrivilegeCode.+ "')")
    public ResponseEntity<Page<RoleDTO>> getPagedRoles(@RequestBody RoleSearchRQ roleSearchRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Search roles : {} , user {}", roleSearchRQ, credentialsDTO.getUserID());

        Page<RoleDTO> pagedRoles = this.roleService.getPagedRoles(roleSearchRQ);

        LOG.info("END : Search roles : {} , user {}", roleSearchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(pagedRoles, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getRoleUpdateDTO/{roleID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<RoleDTO> getRoleUpdateDTO(@PathVariable Integer roleID) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get role : {}, user {} ", roleID, credentialsDTO.getUserID());

        RoleDTO roleUpdateDTO = this.roleService.getRoleUpdateDTO(roleID);

        LOG.info("END : get role : {}, user {} ", roleID, credentialsDTO.getUserID());

        return new ResponseEntity<>(roleUpdateDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateRole", headers = "Accept=application/json", method = RequestMethod.POST)
//    @PreAuthorize("hasAuthority('" + PrivilegeCode.+ "')")
    public ResponseEntity<RoleDTO> saveOrUpdateRole(@RequestBody RoleDTO roleUpdateDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : save/update role : {}, user {}", roleUpdateDTO, credentialsDTO.getUserID());

        RoleDTO roleUpdateDTOResponse = this.roleService.saveOrUpdateRole(roleUpdateDTO, credentialsDTO);

        LOG.info("END : save/update role : {}, user {}", roleUpdateDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(roleUpdateDTOResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getSystemPrivileges", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<PrivilegeDTO>>> getSystemPrivileges() throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get System Privileges for user {}", credentialsDTO.getUserID());

        Map<String, List<PrivilegeDTO>> categoryWisePrivileges = this.roleService.getCategoryWisePrivileges(null, null);

        LOG.info("END : System Privileges for user {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(categoryWisePrivileges, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getRoles", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<RoleDTO>> getRoles() throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get roles by user {}", credentialsDTO.getUserID());

        List<RoleDTO> roles = this.roleService.getRoles(null);

        LOG.info("END : get roles by user {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
    @ResponseExceptionHandler
    @RequestMapping(value = "/approveRoleDTO",headers ="Accept=application/json",method = RequestMethod.POST)
    public ResponseEntity<RoleDTO> approveRoleDTO(@RequestBody ApproveRejectRQ approveRejectRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Approve or Reject Role  : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        RoleDTO approveRejectResponse = masterDataService.approveRoleDTO(approveRejectRQ,credentialsDTO);

        LOG.info("END : Approve or Reject Role : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(approveRejectResponse,HttpStatus.OK);
    }

}
