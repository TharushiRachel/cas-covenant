package lk.sampath.cas_covenant.service.impl;

import java.util.*;
import java.util.stream.Collectors;
import lk.sampath.cas_covenant.common.PropertyFileValue;
import lk.sampath.cas_covenant.controller.base_controller.StandardResponse;
import lk.sampath.cas_covenant.dto.*;
import lk.sampath.cas_covenant.entity.CustomerCovenant;
import lk.sampath.cas_covenant.entity.NoneComplianceCovenant;
import lk.sampath.cas_covenant.entity.facilityPaper.FacilityPaper;
import lk.sampath.cas_covenant.enums.CovenantStatus;
import lk.sampath.cas_covenant.enums.ErrorEnums;
import lk.sampath.cas_covenant.enums.YesNoStatus;
import lk.sampath.cas_covenant.exception.ApiRequestException;
import lk.sampath.cas_covenant.repository.CustomerCovenantRepository;
import lk.sampath.cas_covenant.repository.FacilityPaperRepository;
import lk.sampath.cas_covenant.repository.NoneComplianceCovenantRepository;
import lk.sampath.cas_covenant.service.CustomerCovenantService;
import lk.sampath.cas_covenant.service.FacilityCovenantService;
import lk.sampath.cas_covenant.service.IntegrationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Log4j2
public class CustomerCovenantServiceImpl implements CustomerCovenantService {

  private final CustomerCovenantRepository customerCovenantRepository;
  private final PropertyFileValue propertyFileValue;
  private final FacilityPaperRepository facilityPaperRepository;
  private final IntegrationService integrationService;
  private final NoneComplianceCovenantRepository noneComplianceCovenantRepository;
  private final FacilityCovenantService facilityCovenantService;

