package lk.sampath.cas_covenant.service.impl;

import lk.sampath.cas_covenant.common.PropertyFileValue;
import lk.sampath.cas_covenant.controller.base_controller.StandardResponse;
import lk.sampath.cas_covenant.dto.CovenantFacilitiesDTO;
import lk.sampath.cas_covenant.dto.FacilityCovenantDTO;
import lk.sampath.cas_covenant.entity.FacilityCovenant;
import lk.sampath.cas_covenant.entity.FacilityCovenantFacilities;
import lk.sampath.cas_covenant.entity.facilityPaper.Facility;
import lk.sampath.cas_covenant.entity.facilityPaper.FacilityPaper;
import lk.sampath.cas_covenant.enums.CovenantStatus;
import lk.sampath.cas_covenant.enums.ErrorEnums;
import lk.sampath.cas_covenant.enums.YesNoStatus;
import lk.sampath.cas_covenant.exception.ApiRequestException;
import lk.sampath.cas_covenant.repository.FacilityCovenantRepository;
import lk.sampath.cas_covenant.repository.FacilityPaperRepository;
import lk.sampath.cas_covenant.repository.FacilityRepository;
import lk.sampath.cas_covenant.service.FacilityCovenantService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
public class FacilityCovenantServiceImpl implements FacilityCovenantService {

    private final FacilityCovenantRepository facilityCovenantRepository;
    private final PropertyFileValue propertyFileValue;
    private final FacilityRepository facilityRepository;
    private final FacilityPaperRepository facilityPaperRepository;

