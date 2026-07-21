package com.itechro.cas.model.dto.facilitypaper.response;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.facilitypaper.WSFacilitySecurity;
import com.itechro.cas.model.domain.facilitypaper.WalletShare;
import com.itechro.cas.model.domain.facilitypaper.WalletShareFacility;
import com.itechro.cas.model.dto.facilitypaper.request.WSFacilitySecurityDTO;
import com.itechro.cas.util.DecimalCalculator;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class WalletShareDTO {

    private Integer walletShareId;

    private Integer facilityPaperId;

    private String bank;

    private BigDecimal limitTotal;

    private BigDecimal osTotal;

    private BigDecimal share;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    private DomainConstants.MasterDataActionStatus recordStatus;

    private Integer isSystem;

    private List<WalletShareFacilityDTO> facilities = new ArrayList<>();

    private List<WSFacilitySecurityDTO> commonSecurities = new ArrayList<>();

    public WalletShareDTO() {
    }

    public WalletShareDTO(WalletShare walletShare) {
        this.walletShareId = walletShare.getWalletShareId();
        this.facilityPaperId = walletShare.getFacilityPaper().getFacilityPaperID();
        this.bank = walletShare.getBank();
        this.limitTotal = walletShare.getLimitAmountTotal() != null ? DecimalCalculator.getFormattedValue(walletShare.getLimitAmountTotal())
                : DecimalCalculator.getDefaultZero();
        this.osTotal = walletShare.getOsAmountTotal() != null ? DecimalCalculator.getFormattedValue(walletShare.getOsAmountTotal())
                : DecimalCalculator.getDefaultZero();
        this.share = walletShare.getShare();
        this.createdBy = walletShare.getCreatedBy();
        this.createdDate = walletShare.getCreatedDate();
        this.modifiedBy = walletShare.getModifiedBy();
        this.modifiedDate = walletShare.getModifiedDate();
        this.isSystem = walletShare.getIsSystem();

        if (walletShare.getFacilities() != null && !walletShare.getFacilities().isEmpty()) {
            for (WalletShareFacility facility : walletShare.getFacilities()) {
                this.facilities.add(new WalletShareFacilityDTO(facility));
            }
        }

        if (walletShare.getWsFacilitySecurities() != null && !walletShare.getWsFacilitySecurities().isEmpty()) {
            for (WSFacilitySecurity security : walletShare.getWsFacilitySecurities()) {
                if (security.getIsCommonSecurity().equals(AppsConstants.YesNo.Y)) {
                    this.commonSecurities.add(new WSFacilitySecurityDTO(security));
                }
            }
        }
    }

    public List<WalletShareFacilityDTO> getSortFacilities() {
        List<WalletShareFacilityDTO> result = new ArrayList<>(this.facilities);
        if (!result.isEmpty()){
            result.remove(0);
        }
        return result;
    }

    public Integer getFacilitiesLength() {
        if (this.facilities == null) {
            return 0;
        }
        return this.facilities.size();
    }

    public List<WalletShareFacilityDTO> getFacilities() {
        if (this.facilities == null) {
            this.facilities = new ArrayList<>();
        }
        return facilities;
    }

    public List<WSFacilitySecurityDTO> getCommonSecurities() {
        if (this.commonSecurities == null) {
            this.commonSecurities = new ArrayList<>();
        }
        return commonSecurities;
    }

    public String getWalletShareSecurity(WalletShareDTO share) {

        StringBuilder securitiesTxt = new StringBuilder();

        //map facility securities
        if (share.getFacilities() != null && !share.getFacilities().isEmpty()) {
            int facilityIndex = 1;
            for (WalletShareFacilityDTO shareFacilityDTO : share.getFacilities()) {
                List<String> securities = shareFacilityDTO.getFacilitySecurities().stream()
                        .map(WSFacilitySecurityDTO::getSecurityDetail)
                        .collect(Collectors.toList());

                if (!securities.isEmpty()) {
                    securitiesTxt.append("Facility ")
                            .append(facilityIndex)
                            .append(":\n");

                    for (String security : securities) {
                        securitiesTxt.append(security).append("\n");
                    }

                    securitiesTxt.append("\n\n");
                }
                facilityIndex = facilityIndex + 1;
            }
        }

        //map common securities
        if (share.getCommonSecurities() != null && !share.getCommonSecurities().isEmpty()) {
            if (share.isSystem == 1) {
                int commonSecurityIndex = 1;
                for (WSFacilitySecurityDTO commonSecurity : share.getCommonSecurities()) {

                    if (commonSecurity.getSecurityDetail() != null && !commonSecurity.getSecurityDetail().isEmpty()) {
                        securitiesTxt.append("Common Security ")
                                .append(commonSecurityIndex)
                                .append(":\n");
                        securitiesTxt.append(commonSecurity.getSecurityDetail()).append("\n");
                        securitiesTxt.append("\n\n");
                    }
                    commonSecurityIndex = commonSecurityIndex + 1;
                }
            } else {
                int commonSecurityIndex = 1;
                for (WSFacilitySecurityDTO commonSecurity : share.getCommonSecurities()) {

                    if (commonSecurity.getSecurityDetail() != null && !commonSecurity.getSecurityDetail().isEmpty()) {
                        securitiesTxt.append(":\n");
                        securitiesTxt.append(commonSecurity.getSecurityDetail()).append("\n");
                        securitiesTxt.append("\n\n");
                    }
                    commonSecurityIndex = commonSecurityIndex + 1;
                }
            }
        }

        return securitiesTxt.toString();
    }

    public BigDecimal getShare() {
        if (share != null){
            return share.setScale(2, RoundingMode.HALF_UP);
        }
        return share;
    }
}
