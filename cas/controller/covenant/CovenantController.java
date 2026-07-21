package com.itechro.cas.controller.covenant;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.covenant.CustomerCovenant;
import com.itechro.cas.model.dto.covenants.*;
import com.itechro.cas.model.dto.customer.CustomerCovenantRQ;
import com.itechro.cas.model.dto.customer.request.CustomerCovenantUpdateDTO;
import com.itechro.cas.model.dto.customer.response.CovenantDetailResDTO;
import com.itechro.cas.model.dto.customer.response.CustomerCovenantDeleteResponse;
import com.itechro.cas.model.dto.customer.response.CustomerCovenantSaveResponseDTO;
import com.itechro.cas.model.dto.customer.response.CustomerCovenantUpdateResponseDTO;
import com.itechro.cas.model.dto.facilitypaper.request.FinalDTO;
import com.itechro.cas.model.dto.facilitypaper.response.ApplicationCovenantPostDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.covenant.CovenantService;
import com.itechro.cas.service.integration.IntegrationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 *
 * @author tharushi
 */
@RestController
@RequestMapping(value = "${api.prefix}/covenant")
public class CovenantController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(CovenantController.class);

    @Autowired
    IntegrationService integrationService;

    @Autowired
    private CovenantService covenantService;

    @Autowired
    private ModelMapper modelMapper;

    @ResponseExceptionHandler
    @PostMapping(value = "/getCovenantsDetails", headers = "Accept=application/json")
    public ResponseEntity<CovenantDetailsFinacleDTO> getCovenantDetailsFromFinacle(@RequestBody LoadCovenantDataDTO request) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get covenant details from finacle : user {}", credentialsDTO.getUserID());

        CovenantDetailsFinacleDTO covenantDetailsFinacleDTO = covenantService.getCovenantDetailsFromFinacle(request, credentialsDTO);

        LOG.info("END : Get covenant details from finacle : user {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(covenantDetailsFinacleDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getCovenantList", headers = "Accept=application/json")
    public ResponseEntity<CovenantListDTO> getCovenantList(@RequestBody CustomerCovenantRQ customerCovenantRQ) throws AppsException {


        CredentialsDTO credentialsDTO = getCredentialsDTO();

        CovenantListDTO responseDTO = covenantService.getCovenantList(customerCovenantRQ, credentialsDTO);

        LOG.info("START : get customer covenant from service: {}", responseDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @GetMapping(value = "/getCovenantListByFpRefNumber/{fpRefNumber}", headers = "Accept=application/json")
    public ResponseEntity<List<CustomerCovenantResponseDTO>> getCovenantListByRequestUUID(@PathVariable String fpRefNumber) throws AppsException{

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        List<CustomerCovenantResponseDTO> customerCovenantList = covenantService.getCustomerCovenantList(fpRefNumber, credentialsDTO);

        return new ResponseEntity<>(customerCovenantList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @GetMapping(value = "/getFacilityCovenantList/{fpRefNumber}", headers = "Accept=application/json")
    public ResponseEntity<List<FinalDTO>> getFacilityCovenantList(@PathVariable String fpRefNumber) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        List<FinalDTO> covenants = covenantService.getFacilityCovenantList(fpRefNumber, credentialsDTO);

        //List<FacilityCovenantReqDTO> applicationLevelCovenants = covenants.stream().map(facilityCovenants -> modelMapper.map(facilityCovenants, FacilityCovenantReqDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(covenants, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveCustomerCovenantDetails", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> saveCustomerCovenantDetails(@Validated @RequestBody CustomerCovenantSaveDTO request) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or update customer: {} by user {}", request, credentialsDTO.getUserID());

        List<CustomerCovenantSaveResponse> saveCustomerCovenant = covenantService.saveCustomerCovenant(request, credentialsDTO);

        LOG.info("END : Save or update customer: {} by user {}", saveCustomerCovenant, credentialsDTO.getUserID());

        return new ResponseEntity<>(saveCustomerCovenant, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/saveApplicationCovenantDetails", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> saveApplicationCovenant(@Validated @RequestBody ApplicationCovenantReqDTO request) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Save or update customer: {} by user {}", request, credentialsDTO.getUserName());

        ApplicationCovenantPostDTO response = covenantService.saveOrUpdateApplicationCovenant(request, credentialsDTO);

        LOG.info("END: Save or update customer: {} by user {}", response, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCustomerCovenantByID/{customerCovenantId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<CustomerCovenantUpdateResponseDTO> viewCustomerCovenantById(@PathVariable int customerCovenantId) throws AppsException {

        CustomerCovenant customerCovenant = covenantService.findCustomerCovenantById((customerCovenantId));

        CustomerCovenantUpdateResponseDTO customerCovenantUpdateResponseDTO = modelMapper.map(customerCovenant, CustomerCovenantUpdateResponseDTO.class);

        return new ResponseEntity<>(customerCovenantUpdateResponseDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateCovenant", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CustomerCovenantSaveResponseDTO> updateCustomerCovenant( @Validated @RequestBody CustomerCovenantUpdateDTO request) throws AppsException{
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Update customer covenant : {} by user {}", request, credentialsDTO.getUserID());

        CustomerCovenantSaveResponseDTO updateCustomerCovenant = covenantService.updateCustomerCovenant(request);

        LOG.info("END : Update customer covenant : {} by user {}", request, credentialsDTO.getUserID());

        return new ResponseEntity<>(updateCustomerCovenant, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getCovenantsByFpRefNumber", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> getResponse(@RequestBody CustomerCovenantSaveDTO customerCovenantSaveDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Pass the Customer Covenants to finacle : {} by user {}", customerCovenantSaveDTO.getCasReference(), credentialsDTO.getUserID());

        CovenantDetailResDTO covenantResponseDTO  = covenantService.getCovenantResponse(customerCovenantSaveDTO, credentialsDTO);

        LOG.info("END : Pass the Customer Covenants to finacle : {} by user {}", covenantResponseDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(covenantResponseDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/deleteCovenant/{customerCovenantId}/{createdUserDisplayName}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<CustomerCovenantDeleteResponse> deleteCovenants(@PathVariable Integer customerCovenantId, @PathVariable String createdUserDisplayName) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        Integer deleteCovenantId = covenantService.deleteCovenant(customerCovenantId, createdUserDisplayName, credentialsDTO);
        CustomerCovenantDeleteResponse customerCovenantDeleteResponse = new CustomerCovenantDeleteResponse();
        customerCovenantDeleteResponse.setCustomerCovenantId(deleteCovenantId);
        return new ResponseEntity<>(customerCovenantDeleteResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/addCommentToCovenant", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<NoneComplianceCovenantDTO> addCommentToCovenant(@RequestBody NoneComplianceCovenantDTO noneComplianceCovenantDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Add Comment To Covenant : {} by user {}", noneComplianceCovenantDTO, credentialsDTO.getUserID());

        NoneComplianceCovenantDTO response  = covenantService.addEditCommentToCovenant(noneComplianceCovenantDTO, credentialsDTO);

        LOG.info("END : Add Comment To Covenant : {} by user {}", response, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCovenantCommentList/{facilityPaperId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<NoneComplianceCovenantDTO>> getCovenantCommentList(@PathVariable Integer facilityPaperId) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Covenant Comment List : {} by user {}", facilityPaperId, credentialsDTO.getUserID());

        List<NoneComplianceCovenantDTO> response  = covenantService.getCovenantCommentList(facilityPaperId);

        LOG.info("END : Get Covenant Comment List : {} by user {}", response, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/getCusApplicableCovenantList/{fpRefNumber}/{facilityId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CusApplicableCovenantDTO>> getCusApplicableCovenantList(@PathVariable String fpRefNumber, @PathVariable Integer facilityId) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        List<CusApplicableCovenantDTO> covenants = covenantService.getCusApplicableCovenantList(fpRefNumber, facilityId, credentialsDTO);

        return new ResponseEntity<>(covenants, HttpStatus.OK);
    }

    @RequestMapping(value = "/saveOrUpdateAcctIdWithFacilityId", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ExistingFacilityCovenantsDTO> saveOrUpdateAcctIdWithFacilityId(@RequestBody ExistingFacilityCovenantsDTO request) throws AppsException {

        ExistingFacilityCovenantsDTO response = covenantService.saveOrUpdateAcctIdWithFacilityId(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllExistingFacilityCovenants/{facilityPaperId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<ExistingFacilityCovenantsDTO>> getAllExistingFacilityCovenants(@PathVariable Integer facilityPaperId) throws AppsException {

        List<ExistingFacilityCovenantsDTO> response = covenantService.getAllExistingFacilityCovenants(facilityPaperId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
