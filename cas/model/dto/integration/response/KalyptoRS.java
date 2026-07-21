package com.itechro.cas.model.dto.integration.response;


import com.itechro.cas.model.dto.integration.response.kalipto.KalyptoResponseDTO;

public class KalyptoRS {

    private String string;

    private String message;

    private KalyptoResponseDTO kalyptoResponseDTO;

    private boolean successfullyParse;

    public KalyptoRS() {
    }

    public KalyptoRS(String result){
        this.string = result;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public KalyptoResponseDTO getKalyptoResponseDTO() {
        return kalyptoResponseDTO;
    }

    public void setKalyptoResponseDTO(KalyptoResponseDTO kalyptoResponseDTO) {
        this.kalyptoResponseDTO = kalyptoResponseDTO;
    }

    public boolean getSuccessfullyParse() {
        return successfullyParse;
    }

    public void setSuccessfullyParse(boolean successfullyParse) {
        this.successfullyParse = successfullyParse;
    }

    @Override
    public String toString() {
        return "KalyptoRS{" +
                "string='" + string + '\'' +
                '}';
    }
}

