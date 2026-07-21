package lk.sampath.cas_covenant.service;

import java.util.List;
import lk.sampath.cas_covenant.dto.CovenantDetailsFinacleDTO;
import lk.sampath.cas_covenant.dto.CustomerCovenantDTO;
import lk.sampath.cas_covenant.dto.LoadCovenantDataDTO;
import lk.sampath.cas_covenant.dto.NoneComplianceCovenantDTO;
import lk.sampath.cas_covenant.exception.ApiRequestException;

public interface CustomerCovenantService {

  List<CustomerCovenantDTO> saveCustomerCovenant(List<CustomerCovenantDTO> dtoList)
      throws ApiRequestException;

  CustomerCovenantDTO updateCustomerCovenant(CustomerCovenantDTO dto) throws ApiRequestException;

  List<CustomerCovenantDTO> getAllCustomerCovenant(Integer facilityPaperId)
      throws ApiRequestException;

  CovenantDetailsFinacleDTO getCovenantDetailsFromFinacle(LoadCovenantDataDTO loadCovenantDataDTO)
      throws ApiRequestException;

  NoneComplianceCovenantDTO addEditCommentToCovenant(
      NoneComplianceCovenantDTO noneComplianceCovenantDTO) throws ApiRequestException;

  List<NoneComplianceCovenantDTO> getCovenantCommentList(Integer facilityPaperId)
      throws ApiRequestException;
}
