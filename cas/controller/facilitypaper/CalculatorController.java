package com.itechro.cas.controller.facilitypaper;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.model.dto.facilitypaper.creditcalculator.CalculatorInputDTO;
import com.itechro.cas.model.dto.facilitypaper.creditcalculator.CalculatorResponse;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.faclititypaper.creditcalculator.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@RestController
@RequestMapping("${api.prefix}/calculator")
public class CalculatorController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(CalculatorController.class);

    @Autowired
    private CalculatorService calculatorService;

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCreditCalculatorData", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CalculatorResponse> getCreditCalculatorData(@RequestBody CalculatorInputDTO calculatorInputDTO) throws SAXException, ParserConfigurationException, IOException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get System Outputs : {} , user {}", calculatorInputDTO, credentialsDTO.getUserID());

        CalculatorResponse response = calculatorService.getCreditCalculatorData(calculatorInputDTO, credentialsDTO);

        LOG.info("END : Get System Outputs : {} , user {}", calculatorInputDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @ResponseExceptionHandler
//    @RequestMapping(value = "/getNextRentalAmount", headers = "Accept=application/json", method = RequestMethod.POST)
//    public ResponseEntity<CalculatorResponse> getNextRentalAmount(@RequestBody CalculatorInputDTO calculatorInputDTO) throws AppsException, SAXException, ParserConfigurationException, IOException {
//
//        CredentialsDTO credentialsDTO = getCredentialsDTO();
//
//        LOG.info("START : Get System Outputs : {} , user {}", calculatorInputDTO, credentialsDTO.getUserID());
//
//        CalculatorResponse response = calculatorService.getCreditCalculatorData(calculatorInputDTO, credentialsDTO);
//
//        LOG.info("END : Get System Outputs : {} , user {}", calculatorInputDTO, credentialsDTO.getUserID());
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
}
