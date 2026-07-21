package com.itechro.cas.model.domain.facilitypaper;


import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "T_FP_WALLET_FAC_SECURITIES")
public class WSFacilitySecurity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_WALLET_FAC_SECURITIES")
    @SequenceGenerator(name = "SEQ_T_FP_WALLET_FAC_SECURITIES", sequenceName = "SEQ_T_FP_WALLET_FAC_SECURITIES", allocationSize = 1)
    @Column(name = "SECURITY_ID")
    private Integer securityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_ID")
    private WalletShareFacility walletShareFacility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WALLET_SHARE_ID")
    private WalletShare walletShare;

    @Column(name = "SECURITY_DETAIL")
    private String securityDetail;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_COMMON_SECURITY")
    private AppsConstants.YesNo isCommonSecurity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", updatable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;
}
