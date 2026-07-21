package com.itechro.cas.model.domain.bcc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.ApprovableEntity;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 *
 *
 * @author tharushi
 */
@Entity
@Getter
@Setter
@Table(name = "T_FP_BCC_TEMP")
public class FPBccTemp extends ApprovableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_BCC_TEMP")
    @SequenceGenerator(name = "SEQ_T_FP_BCC_TEMP", sequenceName = "SEQ_T_FP_BCC_TEMP", allocationSize = 1)
    @Column(name = "FP_BCC_ID")
    private Integer fpBccId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID", referencedColumnName = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Enumerated(EnumType.STRING)
    @Column(name = "BCC_STATUS")
    private AppsConstants.BccStatus bccStatus;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "fpBccTemp")
    private Set<FPBccDocumentTemp> fpBccDocumentTempSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "fpBccTemp")
    private Set<FPBccCommentTemp> fpBccCommentTempSet;
}
