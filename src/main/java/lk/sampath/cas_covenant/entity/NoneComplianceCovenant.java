package lk.sampath.cas_covenant.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "T_NON_COMPLIANCE_COVENANTS")
public class NoneComplianceCovenant {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NON_COMPLIANCE_COVENANTS")
  @SequenceGenerator(
      name = "NON_COMPLIANCE_COVENANTS",
      sequenceName = "NON_COMPLIANCE_COVENANTS",
      allocationSize = 1)
  @Column(name = "NON_COMPLIANCE_COVENANT_ID")
  private Integer nonComplianceCovenantId;

  @Column(name = "SRL_NUMBER")
  private Integer serialNumber;

  @Column(name = "FACILITY_PAPER_ID")
  private Integer facilityPaperId;

  @Column(name = "COMMENT_DESC")
  private String comment;

  @Column(name = "ADDED_DATE")
  private Date addedDate;

  @Column(name = "ADDED_BY")
  private String addedBy;

  @Column(name = "ADDED_USER_DISPLAY_NAME")
  private String addedUserDisplayName;

  @Column(name = "ADDED_USER_ID")
  private Integer addedUserId;
}
