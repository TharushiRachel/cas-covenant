package com.itechro.cas.model.dto.integration.response.coveringApproval;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 *
 * @author tharushi
 */
@Data
public class CovAppCustomerDTO implements Serializable {

    private String Status;
    private String AccountNo;
    private String accountManagerUserId;

    private String acctSolId;

    private String clearBalance;

    private String asAtDateAcctBalance;

    private String floatBalance;

    private String fundsinClearing;

    private String sanctionLimit;

    private String acctOpenDate;

    private String schmCode;

    private List<RelatedPartyDetailsDTO> relatedPartyDetails;
}
