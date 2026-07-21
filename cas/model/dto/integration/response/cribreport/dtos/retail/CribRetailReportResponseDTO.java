package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.CribRetailReportResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CribRetailReportResponseDTO implements Serializable {

    private boolean success;

    private String status;

    private DetailResponseDTO detailResponseDTO;

    private List<String> errors;

    private FaultResponseDTO faultResponseDTO;

    public CribRetailReportResponseDTO() {
    }

    public CribRetailReportResponseDTO(CribRetailReportResponse cribRetailReportResponse) {
        this.status = cribRetailReportResponse.getStatus();

        if (cribRetailReportResponse.getStatus() != null && cribRetailReportResponse.getStatus().equals("Success")) {
            this.detailResponseDTO = new DetailResponseDTO(cribRetailReportResponse.getDetailsResponse());
            this.success = true;
        } else {
            this.success = false;
        }

        if (cribRetailReportResponse.getErrors() != null && !cribRetailReportResponse.getErrors().isEmpty()) {
            for (String error : cribRetailReportResponse.getErrors()) {
                this.getErrors().add(error);
            }
        }

        if (cribRetailReportResponse.getFaultResponse() != null && cribRetailReportResponse.getFaultResponse().getDescription() != null) {
            this.faultResponseDTO = new FaultResponseDTO();
            faultResponseDTO.setDescription(cribRetailReportResponse.getFaultResponse().getDescription());
        }
    }

    public DetailResponseDTO getDetailResponseDTO() {
        return detailResponseDTO;
    }

    public void setDetailResponseDTO(DetailResponseDTO detailResponseDTO) {
        this.detailResponseDTO = detailResponseDTO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public FaultResponseDTO getFaultResponseDTO() {
        return faultResponseDTO;
    }

    public void setFaultResponseDTO(FaultResponseDTO faultResponseDTO) {
        this.faultResponseDTO = faultResponseDTO;
    }
}
