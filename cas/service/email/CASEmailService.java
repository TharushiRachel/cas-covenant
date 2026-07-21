package com.itechro.cas.service.email;

import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.dto.email.AttachmentDTO;
import com.itechro.cas.model.dto.email.EmailAttachment;
import com.itechro.cas.model.dto.email.EmailSendRequest;
import com.itechro.cas.model.security.CredentialsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CASEmailService implements EnvironmentAware {

    private static final Logger LOG = LoggerFactory.getLogger(CASEmailService.class);

    private Environment environment;

    @Autowired
    @Qualifier("emailTemplateEngine")
    private TemplateEngine templateEngine;

    @Value("${apps.mail.server.from.other}")
    private String sFromAddr;

    @Value("${apps.mail.server.other}")
    private String mailServerOther;

    @Value("${apps.emails.attachments.size.limit}")
    private int attachmentSizeLimit;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public void sendMail(EmailSendRequest request) throws AppsException {
        SampathEmailService sampathEmailService = new SampathEmailService(mailServerOther);

        boolean isSent = false;
        try {
            String templatePath = this.environment.getProperty("apps.print.html.template.path") + File.separator
                    + "mail" + File.separator + "html" + File.separator + request.getTemplateName() + ".html";

            Context context = new Context();
            context.setVariables(request.getParams());
            String emailContent = "";

            LOG.info("request.getToAddresses() {}", request.getToAddresses());
            if (request.getToAddresses() != null && !request.getToAddresses().isEmpty()) {

                for (String emailAddress : request.getToAddresses()) {

                    if (emailAddress != null && !emailAddress.isEmpty()) {
                        InternetAddress[] toAddressArr = {
                                new InternetAddress(emailAddress)
                        };

                        if ((request.getAttachments() != null) && (!request.getAttachments().isEmpty())) {

                            List<File> attachments = new ArrayList<>();
                            List<String> unAttachmentFileNames = new ArrayList<>();

                            double attachedSize = 0;

                            for (EmailAttachment attachment : request.getAttachments()) {
                                String fileName = attachment.getFileName() + ".pdf";
                                File file = new File(fileName);

                                byte[] bytes = Base64.getDecoder().decode(attachment.getFileDataUri());

                                try (FileOutputStream os = new FileOutputStream(file)) {
                                    os.write(bytes);
                                }

                                long fileLength = file.length();
                                double kilobytes = (double) fileLength / 1024;
                                double megabytes = kilobytes / 1024;
                                double newSize = attachedSize + megabytes;

                                if (attachedSize < attachmentSizeLimit && newSize < attachmentSizeLimit) {
                                    attachments.add(file);
                                    attachedSize = attachedSize + megabytes;
                                } else {
                                    unAttachmentFileNames.add(file.getName());
                                }
                            }

                            if (!unAttachmentFileNames.isEmpty()) {
                                String fileNames = String.join(", ", unAttachmentFileNames);
                                context.setVariable("message", "There are un-attached files. Please get those file from CAS system. Those files are, " + fileNames);
                            }
                            emailContent = this.templateEngine.process(templatePath, context);
                            isSent = sampathEmailService.send(sFromAddr, toAddressArr, null, request.getSubject(), emailContent, attachments);

                            // delete created files
                            for (EmailAttachment attachment : request.getAttachments()) {
                                String fileName = attachment.getFileName() + ".pdf";
                                File fileToDelete = new File(fileName);
                                fileToDelete.delete();
                            }
                        } else {
                            emailContent = this.templateEngine.process(templatePath, context);
                            isSent = sampathEmailService.send(sFromAddr, toAddressArr, null, request.getSubject(), emailContent);
                        }

                        LOG.info("Email Send  {}", isSent);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Error Email Sending ", e);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_SYSTEM_EMAIL_FAILED, e);
        }
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = AppsException.class)
    public void sendMailAsync(EmailSendRequest request, CredentialsDTO credentialsDTO) throws AppsException {
        try {
            this.sendMail(request);
        } catch (AppsException e) {
            LOG.error("ERROR : Asynchronous email sending failed : {} : {}", request, credentialsDTO.getUserID(), e);
        }
    }

    public Environment getEnvironment() {
        return environment;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public void sendMailService(EmailSendRequest request) throws AppsException {
        SampathEmailService sampathEmailService = new SampathEmailService(mailServerOther);

        boolean isSent = false;
        try {
            String templatePath = this.environment.getProperty("apps.print.html.template.path") + File.separator
                    + "mail" + File.separator + "html" + File.separator + request.getTemplateName() + ".html";

            Context context = new Context();
            context.setVariables(request.getParams());
            String emailContent = "";

            LOG.info("To Addresses() {}", request.getToAddresses());
            LOG.info("CC Addresses() {}", request.getCcAddresses());

            if (request.getToAddresses() != null && !request.getToAddresses().isEmpty()) {

                List<InternetAddress> toAddresses = new ArrayList<>();
                for (String emailAddress : request.getToAddresses()) {
                    if (emailAddress != null && !emailAddress.isEmpty() && !toAddresses.contains(new InternetAddress(emailAddress))) {
                        toAddresses.add(new InternetAddress(emailAddress));
                    }
                }
                InternetAddress[] toAddressArr = toAddresses != null && !toAddresses.isEmpty() ? toAddresses.toArray(new InternetAddress[0]) : null;

                List<InternetAddress> ccAddresses = new ArrayList<>();
                for (String emailAddress : request.getCcAddresses()) {
                    boolean isEmailNotExist = !toAddresses.contains(new InternetAddress(emailAddress)) && !ccAddresses.contains(new InternetAddress(emailAddress));
                    if (emailAddress != null && !emailAddress.isEmpty() && isEmailNotExist) {
                        ccAddresses.add(new InternetAddress(emailAddress));
                    }
                }
                InternetAddress[] ccAddressArr = ccAddresses != null && !ccAddresses.isEmpty() ? ccAddresses.toArray(new InternetAddress[0]) : null;

                if (toAddressArr != null) {
                    if ((request.getAttachments() != null) && (!request.getAttachments().isEmpty())) {
                        AttachmentDTO attachmentDTO = prepareAttachments(request.getAttachments());

                        if (!attachmentDTO.getUnAttachmentFileNames().isEmpty()) {
                            String fileNames = String.join(", ", attachmentDTO.getUnAttachmentFileNames());
                            context.setVariable("message", "There are un-attached files. Please get those file from CAS system. Those files are, " + fileNames);
                        }
                        emailContent = this.templateEngine.process(templatePath, context);
                        isSent = sampathEmailService.send(sFromAddr, toAddressArr, ccAddressArr, request.getSubject(), emailContent, attachmentDTO.getAttachements());

                        // delete created files
                        for (EmailAttachment attachment : request.getAttachments()) {
                            String fileName = attachment.getFileName() + ".pdf";
                            File fileToDelete = new File(fileName);
                            fileToDelete.delete();
                        }
                    } else {
                        emailContent = this.templateEngine.process(templatePath, context);
                        isSent = sampathEmailService.send(sFromAddr, toAddressArr, ccAddressArr, request.getSubject(), emailContent);
                    }

                    LOG.info("Email Send  {}", isSent);
                }
            }
        } catch (Exception e) {
            LOG.error("Error Email Sending ", e);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_SYSTEM_EMAIL_FAILED, e);
        }
    }

    private AttachmentDTO prepareAttachments(List<EmailAttachment> attachmentList) throws AppsException {

        AttachmentDTO attachmentDTO = new AttachmentDTO();

        try {
            List<File> attachments = new ArrayList<>();
            List<String> unAttachmentFileNames = new ArrayList<>();

            double attachedSize = 0;

            for (EmailAttachment attachment : attachmentList) {
                String fileName = attachment.getFileName() + ".pdf";
                File file = new File(fileName);

                byte[] bytes = Base64.getDecoder().decode(attachment.getFileDataUri());

                try (FileOutputStream os = new FileOutputStream(file)) {
                    os.write(bytes);
                }

                long fileLength = file.length();
                double kilobytes = (double) fileLength / 1024;
                double megabytes = kilobytes / 1024;
                double newSize = attachedSize + megabytes;

                if (attachedSize < attachmentSizeLimit && newSize < attachmentSizeLimit) {
                    attachments.add(file);
                    attachedSize = attachedSize + megabytes;
                } else {
                    unAttachmentFileNames.add(file.getName());
                }
            }

            attachmentDTO.setAttachements(attachments);
            attachmentDTO.setUnAttachmentFileNames(unAttachmentFileNames);

        } catch (Exception e) {
            LOG.error("Error Email Attachments ", e);
        }

        return attachmentDTO;
    }
}
