package com.itechro.cas.model.dto.integration.request.cribreport;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CribRequestDTO implements Serializable {

    private String applicationNumber;

    private String applicationDate;

    private String fullName;

    private String gender;

    private String creditFacilityType;

    private String creditFacilityCurrency;

    private String reportDate;

    private CreditFacilityAmountDTO creditFacilityAmountDTO;

    private List<IDNumberDTO> idNumberDTOList;

    private String inquiryReason;

    private String correlationId;

    private List<String> sectionsList;

    private String subjectToken;

    private String reportToken;
}