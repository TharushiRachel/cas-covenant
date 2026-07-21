package com.itechro.cas.controller.casmaster;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.casmaster.*;
import com.itechro.cas.model.dto.facilitypaper.facilitypaperreview.FPReviewSummarySearchRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.casmaster.MasterDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/workflow")
public class WorkflowTemplateController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(WorkflowTemplateController.class);

    @Autowired
    private MasterDataService masterDataService;

    @ResponseExceptionHandler
    //@PreAuthorize("hasAuthority('" + PrivilegeCode.SYSTEM.ICAS_SETTING_UPM_GROUP_VIEW+ "')")
    @RequestMapping(value = "getPagedUPMGroup", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<UpmGroupDTO>> getPagedUpmGroupData(@RequestBody UpmGroupSearchRQ searchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search UPM group : {} , user {}", searchRQ, credentialsDTO.getUserID());

        Page<UpmGroupDTO> pageDataResult = masterDataService.getPagedUpmGroupDTO(searchRQ);

        LOG.info("START : Search UPM group : {} , user {}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(pageDataResult, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getUPMGroupDTO/{upmGroupID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<UpmGroupDTO> getUPMGroupDTOByID(@PathVariable Integer upmGroupID) throws Exception {

        LOG.info("START : Get UPM group dto by ID: {}", upmGroupID);

        UpmGroupDTO dataDTO = this.masterDataService.getUpmGroupByID(upmGroupID);

        LOG.info("END : Get UPM group dto By ID : {}", upmGroupID);

        return new ResponseEntity<>(dataDTO, HttpStatus.OK);

    }

    @ResponseExceptionHandler
    //@PreAuthorize("hasAuthority('" + PrivilegeCode.SYSTEM.ICAS_SETTING_UPM_GROUP_ADD_EDIT+ "')")
    @RequestMapping(value = "/saveOrUpdateUPMGroup", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<UpmGroupDTO> saveOrUpdateUPMGroup(@RequestBody UpmGroupDTO updateDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save/update Upm Group : {} by user {}", updateDTO, credentialsDTO.getUserID());

        UpmGroupDTO updatedDTO = this.masterDataService.saveOrUpdateUpmGroupDTO(updateDTO, credentialsDTO);

        LOG.info("END : Save/update Upm Group: {} by user {}", updateDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    //@PreAuthorize("hasAuthority('" + PrivilegeCode.SYSTEM.ICAS_SETTING_WORKFLOW_TEMPLATE_VIEW+ "')")
    @RequestMapping(value = "getPagedWorkflowTemplate", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<WorkFlowTemplateDTO>> getPagedWorkflowTemplate(@RequestBody WorkFlowTemplateSearchRQ searchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search workflow template : {} , user {}", searchRQ, credentialsDTO.getUserID());

        Page<WorkFlowTemplateDTO> pageDataResult = masterDataService.getPagedWorkFlowTemplateDTO(searchRQ);

        LOG.info("START : Search workflow template : {} , user {}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(pageDataResult, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getWorkflowTemplate/{templateID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<WorkFlowTemplateDTO> getWorkflowTemplateByID(@PathVariable Integer templateID) throws Exception {

        LOG.info("START : Get workflow template dto by ID: {}", templateID);

        WorkFlowTemplateDTO dataDTO = this.masterDataService.getWorkFlowTemplateByID(templateID);

        LOG.info("END : Get workflow template dto By ID : {}", templateID);

        return new ResponseEntity<>(dataDTO, HttpStatus.OK);

    }

    @ResponseExceptionHandler
    //@PreAuthorize("hasAuthority('" + PrivilegeCode.SYSTEM.ICAS_SETTING_WORKFLOW_TEMPLATE_ADD_EDIT+ "')")
    @RequestMapping(value = "/saveOrUpdateWorkflowTemplate", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<WorkFlowTemplateDTO> saveOrUpdateWorkflowTemplate(@RequestBody WorkFlowTemplateDTO updateDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save/update workflow template : {} by user {}", updateDTO, credentialsDTO.getUserID());

        WorkFlowTemplateDTO updatedDTO = this.masterDataService.saveOrUpdateWorkFlowTemplateDTO(updateDTO, credentialsDTO);

        LOG.info("END : Save/update workflow template : {} by user {}", updateDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/approveOrRejectWorkflowTemplate", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<WorkFlowTemplateDTO> approveOrRejectCreditFacilityType(@RequestBody ApproveRejectRQ approveRejectRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Aprrove or Reject workflow template  : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        WorkFlowTemplateDTO approveRejectResponse = masterDataService.approveWorkFlowTemplateDTO(approveRejectRQ, credentialsDTO);

        LOG.info("END : Aprrove or Reject workflow template : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(approveRejectResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    //@PreAuthorize("hasAuthority('" + PrivilegeCode.SYSTEM.ICAS_SETTING_UPM_GROUP_APPROVE+ "')")
    @RequestMapping(value = "/approveOrRejectUPMGroup", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<UpmGroupDTO> approveOrRejectUPMGroup(@RequestBody ApproveRejectRQ approveRejectRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Aprrove or Reject UPM Group  : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        UpmGroupDTO approveRejectResponse = masterDataService.approveUpmGroupDTO(approveRejectRQ, credentialsDTO);

        LOG.info("END : Aprrove or Reject UPM Group : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(approveRejectResponse, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getAllApprovedUPMGroups", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<UpmGroupDTO>> getAllApprovedUPMGroups() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get approved Upm Group List, user {}", credentialsDTO.getUserID());

        List<UpmGroupDTO> approvedSupportingDocList = masterDataService.getAllApprovedUPMGroups();

        LOG.info("END : Get approved Upm Group List :size of list:{} , user {}", approvedSupportingDocList.size(), credentialsDTO.getUserID());

        return new ResponseEntity<>(approvedSupportingDocList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAllowApprovedUPMGroupsForLoginUser", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<UpmGroupDTO>> getAllowApprovedUPMGroupsForLoginUser(@RequestBody FPReviewSummarySearchRQ searchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get All approved Upm Group List for Login User, user {}", credentialsDTO.getUserID());

        List<UpmGroupDTO> approvedSupportingDocList = masterDataService.getAllowApprovedUPMGroupsForLoginUser(searchRQ);

        LOG.info("END : Get All approved Upm Group List for Login User:{} , user {}", approvedSupportingDocList.size(), credentialsDTO.getUserID());

        return new ResponseEntity<>(approvedSupportingDocList, HttpStatus.OK);
    }

}