    @Autowired
    public FacilityCovenantServiceImpl(FacilityCovenantRepository facilityCovenantRepository, PropertyFileValue propertyFileValue, FacilityRepository facilityRepository, FacilityPaperRepository facilityPaperRepository) {
        this.facilityCovenantRepository = facilityCovenantRepository;
        this.propertyFileValue = propertyFileValue;
        this.facilityRepository = facilityRepository;
        this.facilityPaperRepository = facilityPaperRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<StandardResponse<List<FacilityCovenantDTO>>> saveFacilityCovenant(List<FacilityCovenantDTO> dtoList) throws ApiRequestException {

        log.info("START | saveAllFacilityCovenants");

        if (dtoList == null || dtoList.isEmpty()) {
            throw new ApiRequestException("Facility covenant list cannot be empty");
        }

        try {
            int displayOrder = 1;
            List<FacilityCovenant> entityList = new ArrayList<>();

            for (FacilityCovenantDTO dto : dtoList) {

                FacilityCovenant parent = mapToParentEntity(dto, displayOrder++);

                List<Facility> facilityList = facilityRepository.findFacilitiesByFacilityPaperID(dto.getFacilityPaperId());

                List<Integer> validFacilityIds = facilityList
                        .stream()
                        .map(Facility::getFacilityID)
                        .filter(Objects::nonNull)
                        .toList();

                List<Integer> facilityIdList = dto.getCovenantFacilities() == null
                        ? Collections.emptyList()
                        : dto.getCovenantFacilities().stream()
                        .map(CovenantFacilitiesDTO::getFacilityID)
                        .filter(Objects::nonNull)
                        .toList();

                validateFacilityIds(validFacilityIds, facilityIdList);

                List<FacilityCovenantFacilities> children = mapToChildEntities(facilityIdList, parent, facilityList);

                parent.setFacilityCovenantFacilitiesSet(children);

                entityList.add(parent);
            }

            List<FacilityCovenant> savedEntities = facilityCovenantRepository.saveAll(entityList);

            List<FacilityCovenantDTO> responseList = savedEntities.stream()
                    .map(FacilityCovenantDTO::new)
                    .toList();

            return ResponseEntity.ok(
                    new StandardResponse<>(
                            ErrorEnums.SUCCESS_CODE.getStatus(),
                            ErrorEnums.SUCCESS_CODE.getLabel(),
                            responseList
                    )
            );

        } catch (Exception e) {
            log.error("Batch save failed", e);
            throw new ApiRequestException("Failed to save facility covenants: " + e.getMessage());
        }
    }

    private FacilityCovenant mapToParentEntity(FacilityCovenantDTO dto, int displayOrder) {

        FacilityCovenant parent = new FacilityCovenant();

        FacilityPaper facilityPaper = facilityPaperRepository.findById(dto.getFacilityPaperId())
                .orElseThrow(() -> new ApiRequestException("Facility paper not found for ID: " + dto.getFacilityPaperId()));

        parent.setRequestUUID(propertyFileValue.getRequestUUID());
        parent.setCustomerFinacleID(dto.getCustomerFinacleID());
        parent.setFacilityPaper(facilityPaper);

        parent.setCovenant_Code(dto.getCovenant_Code());
        parent.setCovenant_Description(dto.getCovenant_Description());
        parent.setCovenant_Frequency(dto.getCovenant_Frequency());
        parent.setCovenant_Due_Date(dto.getCovenant_Due_Date());

        parent.setDisbursementType(dto.getDisbursementType());
        parent.setApplicableType(dto.getApplicableType());

        parent.setStatus(CovenantStatus.Active);
        parent.setIsExists(YesNoStatus.N);

        parent.setCreatedBy(dto.getCreatedBy());
        parent.setCreatedDate(new Date());
        parent.setCreatedUserDisplayName(dto.getCreatedUserDisplayName());

        parent.setDisplayOrder(displayOrder);

        return parent;
    }

    private List<FacilityCovenantFacilities> mapToChildEntities(List<Integer> facilityIds, FacilityCovenant parent, List<Facility> validFacilities) {

        List<FacilityCovenantFacilities> children = new ArrayList<>();
        int childOrder = 1;

        for (Integer id : facilityIds) {
            FacilityCovenantFacilities child = new FacilityCovenantFacilities();
            child.setFacilityID(id);
            //child.setDisplayOrder(childOrder++);
            child.setApplicationLevelCovenant(parent);
            children.add(child);
        }

        return children;
    }

    private void validateFacilityIds(List<Integer> validIds, List<Integer> requestIds)
            throws ApiRequestException {

        if (requestIds.isEmpty()) {
            return;
        }

        List<Integer> invalidIds = requestIds.stream()
                .filter(id -> !validIds.contains(id))
                .toList();

        if (!invalidIds.isEmpty()) {
            throw new ApiRequestException("Invalid facility IDs: " + invalidIds);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<StandardResponse<FacilityCovenantDTO>> updateFacilityCovenant(FacilityCovenantDTO dto) throws ApiRequestException {

        log.info("START | updateFacilityCovenant ID: {}", dto.getApplicationCovenantId());

        if (dto.getApplicationCovenantId() == null) {
            throw new ApiRequestException("ID is required");
        }

        try {
            FacilityCovenant entity = facilityCovenantRepository
                    .findById(dto.getApplicationCovenantId())
                    .orElseThrow(() -> new ApiRequestException("Record not found"));

            updateParentEntity(entity, dto);

            List<Facility> facilityList = facilityRepository.findFacilitiesByFacilityPaperID(dto.getFacilityPaperId());

            List<Integer> validFacilityIds = facilityList.stream()
                    .map(Facility::getFacilityID)
                    .filter(Objects::nonNull)
                    .toList();
            log.info("Valid facility IDs for paper ID {}: {}", dto.getFacilityPaperId(), validFacilityIds);

            List<Integer> facilityIdList = dto.getCovenantFacilities() == null
                    ? Collections.emptyList()
                    : dto.getCovenantFacilities().stream()
                    .map(CovenantFacilitiesDTO::getFacilityID)
                    .filter(Objects::nonNull)
                    .toList();
            log.info("Facility IDs from request for covenant ID {}: {}", dto.getApplicationCovenantId(), facilityIdList);

            validateFacilityIds(validFacilityIds, facilityIdList);

            entity.getFacilityCovenantFacilitiesSet().clear();

            List<FacilityCovenantFacilities> newChildren =
                    mapToChildEntities(facilityIdList, entity, facilityList);

            entity.getFacilityCovenantFacilitiesSet().addAll(newChildren);

            FacilityCovenant updated = facilityCovenantRepository.save(entity);

            return ResponseEntity.ok(
                    new StandardResponse<>(
                            ErrorEnums.SUCCESS_CODE.getStatus(),
                            ErrorEnums.SUCCESS_CODE.getLabel(),
                            new FacilityCovenantDTO(updated)
                    )
            );

        } catch (Exception e) {
            log.error("Update failed", e);
            throw new ApiRequestException("Failed to update: " + e.getMessage());
        }
    }

    private void updateParentEntity(FacilityCovenant entity, FacilityCovenantDTO dto) {

        log.info("Updating entity ID {} with new values", entity.getApplicationCovenantId());

        entity.setCustomerFinacleID(dto.getCustomerFinacleID());
        entity.setFacilityPaper(facilityPaperRepository.findById(dto.getFacilityPaperId())
                .orElseThrow(() -> new ApiRequestException("Facility paper not found for ID: " + dto.getFacilityPaperId())));
        entity.setCovenant_Code(dto.getCovenant_Code());
        entity.setCovenant_Description(dto.getCovenant_Description());
        entity.setCovenant_Frequency(dto.getCovenant_Frequency());
        entity.setCovenant_Due_Date(dto.getCovenant_Due_Date());

        entity.setDisbursementType(dto.getDisbursementType());
        entity.setApplicableType(dto.getApplicableType());

        entity.setModifiedBy(dto.getCreatedBy());
        entity.setLastModifiedDate(new Date());

        log.info("Entity ID {} updated with new values", entity.getApplicationCovenantId());
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<StandardResponse<List<FacilityCovenantDTO>>> getAllFacilityCovenant(Integer facilityPaperId) throws ApiRequestException {

        log.info("START | getAllFacilityCovenant by facility paper ID: {}", facilityPaperId);

        try {
            List<FacilityCovenant> entities =
                    facilityCovenantRepository.findFacilityCovenantsByFacilityPaperID(facilityPaperId);

            log.info("Retrieved {} covenants", entities.size());

            List<Facility> facilityList =
                    facilityRepository.findFacilitiesByFacilityPaperID(facilityPaperId);

            log.info("Retrieved {} facilities", facilityList.size());

            Map<Integer, Facility> facilityMap = facilityList.stream()
                    .filter(f -> f.getFacilityID() != null)
                    .collect(Collectors.toMap(Facility::getFacilityID, Function.identity()));

            List<FacilityCovenantDTO> dtoList = new ArrayList<>();


            for (FacilityCovenant entity : entities) {

                if (entity.getFacilityPaper() == null) {
                    log.warn("FacilityPaper is null for FacilityCovenant with ID {}. Skipping this record.", entity.getApplicationCovenantId());
                    continue;
                }

                FacilityCovenantDTO dto = new FacilityCovenantDTO(entity);

                List<CovenantFacilitiesDTO> childDTOs = new ArrayList<>();

                if (entity.getFacilityCovenantFacilitiesSet() != null) {
                    for (FacilityCovenantFacilities child : entity.getFacilityCovenantFacilitiesSet()) {
                        Facility facility = facilityMap.get(child.getFacilityID());
                        if (facility == null) {
                            log.warn("Invalid facility ID {} in covenant {}", child.getFacilityID(), entity.getApplicationCovenantId());
                            continue;
                        }
                        CovenantFacilitiesDTO childDTO = new CovenantFacilitiesDTO();
                        childDTO.setFacilityID(facility.getFacilityID());
                        childDTO.setCreditFacilityTemplateID(facility.getCreditFacilityTemplate().getCreditFacilityTemplateID());
                        childDTO.setCreditFacilityName(facility.getCreditFacilityTemplate().getCreditFacilityName());
                        childDTO.setFacilityCurrency(facility.getFacilityCurrency());
                        childDTO.setFacilityAmount(facility.getFacilityAmount());
                        childDTO.setFacilityRefCode(facility.getFacilityRefCode());

                        childDTO.setDisplayOrder(child.getDisplayOrder());
                        childDTOs.add(childDTO);
                    }
                }
                dto.setCovenantFacilities(childDTOs);
                dtoList.add(dto);
            }

            return ResponseEntity.ok(
                    new StandardResponse<>(
                            ErrorEnums.SUCCESS_CODE.getStatus(),
                            ErrorEnums.SUCCESS_CODE.getLabel(),
                            dtoList
                    )
            );

        } catch (Exception e) {
            log.error("Retrieval failed", e);
            throw new ApiRequestException("Failed to retrieve: " + e.getMessage());
        }
    }
}
