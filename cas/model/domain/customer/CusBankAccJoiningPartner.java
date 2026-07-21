package com.itechro.cas.model.domain.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.dto.customer.CusBankAccJoiningPartnerDto;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Entity
@Table(name = "T_CB_ACC_JOIN_PARTNER")
public class CusBankAccJoiningPartner extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CB_ACC_JOIN_PARTNER")
    @SequenceGenerator(name = "SEQ_T_CB_ACC_JOIN_PARTNER", sequenceName = "SEQ_T_CB_ACC_JOIN_PARTNER", allocationSize = 1)
    @Column(name = "CB_ACC_JOIN_PARTNER_ID")
    private Integer cusBankAccJoiningPartnerID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_BANK_DETAILS_ID")
    private CustomerBankDetail customerBankDetail;

    @Column(name = "CUSTOMER_FINACLE_ID")
    private String customerFinacleID;

    @Column(name = "CUSTOMER_NIC_NUMBER")
    private String customerNICNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRIMARY_CUSTOMER")
    private AppsConstants.YesNo primaryCustomer;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getCusBankAccJoiningPartnerID() {
        return cusBankAccJoiningPartnerID;
    }

    public void setCusBankAccJoiningPartnerID(Integer cusBankAccJoiningPartnerID) {
        this.cusBankAccJoiningPartnerID = cusBankAccJoiningPartnerID;
    }

    public CustomerBankDetail getCustomerBankDetail() {
        return customerBankDetail;
    }

    public void setCustomerBankDetail(CustomerBankDetail customerBankDetail) {
        this.customerBankDetail = customerBankDetail;
    }

    public String getCustomerFinacleID() {
        return customerFinacleID;
    }

    public void setCustomerFinacleID(String customerFinacleID) {
        this.customerFinacleID = customerFinacleID;
    }

    public String getCustomerNICNumber() {
        return customerNICNumber;
    }

    public void setCustomerNICNumber(String customerNICNumber) {
        this.customerNICNumber = customerNICNumber;
    }

    public AppsConstants.YesNo getPrimaryCustomer() {
        return primaryCustomer;
    }

    public void setPrimaryCustomer(AppsConstants.YesNo primaryCustomer) {
        this.primaryCustomer = primaryCustomer;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public boolean equals(CusBankAccJoiningPartnerDto cusBankAccJoiningPartnerDto) {
        if (cusBankAccJoiningPartnerDto == null) {
            return false;
        }

        if (StringUtils.isNotEmpty(cusBankAccJoiningPartnerDto.getCustomerFinacleID()) && StringUtils.isNotEmpty(cusBankAccJoiningPartnerDto.getCustomerNICNumber())) {
            return cusBankAccJoiningPartnerDto.getCustomerFinacleID().equals(this.getCustomerFinacleID()) &&
                    cusBankAccJoiningPartnerDto.getCustomerNICNumber().equals(this.getCustomerNICNumber());
        }

        if (StringUtils.isNotEmpty(cusBankAccJoiningPartnerDto.getCustomerFinacleID())) {
            return cusBankAccJoiningPartnerDto.getCustomerFinacleID().equals(this.getCustomerFinacleID());
        }

        if (StringUtils.isNotEmpty(cusBankAccJoiningPartnerDto.getCustomerNICNumber())) {
            return cusBankAccJoiningPartnerDto.getCustomerNICNumber().equals(this.getCustomerNICNumber());
        }
        return false;
    }
}
