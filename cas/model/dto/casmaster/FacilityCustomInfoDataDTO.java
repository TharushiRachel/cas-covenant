package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.casmaster.CftCustomFacilityInfo;
import com.itechro.cas.model.domain.casmaster.FacilityCustomInfoData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
public class FacilityCustomInfoDataDTO implements Serializable {

    private Integer facilityCustomInfoDataID;
    private Integer facilityID;
    private Integer cftCustomFacilityInfoID;
    private String customFacilityInfoName;
    private String customFacilityInfoCode;
    private String customInfoData;
    private AppsConstants.YesNo mandatory;
    private Integer displayOrder;
    private AppsConstants.Status status;

    public FacilityCustomInfoDataDTO(FacilityCustomInfoData facilityCustomInfoData) {
        this.facilityCustomInfoDataID = facilityCustomInfoData.getFacilityCustomInfoDataID();
        if (facilityCustomInfoData.getFacility() != null) {
            this.facilityID = facilityCustomInfoData.getFacility().getFacilityID();
        }
        this.cftCustomFacilityInfoID = facilityCustomInfoData.getCftCustomFacilityInfoID();
        this.customInfoData = facilityCustomInfoData.getCustomInfoData();
        this.customFacilityInfoName = facilityCustomInfoData.getCustomFacilityInfoName();
        this.customFacilityInfoCode = facilityCustomInfoData.getCustomFacilityInfoCode();
        this.displayOrder = facilityCustomInfoData.getDisplayOrder();
        this.mandatory = facilityCustomInfoData.getMandatory();
        this.status = facilityCustomInfoData.getStatus();
    }
//    public FacilityCustomInfoDataDTO() {
//    }
//
//    public FacilityCustomInfoDataDTO(FacilityCustomInfoData facilityCustomInfoData){
//        this.facilityCustomInfoDataID = facilityCustomInfoData.getFacilityCustomInfoDataID();
//        if (facilityCustomInfoData.getFacility() != null) {
//            this.facilityID = facilityCustomInfoData.getFacility().getFacilityID();
//        }
//        //this.facilityID = facilityCustomInfoData.getFacility().getFacilityID();
//        this.cftCustomFacilityInfoID = facilityCustomInfoData.getCftCustomFacilityInfoID();
//        this.customInfoName = facilityCustomInfoData.getCustomInfoName();
//        this.customInfoData = facilityCustomInfoData.getCustomInfoData();
//        this.mandatory = facilityCustomInfoData.getMandatory();
//        this.displayOrder = facilityCustomInfoData.getDisplayOrder();
//        this.status = facilityCustomInfoData.getStatus();
//    }

    public FacilityCustomInfoDataDTO(CftCustomFacilityInfo cftCustomFacilityInfo, Integer facilityID){
        this.facilityCustomInfoDataID = null;
        this.facilityID = facilityID;
        this.cftCustomFacilityInfoID = cftCustomFacilityInfo.getCftCustomFacilityInfoID();
        this.customFacilityInfoName = cftCustomFacilityInfo.getCustomFacilityInfoName();
        this.customFacilityInfoCode = cftCustomFacilityInfo.getCustomFacilityInfoCode();
        this.mandatory = cftCustomFacilityInfo.getMandatory();
        this.displayOrder = cftCustomFacilityInfo.getDisplayOrder();
        this.status = cftCustomFacilityInfo.getStatus();
    }


    public FacilityCustomInfoDataDTO() {

    }
}
