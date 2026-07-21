package com.itechro.cas.service.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.casmaster.EnvironmentalRiskAudDao;
import com.itechro.cas.dao.casmaster.EnvironmentalRiskDao;
import com.itechro.cas.dao.casmaster.EnvironmentalRiskTempDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.casmaster.EnvironmentalRisk;
import com.itechro.cas.model.domain.casmaster.EnvironmentalRiskAud;
import com.itechro.cas.model.domain.casmaster.EnvironmentalRiskTemp;
import com.itechro.cas.model.dto.casmaster.EnvRiskRequestDTO;
import com.itechro.cas.model.dto.casmaster.EnvRiskResponseDTO;
import com.itechro.cas.model.dto.casmaster.EnvironmentalRiskDTO;
import com.itechro.cas.model.dto.casmaster.EnvironmentalRiskRespDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnvironmentalRiskService {

    private static final Logger LOG = LoggerFactory.getLogger(EnvironmentalRiskService.class);

    private final EnvironmentalRiskDao environmentalRiskDao;

    private final EnvironmentalRiskTempDao environmentalRiskTempDao;

    private final EnvironmentalRiskAudDao environmentalRiskAudDao;

    public EnvironmentalRiskService(EnvironmentalRiskDao environmentalRiskDao, EnvironmentalRiskTempDao environmentalRiskTempDao,
                                    EnvironmentalRiskAudDao environmentalRiskAudDao) {
        this.environmentalRiskDao = environmentalRiskDao;
        this.environmentalRiskTempDao = environmentalRiskTempDao;
        this.environmentalRiskAudDao = environmentalRiskAudDao;
    }

    public EnvRiskResponseDTO getRiskCategories() throws AppsException{
        EnvRiskResponseDTO response = new EnvRiskResponseDTO();

        try {
            if (!environmentalRiskTempDao.findAll().isEmpty()) {
                List<EnvironmentalRiskTemp> tempList = environmentalRiskTempDao.findAll();
                List<EnvironmentalRiskDTO> tempListDTO = tempList.stream().map(EnvironmentalRiskDTO::new).collect(Collectors.toList());
                response.setTempRiskList(tempListDTO);
            }

            if (!environmentalRiskDao.findAll().isEmpty()) {
                List<EnvironmentalRisk> masterList = environmentalRiskDao.findAll();
                List<EnvironmentalRiskDTO> masterListDTO = masterList.stream().map(EnvironmentalRiskDTO::new).collect(Collectors.toList());
                response.setMasterRiskList(masterListDTO);
            }

        } catch (Exception e){
            LOG.info("Failed categories: ", e);
            throw new AppsException("Failed to fetch risk categories.");
        }

        return response;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public EnvRiskResponseDTO saveTempRiskCategory(EnvRiskRequestDTO envRiskRequestDTO, CredentialsDTO credentialsDTO) throws AppsException{
        EnvRiskResponseDTO response = new EnvRiskResponseDTO();
        Date date = new Date();
        try {
            EnvironmentalRiskTemp environmentalRiskTemp = new EnvironmentalRiskTemp();

            environmentalRiskTemp.setParentId(envRiskRequestDTO.getParentId());
            environmentalRiskTemp.setParentCategoryId(envRiskRequestDTO.getParentCategoryId());
            environmentalRiskTemp.setDescription(envRiskRequestDTO.getDescription());
            environmentalRiskTemp.setScore(envRiskRequestDTO.getScore());
            environmentalRiskTemp.setType(envRiskRequestDTO.getType());
            environmentalRiskTemp.setStatus(envRiskRequestDTO.getStatus());
            environmentalRiskTemp.setCreatedBy(credentialsDTO.getUserName());
            environmentalRiskTemp.setCreatedDate(date);

            environmentalRiskTempDao.saveAndFlush(environmentalRiskTemp);

            response = this.getRiskCategories();
        } catch (Exception e){
            LOG.info("Failed to save temp risk category.");
            throw new AppsException("Failed to save risk category.");
        }

        return response;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = AppsException.class)
    public EnvRiskResponseDTO approveRejectCategory(EnvRiskRequestDTO envRiskRequestDTO, CredentialsDTO credentialsDTO) throws AppsException{
        EnvRiskResponseDTO response;
        Date date = new Date();
        try {
            if (envRiskRequestDTO.getApprovedStatus().equals(DomainConstants.MasterDataApproveStatus.APPROVED.getValue())) {
                approveCategory(envRiskRequestDTO,credentialsDTO);
            } else {
                //manage history data
                EnvironmentalRiskTemp environmentalRiskTemp = environmentalRiskTempDao.getOne(envRiskRequestDTO.getRiskCategoryId());
                EnvironmentalRiskDTO environmentalRiskDTO = new EnvironmentalRiskDTO(environmentalRiskTemp);

                environmentalRiskDTO.setApprovedStatus(envRiskRequestDTO.getApprovedStatus());
                environmentalRiskDTO.setApprovedBy(credentialsDTO.getUserName());
                environmentalRiskDTO.setApprovedDate(date);

                saveCategoryHistory(environmentalRiskDTO, credentialsDTO);

                //remove from temp data
                environmentalRiskTempDao.deleteById(envRiskRequestDTO.getRiskCategoryId());

                if (envRiskRequestDTO.getParentCategoryId() == 0){
                    //remove from temp data with related
                    environmentalRiskTempDao.deleteByParentId(envRiskRequestDTO.getRiskCategoryId());

                    //remove from master data with related
                    environmentalRiskDao.deleteByParentId(envRiskRequestDTO.getRiskCategoryId());
                }
            }

            response = this.getRiskCategories();
        } catch (Exception e){
            LOG.info("Failed to authorize risk category.");
            String message = "Failed to " + envRiskRequestDTO.getApprovedStatus().toLowerCase() + " risk category.";
            throw new AppsException(message);
        }

        return response;
    }

    public void approveCategory(EnvRiskRequestDTO envRiskRequestDTO, CredentialsDTO credentialsDTO){
        Date date = new Date();
        EnvironmentalRisk environmentalRisk = new EnvironmentalRisk();

        if (envRiskRequestDTO.getStatus().equals(AppsConstants.Status.ACT.toString())) {
            if (environmentalRiskDao.findById(envRiskRequestDTO.getParentCategoryId()).isPresent()) {
                environmentalRisk = environmentalRiskDao.getOne(envRiskRequestDTO.getParentCategoryId());

                environmentalRisk.setDescription(envRiskRequestDTO.getDescription());
                environmentalRisk.setScore(envRiskRequestDTO.getScore());
                environmentalRisk.setModifiedBy(credentialsDTO.getUserName());
                environmentalRisk.setModifiedDate(date);
            } else {
                environmentalRisk.setRiskCategoryId(envRiskRequestDTO.getRiskCategoryId());
                environmentalRisk.setParentId(envRiskRequestDTO.getParentId());
                environmentalRisk.setDescription(envRiskRequestDTO.getDescription());
                environmentalRisk.setScore(envRiskRequestDTO.getScore());
                environmentalRisk.setType(envRiskRequestDTO.getType());
                environmentalRisk.setStatus(envRiskRequestDTO.getStatus());
                environmentalRisk.setCreatedBy(credentialsDTO.getUserName());
                environmentalRisk.setCreatedDate(date);
            }
            environmentalRisk.setApprovedStatus(envRiskRequestDTO.getApprovedStatus());
            environmentalRisk.setApprovedBy(credentialsDTO.getUserName());
            environmentalRisk.setApprovedDate(date);

            //save or update master data
            environmentalRisk = environmentalRiskDao.saveAndFlush(environmentalRisk);

            //remove from temp data
            environmentalRiskTempDao.deleteById(envRiskRequestDTO.getRiskCategoryId());
        } else {
            environmentalRisk = environmentalRiskDao.getOne(envRiskRequestDTO.getParentCategoryId());
            environmentalRisk.setStatus(AppsConstants.Status.INA.toString());
            environmentalRisk.setModifiedBy(credentialsDTO.getUserName());
            environmentalRisk.setModifiedDate(date);

            //remove from temp data with related
            environmentalRiskTempDao.deleteByParentId(envRiskRequestDTO.getRiskCategoryId());
            environmentalRiskTempDao.deleteById(envRiskRequestDTO.getRiskCategoryId());

            //remove from master data with related
            environmentalRiskDao.deleteByParentId(envRiskRequestDTO.getParentCategoryId());
            environmentalRiskDao.deleteById(envRiskRequestDTO.getParentCategoryId());
        }
        //manage history data
        saveCategoryHistory(new EnvironmentalRiskDTO(environmentalRisk), credentialsDTO);
    }

    public void saveCategoryHistory(EnvironmentalRiskDTO environmentalRisk, CredentialsDTO credentialsDTO){
        Date date = new Date();
        try {
            EnvironmentalRiskAud environmentalRiskAud = new EnvironmentalRiskAud();

            environmentalRiskAud.setRiskCategoryId(environmentalRisk.getRiskCategoryId());
            environmentalRiskAud.setParentId(environmentalRisk.getParentId());
            environmentalRiskAud.setDescription(environmentalRisk.getDescription());
            environmentalRiskAud.setScore(environmentalRisk.getScore());
            environmentalRiskAud.setType(environmentalRisk.getType());
            environmentalRiskAud.setStatus(environmentalRisk.getStatus());
            environmentalRiskAud.setCreatedBy(environmentalRisk.getCreatedBy());
            environmentalRiskAud.setCreatedDate(environmentalRisk.getCreatedDate());
            environmentalRiskAud.setModifiedBy(credentialsDTO.getUserName());
            environmentalRiskAud.setModifiedDate(date);
            environmentalRiskAud.setApprovedStatus(environmentalRisk.getApprovedStatus());
            environmentalRiskAud.setApprovedBy(credentialsDTO.getUserName());
            environmentalRiskAud.setApprovedDate(date);

            environmentalRiskAudDao.saveAndFlush(environmentalRiskAud);
        } catch (Exception e){
            LOG.info("Failed to save history risk category.");
        }
    }

    public List<EnvironmentalRiskRespDTO> getEnvironmentalRiskTree() throws AppsException {

        List<EnvironmentalRiskRespDTO> response;

        try {
            List<EnvironmentalRisk> allRisks = environmentalRiskDao.findAll();

            response = allRisks.stream().map(risk -> {
                EnvironmentalRiskRespDTO dto = new EnvironmentalRiskRespDTO();
                dto.setRiskCategoryId(risk.getRiskCategoryId());
                dto.setParentId(risk.getParentId());
                dto.setDescription(risk.getDescription());
                dto.setScore(risk.getScore());
                dto.setType(risk.getType());
                return dto;
            }).collect(Collectors.toList());

        } catch (Exception e){
            LOG.info("Failed to fetch risk tree data ", e);
            throw new AppsException("Failed to fetch risk tree data.");
        }
        return response;
    }
}