  @Autowired
  public CustomerCovenantServiceImpl(
          CustomerCovenantRepository customerCovenantRepository,
          PropertyFileValue propertyFileValue,
          FacilityPaperRepository facilityPaperRepository, IntegrationService integrationService, NoneComplianceCovenantRepository noneComplianceCovenantRepository, FacilityCovenantService facilityCovenantService) {
    this.customerCovenantRepository = customerCovenantRepository;
    this.propertyFileValue = propertyFileValue;
    this.facilityPaperRepository = facilityPaperRepository;
      this.integrationService = integrationService;
      this.noneComplianceCovenantRepository = noneComplianceCovenantRepository;
      this.facilityCovenantService = facilityCovenantService;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public ResponseEntity<StandardResponse<List<CustomerCovenantDTO>>> saveCustomerCovenant(List<CustomerCovenantDTO> dtoList) throws ApiRequestException {

    log.info("START | saveCustomerCovenants");

    if (dtoList == null || dtoList.isEmpty()) {
      throw new ApiRequestException("Customer covenant list cannot be null or empty");
    }

    List<CustomerCovenant> entityList = new ArrayList<>();
    List<CustomerCovenantDTO> responseList = new ArrayList<>();

    try {
      // 1. Extract facility reference
      Integer facilityPaperID = dtoList.get(0).getFacilityPaperId();

      // 2. Get max display order directly (OPTIMIZED)
      Integer maxDisplayOrder = customerCovenantRepository.findMaxDisplayOrderByFacilityPaperID(facilityPaperID);

      int displayOrder = (maxDisplayOrder != null) ? maxDisplayOrder + 1 : 1;

      // 3. Fetch all FacilityPapers in ONE query
      Set<Integer> facilityIds = dtoList.stream().map(CustomerCovenantDTO::getFacilityPaperId).collect(Collectors.toSet());

      Map<Integer, FacilityPaper> facilityMap = facilityPaperRepository.findAllById(facilityIds).stream()
              .collect(Collectors.toMap(FacilityPaper::getFacilityPaperID, fp -> fp));

      // 4. Process DTOs
      for (CustomerCovenantDTO dto : dtoList) {
        try {
          FacilityPaper facilityPaper = facilityMap.get(dto.getFacilityPaperId());

          if (facilityPaper == null) {
            throw new ApiRequestException("Invalid FacilityPaper ID: " + dto.getFacilityPaperId());
          }

          CustomerCovenant entity = new CustomerCovenant();

          entity.setRequestUUID(propertyFileValue.getRequestUUID());
          entity.setCreatedBy(dto.getCreatedBy());
          entity.setCreatedUserDisplayName(dto.getCreatedUserDisplayName());
          entity.setCreatedDate(new Date());

          entity.setCustomerFinancialID(dto.getCustomerFinancialID());
          entity.setDisbursementType(dto.getDisbursementType());
          entity.setApplicableType(dto.getApplicableType());

          entity.setFacilityPaper(facilityPaper);

          entity.setCovenant_Code(dto.getCovenant_Code());
          entity.setCovenant_Description(dto.getCovenant_Description());
          entity.setCovenant_Frequency(dto.getCovenant_Frequency());
          entity.setCovenant_Due_Date(dto.getCovenant_Due_Date());
          entity.setNoFrequencyDueDate(dto.getNoFrequencyDueDate());

          entity.setStatus(CovenantStatus.Active);
          entity.setIsExists(YesNoStatus.N);

          entity.setDisplayOrder(displayOrder++);

          entityList.add(entity);

        } catch (Exception ex) {
          log.warn("Skipping invalid DTO: {} | {}", dto.getCustomerFinancialID(), ex.getMessage());
        }
      }

      // 5. Batch save
      List<CustomerCovenant> saved = customerCovenantRepository.saveAll(entityList);

      // 6. Map response
      saved.forEach(e -> responseList.add(new CustomerCovenantDTO(e)));

      log.info("Saved {} records, skipped {}", saved.size(), dtoList.size() - saved.size());

    } catch (Exception e) {
      log.error("Batch save failed", e);
      throw new ApiRequestException("Failed to save customer covenants: " + e.getMessage());
    }

    return ResponseEntity.ok(
        new StandardResponse<>(
            ErrorEnums.SUCCESS_CODE.getStatus(), ErrorEnums.SUCCESS_CODE.getLabel(), responseList));
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public ResponseEntity<StandardResponse<CustomerCovenantDTO>> updateCustomerCovenant(
          CustomerCovenantDTO dto) throws ApiRequestException {

    log.info("START | updateCustomerCovenant | ID: {}", dto.getCustomerCovenantId());

    if (dto.getCustomerCovenantId() == null) {
      throw new ApiRequestException("ID is required for update");
    }

    try {
      // 1. Fetch existing record
      CustomerCovenant entity = customerCovenantRepository.findById(dto.getCustomerCovenantId())
              .orElseThrow(() -> new ApiRequestException(
                      "CustomerCovenant not found for ID: " + dto.getCustomerCovenantId()));

      // 2. Update fields (ONLY mutable ones)
      entity.setCustomerFinancialID(dto.getCustomerFinancialID());
      entity.setDisbursementType(dto.getDisbursementType());
      entity.setApplicableType(dto.getApplicableType());

      entity.setCovenant_Code(dto.getCovenant_Code());
      entity.setCovenant_Description(dto.getCovenant_Description());
      entity.setCovenant_Frequency(dto.getCovenant_Frequency());
      entity.setCovenant_Due_Date(dto.getCovenant_Due_Date());
      entity.setNoFrequencyDueDate(dto.getNoFrequencyDueDate());

      // Optional audit fields
      entity.setModifiedBy(dto.getCreatedBy());
      entity.setLastModifiedDate(new Date());

      // 3. Save
      CustomerCovenant updated = customerCovenantRepository.save(entity);

      // 4. Response
      CustomerCovenantDTO responseDTO = new CustomerCovenantDTO(updated);

      log.info("END | updateCustomerCovenant | ID: {}", dto.getCustomerCovenantId());

      return ResponseEntity.ok(
              new StandardResponse<>(
                      ErrorEnums.SUCCESS_CODE.getStatus(),
                      ErrorEnums.SUCCESS_CODE.getLabel(),
                      responseDTO
              )
      );

    } catch (Exception e) {
      log.error("Error updating covenant ID {}: {}", dto.getCustomerCovenantId(), e.getMessage(), e);
      throw new ApiRequestException("Failed to update customer covenant: " + e.getMessage());
    }
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  public ResponseEntity<StandardResponse<List<CustomerCovenantDTO>>> getAllCustomerCovenant(Integer facilityPaperId) throws ApiRequestException {

    log.info("START | getAllCustomerCovenant | FacilityPaperID: {}", facilityPaperId);

    if(facilityPaperId == null) {
      throw new ApiRequestException("FacilityPaper ID is required");
    }

    try{
        List<CustomerCovenant> covenants = customerCovenantRepository.findCustomerCovenantsByFacilityPaperID(facilityPaperId);

        List<CustomerCovenantDTO> responseList = covenants.stream()
                .map(CustomerCovenantDTO::new)
                .collect(Collectors.toList());

        log.info("END | getAllCustomerCovenant | FacilityPaperID: {} | Records: {}", facilityPaperId, responseList.size());

        return ResponseEntity.ok(
                new StandardResponse<>(
                        ErrorEnums.SUCCESS_CODE.getStatus(),
                        ErrorEnums.SUCCESS_CODE.getLabel(),
                        responseList
                )
        );
    } catch (Exception e) {
        log.error("Error fetching covenants for FacilityPaperID {}: {}", facilityPaperId, e.getMessage(), e);
        throw new ApiRequestException("Failed to fetch customer covenants: " + e.getMessage());
    }

  }

  @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = ApiRequestException.class)
  public CovenantDetailsFinacleDTO getCovenantDetailsFromFinacle(LoadCovenantDataDTO loadCovenantDataDTO) throws ApiRequestException{

    String customerFinacleId = customerCovenantRepository.findCustomerFinancialId(loadCovenantDataDTO.getFacilityPaperId());
    loadCovenantDataDTO.setCustId(customerFinacleId);

    CovenantDetailsFinacleDTO covenantDetailsFinacleDTO = integrationService.getCovenantDetailsFromFinacle(loadCovenantDataDTO);

    if (covenantDetailsFinacleDTO != null && covenantDetailsFinacleDTO.getCovenant() != null) {
      List<CovenantDataDTO> covenantList = covenantDetailsFinacleDTO.getCovenant();

      covenantList.sort((c1, a1) -> {
        boolean isc_other = c1.getCovenantInq().stream().anyMatch(cov -> cov.getCovCod().endsWith("_OTH"));
        boolean isa_other = a1.getCovenantInq().stream().anyMatch(cov -> cov.getCovCod().endsWith("_OTH"));

        if (isc_other && !isa_other) return 1;
        if (!isc_other && isa_other) return -1;
        return 0;
      });

      covenantDetailsFinacleDTO.setCovenant(covenantList);

      // Get matching comments
      Map<String, NoneComplianceCovenantDTO> matchingComments = getMatchingCommentsMap(covenantDetailsFinacleDTO, loadCovenantDataDTO.getFacilityPaperId());

      // Add comments to CovenantInquiryDTO
      for (CovenantDataDTO covenantData : covenantList) {
        for (CovenantInquiryDTO inquiry : covenantData.getCovenantInq()) {
          if (matchingComments.containsKey(inquiry.getSrlNum())) {
            inquiry.setNonComplianceCovenantDTO(matchingComments.get(inquiry.getSrlNum()));
          }
        }
      }

      // Handle the -999 comment separately
      if (matchingComments.containsKey("-999")) {
        NoneComplianceCovenantDTO specialComment = matchingComments.get("-999");
        if(specialComment.getFacilityPaperId().equals(loadCovenantDataDTO.getFacilityPaperId())){
          covenantDetailsFinacleDTO.setSpecialComment(specialComment);
        }
      }

    }

    return covenantDetailsFinacleDTO;

  }

  private Map<String, NoneComplianceCovenantDTO> getMatchingCommentsMap(CovenantDetailsFinacleDTO covenantDetailsFinacleDTO, Integer facilityPaperId) throws ApiRequestException {
    log.info("Getting matching comments for facilityPaperId: {}", facilityPaperId);

    // Get the srlNumList and validate
    List<String> srlNumList = getSrlNumList(covenantDetailsFinacleDTO);
    if (srlNumList == null || srlNumList.isEmpty()) {
      log.warn("SrlNum list is null or empty");
      return Collections.emptyMap();
    }

    // Get the covenant comments and validate
    List<NoneComplianceCovenantDTO> covenantComments = getCovenantCommentList(facilityPaperId);
    if (covenantComments == null || covenantComments.isEmpty()) {
      log.warn("Covenant comments list is null or empty");
      return Collections.emptyMap();
    }

    // Create a map of serialNumber to comment
    return covenantComments.stream()
            .filter(comment ->
                    (comment.getSerialNumber() != null && srlNumList.contains(comment.getSerialNumber().toString()))
                            || (comment.getSerialNumber() != null && comment.getSerialNumber() == -999)
            )
            .collect(Collectors.toMap(
                    comment -> comment.getSerialNumber().toString(),
                    comment -> comment
            ));
  }

  public List<String> getSrlNumList(CovenantDetailsFinacleDTO covenantDetailsFinacleDTO) {
    log.info("Extracting SrlNum list from CovenantDetailsFinacleDTO");
    if (covenantDetailsFinacleDTO == null || covenantDetailsFinacleDTO.getCovenant() == null) {
      return Collections.emptyList();
    }

    List<String> srlNumList = covenantDetailsFinacleDTO.getCovenant().stream()
            .flatMap(covenantData -> covenantData.getCovenantInq().stream())
            .map(CovenantInquiryDTO::getSrlNum)
            .collect(Collectors.toList());

    log.info("SrlNum List: {}", srlNumList);
    return srlNumList;
  }

  @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = ApiRequestException.class)
  public List<NoneComplianceCovenantDTO> getCovenantCommentList(Integer facilityPaperId) throws ApiRequestException {
    List<NoneComplianceCovenant> noneComplianceCovenant = noneComplianceCovenantRepository.findByFacilityPaperId(facilityPaperId);

    if (noneComplianceCovenant == null) {
      throw new ApiRequestException("No comments found for Facility Paper ID: " + facilityPaperId);
    }

    List<NoneComplianceCovenantDTO> response = noneComplianceCovenant.stream()
            .map(NoneComplianceCovenantDTO::new)
            .collect(Collectors.toList());

    return response;
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ApiRequestException.class)
  public NoneComplianceCovenantDTO addEditCommentToCovenant(NoneComplianceCovenantDTO noneComplianceCovenantDTO) throws ApiRequestException {

    log.info("Adding comment to covenant: {}", noneComplianceCovenantDTO);

    NoneComplianceCovenant noneComplianceCovenant;

    if(noneComplianceCovenantDTO.getNonComplianceCovenantId() != null){
      noneComplianceCovenant = noneComplianceCovenantRepository.findById(noneComplianceCovenantDTO.getNonComplianceCovenantId()).orElseThrow(() -> new ApiRequestException("Covenant comment with ID " + noneComplianceCovenantDTO.getNonComplianceCovenantId() + " not found"));
      noneComplianceCovenant.setSerialNumber(noneComplianceCovenantDTO.getSerialNumber());
      noneComplianceCovenant.setFacilityPaperId(noneComplianceCovenantDTO.getFacilityPaperId());
      noneComplianceCovenant.setComment(noneComplianceCovenantDTO.getComment());
      noneComplianceCovenant.setAddedDate(new Date());
      noneComplianceCovenant.setAddedBy(noneComplianceCovenantDTO.getAddedBy());
      noneComplianceCovenant.setAddedUserDisplayName(noneComplianceCovenant.getAddedUserDisplayName());
      noneComplianceCovenant.setAddedUserId(noneComplianceCovenantDTO.getAddedUserId());
    }

    else {
      noneComplianceCovenant = new NoneComplianceCovenant();
      noneComplianceCovenant.setSerialNumber(noneComplianceCovenantDTO.getSerialNumber());
      noneComplianceCovenant.setFacilityPaperId(noneComplianceCovenantDTO.getFacilityPaperId());
      noneComplianceCovenant.setComment(noneComplianceCovenantDTO.getComment());
      noneComplianceCovenant.setAddedDate(new Date());
      noneComplianceCovenant.setAddedBy(noneComplianceCovenantDTO.getAddedBy());
      noneComplianceCovenant.setAddedUserDisplayName(noneComplianceCovenant.getAddedUserDisplayName());
      noneComplianceCovenant.setAddedUserId(noneComplianceCovenantDTO.getAddedUserId());
    }

    noneComplianceCovenantRepository.save(noneComplianceCovenant);

    NoneComplianceCovenantDTO response = new NoneComplianceCovenantDTO(noneComplianceCovenant);

    log.info("Comment added/edited successfully: {}", response);

    return response;
  }

}
