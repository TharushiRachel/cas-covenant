package com.itechro.cas.model.dto.acae.request;

import com.itechro.cas.model.common.PagedParamDTO;
import lombok.Data;

import java.util.Date;

@Data
public class ACAESearchByStatusRQ extends PagedParamDTO {

    private String accountNumber;
    private String customerName;
    private Date recievedDate;
    private Integer acaeStatus;
    private Integer userId;

    private String solId;

    private String solName;

    private String userName;
}
