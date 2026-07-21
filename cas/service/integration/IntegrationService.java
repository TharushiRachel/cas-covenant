package com.itechro.cas.service.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.facilitypaper.GroupExposureDetailDao;
import com.itechro.cas.exception.impl.AppsCommonErrorCode;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.facilitypaper.GroupExposureDetail;
import com.itechro.cas.model.dto.acae.request.ACAEAasRecordRQ;
import com.itechro.cas.model.dto.acae.request.ACAEAcctBalByAcctRQ;
import com.itechro.cas.model.dto.acae.request.ACAEStatByAcctRQ;
import com.itechro.cas.model.dto.acae.response.ACAEAasRecordsDTO;
import com.itechro.cas.model.dto.acae.response.ACAEAcctBalByAcctDTO;
import com.itechro.cas.model.dto.acae.response.ACAEStatByAcctDTO;
import com.itechro.cas.model.dto.covenants.CovenantDetailsFinacleDTO;
import com.itechro.cas.model.dto.covenants.CovenantListDTO;
import com.itechro.cas.model.dto.covenants.LoadCovenantDataDTO;
import com.itechro.cas.model.dto.customer.*;
import com.itechro.cas.model.dto.customer.response.CovenantDetailResDTO;
import com.itechro.cas.model.dto.das.DADesignationCodeDTO;
import com.itechro.cas.model.dto.facilitypaper.GroupExposureCustomerID;
import com.itechro.cas.model.dto.facilitypaper.GroupExposureDetailDTO;
import com.itechro.cas.model.dto.finacle.request.ExportTurnOverRQ;
import com.itechro.cas.model.dto.finacle.request.GuaranteeVolumesRQ;
import com.itechro.cas.model.dto.finacle.request.ImportTurnOverRQ;
import com.itechro.cas.model.dto.finacle.request.LoanAccCovenantReqDTO;
import com.itechro.cas.model.dto.finacle.response.LoanAccountCovenantDTO;
import com.itechro.cas.model.dto.integration.request.*;
import com.itechro.cas.model.dto.integration.request.cribreport.CribCorporateRQ;
import com.itechro.cas.model.dto.integration.request.cribreport.CribRequestDTO;
import com.itechro.cas.model.dto.integration.request.cribreport.CribRetailReportRQ;
import com.itechro.cas.model.dto.integration.request.customerstatistic.CustomerAccountStatRQ;
import com.itechro.cas.model.dto.integration.request.customerstatistic.CustomerCasServiceAccountStatRQ;
import com.itechro.cas.model.dto.integration.request.customerstatistic.CustomerTranDetCashFlowRQ;
import com.itechro.cas.model.dto.integration.request.finacle.*;
import com.itechro.cas.model.dto.integration.response.*;
import com.itechro.cas.model.dto.integration.response.AccountStat.dtos.accountstatistic.CustomerAccountStatisticResponseDTO;
import com.itechro.cas.model.dto.integration.response.AccountStat.dtos.advancedportfolio.AdvancePortfolioResponseDTO;
import com.itechro.cas.model.dto.integration.response.AccountStat.dtos.casstat.CasStatResponseDTO;
import com.itechro.cas.model.dto.integration.response.AccountStat.dtos.depositdetails.DepositDetailResponseDTO;
import com.itechro.cas.model.dto.integration.response.AccountStat.responses.accountstatistic.CustomerAccountStatisticResponse;
import com.itechro.cas.model.dto.integration.response.AccountStat.responses.advancedportfolio.AdvancedPortfolioResponse;
import com.itechro.cas.model.dto.integration.response.AccountStat.responses.casstat.CasStatResponse;
import com.itechro.cas.model.dto.integration.response.AccountStat.responses.depositdetails.DepositDetailResponse;
import com.itechro.cas.model.dto.integration.response.CustomerStatistics.ExecuteFinacleScriptCustomDataResponse;
import com.itechro.cas.model.dto.integration.response.CustomerStatistics.ExecuteFinacleScriptCustomDataResponseDTO;
import com.itechro.cas.model.dto.integration.response.coveringApproval.CovAppCustomerDTO;
import com.itechro.cas.model.dto.integration.response.coveringApproval.TranDetailsResponseDTO;
import com.itechro.cas.model.dto.integration.response.creditschedule.CreditScheduleESBDTO;
import com.itechro.cas.model.dto.integration.response.creditschedule.CreditScheduleInformationDTO;
import com.itechro.cas.model.dto.integration.response.creditschedule.ScheduleResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.CribResponseDTO;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CribCorporateReportResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.CribRetailReportResponse;
import com.itechro.cas.model.dto.integration.response.finacle.*;
import com.itechro.cas.model.dto.integration.response.kalipto.KalyptoResponseDTO;
import com.itechro.cas.model.dto.integration.ws.request.AccountDetailListWSRQ;
import com.itechro.cas.model.dto.integration.ws.request.LoadCustomerDetailWSRQ;
import com.itechro.cas.model.dto.integration.ws.response.*;
import com.itechro.cas.model.dto.integration.ws.response.kalypto.KalyptoResponse;
import com.itechro.cas.model.dto.lead.crm.CrmLoginRequest;
import com.itechro.cas.model.dto.lead.crm.CrmLoginResponse;
import com.itechro.cas.model.dto.lead.crm.CrmSaveResponseDTO;
import com.itechro.cas.model.dto.lead.crm.LeadCrmDTO;
import com.itechro.cas.model.dto.sme.SmeCustomerTurnoverResDTO;
import com.itechro.cas.model.dto.sme.SmeCustomerTurnoverRqDTO;
import com.itechro.cas.model.dto.systemintegration.SystemIntegrationDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.customer.CustomerCribResponseService;
import com.itechro.cas.service.faclititypaper.creditcalculator.CalculatorService;
import com.itechro.cas.util.CalendarUtil;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import javax.xml.bind.JAXB;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class IntegrationService {

    private static final Logger LOG = LoggerFactory.getLogger(IntegrationService.class);

    @Value("${apps.integration.account.detail.by.account.number.enable}")
    private boolean accountDetailByAccountNumberEnabled;
    @Value("${apps.integration.account.detail.by.account.number.url}")
    private String accountDetailByAccountNumberUrl;

    @Value("${apps.integration.load.customer.account.list.enable}")
    private boolean loadCustomerAccountDetailListEnabled;
    @Value("${apps.integration.load.customer.account.list.url}")
    private String loadCustomerAccountDetailListUrl;

    @Value("${apps.integration.load.customer.additional.detail.enable}")
    private boolean loadCustomerAdditionalAccountDetailEnabled;
    @Value("${apps.integration.load.customer.additional.detail.url}")
    private String loadCustomerAdditionalAccountDetailUrl;

    @Value("${apps.integration.facility.detail.by.account.number.enable}")
    private boolean facilityDetailByAccountNumberEnabled;
    @Value("${apps.integration.facility.detail.by.account.number.url}")
    private String facilityDetailByAccountNumberUrl;

    @Value("${apps.integration.upm.detail.by.user.id.and.app.code.enable}")
    private boolean umpDetailByUserIdAndAppCodeEnabled;
    @Value("${apps.integration.upm.detail.by.user.id.and.app.code.url}")
    private String umpDetailByUserIdAndAppCodeUrl;

    @Value("${apps.integration.branch.authority.level.list.enable}")
    private boolean userDetailsBranchAuthorityLevelListEnabled;
    @Value("${apps.integration.branch.authority.level.list.url}")
    private String userDetailsBranchAuthorityLevelListUrl;

    @Value("${apps.integration.assistants.list.enable}")
    private boolean assistantListEnabled;
    @Value("${apps.integration.assistants.list.url}")
    private String assistantListUrl;

    @Value("${apps.integration.upm.detail.by.ad.user.id.and.app.code.enable}")
    private boolean umpDetailByAdUserIdAndAppCodeEnabled;

    @Value("${apps.integration.upm.detail.by.ad.user.id.and.app.code.url}")
    private String umpDetailByAdUserIdAndAppCodeUrl;

    @Value("${apps.integration.load.branch.list.enable}")
    private boolean loadBranchListEnabled;
    @Value("${apps.integration.load.branch.list.url}")
    private String loadBranchListUrl;

    @Value("${apps.integration.load.customer.enable}")
    private boolean loadCustomerEnabled;
    @Value("${apps.integration.load.customer.url}")
    private String loadCustomerUrl;

    //?UniqueIdentifier=200419554
    @Value("${apps.integration.kalypto.enable}")
    private boolean kalypotoEnabled;
    @Value("${apps.integration.kalypto.url}")
    private String kalyptoUrl;

    @Value("${apps.integration.load.customer.account.stat.enable}")
    private boolean customerAccountStatEnabled;
    @Value("${apps.integration.load.customer.account.stat.url}")
    private String customerAccountStatUrl;

    @Value("${apps.integration.load.customer.cas.stat.enable}")
    private boolean customerCasStatEnabled;
    @Value("${apps.integration.load.customer.cas.stat.url}")
    private String customerCasStatUrl;

    @Value("${apps.integration.load.customer.deposit.details.enable}")
    private boolean customerDepositDetailsEnabled;
    @Value("${apps.integration.load.customer.deposit.details.url}")
    private String customerDepositDetailsUrl;

    @Value("${apps.integration.load.customer.facility.details.enable}")
    private boolean customerFacilityDetailsEnabled;
    @Value("${apps.integration.load.customer.facility.details.url}")
    private String customerFacilityDetailsUrl;

    @Value("${apps.integration.load.retail.crib.report.enable}")
    private boolean retailCribReportEnabled;
    @Value("${apps.integration.load.retail.crib.report.url}")
    private String retailCribReportUrl;

    @Value("${apps.integration.load.corporate.crib.report.enable}")
    private boolean corporateCribReportEnabled;
    @Value("${apps.integration.load.corporate.crib.report.url}")
    private String corporateCribReportUrl;

    @Value("${apps.integration.load.corporate.comprehensive}")
    private String corporateComprehensiveUrl;

    @Value("${apps.integration.load.consumer.comprehensive}")
    private String consumerComprehensiveUrl;

    @Value("${apps.cas.application.code}")
    private String appCode;

    @Value("${apps.crib.consumer.ReportId}")
    private String consumerReportID;

    @Value("${apps.crib.consumer.SubjectType}")
    private String consumerSubjectType;

    @Value("${apps.crib.consumer.ResponseType}")
    private String consumerResponseType;

    @Value("${apps.crib.consumer.ReasonCode}")
    private String consumerReasonCode;

    @Value("${apps.crib.consumer.Citizenship}")
    private String consumerCitizenship;

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

    @Value("${apps.integration.load.credit.schedule.enable}")
    private boolean creditScheduleEnabled;
    @Value("${apps.integration.load.credit.schedule.url}")
    private String creditScheduleUrl;

    @Value("${apps.current.user.div.code}")
    private String riskDivCode;

    @Value("${apps.no.of.months.per.year}")
    private String noOfMonthsPerYear;

    @Value("${apps.integration.load.account.cashFlow.url}")
    private String tranDetForCashFlowUrl;

    @Value("${apps.integration.load.account.cashFlow.enable}")
    private boolean tranDetForCashFlowEnabled;

    @Value("${apps.integration.customer.covenant.list.url}")
    private String customerCovenant;

    @Value("${apps.integration.customer.covenant.list.enable}")
    private boolean customerCovenantEnable;

    @Value("${apps.integration.add.customer.covenant.url}")
    private String addCustomerCovenantUrl;

    @Value("${apps.integration.add.customer.covenant.enable}")
    private String addCustomerCovenantEnable;

    @Value("${apps.security.document.submit.div}")
    private String securityDocumentSubmitDiv;

    @Value("${apps.security.document.submit.work.class}")
    private String securityDocumentSubmitWorkClass;

    @Value("${apps.integration.load.covering.approval.details.enable}")
    private boolean coveringApprovalTranDetailsEnabled;

    @Value("${apps.integration.load.covering.approval.details.url}")
    private String coveringApprovalTranDetailsUrl;

    @Value("${apps.integration.load.acae.status.enable}")
    private boolean acaeStatusDetailsEnable;

    @Value("${apps.integration.load.acae.status.url}")
    private String acaeStatusDetailsURL;
    @Value("${apps.bcc.enterer.work.class}")
    private String bccEntererWorkClass;

    @Value("${apps.bcc.authorizer.work.class}")
    private String bccAuthorizerWorkClass;

    @Value("${apps.bcc.inquirer.work.class}")
    private String bccInquirerWorkClass;

    @Value("${apps.integration.load.designations.details.url}")
    private String dasDesignationURL;

    @Value("${apps.integration.load.designations.details.enable}")
    private Boolean dasDesignationEnable;

    @Value("${apps.integration.finacle.url}")
    private String finacleExOutLimitsUrl;

    @Value("${apps.integration.finacle.enable}")
    private boolean finacleExOutLimitsEnable ;

    @Value("${apps.integration.finacleIsWatchlisted.enable}")
    private boolean finacleIsWatchlistedEnable ;
    @Value("${apps.integration.finacleIsWatchlisted.url}")
    private String finacleIsWatchlistedUrl;

    @Value("${apps.integration.finacleGuaranteeVolumes.enable}")
    private boolean finacleGuaranteeVolumesEnable ;
    @Value("${apps.integration.finacleGuaranteeVolumes.url}")
    private String finacleGuaranteeVolumesUrl;

    @Value("${apps.integration.finacle.export.dcBillsTurnover.enable}")
    private boolean exportDcBillsTurnoverEnable ;
    @Value("${apps.integration.finacle.export.dcBillsTurnover.url}")
    private String exportDcBillsTurnoverUrl;

    @Value("${apps.integration.customerAASAccounts.enable}")
    private boolean customerAASAccountsEnable ;
    @Value("${apps.integration.customerAASAccounts.url}")
    private String customerAASAccountsUrl;


    @Value("${apps.integration.insuranceValuation.enable}")
    private boolean insuranceValuationEnable ;
    @Value("${apps.integration.insuranceValuation.url}")
    private String insuranceValuationUrl;

    @Value("${apps.integration.kalypto.system.enable}")
    private boolean kalypotoSystemEnabled;

    @Value("${apps.integration.kalypto.system.url}")
    private String kalyptoSystemUrl;

    @Value("${apps.integration.load.acae.aasRecords.enable}")
    private boolean acaeAasRecordsEnable;

    @Value("${apps.integration.load.acae.aasRecords.url}")
    private String acaeAasRecordsUrl;

    @Value("${apps.integration.load.acae.statByAcct.enable}")
    private boolean acaeStatByAcctEnable;

    @Value("${apps.integration.load.acae.statByAcct.url}")
    private String acaeStatByAcctUrl;

    @Value("${apps.integration.load.acae.acctBalByAccts.enable}")
    private boolean acaeAcctBalByAcctsEnable;

    @Value("${apps.integration.load.acae.acctBalByAccts.url}")
    private String acaeAcctBalByAcctsUrl;

    @Value("${apps.integration.load.acae.acctClassificationData.enable}")
    private boolean acaeAcctClassificationDataEnable;

    @Value("${apps.integration.load.acae.acctClassificationData.url}")
    private String acaeAcctClassificationDataUrl;

    @Value("${apps.integration.load.branch.details.enable}")
    private boolean umpLoadBranchDetailEnable;

    @Value("${apps.integration.load.branch.details.url}")
    private String umpLoadBranchDetailsUrl;

    @Value("${apps.integration.upm.detail.by.ad.user.and.app.code.enable}")
    private boolean umpDetailByAdUserEnable;

    @Value("${apps.integration.upm.detail.by.ad.user.and.app.code.url}")
    private String umpDetailByAdUserUrl;
    @Value("${apps.integration.load.user.application.details.url}")
    private String userApplicationURL;

    @Value("${apps.integration.load.user.application.details.enable}")
    private Boolean userApplicationEnable;

    @Value("${apps.integration.LoanOps.Inq.getReptData.url}")
    private String reportingDataURL;
    @Value("${apps.integration.LoanOps.Inq.getLimitNodeDet.url}")
    private String limitNodeDetailsURL;

    @Value("${apps.integration.load.covenant.details.finacle.account.url}")
    private String accountCovenantDetailsURL;

    @Value("${apps.integration.finacle.export.inwardTurnover.enable}")
    private boolean exportInwardTurnoverEnable ;
    @Value("${apps.integration.finacle.export.inwardTurnover.url}")
    private String exportInwardTurnoverrUrl;

    @Value("${apps.integration.finacle.import.collectionTurnover.enable}")
    private boolean importCollectionTurnoverEnable ;
    @Value("${apps.integration.finacle.import.collectionTurnover.url}")
    private String importCollectionTurnoverUrl;

    @Value("${apps.integration.finacle.import.dcTurnover.enable}")
    private boolean importDCTurnoverEnable ;
    @Value("${apps.integration.finacle.import.dcTurnover.url}")
    private String importDCTurnoverUrl;

    @Value("${apps.integration.finacle.import.outwardTurnover.enable}")
    private boolean importOutwardTurnoverEnable ;
    @Value("${apps.integration.finacle.import.outwardTurnover.url}")
    private String importOutwardTurnoverUrl;

//    @Value("${apps.integration.load.covenant.details.finacle.url}")
//    private String covenantDetailsURL;

    @Value("${apps.integration.load.covenant.details.finacle.enable}")
    private Boolean covenantDetailsEnable;
    
    @Value("${apps.integration.LoanOps.Inq.collateral.url}")
    private String accountCollateralDetailsURL;


    @Value("${apps.integration.load.crib.search.url}")
    private String cribSearch;

    @Value("${apps.integration.load.crib.search.continue.url}")
    private String cribSearchContinue;

    @Value("${apps.integration.load.crib.search.company.url}")
    private String cribSearchCompany;

    @Value("${apps.integration.load.crib.search.continue.company.url}")
    private String cribSearchContinueCompany;

    @Value("${apps.integration.load.crib.report.custom.url}")
    private String cribReportCustom;

    @Value("${apps.integration.load.crib.report.custom.token.url}")
    private String cribReportCustomToken;

    @Value("${apps.integration.load.crib.report.pdf.url}")
    private String cribReportPDF;

    @Value("${apps.integration.load.group.exposure.url}")
    private String groupExposureDetailsURL;

    @Value("${apps.integration.Misc.Inq.PrefentialChg.CPCMRec.url}")
    private String commissionDataURL;

    @Value("${apps.integration.Misc.Inq.PrefentialChg.ReportingStmt.url}")
    private String commissionMoreDataURL;

    @Value("${apps.integration.Misc.Inq.GetRct.url}")
    private String rctUrl;

    @Value("${apps.integration.service.timeout.in.milliseconds.default}")
    private long serviceTimeoutInMillisecondsDefault;

    @Value("${apps.integration.service.timeout.in.milliseconds.max}")
    private long serviceTimeoutInMillisecondsMax;

    @Value("${apps.integration.crm.generate.token.enable}")
    private boolean isCrmLoginEnable;

    @Value("${apps.integration.crm.generate.token.url}")
    private String crmLoginUrl;

    @Value("${apps.integration.crm.save.object.enable}")
    private boolean isSaveCrmObjectEnable;

    @Value("${apps.integration.crm.save.object.url}")
    private String saveCrmObjectUrl;

    @Value("${apps.integration.crm.login.username}")
    private String crmUserName;

    @Value("${apps.integration.crm.login.password}")
    private String crmPassword;

    @Value("${apps.log.originalBase64.enabled}")
    public boolean logOriginalBase64;

    @Value("${apps.integration.sme.turnover.enable}")
    public boolean smeTurnoverEnable;

    @Value("${apps.integration.sme.turnover.enable.url}")
    public String smeTurnoverUrl;

    @Autowired
    private CustomerCribResponseService customerCribResponseService;

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private GroupExposureDetailDao groupExposureDetailDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    public AccountDetailResponse getAccountDetailsByCustomerAccountNumber(AccountDetailLoadRQ accountDetailLoadRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get Account detail by customer account number: {} by {}", accountDetailLoadRQ, credentialsDTO);
        boolean isServiceEnable = this.accountDetailByAccountNumberEnabled;
        AccountDetailResponse detailResponse = null;
        AccountDetailWSRS result = null;
        if (isServiceEnable) {

            String url = this.accountDetailByAccountNumberUrl.replace("{accNo}", accountDetailLoadRQ.getAccountNumber());
            RestTemplate restTemplate = new RestTemplate();
            try {
                result = restTemplate.getForObject(url, AccountDetailWSRS.class);
                detailResponse = new AccountDetailResponse(result);
                detailResponse.setSuccess(true);
                LOG.info("END: Get Account detail by customer account number: {} by {}", result, credentialsDTO);
            } catch (Exception e) {
                LOG.info("ERROR: Error occur while loading Account detail url :{}: {}: {} by {}", url, accountDetailLoadRQ, result, credentialsDTO, e);
            }
        } else {
            LOG.warn("WARN: Account detail loading by account number service is disabled {} by {}", accountDetailLoadRQ, credentialsDTO);
        }
        return detailResponse;
    }

    //this is post request to check
    @Transactional(propagation = Propagation.SUPPORTS)
    public AccountDetailListResponse getCustomerAccountDetailList(AccountDetailListRQ accountDetailListRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get Account detail List {} by {}", accountDetailListRQ, credentialsDTO);
        boolean isServiceEnable = this.loadCustomerAccountDetailListEnabled;
        AccountDetailListResponse detailResponse = null;
        AccountDetailListWSRS result = null;
        if (isServiceEnable) {
            if (accountDetailListRQ.validate()) {

                String url = this.loadCustomerAccountDetailListUrl;
                RestTemplate restTemplate = new RestTemplate();
                AccountDetailListWSRQ wsrq = new AccountDetailListWSRQ(accountDetailListRQ);

                try {
                    result = restTemplate.postForObject(url, wsrq, AccountDetailListWSRS.class);
                    detailResponse = new AccountDetailListResponse(result);
                    detailResponse.setSuccess(true);
                    LOG.info("END: Get Account detail List {} : {} by {}", accountDetailListRQ, result, credentialsDTO);
                } catch (Exception e) {
                    LOG.info("ERROR: Error occur while loading Account detail List url :{}: {}: {} by {}", url, accountDetailListRQ, result, credentialsDTO, e);
                }
            } else {
                LOG.error("ERROR: Get Account detail List  Request Validation failed {} By {}", accountDetailListRQ, credentialsDTO);
            }
        } else {
            LOG.warn("WARN: Get Account detail List service is disabled {} By {}", accountDetailListRQ, credentialsDTO);
        }
        return detailResponse;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public CustomerAddressDetailsResponseDTO getCustomerAddressDetail(AccountDetailListRQ accountDetailListRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get Additional account detail {} by {}", accountDetailListRQ, credentialsDTO);
        boolean isServiceEnable = this.loadCustomerAdditionalAccountDetailEnabled;
        CustomerAddressDetailsResponseDTO customerAddressDetailsResponseDTO = null;
        CustomerAdditionalDetailResponse result = null;
        if (isServiceEnable) {
            if (accountDetailListRQ.validate()) {

                String url = this.loadCustomerAdditionalAccountDetailUrl;
                RestTemplate restTemplate = new RestTemplate();

                AccountDetailListWSRQ wsrq = new AccountDetailListWSRQ(accountDetailListRQ);

                try {
                    result = restTemplate.postForObject(url, wsrq, CustomerAdditionalDetailResponse.class);
                    customerAddressDetailsResponseDTO = new CustomerAddressDetailsResponseDTO(result.getCustomerAddressDetailsResponse());
                    customerAddressDetailsResponseDTO.setSuccess(true);
                    LOG.info("END: Get Additional account detail {} : {} by {}", accountDetailListRQ, result, credentialsDTO);
                } catch (Exception e) {
                    LOG.info("ERROR: Error occur while loading Additional account detail url :{}: {}: {} by {}", url, accountDetailListRQ, result, credentialsDTO, e);
                }

            } else {
                LOG.error("ERROR: Get Additional account detail Request Validation failed {} By {}", accountDetailListRQ, credentialsDTO);
            }
        } else {
            LOG.warn("WARN: Get Additional account detail service is disabled {} By {}", accountDetailListRQ, credentialsDTO);
        }
        return customerAddressDetailsResponseDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public CustomerFacilityDetailResponseDTO getFacilityDetailsByCustomerAccountNumber(FacilityDetailLoadRQ facilityDetailLoadRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get Facility detail by customer account number: {} by {}", facilityDetailLoadRQ, credentialsDTO);
        boolean isServiceEnable = this.facilityDetailByAccountNumberEnabled;
        FacilityDetailResponseList result = null;
        CustomerFacilityDetailResponseDTO customerFacilityDetailResponseDTO = null;
        if (isServiceEnable) {
            if (facilityDetailLoadRQ.getAccountNumber() != null) {
                String url = this.facilityDetailByAccountNumberUrl.replace("{accNo}", facilityDetailLoadRQ.getAccountNumber());
                RestTemplate restTemplate = new RestTemplate();

                try {
                    result = restTemplate.getForObject(url, FacilityDetailResponseList.class);
                    customerFacilityDetailResponseDTO = new CustomerFacilityDetailResponseDTO(result);
                    customerFacilityDetailResponseDTO.setSuccess(true);
                    LOG.info("END: Get Facility detail by customer account number: {} by {}", result, credentialsDTO);
                } catch (Exception e) {
                    LOG.info("ERROR: Error occur while loading facility details url :{}: {}: {} by {}", url, facilityDetailLoadRQ, result, credentialsDTO, e);
                }
            } else {
                LOG.error("ERROR: Get Facility detail by customer account number Request failed {} By {}", facilityDetailLoadRQ, credentialsDTO);
            }
        } else {
            LOG.warn("WARN: Get Facility detail List service is disabled {} by {}", facilityDetailLoadRQ, credentialsDTO);
        }
        return customerFacilityDetailResponseDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public UpmDetailResponse getUpmDetailsByUserIdAndAppCode(UpmDetailLoadRQ upmDetailLoadRQ) {
        LOG.info("START: Get Upm detail by userID and appCode: {}", upmDetailLoadRQ);
        boolean isServiceEnable = this.umpDetailByUserIdAndAppCodeEnabled;
        ObjectMapper objectMapper = new ObjectMapper();
        UpmDetailResponse detailResponse = null;
        UpmDetailLoadRS result = null;
        String responseStr = null;

        if (isServiceEnable) {

            String url = this.umpDetailByUserIdAndAppCodeUrl.replace("{userId}", upmDetailLoadRQ.getUserID())
                    .replace("{appCode}", appCode);
            RestTemplate restTemplate = new RestTemplate();
            try {
                responseStr = restTemplate.getForObject(url, String.class);
                LOG.info("INFO : Response String  {}", responseStr);
                result = objectMapper.readValue(responseStr, UpmDetailLoadRS.class);
                detailResponse = new UpmDetailResponse(result);
                detailResponse.setSuccess(true);
                LOG.info("END : Get Upm detail by userID and appCode url : {} response : {}", url, result);
            } catch (Exception e) {
                LOG.error("ERROR: Response String  {}", responseStr);
                LOG.error("ERROR: Error occur while loading Upm detail by userID and appCode url :{}: {}: {}", url, upmDetailLoadRQ, result, e);
            }

        } else {
            LOG.warn("WARN: Upm detail by userID and appCode service is disabled {} ", upmDetailLoadRQ);
        }
        return detailResponse;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public BranchAuthorityLevelResponseListDTO getUserDetailListFormBranchAuthorityLevel(BranchAuthorityLevelRQ branchAuthorityLevelRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get user list sending Group level and branch code by userID and appCode: {} by {}", branchAuthorityLevelRQ, credentialsDTO);
        boolean isServiceEnable = this.userDetailsBranchAuthorityLevelListEnabled;
        BranchAuthorityLevelResponseListDTO branchAuthorityLevelResponseListDTO = null;
        ResponseEntity<BranchAuthorityLevelResponse[]> result = null;
        if (isServiceEnable) {
            if (branchAuthorityLevelRQ.getAppCode() != null && branchAuthorityLevelRQ.getRoleId() != null && branchAuthorityLevelRQ.getSolId() != null) {

                String url = this.userDetailsBranchAuthorityLevelListUrl.replace("{solId}", branchAuthorityLevelRQ.getSolId())
                        .replace("{roleId}", branchAuthorityLevelRQ.getRoleId()).replace("{appCode}", appCode);
                RestTemplate restTemplate = new RestTemplate();

                LOG.info("INFO : Branch Authority Level url ==> :{}", url);
                try {
                    result = restTemplate.getForEntity(url, BranchAuthorityLevelResponse[].class);
                    LOG.info("INFO : Branch Authority Level Response String  {}", result);
                    BranchAuthorityLevelResponse[] branchAuthorityLevelResponses = result.getBody();
                    branchAuthorityLevelResponseListDTO = new BranchAuthorityLevelResponseListDTO(branchAuthorityLevelResponses);
                    branchAuthorityLevelResponseListDTO.setSuccess(true);
                    LOG.info("END: Get user list Size: {} by {}", branchAuthorityLevelResponseListDTO.getBranchAuthorityLevelResponseDTOList().size(), credentialsDTO);
                    LOG.info("END: Get user list sending Group level and branch code by userID and appCode: {} by {}", branchAuthorityLevelResponses, credentialsDTO);
                } catch (Exception e) {
                    LOG.info("ERROR: Error occur while loading User DetailList Form Branch Authority Level url :{}: {}: {} by {}", url, branchAuthorityLevelRQ, result, credentialsDTO, e);
                }
            } else {
                LOG.info("ERROR: Get user list sending Group level and branch code by userID and appCode Request failed {} by {}", branchAuthorityLevelRQ, credentialsDTO);
            }
        } else {
            LOG.warn("WARN: Get user list sending Group level and branch code service is disabled {} by {}", branchAuthorityLevelRQ, credentialsDTO);
        }
        return branchAuthorityLevelResponseListDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public BranchAuthorityLevelResponseListDTO getAssistants(AssistantRQ assistantRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get Users Assistants: {} by {}", assistantRQ, credentialsDTO);
        boolean isServiceEnable = this.assistantListEnabled;
        BranchAuthorityLevelResponseListDTO branchAuthorityLevelResponseListDTO = null;
        ResponseEntity<BranchAuthorityLevelResponse[]> result = null;
        if (isServiceEnable) {
            if (appCode != null && assistantRQ.getFunctionCode2() != null && assistantRQ.getUpmGroupCode() != null) {
                assistantRQ.setFunctionCode1("-");
                assistantRQ.setFunctionCode3("-");

                String url = this.assistantListUrl
                        .replace("{functioncode1}", assistantRQ.getFunctionCode1())
                        .replace("{functioncode2}", assistantRQ.getFunctionCode2())
                        .replace("{functioncode3}", assistantRQ.getFunctionCode3())
                        .replace("{upmGroupCode}", assistantRQ.getUpmGroupCode())
                        .replace("{appCode}", appCode);
                RestTemplate restTemplate = new RestTemplate();

                LOG.info("INFO : Get Assistants list url ==> :{} : RQ : {}", url, assistantRQ);
                try {
                    result = restTemplate.getForEntity(url, BranchAuthorityLevelResponse[].class);
                    LOG.info("INFO : Get Assistants Response String  {}", result);
                    BranchAuthorityLevelResponse[] branchAuthorityLevelResponses = result.getBody();
                    branchAuthorityLevelResponseListDTO = new BranchAuthorityLevelResponseListDTO(branchAuthorityLevelResponses);
                    branchAuthorityLevelResponseListDTO.setSuccess(true);
                    LOG.info("END: Get Assistants list Size: {} by {}", branchAuthorityLevelResponseListDTO.getBranchAuthorityLevelResponseDTOList().size(), credentialsDTO);
                    LOG.info("END: Get Assistants list : {} by {}", branchAuthorityLevelResponses, credentialsDTO);
                } catch (Exception e) {
                    LOG.info("ERROR: Error occur while loading User Assistants list  url :{}: {}: {} by {}", url, assistantRQ, result, credentialsDTO, e);
                }
            } else {
                LOG.info("ERROR: Get Assistants list Request failed {} by {}", assistantRQ, credentialsDTO);
            }
        } else {
            LOG.warn("WARN: Get Assistants list service is disabled {} by {}", assistantRQ, credentialsDTO);
        }
        return branchAuthorityLevelResponseListDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public UpmDetailResponse prevGetUpmDetailsByAdUserIdAndAppCode(UmpDetailLoadByAdIdRQ upmDetailLoadRQ) throws AppsException {
        LOG.info("START: Get Upm detail by AD userID and appCode: {}", upmDetailLoadRQ);

        ObjectMapper objectMapper = new ObjectMapper();
        UpmDetailResponse detailResponse = null;
        UpmDetailLoadRS result = null;
        String responseStr = null;
        if (this.umpDetailByAdUserIdAndAppCodeEnabled) {
            String url = this.umpDetailByAdUserIdAndAppCodeUrl.replace("{ADUser_ID}", upmDetailLoadRQ.getAdUserID())
                    .replace("{appCode}", appCode);
            RestTemplate restTemplate = new RestTemplate();
            try {
                responseStr = restTemplate.getForObject(url, String.class);
                result = objectMapper.readValue(responseStr, UpmDetailLoadRS.class);
                LOG.info("END : Upm detail by AD userID Response String  {}", responseStr);
            } catch (Exception e) {
                LOG.info("END : Upm detail by AD userID Response String  {}", responseStr);
                LOG.error("ERROR: Error occur while loading Upm detail by AD userID and appCode url :{}: {}: {}", url, upmDetailLoadRQ, result, e);
            }

            if (result == null) {
                LOG.error("ERROR : UPM Details not found for {}", upmDetailLoadRQ);
                throw new AppsException(AppsCommonErrorCode.APPS_UNAUTHORISED);
            }

            detailResponse = new UpmDetailResponse(result);
            detailResponse.setSuccess(true);

            LOG.info("END: Get Upm detail by AD userID and appCode: {}", result);

        } else {
            LOG.warn("WARN: Upm detail by AD userID and appCode service is disabled {}", upmDetailLoadRQ);
        }
        return detailResponse;
    }

    public UpmDetailResponse getUpmDetailsByAdUserIdAndAppCode(UmpDetailLoadByAdIdRQ upmDetailLoadRQ) throws AppsException {
        LOG.info("START: Get Upm detail by AD userID and appCode: {}", upmDetailLoadRQ);

        if (!this.umpDetailByAdUserIdAndAppCodeEnabled) {
            LOG.warn("WARN: Upm detail by AD userID and appCode service is disabled {}", upmDetailLoadRQ);
            return null; // or throw exception if this should be an error case
        }

        ObjectMapper objectMapper = new ObjectMapper();
        UpmDetailResponse detailResponse = null;
        UpmDetailLoadRS result = null;
        String responseStr = null;

        String url = this.umpDetailByAdUserIdAndAppCodeUrl
                .replace("{ADUser_ID}", upmDetailLoadRQ.getAdUserID())
                .replace("{appCode}", appCode);

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);
        requestFactory.setReadTimeout(5000);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        try {
            responseStr = restTemplate.getForObject(url, String.class);
            if (responseStr == null) {
                LOG.error("ERROR: Empty response from UPM detail service for URL: {}", url);
                throw new AppsException(AppsCommonErrorCode.APPS_UNAUTHORISED);
            }

            result = objectMapper.readValue(responseStr, UpmDetailLoadRS.class);
            LOG.info("END : Upm detail by AD userID Response String  {}", responseStr);

            if (result == null) {
                LOG.error("ERROR : UPM Details not found for {}", upmDetailLoadRQ);
                throw new AppsException(AppsCommonErrorCode.APPS_UNAUTHORISED);
            }

            detailResponse = new UpmDetailResponse(result);
            detailResponse.setSuccess(true);

            LOG.info("END: Get Upm detail by AD userID and appCode: {}", result);

        } catch (HttpClientErrorException e) {
            LOG.error("HTTP error while calling UPM detail service URL: {}, Request: {}, ResponseBody: {}", url, upmDetailLoadRQ, e.getResponseBodyAsString(), e);
            throw new AppsException(AppsCommonErrorCode.APPS_UNAUTHORISED, e);
        } catch (JsonProcessingException e) {
            LOG.error("JSON parse error for response: {}, error: {}", responseStr, e);
            throw new AppsException(AppsCommonErrorCode.APPS_UNAUTHORISED, e);
        } catch (ResourceAccessException e) {
            LOG.error("Resource access error (possibly timeout) while calling URL: {}, error: {}", url, e.getMessage(), e);
            throw new AppsException(AppsCommonErrorCode.APPS_UNAUTHORISED, e);
        } catch (Exception e) {
            LOG.error("Unexpected error while getting UPM details: {}", e.getMessage(), e);
            throw new AppsException(AppsCommonErrorCode.APPS_UNAUTHORISED, e);
        }

        return detailResponse;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public UpmDetailResponse getUpmDetailsByAdUserAndAppCode(UmpDetailLoadByAdIdRQ upmDetailLoadRQ) throws AppsException {
        LOG.info("START: Get Upm detail by AD userID and appCode: {}", upmDetailLoadRQ);

        ObjectMapper objectMapper = new ObjectMapper();
        UpmDetailResponse detailResponse = null;
        UpmDetailLoadRS result = null;
        String responseStr = null;
        if (this.umpDetailByAdUserEnable) {
            String url = this.umpDetailByUserIdAndAppCodeUrl.replace("{userId}", upmDetailLoadRQ.getAdUserID())
                    .replace("{appCode}", upmDetailLoadRQ.getAppCode());
            RestTemplate restTemplate = new RestTemplate();
            LOG.info("url:  {}", url);
            try {
                responseStr = restTemplate.getForObject(url, String.class);
                result = objectMapper.readValue(responseStr, UpmDetailLoadRS.class);
                LOG.info("END : Upm detail by AD userID Response String  {}", responseStr);
            } catch (Exception e) {
                LOG.info("END : Upm detail by AD userID Response String  {}", responseStr);
                LOG.error("ERROR: Error occur while loading Upm detail by AD userID and appCode url :{}: {}: {}", url, upmDetailLoadRQ, result, e);
            }

            if (result == null) {
                LOG.error("ERROR : UPM Details not found for {}", upmDetailLoadRQ);
                throw new AppsException(AppsCommonErrorCode.APPS_UNAUTHORISED);
            }

            detailResponse = new UpmDetailResponse(result);
            detailResponse.setSuccess(true);

            LOG.info("END: Get Upm detail by AD userID and appCode: {}", result);

        } else {
            LOG.warn("WARN: Upm detail by AD userID and appCode service is disabled {}", upmDetailLoadRQ);
        }
        return detailResponse;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public CustomerDTO getCustomerDetailFromBank(SearchCustomerRQ searchCustomerRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Get Customer load from Bank: {} by {}", searchCustomerRQ, credentialsDTO.getUserID());
        boolean isServiceEnable = this.loadCustomerEnabled;

        CustomerDTO customerDTO = null;
        if (isServiceEnable) {

            String url = this.loadCustomerUrl;
            String responseStr = null;
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper objectMapper = new ObjectMapper();
            LoadCustomerDetailWSRQ wsrq = new LoadCustomerDetailWSRQ(searchCustomerRQ, credentialsDTO);

            if (!(StringUtils.isNotBlank(wsrq.getCumm()) || StringUtils.isNotBlank(wsrq.getNic())
                    || StringUtils.isNotBlank(wsrq.getAccno()))) {
                LOG.error("ERROR: Invalid Request {}", searchCustomerRQ);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INTEGRATION_INVALID_REQUEST);
            }

            CustomerDetailRS result = null;
            try {
                HttpHeaders headers = new HttpHeaders();
                HttpEntity<LoadCustomerDetailWSRQ> request = new HttpEntity<>(wsrq, headers);
                LOG.info("INFO : Request Body ==> {} : url ==> : {}", new Gson().toJson(request.getBody()), url);

                responseStr = restTemplate.postForObject(url, request, String.class);
                result = objectMapper.readValue(responseStr, CustomerDetailRS.class);
                LOG.info("INFO : Response Str : {}", responseStr);
//                result = restTemplate.postForObject(url, request, CustomerDetailRS.class);
                if (result != null) {
                    customerDTO = new CustomerDTO();
                    this.adaptCustomerDataResponseToCustomerDTO(customerDTO, result.getCustomerDetails());
                }
                LOG.info("END : Customer details : {} by {}", result, credentialsDTO.getUserID());
            } catch (Exception e) {
                LOG.error("ERROR: Response Str : {}", responseStr);
                LOG.error("ERROR: Error occur while loading Customer load from Bank  url :{}: {}: {} by {}", url, wsrq, result, credentialsDTO.getUserID(), e);
            }
        } else {
            LOG.warn("WARN: Customer load from Bank service is disabled {} by {}", searchCustomerRQ, credentialsDTO.getUserID());
        }
        return customerDTO;
    }

    private void adaptCustomerDataResponseToCustomerDTO(CustomerDTO customerDTO, CustomerDetails customerDetails) throws AppsException {
        customerDTO.setCustomerFinancialID(customerDetails.getCustId());
        customerDTO.setStatus(AppsConstants.Status.ACT);
        customerDTO.setEmailAddress(customerDetails.getEmail());
        customerDTO.setTitle(DomainConstants.Title.resolveTitle(customerDetails.getCusttitle()));
        if (StringUtils.isNotBlank(customerDetails.getCustdob())) {
            customerDTO.setDateOfBirth(CalendarUtil.getDefaultFormattedDateTime(CalendarUtil.getFinacelParsedDate(customerDetails.getCustdob())));
        }
        customerDTO.setCustomerName(customerDetails.getCustfullname());

        if (customerDetails.getCustomerAddressDTOList() != null) {
            for (CustomerAddressDTOList addressDTO : customerDetails.getCustomerAddressDTOList()) {
                CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO();
                customerAddressDTO.setStatus(AppsConstants.Status.ACT);
                customerAddressDTO.setCity(addressDTO.getCity());
                customerAddressDTO.setAddress1(addressDTO.getAddress1());
                customerAddressDTO.setAddress2(addressDTO.getAddress2());
                customerAddressDTO.setAddressType(addressDTO.getAddressType());
                customerDTO.getCustomerAddressDTOList().add(customerAddressDTO);
            }
        }
        if (customerDetails.getCustomerTelephoneDTOList() != null) {
            for (CustomerTelephoneDTOList telephoneDTO : customerDetails.getCustomerTelephoneDTOList()) {
                CustomerTelephoneDTO customerTelephoneDTO = new CustomerTelephoneDTO();
                customerTelephoneDTO.setStatus(AppsConstants.Status.ACT);
                customerTelephoneDTO.setDescription(telephoneDTO.getDescription());
                customerTelephoneDTO.setContactNumber(telephoneDTO.getContactNumber());
                customerDTO.getCustomerTelephoneDTOList().add(customerTelephoneDTO);
            }
        }
        if (StringUtils.isNotBlank(customerDetails.getCustnicno())) {
            CustomerIdentificationDTO customerIdentificationDTO = new CustomerIdentificationDTO();
            customerIdentificationDTO.setStatus(AppsConstants.Status.ACT);
            customerIdentificationDTO.setIdentificationNumber(customerDetails.getCustnicno());
            customerIdentificationDTO.setIdentificationType(DomainConstants.CustomerIdentificationType.NIC);
            customerDTO.getCustomerIdentificationDTOList().add(customerIdentificationDTO);

        }

        if (customerDetails.getCustomerBankDetailsDTOList() != null) {
            for (CustomerBankDetailsDTOList bankDetailsDTO : customerDetails.getCustomerBankDetailsDTOList()) {
                CustomerBankDetailsDTO customerBankDetailsDTO = new CustomerBankDetailsDTO();
                customerBankDetailsDTO.setStatus(AppsConstants.Status.ACT);
                customerBankDetailsDTO.setAccountCurrencyCode(bankDetailsDTO.getAcctCrncyCode());
                customerBankDetailsDTO.setBranchCode(bankDetailsDTO.getBranchCode());
                customerBankDetailsDTO.setAccountStatus(bankDetailsDTO.getAccountStatus());
                customerBankDetailsDTO.setBankAccountNumber(bankDetailsDTO.getAccountNo());
                customerBankDetailsDTO.setSchmCode(bankDetailsDTO.getSchmCode());
                customerBankDetailsDTO.setSchmType(bankDetailsDTO.getSchmType());

                if (bankDetailsDTO.getJoiningPartnerList() != null) {
                    for (JoiningPartnerList partner : bankDetailsDTO.getJoiningPartnerList()) {
                        CusBankAccJoiningPartnerDto accJoiningPartnerDto = new CusBankAccJoiningPartnerDto();
                        accJoiningPartnerDto.setCustomerFinacleID(partner.getCifId());
                        accJoiningPartnerDto.setPrimaryCustomer("M".equalsIgnoreCase(partner.getPrimaryCustomer()));
                        customerBankDetailsDTO.getJoiningPartnerList().add(accJoiningPartnerDto);
                    }
                }
                customerDTO.getCustomerBankDetailsDTOList().add(customerBankDetailsDTO);
            }
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public CustomerAccountStatisticResponseDTO getCustomerAccountStatisticResponse(CustomerAccountStatRQ customerAccountStatRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: get Customer Account Statistic Response RQ : {}  by {}", customerAccountStatRQ, credentialsDTO);
        boolean isServiceEnable = this.customerCasStatEnabled;
        CustomerAccountStatisticResponse result = null;
        CustomerAccountStatisticResponseDTO customerAccountStatisticResponseDTO = new CustomerAccountStatisticResponseDTO();
        ;
        if (isServiceEnable) {
            String url = this.customerAccountStatUrl;
            RestTemplate restTemplate = new RestTemplate();

            try {
                LOG.info("INFO : get Customer Account Statistic Request Body ==> {} : url ==> : {}", new Gson().toJson(customerAccountStatRQ), url);
                result = restTemplate.postForObject(url, customerAccountStatRQ, CustomerAccountStatisticResponse.class);
                customerAccountStatisticResponseDTO = new CustomerAccountStatisticResponseDTO(result);
                customerAccountStatisticResponseDTO.setSuccess(true);
                LOG.info("END: get Customer Account Statistic Response {} by {}", result, credentialsDTO);
            } catch (Exception e) {
                LOG.info("ERROR: get Customer Account Statistic Response url :{}: {} by {}", url, result, credentialsDTO, e);
            }
        } else {
            LOG.error("ERROR: get Customer Account Statistic Response By {}", credentialsDTO);
        }
        return customerAccountStatisticResponseDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public CasStatResponseDTO getCasStatResponse(CustomerCasServiceAccountStatRQ customerCasServiceAccountStatRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: get cas stat Response RQ: {} by: {}", customerCasServiceAccountStatRQ, credentialsDTO);
        boolean isServiceEnable = this.customerAccountStatEnabled;
        CasStatResponse resultObj = null;
        CasStatResponseDTO casStatResponseDTO = new CasStatResponseDTO();
        ObjectMapper objectMapper = new ObjectMapper();
        if (isServiceEnable) {
            String url = this.customerCasStatUrl;
            RestTemplate restTemplate = new RestTemplate();
            String resultStr = null;

            try {
                LOG.info("INFO : get cas stat Request Body ==> {} : url ==> : {}", new Gson().toJson(customerCasServiceAccountStatRQ), url);
                resultStr = restTemplate.postForObject(url, customerCasServiceAccountStatRQ, String.class);
                LOG.info("INFO : get cas stat Response {} by {}", resultStr, credentialsDTO);
                resultObj = objectMapper.readValue(resultStr, CasStatResponse.class);
                casStatResponseDTO = new CasStatResponseDTO(resultObj);
                casStatResponseDTO.setSuccess(true);
            } catch (Exception e) {
                LOG.info("ERROR: get cas stat Response url : {} : {} : {} by {}", url, resultStr, resultObj, credentialsDTO, e);
            }
        } else {
            LOG.error("ERROR: get cas stat Response By {}", credentialsDTO);
        }
        return casStatResponseDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public DepositDetailResponseDTO getDepositDetailsResponse(CustomerAccountStatRQ customerAccountStatRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: get deposit details Response RQ : {} by: {}", customerAccountStatRQ, credentialsDTO);
        boolean isServiceEnable = this.customerDepositDetailsEnabled;
        DepositDetailResponse result = null;
        DepositDetailResponseDTO depositDetailResponseDTO = null;
        if (isServiceEnable) {
            String url = this.customerDepositDetailsUrl;
            RestTemplate restTemplate = new RestTemplate();

            try {
                LOG.info("INFO : get deposit details Request Body ==> {} : url ==> : {}", new Gson().toJson(customerAccountStatRQ), url);
                result = restTemplate.postForObject(url, customerAccountStatRQ, DepositDetailResponse.class);
                LOG.info("INFO : get deposit details Response {} by {}", result, credentialsDTO);
                depositDetailResponseDTO = new DepositDetailResponseDTO(result);
                depositDetailResponseDTO.setSuccess(true);
            } catch (Exception e) {
                LOG.info("ERROR: get deposit details Response url :{}: {} by {}", url, result, credentialsDTO, e);
            }
        } else {
            LOG.error("ERROR: get deposit details Response By {}", credentialsDTO);
        }
        return depositDetailResponseDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public AdvancePortfolioResponseDTO getCustomerFacilityDetails(CustomerCasServiceAccountStatRQ customerCasServiceAccountStatRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: get customer facility details Response RQ : {} by: {}", customerCasServiceAccountStatRQ, credentialsDTO);
        boolean isServiceEnable = this.customerFacilityDetailsEnabled;
        AdvancedPortfolioResponse result = null;
        AdvancePortfolioResponseDTO advancePortfolioResponseDTO = null;
        if (isServiceEnable) {
            String url = this.customerFacilityDetailsUrl;
            RestTemplate restTemplate = new RestTemplate();

            try {
                LOG.info("INFO : get customer facility details Request Body ==> {} : url ==> : {}", new Gson().toJson(customerCasServiceAccountStatRQ), url);
                result = restTemplate.postForObject(url, customerCasServiceAccountStatRQ, AdvancedPortfolioResponse.class);
                LOG.info("INFO : get customer facility details Response {} by {}", result, credentialsDTO);
                advancePortfolioResponseDTO = new AdvancePortfolioResponseDTO(result);
                advancePortfolioResponseDTO.setSuccess(true);
            } catch (Exception e) {
                LOG.info("ERROR: get customer facility details Response url :{}: {} by {}", url, result, credentialsDTO, e);
            }
        } else {
            LOG.error("ERROR: get customer facility details Response By {}", credentialsDTO);
        }
        return advancePortfolioResponseDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public BranchLoadResponseListDTO getBranchList(CredentialsDTO credentialsDTO) {
        LOG.info("START: Get Branch list by {}", credentialsDTO);
        boolean isServiceEnable = this.loadBranchListEnabled;
        BranchLoadResponseList result = null;
        BranchLoadResponseListDTO branchLoadResponseListDTO = null;
        if (isServiceEnable) {

            String url = this.loadBranchListUrl;
            RestTemplate restTemplate = new RestTemplate();

            try {
                result = restTemplate.getForObject(url, BranchLoadResponseList.class);
                LOG.info("INFO : Get Branch list url : {} : {} by {}", url, result, credentialsDTO);
                branchLoadResponseListDTO = new BranchLoadResponseListDTO(result);
                branchLoadResponseListDTO.setSuccess(true);
            } catch (Exception e) {
                LOG.info("ERROR: Error occur while loading Branch list url :{}: {} by {}", url, result, credentialsDTO, e);
            }
        } else {
            LOG.error("ERROR: Get Branch list service is disabled By {}", credentialsDTO);

        }
        return branchLoadResponseListDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public CribRetailReportResponse getCribRetailReport(CribRetailReportRQ cribRetailReportRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Get Crib Retail Report RQ : {}  by : {}", cribRetailReportRQ, credentialsDTO);
        boolean isServiceEnable = this.retailCribReportEnabled;
        CribRetailReportResponse resultObj = null;
        String responseStr = null;
        String formattedString = null;
        ObjectMapper objectMapper = new ObjectMapper();

        LoadConsumerComprehensiveRQ loadConsumerComprehensiveRQ = new LoadConsumerComprehensiveRQ();
        loadConsumerComprehensiveRQ.setSearch(new ConsumerComprehensiveDTO());
        loadConsumerComprehensiveRQ.setParam(new ComprehensiveParamDTO());

        loadConsumerComprehensiveRQ.getParam().setReportId(this.consumerReportID);
        loadConsumerComprehensiveRQ.getParam().setSubjectType(this.consumerSubjectType);
        loadConsumerComprehensiveRQ.getParam().setResponseType(this.consumerResponseType);
        loadConsumerComprehensiveRQ.getParam().setReasonCode(this.consumerReasonCode);
        loadConsumerComprehensiveRQ.getSearch().setCitizenship(this.consumerCitizenship);
        loadConsumerComprehensiveRQ.getSearch().setName(cribRetailReportRQ.getCustomerName());
        loadConsumerComprehensiveRQ.getSearch().setGender(cribRetailReportRQ.getCustomerGender());
        loadConsumerComprehensiveRQ.getSearch().setNIC(cribRetailReportRQ.getIdentificationNumber());
        loadConsumerComprehensiveRQ.getSearch().setBranchId("");

        if (isServiceEnable) {

            String url = this.retailCribReportUrl;
            RestTemplate restTemplate = new RestTemplate();
            LOG.info("Request ==>  : {} : url :{}", new Gson().toJson(loadConsumerComprehensiveRQ), this.retailCribReportUrl);
            try {
                responseStr = restTemplate.postForObject(url, loadConsumerComprehensiveRQ, String.class);
                LOG.info("INFO : Crib Retail Report Request ==>  : {} : url :{}", new Gson().toJson(loadConsumerComprehensiveRQ), this.retailCribReportUrl);
                LOG.info("INFO : Get Retail Report Response String : {}", responseStr);
                if (responseStr != null) {
                    formattedString = responseStr.replaceAll("\\[\\{\\}]", "null");
                    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
                    resultObj = objectMapper.readValue(formattedString, CribRetailReportResponse.class);
                }
                this.customerCribResponseService.saveCribResponse(formattedString, cribRetailReportRQ, null, credentialsDTO);
                LOG.info("END: Get Formatted String : {}  ", formattedString);
                LOG.info("END: Get Crib Retail Report : {} by {}", resultObj, credentialsDTO);
            } catch (Exception e) {
                LOG.error("ERROR: Response String : {}", responseStr);
                LOG.error("ERROR: Formatted String : {}", formattedString);
                LOG.error("ERROR: Deserialization Failed Report url : {} : by {}", url, credentialsDTO, e);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CRIB_REPORT_INVALID_RESPONSE);
            }
        } else {
            LOG.error("ERROR: Get Crib Retail Report  is disabled By {}", credentialsDTO);
        }
        return resultObj;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public CribCorporateReportResponse getCorporateCribReport(CribCorporateRQ cribCorporateRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Get Crib Corporate Report RQ : {}  by : {}", cribCorporateRQ, credentialsDTO);
        boolean isServiceEnable = this.corporateCribReportEnabled;
        CribCorporateReportResponse resultObj = null;
        String responseStr = null;
        String formattedString = null;
        ObjectMapper objectMapper = new ObjectMapper();

        LoadCorporateComprehensiveRQ loadCorporateComprehensiveRQ = new LoadCorporateComprehensiveRQ();
        loadCorporateComprehensiveRQ.setSearch(new CorporateComprehensiveDTO());
        loadCorporateComprehensiveRQ.setParam(new ComprehensiveParamDTO());

        loadCorporateComprehensiveRQ.getParam().setReportId(this.corporateReportID);
        loadCorporateComprehensiveRQ.getParam().setSubjectType(this.corporateSubjectType);
        loadCorporateComprehensiveRQ.getParam().setResponseType(this.corporateResponseType);
        loadCorporateComprehensiveRQ.getParam().setReasonCode(this.corporateReasonCode);
        loadCorporateComprehensiveRQ.getSearch().setREGNo(cribCorporateRQ.getIdentificationNumber());
        loadCorporateComprehensiveRQ.getSearch().setName(cribCorporateRQ.getCustomerName());

        LOG.info("INFO : Crib Corporate Report Request ==>  : {} : url : {} ", new Gson().toJson(loadCorporateComprehensiveRQ), this.corporateCribReportUrl);

        if (isServiceEnable) {
            String url = this.corporateCribReportUrl;
            RestTemplate restTemplate = new RestTemplate();
            try {
                responseStr = restTemplate.postForObject(url, loadCorporateComprehensiveRQ, String.class);
                LOG.info("INFO : Get Corporate Report Response String : {}", responseStr);
                if (responseStr != null) {
                    formattedString = responseStr.replaceAll("\\[\\{\\}]", "null");
                    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
                    resultObj = objectMapper.readValue(formattedString, CribCorporateReportResponse.class);
                }

                this.customerCribResponseService.saveCribResponse(formattedString, null, cribCorporateRQ, credentialsDTO);
                LOG.info("END: Get Formatted String : {}  ", formattedString);
                LOG.info("END: Get Crib Corporate Report : {} by {}", resultObj, credentialsDTO);
            } catch (Exception e) {
                LOG.error("ERROR: Response String : {}", responseStr);
                LOG.error("ERROR: Formatted String : {}", formattedString);
                LOG.error("ERROR: Deserialization Failed Report url : {} : by {}", url, credentialsDTO, e);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CRIB_REPORT_INVALID_RESPONSE);
            }
        } else {
            LOG.error("ERROR: Get Crib Corporate Report  is disabled By {}", credentialsDTO);
        }
        return resultObj;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public KalyptoRS getKalyptoPageDetail(LoadKalyptoDataRQ loadKalyptoDataRQ, CredentialsDTO credentialsDTO) {

        LOG.info("START: Load Kalypto detail: {} by {}", loadKalyptoDataRQ, credentialsDTO);
        boolean isServiceEnable = this.kalypotoEnabled;
        KalyptoRS kalyptoRS = null;
        KalyptoResponseDTO kalyptoResponseDTO = null;

        String jsonContentStr = "";
        kalyptoRS = new KalyptoRS();
        if (isServiceEnable) {

            String api = kalyptoUrl + "?UniqueIdentifier=" + loadKalyptoDataRQ.getUniqueIdentifier();
            LOG.info("Load Kalypto Detail API ==> {}", api);
            try {
                RestTemplate restTemplate = new RestTemplate();
                String resultStr = restTemplate.getForObject(api, String.class);
                LOG.info("INFO : Load Kalypto Detail Response String : {}", resultStr);
                if (resultStr != null) {
                    jsonContentStr = JAXB.unmarshal(new StringReader(resultStr), String.class);
                }
                if (jsonContentStr.contains("Error: Evaluation is not exists for selected Borrower")) {
                    kalyptoRS.setSuccessfullyParse(false);
                    kalyptoRS.setMessage(jsonContentStr);
                } else {
                    ObjectMapper om = new ObjectMapper();
                    KalyptoResponse kalyptoResponse = null;
                    try {
                        kalyptoResponse = om.readValue(jsonContentStr, KalyptoResponse.class);
                        //kalyptoRS.setKalyptoResponse(kalyptoResponse);
                        kalyptoResponseDTO = new KalyptoResponseDTO(kalyptoResponse);
                        kalyptoRS.setKalyptoResponseDTO(kalyptoResponseDTO);
                    } catch (IOException e) {
                        LOG.error("ERROR while parsing json value {}", jsonContentStr, e);
                    }
                    kalyptoRS.setSuccessfullyParse(true);
                    kalyptoRS.setString(jsonContentStr);
                }
                LOG.info("END: Load Kalypto detail: {} by {}", loadKalyptoDataRQ, credentialsDTO);
            } catch (Exception e) {
                kalyptoRS.setSuccessfullyParse(false);
                kalyptoRS.setMessage("ERROR occur while loading Kalypto details");
                LOG.error("ERROR: Load Kalypto detail failed : {} by {}", loadKalyptoDataRQ, credentialsDTO, e);
            }

        } else {
            LOG.error("ERROR: Get Kalypto detail service is disabled By {}", credentialsDTO);
        }
        return kalyptoRS;
    }

    @Async
    @Transactional(propagation = Propagation.SUPPORTS)
    public String pushCreditScheduleToFinacle(CreditScheduleInformationDTO creditScheduleInformationDTO, CredentialsDTO credentialsDTO) {
        LOG.info("START: Push Credit Schedule to Finacle : by {}", credentialsDTO);
        boolean isServiceEnable = this.creditScheduleEnabled;
        String responseStr = null;
        String formattedString = null;
        ObjectMapper objectMapper = new ObjectMapper();
        ScheduleResponse resultObj = null;
        if (isServiceEnable) {
            String url = this.creditScheduleUrl;
            RestTemplate restTemplate = new RestTemplate();

            try {
                for (CreditScheduleESBDTO creditScheduleESBDTO : creditScheduleInformationDTO.getCreditScheduleESBCatalog()) {
                    LOG.info("INFO : Push Credit Schedule to Finacle Request Body ==> {} : url ==> : {}", new Gson().toJson(creditScheduleESBDTO.getCreditScheduleESBRQ()), url);
                    responseStr = restTemplate.postForObject(url, creditScheduleESBDTO.getCreditScheduleESBRQ(), String.class);
                    LOG.info("END: Push Credit Schedule to Finacle Response {} by {}", responseStr, credentialsDTO);

                    if (responseStr != null) {
                        formattedString = responseStr.replaceAll("\\[\\{\\}]", "null");
                        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
                        resultObj = objectMapper.readValue(formattedString, ScheduleResponse.class);
                    }

                    this.calculatorService.saveCreditScheduleResponse(resultObj, creditScheduleESBDTO, responseStr, credentialsDTO);
                    LOG.info("END: GetFormatted String : {}  ", formattedString);
                    LOG.info("END: Push Credit Schedule to Finacle : {} by {}", resultObj, credentialsDTO);
                }

            } catch (Exception e) {
                LOG.info("ERROR: Push Credit Schedule to Finacle Response url :{}: {} by {}", url, responseStr, credentialsDTO, e);
            }
        } else {
            LOG.error("ERROR: Push Credit Schedule to Finacle Response By {}", credentialsDTO);
        }
        return responseStr;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ExecuteFinacleScriptCustomDataResponseDTO getTransactionDetailsForCashFlow(CustomerTranDetCashFlowRQ customerTranDetCashFlowRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: get Finacle customer cash flow details Response RQ : {} by: {}", customerTranDetCashFlowRQ, credentialsDTO);
        boolean isServiceEnable = this.tranDetForCashFlowEnabled;
        ExecuteFinacleScriptCustomDataResponse result = null;
        ExecuteFinacleScriptCustomDataResponseDTO executeFinacleScriptCustomDataResponseDTO = null;
        if (isServiceEnable) {
            String url = this.tranDetForCashFlowUrl;
            RestTemplate restTemplate = new RestTemplate();

            try {
                LOG.info("INFO : get Finacle customer cash flow details Request Body ==> {} : url ==> : {}", new Gson().toJson(customerTranDetCashFlowRQ), url);
                result = restTemplate.postForObject(url, customerTranDetCashFlowRQ, ExecuteFinacleScriptCustomDataResponse.class);
                LOG.info("INFO : get Finacle customer cash flow details Response {} by {}", result, credentialsDTO);
                executeFinacleScriptCustomDataResponseDTO = new ExecuteFinacleScriptCustomDataResponseDTO(result);
                executeFinacleScriptCustomDataResponseDTO.setSuccess(true);
            } catch (Exception e) {
                LOG.info("ERROR: get Finacle customer cash flow details Response url :{}: {} by {}", url, result, credentialsDTO, e);
            }
        } else {
            LOG.error("ERROR: get Finacle customer cash flow details Response By {}", credentialsDTO);
        }
        return executeFinacleScriptCustomDataResponseDTO;
    }

    public String getRiskDivCode() {
        return riskDivCode;
    }

    public void setRiskDivCode(String riskDivCode) {
        this.riskDivCode = riskDivCode;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public CovenantListDTO getCovenantList(CustomerCovenantRQ customerCovenantRQ, CredentialsDTO credentialsDTO) {
        boolean isServiceEnable = this.customerCovenantEnable;
        CovenantListDTO result = null;
        CovenantListDTO customerCovenantResponseDTO = null;
        if (isServiceEnable) {
            String url = this.customerCovenant;

            LOG.info("INFO: url to the finacle {}", url);
            RestTemplate restTemplate = new RestTemplate();

            WebClient webClient = WebClient.create();

            try {

                String response = webClient.post()
                        .uri(url)
                        .bodyValue(customerCovenantRQ)
                        .retrieve()
                        .bodyToMono(String.class)
                        .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsDefault))
                        .onErrorReturn("Timeout occurred or service failed for get covenant list")  // optional fallback
                        .block();

                if(response != null || !response.isEmpty()) {
                    LOG.info("INFO: getCovenantList | IntegrationService | get the covenant list response from the finacle successfully");
                }

                Gson gson = new Gson();

                customerCovenantResponseDTO = gson.fromJson(response, CovenantListDTO.class);

                LOG.info("INFO: getCovenantList | IntegrationService | get customer covenant list response {}", customerCovenantResponseDTO);

            } catch (Exception e) {
                LOG.info("ERROR: getCovenantList | IntegrationService | get customer covenant details Response url :{}: by {}", url, credentialsDTO, e.getMessage());
            }
        } else {
            LOG.error("ERROR: getCovenantList | IntegrationService | get customer covenant details Response By {}", credentialsDTO);
        }

        LOG.info("END: getCovenantList | IntegrationService | get customer covenant details Response RQ : {} by: {}", customerCovenantRQ, credentialsDTO);
        return customerCovenantResponseDTO;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public CovenantDetailResDTO getCovenantsUpdated(CovenantDetailResDTO covenantResponseDTO, CredentialsDTO credentialsDTO) {
        boolean isServiceEnable = this.customerCovenantEnable;
        CovenantDetailResDTO result = null;
        CovenantDetailResDTO customerCovenantResponseDTO = null;
        if (isServiceEnable) {
            String url = this.addCustomerCovenantUrl;
            LOG.info("INFO: url to the finacle {}", url);
            LOG.info("INFO: add covenants to the finacle Request Body ==> {}", covenantResponseDTO);
            WebClient webClient = WebClient.create();

            try {

                String response = webClient.post()
                        .uri(url)
                        .bodyValue(covenantResponseDTO)
                        .retrieve()
                        .bodyToMono(String.class)
                        .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsDefault))
                        .onErrorReturn("Timeout occurred or service failed for add covenants to the finacle")  // optional fallback
                        .block();

                LOG.info("INFO: getCovenantsUpdated | IntegrationService | response to the finacle {}", response);

                Gson gson = new Gson();

                customerCovenantResponseDTO = gson.fromJson(response, CovenantDetailResDTO.class);
                customerCovenantResponseDTO.setRequestUUID(covenantResponseDTO.getRequestUUID());
                customerCovenantResponseDTO.setCustId(covenantResponseDTO.getCustId());
                customerCovenantResponseDTO.setCasReference(covenantResponseDTO.getCasReference());
                customerCovenantResponseDTO.setREL(covenantResponseDTO.getREL());

                LOG.info("INFO: getCovenantsUpdated | IntegrationService | get customer covenant response {}", customerCovenantResponseDTO);

            } catch (Exception e) {
                LOG.info("ERROR: getCovenantsUpdated | IntegrationService | get customer covenant details Response url :{}: by {}", url, credentialsDTO, e);
            }
        } else {
            LOG.error("ERROR: getCovenantsUpdated | IntegrationService | get customer covenant details Response By {}", credentialsDTO);
        }

        LOG.info("END: getCovenantsUpdated | IntegrationService | get customer covenant details Response RQ : {} by: {}", covenantResponseDTO, credentialsDTO);
        return customerCovenantResponseDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ACAEAasRecordsDTO getACAEAasRecordService(ACAEAasRecordRQ acaeAasRecordRQ, CredentialsDTO credentialsDTO) {
        boolean isAasRecordsEnable = this.acaeAasRecordsEnable;
        ACAEAasRecordsDTO acaeAasRecordsDTO = null;
        try {
            if (isAasRecordsEnable) {
                String url = this.acaeAasRecordsUrl;
                WebClient webClient = WebClient.create();

                String response = webClient.post()
                        .uri(url)
                        .bodyValue(acaeAasRecordRQ)
                        .retrieve()
                        .bodyToMono(String.class)
                        .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsDefault))
                        .onErrorReturn("Timeout occurred or service failed for getACAEAasRecordService")  // optional fallback
                        .block();

                LOG.info("INFO: getACAEAasRecordService | IntegrationService | response to the finacle {}", response);
                Gson gson = new Gson();

                acaeAasRecordsDTO = gson.fromJson(response, ACAEAasRecordsDTO.class);
                LOG.info("INFO: getACAEAasRecordService | IntegrationService | response to the finacle {}", acaeAasRecordsDTO);
            } else {
                LOG.error("ERROR: getACAEAasRecordService | IntegrationService | get customer covenant details Response By {}", credentialsDTO);
            }
        } catch (Exception e) {
            LOG.error("ERROR : getACAEAasRecordService | IntegrationService | {}", e);
        }
        return acaeAasRecordsDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ACAEStatByAcctDTO getACAEStatByAcctService(ACAEStatByAcctRQ acaeStatByAcctRQ, CredentialsDTO credentialsDTO) {
        boolean isacaeStatByAcctEnable = this.acaeStatByAcctEnable;
        ACAEStatByAcctDTO acaeStatByAcctDTO = null;
        try {
            if (isacaeStatByAcctEnable) {
                String url = this.acaeStatByAcctUrl;
                WebClient webClient = WebClient.create();

                String response = webClient.post()
                        .uri(url)
                        .bodyValue(acaeStatByAcctRQ)
                        .retrieve()
                        .bodyToMono(String.class)
                        .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsDefault))
                        .onErrorReturn("Timeout occurred or service failed in getACAEStatByAcctService")  // optional fallback
                        .block();

                Gson gson = new Gson();

                acaeStatByAcctDTO = gson.fromJson(response, ACAEStatByAcctDTO.class);
            } else {
                LOG.error("ERROR: getACAEStatByAcctService | IntegrationService | get customer covenant details Response By {}", credentialsDTO);
            }
        } catch (Exception e) {
            LOG.error("ERROR getACAEStatByAcctService | IntegrationService | {} : ", e);
        }
        return acaeStatByAcctDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ACAEAcctBalByAcctDTO getACAEAcctBalByAcctService(ACAEAcctBalByAcctRQ acaeAcctBalByAcctRQ, CredentialsDTO credentialsDTO) {
        boolean isAcaeAcctBalByAcctsEnable = this.acaeAcctBalByAcctsEnable;
        ACAEAcctBalByAcctDTO acaeAcctBalByAcctDTO = null;
        try {
            if (isAcaeAcctBalByAcctsEnable) {
                String url = this.acaeAcctBalByAcctsUrl;
                WebClient webClient = WebClient.create();

                String response = webClient.post()
                        .uri(url)
                        .bodyValue(acaeAcctBalByAcctRQ)
                        .retrieve()
                        .bodyToMono(String.class)
                        .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsDefault))
                        .onErrorReturn("Timeout occurred or service failed in getACAEAcctBalByAcctService")  // optional fallback
                        .block();

                Gson gson = new Gson();

                acaeAcctBalByAcctDTO = gson.fromJson(response, ACAEAcctBalByAcctDTO.class);
            } else {
                LOG.error("ERROR: getACAEAcctBalByAcctService | IntegrationService | get customer covenant details Response By {}", credentialsDTO);
            }
        } catch (Exception e) {
            LOG.error("ERROR : getACAEAcctBalByAcctService | IntegrationService | {} ", e);
        }
        return acaeAcctBalByAcctDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ACAEAcctClassificationDTO getACAEAcctClassificationDataService(ACAEAcctClassificationDataRQ acaeAcctClassificationDataRQ, CredentialsDTO credentialsDTO) {
        boolean isAcctClassificationDataEnable = this.acaeAcctClassificationDataEnable;
        ACAEAcctClassificationDTO acaeAcctClassificationDTO = null;
        try {
            if (isAcctClassificationDataEnable) {
                String url = this.acaeAcctClassificationDataUrl;
                WebClient webClient = WebClient.create();

                String response = webClient.post()
                        .uri(url)
                        .bodyValue(acaeAcctClassificationDataRQ)
                        .retrieve()
                        .bodyToMono(String.class)
                        .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsDefault))
                        .onErrorReturn("Timeout occurred or service failed in getACAEAcctClassificationDataService")  // optional fallback
                        .block();

                Gson gson = new Gson();

                acaeAcctClassificationDTO = gson.fromJson(response, ACAEAcctClassificationDTO.class);
            } else {
                LOG.error("ERROR: getACAEAcctClassificationDataService | IntegrationService | get customer covenant details Response By {}", credentialsDTO);
            }
        } catch (Exception e) {
            LOG.error("ERROR : getACAEAcctClassificationDataService | IntegrationService | {} ", e);
        }
        return acaeAcctClassificationDTO;
    }

    public String getNoOfMonthsPerYear() {
        return noOfMonthsPerYear;
    }

    public void setNoOfMonthsPerYear(String noOfMonthsPerYear) {
        this.noOfMonthsPerYear = noOfMonthsPerYear;
    }

    public void setSecurityDocumentSubmitDiv(String securityDocumentSubmitDiv) {
        this.securityDocumentSubmitDiv = securityDocumentSubmitDiv;
    }

    public String getSecurityDocumentSubmitWorkClass() {
        return securityDocumentSubmitWorkClass;
    }

    public void setSecurityDocumentSubmitWorkClass(String securityDocumentSubmitWorkClass) {
        this.securityDocumentSubmitWorkClass = securityDocumentSubmitWorkClass;
    }

    public String getBccEntererWorkClass() {
        return bccEntererWorkClass;
    }

    public void setBccEntererWorkClass(String bccEntererWorkClass) {
        this.bccEntererWorkClass = bccEntererWorkClass;
    }

    public String getBccAuthorizerWorkClass() {
        return bccAuthorizerWorkClass;
    }

    public void setBccAuthorizerWorkClass(String bccAuthorizerWorkClass) {
        this.bccAuthorizerWorkClass = bccAuthorizerWorkClass;
    }

    public String getBccInquirerWorkClass() {
        return bccInquirerWorkClass;
    }

    public void setBccInquirerWorkClass(String bccInquirerWorkClass) {
        this.bccInquirerWorkClass = bccInquirerWorkClass;
    }


    public String getSecurityDocumentSubmitDiv() {
        return securityDocumentSubmitDiv;
    }


    public List<DADesignationCodeDTO> getAllDesignationCodeDetails() {

        LOG.info("START: Get All Designation Code Details");

        boolean dasDesignationEnable = this.dasDesignationEnable;
        String url = this.dasDesignationURL;
        AccountDetailResponse detailResponse = null;
        List<DADesignationCodeDTO> resultSet = null;

        if (dasDesignationEnable) {
            RestTemplate restTemplate = new RestTemplate();
            try {
                ResponseEntity<List<DADesignationCodeDTO>> responseEntity = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<DADesignationCodeDTO>>() {
                        }
                );

                resultSet = responseEntity.getBody();

                LOG.info("END: Get Account detail by customer account number: {}", resultSet);

            } catch (Exception e) {
                LOG.info("ERROR: Error occur while loading Account detail url :{}: {}: {} by {}", e);
            }
        } else {
            LOG.warn("WARN: Account detail loading by account number service is disabled ");
        }
        return resultSet;
    }

    public String getEmailByUserAD(String userAD) {

        ObjectMapper objectMapper = new ObjectMapper();
        UpmDetailResponse detailResponse = null;
        UpmDetailLoadRS result = null;
        String responseStr = null;
        String email = "";

        if (this.umpDetailByAdUserIdAndAppCodeEnabled) {
            String url = this.umpDetailByAdUserIdAndAppCodeUrl.replace("{ADUser_ID}", userAD)
                    .replace("{appCode}", appCode);
            RestTemplate restTemplate = new RestTemplate();
            try {
                responseStr = restTemplate.getForObject(url, String.class);
                result = objectMapper.readValue(responseStr, UpmDetailLoadRS.class);
            } catch (Exception e) {
                LOG.info("ERROR: Get Upm detail by AD ", e);
            }

            detailResponse = new UpmDetailResponse(result);
            email = detailResponse.getEmail() != null ? detailResponse.getEmail() : "";
            detailResponse.setSuccess(true);

            LOG.info("END: Get Upm detail by AD userID and appCode: {}", result);

        } else {
            LOG.warn("WARN: Upm detail by AD userID and appCode service is disabled {}", userAD);
        }
        return email;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<TranDetailsResponseDTO> getCoveringApprovalTranDetails(LoadTranDataRQ loadTranDataRQ, CredentialsDTO credentialsDTO) {

        LOG.info("START: get tran details Response RQ : {} by: {}", loadTranDataRQ, credentialsDTO);

        boolean isServiceEnable = this.coveringApprovalTranDetailsEnabled;
        TranDetailsResponseDTO result = null;
        List<TranDetailsResponseDTO> tranDetailsResponseDTO = null;
        if (isServiceEnable) {

            String url = this.coveringApprovalTranDetailsUrl;
            LOG.info("INFO: url to the finacle {}", url);
            WebClient webClient = WebClient.create();

            try {
                String response = webClient.post()
                        .uri(url)
                        .bodyValue(loadTranDataRQ)
                        .retrieve()
                        .bodyToMono(String.class)
                        .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsDefault))
                        .onErrorReturn("Timeout occurred or service failed in getCoveringApprovalTranDetails")  // optional fallback
                        .block();

                LOG.info("INFO: getCoveringApprovalTranDetails | IntegrationService | get the tran details response from the finacle {}", response);

                Gson gson = new Gson();

                tranDetailsResponseDTO = Collections.singletonList(gson.fromJson(response, TranDetailsResponseDTO.class));
                LOG.info("INFO: getCoveringApprovalTranDetails | IntegrationService |  get the tran details response {} by {}", tranDetailsResponseDTO, credentialsDTO);
            } catch (Exception e) {
                LOG.info("ERROR: getCoveringApprovalTranDetails | IntegrationService |  get the tran details response url :{}: by{}", url, credentialsDTO, e);
            }
        } else {
            LOG.error("ERROR: getCoveringApprovalTranDetails | IntegrationService |  get covering approval tran details Response By {}", credentialsDTO);
        }

        LOG.error(" END: getCoveringApprovalTranDetails | IntegrationService | tranDetailsResponse {}", tranDetailsResponseDTO);
        return tranDetailsResponseDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public CovAppCustomerDTO getAccountBalanceDetailsByRefNoAndAccountNo(LoadBankAccountDetailsRQ searchCustomerRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: get tran details Response RQ : {} by: {}", searchCustomerRQ, credentialsDTO);

        boolean isServiceEnable = this.acaeStatusDetailsEnable;
        CovAppCustomerDTO result = null;
        CovAppCustomerDTO customerDTO = null;
        if (isServiceEnable) {
            String url = this.acaeStatusDetailsURL;
            LOG.info("INFO: url to the finacle {}", url);
            WebClient webClient = WebClient.create();

            try {
                String response = webClient.post()
                        .uri(url)
                        .bodyValue(searchCustomerRQ)
                        .retrieve()
                        .bodyToMono(String.class)
                        .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsDefault))
                        .onErrorReturn("Timeout occurred or service failed in getAccountBalanceDetailsByRefNoAndAccountNo")  // optional fallback
                        .block();

                LOG.info("INFO: getAccountBalanceDetailsByRefNoAndAccountNo | IntegrationService | get the account details response from the finacle {}", response);

                Gson gson = new Gson();

                customerDTO = gson.fromJson(response, CovAppCustomerDTO.class);
                LOG.info("INFO: getAccountBalanceDetailsByRefNoAndAccountNo | IntegrationService | get the account details response {} by {}", customerDTO, credentialsDTO);
            } catch (Exception e) {
                LOG.info("ERROR: getAccountBalanceDetailsByRefNoAndAccountNo | IntegrationService | get the account details response url :{}: by{}", url, credentialsDTO, e);
            }
        } else {
            LOG.error("ERROR: getAccountBalanceDetailsByRefNoAndAccountNo | IntegrationService | get account details Response By {}", credentialsDTO);
        }

        LOG.error("Response: getAccountBalanceDetailsByRefNoAndAccountNo | IntegrationService | {}", customerDTO);
        return customerDTO;
    }

    
    @Transactional(propagation = Propagation.SUPPORTS)
    public FinacleExOutLimitsRS getFinacleExOutLimitsFromFinacle(String cusId, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get Finacle exout limits from API in Integration Service{} by {}", cusId, credentialsDTO);
        boolean isServiceEnable = this.finacleExOutLimitsEnable;
        String uniqueRQId= UUID.randomUUID().toString();
        FinacleExOutLimitsRQ cusIdRQ= new FinacleExOutLimitsRQ();
        cusIdRQ.setCusId(cusId);
        cusIdRQ.setRequestUUID(uniqueRQId);
        FinacleExOutLimitsRS response = null;
        if (isServiceEnable) {


                String url = this.finacleExOutLimitsUrl;
                RestTemplate restTemplate = new RestTemplate();

                try {

                   String result = restTemplate.postForObject(url, cusIdRQ, String.class);

                    Gson gson = new Gson();

                    response = gson.fromJson(result, FinacleExOutLimitsRS.class );


                    LOG.info("END: Get Finacle exout limits from API in Integration Service {} : {} by {}", cusIdRQ, result, credentialsDTO);
                } catch (Exception e) {
                    LOG.info("ERROR: Error occur while loading Get Finacle exout limits from API in Integration Service :{}: {}: {} by {}", url, cusIdRQ, response, credentialsDTO, e);
                }

        } else {
            LOG.warn("WARN: Get Finacle exout limits from API in Integration service is disabled {} By {}", cusIdRQ, credentialsDTO);
        }
        LOG.info("END: Get Finacle exout limits from API in Integration Service {} By {}", cusIdRQ, credentialsDTO);
        return response;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public IsWatchlistedRS isWatchlisted(String cusId, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get watch list status in integeration service {} by {}", cusId, credentialsDTO);
        boolean isServiceEnable = this.finacleIsWatchlistedEnable;
        String uniqueRQId="CAS_"+UUID.randomUUID().toString();
        IsWatchlistedRQ cusIdRQ= new IsWatchlistedRQ();
        cusIdRQ.setCustId(cusId);
        cusIdRQ.setRefId(uniqueRQId);
        IsWatchlistedRS response = null;
        if (isServiceEnable) {


            String url = this.finacleIsWatchlistedUrl;
            RestTemplate restTemplate = new RestTemplate();

            try {

                String result = restTemplate.postForObject(url, cusIdRQ, String.class);

                Gson gson = new Gson();

                response = gson.fromJson(result, IsWatchlistedRS.class );


                LOG.info("END: Get watch list status in integeration service {} : {} by {}", cusIdRQ, result, credentialsDTO);
            } catch (Exception e) {
                LOG.info("ERROR: Error occur while Get watch list status in integeration service  :{}: {}: {} by {}", url, cusIdRQ, response, credentialsDTO, e);
            }

        } else {
            LOG.warn("WARN: Get watch list status in integeration service is disabled {} By {}", cusIdRQ, credentialsDTO);
        }
        LOG.info("END: Get watch list status in integeration service service is disabled {} By {}", cusIdRQ, credentialsDTO);
        return response;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public FinacleGuaranteeVolumesRS getGuaranteeVolumes(GuaranteeVolumesRQ guaranteeVolumesRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get guarantee volumes in Intergration service {} by {}", guaranteeVolumesRQ.getCustId(), credentialsDTO);
        boolean isServiceEnable = this.finacleGuaranteeVolumesEnable;
        String uniqueRQId="CAS_"+UUID.randomUUID().toString();
        FinacleGuaranteeVolumesRQ cusIdRQ= new FinacleGuaranteeVolumesRQ();
        cusIdRQ.setCustId(guaranteeVolumesRQ.getCustId());
        cusIdRQ.setRefId(uniqueRQId);
        cusIdRQ.setStartDate(guaranteeVolumesRQ.getStartDate());
        cusIdRQ.setEndDate(guaranteeVolumesRQ.getEndDate());
        FinacleGuaranteeVolumesRS response = null;
        if (isServiceEnable) {


            String url = this.finacleGuaranteeVolumesUrl;
            RestTemplate restTemplate = new RestTemplate();

            try {

                String result = restTemplate.postForObject(url, cusIdRQ, String.class);

                Gson gson = new Gson();

                response = gson.fromJson(result, FinacleGuaranteeVolumesRS.class );


                LOG.info("END: Get guarantee volumes in Intergration service {} : {} by {}", cusIdRQ, result, credentialsDTO);
            } catch (Exception e) {
                LOG.info("ERROR: Error occur while Get guarantee volumes in Intergration service  :{}: {}: {} by {}", url, cusIdRQ, response, credentialsDTO, e);
            }

        } else {
            LOG.warn("WARN: Get guarantee volumes in Intergration service is disabled {} By {}", cusIdRQ, credentialsDTO);
        }
        LOG.info("END: Get guarantee volumes in Intergration service {} By {}", cusIdRQ, credentialsDTO);
        return response;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public cusAccountsRS getCustomerAccounts(String cusAccount, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get Account detail List {} by {}", cusAccount, credentialsDTO);
        boolean isServiceEnable = this.customerAASAccountsEnable;
        String uniqueRQId="CAS_"+UUID.randomUUID().toString();

        CusAASAccountsRQ cusAASAccountsRQ= new CusAASAccountsRQ();
        cusAASAccountsRQ.setReqId(uniqueRQId);
        cusAASAccountsRQ.setAcctNumber(cusAccount);

        cusAccountsRS response = null;
        if (isServiceEnable) {
            String url = this.customerAASAccountsUrl;
            RestTemplate restTemplate = new RestTemplate();
            try {
                String result = restTemplate.postForObject(url, cusAASAccountsRQ, String.class);
                Gson gson = new Gson();
                response = gson.fromJson(result, cusAccountsRS.class );

                LOG.info("END: Get Customer accounts List {} : {} by {}", cusAccount, result, credentialsDTO);
            } catch (Exception e) {
                LOG.info("ERROR: Error occur while loading finacle Customer accounts List  :{}: {}: {} by {}", url, cusAccount, response, credentialsDTO, e);
            }

        } else {
            LOG.warn("WARN: Get Customer accounts List service is disabled {} By {}", cusAccount, credentialsDTO);
        }
        LOG.info("END: Get finacle Customer accounts List service is disabled {} By {}", cusAccount, credentialsDTO);
        return response;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public ExportDcBillsTurnoverRS getExportDcBillsTurnover(ExportTurnOverRQ cusAccount, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get TTTurnover detail List {} by {}", cusAccount, credentialsDTO);
        boolean isServiceEnable = this.exportDcBillsTurnoverEnable;
        String uniqueRQId="CAS_"+UUID.randomUUID().toString();

        ExportDCBillsTurnOverRQ exportDCBillsTrunOverRQ = new ExportDCBillsTurnOverRQ();
        exportDCBillsTrunOverRQ.setAccId(cusAccount.getAccountId());
        exportDCBillsTrunOverRQ.setStDate(cusAccount.getStartDate());
        exportDCBillsTrunOverRQ.setEnDate(cusAccount.getEndDate());
        exportDCBillsTrunOverRQ.setReqId(uniqueRQId);


        ExportDcBillsTurnoverRS response = new ExportDcBillsTurnoverRS();
        if (isServiceEnable) {
            String url = this.exportDcBillsTurnoverUrl;
            RestTemplate restTemplate = new RestTemplate();
            try {
                String result = restTemplate.postForObject(url, exportDCBillsTrunOverRQ, String.class);
                Gson gson = new Gson();
                response = gson.fromJson(result, ExportDcBillsTurnoverRS.class );

                LOG.info("END: Get TT Turn Over {} : {} by {}", cusAccount, result, credentialsDTO);
            } catch (Exception e) {
                response = null;
                LOG.info("ERROR: Error occur while loading TT Turn Over List  :{}: {}: {} by {}", url, cusAccount, response, credentialsDTO, e);
            }

        }
        else {
            response = null;
            LOG.warn("WARN: Get TT Turn Over List service is disabled {} By {}", cusAccount, credentialsDTO);
        }
        LOG.info("END: Get TT Turn Over List service is disabled {} By {}", cusAccount, credentialsDTO);
        return response;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ExportInwardTurnoverRS getExportInwardTurnover(ExportTurnOverRQ cusAccount, CredentialsDTO credentialsDTO) {

        LOG.info("START: Get TTTurnover detail List {} by {}", cusAccount, credentialsDTO);
        boolean isServiceEnable = this.exportInwardTurnoverEnable;
        String uniqueRQId="CAS_"+UUID.randomUUID().toString();

        ExportInwardTurnOverRQ exportInwardTurnOverRQ = new ExportInwardTurnOverRQ();
        exportInwardTurnOverRQ.setForacId(cusAccount.getAccountId());
        exportInwardTurnOverRQ.setStDate(cusAccount.getStartDate());
        exportInwardTurnOverRQ.setEnDate(cusAccount.getEndDate());
        exportInwardTurnOverRQ.setRequestId(uniqueRQId);

        ExportInwardTurnoverRS response = null;
        if (isServiceEnable) {
            String url = this.exportInwardTurnoverrUrl;
            RestTemplate restTemplate = new RestTemplate();
            try {
                String result = restTemplate.postForObject(url, exportInwardTurnOverRQ, String.class);
                Gson gson = new Gson();
                response = gson.fromJson(result, ExportInwardTurnoverRS.class );

                LOG.info("END: Get TT Turn Over {} : {} by {}", cusAccount, result, credentialsDTO);
            } catch (Exception e) {
                response = null;
                LOG.info("ERROR: Error occur while loading TT Turn Over List  :{}: {}: {} by {}", url, cusAccount, response, credentialsDTO, e);
            }

        } else {
            response = null;
            LOG.warn("WARN: Get TT Turn Over List service is disabled {} By {}", cusAccount, credentialsDTO);
        }

        LOG.info("END: Get TT Turn Over List service is disabled {} By {}", cusAccount, credentialsDTO);
        return response;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public InsuranceFinacleRS getInsuranceValuaationDetails(String cusId, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get Insurance detail List in Integration Serivce {} by {}", cusId, credentialsDTO);
        boolean isServiceEnable = this.insuranceValuationEnable;
        String uniqueRQId="CAS_"+UUID.randomUUID().toString();

        InsuranceFinacleRQ insuranceFinacleRQ= new InsuranceFinacleRQ();
        insuranceFinacleRQ.setRequestId(uniqueRQId);
        insuranceFinacleRQ.setCustomerId(cusId);

        InsuranceFinacleRS response = null;
        String result;
        if (isServiceEnable) {
            String url = this.insuranceValuationUrl;
            RestTemplate restTemplate = new RestTemplate();
            try {
                result = restTemplate.postForObject(url, insuranceFinacleRQ, String.class);
                Gson gson = new Gson();
                response = gson.fromJson(result, InsuranceFinacleRS.class );

                LOG.info("END: Get Insurance detail List in Integration Serivce{} : {} by {}", cusId, result, credentialsDTO);
            } catch (Exception e) {
                LOG.info("ERROR: Error occur while Get Insurance detail List in Integration Serivce :{}: {}: {} by {}", url, cusId, response, credentialsDTO, e);
            }

        } else {
            LOG.warn("WARN: Get Insurance detail List in Integration Serivce is disabled {} By {}", cusId, credentialsDTO);
        }
        LOG.info("END: Get Insurance detail List in Integration Serivce {} By {}", cusId, credentialsDTO);
        return response;
    }

    
    @Transactional(propagation = Propagation.SUPPORTS)
    public KalyptoRS getKalyptoSystemDetail(LoadKalyptoDataRQ loadKalyptoDataRQ, CredentialsDTO credentialsDTO) {

        LOG.info("START: Load Kalypto detail: {} by {}", loadKalyptoDataRQ, credentialsDTO);
        boolean isServiceEnable = this.kalypotoSystemEnabled;
        KalyptoRS kalyptoRS = null;
        KalyptoResponseDTO kalyptoResponseDTO = null;

        String jsonContentStr = "";
        kalyptoRS = new KalyptoRS();
        if (isServiceEnable) {

            String api = kalyptoSystemUrl + "?UniqueIdentifier=" + loadKalyptoDataRQ.getUniqueIdentifier();
            LOG.info("Load Kalypto Detail API ==> {}", api);
            try {
                RestTemplate restTemplate = new RestTemplate();
                String resultStr = restTemplate.getForObject(api, String.class);
                LOG.info("INFO : Load Kalypto Detail Response String : {}", resultStr);
                if (resultStr != null) {
                    jsonContentStr = JAXB.unmarshal(new StringReader(resultStr), String.class);
                }
                if (jsonContentStr.contains("Error: Evaluation is not exists for selected Borrower")) {
                    kalyptoRS.setSuccessfullyParse(false);
                    kalyptoRS.setMessage(jsonContentStr);
                } else {
                    ObjectMapper om = new ObjectMapper();
                    KalyptoResponse kalyptoResponse = null;
                    try {
                        kalyptoResponse = om.readValue(jsonContentStr, KalyptoResponse.class);
                        //kalyptoRS.setKalyptoResponse(kalyptoResponse);
                        kalyptoResponseDTO = new KalyptoResponseDTO(kalyptoResponse);
                        kalyptoRS.setKalyptoResponseDTO(kalyptoResponseDTO);
                    } catch (IOException e) {
                        LOG.error("ERROR while parsing json value {}", jsonContentStr, e);
                    }
                    kalyptoRS.setSuccessfullyParse(true);
                    kalyptoRS.setString(jsonContentStr);
                }
                LOG.info("END: Load Kalypto detail: {} by {}", loadKalyptoDataRQ, credentialsDTO);
            } catch (Exception e) {
                kalyptoRS.setSuccessfullyParse(false);
                kalyptoRS.setMessage("ERROR occur while loading Kalypto details");
                LOG.error("ERROR: Load Kalypto detail failed : {} by {}", loadKalyptoDataRQ, credentialsDTO, e);
            }

        } else {
            LOG.error("ERROR: Get Kalypto detail service is disabled By {}", credentialsDTO);
        }
        return kalyptoRS;
    }

    public List<SystemIntegrationDTO> getUserApplicationList(Integer userID) throws Exception {

        LOG.info("START: get user application list : {}", userID);
        boolean isServiceEnable = this.userApplicationEnable;
        List<SystemIntegrationDTO> systemIntegrationDTOList = null;

        if (isServiceEnable) {
            String url = this.userApplicationURL.replace("{userId}", String.valueOf(userID));
            LOG.info("INFO: url to the service {}", url);

            SslContext sslContext = null;
            WebClient webClient = null;

            try {
                sslContext = SslContextBuilder
                        .forClient()
                        .trustManager(InsecureTrustManagerFactory.INSTANCE)
                        .build();

                SslContext finalSslContext = sslContext;
                HttpClient httpClient = HttpClient.create()
                        .secure(sslSpec -> sslSpec.sslContext(finalSslContext));

                webClient = WebClient.builder()
                        .clientConnector(new ReactorClientHttpConnector(httpClient))
                        .build();

            } catch (SSLException e) {
                LOG.error("SSLException: getUserApplicationList | IntegrationService |  Failed to build SSL context or secure HttpClient", e);
            } catch (Exception e) {
                LOG.error("Exception: getUserApplicationList | IntegrationService |  Unexpected error during WebClient creation", e);
            }

            try {
                String response = webClient.get()
                        .uri(url)
                        .retrieve()
                        .bodyToMono(String.class)
                        .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsDefault))
                        .onErrorReturn("Timeout occurred or service failed in getUserApplicationList")  // optional fallback
                        .block();

                LOG.info("INFO: getUserApplicationList | IntegrationService | get the user application list {}", response);

                Gson gson = new Gson();
                systemIntegrationDTOList = gson.fromJson(response, new TypeToken<List<SystemIntegrationDTO>>() {}.getType());

                for (SystemIntegrationDTO dto : systemIntegrationDTOList) {
                        String combinedCodeWithStatus = dto.getApplicationShortName() + " - " + dto.getActiveStatus();
                        dto.setApplicationCodeWithStatus(combinedCodeWithStatus);
                    }

                LOG.info("INFO: getUserApplicationList | IntegrationService | get the user application list {} by {}", systemIntegrationDTOList, userID);
            } catch (Exception e) {
                LOG.info("ERROR: getUserApplicationList | IntegrationService | get the user application list :{}: by{}", url, userID, e);
            }
        } else {
            LOG.error("ERROR: getUserApplicationList | IntegrationService | get user application list {}", userID);
        }
        return systemIntegrationDTOList;
    }

    public CompReportingResponse getReportingData(ReportingDateRange dateRange) throws AppsException{
        CompReportingResponse responseDTO = new CompReportingResponse();

        String url = this.reportingDataURL;
        //WebClient webClient = WebClient.create();
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configure -> configure
                        .defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024))  // 10MB
                .build();
        WebClient webClient = WebClient.builder()
                .exchangeStrategies(strategies)
                .build();
        try {
            JSONObject bodyValue = new JSONObject();
            String requestId = "cas".concat(String.valueOf(UUID.randomUUID()));
            bodyValue.put("requestId", requestId);
            bodyValue.put("startDate", dateRange.getStartDate());
            bodyValue.put("endDate", dateRange.getEndDate());

            String response = webClient.post()
                    .uri(url)
                    .bodyValue(bodyValue)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                    .onErrorReturn("Timeout occurred or service failed in getReportingData")  // optional fallback
                    .block();

            LOG.info("INFO: getReportingData | IntegrationService | CHECK reporting response {}", response);

            Gson gson = new Gson();
            responseDTO = gson.fromJson(response, CompReportingResponse.class);

            LOG.info("INFO: getReportingData | IntegrationService | CHECK reporting response to responseDTO {}", responseDTO);

        } catch (Exception e) {
            LOG.info("ERROR: getReportingData | IntegrationService | CHECK reporting response :{}", url, e);
        }

        return responseDTO;
    }

    public LimitNodeResponse getLimitNodeData(LimitNodeRequest limitNodeRequest) throws AppsException{
        LimitNodeResponse responseDTO = new LimitNodeResponse();

        String url = this.limitNodeDetailsURL;
        WebClient webClient = WebClient.create();

        JSONObject bodyValue = new JSONObject();
        String requestId = "cas".concat(String.valueOf(UUID.randomUUID()));
        bodyValue.put("reqId", requestId);
        bodyValue.put("limitB2kId",limitNodeRequest.getLimitB2kId());

        try {
            String response = webClient.post()
                    .uri(url)
                    .bodyValue(bodyValue)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                    .onErrorReturn("Timeout occurred or service failed in getLimitNodeData")  // optional fallback
                    .block();

            LOG.info("INFO: getLimitNodeData | IntegrationService | CHECK limit node response {}", response);

            Gson gson = new Gson();
            responseDTO = gson.fromJson(response, LimitNodeResponse.class);

            if (!responseDTO.getStatus().equals("Success")){
                throw new AppsException("An error occurred.");
            }

            LOG.info("INFO: getLimitNodeData | IntegrationService |  CHECK limit node response to responseDTO {}", responseDTO);

        } catch (Exception e) {
            LOG.info("ERROR: getLimitNodeData | IntegrationService |  CHECK limit node response :{}", url, e);
            throw new AppsException("An error occurred getLimitNodeData | IntegrationService | ");
        }

        return responseDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public LoanAccountCovenantDTO getLoanAccountCovenants(LoanAccCovenantReqDTO loanAccCovenantReqDTO) throws AppsException {

        LOG.info("START: get covenant details Response RQ : {}", loanAccCovenantReqDTO);

        LoanAccountCovenantDTO result = null;

        String url = this.accountCovenantDetailsURL;
        LOG.info("INFO: url to the finacle {}", url);
        WebClient webClient = WebClient.create();

        try {
            String response = webClient.post()
                    .uri(url)
                    .bodyValue(loanAccCovenantReqDTO)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                    .onErrorReturn("Timeout occurred or service failed in getLoanAccountCovenants")  // optional fallback
                    .block();

            LOG.info("INFO: getLoanAccountCovenants | IntegrationService | get the covenant details response from the finacle {}", response);

            Gson gson = new Gson();

            result = gson.fromJson(response, LoanAccountCovenantDTO.class);
            LOG.info("INFO: getLoanAccountCovenants | IntegrationService |  get the covenant details response {}", result);
        } catch (Exception e) {
            LOG.info("ERROR: getLoanAccountCovenants | IntegrationService |  get the covenant details response url :{}:", url, e);
        }

        return result;
}

    @Transactional(propagation = Propagation.SUPPORTS)
    public ImportCollectionTurnoverRS getImportCollectionTurnover(ImportTurnOverRQ cusAccount, CredentialsDTO credentialsDTO) {

        LOG.info("START: Get TTTurnover detail List {} by {}", cusAccount, credentialsDTO);
        boolean isServiceEnable = this.importCollectionTurnoverEnable;
        String uniqueRQId="CAS_"+UUID.randomUUID().toString();



        ImportCollectionTurnOverRQ importCollectionTurnOverRQ = new ImportCollectionTurnOverRQ();

        importCollectionTurnOverRQ.setForacId(cusAccount.getAccountId());
        importCollectionTurnOverRQ.setStartDate(cusAccount.getStartDate());
        importCollectionTurnOverRQ.setEndDate(cusAccount.getEndDate());
        importCollectionTurnOverRQ.setReqId(uniqueRQId);

        ImportCollectionTurnoverTempRS tempResponse = null;
        ImportCollectionTurnoverRS response = null;
        if (isServiceEnable) {
            String url = this.importCollectionTurnoverUrl;
            RestTemplate restTemplate = new RestTemplate();
            try {
                String result = restTemplate.postForObject(url, importCollectionTurnOverRQ, String.class);
                Gson gson = new Gson();
                tempResponse = gson.fromJson(result, ImportCollectionTurnoverTempRS.class );

                if(tempResponse.getImpColRecs() instanceof String)
                {
                    response = new ImportCollectionTurnoverRS(tempResponse.getStatus(), tempResponse.getReqId(),new ArrayList<>(),new ArrayList<>());

                }
                else
                {
                    response = gson.fromJson(result, ImportCollectionTurnoverRS.class );
                }

                LOG.info("END: Get TT Turn Over {} : {} by {}", cusAccount, result, credentialsDTO);
            } catch (Exception e) {
                response = null;
                LOG.info("ERROR: Error occur while loading TT Turn Over List  :{}: {}: {} by {}", url, cusAccount, response, credentialsDTO, e);
            }

        } else {
            response = null;
            LOG.warn("WARN: Get TT Turn Over List service is disabled {} By {}", cusAccount, credentialsDTO);
        }

        LOG.info("END: Get TT Turn Over List service is disabled {} By {}", cusAccount, credentialsDTO);
        return response;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ImportDCTurnoverRS getImportDCTurnover(ImportTurnOverRQ cusAccount, CredentialsDTO credentialsDTO) {

        LOG.info("START: Get TTTurnover detail List {} by {}", cusAccount, credentialsDTO);


        boolean isServiceEnable = this.importDCTurnoverEnable;
        String uniqueRQId="CAS_"+UUID.randomUUID().toString();



        ImportDCTurnOverRQ importDCTurnOverRQ = new ImportDCTurnOverRQ();

        importDCTurnOverRQ.setForacId(cusAccount.getAccountId());
        importDCTurnOverRQ.setStartDate(cusAccount.getStartDate());
        importDCTurnOverRQ.setEndDate(cusAccount.getEndDate());
        importDCTurnOverRQ.setReqId(uniqueRQId);

        ImportDCTurnoverTempRS tempResponse = null;
        ImportDCTurnoverRS response = null;
        if (isServiceEnable) {
            String url = this.importDCTurnoverUrl;
            RestTemplate restTemplate = new RestTemplate();
            try {
                String result = restTemplate.postForObject(url, importDCTurnOverRQ, String.class);
                Gson gson = new Gson();
                tempResponse = gson.fromJson(result, ImportDCTurnoverTempRS.class );

                if(tempResponse.getImpDCTORecs() instanceof String)
                {
                    response = new ImportDCTurnoverRS(tempResponse.getStatus(), tempResponse.getReqId(),new ArrayList<>(),new ArrayList<>());

                }
                else
                {
                    response = gson.fromJson(result, ImportDCTurnoverRS.class );
                }

                LOG.info("END: Get TT Turn Over {} : {} by {}", cusAccount, result, credentialsDTO);
            } catch (Exception e) {
                response = null;
                LOG.info("ERROR: Error occur while loading TT Turn Over List  :{}: {}: {} by {}", url, cusAccount, response, credentialsDTO, e);
            }

        } else {
            response = null;
            LOG.warn("WARN: Get TT Turn Over List service is disabled {} By {}", cusAccount, credentialsDTO);
        }

        LOG.info("END: Get TT Turn Over List service is disabled {} By {}", cusAccount, credentialsDTO);
        return response;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ImportOutwardTurnoverRS getImportOutwardTurnover(ImportTurnOverRQ cusAccount, CredentialsDTO credentialsDTO) {

        LOG.info("START: Get TTTurnover detail List {} by {}", cusAccount, credentialsDTO);

        boolean isServiceEnable = this.importOutwardTurnoverEnable;
        String uniqueRQId="CAS_"+UUID.randomUUID().toString();

        ImportOutwardTurnOverRQ importOutwardTurnOverRQ = new ImportOutwardTurnOverRQ();

        importOutwardTurnOverRQ.setForAcId(cusAccount.getAccountId());
        importOutwardTurnOverRQ.setStartDate(cusAccount.getStartDate());
        importOutwardTurnOverRQ.setEndDate(cusAccount.getEndDate());
        importOutwardTurnOverRQ.setReqId(uniqueRQId);


        ImportOutwardTurnoverRS response = null;
        if (isServiceEnable) {
            String url = this.importOutwardTurnoverUrl;
            RestTemplate restTemplate = new RestTemplate();
            try {
                String result = restTemplate.postForObject(url, importOutwardTurnOverRQ, String.class);
                Gson gson = new Gson();
                response = gson.fromJson(result, ImportOutwardTurnoverRS.class );

                LOG.info("END: Get TT Turn Over {} : {} by {}", cusAccount, result, credentialsDTO);
            } catch (Exception e) {
                response = null;
                LOG.info("ERROR: Error occur while loading TT Turn Over List  :{}: {}: {} by {}", url, cusAccount, response, credentialsDTO, e);
            }

        } else {
            response = null;
            LOG.warn("WARN: Get TT Turn Over List service is disabled {} By {}", cusAccount, credentialsDTO);
        }

        LOG.info("END: Get TT Turn Over List service is disabled {} By {}", cusAccount, credentialsDTO);
        return response;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public CovenantDetailsFinacleDTO getCovenantDetailsFromFinacle(LoadCovenantDataDTO loadCovenantDataDTO, CredentialsDTO credentialsDTO) throws Exception{

        LOG.info("START: get covenant details Response RQ : {} by: {}", loadCovenantDataDTO, credentialsDTO);

        boolean isServiceEnable = this.covenantDetailsEnable;
        CovenantDetailsFinacleDTO result = null;

        if(isServiceEnable){

            String url = this.accountCovenantDetailsURL;
            LOG.info("INFO: url to the finacle {}", url);
            LOG.info("INFO: Request to the finacle {}", loadCovenantDataDTO);
            WebClient webClient = WebClient.create();

            try {
                String response = webClient.post()
                        .uri(url)
                        .bodyValue(loadCovenantDataDTO)
                        .retrieve()
                        .bodyToMono(String.class)
                        .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                        .onErrorReturn("Timeout occurred or service failed in getCovenantDetailsFromFinacle")  // optional fallback
                        .block();

                LOG.info("INFO: getCovenantDetailsFromFinacle | IntegrationService | get the tran details response from the finacle {}", response);

                Gson gson = new Gson();

                result = gson.fromJson(response, CovenantDetailsFinacleDTO.class);
                LOG.info("INFO: getCovenantDetailsFromFinacle | IntegrationService |  get the covenant details response {} by {}", result, credentialsDTO);
            } catch (Exception e) {
                LOG.info("ERROR: getCovenantDetailsFromFinacle | IntegrationService |  get the covenant details response url :{}: by{}", url, credentialsDTO, e);
            }
        } else {
            LOG.error("ERROR: getCovenantDetailsFromFinacle | IntegrationService |  get covenant details Response By {}", credentialsDTO);
        }

        LOG.error("END: getCovenantDetailsFromFinacle | IntegrationService | result {}", result);
        return result;
    }

    public AccCollateralResponse getLoanAccountCollateral(AccCollateralRequest accCollateralRequest) throws AppsException {

        LOG.info("START: get collateral details Response RQ : {}", accCollateralRequest);

        AccCollateralResponse result = null;

        String url = this.accountCollateralDetailsURL;
        LOG.info("INFO: url to the finacle {}", url);
        WebClient webClient = WebClient.create();

        JSONObject bodyValue = new JSONObject();
        String requestId = "cas".concat(String.valueOf(UUID.randomUUID()));
        bodyValue.put("RequestUUID", requestId);
        bodyValue.put("type",accCollateralRequest.getType());
        bodyValue.put("acctId",accCollateralRequest.getAcctId());
        bodyValue.put("nodeId",accCollateralRequest.getNodeId());

        LOG.info("JSONObject collateral details RQ : {}", bodyValue);
        try {
            String response = webClient.post()
                    .uri(url)
                    .bodyValue(bodyValue)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                    .onErrorReturn("Timeout occurred or service failed in getLoanAccountCollateral")  // optional fallback
                    .block();

            LOG.info("INFO: getLoanAccountCollateral | IntegrationService | get the collateral response from the finacle {}", response);

            Gson gson = new Gson();

            result = gson.fromJson(response, AccCollateralResponse.class);
            LOG.info("INFO: getLoanAccountCollateral | IntegrationService |  get the collateral details response {}", result);
        } catch (Exception e) {
            LOG.info("ERROR: getLoanAccountCollateral | IntegrationService |  get the collateral details response url :{}:", url, e);
        }
        return result;
    }

    public CribResponseDTO searchIndividualCrib(CribRequestDTO cribRequestDTO) throws AppsException{
        CribResponseDTO responseDTO = new CribResponseDTO();

        String url = this.cribSearch;
        //WebClient webClient = WebClient.create();
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configure -> configure
                        .defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024))  // 10MB
                .build();
        WebClient webClient = WebClient.builder()
                .exchangeStrategies(strategies)
                .build();

        JSONObject bodyValue = new JSONObject();
        JSONObject parameters = new JSONObject();

        parameters.put("applicationNumber",cribRequestDTO.getApplicationNumber());
        parameters.put("applicationDate",cribRequestDTO.getApplicationDate());
        parameters.put("subjectName",cribRequestDTO.getFullName());
        parameters.put("creditFacilityType",cribRequestDTO.getCreditFacilityType());
        parameters.put("creditFacilityCurrency",cribRequestDTO.getCreditFacilityCurrency());
        parameters.put("reportDate",cribRequestDTO.getReportDate());
        parameters.put("creditFacilityAmount",cribRequestDTO.getCreditFacilityAmountDTO());
        parameters.put("fullName",cribRequestDTO.getFullName());
        parameters.put("gender",cribRequestDTO.getGender());
        parameters.put("idNumbersList",cribRequestDTO.getIdNumberDTOList());

        bodyValue.put("parameters",parameters);
        bodyValue.put("inquiryReason",cribRequestDTO.getInquiryReason());
        bodyValue.put("interactiveSearch",false);
        bodyValue.put("consent",true);
        bodyValue.put("timeOut",0);

        try {
            String response = webClient.post()
                    .uri(url)
                    .bodyValue(bodyValue)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                    .onErrorReturn("Timeout occurred or service failed in searchIndividualCrib")  // optional fallback
                    .block();

            LOG.info("INFO: searchIndividualCrib | IntegrationService | Search crib response {}", response);

            Gson gson = new Gson();
            responseDTO = gson.fromJson(response, CribResponseDTO.class);

            LOG.info("INFO: searchIndividualCrib | IntegrationService | Search crib response to responseDTO {}", responseDTO);

        } catch (Exception e) {
            LOG.info("ERROR: searchIndividualCrib | IntegrationService | Search crib response :{}", url, e);
        }

        return responseDTO;
    }

    public CribResponseDTO searchIndividualCribContinue(CribRequestDTO cribRequestDTO) throws AppsException{
        CribResponseDTO responseDTO = new CribResponseDTO();

        String url = this.cribSearchContinue;
        //WebClient webClient = WebClient.create();
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configure -> configure
                        .defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024))  // 10MB
                .build();
        WebClient webClient = WebClient.builder()
                .exchangeStrategies(strategies)
                .build();

        JSONObject bodyValue = new JSONObject();

        bodyValue.put("correlationId",cribRequestDTO.getCorrelationId());
        bodyValue.put("action", "Check");
        bodyValue.put("data", true);

        try {
            String response = webClient.post()
                    .uri(url)
                    .bodyValue(bodyValue)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                    .onErrorReturn("Timeout occurred or service failed in searchIndividualCribContinue")  // optional fallback
                    .block();

            LOG.info("INFO: searchIndividualCribContinue | IntegrationService | Search crib continue response {}", response);

            Gson gson = new Gson();
            responseDTO = gson.fromJson(response, CribResponseDTO.class);

            LOG.info("INFO: searchIndividualCribContinue | IntegrationService |  Search crib continue response to responseDTO {}", responseDTO);

        } catch (Exception e) {
            LOG.info("ERROR: searchIndividualCribContinue | IntegrationService |  Search crib continue response :{}", url, e);
        }

        return responseDTO;
    }

    public CribResponseDTO searchCompanyCrib(CribRequestDTO cribRequestDTO) throws AppsException{
        CribResponseDTO responseDTO = new CribResponseDTO();

        String url = this.cribSearchCompany;
        //WebClient webClient = WebClient.create();
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configure -> configure
                        .defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024))  // 10MB
                .build();
        WebClient webClient = WebClient.builder()
                .exchangeStrategies(strategies)
                .build();

        JSONObject bodyValue = new JSONObject();
        JSONObject parameters = new JSONObject();

        parameters.put("applicationNumber",cribRequestDTO.getApplicationNumber());
        parameters.put("applicationDate",cribRequestDTO.getApplicationDate());
        parameters.put("subjectName",cribRequestDTO.getFullName());
        parameters.put("creditFacilityType",cribRequestDTO.getCreditFacilityType());
        parameters.put("creditFacilityCurrency",cribRequestDTO.getCreditFacilityCurrency());
        parameters.put("reportDate",cribRequestDTO.getReportDate());
        parameters.put("creditFacilityAmount",cribRequestDTO.getCreditFacilityAmountDTO());
        parameters.put("companyName",cribRequestDTO.getFullName());
        parameters.put("idNumbersList",cribRequestDTO.getIdNumberDTOList());

        bodyValue.put("parameters",parameters);
        bodyValue.put("inquiryReason",cribRequestDTO.getInquiryReason());
        bodyValue.put("interactiveSearch",false);
        bodyValue.put("consent",true);
        bodyValue.put("timeOut",0);

        try {
            String response = webClient.post()
                    .uri(url)
                    .bodyValue(bodyValue)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                    .onErrorReturn("Timeout occurred or service failed in searchCompanyCrib")  // optional fallback
                    .block();

            LOG.info("INFO: searchCompanyCrib | IntegrationService | Search crib response {}", response);

            Gson gson = new Gson();
            responseDTO = gson.fromJson(response, CribResponseDTO.class);

            LOG.info("INFO: searchCompanyCrib | IntegrationService | Search crib response to responseDTO {}", responseDTO);

        } catch (Exception e) {
            LOG.info("ERROR: searchCompanyCrib | IntegrationService | Search crib response :{}", url, e);
        }

        return responseDTO;
    }

    public CribResponseDTO searchCompanyCribContinue(CribRequestDTO cribRequestDTO) throws AppsException{
        CribResponseDTO responseDTO = new CribResponseDTO();

        String url = this.cribSearchContinueCompany;
        //WebClient webClient = WebClient.create();
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configure -> configure
                        .defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024))  // 10MB
                .build();
        WebClient webClient = WebClient.builder()
                .exchangeStrategies(strategies)
                .build();

        JSONObject bodyValue = new JSONObject();

        bodyValue.put("correlationId",cribRequestDTO.getCorrelationId());
        bodyValue.put("action", "Check");
        bodyValue.put("data", true);

        try {
            String response = webClient.post()
                    .uri(url)
                    .bodyValue(bodyValue)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                    .onErrorReturn("Timeout occurred or service failed in searchCompanyCribContinue")  // optional fallback
                    .block();

            LOG.info("INFO: searchCompanyCribContinue | IntegrationService | Search crib continue response {}", response);

            Gson gson = new Gson();
            responseDTO = gson.fromJson(response, CribResponseDTO.class);

            LOG.info("INFO: searchCompanyCribContinue | IntegrationService | Search crib continue response to responseDTO {}", responseDTO);

        } catch (Exception e) {
            LOG.info("ERROR: searchCompanyCribContinue | IntegrationService | Search crib continue response :{}", url, e);
        }

        return responseDTO;
    }

    public CribResponseDTO getCustomReport(CribRequestDTO cribRequestDTO) throws AppsException{
        CribResponseDTO responseDTO = new CribResponseDTO();

        String url = this.cribReportCustom;
        //WebClient webClient = WebClient.create();
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configure -> configure
                        .defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024))  // 10MB
                .build();
        WebClient webClient = WebClient.builder()
                .exchangeStrategies(strategies)
                .build();

        JSONObject bodyValue = new JSONObject();
        bodyValue.put("sectionsList",cribRequestDTO.getSectionsList());
        bodyValue.put("subjectToken",cribRequestDTO.getSubjectToken());
        bodyValue.put("timeout",0);

        try {
            String response = webClient.post()
                    .uri(url)
                    .bodyValue(bodyValue)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                    .onErrorReturn("Timeout occurred or service failed in getCustomReport")  // optional fallback
                    .block();

            LOG.info("INFO: getCustomReport | IntegrationService | GET crib report token response {}", response);

            Gson gson = new Gson();
            responseDTO = gson.fromJson(response, CribResponseDTO.class);

            LOG.info("INFO: getCustomReport | IntegrationService | GET crib report token response to responseDTO {}", responseDTO);

        } catch (Exception e) {
            LOG.info("ERROR: getCustomReport | IntegrationService | GET crib report token response :{}", url, e);
        }

        return responseDTO;
    }

    public CribResponseDTO getCustomReportPDF(CribRequestDTO cribRequestDTO) throws AppsException{
        CribResponseDTO responseDTO = new CribResponseDTO();

        String url = this.cribReportPDF;
        //WebClient webClient = WebClient.create();
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configure -> configure
                        .defaultCodecs()
                        .maxInMemorySize(30 * 1024 * 1024))  // 30MB
                .build();
        WebClient webClient = WebClient.builder()
                .exchangeStrategies(strategies)
                .build();

        JSONObject bodyValue = new JSONObject();

        bodyValue.put("reportToken",cribRequestDTO.getReportToken());
        bodyValue.put("languageCode","en-GB");

        try {
            String response = webClient.post()
                    .uri(url)
                    .bodyValue(bodyValue)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                    .onErrorReturn("Timeout occurred or service failed in getCustomReportPDF")  // optional fallback
                    .block();

            LOG.info("INFO: getCustomReportPDF | IntegrationService | GET crib report response {}", response);

            Gson gson = new Gson();
            responseDTO = gson.fromJson(response, CribResponseDTO.class);

            LOG.info("INFO: getCustomReportPDF | IntegrationService |  GET crib report response to responseDTO {}", responseDTO);

        } catch (Exception e) {
            LOG.info("ERROR: getCustomReportPDF | IntegrationService |  GET crib report response {}", e.getMessage());
        }

        return responseDTO;
    }

    public CribResponseDTO getCustomReportByToken(CribRequestDTO cribRequestDTO) throws AppsException{
        CribResponseDTO responseDTO = new CribResponseDTO();

        String url = this.cribReportCustomToken;
        //WebClient webClient = WebClient.create();
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configure -> configure
                        .defaultCodecs()
                        .maxInMemorySize(30 * 1024 * 1024))  // 30MB
                .build();
        WebClient webClient = WebClient.builder()
                .exchangeStrategies(strategies)
                .build();

        JSONObject bodyValue = new JSONObject();
        bodyValue.put("token",cribRequestDTO.getReportToken());

        try {
            String response = webClient.post()
                    .uri(url)
                    .bodyValue(bodyValue)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                    .onErrorReturn("Timeout occurred or service failed in getCustomReportByToken")  // optional fallback
                    .block();

            LOG.info("INFO: getCustomReportByToken | IntegrationService | CHECK crib report token response {}", response);

            Gson gson = new Gson();
            responseDTO = gson.fromJson(response, CribResponseDTO.class);

            LOG.info("INFO: getCustomReportByToken | IntegrationService | CHECK crib report token response to responseDTO {}", responseDTO);

        } catch (Exception e) {
            LOG.info("ERROR: getCustomReportByToken | IntegrationService | CHECK crib report token response :{}", url, e);
        }

        return responseDTO;
    }

    public List<GroupExposureDetailDTO> getGroupExposure(GroupExposureRequest groupExposureRequest) throws AppsException {
        LOG.info("START: get group exposure details Response RQ : {}", groupExposureRequest);

        //delete all exposures by facilityPaperId
        groupExposureDetailDao.deleteRecordsByFacilityID(groupExposureRequest.getFacilityID());

        LOG.info("INFO: DELETED Records for facilityID : {}", groupExposureRequest.getFacilityID());

        List<GroupExposureDetailDTO> resultList = new ArrayList<>();
        String url = this.groupExposureDetailsURL;
        LOG.info("INFO: URL to the Finacle {}", url);

        //WebClient webClient = WebClient.create();
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configure -> configure
                        .defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024))  // 10MB
                .build();
        WebClient webClient = WebClient.builder()
                .exchangeStrategies(strategies)
                .build();

        JSONObject bodyValue = new JSONObject();
        String requestId = "cas".concat(String.valueOf(UUID.randomUUID()));
        bodyValue.put("requestId", requestId);
        bodyValue.put("cifiId", groupExposureRequest.getCifiId());

        try {
            String response = webClient.post()
                    .uri(url)
                    .bodyValue(bodyValue)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsDefault))
                    .onErrorReturn("Timeout occurred or service failed in getGroupExposure")  // optional fallback
                    .block();

            LOG.info("response from finacle : {}", response);

            Gson gson = new Gson();
            GroupExposureResponse result = gson.fromJson(response, GroupExposureResponse.class);

            LOG.info("response from finacle convert to dto: {}", result);

            if (result != null && "Success".equalsIgnoreCase(result.getStatus())) {
                for (GroupExposureData data : result.getExposureDtls()) {
                    GroupExposureCustomerID customerIdDetails = data.getCustomerId();

                    if (customerIdDetails == null ||
                            customerIdDetails.getCustId() == null ||
                            customerIdDetails.getCustId().trim().isEmpty()) {

                        LOG.warn("WARNING: getGroupExposure | IntegrationService |  Skipping entry with missing or invalid customerId.");
                        continue;
                    }

                    GroupExposureDetail entity = new GroupExposureDetail();

                    entity.setCustomerID(customerIdDetails.getCustId());
                    entity.setCustomerName(customerIdDetails.getCustName());
                    entity.setParentCustID(customerIdDetails.getParentCustId());
                    entity.setRelationshipCode(customerIdDetails.getRelationshipCodeAsString());
                    entity.setTotalSanctionLimit(convertToBigDecimal(customerIdDetails.getTotalSanctionLimit()));
                    entity.setOutstandingAmount(convertToBigDecimal(customerIdDetails.getOutstandingAmt()));
                    entity.setFacilityID(groupExposureRequest.getFacilityID());
                    entity.setIsSelected(1);

                    int isPrimary = customerIdDetails.getCustId().equals(groupExposureRequest.getCifiId()) ? 1 : 0;
                    entity.setIsPrimary(isPrimary);

                    if (customerIdDetails.getFundedLimit() != null) {
                        entity.setFundedLimitTotal(
                                convertToBigDecimal(customerIdDetails.getFundedLimit().getTotalSanctionLimit()));
                        entity.setFundedOutstanding(
                                convertToBigDecimal(customerIdDetails.getFundedLimit().getOutstandingAmount()));
                    }

                    if (customerIdDetails.getNonFundedLimit() != null) {
                        entity.setNonFundedLimitTotal(
                                convertToBigDecimal(customerIdDetails.getNonFundedLimit().getTotalSanctionLimit()));
                        entity.setNonFundedOutstanding(
                                convertToBigDecimal(customerIdDetails.getNonFundedLimit().getOutstandingAmount()));
                    }

                    if (customerIdDetails.getCashMargin() != null) {
                        entity.setLienAmount(
                                convertToBigDecimal(customerIdDetails.getCashMargin().getLienAmount()));
                    }

                    groupExposureDetailDao.save(entity);

                    resultList.add(mapToDTO(entity));
                }
            }
        } catch (Exception ex) {
            LOG.error("ERROR: getGroupExposure | IntegrationService | Failed to get the group exposure details response. URL: {}", url, ex);
        }

        LOG.info("resultList: {}", resultList);
        return resultList;
    }

    // Mapping method for converting Entity to DTO
    private GroupExposureDetailDTO mapToDTO(GroupExposureDetail entity) {
        GroupExposureDetailDTO dto = new GroupExposureDetailDTO();
        dto.setGroupExposureID(entity.getGroupExposureID());
        dto.setCustomerID(entity.getCustomerID());
        dto.setCustomerName(entity.getCustomerName());
        dto.setFundedTotalLimit(entity.getFundedLimitTotal());
        dto.setFundedOutstandingAmount(entity.getFundedOutstanding());
        dto.setNonFundedTotalLimit(entity.getNonFundedLimitTotal());
        dto.setNonFundedOutstandingAmount(entity.getNonFundedOutstanding());
        dto.setParentCustID(entity.getParentCustID());
        dto.setRelationshipCode(entity.getRelationshipCode());
        dto.setTotalSanctionLimit(entity.getTotalSanctionLimit());
        dto.setOutstandingAmount(entity.getOutstandingAmount());
        dto.setLienAmount(entity.getLienAmount());
        dto.setFacilityID(entity.getFacilityID());
        dto.setIsSelected(entity.getIsSelected());
        dto.setIsPrimary(entity.getIsPrimary());
        return dto;
    }

    private BigDecimal convertToBigDecimal(String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            LOG.error("Invalid number format for value: {}", value);
            return BigDecimal.ZERO;
        }
    }

    public PrefentialChargeResponse getCommissionData(CommissionChargeRQ commissionChargeRQ) throws AppsException{
        PrefentialChargeResponse responseDTO = new PrefentialChargeResponse();

        String url = this.commissionDataURL;
        //WebClient webClient = WebClient.create();
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configure -> configure
                        .defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024))  // 10MB
                .build();
        WebClient webClient = WebClient.builder()
                .exchangeStrategies(strategies)
                .build();

        try {
            JSONObject bodyValue = new JSONObject();
            String requestId = "cas".concat(String.valueOf(UUID.randomUUID()));
            bodyValue.put("requestId", requestId);
            bodyValue.put("chrgPrcntFrm", commissionChargeRQ.getChargeFrom());
            bodyValue.put("chrgPrcntTo", commissionChargeRQ.getChargeTo());
            bodyValue.put("productId", commissionChargeRQ.getProductId());

            String response = webClient.post()
                    .uri(url)
                    .bodyValue(bodyValue)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                    .onErrorReturn("Timeout occurred or service failed in getCommissionData")  // optional fallback
                    .block();

            LOG.info("INFO: getCommissionData | IntegrationService | CHECK commission response {}", response);

            Gson gson = new Gson();
            responseDTO = gson.fromJson(response, PrefentialChargeResponse.class);

            if (!responseDTO.getStatus().equals("Success") || responseDTO.getReportDtls() == null || responseDTO.getReportDtls().isEmpty()) {
                throw new AppsException("No data found. getCommissionData | IntegrationService |");
            }

            responseDTO.setReportDtls(getSortedCommissionData(commissionChargeRQ,responseDTO.getReportDtls()));

            LOG.info("INFO: getCommissionData | IntegrationService | CHECK commission response to responseDTO {}", responseDTO);

        } catch (Exception e) {
            LOG.info("ERROR: getCommissionData | IntegrationService | CHECK commission response :{}", url, e);
        }

        return responseDTO;
    }

    public CommissionReportingResponse getCommissionLoanAccounts(ReportingDateRange reportingDateRange) throws AppsException{
        CommissionReportingResponse responseDTO = new CommissionReportingResponse();

        String url = this.commissionMoreDataURL;
        //WebClient webClient = WebClient.create();
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configure -> configure
                        .defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024))  // 10MB
                .build();
        WebClient webClient = WebClient.builder()
                .exchangeStrategies(strategies)
                .build();
        try {
            JSONObject bodyValue = new JSONObject();
            String requestId = "cas".concat(String.valueOf(UUID.randomUUID()));
            bodyValue.put("requestId", requestId);
            bodyValue.put("startDate", reportingDateRange.getStartDate());
            bodyValue.put("endDate", reportingDateRange.getEndDate());
            bodyValue.put("cifId", reportingDateRange.getCustomerId());

            String response = webClient.post()
                    .uri(url)
                    .bodyValue(bodyValue)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                    .onErrorReturn("Timeout occurred or service failed in getCommissionLoanAccounts")  // optional fallback
                    .block();

            LOG.info("INFO: getCommissionLoanAccounts | IntegrationService | CHECK commission reporting data response {}", response);

            Gson gson = new Gson();
            responseDTO = gson.fromJson(response, CommissionReportingResponse.class);

            LOG.info("INFO: getCommissionLoanAccounts | IntegrationService |  CHECK commission reporting data response to responseDTO {}", responseDTO);

        } catch (Exception e) {
            LOG.info("ERROR: getCommissionLoanAccounts | IntegrationService | CHECK commission reporting data response :{}", url, e);
        }

        return responseDTO;
    }

    public List<PrefentialChargeDTO> getSortedCommissionData(CommissionChargeRQ commissionChargeRQ,List<PrefentialChargeDTO> dataList){
        List<PrefentialChargeDTO> result = new ArrayList<>();

        // Sort result if product is Import Bills (check whether DA OR DP)
        List<PrefentialChargeDTO> sortedData = dataList.stream().filter(data -> data.getChrgSubType().toString().contains(commissionChargeRQ.getChargeSubType()))
                .collect(Collectors.toList());

        // Remove duplicates (if cif and charge same)
        for (PrefentialChargeDTO commission : sortedData) {
            boolean noneMatch = result.stream().noneMatch(data -> data.getCif().equals(commission.getCif()) && data.getPcntAmt().equals(commission.getPcntAmt()));
            if (noneMatch) {
                result.add(commission);
            }
        }

        return result;
    }

    public List<RCTProductGroup> getRCTProductGroups() {
        List<RCTProductGroup> productGroups = new ArrayList<>();

        String url = this.rctUrl;
        WebClient webClient = WebClient.create();

        JSONObject bodyValue = new JSONObject();
        bodyValue.put("ReqId", UUID.randomUUID());
        bodyValue.put("RefRecType","BZ");
        bodyValue.put("RefCode","");

        try {
            String response = webClient.post()
                    .uri(url)
                    .bodyValue(bodyValue)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                    .onErrorReturn("Timeout occurred or service failed in getRCTProductGroups")
                    .block();// optional fallback


            LOG.info("INFO: getRCTProductGroups | IntegrationService | CHECK Product Group response {}", response);

            Gson gson = new Gson();
            RCTResponse responseDTO = gson.fromJson(response, RCTResponse.class);

            if (responseDTO.getResults() != null && !responseDTO.getResults().isEmpty()) {
                productGroups = responseDTO.getResults().stream().filter(group -> !DomainConstants.excludeProductGroups().contains(group.getRefCode()))
                                                        .collect(Collectors.toList());
            }

            LOG.info("INFO: getRCTProductGroups | IntegrationService | CHECK Product Group response to responseDTO {}", responseDTO);

        } catch (Exception e) {
            LOG.info("ERROR: getRCTProductGroups | IntegrationService | CHECK Product Group response :{}", url, e);
        }

        return productGroups;
    }

    public CrmLoginResponse performCrmLogin(CrmLoginRequest crmLoginRequest) {
        LOG.info("START: Perform CRM Login for user: {}", crmLoginRequest.getUserName());

        CrmLoginResponse crmLoginResponse = null;

        if (isCrmLoginEnable) {
            WebClient webClient = WebClient.create();

            try {
                LOG.info("INFO: Sending CRM Login request to URL: {}", crmLoginUrl);

                crmLoginResponse = webClient.post()
                    .uri(crmLoginUrl)
                    .bodyValue(crmLoginRequest)
                    .retrieve()
                    .bodyToMono(CrmLoginResponse.class)
                    .timeout(Duration.ofMillis(serviceTimeoutInMillisecondsDefault))
                    .block();

                LOG.info("INFO: CRM Login successful for user: {}", crmLoginRequest.getUserName());
            } catch (Exception e) {
                LOG.error("ERROR: Failed to perform CRM Login for user: {}. Error: {}", crmLoginRequest.getUserName(), e.getMessage());
            }
        } else {
         LOG.warn("WARN: CRM Login service is disabled.");
        }

        return crmLoginResponse;
    }

    public CrmSaveResponseDTO saveLeadCrmObject(LeadCrmDTO leadCrmDTO, String authorizationHeader) {
        LOG.info("START: Save Lead CRM Object: {}", leadCrmDTO);

        CrmSaveResponseDTO crmSaveResponseDTO = new CrmSaveResponseDTO();

        if (isSaveCrmObjectEnable) {
            WebClient webClient = WebClient.create();

            try {
                LOG.info("INFO: Sending Lead CRM Object to URL: {}", saveCrmObjectUrl);

                String responseStr = webClient.post()
                        .uri(saveCrmObjectUrl)
                        .header("Authorization", authorizationHeader)
                        .bodyValue(Collections.singletonList(leadCrmDTO))
                        .retrieve()
                        .bodyToMono(String.class)
                        .timeout(Duration.ofMillis(serviceTimeoutInMillisecondsDefault))
                        .block();

                LOG.info("INFO: Successfully saved Lead CRM Object. Response: {}", responseStr);

                Gson gson = new Gson();
                Type listType = new TypeToken<List<CrmSaveResponseDTO>>(){}.getType();
                List<CrmSaveResponseDTO> responseList = gson.fromJson(responseStr, listType);
                crmSaveResponseDTO = responseList.isEmpty() ? new CrmSaveResponseDTO() : responseList.get(0);

            } catch (Exception e) {
                LOG.error("ERROR: Failed to save Lead CRM Object. Error: {}", e.getMessage());
            }
        } else {
            LOG.warn("WARN: Save Lead CRM Object service is disabled.");
        }

        LOG.info("END: Save Lead CRM Object: {}", crmSaveResponseDTO);
        return crmSaveResponseDTO;
    }

    public String getCrmUserName() {
        return crmUserName;
    }

    public String getCrmPassword() {
        return crmPassword;
    }
    
    public boolean isLogOriginalBase64() {
        return logOriginalBase64;
    }

    public void setLogOriginalBase64(boolean logOriginalBase64) {
        this.logOriginalBase64 = logOriginalBase64;
    }

    public SmeCustomerTurnoverResDTO getSmeCustomerTurnoverData(SmeCustomerTurnoverRqDTO request) throws AppsException {
        SmeCustomerTurnoverResDTO responseDTO = new SmeCustomerTurnoverResDTO();

        String url = this.smeTurnoverUrl;
        WebClient webClient = WebClient.create();

        try {
            String response = webClient.post()
                    .uri(url)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                    .onErrorReturn("Timeout occurred or service failed in getSmeCustomerTurnoverData")  // optional fallback
                    .block();

            LOG.info("INFO: getSmeCustomerTurnoverData | IntegrationService | CHECK SME Customer Turnover Data response {}", response);

            Gson gson = new Gson();
            responseDTO = gson.fromJson(response, SmeCustomerTurnoverResDTO.class);

            LOG.info("INFO: getSmeCustomerTurnoverData | IntegrationService | CHECK SME Customer Turnover Data response to responseDTO {}", responseDTO);

        } catch (Exception e) {
            LOG.info("ERROR: getSmeCustomerTurnoverData | IntegrationService | CHECK SME Customer Turnover Data response :{}", url, e);
        }

        return responseDTO;
    }
}
