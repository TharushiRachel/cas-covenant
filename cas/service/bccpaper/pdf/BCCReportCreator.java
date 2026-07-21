package com.itechro.cas.service.bccpaper.pdf;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.bccpaper.BoardCreditCommitteePaper;
import com.itechro.cas.model.dto.bccpaper.BCCPrintPreviewDTO;
import org.springframework.core.env.Environment;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class BCCReportCreator {

    private String templatePath;

    private Environment environment;

    private TemplateEngine templateEngine;

    private String branchName;

    private BCCPrintPreviewDTO bccPrintPreviewDTO;

    public BCCReportCreator() {
    }

    public BCCReportCreator(BoardCreditCommitteePaper boardCreditCommitteePaper) {
        this.bccPrintPreviewDTO = new BCCPrintPreviewDTO(boardCreditCommitteePaper);
    }

    private void init() {
        String templateName = "boardCreditCommittee";
        if (bccPrintPreviewDTO.getPaperType() == DomainConstants.BCCPaperType.BRPTR){
            templateName = "BoardRelatedReviewCommittee";
        }

        if (bccPrintPreviewDTO.getPaperType() == DomainConstants.BCCPaperType.BRPGG){
            templateName = "BoardRelatedReviewCommitteeGG";
        }

        templatePath = this.environment.getProperty("apps.print.html.template.path") + File.separator
                + "bcc" + File.separator + "html" + File.separator + templateName + ".html";
    }

    public String getPdfContent() throws AppsException {
        this.init();
        if (this.branchName != null) {
            this.bccPrintPreviewDTO.setBranchName(this.branchName);
        }
        Context context = new Context();
        Map<String, Object> params = new HashMap<>();
        params.put("bccDTO", bccPrintPreviewDTO);
        context.setVariables(params);
        return this.templateEngine.process(templatePath, context);
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public BCCPrintPreviewDTO getBccPrintPreviewDTO() {
        return bccPrintPreviewDTO;
    }

    public void setBccPrintPreviewDTO(BCCPrintPreviewDTO bccPrintPreviewDTO) {
        this.bccPrintPreviewDTO = bccPrintPreviewDTO;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
