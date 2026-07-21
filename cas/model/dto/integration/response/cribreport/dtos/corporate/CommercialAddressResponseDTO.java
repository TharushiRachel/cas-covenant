package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialAddressResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.MailingAddressResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.PermanentAddressResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommercialAddressResponseDTO implements Serializable {

    private List<MailingAddressResponseDTO> mailingAddressResponseDTOList;

    private List<PermanentAddressResponseDTO> permanentAddressResponseDTOList;

    public CommercialAddressResponseDTO() {
    }

    public CommercialAddressResponseDTO(CommercialAddressResponse commercialAddressResponse) {
        if (commercialAddressResponse != null) {
            if (commercialAddressResponse.getMailingAddressResponse() != null && !commercialAddressResponse.getMailingAddressResponse().isEmpty()) {
                for (MailingAddressResponse mailingAddressResponse : commercialAddressResponse.getMailingAddressResponse()) {
                    getMailingAddressResponseDTOList().add(new MailingAddressResponseDTO(mailingAddressResponse));
                }
            }

            if (commercialAddressResponse.getPermanentAddressResponse() != null && !commercialAddressResponse.getPermanentAddressResponse().isEmpty()) {
                for (PermanentAddressResponse permanentAddressResponse : commercialAddressResponse.getPermanentAddressResponse()) {
                    getPermanentAddressResponseDTOList().add(new PermanentAddressResponseDTO(permanentAddressResponse));
                }
            }
        }
    }

    public List<MailingAddressResponseDTO> getMailingAddressResponseDTOList() {
        if (mailingAddressResponseDTOList == null) {
            this.mailingAddressResponseDTOList = new ArrayList<>();
        }
        return mailingAddressResponseDTOList;
    }

    public void setMailingAddressResponseDTOList(List<MailingAddressResponseDTO> mailingAddressResponseDTOList) {
        this.mailingAddressResponseDTOList = mailingAddressResponseDTOList;
    }

    public List<PermanentAddressResponseDTO> getPermanentAddressResponseDTOList() {
        if (permanentAddressResponseDTOList == null) {
            this.permanentAddressResponseDTOList = new ArrayList<>();
        }
        return permanentAddressResponseDTOList;
    }

    public void setPermanentAddressResponseDTOList(List<PermanentAddressResponseDTO> permanentAddressResponseDTOList) {
        this.permanentAddressResponseDTOList = permanentAddressResponseDTOList;
    }
}
