package com.itechro.cas.model.domain.facilitypaper.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.util.DecimalCalculator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "T_FACILITY_SECURITY")
public class FacilitySecurity extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FACILITY_SECURITY")
    @SequenceGenerator(name = "SEQ_T_FACILITY_SECURITY", sequenceName = "SEQ_T_FACILITY_SECURITY", allocationSize = 1)
    @Column(name = "FACILITY_SECURITY_ID")
    private Integer facilitySecurityID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_ID")
    private Facility facility;

    @Column(name = "SECURITY_CODE")
    private String securityCode;

    @Column(name = "SECURITY_DETAIL")
    private String securityDetail;

    @Column(name = "AMOUNT")
    private BigDecimal securityAmount;

    @Column(name = "CASH_AMOUNT")
    private BigDecimal cashAmount;

    @Column(name = "SECURITY_CURRENCY")
    private String securityCurrency;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_COMMON_SECURITY")
    private AppsConstants.YesNo isCommonSecurity;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_CASH_SECURITY")
    private AppsConstants.YesNo isCashSecurity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "facilitySecurity", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<FacilityFacilitySecurity> facilitiesFacilitySecurities;

    public Integer getFacilitySecurityID() {
        return facilitySecurityID;
    }

    public void setFacilitySecurityID(Integer facilitySecurityID) {
        this.facilitySecurityID = facilitySecurityID;
    }

    public String getSecurityDetail() {
        return securityDetail;
    }

    public void setSecurityDetail(String securityDetail) {
        this.securityDetail = securityDetail;
    }

    public BigDecimal getSecurityAmount() {
        if (securityAmount == null){
            securityAmount = DecimalCalculator.getDefaultZero();
        }
        return securityAmount;
    }

    public void setSecurityAmount(BigDecimal securityAmount) {
        this.securityAmount = securityAmount;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public AppsConstants.YesNo getIsCommonSecurity() {
        return isCommonSecurity;
    }

    public void setIsCommonSecurity(AppsConstants.YesNo isCommonSecurity) {
        this.isCommonSecurity = isCommonSecurity;
    }

    public String getSecurityCurrency() {
        return securityCurrency;
    }

    public void setSecurityCurrency(String securityCurrency) {
        this.securityCurrency = securityCurrency;
    }

    public BigDecimal getCashAmount() {
        if (cashAmount == null){
            cashAmount = DecimalCalculator.getDefaultZero();
        }
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public AppsConstants.YesNo getIsCashSecurity() {
        return isCashSecurity;
    }

    public void setIsCashSecurity(AppsConstants.YesNo isCashSecurity) {
        this.isCashSecurity = isCashSecurity;
    }

    public Set<FacilityFacilitySecurity> getFacilitiesFacilitySecurities() {
        if (facilitiesFacilitySecurities == null) {
            facilitiesFacilitySecurities = new HashSet<>();
        }
        return facilitiesFacilitySecurities;
    }

    public List<FacilityFacilitySecurity> getOrderedFacilitiesFacilitySecurities() {
        return getFacilitiesFacilitySecurities().stream().sorted(new Comparator<FacilityFacilitySecurity>() {
            @Override
            public int compare(FacilityFacilitySecurity o1, FacilityFacilitySecurity o2) {
                return o1.getFacilitySecuritySecurityID().compareTo(o2.getFacilitySecuritySecurityID());
            }
        }).collect(Collectors.toList());
    }

    public void setFacilitiesFacilitySecurities(Set<FacilityFacilitySecurity> facilitiesFacilitySecurities) {
        this.facilitiesFacilitySecurities = facilitiesFacilitySecurities;
    }

    public void addFacilityFacilitySecurity(FacilityFacilitySecurity facilityFacilitySecurity) {
        facilityFacilitySecurity.setFacilitySecurity(this);
        this.getFacilitiesFacilitySecurities().add(facilityFacilitySecurity);
    }

    public void removeFacilityFacilitySecurity(FacilityFacilitySecurity facilityFacilitySecurity) {
        if (facilityFacilitySecurity != null) {
            this.getFacilitiesFacilitySecurities().remove(facilityFacilitySecurity);
        }
    }

    public FacilityFacilitySecurity getFacilityFacilitySecurityByFacilityID(Integer facilityID) {
        return this.getFacilitiesFacilitySecurities().stream().
                filter(facilityFacilitySecurity -> {
                    return facilityFacilitySecurity.getFacility().getFacilityID().equals(facilityID);
                }).findFirst().orElse(null);
    }
}
