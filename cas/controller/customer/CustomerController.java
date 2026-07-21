package com.itechro.cas.controller.customer;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.domain.customer.CustomerEvaluation;
import com.itechro.cas.model.dto.customer.*;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperSummaryDTO;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperSummarySearchRQ;
import com.itechro.cas.model.dto.integration.request.LoadKalyptoDataRQ;
import com.itechro.cas.model.dto.integration.request.customerstatistic.CustomerAccountStatRQ;
import com.itechro.cas.model.dto.integration.request.customerstatistic.CustomerCasServiceAccountStatRQ;
import com.itechro.cas.model.dto.integration.response.AccountStat.dtos.accountstatistic.CustomerAccountStatisticResponseDTO;
import com.itechro.cas.model.dto.integration.response.AccountStat.dtos.advancedportfolio.AdvancePortfolioResponseDTO;
import com.itechro.cas.model.dto.integration.response.AccountStat.dtos.casstat.CasStatResponseDTO;
import com.itechro.cas.model.dto.integration.response.AccountStat.dtos.depositdetails.DepositDetailResponseDTO;
import com.itechro.cas.model.dto.integration.response.CustomerFacilityDetailResponseDTO;
import com.itechro.cas.model.dto.integration.response.CustomerStatistics.ExecuteFinacleScriptCustomDataResponseDTO;
import com.itechro.cas.model.dto.integration.response.KalyptoRS;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.customer.CustomerService;
import com.itechro.cas.service.integration.IntegrationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "${api.prefix}/customer")
public class CustomerController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;

    @Autowired
    IntegrationService integrationService;

    @Autowired
    ModelMapper modelMapper;

    @ResponseExceptionHandler
    @RequestMapping(value = "/getFacilityDetailResponses", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CustomerFacilityDetailResponseDTO> getFacilityDetailResponses(@RequestBody SearchCustomerRQ searchRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search facility Detail Responses : {} by user {}", searchRQ, credentialsDTO.getUserID());

        CustomerFacilityDetailResponseDTO leadDTOPage = this.customerService.loadFacilityDetailList(searchRQ, credentialsDTO);

        LOG.info("END : Search facility Detail Responses : {} by user {}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(leadDTOPage, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/searchCustomerFrom360", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Customer360DTO> searchCustomerFrom360(@RequestBody SearchCustomerRQ searchRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search customer Detail Responses : {} by user {}", searchRQ, credentialsDTO.getUserID());

        Customer360DTO leadDTOPage = this.customerService.searchCustomerFrom360(searchRQ, credentialsDTO);

        if(leadDTOPage.getCustomerFinancialID() == null){
            LOG.error("Error in customer details retrieval: {}", leadDTOPage.getErrorMessage());
            leadDTOPage.setErrorMessage("Invalid customer response from Finacle");
            return new ResponseEntity<>(leadDTOPage, HttpStatus.NOT_FOUND);
        }

        LOG.info("END : Search customer Detail Responses : {} by user {}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(leadDTOPage, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCustomerDetailFromBank", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CustomerDTO> getCustomerDetailFromBank(@RequestBody SearchCustomerRQ searchRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search customer Detail Responses : {} by user {}", searchRQ, credentialsDTO.getUserID());
        CustomerDTO customerDetailFromBank = this.integrationService.getCustomerDetailFromBank(searchRQ, credentialsDTO);
        if (customerDetailFromBank == null) {
            customerDetailFromBank = new CustomerDTO();
        }
        LOG.info("END : Search customer Detail Responses : {} by user {}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(customerDetailFromBank, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedFacilityPaperSummary", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<FacilityPaperSummaryDTO>> getPagedFacilityPaperSummary(@RequestBody FacilityPaperSummarySearchRQ roleSearchRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Search paged facility paper summary : {} , user {}", roleSearchRQ, credentialsDTO.getUserID());

        Page<FacilityPaperSummaryDTO> pagedRoles = this.customerService.getPagedFacilityPaperDTO(roleSearchRQ);

        LOG.info("END : Search paged facility paper summary : {} , user {}", roleSearchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(pagedRoles, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getKalyptoDetail", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<KalyptoRS> getKalyptoDetail(@RequestBody LoadKalyptoDataRQ loadKalyptoDataRQ) throws AppsException {


        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Kalypto details by customer data {} : user ID{}", loadKalyptoDataRQ, credentialsDTO.getUserID());

        KalyptoRS responseDTO = this.integrationService.getKalyptoPageDetail(loadKalyptoDataRQ, credentialsDTO);

        LOG.info("END : Get Kalypto details by customer data {} : user ID{}", loadKalyptoDataRQ, credentialsDTO.getUserID());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedCustomers", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<CustomerDTO>> getPagedCustomersDTO(@RequestBody CustomerSearchRQ searchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : search customer data {} : user ID{}", searchRQ, credentialsDTO.getUserID());

        Page<CustomerDTO> responsePageData = this.customerService.getPagedCustomerDTO(searchRQ);

        LOG.info("END : search customer data {} : user ID{}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(responsePageData, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedCustomersForJoiningParties", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<CustomerDTO>> getPagedCustomersForJoiningParties(@RequestBody CustomerSearchRQ searchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : search customer data for joining parities {} : user ID{}", searchRQ, credentialsDTO.getUserID());

        Page<CustomerDTO> responsePageData = this.customerService.getPagedCustomersForJoiningParties(searchRQ, credentialsDTO);

        LOG.info("END : search customer data for joining parities {} : user ID{}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(responsePageData, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCustomerByID/{customerID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<CustomerDTO> getPagedCustomersDTO(@PathVariable Integer customerID) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get customer by ID {} : user ID{}", customerID, credentialsDTO.getUserID());

        CustomerDTO response = this.customerService.getCustomerByID(customerID);

        LOG.info("END :  get customer by ID {} : user ID{}", customerID, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getCustomerDTOListByIDs", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<CustomerDTO>> getCustomerDTOListByIDs(@RequestBody List<Integer> customerIDs) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get customer list: {} by user {}", customerIDs, credentialsDTO.getUserID());

        List<CustomerDTO> clusters = this.customerService.getCustomerDTOListByIDs(customerIDs);

        LOG.info("END : get customer list: {} by user {}", customerIDs, credentialsDTO.getUserID());

        return new ResponseEntity<>(clusters, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateCustomerDTO", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CustomerDTO> saveOrUpdateCustomerDTO(@RequestBody CustomerDTO customerDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or update customer: {} by user {}", customerDTO, credentialsDTO.getUserID());

        CustomerDTO response = this.customerService.saveOrUpdateCustomerDTO(customerDTO, credentialsDTO);

        LOG.info("END : Save or update customer: {} by user {}", customerDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getConsumerComprehensive", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<String> getConsumerComprehensive() throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("INFO : Deprecated Service : {} ", credentialsDTO.getUserID());
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCorporateComprehensive", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<String> getCorporateComprehensive() throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("INFO : Deprecated Service : {} ", credentialsDTO.getUserID());
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCustomerAccountStatistic", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CustomerAccountStatisticResponseDTO> getCustomerAccountStatisticResponse(@RequestBody CustomerAccountStatRQ customerAccountStatRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Customer Account Statistics list by user {}", credentialsDTO.getUserID());

        CustomerAccountStatisticResponseDTO responseListDTO = integrationService.getCustomerAccountStatisticResponse(customerAccountStatRQ, credentialsDTO);

        LOG.info("END : Get Customer Account Statistics : {} , by user {}", responseListDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(responseListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCasStat", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CasStatResponseDTO> getCasStat(@RequestBody CustomerCasServiceAccountStatRQ customerCasServiceAccountStatRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get cas stat by user {}", credentialsDTO.getUserID());

        CasStatResponseDTO responseListDTO = integrationService.getCasStatResponse(customerCasServiceAccountStatRQ, credentialsDTO);

        LOG.info("END : Get cas stat : {} , by user {}", responseListDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(responseListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCustomerDepositDetails", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<DepositDetailResponseDTO> getCustomerDepositDetails(@RequestBody CustomerAccountStatRQ customerAccountStatRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get customer Deposit Details by user {}", credentialsDTO.getUserID());

        DepositDetailResponseDTO depositDetailResponseDTO = integrationService.getDepositDetailsResponse(customerAccountStatRQ, credentialsDTO);

        LOG.info("END : Get customer Deposit Details : {} , by user {}", depositDetailResponseDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(depositDetailResponseDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCustomerFacilityDetailsByAccountNumber", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<AdvancePortfolioResponseDTO> getCustomerFacilityDetailsByAccountNumber(@RequestBody CustomerCasServiceAccountStatRQ customerCasServiceAccountStatRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get customer facility details by user {}", credentialsDTO.getUserID());

        AdvancePortfolioResponseDTO advancePortfolioResponseDTO = integrationService.getCustomerFacilityDetails(customerCasServiceAccountStatRQ, credentialsDTO);

        LOG.info("END : Get customer facility details : {} , by user {}", advancePortfolioResponseDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(advancePortfolioResponseDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/refreshCustomerDetailFromBank", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Customer360DTO> refreshCustomerDetailFromBank(@RequestBody SearchCustomerRQ searchRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Refresh customer Detail : {} by user {}", searchRQ, credentialsDTO.getUserID());
        Customer360DTO customer360DTO = this.customerService.refreshCustomerDetailFromBank(searchRQ, credentialsDTO);
        LOG.info("END : Refresh customer Detail : {} by user {}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(customer360DTO, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getTranDetForCashFlow", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ExecuteFinacleScriptCustomDataResponseDTO> getTranDetForCashFlow(@RequestBody CustomerAccountStatRQ customerAccountStatRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Finacle customer cash flow details by user {}", credentialsDTO.getUserID());

//        ExecuteFinacleScriptCustomDataResponseDTO executeFinacleScriptCustomDataResponseDTO = integrationService.getTransactionDetailsForCashFlow(customerTranDetCashFlowRQ, credentialsDTO);
        ExecuteFinacleScriptCustomDataResponseDTO executeFinacleScriptCustomDataResponseDTO = this.customerService.getTranDetForCashFlow(customerAccountStatRQ, credentialsDTO);

        LOG.info("END : Get Finacle customer cash flow details : {} , by user {}", executeFinacleScriptCustomDataResponseDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(executeFinacleScriptCustomDataResponseDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCustomerEvaluationList", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CustomerEvaluationDTO>> getActiveApprovedUpcTemplateDtoList() throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: get active customer evaluation: {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        List<CustomerEvaluation> customerEvaluationList = customerService.getCustomerEvaluationList();

        List<CustomerEvaluationDTO> customerEvaluationDTOList = customerEvaluationList.stream().map(customerEvaluation -> modelMapper.map(customerEvaluation, CustomerEvaluationDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(customerEvaluationDTOList, HttpStatus.OK);
    }

    //get customer evaluation by id method
//    @ResponseExceptionHandler
//    @RequestMapping(value = "/getCustomerEvaluationListById/{customerId}", headers = "Accept=application/json", method = RequestMethod.GET)
//    public ResponseEntity<List<CustomerEvaluationListDTO>> getCustomerEvaluationListById(@PathVariable String customerId) throws Exception{
//
//        CredentialsDTO credentialsDTO = getCredentialsDTO();
//
//        LOG.info("START : get customer by ID {} : user ID{}", customerId, credentialsDTO.getUserID());
//
//        List<CustomerEvaluationListDTO
//                > response = this.customerService.getCustomerEvaluationListById(customerId, credentialsDTO);
//
//        if(response != null && !response.isEmpty()){
//            Collections.sort(response, Collections.reverseOrder());
//        }
//
//        LOG.info("END: getting customer evaluations for : {} by: {}", customerId, credentialsDTO.getUserName());
//
//        LOG.info("END: Response", response);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCustomerEvaluationListByCIFId/{customerId}/{customerEvaluationId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CustomerEvaluationDTO>> getCustomerEvaluationById(@PathVariable String customerId, @PathVariable Integer customerEvaluationId) throws Exception{

        CredentialsDTO credentialsDTO = getCredentialsDTO();


        LOG.info("START : get customer by ID {} : user ID{}", customerId, customerEvaluationId, credentialsDTO.getUserID());

        List<CustomerEvaluationDTO> response = this.customerService.getCustomerEvaluationById(customerId, customerEvaluationId, credentialsDTO);

        if(response != null && !response.isEmpty()){
            Collections.sort(response, Collections.reverseOrder());
        }

        LOG.info("END: getting customer evaluations for : {} by: {}", customerId,customerEvaluationId, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @ResponseExceptionHandler
    @RequestMapping(value = "/getCustomerEvaluationAnswers", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CustomerEvaluationAnswersDTO> getReviewCommentFromFPIDAndUpmID(@RequestBody SearchCustomerEvaluationRQ searchCustomerEvaluationRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: getting FP review comments for : {} by: {}", searchCustomerEvaluationRQ, credentialsDTO.getUserName());

        CustomerEvaluationAnswersDTO response = this.customerService.getAnswers(searchCustomerEvaluationRQ);

        LOG.info("END: getting FP review comments for : {} by: {}", searchCustomerEvaluationRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getEvaluationScore/{customerId}/{customerEvaluationId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CustomerEvaluationScoreDTO>> getEvaluationScore(@PathVariable String customerId, @PathVariable Integer customerEvaluationId) throws Exception{

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get customer by ID {} : user ID{}", customerId, customerEvaluationId, credentialsDTO.getUserID());

        List<CustomerEvaluationScoreDTO> response = this.customerService.getCustomerEvaluationDetails(customerId, customerEvaluationId, credentialsDTO);

        if(response != null && !response.isEmpty()){
            Collections.sort(response, Collections.reverseOrder());
        }

        LOG.info("END: getting customer evaluations for : {} by: {}", customerId,customerEvaluationId, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
