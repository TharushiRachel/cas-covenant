package com.itechro.cas.model.dto.lead;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CompLeadFacilityDTO {
    private Long compFacilityId;
    private Long compLeadId;
    private Long leadId;
    private String facilityDescription;
    private Integer requestedTenure;
    private BigDecimal leaseRental;
    private BigDecimal processingFee;
    private BigDecimal leaseAmount;
    private String repaymentMode;
    private BigDecimal upfront;
    private BigDecimal insurance;
    private Date createdDate;
    private String createdBy;
    private Date modifiedDate;
    private String modifiedBy;
    private String status;
    private String effectiveRate;
    private String model;
    private String make;
    private String validityOfOffer;
    private Integer creditFacilityTemplateId;
    private Integer creditFacilityTemplateGroupId;


}




