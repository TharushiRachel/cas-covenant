package com.itechro.cas.service.integration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.advanceAnalytics.AdvanceAnalyticsDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.advanceAnalytics.AnalyticsDecision;
import com.itechro.cas.model.dto.advancedAnalytics.*;
import com.itechro.cas.model.dto.integration.response.StandardResponse;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.util.NumberUtil;
import org.json.simple.JSONArray;
import com.itechro.cas.util.CalendarUtil;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.itechro.cas.util.CalendarUtil.CRM_DATE_FORMAT;

@Service
public class MicroIntegrationService {

    private static final Logger LOG = LoggerFactory.getLogger(MicroIntegrationService.class);

    @Value("${apps.integration.service.timeout.in.milliseconds.default}")
    private long serviceTimeoutInMillisecondsDefault;

    @Value("${application.integration.load.leasing.individual.aa.url}")
    private String leasIndividualAA;

    @Value("${application.integration.load.leasing.journey.individual.joint.url}")
    private String leasJourneyValidationIndividualJoint;

    @Value("${application.integration.load.leasing.journey.limited.liability.url}")
    private String leasJourneyValidationLLC;

    @Value("${application.integration.load.leasing.journey.sole.proprietorship.url}")
    private String leasJourneyValidationSoleProp;

    @Value("${apps.integration.service.timeout.in.milliseconds.max}")
    private long serviceTimeoutInMillisecondsMax;

    @Value("${apps.aa.model.log.path}")
    private String logPath;

