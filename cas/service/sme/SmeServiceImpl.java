package com.itechro.cas.service.sme;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.customer.CustomerDao;
import com.itechro.cas.dao.facilitypaper.CasCustomerDao;
import com.itechro.cas.dao.facilitypaper.FacilityPaperDao;
import com.itechro.cas.dao.sme.*;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.customer.Customer;
import com.itechro.cas.model.domain.facilitypaper.CASCustomer;
import com.itechro.cas.model.domain.facilitypaper.facility.Facility;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.domain.sme.*;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.dto.sme.FpSmeAnswerDTO;
import com.itechro.cas.model.dto.sme.SmeCustomerTurnoverResDTO;
import com.itechro.cas.model.dto.sme.SmeCustomerTurnoverRqDTO;
import com.itechro.cas.model.dto.sme.SmeQuestionDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.integration.IntegrationService;
import lombok.extern.slf4j.Slf4j;
import org.jsmpp.bean.OptionalParameter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SmeServiceImpl implements SmeService{

    private final SmeQuestionDao smeQuestionDao;

    private final SmeQuestionAnswerDao smeQuestionAnswerDao;

    private final FacilityPaperDao facilityPaperDao;

    private final FpSmeAnswerDao fpSmeAnswerDao;

    private final SmeQuestionConfigDao smeQuestionConfigDao;

    private final SmeAnswerDescriptionDao smeAnswerDescriptionDao;

    private final IntegrationService integrationService;

    private final CasCustomerDao casCustomerDao;

    private final CustomerDao customerDao;

    public SmeServiceImpl(SmeQuestionDao smeQuestionDao, SmeQuestionAnswerDao smeQuestionAnswerDao, FacilityPaperDao facilityPaperDao, FpSmeAnswerDao fpSmeAnswerDao, SmeQuestionConfigDao smeQuestionConfigDao, SmeAnswerDescriptionDao smeAnswerDescriptionDao, IntegrationService integrationService, CasCustomerDao casCustomerDao, CustomerDao customerDao) {
        this.smeQuestionDao = smeQuestionDao;
        this.smeQuestionAnswerDao = smeQuestionAnswerDao;
        this.facilityPaperDao = facilityPaperDao;
        this.fpSmeAnswerDao = fpSmeAnswerDao;
        this.smeQuestionConfigDao = smeQuestionConfigDao;
        this.smeAnswerDescriptionDao = smeAnswerDescriptionDao;
        this.integrationService = integrationService;
        this.casCustomerDao = casCustomerDao;
        this.customerDao = customerDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<SmeQuestionDTO> getAllQuestionsAndAnswers() throws AppsException {
        log.info("START: getAllQuestionsAndAnswers - SmeServiceImpl | Fetching all SME Questions and Answers");
        List<SmeQuestionDTO> smeQuestionDTOList = new ArrayList<>();

        List<SmeQuestion> smeQuestionList;
        try {
            smeQuestionList = smeQuestionDao.findAll();
            if (smeQuestionList == null || smeQuestionList.isEmpty()) {
                log.warn("No SME Questions found.");
                return smeQuestionDTOList;
            }

            for (SmeQuestion smeQuestion : smeQuestionList) {
                List<SmeQuestionAnswer> smeQuestionAnswers = smeQuestionAnswerDao.findBySmeQuestion_SmeQuestionId(smeQuestion.getSmeQuestionId());
                smeQuestion.setSmeQuestionAnswerList(smeQuestionAnswers != null ? smeQuestionAnswers : new ArrayList<>());

                List<SmeQuestionConfig> smeQuestionConfigs = smeQuestionConfigDao.findBySmeQuestion(smeQuestion);
                smeQuestion.setSmeQuestionConfigList(smeQuestionConfigs != null ? smeQuestionConfigs : new ArrayList<>());

                for (SmeQuestionAnswer smeQuestionAnswer : smeQuestionAnswers) {
                    List<SmeAnswerDescription> smeAnswerDescriptions = smeAnswerDescriptionDao.findBySmeQuestionAnswer_SmeAnswerId(smeQuestionAnswer.getSmeAnswerId());
                    log.info("Fetched {} SmeAnswerDescriptions for SmeAnswerId: {}", (smeAnswerDescriptions != null ? smeAnswerDescriptions.size() : 0), smeQuestionAnswer.getSmeAnswerId());
                    smeQuestionAnswer.setSmeAnswerDescriptionList(smeAnswerDescriptions != null ? smeAnswerDescriptions : new ArrayList<>());
                }
            }

            boolean isSave = false;

            smeQuestionDTOList = smeQuestionList.stream()
                    .map(sq -> new SmeQuestionDTO(sq, isSave))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("ERROR: getAllQuestionsAndAnswers - SmeServiceImpl | Error while fetching SME Questions and Answers", e);
            throw new AppsException("Error while fetching SME Questions and Answers", e);
        }

        log.info("END: getAllQuestionsAndAnswers - SmeServiceImpl | Successfully fetched SME Questions and Answers");
        return smeQuestionDTOList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<FpSmeAnswerDTO> saveOrUpdateAnswer(List<FpSmeAnswerDTO> fpSmeAnswerDTOList, CredentialsDTO credentialsDTO) throws AppsException {

        log.info("START: saveOrUpdateAnswer - SmeServiceImpl | Saving or Updating SME Answer: {}", fpSmeAnswerDTOList);
        FpSmeAnswerDTO answerDTO = null;
        List<FpSmeAnswerDTO> answerDTOList = new ArrayList<>();

        try {

            List<FpSmeAnswer> existingAnswers = fpSmeAnswerDao.findByFacilityPaper_FacilityPaperIDAndCreatedBy(
                    fpSmeAnswerDTOList.get(0).getFacilityPaperID(), credentialsDTO.getUserName());
            if (existingAnswers != null && !existingAnswers.isEmpty()) {
                fpSmeAnswerDao.deleteAll(existingAnswers);
                log.info("Deleted existing SME Answers for FacilityPaperID: {} and CreatedBy: {}", fpSmeAnswerDTOList.get(0).getFacilityPaperID(), credentialsDTO.getUserName());
            }

            for (FpSmeAnswerDTO fpSmeAnswerDTO : fpSmeAnswerDTOList) {

                FpSmeAnswer fpSmeAnswer = new FpSmeAnswer();

                SmeQuestion smeQuestion = smeQuestionDao.findById(fpSmeAnswerDTO.getSmeQuestionId())
                        .orElseThrow(() -> new AppsException("SME Question not found with ID: " + fpSmeAnswerDTO.getSmeQuestionId()));
                fpSmeAnswer.setSmeQuestion(smeQuestion);

                SmeQuestionAnswer smeQuestionAnswer = smeQuestionAnswerDao.findById(fpSmeAnswerDTO.getSmeAnswerId())
                        .orElseThrow(() -> new AppsException("SME Answer not found with ID: " + fpSmeAnswerDTO.getSmeAnswerId()));
                fpSmeAnswer.setSmeQuestionAnswer(smeQuestionAnswer);

                FacilityPaper facilityPaper = facilityPaperDao.findById(fpSmeAnswerDTO.getFacilityPaperID())
                        .orElseThrow(() -> new AppsException("Facility Paper not found with ID: " + fpSmeAnswerDTO.getFacilityPaperID()));
                fpSmeAnswer.setFacilityPaper(facilityPaper);

                fpSmeAnswer.setAnswer(fpSmeAnswerDTO.getAnswer());
                fpSmeAnswer.setAnswerDescription(smeQuestionAnswer.getCommentDescription());
                fpSmeAnswer.setCreatedBy(credentialsDTO.getUserName());
                fpSmeAnswer.setCreatedDate(new Date());
                fpSmeAnswer.setCreatedUserWorkClass(fpSmeAnswerDTO.getCreatedUserWorkClass());
                fpSmeAnswer.setCreatedUserDisplayName(credentialsDTO.getDisplayName());

                fpSmeAnswerDao.save(fpSmeAnswer);
                answerDTO = new FpSmeAnswerDTO(fpSmeAnswer);
                log.info("Saved/Updated SME Answer: {}", answerDTO);
                answerDTOList.add(answerDTO);

                }
            } catch(Exception e){
                log.error("ERROR: saveOrUpdateAnswer - SmeServiceImpl | Error while Saving or Updating SME Answer", e);
                throw new AppsException("Error while Saving or Updating SME Answer", e);
            }
        return answerDTOList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, List<FpSmeAnswerDTO>> getAnswer(Integer facilityPaperID, CredentialsDTO credentialsDTO) throws AppsException {

        log.info("START: getAnswer - SmeServiceImpl | Fetching SME Answers for FacilityPaperID: {}", facilityPaperID);

        try {
            List<FpSmeAnswer> fpSmeAnswerList = fpSmeAnswerDao.findByFacilityPaper_FacilityPaperID(facilityPaperID);
            if (fpSmeAnswerList == null || fpSmeAnswerList.isEmpty()) {
                log.warn("getAnswer - SmeServiceImpl | No SME Answers found for FacilityPaperID: {}", facilityPaperID);
                return new HashMap<>();
            }

            List<FpSmeAnswer> filteredAnswers = new ArrayList<>();

            for (FpSmeAnswer fpSmeAnswer : fpSmeAnswerList) {
                log.info("getAnswer - SmeServiceImpl | Processing FpSmeAnswer ID: {}", fpSmeAnswer.getAnswerId());

                SmeQuestion smeQuestion = fpSmeAnswer.getSmeQuestion();
                if (smeQuestion != null && smeQuestion.getSmeQuestionConfigList() != null) {
                    log.info("getAnswer - SmeServiceImpl | Found {} SmeQuestionConfigs for SmeQuestion ID: {}", smeQuestion.getSmeQuestionConfigList().size(), smeQuestion.getSmeQuestionId());

                    for (SmeQuestionConfig smeQuestionConfig : smeQuestion.getSmeQuestionConfigList()) {
                        if (fpSmeAnswer.getCreatedUserWorkClass() == smeQuestionConfig.getWorkClass() && smeQuestionConfig.getIsShowComment() == AppsConstants.YesNo.Y){
                            filteredAnswers.add(fpSmeAnswer);
                            break;
                        }
                    }
                }
            }

            Map<String, List<FpSmeAnswerDTO>> groupedAnswers = filteredAnswers.stream()
                    .map(FpSmeAnswerDTO::new)
                    .collect(Collectors.groupingBy(FpSmeAnswerDTO::getCreatedUserDisplayName));

            log.info("END: getAnswer - SmeServiceImpl | Successfully grouped SME Answers by smeQuestionId");
            return groupedAnswers;

        } catch (Exception e) {
            log.error("ERROR: getAnswer - SmeServiceImpl | Error while fetching SME Answers", e);
            throw new AppsException("Error while fetching SME Answers", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, List<FpSmeAnswerDTO>> getAnswerList(Integer facilityPaperID, CredentialsDTO credentialsDTO) throws AppsException {

        log.info("START: getAnswerList - SmeServiceImpl | Fetching SME Answers for FacilityPaperID: {}", facilityPaperID);

        try {
            List<FpSmeAnswer> fpSmeAnswerList = fpSmeAnswerDao.findByFacilityPaper_FacilityPaperID(facilityPaperID);
            if (fpSmeAnswerList == null || fpSmeAnswerList.isEmpty()) {
                log.warn("No SME Answers found for FacilityPaperID: {}", facilityPaperID);
                return new HashMap<>();
            }

            List<FpSmeAnswer> filteredAnswers = new ArrayList<>();

            for (FpSmeAnswer fpSmeAnswer : fpSmeAnswerList) {
                log.info("Processing FpSmeAnswer ID: {}", fpSmeAnswer.getAnswerId());

                SmeQuestion smeQuestion = fpSmeAnswer.getSmeQuestion();
                if (smeQuestion != null && smeQuestion.getSmeQuestionConfigList() != null) {
                    log.info("Found {} SmeQuestionConfigs for SmeQuestion ID: {}", smeQuestion.getSmeQuestionConfigList().size(), smeQuestion.getSmeQuestionId());
                    filteredAnswers.add(fpSmeAnswer);
                }
            }

            Map<String, List<FpSmeAnswerDTO>> groupedAnswers = filteredAnswers.stream()
                    .map(FpSmeAnswerDTO::new)
                    .collect(Collectors.groupingBy(FpSmeAnswerDTO::getCreatedUserDisplayName));

            log.info("END: getAnswerList - SmeServiceImpl | Successfully grouped SME Answers by smeQuestionId");
            return groupedAnswers;

        } catch (Exception e) {
            log.error("ERROR: getAnswerList - SmeServiceImpl | Error while fetching SME Answers", e);
            throw new AppsException("Error while fetching SME Answers", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SmeCustomerTurnoverResDTO getSmeCustomerTurnoverData(SmeCustomerTurnoverRqDTO request, CredentialsDTO credentialsDTO) throws AppsException {

        log.info("START: getSmeCustomerTurnoverData | CustomerFinacleID: {}", request.getCustId());

        SmeCustomerTurnoverResDTO response = new SmeCustomerTurnoverResDTO();

        if (request.getCustId() == null) {
            response.setTurnover(null);
            response.setCurrencyCode(null);

            log.info("Customer Finacle ID is null. Returning null turnover data.");

            return response;
        }

        if (request.getFacilityPaperId() != null) {

            FacilityPaper facilityPaper = facilityPaperDao.findById(request.getFacilityPaperId())
                    .orElseThrow(() -> new AppsException("Facility Paper not found with ID: " + request.getFacilityPaperId()));

            if (!DomainConstants.FacilityPaperStatus.APPROVED.equals(facilityPaper.getCurrentFacilityPaperStatus())) {
                throw new AppsException("Facility Paper is not approved");
            }

            Customer customer = customerDao.findByCustomerFinancialIDAndStatus(request.getCustId(), AppsConstants.Status.ACT);
            if (customer == null) {
                response.setTurnover(null);
                response.setCurrencyCode(null);

                log.info("Customer not found. Returning null turnover data.");

                return response;
            }

            CASCustomer casCustomer = casCustomerDao.findByCustomerIdAndFacilityPaperId(customer.getCustomerID(), facilityPaper.getFacilityPaperID());
            if (casCustomer == null) {
                response.setTurnover(null);
                response.setCurrencyCode(null);

                log.info("CAS Customer not found. Returning null turnover data.");

                return response;
            }

            response.setTurnover(casCustomer.getTurnover());
            response.setCurrencyCode(casCustomer.getCurrencyCode());
            return response;
        }

        request.setRequestId("CAS_000001");
        response = integrationService.getSmeCustomerTurnoverData(request);

        if (response == null) {
            throw new AppsException("No SME Customer Turnover Data found for CustomerFinacleID: " + request.getCustId());
        }

        log.info("END: getSmeCustomerTurnoverData | CustomerFinacleID: {}", request.getCustId());
        return response;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String isFacilityPaperApproved(Integer customerId, Integer facilityPaperId, SmeCustomerTurnoverRqDTO request, CredentialsDTO credentialsDTO) throws AppsException {

        log.info("START: isFacilityPaperApproved - SmeServiceImpl | Checking if Facility Paper is Approved for customerId: {}", customerId);

        CASCustomer casCustomer = null;
        SmeCustomerTurnoverResDTO customerTurnover = null;

        if (customerId != null && facilityPaperId != null) {

            casCustomer = casCustomerDao.findByCustomerIdAndFacilityPaperId(customerId, facilityPaperId);

            if (casCustomer != null) {
                customerTurnover = getSmeCustomerTurnoverData(request, credentialsDTO);

                if (customerTurnover != null) {
                    casCustomer.setCurrencyCode(customerTurnover.getCurrencyCode());
                    casCustomer.setTurnover(customerTurnover.getTurnover());
                } else {
                    casCustomer.setCurrencyCode(null);
                    casCustomer.setTurnover(null);
                }
            }

        } else {
            log.warn("customerId or facilityPaperId is null");

            return null;
        }

        log.info("END: isFacilityPaperApproved - SmeServiceImpl | Finished checking if Facility Paper is Approved for customerId: {}", customerId);

        return (casCustomer != null) ? casCustomer.getTurnover() : null;
    }
}
