package com.itechro.cas.model.dto.acae.request;

import lombok.Data;

import java.util.Date;
@Data
public class ACAECommentRQ {

    String referenceNumber;
    String accountNumber;
    String activeComment;
    Date negDate;
    Date previousNegDate;
}
