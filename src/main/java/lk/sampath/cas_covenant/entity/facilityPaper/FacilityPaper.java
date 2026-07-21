package lk.sampath.cas_covenant.entity.facilityPaper;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_FACILITY_PAPER")
@Getter
@Setter
public class FacilityPaper {

  @Id
  @Column(name = "FACILITY_PAPER_ID")
  private Integer facilityPaperID;

  @Column(name = "FP_REF_NUMBER")
  private String fpRefNumber;
}
