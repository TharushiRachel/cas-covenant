package lk.sampath.cas_covenant.service;

import lk.sampath.cas_covenant.dto.CovenantDetailsFinacleDTO;
import lk.sampath.cas_covenant.dto.CovenantListDTO;
import lk.sampath.cas_covenant.dto.CustomerCovenantRQ;
import lk.sampath.cas_covenant.dto.LoadCovenantDataDTO;
import lk.sampath.cas_covenant.exception.ApiRequestException;

public interface IntegrationService {

    CovenantListDTO getCovenantList(CustomerCovenantRQ customerCovenantRQ) throws ApiRequestException;
    CovenantDetailsFinacleDTO getCovenantDetailsFromFinacle(LoadCovenantDataDTO loadCovenantDataDTO) throws ApiRequestException;

}
