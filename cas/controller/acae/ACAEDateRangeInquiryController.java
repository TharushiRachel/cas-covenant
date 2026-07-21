package com.itechro.cas.controller.acae;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.model.dto.acae.request.ACAEDateRangeInquiryRQ;
import com.itechro.cas.model.dto.acae.response.ACAESearchByStatusDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.acae.ACAEDateRangeInquiryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/acae/date-range-inquiry")
public class ACAEDateRangeInquiryController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ACAEDateRangeInquiryController.class);
    private final ACAEDateRangeInquiryService acaeDateRangeInquiryService;

    @Autowired
    public ACAEDateRangeInquiryController(ACAEDateRangeInquiryService acaeDateRangeInquiryService) {
        this.acaeDateRangeInquiryService = acaeDateRangeInquiryService;
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getACAEDateRangeInquiry", headers = "Accept=application/json")
    public ResponseEntity<List<ACAESearchByStatusDTO>> getACAEDateRangeInquiry(@RequestBody ACAEDateRangeInquiryRQ acaeDateRangeInquiryRQ)
            throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : ACAE Date Range Inquiry : {} , user {}", acaeDateRangeInquiryRQ, credentialsDTO);

        List<ACAESearchByStatusDTO> acaeDateRangeInquiryDTOList = acaeDateRangeInquiryService.
                getACAEDateRangeInquiryService(acaeDateRangeInquiryRQ, credentialsDTO);

        LOG.info("END : ACAE Date Range Inquiry : {} , user {}", acaeDateRangeInquiryDTOList, credentialsDTO.getUserID());

        return new ResponseEntity<>(acaeDateRangeInquiryDTOList, HttpStatus.OK);
    }
}
