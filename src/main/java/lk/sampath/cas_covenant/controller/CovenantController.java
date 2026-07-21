package lk.sampath.cas_covenant.controller;

import java.util.List;
import lk.sampath.cas_covenant.controller.base_controller.StandardResponse;
import lk.sampath.cas_covenant.dto.*;
import lk.sampath.cas_covenant.exception.ApiRequestException;
import lk.sampath.cas_covenant.service.CustomerCovenantService;
import lk.sampath.cas_covenant.service.FacilityCovenantService;
import lk.sampath.cas_covenant.service.IntegrationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@CrossOrigin("*")
@RequestMapping("api/covenant")
public class CovenantController {

  private final CustomerCovenantService customerCovenantService;
  private final FacilityCovenantService facilityCovenantService;
  private final IntegrationService integrationService;

  @Autowired
  public CovenantController(CustomerCovenantService customerCovenantService, FacilityCovenantService facilityCovenantService, IntegrationService integrationService) {
    this.customerCovenantService = customerCovenantService;
      this.facilityCovenantService = facilityCovenantService;
      this.integrationService = integrationService;
  }


  /**
   * Customer Covenants
    * 1. Save Customer Covenants
    * 2. Update Customer Covenants
    * 3. Get All Customer Covenants by Facility Paper ID
   */

  @PostMapping("/saveCustomerCovenant")
  public ResponseEntity<StandardResponse<List<CustomerCovenantDTO>>> saveCustomerCovenant(@RequestBody List<CustomerCovenantDTO> request) throws ApiRequestException {

    log.info("START | saveCustomerCovenant - CovenantController | request : {}", request);

    ResponseEntity<StandardResponse<List<CustomerCovenantDTO>>> response = customerCovenantService.saveCustomerCovenant(request);

    log.info("END | saveComprehensiveLead - CovenantController | response : {}", response);

    return ResponseEntity.ok().body(response.getBody());
  }

  @PostMapping("/updateCustomerCovenant")
  public ResponseEntity<StandardResponse<CustomerCovenantDTO>> updateCustomerCovenant(@RequestBody CustomerCovenantDTO request) throws ApiRequestException {

    log.info("START | updateCustomerCovenant - CovenantController | request : {}", request);

    ResponseEntity<StandardResponse<CustomerCovenantDTO>> response = customerCovenantService.updateCustomerCovenant(request);

    log.info("END | updateCustomerCovenant - CovenantController | response : {}", response);

    return ResponseEntity.ok().body(response.getBody());
  }

  @GetMapping("/getAllCustomerCovenant/{facilityPaperId}")
  public ResponseEntity<StandardResponse<List<CustomerCovenantDTO>>> getAllCustomerCovenant(@PathVariable Integer facilityPaperId) throws ApiRequestException {

    log.info("START | getAllCustomerCovenant - CovenantController | request : {}", facilityPaperId);

    ResponseEntity<StandardResponse<List<CustomerCovenantDTO>>> response = customerCovenantService.getAllCustomerCovenant(facilityPaperId);

    log.info("END | getAllCustomerCovenant - CovenantController | response : {}", response);

    return ResponseEntity.ok().body(response.getBody());
  }

  /**
   * Facility Covenants
   * 1. Save Facility Covenants
   * 2. Update Facility Covenants
   * 3. Get All Facility Covenants by Facility Paper ID
   */

  @PostMapping("/saveFacilityCovenants")
  public ResponseEntity<StandardResponse<List<FacilityCovenantDTO>>> saveFacilityCovenants(@RequestBody List<FacilityCovenantDTO> request) throws ApiRequestException {

    log.info("START | saveFacilityCovenants - CovenantController | request : {}", request);

    ResponseEntity<StandardResponse<List<FacilityCovenantDTO>>> response = facilityCovenantService.saveFacilityCovenant(request);

    log.info("END | saveFacilityCovenants - CovenantController | response : {}", response);

    return ResponseEntity.ok().body(response.getBody());
  }

