package com.itechro.cas.service.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;


public class SampathEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(SampathEmailService.class);

    private Properties prop = null;

    public SampathEmailService(String pSMTPServer) {
        LOG.info("*** smtpserver : " + pSMTPServer);
        prop = new Properties();
        prop.put("mail.smtp.host", pSMTPServer);
    }

    @Async
    public boolean send(String sFromAddr, InternetAddress[] addressTo, InternetAddress[] addressCc, String subject, String body) {
        boolean bFlg = false;
        try {
            Session session = Session.getDefaultInstance(prop, null);

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(sFromAddr));

            LOG.info("Email Messenger send Email Form Address : {}", sFromAddr);

            message.setSubject(subject);

            LOG.info("Email Messenger send Email Subject : {} ", subject);

            message.setContent(body, "text/html");

            message.setSentDate(new Date());

            LOG.info("Email Messenger send Email To Address  : {} ", addressTo);
            message.setRecipients(Message.RecipientType.TO, addressTo);

            try {
                message.setRecipients(Message.RecipientType.CC, addressCc);
                LOG.info("EmailMessenger send() message setRecipients ", addressCc);
            } catch (Exception ex) {
                LOG.error("EmailMessenger send() > Unable to set CC Addr > " + ex);
            }
            Transport.send(message);
            LOG.info("Email Messenger send Email Form : {} To : {} ", sFromAddr, addressTo);
            bFlg = true;

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Email Messenger send Exception : " + e);
            bFlg = false;
        }
        LOG.info("Email Messenger send send success : {} ", bFlg);
        return bFlg;
    }

    public boolean send(String sFromAddr, InternetAddress[] addressTo, InternetAddress[] addressCc, String subject, String body, List<File> attachments) {
        System.out.println("EmailMessenger.send() : E");
        boolean bFlg = false;
        try {
            Session session = Session.getDefaultInstance(prop, null);
            LOG.info("EmailMessenger.send() session");
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(sFromAddr));
            LOG.info("EmailMessenger.send() message.setFrom");

            message.setSubject(subject);
            LOG.info("EmailMessenger.send() message.setSubject");

            if (!attachments.isEmpty()) {

                Multipart multipart = new MimeMultipart();

                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(body, "text/html");
                multipart.addBodyPart(messageBodyPart);

                for (File attachment : attachments) {
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    attachmentPart.attachFile(attachment);
                    attachmentPart.setFileName(attachment.getName());

                    multipart.addBodyPart(attachmentPart);
                }

                message.setContent(multipart, "text/html");

            } else {
                message.setContent(body, "text/html");
            }

            message.setSentDate(new Date());
            LOG.info("EmailMessenger.send() message.setSentDate");

            LOG.info("EmailMessenger.send() addressTo : {} ", addressTo);
            message.setRecipients(Message.RecipientType.TO, addressTo);

            LOG.info("EmailMessenger.send() message.setRecipients(addressTo)");
            try {
                message.setRecipients(Message.RecipientType.CC, addressCc);
                LOG.info("EmailMessenger.send() message.setRecipients(addressCc)");
            } catch (Exception ex) {
                LOG.info("EmailMessenger.send() > Unable to set CC Addr > " + ex);
            }

            Transport.send(message);
            LOG.info("EmailMessenger.send() message.send");
            bFlg = true;

        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("EmailMessenger.send()" + e);
            bFlg = false;
        }
        LOG.info("EmailMessenger.send() : L");
        return bFlg;
    }

}


