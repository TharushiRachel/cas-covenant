package com.itechro.cas.model.dto.integration.request;

import lombok.Data;

import java.io.Serializable;

/**
 *
 *
 * @author tharushi
 */
@Data
public class LoadTranDataRQ implements Serializable {

    private String reqID;

    private String tranId;

    private String trnDate;

    private String prtsrlS;

    private String prtsrlE;
}
