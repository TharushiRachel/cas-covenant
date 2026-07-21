package com.itechro.cas.security.helper;

import com.itechro.cas.config.CasProperties;
import com.itechro.cas.dao.master.jdbc.UserJDBCDao;
import com.itechro.cas.exception.impl.AppsCommonErrorCode;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.integration.request.UmpDetailLoadByAdIdRQ;
import com.itechro.cas.model.dto.integration.request.UpmDetailLoadRQ;
import com.itechro.cas.model.dto.integration.response.UpmDetailResponse;
import com.itechro.cas.model.dto.master.UserDTO;
import com.itechro.cas.security.SecurityService;
import com.itechro.cas.service.integration.IntegrationService;
import com.itechro.cas.service.master.SystemParameterService;
import com.itechro.cas.util.LDAPAuthenticator;
import com.itechro.cas.util.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SortedSet;

public class UserRetriever {

    private static final Logger LOG = LoggerFactory.getLogger(UserRetriever.class);

    private String userDetailsStr;

    private UserDTO userDTO;

    private Boolean isActiveDirectoryEnabled;

    private boolean isUPMEnabled;

    private CasProperties casProperties;

    private UserJDBCDao userJDBCDao;

    private SystemParameterService systemParameterService;

    private IntegrationService integrationService;

    private SecurityService securityService;

    public UserRetriever(String userDetailsStr) {
        this.userDetailsStr = userDetailsStr;
    }

    public UserRetriever initiate() {
        this.isActiveDirectoryEnabled = this.systemParameterService.isActiveDirectoryEnabled();
        this.isUPMEnabled = systemParameterService.isUpmEnabled();
        return this;
    }

    public UserRetriever validate() throws AppsException {
        if (this.useLDAPValidation()) {
            String[] split = PasswordUtil.getSplittedUserDetailsStr(userDetailsStr);

            if (split == null || split.length != 2) {
                //LOG.info("ERROR : invalid login request > Cannot split");
                if (split == null) {
                    //LOG.info("ERROR : split is null");
                } else {
                    //LOG.info("ERROR : split 0 >> {}", split[0]);
                }
                throw new AppsException(AppsCommonErrorCode.APPS_INVALID_LOGIN_REQUEST);
            }
        } else {
            if (StringUtils.isEmpty(userDetailsStr)) {
                //LOG.info("ERROR : user details is empty");
                throw new AppsException(AppsCommonErrorCode.APPS_INVALID_LOGIN_REQUEST);
            }
        }
        return this;
    }

    public UserRetriever retrieveUser() throws AppsException {

        if (this.useLDAPValidation()) {
            this.getLDAPUser();
        } else {
            this.getInternalUser();
        }

        return this;
    }

    private boolean useLDAPValidation() {
        return this.isActiveDirectoryEnabled && !this.casProperties.isAgentModuleEnabled();
    }

    private void getLDAPUser() throws AppsException {

        String[] split = PasswordUtil.getSplittedUserDetailsStr(userDetailsStr);
        LOG.warn("split RES {}",split);
        String userName = split[0];
        LDAPAuthenticator ldapAuthenticator = new LDAPAuthenticator(this.casProperties);
        userDTO = ldapAuthenticator.getAuthenticatedUser(split[0], split[1]);

        if (userDTO == null) {
//            LOG.info("ERROR : invalid ldap login user details {}", userDetailsStr);
            //LOG.info("ERROR : invalid ldap login user details > Unable to get user from LDAP : {}", userName);
            throw new AppsException(AppsCommonErrorCode.APPS_UNABLE_TO_FIND_LDAP_USER);
        }

        //LOG.info("INFO : cache Service started : {}", userName);
        this.securityService.cacheUserCredentials(userDTO.getUserName(), split[1]);
        //LOG.info("END : INFO : cache Service end : {}", userName);
        if (isUPMEnabled) {
            this.setUpmDetails(userName);
        } else {
            userDTO.setBranchCode("001");
//            LOG.warn("UPM is disabled for login {}", userDetailsStr);
            LOG.warn("UPM is disabled for login");
        }

    }

    private void getInternalUser() throws AppsException {
        userDTO = userJDBCDao.getUserDetails(userDetailsStr, this.casProperties.isAgentModuleEnabled());

        if (userDTO == null) {
            //LOG.info("ERROR : Invalid user login");
            throw new AppsException(AppsCommonErrorCode.APPS_UNAUTHORISED);
        }

        if (userDTO.isAgent()) {
            this.assembleAgentUserDetails();
        } else {
            this.assembleAdminUserDetails();
        }
    }

    private void assembleAgentUserDetails() throws AppsException {
        //TODO
    }

    private void assembleAdminUserDetails() throws AppsException {
        if (isUPMEnabled) {
            this.setUpmDetails(userDetailsStr);
        } else {
            userDTO.setBranchCode("004");
            userDTO.setUpmGroupCode("10");
            LOG.warn("UPM is disabled");
        }
    }