  @PostMapping("/updateFacilityCovenant")
  public ResponseEntity<StandardResponse<FacilityCovenantDTO>> updateFacilityCovenant(@RequestBody FacilityCovenantDTO request) throws ApiRequestException {

    log.info("START | updateFacilityCovenant - CovenantController | request : {}", request);

    ResponseEntity<StandardResponse<FacilityCovenantDTO>> response = facilityCovenantService.updateFacilityCovenant(request);

    log.info("END | updateFacilityCovenant - CovenantController | response : {}", response);

    return ResponseEntity.ok().body(response.getBody());
  }

  @GetMapping("/getAllFacilityCovenant/{facilityPaperId}")
  public ResponseEntity<StandardResponse<List<FacilityCovenantDTO>>> getAllFacilityCovenant(@PathVariable Integer facilityPaperId) throws ApiRequestException {

    log.info("START | getAllFacilityCovenant - CovenantController | request : {}", facilityPaperId);

    List<FacilityCovenantDTO> response = facilityCovenantService.getAllFacilityCovenant(facilityPaperId);

    log.info("END | getAllFacilityCovenant - CovenantController | response : {}", response);

    return new ResponseEntity<>(
            new StandardResponse<>(true, "Fetched Successfully", response),
            HttpStatus.OK
    );
  }

  @PostMapping(value = "/getCovenantsDetails")
  public ResponseEntity<StandardResponse<CovenantDetailsFinacleDTO>> getCovenantDetailsFromFinacle(@RequestBody LoadCovenantDataDTO request) throws Exception {

    log.info("START : Get covenant details from finacle : request {}",request);

    CovenantDetailsFinacleDTO response = customerCovenantService.getCovenantDetailsFromFinacle(request);

    log.info("END : Get covenant details from finacle : response {}", response.getCovenant().size());

    return new ResponseEntity<>(
            new StandardResponse<>(true, "Fetched Successfully", response),
            HttpStatus.OK
    );
  }

  @PostMapping(value = "/getCovenantList")
  public ResponseEntity<StandardResponse<CovenantListDTO>> getCovenantList(@RequestBody CustomerCovenantRQ customerCovenantRQ) throws ApiRequestException {
    log.info("START : get customer covenant from service: {}", customerCovenantRQ);
    CovenantListDTO response = integrationService.getCovenantList(customerCovenantRQ);
    log.info("END : get customer covenant from service: {}");
    return new ResponseEntity<>(
            new StandardResponse<>(true, "Fetched Successfully", response),
            HttpStatus.OK
    );
  }

  @PostMapping(value = "/addCommentToCovenant")
  public ResponseEntity<StandardResponse<NoneComplianceCovenantDTO>> addCommentToCovenant(@RequestBody NoneComplianceCovenantDTO noneComplianceCovenantDTO) throws ApiRequestException {
    log.info("START : Add Comment To Covenant : {}", noneComplianceCovenantDTO);
    NoneComplianceCovenantDTO response  = customerCovenantService.addEditCommentToCovenant(noneComplianceCovenantDTO);
    log.info("END : Add Comment To Covenant : {} by user {}", response);
    return new ResponseEntity<>(
            new StandardResponse<>(true, "Fetched Successfully", response),
            HttpStatus.OK
    );
  }

  @GetMapping(value = "/getCovenantCommentList/{facilityPaperId}")
  public ResponseEntity<StandardResponse<List<NoneComplianceCovenantDTO>>> getCovenantCommentList(@PathVariable Integer facilityPaperId) throws Exception {
    log.info("START : Get Covenant Comment List : {} by user {}", facilityPaperId);
    List<NoneComplianceCovenantDTO> response  = customerCovenantService.getCovenantCommentList(facilityPaperId);
    log.info("END : Get Covenant Comment List : {} by user {}", response);
    return new ResponseEntity<>(
            new StandardResponse<>(true, "Fetched Successfully", response),
            HttpStatus.OK
    );
  }

}
