package com.itechro.cas.model.dto.integration.response.coveringApproval;

import com.itechro.cas.commons.constants.DomainConstants;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * @author tharushi
 */
@Data
public class CoveringApprovalTranDetailsDTO implements Serializable {

    private String partTrnSRL;

    private String accNumber;

    private String accName;

    private String tran_amt;

    private String trn_type;

    private String schm_code;

    private String schm_type;

    private String tran_particular;

    private String tran_rmks;

    private String instNum;

    private String instType;

    private String trnSub;
    private String trnCrncy;

    private String trnRate;

    private String rateCode;

    private String psdtFlg;

    private String valueDate;

    private String entryUSR;

    private String entryDate;

    private Object psdtUSR;

    private Object psdtDate;

    private String verUSR;

    private String verDate;

    private String delFlg;

    private String trnStatus;

    private String isExists;

    private Date createdDate;
    private String createdBy;

    private DomainConstants.CoveringApprovalStatus currentStatus;

}
