package com.itechro.cas.service.esg;

import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.esg.*;
import com.itechro.cas.model.security.CredentialsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ESGService {

    List<AnswerDataDTO> saveDataToAnnexure(List<AnswerDataDTO> answerDataDTO, CredentialsDTO credentialsDTO) throws AppsException;

    List<AnswerDataDTO> updateDataToAnnexure(Integer annexureId, Integer applicationFormID, Integer facilityPaperID, List<AnswerDataDTO> answerDataDTO, CredentialsDTO credentialsDTO) throws AppsException;

    AnnexureDTO getAnnexureByAnnexureId(Integer annexureId, CredentialsDTO credentialsDTO) throws AppsException;

    List<AFAnnexureListDTO> getAnnexureList(CredentialsDTO credentialsDTO) throws AppsException;

    List<AnnexureDTO> getAnnexureDataByApplicationFormIdOrFacilityPaperId(AnswerDataRequestDTO answerDataRequestDTO, CredentialsDTO credentialsDTO) throws AppsException;

    AnswerDataDTO deleteAnnexureByAnnexureId(DeleteAnnexureDTO deleteAnnexureDTO, CredentialsDTO credentialsDTO) throws AppsException;

    AnnexureDTO refreshAnnexure(DeleteAnnexureDTO deleteAnnexureDTO, CredentialsDTO credentialsDTO) throws AppsException;

    List<EsgDocStorageDTO> addAttachments(EsgDocStorageDTO esgDocStorageDTO, CredentialsDTO credentialsDTO) throws AppsException;

    List<EsgDocStorageDTO> getAttachments(DeleteAnnexureDTO deleteAnnexureDTO, CredentialsDTO credentialsDTO) throws AppsException;

    byte[] getAttachmentById(Integer esgStorageID, CredentialsDTO credentialsDTO) throws AppsException;

    List<EsgDocStorageDTO> updateAttachment(Integer esgStorageID, EsgDocStorageDTO esgDocStorageDTO, CredentialsDTO credentialsDTO) throws AppsException;

    EsgDocStorageDTO deleteAttachment(Integer esgStorageID, CredentialsDTO credentialsDTO) throws AppsException;
}
