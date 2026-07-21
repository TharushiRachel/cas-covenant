package com.itechro.cas.model.domain.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "T_AF_FACILITY_SECURITY")
public class AFFacilitySecurity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_FACILITY_SECURITY")
    @SequenceGenerator(name = "SEQ_T_AF_FACILITY_SECURITY", sequenceName = "SEQ_T_AF_FACILITY_SECURITY", allocationSize = 1)
    @Column(name = "FACILITY_SECURITY_ID")
    private Integer facilitySecurityID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_ID")
    private AFFacility afFacility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SECURITY_ID")
    private AFSecurity afSecurity;

    @Column(name = "FACILITY_SECURITY_AMOUNT")
    private BigDecimal facilitySecurityAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_CASH_SECURITY")
    private AppsConstants.YesNo isCashSecurity;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getFacilitySecurityID() {
        return facilitySecurityID;
    }

    public void setFacilitySecurityID(Integer facilitySecurityID) {
        this.facilitySecurityID = facilitySecurityID;
    }

    public AFFacility getAfFacility() {
        return afFacility;
    }

    public void setAfFacility(AFFacility afFacility) {
        this.afFacility = afFacility;
    }

    public AFSecurity getAfSecurity() {
        return afSecurity;
    }

    public void setAfSecurity(AFSecurity afSecurity) {
        this.afSecurity = afSecurity;
    }

    public BigDecimal getFacilitySecurityAmount() {
        return facilitySecurityAmount;
    }

    public void setFacilitySecurityAmount(BigDecimal facilitySecurityAmount) {
        this.facilitySecurityAmount = facilitySecurityAmount;
    }

    public AppsConstants.YesNo getIsCashSecurity() {
        return isCashSecurity;
    }

    public void setIsCashSecurity(AppsConstants.YesNo isCashSecurity) {
        this.isCashSecurity = isCashSecurity;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
