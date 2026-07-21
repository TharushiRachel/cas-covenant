package com.itechro.cas.controller.advanceAnaytics;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.advancedAnalytics.AnalyticsDecisionDTO;
import com.itechro.cas.model.dto.advancedAnalytics.LeaseJourneyRequestDTO;
import com.itechro.cas.model.dto.integration.response.StandardResponse;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.integration.MicroIntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "${api.prefix}/advanceAnalytics")
public class AdvanceAnalyticsController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(AdvanceAnalyticsController.class);

    private final MicroIntegrationService microIntegrationService;

    public AdvanceAnalyticsController(MicroIntegrationService microIntegrationService) {
        this.microIntegrationService = microIntegrationService;
    }

    @PostMapping("/getLeasingJourneyValidation")
    public ResponseEntity<StandardResponse<AnalyticsDecisionDTO>> getLeasingJourneyValidation(
            @RequestBody LeaseJourneyRequestDTO leaseJourneyRequestDTO) throws AppsException, IOException {
        LOG.info(
                "START | getLeasingJourneyValidation - AdvanceAnalyticsController | request : {}",
                leaseJourneyRequestDTO);

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        ResponseEntity<StandardResponse<AnalyticsDecisionDTO>> response =
                microIntegrationService.getLeasingJourneyValidation(leaseJourneyRequestDTO, credentialsDTO);
        LOG.info("END | getLeasingJourneyValidation - AdvanceAnalyticsController | response : {}", response);
        return ResponseEntity.ok().body(response.getBody());
    }
}
