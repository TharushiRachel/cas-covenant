package com.itechro.cas.service.finacle;

import com.itechro.cas.dao.facilitypaper.jdbc.FinacleInformationDao;
import com.itechro.cas.dao.finacle.CollateralDetailsDao;
import com.itechro.cas.dao.finacle.ExportTurnOversDao;
import com.itechro.cas.dao.finacle.ImportTurnOversDao;
import com.itechro.cas.dao.finacle.InsuranceDetailsDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.facilitypaper.GuaranteeVolumes;
import com.itechro.cas.model.domain.finacle.CollateralDetails;
import com.itechro.cas.model.domain.finacle.ExportTurnovers;
import com.itechro.cas.model.domain.finacle.ImportTurnovers;
import com.itechro.cas.model.domain.finacle.InsuranceValuationDetails;
import com.itechro.cas.model.dto.finacle.VolumeKey;
import com.itechro.cas.model.dto.finacle.request.*;
import com.itechro.cas.model.dto.finacle.response.*;
import com.itechro.cas.model.dto.integration.request.finacle.ImportOutwardTurnOverRQ;
import com.itechro.cas.model.dto.integration.response.finacle.*;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.integration.IntegrationService;
import com.itechro.cas.service.lead.LeadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FinacleService {
    private static final Logger LOG = LoggerFactory.getLogger(LeadService.class);
    @Autowired
    private IntegrationService integrationService;
    @Value("${apps.cas.schema.name}")
    private String schemaName;

    @Value("${apps.integration.finacleGuaranteeVolumes.isCurrentDate}")
    private Boolean useCurrentDate;

    @Value("${apps.integration.finacleGuaranteeVolumes.finacleDBUpdatedDate}")
    private String finacleUpdatedDate;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    private FinacleInformationDao finacleInformationDao;

    @Autowired
    private InsuranceDetailsDao insuranceDetailsDao;

    @Autowired
    private CollateralDetailsDao collateralDetailsDao;

    @Autowired
    private ExportTurnOversDao exportTurnOverDao;

    @Autowired
    private ImportTurnOversDao importTurnOverDao;

    private String datePattern = "dd-MM-yyyy";

    @Transactional(propagation = Propagation.SUPPORTS)
    public FinacleExOutLimitsRSDTO getFinacleExOutLimits(String cusId, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get Finacle ExOut Limits service {} by {}", cusId, credentialsDTO);
        FinacleExOutLimitsRSDTO response =new FinacleExOutLimitsRSDTO();
        FinacleExOutLimitsRS results = null;

            try {
                results = integrationService.getFinacleExOutLimitsFromFinacle(cusId, credentialsDTO);

                List<LimitDetails> limits= new ArrayList<>();
                List<LoanDetails> loans= new ArrayList<>();


                for (LimitValues limit:results.getNodeDetails().getLimit_Values()) {
                    LimitDetails  tempLimit = new LimitDetails();

                    if(limit.getLPrefix().isEmpty() && limit.getLSuffix().isEmpty() ){
                        continue;
                    }
                    tempLimit.setId(limit.getLSuffix()+limit.getLPrefix());
                    tempLimit.setGrantedAmount(limit.getSancLimit());
                    tempLimit.setOutstandingAmount((limit.getLiab()));
                    tempLimit.setCurrencyType(limit.getCcyCode());
                    limits.add(tempLimit);
                }

                for (Account loan:results.getNot_Linked_AC().getAccount()) {
                    if(loan.getAcctNo().isEmpty()  ){
                        continue;
                    }
                    LoanDetails   tempLoan = new LoanDetails();

                    tempLoan.setId(loan.getAcctNo());
                    tempLoan.setGrantedAmount(loan.getSancLimit());
                    tempLoan.setOutstandingAmount((loan.getClrBal()));
                    tempLoan.setCurrencyType(loan.getCcyCode());
                    if (loan.getAcctNo().charAt(0) == '0') {
                        tempLoan.setLoanType("OD");
                    } else {
                        tempLoan.setLoanType("loan");
                    }
                    loans.add(tempLoan);
                }

                response.setLimits(limits);
                response.setLoans(loans);
                LOG.info("END: Get Existing and Outstanding detail List  service {} by {}", cusId, credentialsDTO);
            } catch (Exception e) {
                response=null;
                LOG.info("ERROR: Error occur while loading finacle detail List service :{} by {}", cusId, credentialsDTO, e);
            }


        LOG.info("END: Get Finacle ExOut Limits service {} By {}", cusId, credentialsDTO);
        return response;
    }



    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean IsWatchlisted(String cusId, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get watch list status service{} by {}", cusId, credentialsDTO);
        Boolean response = false ;
        IsWatchlistedRS results = null;

        try {
            results  = integrationService.isWatchlisted(cusId, credentialsDTO);
            if(results.getIsCustomerWatchListed().equals("y") || results.getIsCustomerWatchListed().equals("Y")){
            response =true;
            }
            else{
                response =false;
            }

            LOG.info("END: Get watch list status  {} by {}", cusId, credentialsDTO);
        } catch (Exception e) {
            response=false;
            LOG.info("ERROR: Error occur while loading watch list status  service :{} by {}", cusId, credentialsDTO, e);
        }

        LOG.info("END: Get watch list status service{} By {}", cusId, credentialsDTO);
        return response;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public GuaranteeVolumesRS getGuaranteeVolumesForCalenderYear(GuaranteeVolumesRQ guaranteeVolumesRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get gurantee volumes for caldenr year service {} by {}", guaranteeVolumesRQ.getCustId(), credentialsDTO);
        GuaranteeVolumesRS response = new GuaranteeVolumesRS()  ;
        FinacleGuaranteeVolumesRS results = null;

        String startDate = "01-01-"+ guaranteeVolumesRQ.getStartDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        String todayString;
        if(useCurrentDate){
            todayString = LocalDate.now().format(formatter);
        }
        else {
            todayString = finacleUpdatedDate;
        }
        LocalDate currentDate = LocalDate.now();
        String createdDate = currentDate.format(formatter);


        List<VolumeSummary> bgmVolumeSummary = new ArrayList<>();
        List<VolumeSummary> bgmVolumeSummaryString = new ArrayList<>();
        List<VolumeSummaryResult> bgmVolumeSummaryDouble = new ArrayList<>();
        List<VolumeSummaryResult> bgmVolumeSummaryDoubleTest = new ArrayList<>();

        List<VolumeSummary> bgmVolumeSummarySumOfMonths = new ArrayList<>() ;

//        List<VolumeSummary> bgmVolumeSummary;
        List<VolumeSummaryResult> bgmVolumeSummaryDoubleWithCreatedDate = new ArrayList<>();
        List<String[]> splittedMonths = new ArrayList<>();
        try {
            splittedMonths = splitDateRangeByMonth(startDate,todayString);
            for (String[] monthPair : splittedMonths){
                guaranteeVolumesRQ.setEndDate(monthPair[1]);
                guaranteeVolumesRQ.setStartDate(monthPair[0]);
                results  = integrationService.getGuaranteeVolumes(guaranteeVolumesRQ, credentialsDTO);

                bgmVolumeSummarySumOfMonths.addAll(results.getBgmVolumeSummary());
            }

            if (!bgmVolumeSummarySumOfMonths.isEmpty()) {
                for (VolumeSummary result :
                        bgmVolumeSummarySumOfMonths) {
                    VolumeSummary tempVol = new VolumeSummary();
                    if (result.getCnyCode() instanceof String) {
                        String str = (String) result.getCnyCode();
                        if (str.trim().isEmpty()) {
                            tempVol.setCnyCode(null);
                        } else {
                            tempVol.setCnyCode(str);
                        }
                    } else {
                        tempVol.setCnyCode(null);
                    }
                    tempVol.setVolume(result.getVolume());
                    tempVol.setEnDate(result.getEnDate());
                    tempVol.setStDate(result.getStDate());
                    bgmVolumeSummary.add(tempVol);
                }

                bgmVolumeSummaryDouble = aggregateVolumes(bgmVolumeSummary);
                Map<String, List<VolumeSummaryResult>> groupedByYear = bgmVolumeSummaryDouble.stream()
                        .collect(Collectors.groupingBy(bgm -> bgm.getYear()));
                List<VolumeSummaryResult> filteredList = groupedByYear.entrySet().stream()
                        .flatMap(entry -> {
                            List<VolumeSummaryResult> volumes = entry.getValue();

                            if (volumes.size() > 1) {
                                volumes = volumes.stream()
                                        .filter(volume -> !(volume.getCnyCode() == null && volume.getTotalVolume().equals("0.00")))
                                        .collect(Collectors.toList());
                            }
                            return volumes.stream();
                        })
                        .collect(Collectors.toList());
                // bgmVolumeSummaryDouble.sort((o1, o2) -> Integer.compare(o1.getYear(), o2.getYear()));

                for (VolumeSummaryResult volume : filteredList) {
                    volume.setCreatedDate(createdDate);
                    bgmVolumeSummaryDoubleWithCreatedDate.add(volume);
                }
                response.setBgmVolumeSummary(bgmVolumeSummaryDoubleWithCreatedDate);
            }
            else {
                response = null;
            }
            LOG.info("END: Get gurantee volumes for caldenr year service {} by {}", guaranteeVolumesRQ.getCustId(), credentialsDTO);
        } catch (Exception e) {
            response=null;
            LOG.info("ERROR: Error occur while Get gurantee volumes for caldenr year service  :{} by {}", guaranteeVolumesRQ.getCustId(), credentialsDTO, e);
        }

        LOG.info("END: Get gurantee volumes for caldenr year service {} By {}", guaranteeVolumesRQ.getCustId(), credentialsDTO);
        return response;
    }

    public static List<String[]> splitDateRangeByMonth(String startDateStr, String endDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);

        List<String[]> dateRanges = new ArrayList<>();

        LocalDate currentStartDate = startDate;
        while (!currentStartDate.isAfter(endDate)) {
            // Start of the month
            LocalDate currentEndDate = currentStartDate.withDayOfMonth(currentStartDate.lengthOfMonth());

            // Adjust end date if it goes beyond the range
            if (currentEndDate.isAfter(endDate)) {
                currentEndDate = endDate;
            }

            // Add the date range to the list
            dateRanges.add(new String[] {
                    currentStartDate.format(formatter),
                    currentEndDate.format(formatter)
            });

            // Move to the next month
            currentStartDate = currentStartDate.plusMonths(1).withDayOfMonth(1);
        }

        return dateRanges;
    }

    private List<VolumeSummaryResult> aggregateVolumes(List<VolumeSummary> volumeSummaries) {

        return volumeSummaries.stream()
                .collect(Collectors.groupingBy(v -> new VolumeKey(v.getCnyCode(), getYearFromDate(v.getStDate()))))
                .entrySet().stream()
                .map(entry -> {
                    VolumeKey key = entry.getKey();
                    double totalVolume = entry.getValue().stream()
                            .mapToDouble(v -> Double.parseDouble(v.getVolume()))
                            .sum();
                    VolumeSummaryResult volumeresult = new VolumeSummaryResult();
                    volumeresult.setYear(String.valueOf(key.getYear()));
                    volumeresult.setTotalVolume(String.format("%.2f", totalVolume));
                    volumeresult.setCnyCode(key.getCnyCode());
                    return volumeresult;
                })
                .collect(Collectors.toList());
    }

    private int getYearFromDate(String stDate) {
        return Integer.parseInt(stDate.split("-")[2]);
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public GuaranteeVolumesRS getGuaranteeVolumesForFinancialYear(GuaranteeVolumesRQ guaranteeVolumesRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get guarantee volumes for financial year {} by {}", guaranteeVolumesRQ.getCustId(), credentialsDTO);
        GuaranteeVolumesRS response = new GuaranteeVolumesRS()  ;
        FinacleGuaranteeVolumesRS results = null;

        String startDate = "01-04-"+ guaranteeVolumesRQ.getStartDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        String todayString ;
        if(useCurrentDate){
            todayString = LocalDate.now().format(formatter);
        }
        else {
            todayString = finacleUpdatedDate;
        }

        LocalDate currentDate = LocalDate.now();
        String createdDate = currentDate.format(formatter);


        guaranteeVolumesRQ.setEndDate(todayString);
        guaranteeVolumesRQ.setStartDate(startDate);
        List<VolumeSummary> bgmVolumeSummary = new ArrayList<>();
        List<VolumeSummary> bgmVolumeSummaryString = new ArrayList<>();
        List<VolumeSummaryResult> bgmVolumeSummaryDouble = new ArrayList<>();
        List<VolumeSummaryResult> bgmVolumeSummaryDoubleWithCreatedDate = new ArrayList<>();
        List<String[]> splittedMonths = new ArrayList<>();
        List<VolumeSummary> bgmVolumeSummarySumOfMonths = new ArrayList<>();

        try {

            splittedMonths = splitDateRangeByMonth(startDate,todayString);
            for (String[] monthPair : splittedMonths){
                guaranteeVolumesRQ.setEndDate(monthPair[1]);
                guaranteeVolumesRQ.setStartDate(monthPair[0]);

                results  = integrationService.getGuaranteeVolumes(guaranteeVolumesRQ, credentialsDTO);
                bgmVolumeSummarySumOfMonths.addAll(results.getBgmVolumeSummary());
            }


            if(!bgmVolumeSummarySumOfMonths.isEmpty()) {
                for (VolumeSummary result :
                        bgmVolumeSummarySumOfMonths) {
                    VolumeSummary tempVol = new VolumeSummary();
                    if (result.getCnyCode() instanceof String) {
                        String str = (String) result.getCnyCode();
                        if (str.trim().isEmpty()) {
                            tempVol.setCnyCode(null);
                        } else {
                            tempVol.setCnyCode(str);
                        }
                    } else {
                        tempVol.setCnyCode(null);
                    }
                    tempVol.setVolume(result.getVolume());
                    tempVol.setEnDate(result.getEnDate());
                    tempVol.setStDate(result.getStDate());
                    tempVol.setCreatedDate(createdDate);

                    bgmVolumeSummary.add(tempVol);
                }

                bgmVolumeSummaryDouble = calculateVolumesInFinancialYear(bgmVolumeSummary);
                // bgmVolumeSummaryDouble.sort((o1, o2) -> Integer.compare(o1.getYear(), o2.getYear()));

                for (VolumeSummaryResult volume : bgmVolumeSummaryDouble) {
                    volume.setCreatedDate(createdDate);
                    bgmVolumeSummaryDoubleWithCreatedDate.add(volume);
                }
                bgmVolumeSummaryDouble = filterVolumeItems(bgmVolumeSummaryDouble);

                response.setBgmVolumeSummary(bgmVolumeSummaryDouble);
            }
            else{
                response=null;
            }

            LOG.info("END: Get guarantee volumes for financial year  {} by {}", guaranteeVolumesRQ.getCustId(), credentialsDTO);
        } catch (Exception e) {
            response=null;
            LOG.info("ERROR: Error occur while loading guarantee volumes for financial year  :{} by {}", guaranteeVolumesRQ.getCustId(), credentialsDTO, e);
        }

        LOG.info("END:Get guarantee volumes for financial year service  {} By {}", guaranteeVolumesRQ.getCustId(), credentialsDTO);
        return response;
    }


    private static List<VolumeSummaryResult> calculateVolumesInFinancialYear(List<VolumeSummary> volumeSummaries) throws ParseException {
        Map<String, Map<String, Double>> financialYearVolumes = new HashMap<>();
        List<VolumeSummaryResult> bgVolumes = new ArrayList<>();
        for (VolumeSummary volumeSummary:
                volumeSummaries) {

            String stDate = volumeSummary.getStDate();
            String cnyCode = Optional.ofNullable(volumeSummary.getCnyCode())
                    .map(Object::toString)
                    .orElse(null);
            double volume = Double.parseDouble(volumeSummary.getVolume());


            String financialYear = getFinancialYear(stDate);


            financialYearVolumes.putIfAbsent(financialYear, new HashMap<>());


            Map<String, Double> cnyVolumes = financialYearVolumes.get(financialYear);
            cnyVolumes.put(cnyCode, cnyVolumes.getOrDefault(cnyCode, 0.00) + volume);
        }
        for (Map.Entry<String, Map<String, Double>> yearEntry : financialYearVolumes.entrySet()) {
            String financialYear = yearEntry.getKey();
            Map<String, Double> cnyVolumes = yearEntry.getValue();


            for (Map.Entry<String, Double> cnyEntry : cnyVolumes.entrySet()) {
                String cnyCode = cnyEntry.getKey();
                double totalVolume = cnyEntry.getValue();
                VolumeSummaryResult volumeresult = new VolumeSummaryResult();

                volumeresult.setTotalVolume(String.format("%.2f", totalVolume));
                volumeresult.setCnyCode(cnyCode);
                volumeresult.setYear(financialYear);
                bgVolumes.add(volumeresult);
            }
        }
        return bgVolumes;
    }

    public List<VolumeSummaryResult> filterVolumeItems(List<VolumeSummaryResult> data) {
        Map<String, List<VolumeSummaryResult>> groupedByYear = data.stream()
                .collect(Collectors.groupingBy(VolumeSummaryResult::getYear));

        List<VolumeSummaryResult> filteredData = groupedByYear.values().stream()
                .flatMap(yearItems -> {
                    if (yearItems.size() > 1) {
                         return yearItems.stream()
                                .filter(item -> !(item.getCnyCode() == null && "0.00".equals(item.getTotalVolume())));
                    } else {

                        return yearItems.stream();
                    }
                })
                .collect(Collectors.toList());

        return filteredData;
    }

    private static String getFinancialYear(String stDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = sdf.parse(stDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        if (month < 4) {
            year = year - 1;
        }
        return year + "-" + (year + 1);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean saveGuaranteeVolumesToDB(GuaranteeVolumeSaveRQ saveGuaranteeVolumeRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: save guarantee volumes {} by {}", saveGuaranteeVolumeRQ, credentialsDTO);
        Boolean saveStatus = false;
        List<VolumeSummary> bgmVolumeSummary = new ArrayList<>();
        GuaranteeVolumes bgmvolume = new GuaranteeVolumes();
        List<GuaranteeVolumes> volumesToDB = new ArrayList();
        try {

            saveStatus = finacleInformationDao.saveGuaranteeVolumesDB(saveGuaranteeVolumeRQ,credentialsDTO);


        } catch (Exception e) {

            LOG.info("ERROR: Error occur while save guarantee volumes service :{} by {}", saveGuaranteeVolumeRQ, credentialsDTO, e);
        }


        LOG.info("END: Save guarantee volumes service {} By {}", saveGuaranteeVolumeRQ, credentialsDTO);
        return saveStatus;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public GuaranteeVolumesRS getGuaranteeVolumesFromDB(GuaranteeVolumesDBRQ cusId, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get guarantee volumes from db {} by {}", cusId, credentialsDTO);
        GuaranteeVolumesRS results = new GuaranteeVolumesRS()  ;
        try {
            results = finacleInformationDao.getGuaranteeVolumesDB(cusId.getFacilityPaperId(), cusId.getCusId(), credentialsDTO);


        } catch (Exception e) {
            results=null;
            LOG.info("ERROR: Error occur while Get cGet guarantee volumes from db  :{} by {}", cusId, credentialsDTO, e);
        }


        LOG.info("END:Get guarantee volumes from db {} By {}", cusId, credentialsDTO);
        return results;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<String> getAllCustomerAccountsFromFinacle(String cusAccount, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get Account detail List {} by {}", cusAccount, credentialsDTO);
        FinacleExOutLimitsRSDTO response =new FinacleExOutLimitsRSDTO();
        cusAccountsRS results = null;
       List<String> accountList = new ArrayList<>();
        try {
            results = integrationService.getCustomerAccounts(cusAccount, credentialsDTO);

            for ( CasAaasData acc:results.getCasAaasData()) {
                if(acc.getSchmType().equals("SBA") || acc.getSchmType().equals("ODA")){
                    accountList.add(acc.getAcctNumber());
                }
            }
        } catch (Exception e) {
            response=null;
            LOG.info("ERROR: Error occur while Get customer accounts List for the account  :{} by {}", cusAccount, credentialsDTO, e);
        }


        LOG.info("END: Get customer accounts List for the account {} By {}", cusAccount, credentialsDTO);
        return accountList;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ExportTurnoverRS> getExportTurnoverForCalenderYear(ExportTurnOverRQ cusAccount, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get Export Turnover List {} by {}", cusAccount, credentialsDTO);
        String Year = cusAccount.getStartDate();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        String today = currentDate.format(formatter);
        String startDate = "01-01-"+ cusAccount.getStartDate();
        String endDate ="31-12-"+ cusAccount.getStartDate();
        cusAccount.setStartDate(startDate);
        cusAccount.setEndDate(endDate);
        ExportDcBillsTurnoverRS dcResults = new ExportDcBillsTurnoverRS();
        ExportInwardTurnoverRS inwardResult = new ExportInwardTurnoverRS();
        List<ExportTurnoverRS> exportTunroverList = new ArrayList<>();
        List<ExportTurnoverRS> exportTurnoverRS = new ArrayList<>();
        ExportTurnoverRS exportTurnoverRSs = new ExportTurnoverRS();

        List<ExportDcBillsTurnover> dcResultsWithMonths = new ArrayList<>();
        List<ExportInwardTurnover> inwardResultWithMonths = new ArrayList<>();
        List<String[]> splittedMonths = new ArrayList<>();
        try {
            splittedMonths = splitDateRangeByMonth(startDate,endDate);
            for (String[] monthPair : splittedMonths){
                cusAccount.setEndDate(monthPair[1]);
                cusAccount.setStartDate(monthPair[0]);

                dcResults = integrationService.getExportDcBillsTurnover(cusAccount, credentialsDTO);
                inwardResult = integrationService.getExportInwardTurnover(cusAccount,credentialsDTO);

                if (dcResults.getStatus().equals("Success") && (inwardResult.getStatus().equals("Success") || inwardResult.getFault().stream().anyMatch(obj -> obj.getDescription().contains("NO RECORDS FOUND")))) {
                    dcResultsWithMonths.addAll(dcResults.getResponseData());
                    inwardResultWithMonths.addAll(inwardResult.getTtTrnOverRecs());
                }
                else {
                    dcResultsWithMonths = new ArrayList<>();
                    inwardResultWithMonths = new ArrayList<>();
                    break;
                }
            }


                    for (ExportDcBillsTurnover data : dcResultsWithMonths)
                    {
                        ExportTurnoverRS tempExpo = new ExportTurnoverRS(cusAccount.getStartDate(),
                                data.getBillCrncyCode(),
                                Double.parseDouble(data.getBillAmt()),
                                Double.parseDouble(data.getConvertedAmt()),
                                data.getUnderLcFlg() == "Y" ? "EXPORT_BILL" : "EXPORT_DC",
                                today,
                                cusAccount.getAccountId()
                        );

                        exportTunroverList.add(tempExpo);

                    }
                    for (ExportInwardTurnover data : inwardResultWithMonths)
                    {
                        ExportTurnoverRS tempExpo = new ExportTurnoverRS(cusAccount.getStartDate(),
                                data.getCollectionCrncy(),
                                Double.parseDouble(data.getCollectionAmt()),
                                Double.parseDouble(data.getTurnoverLkr()),
                                "EXPORT_INWARD",
                                today,
                                cusAccount.getAccountId()
                        );

                        exportTunroverList.add(tempExpo);
                    }
                    LOG.info("END: Get Export Turnover List for the account {} by {}", credentialsDTO, credentialsDTO);
                    Map<String, Map<String, List<ExportTurnoverRS>>> result = groupAndAccumulate(exportTunroverList);


                    exportTurnoverRS = convertMapToList(result, Year, today, cusAccount.getAccountId());


            LOG.info("END: Get Export Turnover List for the account {} by {}", exportTunroverList, credentialsDTO);
        } catch (Exception e) {
            exportTurnoverRS=null;
            LOG.info("ERROR: Error occur while Get Export Turnover List for the account  :{} by {}", cusAccount, credentialsDTO, e);
        }


        LOG.info("END: Get Export Turnover List for the account {} By {}", cusAccount, credentialsDTO);
        return exportTurnoverRS;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public List<String> getCustomerAccounts(String accountRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get TT turnover Account for {} by {}", accountRQ, credentialsDTO);
        List<String> accountsFromFinacle = new ArrayList<>();
        List<String> accountsFromDB = new ArrayList<>();
        try {
            accountsFromFinacle =  getAllCustomerAccountsFromFinacle(accountRQ,credentialsDTO);
          //  accountsFromDB = finacleInformationDao.getTTTurnoverAccountsFromDB(ttTurnoverAccountrq.getFacilityPaperId(),credentialsDTO);

           accountsFromFinacle.removeAll(accountsFromDB);

        } catch (Exception e) {

            LOG.info("ERROR: Error occur while Get TT turnover Account for   :{} by {}", accountRQ, credentialsDTO, e);
        }


        LOG.info("END: Get TT turnover Account for  {} By {}", accountRQ, credentialsDTO);
        return accountsFromFinacle;
    }

//    @Transactional(propagation = Propagation.SUPPORTS)
//    public TTTurnoverAndAccountsRS getTTTurnoverDataAndAccounts(TTTurnoverDetailsRQ ttTurnoverAccountrq, CredentialsDTO credentialsDTO) {
//        LOG.info("START: Get Account detail List {} by {}", ttTurnoverAccountrq, credentialsDTO);
//        List<String> accounts = new ArrayList<>();
//        List<cusAccountDetailsDTORS> ttTurnoverDetails = new ArrayList<>();
//        TTTurnoverAndAccountsRS turnoverAndAccountsRS = new TTTurnoverAndAccountsRS();
//        try {
//                accounts = getTTTurnoverAccounts(ttTurnoverAccountrq,credentialsDTO);
//                ttTurnoverDetails = getTTTurnoverFromDB(ttTurnoverAccountrq,credentialsDTO);
//                turnoverAndAccountsRS.setAccounts(accounts);
//                turnoverAndAccountsRS.setTtTurnoverDetails(ttTurnoverDetails);
//            LOG.info("END: Get customer accounts List for the account {} by {}", ttTurnoverAccountrq, credentialsDTO);
//        } catch (Exception e) {
//
//            LOG.info("ERROR: Error occur while Get customer accounts List for the account  :{} by {}", ttTurnoverAccountrq, credentialsDTO, e);
//        }
//
//
//        LOG.info("END: Get customer accounts List for the account {} By {}", ttTurnoverAccountrq, credentialsDTO);
//        return turnoverAndAccountsRS;    }

//    @Transactional(propagation = Propagation.SUPPORTS)
//    public Boolean saveTTTurnoverDataToDB(saveTTTurnoverDetailsListRQ saveTTTurnoverDetailsRQ, CredentialsDTO credentialsDTO) {
//        LOG.info("START: Save TT Turnover Data to DB {} by {}", saveTTTurnoverDetailsRQ, credentialsDTO);
//        Boolean saveStatus = false;
//        List<cusAccountDetailsDTORS> ttTurnoverDetails = new ArrayList<>();
//        TTTurnoverAndAccountsRS turnoverAndAccountsRS = new TTTurnoverAndAccountsRS();
//        try {
//
//                saveStatus = finacleInformationDao.saveTTTurnoverDetailsDB(saveTTTurnoverDetailsRQ,credentialsDTO);
//
//
//        } catch (Exception e) {
//
//            LOG.info("ERROR: Error occur while Save TT Turnover Data to DB  :{} by {}", saveTTTurnoverDetailsRQ, credentialsDTO, e);
//        }
//
//
//        LOG.info("END: Save TT Turnover Data to DB {} By {}", saveTTTurnoverDetailsRQ, credentialsDTO);
//        return saveStatus;
//    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public List<InsurenceDetailsFromFinacleRS> getInsuranceDetails( InsuranceDetailsRQ cusId, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get Insurance Details {} by {}", cusId, credentialsDTO);
        InsuranceFinacleRS results = null;
        List<InsurenceDetailsFromFinacleRS>insuranceDetails = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        String dateAsString = currentDate.format(formatter);
        try {
            results = integrationService.getInsuranceValuaationDetails(cusId.getCusId(), credentialsDTO);

            for(InsuranceCollateral result : results.getSuccessResponse().getCollateral()) {

                InsurenceDetailsFromFinacleRS insuranceDetailH = new InsurenceDetailsFromFinacleRS();
                insuranceDetailH.setCollateralType(getCollateralType(result.getSecu_type_ind()));
                insuranceDetailH.setInsuranceValuationExpireDates(result.getValuation_expiryDate());
                insuranceDetailH.setCreatedDate(dateAsString);
                List<InsuranceDetails> insuranceDetails1List = new ArrayList<>();
                for(FinacleInsuranceDetails insurance : result.getInsuranceDetails()){
                    InsuranceDetails insuranceDetail = new InsuranceDetails();

                    insuranceDetail.setInsuranceType(insurance.getInsu_type());
                    insuranceDetail.setInsurerDetails(insurance.getCompany_name());
                    insuranceDetail.setPolicyNo(insurance.getInsu_ref_num());
                    insuranceDetail.setRiskCoverStartDate(insurance.getRisk_cover_start_date());
                    insuranceDetail.setRiskCoverEndDate(insurance.getRisk_cover_end_date());
                    insuranceDetail.setPremiumAmount(insurance.getPremium_amt());
                    insuranceDetail.setItemsInsured(insurance.getItems_insurd());
                    insuranceDetail.setPolicyAmount(insurance.getPolicy_amt());

                    insuranceDetails1List.add(insuranceDetail);
                }

                insuranceDetailH.setInsuranceDetails(insuranceDetails1List);

                insuranceDetails.add(insuranceDetailH);
            }

        } catch (Exception e) {
            insuranceDetails = new ArrayList<>();
            LOG.info("ERROR: Error occur while Get Insurance Details  :{} by {}", cusId, credentialsDTO, e);
        }


        LOG.info("END: Get customer accounts List for the account {} By {}", cusId, credentialsDTO);
        return insuranceDetails;
    }

    private String  getCollateralType (String type) {
        switch (type) {
            case "I":
                return "Immovable Property";
            case "M":
                return "Machinery";
            case "T":
                return "Stocks";
            case "V":
                return "Vehicle";
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public Boolean saveInsuranceValuationsToDB(SaveCollateralDetailsRQ saveInsuranceValuationRQ, String cusID, CredentialsDTO credentialsDTO) {
        LOG.info("START: Save Insurance Details service {} by {}", saveInsuranceValuationRQ, credentialsDTO);
        Boolean saveStatus = false;
        List<CollateralDetails> collateralDetails = new ArrayList<>();
        List<InsuranceValuationDetails> insuranceValuationDetails = new ArrayList<>();
        try {

            if(saveInsuranceValuationRQ != null) {
                collateralDetailsDao.deleteByFacilityPaperIdAndCustomerFinacleId(saveInsuranceValuationRQ.getFacilityPaperId(), cusID);
                for(InsurenceDetailsFromFinacleRS collateral : saveInsuranceValuationRQ.getSaveInsuranceValuationRQ()) {
                    CollateralDetails collateralDetail = new CollateralDetails();
                    String uniqueRQId=UUID.randomUUID().toString();

                    collateralDetail.setCollateralType(collateral.getCollateralType());
                    collateralDetail.setInsuranceValuationExpireDates(collateral.getInsuranceValuationExpireDates());
                    collateralDetail.setCollateralId(uniqueRQId);
                    collateralDetail.setFacilityPaperId(saveInsuranceValuationRQ.getFacilityPaperId());
                    collateralDetail.setCustomerFinacleId(cusID);
                    collateralDetail.setCreatedDate(collateral.getCreatedDate());
                    collateralDetailsDao.saveAndFlush(collateralDetail);

                    for(InsuranceDetails insurance : collateral.getInsuranceDetails()){
                        InsuranceValuationDetails insuranceDetail = new InsuranceValuationDetails();

                        insuranceDetail.setCollateralDetails(collateralDetail);
                        insuranceDetail.setInsuranceType(insurance.getInsuranceType());
                        insuranceDetail.setInsurerDetails(insurance.getInsurerDetails());
                        insuranceDetail.setPolicyNo(insurance.getPolicyNo());
                        insuranceDetail.setRiskCoverStartDate(insurance.getRiskCoverStartDate());
                        insuranceDetail.setRiskCoverEndDate(insurance.getRiskCoverEndDate());
                        insuranceDetail.setPremiumAmount(insurance.getPremiumAmount());
                        insuranceDetail.setItemsInsured(insurance.getItemsInsured());
                        insuranceDetail.setPolicyAmount(insurance.getPolicyAmount());

                        insuranceValuationDetails.add(insuranceDetail);
                        insuranceDetailsDao.saveAndFlush(insuranceDetail);
                    }
                    collateralDetails.add(collateralDetail);

                }

                saveStatus = true;


            }
            LOG.info("END: Save Insurance Details service {} by {}", saveInsuranceValuationRQ, credentialsDTO);
        } catch (Exception e) {
            saveStatus =false;

            LOG.info("ERROR: Error occur while Save Insurance Details service :{} by {}", saveInsuranceValuationRQ, credentialsDTO, e);
        }


        LOG.info("END: Save Insurance Details service {} By {}", saveInsuranceValuationRQ, credentialsDTO);
        return saveStatus;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public List<InsurenceDetailsFromFinacleRS> getCollateralAndInsuranceDetails(InsuranceDetailsRQ insuranceDetailsRQ, Boolean isRefresh, CredentialsDTO credentialsDTO) {
        LOG.info("START: get collateral details {} by {}", insuranceDetailsRQ, credentialsDTO);
        List<CollateralDetails> results = new ArrayList<>();
        List<InsurenceDetailsFromFinacleRS> insurenceDetailsFromFinacleRS = new ArrayList<>();


        try {
            insurenceDetailsFromFinacleRS = new ArrayList<>();
            if(!isRefresh) {
              results = collateralDetailsDao.findByFacilityPaperIdWithInsuranceDetails(insuranceDetailsRQ.getFacilityPaperId(), insuranceDetailsRQ.getCusId());
            }
            for (CollateralDetails col : results) {
                InsurenceDetailsFromFinacleRS insuranceDetails = new InsurenceDetailsFromFinacleRS();
                insuranceDetails.setCollateralType(col.getCollateralType());
                insuranceDetails.setInsuranceValuationExpireDates(col.getInsuranceValuationExpireDates());
                insuranceDetails.setCreatedDate(col.getCreatedDate());
                List<InsuranceDetails> insuranceDetailsLIst = new ArrayList<>();

                for (InsuranceValuationDetails ins : col.getInsuranceValuationDetails()) {
                    InsuranceDetails insuranceValuationDetails = new InsuranceDetails();

                    insuranceValuationDetails.setInsuranceType(ins.getInsuranceType());
                    insuranceValuationDetails.setInsurerDetails(ins.getInsurerDetails());
                    insuranceValuationDetails.setPolicyNo(ins.getPolicyNo());
                    insuranceValuationDetails.setRiskCoverStartDate(ins.getRiskCoverStartDate());
                    insuranceValuationDetails.setRiskCoverEndDate(ins.getRiskCoverEndDate());
                    insuranceValuationDetails.setPremiumAmount(ins.getPremiumAmount());
                    insuranceValuationDetails.setItemsInsured(ins.getItemsInsured());
                    insuranceValuationDetails.setPolicyAmount(ins.getPolicyAmount());
                    insuranceDetailsLIst.add(insuranceValuationDetails);

                }
                insuranceDetails.setInsuranceDetails(insuranceDetailsLIst);
                insurenceDetailsFromFinacleRS.add(insuranceDetails);
            }

            if (results.size() == 0){
                insurenceDetailsFromFinacleRS =  getInsuranceDetails(insuranceDetailsRQ,credentialsDTO);

                SaveCollateralDetailsRQ saveInsuranceValuationRQ = new SaveCollateralDetailsRQ();
                saveInsuranceValuationRQ.setFacilityPaperId(insuranceDetailsRQ.getFacilityPaperId());


                saveInsuranceValuationRQ.setSaveInsuranceValuationRQ(insurenceDetailsFromFinacleRS);
                saveInsuranceValuationsToDB(saveInsuranceValuationRQ,insuranceDetailsRQ.getCusId(),credentialsDTO);

            }
            LOG.info("END: Get collateral details {} by {}", insuranceDetailsRQ, credentialsDTO);
        } catch (Exception e) {
            insurenceDetailsFromFinacleRS = null;
            LOG.info("ERROR: Error occur while Get customer accounts List for the account  :{} by {}", insuranceDetailsRQ, credentialsDTO, e);
        }
        LOG.info("END: Get collateral details {} By {}", insuranceDetailsRQ, credentialsDTO);
        return insurenceDetailsFromFinacleRS;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<InsurenceDetailsFromFinacleRS> getCollateralAndInsuranceDetailsDB(InsuranceDetailsRQ insuranceDetailsRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: get collateral details {} by {}", insuranceDetailsRQ, credentialsDTO);
        List<CollateralDetails> results = new ArrayList<>();
        List<InsurenceDetailsFromFinacleRS> insurenceDetailsFromFinacleRS = new ArrayList<>();


        try {
            insurenceDetailsFromFinacleRS = new ArrayList<>();

            results = collateralDetailsDao.findByFacilityPaperIdWithInsuranceDetails(insuranceDetailsRQ.getFacilityPaperId(), insuranceDetailsRQ.getCusId());

            for (CollateralDetails col : results) {
                InsurenceDetailsFromFinacleRS insuranceDetails = new InsurenceDetailsFromFinacleRS();
                insuranceDetails.setCollateralType(col.getCollateralType());
                insuranceDetails.setInsuranceValuationExpireDates(col.getInsuranceValuationExpireDates());
                insuranceDetails.setCreatedDate(col.getCreatedDate());
                List<InsuranceDetails> insuranceDetailsLIst = new ArrayList<>();

                for (InsuranceValuationDetails ins : col.getInsuranceValuationDetails()) {
                    InsuranceDetails insuranceValuationDetails = new InsuranceDetails();

                    insuranceValuationDetails.setInsuranceType(ins.getInsuranceType());
                    insuranceValuationDetails.setInsurerDetails(ins.getInsurerDetails());
                    insuranceValuationDetails.setPolicyNo(ins.getPolicyNo());
                    insuranceValuationDetails.setRiskCoverStartDate(ins.getRiskCoverStartDate());
                    insuranceValuationDetails.setRiskCoverEndDate(ins.getRiskCoverEndDate());
                    insuranceValuationDetails.setPremiumAmount(ins.getPremiumAmount());
                    insuranceValuationDetails.setItemsInsured(ins.getItemsInsured());
                    insuranceValuationDetails.setPolicyAmount(ins.getPolicyAmount());
                    insuranceDetailsLIst.add(insuranceValuationDetails);

                }
                insuranceDetails.setInsuranceDetails(insuranceDetailsLIst);
                insurenceDetailsFromFinacleRS.add(insuranceDetails);
            }


            LOG.info("END: Get collateral details {} by {}", insuranceDetailsRQ, credentialsDTO);
        } catch (Exception e) {
            insurenceDetailsFromFinacleRS = null;
            LOG.info("ERROR: Error occur while Get customer accounts List for the account  :{} by {}", insuranceDetailsRQ, credentialsDTO, e);
        }
        LOG.info("END: Get collateral details {} By {}", insuranceDetailsRQ, credentialsDTO);
        return insurenceDetailsFromFinacleRS;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean getInsuranceExpireAvailability(InsuranceDetailsRQ insuranceDetailsRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: get collateral details {} by {}", insuranceDetailsRQ, credentialsDTO);
        List<CollateralDetails> results = new ArrayList<>();
        List<InsurenceDetailsFromFinacleRS> insurenceDetailsFromFinacleRS = new ArrayList<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        boolean hasExpiredInsurance = false;
        try {
            insurenceDetailsFromFinacleRS = new ArrayList<>();

            results = collateralDetailsDao.findByFacilityPaperIdInsuranceDetials(insuranceDetailsRQ.getFacilityPaperId());

            for (CollateralDetails col : results) {
                InsurenceDetailsFromFinacleRS insuranceDetails = new InsurenceDetailsFromFinacleRS();
                insuranceDetails.setCollateralType(col.getCollateralType());
                insuranceDetails.setInsuranceValuationExpireDates(col.getInsuranceValuationExpireDates());
                insuranceDetails.setCreatedDate(col.getCreatedDate());
                List<InsuranceDetails> insuranceDetailsLIst = new ArrayList<>();

                for (InsuranceValuationDetails ins : col.getInsuranceValuationDetails()) {
                    InsuranceDetails insuranceValuationDetails = new InsuranceDetails();

                    insuranceValuationDetails.setInsuranceType(ins.getInsuranceType());
                    insuranceValuationDetails.setInsurerDetails(ins.getInsurerDetails());
                    insuranceValuationDetails.setPolicyNo(ins.getPolicyNo());
                    insuranceValuationDetails.setRiskCoverStartDate(ins.getRiskCoverStartDate());
                    insuranceValuationDetails.setRiskCoverEndDate(ins.getRiskCoverEndDate());
                    insuranceValuationDetails.setPremiumAmount(ins.getPremiumAmount());
                    insuranceValuationDetails.setItemsInsured(ins.getItemsInsured());
                    insuranceValuationDetails.setPolicyAmount(ins.getPolicyAmount());
                    insuranceDetailsLIst.add(insuranceValuationDetails);

                }
                insuranceDetails.setInsuranceDetails(insuranceDetailsLIst);
                insurenceDetailsFromFinacleRS.add(insuranceDetails);
            }

            hasExpiredInsurance = insurenceDetailsFromFinacleRS.stream().anyMatch(insurance -> {
                String insuranceExpireDate = insurance.getInsuranceValuationExpireDates();

                try {

                    LocalDate expirationDate = LocalDate.parse(insuranceExpireDate, formatter);

                    if (expirationDate.isBefore(today)) {
                        return true;
                    }
                } catch (Exception e) {

                    return false;
                }

                if (insurance.getInsuranceDetails() != null) {
                    return insurance.getInsuranceDetails().stream().anyMatch(detail -> {
                        try {
                            String riskEndDateStr = detail.getRiskCoverEndDate();
                            LocalDate riskEndDate = LocalDate.parse(riskEndDateStr, formatter);

                            return riskEndDate.isBefore(today);
                        } catch (Exception e) {

                            return false;
                        }
                    });
                }
                return false;
            });


            LOG.info("END: Get collateral details {} by {}", insuranceDetailsRQ, credentialsDTO);
        } catch (Exception e) {
            hasExpiredInsurance = false;
            LOG.info("ERROR: Error occur while Get customer accounts List for the account  :{} by {}", insuranceDetailsRQ, credentialsDTO, e);
        }
        LOG.info("END: Get collateral details {} By {}", insuranceDetailsRQ, credentialsDTO);

        return hasExpiredInsurance;
    }


    public <T extends Turnover> Map<String, Map<String, List<T>>> groupAndAccumulate(List<T> exportList) {
        Map<String, Map<String, List<T>>> groupedTO = exportList.stream()
                .collect(Collectors.groupingBy(
                        T::getTurnOverType,
                        Collectors.groupingBy(T::getBillCurrencyCode)
                ));

        return groupedTO;
    }

    public <T extends Turnover> List<T> convertMapToList(Map<String, Map<String,List<T>>> result, String year,String today,String foracid) {
        List<T> exportTurnoverList = new ArrayList<>();

        try {
            for (Map.Entry<String, Map<String, List<T>>> entry : result.entrySet()) {
                String exportType = entry.getKey();
                Map<String, List<T>> currencyMap = entry.getValue();


                for (Map.Entry<String, List<T>> innerEntry : currencyMap.entrySet()) {
                    List<T> turnoverRS = innerEntry.getValue();
                    String currencyCode = innerEntry.getKey();

                    double totalBillAmount = turnoverRS.stream()
                            .mapToDouble(T::getBillAmount)
                            .sum();

                    double totalConvertedBillAmount = turnoverRS.stream()
                            .mapToDouble(T::getConvertedAmount)
                            .sum();

                    ConTurnoverRS tempExport = new ConTurnoverRS(
                            year,
                            currencyCode,
                            totalBillAmount,
                            totalConvertedBillAmount,
                            exportType,
                            today,
                            foracid);

                    exportTurnoverList.add((T) tempExport);
                }

            }
        }
        catch (Exception e){
            exportTurnoverList = null;
        }
        return exportTurnoverList;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ExportTurnoverRS> getExportTurnoverForFinancialYear(ExportTurnOverRQ cusAccount, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get Export Turnover List {} by {}", cusAccount, credentialsDTO);
        String Year = cusAccount.getStartDate();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        String today = currentDate.format(formatter);
        String startDate = "01-04-"+ cusAccount.getStartDate();
        String startYear = cusAccount.getStartDate();
        String endYear;
        try{
            endYear =Integer.toString((Integer.parseInt(cusAccount.getStartDate())+1));
        }
        catch (Exception e ){
            LOG.info("ERROR: Error occur while Get Export Turnover List for the account  :{} by {}", cusAccount, credentialsDTO, e);
            return null;
        }
        String endDate ="31-03-"+ endYear;
        cusAccount.setStartDate(startDate);
        cusAccount.setEndDate(endDate);
        ExportDcBillsTurnoverRS dcResults = new ExportDcBillsTurnoverRS();
        ExportInwardTurnoverRS inwardResult = new ExportInwardTurnoverRS();
        List<ExportTurnoverRS> exportTunroverList = new ArrayList<>();
        List<ExportTurnoverRS> exportTurnoverRS = new ArrayList<>();
        ExportTurnoverRS exportTurnoverRSs = new ExportTurnoverRS();
        List<ExportDcBillsTurnover> dcResultsWithMonths = new ArrayList<>();
        List<ExportInwardTurnover> inwardResultWithMonths = new ArrayList<>();
        List<String[]> splittedMonths = new ArrayList<>();

        try {

            splittedMonths = splitDateRangeByMonth(startDate,endDate);
            for (String[] monthPair : splittedMonths){
                cusAccount.setEndDate(monthPair[1]);
                cusAccount.setStartDate(monthPair[0]);

                dcResults = integrationService.getExportDcBillsTurnover(cusAccount, credentialsDTO);
                inwardResult = integrationService.getExportInwardTurnover(cusAccount,credentialsDTO);

                if (dcResults.getStatus().equals("Success") && (inwardResult.getStatus().equals("Success") || inwardResult.getFault().stream().anyMatch(obj -> obj.getDescription().contains("NO RECORDS FOUND")))) {
                    dcResultsWithMonths.addAll(dcResults.getResponseData());
                    inwardResultWithMonths.addAll(inwardResult.getTtTrnOverRecs());
                }
                else {
                    dcResultsWithMonths = new ArrayList<>();
                    inwardResultWithMonths = new ArrayList<>();
                    break;
                }
            }


                    for (ExportDcBillsTurnover data : dcResultsWithMonths)
                    {
                        ExportTurnoverRS tempExpo = new ExportTurnoverRS(cusAccount.getStartDate(),
                                data.getBillCrncyCode(),
                                Double.parseDouble(data.getBillAmt()),
                                Double.parseDouble(data.getConvertedAmt()),
                                data.getUnderLcFlg() == "Y" ? "EXPORT_BILL" : "EXPORT_DC",
                                today,
                                cusAccount.getAccountId()
                        );

                        exportTunroverList.add(tempExpo);

                    }
                    for (ExportInwardTurnover data : inwardResultWithMonths)
                    {
                        ExportTurnoverRS tempExpo = new ExportTurnoverRS(cusAccount.getStartDate(),
                                data.getCollectionCrncy(),
                                Double.parseDouble(data.getCollectionAmt()),
                                Double.parseDouble(data.getTurnoverLkr()),
                                "EXPORT_INWARD",
                                today,
                                cusAccount.getAccountId()
                        );

                        exportTunroverList.add(tempExpo);
                    }
                    LOG.info("END: Get Export Turnover List for the account {} by {}", credentialsDTO, credentialsDTO);
                    Map<String, Map<String, List<ExportTurnoverRS>>> result = groupAndAccumulate(exportTunroverList);

                    exportTurnoverRS = convertMapToList(result, startYear+"-"+endYear, today, cusAccount.getAccountId());



            LOG.info("END: Get Export Turnover List for the account {} by {}", exportTunroverList, credentialsDTO);
        } catch (Exception e) {
            exportTurnoverRS=null;
            LOG.info("ERROR: Error occur while Get Export Turnover List for the account  :{} by {}", cusAccount, credentialsDTO, e);
        }


        LOG.info("END: Get Export Turnover List for the account {} By {}", cusAccount, credentialsDTO);
        return exportTurnoverRS;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ImportTurnoverRS> getImportTurnoverForCalenderYear(ImportTurnOverRQ cusAccount, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get Import T/O for Calender year {} by {}", cusAccount, credentialsDTO);
        String Year = cusAccount.getStartDate();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        String today = currentDate.format(formatter);
        String startDate = "01-01-"+ cusAccount.getStartDate();
        String endDate ="31-12-"+ cusAccount.getStartDate();
        cusAccount.setStartDate(startDate);
        cusAccount.setEndDate(endDate);

        ImportCollectionTurnoverRS collectionResults = new ImportCollectionTurnoverRS();
        ImportDCTurnoverRS dcResults = new ImportDCTurnoverRS();
        ImportOutwardTurnoverRS onwardResult = new ImportOutwardTurnoverRS();

        List<ImportTurnoverRS> exportTunroverList = new ArrayList<>();
        List<ImportTurnoverRS> exportTurnoverRS = new ArrayList<>();
        ExportTurnoverRS exportTurnoverRSs = new ExportTurnoverRS();

        List<ImportCollectionTurnover> collectionResultsWithMonths = new ArrayList<>();
        List<ImportDCTurnover> dcResultsWithMonths = new ArrayList<>();
        List<ImportOutwardTurnover> onwardResultWithMonths = new ArrayList<>();


        List<String[]> splittedMonths = new ArrayList<>();

        try {
            splittedMonths = splitDateRangeByMonth(startDate,endDate);
            for (String[] monthPair : splittedMonths){
                cusAccount.setEndDate(monthPair[1]);
                cusAccount.setStartDate(monthPair[0]);

                collectionResults = integrationService.getImportCollectionTurnover(cusAccount,credentialsDTO);
                dcResults = integrationService.getImportDCTurnover(cusAccount, credentialsDTO);
                onwardResult = integrationService.getImportOutwardTurnover(cusAccount,credentialsDTO);


                if (dcResults.getStatus().equals("Success")&& collectionResults.getStatus().equals("Success") && (onwardResult.getStatus().equals("Success") || onwardResult.getFault().stream().anyMatch(obj -> obj.getDescription().contains("NO RECORDS FOUND"))))
                {
                    dcResultsWithMonths.addAll(dcResults.getImpDCTORecs());
                    collectionResultsWithMonths.addAll(collectionResults.getImpColRecs());
                    onwardResultWithMonths.addAll(onwardResult.getTtTrnOverRecs());
                }
                else {
                    dcResultsWithMonths= new ArrayList<>();
                    collectionResultsWithMonths= new ArrayList<>();;
                    onwardResultWithMonths= new ArrayList<>();;
                    break;
                }
            }



                    for (ImportDCTurnover data : dcResultsWithMonths)
                    {
                        ImportTurnoverRS tempExpo = new ImportTurnoverRS(cusAccount.getStartDate(),
                                data.getCrncyCode(),
                                Double.parseDouble(data.getEventAmount()),
                                (Double.parseDouble(data.getEventAmount()) * Double.parseDouble(data.getRate())),
                                 "IMPORT_DC",
                                today,
                                cusAccount.getAccountId()
                        );

                        exportTunroverList.add(tempExpo);

                    }
                    for (ImportCollectionTurnover data : collectionResultsWithMonths)
                    {
                        ImportTurnoverRS tempExpo = new ImportTurnoverRS(cusAccount.getStartDate(),
                                data.getBillCRNCYCode(),
                                Double.parseDouble(data.getBillAmt()),
                                Double.parseDouble(data.getConvertedAmt()),
                                "IMPORT_BILL",
                                today,
                                cusAccount.getAccountId()
                        );

                        exportTunroverList.add(tempExpo);

                    }
                    for (ImportOutwardTurnover data : onwardResultWithMonths)
                    {
                        ImportTurnoverRS tempExpo = new ImportTurnoverRS(cusAccount.getStartDate(),
                                data.getTtCrncy(),
                                Double.parseDouble(data.getTtAmt()),
                                Double.parseDouble(data.getLkrAmount()),
                                "IMPORT_OUTWARD",
                                today,
                                cusAccount.getAccountId()
                        );

                        exportTunroverList.add(tempExpo);
                    }
                    LOG.info("END: Get Import T/O for Calender year {} by {}", credentialsDTO, credentialsDTO);
                    Map<String, Map<String, List<ImportTurnoverRS>>> result = groupAndAccumulate(exportTunroverList);

//            exportTurnoverRS = convertMapToList(result,today,Year);
                    exportTurnoverRS = convertMapToList(result, Year, today, cusAccount.getAccountId());



            LOG.info("END: Get Import T/O for Calender year {} by {}", exportTunroverList, credentialsDTO);
        } catch (Exception e) {
            exportTurnoverRS=null;
            LOG.info("ERROR: Error occur while Get Import T/O for Calender year  :{} by {}", cusAccount, credentialsDTO, e);
        }


        LOG.info("END: Get Import T/O for Calender year for the account {} By {}", cusAccount, credentialsDTO);

        return exportTurnoverRS;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ImportTurnoverRS> getImportTurnoverForFinancialYear(ImportTurnOverRQ cusAccount, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get Import T/O for Financial year List {} by {}", cusAccount, credentialsDTO);
        String Year = cusAccount.getStartDate();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        String today = currentDate.format(formatter);
        String startDate = "01-04-"+ cusAccount.getStartDate();

        String startYear = cusAccount.getStartDate();
        String endYear;
        try{
            endYear =Integer.toString((Integer.parseInt(cusAccount.getStartDate())+1));
        }
        catch (Exception e ){
            LOG.info("ERROR: Error occur while Get Import T/O for Financial year for the account  :{} by {}", cusAccount, credentialsDTO, e);
            return null;
        }
        String endDate ="31-03-"+ endYear;


        cusAccount.setStartDate(startDate);
        cusAccount.setEndDate(endDate);

        ImportCollectionTurnoverRS collectionResults = new ImportCollectionTurnoverRS();
        ImportDCTurnoverRS dcResults = new ImportDCTurnoverRS();
        ImportOutwardTurnoverRS onwardResult = new ImportOutwardTurnoverRS();

        List<ImportTurnoverRS> exportTunroverList = new ArrayList<>();
        List<ImportTurnoverRS> exportTurnoverRS = new ArrayList<>();
        ExportTurnoverRS exportTurnoverRSs = new ExportTurnoverRS();

        List<ImportCollectionTurnover> collectionResultsWithMonths = new ArrayList<>();
        List<ImportDCTurnover> dcResultsWithMonths = new ArrayList<>();
        List<ImportOutwardTurnover> onwardResultWithMonths = new ArrayList<>();


        List<String[]> splittedMonths = new ArrayList<>();

        try {

            splittedMonths = splitDateRangeByMonth(startDate,endDate);
            for (String[] monthPair : splittedMonths){
                cusAccount.setEndDate(monthPair[1]);
                cusAccount.setStartDate(monthPair[0]);

                collectionResults = integrationService.getImportCollectionTurnover(cusAccount,credentialsDTO);
                dcResults = integrationService.getImportDCTurnover(cusAccount, credentialsDTO);
                onwardResult = integrationService.getImportOutwardTurnover(cusAccount,credentialsDTO);


                if (dcResults.getStatus().equals("Success")&& collectionResults.getStatus().equals("Success") && (onwardResult.getStatus().equals("Success") || onwardResult.getFault().stream().anyMatch(obj -> obj.getDescription().contains("NO RECORDS FOUND"))))
                {
                    dcResultsWithMonths.addAll(dcResults.getImpDCTORecs());
                    collectionResultsWithMonths.addAll(collectionResults.getImpColRecs());
                    onwardResultWithMonths.addAll(onwardResult.getTtTrnOverRecs());
                }
                else {
                    dcResultsWithMonths= new ArrayList<>();
                    collectionResultsWithMonths= new ArrayList<>();;
                    onwardResultWithMonths= new ArrayList<>();;
                    break;
                }
            }



                    for (ImportDCTurnover data : dcResultsWithMonths)
                    {
                        ImportTurnoverRS tempExpo = new ImportTurnoverRS(cusAccount.getStartDate(),
                                data.getCrncyCode(),
                                Double.parseDouble(data.getEventAmount()),
                                (Double.parseDouble(data.getEventAmount()) * Double.parseDouble(data.getRate())),
                                "IMPORT_DC",
                                today,
                                cusAccount.getAccountId()
                        );

                        exportTunroverList.add(tempExpo);

                    }
                    for (ImportCollectionTurnover data : collectionResultsWithMonths)
                    {
                        ImportTurnoverRS tempExpo = new ImportTurnoverRS(cusAccount.getStartDate(),
                                data.getBillCRNCYCode(),
                                Double.parseDouble(data.getBillAmt()),
                                Double.parseDouble(data.getConvertedAmt()),
                                "IMPORT_BILL",
                                today,
                                cusAccount.getAccountId()
                        );

                        exportTunroverList.add(tempExpo);

                    }
                    for (ImportOutwardTurnover data : onwardResultWithMonths)
                    {
                        ImportTurnoverRS tempExpo = new ImportTurnoverRS(cusAccount.getStartDate(),
                                data.getTtCrncy(),
                                Double.parseDouble(data.getTtAmt()),
                                Double.parseDouble(data.getLkrAmount()),
                                "IMPORT_OUTWARD",
                                today,
                                cusAccount.getAccountId()
                        );

                        exportTunroverList.add(tempExpo);
                    }
                    LOG.info("END: Get Import T/O for Financial year List for the account {} by {}", credentialsDTO, credentialsDTO);
                              Map<String, Map<String, List<ImportTurnoverRS>>> result = groupAndAccumulate(exportTunroverList);

//            exportTurnoverRS = convertMapToList(result,today,Year);
                    exportTurnoverRS = convertMapToList(result, startYear+"-"+endYear, today, cusAccount.getAccountId());

            LOG.info("END: Get Import T/O for Financial year List for the account {} by {}", exportTunroverList, credentialsDTO);
        } catch (Exception e) {
            exportTurnoverRS=null;
            LOG.info("ERROR: Error occur while Get Import T/O for Financial year List for the account  :{} by {}", cusAccount, credentialsDTO, e);
        }


        LOG.info("END: Get Import T/O for Financial year List for the account {} By {}", cusAccount, credentialsDTO);

        return exportTurnoverRS;
    }



//    @Transactional(propagation = Propagation.SUPPORTS)
//    public Boolean saveExportTurnOverToDB(ExportTurnoverSaveRQ saveGuaranteeVolumeRQ, CredentialsDTO credentialsDTO) {
//        LOG.info("START: save guarantee volumes {} by {}", saveGuaranteeVolumeRQ, credentialsDTO);
//        Boolean saveStatus = false;
//        List<VolumeSummary> bgmVolumeSummary = new ArrayList<>();
//        GuaranteeVolumes bgmvolume = new GuaranteeVolumes();
//        List<GuaranteeVolumes> volumesToDB = new ArrayList();
//        try {
//
//            saveStatus = finacleInformationDao.saveGuaranteeVolumesDB(saveGuaranteeVolumeRQ,credentialsDTO);
//
//
//        } catch (Exception e) {
//
//            LOG.info("ERROR: Error occur while save guarantee volumes service :{} by {}", saveGuaranteeVolumeRQ, credentialsDTO, e);
//        }
//
//
//        LOG.info("END: Save guarantee volumes service {} By {}", saveGuaranteeVolumeRQ, credentialsDTO);
//        return saveStatus;
//    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public Boolean saveExportTurnOverToDB(ExportTurnoverSaveRQ exportTurnoverSaveRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START:  Save Export T/O service {} by {}", exportTurnoverSaveRQ, credentialsDTO);
        Boolean saveStatus = false;
        List<ExportTurnovers> importTurnOvers = new ArrayList<>();

        try {

            if(exportTurnoverSaveRQ != null) {
                exportTurnOverDao.deleteByFacilityPaperIdAndCusId(exportTurnoverSaveRQ.getFacilityPaperId(),exportTurnoverSaveRQ.getCusId() );
                for(ExportTurnoverDataRQ exportTO : exportTurnoverSaveRQ.getExportTurnOverData()) {

                    ExportTurnovers exportTurnovers = new ExportTurnovers();
                    String uniqueRQId=UUID.randomUUID().toString();

                    exportTurnovers.setBillAmount(exportTO.getBillAmount());
                    exportTurnovers.setCurencyCode(exportTO.getBillCurrencyCode());
                    exportTurnovers.setConvertedAmount(exportTO.getConvertedAmount());
                    exportTurnovers.setFacilityPaperId(exportTurnoverSaveRQ.getFacilityPaperId());
                    exportTurnovers.setCustomerFinacleId(exportTurnoverSaveRQ.getCusId());
                    exportTurnovers.setCreatedDate(exportTO.getCreatedDate());
                    exportTurnovers.setForacid(exportTO.getForacid());
                    exportTurnovers.setTurnOverType(exportTO.getTurnOverType());
                    exportTurnovers.setYear(exportTO.getYear());

                    exportTurnOverDao.saveAndFlush(exportTurnovers);

                }

                saveStatus = true;


            }
            LOG.info("END:  Save Export T/O service {} by {}", exportTurnoverSaveRQ, credentialsDTO);
        } catch (Exception e) {
            saveStatus =false;

            LOG.info("ERROR: Error occur while  Save Export T/O service :{} by {}", exportTurnoverSaveRQ, credentialsDTO, e);
        }


        LOG.info("END:  Save Export T/O service {} By {}", exportTurnoverSaveRQ, credentialsDTO);
        return saveStatus;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public Boolean saveImportTurnOverToDB(ImportTurnoverSaveRQ importTurnoverSaveRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: Save Import T/O  Service{} by {}", importTurnoverSaveRQ, credentialsDTO);
        Boolean saveStatus = false;
        List<ImportTurnovers> importTurnOvers = new ArrayList<>();

        try {

            if(importTurnoverSaveRQ != null) {
                importTurnOverDao.deleteByFacilityPaperIdAndCusId(importTurnoverSaveRQ.getFacilityPaperId(),importTurnoverSaveRQ.getCusId() );
                for(ImportTurnoverDataRQ exportTO : importTurnoverSaveRQ.getImportTurnOverData()) {

                    ImportTurnovers importTurnovers = new ImportTurnovers();
                    String uniqueRQId=UUID.randomUUID().toString();

                    importTurnovers.setBillAmount(exportTO.getBillAmount());
                    importTurnovers.setCurencyCode(exportTO.getBillCurrencyCode());
                    importTurnovers.setConvertedAmount(exportTO.getConvertedAmount());
                    importTurnovers.setFacilityPaperId(importTurnoverSaveRQ.getFacilityPaperId());
                    importTurnovers.setCustomerFinacleId(importTurnoverSaveRQ.getCusId());
                    importTurnovers.setCreatedDate(exportTO.getCreatedDate());
                    importTurnovers.setForacid(exportTO.getForacid());
                    importTurnovers.setTurnOverType(exportTO.getTurnOverType());
                    importTurnovers.setYear(exportTO.getYear());

                    importTurnOverDao.saveAndFlush(importTurnovers);

                }

                saveStatus = true;


            }
            LOG.info("END: Save Insurance Details service {} by {}", importTurnoverSaveRQ, credentialsDTO);
        } catch (Exception e) {
            saveStatus =false;

            LOG.info("ERROR: Error occur whileSave Import T/O service :{} by {}", importTurnoverSaveRQ, credentialsDTO, e);
        }


        LOG.info("END: Save Import T/O service {} By {}", importTurnoverSaveRQ, credentialsDTO);
        return saveStatus;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ExportTurnoverRS> getExportTurnOversDB(ExportTurnoverGetRQ exportTurnoverGetRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get Export T/O from DB {} by {}", exportTurnoverGetRQ, credentialsDTO);
        List<ExportTurnovers> results = new ArrayList<>();

        List<ExportTurnoverRS> response = new ArrayList<>();

        try {


            results = exportTurnOverDao.findByFacilityPaperIdAndCusId(exportTurnoverGetRQ.getFacilityPaperId(), exportTurnoverGetRQ.getCusId());

            for (ExportTurnovers export : results) {
                ExportTurnoverRS exportDetails = new ExportTurnoverRS();
                exportDetails.setConvertedAmount(export.getConvertedAmount());
                exportDetails.setTurnOverType(export.getTurnOverType());
                exportDetails.setCreatedDate(export.getCreatedDate());
                exportDetails.setBillAmount(export.getBillAmount());
                exportDetails.setYear(export.getYear());
                exportDetails.setForacid(export.getForacid());
                exportDetails.setBillCurrencyCode(export.getCurencyCode());
                List<InsuranceDetails> insuranceDetailsLIst = new ArrayList<>();


                response.add(exportDetails);
            }


            LOG.info("END: Get Export T/O from DB {} by {}", exportTurnoverGetRQ, credentialsDTO);
        } catch (Exception e) {
            response = null;
            LOG.info("ERROR: Error occur while Get customer accounts List for the account  :{} by {}", exportTurnoverGetRQ, credentialsDTO, e);
        }
        LOG.info("END: Get Export T/O from DB {} By {}", exportTurnoverGetRQ, credentialsDTO);
        return response;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ImportTurnoverRS> getImportTurnOversDB(ImportTurnoverGetRQ insuranceDetailsRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START: Get Import T/O from DB {} by {}", insuranceDetailsRQ, credentialsDTO);
        List<ImportTurnovers> results = new ArrayList<>();

        List<ImportTurnoverRS> response = new ArrayList<>();

        try {


            results = importTurnOverDao.findByFacilityPaperIdAndCusId(insuranceDetailsRQ.getFacilityPaperId(), insuranceDetailsRQ.getCusId());

            for (ImportTurnovers export : results) {
                ImportTurnoverRS exportDetails = new ImportTurnoverRS();
                exportDetails.setConvertedAmount(export.getConvertedAmount());
                exportDetails.setTurnOverType(export.getTurnOverType());
                exportDetails.setCreatedDate(export.getCreatedDate());
                exportDetails.setBillAmount(export.getBillAmount());
                exportDetails.setYear(export.getYear());
                exportDetails.setForacid(export.getForacid());
                exportDetails.setBillCurrencyCode(export.getCurencyCode());
                List<InsuranceDetails> insuranceDetailsLIst = new ArrayList<>();


                response.add(exportDetails);
            }


            LOG.info("END: Get Import T/O from DB {} by {}", insuranceDetailsRQ, credentialsDTO);
        } catch (Exception e) {
            response = null;
            LOG.info("ERROR: Error occur while Get Import T/O from DB  :{} by {}", insuranceDetailsRQ, credentialsDTO, e);
        }
        LOG.info("END: Get Import T/O from DB {} By {}", insuranceDetailsRQ, credentialsDTO);
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public Boolean removeInsuranceValuationsToDB(Integer facilityPaperId, String cusID, CredentialsDTO credentialsDTO) {
        LOG.info("START: Remove Insurance Details service for CIF {} in Facilitiy Paper ID {} by {}", cusID, facilityPaperId, credentialsDTO);
        Boolean deleteStatus = false;

        try {

            int rowEffected =  collateralDetailsDao.deleteByFacilityPaperIdAndCustomerFinacleId(facilityPaperId, cusID);
            if(rowEffected>0){
                deleteStatus = true;
            }
            LOG.info("END: Remove Insurance Details service for CIF {} in Facilitiy Paper ID {} by {}", cusID, facilityPaperId, credentialsDTO);
        } catch (Exception e) {
            deleteStatus =false;

            LOG.info("ERROR: Error occur while Remove Insurance Details service for CIF {} in Facilitiy Paper ID {} by {} Error {}", cusID, facilityPaperId, credentialsDTO, e);
        }

        LOG.info("END: Remove Insurance Details service for CIF {} in Facilitiy Paper ID {} by {}", cusID, facilityPaperId, credentialsDTO);
        return deleteStatus;
    }

}
