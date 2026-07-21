package com.itechro.cas.model.dto.customer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itechro.cas.model.dto.covenants.CustomerCovenantSaveChildDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CustomerCovenantSaveResponseDTO {

    @JsonProperty("RequestUUID")
    private String RequestUUID;

    private String custId;

    private String casReference;

    private List<CustomerCovenantSaveChildDTO> covenantDetails;

    private String createdUserDisplayName;

    private String createdBy;

    private Date createdDate;


}