    @Autowired
    private AdvanceAnalyticsDao advanceAnalyticsDao;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ResponseEntity<StandardResponse<AnalyticsDecisionDTO>> getIndividualLeasingAA(
            IndividualLeaseRequestDTO individualLeaseRequestDTO, CredentialsDTO credentialsDTO) throws IOException {
        LOG.info("START | getIndividualLeasingAA | request: {}", individualLeaseRequestDTO);

        StandardResponse<AnalyticsDecisionDTO> standardResponse = new StandardResponse<>();
        IndividualLeaseResDTO individualLeaseResDTO = new IndividualLeaseResDTO();

        String url = this.leasIndividualAA;
        ExchangeStrategies strategies =
                ExchangeStrategies.builder()
                        .codecs(
                                configure -> configure.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // 10MB
                        .build();
        WebClient webClient = WebClient.builder().exchangeStrategies(strategies).build();

        JSONObject bodyValue = new JSONObject();
        bodyValue.put("old_nic_crib", individualLeaseRequestDTO.getOldNicCrib());
        bodyValue.put("new_nic_crib", individualLeaseRequestDTO.getNewNicCrib());
        bodyValue.put("coporate_crib", individualLeaseRequestDTO.getCooperateCrib());
        bodyValue.put("customer_type", individualLeaseRequestDTO.getCustomerType());

        //save AA Logs
        saveAAModelLog(bodyValue, individualLeaseRequestDTO.getLeadRef());
        try {
            String response =
                    webClient
                            .post()
                            .uri(url)
                            .bodyValue(bodyValue)
                            .retrieve()
                            .bodyToMono(String.class)
                            .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                            .onErrorReturn(
                                    "Timeout occurred or service failed in leasIndividualAA") // optional fallback
                            .block();

            LOG.info("INFO: leasIndividualAA | IntegrationService {}", response);

            Gson gson = new Gson();
            individualLeaseResDTO = gson.fromJson(response, IndividualLeaseResDTO.class);

            // update analytics decision to lead
           // AnalyticsDecisionDTO analyticsDecision = updateLeadAnalyticsDecision(individualLeaseRequestDTO.getLeadId(), individualLeaseResDTO, credentialsDTO);

//            standardResponse =
//                    new StandardResponse<>(
//                            AppsConstants.ErrorEnums.SUCCESS_CODE.getStatus(),
//                            AppsConstants.ErrorEnums.SUCCESS_CODE.getLabel(),
//                            analyticsDecision);

            LOG.info("INFO: leasIndividualAA | IntegrationService :{}", standardResponse);
        } catch (Exception e) {
            standardResponse =
                    new StandardResponse<>(
                            AppsConstants.ErrorEnums.ERROR_CODE.getStatus(), AppsConstants.ErrorEnums.ERROR_CODE.getLabel(), null);
            LOG.error(
                    "ERROR: getIndividualLeasingAA | IntegrationService | Exception: {}", e.getMessage(), e);
        }
        LOG.info("END | getIndividualLeasingAA | response: {}", standardResponse);
        return ResponseEntity.ok().body(standardResponse);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public ResponseEntity<StandardResponse<AnalyticsDecisionDTO>> getLeasingJourneyValidation(
            LeaseJourneyRequestDTO leaseJourneyRequestDTO, CredentialsDTO credentialsDTO) throws IOException {
        LOG.info("START | getLeasingJourneyValidation | request: {}", leaseJourneyRequestDTO);

        StandardResponse<AnalyticsDecisionDTO> standardResponse;
        IndividualLeaseResDTO individualLeaseResDTO;
        String url = "";
        if (leaseJourneyRequestDTO.getJourneyType().equals("SoleProprietor") || leaseJourneyRequestDTO.getJourneyType().equals("Partnership")){
            url = this.leasJourneyValidationSoleProp;
        }else if (leaseJourneyRequestDTO.getJourneyType().equals("LimitedLiability")){
            url = this.leasJourneyValidationLLC;
        }else {
            url = this.leasJourneyValidationIndividualJoint;
        }
        ExchangeStrategies strategies =
                ExchangeStrategies.builder()
                        .codecs(
                                configure -> configure.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // 10MB
                        .build();
        WebClient webClient = WebClient.builder().exchangeStrategies(strategies).build();

        JSONObject bodyValue = new JSONObject();

        JSONObject facilityParameters = new JSONObject();
        facilityParameters.put("FacilityType", leaseJourneyRequestDTO.getFacility().getFacilityType());
        facilityParameters.put("FacilityAmount", leaseJourneyRequestDTO.getFacility().getFacilityAmount());
        facilityParameters.put("Tenure", leaseJourneyRequestDTO.getFacility().getTenure());

        JSONArray company = new JSONArray();
        JSONArray individuals = new JSONArray();

        //map company borrowers
        for (BorrowerRequestDTO borrowerRequestDTO : leaseJourneyRequestDTO.getCompany()){
            JSONObject companyBorrower = new JSONObject();
            companyBorrower.put("Name", borrowerRequestDTO.getName());
            companyBorrower.put("Type", borrowerRequestDTO.getType());
            companyBorrower.put("Principal", getPrincipalRequestJson(borrowerRequestDTO.getPrincipal()));
            companyBorrower.put("CRIBCall", getCRIBRequestJson(borrowerRequestDTO.getCribRequests(),false));
            company.add(companyBorrower);
        }

        //map individual borrowers
        for (BorrowerRequestDTO borrowerRequestDTO : leaseJourneyRequestDTO.getIndividuals()){
            JSONObject individualBorrower = new JSONObject();
            individualBorrower.put("Name", borrowerRequestDTO.getName());
            individualBorrower.put("CRIBCall", getCRIBRequestJson(borrowerRequestDTO.getCribRequests(),true));
            individuals.add(individualBorrower);
        }

        bodyValue.put("Facility", facilityParameters);
        bodyValue.put("Company", company);
        bodyValue.put("Individuals", individuals);

        //save AA Logs
        saveAAModelLog(bodyValue, leaseJourneyRequestDTO.getLeadRef());
        try {
            String response =
                    webClient
                            .post()
                            .uri(url)
                            .bodyValue(bodyValue)
                            .retrieve()
                            .bodyToMono(String.class)
                            .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                            .onErrorReturn(
                                    "Timeout occurred or service failed in getLeasingJourneyValidation") // optional fallback
                            .block();

            LOG.info("INFO: getLeasingJourneyValidation | IntegrationService {}", response);

            Gson gson = new Gson();
            individualLeaseResDTO = gson.fromJson(response, IndividualLeaseResDTO.class);

            // update analytics decision to lead
            AnalyticsDecisionDTO analyticsDecision = updateLeadAnalyticsDecision(leaseJourneyRequestDTO, individualLeaseResDTO, credentialsDTO);

            standardResponse =
                    new StandardResponse<>(
                            AppsConstants.ErrorEnums.SUCCESS_CODE.getStatus(),
                            AppsConstants.ErrorEnums.SUCCESS_CODE.getLabel(),
                            analyticsDecision);

            LOG.info("INFO: getLeasingJourneyValidation | IntegrationService :{}", standardResponse);
        } catch (Exception e) {
            standardResponse =
                    new StandardResponse<>(
                            AppsConstants.ErrorEnums.ERROR_CODE.getStatus(), AppsConstants.ErrorEnums.ERROR_CODE.getLabel(), null);
            LOG.error(
                    "ERROR: getLeasingJourneyValidation | IntegrationService | Exception: {}", e.getMessage(), e);
        }
        LOG.info("END | getLeasingJourneyValidation | response: {}", standardResponse);
        return ResponseEntity.ok().body(standardResponse);
    }

    private JSONArray getPrincipalRequestJson(List<BorrowerPrincipalRequestDTO> principalRequests){
        JSONArray principals = new JSONArray();

        for (BorrowerPrincipalRequestDTO principalRequest : principalRequests){
            JSONObject principal = new JSONObject();
            principal.put("Relationship", principalRequest.getRelationship());
            principal.put("IdentityDoc", principalRequest.getIdentityDoc());
            principal.put("IdentityRef", principalRequest.getIdentityRef());
            principal.put("Share", principalRequest.getShare());

            principals.add(principal);
        }
        return principals;
    }

    private JSONArray getCRIBRequestJson(List<CRIBRequestDTO> cribRequests, Boolean isIndividual){
        JSONArray cribCalls = new JSONArray();

        for (CRIBRequestDTO cribRequest : cribRequests){
            JSONObject cribCall = new JSONObject();
            if (isIndividual) {
                cribCall.put("IdentityDoc", cribRequest.getIdentityDoc());
            }
            cribCall.put("IdentityRef", cribRequest.getIdentityRef());
            cribCall.put("CRIB_Report_JSON_Object", cribRequest.getReportJson());

            cribCalls.add(cribCall);
        }
        return cribCalls;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public AnalyticsDecisionDTO updateLeadAnalyticsDecision(
            LeaseJourneyRequestDTO leaseJourneyRequestDTO, IndividualLeaseResDTO individualLeaseResDTO, CredentialsDTO credentialsDTO) {
        LOG.info(
                "START | updateLeadAnalyticsDecision | leadId: {}, individualLeaseResDTO: {}",
                leaseJourneyRequestDTO.getLeadId(),
                individualLeaseResDTO);

        AnalyticsDecisionDTO response = new AnalyticsDecisionDTO();

        try {
            Date date = new Date();
            String json = new Gson().toJson(individualLeaseResDTO.getPayload());

            AnalyticsDecision analyticsDecision = advanceAnalyticsDao.findByLeadId(leaseJourneyRequestDTO.getLeadId());
            if (analyticsDecision == null){
                analyticsDecision = new AnalyticsDecision();
            }
            analyticsDecision.setLeadId(leaseJourneyRequestDTO.getLeadId());
            analyticsDecision.setDecision(json);

            if (individualLeaseResDTO.getPayload() != null) {
                analyticsDecision.setDecisionStatus(individualLeaseResDTO.getPayload().getDecision().toString());
                analyticsDecision.setChannel(individualLeaseResDTO.getPayload().getChannel().toString());
            }
            analyticsDecision.setFacilityData(getFacilityDescription(leaseJourneyRequestDTO.getFacility()));
            analyticsDecision.setCreatedBy(credentialsDTO.getUserName());
            analyticsDecision.setCreatedDate(date);

            analyticsDecision = advanceAnalyticsDao.save(analyticsDecision);
            response = new AnalyticsDecisionDTO(analyticsDecision);
            LOG.info("END: Update Lead With Analytics Decision");

        } catch (Exception e) {
            LOG.error("ERROR: updateLeadAnalyticsDecision | Exception: {}", e.getMessage(), e);
        }
        LOG.info("END | updateLeadAnalyticsDecision | leadId: {}", leaseJourneyRequestDTO.getLeadId());

        return response;
    }

    public void saveAAModelLog(JSONObject bodyValue, String leadRef) throws IOException {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonPretty = gson.toJson(bodyValue);

            Date date = new Date();
            String dateStr = CalendarUtil.getFormattedDate(date, CRM_DATE_FORMAT);
            String fileName = leadRef.concat("_").concat(dateStr).concat(".json");

            fileName = fileName.replaceAll(" ", "_").replaceAll(":", "_");

            Path dir = Paths.get(logPath);
            Files.createDirectories(dir);

            Path target = dir.resolve(fileName);
            Path temp = Files.createTempFile(dir, "aa_model_", ".tmp");

            Files.write(temp,
                    jsonPretty.getBytes("UTF-8"),
                    StandardOpenOption.TRUNCATE_EXISTING);

            Files.move(temp, target,
                    StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            LOG.info("ERROR:: saveAAModelLog ", e);
        }
    }

    private String getFacilityDescription(FacilityRequestDTO facility){
        String description = "";
        if (facility != null) {
            String formattedAmount = facility.getFacilityAmount() != null ? NumberUtil.getFormattedAmount(facility.getFacilityAmount()) : "-";
            description = "<b>Facility Type :- </b>".concat(facility.getFacilityType())
                    .concat("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <b>Facility Amount :- </b> Rs.").concat(formattedAmount)
                    .concat("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <b>Tenure :- </b>").concat(facility.getTenure()).concat(" month(s)");
        }
        return description;
    }
}
