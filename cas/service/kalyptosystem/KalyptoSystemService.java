package com.itechro.cas.service.kalyptosystem;

import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.integration.request.LoadKalyptoDataRQ;
import com.itechro.cas.model.dto.integration.response.KalyptoRS;
import com.itechro.cas.model.dto.kalyptosystem.*;
import com.itechro.cas.model.dto.kalyptosystem.request.KalyptoRQ;
import com.itechro.cas.model.dto.kalyptosystem.request.KalyptoValuesRQ;
import com.itechro.cas.model.dto.kalyptosystem.response.*;
import com.itechro.cas.model.security.CredentialsDTO;
import org.springframework.stereotype.Service;

@Service
public interface KalyptoSystemService {

    public KalyptoValueNameRPDTO getKalyptoValues(KalyptoRQ kalyptoRQ, CredentialsDTO credentialsDTO) throws AppsException;

    public Boolean isExistKalyptoDataService(KalyptoRQ kalyptoRQ);

    public Boolean isAddedNewKalyptoValueService(KalyptoRQ kalyptoRQ, CredentialsDTO credentialsDTO);

    public KalyptoDTO getKalyptoDetails(KalyptoRQ kalyptoRQ, CredentialsDTO credentialsDTO);

    public Boolean saveEditedKALYPTOService(KalyptoValuesRQ kalyptoValuesRQ, CredentialsDTO credentialsDTO);

    public KalyptoIntegrationDTO getKalyptoFromIntegrationService(KalyptoRQ kalyptoRQ, CredentialsDTO credentialsDTO) throws AppsException;

    public KalyptoValueNameRPDTO refreshKalyptoValues(KalyptoRQ kalyptoRQ, CredentialsDTO credentialsDTO) throws AppsException;

    public KalyptoRS getDirectIntegrationService(LoadKalyptoDataRQ loadKalyptoDataRQ, CredentialsDTO credentialsDTO);

}
