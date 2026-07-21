package com.itechro.cas.model.dto.acae.request;

import lombok.Data;
import java.io.Serializable;

@Data
public class ACAEPaperTransferRQ implements Serializable {

    private String searchReference;
    private String accountNumber;
    private String nextUser;
    private String thisUser;
    private Integer status;
    private Integer numOfDays;
    private String currentUsername;

}
