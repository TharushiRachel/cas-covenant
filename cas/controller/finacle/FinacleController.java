package com.itechro.cas.controller.finacle;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.controller.facilitypaper.FacilityPaperController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.model.dto.finacle.request.*;
import com.itechro.cas.model.dto.finacle.response.*;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.finacle.FinacleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.itechro.cas.service.integration.IntegrationService;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "${api.prefix}/finacle")
public class FinacleController extends BaseController {
    @Autowired
    private FinacleService finacleService;
    @Autowired
    private IntegrationService integrationService;
    private static final Logger LOG = LoggerFactory.getLogger(FacilityPaperController.class);

    @ResponseExceptionHandler
    @RequestMapping(value = "/finacleData", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FinacleExOutLimitsRSDTO> getExandOutFinacleData(@RequestBody ExOutLimitsRQ cusId) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Get Ex Out limits: {} by: {}", cusId, credentialsDTO.getUserName());

        FinacleExOutLimitsRSDTO response = this.finacleService.getFinacleExOutLimits(cusId.getCusId(), credentialsDTO);

        LOG.info("END: Get Ex Out limits: {} by: {}", cusId, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getWatchlistStatus", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Boolean> getWatchlistStatus(@RequestBody ExOutLimitsRQ cusId) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();


        LOG.info("START: get watch list status: {} by: {}", cusId, credentialsDTO.getUserName());

        Boolean response = this.finacleService.IsWatchlisted(cusId.getCusId(), credentialsDTO);

        LOG.info("END: START: Save facility repayment: {} by: {}", cusId, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getGuaranteeVolumes", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<GuaranteeVolumesRS> getGuaranteeVolumes(@RequestBody GuaranteeVolumesRQ cusId) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Get guarantee Volumes: {} by: {}", cusId, credentialsDTO.getUserName());

        GuaranteeVolumesRS response = this.finacleService.getGuaranteeVolumesForCalenderYear(cusId, credentialsDTO);

        LOG.info("END: Get guarantee Volumes: {} by: {}", cusId, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getGuaranteeVolumesFinancialYear", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<GuaranteeVolumesRS> getGuaranteeVolumesFinancialVolumes(@RequestBody GuaranteeVolumesRQ cusId) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Get guarantee volume for financial year: {} by: {}", cusId, credentialsDTO.getUserName());

        GuaranteeVolumesRS response = this.finacleService.getGuaranteeVolumesForFinancialYear(cusId, credentialsDTO);

        LOG.info("END: Get guarantee volume for financial year: {} by: {}", cusId, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/saveGuaranteeVolumes", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Boolean> savegetGuaranteeVolumes(@RequestBody GuaranteeVolumeSaveRQ guaranteeVolumeSaveRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Save Guarantee volumes: {} by: {}", guaranteeVolumeSaveRQ, credentialsDTO.getUserName());

        Boolean response = this.finacleService.saveGuaranteeVolumesToDB(guaranteeVolumeSaveRQ, credentialsDTO);

        LOG.info("END: START: Save Guarantee volumes : {} by: {}", guaranteeVolumeSaveRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getGuaranteeVolumesDB", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<GuaranteeVolumesRS> getGuaranteeVolumesFromDB(@RequestBody GuaranteeVolumesDBRQ cusId) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Get guarantee volumes from DB : {} by: {}", cusId, credentialsDTO.getUserName());

        GuaranteeVolumesRS response = this.finacleService.getGuaranteeVolumesFromDB(cusId, credentialsDTO);

        LOG.info("END: START: Get guarantee volumes from DB: {} by: {}", cusId, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getTTTAccounts/{cusAccount}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getAccountsWithNoDataInDB(@PathVariable String cusAccount) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Get account with no data in DB: {} by: {}", cusAccount, credentialsDTO.getUserName());

        List<String > response = this.finacleService.getCustomerAccounts(cusAccount, credentialsDTO);

        LOG.info("END: Get account with no data in DB: {} by: {}", cusAccount, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getExportTurnoverCalender", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<ExportTurnoverRS>> getExportTurnoverCalender(@RequestBody ExportTurnOverRQ exportTurnOverRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Get TT tunrover from Finacle: {} by: {}", exportTurnOverRQ, credentialsDTO.getUserName());

        List<ExportTurnoverRS> response = this.finacleService.getExportTurnoverForCalenderYear(exportTurnOverRQ, credentialsDTO);

        LOG.info("END:  Get TT tunrover from Finacle: {} by: {}", exportTurnOverRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getExportTurnoverFinancial", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<ExportTurnoverRS>> getExportTurnoverFinancial(@RequestBody ExportTurnOverRQ exportTurnOverRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Get TT tunrover from Finacle: {} by: {}", exportTurnOverRQ, credentialsDTO.getUserName());

        List<ExportTurnoverRS> response = this.finacleService.getExportTurnoverForFinancialYear(exportTurnOverRQ, credentialsDTO);

        LOG.info("END:  Get TT tunrover from Finacle: {} by: {}", exportTurnOverRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @ResponseExceptionHandler
//    @RequestMapping(value = "/getExportTurnoverFinacalFinancialYear", headers = "Accept=application/json", method = RequestMethod.POST)
//    public ResponseEntity<List<TTTurnoverDetailsRS>> getExportTurnoverFinacalFinancialYear(@RequestBody ExportTurnOverRQ exportTurnOverRQ) throws Exception {
//
//        CredentialsDTO credentialsDTO = getCredentialsDTO();
//
//        LOG.info("START: Get TT tunrover from Finacle: {} by: {}", exportTurnOverRQ, credentialsDTO.getUserName());
//
//        List<TTTurnoverDetailsRS> response = this.finacleService.getExportTurnoverFinacalFinancialYear(exportTurnOverRQ, credentialsDTO);
//
//        LOG.info("END:  Get TT tunrover from Finacle: {} by: {}", exportTurnOverRQ, credentialsDTO.getUserName());
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

//    @ResponseExceptionHandler
//    @RequestMapping(value = "/getInsuranceDetails", headers = "Accept=application/json", method = RequestMethod.POST)
//    public ResponseEntity<List<InsurenceDetailsFromFinacleRS>> getInsuranceDetails(@RequestBody InsuranceDetailsRQ cusId) throws Exception {
//
//        CredentialsDTO credentialsDTO = getCredentialsDTO();
//
//        LOG.info("START: Save facility repayment: {} by: {}", cusId, credentialsDTO.getUserName());
//
//        List<InsurenceDetailsFromFinacleRS> response = this.finacleService.getInsuranceDetails(cusId, credentialsDTO);
//
//        LOG.info("END: START: Save facility repayment: {} by: {}", cusId, credentialsDTO.getUserName());
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

//    @ResponseExceptionHandler
//    @RequestMapping(value = "/saveInsuranceDetails", headers = "Accept=application/json", method = RequestMethod.POST)
//    public ResponseEntity<Boolean> saveInsuranceDetails(@RequestBody SaveCollateralDetailsRQ saveInsuranceValuationRQ) throws Exception {
//
//        CredentialsDTO credentialsDTO = getCredentialsDTO();
//
//        LOG.info("START: Save facility repayment: {} by: {}", saveInsuranceValuationRQ, credentialsDTO.getUserName());
//
//        Boolean response = this.finacleService.saveInsuranceValuationsToDB(saveInsuranceValuationRQ, credentialsDTO);
//
//        LOG.info("END: START: Save facility repayment: {} by: {}", saveInsuranceValuationRQ, credentialsDTO.getUserName());
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getInsuranceDetails", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<InsurenceDetailsFromFinacleRS>> getInsuranceDetails(@RequestBody InsuranceDetailsRQ insuranceDetailsRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Get Insurance Details: {} by: {}", insuranceDetailsRQ, credentialsDTO.getUserName());

        List<InsurenceDetailsFromFinacleRS> response = this.finacleService.getCollateralAndInsuranceDetails(insuranceDetailsRQ, false,credentialsDTO);

        LOG.info("END: Get Insurance Details: {} by: {}", insuranceDetailsRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/refreshInsuranceDetails", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<InsurenceDetailsFromFinacleRS>> refreshInsuranceDetails(@RequestBody InsuranceDetailsRQ insuranceDetailsRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Refresh insurance Details: {} by: {}", insuranceDetailsRQ, credentialsDTO.getUserName());

        List<InsurenceDetailsFromFinacleRS> response = this.finacleService.getCollateralAndInsuranceDetails(insuranceDetailsRQ, true,credentialsDTO);

        LOG.info("END: Refresh insurance Details: {} by: {}", insuranceDetailsRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getInsuranceDetailsDB", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<InsurenceDetailsFromFinacleRS>> getInsuranceDetailsDB(@RequestBody InsuranceDetailsRQ insuranceDetailsRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Get Insurance Details DB controller: {} by: {}", insuranceDetailsRQ, credentialsDTO.getUserName());

        List<InsurenceDetailsFromFinacleRS> response = this.finacleService.getCollateralAndInsuranceDetailsDB(insuranceDetailsRQ,credentialsDTO);

        LOG.info("END: Get Insurance Details DB controller: {} by: {}", insuranceDetailsRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/hasExpiredItem", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Boolean> getInsuranceExpireAvailability(@RequestBody InsuranceDetailsRQ insuranceDetailsRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Get Insurance Details DB controller: {} by: {}", insuranceDetailsRQ, credentialsDTO.getUserName());

        Boolean response = this.finacleService.getInsuranceExpireAvailability(insuranceDetailsRQ,credentialsDTO);

        LOG.info("END: Get Insurance Details DB controller: {} by: {}", insuranceDetailsRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getImportTurnoverFinacal", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<ImportTurnoverRS>> getImportTurnoverCalender(@RequestBody ImportTurnOverRQ importTurnOverRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START:Get Import T/O for Financial year: {} by: {}", importTurnOverRQ, credentialsDTO.getUserName());

        List<ImportTurnoverRS> response = this.finacleService.getImportTurnoverForFinancialYear(importTurnOverRQ, credentialsDTO);

        LOG.info("END:  Get Import T/O for Financial year: {} by: {}", importTurnOverRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getImportTurnoverCalender", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<ImportTurnoverRS>> getImportTurnoverFinacal(@RequestBody ImportTurnOverRQ importTurnOverRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Get Import T/O for Calender year: {} by: {}", importTurnOverRQ, credentialsDTO.getUserName());

        List<ImportTurnoverRS> response = this.finacleService.getImportTurnoverForCalenderYear(importTurnOverRQ, credentialsDTO);

        LOG.info("END:  Get Import T/O for Calender year: {} by: {}", importTurnOverRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/saveExportTurnOvers", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveExportTurnOvers(@RequestBody ExportTurnoverSaveRQ exportTurnoverSaveRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Save Export T/O: {} by: {}", exportTurnoverSaveRQ, credentialsDTO.getUserName());

        Boolean response = this.finacleService.saveExportTurnOverToDB(exportTurnoverSaveRQ, credentialsDTO);

        LOG.info("END: Save Export T/O : {} by: {}", exportTurnoverSaveRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveImportTurnOvers", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveImportTurnOvers(@RequestBody ImportTurnoverSaveRQ importTurnoverSaveRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Save Import T/O: {} by: {}", importTurnoverSaveRQ, credentialsDTO.getUserName());

        Boolean response = this.finacleService.saveImportTurnOverToDB(importTurnoverSaveRQ, credentialsDTO);

        LOG.info("END: Save Import T/O : {} by: {}", importTurnoverSaveRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getExportTurnOversDB", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<ExportTurnoverRS>> getExportTurnOversDB(@RequestBody ExportTurnoverGetRQ exportTurnoverGetRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Get Export T/O from DB: {} by: {}", exportTurnoverGetRQ, credentialsDTO.getUserName());

        List<ExportTurnoverRS> response = this.finacleService.getExportTurnOversDB(exportTurnoverGetRQ, credentialsDTO);

        LOG.info("END: Get Export T/O from DB : {} by: {}", exportTurnoverGetRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping (value = "/getImportTurnOversDB", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<ImportTurnoverRS>> getImportTurnOversDB(@RequestBody @Valid ImportTurnoverGetRQ importTurnoverGetRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Get Import T/O from DB: {} by: {}", importTurnoverGetRQ, credentialsDTO.getUserName());

        List<ImportTurnoverRS> response = this.finacleService.getImportTurnOversDB(importTurnoverGetRQ, credentialsDTO);

        LOG.info("END: Get Import T/O from DB : {} by: {}", importTurnoverGetRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
