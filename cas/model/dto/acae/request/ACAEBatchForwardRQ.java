package com.itechro.cas.model.dto.acae.request;

import lombok.Data;
import java.io.Serializable;

@Data
public class ACAEBatchForwardRQ implements Serializable {

    private String referenceId;
    private String nextUser;
    private String thisUser;
    private Integer status;
    private String solUserName;
    private String nextUserName;
}
