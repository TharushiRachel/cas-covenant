package com.itechro.cas.model.dto.bcc;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FPBccDocDeleteRQ implements Serializable {
    private Integer fpBccDocumentID;
    private AppsConstants.Status status;
    private Integer facilityPaperID;

}
