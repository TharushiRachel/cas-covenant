package com.itechro.cas.model.dto.facilitypaper.securityDocument;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.securityDocument.FPSecurityDocumentDetail;
import com.itechro.cas.util.CalendarUtil;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SecurityDocumentDTO implements Serializable {

    private Integer securityDocumentID;

    private Integer facilityPaperID;

    private Integer creditFacilityTemplateID;

    private String creditFacilityName;

    private Integer elementID;

    private Integer facilityID;

    private String documentName;

    private String documentContent;

    private String documentStatus;

    private String savedBy;

    private String authBy;

    private String printedBy;

    private String savedDateStr;

    private String printedDateStr;

    private String authDateStr;

    private String savedByDiv;

    private String authByDiv;

    private String printedByDiv;

    private String savedByDisplayName;

    private String authByDisplayName;

    private String printedByDisplayName;

    private String actionComment;

    private AppsConstants.YesNo isRecommendedReturn;

    private String recommendedReturnBy;

    private String recommendedReturnDateStr;

    private String recommendedReturnDisplayName;

    private List<FPSecurityDocumentTagDTO> documentTags;

    public SecurityDocumentDTO() {
    }

    public SecurityDocumentDTO(FPSecurityDocumentDetail securityDocument) {
        this.securityDocumentID = securityDocument.getSecurityDocumentID();
        this.facilityPaperID = securityDocument.getFacilityPaperID();
        this.creditFacilityTemplateID = securityDocument.getCreditFacilityTemplateID();
        this.creditFacilityName = securityDocument.getCreditFacilityName();
        this.elementID = securityDocument.getSecurityDocumentElement().getElementID();
        this.facilityID = securityDocument.getFacilityID();
        this.documentName = securityDocument.getDocumentName();
        this.documentContent = securityDocument.getDocumentContent();
        this.documentStatus = securityDocument.getDocumentStatus();
        this.savedBy = securityDocument.getSavedBy();
        this.authBy = securityDocument.getAuthBy();
        this.printedBy = securityDocument.getPrintedBy();
        this.savedByDiv = securityDocument.getSavedByDiv();
        this.authByDiv = securityDocument.getAuthByDiv();
        this.printedByDiv = securityDocument.getPrintedByDiv();
        this.savedByDisplayName = securityDocument.getSavedByDisplayName();
        this.authByDisplayName = securityDocument.getAuthByDisplayName();
        this.printedByDisplayName = securityDocument.getPrintedByDisplayName();
        this.actionComment = securityDocument.getActionComment();
        this.isRecommendedReturn = securityDocument.getRecommendedReturnBy() != null && !securityDocument.getRecommendedReturnBy().isEmpty() ?
                                    AppsConstants.YesNo.Y : AppsConstants.YesNo.N;
        this.recommendedReturnBy = securityDocument.getRecommendedReturnBy();
        this.recommendedReturnDisplayName = securityDocument.getRecommendedReturnDisplayName();

        if (securityDocument.getSavedDate() != null) {
            this.savedDateStr = CalendarUtil.getDefaultFormattedDateTime(securityDocument.getSavedDate());
        }
        if (securityDocument.getAuthDate() != null) {
            this.authDateStr = CalendarUtil.getDefaultFormattedDateTime(securityDocument.getAuthDate());
        }
        if (securityDocument.getPrintedDate() != null) {
            this.printedDateStr = CalendarUtil.getDefaultFormattedDateTime(securityDocument.getPrintedDate());
        }
        if (securityDocument.getRecommendedReturnDate() != null) {
            this.recommendedReturnDateStr = CalendarUtil.getDefaultFormattedDateTime(securityDocument.getRecommendedReturnDate());
        }
    }
}
