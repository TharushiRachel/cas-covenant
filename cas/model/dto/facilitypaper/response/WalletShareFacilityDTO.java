package com.itechro.cas.model.dto.facilitypaper.response;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.facilitypaper.WSFacilitySecurity;
import com.itechro.cas.model.domain.facilitypaper.WalletShareFacility;
import com.itechro.cas.model.dto.facilitypaper.request.WSFacilitySecurityDTO;
import com.itechro.cas.util.DecimalCalculator;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class WalletShareFacilityDTO {

    private Integer facilityId;

    private Integer walletShareId;

    private String facility;

    private String facilityCurrency;

    private BigDecimal limitAmount;

    private BigDecimal osAmount;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    private DomainConstants.MasterDataActionStatus recordStatus;

    private List<WSFacilitySecurityDTO> facilitySecurities = new ArrayList<>();

    public WalletShareFacilityDTO() {
    }

    public WalletShareFacilityDTO(WalletShareFacility walletShareFacility) {
        this.facilityId = walletShareFacility.getFacilityId();
        this.walletShareId = walletShareFacility.getWalletShare().getWalletShareId();
        this.facility = walletShareFacility.getFacility();
        this.facilityCurrency = walletShareFacility.getFacilityCurrency() != null ? walletShareFacility.getFacilityCurrency() : "";
        this.limitAmount = walletShareFacility.getLimitAmount() != null ? DecimalCalculator.getFormattedValue(walletShareFacility.getLimitAmount())
                : DecimalCalculator.getDefaultZero();
        this.osAmount = walletShareFacility.getOsAmount() != null ? DecimalCalculator.getFormattedValue(walletShareFacility.getOsAmount())
                : DecimalCalculator.getDefaultZero();
        this.createdBy = walletShareFacility.getCreatedBy();
        this.createdDate = walletShareFacility.getCreatedDate();
        this.modifiedBy = walletShareFacility.getModifiedBy();
        this.modifiedDate = walletShareFacility.getModifiedDate();

        if (walletShareFacility.getWsFacilitySecurities() != null && !walletShareFacility.getWsFacilitySecurities().isEmpty()) {
            for (WSFacilitySecurity security : walletShareFacility.getWsFacilitySecurities()) {
                this.facilitySecurities.add(new WSFacilitySecurityDTO(security));
            }
        }
    }

    public List<WSFacilitySecurityDTO> getFacilitySecurities() {
        if (this.facilitySecurities == null) {
            this.facilitySecurities = new ArrayList<>();
        }
        return facilitySecurities;
    }
}