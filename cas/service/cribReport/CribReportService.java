package com.itechro.cas.service.cribReport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.dao.crib.jdbc.CirbResponsesJdbcDao;
import com.itechro.cas.dao.customer.CribSearchHistoryDao;
import com.itechro.cas.dao.storage.DocStorageDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.customer.CribSearchHistory;
import com.itechro.cas.model.dto.customer.CribSearchHistoryDTO;
import com.itechro.cas.model.dto.customer.CustomerCribResponseDTO;
import com.itechro.cas.model.dto.customer.CustomerDTO;
import com.itechro.cas.model.dto.customer.SearchCustomerRQ;
import com.itechro.cas.model.dto.integration.request.CustomerCASCribRQ;
import com.itechro.cas.model.dto.integration.request.cribreport.CribCorporateRQ;
import com.itechro.cas.model.dto.integration.request.cribreport.CribRequestDTO;
import com.itechro.cas.model.dto.integration.request.cribreport.CribRetailReportRQ;
import com.itechro.cas.model.dto.integration.response.cribreport.CribResponseDTO;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CribCorporateReportResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.CribRetailReportResponse;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.cribReport.pdf.CorporateCribReportCreator;
import com.itechro.cas.service.cribReport.pdf.RetailCribReportCreator;
import com.itechro.cas.service.integration.IntegrationService;
import com.itechro.cas.service.storage.StorageService;
import com.itechro.cas.util.CalendarUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;

import java.util.Date;
import java.util.List;

@Service
public class CribReportService implements EnvironmentAware {

    private static final Logger LOG = LoggerFactory.getLogger(IntegrationService.class);

    private Environment environment;

    @Autowired
    private IntegrationService integrationService;

    @Autowired
    private CasProperties casProperties;

    @Autowired
    private CirbResponsesJdbcDao cirbResponsesJdbcDao;

    @Autowired
    @Qualifier("emailTemplateEngine")
    private TemplateEngine templateEngine;

    @Value("${apps.crib.consumer.Male.code}")
    private String consumerMaleCode;

    @Value("${apps.crib.consumer.Female.code}")
    private String consumerFemaleCode;

    @Value("${apps.crib.corporate.ReportId}")
    private String corporateReportID;

    @Value("${apps.crib.corporate.SubjectType}")
    private String corporateSubjectType;

    @Value("${apps.crib.corporate.ResponseType}")
    private String corporateResponseType;

    @Value("${apps.crib.corporate.ReasonCode}")
    private String corporateReasonCode;

    @Autowired
    CribSearchHistoryDao cribSearchHistoryDao;

    @Autowired
    private StorageService storageService;

