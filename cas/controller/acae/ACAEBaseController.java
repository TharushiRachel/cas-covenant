package com.itechro.cas.controller.acae;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.acae.request.ACAEBulkCommentRQ;
import com.itechro.cas.model.dto.acae.request.ACAEListDoneRQ;
import com.itechro.cas.model.dto.acae.request.ACAESearchByStatusRQ;
import com.itechro.cas.model.dto.acae.request.ACAEStatusCountRQ;
import com.itechro.cas.model.dto.acae.response.ACAESearchByStatusDTO;
import com.itechro.cas.model.dto.acae.response.ACAEStatusCountDTO;
import com.itechro.cas.model.dto.acae.response.ACAEUserDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.acae.ACAEBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/acae")
public class ACAEBaseController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ACAEBaseController.class);

    private final ACAEBaseService acaeBaseService;

    @Autowired
    public ACAEBaseController(ACAEBaseService acaeBaseService) {
        this.acaeBaseService = acaeBaseService;
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getAllACAERecordsByStatus", headers = "Accept=application/json")
    public ResponseEntity<List<ACAESearchByStatusDTO>> getACAERecordsByStatusController(@RequestBody ACAESearchByStatusRQ acaeSearchByStatusRQ)
            throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get ACAE Records by Status: {} , user {}", acaeSearchByStatusRQ, credentialsDTO);

        List<ACAESearchByStatusDTO> acaeSearchByStatusDTOList = acaeBaseService.getAllACAERecordsByStatusService(acaeSearchByStatusRQ, credentialsDTO);

        LOG.info("END : Search ACAE Records by Status: {} , user {}", acaeSearchByStatusDTOList, credentialsDTO);

        return new ResponseEntity<>(acaeSearchByStatusDTOList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getACAEPagedRecordsByStatus", headers = "Accept=application/json")
    public ResponseEntity<Page<ACAESearchByStatusDTO>> getACAEPagedRecordsByStatusController(@RequestBody ACAESearchByStatusRQ acaeSearchByStatusRQ)
            throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get ACAE Records by Status: {} , user {}", acaeSearchByStatusRQ, credentialsDTO);

        Page<ACAESearchByStatusDTO> acaeSearchByStatusDTOList = acaeBaseService.getACAEPagedRecordsByStatusService(acaeSearchByStatusRQ, credentialsDTO);

        LOG.info("END : Search ACAE Records by Status: {} , user {}", acaeSearchByStatusDTOList, credentialsDTO);

        return new ResponseEntity<>(acaeSearchByStatusDTOList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getACAEStatusCount", headers = "Accept=application/json")
    public ResponseEntity<ACAEStatusCountDTO> getACAEStatusCountController(@RequestBody ACAEStatusCountRQ acaeStatusCountRQ) {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get ACAE Count by Status: {} , user {}", acaeStatusCountRQ, credentialsDTO);

        ACAEStatusCountDTO acaeStatusCountDTO = acaeBaseService.getACAEStatusCountService(acaeStatusCountRQ, credentialsDTO);

        LOG.info("END : Search ACAE Count by Status: {} , user {}", acaeStatusCountDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(acaeStatusCountDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getEligibilityForwardACAEBatch", headers = "Accept=application/json")
    public ResponseEntity<String> getEligibilityForwardACAEBatchController(@RequestBody ACAEListDoneRQ acaeListDoneRQ) {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Eligibility Forward ACAE Batch: {} , user {} ", acaeListDoneRQ, credentialsDTO);

        String forwardACAEBatch = acaeBaseService.getEligibilityForwardACAEBatchService(acaeListDoneRQ, credentialsDTO);

        LOG.info("END : Eligibility Forward ACAE Batch: {} , user {} ", acaeListDoneRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(forwardACAEBatch, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getACAEPaperUserWiseSummary", headers = "Accept=application/json")
    public ResponseEntity<List<ACAEUserDTO>> getACAEPaperUserWiseSummaryController(@RequestBody ACAESearchByStatusRQ acaeSearchByStatusRQ) {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : ACAE Paper User Wise Summary: {} , user {} ", acaeSearchByStatusRQ, credentialsDTO);

        List<ACAEUserDTO> acaeUserDTOList = acaeBaseService.getACAEPaperUserWiseSummaryService(acaeSearchByStatusRQ, credentialsDTO);

        LOG.info("END : ACAE Paper User Wise Summary: {} , user {} ", acaeSearchByStatusRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(acaeUserDTOList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/saveBulkComments", headers = "Accept=application/json")
    public ResponseEntity<String> saveBulkComments(@RequestBody ACAEBulkCommentRQ acaeBulkCommentRQ)
            throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START :  Save Bulk Comments: {} , user {} ", acaeBulkCommentRQ, credentialsDTO);

        String message = acaeBaseService.saveBulkComments(acaeBulkCommentRQ, credentialsDTO);

        LOG.info("END : save Bulk Comments: {} , user {} ", acaeBulkCommentRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
