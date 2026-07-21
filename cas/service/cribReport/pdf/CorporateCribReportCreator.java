package com.itechro.cas.service.cribReport.pdf;

import com.itechro.cas.config.CasProperties;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.integration.request.cribreport.CribCorporateRQ;
import com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate.support.CribCorporateReportAssembler;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CribCorporateReportResponse;
import org.springframework.core.env.Environment;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CorporateCribReportCreator {

    private String templatePath;

    private Environment environment;

    private TemplateEngine templateEngine;

    private CribCorporateReportAssembler cribCorporateReportAssembler;

    public CorporateCribReportCreator() {
    }

    public CorporateCribReportCreator(CribCorporateReportResponse cribCorporateReportResponse, CribCorporateRQ cribCorporateRQ, CasProperties casProperties) {
        this.cribCorporateReportAssembler = new CribCorporateReportAssembler(cribCorporateReportResponse, casProperties, cribCorporateRQ);
    }

    private void init() {
        templatePath = this.environment.getProperty("apps.print.html.template.path") + File.separator
                + "crib" + File.separator + "corporate" + File.separator + "CribCorporateReport" + ".html";
    }

    public String getPdfContent() throws AppsException {
        this.init();
        Context context = new Context();
        Map<String, Object> params = new HashMap<>();
        params.put("cribDTO", cribCorporateReportAssembler);
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

    public CribCorporateReportAssembler getCribCorporateReportAssembler() {
        return cribCorporateReportAssembler;
    }

    public void setCribCorporateReportAssembler(CribCorporateReportAssembler cribCorporateReportAssembler) {
        this.cribCorporateReportAssembler = cribCorporateReportAssembler;
    }
}
