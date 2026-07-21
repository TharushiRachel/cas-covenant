package com.itechro.cas.model.dto.customer.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.dto.covenants.CustomerCovenantSaveChildDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CustomerCovenantUpdateDTO {

    private Integer customerCovenantId;
    @JsonProperty("RequestUUID")
    private String RequestUUID;

    private String custId;

    private String casReference;

    private Date createdDate;

    private List<CustomerCovenantSaveChildDTO> covenantDetails;

    private String createdUserDisplayName;

    private String createdBy;

    private AppsConstants.CovenantStatus status;

    private AppsConstants.CovenantStatusOnDisbursement disbursementType;

    private String noFrequencyDueDate;
}
