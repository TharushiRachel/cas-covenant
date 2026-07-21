package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CribCorporateReportResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CribCorporateReportResponseDTO implements Serializable {
    private boolean success;

    private String status;

    private CorporateDetailsResponseDTO corporateDetailsResponseDTO;

    private List<String> errors;

    private CorporateFaultResponseDTO corporateFaultResponseDTO;

    public CribCorporateReportResponseDTO() {
    }

    public CribCorporateReportResponseDTO(CribCorporateReportResponse cribCorporateReportResponse) {
        this.status = cribCorporateReportResponse.getStatus();

        if (cribCorporateReportResponse.getStatus() != null && cribCorporateReportResponse.getStatus().equals("Success")) {
            this.corporateDetailsResponseDTO = new CorporateDetailsResponseDTO(cribCorporateReportResponse.getCorporateDetailsResponse());
            this.success = true;
        } else {
            this.success = false;
        }

        if (cribCorporateReportResponse.getErrors() != null) {
            for (String error : cribCorporateReportResponse.getErrors()) {
                this.getErrors().add(error);
            }
        }

        if (cribCorporateReportResponse.getCorporateFaultResponse() != null && cribCorporateReportResponse.getCorporateFaultResponse().getDescription() != null) {
            this.corporateFaultResponseDTO = new CorporateFaultResponseDTO();
            corporateFaultResponseDTO.setDescription(cribCorporateReportResponse.getCorporateFaultResponse().getDescription());
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CorporateDetailsResponseDTO getCorporateDetailsResponseDTO() {
        return corporateDetailsResponseDTO;
    }

    public void setCorporateDetailsResponseDTO(CorporateDetailsResponseDTO corporateDetailsResponseDTO) {
        this.corporateDetailsResponseDTO = corporateDetailsResponseDTO;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getErrors() {
        if (errors == null) {
            this.errors = new ArrayList<>();
        }
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public CorporateFaultResponseDTO getCorporateFaultResponseDTO() {
        return corporateFaultResponseDTO;
    }

    public void setCorporateFaultResponseDTO(CorporateFaultResponseDTO corporateFaultResponseDTO) {
        this.corporateFaultResponseDTO = corporateFaultResponseDTO;
    }
}
