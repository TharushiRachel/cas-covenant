package com.itechro.cas.model.dto.facilitypaper.request;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.facilitypaper.WSFacilitySecurity;
import lombok.Data;

import java.util.Date;

@Data
public class WSFacilitySecurityDTO {

    private Integer securityId;

    private Integer facilityId;

    private Integer walletShareId;

    private String securityDetail;

    private AppsConstants.YesNo isCommonSecurity;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    private DomainConstants.MasterDataActionStatus recordStatus;

    public WSFacilitySecurityDTO() {
    }

    public WSFacilitySecurityDTO(WSFacilitySecurity wsFacilitySecurity) {
        this.securityId = wsFacilitySecurity.getSecurityId();
        this.facilityId = wsFacilitySecurity.getWalletShareFacility() != null ? wsFacilitySecurity.getWalletShareFacility().getFacilityId() : 0;
        this.walletShareId = wsFacilitySecurity.getWalletShare().getWalletShareId();
        this.securityDetail = wsFacilitySecurity.getSecurityDetail();
        this.isCommonSecurity = wsFacilitySecurity.getIsCommonSecurity();
        this.createdDate = wsFacilitySecurity.getCreatedDate();
        this.createdBy = wsFacilitySecurity.getCreatedBy();
        this.modifiedDate = wsFacilitySecurity.getModifiedDate();
        this.modifiedBy = wsFacilitySecurity.getModifiedBy();
    }
}
