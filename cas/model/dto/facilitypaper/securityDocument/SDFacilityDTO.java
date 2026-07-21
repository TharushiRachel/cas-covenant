package com.itechro.cas.model.dto.facilitypaper.securityDocument;

import com.itechro.cas.model.dto.facility.FacilityDTO;
import com.itechro.cas.model.dto.facility.FacilityInterestRateDTO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Data
public class SDFacilityDTO implements Serializable {

    private Integer facilityID;

    private String facilityRefCode;

    private String facilityCurrency;

    private BigDecimal facilityAmount;

    private Integer creditFacilityTemplateID;

    private String creditFacilityName;

    private String facilityTypeName;

    private Integer displayOrder;

    private String purpose;

    private String repayment;

    private String facilityInterestRate;

    public SDFacilityDTO(FacilityDTO facilityDTO) {
        this.facilityID = facilityDTO.getFacilityID();
        this.facilityRefCode = facilityDTO.getFacilityRefCode();
        this.facilityCurrency = facilityDTO.getFacilityCurrency();
        this.facilityAmount = facilityDTO.getFacilityAmount();
        this.creditFacilityTemplateID = facilityDTO.getCreditFacilityTemplateDTO().getCreditFacilityTemplateID();
        this.creditFacilityName = facilityDTO.getCreditFacilityTemplateDTO().getCreditFacilityName();
        this.facilityTypeName = facilityDTO.getCreditFacilityTemplateDTO().getFacilityTypeName();
        this.displayOrder = facilityDTO.getDisplayOrder();
        this.purpose = facilityDTO.getPurpose();
        this.repayment = facilityDTO.getRepayment();
        this.facilityInterestRate = formatFirstValueOptional(facilityDTO.getFacilityInterestRateList());

    }

    public static String formatFirstValueOptional(List<FacilityInterestRateDTO> facilityInterestRateDTOList) {
        return Optional.ofNullable(facilityInterestRateDTOList)
                .filter(l -> !l.isEmpty())
                .map(l -> l.get(0))
                .map(FacilityInterestRateDTO::getValue)
                .filter(v -> v != null && !v.isNaN() && !v.isInfinite())
                .map(v -> String.format(Locale.US, "%.2f", v))
                .orElse("");
    }
}