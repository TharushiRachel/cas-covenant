package com.itechro.cas.model.dto.acae.request;

import lombok.Data;
import java.io.Serializable;

@Data
public class ACAEPaperForwardRQ implements Serializable {

    private String searchReference;
    private String accountNumber;
    private String nextUser;
    private Integer status;

}
