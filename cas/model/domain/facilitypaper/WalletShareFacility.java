package com.itechro.cas.model.domain.facilitypaper;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "T_FP_WALLET_SHARE_FACILITIES")
public class WalletShareFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_WALLET_SHARE_FACILITIES")
    @SequenceGenerator(name = "SEQ_T_FP_WALLET_SHARE_FACILITIES", sequenceName = "SEQ_T_FP_WALLET_SHARE_FACILITIES", allocationSize = 1)
    @Column(name = "FACILITY_ID")
    private Integer facilityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WALLET_SHARE_ID")
    private WalletShare walletShare;

    @Column(name = "FACILITY")
    private String facility;

    @Column(name = "FACILITY_CURRENCY")
    private String facilityCurrency;

    @Column(name = "LIMIT_AMOUNT")
    private BigDecimal limitAmount;

    @Column(name = "OS_AMOUNT")
    private BigDecimal osAmount;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "walletShareFacility")
    private List<WSFacilitySecurity> wsFacilitySecurities = new ArrayList<>();

    public List<WSFacilitySecurity> getWsFacilitySecurities() {
        if (this.wsFacilitySecurities == null){
            this.wsFacilitySecurities = new ArrayList<>();
        }
        return wsFacilitySecurities;
    }
}