    @Autowired
    private DocStorageDao docStorageDao;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public String getRetailCribReport(CribRetailReportRQ cribRetailReportRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Get Retail Crib report {} : {}", cribRetailReportRQ, credentialsDTO);


        String genderCode = this.consumerMaleCode;
        String customerName = null;
        String pdfContent;
        try {
            SearchCustomerRQ searchCustomerRQ = new SearchCustomerRQ();
            searchCustomerRQ.setIdentificationType(cribRetailReportRQ.getIdentificationType().toString());
            searchCustomerRQ.setIdentificationNumber(cribRetailReportRQ.getIdentificationNumber());

            CustomerDTO customerDetailFromBank = this.integrationService.getCustomerDetailFromBank(searchCustomerRQ, credentialsDTO);
            if (customerDetailFromBank != null) {
                genderCode = customerDetailFromBank.getTitle() == DomainConstants.Title.MR ? this.consumerMaleCode : this.consumerFemaleCode;
                customerName = customerDetailFromBank.getCustomerName();
            }
        } catch (AppsException e) {
            LOG.error("ERROR : Error loading customer info from bank {}", cribRetailReportRQ, e);
        }

        if (customerName == null || StringUtils.isEmpty(customerName)) {
            LOG.error("ERROR : Empty customer name {}", cribRetailReportRQ);
            pdfContent = "<div class=\"text-align-center\"><p><b>Connection Failed, Please try after a few minutes.</b></p></div>";
        } else {
            cribRetailReportRQ.setCustomerGender(genderCode);
            cribRetailReportRQ.setCustomerName(customerName);

            CribRetailReportResponse response;

            try {
                response = this.integrationService.getCribRetailReport(cribRetailReportRQ, credentialsDTO);
                RetailCribReportCreator contentCreator = new RetailCribReportCreator(response, cribRetailReportRQ, casProperties);
                contentCreator.setEnvironment(this.environment);
                contentCreator.setTemplateEngine(this.templateEngine);

                pdfContent = contentCreator.getPdfContent();
            } catch (Exception e) {
                LOG.info("ERROR: Get Retail Crib report {} : {}", cribRetailReportRQ, credentialsDTO, e);
                pdfContent = "<div class=\"text-align-center\"><p><b>Service Currently Unavailable </b></p></div>";
            }
        }

        LOG.info("END: Get Retail Crib report {} : {}", cribRetailReportRQ, credentialsDTO);
        return pdfContent;

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<CustomerCribResponseDTO> getCribReportFromCasDB(CustomerCASCribRQ customerCASCribRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Get CAS Retail Crib report {} : {}", customerCASCribRQ, credentialsDTO);
        List<CustomerCribResponseDTO> customerCribResponseDTOS = cirbResponsesJdbcDao.getRetailCribReportFromCasDB(customerCASCribRQ);

        ObjectMapper objectMapper = new ObjectMapper();

        for (CustomerCribResponseDTO customerCribResponseDTO : customerCribResponseDTOS) {
            if (StringUtils.isNotEmpty(customerCribResponseDTO.getNic())) {
                CribRetailReportResponse resultObj;
                RetailCribReportCreator contentCreator;
                try {
                    resultObj = objectMapper.readValue(customerCribResponseDTO.getResponse(), CribRetailReportResponse.class);
                    CribRetailReportRQ cribRetailReportRQ = new CribRetailReportRQ();
                    cribRetailReportRQ.setCustomerName(customerCribResponseDTO.getCustomerName());
                    cribRetailReportRQ.setCustomerGender(customerCribResponseDTO.getCustomerGender());
                    cribRetailReportRQ.setIdentificationNumber(customerCribResponseDTO.getNic());
                    cribRetailReportRQ.setReportOrderDateDate(CalendarUtil.getDefaultParsedDateOnly(customerCribResponseDTO.getCreatedDateStr()));
                    contentCreator = new RetailCribReportCreator(resultObj, cribRetailReportRQ, casProperties);
                    contentCreator.setEnvironment(this.environment);
                    contentCreator.setTemplateEngine(this.templateEngine);
                    customerCribResponseDTO.setPdfReport(contentCreator.getPdfContent());
                } catch (Exception e) {
                    LOG.error("ERROR: Deserialization Failed Report From : {} Created Date : {}  by {}", customerCribResponseDTO.getResponse(), customerCribResponseDTO.getCreatedDateStr(), credentialsDTO, e);
                }

            } else if (StringUtils.isNotEmpty(customerCribResponseDTO.getBrn())) {
                CribCorporateReportResponse resultObj;
                CorporateCribReportCreator contentCreator;
                try {
                    resultObj = objectMapper.readValue(customerCribResponseDTO.getResponse(), CribCorporateReportResponse.class);
                    CribCorporateRQ cribCorporateRQ = new CribCorporateRQ();
                    cribCorporateRQ.setCustomerName(customerCribResponseDTO.getCustomerName());
                    cribCorporateRQ.setIdentificationNumber(customerCribResponseDTO.getBrn());
                    cribCorporateRQ.setREGNo(customerCribResponseDTO.getBrn());
                    cribCorporateRQ.setReportOrderDateDate(CalendarUtil.getDefaultParsedDateOnly(customerCribResponseDTO.getCreatedDateStr()));
                    contentCreator = new CorporateCribReportCreator(resultObj, cribCorporateRQ, casProperties);
                    contentCreator.setEnvironment(this.environment);
                    contentCreator.setTemplateEngine(this.templateEngine);
                    customerCribResponseDTO.setPdfReport(contentCreator.getPdfContent());
                } catch (Exception e) {
                    LOG.error("ERROR: Deserialization Failed Report From : {} Created Date : {}  by {}", customerCribResponseDTO.getResponse(), customerCribResponseDTO.getCreatedDateStr(), credentialsDTO, e);
                }
            }
        }
        LOG.info("END: Get CAS Retail Crib report {} : {} Reports Available: {}", customerCASCribRQ, customerCribResponseDTOS.size(), credentialsDTO);
        return customerCribResponseDTOS;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public String getCorporateCribReport(CribCorporateRQ cribCorporateRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Get Corporate Crib report {} : {}", cribCorporateRQ, credentialsDTO);

        CribCorporateReportResponse response;
        String pdfContent;
        try {
            response = this.integrationService.getCorporateCribReport(cribCorporateRQ, credentialsDTO);
            CorporateCribReportCreator contentCreator = new CorporateCribReportCreator(response, cribCorporateRQ, casProperties);
            contentCreator.setEnvironment(this.environment);
            contentCreator.setTemplateEngine(this.templateEngine);

            pdfContent = contentCreator.getPdfContent();
        } catch (Exception e) {
            LOG.info("END: Get Retail Crib report {} : {}", cribCorporateRQ, credentialsDTO, e);
            pdfContent = "<div class=\"text-align-center\"><p><b>Service Currently Unavailable </b></p></div>";
        }

        LOG.info("END: Get Corporate Crib report {} : {}", cribCorporateRQ, credentialsDTO);
        return pdfContent;

    }

    public CribResponseDTO searchIndividualCrib(CribRequestDTO cribRequestDTO) throws AppsException {
        CribResponseDTO cribResponseDTO = new CribResponseDTO();

        try {
            cribResponseDTO = integrationService.searchIndividualCrib(cribRequestDTO);
            LOG.info("Search crib response service {}",cribRequestDTO);
        } catch (Exception e){
            LOG.error("Exception: Search crib response service",e);
        }

        return cribResponseDTO;
    }

    public CribResponseDTO searchIndividualCribContinue(CribRequestDTO cribRequestDTO) throws AppsException {
        CribResponseDTO cribResponseDTO = new CribResponseDTO();

        try {
            cribResponseDTO = integrationService.searchIndividualCribContinue(cribRequestDTO);
            LOG.info("Search crib response continue service {}",cribRequestDTO);
        } catch (Exception e){
            LOG.error("Exception: Search crib response continue service",e);
        }

        return cribResponseDTO;
    }

    public CribResponseDTO searchCompanyCrib(CribRequestDTO cribRequestDTO) throws AppsException {
        CribResponseDTO cribResponseDTO = new CribResponseDTO();

        try {
            cribResponseDTO = integrationService.searchCompanyCrib(cribRequestDTO);
            LOG.info("Search crib response service {}",cribRequestDTO);
        } catch (Exception e){
            LOG.error("Exception: Search crib response service",e);
        }

        return cribResponseDTO;
    }

    public CribResponseDTO searchCompanyCribContinue(CribRequestDTO cribRequestDTO) throws AppsException {
        CribResponseDTO cribResponseDTO = new CribResponseDTO();

        try {
            cribResponseDTO = integrationService.searchCompanyCribContinue(cribRequestDTO);
            LOG.info("Search crib response continue service {}",cribRequestDTO);
        } catch (Exception e){
            LOG.error("Exception: Search crib response continue service",e);
        }

        return cribResponseDTO;
    }

    public CribResponseDTO getCustomReport(CribRequestDTO cribRequestDTO) throws AppsException {
        CribResponseDTO cribResponseDTO = new CribResponseDTO();

        try {
            cribResponseDTO = integrationService.getCustomReport(cribRequestDTO);
            LOG.info("GET crib report token service {}",cribRequestDTO);
        } catch (Exception e){
            LOG.error("Exception: GET crib report token service",e);
        }

        return cribResponseDTO;
    }

    public CribResponseDTO getCustomReportPDF(CribRequestDTO cribRequestDTO) throws AppsException {
        CribResponseDTO cribResponseDTO = new CribResponseDTO();

        try {
            cribResponseDTO = integrationService.getCustomReportPDF(cribRequestDTO);
            LOG.info("GET crib report service {}", cribRequestDTO);
        } catch (Exception e){
            LOG.error("Exception: GET crib report service",e);
        }

        return cribResponseDTO;
    }

    public CribResponseDTO getCustomReportByToken(CribRequestDTO cribRequestDTO) throws AppsException {
        CribResponseDTO cribResponseDTO = new CribResponseDTO();

        try {
            cribResponseDTO = integrationService.getCustomReportByToken(cribRequestDTO);
            LOG.info("CHECK crib report token service {}",cribRequestDTO);
        } catch (Exception e){
            LOG.error("Exception: CHECK crib report token service",e);
        }

        return cribResponseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CribSearchHistoryDTO saveCribSearch(CribSearchHistoryDTO cribSearchHistoryDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START: Save Customer Crib Report Search :{} by :{}", cribSearchHistoryDTO, credentialsDTO.getUserName());
        Date date = new Date();

        CribSearchHistory cribSearchHistory = new CribSearchHistory();
        cribSearchHistory.setIdentificationType(cribSearchHistoryDTO.getIdentificationType());
        cribSearchHistory.setIdentificationNo(cribSearchHistoryDTO.getIdentificationNumber());
        cribSearchHistory.setFullName(cribSearchHistoryDTO.getFullName());
        cribSearchHistory.setGender(cribSearchHistoryDTO.getGender());
        cribSearchHistory.setFacilityAmount(cribSearchHistoryDTO.getFacilityAmount());
        cribSearchHistory.setInquiryReason(cribSearchHistoryDTO.getInquiryReason());
        cribSearchHistory.setRemark(cribSearchHistoryDTO.getRemark());
        cribSearchHistory.setUploadedDivCode(cribSearchHistoryDTO.getUploadedDivCode());
        cribSearchHistory.setUploadedUserDisplayName(cribSearchHistoryDTO.getUploadedUserDisplayName());
        cribSearchHistory.setCreatedBy(credentialsDTO.getUserName());
        cribSearchHistory.setCreatedDate(date);

        if (cribSearchHistoryDTO.getDocStorageDTO() != null) {
            cribSearchHistoryDTO.getDocStorageDTO().setDescription("CUSTOMER: " + cribSearchHistoryDTO.getIdentificationNumber());
            DocStorageDTO docStorageDTO = this.storageService.saveUpdateDocument(cribSearchHistoryDTO.getDocStorageDTO(), credentialsDTO);
            cribSearchHistory.setDocStorage(this.docStorageDao.getOne(docStorageDTO.getDocStorageID()));
        } else {
            LOG.error("Customer CRIB Storage data null: {}", cribSearchHistoryDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FP_FP_CUSTOMER_DOCUMENT_NULL_SUPPORT_DOC);
        }

        cribSearchHistory = cribSearchHistoryDao.saveAndFlush(cribSearchHistory);

        LOG.info("END: Save Customer Crib Report Search :{} by :{}", cribSearchHistoryDTO, credentialsDTO.getUserName());

        return new CribSearchHistoryDTO(cribSearchHistory);
    }


}
