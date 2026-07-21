package com.itechro.cas.model.dto.casv1;

import com.itechro.cas.model.domain.casv1.FacilityStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FacilityStatusDTO {

    private String refNo;

    private String facilityDate;

    private String preUser;

    private String currentUser;

    private Integer currentUserStatus;

    private BigDecimal directTotal;

    private BigDecimal indirectTotal;

    private BigDecimal totalExposure;

    private BigDecimal preDirectTotal;

    private BigDecimal preIndirectTotal;

    private BigDecimal preTotalExposure;

    private String permittedUser;

    private String approvedUserName;

    private BigDecimal grpPreDirectTotal;

    private BigDecimal grpPreIndirectTotal;

    private BigDecimal grpDirectTotal;

    private BigDecimal grpIndirectTotal;

    private BigDecimal grpPreTotalExposure;

    private BigDecimal grpTotalExposure;

    private String upcFormatNum;

    private Date pickedDate;

    private Date appRecDate;

    private String losRefNo;

    private Date dateOfDisbursement;
    private Integer status;
    private String statusText;

    public FacilityStatusDTO() {
    }

    public FacilityStatusDTO (FacilityStatus facilityStatusDTO){

         this.refNo = facilityStatusDTO.getRefNo();
         this.facilityDate = facilityStatusDTO.getFacilityDate().toString();
         this.preUser = facilityStatusDTO.getPreUser();
         this.currentUser = facilityStatusDTO.getCurrentUser();
         this.currentUserStatus = facilityStatusDTO.getCurrentUserStatus();
//         this.directTotal = facilityStatusDTO.getDirectTotal();
//         this.indirectTotal = facilityStatusDTO.getIndirectTotal();
//         this.totalExposure = facilityStatusDTO.getTotalExposure();
//         this.preDirectTotal = facilityStatusDTO.getPreDirectTotal();
//         this.preIndirectTotal = facilityStatusDTO.getPreIndirectTotal();
//         this.preTotalExposure = facilityStatusDTO.getPreTotalExposure();
         this.permittedUser = facilityStatusDTO.getPermittedUser();
         this.approvedUserName = facilityStatusDTO.getApprovedUserName();
//         this.grpPreDirectTotal = facilityStatusDTO.getGrpPreDirectTotal();
//         this.grpPreIndirectTotal = facilityStatusDTO.getGrpPreIndirectTotal();
//         this.grpDirectTotal = facilityStatusDTO.getGrpDirectTotal();
//         this.grpIndirectTotal = facilityStatusDTO.getGrpIndirectTotal();
//         this.grpPreTotalExposure = facilityStatusDTO.getGrpPreTotalExposure();
//         this.grpTotalExposure = facilityStatusDTO.getGrpTotalExposure();
         this.upcFormatNum = facilityStatusDTO.getUpcFormatNum();
         this.pickedDate = facilityStatusDTO.getPickedDate();
         this.appRecDate = facilityStatusDTO.getAppRecDate();
         this.losRefNo = facilityStatusDTO.getLosRefNo();
         this.dateOfDisbursement = facilityStatusDTO.getDateOfDisbursement();
    }
}
