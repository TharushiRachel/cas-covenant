package com.itechro.cas.service.kalyptosystem.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itechro.cas.dao.kalyptosystem.KalyptoPeriodJPADao;
import com.itechro.cas.dao.kalyptosystem.KalyptoSystemDAO;
import com.itechro.cas.dao.kalyptosystem.KalyptoValuesJPADao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.kalyptosystem.KalyptoPeriod;
import com.itechro.cas.model.domain.kalyptosystem.kalyptoData;
import com.itechro.cas.model.dto.integration.request.LoadKalyptoDataRQ;
import com.itechro.cas.model.dto.integration.response.KalyptoRS;
import com.itechro.cas.model.dto.integration.response.kalipto.KalyptoRatingFormatDTO;
import com.itechro.cas.model.dto.integration.response.kalipto.KalyptoRatingPeriodDTO;
import com.itechro.cas.model.dto.integration.response.kalipto.KalyptoRatingPeriodDataDTO;
import com.itechro.cas.model.dto.integration.ws.response.kalypto.Param;
import com.itechro.cas.model.dto.integration.ws.response.kalypto.Row;
import com.itechro.cas.model.dto.kalyptosystem.*;
import com.itechro.cas.model.dto.kalyptosystem.request.KalyptoDataSaveRQ;
import com.itechro.cas.model.dto.kalyptosystem.request.KalyptoRQ;
import com.itechro.cas.model.dto.kalyptosystem.request.KalyptoValuesRQ;
import com.itechro.cas.model.dto.kalyptosystem.response.*;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.integration.IntegrationService;
import com.itechro.cas.service.kalyptosystem.KalyptoSystemService;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class KalyptoSystemServiceImpl implements KalyptoSystemService {

    private static final Logger LOG = LoggerFactory.getLogger(KalyptoSystemServiceImpl.class);

    private final KalyptoPeriodJPADao kalyptoPeriodDao;

    private final KalyptoValuesJPADao kalyptoValuesdDao;

    private final KalyptoSystemDAO kalyptoSystemDAO;

    private final IntegrationService integrationService;

    private final Environment environment;

    @Autowired
    public KalyptoSystemServiceImpl(KalyptoPeriodJPADao kalyptoPeriodDao, KalyptoValuesJPADao kalyptoValuesdDao,
                                    Environment environment, KalyptoSystemDAO kalyptoSystemDAO, IntegrationService integrationService) {
        this.kalyptoPeriodDao = kalyptoPeriodDao;
        this.kalyptoValuesdDao = kalyptoValuesdDao;
        this.environment = environment;
        this.kalyptoSystemDAO = kalyptoSystemDAO;
        this.integrationService = integrationService;
    }


    public Boolean isExistKalyptoDataService(KalyptoRQ kalyptoRQ) {
        return kalyptoSystemDAO.isExistKalyptoDataDAO(kalyptoRQ);
    }

    public Boolean isAddedNewKalyptoValueService(KalyptoRQ kalyptoRQ, CredentialsDTO credentialsDTO) {
        return kalyptoSystemDAO.isAddedNewKalyptoValuesDAO(kalyptoRQ, credentialsDTO);
    }

    public KalyptoDTO getKalyptoDetails(KalyptoRQ kalyptoRQ, CredentialsDTO credentialsDTO) {
        KalyptoDTO result = new KalyptoDTO();
        KalyptoDTO kalyptoDTO = kalyptoSystemDAO.getKalyptoColomnsDAO(kalyptoRQ, credentialsDTO);

        List<KalyptoDataDTO> kalyptoDataDTOList = kalyptoSystemDAO.getKalyptoRowByKalyptoIdDAO(kalyptoDTO.getId(), credentialsDTO);
        result.setId(kalyptoDTO.getId());
        result.setFacilityId(kalyptoDTO.getFacilityId());
        result.setHeaderText(kalyptoDTO.getHeaderText());
        result.setCustomerId(kalyptoDTO.getCustomerId());
        result.setPeriodId(kalyptoDTO.getPeriodId());
        result.setKalyptoData(kalyptoDataDTOList);
        return result;
    }

    @Transactional
    private Boolean saveNewKalyptoService(KalyptoValuesRQ kalyptoValuesRQ, CredentialsDTO credentialsDTO) {
        boolean isSucess = false;
        try {
            Integer kalyptoId = kalyptoSystemDAO.generateKalyptoId();
            Integer periodId = kalyptoSystemDAO.generatePeriodId();

            kalyptoValuesRQ.setKalyptoId(String.valueOf(kalyptoId));
            kalyptoValuesRQ.setPeriodId(String.valueOf(periodId));

            Boolean isKALYPTOSystem = kalyptoSystemDAO.saveNewKALYPTOSystemDAO(kalyptoValuesRQ, credentialsDTO);

            if (isKALYPTOSystem) {
                for (KalyptoDataSaveRQ kalyptoDataSaveRec : kalyptoValuesRQ.getKalyptoAddedValues()) {
                    KalyptoDataSaveRQ kalyptoDataSaveRQ = new KalyptoDataSaveRQ();
                    kalyptoDataSaveRQ.setParameterId(kalyptoDataSaveRec.getParameterId());
                    kalyptoDataSaveRQ.setIsAddedNew(1);
                    kalyptoDataSaveRQ.setValueNumberic(kalyptoDataSaveRec.getValueNumberic());
                    kalyptoDataSaveRQ.setValuePercentage(kalyptoDataSaveRec.getValuePercentage());
                    kalyptoDataSaveRQ.setValueText(kalyptoDataSaveRec.getValueText());
                    kalyptoDataSaveRQ.setPeriodId(String.valueOf(periodId));
                    kalyptoDataSaveRQ.setCustomerId(kalyptoValuesRQ.getCustomerId());
                    kalyptoDataSaveRQ.setParameterName(kalyptoDataSaveRec.getParameterName());
                    kalyptoDataSaveRQ.setFacilityId(kalyptoDataSaveRec.getFacilityId());
                    kalyptoSystemDAO.saveKalyptoDataDAO(kalyptoDataSaveRQ, credentialsDTO);
                }
                isSucess = true;
            }
        } catch (Exception e) {
            isSucess = false;
            LOG.error("ERROR :  {}", e);
        }
        return isSucess;
    }

    //get Kalypto filter json from template folder
    private List<KalyptoReqDTO> getKalyptoReqJsonService() {
        List<KalyptoReqDTO> response = new ArrayList<>();
        try {
            JSONParser parser = new JSONParser();
            String templatePath = this.environment.getProperty("apps.print.html.template.path") + File.separator
                    + "json" + File.separator + "kalyptoReqRows" + ".json";

            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(templatePath));
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<List<KalyptoReqDTO>> jacksonTypeReference = new TypeReference<List<KalyptoReqDTO>>() {
            };
            if (!jsonArray.isEmpty()) {
                response = objectMapper.readValue(String.valueOf(jsonArray), jacksonTypeReference);
            } else {
                response = new ArrayList<>();
            }
        } catch (IOException e) {
            LOG.error("ERROR : {}", e);
        } catch (ParseException e) {
            LOG.error("ERROR : {}", e);
        } catch (Exception e) {
            LOG.error("ERROR : {}", e);
        }
        return response;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public Boolean saveEditedKALYPTOService(KalyptoValuesRQ kalyptoValuesRQ, CredentialsDTO credentialsDTO) {
        Boolean isSucess = false;
        List<KalyptoValuesDTO> kalyptoValuesDTOList = new ArrayList<>();

        if (!kalyptoValuesRQ.getKalyptoValues().isEmpty()) {
            for (KalyptoValuesWithNameDTO kalyptoValuesWithNameDTO : kalyptoValuesRQ.getKalyptoValues()) {
                for (KalyptoValuesDTO kalyptoValuesDTO : kalyptoValuesWithNameDTO.getValues()) {
                    kalyptoValuesDTOList.add(kalyptoValuesDTO);
                }
            }
        }

        kalyptoSystemDAO.saveKalyptoPeriods(kalyptoValuesRQ.getKalyptoPeriodValues(),true, credentialsDTO);
        isSucess = kalyptoSystemDAO.saveKalyptoValues(kalyptoValuesDTOList, true, credentialsDTO);

        if (!kalyptoValuesRQ.getKalyptoAddedValues().isEmpty() && kalyptoValuesRQ.getIsOpenInput()) {
            this.saveNewKalyptoService(kalyptoValuesRQ, credentialsDTO);
        }
        return isSucess;
    }

    public KalyptoIntegrationDTO getKalyptoFromIntegrationService(KalyptoRQ kalyptoRQ, CredentialsDTO credentialsDTO) throws AppsException {

        KalyptoIntegrationDTO kalyptoIntegrationDTO = new KalyptoIntegrationDTO();
        List<KalyptoRowDTO> uniqueKalyptoRowDTOList = new ArrayList<>();
        List<KalyptoPeriodDTO> kalyptoPeriodDTOList = new ArrayList<>();
        LoadKalyptoDataRQ loadKalyptoDataRQ = new LoadKalyptoDataRQ();
        loadKalyptoDataRQ.setUniqueIdentifier(String.valueOf(kalyptoRQ.getCustomerId()));
        KalyptoRS kalyptoRS = new KalyptoRS();

        try {
            //get  response from KALYPTO integration Service
            kalyptoRS = integrationService.getKalyptoSystemDetail(loadKalyptoDataRQ, credentialsDTO);
        } catch (Exception e) {
            kalyptoIntegrationDTO.setMessage("Kalypto RatingService Response Failed");
            kalyptoIntegrationDTO.setSuccessfullyParse(false);
            throw new AppsException("Kalypto Intgration Service issue");
        }

        try {
            //Get all ParameterId data to List (Get all the row data to list)
            if (!kalyptoRS.getKalyptoResponseDTO().getKalyptoRatingPeriodDataDTOS().isEmpty()) {
                for (KalyptoRatingPeriodDataDTO periodDataRec : kalyptoRS.getKalyptoResponseDTO().getKalyptoRatingPeriodDataDTOS()) {

                    if (!periodDataRec.getPeriod().getParams().isEmpty()) {
                        for (Param paramRec : periodDataRec.getPeriod().getParams()) {

                            if (!(paramRec.getParameterId().isEmpty() || paramRec.getParameterId() == null)) {
                                KalyptoPeriodDTO kalyptoPeriodDTO = new KalyptoPeriodDTO();
                                kalyptoPeriodDTO.setParameterId(Integer.parseInt(paramRec.getParameterId()));
                                kalyptoPeriodDTO.setValueNumeric(paramRec.getValueNumeric());
                                kalyptoPeriodDTO.setValuePercentage(paramRec.getValuePercentage());
                                kalyptoPeriodDTO.setValueText(paramRec.getValueText());
                                kalyptoPeriodDTO.setPeriodId(periodDataRec.getPeriod().getPeriodId());

                                if (!kalyptoPeriodDTOList.contains(kalyptoPeriodDTO)) {
                                    kalyptoPeriodDTOList.add(kalyptoPeriodDTO);
                                }
                            }
                        }
                    }

                }
            }


            //Sort Objects from ParameterId in List( parameterId means row id)
            Collections.sort(kalyptoPeriodDTOList, new Comparator<KalyptoPeriodDTO>() {
                public int compare(KalyptoPeriodDTO o1, KalyptoPeriodDTO o2) {
                    return o1.getParameterId().compareTo(o2.getParameterId());
                }
            });

            List<KalyptoRowDTO> kalyptoRowDTOList = new ArrayList<>();

            //remake row details (Get all the row name and compare with parameter id)
            if (!kalyptoRS.getKalyptoResponseDTO().getKalyptoRatingFormatDTOS().isEmpty()) {
                for (KalyptoRatingFormatDTO ratingFromatRec : kalyptoRS.getKalyptoResponseDTO().getKalyptoRatingFormatDTOS()) {
                    //remake first row details
                    if (!ratingFromatRec.getFormat().getRows().isEmpty()) {
                        for (Row row : ratingFromatRec.getFormat().getRows()) {
                            //check not empty parameter ids
                            if (!row.getParameterId().isEmpty()) {
                                List<KalyptoPeriodDTO> tempRowData = new ArrayList<>();

                                KalyptoRowDTO kalyptoRowDTO = new KalyptoRowDTO();

                                //remake other row details (add first row data array to other row data array)
                                for (KalyptoPeriodDTO otherColumn : kalyptoPeriodDTOList) {
                                    boolean isFound = row.getParameterId().equals(String.valueOf(otherColumn.getParameterId()));
                                    if (isFound) {
                                        otherColumn.setName(row.getName());
                                        tempRowData.add(otherColumn);
                                    }
                                }

                                kalyptoRowDTO.setRow(tempRowData);
                                kalyptoRowDTO.setName(row.getName());
                                kalyptoRowDTOList.add(kalyptoRowDTO);
                            }
                        }
                    }
                }
            }


            //filter the wanted rows from kalyptoReqRows json
            List<KalyptoRowDTO> kalyptoRowReqDTOList = new ArrayList<>();

            //read json array from file reader for filltering service
            List<KalyptoReqDTO> jacksonList = getKalyptoReqJsonService();

            if (!jacksonList.isEmpty()) {

                for (KalyptoRowDTO rowRec : kalyptoRowDTOList) {
                    for (KalyptoReqDTO reqRec : jacksonList) {
                        boolean isRecFound = false;
                        //match possible keys with kalypto side data response
                        for (String possibleTxt : reqRec.getPossibleKeys()) {
                            if (possibleTxt.equalsIgnoreCase(rowRec.getName())) {
                                isRecFound = true;
                                break;
                            }
                        }
                        //assumption : there could be multiple periods related to required keys.
                        // assume that all the same periods have same values
                        if (isRecFound) {
                            KalyptoRowDTO kalyptoRowDTO = new KalyptoRowDTO();
                            kalyptoRowDTO.setRow(rowRec.getRow());
                            kalyptoRowDTO.setName(reqRec.getRealKey());
                            kalyptoRowReqDTOList.add(kalyptoRowDTO);
                        }
                    }
                }
            }

            //Get unique name sets from kalyptoRowReqDTOList
            Set<String> uniqueNameSet = new HashSet<>();
            for (KalyptoRowDTO kalyptoTempDTO : kalyptoRowReqDTOList) {
                uniqueNameSet.add(kalyptoTempDTO.getName());
            }

            //Map Unique Names with kalyptoRowDTOList
            for (String text : uniqueNameSet) {
                for (KalyptoRowDTO kalyptoRowRec : kalyptoRowReqDTOList) {
                    if (kalyptoRowRec.getName().equalsIgnoreCase(text)) {
                        uniqueKalyptoRowDTOList.add(kalyptoRowRec);
                        break;
                    }
                }
            }

            //this function for if row name has not row values or if row name has not

            //  Mapping response to  kalyptoIntegrationDTO
            kalyptoIntegrationDTO.setGeneralTabDetails(kalyptoRS.getKalyptoResponseDTO().getKalyptoRatingEvalDTO());
            kalyptoIntegrationDTO.setTableColumnNames(kalyptoRS.getKalyptoResponseDTO().getKalyptoRatingPeriodDTOS());
            kalyptoIntegrationDTO.setTableRows(uniqueKalyptoRowDTOList);
            kalyptoIntegrationDTO.setMessage(kalyptoRS.getMessage());
            kalyptoIntegrationDTO.setSuccessfullyParse(kalyptoRS.getSuccessfullyParse());

        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return kalyptoIntegrationDTO;
    }



    //get kalypto data from integration service and update the values to DB
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public Boolean getAndSaveKalyptoValues(KalyptoRQ kalyptoRQ, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Get And Update Kalypto : {} by :{} ", kalyptoRQ, credentialsDTO);

        Boolean isGetOrUpdated = false;
        List<kalyptoPeriodValues> kalyptoPeriodValues = new ArrayList<>();

        //get first hand kalypto from integration service
        KalyptoIntegrationDTO kalyptoIntegrationDTO = getKalyptoFromIntegrationService(kalyptoRQ, credentialsDTO);

        //check
        if (!kalyptoIntegrationDTO.isSuccessfullyParse()) {
            throw new AppsException("Data Not Found");
        }

        //save general tab details to DB
        if(kalyptoIntegrationDTO.getGeneralTabDetails()!= null){
            isGetOrUpdated = this.kalyptoSystemDAO.saveKalyptoGeneralDataDAO(kalyptoIntegrationDTO.getGeneralTabDetails(),
                    kalyptoRQ.getCustomerId(), kalyptoRQ.getFacilityId());
        }


        //remaking table col name
        if (!kalyptoIntegrationDTO.getTableColumnNames().isEmpty()) {
            for (KalyptoRatingPeriodDTO periodValues : kalyptoIntegrationDTO.getTableColumnNames()) {
                kalyptoPeriodValues period = new kalyptoPeriodValues();
                period.setCustomerId(kalyptoRQ.getCustomerId());
                period.setFacilityId(kalyptoRQ.getFacilityId());
                period.setPeriodName(periodValues.getHeaderText());
                period.setPeriodId(periodValues.getPeriodId());
                period.setIsAddedNew(0);
                period.setId(0);
                kalyptoPeriodValues.add(period);
            }
        }
        //save col name (periods) to DB
        this.kalyptoSystemDAO.saveKalyptoPeriods(kalyptoPeriodValues,false, credentialsDTO);

        List<KalyptoValuesDTO> kalyptoValues = new ArrayList<>();

        //remaking row data to save to the DB
        if (!kalyptoIntegrationDTO.getTableRows().isEmpty()) {
            for (KalyptoRowDTO values : kalyptoIntegrationDTO.getTableRows()) {
                if (!values.getRow().isEmpty()) {
                    for (KalyptoPeriodDTO rowData : values.getRow()) {
                        KalyptoValuesDTO kalyptoValue = new KalyptoValuesDTO();
                        kalyptoValue.setParameterName(values.getName());
                        kalyptoValue.setValueText(rowData.getValueText() != null ? rowData.getValueText() : null);
                        kalyptoValue.setValueNumberic(rowData.getValueNumeric() != null ? rowData.getValueNumeric() : null);
                        kalyptoValue.setValuePercentage(rowData.getValuePercentage() != null ? rowData.getValuePercentage() : null);
                        kalyptoValue.setPeriodId(rowData.getPeriodId() != null ? rowData.getPeriodId() : null);
                        kalyptoValue.setParameterId(String.valueOf(rowData.getParameterId() != null ? rowData.getParameterId() : null));
                        kalyptoValue.setValueText(rowData.getValueText());
                        kalyptoValue.setIsAddedNew("0");
                        kalyptoValue.setCustomerId(kalyptoRQ.getCustomerId());
                        kalyptoValue.setFacilityId(kalyptoRQ.getFacilityId());
                        kalyptoValues.add(kalyptoValue);
                    }
                }
            }
        }
        //adding row
        isGetOrUpdated = this.kalyptoSystemDAO.saveKalyptoValues(kalyptoValues, false, credentialsDTO);


//        List<KalyptoReqDTO> jacksonList = new ArrayList<>();
//
//        try {
//            jacksonList = getKalyptoReqJsonService();
//        } catch (Exception e) {
//            LOG.error("ERROR : ", e);
//        }
//
//        List<KalyptoValuesDTO> kalyptoValuesDTOList = new ArrayList<>();
//        for (KalyptoReqDTO reqRec : jacksonList) {
//            boolean isRecFound = false;
//            for (KalyptoRowDTO values : kalyptoIntegrationDTO.getTableRows()) {
//                if (reqRec.getRealKey().equals(values.getName())) {
//                    isRecFound = true;
//                    break;
//                }
//            }
//
//            if (isRecFound) {
//                Integer parameterId = kalyptoSystemDAO.generateParameterId();
//                for (KalyptoRatingPeriodDTO rec : kalyptoIntegrationDTO.getTableColumnNames()) {
//                    KalyptoValuesDTO sec = new KalyptoValuesDTO();
//                    sec.setParameterId(String.valueOf(parameterId));
//                    sec.setKalyptoId("0");
//                    sec.setValueNumberic("0.00");
//                    sec.setValuePercentage("0");
//                    sec.setValueText("");
//                    sec.setPeriodId(rec.getPeriodId());
//                    sec.setParameterName(reqRec.getRealKey());
//                    sec.setCustomerId(kalyptoRQ.getCustomerId());
//                    sec.setFacilityId(kalyptoRQ.getFacilityId());
//                    kalyptoValuesDTOList.add(sec);
//                }
//
//            }
//        }

//        kalyptoValues.addAll(kalyptoValuesDTOList);
//        List<KalyptoValuesDTO> finalKalyptoValues = new ArrayList<>();
//
//        //sorting according to json
//        for (KalyptoReqDTO reqRec : jacksonList) {
//            for (KalyptoValuesDTO values : kalyptoValues) {
//                if (reqRec.getRealKey().equals(values.getParameterName())) {
//                    finalKalyptoValues.add(values);
//                }
//            }
//        }


//        isGetOrUpdated = this.saveValuesToDB(finalKalyptoValues, false, credentialsDTO);

        LOG.info("END : Get And Update Kalypto : {} by :{} ", kalyptoRQ, credentialsDTO);

        return isGetOrUpdated;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public Boolean updateRefreshedKalyptoValues(KalyptoRQ kalyptoRQ, CredentialsDTO credentialsDTO) {
        LOG.info("START : Get And Update Kalypto : {} by :{}", kalyptoRQ, credentialsDTO.getUserName());

        Boolean isGetOrUpdated = false;
        try {
            KalyptoIntegrationDTO kalyptoIntegrationDTO = getKalyptoFromIntegrationService(kalyptoRQ, credentialsDTO);
            if (kalyptoIntegrationDTO == null || !kalyptoIntegrationDTO.isSuccessfullyParse()) {
                throw  new AppsException("Integration service not working");
            }
            List<kalyptoPeriodValues> kalyptoPeriodValues = new ArrayList<>();

            for (KalyptoRatingPeriodDTO periodValues : kalyptoIntegrationDTO.getTableColumnNames()) {
                kalyptoPeriodValues period = new kalyptoPeriodValues();
                period.setCustomerId(kalyptoRQ.getCustomerId());
                period.setFacilityId(kalyptoRQ.getFacilityId());
                period.setIsAddedNew(0);
                period.setPeriodName(periodValues.getHeaderText());
                period.setPeriodId(periodValues.getPeriodId());
                kalyptoPeriodValues.add(period);
            }

            //save periods to DB
            isGetOrUpdated = this.kalyptoSystemDAO.saveKalyptoPeriods(kalyptoPeriodValues, false,credentialsDTO);

            //save values to DB
            List<KalyptoValuesDTO> kalyptoValues = new ArrayList<>();

            for (KalyptoRowDTO values : kalyptoIntegrationDTO.getTableRows()) {
                for (KalyptoPeriodDTO rec : values.getRow()) {
                    KalyptoValuesDTO kalyptoValue = new KalyptoValuesDTO();
                    kalyptoValue.setParameterName(values.getName());
                    kalyptoValue.setValueText(rec.getValueText() != null ? rec.getValueText() : null);
                    kalyptoValue.setValueNumberic(rec.getValueNumeric() != null ? rec.getValueNumeric() : null);
                    kalyptoValue.setValuePercentage(rec.getValuePercentage() != null ? rec.getValuePercentage() : null);
                    kalyptoValue.setPeriodId(rec.getPeriodId() != null ? rec.getPeriodId() : null);
                    kalyptoValue.setParameterId(String.valueOf(rec.getParameterId() != null ? rec.getParameterId() : null));
                    kalyptoValue.setIsAddedNew("0");
                    kalyptoValue.setCustomerId(kalyptoRQ.getCustomerId());
                    kalyptoValue.setFacilityId(kalyptoRQ.getFacilityId());
                    kalyptoValues.add(kalyptoValue);
                }
            }
            isGetOrUpdated = kalyptoSystemDAO.saveKalyptoValues(kalyptoValues, false, credentialsDTO);

            LOG.info("END : Get And Update Kalypto : {} by :{} ", kalyptoRQ, credentialsDTO.getUserName());

        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }

        return isGetOrUpdated;
    }


    //get KALYPTO Values from DB
    private KalyptoValueRPDTO getOrInitiateKalyptoValues(KalyptoRQ kalyptoRQ, CredentialsDTO credentialsDTO) {

        LOG.info("START : Get Or Initial KalyptoValues : {} by :{}", kalyptoRQ, credentialsDTO.getUserName());

        KalyptoValueRPDTO kalyptoValuesWithPeriods = new KalyptoValueRPDTO();
        try {
            //is customer in existing db
            Boolean isCustomerExist = isExistKalyptoDataService(kalyptoRQ);

            if (isCustomerExist) {

                List<KalyptoPeriod> periodValues = kalyptoPeriodDao.getPeriodsforCustomer(kalyptoRQ.getCustomerId(), kalyptoRQ.getFacilityId());
                List<kalyptoData> kalyptoValues = kalyptoValuesdDao.getKalyptoValuesForCustomer(kalyptoRQ.getCustomerId(), kalyptoRQ.getFacilityId());

                List<KalyptoValuesDTO> kalyptoValuesDTOList = new ArrayList<>();
                List<kalyptoPeriodValues> kalyptoPeriodList = new ArrayList<>();

                Collections.sort(periodValues, new Comparator<KalyptoPeriod>() {
                    public int compare(KalyptoPeriod o1, KalyptoPeriod o2) {
                        return o1.getId().compareTo(o2.getId());
                    }
                });

                Collections.sort(kalyptoValues, new Comparator<kalyptoData>() {
                    public int compare(kalyptoData o1, kalyptoData o2) {
                        return o1.getId().compareTo(o2.getId());
                    }
                });

                for (KalyptoPeriod period : periodValues) {
                    kalyptoPeriodValues value = new kalyptoPeriodValues();
                    value.setId(period.getId());
                    value.setPeriodId(period.getPeriodId());
                    value.setPeriodName(period.getHeaderText());
                    value.setCustomerId(period.getCustomerId());
                    value.setFacilityId(period.getFacilityId());
                    value.setIsAddedNew(period.getIsNewAdded());
                    kalyptoPeriodList.add(value);
                }

                for (kalyptoData rowValue : kalyptoValues) {
                    KalyptoValuesDTO kalyptoValuesDTO = new KalyptoValuesDTO();
                    kalyptoValuesDTO.setId(rowValue.getId());
                    kalyptoValuesDTO.setPeriodId(String.valueOf(rowValue.getPeriodId()));
                    kalyptoValuesDTO.setValueText(rowValue.getValueText());
                    kalyptoValuesDTO.setIsAddedNew(rowValue.getIsAddedNew());
                    kalyptoValuesDTO.setCustomerId(rowValue.getCustomerId());
                    kalyptoValuesDTO.setValueNumberic(rowValue.getValueNumeric() != null ? String.valueOf(rowValue.getValueNumeric()) : "0.00");
                    kalyptoValuesDTO.setValuePercentage(String.valueOf(rowValue.getValuePercentage()));
                    kalyptoValuesDTO.setParameterName(rowValue.getParameterName());
                    kalyptoValuesDTO.setParameterId(String.valueOf(rowValue.getParameterId()));
                    kalyptoValuesDTO.setFacilityId(rowValue.getFacilityId());
                    kalyptoValuesDTOList.add(kalyptoValuesDTO);
                }
                kalyptoValuesWithPeriods.setPeriods(kalyptoPeriodList);
                kalyptoValuesWithPeriods.setValues(kalyptoValuesDTOList);
            } else {
                kalyptoValuesWithPeriods.setPeriods(null);
                kalyptoValuesWithPeriods.setValues(null);
            }

            LOG.info("END : Get Or Initial KalyptoValues : {} by :{} ", kalyptoRQ, credentialsDTO.getUserName());
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }

        return kalyptoValuesWithPeriods;
    }

    //get KALYPTO initail Values
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public KalyptoValueNameRPDTO getKalyptoValues(KalyptoRQ kalyptoRQ, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Get Kalypto Values in Service : {} by :{}", kalyptoRQ, credentialsDTO.getUserName());

        KalyptoValueNameRPDTO kalyptoValueNameRP = new KalyptoValueNameRPDTO();
        KalyptoValueRPDTO kalyptoValuesWithPeriods = new KalyptoValueRPDTO();

        List<KalyptoValuesDTO> tempKalyptoList;
        KalyptoValuesWithNameDTO tempKalyptoValuesWithName;
        List<KalyptoValuesWithNameDTO> nameAndValueList;

        //check KALYPTO Data is existed in DB
        boolean isKALYPTOExist = isExistKalyptoDataService(kalyptoRQ);

        //get KALYPTO Data from DB
        if (isKALYPTOExist) {
            kalyptoValuesWithPeriods = this.getOrInitiateKalyptoValues(kalyptoRQ, credentialsDTO);
            KalyptoGeneralDTO kalyptoGeneralDTO = this.kalyptoSystemDAO.getKalyptoGeneralDataDAO(kalyptoRQ);
            kalyptoValueNameRP.setKalyptoRatingEvalDTO(kalyptoGeneralDTO);
        } else {
            //get and save the KALYPTO DB From Integration service
            boolean isGetOrUpdated = this.getAndSaveKalyptoValues(kalyptoRQ, credentialsDTO);
            if (isGetOrUpdated) {
                //get customer from DB
                kalyptoValuesWithPeriods = this.getOrInitiateKalyptoValues(kalyptoRQ, credentialsDTO);
                KalyptoGeneralDTO kalyptoGeneralDTO = this.kalyptoSystemDAO.getKalyptoGeneralDataDAO(kalyptoRQ);
                kalyptoValueNameRP.setKalyptoRatingEvalDTO(kalyptoGeneralDTO);
            } else {
                KalyptoGeneralDTO kalyptoGeneralDTO = this.kalyptoSystemDAO.getKalyptoGeneralDataDAO(kalyptoRQ);
                kalyptoValueNameRP.setKalyptoRatingEvalDTO(kalyptoGeneralDTO);
                kalyptoValuesWithPeriods.setPeriods(null);
                kalyptoValuesWithPeriods.setValues(null);
            }
        }

        if (kalyptoValuesWithPeriods.getValues() != null) {
            for (KalyptoValuesDTO value : kalyptoValuesWithPeriods.getValues()) {
                tempKalyptoList = new ArrayList<>();
                tempKalyptoValuesWithName = new KalyptoValuesWithNameDTO();
                nameAndValueList = new ArrayList<>();
                boolean isAdded = false;

                if (kalyptoValueNameRP.getValues() != null) {
                    List<KalyptoValuesWithNameDTO> values = kalyptoValueNameRP.getValues();

                    for (KalyptoValuesWithNameDTO uniqueParameter : values) {
                        if (uniqueParameter.getParameterName().equalsIgnoreCase(value.getParameterName())) {

                            tempKalyptoList = uniqueParameter.getValues();
                            tempKalyptoList.add(value);
                            tempKalyptoValuesWithName.setValues(tempKalyptoList);
                            tempKalyptoValuesWithName.setParameterName(uniqueParameter.getParameterName());
                            nameAndValueList.add(tempKalyptoValuesWithName);
                            isAdded = true;
                        } else {
                            nameAndValueList.add(uniqueParameter);
                        }
                    }

                    if (!isAdded) {
                        tempKalyptoList.add(value);
                        tempKalyptoValuesWithName.setParameterName(value.getParameterName());
                        tempKalyptoValuesWithName.setValues(tempKalyptoList);
                        nameAndValueList.add(tempKalyptoValuesWithName);
                        isAdded = true;
                    }
                }

                if (isAdded) {
                    kalyptoValueNameRP.setValues(nameAndValueList);
                    continue;
                }

                tempKalyptoList.add(value);
                tempKalyptoValuesWithName.setParameterName(value.getParameterName());
                tempKalyptoValuesWithName.setValues(tempKalyptoList);
                nameAndValueList.add(tempKalyptoValuesWithName);
                kalyptoValueNameRP.setValues(nameAndValueList);
            }
        } else {
            kalyptoValueNameRP.setValues(null);
        }
        kalyptoValueNameRP.setPeriods(kalyptoValuesWithPeriods.getPeriods());

        LOG.info("END : Kalypto Values in Service : {} by :{} ", kalyptoRQ, credentialsDTO.getUserName());

        return kalyptoValueNameRP;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public KalyptoValueNameRPDTO refreshKalyptoValues(KalyptoRQ kalyptoRQ, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Refresh Kalypto Values : {} by :{}", kalyptoRQ, credentialsDTO.getUserName());

        boolean isCustomerExist = isExistKalyptoDataService(kalyptoRQ);
        KalyptoValueNameRPDTO kalyptoValuesWithPeriods = new KalyptoValueNameRPDTO();

        if (isCustomerExist) {
            this.updateRefreshedKalyptoValues(kalyptoRQ, credentialsDTO);
            kalyptoValuesWithPeriods = this.getKalyptoValues(kalyptoRQ, credentialsDTO);
        } else {
            kalyptoValuesWithPeriods.setPeriods(null);
            kalyptoValuesWithPeriods.setValues(null);
        }
        LOG.info("END : Add DA Limits : {} by :{} ", kalyptoRQ, credentialsDTO.getUserName());

        return kalyptoValuesWithPeriods;
    }

    public KalyptoRS getDirectIntegrationService(LoadKalyptoDataRQ loadKalyptoDataRQ, CredentialsDTO credentialsDTO) {
        return integrationService.getKalyptoSystemDetail(loadKalyptoDataRQ, credentialsDTO);
    }

}
