package lk.sampath.cas_covenant.entity;

import jakarta.persistence.*;
import java.util.Date;

import lk.sampath.cas_covenant.entity.common.UserTrackableEntity;
import lk.sampath.cas_covenant.entity.facilityPaper.FacilityPaper;
import lk.sampath.cas_covenant.enums.CovenantApplicableType;
import lk.sampath.cas_covenant.enums.CovenantStatus;
import lk.sampath.cas_covenant.enums.CovenantStatusOnDisbursement;
import lk.sampath.cas_covenant.enums.YesNoStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tharushi
 */
@Entity
@Getter
@Setter
@Table(name = "CUSTOMER_COVENANT")
public class CustomerCovenant extends UserTrackableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CUSTOMER_COVENANT")
  @SequenceGenerator(
      name = "SEQ_CUSTOMER_COVENANT",
      sequenceName = "SEQ_CUSTOMER_COVENANT",
      allocationSize = 1)
  @Column(name = "CUSTOMER_COVENANT_ID")
  private Integer customerCovenantId;

  @Column(name = "REQUESTUUID")
  private String requestUUID;

  @Column(name = "CUSTOMER_FINANCIAL_ID")
  private String customerFinancialID;

  //    @Column(name = "FP_REF_NUMBER")
  //    private String facilityPaperRefNumber;
  //
  //    @Column(name = "FACILITY_PAPER_ID")
  //    private Integer facilityPaperId;

  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.ALL})
  @JoinColumns({
    @JoinColumn(name = "FP_REF_NUMBER", referencedColumnName = "FP_REF_NUMBER"),
    @JoinColumn(name = "FACILITY_PAPER_ID", referencedColumnName = "FACILITY_PAPER_ID")
  })
  private FacilityPaper facilityPaper;

  @Column(name = "COVENANT_CODE")
  private String covenant_Code;

  @Column(name = "COVENANT_DESCRIPTION")
  private String covenant_Description;

  @Column(name = "COVENANT_FREQUENCY")
  private String covenant_Frequency;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "COVENANT_DUE_DATE")
  private Date covenant_Due_Date;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS")
  private CovenantStatus status;

  @Column(name = "CREATED_USER_DISPLAY_NAME")
  private String createdUserDisplayName;

  @Enumerated(EnumType.STRING)
  @Column(name = "DISBURSEMENT_TYPE")
  private CovenantStatusOnDisbursement disbursementType;

  @Column(name = "NO_FREQUENCY_DUE_DATE")
  private String noFrequencyDueDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "IS_EXISTS")
  private YesNoStatus isExists;

  @Column(name = "COMPLIENCE_STATUS")
  private String complianceStatus;

  @Column(name = "DISPLAY_ORDER")
  private Integer displayOrder;

  @Enumerated(EnumType.STRING)
  @Column(name = "APPLICABLE_TYPE")
  private CovenantApplicableType applicableType;
}
