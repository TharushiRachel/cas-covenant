package lk.sampath.cas_covenant.service;

import lk.sampath.cas_covenant.controller.base_controller.StandardResponse;
import lk.sampath.cas_covenant.dto.FacilityCovenantDTO;
import lk.sampath.cas_covenant.exception.ApiRequestException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FacilityCovenantService {

    ResponseEntity<StandardResponse<List<FacilityCovenantDTO>>> saveFacilityCovenant(List<FacilityCovenantDTO> dtoList) throws ApiRequestException;

    ResponseEntity<StandardResponse<FacilityCovenantDTO>> updateFacilityCovenant(FacilityCovenantDTO dto) throws ApiRequestException;

    List<FacilityCovenantDTO> getAllFacilityCovenant(Integer facilityPaperId) throws ApiRequestException;
}
