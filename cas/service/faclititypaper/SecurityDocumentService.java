package com.itechro.cas.service.faclititypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.customer.CustomerCovenantDao;
import com.itechro.cas.dao.facility.FacilityDao;
import com.itechro.cas.dao.facilitypaper.FPCustomFacilityInfoDao;
import com.itechro.cas.dao.facilitypaper.jdbc.FacilityPaperJdbcDao;
import com.itechro.cas.dao.facilitypaper.securityDocument.SecurityDocumentDetailDao;
import com.itechro.cas.dao.facilitypaper.securityDocument.SecurityDocumentElementDao;
import com.itechro.cas.dao.facilitypaper.securityDocument.SecurityDocumentTagDetailDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.casmaster.FacilityCustomInfoData;
import com.itechro.cas.model.domain.covenant.CustomerCovenant;
import com.itechro.cas.model.domain.facilitypaper.securityDocument.FPSecurityDocumentDetail;
import com.itechro.cas.model.domain.facilitypaper.securityDocument.FPSecurityDocumentTag;
import com.itechro.cas.model.domain.facilitypaper.securityDocument.SecurityDocumentElement;
import com.itechro.cas.model.dto.covenants.CusApplicableCovenantDTO;
import com.itechro.cas.model.dto.facility.FacilityDTO;
import com.itechro.cas.model.dto.facility.FacilitySecurityDTO;
import com.itechro.cas.model.dto.facilitypaper.CASCustomerDTO;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.dto.facilitypaper.PaymentDetailsObject;
import com.itechro.cas.model.dto.facilitypaper.creditcalculator.CalculatorResponse;
import com.itechro.cas.model.dto.facilitypaper.creditcalculator.StipulatedLossDetailDTO;
import com.itechro.cas.model.dto.facilitypaper.request.ApplicationCovenantDetailsDTO;
import com.itechro.cas.model.dto.facilitypaper.request.FinalDTO;
import com.itechro.cas.model.dto.facilitypaper.securityDocument.*;
import com.itechro.cas.model.dto.integration.response.BranchLoadResponseListDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.faclititypaper.creditcalculator.CalculatorService;
import com.itechro.cas.service.integration.IntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SecurityDocumentService implements EnvironmentAware {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityDocumentService.class);

    @Autowired
    IntegrationService integrationService;

    @Autowired
    private Environment environment;

    @Autowired
    private SecurityDocumentElementDao securityDocumentElementDao;

    @Autowired
    private SecurityDocumentDetailDao securityDocumentDetailDao;

    @Autowired
    private FacilityPaperService facilityPaperService;

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private FacilityPaperJdbcDao facilityPaperJdbcDao;

    @Autowired
    private FacilityDao facilityDao;

    @Autowired
    private FPCustomFacilityInfoDao fpCustomFacilityInfoDao;

    @Autowired
    private SecurityDocumentTagDetailDao securityDocumentTagDetailDao;

    @Autowired
    private CustomerCovenantDao customerCovenantDao;

    @Autowired
    @Qualifier("emailTemplateEngine")
    private TemplateEngine templateEngine;

    @Override
    public void setEnvironment(Environment environment) {

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<FCTemplateElement> getSecurityDocumentElements(SecurityDocumentContentDTO documentContentDTO) throws AppsException {
        List<FCTemplateElement> response = new ArrayList<>();

        try {

            List<FacilityDTO> facilities = facilityDao.findByFacilityPaperFacilityPaperID(documentContentDTO.getFacilityPaperId());

            if (facilities != null) {
                List<SDFacilityDTO> sdFacilities = facilities.stream()
                        .filter(data -> data.getIsNew().equals(AppsConstants.YesNo.Y) && data.getStatus().equals(AppsConstants.Status.ACT))
                        .map(SDFacilityDTO::new)
                        .sorted(Comparator.comparingInt(SDFacilityDTO::getDisplayOrder))
                        .collect(Collectors.toList());

                for (SDFacilityDTO facility : sdFacilities) {

                    FCTemplateElement templateElement = new FCTemplateElement();
                    templateElement.setCreditFacilityName(facility.getCreditFacilityName());
                    templateElement.setCreditFacilityTemplateID(facility.getCreditFacilityTemplateID());
                    templateElement.setFacilityTypeName(facility.getFacilityTypeName());

                    List<SecurityDocumentElement> documentElements = securityDocumentElementDao.findAllByCreditFacilityNameAndStatus(facility.getCreditFacilityName(), AppsConstants.Status.ACT.toString());
                    List<DocumentElementDTO> elements = documentElements.stream().map(DocumentElementDTO::new).collect(Collectors.toList());
                    List<SecurityDocumentDTO> securityDocuments = getFPSecurityDocuments(documentContentDTO.getFacilityPaperId(), facility.getFacilityID());

                    for (DocumentElementDTO element : elements) {
                        List<String> emptyEntries = getElementFacilityTags(facility.getFacilityID(), element);

                        if (!emptyEntries.isEmpty()) {
                            DocumentElementFacilityTag facilityTag = new DocumentElementFacilityTag();
                            facilityTag.setFacilityId(facility.getFacilityID());
                            facilityTag.setEmptyEntries(emptyEntries);

                            element.addFacilityTag(facilityTag);
                        }
                    }
                    templateElement.setDocumentElements(elements);

                    if (response.stream().anyMatch(data -> data.getCreditFacilityName().equals(facility.getCreditFacilityName()))) {
                        FCTemplateElement element = response.stream().filter(data -> data.getCreditFacilityName().equals(facility.getCreditFacilityName()))
                                .collect(Collectors.toList()).get(0);
                        element.addFacility(facility);

                        for (DocumentElementDTO documentElement : element.getDocumentElements()) {

                            List<DocumentElementFacilityTag> facilityTags = templateElement.getDocumentElements().stream()
                                    .filter(data -> data.getElementID().equals(documentElement.getElementID()))
                                    .map(data -> data.getFacilityTags() != null && !data.getFacilityTags().isEmpty() ? data.getFacilityTags() : new ArrayList<DocumentElementFacilityTag>())
                                    .findFirst()
                                    .orElse(new ArrayList<DocumentElementFacilityTag>());

                            if (!facilityTags.isEmpty()) {
                                documentElement.addFacilityTags(facilityTags);
                            }
                        }

                        if (securityDocuments != null && !securityDocuments.isEmpty()) {
                            element.addSecurityDocuments(securityDocuments);
                        }
                    } else {
                        if (securityDocuments != null && !securityDocuments.isEmpty()) {
                            templateElement.addSecurityDocuments(securityDocuments);
                        }
                        templateElement.addFacility(facility);
                        response.add(templateElement);
                    }
                }
            }

        } catch (Exception e) {
            LOG.info("Failed Document Elements Fetch:", e);
            throw new AppsException("Failed to fetch document elements.");
        }

        return response;
    }

    private List<SecurityDocumentDTO> getFPSecurityDocuments(Integer facilityPaperId, Integer facilityId) {
        List<SecurityDocumentDTO> response = new ArrayList<>();

        try {
            List<FPSecurityDocumentDetail> fpSecurityDocuments = securityDocumentDetailDao.getDocumentsByFacilityPaperAndFacilityId(facilityPaperId, facilityId);
            response = fpSecurityDocuments.stream().map(SecurityDocumentDTO :: new).collect(Collectors.toList());

        } catch (Exception e) {
            LOG.info("Failed FP Document Fetch:", e);
        }

        return response;
    }

    private List<String> getElementFacilityTags(Integer facilityId, DocumentElementDTO element) {

        List<String> emptyEntries = new ArrayList<>();

        //removed extra spaces
        String templateName = element.getDocumentFileName().replaceAll("\\s+", " ");
        String folderName = element.getCreditFacilityName().replaceAll("\\s+", "");

        try {
            if (!templateName.isEmpty()) {
                //get template
                String templatePath = this.environment.getProperty("apps.print.html.template.path") + File.separator
                        + "sd" + File.separator + "html" + File.separator + folderName + File.separator + templateName + ".html";

                // Read template content
                Path path = Paths.get(templatePath);
                if (Files.exists(path)) {
                    String templateContent = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                    if (!templateContent.isEmpty()) {
                        Document doc = Jsoup.parse(templateContent);
                        Elements requiredElements = doc.select("[aria-required='true']");

                        if (!requiredElements.isEmpty()) {

                            String[] codes = requiredElements
                                    .stream()
                                    .map(el -> el.attr("th:text"))
                                    .toArray(String[]::new);

                            List<FacilityCustomInfoData> facilityCustomInfoDataList = fpCustomFacilityInfoDao.getAllByFacilityId(facilityId);
                            if (codes.length > 0 && facilityCustomInfoDataList != null) {
                                for (FacilityCustomInfoData infoData : facilityCustomInfoDataList) {
                                    String searchValue = "'".concat(infoData.getCustomFacilityInfoCode()).concat("'");

                                    boolean contains = Arrays.stream(codes).anyMatch(code -> code.contains(searchValue));

                                    if (contains && (infoData.getCustomInfoData() == null || infoData.getCustomInfoData().isEmpty())) {
                                        emptyEntries.add(infoData.getCustomFacilityInfoCode());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.info("Get Element Facility Tags Error: ", e);
        }

        return emptyEntries;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public SecurityDocumentDTO saveOrUpdateSecurityDocument(SecurityDocumentReq securityDocumentReq, CredentialsDTO credentialsDTO) throws AppsException {

        SecurityDocumentDTO response;
        try {
            FPSecurityDocumentDetail securityDocumentDetail = securityDocumentDetailDao.findById(securityDocumentReq.getSecurityDocumentID())
                    .orElse(new FPSecurityDocumentDetail());

            if (securityDocumentDetail.getSecurityDocumentID() == null) {
                securityDocumentDetail = setSavedInfo(securityDocumentDetail, credentialsDTO, AppsConstants.SecurityDocumentStatus.DRAFT);
            } else {
                securityDocumentDetail = handleStatusUpdate(securityDocumentDetail, credentialsDTO, securityDocumentReq.getDocumentStatus());
            }

            if (securityDocumentReq.getDocumentStatus().equals(AppsConstants.SecurityDocumentStatus.DRAFT)){
                securityDocumentDetail.setFacilityPaperID(securityDocumentReq.getFacilityPaperID());
                securityDocumentDetail.setCreditFacilityTemplateID(securityDocumentReq.getCreditFacilityTemplateID());
                securityDocumentDetail.setCreditFacilityName(securityDocumentReq.getCreditFacilityName());
                securityDocumentDetail.setFacilityID(securityDocumentReq.getFacilityID());
                securityDocumentDetail.setDocumentName(securityDocumentReq.getDocumentName());

                SecurityDocumentElement element = securityDocumentElementDao.findById(securityDocumentReq.getElementID())
                        .orElseThrow(() -> new AppsException("Element not found"));
                securityDocumentDetail.setSecurityDocumentElement(element);
            }
            if ((securityDocumentReq.getDocumentStatus().equals(AppsConstants.SecurityDocumentStatus.DRAFT)
                    || securityDocumentReq.getDocumentStatus().equals(AppsConstants.SecurityDocumentStatus.PRINT)) && securityDocumentReq.getDocumentContent() != null &&
                    !securityDocumentReq.getDocumentContent().isEmpty()){
                securityDocumentDetail.setDocumentContent(securityDocumentReq.getDocumentContent());
            }
            securityDocumentDetail.setActionComment(securityDocumentReq.getActionComment());
            securityDocumentDetail = securityDocumentDetailDao.saveAndFlush(securityDocumentDetail);

            response = new SecurityDocumentDTO(securityDocumentDetail);

            if (securityDocumentDetail.getSecurityDocumentID() != null && securityDocumentReq.getDocumentStatus().equals(AppsConstants.SecurityDocumentStatus.DRAFT)){
                saveSecurityDocumentTags(securityDocumentReq, credentialsDTO);
            }

            List<FPSecurityDocumentTagDTO> updatedTags = getDocumentTags(securityDocumentReq.getFacilityPaperID());
            response.setDocumentTags(updatedTags);

        } catch (Exception e) {
            LOG.info("Failed Document Content Save:", e);
            throw new AppsException("Failed to save document details.");
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    private void saveSecurityDocumentTags(SecurityDocumentReq securityDocumentReq, CredentialsDTO credentialsDTO) throws AppsException {

        try {
            Date date = new Date();
            for (FPSecurityDocumentTagDTO tag : securityDocumentReq.getDocumentTags()) {
                FPSecurityDocumentTag prevTag = securityDocumentTagDetailDao.findByTagAndFacilityPaperId(tag.getTag(), tag.getFacilityPaperId());
                if ((prevTag == null || prevTag.getTagValue() == null || prevTag.getTagValue().isEmpty())
                        && tag.getTagValue() != null && !tag.getTagValue().isEmpty()) {
                    FPSecurityDocumentTag newTag = new FPSecurityDocumentTag();
                    newTag.setTag(tag.getTag());
                    newTag.setTagValue(tag.getTagValue());
                    newTag.setFieldType(tag.getFieldType());
                    newTag.setFacilityPaperId(tag.getFacilityPaperId());
                    newTag.setSavedDate(date);
                    newTag.setSavedBy(credentialsDTO.getUserName());

                    securityDocumentTagDetailDao.saveAndFlush(newTag);
                }
            }
        } catch (Exception e) {
            LOG.info("Failed Document Content Tag Save:", e);
            throw new AppsException("Failed to save document tag details.");
        }
    }

    private FPSecurityDocumentDetail setSavedInfo(FPSecurityDocumentDetail detail, CredentialsDTO credentials, AppsConstants.SecurityDocumentStatus status) {
        Date date = new Date();

        detail.setSavedBy(credentials.getUserName());
        detail.setSavedByDisplayName(credentials.getDisplayName());
        detail.setSavedByDiv(credentials.getDivCode());
        detail.setDocumentStatus(status.toString());
        detail.setSavedDate(date);

        return detail;
    }

    private FPSecurityDocumentDetail handleStatusUpdate(FPSecurityDocumentDetail detail, CredentialsDTO credentials, AppsConstants.SecurityDocumentStatus status) {
        Date date = new Date();

        switch (status) {
            case SUBMIT:
            case DRAFT:
            case DELETE:
                return setSavedInfo(detail, credentials, status);
            case RETURN:
            case APPROVE:
                detail.setDocumentStatus(status.toString());
                detail.setAuthBy(credentials.getUserName());
                detail.setAuthByDisplayName(credentials.getDisplayName());
                detail.setAuthByDiv(credentials.getDivCode());
                detail.setAuthDate(date);
                detail.setRecommendedReturnBy(null);
                detail.setRecommendedReturnDisplayName(null);
                detail.setRecommendedReturnDate(null);
                break;
            case PRINT:
                detail.setPrintedBy(credentials.getUserName());
                detail.setPrintedByDisplayName(credentials.getDisplayName());
                detail.setPrintedByDiv(credentials.getDivCode());
                detail.setPrintedDate(date);
                break;
            case RECOMMEND_RETURN:
                detail.setDocumentStatus(AppsConstants.SecurityDocumentStatus.RETURN.toString());
                detail.setRecommendedReturnBy(credentials.getUserName());
                detail.setRecommendedReturnDisplayName(credentials.getDisplayName());
                detail.setRecommendedReturnDate(date);
        }
        return detail;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public String getSecurityDocumentContent(SecurityDocumentContentDTO documentContentDTO, CredentialsDTO credentialsDTO) throws AppsException {

        String content = "";
        SDFacilityPaperPreviewDTO sdFacilityPaperPreviewDTO = new SDFacilityPaperPreviewDTO();

        //removed extra spaces
        String templateName = documentContentDTO.getDocumentName().replaceAll("\\s+", " ");
        try {
            if (!templateName.isEmpty()) {

                //get template
                String templatePath = this.environment.getProperty("apps.print.html.template.path") + File.separator
                        + "sd" + File.separator + "html" + File.separator + documentContentDTO.getFolderName() + File.separator + templateName + ".html";

                //get facility paper by id
                FacilityPaperDTO facilityPaperDTO = this.facilityPaperService.getFacilityPaperDTOByID(documentContentDTO.getFacilityPaperId(), credentialsDTO);
                List<CASCustomerDTO> customers = facilityPaperDTO.getCasCustomerDTOList();

                String accountNo = facilityPaperDTO.getBankAccountID() != null ? facilityPaperDTO.getBankAccountID() : "";
                sdFacilityPaperPreviewDTO.setAccountNo(accountNo);

                //Set document name
                String fpRef = facilityPaperDTO.getFpRefNumber() != null ? facilityPaperDTO.getFpRefNumber() : "";
                String documentName = fpRef.concat("_").concat("F"+documentContentDTO.getFacilityDisplayOrder()).
                                      concat("_").concat(documentContentDTO.getElementName());
                sdFacilityPaperPreviewDTO.setDocumentName(documentName);

                String branchName = "";
                if (!facilityPaperDTO.getBranchCode().isEmpty()) {
                    BranchLoadResponseListDTO responseListDTO = integrationService.getBranchList(credentialsDTO);
                    branchName = responseListDTO.getBranchResponse(facilityPaperDTO.getBranchCode()).getBranchName();
                    sdFacilityPaperPreviewDTO.setBranchName(branchName);
                }

                if (!customers.isEmpty()) {
                    customers.sort(Comparator.comparing(CASCustomerDTO::getCasCustomerID));
                    int i = 1;
                    for (CASCustomerDTO customer : customers) {

                        if (customer.getIsPrimary()) {
                            sdFacilityPaperPreviewDTO.setPrimaryCustomer(new SDCustomerDTO(customer, ""));
                        } else {
                            String customerKey = "J".concat(String.valueOf(i));
                            sdFacilityPaperPreviewDTO.addJoinCustomers(new SDCustomerDTO(customer, customerKey));
                            i++;
                        }
                    }
                }

                //get selected facility
                Optional<FacilityDTO> facilityOpt = facilityPaperDTO.getFacilityDTOList().stream()
                        .filter(data -> data.getFacilityID().equals(documentContentDTO.getFacilityId()))
                        .findFirst();

                if (facilityOpt.isPresent()) {
                    FacilityDTO facility = facilityOpt.get();

                    sdFacilityPaperPreviewDTO.setSdFacility(new SDFacilityDTO(facility));

                    List<CusApplicableCovenantDTO> cusApplicableCovenants = this.facilityPaperService.getCusApplicableCovenantList(facilityPaperDTO.getFpRefNumber(), facility.getFacilityID(), credentialsDTO);
                    List<String> covenants = cusApplicableCovenants.stream().map(CusApplicableCovenantDTO::getCovenant_Description).collect(Collectors.toList());
                    sdFacilityPaperPreviewDTO.setCusApplicableCovenants(covenants);

                    sdFacilityPaperPreviewDTO.setFacilityCustomInfoDataList(facility.getFacilityCustomInfoDataDTOList());

                    CalculatorResponse calculatorResponse = this.calculatorService.getCreditCalculatorData(facility, credentialsDTO);

                    if (calculatorResponse != null) {
                        sdFacilityPaperPreviewDTO.setSystemOutputResponseData(calculatorResponse.getSystemOutputResponseData());

                        List<StipulatedLossDetailDTO> stipulatedLossDetails = this.calculatorService.getStipulatedLossDetails(calculatorResponse.getStipulatedLossValueResponseData(), sdFacilityPaperPreviewDTO);
                        sdFacilityPaperPreviewDTO.setStipulatedLossDetails(stipulatedLossDetails);
                    }

                    PaymentDetailsObject paymentDetailsObject = sdFacilityPaperPreviewDTO.getPaymentDetailsObject();
                    List<String> securities = new ArrayList<>();
                    if (paymentDetailsObject.getSecurityStatement1() != null){
                        securities.add(paymentDetailsObject.getSecurityStatement1());
                    }
                    if (facility.getFacilitySecurityDTOList() != null && !facility.getFacilitySecurityDTOList().isEmpty()) {
                        List<String> fSecurities = facility.getFacilitySecurityDTOList().stream().limit(3).map(FacilitySecurityDTO::getSecurityDetail).collect(Collectors.toList());
                        fSecurities.remove(0);
                        securities.addAll(fSecurities);
                    }

                    sdFacilityPaperPreviewDTO.setFacilitySecurities(securities);

                    for (int i = 0; i <= 2; i++) {
                        if (sdFacilityPaperPreviewDTO.getFacilitySecurities().size() < 3) {
                            securities.add("");
                        }
                    }
                }

                //Get document tags by facility paper Id
                List<FPSecurityDocumentTag> tags = securityDocumentTagDetailDao.findAllByFacilityPaperId(facilityPaperDTO.getFacilityPaperID());
                if (tags != null && !tags.isEmpty()){
                    sdFacilityPaperPreviewDTO.setFpSecurityDocumentTags(tags);
                }

                Context context = new Context();
                Map<String, Object> params = new HashMap<>();
                params.put("fpDTO", sdFacilityPaperPreviewDTO);
                context.setVariables(params);
                content = this.templateEngine.process(templatePath, context);
            }

        } catch (Exception ex) {
            content = "";
            LOG.info("ERROR Security Document Content: ", ex);
            String err_message = "An error occurred while fetching template.";
            if (ex.getCause() != null && !ex.getCause().getMessage().isEmpty()) {
                if (ex.getCause().getMessage().contains("The system cannot find the file specified")) {
                    err_message = "There are no template found for ".concat(templateName.replaceAll("-", " "));
                }
            }

            throw new AppsException(err_message);
        }

        return content;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<SecurityDocumentDTO> getSecurityDocumentHistory(Integer securityDocumentID) throws AppsException {

        List<SecurityDocumentDTO> mainHisotryList = new ArrayList<>();
        List<SecurityDocumentDTO> historyList = facilityPaperJdbcDao.getSecurityDocumentHistoryData(securityDocumentID);
        for (SecurityDocumentDTO fpSecurityDocumentDTO : historyList) {
            mainHisotryList.add(fpSecurityDocumentDTO);
        }
        return mainHisotryList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<CusApplicableCovenantDTO> getCovenantList(String fpRefNumber, Integer facilityId, CredentialsDTO credentialsDTO) throws AppsException {

        List<CusApplicableCovenantDTO> response = new ArrayList<>();
        List<CustomerCovenant> customerCovenants = customerCovenantDao.findByFacilityPaperFpRefNumber(fpRefNumber);

        //map facility covenants
        List<FinalDTO> allFacilityCovenants = facilityPaperService.getFacilityCovenantList(fpRefNumber, credentialsDTO);
        if (allFacilityCovenants != null) {
            for (FinalDTO finalDTO : allFacilityCovenants) {
                for (ApplicationCovenantDetailsDTO applicationCovenantDetailsDTO : finalDTO.getCovValue()) {

                    boolean isFacilityExist = applicationCovenantDetailsDTO.getApplicationCovenantFacilityDTOS().
                            stream().anyMatch(data -> Objects.equals(data.getFacilityID(), facilityId));

                    if (isFacilityExist && applicationCovenantDetailsDTO.getStatus().equals(AppsConstants.CovenantStatus.Active)) {

                        CusApplicableCovenantDTO cusApplicableCovenant = new CusApplicableCovenantDTO();
                        cusApplicableCovenant.setCovenant_Code(applicationCovenantDetailsDTO.getCovenant_Code());
                        cusApplicableCovenant.setCovenant_Description(applicationCovenantDetailsDTO.getCovenant_Description());
                        cusApplicableCovenant.setCovenant_Frequency(applicationCovenantDetailsDTO.getCovenant_Frequency());
                        cusApplicableCovenant.setCovenant_Due_Date(applicationCovenantDetailsDTO.getCovenant_Due_Date());
                        cusApplicableCovenant.setApplicableType(applicationCovenantDetailsDTO.getApplicableType());
                        cusApplicableCovenant.setDisbursementType(applicationCovenantDetailsDTO.getDisbursementType());
                        cusApplicableCovenant.setNoFrequencyDueDate(applicationCovenantDetailsDTO.getNoFrequencyDueDate());

                        response.add(cusApplicableCovenant);
                    }
                }
            }
        }

        //map customer covenants
        if (customerCovenants != null) {
            List<CustomerCovenant> filteredCustomerCovList = customerCovenants.stream().filter(data -> data.getStatus().equals(AppsConstants.CovenantStatus.Active))
                    .collect(Collectors.toList());

            for (CustomerCovenant customerCovenant : filteredCustomerCovList) {
                CusApplicableCovenantDTO cusApplicableCovenant = new CusApplicableCovenantDTO();
                cusApplicableCovenant.setCovenant_Code(customerCovenant.getCovenant_Code());
                cusApplicableCovenant.setCovenant_Description(customerCovenant.getCovenant_Description());
                cusApplicableCovenant.setCovenant_Frequency(customerCovenant.getCovenant_Frequency());
                cusApplicableCovenant.setCovenant_Due_Date(customerCovenant.getCovenant_Due_Date());
                cusApplicableCovenant.setApplicableType(customerCovenant.getApplicableType());
                cusApplicableCovenant.setDisbursementType(customerCovenant.getDisbursementType());
                cusApplicableCovenant.setNoFrequencyDueDate(customerCovenant.getNoFrequencyDueDate());

                response.add(cusApplicableCovenant);
            }
        }
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public void updateDocumentStatusByFacilityPaper(Integer facilityPaperId, CredentialsDTO credentials) throws AppsException{

        try {
            Date date = new Date();

            List<FPSecurityDocumentDetail> securityDocuments = securityDocumentDetailDao.getDocumentStatusByFacilityPaper(facilityPaperId);
            if (securityDocuments != null && !securityDocuments.isEmpty()) {
                for (FPSecurityDocumentDetail detail : securityDocuments){
                    detail.setDocumentStatus(AppsConstants.SecurityDocumentStatus.SUBMIT.toString());
                    detail.setSavedBy(credentials.getUserName());
                    detail.setSavedByDisplayName(credentials.getDisplayName());
                    detail.setSavedByDiv(credentials.getDivCode());
                    detail.setSavedDate(date);

                    securityDocumentDetailDao.save(detail);
                }
            }
        } catch (Exception e){
            LOG.info("Failed To Update Document Status:", e);
            throw new AppsException("Failed to update document status.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<FPSecurityDocumentTagDTO> getDocumentTags(Integer facilityPaperId) throws AppsException {
        List<FPSecurityDocumentTagDTO> response = new ArrayList<>();
        try {
            List<FPSecurityDocumentTag> tags = securityDocumentTagDetailDao.findAllByFacilityPaperId(facilityPaperId);
            if (tags != null && !tags.isEmpty()){
                response = tags.stream().map(FPSecurityDocumentTagDTO::new).collect(Collectors.toList());
            }

        } catch (Exception e){
            LOG.info("Failed To Fetch Document Tags:", e);
            throw new AppsException("Failed to fetch document tags.");
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public void updateDocumentStatusByFacility(Integer facilityId, CredentialsDTO credentials) throws AppsException{

        try {
            Date date = new Date();

            List<FPSecurityDocumentDetail> securityDocuments = securityDocumentDetailDao.getDocumentStatusByFacility(facilityId);
            if (securityDocuments != null && !securityDocuments.isEmpty()) {
                for (FPSecurityDocumentDetail detail : securityDocuments){
                    detail.setDocumentStatus(AppsConstants.SecurityDocumentStatus.DELETE.toString());
                    detail.setSavedBy(credentials.getUserName());
                    detail.setSavedByDisplayName(credentials.getDisplayName());
                    detail.setSavedByDiv(credentials.getDivCode());
                    detail.setSavedDate(date);

                    securityDocumentDetailDao.save(detail);
                }
            }
        } catch (Exception e){
            LOG.info("Failed To Update Document Status:", e);
            throw new AppsException("Failed to update document status.");
        }
    }
}
