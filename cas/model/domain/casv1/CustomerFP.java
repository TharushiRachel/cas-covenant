package com.itechro.cas.model.domain.casv1;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Table(name = "CASV1_CUSTOMER_FP_TABLE")
public class CustomerFP {

    @Column(name = "REF_NUM")
    private String refNo;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "BUS_PROF")
    private String busProf;

    @Column(name = "ACC_NO")
    private String accNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ACC_OP_DATE")
    private Date accOpDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "APP_DATE")
    private Date appDate;

    @Column(name = "NEWAP")
    private String newAp;

    @Column(name = "ADDTIONAL")
    private String additional;

    @Column(name = "RENEWAL")
    private String renewal;

    @Column(name = "TERMS_AMENDED")
    private String termsAmended;

    @Column(name = "CONNECTION_OWNER")
    private String connectionOwner;

    @Column(name = "RISK_GRADING")
    private String riskGrading;

    @Column(name = "SHARE_HOLDING")
    private String shareHolding;

    @Column(name = "BRANCH")
    private String branch;
}
