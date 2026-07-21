package com.itechro.cas.model.dto.bcc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FPBccDocUploadRQ implements Serializable {

    private Integer facilityPaperID;

    //if new fpBcc - fpBccID = 0
    private Integer fpBccID = 0;

    private String documentName;

    private String remark;

    private DocStorageDTO docStorageDTO;

    private String uploadedUserDisplayName;

    private String isSendEmail;

    private Integer fileSize;

    private String fileType;

    //private DomainConstants.MasterDataApproveStatus approveStatus;



}
