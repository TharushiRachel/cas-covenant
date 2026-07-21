package com.itechro.cas.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itechro.cas.exception.impl.AppsCommonErrorCode;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.AppsRuntimeException;
import com.itechro.cas.model.dto.master.UserDTO;
import com.itechro.cas.model.security.CasUserDetails;
import com.itechro.cas.service.master.SystemParameterService;
import com.itechro.cas.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class IexpressDetailsService implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(IexpressDetailsService.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private SecurityService securityService;

    @Autowired
    private SystemParameterService systemParameterService;

    @Override
    public UserDetails loadUserByUsername(String userIdentifiableToken) throws UsernameNotFoundException {
        UserDTO user = null;
        try {

//            LOG.info("-----------------> IexpressDetailsService : loadUserByUsername --------------> {}" , userIdentifiableToken);
            Boolean activeDirectoryEnabled = this.systemParameterService.isActiveDirectoryEnabled();
            if (activeDirectoryEnabled) {
                String userPassword = this.securityService.getUserCredentials(userIdentifiableToken);
                String combinedUserDetailsStr = PasswordUtil.getCombinedUserDetailsStr(userIdentifiableToken, userPassword);
                user = securityService.getUserByUsername(combinedUserDetailsStr);
            } else {
                user = securityService.getUserDetailsByUserAD(userIdentifiableToken);
            }
        } catch (AppsException e) {
            if (e.containsErrorCode(AppsCommonErrorCode.APPS_EMPTY_RESULT)) {
                LOG.warn("User with name {} does not exist", userIdentifiableToken);
                throw new UsernameNotFoundException("Invalid userIdentifiableToken");
            } else {
                LOG.warn("Getting user for {} failed with error {}", userIdentifiableToken, e.getAppsErrorMessages());
                throw new AppsRuntimeException();
            }
        } catch (Exception e) {
            LOG.warn("Getting user for {} failed with unknown error ", e);
            throw new AppsRuntimeException();
        }

        return new CasUserDetails(user);
    }

}
