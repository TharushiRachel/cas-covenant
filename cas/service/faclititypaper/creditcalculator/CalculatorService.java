package com.itechro.cas.service.faclititypaper.creditcalculator;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.creditschedule.jdbc.CreditScheduleResponseJdbcDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.facility.FacilityDTO;
import com.itechro.cas.model.dto.facility.FacilityOtherFacilityInformationDTO;
import com.itechro.cas.model.dto.facility.FacilityRentalInformationDTO;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.dto.facilitypaper.securityDocument.SDFacilityPaperPreviewDTO;
import com.itechro.cas.model.dto.facilitypaper.creditcalculator.*;
import com.itechro.cas.model.dto.integration.request.creditschedule.CreditScheduleESBRQ;
import com.itechro.cas.model.dto.integration.request.creditschedule.CreditScheduleESBResponseSaveRQ;
import com.itechro.cas.model.dto.integration.response.creditschedule.CreditScheduleESBDTO;
import com.itechro.cas.model.dto.integration.response.creditschedule.CreditScheduleInformationDTO;
import com.itechro.cas.model.dto.integration.response.creditschedule.RentalDTO;
import com.itechro.cas.model.dto.integration.response.creditschedule.ScheduleResponse;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.util.CalculatorUtil;
import com.itechro.cas.util.NumberUtil;
import com.itechro.cas.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CalculatorService implements EnvironmentAware {

    private static final Logger LOG = LoggerFactory.getLogger(CalculatorService.class);
    private Environment environment;

    @Value("${apps.cas.application.code}")
    private String casApplicationCode;
    @Autowired
    private CreditScheduleResponseJdbcDao creditScheduleResponseJdbcDao;

    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public CalculatorResponse getCreditCalculatorData(CalculatorInputDTO calculatorInputDTO, CredentialsDTO credentialsDTO) throws SAXException, IOException {

        LOG.info("START : Get System Outputs : {} , user {}", calculatorInputDTO, credentialsDTO.getUserID());

        String facilityType = calculatorInputDTO.getFacilityType().trim();
        String calculatorType = calculatorInputDTO.getCalculatorType().trim();
        List<CalculationDTO> systemOutputResponseData;

        CalculatorUtil calculatorUtil = new CalculatorUtil();
        List<Formula> formulaList = calculatorUtil.parseXml(environment, facilityType, calculatorType);

        //Calculate formula values
        Map<String, String> methodList = new HashMap<>();
        Map<String, String> specialFormulaList = new HashMap<>();
        systemOutputResponseData = formulaList.stream().map(f -> {
            CalculationDTO dto = new CalculationDTO();
            dto.setCode(f.getCode());
            dto.setName(f.getOutputName());
            dto.setOutputCode(f.getOutputCode());
            dto.setOutputOrder(f.getOutputOrder());
            dto.setCurrency(f.isCurrency());
            dto.setPercentage(f.isPercentage());
            dto.setOutput(f.isOutput());

            //Replace formula codes with values
            String expression = StringUtil.replaceFormulaValues(calculatorInputDTO.getData(), f.getValue());

            //Do calculations
            String value = null;
            if (!expression.equals("")) {
                if (f.getType().equalsIgnoreCase(AppsConstants.CreditCalculationType.GENERAL.getType())) {
                    value = CalculatorUtil.calculateGeneralFormula(expression);
                } else if (f.getType().equalsIgnoreCase(AppsConstants.CreditCalculationType.CUSTOM.getType())) {
                    try {
                        value = String.valueOf(CalculatorUtil.calculateCustomFormula(expression));
                    } catch (IOException e) {
                        LOG.error("ERROR : ", e);
                    }
                } else if (f.getType().equalsIgnoreCase(AppsConstants.CreditCalculationType.METHOD.getType())) {
                    methodList.put(f.getCode(), f.getValue());
                } else if (f.getType().equalsIgnoreCase(AppsConstants.CreditCalculationType.SPECIAL.getType())) {
                    specialFormulaList.put(f.getCode(), f.getValue());
                }
            } else {
                value = "0";
            }
            dto.setValue(value);
            return dto;
        }).sorted(Comparator.comparing(CalculationDTO::getOutputOrder)).collect(Collectors.toList());

        List<CreditScheduleDTO> creditScheduleResponseData = CalculatorUtil.getCreditScheduleData(calculatorType, systemOutputResponseData, calculatorInputDTO.getRentalData());

        for (Map.Entry<String, String> method : methodList.entrySet()) {
            double value = CalculatorUtil.calculateMethodFormula(method.getValue(), creditScheduleResponseData, systemOutputResponseData);
            systemOutputResponseData.forEach(r -> {
                if (r.getCode().equals(method.getKey())) {
                    r.setValue(String.valueOf(value));
                }
            });
        }

        for (Map.Entry<String, String> method : specialFormulaList.entrySet()) {
            double value = CalculatorUtil.calculateSpecialFormula(method.getValue(), systemOutputResponseData, calculatorInputDTO);
            systemOutputResponseData.forEach(r -> {
                if (r.getCode().equals(method.getKey())) {
                    r.setValue(String.valueOf(value));
                }
            });
        }

        List<StipulatedLossValueDTO> stipulatedLossValueResponseData = CalculatorUtil.calculateStipulatedLossValueData(facilityType, calculatorType, systemOutputResponseData, calculatorInputDTO.getRentalData());

        CalculatorResponse response = new CalculatorResponse();
        response.setSystemOutputResponseData(systemOutputResponseData);
        response.setCreditScheduleResponseData(creditScheduleResponseData);
        response.setStipulatedLossValueResponseData(stipulatedLossValueResponseData);

        LOG.info("END : Get System Outputs : {} , user {}", calculatorInputDTO, credentialsDTO.getUserID());

        return response;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public CreditScheduleInformationDTO getCreditScheduleForFinacle(FacilityPaperDTO facilityPaperDTO, CredentialsDTO credentialsDTO) throws AppsException {
        CreditScheduleInformationDTO response = new CreditScheduleInformationDTO();
        List<FacilityDTO> facilityDTOList = facilityPaperDTO.getFacilityDTOList();
        List<CreditScheduleESBDTO> creditScheduleESBCatalog = null;
        int facilityIndexForRequest = 1;
        for (FacilityDTO facilityDTO : facilityDTOList) {
            //        TODO Enable Facility Calculations Flag should added to the facility Template

            if (facilityDTO.getIsNew().equals(AppsConstants.YesNo.Y) && facilityDTO.getFacilityOtherFacilityInformationDTOList().size() > 0) {
                if (creditScheduleESBCatalog == null) {
                    creditScheduleESBCatalog = new ArrayList<>();
                }

                CreditScheduleESBDTO creditScheduleESBDTO = new CreditScheduleESBDTO();
                CreditScheduleESBRQ scheduleDTO = new CreditScheduleESBRQ();

                String reqID = StringUtil.generateReqId(casApplicationCode, facilityDTO.getFacilityID(), facilityDTO.getDisplayOrder(), facilityDTO.getFacilityRefCode());
                scheduleDTO.setReqID(reqID);
                scheduleDTO.setCasReference(facilityPaperDTO.getFpRefNumber().concat("_").concat(Integer.toString(facilityIndexForRequest)));
                facilityIndexForRequest++;// This is for multiple facilities identity

                CalculatorInputDTO calculatorInputDTO = new CalculatorInputDTO();
                calculatorInputDTO.setFacilityType(facilityDTO.getFacilityType());

                List<CalculationDTO> data = facilityDTO.getFacilityOtherFacilityInformationDTOList().stream().map(o -> {
                    CalculationDTO calculationDTO = new CalculationDTO(o);
                    return calculationDTO;
                }).collect(Collectors.toList());
                calculatorInputDTO.setData(data);
                calculatorInputDTO.setCalculatorType(facilityDTO.getFacilityRentalInformationDTOList().size() > 0 ? AppsConstants.CalculatorType.STRUCTURED.getType() : AppsConstants.CalculatorType.NORMAL.getType());
                calculatorInputDTO.setRentalData(facilityDTO.getFacilityRentalInformationDTOList());

                CalculatorResponse calculatorResponse = null;
                try {
                    calculatorResponse = getCreditCalculatorData(calculatorInputDTO, credentialsDTO);
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String leaseCapital = "0";
                String rentalAmt = "0";
                String reducingRate = "0";
                String noOfTerms = "0";
                String noOfUpfronts = "0";
                String vatExempted = "Y";
                String effectiveRate = "0";
                String supplierName = "";

                String vatOnRental = "0";
                for (CalculationDTO systemOutput : calculatorResponse.getSystemOutputResponseData()) {
                    leaseCapital = systemOutput.getOutputCode().equals("OUT006") ? systemOutput.getValue() : leaseCapital;
                    reducingRate = systemOutput.getOutputCode().equals("OUT007") ? systemOutput.getValue() : reducingRate;
                    noOfTerms = systemOutput.getOutputCode().equals("OUT002") ? systemOutput.getValue() : noOfTerms;
                    noOfUpfronts = calculatorInputDTO.getCalculatorType().equals(AppsConstants.CalculatorType.NORMAL.getType()) ? (systemOutput.getOutputCode().equals("OUT013") ? systemOutput.getValue() : noOfUpfronts) : noOfUpfronts;

                    vatOnRental = systemOutput.getOutputCode().equals("OUT005") ? systemOutput.getValue() : vatOnRental;
                    effectiveRate = systemOutput.getOutputCode().equals("OUT003") ? systemOutput.getValue() : effectiveRate;
                    supplierName = systemOutput.getOutputCode().equals("OUT014") ? systemOutput.getValue() : supplierName;
                }

                List<RentalDTO> rentals = new ArrayList<>();
                for (CreditScheduleDTO schedule : calculatorResponse.getCreditScheduleResponseData()) {
                    rentalAmt = schedule.getIndex() == 1 ? schedule.getRentalWithVat() : rentalAmt;

                    RentalDTO relDTO = new RentalDTO();
                    relDTO.setCapAmt(NumberUtil.roundUp(new BigDecimal(schedule.getCapital()), 2).toString());
                    relDTO.setIntAmt(NumberUtil.roundUp(new BigDecimal(schedule.getInterest()), 2).toString());
                    relDTO.setCapitalOs(NumberUtil.roundUp(new BigDecimal(schedule.getCapitalOutstanding()), 2).toString());
                    relDTO.setGrssRentOs(NumberUtil.roundUp(new BigDecimal(schedule.getGrossRentalOutstanding()), 2).toString());
                    rentals.add(relDTO);
                }

                scheduleDTO.setLeaseCapital(NumberUtil.roundUp(new BigDecimal(leaseCapital), 2).toString());
                scheduleDTO.setRentalAmt(NumberUtil.roundUp(new BigDecimal(rentalAmt), 2).toString());
                scheduleDTO.setReducingRate(NumberUtil.roundDown(new BigDecimal(reducingRate), 2).toString());
                scheduleDTO.setNoOfTerms(noOfTerms);
                scheduleDTO.setNoOfUpfronts(noOfUpfronts);

                vatExempted = vatOnRental.equals("0") ? vatExempted : "N";
                scheduleDTO.setVatExempted(vatExempted);

                scheduleDTO.setEffectiveRate(effectiveRate);
                scheduleDTO.setSupplierName(supplierName);
                scheduleDTO.setREL(rentals);
                creditScheduleESBDTO.setCreditScheduleESBRQ(scheduleDTO);

                creditScheduleESBDTO.setFacilityPaperId(facilityPaperDTO.getFacilityPaperID());
                creditScheduleESBDTO.setFacilityId(facilityDTO.getFacilityID());

                creditScheduleESBCatalog.add(creditScheduleESBDTO);
            }
        }
        response.setCreditScheduleESBCatalog(creditScheduleESBCatalog);
        LOG.info("END : Credit Schedule request from Application For ESB service : {}", response);
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveCreditScheduleResponse(ScheduleResponse resultObj, CreditScheduleESBDTO creditScheduleESBDTO, String responseStr, CredentialsDTO credentialsDTO) {
        CreditScheduleESBResponseSaveRQ creditScheduleESBResponseSaveRQ = new CreditScheduleESBResponseSaveRQ();
        creditScheduleESBResponseSaveRQ.setFacilityPaperId(creditScheduleESBDTO.getFacilityPaperId());
        creditScheduleESBResponseSaveRQ.setFacilityId(creditScheduleESBDTO.getFacilityId());
        creditScheduleESBResponseSaveRQ.setResponseStatus(resultObj.getStatus());
        creditScheduleESBResponseSaveRQ.setResponse(responseStr);
        creditScheduleESBResponseSaveRQ.setCreatedDate(new Date());
        creditScheduleESBResponseSaveRQ.setCreatedBy(credentialsDTO.getUserName());

        creditScheduleResponseJdbcDao.saveCreditScheduleJDBC(creditScheduleESBResponseSaveRQ);

    }

    public CalculatorResponse getCreditCalculatorData(FacilityDTO facility, CredentialsDTO credentialsDTO)  throws SAXException, IOException {
        List<FacilityOtherFacilityInformationDTO> facilityOtherFacilityInformationList = facility.getFacilityOtherFacilityInformationDTOList();
        List<FacilityRentalInformationDTO> facilityRentalInformationList = facility.getFacilityRentalInformationDTOList();
        CalculatorResponse calculatorResponse = new CalculatorResponse();
        try {
            String calculatorType = "Normal";
            if (facilityRentalInformationList != null && !facilityRentalInformationList.isEmpty()) {
                calculatorType = "Structured";
            }

            List<CalculationDTO> calculationDTOList = new ArrayList<>();
            for (FacilityOtherFacilityInformationDTO item : facilityOtherFacilityInformationList) {
                CalculationDTO calculationDTO = new CalculationDTO();
                calculationDTO.setCode(item.getOtherFacilityInfoCode());
                calculationDTO.setValue(item.getOtherInfoData());

                calculationDTOList.add(calculationDTO);
            }
            CalculatorInputDTO calculatorInputDTO = new CalculatorInputDTO();
            calculatorInputDTO.setCalculatorType(calculatorType);
            calculatorInputDTO.setData(calculationDTOList);
            calculatorInputDTO.setFacilityType(facility.getFacilityType());
            calculatorInputDTO.setRentalData(facilityRentalInformationList);

            calculatorResponse = getCreditCalculatorData(calculatorInputDTO, credentialsDTO);
        } catch (Exception ex) {
            LOG.info("ERROR Credit Calculator Data: ", ex);
        }
        return calculatorResponse;
    }

    public List<StipulatedLossDetailDTO> getStipulatedLossDetails(List<StipulatedLossValueDTO> stipulatedLossValueList, SDFacilityPaperPreviewDTO sdFacilityPaperPreviewDTO) {
        List<StipulatedLossDetailDTO> response = new ArrayList<>();

        Map<String, String> stipulatedLossValueDetails = new HashMap<>();

        Object period = sdFacilityPaperPreviewDTO.getSystemOutputResponseDataValue("CFT_OFI23");

        int fromMonth = 0;
        int toMonth = 6;
        int rowCount = 1;
        int maximumPeriod = 60;
        int actualPeriod = period != null && period != "" ? Integer.parseInt(period.toString()) : 0;
        String slvAmount = "";

        String labelForMonth = "";
        String labelForAmount = "";
        String hashSpaceTag1 = "               -            ";
        String hashSpaceTag2 = "               -            ";
        String slvMonthFormat = "";

        for (int i = 1; i <= maximumPeriod; i += 6) {
            if (i != 1) {
                fromMonth = i;
                toMonth = toMonth + 6;
            }
            if (i == maximumPeriod + 1) {
                i = maximumPeriod;
            }

            if (i < actualPeriod) {
                int finalI = i;
                Optional<StipulatedLossValueDTO> optionalInfo = stipulatedLossValueList.stream().filter(data -> data.getTerm() == finalI).findFirst();
                if (optionalInfo.isPresent()) {
                    slvAmount = sdFacilityPaperPreviewDTO.getFormattedAmount(optionalInfo.get().getStipulatedLossValue());
                }
            } else {
                slvAmount = "";
            }

            labelForMonth = "slvMonthNoRow" + rowCount;
            labelForAmount = "slvAmountRow" + rowCount;

            if (fromMonth == 0 || fromMonth == 7) {
                slvMonthFormat = fromMonth + hashSpaceTag1 + toMonth;
            } else {
                slvMonthFormat = fromMonth + hashSpaceTag2 + toMonth;
            }

            stipulatedLossValueDetails.put(labelForMonth, slvMonthFormat);
            stipulatedLossValueDetails.put(labelForAmount, slvAmount);

            rowCount++;
        }
        LOG.info("stipulatedLossValueDetails: {}", stipulatedLossValueDetails);
        stipulatedLossValueDetails.forEach((key, value) -> {
            response.add(new StipulatedLossDetailDTO(key, value));
        });

        return response;
    }
}
