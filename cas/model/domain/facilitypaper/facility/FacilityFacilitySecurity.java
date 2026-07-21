package com.itechro.cas.model.domain.facilitypaper.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.util.DecimalCalculator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "T_FACILITY_F_SECURITY")
public class FacilityFacilitySecurity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FACILITY_F_SECURITY")
    @SequenceGenerator(name = "SEQ_T_FACILITY_F_SECURITY", sequenceName = "SEQ_T_FACILITY_F_SECURITY", allocationSize = 1)
    @Column(name = "FACILITY_FACILITY_SECURITY_ID")
    private Integer facilitySecuritySecurityID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_ID")
    private Facility facility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_SECURITY_ID")
    private FacilitySecurity facilitySecurity;

    @Column(name = "FACILITY_SECURITY_AMOUNT")
    private BigDecimal facilitySecurityAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_CASH_SECURITY")
    private AppsConstants.YesNo isCashSecurity;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getFacilitySecuritySecurityID() {
        return facilitySecuritySecurityID;
    }

    public void setFacilitySecuritySecurityID(Integer facilitySecuritySecurityID) {
        this.facilitySecuritySecurityID = facilitySecuritySecurityID;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public FacilitySecurity getFacilitySecurity() {
        return facilitySecurity;
    }

    public void setFacilitySecurity(FacilitySecurity facilitySecurity) {
        this.facilitySecurity = facilitySecurity;
    }

    public BigDecimal getFacilitySecurityAmount() {
        if (facilitySecurityAmount == null){
            facilitySecurityAmount = DecimalCalculator.getDefaultZero();
        }
        return facilitySecurityAmount;
    }

    public void setFacilitySecurityAmount(BigDecimal facilitySecurityAmount) {
        this.facilitySecurityAmount = facilitySecurityAmount;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public AppsConstants.YesNo getIsCashSecurity() {
        return isCashSecurity;
    }

    public void setIsCashSecurity(AppsConstants.YesNo isCashSecurity) {
        this.isCashSecurity = isCashSecurity;
    }
}
