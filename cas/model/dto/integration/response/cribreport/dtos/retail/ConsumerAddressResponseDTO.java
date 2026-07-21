package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerAddressResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.MailingAddressResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.PermanentAddressResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConsumerAddressResponseDTO implements Serializable {

    private List<MailingAddressResponseDTO> mailingAddressResponseDTOS;

    private List<PermanentAddressResponseDTO> permanentAddressResponseDTOS;

    public ConsumerAddressResponseDTO() {
    }

    public ConsumerAddressResponseDTO(ConsumerAddressResponse consumerAddressResponse) {
        if (consumerAddressResponse.getMailingAddressResponses() != null) {
            for (MailingAddressResponse mailingAddressResponse : consumerAddressResponse.getMailingAddressResponses()) {
                MailingAddressResponseDTO mailingAddressResponseDTO = new MailingAddressResponseDTO(mailingAddressResponse);
                this.getMailingAddressResponseDTOS().add(mailingAddressResponseDTO);
            }

        }
        if (consumerAddressResponse.getPermanentAddressResponses() != null) {
            for (PermanentAddressResponse permanentAddressResponse : consumerAddressResponse.getPermanentAddressResponses()) {
                PermanentAddressResponseDTO permanentAddressResponseDTO = new PermanentAddressResponseDTO(permanentAddressResponse);
                this.getPermanentAddressResponseDTOS().add(permanentAddressResponseDTO);
            }
        }

    }

    public List<MailingAddressResponseDTO> getMailingAddressResponseDTOS() {
        if (mailingAddressResponseDTOS == null) {
            this.mailingAddressResponseDTOS = new ArrayList<>();
        }
        return mailingAddressResponseDTOS;
    }

    public void setMailingAddressResponseDTOS(List<MailingAddressResponseDTO> mailingAddressResponseDTOS) {
        this.mailingAddressResponseDTOS = mailingAddressResponseDTOS;
    }

    public List<PermanentAddressResponseDTO> getPermanentAddressResponseDTOS() {
        if (permanentAddressResponseDTOS == null) {
            this.permanentAddressResponseDTOS = new ArrayList<>();
        }
        return permanentAddressResponseDTOS;
    }

    public void setPermanentAddressResponseDTOS(List<PermanentAddressResponseDTO> permanentAddressResponseDTOS) {
        this.permanentAddressResponseDTOS = permanentAddressResponseDTOS;
    }
}
