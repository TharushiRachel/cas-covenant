package com.itechro.cas.controller;

import com.google.gson.Gson;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.model.dto.integration.response.StandardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Enumeration;

@RestController
@RequestMapping("${api.prefix}/microservice")
@CrossOrigin("*")
public class MicroserviceProxyController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(MicroserviceProxyController.class);

    @Value("${apps.cas.context.path.microServiceURL}")
    private String microServiceURL;

    @Value("${apps.integration.service.timeout.in.milliseconds.max}")
    private long serviceTimeoutInMillisecondsMax;

    @Value("${apps.cas.context.path.covenantServiceURL}")
    private String covenantServiceURL;

    @ResponseExceptionHandler
    @RequestMapping(value = "/**")
    public ResponseEntity<StandardResponse> proxyRequest(
            HttpServletRequest request,
            @RequestBody(required = false) String body) {
        LOG.info("request.... {}",request.getRequestURI());
        String path = request.getRequestURI().replace("/cas/api/microservice", "");
        if (request.getQueryString() != null) {
            path += "?" + request.getQueryString();
        }

        String url;

        if (path.startsWith("/cas-covenant")) {
            LOG.info("Request is for Covenant Service, forwarding to Covenant Service URL");
            url = covenantServiceURL + path.replaceFirst("/cas-covenant", "");
            LOG.info("Covenant Service URL.... {}",url);
        } else {
            url = microServiceURL + path;
            LOG.info("Micro Service URL.... {}",url);
        }

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (!headerName.equalsIgnoreCase("host")) {
                headers.set(headerName, request.getHeader(headerName));
            }
        }
        LOG.info("Proxy Request Body.... {}",body);
        LOG.info("Proxy Request Headers.... {}",headers);
        LOG.info("Proxy Request Method.... {}",request.getMethod());

        try {
            WebClient webClient = WebClient.create();
            Gson gson = new Gson();
            WebClient.RequestBodySpec requestSpec = webClient.method(HttpMethod.valueOf(request.getMethod()))
                    .uri(url)
                .headers(httpHeaders -> httpHeaders.addAll(headers));

            Mono<String> responseMono;
            if (body != null && !body.trim().isEmpty()) {
            responseMono = requestSpec
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class);
            } else {
            responseMono = requestSpec
                .retrieve()
                .bodyToMono(String.class);
            }

            String response = responseMono
                .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                .block();

            LOG.info("Proxy Request Response.... {}",response);
            if (response == null || response.trim().isEmpty()) {
                StandardResponse  standardResponse =
                        new StandardResponse(
                                AppsConstants.ErrorEnums.ERROR_CODE.getStatus(), AppsConstants.ErrorEnums.ERROR_CODE.getLabel(), null);
                return ResponseEntity.ok(standardResponse);
            } else {
                StandardResponse responseDTO = gson.fromJson(response, StandardResponse.class);
                return ResponseEntity.ok().body(responseDTO);
            }
        } catch (WebClientResponseException e){
            LOG.error("WebClientResponseException:: status={}, responseBody={}", e.getRawStatusCode(), e.getResponseBodyAsString(), e);
            StandardResponse  standardResponse =
                    new StandardResponse(
                            AppsConstants.ErrorEnums.ERROR_CODE.getStatus(), AppsConstants.ErrorEnums.ERROR_CODE.getLabel(), null);
            return ResponseEntity.ok(standardResponse);
        }
        catch (Exception e) {
            LOG.error("Exception::  ",e);
            StandardResponse  standardResponse =
                    new StandardResponse(
                            AppsConstants.ErrorEnums.ERROR_CODE.getStatus(), AppsConstants.ErrorEnums.ERROR_CODE.getLabel(), null);
            return ResponseEntity.ok(standardResponse);
        }
    }
}
