package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
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
@Table(name = "T_FP_WALLET_SHARES")
public class WalletShare {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_WALLET_SHARES")
    @SequenceGenerator(name = "SEQ_T_FP_WALLET_SHARES", sequenceName = "SEQ_T_FP_WALLET_SHARES", allocationSize = 1)
    @Column(name = "WALLET_SHARE_ID")
    private Integer walletShareId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Column(name = "BANK")
    private String bank;

    @Column(name = "LIMIT_AMOUNT_TOTAL")
    private BigDecimal limitAmountTotal;

    @Column(name = "SHARE_PERCENTAGE")
    private BigDecimal share;

    @Column(name = "OS_AMOUNT_TOTAL")
    private BigDecimal osAmountTotal;

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

    @Column(name = "IS_SYSTEM")
    private Integer isSystem;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "walletShare")
    private List<WalletShareFacility> facilities = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "walletShare")
    private List<WSFacilitySecurity> wsFacilitySecurities = new ArrayList<>();

    public List<WalletShareFacility> getFacilities() {
        if (facilities == null){
            facilities = new ArrayList<>();
        }
        return facilities;
    }

    public List<WSFacilitySecurity> getWsFacilitySecurities() {
        if (wsFacilitySecurities == null){
            wsFacilitySecurities = new ArrayList<>();
        }
        return wsFacilitySecurities;
    }
}
