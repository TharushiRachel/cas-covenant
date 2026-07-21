package com.itechro.cas.service.cribReport.pdf;

import com.itechro.cas.config.CasProperties;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.integration.request.cribreport.CribRetailReportRQ;
import com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail.support.CribRetailReportAssembler;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.CribRetailReportResponse;
import org.springframework.core.env.Environment;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class RetailCribReportCreator {

    private String templatePath;

    private Environment environment;

    private TemplateEngine templateEngine;

    private CribRetailReportAssembler cribRetailReportAssembler;

    public RetailCribReportCreator() {
    }

    public RetailCribReportCreator(CribRetailReportResponse cribRetailReportResponse, CribRetailReportRQ cribRetailReportRQ, CasProperties casProperties) {
        this.cribRetailReportAssembler = new CribRetailReportAssembler(cribRetailReportResponse, cribRetailReportRQ, casProperties);
    }

    private void init() {
        templatePath = this.environment.getProperty("apps.print.html.template.path") + File.separator
                + "crib" + File.separator + "retail" + File.separator + "CribRetailReport" + ".html";
    }

    public String getPdfContent() throws AppsException {
        this.init();
        Context context = new Context();
        Map<String, Object> params = new HashMap<>();
        params.put("cribDTO", cribRetailReportAssembler);
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

    public CribRetailReportAssembler getCribRetailReportAssembler() {
        return cribRetailReportAssembler;
    }

    public void setCribRetailReportAssembler(CribRetailReportAssembler cribRetailReportAssembler) {
        this.cribRetailReportAssembler = cribRetailReportAssembler;
    }
}
