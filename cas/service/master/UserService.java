package com.itechro.cas.service.master;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.dao.master.RoleDao;
import com.itechro.cas.dao.master.UserDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.common.PairDTO;
import com.itechro.cas.model.domain.master.User;
import com.itechro.cas.model.dto.master.PasswordUpdateDTO;
import com.itechro.cas.model.dto.master.UserDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.security.SecurityService;
import com.itechro.cas.service.notification.NotificationService;
import com.itechro.cas.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CasProperties casProperties;

    @Autowired
    private NotificationService notificationService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public UserDTO addUser(UserDTO userDTO, CredentialsDTO credentialsDTO, Date createdDate) throws AppsException {
        LOG.info("START : Add User : {} : {}", userDTO, credentialsDTO.getUserName());

        if (this.userDao.findByEmail(userDTO.getUserName()) != null) {
            LOG.error("ERROR : User email already exists : {} : {}", userDTO, credentialsDTO.getUserName());
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_USER_EMAIL_ALREADY_EXIST);
        }

        if (this.userDao.findByUserNameIgnoreCase(userDTO.getUserName()) != null) {
            LOG.error("ERROR : User user name already exists : {} : {}", userDTO, credentialsDTO.getUserName());
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_USER_USER_NAME_ALREADY_EXIST);
        }

        User user = new User();
        user.setPassword(PasswordUtil.generateEncodedPassword(new BCryptPasswordEncoder(), userDTO.getPassword()));
        user.setStatus(userDTO.getStatus());
        user.setUserName(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setTitle(userDTO.getTitle());
        user.setEmail(userDTO.getEmail());
        user.setGender(userDTO.getGender());
        user.setUserType(userDTO.getUserType());
        user.setUserRefID(userDTO.getUserRefID());

        user.setCreatedDate(createdDate);
        user.setCreatedBy(credentialsDTO.getUserName());

        if (!userDTO.getRoles().isEmpty()) {
            user.getRoles().addAll(roleDao.findAllById(userDTO.getRoles()));
        }

        this.securityService.expireUserFromCache(user.getUserName());
        user = userDao.saveAndFlush(user);

        LOG.info("END : Add User : {} : {}", userDTO, credentialsDTO.getUserName());

        return new UserDTO(user);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public UserDTO updateUser(UserDTO userDTO, CredentialsDTO credentialsDTO, Date createdDate) throws AppsException {
        LOG.info("START : Update User : {} : {}", userDTO, credentialsDTO.getUserName());

        User user = this.userDao.getUserByUserRefIDAndUserType(userDTO.getUserRefID(), userDTO.getUserType());

        if (!userDTO.getUserName().equals(user.getUserName())) {
            if (userDao.findByUserNameIgnoreCase(userDTO.getUserName()) != null) {
                LOG.error("ERROR : User Already exists : {} : {}", userDTO, credentialsDTO.getUserID());
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_USER_ALREADY_EXISTS);
            }
        }

        user.setStatus(userDTO.getStatus());
        user.setUserName(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setTitle(userDTO.getTitle());
        user.setEmail(userDTO.getEmail());
        user.setGender(userDTO.getGender());
        user.setUserType(userDTO.getUserType());

        user.setModifiedDate(createdDate);
        user.setModifiedBy(credentialsDTO.getUserName());

        if (!userDTO.getRemovedRoles().isEmpty()) {
            user.removeRoles(roleDao.findAllById(userDTO.getRemovedRoles()));
        }
        if (!userDTO.getAddedRoles().isEmpty()) {
            user.getRoles().addAll(roleDao.findAllById(userDTO.getAddedRoles()));
        }

        this.securityService.expireUserFromCache(user.getUserName());
        user = userDao.saveAndFlush(user);

        LOG.info("END : Update User : {} : {}", userDTO, credentialsDTO.getUserName());

        return new UserDTO(user);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public void updateUserPassword(PasswordUpdateDTO updateDTO, CredentialsDTO credentialsDTO) throws AppsException {
        DomainConstants.PasswordUpdateAction action = updateDTO.getAction();
        User user = userDao.getOne(updateDTO.getUserID());
        boolean isReset = false;
        PairDTO<String, String> passwords = null;

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        switch (action) {
            case RESET:
                if (this.casProperties.isAutoGeneratedPasswordEnabled()) {
                    passwords = PasswordUtil.generateRandomEncodedPassword(passwordEncoder, Integer.parseInt(casProperties.getUserGeneratedPasswordLength()));
                    user.setPassword(passwords.getRight());
                } else {
                    String encodedPassword = PasswordUtil.generateEncodedPassword(passwordEncoder, updateDTO.getNewPassword());
                    user.setPassword(encodedPassword);
                }
                isReset = true;
                break;
            case UPDATE:
                String oldPassword = updateDTO.getOldPassword();
                String existingPassword = user.getPassword();
                if (passwordEncoder.matches(oldPassword, existingPassword)) {
                    String encodedPassword = PasswordUtil.generateEncodedPassword(passwordEncoder, updateDTO.getNewPassword());
                    user.setPassword(encodedPassword);
                } else {
                    LOG.error("ERROR : password mismatch : {} : {}", updateDTO, credentialsDTO.getUserID());
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_MURAPADAYA_MISMATCH);
                }
                break;
        }

        user = userDao.saveAndFlush(user);
        if (isReset) {
            if (this.casProperties.isAllowSendingEmail()) {
                if (this.casProperties.isAutoGeneratedPasswordEnabled()) {
                    this.sendPassword(new UserDTO(user), passwords.getLeft(), true);
                } else {
                    this.sendPassword(new UserDTO(user), updateDTO.getNewPassword(), true);
                }
            }
        }
        this.securityService.expireUserFromCache(user.getUserName());
    }

    private void sendPassword(UserDTO userDTO, String password, boolean isPasswordReset) {
        this.notificationService.notifyPasswordChange(userDTO, password, isPasswordReset);
    }

}
