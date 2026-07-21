package com.itechro.cas.service.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.esg.*;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.esg.*;
import com.itechro.cas.model.dto.applicationform.esg.AFAnnexRespDTO;
import com.itechro.cas.model.dto.esg.AnnexureAnswerDTO;
import com.itechro.cas.model.dto.esg.AnnexureDTO;
import com.itechro.cas.model.dto.esg.AnnexureQuestionDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnexureService {

    private static final Logger LOG = LoggerFactory.getLogger(AnnexureService.class);

    private final AnnexureDao annexureDao;

    private final AnnexureTempDao annexureTempDao;

    private final AnnexureAudDao annexureAudDao;

    private final AnnexureQuestionDao annexureQuestionDao;

    private final AnnexureQuestionTempDao annexureQuestionTempDao;

    private final AnnexureQuestionAudDao annexureQuestionAudDao;

    private final AnnexureAnswerDao annexureAnswerDao;

    private final AnnexureAnswerTempDao annexureAnswerTempDao;

    private final AnnexureAnswerAudDao annexureAnswerAudDao;

    private final AnnexureService annexureServices;

    @PersistenceContext
    private EntityManager entityManager;

    @Lazy
    public AnnexureService(AnnexureDao annexureDao, AnnexureTempDao annexureTempDao, AnnexureAudDao annexureAudDao,
                           AnnexureQuestionDao annexureQuestionDao, AnnexureQuestionTempDao annexureQuestionTempDao,
                           AnnexureQuestionAudDao annexureQuestionAudDao, AnnexureAnswerDao annexureAnswerDao, AnnexureAnswerTempDao annexureAnswerTempDao,
                           AnnexureAnswerAudDao annexureAnswerAudDao, AnnexureService annexureServices) {
        this.annexureDao = annexureDao;
        this.annexureTempDao = annexureTempDao;
        this.annexureAudDao = annexureAudDao;
        this.annexureQuestionDao = annexureQuestionDao;
        this.annexureQuestionTempDao = annexureQuestionTempDao;
        this.annexureQuestionAudDao = annexureQuestionAudDao;
        this.annexureAnswerDao = annexureAnswerDao;
        this.annexureAnswerTempDao = annexureAnswerTempDao;
        this.annexureAnswerAudDao = annexureAnswerAudDao;
        this.annexureServices = annexureServices;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public AFAnnexRespDTO getAnnexes() throws AppsException{
        AFAnnexRespDTO response = new AFAnnexRespDTO();

        try {
            if (!annexureTempDao.findAll().isEmpty()){
                List<AnnexureTemp> annexureTemps = annexureTempDao.findAll();
                List<AnnexureDTO> tempAnnexes = annexureTemps.stream().map(AnnexureDTO::new).collect(Collectors.toList());
                response.setTempAnnexes(tempAnnexes);
            }

            if (!annexureDao.findAll().isEmpty()){
                List<Annexure> annexures = annexureDao.findAll();
                List<AnnexureDTO> masterAnnexes = annexures.stream().map(AnnexureDTO::new).collect(Collectors.toList());
                response.setMasterAnnexes(masterAnnexes);
            }
        } catch (Exception e){
            LOG.info("Failed to fetch annexes: ", e);
            throw new AppsException("Failed to fetch annexes.");
        }

        return response;
    }

    public AnnexureDTO getAnnexeById(Integer annexureId) throws AppsException{
        AnnexureDTO response = new AnnexureDTO();

        try {
            if (annexureDao.findById(annexureId).isPresent()) {
                Annexure afAnnexure = annexureDao.findById(annexureId).get();
                response = new AnnexureDTO(afAnnexure);
            }
        } catch (Exception e){
            LOG.info("Failed to fetch annexe by Id: ", e);
            throw new AppsException("Failed to fetch annexe by Id.");
        }

        return response;
    }

    public AnnexureDTO getTempAnnexeById(Integer annexureId) throws AppsException{
        AnnexureDTO response = new AnnexureDTO();

        try {
            if (annexureTempDao.findById(annexureId).isPresent()) {
                AnnexureTemp annexure = annexureTempDao.findById(annexureId).get();
                response = new AnnexureDTO(annexure);
            }
        } catch (Exception e){
            LOG.info("Failed to fetch annexe by Id: ", e);
            throw new AppsException("Failed to fetch annexe by Id.");
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public AFAnnexRespDTO saveAnnex(AnnexureDTO annex, CredentialsDTO credentialsDTO) throws AppsException {
        AFAnnexRespDTO response;
        Date date = new Date();

        try {
            AnnexureTemp annexTemp = annexureTempDao.findById(annex.getAnnexureId()).orElse(new AnnexureTemp());

            if (!annex.getActionStatus().equals(DomainConstants.MasterDataActionStatus.DRAFT.getValue()) &&
                    !annex.getActionStatus().equals(DomainConstants.MasterDataActionStatus.NEW.getValue())) {
                annexTemp.setParentId(annex.getAnnexureId());
            } else {
                annexTemp.setParentId(0);
            }
            annexTemp.setName(annex.getName());
            annexTemp.setDescription(annex.getDescription());
            annexTemp.setIsMandatory(annex.getIsMandatory());
            annexTemp.setAllowRiskEdit(annex.getAllowRiskEdit());
            annexTemp.setStatus(annex.getStatus());
            annexTemp.setCreatedBy(credentialsDTO.getUserName());
            annexTemp.setCreatedDate(date);
            annexTemp.setActionStatus(annex.getActionStatus());

            if (annex.getAnnexureId() != null && annex.getAnnexureId() > 0) {
                annexTemp.setModifiedBy(credentialsDTO.getUserName());
                annexTemp.setModifiedDate(date);
            }

            List<AnnexureQuestionTemp> tempQuestions = new ArrayList<>();

            for (AnnexureQuestionDTO question : annex.getQuestions()) {
                AnnexureQuestionTemp questionTemp = saveTempQuestion(question, annexTemp, credentialsDTO);
                if (questionTemp != null) {
                    tempQuestions.add(questionTemp);
                }
            }
            annexTemp.setAnnexureQuestionList(tempQuestions);

            annexTemp = annexureTempDao.saveAndFlush(annexTemp);

            response = this.annexureServices.getAnnexes();
            if (annex.getActionStatus().equals(DomainConstants.MasterDataActionStatus.NEW.getValue())) {
                response.getTempAnnexes().clear();
                response.getTempAnnexes().add(new AnnexureDTO(annexTemp));
            }

        } catch (Exception e) {
            LOG.info("Failed to save annexes: ", e);
            throw new AppsException("Failed to save annexes.");
        }

        return response;
    }

    public AnnexureQuestionTemp saveTempQuestion(AnnexureQuestionDTO question, AnnexureTemp annexTemp, CredentialsDTO credentialsDTO) {

        Date date = new Date();

        AnnexureQuestionTemp questionTemp = annexureQuestionTempDao.findById(question.getQuestionId()).orElse(new AnnexureQuestionTemp());

        if (questionTemp.getParentId() != null && questionTemp.getParentId() == 0 && question.getActionStatus().equals(DomainConstants.MasterDataActionStatus.DELETE.getValue())){
            if (question.getAnswers() != null && !question.getAnswers().isEmpty()) {
                annexureAnswerTempDao.deleteByQuestionId(question.getQuestionId());
            }
            annexureQuestionTempDao.deleteById(question.getQuestionId());
            questionTemp = null;
        } else {
            if (!question.getActionStatus().equals(DomainConstants.MasterDataActionStatus.DRAFT.getValue()) &&
                    !question.getActionStatus().equals(DomainConstants.MasterDataActionStatus.NEW.getValue())) {
                questionTemp.setParentId(question.getQuestionId());
            } else {
                questionTemp.setParentId(0);
            }

            questionTemp.setQuestion(question.getQuestion());
            questionTemp.setAnnexureTemp(annexTemp);
            questionTemp.setDisplayOrder(question.getDisplayOrder());
            questionTemp.setIsMandatory(question.getIsMandatory());
            questionTemp.setAnswerType(question.getAnswerType());
            questionTemp.setStatus(question.getStatus());
            questionTemp.setCreatedBy(credentialsDTO.getUserName());
            questionTemp.setCreatedDate(date);
            questionTemp.setActionStatus(question.getActionStatus());

            if (question.getQuestionId() != null && question.getQuestionId() > 0) {
                questionTemp.setModifiedBy(credentialsDTO.getUserName());
                questionTemp.setModifiedDate(date);
            }

            if (!question.getAnswers().isEmpty()) {
                List<AnnexureAnswerTemp> tempAnswers = new ArrayList<>();

                for (AnnexureAnswerDTO answer : question.getAnswers()) {
                    AnnexureAnswerTemp answerTemp = annexureAnswerTempDao.findById(answer.getAnswerId()).orElse(new AnnexureAnswerTemp());

                    if (answerTemp.getParentId() != null && answerTemp.getParentId() == 0 && answer.getActionStatus().equals(DomainConstants.MasterDataActionStatus.DELETE.getValue())) {
                        annexureAnswerTempDao.deleteById(answerTemp.getAnswerId());
                    } else {
                        if (!answer.getActionStatus().equals(DomainConstants.MasterDataActionStatus.DRAFT.getValue()) &&
                                !answer.getActionStatus().equals(DomainConstants.MasterDataActionStatus.NEW.getValue())) {
                            answerTemp.setParentId(answer.getAnswerId());
                        } else {
                            answerTemp.setParentId(0);
                        }

                        answerTemp.setAnswer(answer.getAnswer());
                        answerTemp.setAnnexureQuestionTemp(questionTemp);
                        answerTemp.setDisplayOrder(answer.getDisplayOrder());
                        answerTemp.setStatus(answer.getStatus());
                        answerTemp.setCreatedBy(credentialsDTO.getUserName());
                        answerTemp.setCreatedDate(date);
                        answerTemp.setActionStatus(answer.getActionStatus());

                        if (answer.getAnswerId() != null && answer.getAnswerId() > 0) {
                            answerTemp.setModifiedBy(credentialsDTO.getUserName());
                            answerTemp.setModifiedDate(date);
                        }

                        tempAnswers.add(answerTemp);
                    }
                }
                questionTemp.setAnnexureAnswerList(tempAnswers);
            }
        }
        return questionTemp;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public AFAnnexRespDTO approveRejectAnnex(AnnexureDTO annex, CredentialsDTO credentialsDTO) throws AppsException{
        AFAnnexRespDTO response;

        try {
            if (annex.getApprovedStatus().equals(DomainConstants.MasterDataApproveStatus.APPROVED.getValue())) {
                saveOrUpdateMasterAnnex(annex, credentialsDTO);
            }

            deleteFromTempAnnex(annex);

            //Manage history
            saveHistory(annex,credentialsDTO);

            //flush before get annexes
            annexureTempDao.flush();
            annexureDao.flush();
            entityManager.clear();

            response = this.annexureServices.getAnnexes();

        } catch (Exception e){
            LOG.info("Failed to authorize annexes: ", e);
            throw new AppsException("Failed to authorized annexes.");
        }

        return response;
    }

    public void saveOrUpdateMasterAnnex(AnnexureDTO annex, CredentialsDTO credentialsDTO) throws AppsException{
        Date date = new Date();
        try {
            Annexure annexMaster = new Annexure();
            if (annex.getParentId() > 0){
                annexMaster.setAnnexureId(annex.getParentId());
                annexMaster.setModifiedBy(annex.getModifiedBy());
                annexMaster.setModifiedDate(annex.getModifiedDate());
            } else {
                annexMaster.setAnnexureId(annex.getAnnexureId());
                annexMaster.setCreatedBy(annex.getCreatedBy());
                annexMaster.setCreatedDate(annex.getCreatedDate());
            }
            annexMaster.setName(annex.getName());
            annexMaster.setDescription(annex.getDescription());
            annexMaster.setIsMandatory(annex.getIsMandatory());
            annexMaster.setAllowRiskEdit(annex.getAllowRiskEdit());
            annexMaster.setStatus(annex.getStatus());
            annexMaster.setApprovedStatus(annex.getApprovedStatus());
            annexMaster.setApprovedBy(credentialsDTO.getUserName());
            annexMaster.setApprovedDate(date);

            List<AnnexureQuestion> masterQuestions = mapQuestions(annex,annexMaster);
            annexMaster.setAnnexureQuestionList(masterQuestions);

            annexureDao.saveAndFlush(annexMaster);
        }catch (Exception e){
            LOG.info("Failed to save master data: ", e);
            throw new AppsException("Failed to save master annexes.");
        }
    }

    public List<AnnexureQuestion> mapQuestions(AnnexureDTO annex, Annexure annexMaster) {
        List<AnnexureQuestion> masterQuestions = new ArrayList<>();

        for (AnnexureQuestionDTO question : annex.getQuestions()) {
            if (question.getParentId() == 0 || question.getActionStatus().equals(DomainConstants.MasterDataActionStatus.UPDATE.getValue())) {

                List<AnnexureAnswerDTO> answers = question.getAnswers().stream().map(data -> {
                    if (data.getActionStatus().equals(DomainConstants.MasterDataActionStatus.SUBMITTED.getValue())){
                        data.setActionStatus(DomainConstants.MasterDataActionStatus.UPDATE.toString());
                    }
                    return data;
                }).collect(Collectors.toList());
                question.setAnswers(answers);
                AnnexureQuestion questionMaster = saveQuestion(question, annexMaster);
                masterQuestions.add(questionMaster);
            }

            if (question.getActionStatus().equals(DomainConstants.MasterDataActionStatus.SUBMITTED.getValue())) {
                annexureQuestionDao.findById(question.getParentId()).ifPresent(
                        questionMaster -> {
                            if (!question.getAnswers().isEmpty()) {
                                List<AnnexureAnswer> masterAnswers = mapAnswers(question, questionMaster);
                                questionMaster.setAnnexureAnswerList(masterAnswers);
                            }
                        }
                );
            }

            //handle inactive previous question
            if (question.getActionStatus().equals(DomainConstants.MasterDataActionStatus.UPDATE.getValue())
                    || question.getActionStatus().equals(DomainConstants.MasterDataActionStatus.DELETE.getValue())) {
                annexureQuestionDao.findById(question.getParentId()).ifPresent(
                        inactiveQuestion -> {
                            inactiveQuestion.setStatus(AppsConstants.Status.INA.toString());
                            inactiveQuestion.setModifiedBy(question.getModifiedBy());
                            inactiveQuestion.setModifiedDate(question.getModifiedDate());
                            masterQuestions.add(inactiveQuestion);
                        }
                );
            }
        }
        return masterQuestions;
    }

    public AnnexureQuestion saveQuestion(AnnexureQuestionDTO question, Annexure annexMaster) {

        AnnexureQuestion questionMaster = new AnnexureQuestion();

        questionMaster.setQuestionId(question.getQuestionId());
        questionMaster.setQuestion(question.getQuestion());
        questionMaster.setAnnexure(annexMaster);
        questionMaster.setDisplayOrder(question.getDisplayOrder());
        questionMaster.setIsMandatory(question.getIsMandatory());
        questionMaster.setAnswerType(question.getAnswerType());
        questionMaster.setStatus(question.getStatus());
        questionMaster.setCreatedBy(question.getCreatedBy());
        questionMaster.setCreatedDate(question.getCreatedDate());

        if (!question.getAnswers().isEmpty()) {
            List<AnnexureAnswer> masterAnswers = mapAnswers(question,questionMaster);
            questionMaster.setAnnexureAnswerList(masterAnswers);
        }

        return questionMaster;
    }

    public List<AnnexureAnswer> mapAnswers(AnnexureQuestionDTO question, AnnexureQuestion questionMaster) {
        List<AnnexureAnswer> masterAnswers = new ArrayList<>();

        for (AnnexureAnswerDTO answer : question.getAnswers()) {

            if (answer.getParentId() == 0 || answer.getActionStatus().equals(DomainConstants.MasterDataActionStatus.UPDATE.getValue())) {
                AnnexureAnswer answerMaster = saveAnswer(answer, questionMaster);
                masterAnswers.add(answerMaster);
            }

            //handle inactive previous answer
            if (answer.getActionStatus().equals(DomainConstants.MasterDataActionStatus.UPDATE.getValue()) || answer.getActionStatus().equals(DomainConstants.MasterDataActionStatus.DELETE.getValue())) {
                annexureAnswerDao.findById(answer.getParentId()).ifPresent(
                        inactiveAnswer -> {
                            inactiveAnswer.setStatus(AppsConstants.Status.INA.toString());
                            inactiveAnswer.setModifiedBy(answer.getModifiedBy());
                            inactiveAnswer.setModifiedDate(answer.getModifiedDate());
                            masterAnswers.add(inactiveAnswer);
                        }
                );
            }
        }

        return masterAnswers;
    }

    public AnnexureAnswer saveAnswer(AnnexureAnswerDTO answer, AnnexureQuestion questionMaster) {
        AnnexureAnswer answerMaster = new AnnexureAnswer();

        answerMaster.setAnswerId(answer.getAnswerId());
        answerMaster.setAnswer(answer.getAnswer());
        answerMaster.setAnnexureQuestion(questionMaster);
        answerMaster.setDisplayOrder(answer.getDisplayOrder());
        answerMaster.setStatus(answer.getStatus());
        answerMaster.setCreatedBy(answer.getCreatedBy());
        answerMaster.setCreatedDate(answer.getCreatedDate());

        return answerMaster;
    }

    public void saveHistory(AnnexureDTO annex, CredentialsDTO credentialsDTO){
        Date date = new Date();
        try {
            AnnexureAud annexureAud = new AnnexureAud();
            if (annex.getParentId() > 0){
                annexureAud.setAnnexureId(annex.getParentId());
            } else {
                annexureAud.setAnnexureId(annex.getAnnexureId());
            }

            if (!annex.getActionStatus().equals(DomainConstants.MasterDataActionStatus.SUBMITTED.getValue())) {
                annexureAud.setName(annex.getName());
                annexureAud.setDescription(annex.getDescription());
                annexureAud.setIsMandatory(annex.getIsMandatory());
                annexureAud.setAllowRiskEdit(annex.getAllowRiskEdit());
                annexureAud.setStatus(annex.getStatus());
                annexureAud.setCreatedBy(annex.getCreatedBy());
                annexureAud.setCreatedDate(annex.getCreatedDate());
                annexureAud.setModifiedBy(annex.getModifiedBy());
                annexureAud.setModifiedDate(annex.getModifiedDate());
                annexureAud.setApprovedStatus(annex.getApprovedStatus());
                annexureAud.setApprovedBy(credentialsDTO.getUserName());
                annexureAud.setApprovedDate(date);

                annexureAudDao.saveAndFlush(annexureAud);
            }

            saveQuestionHistory(annex.getQuestions(), annexureAud);

        }catch (Exception e){
            LOG.info("Failed to save history: ", e);
        }
    }

    public void saveQuestionHistory(List<AnnexureQuestionDTO> questions, AnnexureAud annexureAud){

        for (AnnexureQuestionDTO question : questions) {
            AnnexureQuestionAud questionAud = new AnnexureQuestionAud();
            if (question.getParentId() > 0) {
                questionAud.setQuestionId(question.getParentId());
            } else {
                questionAud.setQuestionId(question.getQuestionId());
            }

            if (!question.getActionStatus().equals(DomainConstants.MasterDataActionStatus.SUBMITTED.getValue())) {
                questionAud.setQuestion(question.getQuestion());
                questionAud.setAnnexureId(annexureAud.getAnnexureId());
                questionAud.setDisplayOrder(question.getDisplayOrder());
                questionAud.setIsMandatory(question.getIsMandatory());
                questionAud.setAnswerType(question.getAnswerType());
                questionAud.setStatus(question.getStatus());
                questionAud.setCreatedBy(question.getCreatedBy());
                questionAud.setCreatedDate(question.getCreatedDate());
                questionAud.setModifiedBy(question.getModifiedBy());
                questionAud.setModifiedDate(question.getModifiedDate());

                annexureQuestionAudDao.saveAndFlush(questionAud);
            }

            if (!question.getAnswers().isEmpty()) {
                saveAnswerAud(question.getAnswers(), questionAud);
            }
        }
    }

    public void saveAnswerAud(List<AnnexureAnswerDTO> answers, AnnexureQuestionAud questionAud){
        for (AnnexureAnswerDTO answer : answers) {

            if (!answer.getActionStatus().equals(DomainConstants.MasterDataActionStatus.SUBMITTED.getValue())) {
                AnnexureAnswerAud answerAud = new AnnexureAnswerAud();
                if (answer.getParentId() > 0) {
                    answerAud.setAnswerId(answer.getParentId());
                } else {
                    answerAud.setAnswerId(answer.getAnswerId());
                }

                answerAud.setAnswer(answer.getAnswer());
                answerAud.setQuestionId(questionAud.getQuestionId());
                answerAud.setDisplayOrder(answer.getDisplayOrder());
                answerAud.setStatus(answer.getStatus());
                answerAud.setCreatedBy(answer.getCreatedBy());
                answerAud.setCreatedDate(answer.getCreatedDate());
                answerAud.setModifiedBy(answer.getModifiedBy());
                answerAud.setModifiedDate(answer.getModifiedDate());

                annexureAnswerAudDao.saveAndFlush(answerAud);
            }
        }
    }

    public void deleteFromTempAnnex(AnnexureDTO annex){
        try {
            for (AnnexureQuestionDTO question : annex.getQuestions()){
                for (AnnexureAnswerDTO answer : question.getAnswers()) {
                    annexureAnswerTempDao.deleteById(answer.getAnswerId());
                }
                annexureQuestionTempDao.deleteById(question.getQuestionId());
            }
            annexureTempDao.deleteById(annex.getAnnexureId());

        } catch (Exception e){
            LOG.info("Failed to delete temp data: ", e);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public AFAnnexRespDTO deleteTempAnnexure(Integer annexureId) throws AppsException{
        AFAnnexRespDTO response;

        try {
            AnnexureTemp annexureTemp = annexureTempDao.findById(annexureId).orElseThrow(() -> new AppsException("Annexure not found"));

            //delete temp anexure details
            this.deleteFromTempAnnex(new AnnexureDTO(annexureTemp));

            // flush before get annexes
            annexureTempDao.flush();
            entityManager.clear();

            response = this.annexureServices.getAnnexes();
        } catch (Exception e){
            LOG.info("Failed to delete annexure: ", e);
            throw new AppsException("Failed to delete annexure.");
        }

        return response;
    }
}
