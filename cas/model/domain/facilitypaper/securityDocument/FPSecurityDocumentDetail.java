package com.itechro.cas.model.domain.facilitypaper.securityDocument;

import com.itechro.cas.model.domain.facilitypaper.FPDocumentElement;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "T_FP_SECURITY_DOCUMENT")
@Audited
public class FPSecurityDocumentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_SECURITY_DOCUMENT")
    @SequenceGenerator(name = "SEQ_T_FP_SECURITY_DOCUMENT", sequenceName = "SEQ_T_FP_SECURITY_DOCUMENT", allocationSize = 1)
    @Column(name = "SECURITY_DOCUMENT_ID")
    private Integer securityDocumentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ELEMENT_ID")
    @NotAudited
    private SecurityDocumentElement securityDocumentElement;

    @Column(name = "FACILITY_PAPER_ID")
    private Integer facilityPaperID;

    @Column(name = "FACILITY_ID")
    private Integer facilityID;

    @Column(name = "CREDIT_FACILITY_TEMPLATE_ID")
    private Integer creditFacilityTemplateID;

    @Column(name = "CREDIT_FACILITY_NAME")
    private String creditFacilityName;

    @Column(name = "DOCUMENT_NAME")
    private String documentName;

    @Column(name = "DOCUMENT_CONTENT")
    private String documentContent;

    @Column(name = "SAVED_BY")
    private String savedBy;

    @Column(name = "PRINTED_BY")
    private String printedBy;

    @Column(name = "SAVED_BY_DIV")
    private String savedByDiv;

    @Column(name = "AUTH_BY_DIV")
    private String authByDiv;

    @Column(name = "SAVED_DATE")
    private Date savedDate;

    @Column(name = "PRINTED_DATE")
    private Date printedDate;

    @Column(name = "ACTION_COMMENT")
    private String actionComment;

    @Column(name = "AUTH_BY")
    private String authBy;

    @Column(name = "AUTH_DATE")
    private Date authDate;

    @Column(name = "PRINTED_BY_DIV")
    private String printedByDiv;

    @Column(name = "SAVED_BY_DISPLAY_NAME")
    private String savedByDisplayName;

    @Column(name = "AUTH_BY_DISPLAY_NAME")
    private String authByDisplayName;

    @Column(name = "PRINTED_BY_DISPLAY_NAME")
    private String printedByDisplayName;

    @Column(name = "DOCUMENT_STATUS")
    private String documentStatus;

    @Column(name = "RECOMMENDED_RETURN_BY")
    private String recommendedReturnBy;

    @Column(name = "RECOMMENDED_RETURN_DATE")
    private Date recommendedReturnDate;

    @Column(name = "RECOMMENDED_RETURN_DISPLAY_NAME")
    private String recommendedReturnDisplayName;
}
