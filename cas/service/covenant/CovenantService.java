package com.itechro.cas.service.covenant;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.covenant.ExistingFacilityCovenantsDao;
import com.itechro.cas.dao.covenant.NoneComplianceCovenantDao;
import com.itechro.cas.dao.customer.CustomerCovenantDao;
import com.itechro.cas.dao.customer.CustomerDao;
import com.itechro.cas.dao.facility.FacilityDao;
import com.itechro.cas.dao.facilitypaper.ApplicationCovenantDao;
import com.itechro.cas.dao.facilitypaper.CasCustomerDao;
import com.itechro.cas.dao.facilitypaper.FacilityCovenantFacilitiesDao;
import com.itechro.cas.dao.facilitypaper.FacilityPaperDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.covenant.ExistingFacilityCovenants;
import com.itechro.cas.model.domain.covenant.NoneComplianceCovenant;
import com.itechro.cas.model.domain.customer.Customer;
import com.itechro.cas.model.domain.covenant.CustomerCovenant;
import com.itechro.cas.model.domain.covenant.ApplicationLevelCovenant;
import com.itechro.cas.model.domain.facilitypaper.CASCustomer;
import com.itechro.cas.model.domain.facilitypaper.facility.Facility;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityCovenantFacilities;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.dto.covenants.*;
import com.itechro.cas.model.dto.customer.CustomerCovenantRQ;
import com.itechro.cas.model.dto.covenants.CustomerCovenantSaveChildDTO;
import com.itechro.cas.model.dto.covenants.CustomerCovenantSaveDTO;
import com.itechro.cas.model.dto.customer.request.CustomerCovenantDetailsReqDTO;
import com.itechro.cas.model.dto.customer.request.CustomerCovenantUpdateDTO;
import com.itechro.cas.model.dto.customer.response.CovenantDetailResDTO;
import com.itechro.cas.model.dto.customer.response.CustomerCovenantSaveResponseDTO;
import com.itechro.cas.model.dto.facility.FacilityDTO;
import com.itechro.cas.model.dto.facility.FacilityListForCovenantDTO;
import com.itechro.cas.model.dto.facilitypaper.request.ApplicationCovenantDetailsDTO;
import com.itechro.cas.model.dto.facilitypaper.request.ApplicationCovenantFacilityDTO;
import com.itechro.cas.model.dto.covenants.ApplicationCovenantReqDTO;
import com.itechro.cas.model.dto.facilitypaper.request.FinalDTO;
import com.itechro.cas.model.dto.facilitypaper.response.ApplicationCovenantPostDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.security.SecurityService;
import com.itechro.cas.service.facility.FacilityService;
import com.itechro.cas.service.integration.IntegrationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class CovenantService {

    private final IntegrationService integrationService;

    private final CustomerCovenantDao customerCovenantDao;

    private final SecurityService securityService;

    private final ApplicationCovenantDao applicationCovenantDao;

    private final FacilityService facilityService;

    private final FacilityCovenantFacilitiesDao facilityCovenantFacilitiesDao;

    private final CustomerDao customerDao;

    private final FacilityPaperDao facilityPaperDao;

    private final FacilityDao facilityDao;

    private final ModelMapper modelMapper;

    private final NoneComplianceCovenantDao noneComplianceCovenantDao;

    private final ExistingFacilityCovenantsDao existingFacilityCovenantsDao;

    private final CasCustomerDao casCustomerDao;

    @Value("${apps.covenant.request.uuid}")
    private String covenantRequestUuid;

    public CovenantService(IntegrationService integrationService, CustomerCovenantDao customerCovenantDao, SecurityService securityService, ApplicationCovenantDao applicationCovenantDao, FacilityService facilityService, FacilityCovenantFacilitiesDao facilityCovenantFacilitiesDao, CustomerDao customerDao, FacilityPaperDao facilityPaperDao, FacilityDao facilityDao, ModelMapper modelMapper, NoneComplianceCovenantDao noneComplianceCovenantDao, ExistingFacilityCovenantsDao existingFacilityCovenantsDao, CasCustomerDao casCustomerDao) {
        this.integrationService = integrationService;
        this.customerCovenantDao = customerCovenantDao;
        this.securityService = securityService;
        this.applicationCovenantDao = applicationCovenantDao;
        this.facilityService = facilityService;
        this.facilityCovenantFacilitiesDao = facilityCovenantFacilitiesDao;
        this.customerDao = customerDao;
        this.facilityPaperDao = facilityPaperDao;
        this.facilityDao = facilityDao;
        this.modelMapper = modelMapper;
        this.noneComplianceCovenantDao = noneComplianceCovenantDao;
        this.existingFacilityCovenantsDao = existingFacilityCovenantsDao;
        this.casCustomerDao = casCustomerDao;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CovenantDetailsFinacleDTO getCovenantDetailsFromFinacle(LoadCovenantDataDTO loadCovenantDataDTO, CredentialsDTO credentialsDTO) throws Exception{

        String customerFinacleId = customerDao.findCustomerFinancialId(loadCovenantDataDTO.getFacilityPaperId());
        loadCovenantDataDTO.setCustId(customerFinacleId);

        CovenantDetailsFinacleDTO covenantDetailsFinacleDTO = integrationService.getCovenantDetailsFromFinacle(loadCovenantDataDTO, credentialsDTO);

        if (covenantDetailsFinacleDTO != null && covenantDetailsFinacleDTO.getCovenant() != null) {
            List<CovenantDataDTO> covenantList = covenantDetailsFinacleDTO.getCovenant();

            covenantList.sort((c1, a1) -> {
                boolean isc_other = c1.getCovenantInq().stream().anyMatch(cov -> cov.getCovCod().endsWith("_OTH"));
                boolean isa_other = a1.getCovenantInq().stream().anyMatch(cov -> cov.getCovCod().endsWith("_OTH"));

                if (isc_other && !isa_other) return 1;
                if (!isc_other && isa_other) return -1;
                return 0;
            });

            covenantDetailsFinacleDTO.setCovenant(covenantList);

            // Get matching comments
            Map<String, NoneComplianceCovenantDTO> matchingComments = getMatchingCommentsMap(covenantDetailsFinacleDTO, loadCovenantDataDTO.getFacilityPaperId());

            // Add comments to CovenantInquiryDTO
            for (CovenantDataDTO covenantData : covenantList) {
                for (CovenantInquiryDTO inquiry : covenantData.getCovenantInq()) {
                    if (matchingComments.containsKey(inquiry.getSrlNum())) {
                        inquiry.setNonComplianceCovenantDTO(matchingComments.get(inquiry.getSrlNum()));
                    }
                }
            }

            // Handle the -999 comment separately
            if (matchingComments.containsKey("-999")) {
                NoneComplianceCovenantDTO specialComment = matchingComments.get("-999");
                if(specialComment.getFacilityPaperId().equals(loadCovenantDataDTO.getFacilityPaperId())){
                    covenantDetailsFinacleDTO.setSpecialComment(specialComment);
                }
            }

        }

        return covenantDetailsFinacleDTO;

    }

    public List<String> getSrlNumList(CovenantDetailsFinacleDTO covenantDetailsFinacleDTO) {
        log.info("Extracting SrlNum list from CovenantDetailsFinacleDTO");
        if (covenantDetailsFinacleDTO == null || covenantDetailsFinacleDTO.getCovenant() == null) {
            return Collections.emptyList();
        }

        List<String> srlNumList = covenantDetailsFinacleDTO.getCovenant().stream()
                .flatMap(covenantData -> covenantData.getCovenantInq().stream())
                .map(CovenantInquiryDTO::getSrlNum)
                .collect(Collectors.toList());

        log.info("SrlNum List: {}", srlNumList);
        return srlNumList;
    }

    private Map<String, NoneComplianceCovenantDTO> getMatchingCommentsMap(CovenantDetailsFinacleDTO covenantDetailsFinacleDTO, Integer facilityPaperId) throws AppsException {
        log.info("Getting matching comments for facilityPaperId: {}", facilityPaperId);

        // Get the srlNumList and validate
        List<String> srlNumList = getSrlNumList(covenantDetailsFinacleDTO);
        if (srlNumList == null || srlNumList.isEmpty()) {
            log.warn("SrlNum list is null or empty");
            return Collections.emptyMap();
        }

        // Get the covenant comments and validate
        List<NoneComplianceCovenantDTO> covenantComments = getCovenantCommentList(facilityPaperId);
        if (covenantComments == null || covenantComments.isEmpty()) {
            log.warn("Covenant comments list is null or empty");
            return Collections.emptyMap();
        }

        // Create a map of serialNumber to comment
        return covenantComments.stream()
                .filter(comment ->
                        (comment.getSerialNumber() != null && srlNumList.contains(comment.getSerialNumber().toString()))
                                || (comment.getSerialNumber() != null && comment.getSerialNumber() == -999)
                )
                .collect(Collectors.toMap(
                        comment -> comment.getSerialNumber().toString(),
                        comment -> comment
                ));
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CovenantListDTO getCovenantList(CustomerCovenantRQ customerCovenantRQ, CredentialsDTO credentialsDTO) throws AppsException {

        CovenantListDTO responseDTO = integrationService.getCovenantList(customerCovenantRQ, credentialsDTO);

        return responseDTO;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<CustomerCovenantResponseDTO> getCustomerCovenantList(String fpRefNumber, CredentialsDTO credentialsDTO) throws AppsException {
        List<CustomerCovenant> customerCovenants = customerCovenantDao.findByFacilityPaperFpRefNumber(fpRefNumber);

        List<CustomerCovenantResponseDTO> customerCovenantResponseDTOList = new ArrayList<>();

//        customerCovenants = customerCovenants.stream()
//                .filter(cov -> cov.getDisplayOrder() != null)
//                .sorted(Comparator.comparing(CustomerCovenant::getDisplayOrder))
//                .collect(Collectors.toList());

        // Separate covenants with non-null displayOrder
        List<CustomerCovenant> withDisplayOrder = customerCovenants.stream()
                .filter(cov -> cov.getDisplayOrder() != null)
                .sorted(Comparator.comparing(CustomerCovenant::getDisplayOrder))
                .collect(Collectors.toList());

        // Optionally, handle covenants with null displayOrder
        List<CustomerCovenant> withoutDisplayOrder = customerCovenants.stream()
                .filter(cov -> cov.getDisplayOrder() == null)
                .collect(Collectors.toList());

        // Combine both lists (non-null first, then nulls)
        List<CustomerCovenant> sortedCovenants = new ArrayList<>();
        sortedCovenants.addAll(withDisplayOrder);
        sortedCovenants.addAll(withoutDisplayOrder);

        for(CustomerCovenant customerCovenant: customerCovenants){
            CustomerCovenantResponseDTO customerCovenantResponseDTO = new CustomerCovenantResponseDTO();
            customerCovenantResponseDTO.setCustomerCovenantId(customerCovenant.getCustomerCovenantId());
            customerCovenantResponseDTO.setRequestUUID(customerCovenant.getRequestUUID());
            customerCovenantResponseDTO.setCustomerFinancialID(customerCovenant.getCustomerFinancialID());
            customerCovenantResponseDTO.setFpRefNumber(customerCovenant.getFacilityPaper().getFacilityPaperNumber());
            customerCovenantResponseDTO.setDisbursementType(customerCovenant.getDisbursementType());

            customerCovenantResponseDTO.setCovenant_Code(customerCovenant.getCovenant_Code());
            customerCovenantResponseDTO.setCovenant_Description(customerCovenant.getCovenant_Description());
            customerCovenantResponseDTO.setCovenant_Frequency(customerCovenant.getCovenant_Frequency());
            customerCovenantResponseDTO.setCovenant_Due_Date(customerCovenant.getCovenant_Due_Date());
            customerCovenantResponseDTO.setCreatedBy(customerCovenant.getCreatedBy());
            customerCovenantResponseDTO.setCreatedUserDisplayName(customerCovenant.getCreatedUserDisplayName());
            customerCovenantResponseDTO.setCreatedDate(customerCovenant.getCreatedDate());
            customerCovenantResponseDTO.setStatus(customerCovenant.getStatus());
            customerCovenantResponseDTO.setWorkClass(String.valueOf(securityService.getUserUPMDetails(customerCovenant.getCreatedBy()).getApplicationSecurityClass()));
            customerCovenantResponseDTO.setNoFrequencyDueDate(customerCovenant.getNoFrequencyDueDate());
            customerCovenantResponseDTO.setIsExists(customerCovenant.getIsExists());
            customerCovenantResponseDTO.setComplianceStatus(customerCovenant.getComplianceStatus());
            customerCovenantResponseDTO.setApplicableType(customerCovenant.getApplicableType());

            customerCovenantResponseDTOList.add(customerCovenantResponseDTO);
        }

        return customerCovenantResponseDTOList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<FinalDTO> getFacilityCovenantList(String fpRefNumber, CredentialsDTO credentialsDTO) throws AppsException {

        Map<String, List<ApplicationCovenantDetailsDTO>> map = new HashMap<String, List<ApplicationCovenantDetailsDTO>>();
        List<ApplicationLevelCovenant> applicationLevelCovenants = applicationCovenantDao.findByFacilityPaperFpRefNumber(fpRefNumber);

        for (ApplicationLevelCovenant applicationLevelCovenant : applicationLevelCovenants) {
            List<FacilityListForCovenantDTO> facilityListDTOS = facilityService.getFacilities(applicationLevelCovenant.getFacilityPaper().getFacilityPaperID(), credentialsDTO);

            if (!facilityListDTOS.isEmpty()) {

                ApplicationCovenantDetailsDTO applicationCovenantDetailsDTO = new ApplicationCovenantDetailsDTO();
                applicationCovenantDetailsDTO.setApplicationCovenantId(applicationLevelCovenant.getApplicationCovenantId());
                applicationCovenantDetailsDTO.setCustId(applicationLevelCovenant.getCustomerFinacleID());
                applicationCovenantDetailsDTO.setCasReference(applicationLevelCovenant.getFacilityPaper().getFpRefNumber());
                applicationCovenantDetailsDTO.setCreatedDate(applicationLevelCovenant.getCreatedDate());
                applicationCovenantDetailsDTO.setCreatedBy(applicationLevelCovenant.getCreatedBy());
                applicationCovenantDetailsDTO.setCreatedUserDisplayName(applicationLevelCovenant.getCreatedUserDisplayName());
                applicationCovenantDetailsDTO.setStatus(applicationLevelCovenant.getStatus());
                //applicationCovenantDetailsDTO.setWorkClass(String.valueOf(securityService.getUserUPMDetails(applicationLevelCovenant.getCreatedBy()).getApplicationSecurityClass()));

                applicationCovenantDetailsDTO.setCovenant_Code(applicationLevelCovenant.getCovenant_Code());
                applicationCovenantDetailsDTO.setCovenant_Description(applicationLevelCovenant.getCovenant_Description());
                applicationCovenantDetailsDTO.setCovenant_Frequency(applicationLevelCovenant.getCovenant_Frequency());
                applicationCovenantDetailsDTO.setCovenant_Due_Date(applicationLevelCovenant.getCovenant_Due_Date());
                applicationCovenantDetailsDTO.setDisbursementType(applicationLevelCovenant.getDisbursementType());
                applicationCovenantDetailsDTO.setNoFrequencyDueDate(applicationLevelCovenant.getNoFrequencyDueDate());
                applicationCovenantDetailsDTO.setIsExists(applicationLevelCovenant.getIsExists());
                applicationCovenantDetailsDTO.setComplianceStatus(applicationLevelCovenant.getComplianceStatus());
                applicationCovenantDetailsDTO.setAccountId(applicationLevelCovenant.getAccountId());
                applicationCovenantDetailsDTO.setApplicableType(applicationLevelCovenant.getApplicableType());

                List<FacilityCovenantFacilities> facilityCovenantFacilities = facilityCovenantFacilitiesDao.findByApplicationLevelCovenantApplicationCovenantId(applicationLevelCovenant.getApplicationCovenantId());
                List<ApplicationCovenantFacilityDTO> facilityDTOS = new ArrayList<>();

                Comparator<ApplicationCovenantFacilityDTO> displayOrderComparator = Comparator.comparingInt(facilityDTO -> {
                    FacilityDTO facility = facilityService.getFacilityByID(facilityDTO.getFacilityID());
                    return facility.getDisplayOrder();
                });

                for (FacilityCovenantFacilities facilities : facilityCovenantFacilities) {

                    FacilityDTO facility = facilityService.getFacilityByID(facilities.getFacility().getFacilityID());

                    if(facility.getStatus().equals(AppsConstants.Status.ACT)){
                        ApplicationCovenantFacilityDTO applicationCovenantFacilityDTO = new ApplicationCovenantFacilityDTO();
                        applicationCovenantFacilityDTO.setFacilityID(facilities.getFacility().getFacilityID());
                        applicationCovenantFacilityDTO.setCreditFacilityName(facilities.getCreditFacilityName());
                        applicationCovenantFacilityDTO.setCreditFacilityTemplateID(facilities.getCreditFacilityTemplateID());
                        applicationCovenantFacilityDTO.setFacilityRefCode(facilities.getFacilityRefCode());
                        //BigDecimal facilityAmountInMillions = facility.getFacilityAmount().divide(BigDecimal.valueOf(1_000_000), 3, RoundingMode.HALF_UP);
                        applicationCovenantFacilityDTO.setFacilityAmount(facility.getFacilityAmount());
                        applicationCovenantFacilityDTO.setDisplayOrder(facility.getDisplayOrder());
                        applicationCovenantFacilityDTO.setFacilityCurrency(facilities.getFacilityCurrency());
                        facilityDTOS.add(applicationCovenantFacilityDTO);
                    }

                }

                facilityDTOS.sort(Comparator.comparingInt(ApplicationCovenantFacilityDTO::getDisplayOrder));

                facilityDTOS.sort(displayOrderComparator);
                applicationCovenantDetailsDTO.setApplicationCovenantFacilityDTOS(facilityDTOS);

                applicationCovenantDetailsDTO.getApplicationCovenantFacilityDTOS().sort(Comparator.comparingInt(ApplicationCovenantFacilityDTO::getDisplayOrder));

                String covenantKey = applicationCovenantDetailsDTO.getCovanentKey();
                if (map.containsKey(covenantKey)) {

                    map.get(covenantKey).add(applicationCovenantDetailsDTO);
                } else {

                    List<ApplicationCovenantDetailsDTO> covenantDetailsList = new ArrayList<>();
                    covenantDetailsList.add(applicationCovenantDetailsDTO);
                    map.put(covenantKey, covenantDetailsList);
                }
            }

        }

        List<FinalDTO> finalDTOS = new ArrayList<>();
        for (Map.Entry<String, List<ApplicationCovenantDetailsDTO>> entry : map.entrySet()) {
            FinalDTO finalDTO = new FinalDTO();
            finalDTO.setCovanentKey(entry.getKey());
            finalDTO.setCovValue(entry.getValue());
            finalDTOS.add(finalDTO);
        }

        return finalDTOS;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<CustomerCovenantSaveResponse> saveCustomerCovenant(CustomerCovenantSaveDTO customerCovenantReqDTO, CredentialsDTO credentialsDTO) throws AppsException {
        List<CustomerCovenant> customerCovenantList = new ArrayList<>();
        Date date = new Date();

        List<CustomerCovenant> customerCovenants = customerCovenantDao.findByFacilityPaperFpRefNumber(customerCovenantReqDTO.getCasReference());

        // Determine the starting display order
        int displayOrder = 1;
        for (CustomerCovenant covenant : customerCovenants) {
            if (covenant.getDisplayOrder() == null) {
                covenant.setDisplayOrder(displayOrder++);
            } else {
                displayOrder = Math.max(displayOrder, covenant.getDisplayOrder() + 1);
            }
        }

        for (CustomerCovenantSaveChildDTO customerCovenantDetailsDTO : customerCovenantReqDTO.getCovenantDetails()) {

            CustomerCovenant customerCovenant = new CustomerCovenant();
            customerCovenant.setRequestUUID(this.covenantRequestUuid);
            customerCovenant.setCreatedBy(credentialsDTO.getUserName());
            customerCovenant.setCreatedUserDisplayName(customerCovenantReqDTO.getCreatedUserDisplayName());
            customerCovenant.setCreatedDate(date);
            customerCovenant.setCustomerFinancialID(customerCovenantReqDTO.getCustId());
            customerCovenant.setDisbursementType(customerCovenantReqDTO.getDisbursementType());
            customerCovenant.setApplicableType(customerCovenantDetailsDTO.getApplicableType());

            //Customer customer = customerDao.findCustomerByFinancialID(customerCovenantReqDTO.getCustId());
            customerCovenant.setCustomerFinancialID(customerCovenantReqDTO.getCustId());

            FacilityPaper facilityPaper = facilityPaperDao.findByFpRefNumber(customerCovenantReqDTO.getCasReference());
            customerCovenant.setFacilityPaper(facilityPaper);

            customerCovenant.setCovenant_Code(customerCovenantDetailsDTO.getCovenant_Code());
            customerCovenant.setCovenant_Description(customerCovenantDetailsDTO.getCovenant_Description());
            customerCovenant.setCovenant_Frequency(customerCovenantDetailsDTO.getCovenant_Frequency());
            customerCovenant.setDisbursementType(customerCovenantDetailsDTO.getDisbursementType());
            customerCovenant.setCovenant_Due_Date(customerCovenantDetailsDTO.getCovenant_Due_Date());
            customerCovenant.setNoFrequencyDueDate(customerCovenantDetailsDTO.getNoFrequencyDueDate());

            customerCovenant.setStatus(AppsConstants.CovenantStatus.Active);
            customerCovenant.setIsExists(AppsConstants.YesNo.N);
            customerCovenant.setDisplayOrder(displayOrder++);

            customerCovenantList.add(customerCovenant);

        }
        log.info("Covenant save", customerCovenantList);

        customerCovenantDao.saveAll(customerCovenantList);

        List<CustomerCovenantSaveResponse> covenantSaveResponseList = new ArrayList<>();
        for (CustomerCovenant customerCovenant : customerCovenantList) {
            CustomerCovenantSaveResponse customerCovenantSaveResponse = new CustomerCovenantSaveResponse(customerCovenant);
            covenantSaveResponseList.add(customerCovenantSaveResponse);
        }

        return covenantSaveResponseList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ApplicationCovenantPostDTO saveOrUpdateApplicationCovenant(ApplicationCovenantReqDTO applicationCovenantReqDTO, CredentialsDTO credentialsDTO) throws AppsException {
        Date date = new Date();
        List<ApplicationLevelCovenant> savedCovenants = new ArrayList<>();

        // Retrieve existing covenants for the given casReference
        List<ApplicationLevelCovenant> applicationLevelCovenants = applicationCovenantDao.findByFacilityPaperFpRefNumber(applicationCovenantReqDTO.getCasReference());

        // Handle null displayOrder for existing covenants
        int displayOrder = 1;
        for (ApplicationLevelCovenant covenant : applicationLevelCovenants) {
            if (covenant.getDisplayOrder() == null) {
                covenant.setDisplayOrder(displayOrder++);
            } else {
                displayOrder = Math.max(displayOrder, covenant.getDisplayOrder() + 1);
            }
        }

        for (ApplicationCovenantDetailsDTO applicationCovenantDetailsDTO : applicationCovenantReqDTO.getCovenantDetails()) {
            ApplicationLevelCovenant applicationLevelCovenant;

            if (applicationCovenantReqDTO.getApplicationCovenantId() != null) {
                applicationLevelCovenant = applicationCovenantDao.findById(applicationCovenantReqDTO.getApplicationCovenantId()).orElseThrow(
                        () -> new AppsException("Covenant with ID " + applicationCovenantReqDTO.getApplicationCovenantId() + " not found")
                );
                facilityCovenantFacilitiesDao.deleteByApplicationLevelCovenantApplicationCovenantId(applicationCovenantReqDTO.getApplicationCovenantId());
                applicationLevelCovenant.setCreatedDate(date);
                applicationLevelCovenant.setCreatedBy(credentialsDTO.getUserName());
                applicationLevelCovenant.setCreatedUserDisplayName(applicationCovenantReqDTO.getCreatedUserDisplayName());
            } else {
                applicationLevelCovenant = new ApplicationLevelCovenant();
                applicationLevelCovenant.setCreatedDate(date);
                applicationLevelCovenant.setCreatedBy(credentialsDTO.getUserName());
                applicationLevelCovenant.setCreatedUserDisplayName(applicationCovenantReqDTO.getCreatedUserDisplayName());
                applicationLevelCovenant.setStatus(AppsConstants.CovenantStatus.Active);
            }

            applicationLevelCovenant.setRequestUUID(covenantRequestUuid);

            Customer customer = customerDao.findCustomerByFinancialID(applicationCovenantReqDTO.getCustId());
            if (customer == null) {
                Customer newCustomer = new Customer();
                newCustomer.setCustomerFinancialID(applicationCovenantReqDTO.getCustId());
                customer = customerDao.save(newCustomer);
            }
            applicationLevelCovenant.setCustomerFinacleID(customer.getCustomerFinancialID());

            FacilityPaper facilityPaper = facilityPaperDao.findByFpRefNumber(applicationCovenantReqDTO.getCasReference());
            if (facilityPaper == null) {
                FacilityPaper newFacilityPaper = new FacilityPaper();
                newFacilityPaper.setFpRefNumber(applicationCovenantReqDTO.getCasReference());
                facilityPaper = facilityPaperDao.save(newFacilityPaper);
            }
            applicationLevelCovenant.setFacilityPaper(facilityPaper);

            applicationLevelCovenant.setCovenant_Code(applicationCovenantDetailsDTO.getCovenant_Code());
            applicationLevelCovenant.setCovenant_Description(applicationCovenantDetailsDTO.getCovenant_Description());
            applicationLevelCovenant.setCovenant_Frequency(applicationCovenantDetailsDTO.getCovenant_Frequency());
            applicationLevelCovenant.setCovenant_Due_Date(applicationCovenantDetailsDTO.getCovenant_Due_Date());
            applicationLevelCovenant.setDisbursementType(applicationCovenantDetailsDTO.getDisbursementType());
            applicationLevelCovenant.setNoFrequencyDueDate(applicationCovenantDetailsDTO.getNoFrequencyDueDate());
            applicationLevelCovenant.setIsExists(AppsConstants.YesNo.N);
            applicationLevelCovenant.setDisplayOrder(displayOrder++);
            applicationLevelCovenant.setApplicableType(applicationCovenantDetailsDTO.getApplicableType());

            applicationLevelCovenant = applicationCovenantDao.save(applicationLevelCovenant);

            List<FacilityCovenantFacilities> facilityCovenantFacilitiesList = new ArrayList<>();
            for (ApplicationCovenantFacilityDTO applicationCovenantFacilityDTO : applicationCovenantDetailsDTO.getApplicationCovenantFacilityDTOS()) {
                FacilityCovenantFacilities facilityCovenantFacilities = new FacilityCovenantFacilities();

                Facility facility = facilityDao.getOne(applicationCovenantFacilityDTO.getFacilityID());

                facilityCovenantFacilities.setApplicationLevelCovenant(applicationLevelCovenant);
                facilityCovenantFacilities.setFacility(facility);
                facilityCovenantFacilities.setCreditFacilityTemplateID(applicationCovenantFacilityDTO.getCreditFacilityTemplateID());
                facilityCovenantFacilities.setCreditFacilityName(applicationCovenantFacilityDTO.getCreditFacilityName());
                facilityCovenantFacilities.setFacilityRefCode(applicationCovenantFacilityDTO.getFacilityRefCode());
                facilityCovenantFacilities.setFacilityCurrency(applicationCovenantFacilityDTO.getFacilityCurrency());
                facilityCovenantFacilities.setFacilityAmount(applicationCovenantFacilityDTO.getFacilityAmount());
                facilityCovenantFacilities.setDisplayOrder(applicationCovenantFacilityDTO.getDisplayOrder());

                facilityCovenantFacilitiesList.add(facilityCovenantFacilities);
            }

            facilityCovenantFacilitiesList = facilityCovenantFacilitiesDao.saveAll(facilityCovenantFacilitiesList);

            applicationLevelCovenant.setFacilityCovenantFacilitiesSet(facilityCovenantFacilitiesList);

            savedCovenants.add(applicationLevelCovenant);
        }

        ApplicationCovenantPostDTO response = prepareResponseDTO(savedCovenants, applicationCovenantReqDTO);

        return response;
    }

    private ApplicationCovenantPostDTO prepareResponseDTO(List<ApplicationLevelCovenant> savedCovenants, ApplicationCovenantReqDTO request){
        List<ApplicationCovenantDetailsDTO> applicationCovenantDetailsDTOs = new ArrayList<>();

        ApplicationCovenantPostDTO applicationCovenantSaveResponseDTOS = new ApplicationCovenantPostDTO();

        for (ApplicationLevelCovenant savedCovenant : savedCovenants) {
            ApplicationCovenantDetailsDTO applicationCovenantDetailsDTO = new ApplicationCovenantDetailsDTO();
            applicationCovenantDetailsDTO.setCovenant_Code(savedCovenant.getCovenant_Code());
            applicationCovenantDetailsDTO.setCovenant_Frequency(savedCovenant.getCovenant_Frequency());
            applicationCovenantDetailsDTO.setCovenant_Description(savedCovenant.getCovenant_Description());
            applicationCovenantDetailsDTO.setCovenant_Due_Date(savedCovenant.getCovenant_Due_Date());
            applicationCovenantDetailsDTO.setDisbursementType(savedCovenant.getDisbursementType());
            applicationCovenantDetailsDTO.setCustId(savedCovenant.getCustomerFinacleID());
            applicationCovenantDetailsDTO.setCasReference(savedCovenant.getFacilityPaper().getFpRefNumber());
            applicationCovenantDetailsDTO.setCreatedUserDisplayName(savedCovenant.getCreatedUserDisplayName());
            applicationCovenantDetailsDTO.setCreatedBy(savedCovenant.getCreatedBy());
            applicationCovenantDetailsDTO.setCreatedDate(savedCovenant.getCreatedDate());
            applicationCovenantDetailsDTO.setApplicationCovenantId(savedCovenant.getApplicationCovenantId());


            applicationCovenantSaveResponseDTOS.setApplicationCovenantId(savedCovenant.getApplicationCovenantId());

            List<ApplicationCovenantFacilityDTO> applicationCovenantFacilityDTOS = new ArrayList<>();
            for (FacilityCovenantFacilities facility : savedCovenant.getFacilityCovenantFacilitiesSet()) {
                ApplicationCovenantFacilityDTO facilityDTO = new ApplicationCovenantFacilityDTO();
                facilityDTO.setFacilityID(facility.getFacility().getFacilityID());
                facilityDTO.setFacilityRefCode(facility.getFacilityRefCode());
                facilityDTO.setCreditFacilityTemplateID(facility.getCreditFacilityTemplateID());
                facilityDTO.setCreditFacilityName(facility.getCreditFacilityName());
                facilityDTO.setFacilityCurrency(facility.getFacilityCurrency());
                facilityDTO.setFacilityAmount(facility.getFacilityAmount());
                facilityDTO.setDisplayOrder(facility.getDisplayOrder());
                applicationCovenantFacilityDTOS.add(facilityDTO);
            }

            applicationCovenantDetailsDTO.setApplicationCovenantFacilityDTOS(applicationCovenantFacilityDTOS);
            applicationCovenantDetailsDTOs.add(applicationCovenantDetailsDTO);
        }


        applicationCovenantSaveResponseDTOS.setRequestUUID(request.getRequestUUID());
        applicationCovenantSaveResponseDTOS.setCustId(request.getCustId());
        applicationCovenantSaveResponseDTOS.setCasReference(request.getCasReference());
        applicationCovenantSaveResponseDTOS.setCreatedUserDisplayName(request.getCreatedUserDisplayName());
        applicationCovenantSaveResponseDTOS.setCreatedBy(request.getCreatedBy());
        applicationCovenantSaveResponseDTOS.setCreatedDate(new Date());
        applicationCovenantSaveResponseDTOS.setCovValue(applicationCovenantDetailsDTOs);

        return applicationCovenantSaveResponseDTOS;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CustomerCovenant findCustomerCovenantById(int customerCovenantId) throws AppsException {
        return customerCovenantDao.findById(customerCovenantId).orElseThrow(() -> new AppsException("Covenant with " + customerCovenantId + " does not exist."));

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CustomerCovenantSaveResponseDTO updateCustomerCovenant(CustomerCovenantUpdateDTO customerCovenantUpdateDTO) throws AppsException {

        Date date = new Date();

        CustomerCovenant customerCovenantDb = customerCovenantDao.findById(customerCovenantUpdateDTO.getCustomerCovenantId()).orElseThrow(() ->new RuntimeException("Customer covenant with " + customerCovenantUpdateDTO.getCustomerCovenantId() + " does not exist"));

        for(CustomerCovenantSaveChildDTO customerCovenantDetailsDTO : customerCovenantUpdateDTO.getCovenantDetails()){
            customerCovenantDb.setRequestUUID(customerCovenantDb.getRequestUUID());
            customerCovenantDb.setCustomerFinancialID(customerCovenantUpdateDTO.getCustId());
            customerCovenantDb.setDisbursementType(customerCovenantUpdateDTO.getDisbursementType());

            FacilityPaper facilityPaper = facilityPaperDao.findByFpRefNumber(customerCovenantUpdateDTO.getCasReference());
            customerCovenantDb.setFacilityPaper(facilityPaper);

            customerCovenantDb.setCreatedDate(date);

            customerCovenantDb.setCovenant_Code(customerCovenantDetailsDTO.getCovenant_Code());
            customerCovenantDb.setCovenant_Description(customerCovenantDetailsDTO.getCovenant_Description());
            customerCovenantDb.setCovenant_Frequency(customerCovenantDetailsDTO.getCovenant_Frequency());
            customerCovenantDb.setCovenant_Due_Date(customerCovenantDetailsDTO.getCovenant_Due_Date());
            customerCovenantDb.setDisbursementType(customerCovenantDetailsDTO.getDisbursementType());
            customerCovenantDb.setNoFrequencyDueDate(customerCovenantDetailsDTO.getNoFrequencyDueDate());
            customerCovenantDb.setApplicableType(customerCovenantDetailsDTO.getApplicableType());

            customerCovenantDb.setCreatedUserDisplayName(customerCovenantUpdateDTO.getCreatedUserDisplayName());
        }

        customerCovenantDb.setStatus(AppsConstants.CovenantStatus.Active);
        customerCovenantDao.saveAndFlush(customerCovenantDb);

        return getUpdateResponse(customerCovenantDb, customerCovenantUpdateDTO);
    }

    private CustomerCovenantSaveResponseDTO getUpdateResponse(CustomerCovenant customerCovenant, CustomerCovenantUpdateDTO request) throws AppsException{

        Date date = new Date();

        CustomerCovenantSaveResponseDTO customerCovenantSaveResponseDTOS = modelMapper.map(customerCovenant, CustomerCovenantSaveResponseDTO.class);
        List<CustomerCovenantSaveChildDTO> customerCovenantDetailsDTOLst = new ArrayList<>();
        CustomerCovenantSaveChildDTO customerCovenantDetailsDTO = new CustomerCovenantSaveChildDTO();

        customerCovenantDetailsDTO.setCovenant_Code(customerCovenant.getCovenant_Code());
        customerCovenantDetailsDTO.setCovenant_Description(customerCovenant.getCovenant_Description());
        customerCovenantDetailsDTO.setCovenant_Frequency(customerCovenant.getCovenant_Frequency());
        customerCovenantDetailsDTO.setCovenant_Due_Date(customerCovenant.getCovenant_Due_Date());
        customerCovenantDetailsDTO.setDisbursementType(customerCovenant.getDisbursementType());

        customerCovenantDetailsDTOLst.add(customerCovenantDetailsDTO);

        customerCovenantSaveResponseDTOS.setRequestUUID(request.getRequestUUID());
        customerCovenantSaveResponseDTOS.setCustId(request.getCustId());
        customerCovenantSaveResponseDTOS.setCasReference(request.getCasReference());
        customerCovenantSaveResponseDTOS.setCreatedDate(date);
        customerCovenantSaveResponseDTOS.setCovenantDetails(customerCovenantDetailsDTOLst);

        return customerCovenantSaveResponseDTOS;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CovenantDetailResDTO getCovenantResponse(CustomerCovenantSaveDTO customerCovenantSaveDTO, CredentialsDTO credentialsDTO) throws Exception {

        log.info("START : GET covenant details for fpRefNumber {}", customerCovenantSaveDTO.getCasReference());

        List<CustomerCovenant> customerCovenants = customerCovenantDao.findByFacilityPaperFpRefNumber(customerCovenantSaveDTO.getCasReference());

        CovenantDetailResDTO response = new CovenantDetailResDTO();
        response.setCasReference(customerCovenantSaveDTO.getCasReference());

        if (!customerCovenants.isEmpty()) {
            CustomerCovenant firstCovenant = customerCovenants.get(0);

            response.setRequestUUID(firstCovenant.getRequestUUID());
            response.setCustId(firstCovenant.getCustomerFinancialID());

            List<CustomerCovenantDetailsReqDTO> covenantDetails = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); // Define date format

            for (CustomerCovenant covenant : customerCovenants) {
                if("Active".equals(covenant.getStatus().toString()) && covenant.getDisbursementType().equals(AppsConstants.CovenantStatusOnDisbursement.POST)){
                    CustomerCovenantDetailsReqDTO detail = new CustomerCovenantDetailsReqDTO();
                    detail.setCovenant_Code(covenant.getCovenant_Code());
                    detail.setCovenant_Frequency(covenant.getCovenant_Frequency());
                    //detail.setCovenant_Due_Date(dateFormat.format(covenant.getCovenant_Due_Date()));
                    detail.setCovenant_Description(covenant.getCovenant_Description());

//                    if ("C_OTH".equals(covenant.getCovenant_Code())) {
//                        detail.setCovenant_Description(covenant.getCovenant_Description());
//                    }
//                    else {
//                        detail.setCovenant_Description("");
//                    }

                    if(covenant.getCovenant_Due_Date() != null){
                        detail.setCovenant_Due_Date(dateFormat.format(covenant.getCovenant_Due_Date()));
                    } else {
                        detail.setCovenant_Due_Date(null);
                    }
                    covenantDetails.add(detail);
                }
                response.setREL(covenantDetails);
            }

//            getCovenantDetailsFromFinacle(firstCovenant.getCustomerFinancialID(), fpRefNumber, credentialsDTO);

        }

        log.info("Retrieved covenant details from DB: {}", response);

        integrationService.getCovenantsUpdated(response, credentialsDTO);
        if(response.getCustId() != null) {
            getCovenantDetailsFromFinacle(response.getCustId(), customerCovenantSaveDTO.getCasReference(), credentialsDTO);
        } else {
            String customerFinancialId = customerDao.findCustomerFinancialId(customerCovenantSaveDTO.getFacilityPaperID());
            if(customerFinancialId != null) {
                getCovenantDetailsFromFinacle(customerFinancialId, customerCovenantSaveDTO.getCasReference(), credentialsDTO);
            } else {
                log.warn("Customer financial ID not found for Facility Paper ID: {}", customerCovenantSaveDTO.getFacilityPaperID());
            }
        }

        log.info("END : GET covenant details {}", response);
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public CovenantDetailsFinacleDTO getCovenantDetailsFromFinacle(String custId, String fpRefNumber, CredentialsDTO credentialsDTO) throws Exception {

        LoadCovenantDataDTO loadCovenantDataDTO = new LoadCovenantDataDTO();
        loadCovenantDataDTO.setCustId(custId);
        loadCovenantDataDTO.setRequestId("122");
        loadCovenantDataDTO.setAcctId("");

        CovenantDetailsFinacleDTO covenantDetailsFinacleDTO = integrationService.getCovenantDetailsFromFinacle(loadCovenantDataDTO, credentialsDTO);

        log.info("covenant Details from Finacle {}", covenantDetailsFinacleDTO);

        saveCustomerCovenants(covenantDetailsFinacleDTO, custId, fpRefNumber, credentialsDTO);
        saveFacilityCovenants(covenantDetailsFinacleDTO, custId, fpRefNumber, credentialsDTO);

        return covenantDetailsFinacleDTO;
    }

    private void saveCustomerCovenants(CovenantDetailsFinacleDTO covenantDetailsFinacleDTO, String custId, String fpRefNumber, CredentialsDTO credentialsDTO) throws ParseException {
        List<CustomerCovenant> customerCovenantList = new ArrayList<>();
        Date covenantDueDate = null;

        log.info("Customer ID: {}, Facility Paper Reference Number: {}", custId, fpRefNumber);

        List<CustomerCovenant> existingCustomerCovenants = customerCovenantDao.findByFacilityPaperFpRefNumber(fpRefNumber);

        for (CovenantDataDTO covenant : covenantDetailsFinacleDTO.getCovenant()) {
            log.info("Processing Covenant: {}", covenant);

            for (CovenantInquiryDTO covenantInq : covenant.getCovenantInq()) {
                if ("C".equals(covenantInq.getCovTyp())) {

                    boolean isDuplicate = existingCustomerCovenants.stream()
                            .anyMatch(existing -> custId.equals(existing.getCustomerFinancialID())
                                    && existing.getFacilityPaper() != null
                                    && fpRefNumber.equals(existing.getFacilityPaper().getFpRefNumber())
                                    && covenantInq.getCovCod().equals(existing.getCovenant_Code()));

                    if (!isDuplicate) {
                        CustomerCovenant customerCovenant = new CustomerCovenant();

                        customerCovenant.setRequestUUID(covenantDetailsFinacleDTO.getRequestId());
                        customerCovenant.setCustomerFinancialID(covenantInq.getCustId());
                        customerCovenant.setCovenant_Code(covenantInq.getCovCod());
                        customerCovenant.setCovenant_Description(covenantInq.getCovRem());
                        customerCovenant.setCovenant_Frequency(covenantInq.getCovFrq());

                        customerCovenant.setCreatedDate(new Date());
                        customerCovenant.setCreatedBy(credentialsDTO.getUserName());
                        customerCovenant.setCreatedUserDisplayName(credentialsDTO.getDisplayName());
                        customerCovenant.setStatus(AppsConstants.CovenantStatus.Active);
                        customerCovenant.setDisbursementType(AppsConstants.CovenantStatusOnDisbursement.POST);
                        customerCovenant.setCreatedBy(credentialsDTO.getUserName());
                        customerCovenant.setCreatedUserDisplayName(credentialsDTO.getDisplayName());
                        customerCovenant.setIsExists(AppsConstants.YesNo.Y);
                        customerCovenant.setComplianceStatus(covenantInq.getCompSt());


                        FacilityPaper facilityPaper = facilityPaperDao.findByFpRefNumber(fpRefNumber);
                        customerCovenant.setFacilityPaper(facilityPaper);

                        if (covenantInq.getCovDue() != null && !covenantInq.getCovDue().isEmpty()) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                            try {
                                covenantDueDate = dateFormat.parse(covenantInq.getCovDue());
                                customerCovenant.setCovenant_Due_Date(covenantDueDate);
                            } catch (ParseException e) {
                                log.error("Error parsing date with 'dd-MM-yyyy': {}", covenantInq.getCovDue(), e);
                            }
                        }
                        if ("N".equals(covenantInq.getCovFrq())) {
                            customerCovenant.setNoFrequencyDueDate("At the time of disbursement");
                        }

                        customerCovenantList.add(customerCovenant);
                    } else {
                        log.info("Duplicate Covenant skipped: custId={}, fpRefNumber={}, covCod={}",
                                custId, fpRefNumber, covenantInq.getCovCod());
                    }
                }
            }
        }
        customerCovenantDao.saveAll(customerCovenantList);
    }


    private void saveFacilityCovenants(CovenantDetailsFinacleDTO covenantDetailsFinacleDTO, String custId, String fpRefNumber, CredentialsDTO credentialsDTO) throws ParseException {
        List<ApplicationLevelCovenant> applicationLevelCovenantList = new ArrayList<>();
        Date covenantDueDate = null;

        List<ApplicationLevelCovenant> existingFacilityCovenants = applicationCovenantDao.findByFacilityPaperFpRefNumber(fpRefNumber);

        log.info("Existing Facility Covenants: {}", existingFacilityCovenants);

        for (CovenantDataDTO covenant : covenantDetailsFinacleDTO.getCovenant()) {
            log.info("Processing Covenant: {}", covenant);

            for (CovenantInquiryDTO covenantInq : covenant.getCovenantInq()) {
                if ("A".equals(covenantInq.getCovTyp())) {

                    boolean isDuplicate = existingFacilityCovenants.stream()
                            .anyMatch(existing -> custId.equals(existing.getCustomerFinacleID())
                                    && fpRefNumber.equals(existing.getFacilityPaper().getFpRefNumber())
                                    && covenantInq.getCovCod().equals(existing.getCovenant_Code()));

                    if (!isDuplicate) {
                        ApplicationLevelCovenant applicationLevelCovenant = new ApplicationLevelCovenant();

                        applicationLevelCovenant.setRequestUUID(covenantDetailsFinacleDTO.getRequestId());
                        applicationLevelCovenant.setCustomerFinacleID(covenantInq.getCustId());
                        applicationLevelCovenant.setCovenant_Code(covenantInq.getCovCod());
                        applicationLevelCovenant.setCovenant_Description(covenantInq.getCovRem());
                        applicationLevelCovenant.setCovenant_Frequency(covenantInq.getCovFrq());

                        applicationLevelCovenant.setCreatedDate(new Date());
                        applicationLevelCovenant.setCreatedBy(credentialsDTO.getUserName());
                        applicationLevelCovenant.setCreatedUserDisplayName(credentialsDTO.getDisplayName());
                        applicationLevelCovenant.setStatus(AppsConstants.CovenantStatus.Active);
                        applicationLevelCovenant.setDisbursementType(AppsConstants.CovenantStatusOnDisbursement.POST);
                        applicationLevelCovenant.setCreatedBy(credentialsDTO.getUserName());
                        applicationLevelCovenant.setCreatedUserDisplayName(credentialsDTO.getDisplayName());
                        applicationLevelCovenant.setIsExists(AppsConstants.YesNo.Y);
                        applicationLevelCovenant.setComplianceStatus(covenantInq.getCompSt());
                        applicationLevelCovenant.setAccountId(covenantInq.getAcctId());

                        FacilityPaper facilityPaper = facilityPaperDao.findByFpRefNumber(fpRefNumber);
                        applicationLevelCovenant.setFacilityPaper(facilityPaper);

                        if (covenantInq.getCovDue() != null && !covenantInq.getCovDue().isEmpty()) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                            try {
                                covenantDueDate = dateFormat.parse(covenantInq.getCovDue());
                                applicationLevelCovenant.setCovenant_Due_Date(covenantDueDate);
                            } catch (ParseException e) {
                                log.error("Error parsing date with 'dd-MM-yyyy': {}", covenantInq.getCovDue(), e);
                            }
                        }
                        if ("N".equals(covenantInq.getCovFrq())) {
                            applicationLevelCovenant.setNoFrequencyDueDate("At the time of disbursement");
                        }

                        applicationLevelCovenantList.add(applicationLevelCovenant);
                    } else {
                        log.info("Duplicate Covenant skipped: custId={}, fpRefNumber={}, covCod={}",
                                custId, fpRefNumber, covenantInq.getCovCod());
                    }
                }
            }
        }
        applicationCovenantDao.saveAll(applicationLevelCovenantList);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public Integer deleteCovenant(Integer customerCovenantId, String createdUserDisplayName, CredentialsDTO credentialsDTO) throws AppsException {
        CustomerCovenant customerCovenant = customerCovenantDao.findById(customerCovenantId).orElseThrow(() -> new AppsException("Covenant with " + customerCovenantId + " does not exist."));
        Date date = new Date();
        customerCovenant.setStatus(AppsConstants.CovenantStatus.Inactive);
        customerCovenant.setCreatedDate(date);
        customerCovenant.setCreatedBy(credentialsDTO.getUserName());
        customerCovenant.setCreatedUserDisplayName(createdUserDisplayName);
        return customerCovenant.getCustomerCovenantId();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public NoneComplianceCovenantDTO addEditCommentToCovenant(NoneComplianceCovenantDTO noneComplianceCovenantDTO, CredentialsDTO credentialsDTO) throws AppsException {

        log.info("Adding comment to covenant: {}", noneComplianceCovenantDTO);

        NoneComplianceCovenant noneComplianceCovenant;

        if(noneComplianceCovenantDTO.getNonComplianceCovenantId() != null){
            noneComplianceCovenant = noneComplianceCovenantDao.findById(noneComplianceCovenantDTO.getNonComplianceCovenantId()).orElseThrow(() -> new AppsException("Covenant comment with ID " + noneComplianceCovenantDTO.getNonComplianceCovenantId() + " not found"));
            noneComplianceCovenant.setSerialNumber(noneComplianceCovenantDTO.getSerialNumber());
            noneComplianceCovenant.setFacilityPaperId(noneComplianceCovenantDTO.getFacilityPaperId());
            noneComplianceCovenant.setComment(noneComplianceCovenantDTO.getComment());
            noneComplianceCovenant.setAddedDate(new Date());
            noneComplianceCovenant.setAddedBy(credentialsDTO.getUserName());
            noneComplianceCovenant.setAddedUserDisplayName(credentialsDTO.getDisplayName());
            noneComplianceCovenant.setAddedUserId(credentialsDTO.getUserID());
        }

        else {
            noneComplianceCovenant = new NoneComplianceCovenant();
            noneComplianceCovenant.setSerialNumber(noneComplianceCovenantDTO.getSerialNumber());
            noneComplianceCovenant.setFacilityPaperId(noneComplianceCovenantDTO.getFacilityPaperId());
            noneComplianceCovenant.setComment(noneComplianceCovenantDTO.getComment());
            noneComplianceCovenant.setAddedDate(new Date());
            noneComplianceCovenant.setAddedBy(credentialsDTO.getUserName());
            noneComplianceCovenant.setAddedUserDisplayName(credentialsDTO.getDisplayName());
            noneComplianceCovenant.setAddedUserId(credentialsDTO.getUserID());
        }

        noneComplianceCovenantDao.save(noneComplianceCovenant);

        NoneComplianceCovenantDTO response = new NoneComplianceCovenantDTO(noneComplianceCovenant);

        log.info("Comment added/edited successfully: {}", response);

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<NoneComplianceCovenantDTO> getCovenantCommentList(Integer facilityPaperId) throws AppsException {
        List<NoneComplianceCovenant> noneComplianceCovenant = noneComplianceCovenantDao.findByFacilityPaperId(facilityPaperId);

        if (noneComplianceCovenant == null) {
            throw new AppsException("No comments found for Facility Paper ID: " + facilityPaperId);
        }

        List<NoneComplianceCovenantDTO> response = noneComplianceCovenant.stream()
                .map(NoneComplianceCovenantDTO::new)
                .collect(Collectors.toList());

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<CusApplicableCovenantDTO> getCusApplicableCovenantList(String fpRefNumber, Integer facilityId, CredentialsDTO credentialsDTO) throws AppsException {

        List<CusApplicableCovenantDTO> response = new ArrayList<>();
        List<CustomerCovenant> customerCovenants = customerCovenantDao.findByFacilityPaperFpRefNumber(fpRefNumber);

        //map facility covenants
        List<FinalDTO> allFacilityCovenants = getFacilityCovenantList(fpRefNumber, credentialsDTO);
        if (allFacilityCovenants != null){
            for (FinalDTO finalDTO : allFacilityCovenants){
                for (ApplicationCovenantDetailsDTO applicationCovenantDetailsDTO : finalDTO.getCovValue()){

                    boolean isFacilityExist = applicationCovenantDetailsDTO.getApplicationCovenantFacilityDTOS().
                            stream().anyMatch(data -> Objects.equals(data.getFacilityID(), facilityId));

                    if (isFacilityExist && applicationCovenantDetailsDTO.getApplicableType().equals(AppsConstants.CovenantApplicableType.AC)
                            && applicationCovenantDetailsDTO.getStatus().equals(AppsConstants.CovenantStatus.Active)){

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
            List<CustomerCovenant> filteredCustomerCovList = customerCovenants.stream().filter(data -> data.getApplicableType()
                            .equals(AppsConstants.CovenantApplicableType.AC) && data.getStatus().equals(AppsConstants.CovenantStatus.Active))
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
    public ExistingFacilityCovenantsDTO saveOrUpdateAcctIdWithFacilityId(ExistingFacilityCovenantsDTO existingFacilityCovenantsDTO) throws AppsException {
        log.info("Saving/updating AcctId with FacilityId: {}", existingFacilityCovenantsDTO);
        ExistingFacilityCovenants existingFacilityCovenants;

        if (existingFacilityCovenantsDTO.getId() != null) {
            existingFacilityCovenants = existingFacilityCovenantsDao.findById(existingFacilityCovenantsDTO.getId()).orElseThrow(() -> new AppsException("Existing Facility Covenant with ID " + existingFacilityCovenantsDTO.getId() + " not found"));
        } else {
            existingFacilityCovenants = new ExistingFacilityCovenants();
        }

        existingFacilityCovenants.setAcctId(existingFacilityCovenantsDTO.getAcctId());
        existingFacilityCovenants.setFacilityPaperId(existingFacilityCovenantsDTO.getFacilityPaperId());
        existingFacilityCovenants.setFacilityId(existingFacilityCovenantsDTO.getFacilityId());

        existingFacilityCovenantsDao.save(existingFacilityCovenants);

        return existingFacilityCovenantsDTO;
    }


    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public List<ExistingFacilityCovenantsDTO> getAllExistingFacilityCovenants(Integer facilityPaperId) throws AppsException {
        List<ExistingFacilityCovenants> existingFacilityCovenantsList = existingFacilityCovenantsDao.findByFacilityPaperId(facilityPaperId);
        return existingFacilityCovenantsList.stream()
                .map(covenant -> {
                    ExistingFacilityCovenantsDTO dto = new ExistingFacilityCovenantsDTO();
                    dto.setId(covenant.getId());
                    dto.setAcctId(covenant.getAcctId());
                    dto.setFacilityId(covenant.getFacilityId());
                    dto.setFacilityPaperId(covenant.getFacilityPaperId());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
