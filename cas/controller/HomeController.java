package com.itechro.cas.controller;


import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.model.dto.master.SystemParamDTO;
import com.itechro.cas.service.master.SystemParameterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "${api.prefix}/home")
public class HomeController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private SystemParameterService systemParameterService;

    @Autowired
    private CasProperties casProperties;

    @ResponseExceptionHandler
    @RequestMapping(value = "/system-params", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<Map<String, SystemParamDTO>> confirmTentativeJobAllocation() throws Exception {

        LOG.info("START : Load all system parameters");

        Map<String, SystemParamDTO> systemParameters = systemParameterService.getSystemParameters();

        LOG.info("END : Load all system parameters {}", systemParameters);
        return new ResponseEntity<>(systemParameters, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getConstants", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Map<String, String>>> getConstants() throws Exception {

        LOG.info("START : Load system constants");

        Map<String, Map<String, String>> result = new HashMap<>();
        result.put("webAuditMainCategory", DomainConstants.WebAuditMainCategory.getWebAuditMainCategoryMap());
        result.put("webAuditSubCategory", DomainConstants.WebAuditSubCategory.getWebAuditSubCategoryMap());
        result.put("webAuditSubCategoryCategoryMap", DomainConstants.WebAuditSubCategory.getWebAuditSubCategoryCategoryMap());

        LOG.info("END : Load system constants {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getApplicationProperties", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getApplicationProperties() throws Exception {

        LOG.info("START : Load application properties");

        Map<String, Object> result = new HashMap<>();
        result.put("imageUploadMaxSizeMB", this.casProperties.getImageUploadMaxSizeMB());
        result.put("imageUploadAllowedExtensions", this.casProperties.getImageUploadAllowedExtensions());
        result.put("customFileSizeLimits", parseCustomSizeLimits(this.casProperties.getImageUploadCustomSizeLimits()));
        result.put("leadContextPath", this.casProperties.getLeadContextPath());

        LOG.info("END : Load application properties {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private Map<String, String> parseCustomSizeLimits(String limitsString) {
        Map<String, String> limitsMap = new HashMap<>();

        if (limitsString == null || limitsString.trim().isEmpty()) {
            return limitsMap;
        }

        String[] limitsArray = limitsString.split(",");
        for (String entry : limitsArray) {
            String[] parts = entry.split("=");
            if (parts.length == 2) {
                limitsMap.put(parts[0].trim().toLowerCase(), parts[1].trim());
            }
        }
        return limitsMap;
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getSSOProperties", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> getSSOProperties() throws Exception {

        LOG.info("START : Load SSO properties");

        Map<String, String> result = new HashMap<>();
        result.put("isSSOLogin", this.casProperties.getIsSSOLogin());
        result.put("ssoRedirectUri", this.casProperties.getSsoRedirectUri());
        result.put("ssoLogoutRedirectUri", this.casProperties.getSsoLogoutRedirectUri());

        LOG.info("END : Load SSO properties {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
