package lk.sampath.cas_covenant.service.impl;

import com.google.gson.Gson;
import lk.sampath.cas_covenant.dto.CovenantDetailsFinacleDTO;
import lk.sampath.cas_covenant.dto.CovenantListDTO;
import lk.sampath.cas_covenant.dto.CustomerCovenantRQ;
import lk.sampath.cas_covenant.dto.LoadCovenantDataDTO;
import lk.sampath.cas_covenant.exception.ApiRequestException;
import lk.sampath.cas_covenant.service.IntegrationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
@Log4j2
public class IntegrationServiceImpl implements IntegrationService {

    @Value("${covenant.list}")
    private String covenantList;

    @Value("${existing.finacle.covenant}")
    private String existingFinacleCovenants;

    @Value("${service.timeout.in.milliseconds.max}")
    private long serviceTimeoutInMillisecondsMax;

    @Transactional(propagation = Propagation.SUPPORTS)
    public CovenantListDTO getCovenantList(CustomerCovenantRQ customerCovenantRQ) throws ApiRequestException{
        log.info("START: getCovenantList | IntegrationService | get customer covenant details Response RQ : {} by: {}", customerCovenantRQ);
        CovenantListDTO customerCovenantResponseDTO = null;

            String url = this.covenantList;

            log.info("INFO: url to the finacle {}", url);
            RestTemplate restTemplate = new RestTemplate();

            WebClient webClient = WebClient.create();

            try {

                String response = webClient.post()
                        .uri(url)
                        .bodyValue(customerCovenantRQ)
                        .retrieve()
                        .bodyToMono(String.class)
                        .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                        .onErrorReturn("Timeout occurred or service failed for get covenant list")  // optional fallback
                        .block();

                if(response != null || !response.isEmpty()) {
                    log.info("INFO: getCovenantList | IntegrationService | get the covenant list response from the finacle successfully");
                }

                Gson gson = new Gson();

                customerCovenantResponseDTO = gson.fromJson(response, CovenantListDTO.class);

                log.info("INFO: getCovenantList | IntegrationService | get customer covenant list response {}", customerCovenantResponseDTO);

            } catch (Exception e) {
                log.info("ERROR: getCovenantList | IntegrationService | get customer covenant details Response url :{}", url,  e.getMessage());
            }
        log.info("END: getCovenantList | IntegrationService | get customer covenant details Response RQ : {} ", customerCovenantRQ);
        return customerCovenantResponseDTO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public CovenantDetailsFinacleDTO getCovenantDetailsFromFinacle(LoadCovenantDataDTO loadCovenantDataDTO) throws ApiRequestException {

        log.info("START: get covenant details Response RQ : {} ", loadCovenantDataDTO);

        CovenantDetailsFinacleDTO result = null;
        
        String url = this.existingFinacleCovenants;
        log.info("INFO: url to the finacle {}", url);
        log.info("INFO: Request to the finacle {}", loadCovenantDataDTO);
        WebClient webClient = WebClient.create();

            try {
                String response = webClient.post()
                        .uri(url)
                        .bodyValue(loadCovenantDataDTO)
                        .retrieve()
                        .bodyToMono(String.class)
                        .timeout(Duration.ofMillis(this.serviceTimeoutInMillisecondsMax))
                        .onErrorReturn("Timeout occurred or service failed in getCovenantDetailsFromFinacle")  // optional fallback
                        .block();

                log.info("INFO: getCovenantDetailsFromFinacle | IntegrationService | get the tran details response from the finacle {}", response);

                Gson gson = new Gson();

                result = gson.fromJson(response, CovenantDetailsFinacleDTO.class);
                log.info("INFO: getCovenantDetailsFromFinacle | IntegrationService |  get the covenant details response {} ", result);
            } catch (Exception e) {
                log.info("ERROR: getCovenantDetailsFromFinacle | IntegrationService |  get the covenant details response url :{}", url, e);
            }
        
        log.error("END: getCovenantDetailsFromFinacle | IntegrationService | result {}", result);
        return result;
    }
}
