package com.itechro.cas.model.dto.coveringApproval;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.coveringApproval.CovAppBasicInfo;
import com.itechro.cas.model.domain.customer.Customer;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 *
 * @author tharushi
 */

@Data
public class CovAppBasicInfoDTO implements Serializable {

    private Integer basicInformationID;
    private Integer covAppId;

    private String customerFinancialID;

    private String customerName;

    private String address;

    private String identificationType;

    private String identificationNumber;

    private String accountNumber;

    private String branchCode;

    private String branchName;

    private String tranId;

    private String accounManager;

    private Date tranDate;

    private String tranAmount;

    private String chequeNumber;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    private String clearBalance;

    private String asAtDateAcctBalance;

    private String tranCurrency;

    private String partTranSRL;
    private String tranStatus;
    private String floatBalance;

    private String fundsinClearing;

    private String sanctionLimit;

    public CovAppBasicInfoDTO() {
    }

    public CovAppBasicInfoDTO(CovAppBasicInfo covAppBasicInfo) {
        this.basicInformationID = covAppBasicInfo.getBasicInformationID();
        this.covAppId = covAppBasicInfo.getCoveringApproval().getCovAppId();
        this.customerFinancialID = covAppBasicInfo.getCifId();
        this.customerName = covAppBasicInfo.getCustomerName();
        this.address = covAppBasicInfo.getAddress();
        this.identificationType = covAppBasicInfo.getIdentificationType();
        this.identificationNumber = covAppBasicInfo.getIdentificationNumber();
        this.accountNumber = covAppBasicInfo.getAccountNumber();
        this.branchCode = covAppBasicInfo.getBranchCode();
        this.branchName = covAppBasicInfo.getBranchName();
        this.tranId = covAppBasicInfo.getTranId();
        this.accounManager = covAppBasicInfo.getAccounManager();
        this.tranDate = covAppBasicInfo.getTranDate();
        this.tranAmount = covAppBasicInfo.getTranAmount();
        this.chequeNumber = covAppBasicInfo.getChequeNumber();
        this.createdDate = covAppBasicInfo.getCreatedDate();
        this.createdBy = covAppBasicInfo.getCreatedBy();
        this.modifiedDate = covAppBasicInfo.getModifiedDate();
        this.modifiedBy = covAppBasicInfo.getModifiedBy();
        this.clearBalance= covAppBasicInfo.getClearBalance();
        this.tranCurrency = covAppBasicInfo.getTranCurrency();
        this.partTranSRL = covAppBasicInfo.getPartTranSRL();
        this.tranStatus = covAppBasicInfo.getTranStatus();
    }

}
