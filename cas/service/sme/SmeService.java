package com.itechro.cas.service.sme;

import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.sme.SmeQuestion;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.dto.sme.FpSmeAnswerDTO;
import com.itechro.cas.model.dto.sme.SmeCustomerTurnoverResDTO;
import com.itechro.cas.model.dto.sme.SmeCustomerTurnoverRqDTO;
import com.itechro.cas.model.dto.sme.SmeQuestionDTO;
import com.itechro.cas.model.security.CredentialsDTO;

import java.util.List;
import java.util.Map;

public interface SmeService {

        List<SmeQuestionDTO> getAllQuestionsAndAnswers() throws AppsException;

        List<FpSmeAnswerDTO> saveOrUpdateAnswer(List<FpSmeAnswerDTO> fpSmeAnswerDTOList, CredentialsDTO credentialsDTO) throws AppsException;

        Map<String, List<FpSmeAnswerDTO>> getAnswer(Integer facilityPaperID, CredentialsDTO credentialsDTO) throws AppsException;

        Map<String, List<FpSmeAnswerDTO>> getAnswerList(Integer facilityPaperID, CredentialsDTO credentialsDTO) throws AppsException;

        SmeCustomerTurnoverResDTO getSmeCustomerTurnoverData(SmeCustomerTurnoverRqDTO request, CredentialsDTO credentialsDTO) throws AppsException;

        String isFacilityPaperApproved(Integer customerId, Integer facilityPaperId, SmeCustomerTurnoverRqDTO request, CredentialsDTO credentialsDTO) throws AppsException;

}