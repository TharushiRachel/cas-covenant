package com.itechro.cas.model.dto.integration.response.coveringApproval;

import lombok.Data;

import java.io.Serializable;

/**
 *
 *
 * @author tharushi
 */
@Data
public class RelatedPartyDetailsDTO implements Serializable {

    private String cifId;

    private String accPartyName;

    private String idNumber;

    private Object addressLine1;

    private Object addressLine2;

    private Object addressLine3;

    private Object addressCity;

    private Object addressState;

    private Object addressCountry;
}
