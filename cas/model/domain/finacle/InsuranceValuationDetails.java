package com.itechro.cas.model.domain.finacle;



import com.itechro.cas.model.domain.casmaster.CALevel;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "INSURANCE_VALUATION_DETAILS")
@ToString
public class InsuranceValuationDetails  implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_F_INS_VAL_DETAILS")
    @SequenceGenerator(name = "SEQ_F_INS_VAL_DETAILS", sequenceName = "SEQ_F_INS_VAL_DETAILS", allocationSize = 1)
    @Column(name = "ID")
    private Integer Id;
//    @Column(name = "COLLATERAL_ID")
//    private String collateralId;
    @Column(name = "INSURANCE_TYPE")
    private String insuranceType;
    @Column(name = "INSURER_DETAILS")
    private String insurerDetails;
    @Column(name = "POLICY_NO")
    private String policyNo;
    @Column(name = "POLICY_AMOUNT")
    private String policyAmount;
    @Column(name = "RISK_COVER_START_DATE")
    private String  riskCoverStartDate;
    @Column(name = "RISK_COVER_END_DATE")
    private String  riskCoverEndDate;
    @Column(name = "PREMIUM_AMOUNT")
    private String  premiumAmount;
    @Column(name = "ITEMS_INSURED")
    private String  itemsInsured;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COLLATERAL_ID" , referencedColumnName = "COLLATERAL_ID" )
    private CollateralDetails collateralDetails;
}
