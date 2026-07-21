package com.itechro.cas.service.das;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.dao.das.*;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.das.DADesignationMasterData;
import com.itechro.cas.model.domain.das.DALimit;
import com.itechro.cas.model.domain.das.DALimitTemp;
import com.itechro.cas.model.dto.das.*;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.casmaster.support.CommitteeTypeService;
import com.itechro.cas.service.integration.IntegrationService;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class DasService implements EnvironmentAware {

    private static final Logger LOG = LoggerFactory.getLogger(DasService.class);

    @Autowired
    private Environment environment;

    @Autowired
    private DALimitTempDao daLimitTempDao;

    @Autowired
    private DALimitDao daLimitDao;

//    @Autowired
//    private DADesignationdao dADesignationdao;
    @Autowired
    private DADesignationMasterDao dADesignationMasterDao;

    @Autowired
    private ModelMapper modelMapper;

    private CasProperties casProperties;

    @Autowired
    private IntegrationService integrationService;

    @Autowired
    private CommitteeTypeService committeeTypeService;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public DADesignationAndDALimitsDTO createAndUpdateDALimitsToTemp(DALimitListDTO req, CredentialsDTO credentialsDTO, String isCommittee) throws AppsException {

        //insert into temp table
        LOG.info("START : Add DA Limits : {} by :{} ", req, credentialsDTO.getUserName());
        AppsConstants.Status status = AppsConstants.Status.ACT;
        DALimitListDTO dasListDTO = new DALimitListDTO();
        List<DALimitDTO> dasEntityDTOList = new ArrayList<>();
        Date date = new Date();
        DADesignationAndDALimitsDTO daDesignationAndDALimitsDTO=new DADesignationAndDALimitsDTO();
        if(req != null && req.getDasEntityDTOList() != null){
            Integer designationId = req.getDasEntityDTOList().get(0).getDesignationId();
            DADesignationMasterData dADesignationMasterData = dADesignationMasterDao.findById(designationId).get();
            if (dADesignationMasterData != null){
                for (DALimitDTO dasLimit: req.getDasEntityDTOList()) {
                    if(dasLimit.getDesignationId() != null && dasLimit.getColumnId() != null && dasLimit.getRiskRating() != null && dasLimit.getRiskValue() != null){

                        DALimit exDALimit = daLimitDao.findByDesignationIdAndColumnIdAndStatus(dasLimit.getDesignationId(), dasLimit.getColumnId(), status);
                        DALimitTemp exDALimitTempt = daLimitTempDao.findByDesignationIdAndColumnIdAndStatus(dasLimit.getDesignationId(), dasLimit.getColumnId(), status);

                        if (exDALimit == null && exDALimitTempt == null){ //insert
                            DALimitTemp daLimitTemp = new DALimitTemp();
                            daLimitTemp.setDesignationId(dADesignationMasterData.getId());
                            daLimitTemp.setColumnId(dasLimit.getColumnId());
                            daLimitTemp.setRiskValue(dasLimit.getRiskValue());
                            daLimitTemp.setStatus(status);
                            daLimitTemp.setRiskRating(dasLimit.getRiskRating());
                            daLimitTemp.setApproveStatus(DomainConstants.MasterDataApproveStatus.PENDING);
                            daLimitTemp.setCreatedBy(credentialsDTO.getUserName());
                            daLimitTemp.setCreatedDate(date);
                            daLimitTemp.setIsCommittee(isCommittee);

                            DALimitTemp newDALimit = daLimitTempDao.saveAndFlush(daLimitTemp);

                        } else if(exDALimit != null && exDALimitTempt == null){

                                DALimitTemp daLimitTemp = modelMapper.map(exDALimit, DALimitTemp.class);
                                daLimitTemp.setDesignationId(dADesignationMasterData.getId());
                                daLimitTemp.setColumnId(dasLimit.getColumnId());
                                daLimitTemp.setRiskValue(dasLimit.getRiskValue());
                                daLimitTemp.setStatus(status);
                                daLimitTemp.setRiskRating(dasLimit.getRiskRating());
                                daLimitTemp.setApproveStatus(DomainConstants.MasterDataApproveStatus.PENDING);
                                daLimitTemp.setParentRecordID(exDALimit.getDaLimitsId());
                                daLimitTemp.setModifiedBy(credentialsDTO.getUserName());
                                daLimitTemp.setModifiedDate(date);

                                DALimitTemp newDALimit = daLimitTempDao.saveAndFlush(daLimitTemp);

                        }else if(exDALimit == null && exDALimitTempt != null) {
                                exDALimitTempt.setStatus(status);
                                exDALimitTempt.setApproveStatus(DomainConstants.MasterDataApproveStatus.PENDING);
                                exDALimitTempt.setRiskValue(dasLimit.getRiskValue());
                                exDALimitTempt.setRiskRating(dasLimit.getRiskRating());
                                exDALimitTempt.setParentRecordID(dasLimit.getDaLimitsId());
                                exDALimitTempt.setModifiedBy(credentialsDTO.getUserName());
                                exDALimitTempt.setModifiedDate(date);

                                DALimitTemp newDALimit = daLimitTempDao.saveAndFlush(exDALimitTempt);

                        }
                    }
                }
            }
//
//            List<DALimitTemp> daLimitTempList = daLimitTempDao.findAllByDesignationIdAndStatus(designationId, status);
//
//            for (DALimitTemp daLimitTemp: daLimitTempList) {
//                DALimitDTO daLimitDTO = modelMapper.map(daLimitTemp, DALimitDTO.class);
//                dasEntityDTOList.add(daLimitDTO);
//            }
//            dasListDTO.setDasEntityDTOList(dasEntityDTOList);
             daDesignationAndDALimitsDTO  = this.getAllDALimitsAndDesignations(credentialsDTO);
        }
        LOG.info("END : Add DA Limits : {} by :{} ", req, credentialsDTO.getUserName());
        return daDesignationAndDALimitsDTO;

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public DADesignationAndDALimitsDTO authorizeDALimits(DALimitsAuthorizeRQ req, CredentialsDTO credentialsDTO) throws AppsException {

        //insert to master table
        LOG.info("START:  Authorize DA Limits :{} by :{}", req, credentialsDTO.getUserName());
        AppsConstants.Status status = AppsConstants.Status.ACT;
        DALimitListDTO dasListDTO = new DALimitListDTO();
        List<DALimitDTO> dasEntityDTOList = new ArrayList<>();
        Date date = new Date();
        DADesignationAndDALimitsDTO daDesignationAndDALimitsDTO=new DADesignationAndDALimitsDTO();

        if(req != null && req.getDesignationId() != null){
            Integer designationId = req.getDesignationId();
            DADesignationMasterData dADesignationMasterData = dADesignationMasterDao.findById(designationId).get();
            if (dADesignationMasterData != null){
                List<DALimitTemp> daLimitTempList = daLimitTempDao.findAllByDesignationIdAndStatus(designationId, status);

                for (DALimitTemp dasLimitTemp: daLimitTempList) {
                    if (req.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED){

                        DALimit exDALimit = daLimitDao.findByDesignationIdAndColumnIdAndStatus(dasLimitTemp.getDesignationId(), dasLimitTemp.getColumnId(), status);
                        DALimitTemp exDALimitTemp = daLimitTempDao.findByDesignationIdAndColumnIdAndStatus(dasLimitTemp.getDesignationId(), dasLimitTemp.getColumnId(), status);

                        if(exDALimit == null){
                            DALimit daLimit = new DALimit();
                            daLimit.setDesignationId(dADesignationMasterData.getId());
                            daLimit.setColumnId(dasLimitTemp.getColumnId());
                            daLimit.setRiskValue(dasLimitTemp.getRiskValue());
                            daLimit.setStatus(status);
                            daLimit.setRiskRating(dasLimitTemp.getRiskRating());
                            daLimit.setCreatedBy(exDALimitTemp.getCreatedBy());
                            daLimit.setCreatedDate(exDALimitTemp.getCreatedDate());
                            daLimit.setApprovedBy(credentialsDTO.getUserName());
                            daLimit.setApprovedDate(date);
                            daLimit.setAuthorizerDisplayName(credentialsDTO.getDisplayName());
                            daLimit.setApproveStatus(DomainConstants.MasterDataApproveStatus.APPROVED);

                            DALimit newDALimit = daLimitDao.saveAndFlush(daLimit);

                        }
                        else {
                            DALimit newDALimit = modelMapper.map(exDALimitTemp, DALimit.class);
                            newDALimit.setDaLimitsId(exDALimitTemp.getParentRecordID());
                            newDALimit.setApprovedBy(credentialsDTO.getUserName());
                            newDALimit.setApprovedDate(date);
                            newDALimit.setAuthorizerDisplayName(credentialsDTO.getDisplayName());
                            newDALimit.setApproveStatus(DomainConstants.MasterDataApproveStatus.APPROVED);

                            DALimit updatedDALimit = daLimitDao.saveAndFlush(newDALimit);
                        }
                        //delete temp record
                        daLimitTempDao.deleteById(exDALimitTemp.getDaLimitsId());
                    }else {
                        daLimitTempDao.deleteById(dasLimitTemp.getDaLimitsId());
                    }


                }
            }

//            List<DALimit> daLimitList = daLimitDao.findAllByDesignationIdAndStatus(req.getDesignationId(), status);
//
//            for (DALimit daLimit: daLimitList) {
//                DALimitDTO daLimitDTO = modelMapper.map(daLimit, DALimitDTO.class);
//                dasEntityDTOList.add(daLimitDTO);
//            }
//            dasListDTO.setDasEntityDTOList(dasEntityDTOList);
            daDesignationAndDALimitsDTO  = this.getAllDALimitsAndDesignations(credentialsDTO);
        }

        LOG.info("END : Authorize DA Limits : {} by :{} ", req, credentialsDTO.getUserName());
        return daDesignationAndDALimitsDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FullDATable getAllDALimits(CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START:  Get DA Limits by :{}", credentialsDTO.getUserName());
        AppsConstants.Status status = AppsConstants.Status.ACT;
        List<DATableDTO> daTableDTOList = new ArrayList<>();
        FullDATable allDATableDTOList = new FullDATable();

        List<DADesignationMasterData> dADesignationList = dADesignationMasterDao.findAllByStatus(DomainConstants.DAType.ACT.name());

        for (DADesignationMasterData designation: dADesignationList) {
            List<DALimit> daLimitList = daLimitDao.findAllByDesignationIdAndStatus(designation.getId(), status);

            for (DALimit daLimit: daLimitList) {
                DAColumnsDTO daColumnsDTO = new DAColumnsDTO(daLimit.getColumnId(), daLimit.getRiskValue());
                DATableDTO daTableDTO = new DATableDTO(designation.getId(), daColumnsDTO, daLimit.getModifiedBy(), daLimit.getCreatedBy(), daLimit.getModifiedDate(), daLimit.getCreatedDate());

                daTableDTOList.add(daTableDTO);
            }
            allDATableDTOList.setDaTableDTO(daTableDTOList);
        }

        LOG.info("END : Get DA Limits by :{} ", credentialsDTO.getUserName());
        return allDATableDTOList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FullDATable getAllTempDALimits(CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START:  Get Temp DA Limits by :{}", credentialsDTO.getUserName());
        AppsConstants.Status status = AppsConstants.Status.ACT;
        List<DATableDTO> daTableDTOList = new ArrayList<>();
        FullDATable allDATableDTOList = new FullDATable();

        List<DADesignationMasterData> dADesignationList = dADesignationMasterDao.findAllByStatus(DomainConstants.DAType.ACT.name());

        for (DADesignationMasterData designation: dADesignationList) {
            List<DALimitTemp> daLimitList = daLimitTempDao.findAllByDesignationIdAndStatus(designation.getId(), status);

            for (DALimitTemp daLimit: daLimitList) {
                DAColumnsDTO daColumnsDTO = new DAColumnsDTO(daLimit.getColumnId(), daLimit.getRiskValue());
                DATableDTO daTableDTO = new DATableDTO(designation.getId(), daColumnsDTO, daLimit.getModifiedBy(), daLimit.getCreatedBy(), daLimit.getModifiedDate(), daLimit.getCreatedDate());

                daTableDTOList.add(daTableDTO);
            }
            allDATableDTOList.setDaTableDTO(daTableDTOList);
        }

        LOG.info("END : Get Temp DA Limits by :{} ", credentialsDTO.getUserName());
        return allDATableDTOList;
    }
    public List<DADesignationMasterDataDTO> getAllDADesignationDetails() {
        List<DADesignationMasterData> dADesignationList =  dADesignationMasterDao.findAllByStatus(AppsConstants.Status.ACT.toString());
        List<DADesignationMasterDataDTO> resultList = new ArrayList<>();
        for (DADesignationMasterData daDesignationMasterData : dADesignationList) {
            DADesignationMasterDataDTO daDesignationMasterDataDTO = modelMapper.map(daDesignationMasterData, DADesignationMasterDataDTO.class);
            resultList.add(daDesignationMasterDataDTO);
        }

        resultList.sort(Comparator
                .comparingInt((DADesignationMasterDataDTO obj) -> obj.getDisplayOrder() != null ? obj.getDisplayOrder() : Integer.MAX_VALUE)
                .thenComparingInt(obj -> obj.getDisplayOrder() != null ? obj.getDisplayOrder() : Integer.MAX_VALUE)
        );

        return resultList;

    }

    private DADesignationsWithType getAllDADesignationWithTypes() {
        DADesignationsWithType daDesignationsWithType = new DADesignationsWithType();
        List<DADesignationMasterData> dADesignationList =  dADesignationMasterDao.findAllByStatus(AppsConstants.Status.ACT.toString());
        List<DADesignationMasterDataDTO> committeeDesignations = new ArrayList<>();
        List<DADesignationMasterDataDTO> individualDesignations = new ArrayList<>();


        for (DADesignationMasterData daDesignationMasterData : dADesignationList) {
            DADesignationMasterDataDTO daDesignationMasterDataDTO = modelMapper.map(daDesignationMasterData, DADesignationMasterDataDTO.class);
            if (daDesignationMasterData.getIsCommittee() != null && daDesignationMasterData.getIsCommittee().equals("Y")){
                committeeDesignations.add(daDesignationMasterDataDTO);
            }else {
                individualDesignations.add(daDesignationMasterDataDTO);
            }
        }

        committeeDesignations.sort(Comparator
                .comparingInt((DADesignationMasterDataDTO obj) -> obj.getDisplayOrder() != null ? obj.getDisplayOrder() : Integer.MAX_VALUE)
                .thenComparingInt(obj -> obj.getDisplayOrder() != null ? obj.getDisplayOrder() : Integer.MAX_VALUE)
        );

        individualDesignations.sort(Comparator
                .comparingInt((DADesignationMasterDataDTO obj) -> obj.getDisplayOrder() != null ? obj.getDisplayOrder() : Integer.MAX_VALUE)
                .thenComparingInt(obj -> obj.getDisplayOrder() != null ? obj.getDisplayOrder() : Integer.MAX_VALUE)
        );

        daDesignationsWithType.setCommitteeDesignations(committeeDesignations);
        daDesignationsWithType.setIndividualDesignations(individualDesignations);

        return daDesignationsWithType;

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public DADesignationAndDALimitsDTO saveDADetailsToMaster(DADesignationIntegratedDTO daDesignationIntegratedDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : save DA Details : {} ", daDesignationIntegratedDTO);
        DADesignationDTO dADesignationDTO = daDesignationIntegratedDTO.getDaDesignationDTO();
        DALimitListDTO daLimitListDTO=daDesignationIntegratedDTO.getDaLimitListDTO();
        DADesignationMasterData dADesignationMasterData = new DADesignationMasterData();
        Date date = new Date();
        DADesignationAndDALimitsDTO dasListDTO = new DADesignationAndDALimitsDTO();
        String isCommittee;

        if (dADesignationDTO.getIsCommittee().equals("Y")){
            isCommittee = "Y";
        }else {
            isCommittee = "N";
        }

        dADesignationMasterData.setCreatedDate(date);
        dADesignationMasterData.setCreatedBy(credentialsDTO.getUserName());
        dADesignationMasterData.setModifiedDate(date);
        dADesignationMasterData.setModifiedBy(credentialsDTO.getUserName());
        dADesignationMasterData.setParentRecId(dADesignationDTO.getParentRecId());
        dADesignationMasterData.setStatus(AppsConstants.Status.ACT.toString());
        dADesignationMasterData.setDesignation(dADesignationDTO.getDesignation());
        dADesignationMasterData.setDescription(dADesignationDTO.getDescription());
        dADesignationMasterData.setApprovedBy(dADesignationDTO.getApprovedBy());
        dADesignationMasterData.setApprovedDate(dADesignationDTO.getApprovedDate());
        dADesignationMasterData.setAuthorizedDisplayName(dADesignationDTO.getAuthorizedDisplayName());
        dADesignationMasterData.setApproveStatus(dADesignationDTO.getApproveStatus());
        dADesignationMasterData.setDesignationCode(dADesignationDTO.getDesignationCode());
        dADesignationMasterData.setIsCommittee(isCommittee);

       DADesignationMasterData daDesignationMasterData= this.dADesignationMasterDao.saveAndFlush(dADesignationMasterData);

       if(daDesignationMasterData!=null){
           for (DALimitDTO passedToDALimits: daDesignationIntegratedDTO.getDaLimitListDTO().getDasEntityDTOList()) {
               passedToDALimits.setDesignationId(daDesignationMasterData.getId());
           }
           daLimitListDTO.setDasEntityDTOList(daDesignationIntegratedDTO.getDaLimitListDTO().getDasEntityDTOList());
           dasListDTO = createAndUpdateDALimitsToTemp(  daLimitListDTO, credentialsDTO, isCommittee);

       }
        LOG.info(" END : save DA Details: {} : {}: {} : {} : {} : {} ", dADesignationMasterData.getCreatedBy(),dADesignationMasterData.getModifiedBy(),dADesignationMasterData.getParentRecId(),dADesignationMasterData.getDesignation(),dADesignationMasterData.getDescription());
    return dasListDTO;
    }

    public List<DADesignationCodeDTO>  getDADesignationDropDownList() {

        LOG.info("START: Get DA Designation DropDown List");

        List<DADesignationCodeDTO> daDesignationDropDownDTOList = new ArrayList<>();

        List<DADesignationMasterDataDTO> allDADesignationMasterData = getAllDADesignationDetails();

        List<DADesignationCodeDTO> allDADesignationCodeDTO = integrationService.getAllDesignationCodeDetails();

        if (allDADesignationMasterData.size() > 0) {
            for (DADesignationCodeDTO daDesignationCodeDTO : allDADesignationCodeDTO) {
                boolean foundMatch = false;
                for(DADesignationMasterDataDTO daDesignationMasterData :allDADesignationMasterData){
                    if (daDesignationMasterData.getDesignationCode().equals(daDesignationCodeDTO.getCode())) {
                        foundMatch = true;
                        break;
                    }
                }
                if (!foundMatch) {
                    daDesignationDropDownDTOList.add(daDesignationCodeDTO);
                }
            }
        }else{
            daDesignationDropDownDTOList = allDADesignationCodeDTO;
        }
        Collections.sort(daDesignationDropDownDTOList, Comparator.comparing(DADesignationCodeDTO::getDescription));

        LOG.info("START: Get DA Designation DropDown List {}",daDesignationDropDownDTOList);
        
        return daDesignationDropDownDTOList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<DALimitsWithApproveStatusDTO> getAllDALimitsForAdmin(CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START: Get All DA Limits For Admin {}", credentialsDTO);

        AppsConstants.Status status = AppsConstants.Status.ACT;
        List<DALimitsWithApproveStatusDTO> daList = new ArrayList<>();

        List<DALimitTemp> daLimitTempList = daLimitTempDao.findAllByStatus(status);
        List<DALimit> daLimitList = daLimitDao.findAllByStatus(status);

        for (DALimit dalimit: daLimitList) {
            for (DALimitTemp daLimitTemp: daLimitTempList) {

                if (daLimitTemp.getDesignationId() == dalimit.getDesignationId() && daLimitTemp.getColumnId() == dalimit.getColumnId()){
                    daLimitList.remove(dalimit);
                }
            }
        }

        for (DALimitTemp dalimitTemp: daLimitTempList) {
            DALimitsWithApproveStatusDTO daLimitsWithApproveStatusDTO = new DALimitsWithApproveStatusDTO(dalimitTemp, DomainConstants.DAType.P.name());

            daList.add(daLimitsWithApproveStatusDTO);
        }

        for (DALimit daLimit: daLimitList) {
            DALimitsWithApproveStatusDTO daLimitsWithApproveStatusDTO = new DALimitsWithApproveStatusDTO(daLimit, DomainConstants.DAType.A.name());

            daList.add(daLimitsWithApproveStatusDTO);
        }

        LOG.info("END : Get All DA Limits For Admin :{} ", credentialsDTO.getUserName());

        return daList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<DALimitsWithApproveStatusDTO> getAllPendingAndApprovedDALimitsByDesignationId(CredentialsDTO credentialsDTO, Integer designationId) throws AppsException {

        LOG.info("START : Get All DA Limits by :{} ", credentialsDTO);

        AppsConstants.Status status = AppsConstants.Status.ACT;
        List<DALimitsWithApproveStatusDTO> daList = new ArrayList<>();
        List<DALimitDTO> dasEntityDTOList = new ArrayList<>();

        List<DALimitTemp> daLimitTempList = daLimitTempDao.findAllByDesignationIdAndStatus(designationId, status);
        List<DALimit> daLimitList = daLimitDao.findAllByDesignationIdAndStatus(designationId, status);

        for (DALimitTemp dalimitTemp: daLimitTempList) {
            DALimitsWithApproveStatusDTO daLimitsWithApproveStatusDTO = new DALimitsWithApproveStatusDTO(dalimitTemp, DomainConstants.DAType.P.name());
            daList.add(daLimitsWithApproveStatusDTO);
        }

        for (DALimit daLimit: daLimitList) {
            DALimitsWithApproveStatusDTO daLimitsWithApproveStatusDTO = new DALimitsWithApproveStatusDTO(daLimit, DomainConstants.DAType.A.name());
            daList.add(daLimitsWithApproveStatusDTO);
        }

        LOG.info("END : Get All DA Limits by :{} ", daList);
        return daList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FullDATableWithTypeDTO getAllDALimitsWithApproveStatus(CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START: Get All DA Limits With Approve Status :{}", credentialsDTO.getUserName());

        AppsConstants.Status status = AppsConstants.Status.ACT;
        List<DATableDTO> daTableDTOList = new ArrayList<>();
        List<DATableDTO> daTempTableDTOList = new ArrayList<>();
        FullDATableRS tableRS =  new FullDATableRS();
        FullDATableWithTypeDTO fullDATableWithTypeDTO = new FullDATableWithTypeDTO();
        List<DADesignationMasterData> committeeDesignation = new ArrayList<>();
        List<DADesignationMasterData> individualDesignation = new ArrayList<>();

        List<DADesignationMasterData> dADesignationList = dADesignationMasterDao.findAllByStatus(DomainConstants.DAType.ACT.name());

        for (DADesignationMasterData dADesignationMasterData: dADesignationList) {
            if (dADesignationMasterData.getIsCommittee() != null && dADesignationMasterData.getIsCommittee().equals("Y")){
                committeeDesignation.add(dADesignationMasterData);
            }
            else {
                individualDesignation.add(dADesignationMasterData);
            }
        }

        FullDATableRS committeeDATable = getDATableResponse(committeeDesignation);
        FullDATableRS individualDATable = getDATableResponse(individualDesignation);


        fullDATableWithTypeDTO.setCommittee(committeeDATable);
        fullDATableWithTypeDTO.setIndividual(individualDATable);

//        dADesignationList.sort(Comparator
//                .comparingInt((DADesignationMasterData obj) -> obj.getDisplayOrder() != null ? obj.getDisplayOrder() : Integer.MAX_VALUE)
//                .thenComparingInt(obj -> obj.getDisplayOrder() != null ? obj.getDisplayOrder() : Integer.MAX_VALUE)
//        );
//
//        for (DADesignationMasterData designation: dADesignationList) {
//            List<DALimit> daLimitList = daLimitDao.findAllByDesignationIdAndStatus(designation.getId(), status);
//            List<DALimitTemp> daLimitTempList = daLimitTempDao.findAllByDesignationIdAndStatus(designation.getId(), status);
//
//            for (DALimit daLimit: daLimitList) {
//                DAColumnsDTO daColumnsDTO = new DAColumnsDTO(daLimit.getColumnId(), daLimit.getRiskValue());
//                DATableDTO daTableDTO = new DATableDTO(designation.getId(), daColumnsDTO, daLimit.getModifiedBy(), daLimit.getCreatedBy(), daLimit.getModifiedDate(), daLimit.getCreatedDate());
//
//                daTableDTOList.add(daTableDTO);
//            }
//
//            for (DALimitTemp daLimitTemp: daLimitTempList) {
//                DAColumnsDTO daColumnsDTO = new DAColumnsDTO(daLimitTemp.getColumnId(), daLimitTemp.getRiskValue());
//                DATableDTO daTableDTO = new DATableDTO(designation.getId(), daColumnsDTO, daLimitTemp.getModifiedBy(), daLimitTemp.getCreatedBy(), daLimitTemp.getModifiedDate(), daLimitTemp.getCreatedDate());
//
//                daTempTableDTOList.add(daTableDTO);
//            }
//        }
//        tableRS.setApprovedDALimits(daTableDTOList);
//        tableRS.setPendingDALimits(daTempTableDTOList);

        LOG.info("END : Get All DA Limits With Approve Status :{} ", credentialsDTO.getUserName());

        return fullDATableWithTypeDTO;
    }

    private FullDATableRS getDATableResponse(List<DADesignationMasterData> dADesignationList){
        AppsConstants.Status status = AppsConstants.Status.ACT;
        FullDATableWithTypeDTO fullDATableWithTypeDTO = new FullDATableWithTypeDTO();
        FullDATableRS tableRS =  new FullDATableRS();
        List<DATableDTO> daTableDTOList = new ArrayList<>();
        List<DATableDTO> daTempTableDTOList = new ArrayList<>();

        dADesignationList.sort(Comparator
                .comparingInt((DADesignationMasterData obj) -> obj.getDisplayOrder() != null ? obj.getDisplayOrder() : Integer.MAX_VALUE)
                .thenComparingInt(obj -> obj.getDisplayOrder() != null ? obj.getDisplayOrder() : Integer.MAX_VALUE)
        );

        for (DADesignationMasterData designation: dADesignationList) {
            List<DALimit> daLimitList = daLimitDao.findAllByDesignationIdAndStatus(designation.getId(), status);
            List<DALimitTemp> daLimitTempList = daLimitTempDao.findAllByDesignationIdAndStatus(designation.getId(), status);

            for (DALimit daLimit: daLimitList) {
                DAColumnsDTO daColumnsDTO = new DAColumnsDTO(daLimit.getColumnId(), daLimit.getRiskValue());
                DATableDTO daTableDTO = new DATableDTO(designation.getId(), daColumnsDTO, daLimit.getModifiedBy(), daLimit.getCreatedBy(), daLimit.getModifiedDate(), daLimit.getCreatedDate());

                daTableDTOList.add(daTableDTO);
            }

            for (DALimitTemp daLimitTemp: daLimitTempList) {
                DAColumnsDTO daColumnsDTO = new DAColumnsDTO(daLimitTemp.getColumnId(), daLimitTemp.getRiskValue());
                DATableDTO daTableDTO = new DATableDTO(designation.getId(), daColumnsDTO, daLimitTemp.getModifiedBy(), daLimitTemp.getCreatedBy(), daLimitTemp.getModifiedDate(), daLimitTemp.getCreatedDate());

                daTempTableDTOList.add(daTableDTO);
            }
        }
        tableRS.setApprovedDALimits(daTableDTOList);
        tableRS.setPendingDALimits(daTempTableDTOList);

        return tableRS;

    }



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public DADesignationAndDALimitsDTO getAllDALimitsAndDesignations(CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Get All DA Limits and Designations :{} ", credentialsDTO.getUserName());

        DADesignationAndDALimitsDTO daDesignationAndDALimitsDTO = new DADesignationAndDALimitsDTO();

        FullDATableWithTypeDTO fullDATableRS = getAllDALimitsWithApproveStatus(credentialsDTO);
        DADesignationsWithType daDesignationMasterData = getAllDADesignationWithTypes();

        daDesignationAndDALimitsDTO.setDaDesignationMasterData(daDesignationMasterData);
        daDesignationAndDALimitsDTO.setFullDATableRS(fullDATableRS);

        LOG.info("END : Get All DA Limits and Designations :{} ", daDesignationAndDALimitsDTO);
        return daDesignationAndDALimitsDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public DADesignationAndDALimitsDTO changeDisplayOrder(ChangeDADisplayOrderRQ req, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : changeDisplayOrder : {} by :{} ", req, credentialsDTO.getUserName());

        DADesignationAndDALimitsDTO daDesignationAndDALimitsDTO = new DADesignationAndDALimitsDTO();

        if(req != null && req.getDaDisplayOrderDTOList() != null){

            for (DADisplayOrderDTO daDisplayOrderDTO : req.getDaDisplayOrderDTOList()) {
                DADesignationMasterData dADesignationModel = dADesignationMasterDao.findById(daDisplayOrderDTO.getDesid()).get();

                dADesignationModel.setDisplayOrder(daDisplayOrderDTO.getDisplayOrder());
                DADesignationMasterData newDADesignationModel = this.dADesignationMasterDao.saveAndFlush(dADesignationModel);
            }
            daDesignationAndDALimitsDTO  = this.getAllDALimitsAndDesignations(credentialsDTO);
        }
        LOG.info("END : Add DA Limits : {} by :{} ", req, credentialsDTO.getUserName());
        return daDesignationAndDALimitsDTO;
    }


    public List<DADesignationCodeDTO> getCommitteDesignationsByJsonService() {
        List<DADesignationCodeDTO> response = new ArrayList<>();
        try {
            JSONParser parser = new JSONParser();
            String templatePath = this.environment.getProperty("apps.print.html.template.path") + File.separator
                    + "json" + File.separator + "committee" + ".json";


            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(templatePath));
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<List<DADesignationCodeDTO>> jacksonTypeReference = new TypeReference<List<DADesignationCodeDTO>>() {
            };
            if (!jsonArray.isEmpty()) {
                response = objectMapper.readValue(String.valueOf(jsonArray), jacksonTypeReference);
            } else {
                response = new ArrayList<>();
            }
        } catch (IOException e) {
            LOG.error("ERROR : ", e);
        } catch (ParseException e) {
            LOG.error("ERROR : ", e);
        } catch (Exception e) {
            LOG.error("ERROR : ", e);
        }
        return response;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<DADesignationCodeDTO> getCommitteeDesignations(CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Get All DA Limits and Designations :{} ", credentialsDTO.getUserName());
        List<DADesignationCodeDTO> finalCommitteeDesignationList = new ArrayList<>();
        List<DADesignationMasterDataDTO> allDADesignationMasterData = getAllDADesignationDetails();
        List<DADesignationMasterDataDTO> committeeDADesignationMasterData = new ArrayList<>();

        for (DADesignationMasterDataDTO daDesignation: allDADesignationMasterData) {
            if (daDesignation.getIsCommittee() != null && daDesignation.getIsCommittee().equals("Y")){
                committeeDADesignationMasterData.add(daDesignation);
            }
        }

        List<DADesignationCodeDTO> committeeDesignationsFromJson = getCommitteDesignationsByJsonService();
        List<DADesignationCodeDTO> committeeDesignationsFromDB = committeeTypeService.getAllCommitteeDesignataions(credentialsDTO);

        committeeDesignationsFromJson.addAll(committeeDesignationsFromDB);

        for (DADesignationCodeDTO committeeDaDesignation: committeeDesignationsFromJson) {
            boolean existsInMasterData = false;
            for (DADesignationMasterDataDTO daDesignation: committeeDADesignationMasterData) {
                if (committeeDaDesignation.getCode().equals(daDesignation.getDesignationCode())){
                    existsInMasterData = true;
                    break;
                }
            }
            if (!existsInMasterData) {
                finalCommitteeDesignationList.add(committeeDaDesignation);
            }
        }

        LOG.info("END : Get All DA Limits and Designations :{} ", credentialsDTO);
        Collections.sort(finalCommitteeDesignationList, Comparator.comparing(DADesignationCodeDTO::getCode));
        return finalCommitteeDesignationList;
    }

//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
//    public DADesignationListDTO getALlDesignations(CredentialsDTO credentialsDTO) throws AppsException {
//
//        LOG.info("START : Get All DA Limits and Designations :{} ", credentialsDTO.getUserName());
//        DADesignationListDTO designations = new DADesignationListDTO();
//        List<DADesignationCodeDTO> individualDesignations = getDADesignationDropDownList();
//        List<DADesignationCodeDTO> committeeDesignations = getCommitteeDesignations(credentialsDTO );
//        designations.setCommitteeDesignations(committeeDesignations);
//        designations.setIndividualDesignations(individualDesignations);
//
//        LOG.info("END : Get All DA Limits and Designations :{} ", credentialsDTO);
//        return designations;
//    }

}
