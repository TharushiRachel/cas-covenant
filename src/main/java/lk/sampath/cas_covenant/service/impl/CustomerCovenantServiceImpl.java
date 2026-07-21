package lk.sampath.cas_covenant.service.impl;

import java.util.*;
import java.util.stream.Collectors;
import lk.sampath.cas_covenant.common.PropertyFileValue;
import lk.sampath.cas_covenant.dto.*;
import lk.sampath.cas_covenant.entity.CustomerCovenant;
import lk.sampath.cas_covenant.entity.NoneComplianceCovenant;
import lk.sampath.cas_covenant.entity.facilityPaper.FacilityPaper;
import lk.sampath.cas_covenant.enums.CovenantStatus;
import lk.sampath.cas_covenant.enums.YesNoStatus;
import lk.sampath.cas_covenant.exception.ApiRequestException;
import lk.sampath.cas_covenant.repository.CustomerCovenantRepository;
import lk.sampath.cas_covenant.repository.FacilityPaperRepository;
import lk.sampath.cas_covenant.repository.NoneComplianceCovenantRepository;
import lk.sampath.cas_covenant.service.CustomerCovenantService;
import lk.sampath.cas_covenant.service.IntegrationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Log4j2
public class CustomerCovenantServiceImpl implements CustomerCovenantService {

  private static final String SPECIAL_COMMENT_SRL = "-999";
  private static final int SPECIAL_COMMENT_SERIAL = -999;

  private final CustomerCovenantRepository customerCovenantRepository;
  private final PropertyFileValue propertyFileValue;
  private final FacilityPaperRepository facilityPaperRepository;
  private final IntegrationService integrationService;
  private final NoneComplianceCovenantRepository noneComplianceCovenantRepository;

