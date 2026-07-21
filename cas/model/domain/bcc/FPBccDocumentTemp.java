package com.itechro.cas.model.domain.bcc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.storage.DocStorage;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;

/**
 *
 *
 * @author tharushi
 */
@Entity
@Getter
@Setter
@Table(name = "T_FP_BCC_DOC_TEMP")
public class FPBccDocumentTemp extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_BCC_DOC_TEMP")
    @SequenceGenerator(name = "SEQ_T_FP_BCC_DOC_TEMP", sequenceName = "SEQ_T_FP_BCC_DOC_TEMP", allocationSize = 1)
    @Column(name = "FP_BCC_DOC_ID")
    private Integer FPBccDocumentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FP_BCC_ID", referencedColumnName = "FP_BCC_ID")
    private FPBccTemp fpBccTemp;

    @Column(name = "DOCUMENT_NAME")
    private String documentName;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "UPLOAD_USER_DISPLAY_NAME")
    private String uploadedUserDisplayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

//    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
//    @JoinColumn(name = "DOCUMENT_STORAGE_ID", referencedColumnName = "DOCUMENT_STORAGE_ID")
//    @NotAudited
//    private DocStorage docStorage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_STORAGE_ID")
    @NotAudited
    private DocStorage docStorage;
}
