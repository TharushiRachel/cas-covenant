package com.itechro.cas.controller.master;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.casmaster.*;
import com.itechro.cas.model.dto.facility.PurposeOfAdvancedDTO;
import com.itechro.cas.model.dto.facility.SectorDTO;
import com.itechro.cas.model.dto.integration.request.AssistantRQ;
import com.itechro.cas.model.dto.integration.request.BranchAuthorityLevelRQ;
import com.itechro.cas.model.dto.integration.response.BranchAuthorityLevelResponseListDTO;
import com.itechro.cas.model.dto.integration.response.BranchLoadResponseListDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.casmaster.MasterDataService;
import com.itechro.cas.service.integration.IntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/masterData")
public class MasterDataController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(MasterDataController.class);

    @Autowired
    private IntegrationService integrationService;

    @Autowired
    private MasterDataService masterDataService;

    @ResponseExceptionHandler
    @RequestMapping(value = "/getBranchList", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<BranchLoadResponseListDTO> getBranchList() throws AppsException {


        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Branch list by user {}", credentialsDTO.getUserID());

        BranchLoadResponseListDTO responseListDTO = integrationService.getBranchList(credentialsDTO);
        //TODO REMOVE
//        BranchLoadResponseListDTO responseListDTO = integrationService.getBranchListManual(credentialsDTO);

        LOG.info("END : Get Branch list : {} , by user {}", responseListDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(responseListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAllPurposeOfAdvanced", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<PurposeOfAdvancedDTO>> getPurposeOfAdvanced() throws AppsException {


        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get purpose of advanced list by user {}", credentialsDTO.getUserID());

        List<PurposeOfAdvancedDTO> responseListDTO = masterDataService.getAllPurposeOfAdvanced();

        LOG.info("END : Get purpose of advanced  list : {} , by user {}", responseListDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(responseListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAllSectorData", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<SectorDTO>> getAllSectorData() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get Sector list by user {}", credentialsDTO.getUserID());

        List<SectorDTO> responseListDTO = masterDataService.getAllSectorData();

        LOG.info("END : Get Sector list : {} , by user {}", responseListDTO, credentialsDTO.getUserID());
        return new ResponseEntity<>(responseListDTO, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getAllUpcSectionData", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<UpcSectionDTO>> getAllUpcSectionData() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get upc section list by user {}", credentialsDTO.getUserID());

        List<UpcSectionDTO> responseListDTO = masterDataService.getAllUpcSectionData();

        LOG.info("END : Get upc section list : {} , by user {}", responseListDTO, credentialsDTO.getUserID());
        return new ResponseEntity<>(responseListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAllSubSectorData", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<SubSectorDataRS> getAllSubSectorData() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get sector sub sector data by user {}", credentialsDTO.getUserID());

        SubSectorDataRS responseListDTO = masterDataService.getAllSubSectorData();

        LOG.info("END : Get sector sub sector data : {} , by user {}", responseListDTO, credentialsDTO.getUserID());
        return new ResponseEntity<>(responseListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAllWorkFlowTemplates", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<WorkFlowTemplateDTO>> getAllWorkFlowTemplates() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get Work flow tempplates by user {}", credentialsDTO.getUserID());

        List<WorkFlowTemplateDTO> responseListDTO = masterDataService.getAllWorkFlowTemplates();

        LOG.info("END : Get Work flow tempplates : {} , by user {}", responseListDTO, credentialsDTO.getUserID());
        return new ResponseEntity<>(responseListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getUpmGroupByWorkFlowTemplateID/{workFlowTemplateID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<UpmGroupDTO>> getUpmGroupByWorkFlowTemplateID(@PathVariable Integer workFlowTemplateID) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get Upm Group by user {}", credentialsDTO.getUserID());

        List<UpmGroupDTO> responseListDTO = masterDataService.getUpmGroupByWorkFlowTemplateID(workFlowTemplateID);

        LOG.info("END : Get Upm Group : {} , by user {}", responseListDTO, credentialsDTO.getUserID());
        return new ResponseEntity<>(responseListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<UpmGroupDTO>> getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode(@RequestBody LoggedInUserWorkFlowRQ loggedInUserWorkFlowRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get Upm Group by user Logged in Upm Code : {} : {}", loggedInUserWorkFlowRQ, credentialsDTO.getUserID());

        List<UpmGroupDTO> responseListDTO = masterDataService.getUpmGroupByWorkFlowTemplateIDAndLoggedInUserUpmGroupCode(loggedInUserWorkFlowRQ);

        LOG.info("END : Get Upm Group by user Logged in Upm Code : {} , by user {}", responseListDTO, credentialsDTO.getUserID());
        return new ResponseEntity<>(responseListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getUserDetailListFormBranchAuthorityLevel", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<BranchAuthorityLevelResponseListDTO> getUserDetailListFormBranchAuthorityLevel(@RequestBody BranchAuthorityLevelRQ branchAuthorityLevelRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get User Detail List From Branch AuthorityLevel {}", credentialsDTO.getUserID());

        BranchAuthorityLevelResponseListDTO responseDTO = integrationService.getUserDetailListFormBranchAuthorityLevel(branchAuthorityLevelRQ, credentialsDTO);
        //TODO REMOVE
//        BranchAuthorityLevelResponseListDTO responseDTO = integrationService.getUserDetailListFormBranchAuthorityLevelManually(branchAuthorityLevelRQ, credentialsDTO);

        LOG.info("END : Get Get User Detail List From Branch AuthorityLevel : {} , by user {}", responseDTO, credentialsDTO.getUserID());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getActiveSecuritySummaryTopics", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<SecuritySummaryTopicDTO>> getActiveSecuritySummaryTopics() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get all active security Summary topics by user {}", credentialsDTO.getUserID());

        List<SecuritySummaryTopicDTO> responseListDTO = masterDataService.getActiveSecuritySummaryTopics();

        LOG.info("END : Get all active security Summary topics by user : {} , by user {}", responseListDTO, credentialsDTO.getUserID());
        return new ResponseEntity<>(responseListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getApprovedApplicationFormTopics", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<AFTopicDTO>> getApprovedApplicationFormTopics() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get all active security Summary topics by user {}", credentialsDTO.getUserID());

        List<AFTopicDTO> responseListDTO = masterDataService.getApprovedApplicationFormTopics();

        LOG.info("END : Get all active security Summary topics by user : {} , by user {}", responseListDTO.size(), credentialsDTO.getUserID());
        return new ResponseEntity<>(responseListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCasActiveBranchDepartmentList", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CASBranchDepartmentDTO>> getCasActiveBranchDepartmentList() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get all active CAS Branch Departments by user {}", credentialsDTO.getUserID());

        List<CASBranchDepartmentDTO> responseListDTO = masterDataService.getCasActiveBranchDepartmentList();

        LOG.info("END : Get all active CAS Branch Departments by user : {} , by user {}", responseListDTO.size(), credentialsDTO.getUserID());
        return new ResponseEntity<>(responseListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAssistants", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<BranchAuthorityLevelResponseListDTO> getAssistants(@RequestBody AssistantRQ assistantRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get Users Assistants {}", credentialsDTO.getUserID());

        BranchAuthorityLevelResponseListDTO responseDTO = integrationService.getAssistants(assistantRQ, credentialsDTO);

        LOG.info("END : Get Users Assistants : {} , by user {}", responseDTO, credentialsDTO.getUserID());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }



   /* @ResponseExceptionHandler
    @RequestMapping(value = "/getCommitteeTypes", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CommitteeTypeDTO>> getCommitteeTypes() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START :Get getCommitteeTypes {}", credentialsDTO.getUserID());

        List<CommitteeTypeDTO> responseListDTO = masterDataService.getCommitteeTypesFromDTO();

        LOG.info("END : Get getCommitteeTypes {} , by user {}", responseListDTO.size(), credentialsDTO.getUserID());
        return new ResponseEntity<>(responseListDTO, HttpStatus.OK);
    }*/

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCommittees", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CACommitteeDTO>> getCommittees() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START :Get getCommittees {}", credentialsDTO.getUserID());

        List<CACommitteeDTO> responseListDTO = masterDataService.getCommittees();

        LOG.info("END : Get getCommittees {} , by user {}", responseListDTO.size(), credentialsDTO.getUserID());
        return new ResponseEntity<>(responseListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCommitteeLevels", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CALevelDTO>> getCommitteeLevels() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START :Get getCommitteeLevels {}", credentialsDTO.getUserID());

        List<CALevelDTO> responseListDTO = masterDataService.getCommitteeLevels();

        LOG.info("END : Get getCommitteeLevels {} , by user {}", responseListDTO.size(), credentialsDTO.getUserID());
        return new ResponseEntity<>(responseListDTO, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getCommitteeUsers", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CAUserDTO>> getCommitteeUsers() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START :Get getCommitteeUsers {}", credentialsDTO.getUserID());

        List<CAUserDTO> responseListDTO = masterDataService.getCommitteeUsers();

        LOG.info("END : Get getCommitteeUsers {} , by user {}", responseListDTO.size(), credentialsDTO.getUserID());
        return new ResponseEntity<>(responseListDTO, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getSecurityDocumentSubmitDiv", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getSecurityDocumentSubmitDiv() throws Exception{
        String value = integrationService.getSecurityDocumentSubmitDiv();
        LOG.info("START : getSecurityDocumentSubmitDiv: {}", value);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getSecurityDocumentSubmitWorkClass", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getSecurityDocumentSubmitWorkClass() throws Exception{
        String value =  integrationService.getSecurityDocumentSubmitWorkClass();
        LOG.info("START : getSecurityDocumentSubmitWorkClass: {}", value);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getBCCEntererWorkClass", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getBCCEntererWorkClass() throws Exception{
        String value =  integrationService.getBccEntererWorkClass();
        LOG.info("START : getBCCEntererWorkClass: {}", value);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getBCCAuthorizerWorkClass", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getBCCAuthorizerWorkClass() throws Exception{
        String value =  integrationService.getBccAuthorizerWorkClass();
        LOG.info("START : getBCCAuthorizerWorkClass: {}", value);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getBCCInquirerWorkClass", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getBCCInquirerWorkClass() throws Exception{
        String value =  integrationService.getBccInquirerWorkClass();
        LOG.info("START : getBCCInquirerWorkClass: {}", value);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

}
