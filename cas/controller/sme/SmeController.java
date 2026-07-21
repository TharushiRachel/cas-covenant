package com.itechro.cas.controller.sme;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.model.domain.sme.SmeQuestion;
import com.itechro.cas.model.dto.customer.SearchCustomerRQ;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.dto.integration.response.CustomerFacilityDetailResponseDTO;
import com.itechro.cas.model.dto.sme.FpSmeAnswerDTO;
import com.itechro.cas.model.dto.sme.SmeCustomerTurnoverResDTO;
import com.itechro.cas.model.dto.sme.SmeCustomerTurnoverRqDTO;
import com.itechro.cas.model.dto.sme.SmeQuestionDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.sme.SmeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "${api.prefix}/sme")
@Slf4j
public class SmeController extends BaseController {

    private final SmeService smeService;

    public SmeController(SmeService smeService) {
        this.smeService = smeService;
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAllQuestionsAndAnswers", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<SmeQuestionDTO>> getAllQuestionsAndAnswers() throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        log.info("START :getAllQuestionsAndAnswers | SmeController by user {}", credentialsDTO.getUserID());

        List<SmeQuestionDTO> smeQuestionDTOList = smeService.getAllQuestionsAndAnswers();

        log.info("END :getAllQuestionsAndAnswers | SmeController response : {} ", smeQuestionDTOList);

        return new ResponseEntity<>(smeQuestionDTOList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateAnswer", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<FpSmeAnswerDTO>> saveOrUpdateAnswer(@RequestBody List<FpSmeAnswerDTO> fpSmeAnswerDTOList) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        log.info("START :saveOrUpdateAnswer | SmeController by user {}", credentialsDTO.getUserID());

        List<FpSmeAnswerDTO> response = smeService.saveOrUpdateAnswer(fpSmeAnswerDTOList, credentialsDTO);

        log.info("END :saveOrUpdateAnswer | SmeController response : {} ", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAnswer/{facilityPaperID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<FpSmeAnswerDTO>>> getAnswer(@PathVariable Integer facilityPaperID) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        log.info("START :getAnswer | SmeController by user {}", credentialsDTO.getUserID());

        Map<String, List<FpSmeAnswerDTO>> response = smeService.getAnswer(facilityPaperID, credentialsDTO);

        log.info("END :getAnswer | SmeController response : {} ", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAnswerList/{facilityPaperID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<FpSmeAnswerDTO>>> getAnswerList(@PathVariable Integer facilityPaperID) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        log.info("START :getAnswerList | SmeController by user {}", credentialsDTO.getUserID());

        Map<String, List<FpSmeAnswerDTO>> response = smeService.getAnswerList(facilityPaperID, credentialsDTO);

        log.info("END :getAnswerList | SmeController response : {} ", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/smeCustomerTurnover", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<SmeCustomerTurnoverResDTO> smeCustomerTurnover(@RequestBody SmeCustomerTurnoverRqDTO request) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        log.info("START :smeCustomerTurnover | SmeController by user {}", credentialsDTO.getUserID());

        SmeCustomerTurnoverResDTO response = smeService.getSmeCustomerTurnoverData(request, credentialsDTO);

        log.info("END :smeCustomerTurnover | SmeController response : {} ", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
