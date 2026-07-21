package com.itechro.cas.model.dto.acae.response;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ACAESearchByStatusDTO implements Serializable {

    private Integer recordIndex;
    private String refNumber;
    private String accountNumber;
    private String customerName;
    private String attended;
    private Date recievedDate;
    private String status;
    private String solUserId;
    private String solUserName;
    private boolean isSelected;
    private Double minBal;
    private String currentUserName;
    private boolean isLoadUserName;
}
