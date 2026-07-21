package com.itechro.cas.model.domain.coveringApproval;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.customer.Customer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 *
 *
 * @author tharushi
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "T_COVAPP_BASIC_INFO")
public class CovAppBasicInfo extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_COVAPP_BASIC_INFO")
    @SequenceGenerator(name = "SEQ_T_COVAPP_BASIC_INFO", sequenceName = "SEQ_T_COVAPP_BASIC_INFO", allocationSize = 1)
    @Column(name = "BASIC_INFORMATION_ID")
    private Integer basicInformationID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COVAPP_ID", referencedColumnName = "COVAPP_ID")
    private CoveringApproval coveringApproval;

    @Column(name = "CUSTOMER_FINANCIAL_ID")
    private String cifId;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "IDENTIFICATION_TYPE")
    private String identificationType;

    @Column(name = "IDENTIFICATION_NUMBER")
    private String identificationNumber;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "BRANCH_CODE")
    private String branchCode;

    @Column(name = "BRANCH_NAME")
    private String branchName;

    @Column(name = "TRAN_ID")
    private String tranId;

    @Column(name = "ACCOUNT_MGR")
    private String accounManager;

    @Column(name = "TRAN_DATE")
    private Date tranDate;

    @Column(name = "TRAN_AMOUNT")
    private String tranAmount;

    @Column(name = "CHEQUE_NUMBER")
    private String chequeNumber;

    @Column(name = "CLEAR_BALANCE")
    private String clearBalance;

    @Column(name = "EOD_BALANCE")
    private String asAtDateAcctBalance;

    @Column(name = "TRAN_CURRENCY")
    private String tranCurrency;

    @Column(name = "PART_TRAN_SRL")
    private String partTranSRL;

    @Column(name = "TRAN_STATUS")
    private String tranStatus;

}