    private void setUpmDetails(String userName) throws AppsException {

        SortedSet<String> privileges = null;
        UmpDetailLoadByAdIdRQ upmDetailLoadRQ = new UmpDetailLoadByAdIdRQ();

        upmDetailLoadRQ.setAdUserID(userName);
        upmDetailLoadRQ.setAppCode(this.casProperties.getApplicationCode());
        //LOG.info("INFO : UPM User search service Started : {} : START Date Time ===> {}", userName, new Date());
        UpmDetailResponse responseDTO = integrationService.getUpmDetailsByAdUserIdAndAppCode(upmDetailLoadRQ);
        //LOG.info("INFO : END UPM User search response received : {} : END Date Time ===> {}", userName, new Date());

        //LOG.info("INFO : UPM User response : {}", responseDTO);

        if (StringUtils.isEmpty(responseDTO.getDivCode())) {
            //LOG.info("WARN : User does not exist in UPM {} : User response {}", userName, responseDTO);
            throw new AppsException(AppsCommonErrorCode.APPS_UNABLE_TO_FIND_UPM_USER);
        }

        if (StringUtils.isNotEmpty(responseDTO.getUserID())) {
            userDTO.setUserID(Integer.parseInt(responseDTO.getUserID()));
        }

        userDTO.setUserName(userName);
        userDTO.setDivCode(responseDTO.getDivCode());//this is a request from Sanka Wijesinge ; to update SOL id ==> DIV Code
        userDTO.setSolID(responseDTO.getSolID());
        userDTO.setUpmID(responseDTO.getApplicationCode());
        userDTO.setFirstName(responseDTO.getFirstName());
        userDTO.setLastName(responseDTO.getLastName());
        userDTO.setUpmGroupCode(responseDTO.getApplicationSecurityClass());
        userDTO.setDesignation(responseDTO.getDesignationDescription());
        if (StringUtils.isNotEmpty(responseDTO.getFunctionCode2())) {
            //LOG.info("INFO : User {} is a assistant user of user id {} ", userName, responseDTO.getFunctionCode2());

            UpmDetailLoadRQ superVisorLoadRQ = new UpmDetailLoadRQ();
            superVisorLoadRQ.setUserID(responseDTO.getFunctionCode2());
            superVisorLoadRQ.setAppCode(this.casProperties.getApplicationCode());
            UpmDetailResponse supervisorResponse = integrationService.getUpmDetailsByUserIdAndAppCode(superVisorLoadRQ);
            UserDTO superVisor = new UserDTO(supervisorResponse);

            if (StringUtils.isEmpty(superVisor.getDivCode())) {
                //LOG.info("WARN : User does not exist in UPM {} : User response {}", userName, responseDTO);
                throw new AppsException(AppsCommonErrorCode.APPS_UNABLE_TO_FIND_UPM_USER);
            }

            if (!responseDTO.getFunctionCode2().equals(superVisor.getUserID().toString())) {
                //LOG.info("WARN : Invalid User IDs {} : User response {} : Supervisor {}", userName, responseDTO, superVisor);
                throw new AppsException(AppsCommonErrorCode.APPS_UNABLE_TO_FIND_UPM_USER);
            }

//            if (!responseDTO.getDivCode().equals(superVisor.getDivCode())) {
//                LOG.info("WARN : Invalid User Divisions {} : User response {} : Supervisor {}", userName, responseDTO, superVisor);
//                throw new AppsException(AppsCommonErrorCode.APPS_UNABLE_TO_FIND_UPM_USER);
//            }
            userDTO.setIsAssistantUser(Boolean.TRUE); // set supervisor upm details
            userDTO.setSupervisor(superVisor); // set supervisor upm details
            privileges = this.userJDBCDao.getUserPrivilegesByUMPCode(supervisorResponse.getApplicationSecurityClass(), superVisor.getDivCode());
        } else {
            userDTO.setIsAssistantUser(Boolean.FALSE);
            privileges = this.userJDBCDao.getUserPrivilegesByUMPCode(responseDTO.getApplicationSecurityClass(), responseDTO.getDivCode());
        }
        if (privileges == null || privileges.isEmpty()) {
//            LOG.info("WARN : User does not have privileges {}", userDetailsStr);
            //LOG.info("WARN : User does not have privileges : {}", userName);
            throw new AppsException(AppsCommonErrorCode.APPS_UNABLE_TO_FIND_USER_SECURITY_CLASS);
        }
        userDTO.setPrivileges(privileges);
    }

    public void setCasProperties(CasProperties casProperties) {
        this.casProperties = casProperties;
    }

    public void setUserJDBCDao(UserJDBCDao userJDBCDao) {
        this.userJDBCDao = userJDBCDao;
    }

    public void setSystemParameterService(SystemParameterService systemParameterService) {
        this.systemParameterService = systemParameterService;
    }

    public void setIntegrationService(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }
}