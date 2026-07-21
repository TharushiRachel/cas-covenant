package com.itechro.cas.model.dto.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Where(clause = "status != 'INA'")
public class FacilityListForCovenantDTO implements Serializable, Comparable {

    private Integer facilityID;
    private Integer creditFacilityTemplateID;

    private String creditFacilityName;

    private String facilityRefCode;

    private String facilityCurrency;

    private BigDecimal facilityAmount;

    private Integer displayOrder;

    @Where(clause = "status != 'INA'")
    private AppsConstants.Status status;

    @Override
    public int compareTo(@NotNull Object o) {
        return ((FacilityListForCovenantDTO)o).getDisplayOrder();
    }
}
