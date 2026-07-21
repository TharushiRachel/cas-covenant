package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;

import com.itechro.cas.model.domain.common.ApprovableEntity;


import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "T_USER_DA")
public class UserDa extends ApprovableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_USER_DA")
    @SequenceGenerator(name = "SEQ_T_USER_DA", sequenceName = "SEQ_T_USER_DA", allocationSize = 1)
    @Column(name = "USER_DA_ID")
    private Integer userDaID;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "MAX_AMOUNT")
    private BigDecimal maxAmount;

    @Column(name = "CLEAN_AMOUNT")
    private BigDecimal cleanAmount;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getUserDaID() {
        return userDaID;
    }

    public void setUserDaID(Integer userDaID) {
        this.userDaID = userDaID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public BigDecimal getCleanAmount() {
        return cleanAmount;
    }

    public void setCleanAmount(BigDecimal cleanAmount) {
        this.cleanAmount = cleanAmount;
    }
}

