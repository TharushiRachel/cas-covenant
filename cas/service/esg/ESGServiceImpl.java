package com.itechro.cas.service.esg;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.applicationform.ApplicationFormDao;
import com.itechro.cas.dao.esg.*;
import com.itechro.cas.dao.facilitypaper.FacilityPaperDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import com.itechro.cas.model.domain.esg.*;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.dto.esg.*;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.integration.IntegrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ESGServiceImpl implements ESGService{

    private final AnswerDataDao answerDataDao;

    private final AnnexureDao annexureDao;

    private final AnnexureQuestionDao annexureQuestionDao;

    private final AnnexureAnswerDao annexureAnswerDao;

    private final ApplicationFormDao applicationFormDao;

    private final FacilityPaperDao facilityPaperDao;

    private final EsgDocStorageDao esgDocStorageDao;

    private final IntegrationService integrationService;

    public ESGServiceImpl(AnswerDataDao answerDataDao, AnnexureDao annexureDao, AnnexureQuestionDao annexureQuestionDao, AnnexureAnswerDao annexureAnswerDao, ApplicationFormDao applicationFormDao, FacilityPaperDao facilityPaperDao, EsgDocStorageDao esgDocStorageDao, IntegrationService integrationService) {
        this.answerDataDao = answerDataDao;
        this.annexureDao = annexureDao;
        this.annexureQuestionDao = annexureQuestionDao;
        this.annexureAnswerDao = annexureAnswerDao;
        this.applicationFormDao = applicationFormDao;
        this.facilityPaperDao = facilityPaperDao;
        this.esgDocStorageDao = esgDocStorageDao;
        this.integrationService = integrationService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<AnswerDataDTO> saveDataToAnnexure(List<AnswerDataDTO> answerDataDTOList, CredentialsDTO credentialsDTO) throws AppsException {
        log.info("START | saveDataToAnnexure - ESGServiceImpl | request : {}", answerDataDTOList);

        List<AnswerData> answerDataList = new ArrayList<>();
        try {
            for (AnswerDataDTO answerDataDTO : answerDataDTOList) {
                AnswerData answerData = new AnswerData();

                Annexure annexure = annexureDao.findById(answerDataDTO.getAnnexureId())
                        .orElseThrow(() -> new AppsException("Annexure not found for ID: " + answerDataDTO.getAnnexureId()));
                answerData.setAnnexure(annexure);
                answerData.setAnnexureName(annexure.getName());
                answerData.setAnnexureDescription(annexure.getDescription());
                answerData.setIsMandatory(annexure.getIsMandatory());
                answerData.setAllowRiskEdit(annexure.getAllowRiskEdit());

                answerData.setQuestion(annexureQuestionDao.findById(answerDataDTO.getQuestionId())
                        .orElseThrow(() -> new AppsException("Question not found for ID: " + answerDataDTO.getQuestionId())));

                if (answerDataDTO.getAnswerId() != null) {
                    answerData.setAnswer(annexureAnswerDao.findById(answerDataDTO.getAnswerId())
                            .orElseThrow(() -> new AppsException("Answer not found for ID: " + answerDataDTO.getAnswerId())));
                }

                answerData.setAnswerData(answerDataDTO.getAnswerData());
                answerData.setDisplayOrder(answerDataDTO.getDisplayOrder());

                if (answerDataDTO.getApplicationFormID() != null) {
                    answerData.setApplicationForm(applicationFormDao.findById(answerDataDTO.getApplicationFormID())
                            .orElseThrow(() -> new AppsException("Application Form not found for ID: " + answerDataDTO.getApplicationFormID())));
                }

                if (answerDataDTO.getFacilityPaperID() != null) {
                    answerData.setFacilityPaper(facilityPaperDao.findById(answerDataDTO.getFacilityPaperID())
                            .orElseThrow(() -> new AppsException("Facility Paper not found for ID: " + answerDataDTO.getFacilityPaperID())));
                }

                answerData.setCreatedDate(new Date());
                answerData.setCreatedBy(credentialsDTO.getDisplayName());
                answerData.setStatus(AppsConstants.Status.ACT);
                answerData.setCreatedUserDivCode(credentialsDTO.getDivCode());

                answerDataList.add(answerData);
            }

            answerDataList = answerDataDao.saveAll(answerDataList);

            List<AnswerDataDTO> responseList = new ArrayList<>();
            for (AnswerData answerData : answerDataList) {
                AnswerDataDTO responseDTO = new AnswerDataDTO(answerData);
                responseDTO.setAnnexureId(answerData.getAnnexure() != null ? answerData.getAnnexure().getAnnexureId() : null);
                responseDTO.setQuestionId(answerData.getQuestion() != null ? answerData.getQuestion().getQuestionId() : null);
                responseDTO.setAnswerId(answerData.getAnswer() != null ? answerData.getAnswer().getAnswerId() : null);
                responseDTO.setAnswerDataId(answerData.getAnswerDataId());
                responseDTO.setApplicationFormID(answerData.getApplicationForm() != null ? answerData.getApplicationForm().getApplicationFormID() : null);
                responseDTO.setFacilityPaperID(answerData.getFacilityPaper() != null ? answerData.getFacilityPaper().getFacilityPaperID() : null);
                responseDTO.setDisplayOrder(answerData.getDisplayOrder());
                responseDTO.setCreatedDate(answerData.getCreatedDate());
                responseDTO.setCreatedBy(answerData.getCreatedBy());
                responseDTO.setStatus(answerData.getStatus());
                responseList.add(responseDTO);
            }

            log.info("END | saveDataToAnnexure - ESGServiceImpl | response : {}", responseList);
            return responseList;
        } catch (AppsException ae) {
            log.error("AppsException | saveDataToAnnexure - ESGServiceImpl | {}", ae.getMessage());
            throw ae;
        } catch (Exception e) {
            log.error("Unhandled Exception | saveDataToAnnexure - ESGServiceImpl | {}", e.getMessage(), e);
            throw new AppsException("Unexpected error occurred while saving annexure data", e);
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<AnswerDataDTO> updateDataToAnnexure(Integer annexureId, Integer applicationFormID, Integer facilityPaperID, List<AnswerDataDTO> answerDataDTOList, CredentialsDTO credentialsDTO) throws AppsException {
        log.info("START | updateDataToAnnexure - ESGServiceImpl | request : {}", answerDataDTOList);

        List<AnswerData> answerDataList = new ArrayList<>();
        try {
            if (annexureId == null || answerDataDTOList == null || answerDataDTOList.isEmpty()) {
                throw new AppsException("Invalid input parameters: annexureId and answerDataDTOList must not be null or empty.");
            }

            // Clear existing records if present
            if (applicationFormID != null) {
                List<AnswerData> existingAnswerDataList = answerDataDao.findByAnnexure_AnnexureIdAndApplicationForm_ApplicationFormID(annexureId, applicationFormID);
                if (!existingAnswerDataList.isEmpty()) {
                    log.info("Deleting existing answer data for annexure ID: {} and application form ID: {}", annexureId, applicationFormID);
                    answerDataDao.deleteAll(existingAnswerDataList);
                }
            } else if (facilityPaperID != null) {
                List<AnswerData> existingAnswerDataList = answerDataDao.findByAnnexure_AnnexureIdAndFacilityPaper_FacilityPaperID(annexureId, facilityPaperID);
                if (!existingAnswerDataList.isEmpty()) {
                    answerDataDao.deleteAll(existingAnswerDataList);
                }
            }

            for (AnswerDataDTO answerDataDTO : answerDataDTOList) {
                AnswerData answerData = new AnswerData();

                Annexure annexure = annexureDao.findById(answerDataDTO.getAnnexureId())
                        .orElseThrow(() -> new AppsException("Annexure not found for ID: " + answerDataDTO.getAnnexureId()));
                answerData.setAnnexure(annexure);
                answerData.setAnnexureName(annexure.getName());
                answerData.setAnnexureDescription(annexure.getDescription());
                answerData.setIsMandatory(annexure.getIsMandatory());

                answerData.setQuestion(annexureQuestionDao.findById(answerDataDTO.getQuestionId())
                        .orElseThrow(() -> new AppsException("Question not found for ID: " + answerDataDTO.getQuestionId())));

                if (answerDataDTO.getAnswerId() != null) {
                    answerData.setAnswer(annexureAnswerDao.findById(answerDataDTO.getAnswerId())
                            .orElseThrow(() -> new AppsException("Answer not found for ID: " + answerDataDTO.getAnswerId())));
                }

                answerData.setAnswerData(answerDataDTO.getAnswerData());
                answerData.setDisplayOrder(answerDataDTO.getDisplayOrder());

                if (answerDataDTO.getApplicationFormID() != null) {
                    answerData.setApplicationForm(applicationFormDao.findById(answerDataDTO.getApplicationFormID())
                            .orElseThrow(() -> new AppsException("Application Form not found for ID: " + answerDataDTO.getApplicationFormID())));
                }

                if (answerDataDTO.getFacilityPaperID() != null) {
                    answerData.setFacilityPaper(facilityPaperDao.findById(answerDataDTO.getFacilityPaperID())
                            .orElseThrow(() -> new AppsException("Facility Paper not found for ID: " + answerDataDTO.getFacilityPaperID())));
                }

                answerData.setCreatedDate(new Date());
                answerData.setCreatedBy(credentialsDTO.getDisplayName());
                answerData.setStatus(AppsConstants.Status.ACT);
                answerData.setCreatedUserDivCode(credentialsDTO.getDivCode());

                answerDataList.add(answerData);
            }

            answerDataList = answerDataDao.saveAll(answerDataList);

            List<AnswerDataDTO> responseList = new ArrayList<>();
            for (AnswerData answerData : answerDataList) {
                AnswerDataDTO responseDTO = new AnswerDataDTO(answerData);
                responseDTO.setAnnexureId(answerData.getAnnexure() != null ? answerData.getAnnexure().getAnnexureId() : null);
                responseDTO.setQuestionId(answerData.getQuestion() != null ? answerData.getQuestion().getQuestionId() : null);
                responseDTO.setAnswerId(answerData.getAnswer() != null ? answerData.getAnswer().getAnswerId() : null);
                responseDTO.setAnswerDataId(answerData.getAnswerDataId());
                responseDTO.setApplicationFormID(answerData.getApplicationForm() != null ? answerData.getApplicationForm().getApplicationFormID() : null);
                responseDTO.setFacilityPaperID(answerData.getFacilityPaper() != null ? answerData.getFacilityPaper().getFacilityPaperID() : null);
                responseDTO.setDisplayOrder(answerData.getDisplayOrder());
                responseDTO.setCreatedDate(answerData.getCreatedDate());
                responseDTO.setCreatedBy(answerData.getCreatedBy());
                responseDTO.setStatus(answerData.getStatus());
                responseList.add(responseDTO);
            }

            log.info("END | updateDataToAnnexure - ESGServiceImpl | response : {}", responseList);
            return responseList;

        } catch (AppsException ae) {
            log.error("AppsException | updateDataToAnnexure - ESGServiceImpl | {}", ae.getMessage());
            throw ae;
        } catch (Exception e) {
            log.error("Unhandled Exception | updateDataToAnnexure - ESGServiceImpl | {}", e.getMessage(), e);
            throw new AppsException("Unexpected error occurred while updating annexure data", e);
        }
    }




    /**
     * get the updated Annexure by Annexure ID from the Admin Master Table
     *
     * **/
    @Override
    @Transactional(readOnly = true)
    public AnnexureDTO getAnnexureByAnnexureId(Integer annexureId, CredentialsDTO credentialsDTO) throws AppsException {
        log.info("START: getAnnexureByAnnexureId - ApplicationFormService | annexureId: {} by: {}", annexureId, credentialsDTO.getUserName());

        try {
            if (annexureId == null) {
                throw new AppsException("Annexure ID must not be null.");
            }

            Annexure afAnnexure = annexureDao.findById(annexureId)
                    .orElseThrow(() -> new AppsException("Annexure with ID " + annexureId + " not found."));

            AnnexureDTO afAnnexureDTO = new AnnexureDTO(afAnnexure);

            log.info("END: getAnnexureByAnnexureId - ApplicationFormService | afAnnexureDTO: {}", afAnnexureDTO);
            return afAnnexureDTO;

        } catch (AppsException ae) {
            log.error("AppsException in getAnnexureByAnnexureId | annexureId: {} | Error: {}", annexureId, ae.getMessage());
            throw ae;
        } catch (Exception e) {
            log.error("Unexpected error in getAnnexureByAnnexureId | annexureId: {} | Error: {}", annexureId, e.getMessage(), e);
            throw new AppsException("Unexpected error occurred while fetching annexure data for ID: " + annexureId, e);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<AFAnnexureListDTO> getAnnexureList(CredentialsDTO credentialsDTO) throws AppsException {
        log.info("START: getAnnexureList - ApplicationFormService | by: {}", credentialsDTO.getUserName());

        try {
            List<Annexure> afAnnexureList = annexureDao.findAll();
            log.info("Fetch Annexure List: {}", afAnnexureList);

            List<AFAnnexureListDTO> afAnnexureDTOList = afAnnexureList.stream()
                    .filter(annexure -> AppsConstants.Status.ACT.toString().equals(annexure.getStatus()))
                    .map(annexure -> {
                        AFAnnexureListDTO dto = new AFAnnexureListDTO();
                        dto.setAnnexureId(annexure.getAnnexureId());
                        dto.setName(annexure.getName());
                        dto.setIsMandatory(
                                annexure.getIsMandatory() != null ? annexure.getIsMandatory().toString() : null
                        );
                        dto.setAllowRiskEdit(
                                annexure.getAllowRiskEdit() != null ? annexure.getAllowRiskEdit().toString() : null
                        );
                        return dto;
                    })
                    .collect(Collectors.toList());

            log.info("END: getAnnexureList - ApplicationFormService | afAnnexureDTOList: {} by: {}", afAnnexureDTOList, credentialsDTO.getUserName());
            return afAnnexureDTOList;

        } catch (Exception e) {
            log.error("Error in getAnnexureList - ApplicationFormService | by: {} | Error: {}", credentialsDTO.getUserName(), e.getMessage(), e);
            throw new AppsException("Unexpected error occurred while fetching annexure list.", e);
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<AnnexureDTO> getAnnexureDataByApplicationFormIdOrFacilityPaperId(AnswerDataRequestDTO answerDataRequestDTO, CredentialsDTO credentialsDTO) throws AppsException {
        log.info("START: getAnnexureDataByApplicationFormIdOrFacilityPaperId - ESGServiceImpl | request: {} by: {}", answerDataRequestDTO, credentialsDTO.getUserName());

        try {
            List<AnnexureDTO> annexureDTOList = new ArrayList<>();
            List<AnswerData> answerDataList = new ArrayList<>();

            if (answerDataRequestDTO.getApplicationFormID() != null) {
                answerDataList = answerDataDao.findByApplicationForm_ApplicationFormIDAndStatus(answerDataRequestDTO.getApplicationFormID(), AppsConstants.Status.ACT);
                if (answerDataList.isEmpty()) {
                    //throw new AppsException("No answer data found for the provided application form ID: " + answerDataRequestDTO.getApplicationFormID());
                    log.info("No answer data found for the provided application form ID: {}", answerDataRequestDTO.getApplicationFormID());
                }
                log.info("Found {} answer data entries for application form ID: {}", answerDataList.size(), answerDataRequestDTO.getApplicationFormID());

            } else if (answerDataRequestDTO.getFacilityPaperID() != null) {
                answerDataList = answerDataDao.findByFacilityPaper_FacilityPaperIDAndStatus(answerDataRequestDTO.getFacilityPaperID(), AppsConstants.Status.ACT);
                if (answerDataList.isEmpty()) {
                    //throw new AppsException("No answer data found for the provided facility paper ID: " + answerDataRequestDTO.getFacilityPaperID());
                    log.info("No answer data found for the provided facility paper ID: {}", answerDataRequestDTO.getFacilityPaperID());
                }
                log.info("Found {} answer data entries for facility paper ID: {}", answerDataList.size(), answerDataRequestDTO.getFacilityPaperID());

                } else {
                    log.warn("No application form ID or facility paper ID provided in request.");
                    //throw new AppsException("Either applicationFormID or facilityPaperID must be provided.");
                }

                if (answerDataList.isEmpty()) {
                    log.warn("No answer data found for given request.");
                    return annexureDTOList;
                }

                Map<Annexure, List<AnswerData>> grouped = answerDataList.stream()
                        .collect(Collectors.groupingBy(AnswerData::getAnnexure));

                for (Map.Entry<Annexure, List<AnswerData>> entry : grouped.entrySet()) {
                    Annexure annexure = entry.getKey();
                    List<AnswerData> answerDataForAnnexure = entry.getValue();

                    AnnexureDTO annexureDTO = new AnnexureDTO(annexure);
                    annexureDTO.setAnnexureId(annexure.getAnnexureId());
                    annexureDTO.setName(annexure.getName());
                    annexureDTO.setDescription(annexure.getDescription());
                    annexureDTO.setApplicationFormID(answerDataRequestDTO.getApplicationFormID());
                    annexureDTO.setFacilityPaperID(answerDataRequestDTO.getFacilityPaperID());
                    annexureDTO.setAnnexureAddedBy(entry.getValue().get(0).getCreatedBy());
                    annexureDTO.setAnnexureAddedDate(entry.getValue().get(0).getCreatedDate());
                    annexureDTO.setCreatedUserDivCode(entry.getValue().get(0).getCreatedUserDivCode());

                    List<AnnexureQuestionDTO> questionDTOList = new ArrayList<>();

                    Map<Integer, List<AnswerData>> questionAnswerDataMap = answerDataForAnnexure.stream()
                            .collect(Collectors.groupingBy(ad -> ad.getQuestion().getQuestionId()));

                    for (Map.Entry<Integer, List<AnswerData>> questionEntry : questionAnswerDataMap.entrySet()) {
                        List<AnswerData> questionAnswerDataList = questionEntry.getValue();
                        AnswerData firstAnswerData = questionAnswerDataList.get(0);

                        AnswerData lastAnswerData = questionAnswerDataList.get(questionAnswerDataList.size() - 1);

                        AnnexureQuestion question = firstAnswerData.getQuestion();
                        AnnexureQuestionDTO questionDTO = new AnnexureQuestionDTO(question);
                        questionDTO.setQuestionId(question.getQuestionId());
                        questionDTO.setQuestion(question.getQuestion());
                        questionDTO.setAnsweredBy(lastAnswerData.getCreatedBy());
                        questionDTO.setAnsweredDate(lastAnswerData.getCreatedDate());

//                        List<AnnexureAnswerDTO> answerDTOs = question.getAnnexureAnswerList().stream()
//                                .map(ans -> new AnnexureAnswerDTO(ans.getAnswerId(), ans.getAnswer()))
//                                .collect(Collectors.toList());
//
//                        for (AnnexureAnswerDTO answerDTO : answerDTOs) {
//                            for (AnswerData ad : questionAnswerDataList) {
//                                if (ad.getAnswer() != null && ad.getAnswer().getAnswerId().equals(answerDTO.getAnswerId())) {
//                                    answerDTO.setUserAnswer(ad.getAnswerData());
//                                    answerDTO.setIsSelected(true);
//                                    answerDTO.setDisplayOrder(ad.getAnswer().getDisplayOrder());
//                                    answerDTO.setStatus(ad.getAnswer().getStatus());
//                                    break;
//                                }
//                                else if(ad.getAnswer() == null && answerDTO.getAnswerId() == null){
//                                    log.info("**********");
//                                    answerDTO.setUserAnswer(ad.getAnswerData());
//                                    answerDTO.setIsSelected(true);
//                                }
//                                else {
//                                    answerDTO.setUserAnswer(null);
//                                    answerDTO.setIsSelected(false);
//                                }
//                            }
//                        }
//
//                        questionDTO.setAnswers(answerDTOs);
//                        questionDTOList.add(questionDTO);
//                    }

                        List<AnnexureAnswerDTO> answerDTOs = new ArrayList<>();

                        for (AnnexureAnswer ans : question.getAnnexureAnswerList()) {
                            answerDTOs.add(new AnnexureAnswerDTO(ans.getAnswerId(), ans.getAnswer(), ans.getDisplayOrder()));
                        }

                        boolean hasNullAnswer = questionAnswerDataList.stream().anyMatch(ad -> ad.getAnswer() == null);
                        if (hasNullAnswer) {
                            answerDTOs.add(new AnnexureAnswerDTO(null, "", 0)); // Or use "" instead of "Other"
                        }

                        for (AnnexureAnswerDTO answerDTO : answerDTOs) {
                            for (AnswerData ad : questionAnswerDataList) {
                                if (ad.getAnswer() != null && ad.getAnswer().getAnswerId().equals(answerDTO.getAnswerId())) {
                                    answerDTO.setUserAnswer(ad.getAnswerData());
                                    answerDTO.setIsSelected(true);
                                    if (ad.getAnswer().getDisplayOrder() != null){
                                        answerDTO.setDisplayOrder(ad.getAnswer().getDisplayOrder());
                                    }else {
                                        answerDTO.setDisplayOrder(answerDTO.getDisplayOrder());
                                    }
                                    answerDTO.setStatus(ad.getAnswer().getStatus());
                                    answerDTO.setCreatedBy(ad.getCreatedBy());
                                    answerDTO.setCreatedDate(ad.getCreatedDate());
                                    break;
                                } else if (ad.getAnswer() == null && answerDTO.getAnswerId() == null) {
                                    answerDTO.setUserAnswer(ad.getAnswerData());
                                    answerDTO.setIsSelected(true);
                                } else {
                                    answerDTO.setUserAnswer(null);
                                    answerDTO.setIsSelected(false);
                                }
                            }

                        }

                        if (questionDTO.getAnswerType().equals(AppsConstants.AnswerType.MCQ_WITH_MULTIPLE)){
                            answerDTOs = answerDTOs.stream().filter(data -> data.getAnswerId() != null).collect(Collectors.toList());
                        }

                        questionDTO.setAnswers(answerDTOs);
                        questionDTOList.add(questionDTO);

                    }

                        annexureDTO.setQuestions(questionDTOList);
                    annexureDTOList.add(annexureDTO);
                }

                log.info("END: getAnnexureDataByApplicationFormIdOrFacilityPaperId - ESGServiceImpl | annexureDTOList size: {}", annexureDTOList.size());
                return annexureDTOList;

            } catch(Exception e){
                log.error("Unhandled exception in getAnnexureDataByApplicationFormIdOrFacilityPaperId | Error: {}", e.getMessage(), e);
                throw new AppsException("Unexpected error occurred while retrieving annexure data.", e);
            }
        }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AnswerDataDTO deleteAnnexureByAnnexureId(DeleteAnnexureDTO deleteAnnexureDTO, CredentialsDTO credentialsDTO) throws AppsException {
        log.info("START: deleteAnnexureByAnnexureId - ESGServiceImpl | deleteAnnexureDTO: {} by: {}", deleteAnnexureDTO, credentialsDTO.getUserName());

        try {
            if (deleteAnnexureDTO.getAnnexureId() == null) {
                throw new AppsException("Annexure ID must not be null.");
            } else if(deleteAnnexureDTO.getApplicationFormID() == null && deleteAnnexureDTO.getFacilityPaperID() == null) {
                throw new AppsException("Either applicationFormID or facilityPaperID must be provided.");
            }

            List<AnswerData> answerDataList;

            if( deleteAnnexureDTO.getApplicationFormID() != null ){
                log.info("Fetching answer data for annexure ID: {} and application form ID: {}", deleteAnnexureDTO.getAnnexureId(), deleteAnnexureDTO.getApplicationFormID());
                answerDataList = answerDataDao.findByAnnexure_AnnexureIdAndApplicationForm_ApplicationFormID(deleteAnnexureDTO.getAnnexureId(), deleteAnnexureDTO.getApplicationFormID());
            } else if( deleteAnnexureDTO.getFacilityPaperID() != null ) {
                log.info("Fetching answer data for annexure ID: {} and facility paper ID: {}", deleteAnnexureDTO.getAnnexureId(), deleteAnnexureDTO.getFacilityPaperID());
                answerDataList = answerDataDao.findByAnnexure_AnnexureIdAndFacilityPaper_FacilityPaperID(deleteAnnexureDTO.getAnnexureId(), deleteAnnexureDTO.getFacilityPaperID());
            } else {
                throw new AppsException("Either applicationFormID or facilityPaperID must be provided.");
            }

            if (answerDataList == null || answerDataList.isEmpty()) {
                log.warn("No answer data found for annexure ID: {}", deleteAnnexureDTO.getAnnexureId());
            } else {
                log.info("Found {} answer data entries for annexure ID: {}", answerDataList.size(), deleteAnnexureDTO.getAnnexureId());

                for (AnswerData answerData : answerDataList) {
                    answerData.setStatus(AppsConstants.Status.INA);
                    answerData.setModifiedDate(new Date());
                    answerData.setModifiedBy(credentialsDTO.getUserName());
                }

                answerDataDao.saveAll(answerDataList);
                log.info("Marked answer data as inactive for annexure ID: {}", deleteAnnexureDTO.getAnnexureId());
            }

            AnswerDataDTO responseDTO = new AnswerDataDTO();
            responseDTO.setAnnexureId(deleteAnnexureDTO.getAnnexureId());

            log.info("END: deleteAnnexureByAnnexureId - ESGServiceImpl | response: {}", responseDTO);
            return responseDTO;

        } catch (AppsException ae) {
            log.error("AppsException in deleteAnnexureByAnnexureId | annexureId: {} | Error: {}", deleteAnnexureDTO.getAnnexureId(), ae.getMessage());
            throw ae;
        } catch (Exception e) {
            log.error("Unhandled exception in deleteAnnexureByAnnexureId | annexureId: {} | Error: {}", deleteAnnexureDTO.getAnnexureId(), e.getMessage(), e);
            throw new AppsException("Unexpected error occurred while deleting annexure answer data for ID: " + deleteAnnexureDTO.getAnnexureId(), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AnnexureDTO refreshAnnexure(DeleteAnnexureDTO deleteAnnexureDTO, CredentialsDTO credentialsDTO) throws AppsException {

        log.info("START: refreshAnnexure - ESGServiceImpl | deleteAnnexureDTO: {} by: {}", deleteAnnexureDTO, credentialsDTO.getUserName());

        AnswerDataDTO deletedAnswerData = deleteAnnexureByAnnexureId(deleteAnnexureDTO, credentialsDTO);

        AnnexureDTO annexureDTO = getAnnexureByAnnexureId(deleteAnnexureDTO.getAnnexureId(), credentialsDTO);

        log.info("END: refreshAnnexure - ESGServiceImpl | response: {}", annexureDTO);

        return annexureDTO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<EsgDocStorageDTO> addAttachments(EsgDocStorageDTO esgDocStorageDTO, CredentialsDTO credentialsDTO) throws AppsException {


        log.info("START | addAttachments - ESGServiceImpl | name: {}, fileName: {}, user: {}",
                esgDocStorageDTO.getName(),
                esgDocStorageDTO.getFileName(),
                credentialsDTO.getUserID());

        EsgDocStorage esgDocStorage = new EsgDocStorage();

        if(esgDocStorageDTO.getFacilityPaperID() != null){
            FacilityPaper facilityPaper = facilityPaperDao.findById(esgDocStorageDTO.getFacilityPaperID())
                    .orElseThrow(() -> new AppsException("Facility Paper not found for ID: " + esgDocStorageDTO.getFacilityPaperID()));
            esgDocStorage.setFacilityPaper(facilityPaper);
        } else if(esgDocStorageDTO.getApplicationFormID() != null){
            ApplicationForm applicationForm = applicationFormDao.findById(esgDocStorageDTO.getApplicationFormID())
                    .orElseThrow(() -> new AppsException("Application Form not found for ID: " + esgDocStorageDTO.getApplicationFormID()));
            esgDocStorage.setApplicationForm(applicationForm);
        } else {
            throw new AppsException("Either Facility Paper ID or Application Form ID must be provided.");
        }
        esgDocStorage.setName(esgDocStorageDTO.getName());
        esgDocStorage.setDescription(esgDocStorageDTO.getDescription());
        esgDocStorage.setFileName(esgDocStorageDTO.getFileName());
        esgDocStorage.setDocument(esgDocStorageDTO.getDocument());
        esgDocStorage.setCreatedBy(credentialsDTO.getUserName());
        esgDocStorage.setCreatedDate(new Date());
        esgDocStorage.setStatus(AppsConstants.Status.ACT);

        esgDocStorageDao.save(esgDocStorage);

        //EsgDocStorageDTO docStorageDTO = new EsgDocStorageDTO(esgDocStorage);
        DeleteAnnexureDTO deleteAnnexureDTO = new DeleteAnnexureDTO();
        deleteAnnexureDTO.setFacilityPaperID(esgDocStorage.getFacilityPaper() != null ? esgDocStorage.getFacilityPaper().getFacilityPaperID() : null);
        deleteAnnexureDTO.setApplicationFormID(esgDocStorage.getApplicationForm() != null ? esgDocStorage.getApplicationForm().getApplicationFormID() : null);
        List<EsgDocStorageDTO> docStorageDTOList = getAttachments(deleteAnnexureDTO, credentialsDTO);
        log.info("END: addAttachments - ESGServiceImpl | response: {}", docStorageDTOList);
        return docStorageDTOList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<EsgDocStorageDTO> getAttachments(DeleteAnnexureDTO deleteAnnexureDTO, CredentialsDTO credentialsDTO) throws AppsException {
        log.info("START: getAttachments - ESGServiceImpl | deleteAnnexureDTO: {} by: {}", deleteAnnexureDTO, credentialsDTO.getUserName());

        List<EsgDocStorageDTO> attachmentDTOs = new ArrayList<>();

        if (deleteAnnexureDTO.getFacilityPaperID() == null && deleteAnnexureDTO.getApplicationFormID() == null) {
            throw new AppsException("Either Facility Paper ID or Application Form ID must be provided.");
        }

        else if (deleteAnnexureDTO.getFacilityPaperID() != null) {
            log.info("Fetching attachments for Facility Paper ID: {}", deleteAnnexureDTO.getFacilityPaperID());
            List<EsgDocStorage> attachments = esgDocStorageDao.findByFacilityPaper_FacilityPaperID(deleteAnnexureDTO.getFacilityPaperID());
            attachmentDTOs = attachments.stream().map(EsgDocStorageDTO::new).collect(Collectors.toList());
        }

        else if (deleteAnnexureDTO.getApplicationFormID() != null) {
            log.info("Fetching attachments for Application Form ID: {}", deleteAnnexureDTO.getApplicationFormID());
            List<EsgDocStorage> attachments = esgDocStorageDao.findByApplicationForm_ApplicationFormID(deleteAnnexureDTO.getApplicationFormID());
            attachmentDTOs = attachments.stream().map(EsgDocStorageDTO::new).collect(Collectors.toList());
        }

        log.info("END: getAttachments - ESGServiceImpl | response size: {}", attachmentDTOs.size());
        return attachmentDTOs;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public byte[] getAttachmentById(Integer esgStorageID, CredentialsDTO credentialsDTO) throws AppsException {

        log.info("START: getAttachmentById - ESGServiceImpl | esgStorageID: {} by: {}", esgStorageID, credentialsDTO.getUserName());

        if (esgStorageID == null) {
            throw new AppsException("EsgStorage ID must not be null.");
        }

        EsgDocStorage esgDocStorage = esgDocStorageDao.findById(esgStorageID)
                .orElseThrow(() -> new AppsException("Attachment not found for ID: " + esgStorageID));

        EsgDocStorageDTO esgDocStorageDTO = new EsgDocStorageDTO();
        esgDocStorageDTO.setDocument(esgDocStorage.getDocument());

        log.info("integrationService.logOriginalBase64 {}", integrationService.logOriginalBase64) ;
        if(integrationService.isLogOriginalBase64() == true){
            log.info("*******************") ;
            log.info("END: getAttachmentById - ESGServiceImpl | esgDocStorageDTO: {}", esgDocStorageDTO.getDocument());
        }
        return esgDocStorageDTO.getDocument();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<EsgDocStorageDTO> updateAttachment(Integer esgStorageID, EsgDocStorageDTO esgDocStorageDTO, CredentialsDTO credentialsDTO) throws AppsException {

        log.info("START: updateAttachment - ESGServiceImpl | esgStorageID: {} by: {}", esgStorageID, credentialsDTO.getUserName());

        if (esgStorageID == null) {
            throw new AppsException("EsgStorage ID must not be null.");
        }

        EsgDocStorage existingAttachment = esgDocStorageDao.findById(esgStorageID)
                .orElseThrow(() -> new AppsException("Attachment not found for ID: " + esgStorageID));

        existingAttachment.setName(esgDocStorageDTO.getName());
        existingAttachment.setDescription(esgDocStorageDTO.getDescription());
        existingAttachment.setFileName(esgDocStorageDTO.getFileName());
        if (esgDocStorageDTO.getDocument() != null) {
            existingAttachment.setDocument(esgDocStorageDTO.getDocument());
        }
        existingAttachment.setModifiedBy(credentialsDTO.getUserName());
        existingAttachment.setModifiedDate(new Date());
        existingAttachment.setStatus(AppsConstants.Status.ACT);

        if (esgDocStorageDTO.getFacilityPaperID() != null) {
            FacilityPaper facilityPaper = facilityPaperDao.findById(esgDocStorageDTO.getFacilityPaperID())
                    .orElseThrow(() -> new AppsException("Facility Paper not found for ID: " + esgDocStorageDTO.getFacilityPaperID()));
            existingAttachment.setFacilityPaper(facilityPaper);
        } else if (esgDocStorageDTO.getApplicationFormID() != null) {
            ApplicationForm applicationForm = applicationFormDao.findById(esgDocStorageDTO.getApplicationFormID())
                    .orElseThrow(() -> new AppsException("Application Form not found for ID: " + esgDocStorageDTO.getApplicationFormID()));
            existingAttachment.setApplicationForm(applicationForm);
        } else {
            throw new AppsException("Either Facility Paper ID or Application Form ID must be provided.");
        }

        esgDocStorageDao.save(existingAttachment);

        //EsgDocStorageDTO updatedAttachmentDTO = new EsgDocStorageDTO(existingAttachment);
        DeleteAnnexureDTO deleteAnnexureDTO = new DeleteAnnexureDTO();
        deleteAnnexureDTO.setFacilityPaperID(existingAttachment.getFacilityPaper() != null ? existingAttachment.getFacilityPaper().getFacilityPaperID() : null);
        deleteAnnexureDTO.setApplicationFormID(existingAttachment.getApplicationForm() != null ? existingAttachment.getApplicationForm().getApplicationFormID() : null);
        List<EsgDocStorageDTO> docStorageDTOList = getAttachments(deleteAnnexureDTO, credentialsDTO);
        return docStorageDTOList;
    }

    @Override
    public EsgDocStorageDTO deleteAttachment(Integer esgStorageID, CredentialsDTO credentialsDTO) throws AppsException {

        log.info("START: deleteAttachment - ESGServiceImpl | esgStorageID: {} by: {}", esgStorageID, credentialsDTO.getUserName());

        if (esgStorageID == null) {
            throw new AppsException("EsgStorage ID must not be null.");
        }

        EsgDocStorage esgDocStorage = esgDocStorageDao.findById(esgStorageID)
                .orElseThrow(() -> new AppsException("Attachment not found for ID: " + esgStorageID));
        esgDocStorage.setStatus(AppsConstants.Status.INA);
        esgDocStorageDao.save(esgDocStorage);
        EsgDocStorageDTO deletedAttachmentDTO = new EsgDocStorageDTO(esgDocStorage);
        return deletedAttachmentDTO;
    }
}
