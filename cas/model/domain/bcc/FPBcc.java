package com.itechro.cas.model.domain.bcc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.ApprovableEntity;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.facilitypaper.FPCreditRiskDocument;
import com.itechro.cas.model.domain.facilitypaper.facility.CommitteeStatusHistory;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 *
 * @author tharushi
 */
@Entity
@Getter
@Setter
@Table(name = "T_FP_BCC")
@Audited
@AuditOverride(forClass = ApprovableEntity.class)
@AuditOverride(forClass = UserTrackableEntity.class)
public class FPBcc extends ApprovableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_BCC")
    @SequenceGenerator(name = "SEQ_T_FP_BCC", sequenceName = "SEQ_T_FP_BCC", allocationSize = 1)
    @Column(name = "FP_BCC_ID")
    private Integer fpBccId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Enumerated(EnumType.STRING)
    @Column(name = "BCC_STATUS")
    private AppsConstants.BccStatus bccStatus;

    @Column(name = "CREATED_USER_DISPLAY_NAME")
    private String createdUserDisplayName;

    @Column(name = "APPROVED_USER_DISPLAY_NAME")
    private String approvedUserDisplayName;

    @Column(name = "ACTION_MESSAGE")
    private String actionMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "DOCS_APPROVE_STATUS")
    private DomainConstants.BccDocsApproveStatus docsApproveStatus;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "fpBcc")
    @NotAudited
    private Set<FPBccDocument> fpBccDocumentSet;

    public Set<FPBccDocument> getFpBccDocumentSet() {
        if (fpBccDocumentSet == null) {
            this.fpBccDocumentSet = new HashSet<>();
        }
        return fpBccDocumentSet;
    }

    public void setFpBccDocumentSet(Set<FPBccDocument> fpBccDocumentSet) {
        this.fpBccDocumentSet = fpBccDocumentSet;
    }

    public void addFpBccDocument(FPBccDocument fpBccDocument) {
        this.getFpBccDocumentSet().add(fpBccDocument);
    }


//    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "fpBcc")
//    private Set<FPBccComment> fpBccCommentSet;

}
