package com.itechro.cas.model.dto.customer;

import com.itechro.cas.model.domain.customer.CribSearchHistory;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CribSearchHistoryDTO  implements Serializable {

    private Integer cribSearchHistoryId;

    private DocStorageDTO docStorageDTO;

    private String remark;

    private String inquiryReason;

    private BigDecimal facilityAmount;

    private String uploadedUserDisplayName;

    private String uploadedDivCode;

    private String fullName;

    private String gender;

    private String identificationType;

    private String identificationNumber;

    public CribSearchHistoryDTO() {
    }

    public CribSearchHistoryDTO(CribSearchHistory cribSearchHistory) {
        this.cribSearchHistoryId = cribSearchHistory.getCribSearchHistoryId();
        this.remark = cribSearchHistory.getRemark();
        this.uploadedUserDisplayName = cribSearchHistory.getUploadedUserDisplayName();
        this.uploadedDivCode = cribSearchHistory.getUploadedDivCode();
        this.fullName = cribSearchHistory.getFullName();
        this.gender = cribSearchHistory.getGender();
        this.identificationType = cribSearchHistory.getIdentificationType();
        this.identificationNumber = cribSearchHistory.getIdentificationNo();
        this.inquiryReason = cribSearchHistory.getInquiryReason();
        this.facilityAmount = cribSearchHistory.getFacilityAmount();

        if (cribSearchHistory.getDocStorage() != null) {
            this.docStorageDTO = new DocStorageDTO(cribSearchHistory.getDocStorage(),false);
        }
    }
}