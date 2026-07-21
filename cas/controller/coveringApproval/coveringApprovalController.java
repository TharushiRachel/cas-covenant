package com.itechro.cas.controller.coveringApproval;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.dao.coveringApproval.CovAppBasinInfoDao;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.domain.coveringApproval.CovAppComment;
import com.itechro.cas.model.domain.coveringApproval.CoveringApproval;
import com.itechro.cas.model.dto.coveringApproval.*;
import com.itechro.cas.model.dto.integration.request.LoadBankAccountDetailsRQ;
import com.itechro.cas.model.dto.integration.request.LoadTranDataRQ;
import com.itechro.cas.model.dto.integration.response.coveringApproval.CovAppCustomerDTO;
import com.itechro.cas.model.dto.integration.response.coveringApproval.TranDetailsResponseDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.coveringApproval.CoveringApprovalService;
import com.itechro.cas.service.integration.IntegrationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 * @author tharushi
 */
@RestController
@RequestMapping(value = "${api.prefix}/coveringApproval")
public class coveringApprovalController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(coveringApprovalController.class);

    @Autowired
    IntegrationService integrationService;

    @Autowired
    private CoveringApprovalService coveringApprovalService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CovAppBasinInfoDao covAppBasinInfoDao;

    @ResponseExceptionHandler
    @PostMapping(value = "/loadTranDetails", headers = "Accept=application/json")
    public ResponseEntity<Object> getCoveringApprovalTranDetails(@RequestBody LoadTranDataRQ loadTranDataRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get covering approval tran details by user {}", credentialsDTO.getUserID());

        List<TranDetailsResponseDTO> tranDetailsResponseDTOList = coveringApprovalService.getCoveringApprovalTranDetails(loadTranDataRQ, credentialsDTO);

        LOG.info("END : Get covering approval tran details : {}, by user {}", tranDetailsResponseDTOList, credentialsDTO.getUserID());

        return new ResponseEntity<>(tranDetailsResponseDTOList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getCoveringApprovalDashboardCount", headers = "Accept=application/json")
    public ResponseEntity<CoveringApprovalDashboardCountDTO> getCoveringApprovalDashboardCount(@RequestBody CoveringApprovalDashboardRQ coveringApprovalDashboardRQ) throws AppsException{

        CredentialsDTO credentialsDTO = new CredentialsDTO();

        CoveringApprovalDashboardCountDTO response;

        LOG.info("START : getCoveringApprovalDashboardCount {} : {}", credentialsDTO.getUserID(), coveringApprovalDashboardRQ);

        response = coveringApprovalService.getCoveringApprovalDashboardCount(coveringApprovalDashboardRQ);

        LOG.info("END : getCoveringApprovalDashboardCount {} : {}", credentialsDTO.getUserID(), response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getPagedCoveringApprovalDashboard", headers = "Accept=application/json")
    public ResponseEntity<Page<CoveringApprovalDashboardDTO>> getPagedCoveringApprovalDashboard(@RequestBody CoveringApprovalDashboardRQ coveringApprovalDashboardRQ) throws AppsException{

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Draft coveringApproval dashboard information : {}, user {}", coveringApprovalDashboardRQ, credentialsDTO.getUserID());

        Page<CoveringApprovalDashboardDTO> pageResponse;

        pageResponse = coveringApprovalService.getPagedCoveringApprovalDashboard(coveringApprovalDashboardRQ);

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/updCoveringApproval/{covAppId}", headers = "Accept=application/json")
    public ResponseEntity<Object> updateCoveringApproval(@PathVariable Integer covAppId, @RequestBody CoveringApprovalDTO coveringApprovalDTO) throws AppsException{

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        CoveringApproval coveringApproval = modelMapper.map(coveringApprovalDTO, CoveringApproval.class);

        coveringApproval.setCovAppId(covAppId);

        CoveringApproval coveringApprovalUpdate = coveringApprovalService.updateCoveringApproval(coveringApproval, credentialsDTO);

        CoveringApprovalDTO coveringApprovalDTOUpdate = modelMapper.map(coveringApprovalUpdate, CoveringApprovalDTO.class);

        return new ResponseEntity<>(coveringApprovalDTOUpdate, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/draftCoveringApproval", headers = "Accept=application/json")
    public ResponseEntity<CoveringApprovalDTO> draftCoveringApproval(@RequestBody CoveringApprovalDTO coveringApprovalDTO) throws AppsException{

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Draft coveringApproval base information : {}, user {}", coveringApprovalDTO, credentialsDTO.getUserID());

        CoveringApprovalDTO result = coveringApprovalService.draftCoveringApproval(coveringApprovalDTO, credentialsDTO);

        LOG.info("END : Draft coveringApproval base information : {}, user {}", result, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/loadCustomerBankDetails", headers = "Accept=application/json")
    public ResponseEntity<CovAppCustomerDTO> loadCustomerBankDetails(@RequestBody LoadBankAccountDetailsRQ loadBankAccountDetailsRQ) throws AppsException{

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        CovAppCustomerDTO covAppCustomerDTO = null;

        loadBankAccountDetailsRQ.setRefId("20220224133817459039");
        LOG.info("START : Get Bank Account number : {}, user {}", loadBankAccountDetailsRQ, credentialsDTO.getUserID());

        covAppCustomerDTO = integrationService.getAccountBalanceDetailsByRefNoAndAccountNo(loadBankAccountDetailsRQ, credentialsDTO);

        LOG.info("END : Get Bank Account details : {}, by user {}", covAppCustomerDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(covAppCustomerDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/updateCoveringApproval", headers = "Accept=application/json")
    public ResponseEntity<CoveringApprovalDTO> updateCoveringApprovalStatus(@RequestBody CoveringApprovalStatusChangeRQ coveringApprovalStatusChangeRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Update Covering approval form : {}, user {}", coveringApprovalStatusChangeRQ, credentialsDTO.getUserID());

        CoveringApprovalDTO coveringApprovalDTO = this.coveringApprovalService.updateCoveringApprovalStatus(coveringApprovalStatusChangeRQ, credentialsDTO);

        LOG.info("END : Update Covering approval Form : {}, user {}", coveringApprovalDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(coveringApprovalDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @GetMapping(value = "/getCoveringApprovalByID/{covAppId}", headers = "Accept=application/json")
    public ResponseEntity<CoveringApprovalDTO> getCoveringApprovalByID(@PathVariable Integer covAppId) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Covering Approval By ID : {}, user {} ", covAppId, credentialsDTO.getUserID());

        CoveringApprovalDTO coveringApprovalDTO = this.coveringApprovalService.getCoveringApprovalByID(covAppId, credentialsDTO);

        LOG.info("END : Get Covering Approval By ID : {}, user {} ", covAppId, credentialsDTO.getUserID());

        return new ResponseEntity<>(coveringApprovalDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getPendingCoveringApprovals", headers = "Accept=application/json")
    public ResponseEntity<List<CoveringApprovalDTO>> getPendingCoveringApprovals(@RequestBody CovAppBasicInfoRQ request) throws AppsException{

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Load Covering Approval pending records by Account Number : {} , user {}", request, credentialsDTO.getUserName());

        List<CoveringApprovalDTO> coveringApprovals = coveringApprovalService.getPendingCoveringApprovals(request, credentialsDTO);

        LOG.info("END : Load Covering Approval pending records by Account Number : {} ", coveringApprovals);

        return new ResponseEntity<>(coveringApprovals, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @GetMapping(value = "/getComments/{covAppId}", headers = "Accept=application/json")
    public ResponseEntity<List<CovAppCommentDTO>> getComments(@PathVariable Integer covAppId) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Load Covering Approval pending records by covAppId : {}, user {} ", covAppId, credentialsDTO.getUserName());

        List<CovAppComment> covAppComments = coveringApprovalService.getComments(covAppId, credentialsDTO);

        List<CovAppCommentDTO> covAppCommentDTOList = covAppComments.stream().map(comment -> modelMapper.map(comment, CovAppCommentDTO.class)).collect(Collectors.toList());


        LOG.info("END : Load Covering Approval pending records by covAppId : {} , user {}", covAppId, credentialsDTO.getUserName());

        return new ResponseEntity<>(covAppCommentDTOList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @GetMapping(value = "/getDirectReturnUsersList/{covAppId}", headers = "Accept=application/json")
    public ResponseEntity<List<CovAppStatusHistoryDTO>> getFPDirectReturnUsersList(@PathVariable Integer covAppId) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Covering Approval Direct Return Users List, {} user {}",covAppId, credentialsDTO.getUserID());

        List<CovAppStatusHistoryDTO> response = coveringApprovalService.getDirectReturnUsersList(covAppId, credentialsDTO);

        LOG.info("END : Get Covering Approval Direct Return Users List: {} , user {}", covAppId, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
