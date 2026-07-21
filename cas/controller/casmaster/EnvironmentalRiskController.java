package com.itechro.cas.controller.casmaster;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.casmaster.EnvRiskRequestDTO;
import com.itechro.cas.model.dto.casmaster.EnvRiskResponseDTO;
import com.itechro.cas.model.dto.casmaster.EnvironmentalRiskRespDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.casmaster.EnvironmentalRiskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/environmentalRisk")
public class EnvironmentalRiskController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(EnvironmentalRiskController.class);

    private final EnvironmentalRiskService environmentalRiskService;

    public EnvironmentalRiskController(EnvironmentalRiskService environmentalRiskService) {
        this.environmentalRiskService = environmentalRiskService;
    }

    @ResponseExceptionHandler
    @GetMapping(value = "/getRiskCategories", headers = "Accept=application/json")
    public ResponseEntity<EnvRiskResponseDTO> getRiskCategories() throws AppsException{
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get risk categories: user {}" , credentialsDTO.getUserID());

        EnvRiskResponseDTO result = environmentalRiskService.getRiskCategories();

        LOG.info("END : Get risk categories: user {}",credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/saveTempRiskCategory", headers = "Accept=application/json")
    public ResponseEntity<EnvRiskResponseDTO> saveTempRiskCategory(@RequestBody EnvRiskRequestDTO envRiskRequestDTO) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Save temp risk categories: request {}, user {}" , envRiskRequestDTO, credentialsDTO.getUserID());

        EnvRiskResponseDTO result = environmentalRiskService.saveTempRiskCategory(envRiskRequestDTO,credentialsDTO);

        LOG.info("END : Save temp risk categories: request {}, user {}", envRiskRequestDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/approveRejectCategory", headers = "Accept=application/json")
    public ResponseEntity<EnvRiskResponseDTO> approveRejectCategory(@RequestBody EnvRiskRequestDTO envRiskRequestDTO) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Authorize risk categories: request {}, user {}" , envRiskRequestDTO, credentialsDTO.getUserID());

        EnvRiskResponseDTO result = environmentalRiskService.approveRejectCategory(envRiskRequestDTO,credentialsDTO);

        LOG.info("END : Authorize risk categories: request {}, user {}", envRiskRequestDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getEnvironmentalRiskTree", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<EnvironmentalRiskRespDTO>> getEnvironmentalRiskTree() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Environmental Risk Tree {}", credentialsDTO.getUserID());

        List<EnvironmentalRiskRespDTO> riskDTOS = environmentalRiskService.getEnvironmentalRiskTree();

        LOG.info("END : Search Environmental Risk Tree {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(riskDTOS, HttpStatus.OK);
    }
}