  public CustomerCovenantServiceImpl(
      CustomerCovenantRepository customerCovenantRepository,
      PropertyFileValue propertyFileValue,
      FacilityPaperRepository facilityPaperRepository,
      IntegrationService integrationService,
      NoneComplianceCovenantRepository noneComplianceCovenantRepository) {
    this.customerCovenantRepository = customerCovenantRepository;
    this.propertyFileValue = propertyFileValue;
    this.facilityPaperRepository = facilityPaperRepository;
    this.integrationService = integrationService;
    this.noneComplianceCovenantRepository = noneComplianceCovenantRepository;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public List<CustomerCovenantDTO> saveCustomerCovenant(List<CustomerCovenantDTO> dtoList)
      throws ApiRequestException {

    log.info("START | saveCustomerCovenants | count: {}", dtoList == null ? 0 : dtoList.size());

    if (dtoList == null || dtoList.isEmpty()) {
      throw new ApiRequestException("Customer covenant list cannot be null or empty");
    }

    try {
      Map<Integer, FacilityPaper> facilityMap = loadFacilityPaperMap(dtoList);
      Map<Integer, Integer> nextDisplayOrderByFacility = new HashMap<>();

      List<CustomerCovenant> entityList = new ArrayList<>(dtoList.size());
      for (CustomerCovenantDTO dto : dtoList) {
        FacilityPaper facilityPaper = requireFacilityPaper(facilityMap, dto.getFacilityPaperId());
        int displayOrder =
            nextDisplayOrder(nextDisplayOrderByFacility, dto.getFacilityPaperId());
        entityList.add(mapToEntity(dto, facilityPaper, displayOrder));
      }

      List<CustomerCovenantDTO> responseList =
          customerCovenantRepository.saveAll(entityList).stream()
              .map(CustomerCovenantDTO::new)
              .collect(Collectors.toList());

      log.info("END | saveCustomerCovenants | saved: {}", responseList.size());
      return responseList;

    } catch (ApiRequestException e) {
      throw e;
    } catch (Exception e) {
      log.error("Batch save failed", e);
      throw new ApiRequestException("Failed to save customer covenants: " + e.getMessage(), e);
    }
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public CustomerCovenantDTO updateCustomerCovenant(CustomerCovenantDTO dto)
      throws ApiRequestException {

    log.info("START | updateCustomerCovenant | ID: {}", dto.getCustomerCovenantId());

    if (dto.getCustomerCovenantId() == null) {
      throw new ApiRequestException("ID is required for update");
    }

    try {
      CustomerCovenant entity =
          customerCovenantRepository
              .findById(dto.getCustomerCovenantId())
              .orElseThrow(
                  () ->
                      new ApiRequestException(
                          "CustomerCovenant not found for ID: " + dto.getCustomerCovenantId()));

      applyUpdates(entity, dto);
      CustomerCovenantDTO responseDTO =
          new CustomerCovenantDTO(customerCovenantRepository.save(entity));

      log.info("END | updateCustomerCovenant | ID: {}", dto.getCustomerCovenantId());
      return responseDTO;

    } catch (ApiRequestException e) {
      throw e;
    } catch (Exception e) {
      log.error("Error updating covenant ID {}: {}", dto.getCustomerCovenantId(), e.getMessage(), e);
      throw new ApiRequestException("Failed to update customer covenant: " + e.getMessage(), e);
    }
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<CustomerCovenantDTO> getAllCustomerCovenant(Integer facilityPaperId)
      throws ApiRequestException {

    log.info("START | getAllCustomerCovenant | FacilityPaperID: {}", facilityPaperId);

    if (facilityPaperId == null) {
      throw new ApiRequestException("FacilityPaper ID is required");
    }

    try {
      List<CustomerCovenantDTO> responseList =
          customerCovenantRepository
              .findCustomerCovenantsByFacilityPaperID(facilityPaperId)
              .stream()
              .map(CustomerCovenantDTO::new)
              .collect(Collectors.toList());

      log.info(
          "END | getAllCustomerCovenant | FacilityPaperID: {} | Records: {}",
          facilityPaperId,
          responseList.size());
      return responseList;

    } catch (ApiRequestException e) {
      throw e;
    } catch (Exception e) {
      log.error(
          "Error fetching covenants for FacilityPaperID {}: {}",
          facilityPaperId,
          e.getMessage(),
          e);
      throw new ApiRequestException("Failed to fetch customer covenants: " + e.getMessage(), e);
    }
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public CovenantDetailsFinacleDTO getCovenantDetailsFromFinacle(
      LoadCovenantDataDTO loadCovenantDataDTO) throws ApiRequestException {

    String customerFinacleId =
        customerCovenantRepository.findCustomerFinancialId(
            loadCovenantDataDTO.getFacilityPaperId());
    loadCovenantDataDTO.setCustId(customerFinacleId);

    CovenantDetailsFinacleDTO details =
        integrationService.getCovenantDetailsFromFinacle(loadCovenantDataDTO);

    if (details == null || details.getCovenant() == null) {
      return details;
    }

    List<CovenantDataDTO> covenantList = details.getCovenant();
    sortOtherCovenantsLast(covenantList);
    details.setCovenant(covenantList);

    attachNonComplianceComments(details, covenantList, loadCovenantDataDTO.getFacilityPaperId());
    return details;
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<NoneComplianceCovenantDTO> getCovenantCommentList(Integer facilityPaperId)
      throws ApiRequestException {

    List<NoneComplianceCovenant> comments =
        noneComplianceCovenantRepository.findByFacilityPaperId(facilityPaperId);

    if (comments == null) {
      throw new ApiRequestException("No comments found for Facility Paper ID: " + facilityPaperId);
    }

    return comments.stream().map(NoneComplianceCovenantDTO::new).collect(Collectors.toList());
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public NoneComplianceCovenantDTO addEditCommentToCovenant(NoneComplianceCovenantDTO dto)
      throws ApiRequestException {

    log.info("Adding comment to covenant: {}", dto);

    NoneComplianceCovenant entity =
        dto.getNonComplianceCovenantId() != null
            ? noneComplianceCovenantRepository
                .findById(dto.getNonComplianceCovenantId())
                .orElseThrow(
                    () ->
                        new ApiRequestException(
                            "Covenant comment with ID "
                                + dto.getNonComplianceCovenantId()
                                + " not found"))
            : new NoneComplianceCovenant();

    applyCommentFields(entity, dto);
    noneComplianceCovenantRepository.save(entity);

    NoneComplianceCovenantDTO response = new NoneComplianceCovenantDTO(entity);
    log.info("Comment added/edited successfully: {}", response);
    return response;
  }

  // --- private helpers ---

  private Map<Integer, FacilityPaper> loadFacilityPaperMap(List<CustomerCovenantDTO> dtoList) {
    Set<Integer> facilityIds =
        dtoList.stream().map(CustomerCovenantDTO::getFacilityPaperId).collect(Collectors.toSet());

    return facilityPaperRepository.findAllById(facilityIds).stream()
        .collect(Collectors.toMap(FacilityPaper::getFacilityPaperID, fp -> fp));
  }

  private FacilityPaper requireFacilityPaper(
      Map<Integer, FacilityPaper> facilityMap, Integer facilityPaperId) {
    FacilityPaper facilityPaper = facilityMap.get(facilityPaperId);
    if (facilityPaper == null) {
      throw new ApiRequestException("Invalid FacilityPaper ID: " + facilityPaperId);
    }
    return facilityPaper;
  }

  private int nextDisplayOrder(
      Map<Integer, Integer> nextDisplayOrderByFacility, Integer facilityPaperId) {
    return nextDisplayOrderByFacility.compute(
        facilityPaperId,
        (id, current) -> {
          if (current == null) {
            Integer maxOrder =
                customerCovenantRepository.findMaxDisplayOrderByFacilityPaperID(id);
            return (maxOrder != null ? maxOrder : 0) + 1;
          }
          return current + 1;
        });
  }

  private CustomerCovenant mapToEntity(
      CustomerCovenantDTO dto, FacilityPaper facilityPaper, int displayOrder) {

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
    entity.setDisplayOrder(displayOrder);

    return entity;
  }

  private void applyUpdates(CustomerCovenant entity, CustomerCovenantDTO dto) {
    entity.setCustomerFinancialID(dto.getCustomerFinancialID());
    entity.setDisbursementType(dto.getDisbursementType());
    entity.setApplicableType(dto.getApplicableType());

    entity.setCovenant_Code(dto.getCovenant_Code());
    entity.setCovenant_Description(dto.getCovenant_Description());
    entity.setCovenant_Frequency(dto.getCovenant_Frequency());
    entity.setCovenant_Due_Date(dto.getCovenant_Due_Date());
    entity.setNoFrequencyDueDate(dto.getNoFrequencyDueDate());

    entity.setModifiedBy(dto.getCreatedBy());
    entity.setLastModifiedDate(new Date());
  }

  private void applyCommentFields(NoneComplianceCovenant entity, NoneComplianceCovenantDTO dto) {
    entity.setSerialNumber(dto.getSerialNumber());
    entity.setFacilityPaperId(dto.getFacilityPaperId());
    entity.setComment(dto.getComment());
    entity.setAddedDate(new Date());
    entity.setAddedBy(dto.getAddedBy());
    entity.setAddedUserDisplayName(dto.getAddedUserDisplayName());
    entity.setAddedUserId(dto.getAddedUserId());
  }

  private void sortOtherCovenantsLast(List<CovenantDataDTO> covenantList) {
    covenantList.sort(
        (c1, c2) -> {
          boolean c1Other =
              c1.getCovenantInq().stream().anyMatch(cov -> cov.getCovCod().endsWith("_OTH"));
          boolean c2Other =
              c2.getCovenantInq().stream().anyMatch(cov -> cov.getCovCod().endsWith("_OTH"));

          if (c1Other && !c2Other) return 1;
          if (!c1Other && c2Other) return -1;
          return 0;
        });
  }

  private void attachNonComplianceComments(
      CovenantDetailsFinacleDTO details,
      List<CovenantDataDTO> covenantList,
      Integer facilityPaperId)
      throws ApiRequestException {

    Map<String, NoneComplianceCovenantDTO> matchingComments =
        getMatchingCommentsMap(details, facilityPaperId);

    for (CovenantDataDTO covenantData : covenantList) {
      for (CovenantInquiryDTO inquiry : covenantData.getCovenantInq()) {
        NoneComplianceCovenantDTO comment = matchingComments.get(inquiry.getSrlNum());
        if (comment != null) {
          inquiry.setNonComplianceCovenantDTO(comment);
        }
      }
    }

    NoneComplianceCovenantDTO specialComment = matchingComments.get(SPECIAL_COMMENT_SRL);
    if (specialComment != null && facilityPaperId.equals(specialComment.getFacilityPaperId())) {
      details.setSpecialComment(specialComment);
    }
  }

  private Map<String, NoneComplianceCovenantDTO> getMatchingCommentsMap(
      CovenantDetailsFinacleDTO details, Integer facilityPaperId) throws ApiRequestException {

    log.info("Getting matching comments for facilityPaperId: {}", facilityPaperId);

    Set<String> srlNumSet = getSrlNumSet(details);
    if (srlNumSet.isEmpty()) {
      log.warn("SrlNum list is empty");
      return Collections.emptyMap();
    }

    List<NoneComplianceCovenantDTO> covenantComments = getCovenantCommentList(facilityPaperId);
    if (covenantComments.isEmpty()) {
      log.warn("Covenant comments list is empty");
      return Collections.emptyMap();
    }

    return covenantComments.stream()
        .filter(
            comment ->
                comment.getSerialNumber() != null
                    && (srlNumSet.contains(comment.getSerialNumber().toString())
                        || comment.getSerialNumber() == SPECIAL_COMMENT_SERIAL))
        .collect(
            Collectors.toMap(
                comment -> comment.getSerialNumber().toString(),
                comment -> comment,
                (existing, replacement) -> replacement));
  }

  private Set<String> getSrlNumSet(CovenantDetailsFinacleDTO details) {
    log.info("Extracting SrlNum set from CovenantDetailsFinacleDTO");
    if (details == null || details.getCovenant() == null) {
      return Collections.emptySet();
    }

    Set<String> srlNumSet =
        details.getCovenant().stream()
            .flatMap(covenantData -> covenantData.getCovenantInq().stream())
            .map(CovenantInquiryDTO::getSrlNum)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

    log.info("SrlNum Set: {}", srlNumSet);
    return srlNumSet;
  }
}
