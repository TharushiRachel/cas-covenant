package com.itechro.cas.model.domain.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "T_AF_SECURITY")
public class AFSecurity extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_SECURITY")
    @SequenceGenerator(name = "SEQ_T_AF_SECURITY", sequenceName = "SEQ_T_AF_SECURITY", allocationSize = 1)
    @Column(name = "SECURITY_ID")
    private Integer securityID;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "afSecurity")
    private Set<AFFacilitySecurity> afFacilitySecurities;

    public Integer getSecurityID() {
        return securityID;
    }

    public void setSecurityID(Integer securityID) {
        this.securityID = securityID;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getSecurityDetail() {
        return securityDetail;
    }

    public void setSecurityDetail(String securityDetail) {
        this.securityDetail = securityDetail;
    }

    public BigDecimal getSecurityAmount() {
        return securityAmount;
    }

    public void setSecurityAmount(BigDecimal securityAmount) {
        this.securityAmount = securityAmount;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public String getSecurityCurrency() {
        return securityCurrency;
    }

    public void setSecurityCurrency(String securityCurrency) {
        this.securityCurrency = securityCurrency;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public AppsConstants.YesNo getIsCommonSecurity() {
        return isCommonSecurity;
    }

    public void setIsCommonSecurity(AppsConstants.YesNo isCommonSecurity) {
        this.isCommonSecurity = isCommonSecurity;
    }

    public AppsConstants.YesNo getIsCashSecurity() {
        return isCashSecurity;
    }

    public void setIsCashSecurity(AppsConstants.YesNo isCashSecurity) {
        this.isCashSecurity = isCashSecurity;
    }

    public Set<AFFacilitySecurity> getAfFacilitySecurities() {
        if (afFacilitySecurities == null) {
            this.afFacilitySecurities = new HashSet<>();
        }
        return afFacilitySecurities;
    }

    public List<AFFacilitySecurity> getOrderedSecurities() {
        return getAfFacilitySecurities().stream().sorted(new Comparator<AFFacilitySecurity>() {
            @Override
            public int compare(AFFacilitySecurity o1, AFFacilitySecurity o2) {
                return o1.getFacilitySecurityID().compareTo(o2.getFacilitySecurityID());
            }
        }).collect(Collectors.toList());
    }

    public void setAfFacilitySecurities(Set<AFFacilitySecurity> afFacilitySecurities) {
        this.afFacilitySecurities = afFacilitySecurities;
    }

    public void addAFFacilitySecurities(AFFacilitySecurity afFacilitySecurity) {
        afFacilitySecurity.setAfSecurity(this);
        this.getAfFacilitySecurities().add(afFacilitySecurity);
    }


}
