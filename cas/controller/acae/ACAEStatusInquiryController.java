package com.itechro.cas.controller.acae;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.acae.request.ACAEInquiryByDateRangeRQ;
import com.itechro.cas.model.dto.acae.request.ACAEInquiryByResubmittedRQ;
import com.itechro.cas.model.dto.acae.request.ACAEInquiryBySolIdRQ;
import com.itechro.cas.model.dto.acae.response.ACAEDateRangeInquiryDTO;
import com.itechro.cas.model.dto.acae.response.ACAEInquiryByResubmittedDTO;
import com.itechro.cas.model.dto.acae.response.ACAEInquiryBySolIdDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.acae.ACAEStatusInquiryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/acae/status-inquiry")
public class ACAEStatusInquiryController  extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ACAEStatusInquiryController.class);
    private final ACAEStatusInquiryService acaeStatusInquiryService;

    @Autowired
    public ACAEStatusInquiryController(ACAEStatusInquiryService acaeStatusInquiryService) {
        this.acaeStatusInquiryService = acaeStatusInquiryService;
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getInquiryByDateRange", headers = "Accept=application/json")
    public ResponseEntity<Page<ACAEDateRangeInquiryDTO>> getInquiryByDateRangeController(@RequestBody ACAEInquiryByDateRangeRQ acaeInquiryByDateRangeRQ)
            throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : ACAE Inquiry By Date Range : {}, user {}", acaeInquiryByDateRangeRQ, credentialsDTO);

        Page<ACAEDateRangeInquiryDTO> acaeDateRangeInquiryDTO = acaeStatusInquiryService.getInquiryByDateRangeService(acaeInquiryByDateRangeRQ, credentialsDTO);

        LOG.info("END : ACAE Date Range Inquiry : {} , user {}", acaeDateRangeInquiryDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(acaeDateRangeInquiryDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getInquiryBySolIds", headers = "Accept=application/json")
    public ResponseEntity<List<ACAEInquiryBySolIdDTO>> getInquiryBySolIdsController(@RequestBody ACAEInquiryBySolIdRQ acaeInquiryBySolIdRQ)
            throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : ACAE Inquiry By Sol Ids :{}, user {}", acaeInquiryBySolIdRQ, credentialsDTO);

        List<ACAEInquiryBySolIdDTO> ACAEInquiryBySolIdDTO = acaeStatusInquiryService.getInquiryBySolIdsService(acaeInquiryBySolIdRQ, credentialsDTO);

        LOG.info("END : ACAE Inquiry By Sol Ids : {} , user {}", ACAEInquiryBySolIdDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(ACAEInquiryBySolIdDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getInquiryByResubmittedACAE", headers = "Accept=application/json")
    public ResponseEntity<Page<ACAEInquiryByResubmittedDTO>> getInquiryByResubmittedController(@RequestBody ACAEInquiryByResubmittedRQ acaeInquiryByResubmittedRQ){

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : ACAE Inquiry By Resubmitted :{}, user {}", acaeInquiryByResubmittedRQ, credentialsDTO);

        Page<ACAEInquiryByResubmittedDTO> acaeInquiryByResubmittedDTOList = acaeStatusInquiryService.getInquiryByResubmittedService(acaeInquiryByResubmittedRQ, credentialsDTO);

        LOG.info("END : ACAE Inquiry By Resubmitted : {} , user {}", acaeInquiryByResubmittedDTOList, credentialsDTO.getUserID());

        return new ResponseEntity<>(acaeInquiryByResubmittedDTOList, HttpStatus.OK);
    }
}
