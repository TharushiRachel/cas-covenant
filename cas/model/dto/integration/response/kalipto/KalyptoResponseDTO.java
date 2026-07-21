package com.itechro.cas.model.dto.integration.response.kalipto;

import com.itechro.cas.model.dto.integration.ws.response.kalypto.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KalyptoResponseDTO implements Serializable{

    private KalyptoRatingEvalDTO kalyptoRatingEvalDTO;

    private List<KalyptoRatingFormatDTO> kalyptoRatingFormatDTOS;

    private List<KalyptoRatingStyleClassDTO> kalyptoRatingStyleClassDTOS;

    private List<KalyptoRatingPeriodDTO> kalyptoRatingPeriodDTOS;

    private List<KalyptoRatingPeriodDataDTO> kalyptoRatingPeriodDataDTOS;

    private Map<String, Object> additionalProperties;

    public KalyptoResponseDTO(KalyptoResponse kalyptoResponse){

        if(kalyptoResponse != null) {
            this.kalyptoRatingEvalDTO = new KalyptoRatingEvalDTO(kalyptoResponse.getKalyptoRatingEval());
            this.additionalProperties = kalyptoResponse.getAdditionalProperties();
            if (kalyptoResponse.getKalyptoRatingFormat() != null) {
                for (KalyptoRatingFormat kalyptoRatingFormat : kalyptoResponse.getKalyptoRatingFormat()) {
                    this.getKalyptoRatingFormatDTOS().add(new KalyptoRatingFormatDTO(kalyptoRatingFormat));
                }
            }
            if (kalyptoResponse.getKalyptoRatingStyleClass() != null) {
                for (KalyptoRatingStyleClass kalyptoRatingStyleClass : kalyptoResponse.getKalyptoRatingStyleClass()) {
                    this.getKalyptoRatingStyleClassDTOS().add(new KalyptoRatingStyleClassDTO(kalyptoRatingStyleClass));
                }
            }
            if (kalyptoResponse.getKalyptoRatingPeriod() != null) {
                for (KalyptoRatingPeriod kalyptoRatingPeriod : kalyptoResponse.getKalyptoRatingPeriod()) {
                    this.getKalyptoRatingPeriodDTOS().add(new KalyptoRatingPeriodDTO(kalyptoRatingPeriod));
                }
            }
            if (kalyptoResponse.getKalyptoRatingPeriodData() != null) {
                for (KalyptoRatingPeriodData kalyptoRatingPeriodData : kalyptoResponse.getKalyptoRatingPeriodData()) {
                    this.getKalyptoRatingPeriodDataDTOS().add(new KalyptoRatingPeriodDataDTO(kalyptoRatingPeriodData));
                }
            }
        }
    }

    public KalyptoRatingEvalDTO getKalyptoRatingEvalDTO() {
        return kalyptoRatingEvalDTO;
    }

    public void setKalyptoRatingEvalDTO(KalyptoRatingEvalDTO kalyptoRatingEvalDTO) {
        this.kalyptoRatingEvalDTO = kalyptoRatingEvalDTO;
    }

    public List<KalyptoRatingFormatDTO> getKalyptoRatingFormatDTOS() {
        if (kalyptoRatingFormatDTOS == null) {
            kalyptoRatingFormatDTOS = new ArrayList<>();
        }
        return kalyptoRatingFormatDTOS;
    }

    public void setKalyptoRatingFormatDTOS(List<KalyptoRatingFormatDTO> kalyptoRatingFormatDTOS) {
        this.kalyptoRatingFormatDTOS = kalyptoRatingFormatDTOS;
    }

    public List<KalyptoRatingStyleClassDTO> getKalyptoRatingStyleClassDTOS() {
        if (kalyptoRatingStyleClassDTOS == null) {
            kalyptoRatingStyleClassDTOS = new ArrayList<>();
        }
        return kalyptoRatingStyleClassDTOS;
    }

    public void setKalyptoRatingStyleClassDTOS(List<KalyptoRatingStyleClassDTO> kalyptoRatingStyleClassDTOS) {
        this.kalyptoRatingStyleClassDTOS = kalyptoRatingStyleClassDTOS;
    }

    public List<KalyptoRatingPeriodDTO> getKalyptoRatingPeriodDTOS() {
        if (kalyptoRatingPeriodDTOS == null) {
            kalyptoRatingPeriodDTOS = new ArrayList<>();
        }
        return kalyptoRatingPeriodDTOS;
    }

    public void setKalyptoRatingPeriodDTOS(List<KalyptoRatingPeriodDTO> kalyptoRatingPeriodDTOS) {
        this.kalyptoRatingPeriodDTOS = kalyptoRatingPeriodDTOS;
    }

    public List<KalyptoRatingPeriodDataDTO> getKalyptoRatingPeriodDataDTOS() {
        if (kalyptoRatingPeriodDataDTOS == null) {
            kalyptoRatingPeriodDataDTOS = new ArrayList<>();
        }
        return kalyptoRatingPeriodDataDTOS;
    }

    public void setKalyptoRatingPeriodDataDTOS(List<KalyptoRatingPeriodDataDTO> kalyptoRatingPeriodDataDTOS) {
        this.kalyptoRatingPeriodDataDTOS = kalyptoRatingPeriodDataDTOS;
    }

    public Map<String, Object> getAdditionalProperties() {
        if(additionalProperties == null){
            additionalProperties  = new HashMap<String, Object>();
        }
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
