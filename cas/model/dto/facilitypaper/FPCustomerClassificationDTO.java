package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.FPCustomerClassification;
import lombok.Data;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
public class FPCustomerClassificationDTO {
    private Integer customerClassificationId;

    private Integer facilityPaperId;

    private Integer customerId;

    private List<CustomerClassificationDTO> classificationDTOList;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;
}
