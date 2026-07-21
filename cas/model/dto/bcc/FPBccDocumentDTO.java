package com.itechro.cas.model.dto.bcc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.bcc.FPBccDocument;
import com.itechro.cas.model.dto.facilitypaper.FPLoadOptionDTO;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter

public class FPBccDocumentDTO implements Serializable {

    private Integer fpBccDocumentID;

    private Integer fpBccID;

    private String documentName;

    private String remark;

    private String uploadedUserDisplayName;

    private DocStorageDTO docStorageDTO;

    private String status;

    private String createdBy;

    private String isSendEmail;

    private Integer fileSize;
    private String fileType;
    private DomainConstants.MasterDataApproveStatus approveStatus;


    public FPBccDocumentDTO(FPBccDocument fpBccDocument, boolean loadDoc) {
        this.fpBccDocumentID = fpBccDocument.getFPBccDocumentId();
        this.fpBccID = fpBccDocument.getFpBcc().getFpBccId();
        this.documentName = fpBccDocument.getDocumentName();
        this.remark = fpBccDocument.getRemark();
        this.uploadedUserDisplayName = fpBccDocument.getUploadedUserDisplayName();
        this.status = fpBccDocument.getStatus().getDescription();
        this.createdBy = fpBccDocument.getCreatedBy();
        this.docStorageDTO = new DocStorageDTO(fpBccDocument.getDocStorage(), loadDoc);
        this.isSendEmail = fpBccDocument.getIsSendEmail();
        this.fileSize = fpBccDocument.getFileSize();
        this.fileType = fpBccDocument.getFileType();
        this.approveStatus = fpBccDocument.getApproveStatus();
    }

    public FPBccDocumentDTO(FPBccDocument fpBccDocument) {
        this(fpBccDocument, true);
    }

    public FPBccDocumentDTO(){}

    @Override
    public String toString() {
        return "FPBccDocumentDTO{" +
                "fpBccDocumentID=" + fpBccDocumentID +
                ", fpBccID=" + fpBccID +
                ", documentName='" + documentName + '\'' +
                ", remark='" + remark + '\'' +
                ", uploadedUserDisplayName='" + uploadedUserDisplayName + '\'' +
                ", docStorageDTO=" + docStorageDTO +
                ", status='" + status + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", isSendEmail='" + isSendEmail + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", fileType='" + fileType + '\'' +
                ", approveStatus='" + approveStatus + '\'' +
                '}';
    }
}
