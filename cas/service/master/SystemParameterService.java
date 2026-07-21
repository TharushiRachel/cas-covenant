package com.itechro.cas.service.master;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.master.SystemParameterDao;
import com.itechro.cas.dao.master.jdbc.SystemParameterJDBCDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.domain.master.SystemParameter;
import com.itechro.cas.model.dto.master.SystemParamDTO;
import com.itechro.cas.model.dto.master.SystemParamSearchRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
public class SystemParameterService {

    private static final Logger LOG = LoggerFactory.getLogger(SystemParameterService.class);

    @Autowired
    private SystemParameterJDBCDao systemParameterJdbcDao;

    @Autowired
    private SystemParameterDao systemParameterDao;

    @Value("${apps.authentication.sso}")
    private boolean isSSOAuthentication;


    private static class ParamKeys {
        static final String ACTIVE_DIRECTORY_ENABLED = "CAS_1";
        static final String UPM_ENABLED = "CAS_2";
        static final String TINY_MCE_ENABLED = "CAS_3";
        static final String FACILITY_OUTSTANDING_AMOUNT_VALIDATION_ENABLED = "CAS_4";
        static final String BRANCH_LEVEL_UPM_GROUP_CODE_VALUES = "CAS_5";
        static final String IS_CUSTOMER_DIRECT_SEARCH_FROM_BANK = "CAS_6";
        static final String IS_TOTAL_CHILD_FACILITY_AMOUNT_VALIDATION_IS_ENABLED = "CAS_7";
        static final String BRANCH_DIV_CODE_LIMIT = "CAS_8";
        static final String DEFAULT_APPLICATION_FORM_FORWARDING_UPM_GROUPS = "CAS_9";
        static final String IS_SAME_USER_UPM_LEVEL_TRANSFER_ENABLED = "CAS_10";
        static final String DIV_CODE_IGNORED_UPM_GROUPS = "CAS_11"; // This is to use when papers UPM Group base forwarding for higher level user groups
    }

    private String getParamValue(String paramKey) {
        return this.systemParameterJdbcDao.getParameters().get(paramKey);
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean isActiveDirectoryEnabled() {
       // return AppsConstants.YesNo.valueOf(this.getParamValue(ParamKeys.ACTIVE_DIRECTORY_ENABLED)).getBoolVal();
        return !isSSOAuthentication;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean isUpmEnabled() {
        return AppsConstants.YesNo.valueOf(this.getParamValue(ParamKeys.UPM_ENABLED)).getBoolVal();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean isTinyMCEEnabled() {
        return AppsConstants.YesNo.valueOf(this.getParamValue(ParamKeys.TINY_MCE_ENABLED)).getBoolVal();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean isFacilityOutstandingAmountValidationEnabled() {
        return AppsConstants.YesNo.valueOf(this.getParamValue(ParamKeys.FACILITY_OUTSTANDING_AMOUNT_VALIDATION_ENABLED)).getBoolVal();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean isSameLevelTransferEnabled() {
        return AppsConstants.YesNo.valueOf(this.getParamValue(ParamKeys.IS_SAME_USER_UPM_LEVEL_TRANSFER_ENABLED)).getBoolVal();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public String getBranchLevelUPMGroupCodeValues() {
        return this.getParamValue(ParamKeys.BRANCH_LEVEL_UPM_GROUP_CODE_VALUES);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public String getDIVCodeIgnoredUPMGroups() {
        return this.getParamValue(ParamKeys.DIV_CODE_IGNORED_UPM_GROUPS);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public String getMaxDivCodeLimit() {
        return this.getParamValue(ParamKeys.BRANCH_DIV_CODE_LIMIT);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean isCustomerDirectSearchFromBankEnabled() {
        return AppsConstants.YesNo.valueOf(this.getParamValue(ParamKeys.IS_CUSTOMER_DIRECT_SEARCH_FROM_BANK)).getBoolVal();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean isTotalChildFacilitiesAmountValidationIsEnabled() {
        return AppsConstants.YesNo.valueOf(this.getParamValue(ParamKeys.IS_TOTAL_CHILD_FACILITY_AMOUNT_VALIDATION_IS_ENABLED)).getBoolVal();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, String> getParameters() {
        return this.systemParameterJdbcDao.getParameters();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, SystemParamDTO> getSystemParameters() {
        return this.systemParameterJdbcDao.getSystemParameters();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<SystemParamDTO> getPagedSystemParameters(SystemParamSearchRQ searchRQ) {
        return systemParameterJdbcDao.getPagedSystemParameters(searchRQ);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public SystemParamDTO getSystemParameterUpdateDTO(Integer systemParameterID) {
        SystemParameter systemParameter = this.systemParameterDao.getOne(systemParameterID);
        return new SystemParamDTO(systemParameter);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public SystemParamDTO loadSystemParameterByKey(String key) {
        SystemParameter systemParameter = this.systemParameterDao.getSystemParameterByParamKey(key);
        return new SystemParamDTO(systemParameter);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public SystemParamDTO updateSystemParameter(SystemParamDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START : Edit System Parameter : {} : {}", updateDTO, credentialsDTO.getUserID());

        Date date = new Date();
        SystemParameter systemParameter = this.systemParameterDao.getOne(updateDTO.getSystemParameterID());

        systemParameter.setParamName(updateDTO.getParamName());
        systemParameter.setDescription(updateDTO.getDescription());
        systemParameter.setParamValue(updateDTO.getParamValue());
        systemParameter.setStatus(updateDTO.getStatus());

        systemParameter.setModifiedBy(credentialsDTO.getUserID());
        systemParameter.setModifiedDate(date);

        systemParameter = systemParameterDao.saveAndFlush(systemParameter);

        LOG.info("END : Edit System Parameter : {} : {}", updateDTO, credentialsDTO.getUserID());
        return new SystemParamDTO(systemParameter);
    }
}
