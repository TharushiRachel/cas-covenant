package lk.sampath.cas_covenant.service;

import java.util.List;
import lk.sampath.cas_covenant.controller.base_controller.StandardResponse;
import lk.sampath.cas_covenant.dto.CovenantDetailsFinacleDTO;
import lk.sampath.cas_covenant.dto.CustomerCovenantDTO;
import lk.sampath.cas_covenant.dto.LoadCovenantDataDTO;
import lk.sampath.cas_covenant.dto.NoneComplianceCovenantDTO;
import lk.sampath.cas_covenant.exception.ApiRequestException;
import org.springframework.http.ResponseEntity;

public interface CustomerCovenantService {

  ResponseEntity<StandardResponse<List<CustomerCovenantDTO>>> saveCustomerCovenant(List<CustomerCovenantDTO> dtoList) throws ApiRequestException;

  ResponseEntity<StandardResponse<CustomerCovenantDTO>> updateCustomerCovenant(CustomerCovenantDTO dto) throws ApiRequestException;

  ResponseEntity<StandardResponse<List<CustomerCovenantDTO>>> getAllCustomerCovenant(Integer facilityPaperId) throws ApiRequestException;

  CovenantDetailsFinacleDTO getCovenantDetailsFromFinacle(LoadCovenantDataDTO loadCovenantDataDTO) throws ApiRequestException;

  NoneComplianceCovenantDTO addEditCommentToCovenant(NoneComplianceCovenantDTO noneComplianceCovenantDTO) throws ApiRequestException;

  List<NoneComplianceCovenantDTO> getCovenantCommentList(Integer facilityPaperId) throws ApiRequestException;
}
