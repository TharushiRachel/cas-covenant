package com.itechro.cas.controller.notification;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.model.dto.notification.EmailUnSubscribeDTO;
import com.itechro.cas.model.dto.notification.EmailUnSubscribeRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.notification.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${api.prefix}/notification")
public class NotificationController extends BaseController {

    @Autowired
    NotificationService notificationService;

    private static final Logger LOG = LoggerFactory.getLogger(NotificationController.class);

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateFpUpdateEmailSubscribeState", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<EmailUnSubscribeDTO> setFpUpdateEmailSubscribeState(@RequestBody EmailUnSubscribeRQ searchRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Facility Paper update email notification status update: {} by user {}", searchRQ, credentialsDTO.getUserID());

        EmailUnSubscribeDTO emailUnSubscribeDTO = this.notificationService.updateEmailNotificationGetStatus(searchRQ, credentialsDTO);

        LOG.info("END : Facility Paper update email notification status update: {} by user {}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(emailUnSubscribeDTO, HttpStatus.OK);
    }
}
